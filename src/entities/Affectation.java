package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import filters.GradeFilter;
import filters.SubjectFilter;
import filters.YearFilter;
import fr.ulille.but.sae2_02.graphes.CalculAffectation;
import fr.ulille.but.sae2_02.graphes.GrapheNonOrienteValue;

@SuppressWarnings("serial")
public class Affectation implements Serializable {
	
	
	// Class Attributes
	private List<Guardian> guardians;
	private List<Tutee> tutees;
	
	private List<Student> guardians2;
	private List<Student> tutees2;
	
	//Il aurait été préférable de mettre un attribut List<Filter> mais manque de temps
	private YearFilter tuteesYearFilter;
	private GradeFilter tuteesGradeFilter;
	private SubjectFilter tuteesSubjectFilter;
	
	private YearFilter guardiansYearFilter;
	private GradeFilter guardiansGradeFilter;
	private SubjectFilter guardiansSubjectFilter;
	
	private int weightingGrade = 50;
	private int weightingSubject = 50;
	private int weightingYear = 50;
	
	GrapheNonOrienteValue<Student> modelGraph = new GrapheNonOrienteValue<>();
	CalculAffectation<Student> couples;
	
	
	// Getters && Setters
	public List<Guardian> getGuardians() {
		return guardians;
	}

	public List<Tutee> getTutees() {
		return tutees;
	}
	
	public GrapheNonOrienteValue<Student> getModelGraph() {
		return modelGraph;
	}
	
	public CalculAffectation<Student> getCouples() {
		return couples;
	}

	public void setWeightingGrade(int wheightingGrade) {
		this.weightingGrade = wheightingGrade;
	}

	public void setWeightingSubject(int wheightingSubject) {
		this.weightingSubject = wheightingSubject;
	}

	public void setWeightingYear(int wheightingYear) {
		this.weightingYear = wheightingYear;
	}
	
	public int getWeightingGrade() {
		return weightingGrade;
	}

	public int getWeightingSubject() {
		return weightingSubject;
	}
	public int getWeightingYear() {
		return weightingYear;
	}

	public List<Student> getGuardians2() {
		return guardians2;
	}

	public List<Student> getTutees2() {
		return tutees2;
	}
	
	
	public void setGuardians(List<Guardian> guardians) {
		this.guardians = guardians;
		updateGuardians2();
	}

	public void setTutees(List<Tutee> tutees) {
		this.tutees = tutees;
		updateTutees2();
	}

	// Constructeur
	public Affectation(List<Guardian> guardians, List<Tutee> tutees, int weightingGrade, int weightingSubject, int weightingYear) {
		this.guardians = new ArrayList<>();
		this.tutees = new ArrayList<>();
		this.guardians2 = new ArrayList<>();
		this.tutees2 = new ArrayList<>();
		
		this.guardians = guardians;
		this.tutees = tutees;
		
		this.sizeCompletion();
		
		this.updateGuardians2();
		this.updateTutees2();
		
		this.weightingGrade = weightingGrade;
		this.weightingSubject = weightingSubject;
		this.weightingYear = weightingYear;
		
	}
	
	public Affectation(List<Guardian> guardians, List<Tutee> tutees) {
		this.guardians = new ArrayList<>();
		this.tutees = new ArrayList<>();
		this.guardians2 = new ArrayList<>();
		this.tutees2 = new ArrayList<>();
		
		this.guardians = guardians;
		this.tutees = tutees;
		
		this.sizeCompletion();
		
		this.updateGuardians2();
		this.updateTutees2();
	}
	
	
	
	// Methods
	
	public void addGuardian(Guardian guardian) {
		this.guardians.add(guardian);
		this.updateGuardians2();
	}
	
	public void addTutee(Tutee tutee) {
		this.tutees.add(tutee);
		this.updateTutees2();
	}
	
	public void removeGuardian(Guardian guardian) {
		this.guardians.remove(guardian);
		this.updateGuardians2();
	}
	
	public void removeTutee(Tutee tutee) {
		this.tutees.remove(tutee);
		this.updateTutees2();
	}
	// Mets à jours la liste guardians2 lorsque guardians est modifiée
	private void updateGuardians2() {
		this.guardians2.clear();
		
		for(Guardian g : guardians)  {
			this.guardians2.add((Student)g);
		}
	}
	
	// Mets à jours la liste tutees2 lorsque tutees est modifiée
	private void updateTutees2() {
		this.tutees2.clear();
	
		for(Tutee t : tutees)  {
			this.tutees2.add((Student)t);
		}
	}
	
	// Mets à jours la liste guardians lorsque guardians2 est modifiée
	private void updateGuardians() {
		this.guardians.clear();
		
		for(Student s : guardians2)  {
			this.guardians.add((Guardian)s);
		}
	}
	
	// Mets à jours la liste tutees lorsque tutees2 est modifiée
	private void updateTutees() {
		this.tutees.clear();
	
		for(Student s : tutees2)  {
			this.tutees.add((Tutee)s);
		}
	}
	
	// Permet de mettre la liste de guardians et de tutees à égalité en insérant des étudiants null.
	private void sizeCompletion() {
		int diff = this.guardians.size() - this.tutees.size();
		
		if(diff < 0) {
	
			diff = Math.abs(diff);
			
			for(int i=0; i<diff; i++) {
				this.guardians.add(new Guardian());
			}
			
		} else if (diff > 0) {
			
			diff = Math.abs(diff);

			for(int i=0; i<diff; i++) {
				this.tutees.add(new Tutee());
			}			
		}
	}

	
	/* Filtre la liste des guardians selon les critères pasées en paramètre
	 
	 À SAVOIR :
	 Pour year et grade : 
			include signifie qu'on prends tous ceux qui sont au dessus de la valeur(valeur comprise)
			exclude prends la liste contraire à include
	
	 Même principe pour subject où on prends(ou non) les élèves concernés par la matière.
	*/
	public void guardiansFilter(double average, boolean averageInclude, Subject subject, boolean subjectInclude, int year, boolean yearInclude) {
		this.updateGuardians2();
		
		this.guardiansGradeFilter = new GradeFilter(average);
		this.guardiansSubjectFilter = new SubjectFilter(subject);
		this.guardiansYearFilter = new YearFilter(year);
		
		if(averageInclude) this.guardians2 = this.guardiansGradeFilter.include(this.guardians2);
		else if(!averageInclude) this.guardians2 = this.guardiansGradeFilter.exclude(this.guardians2);
				
		if(subjectInclude) this.guardians2 = this.guardiansSubjectFilter.include(this.guardians2);
		else if(!subjectInclude) this.guardians2 = this.guardiansSubjectFilter.exclude(this.guardians2);
			
		if(yearInclude) this.guardians2 = this.guardiansYearFilter.include(this.guardians2);
		else if(!yearInclude) this.guardians2 = this.guardiansYearFilter.exclude(this.guardians2);
	
		this.updateGuardians();
		this.sizeCompletion();
		this.updateGuardians2();
	}
	
	/* Filtre la liste des tutees selon les critères pasées en paramètre
	 
	 À SAVOIR :
	 Pour year et grade : 
			include signifie qu'on prends tous ceux qui sont au dessus de la valeur(valeur comprise)
			exclude prends la liste contraire à include
	
	 Même principe pour subject où on prends(ou non) les élèves concernés par la matière.
	*/
	public void tuteesFilter(double average, boolean averageInclude, Subject subject, boolean subjectInclude, int year, boolean yearInclude) {
		this.updateTutees2();
		
		this.tuteesGradeFilter = new GradeFilter(average);
		this.tuteesSubjectFilter = new SubjectFilter(subject);
		this.tuteesYearFilter = new YearFilter(year);
		
		if(averageInclude) this.tutees2 = this.tuteesGradeFilter.include(this.tutees2);
		else if(!averageInclude) this.tutees2 = this.tuteesGradeFilter.exclude(this.tutees2);
		
		if(subjectInclude) this.tutees2 = this.tuteesSubjectFilter.include(this.tutees2);
		else if(!subjectInclude) this.tutees2 = this.tuteesSubjectFilter.exclude(this.tutees2);
		
		if(yearInclude) this.tutees2 = this.tuteesYearFilter.include(this.tutees2);
		else if(!yearInclude) this.tutees2 = this.tuteesYearFilter.exclude(this.tutees2);
		
		this.updateTutees();
		this.sizeCompletion();
		this.updateTutees2();
	}
	
	// Calcul le poids de l'arête entre guardian et tutee
	private int compatibilityScore(Guardian guardian, Tutee tutee) {
		int res = 0;
		if(guardian.getFullName().equals(new Student().getFullName())
				|| tutee.getFullName().equals(new Student().getFullName())) res = 9999;
		else {
			res += -(guardian.getGrade() - tutee.getGrade() * this.weightingGrade);
			res += -(guardian.getYear() - tutee.getYear() * weightingYear);
			res += -(guardian.getSubject().getImportanceLevel() - tutee.getSubject().getImportanceLevel() * this.weightingSubject);
		}			
		return res;
	
	}
	
	// Assigne le le poids de l'arête du modelGraph entre tous les guardians et tutees
	public void assignement() {
		this.modelGraph = new GrapheNonOrienteValue<Student>();
		
		for(Guardian g : this.guardians) {
			this.modelGraph.ajouterSommet(g);
		}
		
		for(Tutee t : this.tutees) {
			this.modelGraph.ajouterSommet(t);
		}
		
		for(Guardian g : this.guardians) {
			for(Tutee t : this.tutees) {
				this.modelGraph.ajouterArete(g, t, this.compatibilityScore(g, t));
			}
		}
	}
	
	// Forcer une affectation à deux ou les empêche d'être affecté
	public void assignement(Guardian guardian, Tutee tutee, boolean affecter) {
		this.modelGraph = new GrapheNonOrienteValue<Student>();
		
		for(Guardian g : this.guardians) {
			this.modelGraph.ajouterSommet(g);
		}
		
		for(Tutee t : this.tutees) {
			this.modelGraph.ajouterSommet(t);
		}
		
		for(Guardian g : this.guardians) {
			for(Tutee t : this.tutees) {
				if(g.equals(guardian) && t.equals(tutee) && affecter) this.modelGraph.ajouterArete(g, t, -9999);
				else if(g.equals(guardian) && t.equals(tutee) && !affecter) this.modelGraph.ajouterArete(g, t, +9999);
				else this.modelGraph.ajouterArete(g, t, this.compatibilityScore(g, t));
			}
		}
		
	}
	
	//Exclure cette étudiant
	public void assignement(Student s) {
		this.modelGraph = new GrapheNonOrienteValue<Student>();
		
		for(Guardian g : this.guardians) {
			this.modelGraph.ajouterSommet(g);
		}
		
		for(Tutee t : this.tutees) {
			this.modelGraph.ajouterSommet(t);
		}
		
		for(Guardian g : this.guardians) {
			for(Tutee t : this.tutees) {
				if(g.equals(s)) g.setGrade(-9999);
				else if(t.equals(s)) t.setGrade(9999);
				this.modelGraph.ajouterArete(g, t, this.compatibilityScore(g, t));
			}
		}
	}
	
	public void generateCouples() {
		this.couples = new CalculAffectation<>(modelGraph, tutees2, guardians2);
	}
	
	public void printCouples() {
		for (int i=0; i < this.couples.getAffectation().size(); i++) {
			
			if(this.couples.getAffectation().get(i).getExtremite1().getFullName().equals("null null")
					&& !this.couples.getAffectation().get(i).getExtremite2().getFullName().equals("null null")) {
			
				System.out.println("personne ne peut s'occuper de " + couples.getAffectation().get(i).getExtremite2());
			}
			
			else if(!this.couples.getAffectation().get(i).getExtremite1().getFullName().equals("null null")
					&& this.couples.getAffectation().get(i).getExtremite2().getFullName().equals("null null")) {
				
				System.out.println(couples.getAffectation().get(i).getExtremite1() + " ne s'occupe de personne");
			
			}
			
			else if(!this.couples.getAffectation().get(i).getExtremite1().getFullName().equals("null null")
					&& !this.couples.getAffectation().get(i).getExtremite2().getFullName().equals("null null")) {
				System.out.println(this.couples.getAffectation().get(i).getExtremite1()+
					" s'occupera de " + couples.getAffectation().get(i).getExtremite2());
			}
		}
	}
	
	public JSONObject JSONAffectation() {
		JSONObject JSONAffectation = new JSONObject();
		JSONAffectation.put("guardians", this.guardians);
		JSONAffectation.put("tutees", this.tutees);
		
		if(this.tuteesYearFilter != null) JSONAffectation.put("tuteesYearFilter", this.tuteesYearFilter.JSONYearFilter());
		if(this.tuteesGradeFilter != null) JSONAffectation.put("tuteesGradeFilter", this.tuteesGradeFilter.JSONGradeFilter());
		if(this.tuteesSubjectFilter != null) JSONAffectation.put("tuteesSubjectFilter", this.tuteesSubjectFilter.JSONSubjectFilter());
		
		if(this.guardiansYearFilter != null) JSONAffectation.put("guardiansYearFilter", this.guardiansYearFilter.JSONYearFilter());
		if(this.guardiansGradeFilter != null) JSONAffectation.put("guardiansGradeFilter", this.guardiansGradeFilter.JSONGradeFilter());
		if(this.guardiansSubjectFilter != null) JSONAffectation.put("guardiansSubjectFilter", this.guardiansSubjectFilter.JSONSubjectFilter());
		
		JSONAffectation.put("weightingYear", this.weightingYear);
		JSONAffectation.put("weightingGrade", this.weightingGrade);
		JSONAffectation.put("weightingSubject", this.weightingSubject);
		
		if(this.couples != null) JSONAffectation.put("Couples", this.couples.getAffectation());
		
		return JSONAffectation;
	}

	@Override
	public String toString() {
		return "Affectation [guardians=" + guardians + ", tutees=" + tutees + ", guardians2=" + guardians2
				+ ", tutees2=" + tutees2 + ", tuteesYearFilter=" + tuteesYearFilter + ", tuteesGradeFilter="
				+ tuteesGradeFilter + ", tuteesSubjectFilter=" + tuteesSubjectFilter + ", guardiansYearFilter="
				+ guardiansYearFilter + ", guardiansGradeFilter=" + guardiansGradeFilter + ", guardiansSubjectFilter="
				+ guardiansSubjectFilter + ", weightingGrade=" + weightingGrade + ", weightingSubject="
				+ weightingSubject + ", weightingYear=" + weightingYear + "]";
	}
	
}
