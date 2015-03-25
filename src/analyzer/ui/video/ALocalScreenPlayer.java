package analyzer.ui.video;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Shell;

import util.annotations.Visible;
import analyzer.ui.GeneralizedPlayAndRewindCounter;
import analyzer.ui.PlayerFactory;
import analyzer.ui.graphics.PlayAndRewindCounter;
import bus.uigen.OEFrame;
import bus.uigen.ObjectEditor;
import context.recording.ADisplayBoundsPiper;
//bad hierarchy, just removing recording stuff from superclass, but that is what you get if there is no multiple inheirtance
public class ALocalScreenPlayer extends ALocalScreenRecorderAndPlayer {
	
	
	@Override
	public void listenToDisplayEvents() {
	
	}
	
	
	public void handleEvent(Event event) {
		
//		updateRecorder((Shell) event.widget);
		
	}
	public void controlMoved(ControlEvent e) {
//		Shell aShell = (Shell)e.getSource();
////		System.out.println ("Changed shell " + boundsToString ((Shell)e.getSource()));
//		updateRecorder(aShell);
//		// TODO Auto-generated method stub
		
	}
	

}
