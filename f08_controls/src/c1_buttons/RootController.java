package c1_buttons;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class RootController implements Initializable{
	
	@FXML private CheckBox chk1,chk2;
	@FXML private ToggleGroup group;
	@FXML private ImageView chkImg,radioImg;
	@FXML private Hyperlink hyperLink;
	@FXML private Button btnExit;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// 체크 박스 체크 or 체크 해제 이벤트 시
		// handlerChkAction(ActionEvent e) 호출
		
		chk1.setOnAction(new EventHandler<ActionEvent>() {			
			@Override
			public void handle(ActionEvent event) {
				handlerChkAction(event);
			}
		});
		chk2.setOnAction(e->{
			handlerChkAction(e);
		});
		
		// radio group(ToogleGroup)
		group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {

			@Override
			public void changed(
					ObservableValue<? extends Toggle> observable, 
					Toggle oldValue, 
					Toggle newValue) {
				RadioButton value = (RadioButton)newValue;
				String text = value.getText();
				System.out.println(text);
				
				text = "/images/" + text + ".png";
				
				String path = getClass().getResource(text).toString();
				Image image = new Image(path);
				radioImg.setImage(image);
			}
			
		});
		
		// 하이퍼 링크
		hyperLink.setOnAction(e->{
			System.out.println("hyper link!!!!");
			String link = (String) hyperLink.getUserData();
			System.out.println(link);
			
			// 도메인 정보를 가지고
			// 웹 화면을 애플리케이션에 출력 하는 view
			WebView webView = new WebView();
			// webView는 웹을 뵤여주기위한 view 공간
			WebEngine we = webView.getEngine();
			// engine webView에 보여줄 화면 정보를 로드 하는 class
			/*
			 * <iframe width="1280" height="720" 
			 * src="https://www.youtube.com/embed/JTqCtl548oc" 
			 * title="[SUB] 존재 자체가 인류의 밸런스 붕괴🤦‍♀, 최강의 사기캐 유지민🔥  [차린건 쥐뿔도 없지만] EP.19  #이영지 #카리나 (ENG/JPN/SPA/IND)" 
			 * frameborder="0" allow="accelerometer; 
			 * autoplay; clipboard-write; encrypted-media; 
			 * gyroscope; picture-in-picture; web-share" 
			 * allowfullscreen></iframe>
			 */
			
			link = "https://www.youtube.com/embed/JTqCtl548oc";
			we.load(link);
			
			// webview를 보여주기 위한 새로운 무대 생성
			Stage stage = new Stage();
			BorderPane br = new BorderPane();
			br.setCenter(webView);
			stage.setScene(new Scene(br));
			stage.setWidth(500);
			stage.setHeight(300);
			// stage 크기 변경 설정 false : 변경 불가
			stage.setResizable(false);
			stage.show();
		});
		
		
	} //end init
	
	public void handlerChkAction(ActionEvent e) {
		System.out.println(e.getTarget());
		boolean isCheck1 = chk1.isSelected();
		boolean isCheck2 = chk2.isSelected();
		System.out.println("chk1 : " + isCheck1);
		System.out.println("chk2 : " + isCheck2);

		String resource = "";
		if(isCheck1 && isCheck2) {
			resource = "/images/geek-glasses-hair.gif";
		}else if(isCheck1) {
			resource = "/images/geek-glasses.gif";
		}else if(isCheck2) {
			resource = "/images/geek-hair.gif";
		}else {
			resource = "/images/geek.gif";
		}
		
		// 이미지 변경
		chkImg.setImage(
				new Image(
						getClass().getResource(resource).toString()
				)
		);
	}
	
}










