package entities;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.Main;

public class AffectationTest {
	
	private Affectation affectation;
	
	private List<Guardian> guardians = new ArrayList<Guardian>();
	private List<Tutee> tutees = new ArrayList<Tutee>();
	
	private Guardian sophie;
	private Guardian edouard;
	private Guardian olivier;
	private Guardian juliette;
	
	private Tutee valerie;
	private Tutee martin;
	private Tutee eleonore;
	private Tutee odette;
	private Tutee amelie;
	
	@BeforeEach
	public void initialisation() {
		
		sophie = new Guardian("Vallée", "Sophie", 15.8, 2, Subject.LANGAGEC);
		edouard = new Guardian("Augé", "Édouard", 13.9, 3, Subject.JAVA);
		olivier = new Guardian("Gallet", "Olivier", 11.3, 3, Subject.BDD);
		juliette = new Guardian("Traore", "Juliette", 9.8, 2, Subject.GRAPHES);
		
		valerie = new Tutee("Jacob", "Valérie", 4.7, 1, Subject.JAVA);
		martin = new Tutee("Tanzi", "Martin", 10.1, 1, Subject.LANGAGEC);
		eleonore = new Tutee("Guillou", "Éléonore", 13.1, 1, Subject.BDD);
		odette = new Tutee("Lacroix", "Odette", 15.7, 1, Subject.GRAPHES);
		amelie = new Tutee("Poulain", "Amélie", 17.9, 1, Subject.LANGAGEC);
		
		guardians.add(sophie);
		guardians.add(edouard);
		guardians.add(olivier);
		guardians.add(juliette);
		
		tutees.add(valerie);
		tutees.add(martin);
		tutees.add(eleonore);
		tutees.add(odette);
		tutees.add(amelie);
		
		this.affectation = new Affectation(guardians, tutees);
	}
	
	@Test
	public void affectationTest() {
		/*On teste si les deux listes sont bien à la même taille grâce à la fonction privée sizeCompletion()*/
		assertTrue(this.affectation.getGuardians().size() == this.affectation.getTutees().size());
		
		/*On teste dans la foulée si les listes secondaires sont bien mises à jour avec les méthodes privées updateGuardians2() et updateTutees2()*/
		assertTrue(this.affectation.getGuardians2().size() == this.affectation.getGuardians().size());
		assertTrue(this.affectation.getTutees2().size() == this.affectation.getTutees().size());
	}
	
	@Test
	public void removeGuardianTest() {
		/* On vérifie la présence de sophie à l'idice 0*/
		assertTrue(this.affectation.getGuardians2().get(0) == this.sophie);
		
		/* On la remove via affectation*/
		this.affectation.removeGuardian(sophie);
		
		/* On regarde si elle a bien été enlevée et si la liste secondaire est bien à jour */
		assertFalse(this.affectation.getGuardians().get(0) == this.sophie);
		assertTrue(this.affectation.getGuardians().get(0) == this.affectation.getGuardians2().get(0));
	}
	
	@Test
	public void addGuardianTest() {
		/* On enlève sophie de la liste et on vérifie la bonne supression*/
		assertTrue(this.guardians.get(0) == this.sophie);
		this.guardians.remove(0);
		assertFalse(this.guardians.get(0) == this.sophie);
		
		/* On rajoute sophie via la méthode addGuardian de Affectation*/
		this.affectation.addGuardian(sophie);
		
		/* On regarde si elle a bien été ajoutée et si la liste secondaire est bien à jour */
		assertTrue(this.affectation.getGuardians().get(4) == this.sophie);
		assertTrue(this.affectation.getGuardians().get(0) == this.affectation.getGuardians2().get(0));

	}
	
	@Test
	public void removeTuteeTest() {
		/* On vérifie la présence de valérie à l'idice 0*/
		assertTrue(this.affectation.getTutees().get(0) == this.valerie);
		
		/* On la remove via affectation*/
		this.affectation.removeTutee(valerie);
		
		/* On regarde si elle a bien été enlevée et si la liste secondaire est bien à jour */
		assertFalse(this.affectation.getTutees().get(0) == this.valerie);
		assertTrue(this.affectation.getTutees().get(0) == this.affectation.getTutees2().get(0));
	}
	
	@Test
	public void addTuteeTest() {
		
		/* On enlève valérie de la liste et on vérifie la bonne supression*/
		assertTrue(this.tutees.get(0) == this.valerie);
		this.tutees.remove(0);
		assertFalse(this.tutees.get(0) == this.valerie);
		
		/* On rajoute sophie via la méthode addGuardian de Affectation*/
		this.affectation.addTutee(valerie);
		
		/* On regarde si elle a bien été ajoutée et si la liste secondaire est bien à jour */
		assertTrue(this.affectation.getTutees().get(4) == this.valerie);
		assertTrue(this.affectation.getTutees().get(0) == this.affectation.getTutees2().get(0));

	}
	
	@Test
	public void weightingTest() {
		affectation = new Affectation(guardians, tutees, 25, 50, 75);
		
		assertEquals(this.affectation.getWeightingGrade(), 25);
		assertEquals(this.affectation.getWeightingSubject(), 50);
		assertEquals(this.affectation.getWeightingYear(), 75);
		
	}
	
	@Test
	public void guardianFilterTest() {
		/* Ici, on touche seulement à la liste secondaire guardians2 */

		/* On filtre la liste selon des paramètres arbitraires */
		this.affectation.guardiansFilter(12, true, Subject.JAVA, true, 2, true);

		/* On doit avoir en sortie la liste de guardians2 ne contenant que édouard et le reste d'étudiants null*/
		
		List<Student> notNull = new ArrayList<>();
		for(Student s : this.affectation.getGuardians2()) {
			if(!s.getFullName().equals("null null")) notNull.add(s);
		}
		assertTrue(notNull.size() == 1);
		assertTrue(notNull.contains(edouard));
	}
	
	@Test
	public void tuteesFilterTest() {
		/* Ici, on touche seulement à la liste secondaire tutees2 */

		/* On filtre la liste selon des paramètres arbitraires */
		this.affectation.tuteesFilter(13, false, Subject.BDD, false, 1, true);

		/* On doit avoir en sortie la liste de tutees2 ne contenant que martin et valérie */
		List<Student> notNull = new ArrayList<>();
		for(Student s : this.affectation.getTutees2()) {
			if(!s.getFullName().equals("null null")) notNull.add(s);
		}
		
		assertTrue(notNull.size() == 2);
		assertTrue(notNull.contains(valerie));
		assertTrue(notNull.contains(martin));
	}
	
	@Test
	public void assignementTest() {
		
		/*On réduit le nombre d'étudiants des listes pour mieux comprendre les tests */
		
		this.affectation.guardiansFilter(13, true, Subject.BDD, false, 2, true);
		this.affectation.tuteesFilter(13, false, Subject.BDD, false, 1, true);
		
		/* On assigne sans forcer personne */
		
		this.affectation.assignement();
		
		/* Il ne reste qu'édouard et sophie pour les tuteurs(guardians) et martin et valérie en tutoré*/
		
		this.affectation.generateCouples();
		
		for (int i=0; i < this.affectation.couples.getAffectation().size(); i++) {
			
			if(this.affectation.couples.getAffectation().get(i).getExtremite1() == edouard)
				assertEquals(valerie, this.affectation.couples.getAffectation().get(i).getExtremite2());
			
			if(this.affectation.couples.getAffectation().get(i).getExtremite1() == sophie)
				assertEquals(martin, this.affectation.couples.getAffectation().get(i).getExtremite2());

		}
		
		/* On réinitialise */
		
		this.affectation = new Affectation(guardians, tutees);
		
		/*On réduit le nombre d'étudiants des listes pour mieux comprendre les tests */
		
		this.affectation.guardiansFilter(13, true, Subject.BDD, false, 2, true);
		this.affectation.tuteesFilter(13, false, Subject.BDD, false, 1, true);
		
		/* On force edouard et martin à se mettre ensemble */
		
		this.affectation.assignement(edouard, martin, true);
		
		/* Il ne reste qu'édouard et sophie pour les tuteurs(guardians) et martin et valérie en tutoré*/
		
		this.affectation.generateCouples();
		
		for (int i=0; i < this.affectation.couples.getAffectation().size(); i++) {
			
			if(this.affectation.couples.getAffectation().get(i).getExtremite1() == edouard)
				assertEquals(martin, this.affectation.couples.getAffectation().get(i).getExtremite2());
			
			if(this.affectation.couples.getAffectation().get(i).getExtremite1() == sophie)
				assertEquals(valerie, this.affectation.couples.getAffectation().get(i).getExtremite2());

		}
		
		/* On réinitialise */
		
		this.affectation = new Affectation(guardians, tutees);
		
		/*On réduit le nombre d'étudiants des listes pour mieux comprendre les tests */
		
		this.affectation.guardiansFilter(13, true, Subject.BDD, false, 2, true);
		this.affectation.tuteesFilter(13, false, Subject.BDD, false, 1, true);
		
		/* On force edouard et martin à NE PAS se mettre ensemble*/
		
		this.affectation.assignement(edouard, martin, false);
		
		/* Il ne reste qu'édouard et sophie pour les tuteurs(guardians) et martin et valérie en tutoré*/
		
		this.affectation.generateCouples();
		
		for (int i=0; i < this.affectation.couples.getAffectation().size(); i++) {
			
			if(this.affectation.couples.getAffectation().get(i).getExtremite1() == edouard)
				assertEquals(valerie, this.affectation.couples.getAffectation().get(i).getExtremite2());
			
			if(this.affectation.couples.getAffectation().get(i).getExtremite1() == sophie)
				assertEquals(martin, this.affectation.couples.getAffectation().get(i).getExtremite2());

		}
		
		/* On réinitialise */
		
		this.affectation = new Affectation(guardians, tutees);
		
		/*On réduit le nombre d'étudiants des listes pour mieux comprendre les tests */
		
		this.affectation.guardiansFilter(13, true, Subject.BDD, false, 2, true);
		this.affectation.tuteesFilter(13, false, Subject.BDD, false, 1, true);
		
		/* On force edouard à être avec personne */
		
		this.affectation.assignement(edouard);
		
		/* Il ne reste qu'édouard et sophie pour les tuteurs(guardians) et martin et valérie en tutoré*/
		
		this.affectation.generateCouples();
		
		for (int i=0; i < this.affectation.couples.getAffectation().size(); i++) {
			
			if(this.affectation.couples.getAffectation().get(i).getExtremite1() == edouard) {
				assertEquals("null null", this.affectation.couples.getAffectation().get(i).getExtremite2().getFullName());
			}
			
		}
	}
}
