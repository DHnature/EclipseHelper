package trace.recorder;

import java.util.LinkedList;

import util.trace.TraceableInfo;
import util.trace.Tracer;
import fluorite.commands.EHICommand;
import fluorite.util.EventLoggerConsole;

public class RecordedCommandsCleared extends TraceableInfo{
	LinkedList<EHICommand> commands;
	public RecordedCommandsCleared(String aMessage, LinkedList<EHICommand> aCommands,  Object aFinder) {
		 super(aMessage, aFinder);
		 commands = aCommands;
	}
	public LinkedList<EHICommand> getCommands() {
		return commands;
	}
	
	
    public static String toString(LinkedList<EHICommand> aCommands) {
    	return("(" + 
//    				aCommands.toString() + 
    			"commands" + // do not read the list as it may mutate
    				")");
    }
    public static RecordedCommandsCleared newCase (String aMessage, LinkedList<EHICommand> aCommands,  Object aFinder) {
    	if (Tracer.isPrintInfoEnabled(aFinder) || Tracer.isPrintInfoEnabled(RecordedCommandsCleared.class))
      	  EventLoggerConsole.getConsole().getMessageConsoleStream().println(Tracer.infoPrintBody(RecordedCommandsCleared.class) + ") " + aMessage);
    	if (shouldInstantiate(RecordedCommandsCleared.class)) {
    	RecordedCommandsCleared retVal = new RecordedCommandsCleared(aMessage, aCommands, aFinder);
    	retVal.announce();
    	return retVal;
    	}
		Tracer.info(aFinder, aMessage);
		Tracer.info(RecordedCommandsCleared.class, aMessage);


    	return null;
    }
    public static RecordedCommandsCleared newCase (LinkedList<EHICommand> aCommands,  Object aFinder) {
    	String aMessage = toString(aCommands);
    	return newCase(aMessage, aCommands, aFinder);
//    	ExcludedCommand retVal = new ExcludedCommand(aMessage, aCommands, aFinder);
//    	retVal.announce();
//    	return retVal;
    }
}
