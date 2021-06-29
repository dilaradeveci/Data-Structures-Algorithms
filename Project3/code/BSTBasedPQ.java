package code;

import java.util.Comparator;
import java.util.List;

import given.Entry;
import given.iAdaptablePriorityQueue;

/*
 * Implement a binary search tree based priority queue
 * Do not try to create heap behavior (e.g. no need for a last node)
 * Just use default binary search tree properties
 */

public class BSTBasedPQ<Key, Value> extends BinarySearchTree<Key, Value> implements iAdaptablePriorityQueue<Key, Value> {

	private Comparator<Key> comparator;
	
	public BSTBasedPQ() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void insert(Key k, Value v) {
		// TODO Auto-generated method stub
		super.put(k,v);
	}

	@Override
	public Entry<Key, Value> pop() {
		// TODO Auto-generated method stub
		if(isEmpty()) {
			return null;
		}
		BinaryTreeNode smallest = getRoot();
		while(getLeftChild(smallest) != null) {
			smallest = smallest.getLeftChild();
		}
		BinaryTreeNode tmp = smallest;
		remove((Key) smallest.getKey()); 
		Key k = (Key) tmp.getKey();
		Value v = (Value) tmp.getValue();
		Entry<Key, Value> e = new Entry<>(k,v);
		return e;
	}

	@Override
	public Entry<Key, Value> top() {
		// TODO Auto-generated method stub
		if(isEmpty()) {
			return null;
		}
		BinaryTreeNode smallest = getRoot();
		while(getLeftChild(smallest) != null) {
			smallest = smallest.getLeftChild();
		}

		Key k = (Key) smallest.getKey();
		Value v = (Value) smallest.getValue();
		Entry<Key, Value> e = new Entry<>(k,v);
		return e;
	}

	@Override
	public Key replaceKey(Entry<Key, Value> entry, Key k) {
		// TODO Auto-generated method stub
		BinaryTreeNode node = findNode(getRoot(), entry.getKey());
		if(entry == null || k==null || node == null) {
			return null;
		} else {
			Key old = entry.getKey();
			remove(old);
			insert(k, entry.getValue());
			return old;
		} 
	}

	@Override
	public Key replaceKey(Value v, Key k) {
		// TODO Auto-generated method stub
		Key old = null;
		for(BinaryTreeNode node : getNodesInOrder()) {
			if(node.getValue()==v) {
				old = (Key) node.getKey();
			}
		}
		remove(old);
		insert(k, v);
		return old;
	}

	@Override
	public Value replaceValue(Entry<Key, Value> entry, Value v) {
		// TODO Auto-generated method stub
		BinaryTreeNode node = findNode(getRoot(), entry.getKey());
		if(entry == null || v==null || node == null) {
			return null;
		} else {
			if(entry.getValue() == node.getValue()) {
				Value old = entry.getValue();
				node.setValue(v);
				return old;
			}
			return null;
		}
	}
}