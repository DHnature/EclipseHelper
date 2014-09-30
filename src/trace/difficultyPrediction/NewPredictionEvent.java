package trace.difficultyPrediction;

import util.trace.Traceable;
import util.trace.TraceableInfo;


public class NewPredictionEvent extends TraceableInfo {

	public NewPredictionEvent(String aMessage, Object aFinder) {
		super(aMessage, aFinder);
	}

	public static NewPredictionEvent newCase(Object aFinder) {
		String aMessage = "";
		NewPredictionEvent retVal = new NewPredictionEvent("", aFinder);
		retVal.announce();
		return retVal;
	}

	
}
