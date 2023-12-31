package guide.member.vo;

import java.util.Date;
/*
 탈퇴한 회원 정보를 저장하는 class
 */
public class BackUpMemberVO extends MemberVO{
	
	// 회원 탈퇴 일 정보
	private Date deleteDate;
	
	public BackUpMemberVO() {}

	public BackUpMemberVO(
			int mNum, 
			String mName, 
			String mId, 
			String mPw, 
			long reg, 
			Date deleteDate) {
		super(mNum, mName, mId, mPw, reg);
		this.deleteDate = deleteDate;
	}

	public Date getDeleteDate() {
		return deleteDate;
	}

	public void setDeleteDate(Date deleteDate) {
		this.deleteDate = deleteDate;
	}

	@Override
	public String toString() {
		return super.toString()+" [deleteDate=" + deleteDate + "]";
	}
	
}

















