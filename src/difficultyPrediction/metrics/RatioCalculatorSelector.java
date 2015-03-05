package difficultyPrediction.metrics;

import difficultyPrediction.featureExtraction.ARatioFeaturesFactory;
import difficultyPrediction.featureExtraction.RatioFeatures;
import difficultyPrediction.featureExtraction.RatioFeaturesFactory;
import difficultyPrediction.featureExtraction.RatioFeaturesFactorySelector;

public class RatioCalculatorSelector {
	static RatioCalculatorFactory factory = new ARatioCalculatorFactory();
	static RatioCalculator ratioCalculator;
//	static RatioFeaturesFactory factory;

	public static RatioCalculatorFactory getFactory() {
		return factory;
	}
	public static void setFactory(RatioCalculatorFactory newVal) {
		factory = newVal;
	}
	public static  RatioCalculator getRatioFeatures() {
		if (ratioCalculator == null) {
			ratioCalculator = factory.createRatioCalculator();
		}
		return ratioCalculator;
	}

}
