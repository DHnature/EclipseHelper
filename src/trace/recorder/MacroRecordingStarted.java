package trace.recorder;

import util.trace.Traceable;
import util.trace.TraceableInfo;


public class MacroRecordingStarted extends TraceableInfo {

	public MacroRecordingStarted(String aMessage, Object aFinder) {
		super(aMessage, aFinder);
	}

	public static MacroRecordingStarted newCase(Object aFinder) {
		String aMessage = "";
		MacroRecordingStarted retVal = new MacroRecordingStarted("", aFinder);
		retVal.announce();
		return retVal;
	}

	
}
