package bundle;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

public class BundleController implements Initializable {

	@FXML private Button btnAccept, btnReload, btnCancel;

	public BundleController() {};
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		System.out.println(arg0);
		System.out.println(arg1);
		for(String s : arg1.keySet()) {
			System.out.printf("key : %s, value %s %n",s,arg1.getString(s));
		}
	}

}
