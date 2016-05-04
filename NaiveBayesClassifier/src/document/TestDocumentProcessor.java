/*
 * 
 */
package document;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import util.StringProcessor;
import util.TreeBuilder;

// TODO: Auto-generated Javadoc
/**
 * The Class TestDocumentProcessor. Processes all .txt files containing labeled
 * data to be stored into testable TestDocument objects for the Naive Bayes
 * Classifier.
 */
public class TestDocumentProcessor extends AbstractDocumentProcessor {

	/** The raw documents. */
	private ArrayList<String> rawDocuments;

	/** The test documents. */
	private TestDocumentFiler testDocuments = new TestDocumentFiler();

	/** The stop words. */
	private TreeBuilder stopWords = new TreeBuilder();

	/** The sp. */
	private StringProcessor sp = new StringProcessor();

	/** The feature list. */
	private Map<String, Integer> featureList = new HashMap<String, Integer>();

	/**
	 * Instantiates a new test document processor.
	 */
	public TestDocumentProcessor() {
	}

	/**
	 * This function builds a hashmap of a chosen feature array (from
	 * FeatureSelector objects). It is used as reference of features when
	 * building feature lists for each test document
	 *
	 * @param features
	 *            the new feature list map
	 */
	public void setFeatureMap(String[] features) {
		for (String feature : features) {
			featureList.put(feature, 1);
		}
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
			this.rawDocuments = list;
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
		sp.setStopWordTree(stopWords);
		for (String rawDoc : rawDocuments) {
			int category;
			if (rawDoc.startsWith("0")) {
				category = 0;
			} else {
				category = 1;
			}
			String[] wordArray;
			String[] nGramArray;
			rawDoc = rawDoc.toLowerCase();
			wordArray = wordProcessor(rawDoc);
			nGramArray = nGramProcessor(rawDoc);
			testDocuments.addDocument(wordArray, nGramArray, category);
			testDocuments.addDocumentCount();
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
		String[] str = s.split(" ");
		return sp.keepOnlyFeatures(featureList, str);
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
		return sp.keepOnlyFeatures(featureList, sp.nGrammer(s, 2));
	}

	/**
	 * Gets the test documents.
	 *
	 * @return the test documents
	 */
	public TestDocumentFiler getTestDocuments() {
		return this.testDocuments;
	}
}
