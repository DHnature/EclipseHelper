package context.recording;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Shell;

import util.annotations.Visible;
import analyzer.ui.video.LocalScreenPlayerFactory;
import bus.uigen.OEFrame;
import bus.uigen.ObjectEditor;


public abstract class AnAbstractDisplayBoundsOutputter implements  DisplayBoundsOutputter {
	// should really be specified in a config file
//	public static final String RECORDER_JAVA_PATH = "D:/Program Files/Java/jdk1.7.0_51/bin/java";
//	public static final String RECORDER_CLASS_PATH = "D:/dewan_backup/Java/eclipse/workspace/FileExample/bin";
//	public static final String RECORDER_MAIN_CLASS = "InputAndOutput";
////	public static final String RECORDER_LAUNCHING_COMMAND = RECORDER_JAVA_PATH + 
////									" " + "-cp" + " " + RECORDER_CLASS_PATH +
////									" " + RECORDER_MAIN_CLASS;
//	public static final String[] RECORDER_LAUNCHING_COMMAND = {RECORDER_JAVA_PATH, 
//			"-cp" ,  RECORDER_CLASS_PATH,
//			RECORDER_MAIN_CLASS};
	Display display;
	protected OEFrame oeFrame;
//	ProcessExecer processExecer;
//	ConsoleModel consoleModel;
	public AnAbstractDisplayBoundsOutputter() {
//		display = Display.getCurrent();
//		display.addListener(SWT.RESIZE, this);
//		startRecorder(RECORDER_LAUNCHING_COMMAND);
//		listenToRecorderIOEvents();
		
	}
	
	@Override
	public void connectToDisplayAndRecorder() {
		connectToDisplay();
		listenToDisplayEvents();
		connectToRecorder();
	
	}
	@Visible(false)
	public void connectToDisplay() {
		display = Display.getCurrent();
		display.addListener(SWT.RESIZE, this);
	}
	@Visible(false)
	@Override
	public void listenToDisplayEvents() {
		
		System.out.println("Shell " + display.getActiveShell());
//		display.getActiveShell().addListener(SWT.RESIZE, this);
		Shell[] shells = display.getShells();
		for (Shell shell:shells) {
			
			shell.addListener(SWT.RESIZE, this);
			shell.addControlListener(this);
			System.out.println("Shell " + shell);

			System.out.println("Shell bounds " + shell.getBounds());
		}
//		display.addListener(SWT.RESIZE, this);

	}
	
	
	/* (non-Javadoc)
	 * @see context.recording.DisplayBoundsOutputter#listenToRecorderIOEvents()
	 */

	/* (non-Javadoc)
	 * @see context.recording.DisplayBoundsOutputter#boundsToString()
	 */
	@Visible(false)
	@Override
	public String boundsToString() {
		if (display == null) return "";
		Shell aShell = display.getActiveShell(); // can be null, dangerous!
		if (aShell == null) return "";
		return aShell.getBounds().toString();
	}
	@Visible(false)
	@Override
	public String boundsToString(Shell aShell) {
		
		return aShell.getBounds().toString();
	}
	
	/* (non-Javadoc)
	 * @see context.recording.DisplayBoundsOutputter#updateRecorder()
	 */
//	@Override
//	public void updateRecorder() {
//		System.out.println("Active shell:" + boundsToString());
//		if (processExecer != null)
//		processExecer.consoleModel().setInput(boundsToString());
//	}
	
	/* (non-Javadoc)
	 * @see context.recording.DisplayBoundsOutputter#handleEvent(org.eclipse.swt.widgets.Event)
	 */
	@Visible(false)
	@Override
	public void handleEvent(Event event) {
	
		updateRecorder((Shell) event.widget);
		
	}
	@Visible(false)
	@Override
	public void controlMoved(ControlEvent e) {
		Shell aShell = (Shell)e.getSource();
//		System.out.println ("Changed shell " + boundsToString ((Shell)e.getSource()));
		updateRecorder(aShell);
		// TODO Auto-generated method stub
		
	}
	@Visible(false)
	@Override
	public void controlResized(ControlEvent e) {
		Shell aShell = (Shell)e.getSource();

		System.out.println ("Changed shell " + aShell);
		updateRecorder(aShell);


		// TODO Auto-generated method stub
		
	}
	@Visible(false)
	public void createUI() {
		oeFrame = ObjectEditor.edit(RecorderFactory.getSingleton());
		oeFrame.setSize(250, 150);
	}

}
