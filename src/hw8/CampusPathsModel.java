package hw8;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import hw5.DirectedGraph;
import hw5.LabeledEdge;
import hw7.DijkstraPathFinder;

/**
 * <b>CampusModel</b> is the model represents the buildings and paths on the
 * campus.
 *  Providing methods for the view to access data; 
 *  Performing operations involving the data and returning the result.
 * 
 * @author Yanmeng Kong(Anny)
 */
public class CampusPathsModel {
	
	private static final double oneEighthPI = Math.PI / 8.0;

	// debug flag
	private static final boolean DEBUG = false;
	// a map for buildings'short names to full names
	private Map<String, String> shortToFull;
	// a map for buildings'full names to short names
	private Map<String, String> fullToShort;
	// a map for buildings'short names to its locations(x,y)
	private Map<String, Point> shortToBuildings;
	// a graph for campus paths
	private DirectedGraph<Point, Double> campusPaths;

	// Rep invariant:
	// shortToFull != null 
	// && fullToShort != null 
	// && shortToBuildings != null 
	// && campusPaths != null
	// && every key and value in shortToFull are not null
	// && every key and value in fullToShort are not null
	// && every key and value in shortToBuildings are not null

	// Abstract function:
	// AF(this) = a CampusPathsModel such that
	// 	this.shortToFull = a map for buildings'short names to full names;
	//	this.fullToShort = a map for buildings'full names to short names;
	//	this.shortToBuildings = a map for buildings'short names to its locations(x,y);
	//	this.campusPaths = a graph for campus paths;

	/**
	 * Constructs a campus model.
	 * 
	 * @param buildingsFileName
	 * 			  file to be used to parse buildings
	 * @param pathsFileName
	 *            file to be used to parse paths
	 * @throws Exception if fail to read data from the file,
	 * 		   IllegalArgumentException if either buildingsFileName
	 * 		   or pathsFileName is null
	 */
	public CampusPathsModel(String buildingsFileName, String pathsFileName) 
			throws Exception {

		 if (buildingsFileName == null) {
			 throw new IllegalArgumentException("buildingsFileName cannot be null");
		 }
		 if (pathsFileName == null) {
			 throw new IllegalArgumentException("pathsFileName cannot be null");
		 }
		 
		shortToFull = new HashMap<String, String>();
		fullToShort = new HashMap<String, String>();
		shortToBuildings = new HashMap<String, Point>();
		campusPaths = new DirectedGraph<Point, Double>();

		CampusParser.parseBuildings(buildingsFileName, 
				shortToFull, fullToShort, shortToBuildings);
		campusPaths = CampusParser.parsePaths(pathsFileName);
		checkRep();
	}

	/**
	 * Gets an unmodifiable map for buildings' short names and its full names
	 * 
	 * @return an unmodifiable map for buildings' short names and its full names
	 */
	public Map<String, String> getShortToFull() {
		checkRep();
		return Collections.unmodifiableMap(shortToFull);
	}

	/**
	 * Gets the building's full name of the corresponding short name.
	 * 
	 * @require shortName != null
	 * @param shortName the short name of a building on campus
	 * @return the building's full name,
	 * 		   null if shortName is not in the dataset
	 */
	public String getFullNameOf(String shortName) {
		checkRep();
		return shortToFull.get(shortName);
	}

	/**
	 * Gets the building's short name of the corresponding full name.
	 * 
	 * @require fullName != null
	 * @param fullName the full name of a building on campus
	 * @return the building's short name,
	 * 		   null if fullName is not in the dataset
	 */
	public String getShortNameOf(String fullName) {
		checkRep();
		return fullToShort.get(fullName);
	}

	/**
	 * Gets the location(Point) of the given building.
	 * 
	 * @require shortName != null
	 * @param shortName the short name of given building
	 * @return the location of given building,
	 * 		   null if shortName is not in the dataset
	 */
	public Point getBuildingOf(String shortName) {
		checkRep();
		return shortToBuildings.get(shortName);
	}
	
	/**
	 * Finds the shortest path from origin to dest point
	 * and pack the path in a map.
	 * 
	 * @param origin the origin point of the path
	 * @param dest the destination point of the path
	 * @requires origin != null && dest != null
	 * 			 && campusPaths.containsNode(origin) 
	 * 			 && campusPaths.containsNode(dest)
	 * @return the shortest path from origin to dest point
	 */
	public Map<Point, Double> findShortestPath(Point origin, Point dest) {
		checkRep();
		List<LabeledEdge<Point, Double>> dijkstraPath = 
				DijkstraPathFinder.DijkstraFindPath(campusPaths, origin, dest);

		// hide internal details of DirectedGraph and LabeledEdge
		Map<Point, Double> shortestPath = new LinkedHashMap<Point, Double>();
		for (LabeledEdge<Point, Double> segment : dijkstraPath) {
			shortestPath.put(segment.getEnd(), segment.getLabel());
		}
		checkRep();
		return shortestPath;
	}

	/**
	 * Gets the angle for the path segment p1 to p2. 
	 * 
	 * @param p1 origin point
	 * @param p2 destination point
	 * @return angle in radians of the path segment
	 */
	public double getAngle(Point p1, Point p2) {
		// get the theta
		return Math.atan2(p2.getY() - p1.getY(), p2.getX() - p1.getX());
	}

	/**
	 * Checks if representation invariant holds.
	 */
	private void checkRep() {
		if (DEBUG) {
			assert shortToFull != null : "shortToFull should not be null";
			assert fullToShort != null : "fullToShort should not be null";
			assert shortToBuildings != null : "shortToBuildings should not be null";
			assert campusPaths != null : "campusPaths should not be null";

			for (String shortName : shortToFull.keySet()) {
				assert shortName != null : "short name should not be null";
				assert shortToFull.get(shortName) != null : 
					"corresponding full name should not be null";
			}
			for (String fullName : fullToShort.keySet()) {
				assert fullName != null : "full name should not be null";
				assert fullToShort.get(fullName) != null : 
					"corresponding short name should not be null";
			}
			for (String shortName : shortToBuildings.keySet()) {
				assert shortName != null : "short name should not be null";
				assert shortToBuildings.get(shortName) != null : 
					"building's point should not be null";
			}
		}
	}
}
