/*
 * 
 */
package document;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import util.*;

// TODO: Auto-generated Javadoc
/**
 * The Class TrainingDocumentProcessor.
 */
public class TrainingDocumentProcessor extends AbstractDocumentProcessor {

	/** The test documents. */
	private ArrayList<String> testDocuments;

	/** The categories. */
	private TrainingDocumentFiler[] categories = { new TrainingDocumentFiler(),
			new TrainingDocumentFiler() };

	/** The stop words. */
	private TreeBuilder stopWords = new TreeBuilder();

	/** The sp. */
	private StringProcessor sp = new StringProcessor();

	/**
	 * Instantiates a new training document processor.
	 */
	public TrainingDocumentProcessor() {
	}

	/**
	 * Import training test file and split on lines, storing in ArrayList. Using
	 * buffered reader correctly imports documents to avoid encoding
	 * complications.
	 *
	 * @see document.AbstractDocumentProcessor#importDocFile(java.lang.String)
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
	 * Import stop word text file, storing in binary search tree.
	 *
	 * @seedocument.AbstractDocumentProcessor#importStopWordFile(java.lang.String)
	 */
	public void importStopWordFile(String filePath) throws IOException {
		stopWords.importFile(filePath);
	}

	/**
	 * Process all string documents by removing alphanumeric characters, parsing
	 * negations, moving stopwords and creating nGrams (stored separately from
	 * words).
	 *
	 * @see document.AbstractDocumentProcessor#process()
	 */
	public void process() {
		categorySplitter();
		ArrayList<String> rawDocs;
		sp.setStopWordTree(stopWords);
		// For each of the categories, process the raw documents by using
		// StringProcessor.java methods
		for (TrainingDocumentFiler category : categories) {
			rawDocs = category.getRawDocuments();
			for (int i = 0; i < rawDocs.size(); i++) {
				String rawDoc = rawDocs.get(i);
				String[] wordArray;
				String[] nGramArray;
				rawDoc = rawDoc.toLowerCase();
				wordArray = wordProcessor(rawDoc);
				nGramArray = nGramProcessor(rawDoc);
				category.addDocument(wordArray, nGramArray);
				category.addDocumentCount();
			}
		}
	}


	/**
	 * Category splitter.Split raw document strings into their respective
	 * Category objects based on classification category
	 */
	private void categorySplitter() {
		if (testDocuments.size() != 0) {
			for (String string : testDocuments) {
				if (string.startsWith("0")) {
					categories[0].addRawDocument(string);
				} else {
					categories[1].addRawDocument(string);
				}
			}
			categories[0].setCategory(0);
			categories[1].setCategory(1);
		}
	}

	/**
	 * Word processor that processes string documents by removing alphanumeric
	 * characters, parsing negations, and removing stopwords.
	 *
	 * @param s
	 *            the s
	 * @return the string[]
	 */
	private String[] wordProcessor(String s) {
		s = s.replaceFirst("[^A-Za-z]"," ");
		s = sp.removeNonAlphaNum(s).trim();
		s = s.replaceAll("( [a-z] )", " ");
		s = sp.negationParser(s).trim();
		try {
			s = sp.removeStopWords(s).trim();
		} catch (Exception e) {
			e.printStackTrace();
		}
		s = s.replaceAll("( [a-z] )", " ");

		return s.split(" ");
	}

	/**
	 * N gram processor. Removes alphaNumerics, calls nGrammer, and returns a
	 * String[] of nGrams that contained in the chosen feature list.
	 *
	 * @param s
	 *            the s
	 * @return the string[]
	 */
	private String[] nGramProcessor(String s) {
		s = sp.removeNonAlphaNum(s).trim();
		return sp.nGrammer(s, 2);
	}

	/**
	 * Gets the array of TrainingDocumentFilers
	 *
	 * @return the TrainingDocumentFiler array
	 */
	public TrainingDocumentFiler[] getTrainingDocumentFilerArray() {
		return this.categories;
	}
}
