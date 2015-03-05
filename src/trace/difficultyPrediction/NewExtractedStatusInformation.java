package trace.difficultyPrediction;

import difficultyPrediction.StatusInformation;
import edu.cmu.scs.fluorite.util.EventLoggerConsole;
import trace.plugin.PluginEarlyStarted;
import util.trace.Traceable;
import util.trace.TraceableInfo;
import util.trace.Tracer;


public class NewExtractedStatusInformation extends TraceableInfo {
	StatusInformation statusInformation;
	public NewExtractedStatusInformation(String aMessage, StatusInformation aStatusInformation, Object aFinder) {
		super(aMessage, aFinder);
	}
	
	
	public StatusInformation getStatusInformation() {
		return statusInformation;
	}


	public static NewExtractedStatusInformation newCase(String aMessage, StatusInformation aStatusInformation, Object aFinder) {
		if (Tracer.isPrintInfoEnabled(aFinder) || Tracer.isPrintInfoEnabled(NewExtractedStatusInformation.class))
	    	  EventLoggerConsole.getConsole().getMessageConsoleStream().println("(" + Tracer.infoPrintBody(NewExtractedStatusInformation.class) + ") " +aMessage);
		if (shouldInstantiate(NewExtractedStatusInformation.class)) {
		NewExtractedStatusInformation retVal = new NewExtractedStatusInformation(aMessage, aStatusInformation, aFinder);
		retVal.announce();
		return retVal;
		}
		Tracer.info(aFinder, aMessage);
		return null;
	}

	public static NewExtractedStatusInformation newCase(StatusInformation aStatusInformation, Object aFinder) {
		String aMessage = aStatusInformation.toString();
		return newCase(aMessage, aStatusInformation, aFinder);
//		MacroRecordingStarted retVal = new MacroRecordingStarted("", aFinder);
//		retVal.announce();
//		return retVal;
	}

	
}
