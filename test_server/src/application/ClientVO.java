package application;

import java.net.Socket;

/**
 * 유저 레벨과 전적 등을 저장한다. 
 * 
 * 
 * */
public class ClientVO {

	private Socket socket; 
	
	private String nickName, passWord, level , introduction;
	
	int wins, loses;
	
	private double rate;
	
	
	// 인스턴스의 승률 구하기 
		public String calRate(){
			wins = 1; loses=1;
			
			
			rate = 100*((double)this.wins/(this.wins+this.loses));
			Math.round(rate);
			System.out.println(rate);
			return Math.round(rate)+"%";
		}
	
	
	
	
	
	
	

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public int getWins() {
		return wins;
	}

	public void setWins(int wins) {
		this.wins = wins;
	}

	public int getLoses() {
		return loses;
	}

	public void setLoses(int loses) {
		this.loses = loses;
	}

	public double getRate() {
		return rate;
	}

	public void setRate(int rate) {
		this.rate = rate;
	} 
	
	
	
}
