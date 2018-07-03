package hw5.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import hw5.LabeledEdge;

/**
 * This class contains a set of test cases that can be used to test the
 * implementation of the LabeledEdge class.
 * <p>
 */
public class LabeledEdgeTest {
	
	private final String NODE_A = "a";
	private final String NODE_B = "b";
	private final String NODE_EMPTY = "";

	private final String LABEL_EMPTY = "";
	private final String LABEL_A = "aa";
	private final String LABEL_AB = "ab";
	
	private LabeledEdge<String, String> e, e1, e2, e3;

	@Before
	public void setUp() throws Exception {
		e = new LabeledEdge<String, String>(NODE_EMPTY, LABEL_EMPTY);
		e1 = new LabeledEdge<String, String>(NODE_A, LABEL_A);
		e2 = new LabeledEdge<String, String>(NODE_B, LABEL_AB);
		e3 = new LabeledEdge<String, String>(NODE_A, LABEL_EMPTY);
	}
	
	
	// constructor tests
	
//	@Test(expected = IllegalArgumentException.class)
//	public void testConstructorWithNullEnd() {
//		new LabeledEdge(null, LABEL_EMPTY);
//	}
//	
//	@Test(expected = IllegalArgumentException.class)
//	public void testConstructorWithNullLabel() {
//		new LabeledEdge(NODE_EMPTY, null);
//	}
	
	// getLabel tests
	
	@Test
	public void testGetLabel() {
		assertEquals(LABEL_EMPTY, e.getLabel());
		assertEquals(LABEL_A, e1.getLabel());
		assertEquals(LABEL_AB, e2.getLabel());
	}
	
	// getEnd tests
	
	@Test
	public void testGetEnd() {
		assertEquals(NODE_EMPTY, e.getEnd());
		assertEquals(NODE_A, e1.getEnd());
		assertEquals(NODE_B, e2.getEnd());
	}
	
	// toStirng tests
	
	@Test
	public void testToString() {
		assertEquals("(,)", e.toString());
		assertEquals("(a,aa)", e1.toString());
		assertEquals("(b,ab)", e2.toString());
	}
	
	// Equals tests
	
	@Test
	public void testEqualsReflexive() {
		assertTrue(e.equals(e));
	}
	
	@Test
	public void testEqualsSymmetric() {
		e1 = new LabeledEdge<String, String>(NODE_EMPTY, LABEL_EMPTY);
		assertTrue(e.equals(e1));
		assertTrue(e1.equals(e));
	}
	
	@Test
	public void testEqualsTransitive() {
		e1 = new LabeledEdge<String, String>(NODE_EMPTY, LABEL_EMPTY);
		e2 = new LabeledEdge<String, String>(NODE_EMPTY, LABEL_EMPTY);
		assertTrue(e.equals(e1));
		assertTrue(e1.equals(e2));
		assertTrue(e2.equals(e));
	}
	
	@Test
	public void testEqualsWithDiffLabel() {
		assertFalse(e1.equals(e3));
	}
	
	// hashCode tests
	
	@Test
	public void testHashCodeForSameLabeledEdge() {
		e1 = new LabeledEdge<String, String>(NODE_EMPTY, LABEL_EMPTY);
		assertEquals(e.hashCode(), e1.hashCode());
	}
	
//	// compareTo tests
//	
//	@Test
//	public void testCompareTo() {
//		assertTrue(e.compareTo(e1) < 0);
//		assertTrue(e2.compareTo(e1) > 0);
//		
//		e3 = new LabeledEdge<String, String>(NODE_A, LABEL_AB);
//		assertTrue(e1.compareTo(e3) < 0);
//		
//		e3 = new LabeledEdge<String, String>(NODE_B, LABEL_AB);
//		assertEquals(0, e2.compareTo(e3));
//		
//	}
}
