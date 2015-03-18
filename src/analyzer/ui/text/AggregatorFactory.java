package analyzer.ui.text;

import context.recording.ADisplayBoundsFileWriter;
import context.recording.DisplayBoundsOutputter;
import difficultyPrediction.MultiLevelAggregator;

public class AggregatorFactory {
	static MultiLevelAggregator singleton;
	public static void createSingleton() {
//		singleton = new AMultiLevelAggregator();
		singleton = new ARewindableMultiLevelAggregator();

	}
	public static MultiLevelAggregator getSingleton() {
		if (singleton == null)
			createSingleton();
		return singleton;
	}
}
