package trace.logger;

import java.util.LinkedList;

import edu.cmu.scs.fluorite.commands.ICommand;
import edu.cmu.scs.fluorite.util.EventLoggerConsole;
import trace.plugin.PluginEarlyStarted;
import util.trace.TraceableInfo;
import util.trace.Tracer;

public class MacroCommandsLogEnd extends TraceableInfo{
	LinkedList<ICommand> commands;
	public MacroCommandsLogEnd(String aMessage, LinkedList<ICommand> aCommands,  Object aFinder) {
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
    public static MacroCommandsLogEnd newCase (String aMessage, LinkedList<ICommand> aCommands,  Object aFinder) {
    	if (Tracer.isPrintInfoEnabled(aFinder) || Tracer.isPrintInfoEnabled(MacroCommandsLogEnd.class))
	    	  EventLoggerConsole.getConsole().getMessageConsoleStream().println("(" + Tracer.infoPrintBody(MacroCommandsLogEnd.class) + ") " +aMessage);
    	if (shouldInstantiate(MacroCommandsLogEnd.class)) {
    	MacroCommandsLogEnd retVal = new MacroCommandsLogEnd(aMessage, aCommands, aFinder);
    	retVal.announce();
    	return retVal;
    	}
		Tracer.info(aFinder, aMessage);

    	return null;
    }
    public static MacroCommandsLogEnd newCase (LinkedList<ICommand> aCommands,  Object aFinder) {
    	String aMessage = toString(aCommands);
    	return newCase(aMessage, aCommands, aFinder);
//    	ExcludedCommand retVal = new ExcludedCommand(aMessage, aCommands, aFinder);
//    	retVal.announce();
//    	return retVal;
    }
}
