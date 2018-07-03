package hw9;

import hw8.CampusPathsModel;

/**
 * Allows user to select two buildings and find the shortest path of those two
 * buildings.
 * 
 * @author Yanmeng Kong(Anny)
 *
 */
public class CampusPathsMain {

	public static void main(String[] args) throws Exception {
		// initialize model
		CampusPathsModel model = new CampusPathsModel(
				"src/hw8/data/campus_buildings.dat",
				"src/hw8/data/campus_paths.dat");

		// initialize GUI
		new CampusPathsGUI(model);
	}

}
