package c3_view_controls;

import java.lang.reflect.Field;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ViewController implements Initializable {

	@FXML private ListView<String> listView;
	@FXML private TableView<PhoneVO> tableView;
	@FXML private ImageView imageView;
	@FXML private TextField txtName;
	@FXML private Button btnUpdate;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// ListView에 출력 항목을 items 라고 함.
		// 항목을 전달 하는 class로 ObservableArrayList를 사용
		
		String[] strs = new String[] {
				"갤럭시s1","갤럭시s2","갤럭시s3","갤럭시s4",
				"갤럭시s5","갤럭시s6","갤럭시s7"
		};
		
		List<String> strList = Arrays.asList(strs);
		ObservableList<String> list 
			= FXCollections.observableArrayList(
					//"갤럭시s1,","갤럭시s2,","갤럭시s3,","갤럭시s4,","갤럭시s5,","갤럭시s6,","갤럭시s7,"
					strList);
		
		listView.setItems(list);
		
		// listView 에 선택 항목을 지정 - 0번째 인덱스 번호를 선택항목으로 지정
		
		// listView에 선택 항복 변경 감지
		listView.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(
					ObservableValue<? extends Number> observable, 
					Number oldValue, 
					Number newValue) {
				// 선택된 항목 중 사용자가 선택한 항목이 새로 변경이 되었을 때
				// 변경된 index 번호를 전달
				System.out.println(oldValue);
				System.out.println(newValue);	
				int index = newValue.intValue();
				System.out.println(list.get(index));
				tableView.getSelectionModel().select(index);
				tableView.scrollTo(index);
			}
		});
		
		// tableView - 항목 초기화 - PhoneVO
		// tableView List정보를 저장할 List 생성
		ObservableList<PhoneVO> phoneList = FXCollections.observableArrayList();
		// phone01.png ~ phone07.png
		for(int i = 1; i <= 7; i++) {
			PhoneVO phone = new PhoneVO("갤럭시S"+i,"phone0"+i+".png");
			phoneList.add(phone);
		}
		System.out.println(phoneList);
		/*
		TableColumn<PhoneVO,?> tColumnName = tableView.getColumns().get(0);
		tColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
		
		TableColumn<PhoneVO, ?> tColumnPath = tableView.getColumns().get(1);
		tColumnPath.setCellValueFactory(new PropertyValueFactory<>("path"));
		*/
		
		Field[] fields = PhoneVO.class.getDeclaredFields();
		
		for(int i = 0; i < fields.length; i++) {
			String fieldName = fields[i].getName();
			System.out.println(fieldName);
			TableColumn<PhoneVO,?> tColumn = tableView.getColumns().get(i);
			tColumn.setCellValueFactory(new PropertyValueFactory<>(fieldName));
		}
		
		tableView.setItems(phoneList);
	
		// tableView에 항복 변경 감지 Listener 추가
		// 선택 된 항목을 관리하는 Model class
		// JavaFx에서 다양한 데이터를 관리하기 위해 모델 이라는 개념을 도입
		// Model은 동적으로 조작하는 데이터를 관리하기 위한 객체
		tableView.getSelectionModel()
		.selectedItemProperty()
		.addListener(new ChangeListener<PhoneVO>() {

			@Override
			public void changed(
					ObservableValue<? extends PhoneVO> observable, 
					PhoneVO oldValue, 
					PhoneVO newValue) {
				System.out.println(oldValue);
				System.out.println(newValue);
				String path = "/images/"+newValue.getPath();
				URL url = getClass().getResource(path);
				imageView.setImage(new Image(url.toString()));
				// 이름 정보 수정을 위해서
				// TextField의 값을 선택된 행의 name필드 정보로 변경
				txtName.setText(newValue.getName());
			}
			
		});
		
		// tableView에서 항목을 선택 하면 동일학몽의 listView항목도 같이 선택되게
		// listener 추가
		tableView.getSelectionModel().selectedIndexProperty().addListener((onj,o,n)->{
			int index = n.intValue();
			listView.getSelectionModel().select(index);
			listView.scrollTo(index); // 해당되는 위치를 스크롤 최상단으로 이동
		});
		
		// UPDATE Button Actionn event
		// TextField에 수정된 이름으로 스마트폰 이름 변경
		btnUpdate.setOnAction(e->{
			String name = txtName.getText();
			
			PhoneVO phone = tableView.getSelectionModel().getSelectedItem();
			System.out.println(phone);
			
			int index = tableView.getSelectionModel().getSelectedIndex();
			System.out.println(index);
			
			// ListView list
			list.set(index, name);
			
			// TableView phoneList
			phone.setName(name);

			System.out.println(list);
			System.out.println(phoneList);

			tableView.refresh();
			listView.refresh();
		});
		
	}

}
