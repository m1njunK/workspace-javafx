package application;

import java.net.URL;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class RootController implements Initializable{

	@FXML private Label lblTime;
	@FXML private Button btnStart,btnStop;
	
	private boolean isRun = true;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		btnStart.setOnAction(e->{
			System.out.println(Thread.currentThread());
			System.out.println(Thread.currentThread().isDaemon());
			Thread t = new Thread(()->{
				System.out.println(Thread.currentThread());
				isRun = true;
				while(isRun) {
					LocalTime time = LocalTime.now();
					String data = time.format(
						DateTimeFormatter.ofPattern("mm:ss.SSS")
					);
					Platform.runLater(() -> {
						lblTime.setText(data);
					});
					try {
						Thread.sleep(10);
					} catch (InterruptedException e1) {}
				}
			});
			t.setDaemon(true);
			t.start();
		});
		
		btnStop.setOnAction(e->{
			isRun = false;
		});
	}
	
}
