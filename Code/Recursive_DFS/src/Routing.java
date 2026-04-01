import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

/**
 * Routing class is a simulation engine for building and searching a graph
 * using recursive DFS
 * 
 * @author atd Aaron T Deever
 * @author sps Sean Strout
 *
 */
public class Routing {
	/**
	 * Main driver prompts for the name of a graph file, builds the graph,
	 * prompts for a start and finish node name, and prints a resulting path.
	 * 
	 * @param args not used
	 * @throws FileNotFoundException if file not found
	 */
	public static void main(String[] args) throws FileNotFoundException {
		Scanner in = new Scanner(System.in);
		System.out.print("Enter graph data filename: ");
		String filename = in.next();

		Graph g = new Graph(filename);

		System.out.print(g);

		// repeatedly perform recursive DFS
		while(true) { 

			// prompt for name of start and finish nodes
			System.out.print("Enter starting node name: ");
			String start = in.next();
			if(!g.isInGraph(start)) { 
				System.out.println(start + " is not a valid node in the graph.");
				continue;
			}

			System.out.print("Enter finishing node name: ");
			String finish = in.next();
			if(!g.isInGraph(finish)) { 
				System.out.println(finish + " is not a valid node in the graph.");
				continue;
			}

			System.out.println("Checking for path existence...");
			boolean found = g.canReachRecursiveDFS(start, finish);
			if (found) {
				System.out.println("A path exists between " + start + " and " +
						finish + "!");
			} else {
				System.out.println("NO PATH exists between " + start + " and " +
						finish + "!");
			}

			if (found) {
				System.out.println("Now perform a recursive DFS");
				System.out.print("Enter 'D' for DFS, or other to quit: ");
				String search = in.next();
				if (!search.equals("D") && !search.equals("d")) {
				    break;
				}

				List<Node> path = g.searchRecursiveDFS(start,  finish);

				if(path.isEmpty()) { 
					System.out.println("There is no connection between " + start + 
							" and " + finish + ".");
				}
				else if(path.size() == 1) { 
					System.out.println("You are already there!");
				}
				else { 
					System.out.println("It is possible to get from " + start + 
							" to " + finish + " in " + path.size() + " hops:");
					for(int n=0; n < path.size()-1; n++) { 
						System.out.println(path.get(n).getName() + " to " + 
								path.get(n+1).getName());
					}
					System.out.println("Done!");
				}
				System.out.println();
			}
		}
		in.close();
	}
}
