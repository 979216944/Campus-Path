package hw5;

import javax.annotation.Nullable;

/**
 * <b>LabeledEdge</b> is an immutable representation of an outwards edge with
 * label of a node and the end of the edge.
 * 	(end, label)
 * 
 * Examples of LabeledEdges include "(a,b)", "(abc,a)", and "(0,zero)".
 * 
 * @author Anny
 * 
 *@param <N> edge's end node type
 *@param <E> edge's label type
 */
public class LabeledEdge<N, E> {
	
	// debug flag
	private static final boolean DEBUG = false;
	
	// Rep invariant:
	// this.end != null && this.label != null

	// Abstract function:
	// AF(this) = a labeled edge without start such that
	// this.end = end of the edge
	// this.label = label of the edge

	private final N end;
	private final E label;

	/**
	 * Creates a labeled edge.
	 * 
	 * @param end
	 *            End of the edge
	 * @param label
	 *            Label of the edge
	 * @requires end != null and label != null
	 * @effects constructs a labeled edge with end <var>end</var> and label
	 *          <var>label</var>
	 */
	public LabeledEdge(N end, E label) {
//		if (end == null || label == null) {
//			throw new IllegalArgumentException("end/label Null");
//		}
		this.end = end;
		this.label = label;
		checkRep();
	}
	
	/**
	 * Gets the end of this LabeledEdge.
	 * 
	 * @return the end of this LabeledEdge
	 */
	public N getEnd() {
		checkRep();
		return this.end;
		
	}

	/**
	 * Gets the label of this LabeledEdge.
	 * 
	 * @return the label of this edge
	 */
	public E getLabel() {
		checkRep();
		return this.label;

	}

//	/**
//	 * Compares this object with the specified object for order. Returns a
//	 * negative integer, zero, or a positive integer as this object is less
//	 * than, equal to, or greater than the specified object.
//	 * 
//	 * @param e LabeledEdge object to be compared
//	 * @requires e != null
//	 * @return a negative integer, zero, or a positive integer as this object is
//	 *         less than, equal to, or greater than the specified object
//	 */
//	@Override
//	public int compareTo(LabeledEdge<N, E> e) {
//		checkRep();
////		if (e == null) {
////			throw new IllegalArgumentException("Edge Null");
////		}
//		
//		if (!(end.equals(e.end))) {
//			checkRep();
//			return end.compareTo(e.end);
//		}
//		
//		if (!(label.equals(e.label))) {
//			checkRep();
//			return label.compareTo(e.label);
//		}
//		checkRep();
//		return 0;
//	}

	/**
	 * Standard equality operation.
	 * 
	 * @param o the object to be compared for equality.
	 * @return true if o represents same edge (same end and same label) as this
	 *         edge
	 */
	@Override
	public boolean equals(@Nullable Object o) {
		checkRep();
		if (!(o instanceof LabeledEdge<?, ?>)) {
			return false;
		}

		LabeledEdge<?, ?> e = (LabeledEdge<?, ?>) o;
		checkRep();
		return end.equals(e.end) && label.equals(e.label);
	}

	/**
	 * Standard hashCode function.
	 * 
	 * @return an int that all objects equal to this will also return.
	 */
	@Override
	public int hashCode() {
		checkRep();
		return end.hashCode() + label.hashCode();
	}

	/**
	 * Returns a string representation of this LabeledEdge.
	 * 
	 * @return a String representation of the expression represented by this
	 *         edge
	 */
	@Override
	public String toString() {
		checkRep();
		return "(" + end + "," + label + ")";
	}

	/**
	 * Checks if representation invariant holds.
	 */
	private void checkRep() throws RuntimeException {
		if (DEBUG) {
			assert end != null : "End null";
			assert label != null : "Label null";
		}
	}

}
