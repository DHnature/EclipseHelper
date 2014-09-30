package trace.recorder;

import util.trace.TraceableInfo;

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
    public static ExcludedCommand newCase (String aCommandName,  Object aFinder) {
    	String aMessage = toString(aCommandName);
    	ExcludedCommand retVal = new ExcludedCommand(aMessage, aCommandName, aFinder);
    	retVal.announce();
    	return retVal;
    }
}
