package config;

import analyzer.extension.AnalyzerProcessorFactory;
import analyzer.extension.LiveAnalyzerProcessorFactory;
import analyzer.ui.balloons.ABalloonCreator;
import analyzer.ui.graphics.LineGraphComposer;
import analyzer.ui.text.AMultiLevelAggregator;
import difficultyPrediction.APredictionParameters;

public class LiveModePredictionConfigurer {
	public static void configure() {
		visualizePrediction();
	}
	// can be called by analyzer
	public static void visualizePrediction() {
		LiveAnalyzerProcessorFactory.getSingleton();
		LineGraphComposer.composeUI();
 		APredictionParameters.createUI();
 		AMultiLevelAggregator.createUI();
 		ABalloonCreator.createUI();
	}


}
