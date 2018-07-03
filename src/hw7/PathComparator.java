package hw7;

import java.util.ArrayList;
import java.util.Comparator;

import hw5.LabeledEdge;

/**
 * PathComparator is a comparator used to sort paths(a list of LabeledEdges)
 * by total cost first, size then.
 * 
 * @author Anny
 *
 * @param <N> the type of edge's end node
 * @param <E> the type of edge's label that is compared by this comparator
 */
public class PathComparator<N, E extends Comparable<E>> implements Comparator<ArrayList<LabeledEdge<N, E>>> {
	
		/**
		 * compare by total cost first, size then.
		 * 
		 * @param path1 the first path to be compared
		 * @param path2 the second path to be compared
		 * @return negative integer if path1 is less than path2, 
		 * positive integer if path1 is larger than path2, 0 otherwise.
		 */
		@Override
		public int compare(ArrayList<LabeledEdge<N, E>> path1, ArrayList<LabeledEdge<N, E>> path2) {
			E cost1 = path1.get(path1.size()-1).getLabel();
			E cost2 = path2.get(path2.size()-1).getLabel();
			// compare total cost first
			if (!cost1.equals(cost2)) {
				return cost1.compareTo(cost2);
			}
			// compare size then
			return path1.size() - path2.size();
		}
}
