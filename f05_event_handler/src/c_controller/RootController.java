package c_controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

public class RootController implements Initializable{

	@FXML private Button btn1,btn2,btn3;
	
	public RootController() {
		System.out.println("RootController 생성자 호출");
	}
	/**
	 * initialize 호출 전
	 * FXML 요소 정보를 필드에 초기화
	 */
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		System.out.println("initialize 호출");
		System.out.println("FXML 파일 정보 : " + location);
		System.out.println(btn1);
		System.out.println(btn2);
		System.out.println(btn3);
		
		btn1.setOnAction((e)->{
			Button btn = (Button) e.getTarget();
			System.out.println(btn.getId()+" click!!!");
		});
	
	} // end initialize
	
	public void closeEvent(ActionEvent e) {
		System.out.println("Button Click!!");
		Button btn = (Button) e.getTarget();
		if(btn.getId().equals("btn2")) {
			System.out.println("2번 버튼");
		}else {
			System.out.println("3번 버튼");
		}
	}



	
}
