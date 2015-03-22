package analyzer.extension;

public class AnalyzerProcessorFactory {
	static AnalyzerProcessor singleton;
	public static void createSingleton() {
//		singleton = new AMultiLevelAggregator();
		singleton = new AnAnalyzerProcessor();

	}
	
	public static AnalyzerProcessor getSingleton() {
		if (singleton == null)
			createSingleton();
		return singleton;
	}

}
