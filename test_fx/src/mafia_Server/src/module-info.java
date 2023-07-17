module mafia_Server {
	requires javafx.controls;
	requires javafx.fxml;
	
	opens server to javafx.graphics, javafx.fxml;
	exports server;
}
