package config;

import difficultyPrediction.ATestPredictionParametersSetter;
import difficultyPrediction.PredictionParametersSetterSelector;
import difficultyPrediction.featureExtraction.ARatioFeaturesFactory;
import difficultyPrediction.featureExtraction.RatioFeaturesFactorySelector;
import difficultyPrediction.metrics.ARatioCalculatorFactory;
import difficultyPrediction.metrics.ATestRatioCalculatorFactory;
import difficultyPrediction.metrics.RatioCalculatorSelector;

public class ReplayModeInitializationSelector {
	public static void configure() {
		RatioFeaturesFactorySelector.setFactory(new ARatioFeaturesFactory());
		RatioCalculatorSelector.setFactory(new ATestRatioCalculatorFactory());
		PredictionParametersSetterSelector.setSingleton(new ATestPredictionParametersSetter());
//		RatioCalculatorSelector.setFactory(new ARatioCalculatorFactory());
	}

}
