package difficultyPrediction.predictionManagement;

import difficultyPrediction.Mediator;

public class PredictionManager {
	
	Mediator mediator;
	
	public PredictionManager(Mediator mediator) {
		this.mediator = mediator;
	}
	
	public PredictionManagerStrategy predictionStrategy;
	
	public void onPredictionHandOff(String predictionValue) {
		if(mediator != null) {
			PredictionManagerDetails details = new PredictionManagerDetails(predictionValue);
			mediator.predictionManager_HandOffPrediction(this, details);
		}
	}
	
}
