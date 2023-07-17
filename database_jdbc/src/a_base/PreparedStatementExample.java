package a_base;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Scanner;

public class PreparedStatementExample {

	public static void main(String[] args) {
		// Prepared - 준비가 된
		Connection conn = null;
		// 동적 쿼리 객체
		// 쿼리를 먼저 등록 시켜놓고 질의 실행에 필요한 데이터(값)을 나중에 대입
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/sqldb",
					"root",
					"12345"
			);
			System.out.println("DB 연결 완료");

			// 등록할 질의
			String sql = "SELECT * FROM usertbl WHERE userID = ?";
			//  입력 받은 ID의 회원 정보를 검색
			Scanner sc = new Scanner(System.in);
			System.out.println("검색할 회원의 아이디를 작성해 주세요 > ");
			String id = sc.next();
			
			pstmt = conn.prepareStatement(sql);
			// ? wildcard 자리에 데이터 삽입 - ? 배치에 따라 왼쪽에서 부터 1, 1씩 증가
			pstmt.setString(1, id);
			// "SELECT * FROM usertbl WHERE userID = 'id'"
			rs = pstmt.executeQuery();
			// WHERE 조건절에 검색에 사용한 속성이 PRIMARY KEY
			// userID 가 일치하는 행의 정보를 반환하거나 존재하지않으면 없거나 둘중 하나
			if(rs.next()) {
				//	1		2		3		4		5		6		7		8
				// userID, name, birthyear, addr, mobile1, mobile2, height, mdate
				String userId = rs.getString(1);
				String name = rs.getString(2);
				int birthyear = rs.getInt(3);
				String addr = rs.getString("addr");
				java.util.Date mdate = rs.getDate(8);
				String result = userId+"-"+name+"-"+birthyear+"-"+addr+"-"+mdate;
				System.out.println(result);
				System.out.println("===============================================");
			}else {
				System.out.println(id + " 회원은 존재하지 않습니다.");
			}
			
			// moblie1, mobile2, height, mdate
			sql = "UPDATE usertbl SET mobile1 = ? , mobile2 = ? , height = ? , mdate = ? "
					+ " WHERE userID = ?";
			rs.close();
			pstmt.close();
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "010");
			pstmt.setString(2, "94867166");
			pstmt.setInt(3, 180);
			pstmt.setDate(4, new java.sql.Date(System.currentTimeMillis()));
			pstmt.setString(5, id);
			
			int result = pstmt.executeUpdate();
			System.out.println(result + "개의 행 수정 완료.");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			
			try {
				if(rs != null) {
					rs.close();
				}
				
				if(pstmt != null) {
					pstmt.close();
				}
				
				if(conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		
	}

}
