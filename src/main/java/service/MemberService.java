package service;

import java.util.List;

import vo.MemberVO;

public interface MemberService {
	
	public List<MemberVO> selectList();
	public MemberVO selectOne(MemberVO vo);
	public int insert(MemberVO vo);
	public int update(MemberVO vo);
	public int delete(MemberVO vo);
	
} //interface
