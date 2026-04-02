/*
 * Node.java
 *
 * Version:
 * $Id: Node.java,v 1.1 2014/02/06 16:14:18 atd Exp $
 *
 * Revisions:
 * $Log: Node.java,v $
 * Revision 1.1  2014/02/06 16:14:18  atd
 * Initial revision using stacks and queues for DFS and BFS.
 *
 */

import java.util.LinkedList;
import java.util.List;

/**
 * Class representing a node in a graph.
 * 
 * @author atd Aaron T Deever
 *
 */
public class Node {

	/*
	 *  Name associated with this node.
	 */
	private String name;

	/*
	 * Neighbors of this node are stored as a list (adjacency list).
	 */
	private List<Node> neighbors;

	/**
	 * Constructor.  Initialized with an empty list of neighbors.
	 * 
	 * @param name string representing the name associated with the node.
	 */
	public Node(String name) { 
		this.name = name;
		this.neighbors = new LinkedList<Node>();
	}

	/**
	 * Get the String name associated with this object.
	 * 
	 * @return name.
	 */
	public String getName() { 
		return name;
	}

	/**
	 * Add a neighbor to this node.  Checks if already present, and does not
	 * duplicate in this case.
	 * 
	 * @param n: node to add as neighbor.
	 */
	public void addNeighbor(Node n) { 
		if(!neighbors.contains(n)) { 
			neighbors.add(n);
		}
	}

	/**
	 * Method to return the adjacency list for this node containing all 
	 * of its neighbors.
	 * 
	 * @return the list of neighbors of the given node
	 */
	public List<Node> getNeighbors() { 
		return new LinkedList<Node>(neighbors);
	}

	/**
	 * Method to generate a string associated with the node, including the 
	 * name of the node followed by the names of its neighbors.  Overrides
	 * Object toString method.
	 * 
	 * @return string associated with the node.
	 */
	@Override
		public String toString() { 
			String result;
			result = name + ":  ";

			for(Node nbr : neighbors) { 
				result = result + nbr.getName() + ", ";
			}
			// remove last comma and space, or just spaces in the case of no neighbors
			return (result.substring(0, result.length()-2));
		}

	/**
	 *  Two Node's are equal if they have the same name.
	 *  @param other The other object to check equality with
	 *  @return true if equal; false otherwise
	 */
	@Override
	public boolean equals(Object other) {
		boolean result = false;
		if (other instanceof Node) {
			Node n = (Node) other;
			result = this.name.equals(n.name);
		}
		return result;
	}

	/**
	 * The hash code of a Node is just the hash code of the name,
	 * since no two node's can have the same name.
	 */
	@Override
	public int hashCode() {
		return this.name.hashCode();
	}
}
