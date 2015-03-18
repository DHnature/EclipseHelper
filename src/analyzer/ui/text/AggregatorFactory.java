package analyzer.ui.text;

import context.recording.ADisplayBoundsFileWriter;
import context.recording.DisplayBoundsOutputter;
import difficultyPrediction.AMultiLevelAggregator;
import difficultyPrediction.MultiLevelAggregator;

public class AggregatorFactory {
	static MultiLevelAggregator singleton;
	public static void createSingleton() {
//		boundsOutputter = new ADisplayBoundsPiper();
		singleton = new AMultiLevelAggregator();
	}
	public static MultiLevelAggregator getSingleton() {
		if (singleton == null)
			createSingleton();
		return singleton;
	}
}
