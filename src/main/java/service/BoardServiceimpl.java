package service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import util.BoardDAO;
import vo.BoardVO;

@Service
public class BoardServiceimpl implements BoardService{
	
	@Autowired
	BoardDAO dao;
	
	//** Ajax id BoardList
	public List<BoardVO> idbList(BoardVO bvo){
		return dao.idbList(bvo);
	}
	
	//**댓글 등록
	public int rinsert(BoardVO vo) {
		return dao.rinsert(vo);
	}//rinsert
	
	public List<BoardVO> selectList(){
		return dao.selectList();
	}//selectList
	public BoardVO selectOne(BoardVO vo){
		return dao.selectOne(vo);
	}
	public int insert(BoardVO vo){	
		return dao.insert(vo);
	}
	public int update(BoardVO vo){	
		return dao.update(vo);
	}
	public int delete(BoardVO vo){	
		return dao.delete(vo);
	}
	public int countUp(BoardVO vo){
		return dao.countUp(vo);
	}
	
}
