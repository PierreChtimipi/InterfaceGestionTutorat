package entities;

@SuppressWarnings("serial")
public class notAGuardianException extends Exception {

	public notAGuardianException () {
		System.err.println("L'étudiant n'est pas un deuxième ou troisième année");
	}
}
