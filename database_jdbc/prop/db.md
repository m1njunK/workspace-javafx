=============================================
## project에 MySQL JDBC 등록하기 

mvnrepository.com 접속
검색창에 mysql-connector 검색

MySQL Connector/J 선택

내컴퓨터에 설치된 version 선택

Files 탭의 jar파일 다운로드

=============================================

## MySQL 설치 시 connector/J 설치 완료 시

C:\Program Files (x86)\MySQL\Connector J 8.0
경로로 이동하여 
mysql-connector-java-version.jar 확인


project 우클릭 -> build path ->
Add External Archives 선택

다운로드 받은 jar 파일 선택 후 열기
(mysql-connector-java-version.jar)

-> Referenced Libraries 에 등록된 jar 확인 후 완료

----------------------------------------------

project에 library 파일이 있는 경우

file 우클릭 -> build path -> add to Build Path

Referenced Libraries 에 등록된 jar 확인 후 완료

=============================================

-- eclipse 에 data source explorer 설치하기

Help -> install new Software... 선택

Work With 작성란에 해당 내용 기입
https://download.eclipse.org/releases/2023-03/
후 Enter

검색된 내용 중

Database Development Check 후 next
 
I accept 동의 후 finish

설치 완료 되면 eclipse restart

재시작 후 

Window -> Show View -> Other 선택

Data Management -> Data Source Explorer 선택

Data Source Explorer탭의 
DataBase Connections 우클릭 New... 선택

JDBC 추가 
다운로드 받은 mysql-connector jar 파일 등록

접근 하려는 DBMS url, username, password 정보를 등록 하여

connection 정보 추가

SQL 파일 생성 후 상단의 Type , Name , DataBase 선택
연결된 DBMS의 database 와 계정 정보를 통해
Query(질의) 작성 후 실행

================================================

## JDBC(Java DataBase Connectivity)

JAVA 프로그램 내에서 데이터베이스와 관련된 작업을 처리할 수 있도록 도와주는 자바 표준 인터페이스
관계형 데이터베이스 시스템에 접근하여 SQL 문을 실행하기 위한 자바 API 또는 자바 라이브러리
JDBC API를 사용하면 DBMS의 종류에 상관없이 데이터베이스 작업을 처리할 수 있음

================================================

## JDBC를 사용한 JAVA와 데이터베이스의 연동

1 java.sql.* 패키지 임포트,

2 JDBC 드라이버 로딩, 

3 데이터베이스 접속을 위한 Connection 객체 생성
  - Connection : Database 와 계정정보를 통해 연결된 객체, Schema, user, password 정보를 저장 

4 쿼리문을 실행하기 위한 
	Statement/PreparedStatement/CallableStatement 객체 생성 
 	Statement : 구문 - 질의를 실행 시키고 결과를 반환환하는 객체

5 쿼리 실행, 
  쿼리 실행의 결과 값(int 또는 ResultSet) 사용 
  ResultSet : 검색 쿼리의 결과를 저장하는 객체 
  int : UPDATE, DELETE, INSERT 작업이 수행된 행의 개수

6 사용된 객체(ResultSet, Statement/PreparedStatement/CallableStatement, Connection)
  사용 완료 시 자원해제 (close)

===================================================
## JDBC 드라이버 로딩하기

JDBC 드라이버 로딩 단계에서는 드라이버 인터페이스를 구현하는 작업
Class.forName(String className) 메소드를 이용하여 JDBC 드라이버를 로딩
-- 등록된 라이브러리 확인

-- JDBC 드라이버가 로딩되면 자동으로 객체가 생성되고 DriverManager 클래스에 등록

 JDBC 드라이버 로딩은 프로그램 수행 시 한 번만 필요

# 2 Connection 객체 생성하기
JDBC 드라이버에서 데이터베이스와 연결된 커넥션을 가져오기 위해 DriverManager 클래스의 getConnection( ) 메소드를 사용
DriverManager 클래스로 Connection 객체를 생성할 때 JDBC 드라이버를 검색하고, 검색된 드라이버를 이용하여 Connection 객체를 생성한 후 이를 반환

java.sql.Connection conn = java.sql.DriverManager.getConnection(url, username, password);

#3 데이터베이스 연결 닫기
데이터베이스 연결이 더 이상 필요하지 않으면 데이터베이스와 JDBC 리소스가 자동으로 닫힐 때까지 대기하는 것이 아니라 close( ) 메소드로 생성한 Connection 객체를 해제
일반적으로 데이터베이스 리소스를 사용하지 않기 위해 사용을 끝내자마자 리소스를 해제하는 것이 좋음

부가 설명

-- 우리가 작업하는 프로그래밍의 변수나 instance들은 메모리에 있는
value와 address를 제어하는 것.

File은 HDD즉 메모리 외부에 존재하는 자원이고 이 자원을 쓰려면 외부자원을  
open 해서 메모리에 가지고 와야하며 다 사용하고 나면 연결을 해제(close) 해줘야 함.

database management system에 연결된 connection도 외부자원이므로 사용하고 나면 close 해줘야함.

# 데이터베이스 SQL문 실행 
## 1 Statement 객체로 데이터 접근하기

Statement 객체
정적인 쿼리에 사용
하나의 쿼리를 사용하고 나면 더는 사용할 수 없음
하나의 쿼리를 끝내면 close( )를 사용하여 객체를 즉시 해제해야 함
close()를 사용하여 객체를 즉시 해제하지 않으면 무시할 수 없는 공간이 필요하며 다른 작업을 수행하는 동안 멈추지 않기 때문. 
복잡하지 않은 간단한 쿼리문을 사용하는 경우에 좋음

public ResultSet executeQuery(String sql);
검색질의 SELECT 문을 사용할때 사용(ResultSet 객체반환)

public int executeUpdate(String sql);
DML(INSERT , UPDATE, DELETE SQL문을 사용할때 사용)
테이블에 변경된 행의 개수를 반환

public void close();
Statement 객체를 반환(자원해제)할때 사용


## 2 PreparedStatement 객체로 데이터 접근하기
PreparedStatement 객체
동적인 쿼리에 사용
Prepared Statement 객체는 하나의 객체로 여러 번의 쿼리를 실행할 수 있으며, 동일한 쿼리문을 특정 값만 바꾸어서 여러 번 실행해야 할 때, 매개변수가 많아서 쿼리문을 정리해야 할 때 유용

public ResultSet executeQuery();
검색질의 SELECT 문을 사용할때 사용(ResultSet 객체반환)

public int executeUpdate();
DML(INSERT , UPDATE, DELETE SQL문을 사용할때 사용)
테이블에 변경된 행의 개수를 반환

public void close();
Statement 객체를 반환(자원해제)할때 사용



## 3 ResultSet - 검색 결과를 저장하는 객체
ResultSet 객체
SELECT 검색 질의로 실행된 행의 정보를 행단위로 읽어오는 객체

public boolean next();
검색 질의 실행 후 결과가 존재하는지 확인하는 method
true : 존재함, false : 더이상 읽을 행정보가 없음.
읽을 행정보가 존재하면 해당 행으로 이동하여 속성(column)별로 값을 읽을 수 있도록 함.

public type getType(column number or column name);
읽어올 데이터 타입별 method가 존재 
column과 일치하는 java의 데이터 타입을 지정하면
지정된 타입으로 database에서 읽어들인 행의 속성값을 반환























