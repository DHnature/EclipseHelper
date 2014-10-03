package trace.logger;

import util.trace.TraceableInfo;
import util.trace.Tracer;

public class LogFileCreated extends TraceableInfo{
	String fileName;
	public LogFileCreated(String aMessage, String aFileName,  Object aFinder) {
		 super(aMessage, aFinder);
		 fileName = aFileName;
	}
	public String getFileName() {
		return fileName;
	}
	
	
    public static String toString(String aFileName) {
    	return("(" + 
    				aFileName + 
    				")");
    }
    public static LogFileCreated newCase (String aMessage, String aFileName,  Object aFinder) {
    	if (shouldInstantiate(LogFileCreated.class)) {
    	LogFileCreated retVal = new LogFileCreated(aMessage, aFileName, aFinder);
    	retVal.announce();
    	return retVal;
    	}
		Tracer.info(aFinder, aMessage);

    	return null;
    }
    public static LogFileCreated newCase (String aFileName,  Object aFinder) {
    	String aMessage = toString(aFileName);
    	return newCase(aMessage, aFileName, aFinder);
//    	ExcludedCommand retVal = new ExcludedCommand(aMessage, aFileName, aFinder);
//    	retVal.announce();
//    	return retVal;
    }
}
