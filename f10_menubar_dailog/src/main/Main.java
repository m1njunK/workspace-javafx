package main;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader(
					getClass().getResource("/main/Root.fxml")
			);
			// FXML container load
			Parent root = loader.load();
			
			// FXML 파일과 함께 로드된 Controller 인스턴스 반환
			RootController control = loader.getController();
			Scene scene = new Scene(root);
			control.setStage(primaryStage);
			
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
