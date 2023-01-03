package fxAnce;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class ErrorController {
	
	@FXML
	TextArea error;
	
	static String message = "";
	
	public void initialize() {
		error.setText(message);
	}
}
