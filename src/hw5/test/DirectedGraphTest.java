package hw5.test;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import hw4.test.SpecificationTests;
import hw5.DirectedGraph;
import hw5.LabeledEdge;

/**
 * This class contains a set of test cases that can be used to test the
 * implementation of the DirectedGraph class.
 * <p>
 */
public class DirectedGraphTest {

	private final String NODE_A = "a";
	private final String NODE_B = "b";
	private final String NODE_C = "c";
	private final String NODE_EMPTY = "";
	private final String NODE_X = "x";

	private final String LABEL_A = "aa";
	private final String LABEL_B = "bb";
	private final String LABEL_C = "cc";
	private final String LABEL_AB = "ab";
	private final String LABEL_BC = "bc";
	private final String LABEL_BA = "ba";
	private final String LABEL_CA = "ca";

	private final String LABEL_AB2 = "ab2";

	private DirectedGraph<String, String> graph, graph1, graph2, graph3;
	private Set<String> nodes, nodes1, nodes2, nodes3;
	private Set<LabeledEdge<String, String>> edges, edges3;

	/**
	 * checks that Java asserts are enabled, and exits if not
	 */
	@Before
	public void testAssertsEnabled() {
		SpecificationTests.checkAssertsEnabled();
	}

	// easier way to construct an empty graph
	private static DirectedGraph<String, String> graph() {
		return new DirectedGraph<String, String>();
	}

	// convenient way to make a DirectedGraph with node n
	private static DirectedGraph<String, String> graph(String n) {
		DirectedGraph<String, String> g = graph();
		g.addNode(n);
		return g;

	}

	// SetUp Method depends on Directed addNode and addEdge
	@Before
	public void setUp() {
		// empty graph
		graph = graph();
		nodes = new HashSet<String>();
		edges = new HashSet<LabeledEdge<String, String>>();

		// graph1 with one empty node
		graph1 = graph(NODE_EMPTY);
		nodes1 = new HashSet<String>();
		nodes1.add(NODE_EMPTY);

		// graph2 with one node
		graph2 = graph(NODE_A);
		nodes2 = new HashSet<String>();
		nodes2.add(NODE_A);

		// graph3 is a reflexive and symmetric graph
		// {a=[(a,aa), (b,ab)],
		// b=[(b,bb), (c,bc)],
		// c=[(c,cc), (a,ca)]}
		graph3 = graph();
		nodes3 = new HashSet<String>();
		edges3 = new HashSet<LabeledEdge<String, String>>();
		graph3.addNode(NODE_A);
		graph3.addNode(NODE_B);
		graph3.addNode(NODE_C);
		graph3.addEdge(NODE_A, NODE_A, LABEL_A);
		graph3.addEdge(NODE_B, NODE_B, LABEL_B);
		graph3.addEdge(NODE_C, NODE_C, LABEL_C);
		graph3.addEdge(NODE_A, NODE_B, LABEL_AB);
		graph3.addEdge(NODE_B, NODE_C, LABEL_BC);
		graph3.addEdge(NODE_C, NODE_A, LABEL_CA);
		nodes3.add(NODE_A);
		nodes3.add(NODE_B);
		nodes3.add(NODE_C);
		edges3.add(new LabeledEdge<String, String>(NODE_A, LABEL_A));
		edges3.add(new LabeledEdge<String, String>(NODE_B, LABEL_B));
		edges3.add(new LabeledEdge<String, String>(NODE_C, LABEL_C));
		edges3.add(new LabeledEdge<String, String>(NODE_B, LABEL_AB));
		edges3.add(new LabeledEdge<String, String>(NODE_C, LABEL_BC));
		edges3.add(new LabeledEdge<String, String>(NODE_A, LABEL_CA));
	}

	///////////////////////////////////////////////////////////////////////////////////////
	//// Constructor Test
	///////////////////////////////////////////////////////////////////////////////////////
	@Test
	public void testNonNullAfterConstructed() {
		assertTrue(graph != null);
		assertTrue(graph1 != null);
		assertTrue(graph2 != null);
		assertTrue(graph3 != null);
	}

	@Test
	public void testSizeAfterConstructed() {
		assertEquals(0, graph.size());
		assertEquals(1, graph1.size());
		assertEquals(1, graph2.size());
		assertEquals(3, graph3.size());
	}

	@Test
	public void testIsEmptyAfterConstructed() {
		assertTrue(graph.isEmpty());
		assertFalse(graph1.isEmpty());
		assertFalse(graph2.isEmpty());
		assertFalse(graph3.isEmpty());
	}

	@Test
	public void testGetNodesAfterConstructed() {
		assertEquals(nodes, graph.getNodes());
		assertEquals(nodes1, graph1.getNodes());
		assertEquals(nodes2, graph2.getNodes());
		assertEquals(nodes3, graph3.getNodes());
	}

	@Test
	public void testToStringAfterConstructed() {
		assertEquals("{}", graph.toString());
		assertEquals("{=[]}", graph1.toString());
		assertEquals("{a=[]}", graph2.toString());
		assertEquals("{a=[(a,aa), (b,ab)], b=[(b,bb), (c,bc)], " + "c=[(c,cc), (a,ca)]}", graph3.toString());
	}
	///////////////////////////////////////////////////////////////////////////////////////
	//// Null(Exception) Test
	//// --------------------------------------
	//// null tests:
	//// containsNode, containsEdge,
	//// getEdges,
	//// addNode, removeNode,
	//// addEdge, removeEdge
	//// --------------------------------------
	//// node not present tests:
	//// getEdges, addEdge, removeEdge
	////
	///////////////////////////////////////////////////////////////////////////////////////

//	// containsNode null test
//	@Test(expected = IllegalArgumentException.class)
//	public void testContainsNodeWithNull() {
//		graph.containsNode(null);
//	}
//
//	// 6 ContainsEdge null tests
//	@Test(expected = IllegalArgumentException.class)
//	public void testContainsEdgeWithNullStart() {
//		graph.containsEdge(null, NODE_A, LABEL_A);
//	}
//
//	@Test(expected = IllegalArgumentException.class)
//	public void testContainsEdgeWithNullEnd() {
//		graph.containsEdge(NODE_A, null, LABEL_A);
//	}
//
//	@Test(expected = IllegalArgumentException.class)
//	public void testContainsEdgeWithNullLabel() {
//		graph.containsEdge(NODE_A, NODE_A, null);
//	}
//
//	@Test(expected = IllegalArgumentException.class)
//	public void testContainsEdgeWithNullStartEnd() {
//		graph.containsEdge(null, null, NODE_A);
//	}
//
//	@Test(expected = IllegalArgumentException.class)
//	public void testContainsEdgeWithNullStartLabel() {
//		graph.containsEdge(null, NODE_A, null);
//	}
//
//	@Test(expected = IllegalArgumentException.class)
//	public void testContainsEdgeWithNullENDLabel() {
//		graph.containsEdge(NODE_A, null, null);
//	}
//
//	@Test(expected = IllegalArgumentException.class)
//	public void testContainsEdgeWithNullStartEndLabel() {
//		graph.containsEdge(null, null, null);
//	}
//
//	// getEdges null test
//	@Test(expected = IllegalArgumentException.class)
//	public void testGetEdgesWithNull() {
//		graph.getEdges(null);
//	}
//
//	// addNode null test
//	@Test(expected = IllegalArgumentException.class)
//	public void testAddNodeWithNull() {
//		graph.addNode(null);
//	}
//
//	// removeNode null test
//	@Test(expected = IllegalArgumentException.class)
//	public void testRemoveNodeWithNull() {
//		graph.removeNode(null);
//	}
//
//	// 6 addEdge null tests
//	@Test(expected = IllegalArgumentException.class)
//	public void testAddEdgeWithNullStart() {
//		graph.addEdge(null, NODE_A, LABEL_A);
//	}
//
//	@Test(expected = IllegalArgumentException.class)
//	public void testAddEdgeWithNullEnd() {
//		graph.addEdge(NODE_A, null, LABEL_A);
//	}
//
//	@Test(expected = IllegalArgumentException.class)
//	public void testAddEdgeWithNullLabel() {
//		graph.addEdge(NODE_A, NODE_A, null);
//	}
//
//	@Test(expected = IllegalArgumentException.class)
//	public void testAddEdgeWithNullStartEnd() {
//		graph.addEdge(null, null, NODE_A);
//	}
//
//	@Test(expected = IllegalArgumentException.class)
//	public void testAddEdgeWithNullStartLabel() {
//		graph.addEdge(null, NODE_A, null);
//	}
//
//	@Test(expected = IllegalArgumentException.class)
//	public void testAddEdgeWithNullENDLabel() {
//		graph.addEdge(NODE_A, null, null);
//	}
//
//	@Test(expected = IllegalArgumentException.class)
//	public void testAddEdgeWithNullStartEndLabel() {
//		graph.addEdge(null, null, null);
//	}
//
//	// 6 removeEdge null tests
//	@Test(expected = IllegalArgumentException.class)
//	public void testRemoveEdgeWithNullStart() {
//		graph.removeEdge(null, NODE_A, LABEL_A);
//	}
//
//	@Test(expected = IllegalArgumentException.class)
//	public void testRemoveEdgeWithNullEnd() {
//		graph.removeEdge(NODE_A, null, LABEL_A);
//	}
//
//	@Test(expected = IllegalArgumentException.class)
//	public void testRemoveEdgeWithNullLabel() {
//		graph.removeEdge(NODE_A, NODE_A, null);
//	}
//
//	@Test(expected = IllegalArgumentException.class)
//	public void testRemoveEdgeWithNullStartEnd() {
//		graph.removeEdge(null, null, NODE_A);
//	}
//
//	@Test(expected = IllegalArgumentException.class)
//	public void testRemoveEdgeWithNullStartLabel() {
//		graph.removeEdge(null, NODE_A, null);
//	}
//
//	@Test(expected = IllegalArgumentException.class)
//	public void testRemoveEdgeWithNullENDLabel() {
//		graph.removeEdge(NODE_A, null, null);
//	}
//
//	@Test(expected = IllegalArgumentException.class)
//	public void testRemoveEdgeWithNullStartEndLabel() {
//		graph.removeEdge(null, null, null);
//	}

	// ------------------------------------------
	// if node passed in is not present in graph

//	// getEdges test
//	@Test(expected = IllegalArgumentException.class)
//	public void testGetEdgesOfNodeThatNotPresent() {
//		graph.getEdges(NODE_A);
//	}
//
//	// 4 addEdge tests
//	@Test(expected = IllegalArgumentException.class)
//	public void testAddEdgeOfNodesThatNotPresent() {
//		graph.addEdge(NODE_A, NODE_A, NODE_A);
//	}
//
//	@Test(expected = IllegalArgumentException.class)
//	public void testAddEdgeOfStartNodeNotPresent() {
//		graph2.addEdge(NODE_B, NODE_A, LABEL_BA);
//	}
//
//	@Test(expected = IllegalArgumentException.class)
//	public void testAddEdgeOfEndNodeNotPresent() {
//		graph2.addEdge(NODE_A, NODE_B, LABEL_AB);
//	}
//
//	@Test(expected = IllegalArgumentException.class)
//	public void testAddEdgeOfStartEndNotPresent() {
//		graph2.addEdge(NODE_B, NODE_B, LABEL_B);
//	}
//
//	// removeEdge test
//	@Test(expected = IllegalArgumentException.class)
//	public void testRemoveEdgeOfNodesThatNotPresent() {
//		graph.getEdges(NODE_A);
//	}
	///////////////////////////////////////////////////////////////////////////////////////
	//// size Test
	///////////////////////////////////////////////////////////////////////////////////////

	@Test
	public void testSizeAfterAdding() {
		// before adding
		assertEquals(0, graph.size());
		assertEquals(1, graph1.size());
		assertEquals(1, graph2.size());
		assertEquals(3, graph3.size());

		// after adding
		graph.addNode(NODE_X);
		graph1.addNode(NODE_X);
		graph2.addNode(NODE_X);
		graph3.addNode(NODE_X);
		assertEquals(1, graph.size());
		assertEquals(2, graph1.size());
		assertEquals(2, graph2.size());
		assertEquals(4, graph3.size());
	}

	@Test
	public void testSizeAfterRemoving() {
		// before removing
		assertEquals(1, graph1.size());
		assertEquals(1, graph2.size());
		assertEquals(3, graph3.size());

		// after removing
		graph1.removeNode(NODE_EMPTY);
		graph2.removeNode(NODE_A);
		graph3.removeNode(NODE_A);
		assertEquals(0, graph1.size());
		assertEquals(0, graph2.size());
		assertEquals(2, graph3.size());
	}

	///////////////////////////////////////////////////////////////////////////////////////
	//// isEmpty Test
	///////////////////////////////////////////////////////////////////////////////////////
	@Test
	public void testIsEmptyAfterAdd() {
		// before addNode
		assertTrue(graph.isEmpty());
		assertFalse(graph1.isEmpty());
		assertFalse(graph2.isEmpty());
		assertFalse(graph3.isEmpty());

		// After addNode
		graph.addNode(NODE_X);
		graph1.addNode(NODE_X);
		graph2.addNode(NODE_X);
		graph3.addNode(NODE_X);
		assertFalse(graph.isEmpty());
		assertFalse(graph1.isEmpty());
		assertFalse(graph2.isEmpty());
		assertFalse(graph3.isEmpty());
	}

	@Test
	public void testIsEmptyAfterRemove() {
		// before remove
		assertFalse(graph1.isEmpty());
		assertFalse(graph2.isEmpty());
		assertFalse(graph3.isEmpty());

		// After remove
		graph1.removeNode(NODE_EMPTY);
		graph2.removeNode(NODE_A);
		graph3.removeNode(NODE_A);
		assertTrue(graph1.isEmpty());
		assertTrue(graph2.isEmpty());
		assertFalse(graph3.isEmpty());
	}

	///////////////////////////////////////////////////////////////////////////////////////
	//// clear Test
	///////////////////////////////////////////////////////////////////////////////////////
	@Test
	public void testIsEmptyAfterClearAll() {
		// before clear
		assertFalse(graph1.isEmpty());
		assertFalse(graph2.isEmpty());
		assertFalse(graph3.isEmpty());

		// After clear
		graph1.clearAll();
		graph2.clearAll();
		graph3.clearAll();
		assertTrue(graph1.isEmpty());
		assertTrue(graph2.isEmpty());
		assertTrue(graph3.isEmpty());
	}

	@Test
	public void testSizeAfterClearAll() {
		// before clear all
		assertEquals(1, graph1.size());
		assertEquals(1, graph2.size());
		assertEquals(3, graph3.size());

		// after clear all
		graph1.clearAll();
		graph2.clearAll();
		graph3.clearAll();
		assertEquals(0, graph1.size());
		assertEquals(0, graph2.size());
		assertEquals(0, graph3.size());
	}

	///////////////////////////////////////////////////////////////////////////////////////
	//// containsNode Test
	///////////////////////////////////////////////////////////////////////////////////////
	@Test
	public void testContainsNodeAfterAdd() {
		// before add
		assertFalse(graph.containsNode(NODE_X));
		assertFalse(graph1.containsNode(NODE_X));
		assertFalse(graph2.containsNode(NODE_X));
		assertFalse(graph3.containsNode(NODE_X));

		// after add
		graph.addNode(NODE_X);
		graph1.addNode(NODE_X);
		graph2.addNode(NODE_X);
		graph3.addNode(NODE_X);
		assertTrue(graph.containsNode(NODE_X));
		assertTrue(graph1.containsNode(NODE_X));
		assertTrue(graph2.containsNode(NODE_X));
		assertTrue(graph3.containsNode(NODE_X));
	}

	// containsEdge test
	@Test
	public void testContainsEdgeThatNotPresent() {
		assertFalse(graph.containsEdge(NODE_A, NODE_A, LABEL_A));
	}

	///////////////////////////////////////////////////////////////////////////////////////
	//// containsEdge Test
	///////////////////////////////////////////////////////////////////////////////////////
//	@Test
//	public void testContainsEdgeNullLabel() {
//		assertTrue(graph1.containsNode(NODE_EMPTY));
//		assertFalse(graph.containsEdge("", "", null));
//	}

	@Test
	public void testContainsEdgeNotPresent() {
		assertFalse(graph.containsEdge("", "", ""));
		assertFalse(graph1.containsEdge("", "", ""));
		assertFalse(graph2.containsEdge("", "", ""));
		assertFalse(graph3.containsEdge("", "", ""));
		
		// added containsEdge(2 parameters) tests 
		assertFalse(graph.containsEdge("", ""));
		assertFalse(graph1.containsEdge("", ""));
		assertFalse(graph2.containsEdge("", ""));
		assertFalse(graph3.containsEdge("", ""));
	}

	@Test
	public void testContainsEdgePresent() {
		assertTrue(graph3.containsEdge(NODE_A, NODE_A, LABEL_A));
		assertTrue(graph3.containsEdge(NODE_B, NODE_B, LABEL_B));
		assertTrue(graph3.containsEdge(NODE_C, NODE_C, LABEL_C));
		assertTrue(graph3.containsEdge(NODE_A, NODE_B, LABEL_AB));
		assertTrue(graph3.containsEdge(NODE_B, NODE_C, LABEL_BC));
		assertTrue(graph3.containsEdge(NODE_C, NODE_A, LABEL_CA));
		
		// added containsEdge(2 parameters) tests 
		assertTrue(graph3.containsEdge(NODE_A, NODE_A));
		assertTrue(graph3.containsEdge(NODE_B, NODE_B));
		assertTrue(graph3.containsEdge(NODE_C, NODE_C));
		assertTrue(graph3.containsEdge(NODE_A, NODE_B));
		assertTrue(graph3.containsEdge(NODE_B, NODE_C));
		assertTrue(graph3.containsEdge(NODE_C, NODE_A));
	}

	///////////////////////////////////////////////////////////////////////////////////////
	//// getNodes Test
	///////////////////////////////////////////////////////////////////////////////////////
	@Test
	public void testGetNodesAfterAdd() {
		graph.addNode(NODE_X);
		nodes.add(NODE_X);
		assertEquals(nodes, graph.getNodes());
	}

	@Test
	public void testGetNodesAfterAddRemove() {
		testGetNodesAfterAdd();
		graph.removeNode(NODE_X);
		nodes.remove(NODE_X);
		assertEquals(nodes, graph.getNodes());
	}

	///////////////////////////////////////////////////////////////////////////////////////
	//// getEdges Test
	///////////////////////////////////////////////////////////////////////////////////////
	@Test
	public void testGetEdgesWhenNoEdges() {
		assertEquals(0, graph1.getEdges(NODE_EMPTY).size());
		assertEquals(0, graph2.getEdges(NODE_A).size());
	}

	@Test
	public void testGetEdgesGraph3() {
		assertEquals(2, graph3.getEdges(NODE_A).size());
		assertEquals(2, graph3.getEdges(NODE_B).size());
		assertEquals(2, graph3.getEdges(NODE_C).size());
	}

	//////////////////////////////////////////////////////////////////////////////////////
	//// addNode Test
	///////////////////////////////////////////////////////////////////////////////////////
	@Test
	public void testAddNodeX() {
		assertTrue(graph.addNode(NODE_X));
	}

	@Test
	public void testContainsNodeXAfterAdd() {
		testAddNodeX();
		assertTrue(graph.containsNode(NODE_X));
	}

	///////////////////////////////////////////////////////////////////////////////////////
	//// addEdge Test
	///////////////////////////////////////////////////////////////////////////////////////

	@Test
	public void testAddEdgeNonExistNodeTest() {
		// test empty string
		assertTrue(graph1.addEdge(NODE_EMPTY, NODE_EMPTY, NODE_EMPTY));
		assertTrue(graph1.containsEdge(NODE_EMPTY, NODE_EMPTY, NODE_EMPTY));

		assertTrue(graph2.addEdge(NODE_A, NODE_A, LABEL_A));
		assertTrue(graph2.containsEdge(NODE_A, NODE_A, LABEL_A));
	}

	///////////////////////////////////////////////////////////////////////////////////////
	//// removeNode Test
	///////////////////////////////////////////////////////////////////////////////////////
	@Test
	public void testRemoveNode() {
		assertTrue(graph1.removeNode(NODE_EMPTY));
		assertFalse(graph1.containsNode(NODE_EMPTY));

		assertTrue(graph2.removeNode(NODE_A));
		assertFalse(graph2.containsNode(NODE_A));
		
		assertTrue(graph3.removeNode(NODE_A));
		assertFalse(graph3.containsNode(NODE_A));
		assertTrue(graph3.containsNode(NODE_B));
		assertTrue(graph3.containsNode(NODE_C));
		assertFalse(graph3.containsEdge(NODE_A, NODE_A, LABEL_A));
		assertFalse(graph3.containsEdge(NODE_C, NODE_A, LABEL_CA));
	}

	///////////////////////////////////////////////////////////////////////////////////////
	//// removeEdge Test
	///////////////////////////////////////////////////////////////////////////////////////
	@Test
	public void testRemoveEdgeWithExistingEdgeButDiffLabel() {
		assertFalse(graph3.removeEdge(NODE_A, NODE_B, LABEL_BA));
		assertFalse(graph2.removeEdge(NODE_A, NODE_A, LABEL_AB));
	}

	@Test
	public void testRemoveEdgeWithExistingEdge() {
		assertTrue(graph3.removeEdge(NODE_A, NODE_A, LABEL_A));
		assertTrue(graph3.removeEdge(NODE_A, NODE_B, LABEL_AB));
	}

	///////////////////////////////////////////////////////////////////////////////////////
	//// clearALL Test
	///////////////////////////////////////////////////////////////////////////////////////
	@Test
	public void testClearAllOnGraph3() {
		// before clearAll
		assertEquals(3, graph3.size());

		// after clearAll
		graph3.clearAll();
		assertEquals(0, graph3.size());
	}

	///////////////////////////////////////////////////////////////////////////////////////
	//// toString Test
	///////////////////////////////////////////////////////////////////////////////////////
	@Test
	public void testToStringAfterAdd() {
		assertEquals("{}", graph.toString());
		graph.addNode(NODE_A);
		assertEquals("{a=[]}", graph.toString());

	}

	@Test
	public void testToStringOnGraphWithEmptyString() {
		assertEquals("{=[]}", graph1.toString());
	}

	///////////////////////////////////////////////////////////////////////////////////////
	//// Equals and hashCode Test
	///////////////////////////////////////////////////////////////////////////////////////
	@Test
	public void testEquals() {
		graph.addNode(NODE_A);
		graph1.clearAll();
		graph1.addNode(NODE_A);
		assertEquals(graph, graph);
		assertEquals(graph1, graph1);
		assertEquals(graph2, graph2);

	}

	@Test
	public void testEqualsSymmetric() {
		graph1.clearAll();
		assertEquals(graph, graph1);
		assertEquals(graph1, graph);
		assertEquals(graph1.hashCode(), graph.hashCode());
	}

	@Test
	public void testEqualsTransitive() {
		graph.addNode(NODE_A);
		graph1.clearAll();
		graph1.addNode(NODE_A);
		assertEquals(graph, graph1);
		assertEquals(graph1, graph2);
		assertEquals(graph2, graph);
	}

	// a series of tests
	@Test
	public void testEmptySizeToStringAfterAddNodeAToGraph() {
		// add node A
		assertTrue(graph.addNode(NODE_A));
		assertFalse(graph.isEmpty());
		assertEquals(1, graph.size());
		assertEquals("{a=[]}", graph.toString());
	}
	
	@Test
	public void testContainsNodeAndContainsEdgeFunctionCorrectlyAfterAddNodeAToGraph() {	
		testEmptySizeToStringAfterAddNodeAToGraph();
		assertTrue(graph.containsNode(NODE_A));
		assertFalse(graph.containsNode(NODE_B));
		assertFalse(graph.containsNode(NODE_EMPTY));
		assertFalse(graph.containsEdge(NODE_A, NODE_A, LABEL_A));
		assertFalse(graph.containsEdge(NODE_A, NODE_B, LABEL_AB));
	}
	
	@Test
	public void testGetNodesAndGetEdgesReturnsCorrectSetAfterAddNodeAToGraph() {	
		testEmptySizeToStringAfterAddNodeAToGraph();
		nodes.add(NODE_A);
		assertEquals(nodes, graph.getNodes());
		assertEquals(edges, graph.getEdges(NODE_A));
		assertTrue(graph.getEdges(NODE_A).isEmpty());
	}
	
	@Test
	public void testAddNodeAAgainAfterAddNodeAToGraph() {
		testEmptySizeToStringAfterAddNodeAToGraph();
		// add node A again
		assertFalse(graph.addNode(NODE_A));
	}
	
	@Test
	public void testEmptySizeToStringAfterAddNodeAAgain() {
		testAddNodeAAgainAfterAddNodeAToGraph();
		assertEquals(1, graph.size());
		assertEquals("{a=[]}", graph.toString());
	}
	
	@Test
	public void testContainsNodeAndContainsEdgeFunctionCorrectlyAddNodeAAgain() {
		testEmptySizeToStringAfterAddNodeAAgain();
		assertTrue(graph.containsNode(NODE_A));
		assertFalse(graph.containsNode(NODE_B));
		assertFalse(graph.containsNode(NODE_EMPTY));
		assertFalse(graph.containsEdge(NODE_A, NODE_A, LABEL_A));
		assertFalse(graph.containsEdge(NODE_A, NODE_B, LABEL_AB));
	}
	
	@Test
	public void testGetNodesAndGetEdgesReturnsCorrectSetAddNodeAAgain() {
		testEmptySizeToStringAfterAddNodeAAgain();
		nodes.clear();
		nodes.add(NODE_A);
		assertEquals(nodes, graph.getNodes());
		assertEquals(edges, graph.getEdges(NODE_A));
		assertTrue(graph.getEdges(NODE_A).isEmpty());
	}
	
	@Test
	public void testRemoveNodeAAfterAdd() {
		testEmptySizeToStringAfterAddNodeAToGraph();
		// remove node A
		assertFalse(graph.removeNode(NODE_B));
		assertTrue(graph.removeNode(NODE_A));
		assertFalse(graph.removeNode(NODE_A));
	}
	
	@Test
	public void testEmptySizeToStringAfterRemoveA() {
		testRemoveNodeAAfterAdd();
		assertTrue(graph.isEmpty());
		assertEquals(0, graph.size());
		assertEquals("{}", graph.toString());
	}
	
	@Test
	public void testContainsNodeAndContainsEdgeFuntionCorrectlyAfterRemoveA() {
		testRemoveNodeAAfterAdd();
		assertFalse(graph.containsNode(NODE_A));
		assertFalse(graph.containsNode(NODE_B));
		assertFalse(graph.containsNode(NODE_EMPTY));
		assertFalse(graph.containsEdge(NODE_A, NODE_A, LABEL_A));
		assertFalse(graph.containsEdge(NODE_A, NODE_B, LABEL_AB));
		nodes.clear();
		assertEquals(nodes, graph.getNodes());
	}
	
	@Test
	public void testAddNodeAAndB() {
		// add node A and node B
		assertTrue(graph.addNode(NODE_A));
		assertTrue(graph.addNode(NODE_B));
	}
	
	@Test
	public void testEmptySizeToStringAfterAddNodeAAndB() {
		testAddNodeAAndB();
		assertEquals(2, graph.size());
		assertEquals("{a=[], b=[]}", graph.toString());
	}
	
	@Test
	public void testContainsNodeAndContainsEdgeFuntionCorrectlyAfterAddNodeAAndB() {
		testAddNodeAAndB();
		assertTrue(graph.containsNode(NODE_A));
		assertTrue(graph.containsNode(NODE_B));
		assertFalse(graph.containsNode(NODE_C));
		assertFalse(graph.containsNode(NODE_EMPTY));
		
	}
	
	@Test
	public void testGetNodesAndGetEdgesReturnsCorrectSetAfterAddNodeAAndB() {
		testAddNodeAAndB();
		nodes.clear();
		nodes.add(NODE_A);
		nodes.add(NODE_B);
		assertEquals(nodes, graph.getNodes());
		assertEquals(edges, graph.getEdges(NODE_A));
		assertTrue(graph.getEdges(NODE_A).isEmpty());
		assertEquals(edges, graph.getEdges(NODE_B));
		assertTrue(graph.getEdges(NODE_B).isEmpty());
	}
	
	@Test
	public void testRemoveNodeBAfterAddNodeAAndB() {
		testAddNodeAAndB();
		// remove node B
		assertFalse(graph.removeNode(NODE_C));
		assertTrue(graph.removeNode(NODE_B));
	}
	
	@Test
	public void testEmptySizeToStringAfterRemoveNodeB() {
		testRemoveNodeBAfterAddNodeAAndB();
		assertEquals(1, graph.size());
		assertEquals("{a=[]}", graph.toString());
	}
	
	@Test
	public void testContainsNodeAndContainsEdgeFuntionCorrectlyAfterRemoveNodeB() {
		testRemoveNodeBAfterAddNodeAAndB();
		assertTrue(graph.containsNode(NODE_A));
		assertFalse(graph.containsNode(NODE_B));
		assertFalse(graph.containsNode(NODE_EMPTY));
		assertFalse(graph.containsEdge(NODE_A, NODE_A, LABEL_A));
		assertFalse(graph.containsEdge(NODE_A, NODE_B, LABEL_AB));
	}
	
	@Test
	public void testGetNodesAndGetEdgesReturnsCorrectSetAfterRemoveNodeB() {
		testRemoveNodeBAfterAddNodeAAndB();
		nodes.clear();
		nodes.add(NODE_A);
		assertEquals(nodes, graph.getNodes());
		assertEquals(edges, graph.getEdges(NODE_A));
		assertTrue(graph.getEdges(NODE_A).isEmpty());
	}
	
	@Test
	public void testAddAReflexiveEdgeOnNodeA() {
		testEmptySizeToStringAfterAddNodeAToGraph();
		
		// add a reflexive edge on A
		assertTrue(graph.addEdge(NODE_A, NODE_A, LABEL_A));
		edges.add(new LabeledEdge<String, String>(NODE_A, LABEL_A));
		assertEquals(edges, graph.getEdges(NODE_A));
		assertEquals("{a=[(a,aa)]}", graph.toString());
	}
	
	@Test
	public void testRemoveItAfterAddAReflexiveEdgeOnNodeA() {
		testAddAReflexiveEdgeOnNodeA();

		// remove the edge aa
		assertTrue(graph.removeEdge(NODE_A, NODE_A, LABEL_A));
		edges.remove(new LabeledEdge<String, String>(NODE_A, LABEL_A));
		assertEquals(edges, graph.getEdges(NODE_A));
		assertEquals("{a=[]}", graph.toString());
	}
	
	@Test
	public void testRemoveNodeAAfterRemoveReflexiveEdgeOnNodeA() {
		testRemoveItAfterAddAReflexiveEdgeOnNodeA();
		
		// remove node A
		assertTrue(graph.removeNode(NODE_A));
		nodes.remove(NODE_A);
	}
	
	@Test
	public void testAddEdgeFromAToB() {
		// add an edge between node A and node B
		assertTrue(graph.addNode(NODE_A));
		assertTrue(graph.addNode(NODE_B));
		assertTrue(graph.addEdge(NODE_A, NODE_B, LABEL_AB));
	}
	
	@Test
	public void testGetEdgesFunctionCorrectlyAfterAddEdgeFromAToB() {
		testAddEdgeFromAToB();
		nodes.add(NODE_A);
		nodes.add(NODE_B);
		edges.add(new LabeledEdge<String, String>(NODE_B, LABEL_AB));
		assertEquals(edges, graph.getEdges(NODE_A));
		assertEquals(new HashSet<LabeledEdge<String, String>>(), graph.getEdges(NODE_B));
	}

	@Test
	public void testAddEdgeAToBAgainAfterAddEdgeFromAToB() {
		testAddEdgeFromAToB();
		// add the edge ab again
		assertFalse(graph.addEdge(NODE_A, NODE_B, LABEL_AB));
	}
	
	@Test
	public void testGetEdgesReturnsCorrectSetAfterAddEdgeAToBAgain() {
		testAddEdgeAToBAgainAfterAddEdgeFromAToB();
		
		edges.clear();
		edges.add(new LabeledEdge<String, String>(NODE_B, LABEL_AB));
		assertEquals(edges, graph.getEdges(NODE_A));
		assertEquals(new HashSet<LabeledEdge<String, String>>(), graph.getEdges(NODE_B));
	}
	
	@Test
	public void testAddInverseEdgeBToAAfterAddEdgeFromAToB() {
		testAddEdgeFromAToB();
		
		// add the inverse edge ba
		assertTrue(graph.addEdge(NODE_B, NODE_A, LABEL_BA));
	}
	
	@Test
	public void testGetEdgesForNodeAReturnsCorrectSetAfterAddInverseEdgeBToA() {
		testAddInverseEdgeBToAAfterAddEdgeFromAToB();
		
		edges.clear();
		edges.add(new LabeledEdge<String, String>(NODE_B, LABEL_AB));
		assertEquals(edges, graph.getEdges(NODE_A));
	}
	
	@Test
	public void testGetEdgesForNodeBReturnsCorrectSetAfterAddInverseEdgeBToA() {
		testAddInverseEdgeBToAAfterAddEdgeFromAToB();
		
		edges.clear();
		edges.add(new LabeledEdge<String, String>(NODE_A, LABEL_BA));
		assertEquals(edges, graph.getEdges(NODE_B));
	}
	
	@Test
	public void testAddEdgesTillCompleteGraphAfterAddEdgeFromAToB() {
		testAddInverseEdgeBToAAfterAddEdgeFromAToB();

		// completes the graph
		assertTrue(graph.addEdge(NODE_A, NODE_A, LABEL_A));
		assertTrue(graph.addEdge(NODE_B, NODE_B, LABEL_B));
	}
	
	@Test
	public void testGetEdgesOnNodeAReturnsCorrectSetAfterCompleteGraph() {
		testAddEdgesTillCompleteGraphAfterAddEdgeFromAToB();
		
		edges.clear();
		edges.add(new LabeledEdge<String, String>(NODE_B, LABEL_AB));
		edges.add(new LabeledEdge<String, String>(NODE_A, LABEL_A));
		assertEquals(edges, graph.getEdges(NODE_A));
	}
	
	@Test
	public void testGetEdgesOnNodeBReturnsCorrectSetAfterCompleteGraph() {
		testAddEdgesTillCompleteGraphAfterAddEdgeFromAToB();
		
		edges.clear();
		edges.add(new LabeledEdge<String, String>(NODE_A, LABEL_BA));
		edges.add(new LabeledEdge<String, String>(NODE_B, LABEL_B));
		assertEquals(edges, graph.getEdges(NODE_B));
	}
	
	
	@Test
	public void testAddMultiEdgesOnCompleteGraph() {
		testAddEdgesTillCompleteGraphAfterAddEdgeFromAToB();
		
		// add multi edge on complete graph
		assertTrue(graph.addEdge(NODE_A, NODE_B, LABEL_AB2));
	}
	
	@Test
	public void testGetEdgesOnNodeAReturnsCorrectSetAfterAddMultiEdgesOnCompleteGraph() {
		testAddMultiEdgesOnCompleteGraph();
		
		edges.clear();
		edges.add(new LabeledEdge<String, String>(NODE_B, LABEL_AB));
		edges.add(new LabeledEdge<String, String>(NODE_A, LABEL_A));
		edges.add(new LabeledEdge<String, String>(NODE_B, LABEL_AB2));
		assertEquals(edges, graph.getEdges(NODE_A));
	}
	
	@Test
	public void testGetEdgesOnNodeBReturnsCorrectSetAfterAddMultiEdgesOnCompleteGraph() {
		testAddMultiEdgesOnCompleteGraph();
		
		edges.clear();
		edges.add(new LabeledEdge<String, String>(NODE_A, LABEL_BA));
		edges.add(new LabeledEdge<String, String>(NODE_B, LABEL_B));
		assertEquals(edges, graph.getEdges(NODE_B));
	}

}
