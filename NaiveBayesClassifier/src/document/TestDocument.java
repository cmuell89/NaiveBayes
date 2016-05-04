/*
 * 
 */
package document;

import java.util.HashMap;
import java.util.Map;

import util.hashUtilities;

// TODO: Auto-generated Javadoc
/**
 * The Class TestDocument. Objects of this class are used to store individual test documents. In this implementation,
 * each test document is a singe movie review that will be classified as either positive (1) or negative (0).
 */
public class TestDocument{
	
	/** The words. */
	private String[] words;
	
	/** The word map. */
	private Map<String, Integer> wordMap;
	
	/** The n grams. */
	private String[] nGrams;
	
	/** The n gram map. */
	private Map<String, Integer> nGramMap;
	
	/** The actual category. */
	private int actualCategory;
	
	/** The test category. */
	private int testCategory;

	
	/**
	 * Instantiates a new test document.
	 *
	 * @param words the words
	 * @param nGrams the n grams
	 * @param actualCategory the actual category (used for measureing accuracy of classification label)
	 */
	TestDocument(String[] words, String[] nGrams, int actualCategory) {
		this.words = words;
		this.nGrams = nGrams;
		this.actualCategory = actualCategory;
	}
	
	/**
	 * Map all words. Creates frequency valued <String,Integer> hashmaps of each word array.
	 * This implmentation has a set of singular words and nGrams.
	 */
	public void mapAllWords() {
		hashUtilities hasher = new hashUtilities();
		this.wordMap = new HashMap<String, Integer>();
		this.nGramMap = new HashMap<String, Integer>();
		hasher.frequencyHasher(wordMap, words);
		hasher.frequencyHasher(nGramMap, nGrams);
	}
	
	/**
	 * Gets the words.
	 *
	 * @return the words
	 */
	public String[] getWords() {
		return words;
	}

	/**
	 * Sets the words.
	 *
	 * @param words the new words
	 */
	public void setWords(String[] words) {
		this.words = words;
	}

	/**
	 * Gets the word map.
	 *
	 * @return the word map
	 */
	public Map<String, Integer> getWordMap() {
		return wordMap;
	}

	/**
	 * Gets the n grams.
	 *
	 * @return the n grams
	 */
	public String[] getnGrams() {
		return nGrams;
	}

	/**
	 * Sets the n grams.
	 *
	 * @param nGrams the new n grams
	 */
	public void setnGrams(String[] nGrams) {
		this.nGrams = nGrams;
	}

	/**
	 * Gets the n gram map.
	 *
	 * @return the n gram map
	 */
	public Map<String, Integer> getnGramMap() {
		return nGramMap;
	}

	/**
	 * Gets the test category.
	 *
	 * @return the test category
	 */
	public int getTestCategory() {
		return testCategory;
	}

	/**
	 * Sets the test category.
	 *
	 * @param testCategory the new test category
	 */
	public void setTestCategory(int testCategory) {
		this.testCategory = testCategory;
	}

	/**
	 * Gets the actual category.
	 *
	 * @return the actual category
	 */
	public int getActualCategory() {
		return actualCategory;
	}
}
