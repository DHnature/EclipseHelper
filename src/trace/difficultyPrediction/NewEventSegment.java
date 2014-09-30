package trace.difficultyPrediction;

import util.trace.Traceable;
import util.trace.TraceableInfo;


public class NewEventSegment extends TraceableInfo {

	public NewEventSegment(String aMessage, Object aFinder) {
		super(aMessage, aFinder);
	}

	public static NewEventSegment newCase(Object aFinder) {
		String aMessage = "";
		NewEventSegment retVal = new NewEventSegment("", aFinder);
		retVal.announce();
		return retVal;
	}

	
}
