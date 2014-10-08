package difficultyPrediction.featureExtraction;

import java.util.List;

import difficultyPrediction.metrics.APercentageCalculator;

import edu.cmu.scs.fluorite.commands.ICommand;
public class ExtractRatiosBasedOnNumberOfEvents implements
		FeatureExtractionStrategy {

	public ExtractRatiosBasedOnNumberOfEvents() {
		
	}
	
	private APercentageCalculator metrics = new APercentageCalculator();
	
	 private static final int NAVIGATION_PERCENTAGE = 0;
     private static final int DEBUG_PERCENTAGE = 1;
     private static final int FOCUS_PERCENTAGE = 2;
     private static final int EDIT_PERCENTAGE = 3;
     private static final int REMOVE_PERCENTAGE = 4;
	
	public void performFeatureExtraction(List<ICommand> actions, ARatioBasedFeatureExtractor featureExtractor) {
			List<Double> percentages = null;
			percentages = metrics.computeMetrics(actions);
			featureExtractor.onFeatureHandOff(percentages.get(EDIT_PERCENTAGE), percentages.get(DEBUG_PERCENTAGE), 
                    percentages.get(NAVIGATION_PERCENTAGE), percentages.get(FOCUS_PERCENTAGE), percentages.get(REMOVE_PERCENTAGE));		
	}

}
