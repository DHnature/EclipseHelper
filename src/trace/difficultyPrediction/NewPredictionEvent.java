package trace.difficultyPrediction;

import util.trace.Traceable;
import util.trace.TraceableInfo;
import util.trace.Tracer;


public class NewPredictionEvent extends TraceableInfo {

	public NewPredictionEvent(String aMessage, Object aFinder) {
		super(aMessage, aFinder);
	}
	
	public static NewPredictionEvent newCase(String aMessage, Object aFinder) {
		if (shouldInstantiate(NewPredictionEvent.class)) {
		NewPredictionEvent retVal = new NewPredictionEvent("", aFinder);
		retVal.announce();
		return retVal;
		}
		Tracer.info(aFinder, aMessage);

		return null;
	}

	public static NewPredictionEvent newCase(Object aFinder) {
		String aMessage = "";
		return newCase(aMessage, aFinder);
//		MacroRecordingStarted retVal = new MacroRecordingStarted("", aFinder);
//		retVal.announce();
//		return retVal;
	}

	
}
