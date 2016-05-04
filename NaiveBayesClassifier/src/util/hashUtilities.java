/*
 * 
 */
package util;

import java.util.ArrayList;
import java.util.Map;

import document.TrainingDocument;
import document.TrainingDocumentFiler;

// TODO: Auto-generated Javadoc
/**
 * The Class hashUtilities.
 */
public class hashUtilities {
	
	/**
	 * Frequency hash categories.
	 *
	 * @param categories the categories
	 * @return the training document filer[]
	 */
	public TrainingDocumentFiler[] frequencyHashCategories(TrainingDocumentFiler[] categories) {
		for (TrainingDocumentFiler c : categories) {
			ArrayList<TrainingDocument> docs = c.getDocumentArrayList();
			for (TrainingDocument d : docs) {
				c.setFrequencyHash(frequencyHasher(c.getWordFrequencyMap(), d.getWords()));
				c.setnGramFrequencyMap(frequencyHasher(c.getnGramFrequencyMap(), d.getnGrams()));
				}
			}
		return categories;
	}
	
	/**
	 * Map smash.
	 *
	 * @param currMap the curr map
	 * @param newMap the new map
	 * @return the map
	 */
	//Combiens the two frequency hashMaps together.
	public Map<String, Integer> mapSmash(Map<String, Integer> currMap, Map<String, Integer> newMap){
				currMap.putAll(newMap);
				return currMap;
	}
	
	/**
	 * Frequency hasher.
	 *
	 * @param map the map
	 * @param a the a
	 * @return the map
	 */
	//Adds to the frequency count of the provided word.
	public Map<String, Integer> frequencyHasher(Map<String, Integer> map, String[] a) {
		Map<String, Integer> m = map;
		for (String w : a) {
			Integer n = m.get(w);
			// check if n is null, if yes, n is 1 otherwise ++n
			n = (n == null) ? 1 : ++n;
			// rehash into map
			m.put(w, n);
		}
		return map;
	}

	/**
	 * Gets the frequency.
	 *
	 * @param map the map
	 * @param word the word
	 * @return the frequency
	 */
	// From given HashMap, gives the frequency of the word provided.
		public int getFrequency(Map<String, Integer> map, String word) {
			Map<String, Integer> m = map;
			Integer n = m.get(word);
			if (n != null) {
				return n.intValue();
			} else {
				return 0;
			}
		}

}
