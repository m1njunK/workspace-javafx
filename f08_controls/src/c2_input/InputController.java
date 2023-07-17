package c2_input;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

public class InputController implements Initializable {

	@FXML private TextField txtTitle;
	@FXML private PasswordField txtPass;
	@FXML private ColorPicker colorPicker;
	@FXML private ComboBox<String> comboPublic;
	@FXML private DatePicker datePicker;
	@FXML private TextArea txtContent;
	@FXML private Button btnReg, btnCancel;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		btnReg.setOnAction(e->{
			// TextxField, PasswordField, TextArea
			// 와 같이 텍스트를 작성하는 입력 요소는 getText() 로 값을 읽음
			String titleText = txtTitle.getText();
			System.out.println("제목 : " + titleText);
			
			String passText = txtPass.getText();
			System.out.println("비밀번호 : " + passText);
			
			String content = txtContent.getText();
			System.out.println("content : " + content);
			
			// ColorPicker - RGBA(Red,Green,Blue,Alpha or Brightness)
			// defalut - 기본값은 ff/ff/ff/ff - 흰색
			// color 객체 - 색상에 대한 정보를 저장하는 객체
			// 색상을 이용하는 요소에서 다양하게 사용
			Color color = colorPicker.getValue();
			System.out.println("color : " + color);
			System.out.println("RED : " + color.getRed());
			System.out.println("BLUE : " + color.getBlue());
			System.out.println("Green : " + color.getGreen());
			System.out.println("Alpha : " + color.getBrightness());
			// Color(red,green,blue,opacity==alpha==Brightness)
			// 0~1.0
			color = new Color(1, 0, 0, 1);
			System.out.println(color);
			
			// ComboBox - 지정된 목록에서 이 벤트가 발생한 시점에 사용자가
			// 선택한 항목의 값을 반환
			String comboData = comboPublic.getValue();
			System.out.println("comboBox : " + comboData);
			
			// DatePicker - 시분초를 제외한 날짜 정보를 제공하는 control
			// LocalDate type으로 반환
			LocalDate date = datePicker.getValue();
			System.out.println(date);
		});
		
		// 취소 버튼
		btnCancel.setOnAction(e->{
			// 기존 값을 새로운 텍스트로 대체 하거나 삭제
			txtTitle.setText(null);
			txtPass.setText("");
			txtContent.clear();
			
			// 콤보 박스의 선택 목록
			ObservableList<String> oldList = comboPublic.getItems();
			System.out.println(oldList);
			ObservableList<String> newList = FXCollections.observableArrayList(
					"공개","비공개","조금만공개"
			);
			comboPublic.setItems(newList);
			colorPicker.setValue(new Color(1,1,1,1));
			datePicker.setValue(null);
			// 제목 텍스트 필드에 포커스
			txtTitle.requestFocus();
		}); // end btnCancel
		
		// TextFiled가 포커스된 상태에서 keyboard에 키가 눌러졌을때
		txtTitle.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				KeyCode code = event.getCode();
				// 사용자가 입력한 keyboard의 key code 정보
				if(code == KeyCode.ENTER) {
					// 패스워드 입력란에 포커스
					txtPass.requestFocus();
				}
			}
		}); // txtTitle keyPressed end
	
		
		// new ChangeListener<String>(){public void handle(target,oldValue,newValue){}}
		txtPass.textProperty().addListener((t,o,n)->{
			// txtPass에 값이 작성 되면 textArea에 작성된 내용을 추가
//			txtContent.setText(n+"\n");
			txtContent.appendText(n+"\n");
		});
		
		txtPass.setOnKeyPressed(e->{
			if(e.getCode() == KeyCode.ENTER) {
				// cancel 버튼에- action event를 생성
				btnCancel.fire();
			}
		});
		
	} // end Main	 
	

}
