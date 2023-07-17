package bundle;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) {
			// Locale : 각 나라 지역의[언어][나라] : 언어코드 - 국가코드 등의
		// 공통 정보를 담고 있는 class
		// Map 형태로 구현된 데이터의 묶음을 bundle이라고 함.
		try {
			for(Locale locale : Locale.getAvailableLocales()) {
				// 한글이름
				System.out.println("Display Language : " + locale.getDisplayLanguage());
				System.out.println("Language code : " + locale.getLanguage());
				System.out.println("Didplay Country : " + locale.getDisplayCountry());
				System.out.println("Country code : " + locale.getCountry());
			}
			
			System.out.println("==================================================");
			Locale locale = Locale.getDefault();
			System.out.println("현재 접속한 컴퓨터 운영체제의 국가 코드 정보 : " + locale);
			locale = new Locale("ja","JP");
			locale = new Locale("zh","CN");
			locale = new Locale("en","US");
			locale = new Locale("de","DE");
			Locale.setDefault(locale);
			
			ResourceBundle bundle = ResourceBundle.getBundle("prop.s");
			
			HBox root = (HBox) FXMLLoader.load(getClass().getResource("Bundle.fxml"),bundle);
			
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.show();
			
		} catch (IOException e) {

			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
