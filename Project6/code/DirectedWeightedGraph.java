package code;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.Map.Entry;

public class DirectedWeightedGraph<V> extends BaseGraph<V>  {

	/*
	 * YOU CAN ADD ANY FIELDS AND ADDITIONAL METHODS AS YOU LIKE
	 * 
	 */
	private HashMap<V, HashMap<V, Float>> adjacencyL;

	@Override
	public String toString() {
		String tmp = "Directed Weighted Graph";
		return tmp;
	}
	public DirectedWeightedGraph() {
		// TODO Auto-generated constructor stub
		adjacencyL = new HashMap<>();
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
			adjacencyL.put(v, new HashMap<V, Float>());
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
		if(adjacencyL.get(v1).containsKey(v2)) {
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
			adjacencyL.get(source).put(target, (float) 0);
		}

		@Override
		public void insertEdge(V source, V target, float weight) {
			// TODO Auto-generated method stub
			if (!adjacencyL.containsKey(source)) {
				insertVertex(source);
			} 
			if (!adjacencyL.containsKey(target)) {
				insertVertex(target);
			} 
			adjacencyL.get(source).put(target, weight);
		}

		@Override
		public boolean removeEdge(V source, V target) {
			// TODO Auto-generated method stub
			if(!adjacencyL.containsKey(source) || !adjacencyL.containsKey(target)  || !areAdjacent(source, target)) {
				return false;
			}
			if(adjacencyL.containsKey(source) && adjacencyL.containsKey(target)) {
			adjacencyL.get(source).remove(target);
			return true;
			}
			return false;
		}


		@Override
		public float getEdgeWeight(V source, V target) {
			// TODO Auto-generated method stub
			if(!areAdjacent(source, target)) {
				return Float.MAX_VALUE;
			}
			if(!adjacencyL.containsKey(source) || !adjacencyL.containsKey(target)) {
				return Float.MAX_VALUE;
			}
			return adjacencyL.get(source).get(target);
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
			return total;
		}

		@Override
		public boolean isDirected() {
			// TODO Auto-generated method stub
			return true;
		}

		@Override
		public boolean isWeighted() {
			// TODO Auto-generated method stub
			return true;
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
			if(!adjacencyL.containsKey(v) || v == null) {
				return -1;
			}
			int inDeg = 0;
			for(Entry<V, HashMap<V, Float>> entry : adjacencyL.entrySet())	 {
				for(int i=0; i<entry.getValue().size(); i++) {
					if(entry.getValue().containsKey(v)) {
						inDeg++;
					}
				}
			}
			return inDeg;
		}

		@Override
		public Iterable<V> outgoingNeighbors(V v) {
			// TODO Auto-generated method stub
			if(!adjacencyL.containsKey(v) || v==null) {
				return null;
			}
			return adjacencyL.get(v).keySet();
		}

		@SuppressWarnings("null")
		@Override
		public Iterable<V> incomingNeighbors(V v) {
			// TODO Auto-generated method stub
			if(!adjacencyL.containsKey(v) || v==null) {
				return null;
			}
			Set<V> in = new HashSet();
			for(V u : vertices()) {
				for(V w : outgoingNeighbors(u)) {
					if(w.equals(v)) {
						in.add(u);
					}
				}
			}
			return in;
		}
	}

