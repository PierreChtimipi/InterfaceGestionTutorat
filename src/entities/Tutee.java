package entities;

public class Tutee extends Student {
	
	public Tutee() {
		this.surname = "null";
		this.firstName = "null";
		this.grade = 0;
		this.year = 1;
		this.subject = Subject.NULL;
	}
	
	public Tutee(String surname, String firstName, double grade, int year, Subject subject) throws notATuteeException {
		super(surname, firstName, grade, subject);	
		this.year = checkTutee(year);
	}
	
	public Tutee(String surname, String firstName, String grade, int year, Subject subject) throws notATuteeException {
		super(surname, firstName, grade, subject);	
		this.year = checkTutee(year);
	}
	
	private int checkTutee(int year) throws notATuteeException {
		if(year != 1) throw new notATuteeException();
		return year;
	}
	
}
