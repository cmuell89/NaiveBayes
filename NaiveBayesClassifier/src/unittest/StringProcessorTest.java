/*
 * 
 */
package unittest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;

import util.StringProcessor;
import util.TreeBuilder;

/**
 * The Class StringProcessorTest.
 */
public class StringProcessorTest {

	/** The sp. */
	StringProcessor sp = new StringProcessor();
	TreeBuilder stopWordTree = new TreeBuilder();

	/** The test n gram. */
	String testNGram = "And of course it will be not awesome. It will be great.";

	/** The test bi gram. */
	String testBiGram = "And of, of course, course it, it will, will be, be not, not awesome., awesome. It, It will, will be, be great.";

	/** The bi grams. */
	String[] biGrams;

	/** The test string. */
	String testString = "";

	/** The remove alpha num test. */
	String removeAlphaNumTest = "";

	/** The negation parse test. */
	String negationParseTest = "";

	/** The stop words. */
	String stopWords = "";

	/**
	 * String processor test init.
	 */
	@Before
	public void StringProcessorTestInit() {
		try {
			stopWordTree.importFile("stopWords.txt");
		} catch (IOException e1) {
			fail("IOException: Check for stopword filename.");
		}
		try {
			Scanner in = new Scanner(new File("stopWords.txt"));
			stopWords = in.useDelimiter("\\A").next();
			in.close();
		} catch (FileNotFoundException e) {
			fail("FileNoteFoundException: Check filename.");
		}
		try {
			Scanner in = new Scanner(new File("testTextFiles/unitTest.txt"));
			testString = in.useDelimiter("\\A").next();
			in.close();
		} catch (FileNotFoundException e) {
			fail("FileNoteFoundException: Check filename.");
		}
		try {
			Scanner in = new Scanner(new File(
					"testTextFiles/removeAlphaNum.txt"));
			removeAlphaNumTest = in.useDelimiter("\\A").next();
			in.close();
		} catch (FileNotFoundException e) {
			fail("FileNoteFoundException: Check filename.");
		}
		try {
			Scanner in = new Scanner(
					new File("testTextFiles/negationParse.txt"));
			negationParseTest = in.useDelimiter("\\A").next();
			in.close();
		} catch (FileNotFoundException e) {
			fail("FileNoteFoundException: Check filename.");
		}
		biGrams = testBiGram.split(", ");
	}

	/**
	 * Test remove all alpha numerics.
	 */
	@Test
	public void testRemoveAllAlphaNumerics() {
		String result = sp.removeNonAlphaNum(testString);
		assertEquals("Strings do not match.", removeAlphaNumTest, result);
	}

	/**
	 * Test negation parser.
	 */
	@Test
	public void testNegationParser() {
		String result = sp.negationParser(testString);
		assertEquals("Strings do not match.", negationParseTest, result);
	}

	/**
	 * Test remove all stop words.
	 */
	@Test
	public void testRemoveAllStopWords() {
		String result;
		try {
			result = sp.removeStopWords(stopWords);
			assertEquals("Strings not equal. They should be empty.", "", result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Test n grammer.
	 */
	@Test
	public void testNGrammer() {
		String[] result = sp.nGrammer(testNGram, 2);
		assertEquals("Lengths of arrays not equal.", biGrams.length,
				result.length);
		for (int i = 0; i < biGrams.length; i++) {
			assertEquals("At index " + i
					+ " of biGrams. These strings are not equal.", biGrams[i],
					result[i]);
		}
	}

}
