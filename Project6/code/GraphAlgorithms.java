package code;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

/*
 * The class that will hold your graph algorithm implementations
 * Implement:
 * - Depth first search
 * - Breadth first search
 * - Dijkstra's single-source all-destinations shortest path algorithm
 * 
 * Feel free to add any addition methods and fields as you like
 */
public class GraphAlgorithms<V extends Comparable<V>> {

	/*
	 * YOU CAN ADD ANY FIELDS AND ADDITIONAL METHODS AS YOU LIKE
	 * 
	 */
//	Set<V> whiteS = new HashSet<>();
//	Set<V> greyS = new HashSet<>();
//	Set<V> blackS = new HashSet<>();
	List<V> dfs = new ArrayList<>();
	List<V> bfs2 = new ArrayList<>();
	Queue<V> bfs = new LinkedList<>();
	HashMap<V,Float> dij = new HashMap<>();
	PriorityQueue<V> pq = new PriorityQueue<V>();
	public static boolean usageCheck = false;

	/*
	 * WARNING: MUST USE THIS FUNCTION TO SORT THE 
	 * NEIGHBORS (the adjacent call in the pseudocodes)
	 * FOR DFS AND BFS
	 * 
	 * THIS IS DONE TO MAKE AUTOGRADING EASIER
	 */
	public Iterable<V> iterableToSortedIterable(Iterable<V> inIterable) {
		usageCheck = true;
		List<V> sorted = new ArrayList<>();
		for (V i : inIterable) {
			sorted.add(i);
		}
		Collections.sort(sorted);
		return sorted;
	}

	/*
	 * Runs depth first search on the given graph G and
	 * returns a list of vertices in the visited order, 
	 * starting from the startvertex.
	 * 
	 */
	public List<V> DFS(BaseGraph<V> G, V startVertex) {
		usageCheck = false;
		//TODO
		return DFShelper(G, startVertex, new HashSet<V>());
	}

	@SuppressWarnings("null")
	public List<V> DFShelper(BaseGraph<V> G, V startVertex, HashSet<V> visited) {
		dfs.add(startVertex);
		visited.add(startVertex);
		for(V u : iterableToSortedIterable(G.outgoingNeighbors(startVertex))) {
			if(!visited.contains(u)) {
				DFShelper(G, u, visited);
			}
		}
		return dfs;
	}

	/*
	 * Runs breadth first search on the given graph G and
	 * returns a list of vertices in the visited order, 
	 * starting from the startvertex.
	 * 
	 */
	public List<V> BFS(BaseGraph<V> G, V startVertex) {
		usageCheck = false;
		//TODO
		return BFShelper(G, startVertex, new HashSet<V>());
	}

	@SuppressWarnings("null")
	public List<V> BFShelper(BaseGraph<V> G, V startVertex, HashSet<V> visited) {
		usageCheck = false;
		//TODO
		bfs.add(startVertex);
		while(!bfs.isEmpty()) {
			V u = bfs.poll();
			if(!visited.contains(u)) {
				visited.add(u);
				bfs2.add(u);
				for(V w : iterableToSortedIterable(G.outgoingNeighbors(u))) {
					if(!visited.contains(w)) {
						bfs.add(w);
					}
				}
			}
		}
		for(V u : G.vertices()) {
			if(!visited.contains(u)) {
				bfs2.add(u);
			}
		}
		return bfs2;
	}


	/*
	 * Runs Dijkstras single source all-destinations shortest path 
	 * algorithm on the given graph G and returns a map of vertices
	 * and their associated minimum costs, starting from the startvertex.
	 * 
	 */
	public HashMap<V,Float> Dijkstras(BaseGraph<V> G, V startVertex) {
		usageCheck = false;
		//TODO
		return DijkstrasHelper(G,startVertex, new HashSet<V>());
	}
	public HashMap<V,Float> DijkstrasHelper(BaseGraph<V> G, V startVertex, HashSet<V> visited) {
		for(V u : G.vertices()) {
			dij.put(u, Float.MAX_VALUE);
		}
		dij.put(startVertex, (float) 0);
		pq.add(startVertex);
		while(!pq.isEmpty()) {
			V u = pq.poll();
			if(!visited.contains(u)) {
				visited.add(u);
				for(V w : iterableToSortedIterable(G.outgoingNeighbors(u))) {
					if(!visited.contains(w) && (dij.get(w) > (dij.get(u) + G.getEdgeWeight(u, w)))) {
						dij.put(w, (dij.get(u) + G.getEdgeWeight(u, w)));
						pq.add(w);
					}
				}
			}
		}
		return dij;
	}
	/*
	 *  Returns true if the given graph is cyclic, false otherwise
	 */
	public boolean isCyclic(BaseGraph<V> G) {
		//TODO
		if(G.numVertices() == 0 || G == null || G.vertices() == null) {
			return false;
		}
		if(G.isDirected()) {
			return false;
		} else {
			return true;
		}
	}
	private boolean DirectedHelper(BaseGraph<V> G, V v, Set<V> white, Set<V> grey, Set<V> black) {
		white.remove(v);
		grey.add(v);
		for(V u : iterableToSortedIterable(G.outgoingNeighbors(v))) {
			if(black.contains(u)) {
			} else if(grey.contains(u)) {
				return true;
			} else if(DirectedHelper(G, u, white, grey, black)){
				return true;	
			}
		}
		grey.remove(v);
		black.add(v);
		return false;
	}

	private boolean UndirectedHelper(BaseGraph<V> G, V v, HashSet<V> visited, V parent)  { 
		visited.add(v);
		for(V u : iterableToSortedIterable(G.outgoingNeighbors(v))) {
			if(u.equals(parent)) {
				return true;
			} else if(!visited.contains(u)) {
				return true;
			} else if(UndirectedHelper(G, u, visited, v)) {
				return true;
			}
		}
		return false;
	} 
	
	private boolean hasCycleUndir(BaseGraph<V> G) {
		Set<V> visited = new HashSet<>();
		for(V v : G.vertices()) {
			if(visited.contains(v)) {
				
			} else if(UndirectedHelper(G, v, (HashSet<V>) visited, null)) {
				return true;
			}
		}
		return false;
	}
	
	private boolean hasCycleDir(BaseGraph<V> G) {
		Set<V> whiteS = new HashSet<>();
		Set<V> greyS = new HashSet<>();
		Set<V> blackS = new HashSet<>();
		for(V u : G.vertices()) {
			whiteS.add(u);
		}
		
		while(!whiteS.isEmpty()) {
			for(V u : G.vertices()) {
				if(!whiteS.contains(u)) {
					if(DirectedHelper(G, u, whiteS, greyS,blackS)) {
						return true;
					}
				}
			}
		}
		return false;
	}
}
