package context.recording;

import java.io.IOException;

import org.eclipse.swt.widgets.Shell;

import util.misc.Common;


public class ADisplayBoundsFileWriter extends AnAbstractDisplayBoundsOutputter implements  DisplayBoundsOutputter {
	// should really be specified in a config file
	public static final String RECORDER_FILE_PATH = "D:/EcliseBounds.txt";
//	public static final String RECORDER_LAUNCHING_COMMAND = RECORDER_JAVA_PATH + 
//									" " + "-cp" + " " + RECORDER_CLASS_PATH +
//									" " + RECORDER_MAIN_CLASS;
	
//	public ADisplayBoundsFileWriter() {
//		
//		
//	}	
	@Override
	public void connectToRecorder() {
//		startRecorder(RECORDER_LAUNCHING_COMMAND);		
//		listenToRecorderIOEvents();
	}
	

	/* (non-Javadoc)
	 * @see context.recording.DisplayBoundsOutputter#startRecorder(java.lang.String)
	 */
	public void startRecorder() {
//		startRecorder(RECORDER_LAUNCHING_COMMAND);

	}
//	@Override
//	public void startRecorder(String[] aCommand) {		
//		processExecer = OEMisc.runWithProcessExecer(aCommand);
//		consoleModel = processExecer.getConsoleModel();
//		
//	}
	
	/* (non-Javadoc)
	 * @see context.recording.DisplayBoundsOutputter#listenToRecorderIOEvents()
	 */
//	@Override
//	public void listenToRecorderIOEvents() {
//		processExecer.consoleModel().addPropertyChangeListener(this);
//	}
	
	/* (non-Javadoc)
	 * @see context.recording.DisplayBoundsOutputter#boundsToString()
	 */
//	@Override
//	public String boundsToString() {
//		if (display == null) return "";
//		Shell aShell = display.getActiveShell(); // can be null, dangerous!
//		if (aShell == null) return "";
//		return aShell.getBounds().toString();
//	}
//	
//	@Override
//	public String boundsToString(Shell aShell) {
//		
//		return aShell.getBounds().toString();
//	}
	
	/* (non-Javadoc)
	 * @see context.recording.DisplayBoundsOutputter#updateRecorder()
	 */
//	@Override
//	public void updateRecorder() {
//		System.out.println("Active shell:" + boundsToString());
//		if (processExecer != null)
//		processExecer.consoleModel().setInput(boundsToString());
//	}
	@Override
	public void updateRecorder(Shell aShell) {
		System.out.println("Updated shell:" + boundsToString(aShell));
		   try {
			Common.writeText(RECORDER_FILE_PATH, boundsToString(aShell));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
//	/* (non-Javadoc)
//	 * @see context.recording.DisplayBoundsOutputter#handleEvent(org.eclipse.swt.widgets.Event)
//	 */
//	@Override
//	public void handleEvent(Event event) {
//	
//		updateRecorder((Shell) event.widget);
//		
//	}
//	/* (non-Javadoc)
//	 * @see context.recording.DisplayBoundsOutputter#propertyChange(java.beans.PropertyChangeEvent)
//	 */
//	@Override
//	public void propertyChange(PropertyChangeEvent evt) {
//		
//	}
//	@Override
//	public void controlMoved(ControlEvent e) {
//		Shell aShell = (Shell)e.getSource();
////		System.out.println ("Changed shell " + boundsToString ((Shell)e.getSource()));
//		updateRecorder(aShell);
//		// TODO Auto-generated method stub
//		
//	}
//	@Override
//	public void controlResized(ControlEvent e) {
//		Shell aShell = (Shell)e.getSource();
//
//		System.out.println ("Changed shell " + aShell);
//		updateRecorder(aShell);
//
//
//		// TODO Auto-generated method stub
//		
//	}

	

}
