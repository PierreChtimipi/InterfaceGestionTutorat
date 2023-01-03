package entities;

public class Student {

	protected String surname;
	protected String firstName;
	protected double grade;
	protected int year;
	protected Subject subject;

	
	public Student() {}
	
	public Student(String surname, String firstName, double grade, Subject subject) {
		this.surname = surname;
		this.firstName = firstName;
		this.grade = grade;
		this.subject = subject;
	}
	
	public Student(String surname, String firstName, String grade, Subject subject) {
		this(surname, firstName, Double.valueOf(grade), subject);
	}
	
	
	// Getters && Setters
	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public double getGrade() {
		return grade;
	}

	public void setGrade(double note) {
		this.grade = note;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}
	
	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}
	
	// Methods
	
	public String getFullName() {
		return this.surname + " " + this.firstName;
	}

	@Override
	public String toString() {
		return this.getFullName();
	}

	
	
}
