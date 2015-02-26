package context.recording;

public class RecorderFactory {
	static DisplayBoundsOutputter boundsOutputter;
	public static void createSingleton() {
		boundsOutputter = new ADisplayBoundsOuputter();
		boundsOutputter.connectToDisplayAndRecorder();
	}
	public static DisplayBoundsOutputter getSingleton() {
		return boundsOutputter;
	}

}
