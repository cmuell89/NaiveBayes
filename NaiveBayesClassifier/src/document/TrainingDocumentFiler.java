/*
 * 
 */
package document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

// TODO: Auto-generated Javadoc
/**
 * The Class TrainingDocumentFiler. This class stores or "files"
 * TrainingDocument class objects and maintains basic information about the
 * TrainingDocuments stored within.
 */
public class TrainingDocumentFiler {

	/** The raw documents. */
	// sorted wordlist
	private ArrayList<String> rawDocuments = new ArrayList<String>();

	/** The category. */
	// 0 for negative; 1 for positive;
	private int category;

	/** The number of documents. */
	// number of documents (movie reviews)
	private int numberOfDocuments;

	/** The documents. */
	private ArrayList<TrainingDocument> documents = new ArrayList<TrainingDocument>();

	/** The word frequency map. */
	private Map<String, Integer> wordFrequencyMap = new HashMap<String, Integer>();

	/** The n gram frequency map. */
	private Map<String, Integer> nGramFrequencyMap = new HashMap<String, Integer>();

	/**
	 * Instantiates a new training document filer.
	 */
	public TrainingDocumentFiler() {
	}

	/**
	 * Adds a Test Document object to the documents list. It calls the
	 * mapAllWords() function to ensure each document object stored has updated
	 * HashMaps for the frequency of words (see TestDocument)
	 *
	 * @param parsedDocument
	 *            the parsed document
	 * @param nGramDocument
	 *            the n gram document
	 */
	public void addDocument(String[] parsedDocument, String[] nGramDocument) {
		TrainingDocument newDoc = new TrainingDocument(parsedDocument,
				nGramDocument);
		newDoc.mapAllWords();
		documents.add(newDoc);
	}

	/**
	 * Gets the document array list.
	 *
	 * @return the document array list
	 */
	public ArrayList<TrainingDocument> getDocumentArrayList() {
		return this.documents;
	}

	/**
	 * Adds the raw document.
	 *
	 * @param document
	 *            the document
	 */
	public void addRawDocument(String document) {
		this.rawDocuments.add(document);
	}

	/**
	 * Gets the raw documents.
	 *
	 * @return the raw documents
	 */
	public ArrayList<String> getRawDocuments() {
		return this.rawDocuments;
	}

	/**
	 * Gets the category.
	 *
	 * @return the category
	 */
	public int getCategory() {
		return category;
	}

	/**
	 * Sets the category.
	 *
	 * @param category
	 *            the new category
	 */
	public void setCategory(int category) {
		this.category = category;
	}

	/**
	 * Gets the number of documents.
	 *
	 * @return the number of documents
	 */
	public int getNumberOfDocuments() {
		return numberOfDocuments;
	}

	/**
	 * Adds the document count.
	 */
	public void addDocumentCount() {
		this.numberOfDocuments++;
	}

	/**
	 * Sets the number of documents.
	 * Unused.
	 * @param numberOfDocuments
	 *            the new number of documents
	 */
	public void setNumberOfDocuments(int numberOfDocuments) {
		this.numberOfDocuments = numberOfDocuments;
	}

	/**
	 * Gets the word frequency map.
	 *
	 * @return the word frequency map
	 */
	public Map<String, Integer> getWordFrequencyMap() {
		return wordFrequencyMap;
	}

	/**
	 * Sets the frequency hash.
	 *
	 * @param frequencyHash
	 *            the frequency hash
	 */
	public void setFrequencyHash(Map<String, Integer> frequencyHash) {
		this.wordFrequencyMap = frequencyHash;
	}

	/**
	 * Gets the n gram frequency map.
	 *
	 * @return the n gram frequency map
	 */
	public Map<String, Integer> getnGramFrequencyMap() {
		return nGramFrequencyMap;
	}

	/**
	 * Setn gram frequency map.
	 *
	 * @param nGramFrequencyMap
	 *            the n gram frequency map
	 */
	public void setnGramFrequencyMap(Map<String, Integer> nGramFrequencyMap) {
		this.nGramFrequencyMap = nGramFrequencyMap;
	}

}
