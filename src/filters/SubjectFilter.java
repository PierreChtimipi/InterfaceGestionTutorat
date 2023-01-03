package filters;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import entities.Student;
import entities.Subject;

public class SubjectFilter extends Filter {

	// Class Attributes
	private Subject subject;
	
	// Constructor
	public SubjectFilter(Subject subject) {
		this.subject = subject;
	}
	
	// Methods
	
	@Override
	public List<Student> include(List<Student> liste) {
		List<Student> newList = new ArrayList<Student>();
		
		for(Student s : liste) {
			if(s.getSubject() == this.subject) newList.add(s);
		}
		
		return newList;
	}

	public JSONObject JSONSubjectFilter() {
		JSONObject jso = new JSONObject();
		jso.put("subject", this.subject);
		return jso;
	}
}
