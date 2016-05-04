/*
 * 
 */
package naivebayes;

import java.util.ArrayList;


import document.TestDocument;
import document.TestDocumentFiler;
import model.ClassificationModel;


// TODO: Auto-generated Javadoc
/**
 * The Class Classifier.
 */
public class Classifier {
	
	/** The test documents. */
	private TestDocumentFiler testDocuments;
	
	/** The model. */
	private ClassificationModel model;
	
	/** The number correct. */
	private int numberCorrect;
	
	/** The labels. */
	ArrayList<Integer> labels = new ArrayList<Integer>();
	
	/**
	 * Instantiates a new classifier.
	 */
	public Classifier(){
	};
	
	/**
	 * Sets the classification model.
	 *
	 * @param model the new classification model
	 */
	public void setClassificationModel(ClassificationModel model){
		this.model = model;
	}
	
	/**
	 * Sets the test documents.
	 *
	 * @param testDocuments the new test documents
	 */
	public void setTestDocuments(TestDocumentFiler testDocuments){
		this.testDocuments = testDocuments;
	}
	
	/**
	 * Gets the labels.
	 *
	 * @return the labels
	 */
	public ArrayList<Integer> getLabels(){
		return this.labels;
	}
	
	/**
	 * Classify.
	 */
	public void classify(){
		ArrayList<TestDocument> testDocs = testDocuments.getDocumentArrayList();
		for(TestDocument doc : testDocs){
			double n = calculateNegativeLogProbability(doc);
			double p = calculatePositiveLogProbability(doc);
			if(p>n){
				doc.setTestCategory(1);
			}else{
				doc.setTestCategory(0);
			}
			labels.add(doc.getTestCategory());
			if(doc.getActualCategory()==doc.getTestCategory()){
				numberCorrect++;
			}
		}
	}
	
	/**
	 * Calculate negative log probability.
	 *
	 * @param doc the doc
	 * @return the double
	 */
	private double calculateNegativeLogProbability(TestDocument doc){
		double logProbability = model.getNegProb();
		String[] words = doc.getWords();
		String[] nGrams = doc.getnGrams();
		for(String s : words){
			logProbability += model.getNegativeWordProbability(s);
		}
		for(String s: nGrams){
				logProbability += model.getNegativeWordProbability(s);
		}
		return logProbability;
	}
	
	/**
	 * Calculate positive log probability.
	 *
	 * @param doc the doc
	 * @return the double
	 */
	private double calculatePositiveLogProbability(TestDocument doc){
		double logProbability = model.getPosProb();
		String[] words = doc.getWords();
		String[] nGrams = doc.getnGrams();
		for(String s : words){
			logProbability += model.getPositiveWordProbability(s);
		}
		for(String s: nGrams){
				logProbability += model.getPositiveWordProbability(s);
		}
		return logProbability;
	}
	
	/**
	 * Classification accuracy.
	 *
	 * @return the double
	 */
	public double classificationAccuracy(){
		return ((double)numberCorrect)/(double)testDocuments.getNumberOfDocuments();
	}
	
	

}
