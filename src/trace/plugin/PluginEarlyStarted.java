package trace.plugin;

import util.trace.Traceable;
import util.trace.TraceableInfo;
import util.trace.Tracer;


public class PluginEarlyStarted extends TraceableInfo {

	public PluginEarlyStarted(String aMessage, Object aFinder) {
		super(aMessage, aFinder);
	}
	
	public static PluginEarlyStarted newCase(String aMessage, Object aFinder) {
		if (shouldInstantiate(PluginEarlyStarted.class)) {
		PluginEarlyStarted retVal = new PluginEarlyStarted("", aFinder);
		retVal.announce();
		return retVal;
		}
		Tracer.info(aFinder, aMessage);

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
