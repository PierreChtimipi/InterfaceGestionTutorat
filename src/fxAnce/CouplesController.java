package fxAnce;
import entities.Student;
import fr.ulille.but.sae2_02.graphes.Arete;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;

public class CouplesController {
	
	// Class Attributes
	@FXML
    Canvas canva3;
	@FXML
    Canvas canva4;
	@FXML
	Label score;
	@FXML
	ListView<Arete<Student>> listView;
	@FXML
	HBox hbox;
	@FXML
	Label label;
	@FXML
	Label noteTuteur;
	@FXML
	Label noteTutore;
	@FXML
	Label matiereTuteur;
	@FXML
	Label matiereTutore;
	@FXML
	Label anneeTuteur;
	@FXML
	Label anneeTutore;
	
	// Initialize
    public void initialize() {
    	if(MainController.af.getCouples() != null) {
    		listView.getItems().addAll(MainController.af.getCouples().getAffectation());
    	}
    }
    
    
    // Methods
 
    public void updateDetails() {
    	
    	noteTuteur.setText(listView.getSelectionModel().getSelectedItem().getExtremite1().getGrade()+"");
    	noteTutore.setText(""+listView.getSelectionModel().getSelectedItem().getExtremite2().getGrade());
    	
    	matiereTuteur.setText(listView.getSelectionModel().getSelectedItem().getExtremite1().getSubject().getSubjectName());
    	matiereTutore.setText(listView.getSelectionModel().getSelectedItem().getExtremite2().getSubject().getSubjectName());
    	
    	anneeTuteur.setText(listView.getSelectionModel().getSelectedItem().getExtremite1().getYear()+"");
    	anneeTutore.setText(listView.getSelectionModel().getSelectedItem().getExtremite2().getYear()+"");
    	
    	GraphicsContext gc1 = canva3.getGraphicsContext2D();
        Image image1 = new Image("file:/home/choukhis/eclipse-workspace/SAEJava/res/toile_Araignee_Competences.jpg", canva3.getHeight(), canva3.getWidth(), true, true);
        gc1.drawImage(image1, 0, 0);
        
        GraphicsContext gc2 = canva4.getGraphicsContext2D();
        Image image2 = new Image("file:/home/choukhis/eclipse-workspace/SAEJava/res/toile_Araignee_Competences.jpg", canva4.getHeight(), canva4.getWidth(), true, true);
        gc2.drawImage(image2, 0, 0);

    }
	
}
