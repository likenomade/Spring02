package com.ncs.green;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import service.MemberService;
import vo.MemberVO;

@Controller
public class MemberController {
	@Autowired
	MemberService service;
	
	// ** Image DownLoad
	   @RequestMapping(value = "/dnload")
	   public ModelAndView dnload(ModelAndView mv, @RequestParam("dnfile") String dnfile) {
	      // String dnfile=request.getParameter("dnfile") 와 동일

	      dnfile =  "C:\\IDset\\eclipse\\Spring02\\src\\main\\webapp\\" + dnfile;
	      System.out.println("** dnfile => " + dnfile);
	      File file = new File(dnfile);

	      mv.addObject("downloadFile", file);
	      mv.setViewName("download");
	      // 일반적인 경우 views/download.jsp 를 찾음, 그러나 이 경우에는 아님
	      // => servlet-context.xml 에 설정하는 view 클래스 (DownloadView.java) 의
	      // id 와 동일 해야함.
	      return mv;

	      /*
	       * 위 50~51 행은 아래처럼 작성할 수도 있다. return new ModelAndView("download",
	       * "downloadFile", file);
	       * 
	       * => 생성자 참고 public ModelAndView(View view, String modelName, Object
	       * modelObject) { this.view = view; addObject(modelName, modelObject); }
	       */
	   } // dnload
	
	
	// Ajax jsonView MemberList
	@RequestMapping(value = "/amlist")
	public ModelAndView amlist(HttpServletRequest request, ModelAndView mv) {
		List<MemberVO> list = service.selectList();
		if ( list != null) {
			mv.addObject("Banana", list);
		}else {
			mv.addObject("message","~~ 출력자료가 1건도 없습니다 ~~");
		}
		mv.setViewName("axjxTest/axMemberList"); // forward
		return mv;
	} //amList
	
	// Ajax jsonView Member Delete
	@RequestMapping(value = "/jsdelete")
	public ModelAndView jsdelete( ModelAndView mv , MemberVO vo) {

		if (service.delete(vo) >0) {
			mv.addObject("success", "T");
		}else {
			mv.addObject("success", "F");
		}
		mv.setViewName("jsonView");
		return mv;
	} //jsdelete
	
	// ** JSON : 제이슨, JavaScript Object Notation
	// 자바스크립트의 객체 표기법으로, 데이터를 전달 할 때 사용하는 표준형식.
	// 속성(key) 과 값(value) 이 하나의 쌍을 이룸
	// ** JAVA객체 -> JSON 변환하기
	// 1) GSON
	// : 자바 객체의 직렬화/역직렬화를 도와주는 라이브러리 (구글에서 만듦)
	// 즉, JAVA객체 -> JSON 또는 JSON -> JAVA객체
	// 2) @ResponseBody (매핑 메서드에 적용)
	// : 메서드의 리턴값이 View 를 통해 출력되지 않고 HTTP Response Body 에 직접 쓰여지게 됨.
	// 이때 쓰여지기전, 리턴되는 데이터 타입에 따라 종류별 MessageConverter에서 변환이 이뤄진다.
	// MappingJacksonHttpMessageConverter 를 사용하면 request, response 를 JSON 으로 변환
	// view (~.jsp) 가 아닌 Data 자체를 전달하기위한 용도
	// @JsonIgnore : VO 에 적용하면 변환에서 제외

	// 3) jsonView
	// => Spring 에서 MappingJackson2JsonView를 사용해서
	// ModelAndView를 json 형식으로 반환해 준다.
	// => 방법
	// -> pom dependency추가 , xml 에 bean 등록
	// -> return할 ModelAndView 생성시 View를 "jsonView"로 설정

	// ** Json Login Test
	//=> viewName 을 "jsonView"로
	// => addObject 
	//=> 성공 loginSuccess == 'T' : 성공 
	// =>실패  loginSuccess == 'F' :실패,  message
	@RequestMapping(value = "/jlogin")
	public ModelAndView jlogin(HttpServletRequest request, 
	         HttpServletResponse response, ModelAndView mv, MemberVO vo) {
	      
	      // jsonView 사용시 response 의 한글 처리
	      response.setContentType("text/html; charset=UTF-8");
		
		String password = vo.getPassword();
		vo = service.selectOne(vo);
		if ( vo != null) { // ID Ok
			if (vo.getPassword().equals(password)) {
				// Login 성공 -> session 보관, home
				request.getSession().setAttribute("loginID", vo.getId());
				mv.addObject("loginSuccess",'T');
			}else {
				// Password 오류
				mv.addObject("message", "~~ Password 오류 !! 다시 하세요 ~~");
				mv.addObject("loginSuccess",'F');
			}
		}else { // ID 오류
			mv.addObject("message", "~~ ID 오류 !! 다시 하세요 ~~");
			mv.addObject("loginSuccess",'F');
		}
		mv.setViewName("jsonView");
		return mv;
	} //jlogin
	
	//***ID 중복확인	
	@RequestMapping(value = "/idCheck")
	public ModelAndView idCheck(ModelAndView mv, MemberVO vo) {
		//전달된 ID 가 존재하는지 확인
		// => not Null : 존재 -> 사용불가
		// => Null : 없음 -> 사용가능
		//=> 그러므로 전달된 ID 보관 해야함.
		mv.addObject("newID", vo.getId());
		if (service.selectOne(vo)!=null) {
			mv.addObject("idUse","F"); //사용불가
		}else {
			mv.addObject("idUse","T"); //사용불가
		}
		mv.setViewName("member/idDupCheck");
		return mv;
	}//idCheck

	// *** Login & Logout
	@RequestMapping(value = "/mloginf")
	public ModelAndView mloginf(ModelAndView mv) {
		mv.setViewName("login/loginForm");
		return mv;
	}

	@RequestMapping(value = "/mlogin")
	public ModelAndView mlogin(HttpServletRequest request, ModelAndView mv, MemberVO vo) {

		String password = vo.getPassword();
		vo = service.selectOne(vo);
		if ( vo != null) { // ID Ok
			if (vo.getPassword().equals(password)) {
				// Login 성공 -> session 보관, home
				request.getSession().setAttribute("loginID", vo.getId());
				mv.setViewName("redirect:home"); 
			}else {
				// Password 오류
				mv.addObject("message", "~~ Password 오류 !! 다시 하세요 ~~");
				mv.setViewName("login/loginForm");
			}
		}else { // ID 오류
			mv.addObject("message", "~~ ID 오류 !! 다시 하세요 ~~");
			mv.setViewName("login/loginForm");
		}
		return mv;
	} //mlogin

	/*
	 * @RequestMapping(value = "/mlogout") public ModelAndView
	 * mlogout(HttpServletRequest request, ModelAndView mv) throws
	 * UnsupportedEncodingException {
	 * 
	 * HttpSession session = request.getSession(false); String message = null; if
	 * (session !=null && session.getAttribute("loginID") !=null) {
	 * session.invalidate(); message = "~~ 로그아웃 성공 ~~"; }else { message =
	 * "~~ 로그인 하지 않았습니다 ~~"; } message = URLEncoder.encode(message, "UTF-8"); //
	 * mv.setViewName("home"); -> home.jsp
	 * mv.setViewName("redirect:home?message="+message); // 요청명 home , "redirect:" 는
	 * 오류 return mv; } //mlogout
	 */
	// *** List & Detail	
	@RequestMapping(value = "/mlogout")
	   public ModelAndView mlogout(HttpServletRequest request, 
	                  ModelAndView mv, RedirectAttributes rttr) {
	      
	      HttpSession session = request.getSession(false);
	      String message = null;
	      if (session !=null && session.getAttribute("loginID") !=null) {
	         session.invalidate();
	         message = "~~ 로그아웃 성공 ~~";
	      }else {
	         message = "~~ 로그인 하지 않았습니다 ~~";
	      }
	      rttr.addFlashAttribute("message",message) ;
	      // session 에 보관되므로 url에 붙지않이 때문에 깨끗하고 f5(새로고침)에 영향을 주지않음  
	      // home 에서 request 처리하지않아도 됨
	      // 단, rttr.addAttribute("message",message) 는 url 에 붙어전달됨 
	      
	      // mv.setViewName("home"); -> home.jsp
	      mv.setViewName("redirect:home"); 
	      // 요청명 home , "redirect:" 는 오류
	      return mv;
	   } //mlogout
	
	
	@RequestMapping(value = "/mlist")
	public ModelAndView mlist(HttpServletRequest request, ModelAndView mv) {

		List<MemberVO> list = service.selectList();
		if ( list != null) {
			mv.addObject("Banana", list);
		}else {
			mv.addObject("message","~~ 출력자료가 1건도 없습니다 ~~");
		}
		// redirect 요청시 전달된 message 처리
		// => from mdetail
		if (request.getParameter("message") !=null )
			mv.addObject("message",request.getParameter("message"));

		mv.setViewName("member/memberList"); // forward
		return mv;
	} //mList

	@RequestMapping(value = "/mdetail")
	public ModelAndView mdetail(HttpServletRequest request, ModelAndView mv, MemberVO vo) 
			throws UnsupportedEncodingException {

		// redirect 요청시 전달된 message 처리
		// => from mdetail
		if (request.getParameter("message") !=null )
			mv.addObject("message",request.getParameter("message"));

		vo = service.selectOne(vo);
		if ( vo != null) {
			if ("U".equals(request.getParameter("jcode"))) {
				mv.setViewName("member/updateForm");   
			}else {
				mv.setViewName("member/memberDetail");  
			}
		}else {
			// url 로 전달되는 한글 message 처리 위한 encoding
			String message = URLEncoder.encode("~~ member 가 없네용 ~~", "UTF-8");
			mv.setViewName("redirect:mlist?message="+message); // sendRedirect
		}
		return mv;
	} //mList

	// *** Join 
	@RequestMapping(value = "/mjoinf")
	public ModelAndView mjoinf(ModelAndView mv) {
		mv.setViewName("member/joinForm");
		return mv;
	} //mjoinf

	@RequestMapping(value = "/mjoin")
	public ModelAndView mjoin(HttpServletRequest request, ModelAndView mv, MemberVO vo)
			throws  IOException {

		// ** Uploadfile (image)처리
		// => MultipartFile 타입의 uploadfilef 의 정보에서 화일명을 get,
		// => upload된 image 를 서버의 정해진 폴더 (물리적위치)에 저장 하고, -> file1
		// => 이 위치에 대한 정보를 table에 저장 (vo에 set) -> file2

		// 실제파일을 보관할 물리적 위치 찾기
		//1) 현재 작업중인 이클립스 기준(배포전, ver01)
		// =>C:/IDset\eclipse/Spring02/src/main/webapp/resources/uploadImage
		// 2) 배포후에는 서버 내에서의 위치가 됨.

		// ** 경로
		String realPath = request.getRealPath("/");
		System.out.println("realPath_ver01 : "+realPath);
		//회원가입 폼에서 오류를 내면 realpath 경로가나옴.

		//실습1)  ver01
		realPath  = "C:\\IDset\\eclipse\\Spring02\\src\\main\\webapp\\resources\\uploadImage/";

		//** 폴더 만들기 (File 클래스 활용)
		// => 저장 경로에 폴더가 없는 경우 만들어 준다
		File f1 = new File(realPath); // 매개변수 로 지정된 정보에 대한 File 객체생성
		System.out.println("생성직후 f1=>"+f1);
		if (!f1.exists()) {
			f1.mkdir();
			//realPath 디렉터리가 존재하는지 검사 (uploadImage 폴더 존재 확인)
			//=> 존재하지 않으면 디렉토리 생성
		}
		// ** MultipartFile
		// => 업로드한 파일에 대한 모든 정보를 가지고 있으며 이의 처리를 위한 메서드를 제공한다.
		// String getOriginalFilename(), void transferTo(File destFile),
		// boolean isEmpty()

		MultipartFile uploadfilef = null; //joinForm 과 관련.
		//=> 기본 Image 설정
		String file1 , file2 = "resources/uploadImage/basicman2.jpg";
		// 전송된 Image 가 있는지 확인
		uploadfilef = vo.getUploadfilef() ;
		System.out.println("vo.getUploadfilef() ; => "+vo.getUploadfilef());

		if (uploadfilef != null && !uploadfilef.isEmpty()) {
			file1 = realPath + uploadfilef.getOriginalFilename(); 
			//드라이브에 저장되는 실제 경로와 파일명
			uploadfilef.transferTo(new File(file1));    //file 붙여넣기 throw exception 해줫음
			file2 = "resources/uploadImage/" + uploadfilef.getOriginalFilename();
		}
		vo.setUploadfile(file2);

		if (service.insert(vo) > 0) {
			// 가입성공 -> 로그인 유도 메시지 출력 : loginForm.jsp
			mv.addObject("message", " 회원 가입 성공 !!! 로그인 후 이용하세요 ~~");
			mv.setViewName("login/loginForm");
		} else {
			// 가입실패 -> 재가입 유도 메시지 출력 : joinForm.jsp
			mv.addObject("message", " 회원 가입 실패 !!! 다시 하세요 ~~");
			mv.setViewName("member/joinForm");
		}
		return mv;
	} //mjoin

	// *** Update & Delete	
	@RequestMapping(value = "/mupdate")
	public ModelAndView mupdate(HttpServletRequest request, ModelAndView mv, MemberVO vo)
			throws UnsupportedEncodingException , IOException {
		
		// ** ImageUpload
		// ver01 (배포전, 이클립스환경)
		String realPath = "C:\\IDset\\eclipse\\Spring02\\src\\main\\webapp\\resources\\uploadImage/";
		
		MultipartFile uploadfilef = null ;
		// => 기본 Image 설정
		String file1, file2 ;
		// 전송된 Image 가 있는지 확인
		uploadfilef = vo.getUploadfilef();
		if ( uploadfilef != null && !uploadfilef.isEmpty()) {  // newImage 선택
			file1 = realPath + uploadfilef.getOriginalFilename();  
			// 드라이브에 저장되는 실제 경로와 화일명
			uploadfilef.transferTo(new File(file1)); // file 붙여넣기
			file2 = "resources/uploadImage/" + uploadfilef.getOriginalFilename();
			vo.setUploadfile(file2);
		}
//**********************************************************
		String message = null;
		if (service.update(vo) > 0) {
			// 수정성공 -> message, List출력 (memberList.jsp) 
			// url 로 전달되는 한글 message 처리 위한 encoding
			message = URLEncoder.encode("~~ 정보수정 성공 ~~", "UTF-8");
			mv.setViewName("redirect:mlist?message="+message); // sendRedirect
		} else {
			// 수정실패 -> message, Ddetail (mdetail)
			message = URLEncoder.encode("~~ 정보수정 실패 !!! 다시 하세요 ~~", "UTF-8");
			mv.setViewName("redirect:mdetail?id="+vo.getId()+"&message="+message+"&jcode=U"); // sendRedirect
		}
		return mv;
	} //mupdate	

	@RequestMapping(value = "/mdelete")
	public ModelAndView mdelete(HttpServletRequest request, ModelAndView mv, MemberVO vo)
			throws UnsupportedEncodingException {

		// session 정보에서 id getAttribute
		HttpSession session = request.getSession(false);
		String message = null;
		if (session !=null && session.getAttribute("loginID") !=null) {
			// 삭제준비
			vo.setId((String)session.getAttribute("loginID"));

			// 삭제오류 Test -> Detail
			//vo.setId("testtest");
			// 삭제처리
			if (service.delete(vo) > 0) { // 삭제성공 -> session삭제, List
				session.invalidate();   // session삭제
				message = URLEncoder.encode("~~ 삭제성공 , 1 개월후 재가입 할수있습니다 ~~", "UTF-8");
				mv.setViewName("redirect:mlist?message="+message); // sendRedirect
			}else { // 삭제오류 -> Detail
				message = URLEncoder.encode("~~ 삭제 실패 ~~", "UTF-8");
				mv.setViewName("redirect:mdetail?id="+vo.getId()+"&message="+message);
			}
		}else {
			message = URLEncoder.encode("~~ 삭제할 정보가 없습니다 (sessionIsNull) ~~", "UTF-8");
			mv.setViewName("redirect:home?message="+message); 
		}
		return mv;
	} //mdelete	

} //MemberController
