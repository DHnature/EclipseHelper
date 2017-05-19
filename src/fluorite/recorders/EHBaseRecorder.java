package fluorite.recorders;

import org.eclipse.ui.IEditorPart;

import fluorite.model.EHEventRecorder;

public abstract class EHBaseRecorder {

	protected EHBaseRecorder() {
		mRecorder = EHEventRecorder.getInstance();
	}

	public abstract void addListeners(IEditorPart editor);

	public abstract void removeListeners(IEditorPart editor);

	private EHEventRecorder mRecorder;

	protected EHEventRecorder getRecorder() {
		return mRecorder;
	}

}
