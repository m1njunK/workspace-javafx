package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCharacterCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.Mnemonic;
import javafx.stage.Stage;

public class RootController implements Initializable{

	@FXML private Button btn1,btn2,btn3;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// btn1.setMnemonicParsing(true);
		btn1.setText("_A 버튼");
		btn1.setOnAction(e->{
			System.out.println("1번 버튼 선택");
		});
		
		btn2.setText("_S 버튼");
		btn2.setOnAction(e->{
			System.out.println("2번 버튼 선택");
		});
		
		btn3.setText("ss_Oreo");
		btn3.setOnAction(e->{
			System.out.println("3번 버튼 선택");
		});
	}


	public void setPrimaryStage(Stage primaryStage) {
		// alt keyCode 조합이 아닌 단축키 조합 추가
		KeyCombination kc = new KeyCharacterCombination("P",KeyCharacterCombination.ALT_DOWN,KeyCombination.SHIFT_DOWN);
		Mnemonic mnemonic = new Mnemonic(btn1,kc);
		primaryStage.getScene().addMnemonic(mnemonic);
	}
	
}
