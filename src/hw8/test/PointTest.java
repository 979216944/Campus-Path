package hw8.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import hw8.Point;

/**
 * This class contains a set of test cases that can be used to test the
 * implementation of the Point class.
 * <p>
 */
public class PointTest {

	private Point p, p1, p2;
	private static final double DELTA = 0.00000001;

	@Before
	public void setUp() throws Exception {
		p = new Point(0.1, 0.2);
		p1 = new Point(0.3, 0.4);
		p2 = new Point(0.5, 0.6);
	}

	// test getX()

	@Test
	public void testGetX() {
		assertEquals(p.getX(), 0.1, DELTA);
		assertEquals(p1.getX(), 0.3, DELTA);
		assertEquals(p2.getX(), 0.5, DELTA);
	}

	// test getY()

	@Test
	public void testGetY() {
		assertEquals(p.getY(), 0.2, DELTA);
		assertEquals(p1.getY(), 0.4, DELTA);
		assertEquals(p2.getY(), 0.6, DELTA);
	}

	// Equals tests

	@Test
	public void testEqualsWithNull() {
		assertFalse(p.equals(null));
	}

	@Test
	public void testEqualsWithObject() {
		assertFalse(p.equals(new Object()));
	}

	@Test
	public void testEqualsReflexive() {
		assertTrue(p.equals(p));
	}

	@Test
	public void testEqualsSymmetric() {
		Point temp = new Point(0.1, 0.2);
		assertTrue(p.equals(temp));
		assertTrue(temp.equals(p));
	}

	@Test
	public void testEqualsTransitive() {
		Point temp = new Point(0.1, 0.2);
		Point temp2 = new Point(0.1, 0.2);
		assertTrue(p.equals(temp));
		assertTrue(temp.equals(temp2));
		assertTrue(temp2.equals(p));
	}

	// test hashCode()

	@Test
	public void testHashCode() {
		assertEquals((new Point(0.1, 0.2)).hashCode(), p.hashCode());
		assertTrue(p.hashCode() != p1.hashCode());
	}

	// test toString()

	@Test
	public void testToString() {
		assertEquals("(0.1,0.2)", p.toString());
		assertEquals("(0.3,0.4)", p1.toString());
		assertEquals("(0.5,0.6)", p2.toString());
	}
}
