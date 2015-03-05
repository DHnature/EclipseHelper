package trace.plugin;

import edu.cmu.scs.fluorite.util.EventLoggerConsole;
import trace.recorder.MacroRecordingStarted;
import trace.recorder.NewMacroCommand;
import util.trace.Traceable;
import util.trace.TraceableInfo;
import util.trace.Tracer;


public class PluginEarlyStarted extends TraceableInfo {

	public PluginEarlyStarted(String aMessage, Object aFinder) {
		super(aMessage, aFinder);
	}
	
	public static PluginEarlyStarted newCase(String aMessage, Object aFinder) {
		if (Tracer.isPrintInfoEnabled(aFinder) || Tracer.isPrintInfoEnabled(PluginEarlyStarted.class))
	    	  EventLoggerConsole.getConsole().getMessageConsoleStream().println("(" + Tracer.infoPrintBody(PluginEarlyStarted.class) + ") " +aMessage);
		if (shouldInstantiate(PluginEarlyStarted.class)) {
		PluginEarlyStarted retVal = new PluginEarlyStarted("", aFinder);
		retVal.announce();
		return retVal;
		}
		Tracer.info(aFinder, aMessage);
		Tracer.info(PluginEarlyStarted.class, aMessage);


		return null;
	}

	public static PluginEarlyStarted newCase(Object aFinder) {
		String aMessage = "";
		return newCase(aMessage, aFinder);
//		MacroRecordingStarted retVal = new MacroRecordingStarted("", aFinder);
//		retVal.announce();
//		return retVal;
	}

	
}
