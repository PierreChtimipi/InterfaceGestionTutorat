package filters;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import entities.Student;

public class GradeFilter extends Filter{
	
	// Attributs de classe
	private double average;
	
	// Constructeurs
	public GradeFilter(double average) {
		this.average = average;
	}
	
	// Méthodes
	
	@Override
	public List<Student> include(List<Student> liste) {
		List<Student> newList = new ArrayList<Student>();
		
		for(Student s : liste) {
			if(s.getGrade() >= this.average) newList.add(s);
		}
		
		return newList;
	}
	
	public JSONObject JSONGradeFilter() {
		JSONObject jso = new JSONObject();
		jso.put("average", this.average);
		return jso;
	}
}
