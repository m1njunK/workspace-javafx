package a_base;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Scanner;

public class StatementExample {

	public static void main(String[] args) {
		// Statement - Connection 객체의 연결 정보를 이용하여 질의를 실행 시키고
		// 			   결과를 반환하는 class
		
		// DBMS 와 연결된 session 정보를 저장하고 있는 객체
		Connection conn = null;
		// session 안에서 질의 전송을 도와주는 객체
		Statement stmt = null;
		// 검색 질의의 결과 정보를 저장하는 객체
		ResultSet rs = null;
				
		try {
			// Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/sqldb",
					"root",
					"12345"
			);
			
			System.out.println(conn.getAutoCommit());
			System.out.println(conn);
			
			// auto commit 설정 변경
			conn.setAutoCommit(false);
		
			// 연결된 database ex) sqldb에 질의를 수행할 Statement 객체 반환
			stmt = conn.createStatement();
			String sql = "SELECT * FROM usertbl";
			
			// 검색 질의에 대한 결과를 저장하는 class
			// 검색 질의 실행 후 반환
			rs = stmt.executeQuery(sql);
			// rx.next()
			// 읽어올 행 정보가 있으면 해당 위치로 이동한후 true 반환
			// 읽어올 행정보가 없으면 false 반환
			while(rs.next()) {
				//	1		2		3		4		5		6		7		8
				// userID, name, birthyear, addr, mobile1, mobile2, height, mdate
				String userId = rs.getString(1);
				String name = rs.getString(2);
				int birthyear = rs.getInt(3);
				String addr = rs.getString("addr");
				Date mdate = rs.getDate(8);
				String result = userId+"-"+name+"-"+birthyear+"-"+addr+"-"+mdate;
				System.out.println(result);
				System.out.println("===============================================");
			}
			
			Scanner sc = new Scanner(System.in);
			System.out.println("userID를 입력해 주세요.");
			String id = sc.next();
			System.out.println("이름을 입력해 주세요 > ");
			String name = sc.next();
			System.out.println("생년월일을 입력해 주세요 ex) 1982 > ");
			int birthYear = sc.nextInt();
			System.out.println("주소를 입력해 주세요 도시이름 2자 > ");
			String addr = sc.next();
			
			rs.close();
			stmt.close();
			
			stmt = conn.createStatement();
			sql = "INSERT INTO userTbl(userID,name,birthYear,addr) "
					+ "VALUES('"+id+"','"+name+"',"+birthYear+",'"+addr+"')";
			System.out.println(sql);
			int result = stmt.executeUpdate(sql);
			System.out.println(result+"개의 행 삽입 완료");
			// 삽입 이전 시점으로 rollback
			conn.commit();
			
		//} catch (ClassNotFoundException e) {
		//	e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {}
		}finally {
			try {
				if(rs != null) rs.close();
				if(stmt != null) stmt.close();
				if(conn != null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}
	
}
