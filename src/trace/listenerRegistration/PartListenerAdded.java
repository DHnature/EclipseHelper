package trace.listenerRegistration;

import org.eclipse.ui.IPartListener;

import edu.cmu.scs.fluorite.util.EventLoggerConsole;
import trace.plugin.PluginStopped;
import util.trace.TraceableInfo;
import util.trace.Tracer;

public class PartListenerAdded extends TraceableInfo{
	IPartListener partListener;
	public PartListenerAdded(String aMessage, IPartListener aPartListener,  Object aFinder) {
		 super(aMessage, aFinder);
		 partListener = aPartListener;
	}
	public IPartListener getPartListener() {
		return partListener;
	}
	
	
    public static String toString(IPartListener aPartListener) {
    	return("(" + 
    				aPartListener.toString() +
    				")");
    }
    public static PartListenerAdded newCase (String aMessage, IPartListener aPartListener,  Object aFinder) {
    	if (Tracer.isPrintInfoEnabled(aFinder) || Tracer.isPrintInfoEnabled(PartListenerAdded.class))
      	  EventLoggerConsole.getConsole().getMessageConsoleStream().println("(" + Tracer.infoPrintBody(PartListenerAdded.class) + ") " +aMessage);
    	if (shouldInstantiate(PartListenerAdded.class)) {
    	PartListenerAdded retVal = new PartListenerAdded(aMessage, aPartListener, aFinder);
    	retVal.announce();
    	return retVal;
    	}
		Tracer.info(aFinder, aMessage);
		Tracer.info(PartListenerAdded.class, aMessage);


    	return null;
    }
    public static PartListenerAdded newCase (IPartListener aPartListener,  Object aFinder) {
    	String aMessage = toString(aPartListener);
    	return newCase(aMessage, aPartListener, aFinder);
//    	ExcludedCommand retVal = new ExcludedCommand(aMessage, aPartListener, aFinder);
//    	retVal.announce();
//    	return retVal;
    }
}
