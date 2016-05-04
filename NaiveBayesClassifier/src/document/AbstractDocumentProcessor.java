/*
 * 
 */
package document;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import util.TreeBuilder;


/**
 * The Class AbstractDocumentProcessor.
 */
public abstract class AbstractDocumentProcessor {

	/** The test documents. */
	@SuppressWarnings("unused")
	private ArrayList<String> testDocuments;

	/** The stop words. */
	private TreeBuilder stopWords = new TreeBuilder();

	/**
	 * Import doc file.
	 *
	 * @param filePath
	 *            the file path
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public void importDocFile(String filePath) throws IOException {
		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
			String curr;
			ArrayList<String> list = new ArrayList<String>();
			while ((curr = br.readLine()) != null) {
				list.add(curr);
			}
			this.testDocuments = list;
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Import stop word file.
	 *
	 * @param filePath
	 *            the file path
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public void importStopWordFile(String filePath) throws IOException {
		stopWords.importFile(filePath);
	}

	/**
	 * Process documents is the core method that needs to be implemented to
	 * process document types. For this particular Naive Bayes implementation,
	 * there are only training and test document styled implementations.
	 */
	// Process Documents
	public abstract void process();

}
