package tools;

import entities.Student;
import fr.ulille.but.sae2_02.graphes.CalculAffectation;
import fr.ulille.but.sae2_02.graphes.GrapheNonOrienteValue;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Paint;

public class JavaFxConversion {

	//Class Attributes
	GrapheNonOrienteValue<Student> modelGraph;
	CalculAffectation<Student> affectationCalcul;
	
	//Constructor
	public JavaFxConversion(CalculAffectation<Student> affectationCalcul, GrapheNonOrienteValue<Student> modelGraph) {
		this.affectationCalcul = affectationCalcul;
		this.modelGraph = modelGraph;
	}
	
	public HBox affectationToHBox(int index, HBox hbox) {
		
		Label label = new Label(this.modelGraph.getPoids(this.affectationCalcul.getAffectation().get(index).getExtremite1(), this.affectationCalcul.getAffectation().get(index).getExtremite2())+"");
		
		hbox.getChildren().addAll(label);
		label.setPrefSize(30, 30);
		label.setBackground(Background.fill(Paint.valueOf("red")));
		return hbox;
	}
}
