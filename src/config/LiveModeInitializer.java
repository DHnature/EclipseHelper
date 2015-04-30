package config;

import context.recording.ADisplayBoundsFileWriter;
import context.recording.ADisplayBoundsPiper;
import context.recording.RecorderFactory;
import analyzer.ui.video.LocalScreenPlayerFactory;
import dayton.ServerStatusUpdaterFactory;
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
		RecorderFactory.getSingleton().connectToDisplay();
		LocalScreenPlayerFactory.getSingleton(); // does not subsume RecorderFactory
		ServerStatusUpdaterFactory.getOrCreateSingleton();
//		SarosAccessorFactory.createSingleton();
//		(new ADisplayBoundsFileWriter()).start();;
	}
}