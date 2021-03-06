package fluorite.model;

import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

import org.eclipse.core.runtime.Platform;

import fluorite.commands.EHICommand;
import fluorite.util.Utilities;

public class FluoriteXMLFormatter extends Formatter {

	public FluoriteXMLFormatter(long startTimestamp) {
		mStartTimestamp = startTimestamp;
	}

	private long mStartTimestamp;
	// If this value is returned, there's something wrong with the version recognizing logic. 
	private static final String LOG_VERSION = "0.5.0.unknown";
	
	/**
	 * Programatically determine the Fluorite version number and use it for log version.
	 * @return Log version (which is the same as Fluorite version)
	 */
	private static String getLogVersion() {
		String versionStr = LOG_VERSION;

		try {
			versionStr = Platform.getBundle("edu.cmu.scs.fluorite").getVersion().toString();
		} catch (Exception e) {
			System.out.println ("not running in plug-in mode, returning for versuionString 0.5.3.qualifier");
			return "0.5.3.qualifier";
			// Do nothing but just print out the stack trace.
			// There might be some null pointer exception or such.
//			e.printStackTrace();
		}

		return versionStr;
	}

	@Override
	public String format(LogRecord rec) {
		Object[] params = rec.getParameters();
		if (params.length != 1 || !(params[0] instanceof EHICommand)) {
			return null;
		}

		EHICommand command = (EHICommand) params[0];
		return command.persist();
	}

	@Override
	public String getHead(Handler h) {
		return "<Events startTimestamp=\"" + Long.toString(mStartTimestamp)
				+ "\" logVersion=\"" + getLogVersion() + "\">" + Utilities.NewLine;
	}

	@Override
	public String getTail(Handler h) {
		return "</Events>" + Utilities.NewLine;
	}

}
