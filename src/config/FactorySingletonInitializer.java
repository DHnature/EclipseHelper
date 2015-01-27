package config;

import difficultyPrediction.DifficultyPredictionSettings;
import difficultyPrediction.PredictionParametersSetterSelector;
import difficultyPrediction.featureExtraction.ARatioFeaturesFactory;
import difficultyPrediction.featureExtraction.RatioFeaturesFactorySelector;
import difficultyPrediction.metrics.ARatioCalculatorFactory;
import difficultyPrediction.metrics.ATestRatioCalculatorFactory;
import difficultyPrediction.metrics.RatioCalculatorSelector;

public class FactorySingletonInitializer {
	public static void configure() {
		if (DifficultyPredictionSettings.isReplayMode()) {
			ReplayModeInitializer.configure();
		}
		else 
			LiveModeInitializer.configure();

		
	PredictionParametersSetterSelector.getSingleton().setPredictionParameters();
	}

}
