package trace.view;

import org.eclipse.swt.widgets.Composite;

import util.trace.Traceable;
import util.trace.TraceableInfo;
import util.trace.Tracer;


public class ViewCreated extends TraceableInfo {
	Composite composite;
	public ViewCreated(String aMessage, Composite aComposite, Object aFinder) {
		super(aMessage, aFinder);
	}
	
	
	public Composite getComposite() {
		return composite;
	}


	public static ViewCreated newCase(String aMessage, Composite aComposite, Object aFinder) {
		if (shouldInstantiate(ViewCreated.class)) {
		ViewCreated retVal = new ViewCreated(aMessage, aComposite, aFinder);
		retVal.announce();
		return retVal;
		}
		Tracer.info(aFinder, aMessage);
		return null;
	}

	public static ViewCreated newCase(Composite aComposite, Object aFinder) {
		String aMessage = aComposite.toString();
		return newCase(aMessage, aComposite, aFinder);
//		MacroRecordingStarted retVal = new MacroRecordingStarted("", aFinder);
//		retVal.announce();
//		return retVal;
	}

	
}
