module f06_bundle_event {
	requires javafx.controls;
	requires javafx.fxml;
	
	opens bundle to javafx.graphics, javafx.fxml;
}
