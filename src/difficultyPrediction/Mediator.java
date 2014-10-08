package difficultyPrediction;

import difficultyPrediction.eventAggregation.AnEventAggregator;
import difficultyPrediction.eventAggregation.AnEventAggregatorDetails;
import difficultyPrediction.featureExtraction.ARatioBasedFeatureExtractor;
import difficultyPrediction.featureExtraction.FeatureExtractorDetails;
import difficultyPrediction.predictionManagement.APredictionManager;
import difficultyPrediction.predictionManagement.APredictionManagerDetails;
import difficultyPrediction.statusManager.StatusManager;
import difficultyPrediction.statusManager.StatusManagerDetails;


public interface Mediator {
	public void eventAggregator_HandOffEvents(AnEventAggregator aggregator, AnEventAggregatorDetails details);
	public void featureExtractor_HandOffFeatures(ARatioBasedFeatureExtractor extractor, FeatureExtractorDetails details);
	public void predictionManager_HandOffPrediction(APredictionManager manager, APredictionManagerDetails details);
	public void statusManager_HandOffStatus(StatusManager manager, StatusManagerDetails details);
}
