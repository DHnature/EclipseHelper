package edu.cmu.scs.fluorite.recorders;

import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.events.ShellListener;
import org.eclipse.ui.IEditorPart;

import edu.cmu.scs.fluorite.commands.ShellCommand;

public class ShellRecorder extends BaseRecorder implements ShellListener {

	private static ShellRecorder instance = null;

	public static ShellRecorder getInstance() {
		if (instance == null) {
			instance = new ShellRecorder();
		}

		return instance;
	}

	@Override
	public void addListeners(IEditorPart editor) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeListeners(IEditorPart editor) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void shellActivated(ShellEvent e) {
		getRecorder().recordCommand(new ShellCommand(true,false,false,false, false));
	}

	@Override
	public void shellClosed(ShellEvent e) {
		getRecorder().recordCommand(new ShellCommand(false,true,false,false, false));
		
	}

	@Override
	public void shellDeactivated(ShellEvent e) {
		getRecorder().recordCommand(new ShellCommand(false,false,true,false, false));
		
		
	}

	@Override
	public void shellDeiconified(ShellEvent e) {//maximized
		getRecorder().recordCommand(new ShellCommand(false,false,false,true, false));
		
	}

	@Override
	public void shellIconified(ShellEvent e) {//minimized
		getRecorder().recordCommand(new ShellCommand(false,false,false,false, true));
		
	}

}
