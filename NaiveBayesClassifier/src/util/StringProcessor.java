/*
 * 
 */
package util;

import java.util.ArrayList;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// TODO: Auto-generated Javadoc
/**
 * The Class StringProcessor.
 */
public class StringProcessor {
	
	/** The stop words. */
	TreeBuilder stopWords;
	
	/** The stop word file. */
	String stopWordFile;
	
	/**
	 * Instantiates a new string processor.
	 *
	 * @param stopWordFile the stop word file
	 */
	public StringProcessor() {
		
	};
	
	/**
	 * Builds the stop word list.
	 */
	public void setStopWordTree(TreeBuilder stopFileTree){
		this.stopWords = stopFileTree;
	}
	
	/**
	 * Keep only features.
	 *
	 * @param featureMap the feature map
	 * @param array the array
	 * @return the string[]
	 */
	public String[] keepOnlyFeatures(Map<String,Integer> featureMap, String[] array){
		ArrayList<String> wordList = new ArrayList<String>();
		for(String s : array){
			if(featureMap.containsKey(s)){
				wordList.add(s);
			}
			
		}
		String[] a = new String[wordList.size()];
		return wordList.toArray(a);		
	}
	
	

	/**
	 * Removes the non alpha num.
	 *
	 * @param string the string
	 * @return the string
	 */
	// remove all non-alphanumerics exlcuding numerals from string
	public String removeNonAlphaNum(String string) {
		string = string.replaceAll("[^A-Za-z]"," ");
		// specific to test data set: Remove the "br" from leftover xml <br>
		// tags.
		string = string.replaceAll("br", "");
		string = string.replaceAll("\\s\\s+", " ");
		return string;
	}

	/**
	 * Negation parser.
	 *
	 * @param string the string
	 * @return the string
	 */
	// parses out negations by using a regex to find not
	public String negationParser(String string) {
		Pattern regexPattern = Pattern.compile("(not\\s\\w+)");
		Matcher match = regexPattern.matcher(string);
		while (match.find()) {
			String currentString = match.group();
			String negationString = currentString.replace(" ", "_");
			string = string.replaceAll(currentString, negationString);
			string = string.replaceAll("\\s\\s+", " ");
		}
		//Doesn't improve anything
//		regexPattern = Pattern.compile("([a-zA-Z]+(nt)\\s\\w+)");
//		match = regexPattern.matcher(string);
//		while (match.find()) {
//			String currentString = match.group();
//			String negationString = currentString.replace(" ", "_");
//			string = string.replaceAll(currentString, negationString);
//			string = string.replaceAll("\\s\\s+", " ");
//		}
		return string;
	}

	/**
	 * Removes the stop words.
	 *
	 * @param string the string
	 * @return the string
	 * @throws Exception 
	 */
	// replaces all stop words with "" null character and reconstructs string.
	public String removeStopWords(String string) throws Exception {
		if(stopWords == null){
			throw new Exception();
		}else{
		String[] words = string.split("\\s");
		String result = "";
		for (int i = 0; i < words.length; i++) {
			words[i].trim();
			if (stopWords.find(words[i]) == 1) {
				words[i] = "";
			}
			if(words[i].length()>0){
				result += (words[i] + " ");
			}
		}
		return result.replaceAll("\\s\\s+", " ").trim();
		}
	}
	
	/**
	 * N grammer.
	 *
	 * @param rawDoc the raw doc
	 * @param n the n
	 * @return the string[]
	 */
	//creates n-grams of size n, expects preparse data. Might not work cleanly unless alpha numerics and newlines have been removed.
	public String[] nGrammer(String rawDoc, int n) {
		// Adapted from:
		// http://stackoverflow.com/questions/3656762/n-gram-generation-from-a-sentence
		ArrayList<String> nGrams = new ArrayList<String>();
		rawDoc = rawDoc.trim();
		rawDoc = rawDoc.replaceAll("( [a-z] )", " ");
		rawDoc = rawDoc.replaceAll("\\s\\s+", " ");
		String[] words = rawDoc.split(" ");
		for (int i = 0; i < words.length - n + 1; i++) {
			String newGram = strBuild(words, i, i + n).trim();
			String[] splitGram = newGram.split(" ");
//			If both words of nGram are stop words then ignore.
			if(splitGram.length!=1){
				 if(stopWords.find(splitGram[0])!=1 || stopWords.find(splitGram[1])!=1){
						nGrams.add(strBuild(words, i, i + n).trim());
					}
			}
		}
		String[] result = new String[nGrams.size()];
		nGrams.toArray(result);
		return result; 
	}

	// 
	/**
	 * Str build. Adapted from: http://stackoverflow.com/questions/3656762/n-gram-generation-from-a-sentence
	 *
	 * @param words the words
	 * @param start the start
	 * @param end the end
	 * @return the string
	 */
	private String strBuild(String[] words, int start, int end) {
		StringBuilder sb = new StringBuilder();
		for (int i = start; i < end; i++)
			sb.append((i > start ? " " : "") + words[i]);
		return sb.toString().replaceAll("\\s\\s+", " ").trim();

	}
}
