package tools;

import java.util.Scanner;

import entities.Subject;

public class KeyboardEntries {
	
	public static Scanner scan = new Scanner(System.in);
public static boolean check = false;
	
	// Methods
	
	public static String readString() {
		String word;
		
		while(!check) {
			try {
				word = scan.next();
				return word;
			} catch (Exception e) {
				System.out.println("Veuillez entrer une chaine de caractère");
			}
		}
		
		check = false;
		return "erreur";
	}
	
	public static int readInt() {
		int integer;
		
		while(!check) {
			try {
				integer = Integer.parseInt(scan.next());
				return integer;
			} catch (Exception e) {
				System.out.println("Veuillez entrer un entier");

			}
		}
		
		check = false;
		return 404;
	}
	
	public static double readDouble() {
		double douBle;
		
		while(!check) {
			try {
				douBle = Double.parseDouble(scan.next());
				return douBle;
			} catch (Exception e) {
				System.out.println("Si votre nombre est un décimal, veuillez l'écrire comme suit : 10.5");
			}
		}
		
		check = false;
		return 404.404;
	}
	
	public static Subject readSubject() {
		Subject subject;
		
		while(!check) {
			try {
				subject = Subject.valueOf(scan.next().toUpperCase());
				check = true;
				return subject;
			} catch (Exception e) {
				System.err.println("Matière disponibles : {LANGAGEC, JAVA, BDD, GRAPHES}");
			}
		}
		
		check = false;
		return Subject.NULL;
	}
}