package server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

public class ServerController implements Initializable{
	
	@FXML Button btnStart;
	@FXML TextArea displayText;
	@FXML ListView<Member> listView;
	
	// Client Thread를 관리할 스레드 풀
	ExecutorService serverPool;
	// 운영체제에서 포트를 할당받아
	// client socket 연결 관리 할 class
	ServerSocket server;
	// 연결된 클라이언트의 출력 스트림 정보를 저장할 Map 객체
	// generic<ClientID, socket 출력 스트림>
	Hashtable<String, PrintWriter> clients;
	
	List<Member> mlist = new ArrayList<>();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		btnStart.setOnAction(e->{
			String text = btnStart.getText();
			
			if(text.equals("START")) {
				startServer();
				btnStart.setText("STOP");
			}else {
				stopServer();
				btnStart.setText("START");
			}
			
		});
		
	}

	private void startServer() {
		serverPool = Executors.newFixedThreadPool(50);
		clients = new Hashtable<>();
		
		// serverSocket 생성
		try {
			server = new ServerSocket(5001);
		} catch (IOException e) {
			displayText.appendText("서버 연결 오류 : " + e.getMessage()+"\n");
			stopServer();
			return;
		}
		Runnable run = new Runnable() {
			@Override
			public void run() {
				printText("[서버 시작]");
				while(true) {
					try {
						printText("client 연결 대기중.....");
						Socket client = server.accept();
						String address = client.getRemoteSocketAddress().toString();
						String message = "[연결 수락 : "+address+"]";
						printText(message);
						// 연결된 client마다 전달 메세지를 receive할 수 있도록
						// 스레드 풀에 작업을 정의한 ServerTask 전달
						serverPool.submit(new ServerTask(client, ServerController.this));
					} catch (IOException e) {
						stopServer();
						break;
					}
				}
			}
		};
		serverPool.submit(run);
		
	}

	private void stopServer() {
		
	}
	
	public void printText(String text) {
		Platform.runLater(()->{
			displayText.appendText(text+"\n");
		});
	}
	
	
}
