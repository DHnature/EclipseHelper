package difficultyPrediction.predictionManagement;

public interface PredictionManagerStrategy {
	public void predictSituation(double editRatio, double debugRatio, double navigationRatio, double focusRatio, double removeRatio);
}
