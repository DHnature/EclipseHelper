package config;

import difficultyPrediction.DifficultyPredictionSettings;
import difficultyPrediction.featureExtraction.ARatioFeaturesFactory;
import difficultyPrediction.featureExtraction.RatioFeaturesFactorySelector;
import difficultyPrediction.metrics.ARatioCalculatorFactory;
import difficultyPrediction.metrics.ATestRatioCalculatorFactory;
import difficultyPrediction.metrics.RatioCalculatorSelector;

public class InitializationSelector {
	public static void configure() {
		if (DifficultyPredictionSettings.isReplayMode()) {
			ReplayModeInitializationSelector.configure();
		}
		else 
			LiveModeInitializationSelector.configure();

	}

}
