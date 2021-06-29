package code;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import given.iMap;
import given.iBinarySearchTree;

/*
 * Implement a vanilla binary search tree using a linked tree representation
 * Use the BinaryTreeNode as your node class
 */

public class BinarySearchTree<Key, Value> implements iBinarySearchTree<Key, Value>, iMap<Key, Value> {

	private int size;
	private BinaryTreeNode root;
	private Comparator<Key> comparator;

	public BinarySearchTree() {
		super();
		size = 0;
		root = null;
	}

	public BinaryTreeNode findNode(BinaryTreeNode current, Key k) {
		if(current == null) {
			return null;
		}
		if (current.getKey() == k) {
			return current;
		}
		int difference = comparator.compare(k, (Key) current.getKey());
		if(difference>0) {
			return findNode(current.rightChild, k); 
		}else if(difference<0) {
			return findNode(current.leftChild, k);
		}else {
			return current;
		}
	}

	@Override
	public Value get(Key k) {
		// TODO Auto-generated method stub
		if(findNode(getRoot(), k) == null) {
			return null;
		}
		return (Value) findNode(getRoot(), k).getValue();
	}


	protected BinaryTreeNode putHelper(BinaryTreeNode current, Key k, Value v) {
		BinaryTreeNode newNode = new BinaryTreeNode(k, v);
		if (current == null) {
			current = newNode;
			return current;
		}
		int difference = comparator.compare(k, (Key) current.getKey());
		if (difference > 0) {
			current.setRightChild(putHelper(current.rightChild, k, v));
		} else if (difference < 0) {
			current.setLeftChild(putHelper(current.leftChild, k, v));
		} else {
			return current;
		}
		return current;
	}

	@Override
	public Value put(Key k, Value v) {
		// TODO Auto-generated method stub
		Value tmp = get(k);
		if(tmp == null) {
			root =  putHelper(root, k, v);
			size++;
			return null;
		} else {
			putHelper(root, k, v);
			findNode(root, k).setValue(v);
			return tmp;
		}		
	}

	private  BinaryTreeNode<Key, Value> delete(BinaryTreeNode node) {
		if (node.leftChild == null) {
			return node.rightChild;
		} else {
			node.setLeftChild(delete(node.leftChild));
			return node;
		}
	}



	private BinaryTreeNode<Key, Value> min(BinaryTreeNode node) { 
		if (node.leftChild == null) {
			return node; 
		}
		else    {
			return min(node.leftChild); 
		}
	} 

	private BinaryTreeNode<Key, Value> removeHelper(BinaryTreeNode node, Key key) {
		if (node == null) {
			return null;
		}

		int difference = comparator.compare(key, (Key) node.getKey());
		if(difference > 0) {
			node.setRightChild(removeHelper(node.getRightChild(), key));
		}
		else if (difference < 0) {
			node.setLeftChild(removeHelper(node.getLeftChild(),  key));
		}
		else { 
			if (node.leftChild == null) {
				return node.getRightChild();
			}
			if (node.rightChild  == null) {
				return node.getLeftChild();
			}
			BinaryTreeNode tmp = node;
			node = min(tmp.rightChild);
			node.setRightChild(delete(tmp.rightChild));
			node.setLeftChild(tmp.leftChild);
		} 
		return node;
	}

	@Override
	public Value remove(Key k) {
		Value old = null;
		BinaryTreeNode node = findNode(root, k);
		if (k == null || isEmpty() || node == null) {
			return null;
		}
		old = (Value) node.getValue();
		root = removeHelper(root, k);
		size--;
		return old;
	}

	@Override
	public Iterable<Key> keySet() {
		// TODO Auto-generated method stub
		List<Key> keys = new ArrayList<Key>();
		for(BinaryTreeNode current : getNodesInOrder()) {	
			keys.add((Key) current.getKey());
		}
		return keys;
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return size;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return size() == 0;
	}

	@Override
	public BinaryTreeNode<Key, Value> getRoot() {
		// TODO Auto-generated method stub
		return root;
	}

	public BinaryTreeNode<Key, Value> parentHelper(BinaryTreeNode<Key, Value> node, Key k){
		if (node == null) {
			return null;
		}

		BinaryTreeNode parent = null;
		while (node != null) {
			int difference = comparator.compare(node.getKey(), k);
			if (difference < 0) {
				parent = node;
				node = node.getRightChild();
			} else if (difference > 0) {
				parent = node;
				node = node.getLeftChild();
			} else {
				break;
			}
		}
		return parent;
	}

	@Override
	public BinaryTreeNode<Key, Value> getParent(BinaryTreeNode<Key, Value> node) {
		// TODO Auto-generated method stub
		if(isRoot(node) || node == null) {
			return null;
		} else {
			return parentHelper(getRoot(), node.getKey());
		}
	}

	@Override
	public boolean isInternal(BinaryTreeNode<Key, Value> node) {
		// TODO Auto-generated method stub
		if(node == null) {
			return false;
		}else if(node.leftChild != null) {
			return true;
		} else if(node.rightChild != null) {
			return true;
		}
		return false;
	}

	@Override
	public boolean isExternal(BinaryTreeNode<Key, Value> node) {
		// TODO Auto-generated method stub
		if(node == null) {
			return true;
		}
		if(getRightChild(node) == null && getLeftChild(node) == null) {
			return true;
		}
		return false;
	}

	@Override
	public boolean isRoot(BinaryTreeNode<Key, Value> node) {
		// TODO Auto-generated method stub
		if(node == getRoot()) {
			return true;
		}
		return false;
	}

	@Override
	public BinaryTreeNode<Key, Value> getNode(Key k) {
		// TODO Auto-generated method stub
		return findNode(root, k);
	}

	@Override
	public Value getValue(Key k) {
		// TODO Auto-generated method stub
		if(getNode(k) == null) {
			return null;
		}
		return (Value) getNode(k).getValue();
	}

	@Override
	public BinaryTreeNode<Key, Value> getLeftChild(BinaryTreeNode<Key, Value> node) {
		// TODO Auto-generated method stub
		if(node == null) {
			return null;
		}
		return node.leftChild;
	}

	@Override
	public BinaryTreeNode<Key, Value> getRightChild(BinaryTreeNode<Key, Value> node) {
		// TODO Auto-generated method stub
		if(node == null) {
			return null;
		}
		return node.rightChild;
	}

	@Override
	public BinaryTreeNode<Key, Value> sibling(BinaryTreeNode<Key, Value> node) {
		// TODO Auto-generated method stub
		if(!isRoot(node)) {
			int difference = comparator.compare(getParent(node).getKey(), (Key) node.getKey());
			if (difference > 0) {
				return getParent(node).getRightChild();
			} else {
				return getParent(node).getLeftChild();
			}
		}else {
			return null;
		}
	}

	@Override
	public boolean isLeftChild(BinaryTreeNode<Key, Value> node) {
		// TODO Auto-generated method stub
		if(isRoot(node)) {
			return false;
		}
		int difference = comparator.compare(getParent(node).getKey(), (Key) node.getKey());
		if(difference > 0) {
			return true;
		}
		return false;
	}

	@Override
	public boolean isRightChild(BinaryTreeNode<Key, Value> node) {
		// TODO Auto-generated method stub
		if(isRoot(node)) {
			return false;
		}
		int difference = comparator.compare(getParent(node).getKey(), (Key) node.getKey());
		if(difference < 0) {
			return true;
		}
		return false;
	}

	public void inOrderHelper (BinaryTreeNode current, List<BinaryTreeNode<Key,Value>> list) {
		if(current == null) {
			return;
		}
		inOrderHelper(current.leftChild, list);
		list.add(current);
		inOrderHelper(current.rightChild, list);		
	}

	@Override
	public List<BinaryTreeNode<Key, Value>> getNodesInOrder() {
		List<BinaryTreeNode<Key, Value>> list = new ArrayList<BinaryTreeNode<Key, Value>>();
		if(getRoot() != null) {
			inOrderHelper(getRoot(), list);
		}
		return list;
	}


	@Override
	public void setComparator(Comparator<Key> C) {
		// TODO Auto-generated method stub
		this.comparator = C;
	}

	@Override
	public Comparator<Key> getComparator() {
		// TODO Auto-generated method stub
		return comparator;
	}

	@Override
	public BinaryTreeNode<Key, Value> ceiling(Key k) {
		BinaryTreeNode node = ceilingHelper(root, k);
		if (k == null || isEmpty() || node == null) {
			return null;
		}else {
			return node;
		}
	}

	private BinaryTreeNode<Key, Value> ceilingHelper(BinaryTreeNode node, Key key) {
		if (node == null) {
			return null;
		}
		int difference = comparator.compare(key, (Key) node.getKey());
		if (difference == 0) {
			return node;
		}
		if (difference < 0) { 
			BinaryTreeNode current = ceilingHelper(node.getLeftChild(), key); 
			if (current != null) {
				return current;
			}
			else {
				return node; 
			}
		} 
		return ceilingHelper(node.getRightChild(), key); 
	} 

	@Override
	public BinaryTreeNode<Key, Value> floor(Key k) {
		// TODO Auto-generated method stub
		BinaryTreeNode node = floorHelper(root, k);
		if (k == null || isEmpty() || node == null) {
			return null;
		}else {
			return node;
		}
	}

	private BinaryTreeNode<Key, Value> floorHelper(BinaryTreeNode node, Key key) {
		if (node == null) {
			return null;
		}
		int difference = comparator.compare(key, (Key) node.getKey());
		if (difference == 0) {
			return node;
		}
		if (difference < 0) { 
			return floorHelper(node.getLeftChild(), key);
		}
		BinaryTreeNode current = floorHelper(node.getRightChild(), key);
		if(current != null) {
			return current;
		}
		return node; 
	}
}
