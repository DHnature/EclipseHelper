package difficultyPrediction.featureExtraction;

import util.models.PropertyListenerRegistrar;

public interface RatioFetauresObservable extends PropertyListenerRegistrar {
	void addRatioFeaturesListener(RatioFeaturesListener aListener);

	void removeRatioFetauresListener(RatioFeaturesListener aListener);

}
