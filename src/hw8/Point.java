package hw8;

/**
 * <b>Point</b> is an immutable representation of a point on the campus map with
 * x and y coordinates.
 * (x, y)
 * 
 * Examples of LabeledEdges include "(1.0,1.0)", "(0.0,0.0)", and "(1.0,3.0)".
 * 
 * @author Yanmeng Kong(Anny)
 */
public class Point {
	
	// debug flag
	private static final boolean DEBUG = false;
	
	private Double x; // x coordinate 
	private Double y; // y coordinate
	
	// Rep invariant:
	//		x != null && y != null
	
	// Abstract function:
	// AF(this) = a point such that 
	//		this.x = x coordinate;
	//		this.y = y coordinate;
	
	/**
	 * Creates a point with x, y coordinates. 
	 * 
	 * @param x x coordinate of the point
	 * @param y y coordinate of the point
	 */
	public Point(double x, double y) {
		this.x = x;
		this.y = y;
		checkRep();
	}
	
	/**
	 * Gets the x coordinate of the point.
	 * 
	 * @return the x coordinate of the point
	 */
	public double getX() {
		checkRep();
		return x;
	}
	
	/**
	 * Gets the y coordinate of the point.
	 * 
	 * @return the y coordinate of the point
	 */
	public double getY() {
		checkRep();
		return y;
	}
	
	/**
	 * Standard equality operation.
	 * 
	 * @param o the object to be compared for equality
	 * @return true if o represents the same point(same x and y) as this
	 */
	@Override
	public boolean equals(Object o) {
		checkRep();
		if (!(o instanceof Point)) {
			checkRep();
			return false;
		}
		
		Point other = (Point) o;
		checkRep();
		return (other.x.equals(x)) && (other.y.equals(y));
	}
	
	/**
	 * Standard hashCode function.
	 * 
	 * @return an int that all objects equal to this will also return.
	 */
	@Override
	public int hashCode() {
		checkRep();
		return x.hashCode() + y.hashCode();
	}

	/**
	 * Returns a string representation of this Point.
	 * 
	 * @return a String representation of the expression represented by this
	 *         point
	 */
	@Override
	public String toString() {
		checkRep();
		return "(" + x + "," + y + ")";
	}
	
	/**
	 * Checks if representation invariant holds.
	 */
	private void checkRep() {
		if (DEBUG) {
			assert x != null : "x should not be null";
			assert y != null : "y should not be null";
		}
	}
}
