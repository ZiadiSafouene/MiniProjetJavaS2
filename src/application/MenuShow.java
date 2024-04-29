package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MenuShow {
	@FXML 
	private VBox myVbox=new VBox();
	@FXML 
	private Text title=new Text();
	@FXML 
	private Text description=new Text();
	
	public void resize(ActionEvent e) {
		myVbox.setVisible(!myVbox.isVisible());
		if (myVbox.isVisible()) {
			title.setLayoutX(150);
			title.setLayoutY(206);
			description.setLayoutX(239);
			description.setLayoutY(372);
		}
		else {
			title.setLayoutX(88);
			title.setLayoutY(209);
			description.setLayoutX(183);
			description.setLayoutY(409);
			
		}
		
	}

}
