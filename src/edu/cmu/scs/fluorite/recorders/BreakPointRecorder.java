package edu.cmu.scs.fluorite.recorders;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IMarkerDelta;
import org.eclipse.debug.core.IBreakpointListener;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.ui.IEditorPart;

import edu.cmu.scs.fluorite.commands.BreakPointCommand;
import edu.cmu.scs.fluorite.commands.ExceptionCommand;

public class BreakPointRecorder extends BaseRecorder implements IBreakpointListener{

	private static BreakPointRecorder instance = null;
	


	public static BreakPointRecorder getInstance() {
		if (instance == null) {
			instance = new BreakPointRecorder();
		}

		return instance;
	}
	
	@Override
	public void breakpointAdded(IBreakpoint breakpoint) {
		try
		{
			IMarker marker = breakpoint.getMarker();
			String lineNumber = marker.getAttribute(IMarker.LINE_NUMBER).toString();
			getRecorder().recordCommand(new BreakPointCommand(lineNumber,true));
		
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void breakpointRemoved(IBreakpoint breakpoint, IMarkerDelta delta) {
		try
		{
			IMarker marker = breakpoint.getMarker();
			String lineNumber = marker.getAttribute(IMarker.LINE_NUMBER).toString();
			getRecorder().recordCommand(new BreakPointCommand(lineNumber,false));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
	}

	@Override
	public void breakpointChanged(IBreakpoint breakpoint, IMarkerDelta delta) {
		// TODO Auto-generated method stub
		
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
