package student_basic;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import model.StudentVO;

public class PieChartController implements Initializable{

	@FXML private PieChart pieChart;
	@FXML private Button btnClose;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		/*
		// pieChart에 출력 Data를 전달 받아야 함.
		// container에 무대 정보가 추가 되기 전에 호출 되므로 오류 발생
		Stage stage = (Stage) pieChart.getScene().getWindow();
		System.out.println(stage);
		StudentVO value = (StudentVO) stage.getUserData();
		System.out.println(value);
		*/
		
		btnClose.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// 무대 정보 받아오기
				Stage stage = (Stage) pieChart.getScene().getWindow();
				stage.close();
			}
		});
		
	}

	// container가 stage에 등록 된 후 호출
	public void setStudentData(StudentVO value) {
		System.out.println("param : " + value);
		Stage stage = (Stage)btnClose.getScene().getWindow();
		StudentVO vo = (StudentVO)stage.getUserData();
		System.out.println("userData : " + vo);
		
		pieChart.setTitle(vo.getName());
		
		pieChart.setData(FXCollections.observableArrayList(
				new Data("국어",vo.getKor()),
				new Data("영어",vo.getEng()),
				new Data("수학",vo.getMath())
		));
	}
	
}
