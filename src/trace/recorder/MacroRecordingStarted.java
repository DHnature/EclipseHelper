package trace.recorder;

import util.trace.Traceable;
import util.trace.TraceableInfo;
import util.trace.Tracer;


public class MacroRecordingStarted extends TraceableInfo {

	public MacroRecordingStarted(String aMessage, Object aFinder) {
		super(aMessage, aFinder);
	}
	
	public static MacroRecordingStarted newCase(String aMessage, Object aFinder) {
		if (shouldInstantiate(MacroRecordingStarted.class)) {
		MacroRecordingStarted retVal = new MacroRecordingStarted("", aFinder);
		retVal.announce();
		return retVal;
		}
		Tracer.info(aFinder, aMessage);

		return null;
	}

	public static MacroRecordingStarted newCase(Object aFinder) {
		String aMessage = "";
		return newCase(aMessage, aFinder);
//		MacroRecordingStarted retVal = new MacroRecordingStarted("", aFinder);
//		retVal.announce();
//		return retVal;
	}

	
}
