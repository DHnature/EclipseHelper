package config;

import context.recording.ADisplayBoundsFileWriter;
import context.recording.ADisplayBoundsPiper;
import context.recording.RecorderFactory;
import analyzer.ui.video.LocalScreenPlayerFactory;
import difficultyPrediction.ATestPredictionParametersSetter;
import difficultyPrediction.PredictionParametersSetterSelector;
import difficultyPrediction.featureExtraction.ARatioFeaturesFactory;
import difficultyPrediction.featureExtraction.RatioFeaturesFactorySelector;
import difficultyPrediction.metrics.ATestRatioCalculatorFactory;
import difficultyPrediction.metrics.RatioCalculatorSelector;

public class LiveModeInitializer {
	public static void configure() {
		RatioFeaturesFactorySelector.setFactory(new ARatioFeaturesFactory());
		RatioCalculatorSelector.setFactory(new ATestRatioCalculatorFactory());
//		RatioCalculatorSelector.setFactory(new ARatioCalculatorFactory());
//		PredictionParametersSetterSelector.setSingleton(new APredictionParametersSetter());
		PredictionParametersSetterSelector.setSingleton(new ATestPredictionParametersSetter());
		RecorderFactory.createSingleton();
		LocalScreenPlayerFactory.createSingleton(); // does not subsume RecorderFactory
//		SarosAccessorFactory.createSingleton();
		(new ADisplayBoundsFileWriter()).connectToDisplayAndRecorder();;
	}
}