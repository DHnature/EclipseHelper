package analyzer;
import javax.swing.JFileChooser;

import util.annotations.Row;
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
