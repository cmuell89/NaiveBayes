/*
 * 
 */
package unittest;

import static org.junit.Assert.*;

import java.io.IOException;
import org.junit.Test;

import document.TrainingDocumentFiler;
import document.TrainingDocumentProcessor;

// TODO: Auto-generated Javadoc
/**
 * The Class TestDocumentProcessorTest.
 */
public class TestDocumentProcessorTest {

	/**
	 * Test document processor globally.
	 */
	@Test
	public void testDocumentProcessorGlobally() {
		TrainingDocumentProcessor processor = new TrainingDocumentProcessor();
		TrainingDocumentFiler[] categories;
		
		try {
			processor.importDocFile("testTextFiles/testDocuments.txt");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		try {
			processor.importStopWordFile("stopWords.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}
		

		processor.process();
		categories = processor.getTrainingDocumentFilerArray();
		assertEquals("Negative sentiment category should be 0",0,categories[0].getCategory());
		assertEquals("Positive sentiment category should be 1",1,categories[1].getCategory());
		assertEquals("Number of negative documents should be 8", 8, categories[0].getNumberOfDocuments());
		assertEquals("Number of positive documents should be 8", 8, categories[1].getNumberOfDocuments());
	}

}
