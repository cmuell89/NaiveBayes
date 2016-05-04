/*
 * 
 */


import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

import naivebayes.Classifier;
import model.ClassificationModel;
import model.ModelBuilder;
import document.TestDocumentProcessor;
import document.TrainingDocumentProcessor;
import feature.FeatureStatistics;
import feature.FrequencyFeatureSelector;
import feature.MutualInformationSelector;
import feature.MutualInformationSelector.Feature;

// TODO: Auto-generated Javadoc
/**
 * The Class NaiveBayesClassifier.
 */
public class NaiveBayesClassifier {

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		TrainingDocumentProcessor trainingProcessor = new TrainingDocumentProcessor();
		FrequencyFeatureSelector fs = new FrequencyFeatureSelector(2000,1,1000,1); // (2000,8,1000,26) provided the best
		MutualInformationSelector ms = new MutualInformationSelector();
		ModelBuilder build = new ModelBuilder();
		TestDocumentProcessor trainingForClassificationProcessor = new TestDocumentProcessor();
		TestDocumentProcessor testingForClassificationProcessor = new TestDocumentProcessor();
		Classifier trainingClassifier = new Classifier();
		Classifier testClassifier = new Classifier();
		int labelingTime;
		int trainingTime;
		ClassificationModel model;
		//String[] frequencyFeatures;
		ArrayList<FeatureStatistics> featureStatList = new ArrayList<FeatureStatistics>();
		String[] mutualInformationFeatures;
		
		long startTraining = System.currentTimeMillis();
		try {
			trainingProcessor.importDocFile(args[0]);
			trainingProcessor.importStopWordFile("stopWords.txt");
			trainingProcessor.process();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		
		try {
			fs.setDocumentFiler(trainingProcessor.getTrainingDocumentFilerArray());
			//Uncomment the below
			//fs.selectFeaturesWordsOnly();
			//and comment the below
			fs.selectFeauturesIncludeNGrams();
			//to only process single words and no bigrams.
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		//frequencyFeatures = fs.getFeatureList();
		featureStatList = fs.getFeatureStatList();
		ms.setDocumentFiler(trainingProcessor.getTrainingDocumentFilerArray());	
		ms.setFrequentFeatures(featureStatList);
		ms.setFeatures(.0001); //.00036 provided the highest accuracy for the given training dataset.
		mutualInformationFeatures = ms.getFeatures();
		//build.setFeatures(frequencyFeatures);
		build.setFeatures(mutualInformationFeatures);
		build.setDocumentFiler(fs.getDocumentFiler());
		//Uncomment the below
		//build.buildFeatureProbabilities();
		//and comment the below
		build.buildFeatureProbabilitiesWithNGrams();
		//to only process single words and no bigrams.
		build.buildClassificationProbabilities();
		model = build.getModel();
		long endTraining  = System.currentTimeMillis();
		trainingTime = (int) ((endTraining - startTraining)/1000);
		
		long startLabel = System.currentTimeMillis();
		try {
			//or use frequency features
			//trainingForClassificationProcessor.setFeatureMap(frequencyFeatures);
			trainingForClassificationProcessor.setFeatureMap(mutualInformationFeatures);
			trainingForClassificationProcessor.importDocFile(args[0]);
			trainingForClassificationProcessor.importStopWordFile("stopWords.txt");
			trainingForClassificationProcessor.process();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			//or use frequency features
			//testingForClassificationProcessor.setFeatureMap(frequencyFeatures);
			testingForClassificationProcessor.setFeatureMap(mutualInformationFeatures);
			testingForClassificationProcessor.importDocFile(args[1]);
			testingForClassificationProcessor.importStopWordFile("stopWords.txt");
			testingForClassificationProcessor.process();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		trainingClassifier.setTestDocuments(trainingForClassificationProcessor.getTestDocuments());
		trainingClassifier.setClassificationModel(model);
		trainingClassifier.classify();
		
		testClassifier.setTestDocuments(testingForClassificationProcessor.getTestDocuments());
		testClassifier.setClassificationModel(model);
		testClassifier.classify();
		ArrayList<Integer> testLabels = testClassifier.getLabels();
		long endLabel = System.currentTimeMillis();
		labelingTime = (int) ((endLabel - startLabel)/1000);
		for(Integer label : testLabels){
			System.out.println(label);
		}
		DecimalFormat df = new DecimalFormat("#.000");
		System.out.println(trainingTime + " seconds (training)");
		System.out.println(labelingTime + " seconds (labeling)");
		System.out.println(df.format(trainingClassifier.classificationAccuracy()) + " (training)");
		System.out.println(df.format(testClassifier.classificationAccuracy()) + " (testing)");
		
		/* UNCOMMENT TO PRINT OUT TOP FEATURES WITH NORMALIZED BASED ON MUTUAL INFORMATION. MAKE SURE TO UNCOMMENT
		THE IMPORT AS WELL.*/
		ArrayList<Feature> list = ms.getOrderedFeatureList();
		DecimalFormat df2 = new DecimalFormat("#.00000000000");
		System.out.println("Top ten features: ");
		for(int i = 0;i<100;i++){
			Feature feature = list.get(i);
			System.out.println("Feature: " + feature.getFeature() + "\n" + "MI: " + df2.format(feature.getMutualInformation()));
		}
		
		
	}

}
