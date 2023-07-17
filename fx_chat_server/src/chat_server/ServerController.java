package chat_server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.Hashtable;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ServerController implements Initializable{

	@FXML private TextArea displayText;
	@FXML private TextField txtPort;
	@FXML private Button btnStartStop;
	
	// Client Thread를 관리 할 스레드 풀
	ExecutorService serverPool;
	
	// 운영체제에서 포트를 할당 받아
	// client socket 연결 관리를 할 class
	ServerSocket server;
	// 연결된 클라이언트의 출력 스트림 정보를 저장할 Map 객체
	// generic<Client ID, socket 출력 스트림>
	Hashtable<String,PrintWriter> clients;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		btnStartStop.setOnAction(e->{
			String text = btnStartStop.getText();
			// _Start
			if(text.equals("_Start")) {
				startServer();
				btnStartStop.setText("S_top");
			}else {
				stopServer();
				btnStartStop.setText("_Start");
			}
		});
	}
	
	// server 시작
	public void startServer() {
		serverPool = Executors.newFixedThreadPool(50);
		clients = new Hashtable<>();
		
		String port = txtPort.getText();
		if(port.trim().length() == 0) {
			printText("작성된 포트 번호가 없습니다.\n");
			return;
		}
		// PORT 번호는 - 정수타입이어야 한다
		// 0 ~ 65535
		// 1024 ~ 60000;
//		[5][0][0][1]
		for(char c : port.toCharArray()) {
			if(c < 48 || c > 57) {
				printText("잘못된 PORT 번호입니다.");
				return;
			}
		}
		
		int portNumber = Integer.parseInt(port);
		// 해당 포트번호를 할당받아 client에 연결 정보를 수락할 수 있ㄷ호록
		// serverSocket 생성
		
		try {
			server = new ServerSocket(portNumber);
		} catch (IOException e) {
			printText("서버 연결 오류 : " + e.getMessage()+"\n");
			stopServer();
			return;
		}
		Runnable run = new Runnable() {
			
			@Override
			public void run() {
				printText("[서버 시작]\n");
				while(true) {
					try {
						printText("cilent 연결 대기중....");
						Socket client = server.accept();
						String address = client.getRemoteSocketAddress().toString();
						String message = "[연결 수락 : "+address+"]";
						printText(message);
						// 연결된 client마다 전달 메세지를 receive 할 수 있도록
						// 스레드 풀에 작업을 정의한 ServerTask 전달
						serverPool.submit(new ServerTask(client, ServerController.this));
					} catch (IOException e) {
						stopServer();
						break;
					}
				}
			}
		};
		// 스레드 풀에 수행될 작업 전달
		serverPool.submit(run);
		
		
	}
	
	// 작업 스레드 에서 textArea에 출력하는 UI 작업을 처리
	public void printText(String text) {
		Platform.runLater(()->{
			displayText.appendText(text+"\n");
		});
	}
	
	// 자원해제 후 서버 종료
	public void stopServer() {
		
		if(clients != null) {
			for(PrintWriter p : clients.values()) {
				if(p != null) {
					p.close();
				}
			}
		}
		
		clients.clear();
		
		try {
			if(server != null && !server.isClosed()) {
					server.close();
			}
			
			if(serverPool != null && !serverPool.isShutdown()) {
				serverPool.shutdownNow();
			}
			
			printText("[서버 중지]");
			
		} catch (IOException e) {}
	}
}
