/*
 * 
 */
package util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.Collator;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Scanner;

import util.BinaryTreeNode;

// TODO: Auto-generated Javadoc
/**
 * The Class TreeBuilder.
 */
public class TreeBuilder {
		
		/** The count. */
		private int count;
		
		/** The root. */
		private BinaryTreeNode root = new BinaryTreeNode();
		
		/**
		 * Import file.
		 *
		 * @param filename the filename
		 * @throws IOException Signals that an I/O exception has occurred.
		 */
		public void importFile(String filename) throws IOException {
			File file = new File(filename);
			LinkedList<String> wordList = new LinkedList<String>();
			Scanner in;
			try {
				in = new Scanner(file);
				while (in.hasNextLine()) {
							String line = in.nextLine();
							wordList.add(line.trim());
						}
				Collections.sort(wordList, new Comparator<String>() {
			         @Override
			         public int compare(String o1, String o2) {
			             return Collator.getInstance().compare(o1, o2);
			         }
			     });
				in.close();
			} catch (FileNotFoundException e) {
				throw new IOException();
			}
			root = buildBalancedTree(wordList, 0, wordList.size() - 1);
		}	
		
	/**
	 * Balanced tree.
	 *
	 * @param wordList the word list
	 * @param start the start
	 * @param end the end
	 * @return the binary tree node
	 */
	public BinaryTreeNode buildBalancedTree(LinkedList<String> wordList, int start, int end) {
		LinkedList<String> list = wordList;
		if (start > end) {
			return null;
		}
		// Get the middle of the list
		int middle = start + (end - start) / 2;
		// Recursively work on left child branch of parent node.
		BinaryTreeNode leftChild = buildBalancedTree(list, start, middle - 1);
		BinaryTreeNode parent = new BinaryTreeNode(list.getFirst().toString());
		list.removeFirst();
		parent.left = leftChild;
		parent.right = buildBalancedTree(list, middle + 1, end);
		count++;
		return parent;
	}
	
	/**
	 * Find.
	 *
	 * @param word the word
	 * @return the int
	 */
	//returns 1 if word is found, returns 0 if word is not found, returns -1 if empty list.
	public int find(String word) {
		String test = word.toLowerCase();
		int result = 0;
		BinaryTreeNode parent = root;
		String curr;
		// check if list is empty
		if (parent.value == null) {
			return -1;
		}
		// preceeding word is parent node value if reassigning parent to right
		// child
		// proceeding word is parent node value if reassigning parent to left
		// child
		while (parent != null) {
			curr = parent.value;
			// check if current node value is equal to word, return null if true
			if (test.equals(curr)) {
				result = 1;
				break;
			}
			if (test.compareTo(curr) < 0) {
				if (parent.left != null) {
					parent = parent.left;
				} else {
					parent = null;
				}
			} else if (test.compareTo(curr) > 0) {
				if (parent.right != null) {
					parent = parent.right;
				} else {
					parent = null;
				}
			}
		}
		return result;
	}

	/**
	 * Adds the.
	 *
	 * @param word the word
	 */
	public void add(String word) {
		word = word.toLowerCase();
		BinaryTreeNode parent = root;
		if (parent.value == null) {
			root.value = word;
		} else {
			String curr;
			while (parent != null) {
				curr = parent.value;
				if (word.compareTo(curr) < 0) {
					if (parent.left != null) {
						parent = parent.left;
					} else {
						BinaryTreeNode newNode = new BinaryTreeNode(word);
						parent.left = newNode;
						count++;
						break;
					}
				} else if (word.compareTo(curr) > 0) {
					if (parent.right != null) {
						parent = parent.right;
					} else {
						BinaryTreeNode newNode = new BinaryTreeNode(word);
						parent.right = newNode;
						count++;
						break;
					}
				} else {
					parent = null;
				}
			}
		}
	}
	
	/**
	 * To string in order.
	 *
	 * @param parent the parent
	 * @return the string
	 */
	public String toStringInOrder(BinaryTreeNode parent){
		String string = "";
		if (parent.left != null) {
			string += (" " + toStringInOrder(parent.left));
		}
		string += (" " +  parent.value);
		if (parent.right != null) {
			string += (" " +  toStringInOrder(parent.right));
		}
		return string.trim();
	}
	
	/**
	 * Gets the root.
	 *
	 * @return the root
	 */
	public BinaryTreeNode getRoot() {
		return root;
	}

	/**
	 * Gets the count.
	 *
	 * @return the count
	 */
	public int getCount() {
		return count;
	}
	
	/**
	 * Prints the in order.
	 *
	 * @param parent the parent
	 */
	public void printInOrder(BinaryTreeNode parent) {
		if (parent.left != null) {
			printInOrder(parent.left);
		}
		System.out.println(parent.value);
		if (parent.right != null) {
			printInOrder(parent.right);
		}
	}

}
