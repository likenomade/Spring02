package util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
	public static Connection getConnection() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver"); // 1) DB 드라이버 이름 지정
			String url="jdbc:oracle:thin:@127.0.0.1:1521:orcl";// 2) DB 드라이버 위치 지정
			return DriverManager.getConnection(url, "student", "test"); // 3) 로그인 ( Connection 생성 )
		} catch (Exception e) {
			System.out.println("** DB 연결 실패 => "+e.toString());
			return null;
		}
	} // getConnection
}
//DB 드라이버 이름 지정
//DB 드라이버 위치 지정
//Connection 생성
