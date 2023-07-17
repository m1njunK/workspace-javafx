package chat_server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

// 연결된 client정보를 저장 하여 입력과 출력을 담당할 class
public class ServerTask implements Runnable{
	// 현재 task에 연결된 client socket 정보
	Socket client;
	// display 정보가 있는 controller instance
	ServerController sc;
	
	// 연결된 client에 출력할 스트림
	PrintWriter printer;
	// client에서 출력된 데이터를 입력받을 스트림
	BufferedReader reader;
	
	// 연결된 client의 nickName 정보
	String nickName;
	
	// receive task end flag
	boolean isRun = true;
	
	public ServerTask(Socket client,ServerController sc) {
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
	@Override
	public void run() {
		sc.printText(client.getRemoteSocketAddress()+ " receive 시작");
		while(isRun) {
			try {
				// client에서 메세지가 전달 될때 까지 blocking
				String receiveData = reader.readLine();
				if(receiveData == null) {
					break;
				}
				System.out.println(receiveData);
				// 0|nickName
				// 1|message
				String[] data = receiveData.split("\\|");
				// [0,nickName][1,message]
				String code = data[0];
				String text = data[1];
				
				switch(code) {
					case "0" : 
						// 닉네임 전달
						// hashtable 에 등록된 닉네임이 이미 key값으로 존재하면 true
						if(sc.clients.containsKey(text)) {
							sc.printText("1|"+text+"-사용할 수 없는 닉네임 요청");
							this.printer.println(text + " 사용 할 수 없는 닉네임 입니다.");
							isRun = false;
							client.close();
							break;
						}
						// 중복되지 않는 닉네임 필드에 저장
						nickName = text;
						// client목록에 등록
						sc.clients.put(text, printer);
						// client 목록 갱신
						String sendData = "";
						
						for(String s : sc.clients.keySet()) {
							sendData += s+",";
						}
						// 0|닉네임,닉네임,닉네임,닉네임, ....
						broadCast(0, sendData);
						broadCast(1,nickName+"님이 입장하셨습니다. -방인원 : " + sc.clients.size());
						
						break;
						
					case "1" :
						// 1|/w 닉네임 메세지
						// text : /w 닉네임 메세지
						if(text.startsWith("/w")) {
							System.out.println("귓속말");
							int begin = text.indexOf(" ")+1;
							// begin 부터 시작해서 첫번째 매개변수가 어디에 위치하고 있는지
							// 인덱스 번호 반환
							int end = text.indexOf(" ", begin);
							
							if(begin != -1 && end != -1) {
								// begin 인덱스 부터 end 인덱스 이전까지
								String nick = text.substring(begin,end);
								// end 인덱스 부터 끝까지 문자열을 잘라냄
								String message = text.substring(end);
								PrintWriter pw = sc.clients.get(nick);
								if(pw == null) {
									// 일치하는 닉네임의 사용자를 찾지 못함.
									this.printer.println("1|"+nick+" 사용자가 존재하지 않습니다.");
								}else {
									// 닉네임이 일치하는 사용자에게 메세지 전달
									pw.println("1|"+this.nickName+"님의 귓속말 : " + message);
								}
							}else {
								this.printer.println("1|잘못된 명령입니다.");
							}
							
						}else {
							// !startsWith("/w")
							broadCast(1, nickName+":"+text);
						}
						break;
					}
				
			} catch (IOException e) {
				System.out.println("client 연결 종료 : " + e.getMessage());
				isRun = false;
			}
		}	// end while 종료
		// while - task 작업 종료
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
		broadCast(1,nickName+"님이 나가셨습니다. 방인원 : " + sc.clients.size());
	}
	
	// 연결된 모든 client에게 메세지 전달
	// code : 0 - 접속자 목록 출력, 1 : 메세지 전달
 	public void broadCast(int code , String message) {
		for(PrintWriter p : sc.clients.values()) {
			p.println(code+"|"+message);
		}
	}
	
}
