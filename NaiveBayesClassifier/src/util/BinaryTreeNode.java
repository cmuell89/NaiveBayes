/*
 * 
 */
package util;

// TODO: Auto-generated Javadoc
/**
 * The Class BinaryTreeNode.
 */
public class BinaryTreeNode {

	/** The left. */
	public BinaryTreeNode left;

	/** The right. */
	public BinaryTreeNode right;

	/** The value. */
	public String value;

	/**
	 * Instantiates a new binary tree node.
	 */
	BinaryTreeNode() {
	}

	/**
	 * Instantiates a new binary tree node.
	 *
	 * @param value
	 *            the value
	 */
	BinaryTreeNode(String value) {
		this.value = value;
	}

	/**
	 * Instantiates a new binary tree node.
	 *
	 * @param node
	 *            the node
	 */
	BinaryTreeNode(BinaryTreeNode node) {
		left = node.left;
		right = node.right;
		value = node.value;
	}
}
