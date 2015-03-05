package edu.cmu.scs.fluorite.recorders;

import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.variables.IValueVariable;
import org.eclipse.core.variables.IValueVariableListener;
import org.eclipse.debug.core.DebugEvent;
import org.eclipse.debug.core.IDebugEventSetListener;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.IProcess;
import org.eclipse.ui.IEditorPart;

import edu.cmu.scs.fluorite.commands.RunCommand;

public class VariableValueRecorder extends BaseRecorder implements
IValueVariableListener {
	
	

	private static VariableValueRecorder instance = null;

	public static VariableValueRecorder getInstance() {
		if (instance == null) {
			instance = new VariableValueRecorder();
		}

		return instance;
	}

	private VariableValueRecorder() {
		super();
	}

	@Override
	public void addListeners(IEditorPart editor) {
		// Do nothing.
	}

	@Override
	public void removeListeners(IEditorPart editor) {
		// Do nothing.
	}

	@Override
	public void variablesAdded(IValueVariable[] variables) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void variablesRemoved(IValueVariable[] variables) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void variablesChanged(IValueVariable[] variables) {
		// TODO Auto-generated method stub
		
	}

	

}
