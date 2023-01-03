package fxAnce;

import java.io.IOException;
import java.net.URL;

import entities.Guardian;
import entities.Subject;
import entities.Tutee;
import entities.notAGuardianException;
import entities.notATuteeException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ModalController {
	
	@FXML
	ToggleGroup role;
	@FXML
	TextField surname;
	@FXML
	TextField firstname;
	@FXML
	ComboBox<Integer> year;
	@FXML
	TextField grade;
	@FXML
	ComboBox<Subject> subject;
	@FXML
	Button cancel;
	@FXML
	Button add;
	@FXML
	RadioButton guardian;
	@FXML
	RadioButton tutee;
	
	static boolean done = false;
	
	public void initialize() {
		year.getItems().addAll(1, 2, 3);
		subject.getItems().addAll(Subject.JAVA, Subject.BDD, Subject.GRAPHES, Subject.LANGAGEC);
	}
	
	public void createStudent(ActionEvent ev) {
		boolean checked = false;
		int cpt = 0;
		while(!checked && cpt < 1) {
			if(role.getSelectedToggle().equals(tutee))
				try {
					MainController.af.addTutee(new Tutee(surname.getText(), firstname.getText(), grade.getText(), year.getValue(), subject.getValue()));
					checked = true;
				} catch (notATuteeException nate) {
					ErrorController.message = "Il faut être première année pour bénéficier du tutorat...";
					nate.getMessage();
					FXMLLoader loader = new FXMLLoader();
			        URL fxmlFileUrl = getClass().getResource("error.fxml");
			        if (fxmlFileUrl == null) {
			                System.out.println("Impossible de charger le fichier fxml");
			                System.exit(-1);
			        }
			        
			        loader.setLocation(fxmlFileUrl);
			        
			        Parent root;
					try {
						root = loader.load();
						Scene scene = new Scene(root);
				        Stage stage = new Stage();
				        stage.initModality(Modality.APPLICATION_MODAL);
				        stage.setScene(scene);
				        stage.setTitle("FXML demo");
				        stage.show();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			else
				try {
					MainController.af.addGuardian(new Guardian(surname.getText(), firstname.getText(), grade.getText(), year.getValue(), subject.getValue()));
					checked = true;
				} catch (notAGuardianException nage) {
					ErrorController.message = "Seuls les deuxième et troisème année sont autorisée à dispenser de l'aide...";
					nage.getMessage();
					FXMLLoader loader = new FXMLLoader();
			        URL fxmlFileUrl = getClass().getResource("error.fxml");
			        if (fxmlFileUrl == null) {
			                System.out.println("Impossible de charger le fichier fxml");
			                System.exit(-1);
			        }
			        
			        loader.setLocation(fxmlFileUrl);
			        
			       
			        Parent root;
					try {
						root = loader.load();
						Scene scene = new Scene(root);
				        Stage stage = new Stage();
				        stage.initModality(Modality.APPLICATION_MODAL);
				        stage.setScene(scene);
				        stage.setTitle("FXML demo");
				        stage.show();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			cpt++;
		}
		done = true;
		close(ev);
		done = false;
	}
	
	public void close(ActionEvent e) {
		((Node) (e.getSource())).getScene().getWindow().hide();
	}
}
