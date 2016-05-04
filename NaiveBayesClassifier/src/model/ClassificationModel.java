/*
 * 
 */
package model;

import java.util.Map;

// TODO: Auto-generated Javadoc
/**
 * The Class ClassificationModel.
 */
public class ClassificationModel {
	
	/** The pos prob. */
	private double posProb;
	
	/** The neg prob. */
	private double negProb;
	
	/** The postive probabilities. */
	private Map<String, Double> postiveProbabilities;
	
	/** The negative proabilities. */
	private Map<String, Double> negativeProabilities;

	/**
	 * Instantiates a new classification model.
	 */
	public ClassificationModel() {

	}

	/**
	 * Gets the positive word probability.
	 *
	 * @param word the word
	 * @return the positive word probability
	 */
	public double getPositiveWordProbability(String word) {
		if (postiveProbabilities.get(word) == null) {
			return 0;
		} else {
			return postiveProbabilities.get(word);
		}
	}

	/**
	 * Gets the negative word probability.
	 *
	 * @param word the word
	 * @return the negative word probability
	 */
	public double getNegativeWordProbability(String word) {
		if (negativeProabilities.get(word) == null) {
			return 0;
		} else {
			return negativeProabilities.get(word);
		}
	}

	/**
	 * Gets the pos prob.
	 *
	 * @return the pos prob
	 */
	public double getPosProb() {
		return posProb;
	}

	/**
	 * Sets the pos prob.
	 *
	 * @param posProb the new pos prob
	 */
	public void setPosProb(double posProb) {
		this.posProb = posProb;
	}

	/**
	 * Gets the neg prob.
	 *
	 * @return the neg prob
	 */
	public double getNegProb() {
		return negProb;
	}

	/**
	 * Sets the neg prob.
	 *
	 * @param negProb2 the new neg prob
	 */
	public void setNegProb(double negProb2) {
		this.negProb = negProb2;
	}

	/**
	 * Gets the postive probabilities.
	 *
	 * @return the postive probabilities
	 */
	public Map<String, Double> getPostiveProbabilities() {
		return postiveProbabilities;
	}

	/**
	 * Sets the postive probabilities.
	 *
	 * @param postiveProbabilities the postive probabilities
	 */
	public void setPostiveProbabilities(Map<String, Double> postiveProbabilities) {
		this.postiveProbabilities = postiveProbabilities;
	}

	/**
	 * Gets the negative proabilities.
	 *
	 * @return the negative proabilities
	 */
	public Map<String, Double> getNegativeProabilities() {
		return negativeProabilities;
	}

	/**
	 * Sets the negative proabilities.
	 *
	 * @param negativeProabilities the negative proabilities
	 */
	public void setNegativeProabilities(Map<String, Double> negativeProabilities) {
		this.negativeProabilities = negativeProabilities;
	}

}
