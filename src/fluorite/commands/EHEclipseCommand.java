package fluorite.commands;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.commands.Category;
import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.IHandler2;
import org.eclipse.core.commands.common.NotDefinedException;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.commands.ICommandService;
import org.eclipse.ui.handlers.IHandlerService;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;

import difficultyPrediction.DifficultyPredictionSettings;
import edu.cmu.scs.fluorite.commands.EclipseCommand;
import analyzer.AnalyzerFactory;
import fluorite.util.EventLoggerConsole;

public class EHEclipseCommand extends EclipseCommand implements EHICommand{

//	public static final String XML_ID_ATTR = "commandID";
//	
	public EHEclipseCommand(String commandId, int repeatCount) {
		super(commandId);
//		mCommandId = commandId;
		mRepeatCount = repeatCount;
	}
//
//	private String mCommandId;
	private int mRepeatCount;
//
	public EHEclipseCommand(String commandId) {
		this(commandId, 1);
	}
//
//	public EHEclipseCommand() {
//		// nothing to do here
//	}
//
//	@SuppressWarnings("deprecation")
//	public boolean execute(IEditorPart target) {
//		ICommandService cs = (ICommandService) PlatformUI.getWorkbench()
//				.getAdapter(ICommandService.class);
//		Command command = cs.getCommand(mCommandId);
//		if (command != null) {
//			try {
//				IHandlerService hs = (IHandlerService) PlatformUI
//						.getWorkbench().getAdapter(IHandlerService.class);
//				ExecutionEvent exEvent = hs.createExecutionEvent(command, null);
//
//				// This check is necessary because certain commands (like copy)
//				// may be disabled until there
//				// is a selection. However, the keystrokes that set the
//				// selection kick off an event
//				// that is not guaranteed to be received in time to run the copy
//				// command. The 'new'
//				// way is to implement IHandler2, which explicitly runs the code
//				// to check the enablement,
//				// so for commands that don't implement that, I've gone back to
//				// the deprecated "execute"
//				// method that doesn't set (or check) the possibly out-of-date
//				// enable flag at all.
//
//				for (int i = 0; i < mRepeatCount; ++i) {
//					if (command.getHandler() instanceof IHandler2)
//						command.executeWithChecks(exEvent);
//					else
//						command.execute(exEvent);
//				}
//				return true;
//			} catch (Exception e) {
//				e.printStackTrace();
//				EventLoggerConsole.getConsole().write(e,
//						EventLoggerConsole.Type_Error);
//			}
//		}
//
//		return false;
//	}
//
//	public void dump() {
//		System.out.println("Eclipse Command: " + mCommandId + ", repeat: "
//				+ Integer.toString(mRepeatCount));
//	}
//
//	public Map<String, String> getAttributesMap() {
//		Map<String, String> attrMap = new HashMap<String, String>();
//		attrMap.put(XML_ID_ATTR, mCommandId);
//		return attrMap;
//	}
//
//	public Map<String, String> getDataMap() {
//		return null;
//	}
//
//	@Override
//	public void createFrom(Element commandElement) {
//		super.createFrom(commandElement);
//		
//		Attr attr = null;
//		
//		if ((attr = commandElement.getAttributeNode(XML_ID_ATTR)) != null) {
//			mCommandId = attr.getValue();
//		}
//	}
//
//	public String getCommandType() {
//		return "EclipseCommand";
//	}
//
//	public String getDescription() {
//		if (DifficultyPredictionSettings.isReplayMode()) // workbench is not initialized in replay mode
//			return "";
//		ICommandService cs = (ICommandService) PlatformUI.getWorkbench()
//				.getAdapter(ICommandService.class);
//		if (cs == null) {
//			System.err.println("Null command service:" );
//			return "";
//		}
//		Command command = cs.getCommand(mCommandId);
//		try {
//			return command.getDescription();
//		} catch (NotDefinedException e) {
//			e.printStackTrace();
//		}
//
//		return "";
//	}
//
//	public String getName() {
//		if (DifficultyPredictionSettings.isReplayMode()) // workbench is not initialized in replay mode
//			return mCommandId;
//		
//		ICommandService cs = (ICommandService) PlatformUI.getWorkbench()
//				.getAdapter(ICommandService.class);
//		if (cs == null) {
//			System.err.println("Null command service:" );
//			return mCommandId;
//		}
//		Command command = cs.getCommand(mCommandId);
//		try {
//			return command.getName();
//		} catch (NotDefinedException e) {
//			// e.printStackTrace();
//		}
//		
//
//		return mCommandId;
//	}
//
//	public String getCategory() {
//		if (DifficultyPredictionSettings.isReplayMode()) // workbench is not initialized in replay mode
//			return "";
//		ICommandService cs = (ICommandService) PlatformUI.getWorkbench()
//				.getAdapter(ICommandService.class);
//		if (cs == null) {
//			System.err.println("Null command service:" );
//			return "";
//		}
//		Command command = cs.getCommand(mCommandId);
//		try {
//			Category cat = command.getCategory();
//			if (cat != null)
//				return cat.getName();
//		} catch (NotDefinedException e) {
//			// e.printStackTrace();
//		}
//
//		return "";
//	}
//
//	public String getCategoryID() {
//		ICommandService cs = (ICommandService) PlatformUI.getWorkbench()
//				.getAdapter(ICommandService.class);
//		if (cs == null) {
//			return "";
//		}
//
//		Command command = cs.getCommand(mCommandId);
//		try {
//			Category cat = command.getCategory();
//			if (cat != null)
//				return cat.getId();
//		} catch (NotDefinedException e) {
//			// e.printStackTrace();
//		}
//
//		return "";
//	}
//
//	public String getCommandID() {
//		return mCommandId;
//	}
//
//	public boolean combine(EHICommand anotherCommand) {
//		if (!(anotherCommand instanceof EHEclipseCommand)) {
//			return false;
//		}
//
//		EHEclipseCommand nextCommand = (EHEclipseCommand) anotherCommand;
//		if (nextCommand.getCommandID().equals(getCommandID())) {
//			return true;
//		}
//
//		return false;
//	}
}
