package difficultyPrediction.statusManager;

import difficultyPrediction.metrics.APredictionHolder;

public class StatusAggregationDiscreteChunks implements StatusManagerStrategy{
	public StatusManager manager;
	private static final String STUCK = "YES";
	private static final String PROGRESS = "NO";
	private static final int MAX_PERCENTAGE = 60;
	private static final int WHOLE_PERCENTAGE = 100; //Uhh? haha
	private APredictionHolder holdPredictions = new APredictionHolder();
	private int numberOfPredictionsForDominantStatus = 5;
	
	public StatusAggregationDiscreteChunks(StatusManager manager) {
		this.manager = manager;
	}
	//TODO debug here and figure out why the tool never predicts stuck, even though im doing actions that indicate that I am stuck
	//TODO add the interdeterminate ("TIE") and check to make sure that this working correctly
	public void aggregateStatuses(String status) {
		holdPredictions.predictions.add(status);
		if(status.toUpperCase().equals(STUCK)) {
			holdPredictions.numberOfYes++;
		}
		else if (status.toUpperCase().equals(PROGRESS))
			holdPredictions.numberOfNo++;
		else {
			//There is an error
			return;
		}
		
		if(holdPredictions.predictions.size() >= numberOfPredictionsForDominantStatus) {
			double percentage = WHOLE_PERCENTAGE * holdPredictions.numberOfYes / ((double)holdPredictions.predictions.size());
			if(percentage > MAX_PERCENTAGE) {
				manager.onStatusHandOff(STUCK);
			} else {
				manager.onStatusHandOff(PROGRESS);
			}
			holdPredictions.numberOfNo = 0;
			holdPredictions.numberOfYes = 0;
			holdPredictions.predictions.clear();
		}
	}
}
