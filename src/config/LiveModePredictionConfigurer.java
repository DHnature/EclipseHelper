package config;

import analyzer.extension.AnalyzerProcessorFactory;
import analyzer.extension.LiveAnalyzerProcessorFactory;
import analyzer.ui.APredictionVisualizationController;
import analyzer.ui.PredictionVisualizationControllerFactory;
import analyzer.ui.balloons.ABalloonCreator;
import analyzer.ui.graphics.LineGraphComposer;
import analyzer.ui.graphics.LineGraphFactory;
import analyzer.ui.text.AMultiLevelAggregator;
import analyzer.ui.text.AggregatorFactory;
import analyzer.ui.video.ALocalScreenRecorderAndPlayer;
import analyzer.ui.video.LocalScreenRecorderAndPlayerFactory;
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
		APredictionVisualizationController.createUI();
		
//		LineGraphComposer.composeUI();
// 		APredictionParameters.createUI();
// 		AMultiLevelAggregator.createUI();
// 		ALocalScreenRecorderAndPlayer.createUI();
// 		ABalloonCreator.createUI();
	}


}
