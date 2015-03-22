package analyzer.ui;

import analyzer.ui.graphics.PlayAndRewindCounter;
import analyzer.ui.graphics.RatioFileReader;
import analyzer.ui.text.ARewindableMultiLevelAggregator;
import difficultyPrediction.MultiLevelAggregator;

public class PlayerFactory {
	static GeneralizedPlayAndRewindCounter singleton;
	public static void createSingleton() {
//		singleton = new AMultiLevelAggregator();
		singleton = new AGeneralizedPlayAndRewindCounter();

	}
	public static void createSingleton(RatioFileReader reader) {
		singleton = new AGeneralizedPlayAndRewindCounter(reader);

	}
	public static GeneralizedPlayAndRewindCounter getSingleton() {
		if (singleton == null)
			createSingleton();
		return singleton;
	}

}
