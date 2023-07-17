package chat_client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

public class ClientController implements Initializable{

	@FXML private TextArea txtDisplay;
	@FXML private TextField txtIp, txtPort, txtNick, txtInput;
	@FXML private ListView<String> listView;
	@FXML private Button btnConn, btnSend;
	
	// 연결된 server 정보를 저장 Socket;
	Socket server;
	// 연결 요청을 보낼 server ip 주소
	InetAddress ip;
	// 연결 요청을 보낼 server port 번호
	int port;
	// 서버로 출력할 스트림
	PrintWriter printer;
	// 서버에서 데이터를 입력받을 스트림
	BufferedReader br;
	// client가 사용 중인 닉네임(ID)
	String nickName;
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		
		
		Platform.runLater(()->{
			txtIp.requestFocus();
		});
		
		btnConn.setOnAction(e->{
			String ip = txtIp.getText().trim();
			if(ip.equals("")) {
				displayText("아이피 주소를 작성해주세요.");
				txtIp.requestFocus();
				return;
			}
			try {
				// getByName에 전달된 문자열이 ipv4 주소값이나 domain형태가
				// 아니면 UnknownHostException 발생
				this.ip = InetAddress.getByName(ip);
			} catch (UnknownHostException e1) {
				displayText("사용할 수 없는 주소입니다.");
				txtIp.clear();
				txtIp.requestFocus();
				return;
			}	
			
			String textPort = txtPort.getText().trim();
			if(textPort.equals("")) {
				displayText("포트 번호를 작성해 주세요.");
				txtPort.requestFocus();
				return;
			}
			
			for(char c : textPort.toCharArray()) {
				if(c < 48 || c > 57) {
					displayText("잘못된 포트번호 입니다.");
					txtPort.clear();
					txtPort.requestFocus();
					return;
				}
			}
			
			this.port = Integer.parseInt(textPort);
			
			String nick = txtNick.getText().trim();
			if(nick.equals("")) {
				displayText("닉네임을 작성해주세요");
				txtNick.requestFocus();
				return;
			}
			
			this.nickName = nick;
			startClient();
		}); // end btnConn action event
		
		txtIp.setOnKeyPressed(event->{
			if(event.getCode() == KeyCode.ENTER) {
				txtPort.requestFocus();
			}
		});
		
		txtPort.setOnKeyPressed(event->{
			if(event.getCode() == KeyCode.ENTER) {
				txtNick.requestFocus();
			}
		});
		
		txtNick.setOnKeyPressed(event->{
			if(event.getCode() == KeyCode.ENTER) {
				btnConn.fire();
			}
		});
		
		txtInput.setOnKeyPressed(event->{
			if(event.getCode() == KeyCode.ENTER) {
				btnSend.fire();
			}
		
		});
		
		
		// 메세지 전달
		btnSend.setOnAction(e->{
			String text = txtInput.getText().trim();
			if(text.equals("")) {
				displayText("메세지를 먼저 작성해주세여.");
				txtInput.requestFocus();
				return;
			}
			// 서버로 메세지 전달
			send(1,text);
		});
		
		// 리스트 뷰 항목에 사용자 항목 선택 시 귓속말 전달 할 수 있도록 추가
		listView.setOnMouseClicked(event->{
			
			if(event.getClickCount() == 2) {
				String nickName = listView.getSelectionModel().getSelectedItem();
				if(nickName == null) {
					displayText("먼저 닉네임을 선택해주세요.");
					return;
				}
				
				if(this.nickName.equals(nickName)) {
					displayText("자신은 선택이 안됩니다.");
					return;
				}
				
				txtInput.clear();
				// /w 닉네임 메세지
				txtInput.setText("/w "+nickName+" ");
				txtInput.requestFocus();
				// 커서를 마지막으로 이동
				txtInput.selectEnd();
			}
		});
		
	}
	
	// client 시작
	public void startClient() {		
		try {
			server = new Socket(ip,port);
			displayText("[연결 완료 : "+server.getRemoteSocketAddress()+"]");
			
			// server와 통신할 입출력 스트림 초기화
			printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(server.getOutputStream())),true
			);
			
			br = new BufferedReader(new InputStreamReader(server.getInputStream()));

			send(0,nickName);
			
		} catch (IOException e) {
			displayText("[서버 연결안됨. IP와 PORT를 확인해 주세요]");
			stopClient();
			return;
		}
		// 서버에서 발신된 데이터 수신
		receive();
	}
	
	// 자원해제 후 client 종료
	public void stopClient() {
		displayText("[서버와 연결 종료]");
		if(server != null && !server.isClosed()) {
			try {
				server.close();
			} catch (IOException e) {}
		}
	}
	
	// 메세지를 구분할 수 있는 code와 함께 server에 메세지 전달
	public void send(int code , String msg) {
		// 0 | 닉네임
		// 1 | 메세지
		printer.println(code+"|"+msg);
		txtInput.clear();
		txtInput.requestFocus();
	}
	
	// server에서 메세지 전달 받음
	public void receive() {
		Thread t = new Thread(()->{
			while(true) {
				
				try {
					String receiveData = br.readLine();
					if(receiveData == null) {
						break;
					}
					
					System.out.println(receiveData);
					String[] data = receiveData.split("\\|");
					// code 0 : 접속자 목록
					// code 1 : 일반 메세지
					String code = data[0];
					String text = data[1];
					
					if(code.equals("0")) {
						// 접속자 목록 갱신
						String[] list = text.split(",");
						ObservableList<String> array
						 = FXCollections.observableArrayList(list);
						Platform.runLater(()->{
							listView.setItems(array);
						});
					}else if(code.equals("1")) {
						// 일반 메세지 출력
						displayText(text);
					}
					
					displayText(text);
				} catch (IOException e) {
					stopClient();
					break;
				}
			}
		});
		t.setDaemon(true);
		t.start();
	}
	
	// txtDisplay textArea에 text 작성
	public void displayText(String text) {
		Platform.runLater(()->{
			txtDisplay.appendText(text+"\n");
		});
	}
}
