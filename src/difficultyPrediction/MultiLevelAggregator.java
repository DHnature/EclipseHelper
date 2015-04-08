package difficultyPrediction;

import util.models.PropertyListenerRegistrar;
import analyzer.Resettable;
import difficultyPrediction.featureExtraction.RatioFeaturesListener;
import difficultyPrediction.featureExtraction.WebLinkListener;
import difficultyPrediction.statusManager.StatusListener;

public interface MultiLevelAggregator extends
	RatioFeaturesListener, WebLinkListener, StatusListener, 
	DifficultyPredictionEventListener, PropertyListenerRegistrar, Resettable{

}
