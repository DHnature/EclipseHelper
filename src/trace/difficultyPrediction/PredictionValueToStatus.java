package trace.difficultyPrediction;

import util.trace.Traceable;
import util.trace.TraceableInfo;


public class PredictionValueToStatus extends TraceableInfo {

	public PredictionValueToStatus(String aMessage, Object aFinder) {
		super(aMessage, aFinder);
	}

	public static PredictionValueToStatus newCase(Object aFinder) {
		String aMessage = "";
		PredictionValueToStatus retVal = new PredictionValueToStatus("", aFinder);
		retVal.announce();
		return retVal;
	}

	
}
