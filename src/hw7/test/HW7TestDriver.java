package hw7.test;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hw5.DirectedGraph;
import hw5.LabeledEdge;
import hw5.SortByEndLabel;
import hw6.test.HW6TestDriver;
import hw7.DijkstraPathFinder;
import hw7.MarvelPaths2;


/**
 * This class implements a testing driver which reads test scripts
 * from files for your graph ADT and improved MarvelPaths application
 * using Dijkstra's algorithm.
 **/
public class HW7TestDriver extends HW6TestDriver {


    public static void main(String args[]) {
        try {
            if (args.length > 1) {
                printUsage();
                return;
            }

            HW6TestDriver td;

            if (args.length == 0) {
                td = new HW6TestDriver(new InputStreamReader(System.in),
                                       new OutputStreamWriter(System.out));
            } else {

                String fileName = args[0];
                File tests = new File (fileName);

                if (tests.exists() || tests.canRead()) {
                    td = new HW6TestDriver(new FileReader(tests),
                                           new OutputStreamWriter(System.out));
                } else {
                    System.err.println("Cannot read from " + tests.toString());
                    printUsage();
                    return;
                }
            }

            td.runTests();

        } catch (IOException e) {
            System.err.println(e.toString());
            e.printStackTrace(System.err);
        }
    }
    
    private static void printUsage() {
        System.err.println("Usage:");
        System.err.println("to read from a file: java hw7.test.HW7TestDriver <name of input script>");
        System.err.println("to read from standard in: java hw7.test.HW7TestDriver");
    }

    private final Map<String, DirectedGraph<String, Double>> graphs = 
    		new HashMap<String, DirectedGraph<String, Double>>();
    
    /**
     * @requires r != null && w != null
     *
     * @effects Creates a new HW6TestDriver which reads command from
     * <tt>r</tt> and writes results to <tt>w</tt>.
     **/
    public HW7TestDriver(Reader r, Writer w) {
    	super(r, w);
    }
    
    /**
     * @effects Executes the commands read from the input and writes results to the output
     * @throws IOException if the input or output sources encounter an IOException
     **/
    public void runTests() throws IOException {
  	  super.runTests();
    }

    @Override
    public void createGraph(String graphName) {
    	if (graphName == null) {
    		throw new IllegalArgumentException("Bad Graph Name <null>");
    	}
         graphs.put(graphName, new DirectedGraph<String, Double>());
        output.println("created graph " + graphName);
    }

    @Override
    public void addNode(String graphName, String nodeName) {
         DirectedGraph<String, Double> graph = graphs.get(graphName);
         graph.addNode(nodeName);
         output.println("added node "+ nodeName + " to " + graphName);
    }

    @Override
    public void addEdge(String graphName, String parentName, String childName,
            String edgeLabel) {
         DirectedGraph<String, Double> graph = graphs.get(graphName);
         double weight = Double.parseDouble(edgeLabel);
         graph.addEdge(parentName, childName, weight);
         String result = "added edge " + roundTo3(weight) + 
 						" from " + parentName + " to " + 
 						childName + " in " + graphName;
         output.println(result);
    }
   
    @Override
    public void listNodes(String graphName) {
    	DirectedGraph<String, Double> graph = graphs.get(graphName);
    	String result = graphName + " contains:"; 
    	
    	// sort nodes
    	List<String> sortedNodes = new ArrayList<String>();
    	sortedNodes.addAll(graph.getNodes());
    	Collections.sort(sortedNodes);
    	
        for (String n: sortedNodes) {
        	result += " " + n;
        }
        output.println(result);
    }

    @Override
    public void listChildren(String graphName, String parentName) {
    	DirectedGraph<String, Double> graph = graphs.get(graphName);
    	
    	// sort edges by ends first, labels then
    	List<LabeledEdge<String, Double>> sortedEdges = 
    			new ArrayList<LabeledEdge<String, Double>>();
    	sortedEdges.addAll(graph.getEdges(parentName));
    	Collections.sort(sortedEdges, new SortByEndLabel<String, Double>());
    	
        String result = "the children of " + parentName + " in " 
        				+ graphName + " are:";
        for (LabeledEdge<String, Double> e : sortedEdges) {
			result += " " + e.getEnd() + "(" + roundTo3(e.getLabel()) + ")";
        }
        output.println(result);
    }

    @Override
    public void loadGraph(String graphName, String filename) throws Exception {
    	filename = "src/hw7/data/" + filename;
        graphs.put(graphName, MarvelPaths2.buildGraph(filename));
        output.println("loaded graph " + graphName);
    }

    @Override
    public void findPath(String graphName, String origin, String dest) {
        DirectedGraph<String, Double> g = graphs.get(graphName);
        if (!g.containsNode(origin) && !g.containsNode(dest)) {
        	output.println("unknown character " + origin);
        	output.println("unknown character " + dest);
        } else if (!g.containsNode(origin)) {
        	output.println("unknown character " + origin);
        } else if (!g.containsNode(dest)) {
        	output.println("unknown character " + dest);
        } else {
        	String node = origin;
	        String result = "path from " + origin + " to " + dest + ":";
	        List<LabeledEdge<String, Double>> path = DijkstraPathFinder.DijkstraFindPath(g, origin, dest);
	        
	        // check if there is a path found
	        if (path == null) {
	        	result += "\n" + "no path found";
	    	} else {
	    		double lastCost = 0.0;
	    		path.remove(0);
	    		for (LabeledEdge<String, Double> edge : path) {
	    			result += "\n" + node + " to " + edge.getEnd() + 
	    					" with weight " + roundTo3(edge.getLabel()-lastCost);
	    			lastCost = edge.getLabel();
	    			node = edge.getEnd();
	    		}
	    		result += "\n" + "total cost: " + roundTo3(lastCost);
	    	}
	        output.println(result);
        }
    }
    
    /**
     * @param d double to be round to the nearest value if it has more digits
     * @return a numeric value with exactly 3 digits after the decimal point
     **/
    private String roundTo3(double d) {
    	return String.format("%.3f", d);
    }

}
