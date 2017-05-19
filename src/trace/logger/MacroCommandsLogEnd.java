package trace.logger;

import java.util.LinkedList;

import util.trace.TraceableInfo;
import util.trace.Tracer;
import fluorite.commands.EHICommand;
import fluorite.util.EventLoggerConsole;

public class MacroCommandsLogEnd extends TraceableInfo{
	LinkedList<EHICommand> commands;
	public MacroCommandsLogEnd(String aMessage, LinkedList<EHICommand> aCommands,  Object aFinder) {
		 super(aMessage, aFinder);
		 commands = aCommands;
	}
	public LinkedList<EHICommand> getCommands() {
		return commands;
	}
	
	
    public static String toString(LinkedList<EHICommand> aCommands) {
    	return("(" + 
//    				aCommands.toString() + 
    			"commands" +
    				")");
    }
    public static MacroCommandsLogEnd newCase (String aMessage, LinkedList<EHICommand> aCommands,  Object aFinder) {
    	try {
    	if (Tracer.isPrintInfoEnabled(aFinder) || Tracer.isPrintInfoEnabled(MacroCommandsLogEnd.class))
	    	  EventLoggerConsole.getConsole().getMessageConsoleStream().println("(" + Tracer.infoPrintBody(MacroCommandsLogEnd.class) + ") " +aMessage);
    	} catch (Exception e) {
    		System.out.println("MacroCommandsLogEnd" + e); // console disposed
    	}
    	if (shouldInstantiate(MacroCommandsLogEnd.class)) {
    	MacroCommandsLogEnd retVal = new MacroCommandsLogEnd(aMessage, aCommands, aFinder);
    	retVal.announce();
    	return retVal;
    	}
		Tracer.info(aFinder, aMessage);

    	return null;
    }
    public static MacroCommandsLogEnd newCase (LinkedList<EHICommand> aCommands,  Object aFinder) {
    	String aMessage = toString(aCommands);
    	return newCase(aMessage, aCommands, aFinder);
//    	ExcludedCommand retVal = new ExcludedCommand(aMessage, aCommands, aFinder);
//    	retVal.announce();
//    	return retVal;
    }
}
