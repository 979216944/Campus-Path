package hw7;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

import hw5.DirectedGraph;
import hw5.LabeledEdge;

/**
 * <b>DijkstraPathFinder</b> gets the minimum-cost path between origin node
 * and end node in the graph via Dijkstra search.
 * 
 * @author Anny
 *
 */
public class DijkstraPathFinder {

	/**
	 * Finds the minimum-cost path from origin to dest in the graph via Dijkstra search.
	 * 
	 * @param g graph where to find a path between two nodes
	 * @param origin origin of the path
	 * @param dest destination of the path
	 * @requires g != null && origin != null && dest != null 
	 * 	         && g.containsNode(origin) && g.containsNode(dest)
	 * @return the minimum-cost path from origin to dest, 
	 * 		   null if no path found
	 */
	public static <N, E extends Number> List<LabeledEdge<N, Double>> DijkstraFindPath(
			DirectedGraph<N, E> g, N origin, N dest) {
		
		// Each element is a path from origin to a given node. 
		// A path's “priority” in the queue is the total cost of that path. 
		// Nodes for which no path is known yet are not in the queue.
		Queue<ArrayList<LabeledEdge<N, Double>>> active = 
				new PriorityQueue<ArrayList<LabeledEdge<N, Double>>>(new PathComparator<N, Double>());
		
		// initialize known set
		Set<N> finished = new HashSet<N>();
		
		// set the path to itself as cost 0
		// add the edge with zero cost to active
		List<LabeledEdge<N, Double>> source = new ArrayList<LabeledEdge<N, Double>>();
		source.add(new LabeledEdge<N, Double>(origin, 0.0));
		active.add((ArrayList<LabeledEdge<N, Double>>) source);
		
		while (!active.isEmpty()) {
			// minPath is the lowest-cost path in active and is the 
			// minimum-cost path for some node
			List<LabeledEdge<N, Double>> minPath = active.poll();
			

			// destination of minPath
			N minDest = minPath.get(minPath.size()-1).getEnd();
			// cost of minPath
			double minCost = minPath.get(minPath.size()-1).getLabel();
			
			// dest found
			if (minDest.equals(dest)) {
				return minPath;
			}
			// nodes already visited
			if (finished.contains(minDest)) {
				continue;
			}
			
			for (LabeledEdge<N, E> e : g.getEdges(minDest)) {
				// If we don't know the minimum-cost path from start to child,
	            // examine the path we've just found
				if (!finished.contains(e.getEnd())) {
					// add new path
					List<LabeledEdge<N, Double>> newPath = 
							new ArrayList<LabeledEdge<N, Double>>(minPath); 
					newPath.add(new LabeledEdge<N, Double>(e.getEnd(), minCost + e.getLabel().doubleValue()));
					active.add((ArrayList<LabeledEdge<N, Double>>) newPath);
				}
			}
			
			// update known set
			finished.add(minDest);
		}
		
		// no path found from origin to dest
		return null;
	}
}
