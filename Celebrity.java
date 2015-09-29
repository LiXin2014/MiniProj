package miniProject;

import java.util.ArrayList;

/*
 * A Celebrity class, servers as a node in the graph.
 */
public class Celebrity {

	private String firstName;
	private String lastName;
	// track whether this node is visited when traversing
	boolean wasVisited;
	// track whether this node is deleted in specific list
	boolean deleted;
	// All the nodes that this celebrity can connect to. So it's like using
	// Adjacency List to represent the graph.
	private ArrayList<Celebrity> next;

	public Celebrity(String first, String last) {
		firstName = first;
		lastName = last;
		wasVisited = false;
		deleted = false;
		next = new ArrayList<Celebrity>();
	}

	/*
	 * Add cel to the list of next for this Celebrity.
	 */
	public void addNext(Celebrity cel) {
		next.add(cel);
	}

	public ArrayList<Celebrity> getAdjNodes() {
		return next;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String toString() {
		return firstName + " " + lastName;
	}
}
