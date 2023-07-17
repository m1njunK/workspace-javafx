package application;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

public class ButtonActionEventHandler implements EventHandler<ActionEvent>{

	@Override
	public void handle(ActionEvent event) {
		System.out.println(event);
		// 이벤트가 발생할 요소
		Button button = (Button) event.getTarget();
		System.out.println("Button text : " + button.getText());
	}
		
}
