package hw6;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import hw5.DirectedGraph;
import hw5.LabeledEdge;
import hw5.SortByEndLabel;

/**
 * This class reads the Marvel data from a file (marvel.tsv), builds a graph, 
 *  and finds paths between characters in the graph.
 * 
 * @author Yanmeng Kong(Anny)
 */
public class MarvelPaths {
	
	/**
	 * Builds a graph from a file filename
	 * 
	 * @param filename filename of the file 
	 * @return a DirectedGraph built from the data given
	 * @throws Exception if fail to read data from filename
	 * 		   IllegalArgumentException if the filename is null
	 */
	public static DirectedGraph<String, String> buildGraph(String filename) throws Exception {
		if (filename == null) {
			throw new IllegalArgumentException("filename null");
		}
		
		DirectedGraph<String, String> graph = new DirectedGraph<String, String>();
		Set<String> characters = new HashSet<String>();
		Map<String, List<String>> books = new HashMap<String, List<String>>();
		MarvelParser.parseData(filename, characters, books);
		
		// add characters as nodes
		// Given character c, graph = {c=[]}
		for (String c : characters) {
			graph.addNode(c);
		}
		
		// add edges 
		// Given (1, 2, 3) in book a,
		// we will add edges:
		// graph = {1=[(2,a), (3,a)], 2=[(1,a), (3,a)], 3=[(1,a), (2,a)]
		for (String book : books.keySet()) {
			for (String cStart : books.get(book)) {
				for (String cEnd : books.get(book)) {
					// no reflexive edges
					if (!cStart.equals(cEnd)) {
						graph.addEdge(cStart, cEnd, book);
					}
				}
			}
		}
		return graph;
	}
	
	/**
	 * Find a shortest path between two characters in the graph via breadth-first search.
	 * 
	 * @param g g two nodes origin and dest in
	 * @param origin origin of the path
	 * @param dest destination of the path
	 * @requires g != null && origin != null && dest != null 
	 * 	&& g.containsNode(origin) && g.containsNode(dest)
	 * @return the shortest path from origin to dest if any,
	 * 		   null if no path found
	 */
	public static List<LabeledEdge<String, String>> BFSFindPath(
			DirectedGraph<String, String> g, String origin, String dest) {
		// nodes to visit
		List<String> nodesToVisit = new LinkedList<String>();
		
		// Each key in M is a visited node.
		// Each value is a path from start to that node.
		Map<String, ArrayList<LabeledEdge<String, String>>> paths = 
				new HashMap<String, ArrayList<LabeledEdge<String, String>>>();
		
		// add origin to the queue nodesToVisit
		nodesToVisit.add(origin);
		paths.put(origin, new ArrayList<LabeledEdge<String, String>>());
		
		while (!nodesToVisit.isEmpty()) {
			String node = ((LinkedList<String>) nodesToVisit).removeFirst();
			if (node.equals(dest)) {
				return new ArrayList<LabeledEdge<String, String>>(paths.get(node));
			}
			
			// sort edges lexicographically(ends first, labels then)
			List<LabeledEdge<String, String>> edges =new ArrayList<LabeledEdge<String, String>>();
			edges.addAll(g.getEdges(node));
			Collections.sort(edges, new SortByEndLabel<String, String>());
			
			for (LabeledEdge<String, String> e : edges) {
				String endNode = e.getEnd();
				// check if the node is already visited
				if (!paths.containsKey(endNode)) {
					// append new edge to the path
					List<LabeledEdge<String, String>> path = 
							new ArrayList<LabeledEdge<String, String>>(paths.get(node));
					path.add(e);
					paths.put(endNode, (ArrayList<LabeledEdge<String, String>>) path);
					// update next node to visit
					nodesToVisit.add(endNode);
				}
			}
		}
		// no path found from origin to dest
		return null;
	}
	
	/**
	 * Allows user to type in two characters and find the 
	 * shortest path of those two characters.
	 */
	public static void main(String[] args) throws Exception {
		System.out.println("Loading a BFSFindPath program on finding the shortest path "
				+ "for two Marvel characters...");
		DirectedGraph<String, String> g = buildGraph("src/hw6/data/marvel.tsv");
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
		        List<LabeledEdge<String, String>> path = BFSFindPath(g, origin, dest);
		        
		        if (path == null) {
		        	result += "\n" + "no path found";
		    	} else {
		    		for (LabeledEdge<String, String> edge : path) {
		    			result += "\n\t" + node + " to " + edge.getEnd() + 
		    					" via " + edge.getLabel();
		    			node = edge.getEnd();
		    		}
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
		System.out.println("Exit BFSFindPath program.");
	}
}