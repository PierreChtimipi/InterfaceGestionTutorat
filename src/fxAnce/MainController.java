package fxAnce;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import entities.Affectation;
import entities.Guardian;
import entities.Subject;
import entities.Tutee;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.PopupControl;
import javafx.scene.control.Slider;
import javafx.scene.control.Spinner;
import javafx.scene.control.TabPane;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import main.Main;

public class MainController {

	@FXML
	CheckBox jg;
	@FXML
	CheckBox jt;
	@FXML
	CheckBox bg;
	@FXML
	CheckBox bt;
	@FXML
	CheckBox gg;
	@FXML
	CheckBox gt;
	@FXML
	CheckBox cg;
	@FXML
	CheckBox ct;
	@FXML
	Spinner<Double> minGuardian;
	@FXML
	Spinner<Double> minTutee;
	@FXML
	Spinner<Double> maxGuardian;
	@FXML
	Spinner<Double> maxTutee;
	@FXML
	CheckBox y2;
	@FXML
	CheckBox y3;
	@FXML
	Button appliquerFiltres;
	@FXML
	Slider moyenne;
	@FXML
	Slider matiere;
	@FXML
	Slider annee;
	@FXML
	Label labelMoyenne;
	@FXML
	Label labelMatiere;
	@FXML
	Label labelAnnee;
	@FXML
	ListView<Guardian> guardiansLv;
	@FXML
	ListView<Tutee> tuteesLv;
	@FXML
	Label guardian;
	@FXML
	Label tutee;
//	@FXML
//	Button forcer;
//	@FXML
//	Button eviter;
	
	
	// Class Attributes
    Stage root;
    String path;
    
    static Affectation af;
    List<Guardian> guardiansCopy = new ArrayList<>();
    List<Tutee> tuteesCopy = new ArrayList<>();
    
    int guardianChoosenYear;
    double guardianMin = 0;
    double guardianMax = 20;
    
    double tuteeMin = 0;
    double tuteeMax = 20;
    
    int ponderationMoyenne = 50;
    int ponderationMatiere = 50;
    int ponderationAnnee = 50;
    
	
	public void initialize() {
		af = new Affectation(new ArrayList<>(guardiansCopy), new ArrayList<>(tuteesCopy));
		
		labelMoyenne.setText("Moyenne : " + (int)moyenne.getValue());
		labelMatiere.setText("Matière : " + (int)matiere.getValue());
		labelAnnee.setText("Année : " + (int)annee.getValue());
	}
	
	public void onMoyenneChanged() {
		ponderationMoyenne = (int)moyenne.getValue();
		labelMoyenne.setText("Moyenne : " + ponderationMoyenne);
	}
	
	public void onMatiereChanged() {
		ponderationMatiere = (int)matiere.getValue();
		labelMatiere.setText("Matière : " + ponderationMatiere);
	}
	
	public void onAnneeChanged() {
		ponderationAnnee = (int)annee.getValue();
		labelAnnee.setText("Année : " + ponderationAnnee);
	}
	
	
	// Filtering
	public void onGuardianCheckedSubject(ActionEvent e) {
		guardiansFilter();	
	}
	
	public void onTuteeCheckedSubject(ActionEvent e) {
		tuteesFilter();
	}
	
	public void onGuardianCheckedYear(ActionEvent e) {
		CheckBox checkBoxTarget = (CheckBox) e.getTarget();
		guardianChoosenYear = Integer.parseInt(checkBoxTarget.toString().substring(13, 14));
		
		guardiansFilter();
	}
	
	public void onGuardianSpinnerTextAreaChanged(KeyEvent e) {

		if(e.getTarget().equals(minGuardian)) {
			guardianMin = minGuardian.getValue();
		} else if(e.getTarget().equals(maxGuardian)) {
			guardianMax = maxGuardian.getValue();
		}
		
		guardiansFilter();
	}
	
	public void onTuteeSpinnerTextAreaChanged(KeyEvent e) {
		
		if(e.getTarget().equals(minTutee)) {
			tuteeMin = minTutee.getValue();
		} else if(e.getTarget().equals(maxTutee)) {
			tuteeMax = maxTutee.getValue();
		}
		
		tuteesFilter();
	}
	
	public void onGuardianSpinnerChanged(MouseEvent e) {
		
		if(minGuardian.getChildrenUnmodifiable().contains(e.getTarget())) {
			guardianMin = minGuardian.getValue();
		} else if(maxGuardian.getChildrenUnmodifiable().contains(e.getTarget())) {
			guardianMax = maxGuardian.getValue();
		}

		guardiansFilter();
	}
	
	public void onTuteeSpinnerChanged(MouseEvent e) {
		
		if(minTutee.getChildrenUnmodifiable().contains(e.getTarget())) {
			tuteeMin = minTutee.getValue();
		} else if(maxTutee.getChildrenUnmodifiable().contains(e.getTarget())) {
			tuteeMax = maxTutee.getValue();
		}
		
		tuteesFilter();
	}
	
	public void applyFilter() {
		if(af != null) {
			guardiansFilter();
			tuteesFilter();
		}
    }
	
	// Imports && Exports
    public void openFile() {
        final FileChooser fileChooser = new FileChooser();
        configuringFileChooser(fileChooser);
        //TODO Gérer le non retour de fichier dans le cas ou on annule l'import;
        
        File file = fileChooser.showOpenDialog(root);
        
        if(file != null) {
        	af = Main.loadAffectation(file.toString());
        	guardiansCopy.addAll(af.getGuardians());
        	tuteesCopy.addAll(af.getTutees());
        }
        
        if(guardiansLv.getItems().isEmpty()) addGuardiansToListView(guardiansCopy);
        if(tuteesLv.getItems().isEmpty()) addTuteesToListView(tuteesCopy);
    }
    
    public void saveFile() {

    	FileChooser fileChooser = new FileChooser();
    	
    	 // Set title for FileChooser
        fileChooser.setTitle("Select JSON file");

        // Set Initial Directory
        fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
    	 
        //Set extension filter for text files
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("json", "*.json");
        fileChooser.getExtensionFilters().add(extFilter);

        //Show save file dialog
        File file = fileChooser.showSaveDialog(root);

        if (file != null) {
            Main.save(af.JSONAffectation(), file.getPath());
        }
    }

    // Creation
    
    public void addStudent() {
    	FXMLLoader loader = new FXMLLoader();
        URL fxmlFileUrl = getClass().getResource("PopUpCreationEtudiant.fxml");
        if (fxmlFileUrl == null) {
                System.out.println("Impossible de charger le fichier fxml");
                System.exit(-1);
        }
        
        loader.setLocation(fxmlFileUrl);
        
       
        Parent root;
		try {
			root = loader.load();
			Scene scene = new Scene(root);
	        Stage stage = new Stage(StageStyle.UNDECORATED);
	        stage.initModality(Modality.APPLICATION_MODAL);
	        stage.setScene(scene);
	        stage.setTitle("FXML demo");
	        stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		guardiansLv.getItems().clear();
		addGuardiansToListView(af.getGuardians());
		
		tuteesLv.getItems().clear();
		addTuteesToListView(af.getTutees());
    }
    
    // Affectation
    
    public void assignement() {
    	af.assignement();
    	openCouplesView();
    }
    
    public void forcedAssignement() {
    	af.assignement(guardiansLv.getSelectionModel().getSelectedItem(), tuteesLv.getSelectionModel().getSelectedItem(), true);
    	openCouplesView();
    }
    
    public void avoidedAssignement() {
    	af.assignement(guardiansLv.getSelectionModel().getSelectedItem(), tuteesLv.getSelectionModel().getSelectedItem(), true);
    	openCouplesView();
    }
    
    // Internal Methods
    
    private void openCouplesView() {
    	af.generateCouples();
    	
    	FXMLLoader loader = new FXMLLoader();
        URL fxmlFileUrl = getClass().getResource("Couples.fxml");
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
    
    private void guardiansFilter() {
    	if(af != null) {
    		
	    	af.setGuardians(new ArrayList<>(guardiansCopy));
	    	
	    	af.guardiansFilter(guardianMin, true, Subject.NULL, false, 1, true);
	    	af.guardiansFilter(guardianMax, false, Subject.NULL, false, 1, true);
	    	
	    	if(!y2.isSelected() && y3.isSelected()) af.guardiansFilter(0, true, Subject.NULL, false, 3, true);
	    	
	    	if(y2.isSelected() && !y3.isSelected()) {
	    		af.guardiansFilter(0, true, Subject.NULL, false, 2, true);
	    		af.guardiansFilter(0, true, Subject.NULL, false, 3, false);
	    	}
	    	
	    	if(!y2.isSelected() && !y3.isSelected()) af.guardiansFilter(0, true, Subject.NULL, true, 1, true);
	
	    	if(!jg.isSelected()) af.guardiansFilter(0, true, Subject.JAVA, false, 2, true);
	    	if(!bg.isSelected()) af.guardiansFilter(0, true, Subject.BDD, false, 2, true);
	    	if(!gg.isSelected()) af.guardiansFilter(0, true, Subject.GRAPHES, false, 2, true);
	    	if(!cg.isSelected()) af.guardiansFilter(0, true, Subject.LANGAGEC, false, 2, true);
	    	
	    	
	    	guardiansLv.getItems().clear();
	    	addGuardiansToListView(af.getGuardians());
	    	
    	}
    }
    
    public void updateAffectation() {
    	if(guardiansLv.getSelectionModel().getSelectedItem() != null)
    		guardian.setText("Tuteur : " + guardiansLv.getSelectionModel().getSelectedItem().toString());
    	if(tuteesLv.getSelectionModel().getSelectedItem() != null)
    		tutee.setText("Tutoré : " + tuteesLv.getSelectionModel().getSelectedItem().toString());
    }
    
    private void tuteesFilter() {
    	if(af != null) {
    		
	    	af.setTutees(new ArrayList<>(tuteesCopy));
	    	
	    	af.tuteesFilter(tuteeMin, true, Subject.NULL, false, 2, false);
	    	af.tuteesFilter(tuteeMax, false, Subject.NULL, false, 2, false);
	   
	    	if(!jt.isSelected()) af.tuteesFilter(0, true, Subject.JAVA, false, 2, false);
	    	if(!bt.isSelected()) af.tuteesFilter(0, true, Subject.BDD, false, 2, false);
	    	if(!gt.isSelected()) af.tuteesFilter(0, true, Subject.GRAPHES, false, 2, false);
	    	if(!ct.isSelected()) af.tuteesFilter(0, true, Subject.LANGAGEC, false, 2, false);
	    	
	    	tuteesLv.getItems().clear();
	    	addTuteesToListView(af.getTutees());
	    	
    	}
    }
    
    private void configuringFileChooser(FileChooser fileChooser) {
          // Set title for FileChooser
          fileChooser.setTitle("Select JSON file");

          // Set Initial Directory
          fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));

          // Add Extension Filters
          fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("json", "*.json"));
    }
    
    private void addGuardiansToListView(List<Guardian> guardians) {
    	//TODO Proposer save si liste non vide, sinon ecraser liste actuelle
    	for(Guardian g : guardians) {
    		if(!g.getFullName().equals("null null")) guardiansLv.getItems().add(g);
    	}
    } 
    
    private void addTuteesToListView(List<Tutee> tutees) {
    	for(Tutee t : tutees) {
			if(!t.getFullName().equals("null null")) tuteesLv.getItems().add(t);
    	}
    }

}
