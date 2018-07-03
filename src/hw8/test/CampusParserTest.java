package hw8.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import hw5.DirectedGraph;
import hw8.CampusParser;
import hw8.Point;

/**
 * This class contains a set of test cases that can be used to test the
 * implementation of the CampusParser class.
 * <p>
 */
public class CampusParserTest {

	private Map<String, String> shortToFull;
	private Map<String, String> fullToShort;
	private Map<String, Point> shortToBuildings;
	private DirectedGraph<Point, Double> campusPaths;

	@Before
	public void setUp() throws Exception {
		shortToFull = new HashMap<String, String>();
		fullToShort = new HashMap<String, String>();
		shortToBuildings = new HashMap<String, Point>();
		campusPaths = new DirectedGraph<Point, Double>();
	}

	// test parseBuildings
	
	@Test(expected = Exception.class)
	public void testParseBuildingsWithMalformedBuildings() throws Exception{
		CampusParser.parseBuildings("src/hw8/data/malformed_buildings.dat", 
				shortToFull, fullToShort, shortToBuildings);
	}
	
	@Test
	public void testParseBuildingsWithEmptyBuildings() throws Exception {
		CampusParser.parseBuildings("src/hw8/data/empty_buildings.dat", 
				shortToFull, fullToShort, shortToBuildings);
		assertTrue(shortToFull.isEmpty());
		assertTrue(fullToShort.isEmpty());
		assertTrue(shortToBuildings.isEmpty());
		assertEquals(new HashMap<String, String>(), shortToFull);
		assertEquals(new HashMap<String, String>(), fullToShort);
		assertEquals(new HashMap<String, Point>(), shortToBuildings);
	}
	
	@Test
	public void testParseBuildingsWithTwoBuildings() throws Exception {
		CampusParser.parseBuildings("src/hw8/data/two_buildings.dat", 
				shortToFull, fullToShort, shortToBuildings);
		Map<String, String> expected1 = new HashMap<String, String>();
		Map<String, String> expected2 = new HashMap<String, String>();
		Map<String, Point> expected3 = new HashMap<String, Point>();
		expected1.put("ONE", "Building 1");
		expected1.put("TWO", "Building 2");
		expected2.put("Building 1", "ONE");
		expected2.put("Building 2", "TWO");
		expected3.put("ONE", new Point(1.0, 2.0));
		expected3.put("TWO", new Point(2.0, 3.0));
		assertEquals(expected1, shortToFull);
		assertEquals(expected2, fullToShort);
		assertEquals(expected3, shortToBuildings);
	}
	
	@Test
	public void testParseBuildingsWithTwoBuildingsAndComment() throws Exception {
		CampusParser.parseBuildings("src/hw8/data/two_buildings_comment.dat", 
				shortToFull, fullToShort, shortToBuildings);
		Map<String, String> expected1 = new HashMap<String, String>();
		Map<String, String> expected2 = new HashMap<String, String>();
		Map<String, Point> expected3 = new HashMap<String, Point>();
		expected1.put("ONE", "Building 1");
		expected1.put("TWO", "Building 2");
		expected2.put("Building 1", "ONE");
		expected2.put("Building 2", "TWO");
		expected3.put("ONE", new Point(1.0, 2.0));
		expected3.put("TWO", new Point(2.0, 3.0));
		assertEquals(expected1, shortToFull);
		assertEquals(expected2, fullToShort);
		assertEquals(expected3, shortToBuildings);
	}
	
	// test parsePaths

	@Test(expected = Exception.class)
	public void testParsePathsWithMalformedPaths() throws Exception {
		campusPaths = CampusParser.parsePaths("src/hw8/data/malformed_paths.dat");
	}
	
	@Test
	public void testParsePathsWithEmptyBuildings() throws Exception {
		campusPaths = CampusParser.parsePaths("src/hw8/data/empty_paths.dat");
		assertTrue(campusPaths.isEmpty());
		assertEquals(new DirectedGraph<Point, Double>(), campusPaths);
	}

	@Test
	public void testParsePathsWithTwoBuildings() throws Exception {
		campusPaths = CampusParser.parsePaths("src/hw8/data/two_paths.dat");
		DirectedGraph<Point, Double> exptected = new DirectedGraph<Point, Double>();
		Point origin = new Point(1.0, 2.0);
		Point dest = new Point(2.0, 3.0);
		exptected.addNode(origin);
		exptected.addNode(dest);
		exptected.addEdge(origin, dest, 10.0);
		exptected.addEdge(dest, origin, 10.0);
		assertEquals(exptected, campusPaths);
	}
	
	@Test
	public void testParsePathsWithTwoBuildingsAndComment() throws Exception {
		campusPaths = CampusParser.parsePaths("src/hw8/data/two_paths_comment.dat");
		DirectedGraph<Point, Double> exptected = new DirectedGraph<Point, Double>();
		Point origin = new Point(1.0, 2.0);
		Point dest = new Point(2.0, 3.0);
		exptected.addNode(origin);
		exptected.addNode(dest);
		exptected.addEdge(origin, dest, 10.0);
		exptected.addEdge(dest, origin, 10.0);
		assertEquals(exptected, campusPaths);
	}
}
