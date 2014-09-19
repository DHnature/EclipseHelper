package difficultyPrediction;

import difficultyPrediction.eventAggregation.EventAggregator;
import difficultyPrediction.eventAggregation.EventAggregatorDetails;
import difficultyPrediction.featureExtraction.FeatureExtractor;
import difficultyPrediction.featureExtraction.FeatureExtractorDetails;
import difficultyPrediction.predictionManagement.PredictionManager;
import difficultyPrediction.predictionManagement.PredictionManagerDetails;
import difficultyPrediction.statusManager.StatusManager;
import difficultyPrediction.statusManager.StatusManagerDetails;


public interface Mediator {
	public void eventAggregator_HandOffEvents(EventAggregator aggregator, EventAggregatorDetails details);
	public void featureExtractor_HandOffFeatures(FeatureExtractor extractor, FeatureExtractorDetails details);
	public void predictionManager_HandOffPrediction(PredictionManager manager, PredictionManagerDetails details);
	public void statusManager_HandOffStatus(StatusManager manager, StatusManagerDetails details);
}
