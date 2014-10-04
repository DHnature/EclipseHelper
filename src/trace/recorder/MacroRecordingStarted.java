package trace.recorder;

import edu.cmu.scs.fluorite.util.EventLoggerConsole;
import trace.plugin.PluginStopped;
import util.trace.Traceable;
import util.trace.TraceableInfo;
import util.trace.Tracer;


public class MacroRecordingStarted extends TraceableInfo {

	public MacroRecordingStarted(String aMessage, Object aFinder) {
		super(aMessage, aFinder);
	}
	
	public static MacroRecordingStarted newCase(String aMessage, Object aFinder) {
		if (Tracer.isPrintInfoEnabled(aFinder) || Tracer.isPrintInfoEnabled(MacroRecordingStarted.class))
	    	  EventLoggerConsole.getConsole().getMessageConsoleStream().println("(" + Tracer.infoPrintBody(MacroRecordingStarted.class) + ") " + aMessage);
		if (shouldInstantiate(MacroRecordingStarted.class)) {
		MacroRecordingStarted retVal = new MacroRecordingStarted("", aFinder);
		retVal.announce();
		return retVal;
		}
		Tracer.info(aFinder, aMessage);
		Tracer.info(MacroRecordingStarted.class, aMessage);


		return null;
	}

	public static MacroRecordingStarted newCase(Object aFinder) {
		String aMessage = "";
		return newCase(aMessage, aFinder);
//		MacroRecordingStarted retVal = new MacroRecordingStarted("", aFinder);
//		retVal.announce();
//		return retVal;
	}

	
}
