/*
 * 
 */
package document;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import util.hashUtilities;

// TODO: Auto-generated Javadoc
/**
 * The Class TrainingDocument.
 */
public class TrainingDocument{
	
	/** The total count. */
	private int totalCount;
	
	/** The word count. */
	private int wordCount;
	
	/** The words. */
	private String[] words;
	
	/** The word map. */
	private Map<String, Integer> wordMap;
	
	/** The n gram count. */
	private int nGramCount;
	
	/** The n grams. */
	private String[] nGrams;
	
	/** The n gram map. */
	private Map<String, Integer> nGramMap;

	/**
	 * Instantiates a new training document.
	 *
	 * @param words the words
	 * @param nGrams the n grams
	 */
	TrainingDocument(String[] words, String[] nGrams) {
		this.words = words;
		Arrays.sort(this.words);
		this.nGrams = nGrams;
		Arrays.sort(this.nGrams);
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
	
	public boolean hasWordTest(String word){
		boolean result = false;
		if(nGramMap.containsKey(word) || wordMap.containsKey(word)){
			result = true;
		}
		return result;
	}

	/**
	 * Gets the total count.
	 *
	 * @return the total count
	 */
	public int getTotalCount() {
		return totalCount;
	}

	/**
	 * Update total count.
	 *
	 * @param totalCount the total count
	 */
	public void updateTotalCount(int totalCount) {
		this.totalCount = words.length + nGrams.length;
	}

	/**
	 * Gets the word count.
	 *
	 * @return the word count
	 */
	public int getWordCount() {
		return wordCount;
	}

	/**
	 * Update word count.
	 */
	public void updateWordCount() {
		this.wordCount = words.length;
		;
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
	 * Gets the n gram count.
	 *
	 * @return the n gram count
	 */
	public int getnGramCount() {
		return nGramCount;
	}

	/**
	 * Sets the n gram count.
	 *
	 * @param nGramCount the new n gram count
	 */
	public void setnGramCount(int nGramCount) {
		this.nGramCount = nGramCount;
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
}
