package config;

import difficultyPrediction.DifficultyPredictionSettings;
import difficultyPrediction.PredictionParametersSetterSelector;

public class FactorySingletonInitializer {
	public static void configure() {
		if (DifficultyPredictionSettings.isReplayMode()) {
			ReplayModeInitializer.configure();
		}
		else 
			LiveModeInitializer.configure();

		
	PredictionParametersSetterSelector.getSingleton().setPredictionParameters();
	}

}
