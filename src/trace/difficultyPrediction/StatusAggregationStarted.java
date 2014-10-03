package trace.difficultyPrediction;

import util.trace.Traceable;
import util.trace.TraceableInfo;
import util.trace.Tracer;


public class StatusAggregationStarted extends TraceableInfo {

	public StatusAggregationStarted(String aMessage, Object aFinder) {
		super(aMessage, aFinder);
	}
	
	public static StatusAggregationStarted newCase(String aMessage, Object aFinder) {
		if (shouldInstantiate(StatusAggregationStarted.class)) {
		StatusAggregationStarted retVal = new StatusAggregationStarted("", aFinder);
		retVal.announce();
		return retVal;
		}
		Tracer.info(aFinder, aMessage);

		return null;
	}

	public static StatusAggregationStarted newCase(Object aFinder) {
		String aMessage = "";
		return newCase(aMessage, aFinder);
//		MacroRecordingStarted retVal = new MacroRecordingStarted("", aFinder);
//		retVal.announce();
//		return retVal;
	}

	
}
