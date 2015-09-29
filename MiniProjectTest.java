package miniProject;

import static org.junit.Assert.*;

import java.util.ArrayList;

import miniProject.MiniProject.Location;

import org.junit.Test;

/*
 * Unit test class for class MiniProject.
 */
public class MiniProjectTest {

	@Test
	public void constructor() {
		String[] input = "Lebron James John Lennon Elton John James Elton James Faulkner"
				.split(" ");
		MiniProject mp = new MiniProject(input);
		assertTrue(mp.cel.length == input.length / 2);
		for (int i = 0; i < mp.cel.length; i++) {
			assertTrue(mp.cel[i].getFirstName().equals(input[2 * i]));
			assertTrue(mp.cel[i].getLastName().equals(input[2 * i + 1]));
		}
		assertTrue(mp.starters.size() == 1);
		assertTrue(mp.starters.get(0).toString().equals("Lebron James"));
	}

	@Test
	public void getAdjUnvisitedVertex() {
		Celebrity xin = new Celebrity("xin", "li");
		xin.addNext(new Celebrity("nachiket", "naik"));
		xin.addNext(new Celebrity("deb", "seal"));
		Location forXin = new Location(xin);
		MiniProject minP = new MiniProject();
		Celebrity nextCel = minP.getAdjUnvisitedVertex(forXin);
		assertTrue(forXin.index == 0);
		assertTrue(nextCel.getFirstName() == "nachiket");
		assertTrue(nextCel.getLastName() == "naik");

		nextCel = minP.getAdjUnvisitedVertex(forXin);
		assertTrue(forXin.index == 1);
		assertTrue(nextCel.getFirstName() == "deb");
		assertTrue(nextCel.getLastName() == "seal");
	}

	@Test
	public void dfs() {
		String[] input = "A B B B B C C D B B D B".split(" ");
		MiniProject mp = new MiniProject(input);
		assertTrue(mp.starters.get(0).toString().equals("A B"));
		ArrayList<ArrayList<Celebrity>> result = mp.dfs(mp.starters.get(0));
		assertTrue(result.size() == 1);
		assertTrue(result.get(0).get(0).toString().equals("A B"));
		assertTrue(result.get(0).get(1).toString().equals("B B"));
		assertTrue(result.get(0).get(2).toString().equals("B C"));
		assertTrue(result.get(0).get(3).toString().equals("C D"));
		assertTrue(result.get(0).get(4).toString().equals("D B"));
		assertTrue(result.get(0).get(5).toString().equals("B B"));
	}

	@Test
	public void getLongestPath() {
		String[] input = "A B B C B E E C H E G H F G".split(" ");
		MiniProject mp = new MiniProject(input);
		assertTrue(mp.starters.size() == 2);
		ArrayList<Celebrity> longest = mp.getLongestPath();
		assertTrue(longest.size() == 4);
		assertTrue(longest.get(0).toString().equals("F G"));
		assertTrue(longest.get(1).toString().equals("G H"));
		assertTrue(longest.get(2).toString().equals("H E"));
		assertTrue(longest.get(3).toString().equals("E C"));
	}

	@Test
	public void getGameResult() {
		String[] input = "A B B C B E E C H E G H F G".split(" ");
		MiniProject mp = new MiniProject(input);
		assertTrue(mp.getGameResult().equals("F G H E C"));
	}

}
