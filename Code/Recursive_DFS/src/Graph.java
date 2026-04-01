/*
 * Graph.java
 *
 * Version:
 * $Id: Graph.java,v 1.1 2014/02/06 16:14:18 atd Exp $
 *
 * Revisions:
 * $Log: Graph.java,v $
 * Revision 1.1  2014/02/06 16:14:18  atd
 * Initial revision using stacks and queues for DFS and BFS.
 *
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;

/**
 * Graph class.  Holds representation of a graph as well as functions to 
 * interact with the graph.
 * 
 * @author atd Aaron T Deever
 * @author sps Sean Strout
 *
 */
public class Graph {

	/*
	 * graph is represented using a map (dictionary).
	 */
	private Map<String, Node> graph;

	/**
	 * Constructor.  Loads graph from a given filename.  Assumes that each line
	 * in the input file contains the names of two nodes.  Creates nodes
	 * as necessary as well as undirected edges between the nodes.
	 * Returns the graph in the form of a map having the names of the
	 * nodes as keys, and the nodes themselves as values.
	 * 
	 * @param filename name of the input graph specification file
         * @throws FileNotFoundException if file not found
	 */
	public Graph(String filename) throws FileNotFoundException { 

		// open the file for scanning
		File file = new File(filename);
		Scanner in = new Scanner(file);

		// create the graph
		graph = new HashMap<String, Node>();

		// loop over and parse each line in the input file
		while (in.hasNextLine()) {
			// read and split the line into an array of strings
			// where each string is separated by a space.
			Node n1, n2;
			String line = in.nextLine();
			String[] fields = line.split("( )+"); // 1 or more ' '

			// creates new nodes as necessary
			if (graph.containsKey(fields[0])) { 
				n1 = graph.get(fields[0]);
			}
			else { 
				n1 = new Node(fields[0]);
				graph.put(fields[0],  n1);
			}
			if (graph.containsKey(fields[1])) { 
				n2 = graph.get(fields[1]);
			}
			else { 
				n2 = new Node(fields[1]);
				graph.put(fields[1],  n2);
			}

			n1.addNeighbor(n2);
			n2.addNeighbor(n1);
		}
		in.close();
	}

	/**
	 * Method to generate a string associated with the graph.  The string
	 * comprises one line for each node in the graph. Overrides
	 * Object toString method.
	 * 
	 * @return string associated with the graph.
	 */
	public String toString() { 
		String result = "";
		for (String name : graph.keySet()) { 
			result = result + graph.get(name).toString() + "\n";
		}
		return result;
	}

	/**
	 * Method to check if a given String node is in the graph.
	 * @param nodeName: string name of a node
	 * @return boolean true if the graph contains that key; false otherwise
	 */
	public boolean isInGraph(String nodeName) { 
		return graph.containsKey(nodeName);
	}
	
	/**
     * Recursive function that visits all nodes reachable from the given node
     * in depth-first search fashion.  Visited list is updated in the process.
     * 
     * @param node the node from which to search
     * @param visited the list of nodes that have already been visited
     */
    private void visitDFS(Node node, Set<Node> visited) { 
        for (Node neighbor : node.getNeighbors()) { 
            if(!visited.contains(neighbor)) { 
                visited.add(neighbor);
                visitDFS(neighbor, visited);
            }
        }
    }

	/**
	 * For a given start and finish node, we simply want to know whether
	 * a path exists, or not, between them. 
	 * 
	 * @param start the name associated with the node from which to start the search
	 * @param finish the name associated with the destination node	 
	 * @return boolean true if a path exists; false otherwise
	 */
	public boolean canReachRecursiveDFS(String start, String finish) {
        // assumes input check occurs previously
        Node startNode, finishNode;
        startNode = graph.get(start);
        finishNode = graph.get(finish);
        
        // construct a visited list of all nodes reachable from the start
        Set<Node> visited = new HashSet<Node>();
        visited.add(startNode);
        visitDFS(startNode, visited);  // recursive function
        return (visited.contains(finishNode));	
    }
	
	/**
	 * Method that visits all nodes reachable from the given starting node
     * in depth-first search fashion using the call stack.  When/if the
     * finish node is encountered, a path is returned with the node name.
     * Each predecessor node in the path takes their node name and prepends
     * it to the list, thus forming the solution path.
	 * @param start start node
	 * @param finish finish node
	 * @param visited set of visited nodes
         * @return the list of nodes in the path, if one exists, empty list otherwise
	 */
	private List<Node> buildPathRecursiveDFS(Node start, Node finish, Set<Node> visited) {
	    List<Node> path = new LinkedList<Node>();
	    
	    // base case when start gets to finish
	    if (start.equals(finish)) {
	        path.add(0, start);
	        return path;
	    } else {
	        for (Node neighbor : start.getNeighbors()) { 
	            if(!visited.contains(neighbor)) { 
	                visited.add(neighbor);
	                path = buildPathRecursiveDFS(neighbor, finish, visited);
	                // if path is not empty, we are part of the solution path,
	                // append ourselves to the front of the path list and return
	                if(path.size() > 0) { 
	                    path.add(0, start);
	                    return path;
	                }
	            }
	        }
	        // no luck, return empty path
	        return path;  
	    }   
	}

	/**
	 * Main routine for searching a graph to find a path between a 
	 * start and end node.  It uses a visited set to prevent cycles.
	 * 
	 * @param start the name associated with the node from which to start the search
	 * @param finish the name associated with the destination node
	 * @return path the path from start to finish.  Empty if there is no such path.
	 * 
	 * Precondition: the inputs correspond to nodes in the graph. 
	 */
	public List<Node> searchRecursiveDFS(String start, String finish) { 
        // assumes input check occurs previously
        Node startNode, finishNode;
        startNode = graph.get(start);
        finishNode = graph.get(finish);
        
        // construct a visited list of all nodes reachable from the start
        Set<Node> visited = new HashSet<Node>();
        visited.add(startNode);
        
        List<Node> path = buildPathRecursiveDFS(startNode, finishNode, visited);
        return path;
	}
}
