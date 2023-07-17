package e1_property_listener;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class RootController implements Initializable{

	@FXML private Slider slider;
	@FXML private ImageView img;
	@FXML private ToggleButton toggle;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		DoubleProperty prop = slider.valueProperty();
		System.out.println(prop);
		double value = prop.doubleValue();
		System.out.println(value);
		// 0.0 ~ 100.0
		// slider의 속성 값의 변경에 따라 이미지의 크기를 변경
		setImageWidth(value);
		
		prop.addListener(new ChangeListener<Number>() {

			@Override
			public void changed(
					ObservableValue<? extends Number> observable, 
					Number oldValue, 
					Number newValue) {
				System.out.println("속성값이 변경된 요소 정보 : " + observable);
				System.out.println("기존 값 : " + oldValue);
				System.out.println("변경된 값 : " + newValue);
				Double value = newValue.doubleValue();
				setImageWidth(value);
			}
		});
		
		prop.addListener((obj,old,newValue)->{
			System.out.println("lambda listener : " + newValue);
		});
		
		toggle.selectedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(
					ObservableValue<? extends Boolean> observable, 
					Boolean oldValue, 
					Boolean newValue) {
				System.out.println(newValue);
				// toggle 버튼이 선택 / 해제 시 ImageView에 이미지 변경
				if(newValue) {
					String path = getClass().getResource("animals/cat3.jpg").toString();
					System.out.println(path);
					Image image = new Image(path);
					img.setImage(image);
				}else {
					Image image = new Image(
							getClass().getResource("animals/dog.jpg").toString()
					);
					img.setImage(image);
				}
			}
			
		});
		
	}
	
	public void setImageWidth(double value) {
		// 0.0 ~ 100.0
		// imageView 가 포함된 container width : 350
		double width = (350/100.0) * value;
		width = (width == 0) ? 1 : width;
		img.setFitWidth(width);
	}
	
}
