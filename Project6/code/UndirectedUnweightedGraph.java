package code;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class UndirectedUnweightedGraph<V> extends BaseGraph<V> {

	/*
	 * YOU CAN ADD ANY FIELDS AND ADDITIONAL METHODS AS YOU LIKE
	 * 
	 */
	private HashMap<V, Set<V>> adjacencyL;
	@Override
	public String toString() {
		String tmp = "Undirected Unweighted Graph";
		return tmp;
	}

	public UndirectedUnweightedGraph() {
		this.adjacencyL = new HashMap<>();
	}

	public boolean isEmpty() {
		if(adjacencyL.isEmpty()) {
			return true;
		}
		return false;
	}
	
	@Override
	public void insertVertex(V v) {
		// TODO Auto-generated method stub
		if (!adjacencyL.containsKey(v)) {
			adjacencyL.put(v, new HashSet<V>());
		}
	}

	@Override
	public V removeVertex(V v) {
		// TODO Auto-generated method stub
		if (!adjacencyL.containsKey(v)) {
			return null;
		}
		adjacencyL.remove(v);
		for (V u: vertices()) {
			adjacencyL.get(u).remove(v);
		}
		return v;
	}

	@Override
	public boolean areAdjacent(V v1, V v2) {
		// TODO Auto-generated method stub
		if(!adjacencyL.containsKey(v1) || !adjacencyL.containsKey(v2)) {
			return false;
		}
		if(adjacencyL.get(v1).contains(v2)) {
			return true;
		}
		return false;
	}
	
	@Override
	public void insertEdge(V source, V target) {
		// TODO Auto-generated method stub
		if (!adjacencyL.containsKey(source)) {
			insertVertex(source);
		} 
		if(!adjacencyL.containsKey(target)) {
			insertVertex(target);
		}
		adjacencyL.get(source).add(target);
		adjacencyL.get(target).add(source);
	}


	@Override
	public void insertEdge(V source, V target, float weight) {
		// TODO Auto-generated method stub
		insertEdge(source, target);
	}

	@Override
	public boolean removeEdge(V source, V target) {
		// TODO Auto-generated method stub
		if(!adjacencyL.containsKey(source) || !adjacencyL.containsKey(target) || !areAdjacent(source, target)) {
			return false;
		}
		if(adjacencyL.containsKey(source) && adjacencyL.containsKey(target)) {
		adjacencyL.get(source).remove(target);
		adjacencyL.get(target).remove(source);
		return true;
		}
		return false;
	}


	@Override
	public float getEdgeWeight(V source, V target) {
		// TODO Auto-generated method stub
		if(!areAdjacent(source, target)) {
			return 0;
		}
		if(!adjacencyL.containsKey(source) || !adjacencyL.containsKey(target)) {
			return 0;
		}
		return 1;
	}

	@Override
	public int numVertices() {
		// TODO Auto-generated method stub
		return adjacencyL.size();
	}

	@Override
	public Iterable<V> vertices() {
		// TODO Auto-generated method stub
		return adjacencyL.keySet();
	}

	@Override
	public int numEdges() {
		// TODO Auto-generated method stub
		int total =0;
		for (V u: vertices()) {
			int s = adjacencyL.get(u).size();
			total = total + s;
		}
		return total/2;
	}


	@Override
	public boolean isDirected() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isWeighted() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int outDegree(V v) {
		// TODO Auto-generated method stub
		if(!adjacencyL.containsKey(v)) {
			return -1;
		}
		int out = adjacencyL.get(v).size();
		return out;
	}

	@Override
	public int inDegree(V v) {
		// TODO Auto-generated method stub
		return outDegree(v);
	}

	@Override
	public Iterable<V> outgoingNeighbors(V v) {
		// TODO Auto-generated method stub
		if(!adjacencyL.containsKey(v) || v==null) {
			return null;
		}
		return adjacencyL.get(v);
	}

	@Override
	public Iterable<V> incomingNeighbors(V v) {
		// TODO Auto-generated method stub
		return outgoingNeighbors(v);
	}

}
