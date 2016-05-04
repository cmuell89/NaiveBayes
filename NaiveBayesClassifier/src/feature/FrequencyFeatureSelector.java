/*
 * 
 */
package feature;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import document.TrainingDocument;
import document.TrainingDocumentFiler;
import util.hashUtilities;

/**
 * The Class FrequencyFeatureSelection.
 */
public class FrequencyFeatureSelector extends FeatureSelector {

	/** The Constant NullPointerException. */
	private static final Exception NullPointerException = null;

	/** The categories. */
	private TrainingDocumentFiler[] categories;

	/** The feature map. */
	private Map<String, Integer> featureMap = new HashMap<String, Integer>();
	
	/** The featureList. */
	private ArrayList<FeatureStatistics> featureStatList = new ArrayList<FeatureStatistics>();

	/** The hasher. */
	private hashUtilities hasher = new hashUtilities();

	/** The word max. */
	private int wordMax;

	/** The word min. */
	private int wordMin;

	/** The n gram max. */
	private int nGramMax;

	/** The n gram min. */
	private int nGramMin;

	/**
	 * Instantiates a new frequency feature selection.
	 *
	 * @param wordMax
	 *            the word max
	 * @param wordMin
	 *            the word min
	 * @param nGramMax
	 *            the n gram max
	 * @param nGramMin
	 *            the n gram min
	 */
	public FrequencyFeatureSelector(int wordMax, int wordMin, int nGramMax,
			int nGramMin) {
		this.wordMax = wordMax;
		this.wordMin = wordMin;
		this.nGramMax = nGramMax;
		this.nGramMin = nGramMin;
	}

	/**
	 * Select features using words only. This will not use nGrams. The method
	 * filters it's selection by using hard criteria.
	 *
	 * @throws Exception
	 *             Throws a null pointer exception if categories[] has not been
	 *             set.
	 */
	public void selectFeaturesWordsOnly() throws Exception {
		if (categories == null) {
			throw NullPointerException;
		} else {
			for (TrainingDocumentFiler c : categories) {
				ArrayList<TrainingDocument> docs = c.getDocumentArrayList();
				for (TrainingDocument d : docs) {
					// frequencyHasher hashmaps word/ngrams in each word array
					// of each document to global hashmap of words in the
					// TrainingDocumentFiler
					c.setFrequencyHash(hasher.frequencyHasher(
							c.getWordFrequencyMap(), d.getWords()));
					c.setnGramFrequencyMap(hasher.frequencyHasher(
							c.getnGramFrequencyMap(), d.getnGrams()));
				}
			}
			selectWordFeatures();
		}
	}

	/**
	 * Select feautures using words and nGrams. The method filters its selection
	 * by using hard criteria of frequeny count.
	 *
	 * @throws Exception
	 *             Throws a null pointer exception if categories[] has not been
	 *             set.
	 */
	public void selectFeauturesIncludeNGrams() throws Exception {
		if (categories == null) {
			throw NullPointerException;
		} else {
			hasher.frequencyHashCategories(categories);
			selectWordFeatures();
			selectNGramFeatures();
		}

	}

	/**
	 * Select word features.
	 */
	private void selectWordFeatures() {
		Map<String, Integer> map = new HashMap<String, Integer>();
		for (TrainingDocumentFiler c : categories) {
			if (c.getWordFrequencyMap() != null) {
				map.putAll(c.getWordFrequencyMap());
			}
		}
		for (String key : map.keySet()) {
			if (hasher.getFrequency(map, key) > wordMin
					&& hasher.getFrequency(map, key) < wordMax) {
				Integer n = hasher.getFrequency(map, key);
				featureMap.put(key, n);
				int positiveDocumentCount = 0;
				int negativeDocumentCount = 0;
				for(TrainingDocument doc : categories[0].getDocumentArrayList()){
					if(doc.hasWordTest(key)){
						positiveDocumentCount++;
					}
				}
				for(TrainingDocument doc : categories[1].getDocumentArrayList()){
					if(doc.hasWordTest(key)){
						negativeDocumentCount++;
					}
				}
				FeatureStatistics feature = new FeatureStatistics(key, n, positiveDocumentCount, negativeDocumentCount);
				featureStatList.add(feature);
			}
		}
	}

	/**
	 * Select n gram features.
	 */
	private void selectNGramFeatures() {
		Map<String, Integer> map = new HashMap<String, Integer>();
		for (TrainingDocumentFiler c : categories) {
			if (c.getnGramFrequencyMap() != null) {
				map.putAll(c.getnGramFrequencyMap());
			}
		}
		for (String key : map.keySet()) {
			if (hasher.getFrequency(map, key) > nGramMin
					&& hasher.getFrequency(map, key) < nGramMax) {
				Integer n = hasher.getFrequency(map, key);
				featureMap.put(key, n);
			}
		}
	}

	/**
	 * Gets the document filer.
	 *
	 * @return the document filer
	 */
	public TrainingDocumentFiler[] getDocumentFiler() {
		return categories;
	}

	/**
	 * Sets the document filer.
	 *
	 * @param categories
	 *            the new document filer
	 */
	public void setDocumentFiler(TrainingDocumentFiler[] categories) {
		this.categories = categories;
	}

	/**
	 * Gets the frequency map.
	 *
	 * @return the frequency map
	 */
	public Map<String, Integer> getFrequencyMap() {
		return this.featureMap;
	}
	
	public ArrayList<FeatureStatistics> getFeatureStatList(){
		return featureStatList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see feature.FeatureSelector#getFeatureList()
	 */
	@Override
	public String[] getFeatureList() {
		String[] array = new String[this.featureMap.size()];
		this.featureMap.keySet().toArray(array);
		return array;
	}
}
