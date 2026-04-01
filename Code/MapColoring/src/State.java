import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * The representation for a single state, which can also be viewed as a vertex
 * in a graph.
 *
 * @author sps (Sean Strout @ RIT CS)
 */
public class State {
    /*
     * The name of the state
     */
    private String name;

    /*
     * The collection of adjacent neighboring states
     */
    private Set<State> neighbors;

    /**
     * Construct a new state with no neighbors (initially).
     *
     * @param name The name of the state
     */
    public State(String name) {
        this.name = name;
        this.neighbors = new HashSet<State>();
    }

    /**
     * Get the name of the state.
     *
     * @return The name of this state
     */
    public String getName() {
        return this.name;
    }

    /**
     * Return this state's adjacent neighbors.
     *
     * @return the adjacenet neighbors for this state
     */
    public Collection<State> getNeighbors() {
        return neighbors;
    }

    /**
     * Add a new adjacent state (e.g. create a new directed edge) from
     * this state to the neighbor.
     *
     * @param neighbor The direct adjacent neighbor to this state
     */
    public void addNeighbor(State neighbor) {
        this.neighbors.add(neighbor);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        State state = (State) o;

        // two states are equal if their names are equal
        return name.equals(state.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return this.name;
    }
}
