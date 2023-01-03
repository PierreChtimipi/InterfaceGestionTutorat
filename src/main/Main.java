package main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import entities.Affectation;
import entities.Guardian;
import entities.Student;
import entities.Subject;
import entities.Tutee;
import entities.notAGuardianException;
import entities.notATuteeException;
import tools.KeyboardEntries;

public class Main {
	
	// Static Methods
	public static String save(JSONObject jsonObject, String fileName) {
		fileName += ".json";
		try {
	         Writer fw = new FileWriter(fileName);
	         fw.write(jsonObject.toString());
	         fw.close();
		} catch (IOException e) {
	         e.printStackTrace();
		}
		
		return fileName;
	}
	
	public static Affectation loadAffectation(String path) {
		
		List<String> strings = null;
		
		try {
			strings = Files.readAllLines(Paths.get(path), Charset.forName("utf8"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String jsonString = String.join(" ", strings);
		
		JSONObject jsonObject = new JSONObject(jsonString);
		
		JSONArray guardiansArr = jsonObject.getJSONArray("guardians");
		JSONArray tuteesArr = jsonObject.getJSONArray("tutees");
		
		
		List<Guardian> guardians = new ArrayList<>();
		List<Tutee> tutees = new ArrayList<>();
		
		for (int i = 0; i < guardiansArr.length(); i++) {
			if(!guardiansArr.getJSONObject(i).getString("fullName").equals("null null")) {
				String firstName = guardiansArr.getJSONObject(i).getString("firstName");
				String surname = guardiansArr.getJSONObject(i).getString("surname");
				int year = guardiansArr.getJSONObject(i).getInt("year");
				double grade = guardiansArr.getJSONObject(i).getDouble("grade");
				Subject subject = Subject.valueOf(guardiansArr.getJSONObject(i).getString("subject"));
				
				try {
					guardians.add(new Guardian(surname, firstName, grade, year, subject));
				} catch (notAGuardianException nage) {
					nage.getMessage();
				}
			}
		}
		
		for (int i = 0; i < tuteesArr.length(); i++) {
			
			if(!tuteesArr.getJSONObject(i).getString("fullName").equals("null null")) {
				String firstName = tuteesArr.getJSONObject(i).getString("firstName");
				String surname = tuteesArr.getJSONObject(i).getString("surname");
				int year = tuteesArr.getJSONObject(i).getInt("year");
				double grade = tuteesArr.getJSONObject(i).getDouble("grade");
				Subject subject = Subject.valueOf(tuteesArr.getJSONObject(i).getString("subject"));
				
				try {
					tutees.add(new Tutee(surname, firstName, grade, year, subject));
				} catch (notATuteeException nate) {
					nate.getMessage();
				}
			}
		}
		
		
		return new Affectation(guardians, tutees, jsonObject.getInt("weightingGrade"), jsonObject.getInt("weightingSubject"), jsonObject.getInt("weightingYear"));
		
	}

	static int menu1() {
		System.out.println("\n1. Importer une affectation\n2. Créer une affectation\n");
		System.out.print(sChoice + "(1 ou 2) : ");
		
		choice = KeyboardEntries.readInt();
			if(choice == 1) {
				choice = 0;
				System.out.print("Entrez un nom de fichier(en .json) : ");
				
				path = KeyboardEntries.readString();
				file = new File(path);
				isFile = file.exists() && !file.isDirectory();
				
				while(!isFile && choice != 2) {
					System.out.println("\nFichier inexistant...");
					System.out.println("1. Réessayer\n2. Retour\n");
					System.out.print(sChoice + "(1 ou 2) : ");
					
					choice = KeyboardEntries.readInt();
					
					if(choice == 1) {
						System.out.print("Entrez un nom de fichier(en .json) : ");
						path = KeyboardEntries.readString();
						file = new File(path);
						isFile = file.exists() && !file.isDirectory();
					}
				}
				
				if(choice == 0) {
					return 0;
				} 
				
				else if(choice == 2) {
					return 1;
				}
				
				else if(choice == 1 && isFile) {
					return 0;
				}
				
			} else if (choice == 2) {
				return 2;
			} else if (choice == 4) {
				quit = true;
				return 1;
			}
			
			return 1;
	}

	static int customizingMenu() {
		System.out.println("\n\n1. Ajuster les pondérations\n2. Filter les élèves\n3. Générer les couples\n4. sauvegarder\n5. Menu");
		
		System.out.println(sChoice + "(entre 1 et 5)");
		
		customizingMenuChoice = KeyboardEntries.readInt();
		
		switch (customizingMenuChoice) {
		case 1:
			System.out.println("Pondérations sur 100 :\n\t-des moyennes : " + affectation.getWeightingGrade() + "\n\t-des matières : " + affectation.getWeightingSubject()
				+ "\n\t-de l'année d'étude : " + affectation.getWeightingYear());
			
			System.out.println("entrez 3 pondérations (0 à 100) respectivement pour moyennes, matières et année d'étude : ");
			affectation.setWeightingGrade(KeyboardEntries.readInt());
			affectation.setWeightingSubject(KeyboardEntries.readInt());
			affectation.setWeightingYear(KeyboardEntries.readInt());
			
			System.out.println("Pondérations sur 100 :\n\t-des moyennes : " + affectation.getWeightingGrade() + "\n\t-des matières : " + affectation.getWeightingSubject()
			+ "\n\t-de l'année d'étude : " + affectation.getWeightingYear());

			return 0;
		case 2:
			System.out.println("filtrer les étudiants par moyenne, matière et année");
			
			System.out.println("\nFilter les tuteurs : ");
			System.out.print("moyenne : ");
			double grade = KeyboardEntries.readDouble();
			System.out.print("min/max : ");
			String minOrMax =  KeyboardEntries.readString();
			
			System.out.print("matière : ");
			Subject subject = KeyboardEntries.readSubject();
			System.out.print("incluse/exclue : ");
			String subjectInclusion =  KeyboardEntries.readString();
			
			System.out.print("annee : ");
			int year = KeyboardEntries.readInt();
			System.out.print("min/max : ");
			String yearMinOrMax =  KeyboardEntries.readString();
			
			System.out.println("\nFilter les tutorés : ");
			System.out.print("moyenne : ");
			double tgrade = KeyboardEntries.readDouble();
			System.out.print("min/max : ");
			String tminOrMax =  KeyboardEntries.readString();
			
			System.out.print("matière : ");
			Subject tsubject = KeyboardEntries.readSubject();
			System.out.print("incluse/exclue : ");
			String tsubjectInclusion =  KeyboardEntries.readString();
			
			System.out.print("annee : ");
			int tyear = KeyboardEntries.readInt();
			System.out.print("min/max : ");
			String tyearMinOrMax =  KeyboardEntries.readString();

			boolean averageInclude = false;
			boolean subjectInlcude = false;
			boolean yearInclude = false;
			
			boolean taverageInclude = false;
			boolean tsubjectInlcude = false;
			boolean tyearInclude = false;
			
			if(minOrMax.equals("min")) averageInclude = true;
			if(subjectInclusion.equals("incluse")) subjectInlcude = true;
			if(yearMinOrMax.equals("min")) yearInclude = true;
			
			if(tminOrMax.equals("min")) taverageInclude = true;
			if(tsubjectInclusion.equals("incluse")) tsubjectInlcude = true;
			if(tyearMinOrMax.equals("min")) tyearInclude = true;
			
			affectation.guardiansFilter(grade, averageInclude, subject, subjectInlcude, year, yearInclude);
			affectation.tuteesFilter(tgrade, taverageInclude, tsubject, tsubjectInlcude, tyear, tyearInclude);
			
			System.out.println("Tutuers restants : " + affectation.getGuardians());
			System.out.println("Tutorés restants : " + affectation.getTutees());
			
			return 0;
		case 3:
			System.out.println("Générer les couples : \n1. assignation naturelle\n2. forcer un couple\n3. empêcher un couple\n4. exlcure un étudiant");
			
			System.out.print(sChoice + "(1 à 4) : ");
			int innerChoice = KeyboardEntries.readInt();
			
			switch (innerChoice) {
			case 1:
				affectation.assignement();
				affectation.generateCouples();
				
				System.out.println("Résultat : ");
				affectation.printCouples();
				return 0;
			case 2:
				System.out.println("Entrez les PRÉNOMS des deux personnes à réunir : ");
				
				System.out.println("Tuteur : " );
				String guardian = KeyboardEntries.readString();
				
				System.out.println("Tutoré : ");
				String tutee = KeyboardEntries.readString();
				
				Guardian guardStudent = new Guardian();
				Tutee tuteeStudent = new Tutee();
				
				for(Guardian g : affectation.getGuardians()) {
					if(g.getFirstName().equals(guardian)) guardStudent = g;
				}
				
				for(Tutee t : affectation.getTutees()) {
					if(t.getFirstName().equals(tutee)) tuteeStudent = t;
				}
				
				affectation.assignement(guardStudent, tuteeStudent, true);
				affectation.generateCouples();
				
				System.out.println("Résultat : ");
				affectation.printCouples();
				
				return 0;
				
			case 3:
				System.out.println("Entrez les PRÉNOMS des deux personnes à éloigner : ");
				
				System.out.println("Tuteur : " );
				String guardian2 = KeyboardEntries.readString();
				
				System.out.println("Tutoré : ");
				String tutee2 = KeyboardEntries.readString();
				
				Guardian guardStudent2 = new Guardian();
				Tutee tuteeStudent2 = new Tutee();
				
				for(Guardian g : affectation.getGuardians()) {
					if(g.getFirstName().equals(guardian2)) guardStudent2 = g;
				}
				
				for(Tutee t : affectation.getTutees()) {
					if(t.getFirstName().equals(tutee2)) tuteeStudent2 = t;
				}
				
				affectation.assignement(guardStudent2, tuteeStudent2, false);
				affectation.generateCouples();
				
				System.out.println("Résultat : ");
				affectation.printCouples();
				
				return 0;
				
			case 4:
				
				System.out.println("Entrez le PRÉNOM de la personne à exclure : ");
				
				System.out.println("Etudiant à exclure : ");
				String studentName = KeyboardEntries.readString();
				
				Student student = new Student();
				
				for(Guardian g : affectation.getGuardians()) {
					if(g.getFirstName().equals(studentName)) student = (Student) g;
				}
				
				for(Tutee t : affectation.getTutees()) {
						if(t.getFirstName().equals(studentName)) student = (Student)t;
				}
				
				affectation.assignement(student);
				affectation.generateCouples();
				
				System.out.println("Résultat : ");
				affectation.printCouples();
				
				return 0;
			}
			
			return 0;
		case 4:
			System.out.print("Sauvegarder les couples sous le nom(sans le .json) : ");
			save(affectation.JSONAffectation(), KeyboardEntries.readString());
			
			return 0;
		case 5:
			return 1;
		}
		return 1;
	}
	
	// Static Attributes
	private static String sChoice = "entrez votre choix";
	static int choice = 0;
	static int menuChoice = 0;
	static int customizingMenuChoice = 0;
	static int returnChoice = 0;
	static String path = "";
	static boolean isFile = false;
	static File file = new File(path);
	static Affectation affectation;
	
	static boolean quit = false;
	
	
	// Main
	public static void main(String[] args) {
		do {
			menuChoice = menu1();
		} while(menuChoice == 1 && !quit);
		
		if(menuChoice == 0) {
			affectation = loadAffectation(path);
			while(customizingMenu() == 0);
			menu1();
		} else if (menuChoice == 2) {
			System.out.println("En cours de dev...\nChargement d'une affecation provisoire...");
			affectation = loadAffectation("test.json");
			System.out.println("affecation créée : " + affectation);
			while(customizingMenu() == 0);
			menu1();
		} else if(quit) {
			System.out.println("Fermeture du programe...");
		}
		
		
	}
}
