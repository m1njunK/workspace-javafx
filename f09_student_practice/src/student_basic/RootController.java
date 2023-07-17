package student_basic;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.StudentVO;

public class RootController implements Initializable{
	
	@FXML private TableView<StudentVO> tableView;
	@FXML private Button btnAdd, btnBarChart;

	ObservableList<StudentVO> list;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		list = FXCollections.observableArrayList(
				new StudentVO("홍길동A",40,60,80),
				new StudentVO("홍길동B",60,80,40),
				new StudentVO("홍길동C",80,40,60)
		);
		
		/*
		TableColumn<StudentVO, String> nameColumn = new TableColumn<>("이름");
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		tableView.getColumns().add(nameColumn);
		*/
		
		Class<StudentVO> clazz = StudentVO.class;
		Field[] fields = clazz.getDeclaredFields();
		
		for(int i = 0; i < fields.length; i++) {
			String name = fields[i].getName();
			TableColumn<StudentVO, ?> tc = new TableColumn<>(name);
			tc.setCellValueFactory(new PropertyValueFactory<>(name));
			// column 너비 지정
			tc.setPrefWidth(100);
			// column 크기 수정 불가
			tc.setResizable(false);
			tc.setStyle("-fx-alignment:center;-fx-text-fill:red;");
			tableView.getColumns().add(tc);
		}
		
		tableView.setItems(list);
		
		tableView.setOnMouseClicked(new EventHandler<MouseEvent>() {
			
			@Override
			public void handle(MouseEvent event) {
				// tableView 항목 더블 click 시 pieChart 창 오픈
				// 단시간내에 click된 횟수
				int clickCount = event.getClickCount();
				System.out.println("click count : " + clickCount);
				// 마우스로 click된 종류
				// 좌클릭 - PRIMARY, 우클릭 - SECONDAARY, MIDDLE - 휠마우스
				MouseButton btn = event.getButton();
				System.out.println(btn);
				
				// 좌클릭 - 더블클릭
				if(btn == MouseButton.PRIMARY && clickCount == 2) {
					
					// event 발생 시점에 선택된 항목 
					StudentVO value = tableView.getSelectionModel().getSelectedItem();
					System.out.println(value);
					if(value == null) {
						return;
					}
				
				// fxml 파일에 대한 정보를 Controller로 관리
				Stage stage = new Stage();
				FXMLLoader loader = null;
				Parent root = null;
				try {
					loader = new FXMLLoader(getClass().getResource("PieChart.fxml"));
					root = loader.load();
				} catch (IOException e) {
					e.printStackTrace();
					return;
				}	
				
				PieChartController controller = loader.getController();
				stage.setScene(new Scene(root));
				// 사용자 정의 Data
				stage.setUserData(value);
				// root container 정보가 stage에 등록 되고 난 후 호출
				controller.setStudentData(value);
				
				stage.setTitle("파이 그래프");
				stage.show();
					
				/*
				// fxml 파일을 이용한 스테이지 구성
				Stage stage = new Stage();
				Parent root = null;
				
				try {
					root = FXMLLoader.load(getClass().getResource("PieChart.fxml"));
				} catch (IOException e) {
					e.printStackTrace();
					return;
				}
					
				PieChart pieChart = (PieChart) root.lookup("#pieChart");

				pieChart.setData(FXCollections.observableArrayList(
						new PieChart.Data("국어", value.getKor()),
						new PieChart.Data("수학", value.getMath()),
						new PieChart.Data("영어", value.getEng())
				));
				
				Button btnClose = (Button) root.lookup("#btnClose");
				btnClose.setOnAction(e->{
					stage.close();
				});
				
				Scene scene = new Scene(root);
				stage.setScene(scene);
				stage.setTitle("파이 그래프");
				stage.show();
				*/
					
					// 자바 코드로 UI 생성 및 이벤트 처리
					/*
					BorderPane pane = new BorderPane();
					pane.setPrefWidth(300);
					pane.setPrefHeight(300);
					PieChart chart = new PieChart();
					chart.setData(FXCollections.observableArrayList(
							new PieChart.Data("국어", value.getKor()),
							new PieChart.Data("수학", value.getMath()),
							new PieChart.Data("영어", value.getEng())
					));
					pane.setCenter(chart);
					
					HBox hBox = new HBox();
					// hBox 높이 지정
					hBox.setPrefHeight(50);
					// hBox 내부 요소 정렬 기준을 CENTER로 지정
					// 정렬 기준의 매개변수로 Pos(Position) 기준이 되는 값을 상수로 저장하는 class
					hBox.setAlignment(Pos.CENTER);
					Button btnClose = new Button("닫기");
					hBox.getChildren().add(btnClose);
					
					pane.setBottom(hBox);
					
					Scene scene = new Scene(pane);
					Stage stage = new Stage();
					
					btnClose.setOnAction(e->{
						stage.close();
					});
					
					stage.setScene(scene);
					stage.setTitle("파이 그래프");
					stage.show();
					*/
				} // end double click event
				
			} // end handle
			
		}); // end mouse clicked event
		
		// 추가 버튼 이벤트 추가
		btnAdd.setOnAction(e->{
			Stage stage = new Stage();
			
			FXMLLoader loader = null;
			Parent root = null;
			
			try {
				loader = new FXMLLoader(
						getClass().getResource("Form.fxml")
				);
				root = loader.load();
			} catch (IOException e1) {
				e1.printStackTrace();
				return;
			}
			FormController controller = loader.getController();
			Scene scene = new Scene(root);
			stage.setScene(scene);
			// tableView list 정보, 현재 show할 무대 정보 매개변수로 전달
			controller.init(list, stage);
			stage.show();
			
		});	// end 추가 버튼 이벤트
		
		// 학생별 막대그래프 버튼 이벤트
		
		btnBarChart.setOnAction(e->{
			Stage stage = new Stage();
			stage.setTitle("막대 그래프");
			
			FXMLLoader loader = null;
			Parent root = null;
			
			try {
				loader = new FXMLLoader(
					getClass().getResource("BarChart.fxml")
				);
				root = loader.load();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
			stage.setScene(new Scene(root));
			// barChart에서 사용할 list, Stage 정보 controller에 초기화
			BarChartController controller = loader.getController();
			// controller에 추가된 init 메소드를 통하여 초기화
			controller.init(list,stage);
			
			stage.show();
		});	// end barChart event
		
		
	}	// end initialize
}
