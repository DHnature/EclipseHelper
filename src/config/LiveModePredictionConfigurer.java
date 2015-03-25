package config;

import analyzer.extension.LiveAnalyzerProcessorFactory;
import analyzer.ui.APredictionController;
import analyzer.ui.balloons.ABalloonCreator;
import analyzer.ui.graphics.LineGraphFactory;
import analyzer.ui.text.AggregatorFactory;
import difficultyPrediction.APredictionParameters;

public class LiveModePredictionConfigurer {
	public static void configure() {
		visualizePrediction();
	}
	// can be called by analyzer
	public static void visualizePrediction() {
		LiveAnalyzerProcessorFactory.getSingleton();
		LineGraphFactory.createSingleton();
		APredictionParameters.getInstance();
		AggregatorFactory.createSingleton();
//		LocalScreenRecorderAndPlayerFactory.createSingleton();
		ABalloonCreator.getInstance();
//		APredictionController.createUI();
		
//		LineGraphComposer.composeUI();
// 		APredictionParameters.createUI();
// 		AMultiLevelAggregator.createUI();
// 		ALocalScreenRecorderAndPlayer.createUI();
// 		ABalloonCreator.createUI();
	}


}
