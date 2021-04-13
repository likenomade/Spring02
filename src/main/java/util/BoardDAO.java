package util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import vo.BoardVO;

// ** Board CRUD 구현
@Repository
public class BoardDAO {
	private Connection cn = DBConnection.getConnection(); 
	private Statement st;
	private PreparedStatement pst;
	private ResultSet rs;
	private String sql;

	//** Ajax id BoardList
	public List<BoardVO> idbList(BoardVO bvo) {
		//sql="select * from board order by seq desc";
		sql="select * from board where id=? order by root  desc, step asc ";
		List<BoardVO> list = new ArrayList<BoardVO>();
		try {
			pst=cn.prepareStatement(sql);
			pst.setString(1, bvo.getId());
			rs=pst.executeQuery();

			if (rs.next()) {
				do {
					BoardVO vo = new BoardVO();
					vo.setSeq(rs.getInt(1));
					vo.setTitle(rs.getString(2));
					vo.setId(rs.getString(3));
					vo.setContent(rs.getString(4));
					vo.setRegdate(rs.getString(5));
					vo.setCnt(rs.getInt(6));
					vo.setRoot(rs.getInt(7));
					vo.setStep(rs.getInt(8));
					vo.setIndent(rs.getInt(9));
					list.add(vo);
				}while(rs.next());
			}else {
				System.out.println("** selectList : 출력자료가 1건도 없습니다 **");
				list=null;
			}
		} catch (Exception e) {
			System.out.println("** selectList Exception => "+e.toString());
			list=null;
		}
		return list;
	} //idbList


	//** reply insert
	//=> 답글 등록과 step 증가	
	public int stepUpdate(BoardVO vo) {
		// 조건 => root 동일하고 step 이 vo 의 step 과  같거나 큰경우
		sql="UPDATE board set step=step+1  WHERE root=? and step >= ?";
		try {
			pst=cn.prepareStatement(sql);
			pst.setInt(1,vo.getRoot());
			pst.setInt(2,vo.getStep());
			return pst.executeUpdate();
		} catch (Exception e) {
			System.out.println("** update Exception => "+e.toString());
			return 0;
		}
	}// stepUpdate

	public int rinsert(BoardVO vo) {
		System.out.println("** stepUpdate 결과 =>" + stepUpdate(vo));
		sql="insert into board values("
				+ "(select nvl(max(seq),0)+1 from board)," // 글번호 자동증가
				+ "?,?,?,sysdate,0,?,?,? )";
		//원글의 root (seq) stop(0) indent(0)  -> vo 에서 get
		try {
			pst=cn.prepareStatement(sql);
			pst.setString(1,vo.getTitle());
			pst.setString(2,vo.getId());
			pst.setString(3,vo.getContent());
			pst.setInt(4, vo.getRoot());
			pst.setInt(5, vo.getStep());
			pst.setInt(6, vo.getIndent());
			return pst.executeUpdate();

		} catch (Exception e) {
			System.out.println("** insert Exception => "+e.toString());
			return 0;
		}
	}//rinsert


	// ** selectList
	public List<BoardVO> selectList() {
		//sql="select * from board order by seq desc";
		sql="select * from board order by root  desc, step asc";
		List<BoardVO> list = new ArrayList<BoardVO>();
		try {
			st = cn.createStatement();
			rs=st.executeQuery(sql);
			if (rs.next()) {
				do {
					BoardVO vo = new BoardVO();
					vo.setSeq(rs.getInt(1));
					vo.setTitle(rs.getString(2));
					vo.setId(rs.getString(3));
					vo.setContent(rs.getString(4));
					vo.setRegdate(rs.getString(5));
					vo.setCnt(rs.getInt(6));
					vo.setRoot(rs.getInt(7));
					vo.setStep(rs.getInt(8));
					vo.setIndent(rs.getInt(9));
					list.add(vo);
				}while(rs.next());
			}else {
				System.out.println("** selectList : 출력자료가 1건도 없습니다 **");
				list=null;
			}
		} catch (Exception e) {
			System.out.println("** selectList Exception => "+e.toString());
			list=null;
		}
		return list;
	} //selectList

	// ** selectOne
	public BoardVO selectOne(BoardVO vo) {
		sql="select * from board where seq=?";
		try {
			pst=cn.prepareStatement(sql);
			pst.setInt(1, vo.getSeq());
			rs=pst.executeQuery();
			if (rs.next()) {
				vo.setSeq(rs.getInt(1));
				vo.setTitle(rs.getString(2));
				vo.setId(rs.getString(3));
				vo.setContent(rs.getString(4));
				vo.setRegdate(rs.getString(5));
				vo.setCnt(rs.getInt(6));
				vo.setRoot(rs.getInt(7));
				vo.setStep(rs.getInt(8));
				vo.setIndent(rs.getInt(9));
				return vo;
			}else {
				System.out.println("** selctOne NotFound **");
				return null;
			}
		} catch (Exception e) {
			System.out.println("** selctOne Exception => "+e.toString());
			return null;
		} //try
	} //selctOne

	// ** insert (원글)
	// => 답글 추가
	public int insert(BoardVO vo) {
		sql="insert into board values("
				+ "(select nvl(max(seq),0)+1 from board)," // 글번호 자동증가
				+ "?,?,?,sysdate,0, "
				+ "(select nvl(max(seq),0)+1 from board),0,0)"; //원글의 root (seq) stop(0) indent(0)
		try {
			pst=cn.prepareStatement(sql);
			pst.setString(1,vo.getTitle());
			pst.setString(2,vo.getId());
			pst.setString(3,vo.getContent());
			return pst.executeUpdate();
		} catch (Exception e) {
			System.out.println("** insert Exception => "+e.toString());
			return 0;
		}
	} //insert

	// ** update
	public int update(BoardVO vo) {
		sql="UPDATE board set title=?, content=? WHERE seq=?";
		try {
			pst=cn.prepareStatement(sql);
			pst.setString(1,vo.getTitle());
			pst.setString(2,vo.getContent());
			pst.setInt(3,vo.getSeq());
			return pst.executeUpdate();
		} catch (Exception e) {
			System.out.println("** update Exception => "+e.toString());
			return 0;
		}
	} //update

	// ** delete	
	public int delete(BoardVO vo) {
		//답글 등록후
		//=> 원글 삭제 : 모든 후손들 같이 삭제 
		// => 답글 삭제 : 현재글만 삭제
		// => 원글 or 답글 구분 : seq == root  원글
		if (vo.getSeq() == vo.getRoot()) { //원글
			//	sql="DELETE from board where root=vo.getRoot() or vo.getSeq()";
			sql="DELETE from board where root=?";
		}else { //답글
			//	sql="DELETE from board where seq= vo.getSeq()";
			sql="DELETE from board where seq=?";
		}
		try {
			pst=cn.prepareStatement(sql);
			pst.setInt(1,vo.getSeq());
			return pst.executeUpdate();
		} catch (Exception e) {
			System.out.println("** delete Exception => "+e.toString());
			return 0;
		}
	} //delete


	//**countUp

	public int countUp(BoardVO vo) {
		sql="UPDATE board set cnt=? where seq=?";
		//sql="UPDATE board set cnt=cnt+1 where seq=?"; 이런식도 가능
		try {
			pst=cn.prepareStatement(sql);
			pst.setInt(1,vo.getCnt());
			pst.setInt(2,vo.getSeq());
			return pst.executeUpdate();
		} catch (Exception e) {
			System.out.println("** countUp Exception => "+e.toString());
			return 0;
		}
	} //countUp


} //class
