package difficultyPrediction.featureExtraction;

public interface RatioBasedFeatureExtractor {

	public abstract void onFeatureHandOff(double editRatio, double debugRatio,
			double navigationRatio, double focusRatio, double removeRatio);

	public abstract void onFeatureHandOff(double editRatio, double debugRatio,
			double navigationRatio, double focusRatio, double removeRatio,
			double exceptionsPerRun);

}