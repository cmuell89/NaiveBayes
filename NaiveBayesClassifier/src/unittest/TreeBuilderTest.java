/*
 * 
 */
package unittest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import org.junit.Test;

import util.TreeBuilder;

// TODO: Auto-generated Javadoc
/**
 * The Class TreeBuilderTest.
 */
public class TreeBuilderTest {
	
	/** The builder. */
	TreeBuilder builder = new TreeBuilder();
	
	/**
	 * Test import file fail.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Test(expected = IOException.class)
	public void testImportFileFail() throws IOException {
		try {
			builder.importFile("DNE.txt");
		} catch (IOException e) {
			assertNotNull("should not be null", e);
			throw e;
		}
	}
		
	/**
	 * Test import file.
	 */
	@Test
	public void testImportFile(){
		try {
			builder.importFile("stopWords.txt");
			if(builder.getCount() != 175){
				fail("Incorrectly imported file. Binary search tree should have 175 words.");
			}
			assertEquals("Root not \'nor\' as expected", "nor", builder.getRoot().value);
		} catch (IOException e) {
			fail("IOException should not have occured. Check filename/if file exists.");
		}
	}
	
	/**
	 * Test find word.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Test
	public void testFindWord() throws IOException{
		//empty binary tree
		assertEquals("Did not return -1 as expected",-1,builder.find("ciao"));
		builder.importFile("stopWords.txt");
		//filled binary tree
		assertEquals("Did not return 1 as expected",1, builder.find("can't"));
		assertEquals("Did not return 0 as expected",0, builder.find("ciao"));
	}
	
	/**
	 * Test add word.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Test
	public void testAddWord() throws IOException{
		builder.importFile("stopWords.txt");
		builder.add("ciao");
		assertEquals("Did not find added word",1,builder.find("ciao"));
	}
	
	/**
	 * Test to string in order.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Test
	public void testToStringInOrder() throws IOException{
		builder.importFile("stopWords.txt");
		String actual = builder.toStringInOrder(builder.getRoot());
		String expected = "";
		try {
			//words in stopWords.txt are in order
			File file = new File("stopWords.txt");
			Scanner in = new Scanner(file);
			while (in.hasNextLine()) {
				String line = in.nextLine();
				expected += (" " + line);
			}
			expected = expected.trim();
			in.close();
		} catch (FileNotFoundException e) {
			fail("Unpected exception");
		}
		assertEquals("Strings not equal, toStringInOrder fail or strings not in order in test file.", expected, actual);
	}
		
		
		
		
		
}
