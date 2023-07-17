module f13_canvas {
	requires javafx.controls;
	requires javafx.fxml;
	
	opens application to javafx.graphics, javafx.fxml;
	opens car_thread to javafx.graphics, javafx.fxml;
	opens draw_line to javafx.graphics, javafx.fxml;
}
