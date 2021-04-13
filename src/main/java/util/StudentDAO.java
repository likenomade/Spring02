package util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import vo.StudentVO;

// DAO : Data Access Object
// => Student Table 의 CRUD 구현

public class StudentDAO {
	static Connection cn= DBConnection.getConnection();
	Statement st;
	PreparedStatement pst;
	ResultSet rs;
	String sql;

	// ** selectList
	// => ArrayList : 순차처리만 하면되므로...
	public List<StudentVO> selectList() {
		List<StudentVO> list = new ArrayList<StudentVO>();
		sql= "select * from student order by snum" ;
		// 출력자료가 있는지 확인 
		try {
			st = cn.createStatement();
			rs=st.executeQuery(sql);
			
			if (rs.next()) {
				do {
					StudentVO vo = new StudentVO() ;

					vo.setSnum(rs.getInt(1));
					vo.setSname(rs.getString("sname"));
					vo.setSex(rs.getString(3));
					vo.setAge(rs.getInt(4));
					vo.setBirthday(rs.getString(5));
					vo.setEmail(rs.getString(6));
					vo.setNote(rs.getString(7));
					vo.setGnum(rs.getInt(8));
					vo.setRegdate(rs.getString(9));
					list.add(vo);
				} while(rs.next());
			}else {
				System.out.println("** 출력할 자료가 1건도 없습니다 ~~");
				list = null;
			}
		} catch (Exception e) {
			System.out.println("selectList Exception"+e.toString());
		}
		return list;

	} // selectList

	// ** detail
	public StudentVO detail(StudentVO vo) {
		sql = "select * from student where snum=?";

		try {
			pst = cn.prepareStatement(sql);
			pst.setInt(1, vo.getSnum());
			rs=pst.executeQuery();

			if (rs.next()) { // 자료 존재
				vo.setSnum(rs.getInt(1));
				vo.setSname(rs.getString("sname"));
				vo.setSex(rs.getString(3));
				vo.setAge(rs.getInt(4));
				vo.setBirthday(rs.getString(5));
				vo.setEmail(rs.getString(6));
				vo.setNote(rs.getString(7));
				vo.setGnum(rs.getInt(8));
				vo.setRegdate(rs.getString(9));
			}else {			// NotFound
				//vo.setEmail("notfound");
				vo=null;
				System.out.println("**Student NotFound~**");
			}
		} catch (Exception e) {
			System.out.println("detail Exception"+e.toString());
		}
		return vo;
	}
	// detail

	// ** insert => Auto Numbering : nvl 적용 
	public int insert(StudentVO vo){            //snum 오토 넘버링ㄱ
		sql = "insert into student values((select nvl(max(snum),0)+1 from student),?,?,?,?,?,?,?,sysdate)";
		try {
			pst = cn.prepareStatement(sql);      //nvl함수:nvl(num,0)=>num이 null이면 0을 반환.
			pst.setString(1,vo.getSname());
			pst.setString(2,vo.getSex());
			pst.setInt(3,vo.getAge());
			pst.setString(4,vo.getBirthday());
			pst.setString(5,vo.getEmail());
			pst.setString(6,vo.getNote());
			pst.setInt(7,vo.getGnum());
			return pst.executeUpdate(); 

		} catch (Exception e) {
			System.out.println("selectList Exception"+e.toString());
			return 0;
		}//catch
	}

	public int update(StudentVO vo){	
		sql = "update student set "
				+ "sname=?,sex=?, age=?,birthday=?," 
				+ "email=?, note=?,gnum=? where snum=?";
		
		try {
			pst=cn.prepareStatement(sql);
			pst.setString(1, vo.getSname());
			pst.setString(2, vo.getSex());
			pst.setInt(3, vo.getAge());
			pst.setString(4, vo.getBirthday());
			pst.setString(5, vo.getEmail());
			pst.setString(6, vo.getNote());
			pst.setInt(7, vo.getGnum());
			pst.setInt(8, vo.getSnum()); //기능적으로 봤을때 primary key 는 수정하지 x
			return pst.executeUpdate() ;  // 처리완료 된 row 의 갯수를 return
		} catch (Exception e) {
			System.out.println("selectList Exception"+e.toString());
			return 0;
		}
		
	} // update

	public int delete(StudentVO vo) {
		sql = "delete from student where snum=?";
		try {
			pst=cn.prepareStatement(sql);
			pst.setInt(1, vo.getSnum());
			return pst.executeUpdate();
		} catch (Exception e) {
			System.out.println("selectList Exception"+e.toString());
			return 0;
		}
	} // delete

	//Transaction Test 를 위한 insert 구문
	public int tranTest() throws Exception{
		sql = "insert into student values"
				+"(77,'홍길동','남',22,09-09,'sqltest@green.com','',5,sysdate)";

		pst = cn.prepareStatement(sql);
		return pst.executeUpdate();
	}
} // class
