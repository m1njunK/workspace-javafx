package application;
	


import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


public class Main extends Application {
	
	@Override
	public void start(Stage primaryStage) {
		
		HBox hbox = new HBox();
		
		Button btn = new Button("버튼1");
		// 너비와 높이 한번에 지정 (폭, 높이)
		btn.setPrefSize(200, 100);
		
		ButtonActionEventHandler baeh = new ButtonActionEventHandler();
		btn.setOnAction(baeh);
		
		// 익명 구현 객체를 이용한 이벤트 처리 전달
		Button btn2 = new Button("버튼2");
		EventHandler<ActionEvent> handle2 = new EventHandler<>() {

			@Override
			public void handle(ActionEvent arg0) {
				System.out.println("버튼2 click!@!@");
				primaryStage.close();
			}
			
		};
		btn2.setOnAction(handle2);
		
		Button btn3 = new Button("버튼3");
		btn3.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				System.out.println("버튼 3!!!");
			}
			
		});
		
		Button btn4 = new Button("버튼4");
		btn4.setOnAction(e ->{
			System.out.println("버튼 4 click!");
			Platform.exit();
		});
		
		
		/*
		hbox.getChildren().add(btn);
		hbox.getChildren().add(btn2);
		hbox.getChildren().add(btn3);
		hbox.getChildren().add(btn4);
		*/
		
		hbox.getChildren().addAll(btn,btn2,btn3,btn4);
		
		Scene scene = new Scene(hbox);
		primaryStage.setScene(scene);
		// 무대(윈도우)에 닫기버튼 클릭 이벤트
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				System.out.println(event);
				// 발생 window close를 소비하여 발생 시키지 않음.
				event.consume();
			}
		});
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
