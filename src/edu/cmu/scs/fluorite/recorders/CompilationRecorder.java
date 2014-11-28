package edu.cmu.scs.fluorite.recorders;

import org.eclipse.ui.IEditorPart;

import edu.cmu.scs.fluorite.commands.CompilationCommand;

public class CompilationRecorder extends BaseRecorder  {

private static CompilationRecorder instance = null;
	
	public static CompilationRecorder getInstance() {
		if (instance == null) {
			instance = new CompilationRecorder();
		}

		return instance;
	}
	
	public void record(CompilationCommand command)
	{
		getRecorder().recordCommand(command);
	}
	
	
	@Override
	public void addListeners(IEditorPart editor) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeListeners(IEditorPart editor) {
		// TODO Auto-generated method stub
		
	}


}


