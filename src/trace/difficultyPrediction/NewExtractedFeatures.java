package trace.difficultyPrediction;

import util.trace.Traceable;
import util.trace.TraceableInfo;


public class NewExtractedFeatures extends TraceableInfo {

	public NewExtractedFeatures(String aMessage, Object aFinder) {
		super(aMessage, aFinder);
	}

	public static NewExtractedFeatures newCase(Object aFinder) {
		String aMessage = "";
		NewExtractedFeatures retVal = new NewExtractedFeatures("", aFinder);
		retVal.announce();
		return retVal;
	}

	
}
