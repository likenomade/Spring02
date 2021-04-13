package util;
//import java.sql.*
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import vo.MemberVO;

@Repository
public class MemberDAO {
		static Connection cn= DBConnection.getConnection();
		Statement st;
		PreparedStatement pst;
		ResultSet rs;
		String sql;

		//SelectList
		public List<MemberVO> selectList() {
			List<MemberVO> list = new ArrayList<MemberVO>();
		    sql= "select * from member order by id" ;
			try {
				st = cn.createStatement();
				rs=st.executeQuery(sql);
				if (rs.next()) {
					do {
						MemberVO vo = new MemberVO() ;
						vo.setId(rs.getString(1));
						vo.setPassword(rs.getString(2));
						vo.setName(rs.getString(3));
						vo.setLev(rs.getString(4));
						vo.setBirthd(rs.getString(5));
						vo.setPoint(rs.getInt(6));
						vo.setWeight(rs.getDouble(7));
						vo.setRid(rs.getString(8));
						vo.setUploadfile(rs.getString(9));
						list.add(vo);
					} while(rs.next());
				}else {
					System.out.println("** 출력할 Data가 없습니다 ~~");
					list = null;
				}
			} catch (Exception e) {
				System.out.println("selectList Exception"+e.toString());
			}
			return list;

		} // selectList

		// ** selectOne
		public MemberVO selectOne(MemberVO vo) {
			sql = "select * from member where id=?";
			try {
				pst = cn.prepareStatement(sql);
				pst.setString(1, vo.getId());
				rs=pst.executeQuery(); //조회

				if (rs.next()) { // 자료 존재
					vo.setPassword(rs.getString(2));
					vo.setName(rs.getString(3));
					vo.setLev(rs.getString(4));
					vo.setBirthd(rs.getString(5));
					vo.setPoint(rs.getInt(6));
					vo.setWeight(rs.getDouble(7));
					vo.setRid(rs.getString(8));
					vo.setUploadfile(rs.getString(9));
				}else {			// NotFound
					vo=null;
					System.out.println("**Member NotFound~**");
				}
			} catch (Exception e) {
				System.out.println("selectOne Exception"+e.toString());
				vo=null;
			}
			return vo;
		}
		// selectOne

		// ** Join -> Insert
		public int insert(MemberVO vo){       //몇명 입력햇는지 확인해야되서 int 타입으로 해야함.
			//sql = "insert into member values((select nvl(max(id),0)+1 from member),?,?,?,?,?,?,?)";
			sql = "insert into member values (?,?,?,?,?,?,?,?,?)";
			try {
				pst = cn.prepareStatement(sql);     

				pst.setString(1,vo.getId());
				pst.setString(2,vo.getPassword());
				pst.setString(3,vo.getName());
				pst.setString(4,vo.getLev());
				pst.setString(5,vo.getBirthd());
				pst.setInt(6,vo.getPoint());
				pst.setDouble(7,vo.getWeight());
				pst.setString(8,vo.getRid());
				pst.setString(9, vo.getUploadfile());
				return pst.executeUpdate(); 

			} catch (Exception e) {
			System.out.println("selectList Exception"+e.toString());
				//e.printStackTrace();
				return 0;
			}//catch
		}

		public int update(MemberVO vo){	
			sql = "update member set "
					+ "password=?, name=?,lev=?," 
					+ "birthd=?, point=?,weight=?,rid=?,uploadfile =? where id=?";
			
			try {
				pst=cn.prepareStatement(sql);
				
				pst.setString(1,vo.getPassword());
				pst.setString(2,vo.getName());
				pst.setString(3,vo.getLev());
				pst.setString(4,vo.getBirthd());
				pst.setInt(5,vo.getPoint());
				pst.setDouble(6,vo.getWeight());
				pst.setString(7,vo.getRid());
				pst.setString(8,vo.getUploadfile());
				pst.setString(9,vo.getId());
				
				return pst.executeUpdate() ;  // 처리완료 된 row 의 갯수를 return
			} catch (Exception e) {
				System.out.println("selectList Exception"+e.toString());
				return 0;
			}
			
		} // update

		public int delete(MemberVO vo) {
			sql = "delete from member where id=?";
			try {
				pst=cn.prepareStatement(sql);
				
				pst.setString(1, vo.getId());
				
				return pst.executeUpdate();
			} catch (Exception e) {
				System.out.println("selectList Exception"+e.toString());
				return 0;
			}
		} // delete

} // class


