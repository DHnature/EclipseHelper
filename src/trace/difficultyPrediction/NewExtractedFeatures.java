package trace.difficultyPrediction;

import difficultyPrediction.featureExtraction.RatioFeatures;
import edu.cmu.scs.fluorite.util.EventLoggerConsole;
import trace.plugin.PluginEarlyStarted;
import util.trace.Traceable;
import util.trace.TraceableInfo;
import util.trace.Tracer;


public class NewExtractedFeatures extends TraceableInfo {
	RatioFeatures ratioFeatures;
	public NewExtractedFeatures(String aMessage, RatioFeatures aRatioFeatures, Object aFinder) {
		super(aMessage, aFinder);
	}
	
	
	public RatioFeatures getRatioFeatures() {
		return ratioFeatures;
	}


	public static NewExtractedFeatures newCase(String aMessage, RatioFeatures aRatioFeatures, Object aFinder) {
		if (Tracer.isPrintInfoEnabled(aFinder) || Tracer.isPrintInfoEnabled(NewExtractedFeatures.class))
	    	  EventLoggerConsole.getConsole().getMessageConsoleStream().println("(" + Tracer.infoPrintBody(NewExtractedFeatures.class) + ") " +aMessage);
		if (shouldInstantiate(NewExtractedFeatures.class)) {
		NewExtractedFeatures retVal = new NewExtractedFeatures(aMessage, aRatioFeatures, aFinder);
		retVal.announce();
		return retVal;
		}
		Tracer.info(aFinder, aMessage);
		return null;
	}

	public static NewExtractedFeatures newCase(RatioFeatures aRatioFeatures, Object aFinder) {
		String aMessage = aRatioFeatures.toString();
		return newCase(aMessage, aRatioFeatures, aFinder);
//		MacroRecordingStarted retVal = new MacroRecordingStarted("", aFinder);
//		retVal.announce();
//		return retVal;
	}

	
}
