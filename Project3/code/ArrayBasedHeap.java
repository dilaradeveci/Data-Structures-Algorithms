package code;

import java.util.ArrayList;
import java.util.Comparator;

import given.Entry;
import given.iAdaptablePriorityQueue;

/*
 * Implement an array based heap
 * Note that you can just use Entry here!
 * 
 */

public class ArrayBasedHeap<Key, Value> implements iAdaptablePriorityQueue<Key, Value> {

	// Use this arraylist to store the nodes of the heap. 
	// This is required for the autograder. 
	// It makes your implementation more verbose (e.g. nodes[i] vs nodes.get(i)) but then you do not have to deal with dynamic resizing
	protected ArrayList<Entry<Key,Value>> nodes;
	private Comparator<Key> comparator;
	private Comparator<Value> comparator2;

	public ArrayBasedHeap() {
		super();
		nodes = new ArrayList<>();
	}

	public ArrayBasedHeap(ArrayList<Entry<Key, Value>> nodes) {
		super();
		this.nodes = nodes;
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return nodes.size();
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return size() == 0;
	}

	@Override
	public void setComparator(Comparator<Key> C) {
		// TODO Auto-generated method stub
		comparator = C;
	}

	@Override
	public Comparator<Key> getComparator() {
		// TODO Auto-generated method stub
		return comparator;
	}

	@Override
	public void insert(Key k, Value v) {
		// TODO Auto-generated method stub
		Entry newEntry = new Entry<>();
		newEntry.setKey(k);
		newEntry.setValue(v);
		nodes.add(newEntry);
		upheap(size()-1);
	}

	@Override
	public Entry<Key, Value> pop() {
		// TODO Auto-generated method stub
		if(isEmpty()) {
			return null;
		}
		Entry tmp = nodes.get(0);
		swap(0, size()-1);
		nodes.remove(size()-1);
		downheap(0);
		return tmp;
	}

	@Override
	public Entry<Key, Value> top() {
		// TODO Auto-generated method stub
		if(isEmpty()) {
			return null;
		}
		return nodes.get(0);
	}

	@Override
	public Value remove(Key k) {
		// TODO Auto-generated method stub
		int index = findKey(k);
		if(index==-1) {
			return null;
		} else {
			Entry<Key,Value> entry = nodes.get(index);
			nodes.remove(entry);
			heapify();
			return entry.getValue();
		}
	}

	protected void heapify() { 
		int index = parentOf(size()-1); 
		for (int i = index; i >= 0; i--) {
			downheap(i);
		}
	}

	public int findKey(Key k) {
		int index = -1;
		for(Entry<Key, Value> entry : nodes) { 
			int difference = comparator.compare(entry.getKey(), k);
			if(difference == 0) {
				index = nodes.indexOf(entry);
			}
		}
		return index;
	}

	@Override
	public Key replaceKey(Entry<Key, Value> entry, Key k) {
		// TODO Auto-generated method stub
		int index = findKey(entry.getKey());
		if(index == -1) {
			return null;
		} else {
			Key old = nodes.get(index).getKey();
			nodes.get(index).setKey(k);
			int difference = comparator.compare(nodes.get(index).getKey(), nodes.get(parentOf(index)).getKey());
			if(difference < 0) {
				upheap(index);
			} else if(difference > 0) {
				downheap(index);
			} else {
				return old;
			}
			return old;
		}
	}

	@Override
	public Key replaceKey(Value v, Key k) {
		// TODO Auto-generated method stub
		Key old = null;
		int index = 0;
		for (Entry e : nodes) {
			if(e.getValue() == v) {
				old = (Key) e.getKey();
				e.setKey(k);
				index = nodes.indexOf(e);
			}
		}
		int difference = comparator.compare(nodes.get(index).getKey(), nodes.get(parentOf(index)).getKey());
		if(difference < 0) {
			upheap(index);
		} else if(difference > 0) {
			downheap(index);
		} else {
			return old;
		}
		return old;
	}

	@Override
	public Value replaceValue(Entry<Key, Value> entry, Value v) {
		// TODO Auto-generated method stub
		int index = findKey(entry.getKey());
		if(index == -1) {
			return null;
		} else {
			Value old = nodes.get(index).getValue();
			nodes.get(index).setValue(v);
			return old;
		}
	}

	public int parentOf(int i) {
		return (i-1)/2;
	}

	public int leftChildOf(int i) {
		return (2*i)+1;
	}
	public int rightChildOf(int i) {
		return (2*i)+2;
	}
	public boolean hasLeftChild(int i) {
		return (leftChildOf(i) < size());
	}
	public boolean hasRightChild(int i) {
		return (rightChildOf(i) < size());
	}

	public void swap(int i, int j) {
		Entry e = nodes.get(i);
		nodes.set(i, nodes.get(j));
		nodes.set(j, e);
	}

	public void upheap(int i) {
		while(i > 0) {
			int p = parentOf(i);
			int difference = comparator.compare(nodes.get(i).getKey(), nodes.get(p).getKey());
			if(difference == 0) {
				break;
			} else if(difference > 0) {
				break;
			} else {
				swap(i, p);
				i = p;
			}
		}
	}

	public void downheap(int i) {
		while(hasLeftChild(i)) {
			int smallChild = leftChildOf(i);
			if(hasRightChild(i)) {
				int rc = rightChildOf(i);
				int difference = comparator.compare(nodes.get(rc).getKey(), nodes.get(smallChild).getKey());
				if(difference < 0) {
					smallChild = rc;
				}
			}  
			int difference2 = comparator.compare(nodes.get(smallChild).getKey(), nodes.get(i).getKey());
			if(difference2 >= 0) {
				break;
			} else {
				swap(i ,smallChild);
				i = smallChild;
			}
		}
	}
}
