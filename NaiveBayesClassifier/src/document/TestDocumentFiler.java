/*
 * 
 */
package document;

import java.util.ArrayList;

// TODO: Auto-generated Javadoc
/**
 * The Class TestDocumentFiler. This class stores or files TestDocument class
 * objects and maintains basic information on each about the TestDocuments
 * stored within.
 */
public class TestDocumentFiler {

	/** The raw documents containing the separated text documents of each. */
	private ArrayList<String> rawDocuments = new ArrayList<String>();

	/** The number of documents. */
	private int numberOfDocuments;

	/** The documents. */
	private ArrayList<TestDocument> documents = new ArrayList<TestDocument>();

	/**
	 * Instantiates a new test document filer.
	 */
	public TestDocumentFiler() {
	}

	/**
	 * Adds a Test Document object to the documents list. It calls the
	 * mapAllWords() function to ensure each document object stored has
	 * updated HashMaps for the frequency of words (see TestDocument)
	 *
	 * @param wordList
	 *            the parsed document
	 * @param nGramlist
	 *            the n gram document
	 * @param category
	 *            the category
	 */
	public void addDocument(String[] wordList, String[] nGramlist, int category) {
		TestDocument newDoc = new TestDocument(wordList, nGramlist, category);
		newDoc.mapAllWords();
		documents.add(newDoc);
	}

	/**
	 * Gets the document array list.
	 *
	 * @return the document array list
	 */
	public ArrayList<TestDocument> getDocumentArrayList() {
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
	 *
	 * @param numberOfDocuments
	 *            the new number of documents
	 */
	public void setNumberOfDocuments(int numberOfDocuments) {
		this.numberOfDocuments = numberOfDocuments;
	}

}
