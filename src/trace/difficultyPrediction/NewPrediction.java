package trace.difficultyPrediction;

import util.trace.Traceable;
import util.trace.TraceableInfo;
import util.trace.Tracer;


public class NewPrediction extends TraceableInfo {

	public NewPrediction(String aMessage, Object aFinder) {
		super(aMessage, aFinder);
	}
	
	public static NewPrediction newCase(String aMessage, Object aFinder) {
		if (shouldInstantiate(NewPrediction.class)) {
		NewPrediction retVal = new NewPrediction("", aFinder);
		retVal.announce();
		return retVal;
		}
		Tracer.info(aFinder, aMessage);

		return null;
	}

	public static NewPrediction newCase(Object aFinder) {
		String aMessage = "";
		return newCase(aMessage, aFinder);
//		MacroRecordingStarted retVal = new MacroRecordingStarted("", aFinder);
//		retVal.announce();
//		return retVal;
	}

	
}
