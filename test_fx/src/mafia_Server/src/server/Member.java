package server;

public class Member {
	
	// 닉네임
	String nickname;
	// 아이디
	String Id;
	// 비밀번호
	String Pw;
	// 승리횟수
	int winCount;
	// 이미지 경로
	String image;
	
	public Member(String nickname, String id, String pw, int winCount) {
		this.nickname = nickname;
		Id = id;
		Pw = pw;
		this.winCount = winCount;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	public String getPw() {
		return Pw;
	}

	public void setPw(String pw) {
		Pw = pw;
	}

	public int getWinCount() {
		return winCount;
	}

	public void setWinCount(int winCount) {
		this.winCount = winCount;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
	
}
