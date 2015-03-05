package config;

import difficultyPrediction.AMultiLevelAggregator;
import difficultyPrediction.APredictionParameters;
import difficultyPrediction.DifficultyPredictionSettings;
import edu.cmu.scs.fluorite.model.EventRecorder;
import analyzer.ui.balloons.ABalloonCreator;
import analyzer.ui.graphics.LineGraphComposer;
import bus.uigen.OEFrame;
import bus.uigen.ObjectEditor;

public class PredictorConfigurer {
	// add listeners here also
	public static void configure() {
//		AMultiLevelAggregator.getInstance();
		
		// below for demo UI, comment out when creating plug in
//		EventRecorder.setAsyncFireEvent(false);
		// change this for non replay or live mode
		if (DifficultyPredictionSettings.isReplayMode()) {
			ReplayModePredictionConfigurer.configure();
		}
		else 
			LiveModePredictionConfigurer.configure();
//		if (!DifficultyPredictionSettings.isReplayMode()) {
////			visualizePrediction(); // for running
//			
//		}

 	}
	// can be called by analyzer
	public static void visualizePrediction() {
		LineGraphComposer.composeUI();
 		APredictionParameters.createUI();
 		AMultiLevelAggregator.createUI();
 		ABalloonCreator.createUI();
	}

}
