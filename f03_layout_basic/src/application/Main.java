package application;
	
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		
		// 자바 코드로 레이아웃 구성
		// 세로 - 수직으로 내부에 요소를 배치하는 컨테이너
		VBox root = new VBox();
		root.prefHeight(150);	// 높이 설정
		root.prefWidth(350);	// 너비(폭)설정
		
		Label label = new Label();			// 문자열을 저장하는 요소
		label.setText("Hello JAVAFX!!");	// 요소에서 출력할 text 지정
		label.setFont(new Font(50));		// font - 글꼴이나 크기 설정
		
		// container에 포함된 자식 요소 목록
		ObservableList<Node> list = root.getChildren();
		list.add(label);
		
		Button btn = new Button();	// Button control 생성
		btn.setText("확인");			// button의 text 설정
		btn.setOnAction(new EventHandler<ActionEvent>() {		
			@Override
			public void handle(ActionEvent arg0) {
				Platform.exit();
			}
		});
		
		list.add(btn);
		
		// VBox를 root container로 지정하여 Scene(장면)을 생성
		Scene scene = new Scene(root);
		// 무대에서 출력할 장면 설정
		primaryStage.setScene(scene);
		// 무대 타이틀 제목 지정
		primaryStage.setTitle("Hello JavaFX!!!");
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
