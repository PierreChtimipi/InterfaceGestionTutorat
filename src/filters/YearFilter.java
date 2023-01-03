package filters;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import entities.Student;

public class YearFilter extends Filter {
	
	// Class Attributes
	private int year;
	
	// Constructor
	public YearFilter(int year) {
		this.year = year;
	}
	
	// Methods
	
	@Override
	public List<Student> include(List<Student> liste) {
		List<Student> newList = new ArrayList<Student>();
		
		for(Student s : liste) {
			if(s.getYear() >= this.year) newList.add(s);
		}
		
		return newList;
	}
	
	public JSONObject JSONYearFilter() {
		JSONObject jso = new JSONObject();
		jso.put("year", this.year);
		return jso;
	}
	
	
}
