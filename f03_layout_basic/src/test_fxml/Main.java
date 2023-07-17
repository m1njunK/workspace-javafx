package test_fxml;
	
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {
	
	@Override
	public void start(Stage primaryStage) throws IOException {
		
		Parent root = FXMLLoader.load(getClass().getResource("Sample.fxml"));
		Scene scene = new Scene(root);
		// 무대에서 출력할 장면 설정
		primaryStage.setScene(scene);
		// 무대 타이틀 제목 지정
		primaryStage.setTitle("Hello JavaFX!!!");
		primaryStage.show();
		
	}
	
	public static void main(String[] args) {
		launch(args);
		/*
		 * <img src="src/img.jpg" /> <br/>
		 */
	}
	
}
