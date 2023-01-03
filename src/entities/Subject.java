package entities;

public enum Subject {
	
	// Instances
	LANGAGEC("langage C", 2), JAVA("java", 8), BDD("bdd", 3), GRAPHES("graphes", 1), NULL("null", -9999);
	
	// Class Attributes
	private String subjectName;
	private int importanceLevel;
	
	// Getters && Setters
	
	public String getSubjectName() {
		return subjectName;
	}

	public int getImportanceLevel() {
		return importanceLevel;
	}
	
	// Constructor
	private Subject(String subjectName, int importanceLevel) {
		this.subjectName = subjectName;
		this.importanceLevel = importanceLevel;
	}
}
