package miniProject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;

/*
 * MiniProject class
 * Reads from input file, creates celebrity name graph, and returns the longest name chain.
 */
public class MiniProject {

	/*
	 * Helper class for DFS. Help to solve the problem that dfs doesn't work for
	 * graphs with cycles.
	 */
	static class Location {
		Celebrity c;
		int index;

		public Location(Celebrity c) {
			this.c = c;
			this.index = -1;
		}
	}

	Celebrity[] cel;
	ArrayList<Celebrity> starters; // store Celebrity node that can be the start
									// for a path.

	public MiniProject() {

	}

	/*
	 * In this constructor, array of celebrities will be constructed. next list
	 * for each celebrity will be set. And starters list will be set and
	 * optimized.
	 */
	public MiniProject(String[] input) {
		cel = new Celebrity[input.length / 2];
		starters = new ArrayList<Celebrity>();
		for (int i = 0; i < (input.length) / 2; i++) {
			cel[i] = new Celebrity(input[i * 2], input[2 * i + 1]);
		}

		/*
		 * Add adjacent nodes for each celebrity into its next list. And if
		 * there's adjacent nodes for this celebrity, put it into starters list.
		 */
		for (int i = 0; i < cel.length; i++) {
			for (int j = 0; j < cel.length; j++) {
				if (i != j
						&& cel[i].getLastName().toLowerCase()
								.equals(cel[j].getFirstName().toLowerCase())) {
					cel[i].addNext(cel[j]);
					if (!starters.contains(cel[i])) {
						boolean added = false;
						for (int k = 0; k < starters.size(); k++) {
							if (starters.get(k).getFirstName().toLowerCase()
									.equals(cel[i].getLastName().toLowerCase())) {
								starters.add(k, cel[i]);
								added = true;
								break;
							}
						}
						if (!added)
							starters.add(cel[i]);
					}
				}
			}
		}

		/*
		 * Go through nodes in starters list, set deleted to true for nodes that
		 * can be deleted. For example. If there is a path A->B->C, we know we
		 * only needs to compute longest path from A, so we can delete B and C.
		 * But now this part can't get ideal result for below input
		 * A B B B B C C D B B D B
		 * After following two for loops, starters will be left with two nodes (A B) and (C D),
		 * But ideally, we should only have (A B) left in the starters.
		 * To Do: Think of a algorithm which can get the most optimized solution.
		 */
		for (int i = 0; i < starters.size(); i++) {
			ArrayList<Celebrity> temp = starters.get(i).getAdjNodes();
			starters.get(i).wasVisited = true;

			for (Celebrity cel : temp) {
				if (starters.contains(cel)) {
					if (cel.wasVisited == false) {
						cel.deleted = true;
					}
				}
			}
		}
		for (int i = 0; i < starters.size();) {
			starters.get(i).wasVisited = false;
			if (starters.get(i).deleted == true) {
				starters.get(i).deleted = false;
				starters.remove(i);
			} else
				i++;
		}
	}

	/*
	 * Do a dfs on node cel, and return all paths that starts from node cel in
	 * result.
	 */
	public ArrayList<ArrayList<Celebrity>> dfs(Celebrity cel) {
		cel.wasVisited = true;
		Stack<Location> theStack = new Stack<Location>();
		ArrayList<ArrayList<Celebrity>> result = new ArrayList<ArrayList<Celebrity>>();
		ArrayList<Celebrity> helper = new ArrayList<Celebrity>();
		int max = 0;
		theStack.push(new Location(cel));
		helper.add(cel);

		while (!theStack.isEmpty()) {
			Celebrity unvisited = getAdjUnvisitedVertex(theStack.peek());
			if (unvisited == null) {
				if (theStack.size() > max) {
					ArrayList<Celebrity> temp = new ArrayList<Celebrity>();
					temp.addAll(helper);
					result.add(temp);
					max = theStack.size();
				}
				Celebrity temp = (theStack.pop()).c;
				temp.wasVisited = false;
				helper.remove(helper.size() - 1);
			} else {
				helper.add(unvisited);
				unvisited.wasVisited = true;
				theStack.push(new Location(unvisited));
			}
		}
		return result;
	}

	/*
	 * Get unvisited node for Location L, which includes a Celebrity object and
	 * an index for the Celebrity object.
	 */
	public Celebrity getAdjUnvisitedVertex(Location L) {
		ArrayList<Celebrity> temp = L.c.getAdjNodes();
		for (int i = L.index + 1; i < temp.size(); i++) {
			if (temp.get(i).wasVisited == false) {
				L.index = i;
				return temp.get(i);
			}
		}
		return null;
	}

	/*
	 * Do dfs for each node in starters, the dfs will return all paths for this
	 * node. And this function return the longest path of all these paths.
	 */
	public ArrayList<Celebrity> getLongestPath() {
		ArrayList<Celebrity> longest = new ArrayList<Celebrity>();
		for (Celebrity cel : starters) {
			ArrayList<ArrayList<Celebrity>> result = dfs(cel);
			for (ArrayList<Celebrity> list : result) {
				if (list.size() > longest.size())
					longest = list;
			}
		}
		return longest;
	}

	/*
	 * Formatting the result. For list (A, B)->(B,C)->(C,D), returns a String
	 * "A C D".
	 */
	public String getGameResult() {
		ArrayList<Celebrity> res = getLongestPath();
		StringBuffer result = new StringBuffer();
		result.append(res.get(0).toString());
		for (int i = 1; i < res.size(); i++) {
			result.append(" ");
			result.append(res.get(i).getLastName());
		}
		return result.toString();
	}

	/*
	 * Read from a file. Assumes the file only has one line of input, and the
	 * input is valid. By being valid, we mean the line is in below fomat:
	 * Celebrity1FirstName Celebrity1LastName Celebrity2FirstName
	 * Celebrity2LastName ...
	 */
	public static String readFromFile(String filepath) {
		BufferedReader br = null;
		String line = "";

		try {
			br = new BufferedReader(new FileReader(filepath));
			line = br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return line;
	}

	public static void main(String[] args) {
		File f = new File("input.txt");
		String filePath = f.getAbsolutePath();
		String line = MiniProject.readFromFile(filePath);
		String[] input = line.split(" ");

		MiniProject mp = new MiniProject(input);
		System.out.println("The result for Celebrity Name Game is: ");
		System.out.println(mp.getGameResult());
	}
}