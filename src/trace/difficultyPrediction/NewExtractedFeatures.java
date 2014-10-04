package trace.difficultyPrediction;

import difficultyPrediction.StatusInformation;
import edu.cmu.scs.fluorite.util.EventLoggerConsole;
import trace.plugin.PluginEarlyStarted;
import util.trace.Traceable;
import util.trace.TraceableInfo;
import util.trace.Tracer;


public class NewExtractedFeatures extends TraceableInfo {
	StatusInformation statusInformation;
	public NewExtractedFeatures(String aMessage, StatusInformation aStatusInformation, Object aFinder) {
		super(aMessage, aFinder);
	}
	
	
	public StatusInformation getStatusInformation() {
		return statusInformation;
	}


	public static NewExtractedFeatures newCase(String aMessage, StatusInformation aStatusInformation, Object aFinder) {
		if (Tracer.isPrintInfoEnabled(aFinder) || Tracer.isPrintInfoEnabled(NewExtractedFeatures.class))
	    	  EventLoggerConsole.getConsole().getMessageConsoleStream().println("(" + Tracer.infoPrintBody(NewExtractedFeatures.class) + ") " +aMessage);
		if (shouldInstantiate(NewExtractedFeatures.class)) {
		NewExtractedFeatures retVal = new NewExtractedFeatures(aMessage, aStatusInformation, aFinder);
		retVal.announce();
		return retVal;
		}
		Tracer.info(aFinder, aMessage);
		return null;
	}

	public static NewExtractedFeatures newCase(StatusInformation aStatusInformation, Object aFinder) {
		String aMessage = aStatusInformation.toString();
		return newCase(aMessage, aStatusInformation, aFinder);
//		MacroRecordingStarted retVal = new MacroRecordingStarted("", aFinder);
//		retVal.announce();
//		return retVal;
	}

	
}
