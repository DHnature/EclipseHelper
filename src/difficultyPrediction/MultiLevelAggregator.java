package difficultyPrediction;

import analyzer.Resettable;
import util.models.PropertyListenerRegistrar;
import difficultyPrediction.featureExtraction.RatioFeaturesListener;
import difficultyPrediction.statusManager.StatusListener;

public interface MultiLevelAggregator extends
	RatioFeaturesListener, StatusListener, 
	DifficultyPredictionEventListener, PropertyListenerRegistrar, Resettable{

}
