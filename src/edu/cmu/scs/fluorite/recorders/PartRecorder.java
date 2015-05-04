package edu.cmu.scs.fluorite.recorders;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.text.IDocument;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IWorkbenchPart;

import context.saros.SarosAccessorFactory;
//import de.fu_berlin.inf.dpp.ui.views.SarosView;
import trace.workbench.PartActivated;
import trace.workbench.PartOpened;
import edu.cmu.scs.fluorite.commands.FileOpenCommand;
import edu.cmu.scs.fluorite.model.FileSnapshotManager;
import edu.cmu.scs.fluorite.util.Utilities;

public class PartRecorder extends BaseRecorder implements IPartListener {

	private static PartRecorder instance = null;
	List<IWorkbenchPart> opendedParts = new ArrayList();
	List<IWorkbenchPart> activeParts = new ArrayList();
	List<IPartListener> partListeners = new ArrayList();

	public static PartRecorder getInstance() {
		if (instance == null) {
			instance = new PartRecorder();
		}

		return instance;
	}

	private PartRecorder() {
		super();
	}
	
	public void addPartListener(IPartListener newVal) {
		if (!partListeners.contains(newVal))
			partListeners.add(newVal);
		
	}

	@Override
	public void addListeners(IEditorPart editor) {
		// Do nothing.
	}

	@Override
	public void removeListeners(IEditorPart editor) {
		// Do nothing.
	}

	public void partActivated(IWorkbenchPart part) {
		PartActivated.newCase(part, this);
		if (!activeParts.contains(part))
			activeParts.add(part);
		for (IPartListener aListener:partListeners) {
			aListener.partActivated(part);
		}
		if (part instanceof IEditorPart) {
			if (getRecorder().getEditor() == part) {
				return;
			}

			if (getRecorder().getEditor() != null) {
				String filePath = Utilities.getFilePathFromEditor(getRecorder()
						.getEditor());
				IDocument currentDoc = Utilities.getDocument(getRecorder()
						.getEditor());

				if (filePath != null && currentDoc != null) {
					FileSnapshotManager.getInstance().updateSnapshot(filePath,
							currentDoc.get());
				}

				getRecorder().removeListeners();
			}

			IEditorPart editor = (IEditorPart) part;
			getRecorder().addListeners(editor);
			
			FileOpenCommand newFoc = new FileOpenCommand(editor);
			getRecorder().recordCommand(newFoc);
			getRecorder().fireActiveFileChangedEvent(newFoc);
		}
	}

	public void partBroughtToTop(IWorkbenchPart part) {
		for (IPartListener aListener:partListeners) {
			aListener.partBroughtToTop(part);
		}
		// TODO Auto-generated method stub

	}

	public void partClosed(IWorkbenchPart part) {
		if (opendedParts.contains(part))
			opendedParts.remove(part);
		for (IPartListener aListener:partListeners) {
			aListener.partClosed(part);
		}
		if (part instanceof IEditorPart) {
			getRecorder().removeListeners();
		}
	}

	public void partDeactivated(IWorkbenchPart part) {
		if (activeParts.contains(part))
			activeParts.remove(part);
		for (IPartListener aListener:partListeners) {
			aListener.partDeactivated(part);
		}
		// if (part instanceof IEditorPart) {
		// removeListeners();
		// }
	}

	public void partOpened(IWorkbenchPart part) {
		if (!opendedParts.contains(part))
			opendedParts.add(part);
		for (IPartListener aListener:partListeners) {
			aListener.partOpened(part);
		}
//		if (part instanceof SarosView  ) {
//			SarosAccessorFactory.getSingleton().setSarosView((SarosView) part); 
//		}
		PartOpened.newCase(part, this);

	}

}
