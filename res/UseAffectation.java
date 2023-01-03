package main;

import java.util.ArrayList;
import java.util.List;

import entities.Affectation;
import entities.Guardian;
import entities.Student;
import entities.Subject;
import entities.Tutee;
import fr.ulille.but.sae2_02.graphes.CalculAffectation;
import fr.ulille.but.sae2_02.graphes.GrapheNonOrienteValue;

public class UseAffectation {
	
	public static void main(String[] args) {
		
					// INITILISATION //
		
		
		/*Instanciation des étudiants*/
		Guardian sophie = new Guardian("Vallée", "Sophie", 15.8, 2, Subject.LANGAGEC);
		Guardian edouard = new Guardian("Augé", "Édouard", 13.9, 3, Subject.JAVA);
		Guardian olivier = new Guardian("Gallet", "Olivier", 11.3, 3, Subject.BDD);
		Guardian juliette = new Guardian("Traore", "Juliette", 9.8, 2, Subject.GRAPHES);
		Guardian nul = new Guardian();
		
		Tutee valerie = new Tutee("Jacob", "Valérie", 4.7, 1, Subject.JAVA);
		Tutee martin = new Tutee("Tanzi", "Martin", 10.1, 1, Subject.LANGAGEC);
		Tutee eleonore = new Tutee("Guillou", "Éléonore", 13.1, 1, Subject.BDD);
		Tutee odette = new Tutee("Lacroix", "Odette", 15.7, 1, Subject.GRAPHES);
		Tutee amelie = new Tutee("Poulain", "Amélie", 17.9, 1, Subject.LANGAGEC);
		
		/*Instanciation des listes d'étudiants et du graphe modèle*/
		
		List<Guardian> guardians = new ArrayList<Guardian>();
		List<Tutee> tutees = new ArrayList<Tutee>();
		
		guardians.add(sophie);
		guardians.add(edouard);
		guardians.add(olivier);
		guardians.add(juliette);
		guardians.add(nul);
		
		tutees.add(valerie);
		tutees.add(martin);
		tutees.add(eleonore);
		tutees.add(odette);
		tutees.add(amelie);
		
		GrapheNonOrienteValue<Student> modelGraph = new GrapheNonOrienteValue<>();
		
		
					// MISES EN SITUATIONS //
		
		Affectation affectation = new Affectation(guardians, tutees);
		
		
		/*Graphe sans particularité*/
		
		affectation.assignement(modelGraph);
		
		System.out.println(modelGraph);
		
		CalculAffectation<Student> affectationCalcul = new CalculAffectation<Student>(modelGraph, affectation.getGuardians2(), affectation.getTutees2());
		
		for (int i=0;i<5;i++) {
			System.out.println(affectationCalcul.getAffectation().get(i).getExtremite1()+
					" s'occupera de "+affectationCalcul.getAffectation().get(i).getExtremite2());
		}
		
		/*Graphe avec Fixation affectation*/
		
		modelGraph = affectation.assignement(sophie, amelie, true);
		affectationCalcul = new CalculAffectation<>(modelGraph, affectation.getGuardians2(), affectation.getTutees2());
		
		System.out.println("\nFixation affectation : sophie et amélie\n\n" + modelGraph);
	
		for (int i=0;i<5;i++) {
			System.out.println(affectationCalcul.getAffectation().get(i).getExtremite1()+
					" s'occupera de "+affectationCalcul.getAffectation().get(i).getExtremite2());
		}
		
		modelGraph = affectation.assignement(sophie, amelie, false);
		affectationCalcul = new CalculAffectation<>(modelGraph, affectation.getGuardians2(), affectation.getTutees2());
		
		System.out.println("\nNON Fixation affectation : sophie et amélie\n\n" + modelGraph);
	
		for (int i=0;i<5;i++) {
			System.out.println(affectationCalcul.getAffectation().get(i).getExtremite1()+
					" s'occupera de "+affectationCalcul.getAffectation().get(i).getExtremite2());
		}
		
		/*Graphe avec Pondération des critères d'affectation*/
		
		//modelGraph = affectation.assignement(false);
		affectationCalcul = new CalculAffectation<Student>(modelGraph, affectation.getGuardians2(), affectation.getTutees2());
		
		System.out.println("\nPondération des critères d'affectation : pas de priorité aux 3ème années\n\n" + modelGraph);
		
		for (int i=0;i<5;i++) {
			System.out.println(affectationCalcul.getAffectation().get(i).getExtremite1()+
					" s'occupera de "+affectationCalcul.getAffectation().get(i).getExtremite2());
		}
		
		/*Graphe pour Éviter d'affecter un candidat*/
		
		modelGraph = affectation.assignement(valerie);
		affectationCalcul = new CalculAffectation<Student>(modelGraph, affectation.getGuardians2(), affectation.getTutees2());
		
		System.out.println("\nÉviter d'affecter un candidat : valérie\n\n" + modelGraph);
		
		for (int i=0;i<5;i++) {
			System.out.println(affectationCalcul.getAffectation().get(i).getExtremite1()+
					" s'occupera de "+affectationCalcul.getAffectation().get(i).getExtremite2());
		}
		
	}
}
