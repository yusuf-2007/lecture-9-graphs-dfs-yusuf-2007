import java.util.*;

/**
 * This is the representation of a "coloring" of our map of states.  It uses
 * the graph that was constructed in the main program, MapColoring.
 *
 * @author sps (Sean Strout @ RIT CS)
 */
public class ColoringConfig implements Configuration {
    /*
     * The graph of states
     */
    private Map<String, State> states;

    /*
     * This contains a mapping of state's to their colors (strings).  If a
     * state is currently uncolored, it holds a string color of null.
     */
    private Map<State, String> coloring;

    /*
     * The current state that was just colored (begins as null)
     */
    private State current;

    /*
     * The palette of colors based on the total colors in the constructor
     * call.  These are the full set of colors we can use to color all states.
     */
    private String[] palette;

    /*
     * A collection of colors that the palette will reference.
     */
    private final static List<String> COLORS = new ArrayList<String>(
            Arrays.asList("RED", "GREEN", "BLUE", "YELLOW", "CYAN", "BLACK"));

    /**
     * Construct the initial coloring config.
     *
     * @param states The graph of states
     * @param totalColors The maximum number of colors that can be used
     */
    public ColoringConfig(Map<String, State> states, int totalColors) {
        // store the graph.  We will never change this representation, but will
        // use it in getSuccessors() to locate neighbors
        this.states = states;

        // Create the mapping of states to colors, initially the are all
        // uncolored (null)
        this.coloring = new HashMap<State, String>();
        for (String state : states.keySet()) {
            this.coloring.put(states.get(state), null);
        }

        // So that we don't have to scan through the colorings to find the
        // next state to color, we will use this reference
        this.current = null;

        // if the user selected too many total colors, we want to limit
        // them to the maximum selectable
        if (totalColors > COLORS.size()) {
            totalColors = COLORS.size();
        }

        // populate the palette with colors
        this.palette = new String[totalColors];
        for (int i=0; i<totalColors; ++i) {
            this.palette[i] = COLORS.get(i);
        }
    }

    /**
     * A copy constructor
     * @param other The config to make a copy of
     */
    private ColoringConfig(ColoringConfig other) {
        // "shallow copy".  Use this for things like primitives or
        // non-mutable objects.
        this.states = other.states;
        this.current = other.current;

        // make a complete copy of the coloring using HashMap's constructor
        // that takes another HashMap.  This will make a complete copy
        // of the colorings so we don't override another configuration's copy
        this.coloring = new HashMap<State, String>(other.coloring);

        // this demonstrates how you would copy an array.  It is not needed
        // for this example since the palette is the same for all configurations,
        // but you may find this useful with the backtracking lab
        this.palette = new String[other.palette.length];
        System.arraycopy(other.palette, 0, this.palette, 0, this.palette.length);

    }

    @Override
    public Collection<Configuration> getSuccessors() {
        // find next state to color (will have a null Color value)
        for (State state : coloring.keySet()) {
            if (coloring.get(state) == null) {
                this.current = state;
                break;
            }
        }

        // generate successors where the current state is colored with
        // all possible colors in the palette
        Collection<Configuration> successors = new LinkedList<Configuration>();
        for (String color : this.palette) {
            ColoringConfig successor = new ColoringConfig(this);
            successor.coloring.put(this.current, color);
            successors.add(successor);
        }

        return successors;
    }

    @Override
    public boolean isValid() {
        // first state is valid
        if (this.current == null) {
            return true;
        }

        // check if any adjacent state has the same color as the current state
        for (State neighbor : this.current.getNeighbors()) {
            if (this.coloring.get(current).equals(this.coloring.get(neighbor))) {
                return false;
            }
        }
        return true;

    }

    @Override
    public boolean isGoal() {
        // goal if all states are colored (no null Color's)
        return !coloring.values().contains(null);
    }

    @Override
    public String toString() {
        String result = "ColoringConfig{" +
                "current=" + (this.current != null ? this.current.getName() : "null") +
                ", coloring=";
        for (Map.Entry<State, String> entry : this.coloring.entrySet()) {
            result += entry.getKey().getName() + ":";
            result += entry.getValue() != null ? entry.getValue() : "null";
            result += " ";
        }
        result += "}";

        return result;
    }
}
