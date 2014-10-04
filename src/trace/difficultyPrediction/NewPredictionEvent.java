package trace.difficultyPrediction;

import edu.cmu.scs.fluorite.util.EventLoggerConsole;
import trace.plugin.PluginEarlyStarted;
import util.trace.Traceable;
import util.trace.TraceableInfo;
import util.trace.Tracer;


public class NewPredictionEvent extends TraceableInfo {

	public NewPredictionEvent(String aMessage, Object aFinder) {
		super(aMessage, aFinder);
	}
	
	public static NewPredictionEvent newCase(String aMessage, Object aFinder) {
		if (Tracer.isPrintInfoEnabled(aFinder) || Tracer.isPrintInfoEnabled(NewPredictionEvent.class))
	    	  EventLoggerConsole.getConsole().getMessageConsoleStream().println("(" + Tracer.infoPrintBody(NewPredictionEvent.class) + ") " +aMessage);
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
