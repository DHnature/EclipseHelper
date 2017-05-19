package trace.recorder;

import util.trace.TraceableInfo;
import util.trace.Tracer;
import fluorite.util.EventLoggerConsole;

public class ExcludedCommand extends TraceableInfo{
	String commandName;
	public ExcludedCommand(String aMessage, String aCommandName,  Object aFinder) {
		 super(aMessage, aFinder);
		 commandName = aCommandName;
	}
	public String getCommandName() {
		return commandName;
	}
	
	
    public static String toString(String aCommandName) {
    	return("(" + 
    				aCommandName + 
    				")");
    }
    public static ExcludedCommand newCase (String aMessage, String aCommandName,  Object aFinder) {
    	if (Tracer.isPrintInfoEnabled(aFinder) || Tracer.isPrintInfoEnabled(ExcludedCommand.class))
      	  EventLoggerConsole.getConsole().getMessageConsoleStream().println("(" + Tracer.infoPrintBody(ExcludedCommand.class) + ") " +aMessage);
    	if (shouldInstantiate(ExcludedCommand.class)) {
    	ExcludedCommand retVal = new ExcludedCommand(aMessage, aCommandName, aFinder);
    	retVal.announce();
    	return retVal;
    	}
		Tracer.info(aFinder, aMessage);
		Tracer.info(ExcludedCommand.class, aMessage);


    	return null;
    }
    public static ExcludedCommand newCase (String aCommandName,  Object aFinder) {
    	String aMessage = toString(aCommandName);
    	return newCase(aMessage, aCommandName, aFinder);
//    	ExcludedCommand retVal = new ExcludedCommand(aMessage, aCommandName, aFinder);
//    	retVal.announce();
//    	return retVal;
    }
}
