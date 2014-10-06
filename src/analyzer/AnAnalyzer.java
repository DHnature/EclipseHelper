package analyzer;
import javax.swing.JFileChooser;

import util.annotations.Row;
import util.annotations.Visible;
import bus.uigen.ObjectEditor;
import bus.uigen.models.AFileSetterModel;
import bus.uigen.models.FileSetterModel;

public class AnAnalyzer {

	FileSetterModel participantsFolder;
	int segmentLength = 50;
	public AnAnalyzer() {
		participantsFolder = new AFileSetterModel(JFileChooser.DIRECTORIES_ONLY);
		participantsFolder.setText(MainConsoleUI.PARTICIPANT_INFORMATION_DIRECTORY);
		
	}
	@Row(0)
	public FileSetterModel getParticipantsFolder() {
		return participantsFolder;
	}
	@Visible(false)
	public String getParticipantsFolderName() {
		return participantsFolder.getText();
	}
	@Visible(false)
	public void setParticipantsFolderName(String aName) {
		 participantsFolder.setText(aName);
	}
	@Row(1)
	public int getSegmentLength() {
		return segmentLength;
	}
	public void setSegmentLength(int newVal) {
		this.segmentLength = newVal;
	}
	public static void main (String[] args) {
		ObjectEditor.edit(new AnAnalyzer());
		
	}

}
