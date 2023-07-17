package application;

import java.lang.reflect.Field;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class AppController implements Initializable{

	@FXML private TableView<Student> tableView;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		ObservableList<Student> studentList = FXCollections.observableArrayList();
		studentList.add(new Student("홍길동A",40,60,80));
		studentList.add(new Student("홍길동B",60,80,40));
		studentList.add(new Student("홍길동A",80,40,60));
		System.out.println(studentList);
		
		
		Field[] field = Student.class.getDeclaredFields();
		
		
		
//		for(int i = 0; i < field.length; i++) {
//			String fieldName = field[i].getName();
//			System.out.println(fieldName);
//			TableColumn<Student,?> tColumn = tableView.getColumns().get(i);
//			tColumn.setCellValueFactory(new PropertyValueFactory<>(fieldName));
//		}
//		
//		tableView.setItems(studentList);
		
	}
	
}

