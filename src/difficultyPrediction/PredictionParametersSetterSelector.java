package difficultyPrediction;

public class PredictionParametersSetterSelector {
	static PredictionParametersSetter singleton = new APredictionParametersSetter();
	public static PredictionParametersSetter getSingleton() {
		return singleton;
	}

	public static void setSingleton(PredictionParametersSetter newVal) {
		singleton = newVal;
	}
}
