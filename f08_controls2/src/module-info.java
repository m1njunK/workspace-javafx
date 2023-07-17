module f08_controls2 {
	requires javafx.controls;
	requires javafx.fxml;
	
	opens c1_buttons to javafx.graphics, javafx.fxml;
}
