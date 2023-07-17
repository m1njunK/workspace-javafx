package c1_buttons;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker.State;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class WebViewController implements Initializable {

	@FXML private WebView webView;
	@FXML private ImageView imageView;
	
	private WebEngine engine;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		engine = webView.getEngine();
		
		State state = engine.getLoadWorker().getState();
		System.out.println(state);
		// State.READY
		engine.load("https://www.youtube.com/embed/JTqCtl548oc");
		// State.SCHEDULED - 작업스레드 대기 상태 
		// State.RUNNING - load 진행 상태
		// State.SUCCEEDED - 로드 작업 성공 완료
		// State.FAILED - 로드 작성 실패
		engine.getLoadWorker().stateProperty().addListener(new ChangeListener<State>() {

			@Override
			public void changed(ObservableValue<? extends State> observable, 
						State oldValue, 
						State newValue) {
				System.out.println("이전 : " + oldValue);
				System.out.println("현재 : " + newValue);
				if(newValue.equals(State.SUCCEEDED)) {
					// 이미지를 화면에서 숨김
					imageView.setVisible(false);
				}
				
				if(newValue.equals(State.FAILED)) {
					System.out.println("로드 실패");
					Platform.exit();
				}
			}
		});
		
	}

}
