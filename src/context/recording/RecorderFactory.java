package context.recording;

public class RecorderFactory {
	static DisplayBoundsOutputter boundsOutputter;
	public static void createSingleton() {
//		boundsOutputter = new ADisplayBoundsPiper();
		boundsOutputter = new ADisplayBoundsFileWriter();

		boundsOutputter.connectToDisplayAndRecorder();
	}
	public static DisplayBoundsOutputter getSingleton() {
		if (boundsOutputter == null)
			createSingleton();
		return boundsOutputter;
	}

}
