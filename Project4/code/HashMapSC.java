package code;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import given.AbstractHashMap;
import given.HashEntry;

/*
 * The file should contain the implementation of a hashmap with:
 * - Separate Chaining for collision handling
 * - Multiply-Add-Divide (MAD) for compression: (a*k+b) mod p
 * - Java's own linked lists for the secondary containers
 * - Resizing (to double its size) and rehashing when the load factor gets above a threshold
 *   Note that for this type of hashmap, load factor can be higher than 1
 * 
 * Some helper functions are provided to you. We suggest that you go over them.
 * 
 * You are not allowed to use any existing java data structures other than for the buckets (which have been 
 * created for you) and the keyset method
 */

public class HashMapSC<Key, Value> extends AbstractHashMap<Key, Value> {

	// The underlying array to hold hash entry Lists
	private LinkedList<HashEntry<Key, Value>>[] buckets;
	private HashEntry<Key, Value> DEFUNCT = new HashEntry<Key, Value>(null, null);
	// Note that the Linkedlists are still not initialized!
	@SuppressWarnings("unchecked")
	protected void resizeBuckets(int newSize) {
		// Update the capacity
		N = nextPrime(newSize);
		buckets = (LinkedList<HashEntry<Key, Value>>[]) Array.newInstance(LinkedList.class, N);
	}

	/*
	 * ADD MORE FIELDS IF NEEDED
	 * 
	 */

	// The threshold of the load factor for resizing
	protected float criticalLoadFactor;

	/*
	 * ADD A NESTED CLASS IF NEEDED
	 * 
	 */

	public int hashValue(Key key, int iter) {
		return hashValue(key);
	}

	public int hashValue(Key key) {
		// TODO: Implement the hashvalue computation with the MAD method. Will be almost
		// the same as the primaryHash method of HashMapDH
		int hashCode = (a* Math.abs(key.hashCode()) + b) % P;
		return Math.abs(hashCode) % N;
	}

	// Default constructor
	public HashMapSC() {
		this(101);
	}

	public HashMapSC(int initSize) {
		// High criticalAlpha for representing average items in a secondary container
		this(initSize, 10f);
	}

	public HashMapSC(int initSize, float criticalAlpha) {
		N = initSize;
		criticalLoadFactor = criticalAlpha;
		resizeBuckets(N);

		// Set up the MAD compression and secondary hash parameters
		updateHashParams();

		/*
		 * ADD MORE CODE IF NEEDED
		 * 
		 */
	}

	/*
	 * ADD MORE METHODS IF NEEDED
	 * 
	 */

	@Override
	public Value get(Key k) {
		// TODO Auto-generated method stub
		if(k == null)  {
			return null;
		}
		if(buckets[hashValue(k)] != null) {
			for(HashEntry<Key, Value> entry : buckets[hashValue(k)]) {
				if(entry.getKey().equals(k)) {
					return entry.getValue();
				}
			}
		}
		return null;
	}

	@Override
	public Value put(Key k, Value v) {
		// TODO Auto-generated method stub
		// Do not forget to resize if needed!
		// Note that the linked lists are not initialized!
		if(k == null) {
			return null;
		}
		checkAndResize();
		if(buckets[hashValue(k)] == null) {
			checkAndResize();
			buckets[hashValue(k)] = new LinkedList<HashEntry<Key, Value>>();
			buckets[hashValue(k)].add(new HashEntry<>(k,v));
			n++;
			return null;
		} 

		for(int i=0; i<buckets[hashValue(k)].size(); i++) {
			if(buckets[hashValue(k)].get(i).getKey().equals(k)) {
				checkAndResize();
				Value old = buckets[hashValue(k)].get(i).getValue();
				buckets[hashValue(k)].get(i).setValue(v);
				return old;
			}
		}

		checkAndResize();
		buckets[hashValue(k)].add(new HashEntry<>(k,v));
		n++;
		return null;
	}

	@Override
	public Value remove(Key k) {
		// TODO Auto-generated method stub
		if(k == null || buckets[hashValue(k)] == null || get(k) == null) {
			return null;
		}

		for(int i=0; i<buckets[hashValue(k)].size(); i++) {
			if(buckets[hashValue(k)].get(i).getKey().equals(k)) {
				Value old = buckets[hashValue(k)].get(i).getValue();
				buckets[hashValue(k)].remove(i);
				if(buckets[hashValue(k)].isEmpty()) {
    				buckets[hashValue(k)] = null;
    			}
				n--;
				return old;
			}

		}
		return null;
	}

	@Override
	public Iterable<Key> keySet() {
		// TODO Auto-generated method stub
		List<Key> keys = new ArrayList<Key>();
		for(int i=0; i<buckets.length; i++) {
			if(buckets[i] != null) {
				for(int j=0; j<buckets[i].size(); j++) {
					keys.add(buckets[i].get(j).getKey());
				}
			}
		}
		return keys;
	}

	/**
	 * checkAndResize checks whether the current load factor is greater than the
	 * specified critical load factor. If it is, the table size should be increased
	 * to 2*N and recreate the hash table for the keys (rehashing). Do not forget to
	 * re-calculate the hash parameters and do not forget to re-populate the new
	 * array!
	 */
	protected void checkAndResize() {
		if (loadFactor() > criticalLoadFactor) {
			// TODO: Fill this yourself
			LinkedList<HashEntry<Key, Value>>[] tmp =(LinkedList<HashEntry<Key, Value>>[]) Array.newInstance(LinkedList.class, N);
			for(int i=0; i<buckets.length; i++) {
				if(buckets[i] != null) {
					for(int j=0; j<buckets[i].size(); j++) {
						tmp[i].add(buckets[i].get(j));
					}
				}
			}
			resizeBuckets(2*N);
			updateHashParams();
			for(int i=0; i<tmp.length; i++) {
				if(tmp[i] != null) {
					for(int j=0; j<tmp[i].size(); j++) {
						buckets[i].add(tmp[i].get(j));
					}
				}
			} 
		}
	}
}
