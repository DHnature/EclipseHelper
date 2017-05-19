package trace.difficultyPrediction;

import util.trace.TraceableInfo;
import util.trace.Tracer;
import difficultyPrediction.eventAggregation.AnEventAggregatorDetails;
import fluorite.util.EventLoggerConsole;


public class NewEventSegmentAggregation extends TraceableInfo {
	AnEventAggregatorDetails eventAggregatorDetails;
	public NewEventSegmentAggregation(String aMessage, AnEventAggregatorDetails aEventAggregatorDetails, Object aFinder) {
		super(aMessage, aFinder);
	}
	
	
	public AnEventAggregatorDetails getEventAggregatorDetails() {
		return eventAggregatorDetails;
	}


	public static NewEventSegmentAggregation newCase(String aMessage, AnEventAggregatorDetails aEventAggregatorDetails, Object aFinder) {
		if (Tracer.isPrintInfoEnabled(aFinder) || Tracer.isPrintInfoEnabled(NewEventSegmentAggregation.class))
	    	  EventLoggerConsole.getConsole().getMessageConsoleStream().println("(" + Tracer.infoPrintBody(NewEventSegmentAggregation.class) + ") " +aMessage);
		if (shouldInstantiate(NewEventSegmentAggregation.class)) {
			NewEventSegmentAggregation retVal = new NewEventSegmentAggregation(aMessage, aEventAggregatorDetails, aFinder);
		retVal.announce();
		return retVal;
		}
		Tracer.info(aFinder, aMessage);
		return null;
	}

	public static NewEventSegmentAggregation newCase(AnEventAggregatorDetails aEventAggregatorDetails, Object aFinder) {
		String aMessage = aEventAggregatorDetails.toString();
		return newCase(aMessage, aEventAggregatorDetails, aFinder);
//		MacroRecordingStarted retVal = new MacroRecordingStarted("", aFinder);
//		retVal.announce();
//		return retVal;
	}

	
}
