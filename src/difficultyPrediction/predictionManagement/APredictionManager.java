package difficultyPrediction.predictionManagement;

import difficultyPrediction.Mediator;

public class APredictionManager {
	
	Mediator mediator;
	
	public APredictionManager(Mediator mediator) {
		this.mediator = mediator;
	}
	
	public PredictionManagerStrategy predictionStrategy;
	
	public void onPredictionHandOff(String predictionValue) {
		if(mediator != null) {
			APredictionManagerDetails details = new APredictionManagerDetails(predictionValue);
			mediator.predictionManager_HandOffPrediction(this, details);
		}
	}
	
}
