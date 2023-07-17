package application;
	
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javafx.application.Application;
import javafx.stage.Stage;


public class Main extends Application {
	
	public Main() {
		System.out.println(Thread.currentThread() + " - Main 생성자 호출");
	}
	
	@Override
	public void init() throws Exception {
		// main method 에서 실행 시 전달받은 arguments(인자값)
		// 을 Application 에서 가공 처리하여 저장 == parameters
		Parameters params = super.getParameters();
		List<String> list = params.getRaw();
		System.out.println(list);
		
		Map<String,String> map = params.getNamed();
		System.out.println(map);
		
		System.out.println(Thread.currentThread() + "init 호출");
	}

	@Override
	public void stop() throws Exception {
		System.out.println(Thread.currentThread() + "stop 호출");
	}

	@Override
	public void start(Stage primaryStage) {
		try {
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		System.out.println(Arrays.toString(args));
		System.out.println(Thread.currentThread() + " main() 호출");
		launch(args);
		System.out.println(Thread.currentThread() + " main() 종료");
	}
}
