-- 실행 할 쿼리 선택 후
-- alt + x  || alt + c
-- member 정보를 저장하는 테이블
CREATE TABLE tbl_member(
	mNum INT PRIMARY KEY auto_increment,
	mName VARCHAR(50),
	mId VARCHAR(50) NOT NULL UNIQUE,
	mPw VARCHAR(50) NOT NULL,
	reg BIGINT DEFAULT 0
);

DESC tbl_member;

INSERT INTO tbl_member(mName,mId,mPw)
VALUES('관리자','root','12345');

SELECT * FROM tbl_member;

commit;

-- 탈퇴한 회원의 정보를 임시 저장할 back_up_table 생성
-- tbl_member table의 구조만 복사해서 새로운 테이블 생성
CREATE TABLE back_up_member LIKE tbl_member;

DESC back_up_member;



-- 회원탈퇴 정보가 back_up된 시간 column 추가
ALTER TABLE back_up_member ADD COLUMN deleteDate TIMESTAMP DEFAULT now();

SELECT * FROM back_up_member;












