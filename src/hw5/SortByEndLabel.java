package hw5;

import java.util.Comparator;

/**
 * SortByEndLabel is a comparator used to sort LabeledEdges
 * by end nodes first, labels then.
 * For example, 
 * compare(e1, e2) < 0, 
 * compare(e2, e1) < 0, 
 * compare(e1, e1) == 0,
 * where 
 * e1 = (a, c),
 * e2 = (b, a).
 * 
 * @author Anny
 *
 * @param <N> the type of edge's end node may be compared by this comparator
 * @param <E> the type of edge's label may be compared by this comparator
 */
public class SortByEndLabel <N extends Comparable<N>, E extends Comparable<E>> 
		implements Comparator<LabeledEdge<N, E>> {
	
		/**
		 * compare by end nodes first, labels then.
		 * 
		 * @param e1 the first LabeledEdge to be compared
		 * @param e2 the second LabeledEdge to be compared
		 * @return negative integer if e1 is less than e2, 
		 * positive integer if e1 is larger than e2, 0 otherwise.
		 */
		@Override
		public int compare(LabeledEdge<N, E> e1, LabeledEdge<N, E> e2) {
			if (!e1.getEnd().equals(e2.getEnd())) {
				return e1.getEnd().compareTo(e2.getEnd());
			}
			if (!e1.getLabel().equals(e2.getLabel())) {
				return e1.getLabel().compareTo(e2.getLabel());
			}
			return 0;
	}
}
