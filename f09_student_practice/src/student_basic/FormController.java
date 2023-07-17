package student_basic;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.StudentVO;

public class FormController {
	
	@FXML private TextField txtName, txtKor, txtMath, txtEng;
	@FXML private Button btnSave, btnCancel;
	

	// tableView의 리스트 정보 , 현재 생성된 Form stage 정보
	public void init(ObservableList<StudentVO> list, Stage formStage) {
		System.out.println(list);
		btnCancel.setOnAction(e->{
			formStage.close();
		});
		
		
		// 초기화 시점에 작성된 내용이 없을때 값을 읽어와 활용하는 경우 작성된 내용을 쓸수 없음
		// String name = txtName.getText();
		
		btnSave.setOnAction(e->{
			// 저장 버튼 선택 시 TextField에 작성된 내용으로
			// 학생 정보를 List에 추가하여 tableView에 반영
			
			// 이벤트가 발생한 시점에 작성된 내용 읽기
			String name = txtName.getText();
			String strKor = txtKor.getText();
			String strMath = txtMath.getText();
			String strEng = txtEng.getText();
			
			StudentVO vo = new StudentVO();
			int kor = Integer.parseInt(strKor);
			int math = Integer.parseInt(strMath);
			int eng = Integer.parseInt(strEng);
			
			vo.setName(name);
			vo.setKor(kor);
			vo.setMath(math);
			vo.setEng(eng);
			// tableView list 목록에 학생 정보 추가
			list.add(vo);
			
			txtName.clear();
			txtKor.clear();
			txtMath.clear();
			txtEng.clear();
			// txtName field에 포커스 요청
			txtName.requestFocus();
			
			
		});
	}
	
}


