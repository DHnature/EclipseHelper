package analyzer;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import javax.swing.JFileChooser;

import edu.cmu.scs.fluorite.commands.ICommand;
import edu.cmu.scs.fluorite.util.LogReader;
import util.annotations.Column;
import util.annotations.Row;
import util.annotations.Visible;
import bus.uigen.OEFrame;
import bus.uigen.ObjectEditor;
import bus.uigen.models.AFileSetterModel;
import bus.uigen.models.FileSetterModel;

public class AnAnalyzer {
	public static final String PARTICIPANT_INFORMATION_DIRECTORY = "data/ExperimentalData/";

	public static final String PARTICIPANT_INFORMATION_FILE = "Participant_Info.csv";
	public static final String DEFAULT_NUMBER_OF_SEGMENTS = "50";
	public static final String ALL_PARTICIPANTS = "All";
	static Hashtable<String, String> participants = new Hashtable<String, String>();
	List<List<ICommand>> commandsList;

	FileSetterModel participantsFolder;
	AParametersSelector parameters;
	LogReader reader;
	public AnAnalyzer() {
		reader = new LogReader();
		participantsFolder = new AFileSetterModel(JFileChooser.DIRECTORIES_ONLY);
		participantsFolder.setText(MainConsoleUI.PARTICIPANT_INFORMATION_DIRECTORY);
		parameters = new AParametersSelector(this);
		parameters.getParticipants().addChoice(ALL_PARTICIPANTS);
		parameters.getParticipants().setValue(ALL_PARTICIPANTS);
		
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
	boolean directoryLoaded;
	public boolean preLoadDirectory() {
		return !directoryLoaded;
	}
	@Visible(false)
	public void loadDirectory() {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(
					PARTICIPANT_INFORMATION_DIRECTORY
							+ PARTICIPANT_INFORMATION_FILE));
			String word = null;
			while ((word = br.readLine()) != null) {
				String[] userInfo = word.split(",");
				participants.put(userInfo[0].trim(), userInfo[1].trim());
				parameters.getParticipants().addChoice(userInfo[0]);
			}
			br.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		directoryLoaded = true;		
	}
	public boolean preLoadLogs() {
		return directoryLoaded && !logsLoaded;
	}
	boolean logsLoaded;
	@Visible(false)
    public void loadLogs() {
		String participantId = parameters.getParticipants().getValue();
		String numberOfSegments = "" + parameters.getSegmentLength();

		
		if(participantId.equalsIgnoreCase(""))
			participantId = ALL_PARTICIPANTS;
		
		if(numberOfSegments.equalsIgnoreCase(""))
			numberOfSegments = DEFAULT_NUMBER_OF_SEGMENTS;
		
		//todo need to ask for discrete chunks or sliding window
		//may d for discrete and s for sliding window

//		scanIn.close();

		System.out.println("Processing logs for: " + participantId);
		List<String> participantIds = parameters.getParticipants().getChoices();
		List<List<ICommand>> commandsList;
		if (participantId.equals(ALL_PARTICIPANTS)) {
			for (String aParticipantId:participantIds) {
				if (aParticipantId.equals(ALL_PARTICIPANTS))
					continue;

				
//				String aParticipantId = participantIds
//						.nextElement();
//				String aParticipantFolder = participants.
				String aParticipanttFolder = participants.get(aParticipantId);
				commandsList = convertXMLLogToObjects(aParticipanttFolder);
				 MainConsoleUI.processCommands(commandsList, numberOfSegments,aParticipanttFolder);
			}
		} else {
			String aParticipanttFolder = participants.get(participantId);

			commandsList =  convertXMLLogToObjects(aParticipanttFolder);
			 MainConsoleUI.processCommands(commandsList, numberOfSegments, aParticipanttFolder);
		}

		logsLoaded = true;
	}
	public  List<List<ICommand>> convertXMLLogToObjects(
			String aFolderName) {
		
		List<List<ICommand>> listOfListOFcommands = new Vector<List<ICommand>>();
		String fullName = participantsFolder.getText()
				+ aFolderName + "/";
		File folder = new File(fullName);
		if (!folder.exists()) {
			System.out.println("folder does not exist:" + fullName);
			return listOfListOFcommands;
		}
		if (!folder.isDirectory()) {
			System.out.println("folder not a directory:" + fullName);
			return listOfListOFcommands;
		}
		List<String> participantFiles = MainConsoleUI.getFilesForFolder(folder);
		System.out.println("Particpant " + aFolderName + " has "
				+ participantFiles.size() + " file(s)");
		System.out.println();
		for (int i = 0; i < participantFiles.size(); i++) {
			String aFileName = fullName
					+ participantFiles.get(i);
//			List<ICommand> commands = reader.readAll(participantDirectory
//					+ participantFiles.get(i));
			System.out.println("Reading " + aFileName);
//			List<ICommand> commands;
			try {
			List<ICommand> commands = reader.readAll(aFileName);
			listOfListOFcommands.add(commands);

			} catch (Exception e) {
				System.out.println("Could not read file" + aFileName + e);
				
			}
			
//			listOfListOFcommands.add(commands);
		}

		return listOfListOFcommands;
	}
	@Row(1)
	public AParametersSelector getParameters() {
		return parameters;
	}
	//	int segmentLength = 50;
//	@Row(1)
//	public int getSegmentLength() {
//		return segmentLength;
//	}
//	public void setSegmentLength(int newVal) {
//		this.segmentLength = newVal;
//	}
	public static void main (String[] args) {
		OEFrame frame = ObjectEditor.edit(new AnAnalyzer());
		frame.setSize(550, 200);
		
	}

}
