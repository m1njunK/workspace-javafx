package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class Main extends Application {
	
	// 도화지
	private Canvas canvas;
	
	// 그리기 도구
	private GraphicsContext gc;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = (BorderPane)FXMLLoader.load(
					getClass().getResource("Root.fxml")
			);
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.show();
			
			canvas = (Canvas) root.lookup("#canvas");
			
			// canvas에 그려줄 그리기 도구
			gc = canvas.getGraphicsContext2D();
			
			draw();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	// 그리기 도구 활용 방법
	private void draw() {
		// 그리기 도구에 색상 지정(채우기)
		gc.setFill(Color.SKYBLUE);
		// 사각형 그리기
		// (가로위치,세로위치,너비(폭),높이)
		gc.fillRect(100,100,100,100);
		
		// 외곽선 색 지정
		gc.setStroke(Color.RED);
		gc.strokeRect(150, 150, 100, 100);
		
		// 선그리기 도구
		// (시작 x, 시작 y, 끝 x, 끝 y)
		gc.strokeLine(0, 0, 500, 500);
		gc.strokeLine(250, 0, 250, 500);
		gc.strokeLine(500, 0, 0, 500);
		gc.strokeLine(0, 250, 500, 250);
		
		// 여러 선을 이어서 그려주는 도구
		gc.beginPath(); // 선을 이어그리기 시작
		gc.setStroke(Color.ALICEBLUE);
		gc.lineTo(10, 10);
		gc.lineTo(20, 30);
		gc.setStroke(Color.RED);
		gc.lineTo(50, 40);
		gc.lineTo(10, 10);
		gc.stroke(); // 선 그리기
		
		gc.setStroke(Color.BLACK);
		// 호를 그리는 도구
		gc.strokeOval(100, 100, 50, 50);
		
		// 이미지 그리기 도구
		Image image = new Image(
				getClass().getResource("img/cat1.jpg").toString()
		);
		// (그려줄 이미지, 시작 x, 시작 y)
		// gc.drawImage(image, 0, 0);
		// (그려줄 이미지, 시작 x, 시작 y, 너비, 높이)
		gc.drawImage(image, 200, 200, 100, 100);
	
		Thread t = new Thread(()->
		{
			
			for(int i = 0; i < canvas.getWidth()/2-50; i++) {
				int j = 400-i;
				gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
				gc.drawImage(image, i, i,100,100);
				gc.drawImage(image, canvas.getWidth()-i-100, i,100,100);
				gc.drawImage(image, j, j,100,100);
				gc.drawImage(image, i, j,100,100);
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(i == canvas.getWidth()/2-51) {
					int l = 200;
					gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
					for(int k = 100 ; k < 500; k++) {
						gc.drawImage(image, l, l-10,k,k++);
						try {
							Thread.sleep(5);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						l--;
					}
				}				
				
			}
		
		});
		
		t.start();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
