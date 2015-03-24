package difficultyPrediction;

import util.models.PropertyListenerRegistrar;
import analyzer.Resettable;
import difficultyPrediction.featureExtraction.RatioFeaturesListener;
import difficultyPrediction.statusManager.StatusListener;

public interface MultiLevelAggregator extends
	RatioFeaturesListener, StatusListener, 
	DifficultyPredictionEventListener, PropertyListenerRegistrar, Resettable{

}
