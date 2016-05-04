package feature;

public class FeatureStatistics {
	
	private String word;
	private int frequencyCount;
	private int positiveDocumentCount;
	private int negativeDocumentCount;

	
	public FeatureStatistics(String word, int frequencyCount, int positiveDocumentCount, int negativeDoumentCount){
		this.word = word;
		this.frequencyCount = frequencyCount;
		this.positiveDocumentCount = positiveDocumentCount;
		this.negativeDocumentCount = negativeDoumentCount;
	}


	public String getWord() {
		return word;
	}


	public int getFrequencyCount() {
		return frequencyCount;
	}


	public int getDocumentCount(int category){
		if(category == 0){
			return negativeDocumentCount;
		}
		if(category == 1){
			return positiveDocumentCount;
		}
		else{
			return -1;
		}
	}
	
	
}
