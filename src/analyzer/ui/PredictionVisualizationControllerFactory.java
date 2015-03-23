package analyzer.ui;


public class PredictionVisualizationControllerFactory {
	static PredictionVisualizationController singleton;
	public static void createSingleton() {
		singleton = new APredictionVisualizationController();
	}	
	public static PredictionVisualizationController getSingleton() {
		if (singleton == null)
			createSingleton();
		return singleton;
	}
}
