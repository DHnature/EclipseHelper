package trace.difficultyPrediction;

import util.trace.Traceable;
import util.trace.TraceableInfo;


public class StatusAggregationStarted extends TraceableInfo {

	public StatusAggregationStarted(String aMessage, Object aFinder) {
		super(aMessage, aFinder);
	}

	public static StatusAggregationStarted newCase(Object aFinder) {
		String aMessage = "";
		StatusAggregationStarted retVal = new StatusAggregationStarted("", aFinder);
		retVal.announce();
		return retVal;
	}

	
}
