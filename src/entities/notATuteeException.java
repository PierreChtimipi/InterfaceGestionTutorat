package entities;

@SuppressWarnings("serial")
public class notATuteeException extends Exception{

	public notATuteeException() {
		System.err.println("L'étudiant n'est pas un premier année");
	}
}
