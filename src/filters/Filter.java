package filters;

import java.util.ArrayList;
import java.util.List;

import entities.Student;

public abstract class Filter {
	
	// Attributes
	String label = "None";
	
	// Methods
	public abstract List<Student> include(List<Student> liste);
	
	public List<Student> exclude(List<Student> liste) {
		List<Student> newList = new ArrayList<Student>(liste);
		newList.removeAll(include(liste));
		return newList;
	}
}
