package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class RootController implements Initializable{
	@FXML private ComboBox<String> comboBox;
	@FXML private TextArea textArea;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		comboBox.getSelectionModel().selectedIndexProperty().addListener((target,o,n)->{
			int index = n.intValue();
			System.out.println("select comboBox : " + index);
			
			switch(index) {
			case 0 :
				// directory chooser
				// javaFX에서 제공하는 폴더 선택 상자
				DirectoryChooser chooser = new DirectoryChooser();
				chooser.setTitle("디렉토리 선택");
				// 오픈 기본 디렉토리 내 PC
				
				// 현재 프로젝트에 src폴더
				// chooser.setInitialDirectory(new File("src"));
				chooser.setInitialDirectory(new File("C:\\Temp"));
				chooser.showDialog(primaryStage);
				// showDialog - directory 선택 완료 시 또는 취소 시 까지 blocking
				// dialog 창은 부모창에 부가적인 정보를 추가하는 창으로 창의 기능이 다하고 나면
				// 소유권을 전달할 부모 창(stage)에 대한 정보가 전달되어야 함.
				File selectedDir = chooser.showDialog(primaryStage);
				System.out.println(selectedDir);
				if(selectedDir != null) {
					System.out.println(selectedDir.isDirectory());
					for(File f : selectedDir.listFiles()) {
						textArea.appendText(f.getName()+"\n");
					}
				}
				break;
			case 1 :
				// file chooser - file 선택 상자
				FileChooser fileChooser = new FileChooser();
				fileChooser.setInitialDirectory(new File("C:\\Temp"));
				fileChooser.setTitle("로드할 문서를 선택해주세요!");
				ObservableList<ExtensionFilter> filters = fileChooser.getExtensionFilters();
				filters.addAll(
						new ExtensionFilter("Text Files", "*.txt", "*.hwp","*.pdf"),
						new ExtensionFilter("Image Filters","*.png","*.jpg",".jpeg","*.gif"),
						new ExtensionFilter("All Files","*.*")
				);
				// 파일을 선택창이 종료되기 전까지 blocking
				// 종료가 되면 소유권을 전달할 스테이지 정보 전달
				// File selectedFile = fileChooser.showOpenDialog(primaryStage);
				
				File selectedFile = fileChooser.showSaveDialog(primaryStage);
				if(selectedFile != null) {
					System.out.println(selectedFile.isFile());
					System.out.println(selectedFile.getAbsolutePath());
				}
				
				// 여러 파일을 선택 할 수 있는 dialog
				List<File> selectedFiles = fileChooser.showOpenMultipleDialog(primaryStage);
				System.out.println(selectedFiles);
				
				break;
				
			case 2 :
				// popup window
				handlePopUp();
				break;
				
			case 3 :
				// custom window
				handleCustom();
				break;
			}
		});
	}
	
	private void handleCustom() {
		Stage stage = new Stage();
		// DECORATED - 기본 값 - 일반 적인 윈도우 스타일(제목줄 장식-아이콘,타이틀,축소,최대화면,닫기)
		stage = new Stage(StageStyle.DECORATED);
		// 흰배경에 제목줄 x
		stage = new Stage(StageStyle.UNDECORATED);
		// 흰배경, 제목줄에 타이틀 종료 버튼만 존재
		stage = new Stage(StageStyle.UTILITY);
		// 
		// stage.initStyle(StageStyle.TRANSPARENT);
		
		stage.setTitle("Custom Dialog");
		// 소유자 창 지정 - stage의 기능이 종료되면 돌아갈 소유자창 지정
		// stage.initOwner(primaryStage);
		// WINDOW_MODAL, APPLICATION_MODAL
		// WINDOW_MODAL 소유자의 창이 지정되어 있을 경우 소유권을 이동하지 않음
		// APPLICATION_MODAL 전체 애플리케이션에서 소유자 창이 없어도 소유권을 이동하지 않음
		stage.initModality(Modality.APPLICATION_MODAL);
		Parent parent = null;
		try {
			parent = FXMLLoader.load(getClass().getResource("/main/Custom.fxml"));
		} catch (IOException e) {}
		
		ObservableList<Node> childrens = ((VBox)parent).getChildren();
		for(Node n : childrens) {
			Button btn = (Button) n;
			String text = btn.getText();
			System.out.println(text);
			btn.setOnAction(e->{
				handleAlertDialog(text);
			});
		}
		
		
		
		Scene scene = new Scene(parent);
		scene.setFill(Color.TRANSPARENT);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.show();

	}

	private void handleAlertDialog(String text) {
		System.out.println(text);
		switch(text) {
			case "알림1" :
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle(text);
				alert.setHeaderText("알림창 입니다.\n헤더정보입니다.");
				alert.setContentText("이곳은 컨텐츠 정보입니다.\n내용을 확인하세요.");
				alert.show();
				break;
			case "경고2" :
				alert = new Alert(AlertType.WARNING);
				alert.setTitle(text);
				alert.setHeaderText("경고 창!");
				alert.setContentText(null);
				alert.show();
				break;
			case "오류3" :
				alert = new Alert(AlertType.ERROR);
				alert.setTitle(text);
				alert.setHeaderText(null);
				alert.setHeaderText("오류 창!");
				alert.show();
				break;
			case "확인4" :
				alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("결제 확인");
				alert.setHeaderText("구매하실 상품의 결제 정보입니다.");
				alert.setContentText("확인 시 5,000,000원 결제 됩니다.");
				Optional<ButtonType> result = alert.showAndWait();
				// ButtonType.
				if(result.get() == ButtonType.OK) {
					System.out.println("결제 완료");
				}else if(result.get() == ButtonType.CANCEL) {
					// cancel 버튼 + 닫기 버튼 + alt f4
					System.out.println("결제 취소");
				}
				break;
				
			case "입력5" :
				// 간단한 문자열을 입력 받을 수 있는 창
				TextInputDialog dialog = new TextInputDialog("이수석");
				dialog.setTitle(text);
				dialog.setHeaderText(null);
				dialog.setContentText("이름을 입력해주세요");
				Optional<String> res = dialog.showAndWait();
				// 확인 버튼을 선택 true , 취소 면 false
				if(res.isPresent()) {
					String name = res.get();
					System.out.println("name : " + name);
				}else {
					System.out.println("취소");
				}
				break;
			case "Custom6" :
				alert = new Alert(AlertType.NONE,"이곳은 content 정보입니다.\n내용을 확인하세요",
						ButtonType.OK,
						ButtonType.CANCEL,
						ButtonType.NEXT,
						ButtonType.PREVIOUS
						);
				alert.setTitle(text);
				alert.setHeaderText(null);
				Optional<ButtonType> result1 = alert.showAndWait();
				if(result1.get() == ButtonType.OK) {
					System.out.println("확인");
				}else if(result1.get() == ButtonType.NEXT) {
					System.out.println("다음");
				}
				// ...
				break;
		}
	}

	private void handlePopUp() {
		Popup popup = new Popup();
		
		Parent parent = null;
		
		try {
			parent = FXMLLoader.load(
					getClass().getResource("PopUp.fxml")
					);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		popup.setAutoHide(false);
		
		ImageView imageView = (ImageView) parent.lookup("#imgMessage");
		imageView.setOnMouseClicked(event->{
			System.out.println("image mouse click!");
			popup.hide();
		} );
		
		Label lblMessage = (Label) parent.lookup(".lblMessage");
		lblMessage.setText("메세지 알림");
		
		popup.getContent().add(parent);
		popup.show(primaryStage);
		
	}

	private Stage primaryStage;
	
	
	public void setStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}
	
	public void handleNew() throws IOException {
		System.out.println("New!");
		Runtime rt = Runtime.getRuntime();
		//rt.exec("calc");
		rt.exec("notepad");
	}
	
	// fileChooser를 이용하여 선택된 txt 파일의 정보를 읽어
	// textArea에 출력
	
	public void handleOpen() throws IOException {
		System.out.println("Open!");
		textArea.appendText("Open\n");
		// 파일 선택창
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().add(new ExtensionFilter("Text file", "*.txt"));
		fileChooser.setInitialDirectory(new File("C:\\temp"));
		File selectedFile = fileChooser.showOpenDialog(primaryStage);
		if(selectedFile != null) {
			textArea.appendText(selectedFile.getPath() + "\n");
			FileReader reader = new FileReader(selectedFile);
			BufferedReader bReader = new BufferedReader(reader);
			// 파일 추저로 선택된 txt 파일의 텍스트 내용을
			// textArea에 추가로 작성
			
			String message = null;
			while((message = bReader.readLine()) != null) {
				textArea.appendText(message+"\n");
			}
			bReader.close();
			
		}
		
	}
	
	public void handleSave() throws IOException {
		System.out.println("Save!");
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().add(new ExtensionFilter("text files", "*.txt"));
		File selectedFile = fileChooser.showSaveDialog(primaryStage);
		if(selectedFile != null) {
			
			System.out.println(selectedFile.getPath());
			
			FileWriter writer = new FileWriter(selectedFile,true);
			BufferedWriter bWriter = new BufferedWriter(writer);
			String message = textArea.getText();
			bWriter.write(message);
			bWriter.flush();
			bWriter.close();
		}
	}
	
	public void handleClose() {
		System.out.println("Close!");
	}
	
}
