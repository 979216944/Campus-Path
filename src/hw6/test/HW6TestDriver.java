package hw6.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import hw5.DirectedGraph;
import hw5.LabeledEdge;
import hw5.SortByEndLabel;
import hw6.MarvelPaths;

/**
 * This class implements a testing driver which reads test scripts
 * from files for testing Graph.
 **/
public class HW6TestDriver {

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
        System.err.println("to read from a file: java hw6.test.HW6TestDriver <name of input script>");
        System.err.println("to read from standard in: java hw6.test.HW6TestDriver");
    }

    /** String -> Graph: maps the names of graphs to the actual graph **/
    //TODO for the student: Parameterize the next line correctly.
    private final Map<String, DirectedGraph<String, String>> graphs = 
    		new HashMap<String, DirectedGraph<String, String>>();
    protected final PrintWriter output;
    protected final BufferedReader input;

    /**
     * @requires r != null && w != null
     *
     * @effects Creates a new HW6TestDriver which reads command from
     * <tt>r</tt> and writes results to <tt>w</tt>.
     **/
    public HW6TestDriver(Reader r, Writer w) {
        input = new BufferedReader(r);
        output = new PrintWriter(w);
    }

    /**
     * @effects Executes the commands read from the input and writes results to the output
     * @throws IOException if the input or output sources encounter an IOException
     **/
    public void runTests()
        throws IOException
    {
        String inputLine;
        while ((inputLine = input.readLine()) != null) {
            if ((inputLine.trim().length() == 0) ||
                (inputLine.charAt(0) == '#')) {
                // echo blank and comment lines
                output.println(inputLine);
            }
            else
            {
                // separate the input line on white space
                StringTokenizer st = new StringTokenizer(inputLine);
                if (st.hasMoreTokens()) {
                    String command = st.nextToken();

                    List<String> arguments = new ArrayList<String>();
                    while (st.hasMoreTokens()) {
                        arguments.add(st.nextToken());
                    }

                    executeCommand(command, arguments);
                }
            }
            output.flush();
        }
    }

    protected void executeCommand(String command, List<String> arguments) {
        try {
            if (command.equals("CreateGraph")) {
                createGraph(arguments);
            } else if (command.equals("AddNode")) {
                addNode(arguments);
            } else if (command.equals("AddEdge")) {
                addEdge(arguments);
            } else if (command.equals("ListNodes")) {
                listNodes(arguments);
            } else if (command.equals("ListChildren")) {
                listChildren(arguments);
            } else if (command.equals("LoadGraph")) {  
            	loadGraph(arguments);
            } else if (command.equals("FindPath")) {
            	findPath(arguments);
            } else {
                output.println("Unrecognized command: " + command);
            }
        } catch (Exception e) {
            output.println("Exception: " + e.toString());
        }
    }

    protected void createGraph(List<String> arguments) {
        if (arguments.size() != 1) {
            throw new CommandException("Bad arguments to CreateGraph: " + arguments);
        }

        String graphName = arguments.get(0);
        createGraph(graphName);
    }

    protected void createGraph(String graphName) {
        // Insert your code here.
    	if (graphName == null) {
    		throw new IllegalArgumentException("Bad Graph Name <null>");
    	}
         graphs.put(graphName, new DirectedGraph<String, String>());
        output.println("created graph " + graphName);
    }

    protected void addNode(List<String> arguments) {
        if (arguments.size() != 2) {
            throw new CommandException("Bad arguments to addNode: " + arguments);
        }

        String graphName = arguments.get(0);
        String nodeName = arguments.get(1);

        addNode(graphName, nodeName);
    }

    protected void addNode(String graphName, String nodeName) {
        // Insert your code here.
         DirectedGraph<String, String> graph = graphs.get(graphName);
         graph.addNode(nodeName);
         output.println("added node "+ nodeName + " to " + graphName);
    }

    protected void addEdge(List<String> arguments) {
        if (arguments.size() != 4) {
            throw new CommandException("Bad arguments to addEdge: " + arguments);
        }

        String graphName = arguments.get(0);
        String parentName = arguments.get(1);
        String childName = arguments.get(2);
        String edgeLabel = arguments.get(3);

        addEdge(graphName, parentName, childName, edgeLabel);
    }

    protected void addEdge(String graphName, String parentName, String childName,
            String edgeLabel) {
        // Insert your code here.
         DirectedGraph<String, String> graph = graphs.get(graphName);
         graph.addEdge(parentName, childName, edgeLabel);
         String result = "added edge " + edgeLabel + 
 						" from " + parentName + " to " + 
 						childName + " in " + graphName;
         output.println(result);
    }

    protected void listNodes(List<String> arguments) {
        if (arguments.size() != 1) {
            throw new CommandException("Bad arguments to listNodes: " + arguments);
        }

        String graphName = arguments.get(0);
        listNodes(graphName);
    }

    protected void listNodes(String graphName) {
        // Insert your code here.
    	DirectedGraph<String, String> graph = graphs.get(graphName);
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

    protected void listChildren(List<String> arguments) {
        if (arguments.size() != 2) {
            throw new CommandException("Bad arguments to listChildren: " + arguments);
        }

        String graphName = arguments.get(0);
        String parentName = arguments.get(1);
        listChildren(graphName, parentName);
    }

    protected void listChildren(String graphName, String parentName) {
        // Insert your code here.
    	DirectedGraph<String, String> graph = graphs.get(graphName);
    	
    	// sort edges by ends first, labels then
    	List<LabeledEdge<String, String>> sortedEdges = 
    			new ArrayList<LabeledEdge<String, String>>();
    	sortedEdges.addAll(graph.getEdges(parentName));
    	Collections.sort(sortedEdges, new SortByEndLabel<String, String>());
    	
        String result = "the children of " + parentName + " in " 
        				+ graphName + " are:";
        for (LabeledEdge<String, String> e : sortedEdges) {
			result += " " + e.getEnd() + "(" + e.getLabel() + ")";
        }
        output.println(result);
    }

    protected void loadGraph(List<String> arguments) throws Exception {
        if (arguments.size() != 2) {
            throw new CommandException("Bad arguments to loadGraph: " + arguments);
        }

        String graphName = arguments.get(0);
        String filename = arguments.get(1);
        loadGraph(graphName, filename);
    }

    protected void loadGraph(String graphName, String filename) throws Exception {
    	filename = "src/hw6/data/" + filename;
        graphs.put(graphName, MarvelPaths.buildGraph(filename));
        output.println("loaded graph " + graphName);
    }

    protected void findPath(List<String> arguments) {
        if (arguments.size() != 3) {
            throw new CommandException("Bad arguments to findPath: " + arguments);
        }

        String graphName = arguments.get(0);
        String origin = arguments.get(1).replace('_', ' ');
        String dest = arguments.get(2).replace('_', ' ');
        findPath(graphName, origin, dest);
    }

    protected void findPath(String graphName, String origin, String dest) {
        DirectedGraph<String, String> g = graphs.get(graphName);
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
	        List<LabeledEdge<String, String>> path = MarvelPaths.BFSFindPath(g, origin, dest);
	        
	        // check if there is a path found
	        if (path == null) {
	        	result += "\n" + "no path found";
	    	} else {
	    		for (LabeledEdge<String, String> edge : path) {
	    			result += "\n" + node + " to " + edge.getEnd() + " via " + edge.getLabel();
	    			node = edge.getEnd();
	    		}
	    	}
	        output.println(result);
        }
    }

    /**
     * This exception results when the input file cannot be parsed properly
     **/
    static class CommandException extends RuntimeException {

        public CommandException() {
            super();
        }
        public CommandException(String s) {
            super(s);
        }

        public static final long serialVersionUID = 3495;
    }
}
