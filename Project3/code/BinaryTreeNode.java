package code;

import given.Entry;

/*
 * The binary node class which extends the entry class.
 * This will be used in linked tree implementations
 * 
 */
public class BinaryTreeNode<Key, Value> extends Entry<Key, Value> {
	protected BinaryTreeNode leftChild;
	protected BinaryTreeNode rightChild;

	public BinaryTreeNode(Key k, Value v) {
		super(k, v);
		leftChild = null;
		rightChild = null;
	}
	public BinaryTreeNode(Key k, Value v, BinaryTreeNode l, BinaryTreeNode r) {
		super(k, v);
		leftChild = l;
		rightChild = r;
	}
	public BinaryTreeNode getLeftChild() {
		return leftChild;
	}
	public void setLeftChild(BinaryTreeNode leftChild) {
		this.leftChild = leftChild;
	}
	public BinaryTreeNode getRightChild() {
		return rightChild;
	}
	public void setRightChild(BinaryTreeNode rightChild) {
		this.rightChild = rightChild;
	}


	/*
	 * 
	 * This constructor is needed for the autograder. You can fill the rest to your liking.
	 * YOUR CODE AFTER THIS:
	 * 
	 */


}
