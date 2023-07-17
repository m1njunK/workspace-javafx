package c5_charts;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.BubbleChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;

public class ChartController implements Initializable {

	@FXML private PieChart pieChart;
	@FXML private BarChart<String, Integer> barChart;
	@FXML private AreaChart<String, Integer> areaChart;
	@FXML private BubbleChart<Integer, Integer> bubbleChart;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// pie chart에 추가될 data를 observableList로 전달
		// chart 지정되는 Data 제네릭 타입에 맞게 Data class로 생성
		ObservableList<PieChart.Data> list
			= FXCollections.observableArrayList();
		list.add(new PieChart.Data("AWT",5)); // data명, 적용될 수치(double)
		list.add(new PieChart.Data("Swing", 25));
		list.add(new PieChart.Data("SWT",30));
		list.add(new PieChart.Data("JavaFX",60));
		pieChart.setData(list);
		
		// barChart
		barChart.setTitle("평균 키");
		// 막대 별로 어떤 정보를 저장하고 있는 지를 표현
		Series<String, Integer> series1 = new Series<>();
		series1.setName("남성");
		
		Series<String,Integer> series2 = new Series<>();
		series2.setName("여성");
		
		ObservableList<Data<String,Integer>> listBar = FXCollections.observableArrayList();
		listBar.add(new Data<>("2019",173));
		listBar.add(new Data<>("2020",173));
		listBar.add(new Data<>("2021",174));
		listBar.add(new Data<>("2022",176));
		series1.setData(listBar);
		
		listBar = FXCollections.observableArrayList();
		listBar.add(new Data<>("2019",160));
		listBar.add(new Data<>("2020",159));
		listBar.add(new Data<>("2021",163));
		listBar.add(new Data<>("2022",165));
		series2.setData(listBar);
		
		barChart.getData().add(series1);
		barChart.getData().add(series2);
		
		areaChart.setTitle("평균 온도");
		Series<String,Integer> series3 = new Series<>();
		series3.setName("서울");
		ObservableList<Data<String,Integer>> listChart = FXCollections.observableArrayList();
		listChart.add(new Data<>("2016",26));
		listChart.add(new Data<>("2017",24));
		listChart.add(new Data<>("2018",28));
		listChart.add(new Data<>("2019",29));
		listChart.add(new Data<>("2020",27));
		series3.setData(listChart);
		areaChart.getData().add(series3);
		
		Series<String,Integer> series4 = new Series<>();
		series4.setName("부산");
		listChart = FXCollections.observableArrayList();
		listChart.add(new Data<>("2016",29));
		listChart.add(new Data<>("2017",30));
		listChart.add(new Data<>("2018",31));
		listChart.add(new Data<>("2019",24));
		listChart.add(new Data<>("2020",32));
		series4.setData(listChart);
		areaChart.getData().add(series4);

		// BubbleChart
		// 체류 시간 별 상품 구매 수와 판매 금액
		// x : 체류시간, y : 금액, 구매수를 범위로 표현 
		Series<Integer,Integer> seriesA = new Series<>();
		seriesA.setName("40대");
		//								x  y scale
		seriesA.getData().add(new Data<>(5,0,0));
		seriesA.getData().add(new Data<>(10,5,5));
		seriesA.getData().add(new Data<>(20,4,7));
		seriesA.getData().add(new Data<>(30,3,2));
		bubbleChart.getData().add(seriesA);
		
	}
}
