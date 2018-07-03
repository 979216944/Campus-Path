package hw8.test;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Test;

import hw8.CampusPathsModel;
import hw8.Point;

/**
 * This class contains a set of test cases that can be used to test the
 * implementation of the CampusPathsModel class.
 * <p>
 */
public class CampusPathsModelTest {

	// test constructor
	
	@Test
	public void testConstructorWithColor() throws Exception {
		CampusPathsModel m = new CampusPathsModel(
				"src/hw8/data/color_buildings.dat", 
				"src/hw8/data/color_paths.dat");
		assertTrue(m != null);
	}
	
	@Test(expected = Exception.class)
	public void testConstructorWithMalformedBuildings() throws Exception {
		new CampusPathsModel("src/hw8/data/malformed_buildings.dat", 
				             "src/hw8/data/color_paths.dat");
	}
	
	@Test(expected = Exception.class)
	public void testConstructorWithMalformedPaths() throws Exception {
		new CampusPathsModel("src/hw8/data/color_buildings.dat", 
							 "src/hw8/data/malformed_paths.dat");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testConstructorWithFirstNull() throws Exception {
		new CampusPathsModel(null, "src/hw8/data/color_paths.dat");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testConstructorWithSecondNull() throws Exception {
		new CampusPathsModel("src/hw8/data/color_buildings.dat", null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testConstructorWithBothNull() throws Exception {
		new CampusPathsModel(null, null);
	}
	
	// test getShortToFull
	
	@Test
	public void testGetShortToFullWithEmptyBuildingsAndPaths() throws Exception {
		CampusPathsModel m = new CampusPathsModel(
				"src/hw8/data/empty_buildings.dat", "src/hw8/data/empty_paths.dat");
		assertEquals(new HashMap<String, String>(), m.getShortToFull());
	}
	
	@Test
	public void testGetShortToFullWithTwoBuildingsAndPaths() throws Exception {
		CampusPathsModel m = new CampusPathsModel(
				"src/hw8/data/two_buildings.dat", "src/hw8/data/two_paths.dat");
		Map<String, String> expected = new HashMap<String, String>();
		expected.put("ONE", "Building 1");
		expected.put("TWO", "Building 2");
		assertEquals(expected, m.getShortToFull());
	}
	
	@Test
	public void testGetShortToFullWithColorBuildingsAndPaths() throws Exception {
		CampusPathsModel m = new CampusPathsModel(
				"src/hw8/data/color_buildings.dat", "src/hw8/data/color_paths.dat");
		
		Map<String, String> expected = new HashMap<String, String>();
		expected.put("R", "RED");
		expected.put("B", "BLUE");
		expected.put("G", "GREEN");
		expected.put("P", "PURPLE");
		expected.put("Y", "YELLOW");
		expected.put("W", "WHITE");
		assertEquals(expected, m.getShortToFull());
	}
	
	// test getFullNameOf
	
	@Test
	public void testGetFullNameOfWithEmptyBuildingsAndPaths() throws Exception {
		CampusPathsModel m = new CampusPathsModel(
				"src/hw8/data/empty_buildings.dat", "src/hw8/data/empty_paths.dat");
		assertEquals(null, m.getFullNameOf("empty"));
	}
	
	@Test
	public void testGetFullNameOfWithTwoBuildingsAndPaths() throws Exception {
		CampusPathsModel m = new CampusPathsModel(
				"src/hw8/data/two_buildings.dat", "src/hw8/data/two_paths.dat");
		assertEquals("Building 1", m.getFullNameOf("ONE"));
		assertEquals("Building 2", m.getFullNameOf("TWO"));
	}
	
	@Test
	public void testGetFullNameOfWithColorBuildingsAndPaths() throws Exception {
		CampusPathsModel m = new CampusPathsModel(
				"src/hw8/data/color_buildings.dat", "src/hw8/data/color_paths.dat");
		assertEquals("RED", m.getFullNameOf("R"));
		assertEquals("BLUE", m.getFullNameOf("B"));
		assertEquals("GREEN", m.getFullNameOf("G"));
		assertEquals("PURPLE", m.getFullNameOf("P"));
		assertEquals("YELLOW", m.getFullNameOf("Y"));
		assertEquals("WHITE", m.getFullNameOf("W"));
	}
	
	// test getShortNameOf
	
	@Test
	public void testGetShortNameOfWithEmptyBuildingsAndPaths() throws Exception {
		CampusPathsModel m = new CampusPathsModel(
				"src/hw8/data/empty_buildings.dat", "src/hw8/data/empty_paths.dat");
		assertEquals(null, m.getShortNameOf("empty"));
	}
	
	@Test
	public void testGetShortNameOfWithTwoBuildingsAndPaths() throws Exception {
		CampusPathsModel m = new CampusPathsModel(
				"src/hw8/data/two_buildings.dat", "src/hw8/data/two_paths.dat");
		assertEquals("ONE", m.getShortNameOf("Building 1"));
		assertEquals("TWO", m.getShortNameOf("Building 2"));
	}
	
	@Test
	public void testGetShortNameOfWithColorBuildingsAndPaths() throws Exception {
		CampusPathsModel m = new CampusPathsModel(
				"src/hw8/data/color_buildings.dat", "src/hw8/data/color_paths.dat");
		assertEquals("R", m.getShortNameOf("RED"));
		assertEquals("B", m.getShortNameOf("BLUE"));
		assertEquals("G", m.getShortNameOf("GREEN"));
		assertEquals("P", m.getShortNameOf("PURPLE"));
		assertEquals("Y", m.getShortNameOf("YELLOW"));
		assertEquals("W", m.getShortNameOf("WHITE"));
	}

	// test getBuildingOf
	
	@Test
	public void testGetBuildingOfWithEmptyBuildingsAndPaths() throws Exception {
		CampusPathsModel m = new CampusPathsModel(
				"src/hw8/data/empty_buildings.dat", "src/hw8/data/empty_paths.dat");
		assertEquals(null, m.getBuildingOf("empty"));
	}
	
	@Test
	public void testGetBuildingOfWithTwoBuildingsAndPaths() throws Exception {
		CampusPathsModel m = new CampusPathsModel(
				"src/hw8/data/two_buildings.dat", "src/hw8/data/two_paths.dat");
		assertEquals(new Point(1.0, 2.0), m.getBuildingOf("ONE"));
		assertEquals(new Point(2.0, 3.0), m.getBuildingOf("TWO"));
	}
	
	@Test
	public void testGetBuildingOfWithColorBuildingsAndPaths() throws Exception {
		CampusPathsModel m = new CampusPathsModel(
				"src/hw8/data/color_buildings.dat", "src/hw8/data/color_paths.dat");
		assertEquals(new Point(0.0, 0.0), m.getBuildingOf("R"));
		assertEquals(new Point(1.0, 1.0), m.getBuildingOf("B"));
		assertEquals(new Point(2.0, 2.0), m.getBuildingOf("G"));
		assertEquals(new Point(3.0, 3.0), m.getBuildingOf("P"));
		assertEquals(new Point(4.0, 4.0), m.getBuildingOf("Y"));
		assertEquals(new Point(5.0, 5.0), m.getBuildingOf("W"));
	}
	
	// test findShortestPath

	@Test
	public void testFindShortestPathWithTwoBuildingsAndPathsFromOneToTwo() throws Exception {
		CampusPathsModel m = new CampusPathsModel(
				"src/hw8/data/two_buildings.dat", "src/hw8/data/two_paths.dat");
		
		Point origin = new Point(1.0, 2.0);
		Point dest = new Point(2.0, 3.0);
		Map<Point, Double> expected = new LinkedHashMap<Point, Double>();
		expected.put(origin, 0.0);
		expected.put(dest, 10.0);
		assertEquals(expected, m.findShortestPath(origin, dest));
	}
	
	@Test
	public void testFindShortestPathWithTwoBuildingsAndPathsFromTwoToOne() throws Exception {
		CampusPathsModel m = new CampusPathsModel(
				"src/hw8/data/two_buildings.dat", "src/hw8/data/two_paths.dat");
		
		Point origin = new Point(2.0, 3.0);
		Point dest = new Point(1.0, 2.0);
		Map<Point, Double> expected = new LinkedHashMap<Point, Double>();
		expected.put(origin, 0.0);
		expected.put(dest, 10.0);
		assertEquals(expected, m.findShortestPath(origin, dest));
	}

	@Test
	public void testFindShortestPathWithColorBuildingsAndPathsFromRedToWhite() throws Exception {
		CampusPathsModel m = new CampusPathsModel(
				"src/hw8/data/color_buildings.dat", "src/hw8/data/color_paths.dat");
		
		Point origin = m.getBuildingOf("R");
		Point dest = m.getBuildingOf("W");
		Point blue = m.getBuildingOf("B");
		Map<Point, Double> expected = new LinkedHashMap<Point, Double>();
		expected.put(origin, 0.0);
		expected.put(blue, 1.0);
		expected.put(dest, 5.0);
		assertEquals(expected, m.findShortestPath(origin, dest));
	}
}
