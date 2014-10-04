package trace.plugin;

import util.trace.Traceable;
import util.trace.TraceableInfo;
import util.trace.Tracer;


public class PluginStarted extends TraceableInfo {

	public PluginStarted(String aMessage, Object aFinder) {
		super(aMessage, aFinder);
	}
	
	public static PluginStarted newCase(String aMessage, Object aFinder) {
		if (shouldInstantiate(PluginStarted.class)) {
		PluginStarted retVal = new PluginStarted("", aFinder);
		retVal.announce();
		return retVal;
		}
		Tracer.info(aFinder, aMessage);

		return null;
	}

	public static PluginStarted newCase(Object aFinder) {
		String aMessage = "";
		return newCase(aMessage, aFinder);
//		MacroRecordingStarted retVal = new MacroRecordingStarted("", aFinder);
//		retVal.announce();
//		return retVal;
	}

	
}
