/*
 * 
 */
package feature;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import document.TrainingDocumentFiler;


// TODO: Auto-generated Javadoc
/**
 * The Class MutualInformationSelection.
 */
public class MutualInformationSelector {

	/** The Training DocumentFiler via external source to class. */
	private TrainingDocumentFiler[] categories;


	/** The features. */
	private String[] features;

	/** The frequent features via external source to class. */
	private ArrayList<FeatureStatistics> frequentFeatures = new ArrayList<FeatureStatistics>();

	/** The n. */
	private Double N00, N01, N10, N11, NX0, N0X, NX1, N1X, N;

	private ArrayList<Feature> featureList = new ArrayList<Feature>();


	
	/**
	 * Sets the features.
	 */
	public void setFeatures(double mutualInformationThreshold){
		Map<String,Double> positiveMutualInformationMap = getThisMutualInfomationMap(categories[1],categories[0]);
		Map<String,Double> positiveFilteredFeatures = getFeatureListUsingThreshold(positiveMutualInformationMap, mutualInformationThreshold); //.0000001995 gives best
		Map<String,Double> negativeMutualInformationMap = getThisMutualInfomationMap(categories[0],categories[1]);
		Map<String,Double> negativeFilteredFeatures = getFeatureListUsingThreshold(negativeMutualInformationMap, mutualInformationThreshold); //.0000001995 gives best
		Map<String,Double> featureMap = new HashMap<String,Double>();
		
		featureMap.putAll(positiveFilteredFeatures);
		featureMap.putAll(negativeFilteredFeatures);
		features = new String[featureMap.size()];
		features = featureMap.keySet().toArray(features);
		this.featureList = sortFeatureObjectList(featureMap);	
		
	}

	/**
	 * Gets the features.
	 *
	 * @return the features
	 */
	public String[] getFeatures() {
		if (features == null) {
			System.out.println("Please run setFeatures()");
			return null;
		} else {
			return features;
		}
	}

	/**
	 * Gets the sorted arrayList of positive Feature objects according to mutual
	 * information.
	 * 
	 * @return the ordered feature object list
	 */
	public ArrayList<Feature> getOrderedFeatureList() {
		Collections.sort(featureList);
		return featureList;
	}


	/**
	 * Sets the document filer.
	 *
	 * @param filer
	 *            the new document filer
	 */
	public void setDocumentFiler(TrainingDocumentFiler[] filer) {
		this.categories = filer;
	}

	/**
	 * Sets the frequent features.
	 *
	 * @param frequentFeatures
	 *            the new frequent features
	 */
	public void setFrequentFeatures(ArrayList<FeatureStatistics> frequentFeatures) {
		this.frequentFeatures = frequentFeatures;
	}

	/**
	 * Gets the this mutual infomation map.
	 *
	 * @param thisCategory
	 *            the this category
	 * @param thatCategory
	 *            the that category
	 * @return the this mutual infomation map
	 */
	private Map<String, Double> getThisMutualInfomationMap(
			TrainingDocumentFiler thisCategory,
			TrainingDocumentFiler thatCategory) {
		Map<String, Double> mutualInformationMap = new HashMap<String, Double>();
		int thisTotalDocumentCount = thisCategory.getNumberOfDocuments();
		int thatTotalDocumentCount = thatCategory.getNumberOfDocuments();
		for (FeatureStatistics f : frequentFeatures) {
			String s = f.getWord();
			int thisCategoryCount = f.getDocumentCount(thisCategory.getCategory());
			int thatCategoryCount = f.getDocumentCount(thatCategory.getCategory());
			
			setValuesForWord(thisTotalDocumentCount, thatTotalDocumentCount,
					thisCategoryCount, thatCategoryCount);
			double MI = calculateMutualInformation();
			mutualInformationMap.put(s, MI);
		}
		return mutualInformationMap;
	}

	/**
	 * Calculate mutual information.
	 *
	 * @return the double
	 */
	private double calculateMutualInformation() {
		double a = (N11 / N) * (Math.log((N * N11) / (N1X * NX1)));
		double b = (N01 / N) * (Math.log((N * N01) / (N0X * NX1)));
		double c = (N10 / N) * (Math.log((N * N10) / (NX0 * N1X)));
		double d = (N00 / N) * (Math.log((N * N00) / (N0X * NX0)));
		if (Double.isNaN(a)) {
			a = 0.0;
		}
		if (Double.isNaN(b)) {
			b = 0.0;
		}
		if (Double.isNaN(c)) {
			c = 0.0;
		}
		if (Double.isNaN(d)) {
			d = 0.0;
		}
		return Math.abs(a + b + c + d);
	}


	/**
	 * Sets the values for word.
	 *
	 * @param word
	 *            the word
	 * @param thisCorpusCount
	 *            the this corpus count
	 * @param thatCorpusCount
	 *            the that corpus count
	 * @param thisCategoryMap
	 *            the this category map
	 * @param thatCategoryMap
	 *            the that category map
	 */
	private void setValuesForWord(int thisTotalDocumentCount,
			int thatTotalDocumentCount, int thisCategoryCount, int thatCategoryCount) {
		setN11(thisCategoryCount);
		setN10(thatCategoryCount);
		setN01(thisCategoryCount, thisTotalDocumentCount);
		setN00(thatCategoryCount, thatTotalDocumentCount);
		setCompositeValues();
		setN();
	}


	/**
	 * Sets the n11.
	 *
	 * @param word
	 *            the word
	 * @param map
	 *            the map
	 */
	private void setN11(int categoryCount) {
		this.N11 = (double)categoryCount;
		
	}

	/**
	 * Sets the n10.
	 *
	 * @param word
	 *            the word
	 * @param map
	 *            the map
	 */
	private void setN10(int categoryCount){
		this.N10 = (double)categoryCount;
	}

	/**
	 * Sets the n01.
	 *
	 * @param word
	 *            the word
	 * @param map
	 *            the map
	 * @param wordCount
	 *            the word count
	 */
	private void setN01(int categoryCount, int totalDocumentCount) {
		N01 = (double) (totalDocumentCount - categoryCount);
	}

	/**
	 * Sets the n00.
	 *
	 * @param word
	 *            the word
	 * @param map
	 *            the map
	 * @param wordCount
	 *            the word count
	 */
	private void setN00(int categoryCount, int totalDocumentCount) {
		N00 = (double) (totalDocumentCount - categoryCount);
	}

	/**
	 * Sets the composite values.
	 */
	private void setCompositeValues() {
		NX0 = N00 + N10;
		N0X = N00 + N01;
		NX1 = N01 + N11;
		N1X = N10 + N11;
	}

	/**
	 * Sets the n.
	 */
	private void setN() {
		N = N00 + N10 + N01 + N11;
	}

	/**
	 * Gets the feature list using threshold.
	 *
	 * @param map
	 *            the map
	 * @param threshold
	 *            the threshold
	 * @return the feature list using threshold
	 */
	private Map<String, Double> getFeatureListUsingThreshold(Map<String, Double> map, double threshold) {
		Map<String, Double> filteredFeatureMap = new HashMap<String, Double>();
		Set<Entry<String, Double>> entries = map.entrySet();
		Iterator<Entry<String, Double>> iterator = entries.iterator();

		while (iterator.hasNext()) {
			Entry<String, Double> entry = iterator.next();
			if (entry.getValue() > threshold) {
				filteredFeatureMap.put(entry.getKey(), entry.getValue());
			}
		}

		return filteredFeatureMap;
	}
	
	private ArrayList<Feature> sortFeatureObjectList(Map<String,Double> map){
		Set<Entry<String, Double>> entries = map.entrySet();
		Iterator<Entry<String, Double>> iterator = entries.iterator();
		ArrayList<Feature> list = new ArrayList<Feature>();
		while(iterator.hasNext()){
			Entry<String, Double> entry = iterator.next();
			Feature feature = new Feature(entry.getKey(),entry.getValue());
			list.add(feature);	
		}
		Collections.sort(list);
		return list;
	}
	
	
	public class Feature implements Comparable<Feature> {
		private String feature;
		private double mutualInformation;

		public Feature(String feature, double mutualInformation) {
			this.feature = feature;
			this.mutualInformation = mutualInformation;
		}
		
		public String getFeature(){
			return this.feature;
		}
		
		public double getMutualInformation() {
			return this.mutualInformation;
		}

		@Override
		public int compareTo(Feature anotherFeature) throws ClassCastException {
			if (!(anotherFeature instanceof Feature))
				throw new ClassCastException("A Person object expected.");
			double compareMutualInformation = (anotherFeature)
					.getMutualInformation();
			if (this.mutualInformation < compareMutualInformation)
				return 1;
			if (this.mutualInformation > compareMutualInformation)
				return -1;
			return 0;
		}
	}
}
