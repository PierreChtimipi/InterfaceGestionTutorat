package entities;

public class Guardian extends Student {
	
	public Guardian() {
		this.surname = "null";
		this.firstName = "null";
		this.grade = 0;
		this.year = 2;
		this.subject = Subject.NULL;
	}
	
	public Guardian(String surname, String firstName, double grade, int year, Subject subject) throws notAGuardianException {
		super(surname, firstName, grade, subject);
		this.year = checkGuardian(year);
	}
	
	public Guardian(String surname, String firstName, String grade, int year, Subject subject) throws notAGuardianException {
		super(surname, firstName, grade, subject);
		this.year = checkGuardian(year);
	}
	
	private int checkGuardian(int year) throws notAGuardianException {
		
		if(year < 2) throw new notAGuardianException();
		return year;
	}
	
}
