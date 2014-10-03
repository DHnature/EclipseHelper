package trace.difficultyPrediction;

import difficultyPrediction.StatusInformation;
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
		if (shouldInstantiate(NewExtractedFeatures.class)) {
		NewExtractedFeatures retVal = new NewExtractedFeatures("", aStatusInformation, aFinder);
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
