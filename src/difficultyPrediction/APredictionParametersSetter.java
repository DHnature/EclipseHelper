package difficultyPrediction;

public class APredictionParametersSetter implements PredictionParametersSetter {
	public static int SEGMENT_LENGTH = 25;
	public static int START_UP_LAG = 50;
	public static int STATUSES_AGGREGATED = 5;
	public void setPredictionParameters() {
		PredictionParameters predictionParameters = APredictionParameters.getInstance();
		predictionParameters.setSegmentLength(SEGMENT_LENGTH);
		predictionParameters.setStatusAggregated(STATUSES_AGGREGATED);
		
	}

}
