package server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// 연결된 client정보를 저장하여 입력과 출력을 담당할 class
public class ServerTask implements Runnable{
	// 현재 task에 연결된 client socket 정보
	Socket client;
	// display 정보가 있는 controller instance
	ServerController sc;

	// 연결된 client에 출력할 스트림
	PrintWriter printer;
	// client에서 출력된 데이터를 입력받는 스트림
	BufferedReader reader;
	
	// 연결된 client의 nickName 정보
	String nickName;

	boolean isRun = true;
	
	List<Member> mlist = new ArrayList<>();
	
	public ServerTask(Socket client, ServerController sc) {
		this.client = client;
		this.sc = sc;
		
		try {
			OutputStream os = client.getOutputStream();
			OutputStreamWriter osw = new OutputStreamWriter(os);
			BufferedWriter writer = new BufferedWriter(osw);
			printer = new PrintWriter(writer,true);
			
			reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
			
		} catch (IOException e) {
			sc.printText("Client 연결 오류 : " + e.getMessage());
		}
	}
	
	// receive 역할을 담당
	public void run() {
		sc.printText(client.getRemoteSocketAddress()+" receive 시작");
		mlist.add(new Member("길","1234","1234",0));
		while(isRun) {
			try {

				String receiveData = reader.readLine();
				if(receiveData == null) {
					break;
				}
				
				String[] data = receiveData.split(",");
				String code = data[0];
				String id = data[1].trim();
				String pw = data[2].trim();
				PrintWriter p ;
				System.out.println(Arrays.toString(data));
				switch(code) {
					case "login" : // 로그인
							sc.clients.put(id, printer);
							p = sc.clients.get(id);
							for(int i = 0 ; i < mlist.size() ; i++) {
								if(mlist.get(i).getId().equals(id) && 
								   mlist.get(i).getPw().equals(pw)) {
									p.println("success");
									sc.printText(id+"님이 로그인하셨습니다.");
									break;
								}else if(!mlist.get(i).getId().equals(id)){
									p.println("faild");
									sc.printText(id+"님이 존재하지 않는 아이디로 로그인 시도하셨습니다.");
									break;
								}
							}
							break;
							
					case "join" : // 회원가입
							String nick = data[3].trim();
							sc.clients.put(id, printer);
							p = sc.clients.get(id);
							for(int i = 0 ; i < mlist.size() ; i++) {
								if(!mlist.get(i).getId().equals(id) &&
								   !mlist.get(i).getNickname().equals(nick)) {
									mlist.add(new Member(nick,id,pw,0));
									p.println("joinOk");
									sc.printText(id+"님이 회원가입하셨습니다.");
									break;
								}
								
								if(mlist.get(i).getId().equals(id) ||
								   mlist.get(i).getNickname().equals(nick)) {
									p.println("joinNo");
									break;
								}
							}
							sc.clients.remove(id);
							client.close();
							break;
							
					case "idCheck" : // 아이디 중복체크
							sc.clients.put(id, printer);
							p = sc.clients.get(id);
							for(int i = 0 ; i < mlist.size() ; i++) {
								if(mlist.get(i).getId().equals(id)) {
									p.println("idCheckNo");
									break;
								}else if(!mlist.get(i).getId().equals(id)) {
									p.println("idCheckOk");
									break;
								}
							}
							sc.clients.remove(id);
							client.close();
							break;
				}
				
			} catch (IOException e) {
				sc.printText("client 연결 종료 : " + e.getMessage());
				isRun = false;
			}
		}// while
		
		// while- task 작업 종료
		// client 소켓도 종료
		if(client != null && !client.isClosed()) {
			try {
				client.close();
			} catch (IOException e) {}
		}
		sc.clients.remove(nickName);
		// 나간 인원 정보로 목록 갱신
		
		String list = "";
		for(String s : sc.clients.keySet()) {
			list += s+",";
		}
		broadCast(0, list);
		broadCast(1, nickName+"님이 나가셨습니다. 방인원 : "+sc.clients.size());
	}
	
	// 연결된 모든 client에게 메세지 전달
	// code : 0 - 접속자 목록 출력 , 1 : 메세지 전달
	public void broadCast(int code, String msg) {
		for(PrintWriter p : sc.clients.values()) {
			p.println(code+"|"+msg);
		}
	}
	
	public void broadXY(String msg) {
		for(PrintWriter p : sc.clients.values()) {
			if(p != this.printer) {
				p.println(msg);
			}
		}
	}
	
}















