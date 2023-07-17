package guide.member.dao;

import java.util.ArrayList;

import guide.member.vo.BackUpMemberVO;
import guide.member.vo.MemberVO;

public interface MemberDAO {
	
	// 회원가입
	// 회원정보를 저장하고 있는 member를 넘겨받아서
	// 회원정보를 테이블에 추가하고 등록된 회원 정보를 반환
	MemberVO join(MemberVO member);
	
	// 회원 검색
	// mId , mPw가 일치하는 사용자 검색
	MemberVO selectMember(String mId, String mPw);
	
	// mNum 회원번호로 회원 검색
	MemberVO selectMember(int mNum);
	
	// 아이디가 기존에 존재하는지 확인
	boolean selectMember(String mId);
	
	// 회원 목록 검색
	ArrayList<MemberVO> select();
	
	// 회원정보 수정
	int update(MemberVO member);
	
	// 회원 탈퇴 - 회원 정보 삭제
	int delete(int mNum);
	
	// 탈퇴한 회원 목록 
	ArrayList<BackUpMemberVO> deleteMember();
	
}

















