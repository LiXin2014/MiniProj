package miniProject;

import static org.junit.Assert.*;

import org.junit.Test;

/*
 * Unit test class for class Celebrity.
 */
public class CelebrityTest {

	@Test
	public void constructor() {
		Celebrity xin = new Celebrity("Xin", "Li");
		assertTrue(xin.getFirstName() == "Xin");
		assertTrue(xin.getLastName() == "Li");
		assertTrue(xin.deleted == false);
		assertTrue(xin.wasVisited == false);
		assertTrue(xin.getAdjNodes().size() == 0);
		assertTrue(xin.toString().equals("Xin Li"));
	}

	@Test
	public void addNext() {
		Celebrity xin = new Celebrity("Xin", "Li");
		xin.addNext(new Celebrity("Nachiket", "Naik"));
		assertTrue(xin.getAdjNodes().get(0).toString().equals("Nachiket Naik"));
		xin.addNext(new Celebrity("Deb", "Seal"));
		assertTrue(xin.getAdjNodes().get(1).toString().equals("Deb Seal"));
	}
}
