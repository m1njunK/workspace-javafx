module f08_controls {
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.web;
	requires javafx.media;
	
	opens c1_buttons to javafx.graphics, javafx.fxml;
	// c2_input
	opens c2_input to javafx.graphics, javafx.fxml;
	opens c3_view_controls to javafx.graphics, javafx.fxml;
	opens c4_media_view to javafx.graphics, javafx.fxml;
	opens c5_charts to javafx.graphics, javafx.fxml;
	
	// 기본값으로 모듈은 자신의 API class를 다른 모듈에 노출 시키지 않도록
	// 강력한 캡슐화가 되어있음. - 모듈 시스템이 만들어진 주요 동기 중 하나
	// API가 외부에서 사용 할 수 있게 하려면 명시적으로 공개범위를 지정
	// 모듈 내의 패키지 내부에 모든 public 멤버들을 외부로 노출 시키는 예약어 exports
	exports c3_view_controls;

}
