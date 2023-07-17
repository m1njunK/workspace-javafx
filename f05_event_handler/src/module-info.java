module f05_event_handler {
	requires javafx.controls;
	requires javafx.base;
	requires javafx.fxml;
	
	opens application to javafx.graphics, javafx.fxml;
	opens b_fxml_event to javafx.graphics, javafx.fxml;
	opens c_controller to javafx.graphics, javafx.fxml;
}
