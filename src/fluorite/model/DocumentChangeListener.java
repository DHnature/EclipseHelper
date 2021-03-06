package fluorite.model;

import fluorite.commands.EHBaseDocumentChangeEvent;
import fluorite.commands.EHFileOpenCommand;

public interface DocumentChangeListener {
	
	/**
	 * Fired when a new file was opened and the corresponding FileOpenCommand was recorded. 
	 */
	public void activeFileChanged(EHFileOpenCommand foc);
	
	/**
	 * Fired when a new documentChange event was recorded.
	 */
	public void documentChanged(EHBaseDocumentChangeEvent docChange);
	
	/**
	 * Fired when a new documentChange event was recorded and its values are fixed.
	 */
	public void documentChangeFinalized(EHBaseDocumentChangeEvent docChange);

}
