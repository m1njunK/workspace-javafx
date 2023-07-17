module f07_property_listner {
	requires javafx.controls;
	requires javafx.fxml;
	
	opens e1_property_listener to javafx.graphics, javafx.fxml;
}
