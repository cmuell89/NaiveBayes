/*
 * 
 */
package unittest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import org.junit.Test;
import util.*;

// TODO: Auto-generated Javadoc
/**
 * The Class FeatureSelectorTest.
 */
public class FeatureSelectorTest {

	/** The fh. */
	hashUtilities fh = new hashUtilities( );
	
	/** The map. */
	Map<String,Integer> map = new HashMap<String,Integer>();
	
	/** The array. */
	String[] array;
	
	/**
	 * Test.
	 */
	@Test
	public void test() {
		Scanner in;
		try {
			in = new Scanner(new File("testTextFiles/wordFrequencyTest.txt"));
			String str = "";
			while(in.hasNextLine()){
				str += in.nextLine() + " ";
			}
			str = str.replaceAll("[0-9]", "");
			str = str.replaceAll("  ", " ");
			str = str.trim();
			String[] array = str.split(" ");
			for(String s : array){
				s = s.trim();	
			}
			this.map = fh.frequencyHasher(this.map, array);
			assertEquals("These values should be equal.",10, fh.getFrequency(this.map, "logical"));
			assertEquals("These values should be equal.",9, fh.getFrequency(this.map, "convulsion"));
			assertEquals("These values should be equal.",8, fh.getFrequency(this.map, "round"));
			assertEquals("These values should be equal.",7, fh.getFrequency(this.map, "spirits"));
			assertEquals("These values should be equal.",6, fh.getFrequency(this.map, "bluster"));
			assertEquals("These values should be equal.",5, fh.getFrequency(this.map, "luxury"));
			assertEquals("These values should be equal.",4, fh.getFrequency(this.map, "appeal"));
			assertEquals("These values should be equal.",3, fh.getFrequency(this.map, "happy"));
			assertEquals("These values should be equal.",2, fh.getFrequency(this.map, "joy"));
			assertEquals("These values should be equal.",1, fh.getFrequency(this.map, "the"));
			assertEquals("These values should be equal.",0, fh.getFrequency(this.map, "notinlist"));
		} catch (FileNotFoundException e) {
			fail();
			e.printStackTrace();
		}
		

	}

}
