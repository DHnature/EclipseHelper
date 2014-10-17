package difficultyPrediction;

import difficultyPrediction.eventAggregation.AnEventAggregator;
import difficultyPrediction.eventAggregation.AnEventAggregatorDetails;
import difficultyPrediction.eventAggregation.EventAggregator;
import difficultyPrediction.featureExtraction.ARatioFeatures;
import difficultyPrediction.featureExtraction.RatioBasedFeatureExtractor;
import difficultyPrediction.featureExtraction.RatioFeatures;
import difficultyPrediction.predictionManagement.APredictionManagerDetails;
import difficultyPrediction.predictionManagement.PredictionManager;
import difficultyPrediction.statusManager.StatusManager;
import difficultyPrediction.statusManager.StatusManagerDetails;
import edu.cmu.scs.fluorite.commands.ICommand;


public interface Mediator {
	public void eventAggregator_HandOffEvents(AnEventAggregator aggregator, AnEventAggregatorDetails details);
//	public void featureExtractor_HandOffFeatures(RatioBasedFeatureExtractor extractor, AFeatureExtractorDetails details);
	public void predictionManager_HandOffPrediction(PredictionManager manager, APredictionManagerDetails details);
	public void statusManager_HandOffStatus(StatusManager manager, StatusManagerDetails details);
	void featureExtractor_HandOffFeatures(RatioBasedFeatureExtractor extractor,
			RatioFeatures details);
	void processEvent(ICommand e);
	public EventAggregator getEventAggregator();


	public void setEventAggregator(EventAggregator eventAggregator) ;


	public RatioBasedFeatureExtractor getFeatureExtractor() ;


	public void setFeatureExtractor(RatioBasedFeatureExtractor featureExtractor);


	public PredictionManager getPredictionManager() ;


	public void setPredictionManager(PredictionManager predictionManager) ;


	public StatusManager getStatusManager() ;


	public void setStatusManager(StatusManager statusManager) ;

	public StatusInformation getStatusInformation() ;


	public void setStatusInformation(StatusInformation statusInformation) ;
}
