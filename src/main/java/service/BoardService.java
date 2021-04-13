package service;

import java.util.List;

import vo.BoardVO;


public interface BoardService {
	
	//** Ajax id BoardList
	public List<BoardVO> idbList(BoardVO bvo);
	
	//**댓글 등록
	public int rinsert(BoardVO vo);
	
	public List< BoardVO> selectList();
	public  BoardVO selectOne( BoardVO vo);
	public int insert( BoardVO vo);
	public int update( BoardVO vo);
	public int delete( BoardVO vo);
	public int countUp(BoardVO vo);

}
