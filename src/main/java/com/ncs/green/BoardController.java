package com.ncs.green;

//import java.io.UnsupportedEncodingException;
//import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import service.BoardService;
import vo.BoardVO;

@Controller
public class BoardController {
	@Autowired
	BoardService service;

	// ** Ajax jsonView BoardDetail
	   @RequestMapping(value = "/jbdetail")
	   public ModelAndView jbdetail(HttpServletResponse response, ModelAndView mv, BoardVO vo) {
	      
	      // jsonView 사용시 response 의 한글 처리
	      response.setContentType("text/html; charset=UTF-8");
	      
	      vo = service.selectOne(vo);
	      if ( vo != null) {
	         mv.addObject("content", vo.getContent());
	      }else {
	         mv.addObject("content","~~ 글번호에 해당하는 글이 없습니다 ~~");
	      }
	      mv.setViewName("jsonView");
	      return mv;
	   } //jbdetail
	
	
	//Ajax ab BoardList
	@RequestMapping(value = "/ablist")
	public ModelAndView ablist(BoardVO vo, ModelAndView mv) {
		List<BoardVO> list = service.selectList();
		if ( list != null) {
			mv.addObject("Banana", list);
		}else {
			mv.addObject("message","~~ 출력자료가 1건도 없습니다 ~~");
		}
		mv.setViewName("axjxTest/axboardList");
		return mv;
	} //abList


	//Ajax Id BoardList
	@RequestMapping(value = "/idblist")
	public ModelAndView idblist(BoardVO vo, ModelAndView mv) {

		List<BoardVO> list = service.idbList(vo);
		if ( list != null) {
			mv.addObject("Banana", list);
		}else {
			mv.addObject("message","~~ 출력자료가 1건도 없습니다 ~~");
		}
		mv.setViewName("board/boardList");
		return mv;
	} //idbList


	//** 답글 등록
	// 1) rinsertForm 출력
	@RequestMapping(value = "/rinsertf")
	public ModelAndView rinsertf ( ModelAndView mv, BoardVO vo) {

		mv.setViewName("board/replyForm");
		return mv;
	}

	//2) 답글 저장	
	@RequestMapping(value = "/rinsert") 
	public ModelAndView rinsert( ModelAndView mv, BoardVO vo) {
		//vo 에 담겨있는 Value 
		//=> id, title , content 저장을위해 필요한 값
		//=> root , step , indent 는 부모글(원글)의 value
		//    -> 답글의 root는 원글 root 와 동일
		//    ->답글의 step는 원글 step+1
		//        기존 답글의 step의 값이 위에서 계산된 step 보다 같거나 큰 경우에는 
		//        1씩 모두증가해야한다.(sql 에서 처리)
		//    -> 답글의 indent 는 원글 indent+1

		vo.setStep(vo.getStep()+1);
		vo.setIndent(vo.getIndent()+1);

		if(service.rinsert(vo)>0) {
			//성공 -> 
			mv.addObject("message","글 등록 성공");
			mv.setViewName("redirect:blist");
		}else {
			mv.addObject("message","글등록 삭제");
			mv.setViewName("board/replyForm");
		}
		return mv;
	}

	//**************************************************기존 CRUD

	@RequestMapping(value = "/blist")
	public ModelAndView blist(HttpServletRequest request, ModelAndView mv) {

		List<BoardVO> list = service.selectList();
		if ( list != null) {
			mv.addObject("Banana", list);
		}else {
			mv.addObject("message","~~ 출력자료가 1건도 없습니다 ~~");
		}
		// redirect 요청시 전달된 message 처리
		// => from mdetail
		if (request.getParameter("message") !=null )
			mv.addObject("message",request.getParameter("message"));

		mv.setViewName("board/boardList"); // forward
		return mv;
	} //bList

	@RequestMapping(value = "/bdetail")
	public ModelAndView bdetail(HttpServletRequest request, ModelAndView mv, BoardVO vo) {
		// ** 조회수 증가
		// => 조건 : 글쓴이의 ID 와 글보는이의 ID (logID) 가 다른 경우
		//          로그인 하지않은 경우도 포함. 
		// => 처리 순서 : 증가 후 조회
		// => 증가 
		//    controller, Java 구문으로 : getCnt() -> setCnt(++getCnt()) -> board Update 
		//     DAO 에서 sql 구문으로 : board update -> cnt=cnt+1

		// 1. 증가조건 확인 
		// => session 에서 logID get
		HttpSession session = request.getSession(false);
		String loginID = null; // 로그인 하지 않음
		if (session !=null && session.getAttribute("loginID") !=null) {
			loginID = (String)session.getAttribute("loginID");
		}

		// => board , selectOne 으로 ID get
		vo=service.selectOne(vo);
		if (vo !=null) {
			// 2. 비교 & 증가
			//=> 조건 처리
			if (!vo.getId().equals(loginID)) {
				vo.setCnt(vo.getCnt()+1);
				service.countUp(vo);
			}
			if ("U".equals(request.getParameter("jcode"))) {
				mv.setViewName("board/updateForm");   
			}else {
				mv.setViewName("board/boardDetail");  
			}

		}else {
			mv.addObject("message", "~~ 글번호에 해당하는글이 존재하지 않습니다 ~~");
			mv.setViewName("redirect:blist");
			//아래껀 참고용으로.
			/* url 로 전달되는 한글 message 처리 위한 encoding
	         String message = URLEncoder.encode("~~ 해당하는 글이 없네용 ~~", "UTF-8");
	         mv.setViewName("redirect:blist?message="+message); // sendRedirect
	         => 메서드 헤더에 throws UnsupportedEncodingException  해야함
			 */
		}
		// redirect 요청시 전달된 message 처리
		// => from mdetail
		if (request.getParameter("message") !=null )
			mv.addObject("message",request.getParameter("message"));

		return mv;
	} //bdetail


	@RequestMapping(value = "/bupdate")
	public ModelAndView bupdate( ModelAndView mv, BoardVO vo)  {
		String message = null;
		if(service.update(vo)>0) {
			mv.addObject("message","글수정 성공");
		}else {
			mv.addObject("message","글수정 실패");
		}
		mv.setViewName("redirect:bdetail?seq="+vo.getSeq());
		return mv;
	} //bupdate


	@RequestMapping(value = "/bdelete")
	public ModelAndView bdelete( ModelAndView mv, BoardVO vo) {

		String message = null;
		if(service.delete(vo)>0) {
			//성공 -> blist
			mv.addObject("message","글삭제 성공!");
			mv.setViewName("redirect:blist");

		}else {
			//실패시 bdetail
			mv.addObject("message","글삭제 실패");
			mv.setViewName("redirect:bdetail?seq="+vo.getSeq());
		}
		return mv;

	} //bdelete

	@RequestMapping(value = "/binsertf")
	public ModelAndView binsertf( ModelAndView mv, BoardVO vo) {
		mv.setViewName("board/insertForm");
		return mv;
	}

	@RequestMapping(value = "/binsert") 
	public ModelAndView binsert( ModelAndView mv, BoardVO vo) {
		if(service.insert(vo)>0) {
			//성공 -> boardlist
			mv.addObject("message","글 등록 성공");
			mv.setViewName("redirect:blist");
		}else {
			mv.addObject("message","글등록 삭제");
			mv.setViewName("board/insertForm");
		}
		return mv;
	}

} //class

