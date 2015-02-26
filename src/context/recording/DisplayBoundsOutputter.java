package context.recording;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

public interface DisplayBoundsOutputter extends Listener, PropertyChangeListener, ControlListener {

	public abstract void startRecorder(String aCommand[]);

	public abstract void listenToRecorderIOEvents();

	public abstract String boundsToString();

	public abstract void updateRecorder();

	public abstract void handleEvent(Event event);

	public abstract void propertyChange(PropertyChangeEvent evt);

	void connectToDisplayAndRecorder();

	void connectToRecorder();

	void listenToDisplayEvents();

	void updateRecorder(Shell aShell);

	String boundsToString(Shell aShell);

}