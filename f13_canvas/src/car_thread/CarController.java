package car_thread;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;

public class CarController implements Initializable {

	@FXML private Canvas bgCanvas;
	@FXML private VBox carBox;
	@FXML private Button btnStart;
	
	// thread 종료 flag
	boolean isStart;
	// 차 및 배경을 반복하면서 이동 시킬 스레드를 저장
	List<Thread> threadList;
	
	// 변경되는 x 좌표를 저장할 field
	int bgX, i;
	
	int[] x;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initBg();
		initCar();
		btnStart.setOnAction(e->{
			isStart = true;
			for(Thread t : threadList) {
				t.start();
			}
			btnStart.setDisable(true);
		});
	}

	private void initCar() {
		i = 0;
		x = new int[4];
		
		Set<Node> carList = carBox.lookupAll("Canvas");
		
		for(Node n : carList) {
			final int j = i;
			Canvas canvas = (Canvas) n;
			GraphicsContext gc = canvas.getGraphicsContext2D();
			Image image = new Image(getClass().getResource("img/car"+(j+1)+".png").toString());
			gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
			gc.drawImage(image, 0, 24);
			threadList.add(new Thread(()->{
				while(isStart) {
					int a = (int) (Math.random()*15)+1;
					Platform.runLater(()->{
						gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
						gc.drawImage(image, x[j], 24);
					});
					x[j] += a;
					
					if(x[j] >= canvas.getWidth()) {
						x[j] = 0;
					}
					
//					if(x[j] + image.getWidth() >= canvas.getWidth()) {
//						isStart = false;
//						System.out.println((j+1)+"번 차량 승리!");
//						Platform.runLater(()->{
//							Alert alert = new Alert(AlertType.INFORMATION);
//							alert.setHeaderText(null);
//							alert.setContentText((j+1)+"번 차량 승리!");
//							alert.showAndWait();
//							btnStart.setDisable(false);
//							initBg();
//							initCar();
//						});
//					}
					
					

					
					
					
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}));
			i++;
		}
	}

	// 배경 이미지 초기화
	private void initBg() {
		threadList = new ArrayList<>();
		GraphicsContext bgGc = bgCanvas.getGraphicsContext2D();
		Image image = new Image(getClass().getResource("img/sky.jpg").toString());
		bgGc.drawImage(image, 0,0,626, 400);
	
		Thread t = new Thread(()->{
			bgX = 0;
			while(isStart) {
				Platform.runLater(()->{
					bgGc.clearRect(0, 0, bgCanvas.getWidth(), bgCanvas.getHeight());
					Image image_back = new Image(getClass().getResource("img/sky_back.jpg").toString());
					bgGc.drawImage(image, bgX, 0,626,400);
					bgGc.drawImage(image_back, bgX+626, 0,626,400);
				});
				bgX -= 1;
				if(bgX <= -626) {
					bgX = 0;
				}
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		t.setDaemon(true);
		threadList.add(t);
	}

}
