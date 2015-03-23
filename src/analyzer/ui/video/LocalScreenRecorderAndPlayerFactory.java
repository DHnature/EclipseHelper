package analyzer.ui.video;

public class LocalScreenRecorderAndPlayerFactory {
	static LocalScreenRecorderAndPlayer singleton;
	public static void createSingleton() {
//		singleton = new AMultiLevelAggregator();
		singleton = new ALocalScreenRecorderAndPlayer();

	}
	
	public static LocalScreenRecorderAndPlayer getSingleton() {
		if (singleton == null)
			createSingleton();
		return singleton;
	}


}
