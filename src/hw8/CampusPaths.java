package hw8;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * This class reads the campus buildings and paths data from two files
 * (campus_buildings.dat, campus_paths.dat), and provides a route-finding tool
 * that take the names of two buildings and generate directions for the shortest
 * walking route between them.
 * 
 * @author Yanmeng Kong(Anny)
 */
public class CampusPaths {

	private static final double oneEighthPI = Math.PI / 8.0;
	
	/**
	 * Shows the prompt.
	 */
	private static void showPrompt() {
		System.out.print("Enter an option ('m' to see the menu): ");
	}
	
	/**
	 * Shows the menu options.
	 */
	private static void showMenu() {
		String menu = "Menu:\n" 
				+ "\tr to find a route\n" 
				+ "\tb to see a list of all buildings\n" 
				+ "\tq to quit\n";
		System.out.println(menu);
	}
	
	/**
	 * Gets the direction for the path segment. 
	 * There are 8 direction sectors: E, NE, N, NW, SE, S, SW, W.
	 * Points that fall exactly on a boundary between sectors are 
	 * classified as a single-letter direction (N, E, S, or W). 
	 * 
	 * @param theta the angle used to determine the direction
	 * @return direction the path segment falls in
	 */
	private static String getDirection(double theta) {
		if (theta >= -oneEighthPI && theta <= oneEighthPI) {
			return "E";
		} else if (theta > -3 * oneEighthPI && theta < -oneEighthPI) {
			return "NE";
		} else if (theta >= -5 * oneEighthPI && theta <= -3 * oneEighthPI) {
			return "N";
		} else if (theta > -7 * oneEighthPI && theta < -5 * oneEighthPI) {
			return "NW";
		} else if (theta > oneEighthPI && theta < 3 * oneEighthPI) {
			return "SE";
		} else if (theta >= 3 * oneEighthPI && theta <= 5 * oneEighthPI) {
			return "S";
		} else if (theta > 5 * oneEighthPI && theta < 7 * oneEighthPI) {
			return "SW";
		}
		return "W";
	}

	/**
	 * Gets the shortest path from one building to another.
	 * 
	 * @requires model != null && originShort != null && 
	 * 			 destShort != null
	 * @param model
	 *            model of the CampusPathsModel
	 * @param originShort
	 *            short name of the origin building
	 * @param destShort
	 *            short name of the destination building
	 */
	private static void getShortestPath(CampusPathsModel model, 
			String originShort, String destShort) {
		// get buildings' points on the map
		Point originPoint = model.getBuildingOf(originShort);
		Point destPoint = model.getBuildingOf(destShort);
		if (originPoint == null && destPoint == null) {
			System.out.println("Unknown building: " + originShort);
			System.out.println("Unknown building: " + destShort + "\n");
		} else if (originPoint == null) {
			System.out.println("Unknown building: " + originShort + "\n");
		} else if (destPoint == null) {
			System.out.println("Unknown building: " + destShort + "\n");
		} else {
			String result = "Path from " + model.getFullNameOf(originShort) + 
					" to " + model.getFullNameOf(destShort) + ":\n";
			// get the shortest path
			Map<Point, Double> shortestPath = 
					model.findShortestPath(originPoint, destPoint);
			// initialize the previous distance
			double prevDistance = 0.0;

			// get all points along the shortest path 
			List<Point> points = new ArrayList<Point>(shortestPath.keySet());
			// get rid of the origin point
			points.remove(0);

			Point prevPoint = originPoint;
			for (Point point : points) {
				double destX = point.getX();
				double destY = point.getY();
				double distance = shortestPath.get(point);

				// get the angle in radians
				double theta = model.getAngle(prevPoint, point);
				// append the path segment
				result += String.format("\tWalk %.0f feet %s to (%.0f, %.0f)\n", 
						(distance - prevDistance), getDirection(theta), destX, destY);

				// update
				prevPoint = point;
				prevDistance = distance;
			}
			// append the total distance
			result += String.format("Total distance: %.0f feet\n", prevDistance);
			System.out.println(result);
		}
	}
	
	/**
	 * Lists all buildings in the form 
	 * short name: long name. 
	 * Buildings are listed in alphabetical order of short name.
	 * 
	 * @requires model != null
	 * @param model
	 *            model of the CampusPathsModel
	 */
	private static void listAllBuildings(CampusPathsModel model) {
		
		String result = "Buildings:\n";
		// get buildings'names(short name to full name)
		Map<String, String> shortToFull = model.getShortToFull();
		
		// sort in alphabetical order of short name.
		List<String> shortNames = new ArrayList<String>(shortToFull.keySet());
		Collections.sort(shortNames);
		
		for (String shortName : shortNames) {
			result += "\t" + shortName + ": " + shortToFull.get(shortName) + "\n";
		}
		
		System.out.println(result);
	}

	/**
	 * Allows user to type in two buildings and find the 
	 * shortest path of those two buildings. It repeatedly 
	 * prompts the user for one of the following one-character 
	 * commands: b, r, q, m.
	 */
	public static void main(String[] args) {
		try {
			// initialize model
			CampusPathsModel model = new CampusPathsModel(
					"src/hw8/data/campus_buildings.dat",
					"src/hw8/data/campus_paths.dat");

			showMenu();
			showPrompt();
			
			Scanner scan = new Scanner(System.in);
			
			while (true) {
				String option = scan.nextLine();

				// echo empty input lines or lines beginning with #
				if (option.isEmpty() || option.startsWith("#")) {
					System.out.println(option);
					continue;
				}

				switch (option) {
				case "m":
					showMenu();
					break;
				case "r":
					System.out.print("Abbreviated name of starting building: ");
					String originShort = scan.nextLine();
					System.out.print("Abbreviated name of ending building: ");
					String destShort = scan.nextLine();
					getShortestPath(model, originShort, destShort);
					break;
				case "b":
					listAllBuildings(model);
					break;
				case "q":
					scan.close();
					return;
				default:
					System.out.println("Unknown option\n");
				}
				
				showPrompt();
			}
		} catch (Exception e) {
			System.err.println(e.toString());
			e.printStackTrace(System.err);
		}
	}
}
