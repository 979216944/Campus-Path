package hw5;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * <b>DirectedGraph</b> represents a mutable, directed graph without identical edges.
 * In other words, this graph does not contain two "identical" edges with the same
 *  starting point, ending point, and label.
 * It is a collection of nodes (namely vertices) and edges. 
 * Each edge contains an end node and a label. 
 *            
 * Examples of DirectedGraphs include "{}", "{a=[]}", 
 * and"{a=[(b,c), (b,d)], b=[(a,b)], c=[(c,c)], d=[]}".
 *            
 * @author Anny
 * 
 * @param <N> node data type
 * @param <E> edge's label type
 */
public class DirectedGraph<N, E> {
	
	// debug flag
	private static final boolean DEBUG = false;
	
	// directed graph
	private final Map<N, Set<LabeledEdge<N, E>>> graph;
	
	// Abstraction Function:
	// A Graph g represents a collection of unique nodes and unique 
	// labeled edges, such that 
	//        g = {} if g is an empty graph
	//        g = {a=[], ...} if a is a node and a has no outwards edges
	//        g = {a=[(b,d), (c,e), ...], b=[...], c=[...]} otherwise
	//        where b, c are ends of a's edges, and d, e are labels
	//
	// The node n in g, stored in g.graph as key, should be non-null
	// and either have non-null edges attached to it or not have any.
	//
	// Edges stored in g.graph as values are equals if they have the same
	// start, end, and label. Labels should be non-null and both start 
	// and end nodes are in the graph.
	
	// Rep invariant:
	//     g.graph != null 
	//	   && every node and edge are not null
	//	   && graph must contain node n if n is included in any edges.

	
	/**
	 * Creates an empty directed graph.
	 * 
	 * @effects constructs an empty directed graph
	 */
	public DirectedGraph() {
		graph = new HashMap<N, Set<LabeledEdge<N, E>>>();
		checkRep();
	}
	
	/**
	 * Gets size of this hashMap.
	 * 
	 * @requires the graph is not null
	 * @return the size of graph
	 */
	public int size() {
		checkRep();
		return graph.size();		
	}

	/**
	 * Checks if this.graph is empty.
	 * 
	 * @return true if the graph is empty
	 */
	public boolean isEmpty() {
		checkRep();
		return this.graph.isEmpty();
	}

	/**
	 * Checks if node n is in the graph.
	 * 
	 * @param n node to be checked if it is in the graph
	 * @requires n != null. 
	 * @return true if n is in this.graph, false otherwise
	 */
	public boolean containsNode(N n) {
		checkRep();
		return graph.containsKey(n);
	}

	/**
	 * Checks if this graph contains the specified edge.
	 * 
	 * @param start start of the edge
	 * @param end end of the edge
	 * @param label label of the edge
	 * @requires start != null && end != null && label != null
	 * @return true if this graph contains the specified edge, 
	 * 	false otherwise
	 */
	public boolean containsEdge(N start, N end, E label) {
		checkRep();
		return containsNode(start) && containsNode(end)
			&& getEdges(start).contains(new LabeledEdge<N, E>(end, label));
	}
	
	/**
	 * Checks if this graph contains the edge with arbitrary label.
	 * 
	 * @param start start of the edge
	 * @param end end of the edge
	 * @requires start != null && end != null
	 * @return true if this graph contains the specified edge, 
	 * 	false otherwise
	 */
	public boolean containsEdge(N start, N end) {
		checkRep();
		if (!containsNode(start) || !containsNode(end)) {
			return false;
		}
		for (LabeledEdge<N, E> e : getEdges(start)) {
			if (e.getEnd().equals(end)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Gets an unmodifiable set of nodes.
	 * 
	 * @return an unmodifiable set of nodes
	 */
	public Set<N> getNodes() {
		checkRep();
		return Collections.unmodifiableSet(graph.keySet());
	}

	/**
	 * Gets an unmodifiable set of outwards edges of node n.
	 * 
	 * @param n start node of all edges
	 * @requires n != null && containsNode(n)
	 * @return an unmodifiable set of outgoing edges of n,
	 * 	null if n is not in the graph
	 */
	public Set<LabeledEdge<N, E>> getEdges(N n) {
		checkRep();
		return Collections.unmodifiableSet(graph.get(n));
	}
	
//	/**
//	 * Gets the first edge's label with specified start and end.
//	 * 
//	 * @param start start of the edge
//	 * @param end end of the edge
//	 * @requires start != null && end != null
//	 * @return the label of this edge, null if the edge not present
//	 */
//	public E getFirstEdgeLabel(N start, N end) {
//		checkRep();
//		if (!containsNode(start) || !containsNode(end)) {
//			return null;
//		}
//		for (LabeledEdge<N, E> e : getEdges(start)) {
//			if (e.getEnd().equals(end)) {
//				return e.getLabel();
//			}
//		}
//		return null;
//	}

	/**
	 * Add a new node to the graph if it is not present.  
	 * 
	 * @param n node to be added
	 * @modifies this
	 * @requires n != null
	 * @effects add node n to the graph if it is not already present
	 * @return true if n was successfully added to the graph,
	 * 			false if n is already in the graph
	 */
	public boolean addNode(N n) {
		checkRep();
		// check if the node passed in already exists in the graph
		if (containsNode(n)) {
			checkRep();
			return false;
		} else {
			graph.put(n, new HashSet<LabeledEdge<N, E>>());
			checkRep();
			return true;
		}
	}
	
	/**
	 * Adds an edge(from start to end with label) to the graph.
	 * 
	 * @param start start of the edge
	 * @param end end of the edge
	 * @param label label of the edge
	 * @requires start != null && end != null && label != null
	 * 	&& containsNode(start) && containsNode(end)
	 * @modifies this
	 * @effects adds an edge to the graph if possible
	 * @return true if the edge was successfully added, 
	 * 	false if the edge already in the graph
	 */
	public boolean addEdge(N start, N end, E label) {
		checkRep();
		// check if specified edge already exists in the graph
		LabeledEdge<N, E> e = new LabeledEdge<N, E>(end, label);
		if (graph.get(start).contains(e)) {
			checkRep();
			return false;
		} else {
			graph.get(start).add(e);
			checkRep();
			return true;
		}
	}

	/**
     * Removes a Node and all edges related to it from the graph.
     * 
     * @param n Node to be removed.
     * @requires n != null
     * @modifies this
     * @effects remove n and its related edges
     * @return true if Node was successfully removed from the graph,
     * 	false if n is not in the graph
     */
	public boolean removeNode(N n) {
		checkRep();
		if (!containsNode(n)) {
			checkRep();
			return false;
		} else {
			// remove edges with n as end
			for (N node : graph.keySet()) {
				HashSet<LabeledEdge<N, E>> redges = new HashSet<LabeledEdge<N, E>>();
				for (LabeledEdge<N, E> e : graph.get(node)) {
					if (e.getEnd().equals(n)) {
						redges.add(e);
					}
				}
				for (LabeledEdge<N, E> re : redges) {
					graph.get(node).remove(re);
				}
			}
			
			// remove node itself
			graph.remove(n);
			checkRep();
			return true;
		}		
	}
	
	/**
	 * Removes an edge from the graph.
	 * 
	 * @param start start of the edge
	 * @param end end of the edge
	 * @param label label of the edge
	 * @requires start != null && end != null && label != null
	 * 	&& containsNode(start) && containsNode(end)
	 * @modifies this
	 * @effects removes an edge from the graph if possible
	 * @return true if it was successfully removed, 
	 * 	false if the edge is not in the graph.
	 */
	public boolean removeEdge(N start, N end, E label) {
		checkRep();
		// check if the specified edge is in the graph
		LabeledEdge<N, E> e = new LabeledEdge<N, E>(end, label);
		if (!graph.get(start).contains(e)) {
			checkRep();
			return false;
		}
		graph.get(start).remove(e);
		checkRep();
		return true;		
	}
	
	/**
	 * Clears all entries(nodes and edges) in the graph.
	 * 
	 * @modifies this
	 * @effects this.isEmpty() == true
	 */
	public void clearAll() {
		checkRep();
		this.graph.clear();
	}
	
	/**
	 * Standard equality operation.
	 *
	 * @param obj The object to be compared for equality.
	 * @return true if and only if 'obj' is an instance of a DirectedGraph and 'this'
	 *         and 'obj' represent the same DirectedGraph. 
	 */	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof DirectedGraph) {
			DirectedGraph<?, ?> g = (DirectedGraph<?, ?>) obj;
			checkRep();
			return graph.equals(g.graph);
		}
		checkRep();
		return false;
	}
	
	/**
	* Standard hashCode function.
	*
	* @return an int that all objects equal to this will also return.
	*/
	@Override
	public int hashCode() {
		checkRep();
		return this.graph.hashCode();
	}
	
	/**
	 * Returns a string representation of this DirectedGraph.
	 * 
	 * @return string representation of the graph
	 */
	@Override
	public String toString() {
		checkRep();
		return this.graph.toString();
	}
	
	/**
	* Checks that the representation invariant holds (if any).
	*/
	private void checkRep() {
		if (DEBUG) {
		assert this.graph != null : "Graph cannot be null";
	
		// check legal edges
			for (N node : graph.keySet()) {
				assert node != null : "Node cannot be null";
				
				for (LabeledEdge<N, E> e : graph.get(node)) {
					assert e != null : "Edge cannot be null";
					assert graph.containsKey(e.getEnd()) : "Graph cannot have edges of non-exist nodes";
				}
			}
		}
	}
}
