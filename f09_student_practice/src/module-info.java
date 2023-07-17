module f099_student_practice {
	requires javafx.controls;
	requires javafx.fxml;
	
	opens student_basic to javafx.graphics, javafx.fxml;
	
	exports model;
}
