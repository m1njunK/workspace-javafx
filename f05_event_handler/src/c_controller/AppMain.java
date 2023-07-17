package c_controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AppMain extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		System.out.println("START 호출");
		
		Parent root = FXMLLoader.load(
				getClass().getResource("/c_controller/Root.fxml")
				);
		System.out.println("FXML load 완료");
		
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.show();
		
		System.out.println("START 종료");
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
