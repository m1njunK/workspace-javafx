package student_basic;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import model.StudentVO;

public class BarChartController {
	// xAxis = category - 학생 이름
	// yAxis = Number - 점수
	@FXML private BarChart<String,Integer> barChart;
	@FXML private Button btnClose;
	
	public void init(ObservableList<StudentVO> list, Stage stage) {
		// 닫기 버튼 이벤트 발생 시 BarChart Stage 종료
		btnClose.setOnAction(e->stage.close());
		
		// 전달 받은 학생목록 리스트 정보를 이용하여 BarChart 초기화
		Series<String, Integer> seriesKor = new Series<>();
		seriesKor.setName("국어");
		
		Series<String, Integer> seriesMath = new Series<>();
		seriesMath.setName("수학");
		
		Series<String, Integer> seriesEng = new Series<>();
		seriesEng.setName("영어");

		// barChart에 series 등록
		barChart.getData().add(seriesKor);
		barChart.getData().add(seriesMath);
		barChart.getData().add(seriesEng);
		
		// XYChart.Data
		ObservableList<Data<String,Integer>> listKor = FXCollections.observableArrayList();
		ObservableList<Data<String,Integer>> listMath = FXCollections.observableArrayList();
		ObservableList<Data<String,Integer>> listEng = FXCollections.observableArrayList();
		
		// 각 시리즈 별로 출력할 카테고리와 점수 추가
		for(StudentVO s : list) {
			listKor.add(new Data<>(s.getName(), s.getKor()));
			listMath.add(new Data<>(s.getName(), s.getMath()));
			listEng.add(new Data<>(s.getName(), s.getEng()));
		}
		
		seriesKor.setData(listKor);
		seriesMath.setData(listMath);
		seriesEng.setData(listEng);
		
	}
	
}
