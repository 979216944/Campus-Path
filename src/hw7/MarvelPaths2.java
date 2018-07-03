package hw7;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;

import hw5.DirectedGraph;
import hw5.LabeledEdge;


/**
 * This class reads the Marvel data from a file (marvel.tsv), builds a graph, 
 *  and finds minimum-cost paths from one character to another in the graph.
 * 
 * @author Yanmeng Kong(Anny)
 */
public class MarvelPaths2 {
	
	/**
	 * Builds graph using the data from the file.
	 * 
	 * @param filename file to be used to build the graph
	 * @throws Exception if fail to read data from the file,
	 * 		   IllegalArgumentException if the filename is null
	 */
	public static DirectedGraph<String, Double> buildGraph(String filename) throws Exception {
		if (filename == null) {
			throw new IllegalArgumentException("filename cannot be null.");
		}
		
		// parse the file to build graph
		Map<String, HashMap<String, Integer>> sharedBooks = 
				MarvelParser2.parseData(filename);
		
		DirectedGraph<String, Double> graph = new DirectedGraph<String, Double>();
		
		// add characters as nodes
		// Given character c, graph = {c=[]}
		for (String c : sharedBooks.keySet()) {
			graph.addNode(c);
		}
		
		// Add weighted edges between any two character nodes
		// at most one directed edge from any particular node to any other node
		// and no reflexive edges
		// For example,
		// Given sharedBooks = {1=(2,1), 2=(1,1)} where characters 1 and 2 in book a
		// we will add edges:
		// graph = {1=[(2,1.000)], 2=[(1,1.000)]}
		for (String cStart : sharedBooks.keySet()) {
			Map<String, Integer> CStartSharedBooks = sharedBooks.get(cStart);
			for (String cEnd : CStartSharedBooks.keySet()) {
				// edge's weight is the multiplicative inverse 
				// of how many books they share
				// same two nodes in the opposite direction with the same count
				double edgeWeight = 1.0 / CStartSharedBooks.get(cEnd);
				graph.addEdge(cStart, cEnd, edgeWeight);
				graph.addEdge(cEnd, cStart, edgeWeight);
			}
		}
		
//		// check no multiple edges
//		for (String node : graph.getNodes()) {
//			Set<LabeledEdge<String, Double>> edges = graph.getEdges(node);
//			for (LabeledEdge<String, Double> e : edges) {
//				for (LabeledEdge<String, Double> e2 : edges) {
//					if (e.getEnd().equals(e2.getEnd()) && e.getLabel() != e2.getLabel()) {
//						throw new Exception("duplicate edges");
//					}
//				}
//			}
//		}

		return graph;
	}
	
	/**
	 * Allows user to type in two characters and find the 
	 * shortest path of those two characters.
	 */
	public static void main(String[] args) throws Exception {
		System.out.println("Loading a DijkstraFindPath program on finding the minimum-cost path "
				+ "for two Marvel characters...");
		DirectedGraph<String, Double> g = buildGraph("src/hw7/data/marvel.tsv");
		Scanner scan = new Scanner(System.in);
		String origin, dest;
		boolean exit = true;
		
		do {
			System.out.print("Please type in your origin character: ");
			origin = scan.nextLine();
			System.out.print("Please type in your destination character: ");
			dest = scan.nextLine();
			
			// check if origin and destination nods are in the graph
			if (!g.containsNode(origin) && !g.containsNode(dest)) {
				System.out.println("Origin character " + origin + " not found.");
				System.out.println("Destination character " + dest + " not found.");
			} else if (!g.containsNode(origin)) {
				System.out.println("Origin character " + origin + " not found.");
			} else if (!g.containsNode(dest)) {
				System.out.println("Destination character " + dest + " not found.");
			} else{
				// start BFSFindPath on two nodes if they are in the graph
				String node = origin;
		        String result = "Path from " + origin + " to " + dest + ":";
		        List<LabeledEdge<String, Double>> path = DijkstraPathFinder.DijkstraFindPath(g, origin, dest);
		        
		        if (path == null) {
		        	result += "\n" + "no path found";
		    	} else {
		    		double prevCost = 0.0;
		    		path.remove(0);
		    		for (LabeledEdge<String, Double> edge : path) {
		    			result += "\n" + node + " to " + edge.getEnd() + 
		    					" with weight " + String.format("%.3f", edge.getLabel() - prevCost);
		    			prevCost = edge.getLabel();
		    			node = edge.getEnd();
		    		}
		    		result += "\n" + "total cost: " + String.format("%.3f", prevCost);
		    	}
		        System.out.println("Path found! \n" + result);
			}
			
			// ask to exit (user friendly)
			System.out.print("Do you want to exit? ");
			String answer = scan.nextLine();
			answer = answer.toLowerCase();
			
			// yes or no response == exit
			exit = answer.length() == 0 || answer.charAt(0) == 'y';
		} while(!exit);
		
		scan.close();
		System.out.println("Exit DijkstraFindPath program.");
	}
}
