package b_fxml_event;

import java.util.Set;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class FxmlEventMain extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		HBox root = FXMLLoader.load(
				getClass().getResource("/b_fxml_event/Root.fxml")
			);
		// container에 등록된 모든 자식 요소 정보를
		// list로 전달 받아 이벤트 추가
		ObservableList<Node> list = root.getChildren();
		System.out.println(list.get(0));
		Node btn1 = list.get(0);
		// btn1.setOnAction
		((Button) btn1).setOnAction(e->{
			System.out.println("1번크 ㄹ릭!");
		});
		
		// 선택자와 일치하는 자식 요소 검색
		Node btnLookUp = root.lookup("#btn2");
		System.out.println(btnLookUp);
		Node btnLookUp2 = root.lookup(".btn3");
		System.out.println(btnLookUp2);
		Node btnLookUp3 = root.lookup("Button");
		System.out.println(btnLookUp3);
		
		Set<Node> lookupAll = root.lookupAll("Button");
		System.out.println(lookupAll);
		
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("fxml test");
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
