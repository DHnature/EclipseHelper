package difficultyPrediction;

public class DifficultyPredictionSettings {
	static String ratiosFileName;
	static boolean replayMode;
	static int segmentLength = 50;

	public DifficultyPredictionSettings() {
	}

	public static boolean isReplayMode() {
		return replayMode;
	}

	public static void setReplayMode(boolean replayMode) {
		DifficultyPredictionSettings.replayMode = replayMode;
	}

	public static int getSegmentLength() {
		return segmentLength;
	}

	public static void setSegmentLength(int segmentLength) {
		DifficultyPredictionSettings.segmentLength = segmentLength;
	}

	public static String getRatiosFileName() {
		return ratiosFileName;
	}

	public static void setRatiosFileName(String ratiosFileName) {
		DifficultyPredictionSettings.ratiosFileName = ratiosFileName;
	}
	
	

}
