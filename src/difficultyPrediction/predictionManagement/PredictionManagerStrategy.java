package difficultyPrediction.predictionManagement;

public interface PredictionManagerStrategy {
	public void predictSituatation(double editRatio, double debugRatio, double navigationRatio, double focusRatio, double removeRatio);
}
