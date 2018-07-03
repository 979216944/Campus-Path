package hw8;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

import hw5.DirectedGraph;

/**
 * Parser utility to load the campus buildings and paths dataset.
 */
public class CampusParser {
	/**
	 * A checked exception class for bad data files
	 */
	@SuppressWarnings("serial")
	public static class MalformedDataException extends Exception {
		public MalformedDataException() {
		}

		public MalformedDataException(String message) {
			super(message);
		}

		public MalformedDataException(Throwable cause) {
			super(cause);
		}

		public MalformedDataException(String message, Throwable cause) {
			super(message, cause);
		}
	}

	/**
	 * Reads the Campus buildings dataset. 
	 * Each line has four tab-separated fields: 
	 * shortName longName x y 
	 * where shortName is the abbreviated name of a building, 
	 * longName is the full name of a building, and (x,y) is the
	 * location of the building's entrance in pixels on campus_map.jpg.
	 * 
	 * @requires filename is a valid file path
	 * @param filename
	 *            the file that will be read
	 * @param shortToFull
	 *            a map for buildings'short names to full names
	 * @param fullToShort
	 *            a map for buildings'full names to short names
	 * @param buildings
	 *            a map for buildings'short names to its locations(x,y)
	 * @modifies shortToFull, fullToShort, buildings
	 * @effects fills shortToFull with a map for buildings'short names to full
	 *          names
	 * @effects fills fullToShort with a map for buildings'full names to short
	 *          names
	 * @effects fills buildings with a map for buildings'short names to its
	 *          locations(x,y)
	 * @throws MalformedDataException
	 *             if the file is not well-formed
	 */
	public static void parseBuildings(String filename, Map<String, String> shortToFull, 
			Map<String, String> fullToShort, Map<String, Point> buildings) 
					throws MalformedDataException {

		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(filename));

			String inputLine;
			while ((inputLine = reader.readLine()) != null) {

				// Ignore comment lines.
				if (inputLine.startsWith("#")) {
					continue;
				}

				// Parse the data, stripping out quotation marks and throwing
				// an exception for malformed lines.
				inputLine = inputLine.replace("\"", "");
				String[] tokens = inputLine.split("\t");
				if (tokens.length != 4) {
					throw new MalformedDataException(
							"Line should contain exactly one tab: " + inputLine);
				}

				String shortName = tokens[0];
				String fullName = tokens[1];
				double x = Double.parseDouble(tokens[2]);
				double y = Double.parseDouble(tokens[3]);

				shortToFull.put(shortName, fullName);
				fullToShort.put(fullName, shortName);
				buildings.put(shortName, new Point(x, y));

			}
		} catch (IOException e) {
			System.err.println(e.toString());
			e.printStackTrace(System.err);
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					System.err.println(e.toString());
					e.printStackTrace(System.err);
				}
			}
		}
	}

	/**
	 * Reads the Campus paths dataset. 
	 * For each endpoint of a pathsegment, there is a line in the file 
	 * listing the pixel coordinates of that point in campus_map.jpg, 
	 * followed by a tab-indented line for each endpoint to which it is 
	 * connected with a path segment. Each indented line lists the coordinates 
	 * of the other endpoint and the distance of the segment between them in feet. 
	 * Thus, the structure is as follows: 
	 * x1,y1 
	 * 		x2,y2: distance12
	 * 		x3,y3: distance13 
	 * 		... 
	 * ... 
	 * xi,yi 
	 * 		xj,yj: distanceij 
	 * 		... 
	 * where distanceij is the distance from (xi,yi) to (xj,yj).
	 * 
	 * @requires filename is a valid file path && no duplicate non-indented
	 *           lines
	 * @param filename
	 *            file to be used to build the graph
	 * @return a graph for all paths and its distances
	 * @throws MalformedDataException
	 *             if the file is not well-formed
	 */
	public static DirectedGraph<Point, Double> parsePaths(String filename) 
			throws MalformedDataException {

		BufferedReader reader = null;
		// a graph for all paths and its distances
		DirectedGraph<Point, Double> campusPaths = new DirectedGraph<Point, Double>();
		try {
			reader = new BufferedReader(new FileReader(filename));

			String inputLine;
			Point startPoint = null;
			while ((inputLine = reader.readLine()) != null) {

				// Ignore comment lines.
				if (inputLine.startsWith("#")) {
					continue;
				}

				// Parse the data, stripping out quotation marks and tabs
				inputLine = inputLine.replace("\"", "").replace("\t", "");
				String[] tokens = inputLine.split(": ");
				String[] xyData = tokens[0].split(",");
				Point point = new Point(Double.parseDouble(xyData[0]), 
										Double.parseDouble(xyData[1]));

				if (tokens.length == 1) { // non-indented line
					// add the new start point
					// then update start point
					campusPaths.addNode(point);
					startPoint = point;
				} else if (tokens.length == 2) { // indented line
					// indented line comes first--not well-formed
					if (startPoint == null) {
						throw new MalformedDataException(
								"File should contain non-indented line first");
					}
					// add the new end point and corresponding startPoint-point
					// edge
					campusPaths.addNode(point);
					campusPaths.addEdge(startPoint, point, Double.parseDouble(tokens[1]));
				} else {
					// throw an exception for malformed lines.
					throw new MalformedDataException(
							"Line should contain zero or one tab: " + inputLine);
				}
			}
		} catch (IOException e) {
			System.err.println(e.toString());
			e.printStackTrace(System.err);
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					System.err.println(e.toString());
					e.printStackTrace(System.err);
				}
			}
		}
		return campusPaths;
	}
}