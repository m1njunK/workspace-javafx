package a_base;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionExample {

	int a = 100; // field선언
	
	ConnectionExample(){
		System.out.println("3. ConnectionExample 생성자 호출 "+a);
	}
	
	static{
		System.out.println("1. 설계 정보 등록 시 실행");
	}
	
	{
		// 접근할 수 있는 식별자가 존재 하지 않기 때문에
		// 인스턴스가 생성이 되면 즉시 실행
		System.out.println("2. instance 생성 시 실행되는 블럭");
	}
	
	public static void main(String[] args) {

		new ConnectionExample();
		
		String driver = "com.mysql.cj.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/sqldb";
		String user = "root";
		String pass = "12345";
		
		
		try {
			Class.forName(driver);
			System.out.println("Driver class가 존재함");
			
			try {
				// java.sql.
				// 1번 방법
				Connection conn = DriverManager.getConnection(
						"jdbc:mysql://localhost:3306/sqlDB?user=root&password=12345"
						);
				System.out.println(conn);
				conn.close();
				
				// 2번 방법
				conn = DriverManager.getConnection(url,user,pass);
				System.out.println(conn);
				conn.close();
				
				// 3번 방법
				Properties prop = new Properties();
				prop.setProperty("user", "root");
				prop.setProperty("password", pass);
				conn = DriverManager.getConnection(url,prop);
				System.out.println(conn);
				conn.close();
				
				// 4번방법 - 외부 properties 파일 이용
				try {
					File file = new File("prop/mysql.properties");
					FileReader reader = new FileReader(file);
					prop = new Properties();
					prop.load(reader);
					System.out.println(prop);
					conn = DriverManager.getConnection(url,prop);
					System.out.println(conn);
					conn.close();
				} catch (FileNotFoundException e) {
					System.out.println("경로에 파일 찾지 몬함 " + e.getMessage());
				} catch (IOException e) {
					System.out.println("properties 파일의 형식오류 : " + e.getMessage());
				}
				
			} catch (SQLException e) {
				System.out.println("DB 연결 정보 오류 : " + e.getMessage());
			}
			
		} catch (ClassNotFoundException e) {
			System.out.println("jdbc Mysql Driver가 존재하지 않음 "+e.getMessage());
		}
	}

}
