package trace.logger;

import java.util.LinkedList;

import edu.cmu.scs.fluorite.commands.ICommand;
import edu.cmu.scs.fluorite.util.EventLoggerConsole;
import trace.plugin.PluginEarlyStarted;
import util.trace.TraceableInfo;
import util.trace.Tracer;

public class MacroCommandsLogBegin extends TraceableInfo{
	LinkedList<ICommand> commands;
	public MacroCommandsLogBegin(String aMessage, LinkedList<ICommand> aCommands,  Object aFinder) {
		 super(aMessage, aFinder);
		 commands = aCommands;
	}
	public LinkedList<ICommand> getCommands() {
		return commands;
	}
	
	
    public static String toString(LinkedList<ICommand> aCommands) {
    	return("(" + 
    				aCommands.toString() + 
    				")");
    }
    public static MacroCommandsLogBegin newCase (String aMessage, LinkedList<ICommand> aCommands,  Object aFinder) {
    	try {
    	if (Tracer.isPrintInfoEnabled(aFinder) || Tracer.isPrintInfoEnabled(MacroCommandsLogBegin.class))
	    	  EventLoggerConsole.getConsole().getMessageConsoleStream().println("(" + Tracer.infoPrintBody(MacroCommandsLogBegin.class) + ") " +aMessage);
    	} catch (Exception e) {
    		System.out.println("MacroCommandsLogBegin" + e);
    	}
    	if (shouldInstantiate(MacroCommandsLogBegin.class)) {
    	MacroCommandsLogBegin retVal = new MacroCommandsLogBegin(aMessage, aCommands, aFinder);
    	retVal.announce();
    	return retVal;
    	}
		Tracer.info(aFinder, aMessage);

    	return null;
    }
    public static MacroCommandsLogBegin newCase (LinkedList<ICommand> aCommands,  Object aFinder) {
    	String aMessage = toString(aCommands);
    	return newCase(aMessage, aCommands, aFinder);
//    	ExcludedCommand retVal = new ExcludedCommand(aMessage, aCommands, aFinder);
//    	retVal.announce();
//    	return retVal;
    }
}
