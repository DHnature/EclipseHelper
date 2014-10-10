package config;

import difficultyPrediction.featureExtraction.ARatioFeaturesFactory;
import difficultyPrediction.featureExtraction.RatioFeaturesFactorySelector;

public class FactoriesSelector {
	public static void configureFactories() {
		RatioFeaturesFactorySelector.setFactory(new ARatioFeaturesFactory());
	}

}
