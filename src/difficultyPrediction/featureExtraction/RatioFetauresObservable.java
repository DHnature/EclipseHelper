package difficultyPrediction.featureExtraction;

import util.models.PropertyListenerRegisterer;

public interface RatioFetauresObservable extends PropertyListenerRegisterer {
	void addRatioFeaturesListener(RatioFeaturesListener aListener);

	void removeRatioFetauresListener(RatioFeaturesListener aListener);

}
