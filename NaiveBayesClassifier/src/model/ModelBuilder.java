/*
 * 
 */
package model;

import java.util.HashMap;
import java.util.Map;

import document.TrainingDocumentFiler;
import util.hashUtilities;

// TODO: Auto-generated Javadoc
/**
 * The Class ModelBuilder.
 */
public class ModelBuilder {
	
	/** The selected features. */
	private String[] selectedFeatures;
	
	/** The positive feature probabilities. */
	private Map<String,Double> positiveFeatureProbabilities = new HashMap<String, Double>();
	
	/** The negative feature probabilities. */
	private Map<String,Double> negativeFeatureProbabilities = new HashMap<String, Double>();
	
	/** The categories. */
	private TrainingDocumentFiler[] categories;
	
	/** The model. */
	private ClassificationModel model = new ClassificationModel();
	
	/** The hasher. */
	private hashUtilities hasher = new hashUtilities();
	
	/**
	 * Instantiates a new model builder.
	 */
	public ModelBuilder(){
	}
	
	/**
	 * Builds the feature probabilities.
	 */
	public void buildFeatureProbabilities(){
		
		Map<String,Integer> positiveFrequency = new HashMap<String,Integer>();
		Map<String,Integer> negativeFrequency = new HashMap<String,Integer>();
		double positiveTotalCount = 0;
		double negativeTotalCount = 0;
		negativeFrequency.putAll(categories[0].getWordFrequencyMap());
		positiveFrequency.putAll(categories[1].getWordFrequencyMap());
		for(Integer i : positiveFrequency.values()){
			positiveTotalCount += (double)i;
		}
		for(Integer i : negativeFrequency.values()){
			negativeTotalCount += (double)i;
		}
		
		for(String string : selectedFeatures){
			double negFreq = hasher.getFrequency(negativeFrequency, string);
			//Perform laplacian smoothing.
			double negProb = ((negFreq + 1) / (2*negativeTotalCount));
			negProb = Math.log(negProb);
			negativeFeatureProbabilities.put(string, negProb);
			
			double posFreq = hasher.getFrequency(positiveFrequency, string);
			//Perform laplacian smoothing.
			double posProb = ((posFreq + 1) / (2*positiveTotalCount));
			posProb = Math.log(posProb);
			positiveFeatureProbabilities.put(string, posProb);
		}
		
		model.setNegativeProabilities(negativeFeatureProbabilities);
		model.setPostiveProbabilities(positiveFeatureProbabilities);
	}
	
	/**
	 * Builds the feature probabilities with n grams.
	 */
	public void buildFeatureProbabilitiesWithNGrams(){
		
		Map<String,Integer> positiveFrequency = new HashMap<String,Integer>();
		Map<String,Integer> negativeFrequency = new HashMap<String,Integer>();
		double positiveTotalCount = 0;
		double negativeTotalCount = 0;
		negativeFrequency.putAll(categories[0].getWordFrequencyMap());
		negativeFrequency.putAll(categories[0].getnGramFrequencyMap());
		positiveFrequency.putAll(categories[1].getWordFrequencyMap());
		positiveFrequency.putAll(categories[1].getnGramFrequencyMap());
		for(Integer i : positiveFrequency.values()){
			positiveTotalCount += (double)i;
		}
		for(Integer i : negativeFrequency.values()){
			negativeTotalCount += (double)i;
		}
		
		for(String string : selectedFeatures){
			double negFreq = hasher.getFrequency(negativeFrequency, string);
			//Perform laplacian smoothing.
			double negProb = ((negFreq + 1) / (2*negativeTotalCount));
			negProb = Math.log(negProb);
			negativeFeatureProbabilities.put(string, negProb);
			
			double posFreq = hasher.getFrequency(positiveFrequency, string);
			//Perform laplacian smoothing.
			double posProb = ((posFreq + 1) / (2*positiveTotalCount));
			posProb = Math.log(posProb);
			positiveFeatureProbabilities.put(string, posProb);
		}
		
		model.setNegativeProabilities(negativeFeatureProbabilities);
		model.setPostiveProbabilities(positiveFeatureProbabilities);
	}
	
	/**
	 * Builds the classification probabilities.
	 */
	public void buildClassificationProbabilities(){
		double numNegDocs = categories[0].getNumberOfDocuments();
		double numPosDocs = categories[1].getNumberOfDocuments();
		double totalDocs = numNegDocs + numPosDocs;
		double negProb = Math.log(numNegDocs/totalDocs);
		double posProb = Math.log(numPosDocs/totalDocs);
		model.setNegProb(negProb);
		model.setPosProb(posProb);
	}
	
	/**
	 * Sets the document filer.
	 *
	 * @param categories the new document filer
	 */
	public void setDocumentFiler(TrainingDocumentFiler[] categories){
		this.categories = categories;
	}
	
	/**
	 * Sets the features.
	 *
	 * @param features the new features
	 */
	public void setFeatures(String[] features){
		this.selectedFeatures = features;
	}
	
	/**
	 * Gets the model.
	 *
	 * @return the model
	 */
	public ClassificationModel getModel(){
		return model;
	}
	
	
}
