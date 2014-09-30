package trace.difficultyPrediction;

import util.trace.Traceable;
import util.trace.TraceableInfo;


public class NewPrediction extends TraceableInfo {

	public NewPrediction(String aMessage, Object aFinder) {
		super(aMessage, aFinder);
	}

	public static NewPrediction newCase(Object aFinder) {
		String aMessage = "";
		NewPrediction retVal = new NewPrediction("", aFinder);
		retVal.announce();
		return retVal;
	}

	
}
