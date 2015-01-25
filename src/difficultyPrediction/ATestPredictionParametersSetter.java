package difficultyPrediction;

public class ATestPredictionParametersSetter implements PredictionParametersSetter {
//	public static int SEGMENT_LENGTH = 25;
	public static int SEGMENT_LENGTH = 5;
//	public static int START_UP_LAG = 50;
	public static int START_UP_LAG = 5;
	public static int STATUSES_AGGREGATED = 3;
	public void setPredictionParameters() {
		PredictionParameters predictionParameters = APredictionParameters.getInstance();
		predictionParameters.setStartupLag(START_UP_LAG);
		predictionParameters.setSegmentLength(SEGMENT_LENGTH);
		predictionParameters.setStatusAggregated(STATUSES_AGGREGATED);
		
	}

}
