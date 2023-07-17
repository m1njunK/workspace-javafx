package draw_line;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.paint.Color;

public class DrawController implements Initializable {

	@FXML private Canvas canvas;
	@FXML private TextArea txtArea;
	@FXML private ColorPicker pick;
	@FXML private Slider slider;
	@FXML private Button btnClear;
	
	// 그리기 도구
	GraphicsContext gc;
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		gc = canvas.getGraphicsContext2D();
		// color picker의 기본 색상은 WHITE
		pick.setValue(Color.BLACK);		
		gc.setStroke(Color.BLACK);		// 선색
		gc.setLineWidth(1); 			// 선 굵기
		slider.setMin(1);
		slider.setMax(100);
		txtArea.setEditable(false);
		
		// canvas위에서 마우스가 움직일 때
		canvas.setOnMouseMoved(e->{
			double x = e.getScreenX();
			double y = e.getScreenY();
			txtArea.appendText("screen x : " + x + ", y : " + y + "\n");
			x = e.getX();
			y = e.getY();
			txtArea.appendText("canvas x : " + x + ", y : " + y + "\n");
		});
		
		// 마우스가 눌러졌을 때
		canvas.setOnMousePressed(e->{
			gc.beginPath();
			gc.lineTo(e.getX(), e.getY());
		});
		
		// 마우스가 드래그 되는 동안
		canvas.setOnMouseDragged(e->{
			gc.lineTo(e.getX(), e.getY());
			gc.stroke();
		});
		
		btnClear.setOnAction(e->{
			gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
			pick.setValue(Color.BLACK);
			slider.setValue(1);
			gc.setLineWidth(1);
			gc.setStroke(Color.BLACK);
		});
		
		pick.valueProperty().addListener((target,o,n)->{
			gc.setStroke(n);
			
		});
		
		slider.valueProperty().addListener((target,o,n)->{
			int value = n.intValue();
			double result = value/10.0;
			gc.setLineWidth(result);
		});
		
	}

}
