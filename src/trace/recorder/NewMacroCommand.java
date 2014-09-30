package trace.recorder;

import util.trace.TraceableInfo;

public class NewMacroCommand extends TraceableInfo{
	String commandName;
	long relativeTimeStamp;		
	public NewMacroCommand(String aMessage, String aCommandName, long aRelativeTimeStamp, Object aFinder) {
		 super(aMessage, aFinder);
		 commandName = aCommandName;
		 relativeTimeStamp = aRelativeTimeStamp;		 
	}
	public String getCommandName() {
		return commandName;
	}
	public long getRelativeTimeStamp() {
		return relativeTimeStamp;
	}
	
    public static String toString(String aCommandName, long aRelativeTimeStamp) {
    	return("(" + 
    				aCommandName 
    				+ "," + aRelativeTimeStamp + ")");
    }
    public static NewMacroCommand newCase (String aCommandName, long aRelativeTimeStamp, Object aFinder) {
    	String aMessage = toString(aCommandName, aRelativeTimeStamp);
    	NewMacroCommand retVal = new NewMacroCommand(aMessage, aCommandName, aRelativeTimeStamp, aFinder);
    	retVal.announce();
    	return retVal;
    }
}
