package context.recording;

import analyzer.ui.video.ALocalScreenRecorderAndPlayer;

public class RecorderFactory {
	static DisplayBoundsOutputter boundsOutputter;
	public static void createSingleton() {
//		boundsOutputter = new ALocalScreenRecorderAndPlayer();
		boundsOutputter = new ADisplayBoundsFileWriter();
		// do not connect by default
//		boundsOutputter.connectToDisplayAndRecorder();
	}
	public static DisplayBoundsOutputter getSingleton() {
		if (boundsOutputter == null)
			createSingleton();
		return boundsOutputter;
	}

}