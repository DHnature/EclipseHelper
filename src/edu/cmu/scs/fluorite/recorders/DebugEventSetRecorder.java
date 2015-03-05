package edu.cmu.scs.fluorite.recorders;

import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.DebugEvent;
import org.eclipse.debug.core.IDebugEventSetListener;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.IProcess;
import org.eclipse.ui.IEditorPart;

import edu.cmu.scs.fluorite.commands.RunCommand;

public class DebugEventSetRecorder extends BaseRecorder implements
		IDebugEventSetListener {

	private static DebugEventSetRecorder instance = null;

	public static DebugEventSetRecorder getInstance() {
		if (instance == null) {
			instance = new DebugEventSetRecorder();
		}

		return instance;
	}

	private DebugEventSetRecorder() {
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

	public void handleDebugEvents(DebugEvent[] debugEvents) {
		for (DebugEvent event : debugEvents) 
		{
			if (event.getKind() == DebugEvent.CREATE
					|| event.getKind() == DebugEvent.TERMINATE)
			{
				Object source = event.getSource();
				boolean terminate = event.getKind() == DebugEvent.TERMINATE;

				if (source instanceof IProcess) {
					IProcess process = (IProcess) source;
					ILaunchConfiguration config = process.getLaunch()
							.getLaunchConfiguration();

					if (config == null) {
						return;
					}

					@SuppressWarnings("rawtypes")
					Map attributes = null;
					try {
						attributes = config.getAttributes();
					} catch (CoreException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					// Retrieve the corresponding project name
					String projectName = (String) (attributes
							.get("org.eclipse.jdt.launching.PROJECT_ATTR"));

					getRecorder().recordCommand(
							new RunCommand(false, terminate, projectName, false, false, false, false));

				} else if (source instanceof IDebugTarget) {
					getRecorder().recordCommand(
							new RunCommand(true, terminate, null,false, false, false, false));
				}
			}
			
			if (event.getKind() ==DebugEvent.BREAKPOINT)
			{
				Object source = event.getSource();
				
				if (source instanceof IProcess) {
					IProcess process = (IProcess) source;
					ILaunchConfiguration config = process.getLaunch()
							.getLaunchConfiguration();

					if (config == null) {
						return;
					}

					@SuppressWarnings("rawtypes")
					Map attributes = null;
					try {
						attributes = config.getAttributes();
					} catch (CoreException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					// Retrieve the corresponding project name
					String projectName = (String) (attributes
							.get("org.eclipse.jdt.launching.PROJECT_ATTR"));
					
					getRecorder().recordCommand(
							new RunCommand(false, false, projectName,true, false, false, false));
				}
				else if (source instanceof IDebugTarget) {
					getRecorder().recordCommand(
							new RunCommand(false, false, null,true, false, false, false));

				}
			}
			if (event.getKind() ==DebugEvent.STEP_END)
			{
				getRecorder().recordCommand(
							new RunCommand(false, false, null,false, true, false, false));
				
			}
			if (event.getKind() ==DebugEvent.STEP_INTO)
			{
				getRecorder().recordCommand(
							new RunCommand(false, false, null,false, false, true, false));
				
			}
				
			if (event.getKind() ==DebugEvent.STEP_RETURN)
			{
				getRecorder().recordCommand(
							new RunCommand(false, false, null,false, false, false, true));
				
			}
			
		}
	}

}
