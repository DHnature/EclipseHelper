package context.recording;

import java.io.File;
import java.io.IOException;

import org.eclipse.swt.widgets.Shell;

import bus.uigen.misc.OEMisc;
import util.misc.Common;
import util.pipe.ConsoleModel;
import util.remote.ProcessExecer;

public class ADisplayBoundsFileWriter extends AnAbstractDisplayBoundsOutputter implements  DisplayBoundsOutputter {
	
	public static final String WORKSPACE_PATH = "/Users/nicholasdillon/Documents/UNC/Research/WorkspaceIUI/Record20secs/";
	public static final String RECORDER_JAVA_PATH = "/Library/Java/JavaVirtualMachines/jdk1.7.0_10.jdk/Contents/Home/bin/java";
	public static final String RECORDER_CLASS_PATH = WORKSPACE_PATH + "bin:"
			+ WORKSPACE_PATH + "slf4j-api-1.7.7.jar:"
			+ WORKSPACE_PATH + "slf4j-simple-1.7.7.jar:" 
			+ WORKSPACE_PATH + "xuggle-xuggler-5.4.jar";
	public static final String RECORDER_MAIN_CLASS = "Record20secs";
	
	public static final String[] RECORDER_LAUNCHING_COMMAND = 
		{RECORDER_JAVA_PATH, "-cp" ,  RECORDER_CLASS_PATH, RECORDER_MAIN_CLASS};
	
	ProcessExecer processExecer;
	ConsoleModel consoleModel;

	public void connectToRecorder() {
		System.err.println("Calling connect to recorder");
		startRecorder(RECORDER_LAUNCHING_COMMAND);
		// Doesnt matter; just launch recorder and continuously write to file
		//listenToRecorderIOEvents();
	}

	public void startRecorder() {
		startRecorder(RECORDER_LAUNCHING_COMMAND);

	}

	public void startRecorder(String[] aCommand) {		
		processExecer = OEMisc.runWithProcessExecer(aCommand);
		consoleModel = processExecer.getConsoleModel();
		
	}

	@Override
	public void updateRecorder(Shell aShell) {
		String baseName = ADisplayBoundsFileWriter.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		baseName = baseName.replace("%20", " "); // Replace %20's with spaces
		String outputFilename = baseName + "../../WorkspaceIUI/screencaptures/frame.txt";
		File screencaps = new File(baseName + "/../../WorkspaceIUI/screencaptures");
		if(!screencaps.exists()) {
			System.err.println("screencaptures directory does not exist... creating...");
			screencaps.mkdir();
		}
		String bounds = boundsToString(aShell);
		bounds = bounds.substring(bounds.indexOf('{')+1, bounds.length()-1);
		bounds = bounds.replace(',', ' ');
		System.out.println("Updated shell: " + bounds);
		try {
			Common.writeText(outputFilename, bounds);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}