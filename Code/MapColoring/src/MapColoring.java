import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;

/**
 * Main program for map coloring problem.
 *
 * @author sps (Sean Strout @ RIT CS)
 */
public class MapColoring {

    /**
     * Builds the graph from a file of states and neighbors, e.g.:
     *     state1,neighbor1,neighbor2,...neighborN
     *     state2,neighbor1,neighbor2,...neighborM
     *     ...
     * @param filename The name of the file (see the data/ directory)
     * @return a graph mapping the state name (String) to a State object (think of it
     *      like a node).
     * @throws FileNotFoundException if there is an error opening the file
     */
    private static Map<String, State> buildGraph(String filename) throws FileNotFoundException {
        Map<String, State> states = new LinkedHashMap<String, State>();
        Scanner in = new Scanner(new File(filename));
        // loop over each line in the file and construct State object and
        // connect neighbors as edges
        while (in.hasNextLine()) {
            String line = in.nextLine();
            String[] neighbors = line.split(",");
            if (!states.containsKey(neighbors[0])) {
                states.put(neighbors[0], new State(neighbors[0]));
            }
            State state = states.get(neighbors[0]);
            for (int i=1; i<neighbors.length; ++i) {
                if (!states.containsKey(neighbors[i])) {
                    states.put(neighbors[i], new State(neighbors[i]));
                }
                State neighbor = states.get(neighbors[i]);
                state.addNeighbor(neighbor);
            }
        }

        in.close();
        return states;
    }

    /**
     * The main function.
     *
     * @param args The command line arguments (name of input file)
     * @throws FileNotFoundException If file not found
     */
    public static void main(String[] args) throws FileNotFoundException {
        if (args.length != 3) {
            System.err.println("Usage: java MapColoring map-file #-colors debug");
        } else {
            // build the graph of the states (node) with edges to neighboring states
            Map<String, State> states = buildGraph(args[0]);

            // create the initial config with no colorings
            ColoringConfig init = new ColoringConfig(states, Integer.parseInt(args[1]));

            // construct the backtracker and solve for the initial config
            Backtracker bt = new Backtracker(args[2].equals("true"));
            Optional<Configuration> sol = bt.solve(init);

            // look at the result
            if (sol.isPresent()) {
                System.out.println("Solution: " + sol.get());
            } else {
                System.out.println("No solution!");
            }
        }
    }
}
