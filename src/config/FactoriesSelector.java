package config;

import difficultyPrediction.featureExtraction.ARatioFeaturesFactory;
import difficultyPrediction.featureExtraction.RatioFeaturesFactorySelector;
import difficultyPrediction.metrics.ARatioCalculatorFactory;
import difficultyPrediction.metrics.ATestRatioCalculatorFactory;
import difficultyPrediction.metrics.RatioCalculatorSelector;

public class FactoriesSelector {
	public static void configureFactories() {
		RatioFeaturesFactorySelector.setFactory(new ARatioFeaturesFactory());
		RatioCalculatorSelector.setFactory(new ATestRatioCalculatorFactory());
//		RatioCalculatorSelector.setFactory(new ARatioCalculatorFactory());
	}

}
