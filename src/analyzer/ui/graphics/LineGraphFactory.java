package analyzer.ui.graphics;

import analyzer.ui.PlayerFactory;

public class LineGraphFactory {
	static LineGraph singleton;
	public static void createSingleton() {
//		boundsOutputter = new ADisplayBoundsPiper();
		singleton = new ALineGraph(PlayerFactory.getSingleton(), new ARatioFileReader());

	}
	public static LineGraph getSingleton() {
		if (singleton == null)
			createSingleton();
		return singleton;
	}

}
