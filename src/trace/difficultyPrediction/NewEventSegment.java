package trace.difficultyPrediction;

import util.trace.Traceable;
import util.trace.TraceableInfo;
import util.trace.Tracer;


public class NewEventSegment extends TraceableInfo {

	public NewEventSegment(String aMessage, Object aFinder) {
		super(aMessage, aFinder);
	}
	
	public static NewEventSegment newCase(String aMessage, Object aFinder) {
		if (shouldInstantiate(NewEventSegment.class)) {
		NewEventSegment retVal = new NewEventSegment("", aFinder);
		retVal.announce();
		return retVal;
		}
		Tracer.info(aFinder, aMessage);
		return null;
	}

	public static NewEventSegment newCase(Object aFinder) {
		String aMessage = "";
		return newCase(aMessage, aFinder);
//		MacroRecordingStarted retVal = new MacroRecordingStarted("", aFinder);
//		retVal.announce();
//		return retVal;
	}

	
}
