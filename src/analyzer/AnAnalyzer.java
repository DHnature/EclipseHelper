package analyzer;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import javax.swing.JFileChooser;

import util.annotations.Row;
import util.annotations.Visible;
import bus.uigen.OEFrame;
import bus.uigen.ObjectEditor;
import bus.uigen.models.AFileSetterModel;
import bus.uigen.models.FileSetterModel;
import config.FactoriesSelector;
import difficultyPrediction.ADifficultyPredictionPluginEventProcessor;
import difficultyPrediction.DifficultyPredictionPluginEventProcessor;
import difficultyPrediction.DifficultyPredictionSettings;
import difficultyPrediction.Mediator;
import difficultyPrediction.eventAggregation.EventAggregator;
import difficultyPrediction.featureExtraction.RatioFeatures;
import edu.cmu.scs.fluorite.commands.ICommand;
import edu.cmu.scs.fluorite.util.LogReader;

public class AnAnalyzer implements Analyzer  {
	public static final String PARTICIPANT_DIRECTORY = "data/";
	public static final String EXPERIMENTAL_DATA = "ExperimentalData/";
	public static final String OUTPUT_DATA = "OutputData/";
	public static final String ECLIPSE_FOLDER = "Eclipse/";
	public static final String BROWSER_FOLDER = "Browser/";


	public static final String PARTICIPANT_INFORMATION_DIRECTORY = "data/ExperimentalData/";
	public static final String PARTICIPANT_OUTPUT_DIRECTORY = "data/OutputData/";

	public static final String PARTICIPANT_INFORMATION_FILE = "Participant_Info.csv";
	public static final int SEGMENT_LENGTH = 50;
	public static final String ALL_PARTICIPANTS = "All";
	public static final String IGNORE_KEYWORD="IGNORE";
	static Hashtable<String, String> participants = new Hashtable<String, String>();
	static long startTimeStamp;
	List<List<ICommand>> nestedCommandsList;

	FileSetterModel participantsFolder, ouputFolder, experimentalData;
	AnAnalyzerParametersSelector parameters;
	LogReader reader;
//	protected Thread difficultyPredictionThread;	
//	protected DifficultyPredictionRunnable difficultyPredictionRunnable;
//	protected BlockingQueue<ICommand> pendingPredictionCommands;
	DifficultyPredictionPluginEventProcessor difficultyEventProcessor;
	List<AnalyzerListener> listeners = new ArrayList();
	
//	boolean newRatioFiles;
	
	
	public AnAnalyzer() {
		DifficultyPredictionSettings.setReplayMode(true);
		DifficultyPredictionSettings.setSegmentLength(SEGMENT_LENGTH);

		reader = new LogReader();
		participantsFolder = new AFileSetterModel(JFileChooser.DIRECTORIES_ONLY);
		participantsFolder.setText(PARTICIPANT_DIRECTORY);
		parameters = new AnAnalyzerParametersSelector(this);
		parameters.getParticipants().addChoice(ALL_PARTICIPANTS);
		parameters.getParticipants().setValue(ALL_PARTICIPANTS);

		 
		
	}
	/* (non-Javadoc)
	 * @see analyzer.Analyzer#getParticipantsFolder()
	 */
	@Override
	@Row(0)
	public FileSetterModel getParticipantsFolder() {
		return participantsFolder;
	}
	/* (non-Javadoc)
	 * @see analyzer.Analyzer#getParticipantsFolderName()
	 */
	@Override
	@Visible(false)
	public String getParticipantsFolderName() {
		return participantsFolder.getText();
	}
	/* (non-Javadoc)
	 * @see analyzer.Analyzer#setParticipantsFolderName(java.lang.String)
	 */
	@Override
	@Visible(false)
	public void setParticipantsFolderName(String aName) {
		 participantsFolder.setText(aName);
	}
	boolean directoryLoaded;
	/* (non-Javadoc)
	 * @see analyzer.Analyzer#preLoadDirectory()
	 */
	@Override
	public boolean preLoadDirectory() {
		return !directoryLoaded;
	}
	/* (non-Javadoc)
	 * @see analyzer.Analyzer#loadDirectory()
	 */
	@Override
	@Visible(false)
	public void loadDirectory() {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(
					participantsFolder.getLabel().getText() + EXPERIMENTAL_DATA
//					PARTICIPANT_INFORMATION_DIRECTORY
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
	/* (non-Javadoc)
	 * @see analyzer.Analyzer#preLoadLogs()
	 */
	@Override
	public boolean preLoadLogs() {
		return directoryLoaded;
//				&& !logsLoaded;
	}
	boolean logsLoaded;
	/* (non-Javadoc)
	 * @see analyzer.Analyzer#loadLogs()
	 */
	@Override
	@Visible(false)
    public void loadLogs() {
		final Runnable aRunnable = 
				new Runnable() {
					public void run() {
						syncLoadLogs();
					}};
		Thread aThread = (new Thread(aRunnable));
		aThread.start();
	}
	public void syncLoadLogs() {
		FactoriesSelector.configureFactories();
		String participantId = parameters.getParticipants().getValue();
		String numberOfSegments = "" + parameters.getPredictionParameters().getSegmentLength();

		
		if(participantId.equalsIgnoreCase(""))
			participantId = ALL_PARTICIPANTS;
		
		if(numberOfSegments.equalsIgnoreCase(""))
			numberOfSegments = "" + SEGMENT_LENGTH;
		
		//todo need to ask for discrete chunks or sliding window
		//may d for discrete and s for sliding window

//		scanIn.close();
 
		//Now get all the participants in a list
		List<String> participantList=new ArrayList<String>(Arrays.asList(participantId.split(" ")));
		participantList.removeAll(Collections.singleton(""));
		
		System.out.println("Processing logs for: " + participantId);
		List<String> participantIds = parameters.getParticipants().getChoices();
		List<List<ICommand>> commandsList;
		
		if (participantList.get(0).equals(ALL_PARTICIPANTS)) {
			boolean ignoreOn = false;
			//Build the ignore list
			if(participantList.size()>1 && participantList.get(1).equalsIgnoreCase(IGNORE_KEYWORD)) {
				ignoreOn=true;
				
			}
			
			
			notifyNewParticipant(ALL_PARTICIPANTS, null);
			for (String aParticipantId:participantIds) {
				if (aParticipantId.equals(ALL_PARTICIPANTS) ||
						(ignoreOn && participantList.contains(aParticipantId))) {
					
					continue;
				}
		
				processParticipant(aParticipantId);
				
			}
			
			notifyFinishParticipant(ALL_PARTICIPANTS, null);
			
		} else {
			String aParticipanttFolder = participants.get(participantId);
			processParticipant(participantId);

		}
		//old stuff, in case we need to revert 12/20/2014
		
//		if (participantId.equals(ALL_PARTICIPANTS)) {
//			notifyNewParticipant(ALL_PARTICIPANTS, null);
//			for (String aParticipantId:participantIds) {
//				if (aParticipantId.equals(ALL_PARTICIPANTS)) {
//					continue;
//				}
//				// integrated analyzer
//				processParticipant(aParticipantId);
////				waitForParticipantLogsToBeProcessed();
//				
//
//// jason's code
////				String aParticipanttFolder = participants.get(aParticipantId);
////				commandsList = convertXMLLogToObjects(aParticipanttFolder);
////				 MainConsoleUI.processCommands(participantsFolder.getText(), commandsList, numberOfSegments,aParticipanttFolder);
//			}
//			
//			notifyFinishParticipant(ALL_PARTICIPANTS, null);
//		} else {
//			String aParticipanttFolder = participants.get(participantId);
//			processParticipant(participantId);
//// jason's code, separator mediator
////			commandsList =  convertXMLLogToObjects(aParticipanttFolder);
////			DifficultyPredictionSettings.setRatiosFileName(aParticipanttFolder + "ratios.csv");
////			processParticipant(participantId);
////			 MainConsoleUI.processCommands(participantsFolder.getText(), commandsList, numberOfSegments, aParticipanttFolder);
//		}

		logsLoaded = true;
	}
	
	public void processBrowserHistoryOfFolder (String aFolderName) {
		String fullName =	aFolderName;
		File folder = new File(fullName);
		if (!folder.exists()) {
			System.out.println("folder does not exist:" + fullName);
			return;
		}
		if (!folder.isDirectory()) {
			System.out.println("folder not a directory:" + fullName);
			return;
		}
		List<String> participantFiles = MainConsoleUI.getFilesForFolder(folder);
		System.out.println("Particpant " + aFolderName + " has "
				+ participantFiles.size() + " file(s)");
//		System.out.println();
		for (int i = 0; i < participantFiles.size(); i++) {
			String aFileName = fullName
					+ participantFiles.get(i);
			if (!aFileName.endsWith(".txt"))
				continue;
				
//			List<ICommand> commands = reader.readAll(participantDirectory
//					+ participantFiles.get(i));
			System.out.println("Reading " + aFileName);
			processBrowserHistoryOfFile(aFileName);
//			
			
//			listOfListOFcommands.add(commands);
		}
		notifyFinishedBrowserLines();

	
	}
	
	public void processBrowserHistoryOfFile(String aFileName) {
		try {
		FileInputStream fis = new FileInputStream(aFileName);
		 
		//Construct BufferedReader from InputStreamReader
		BufferedReader br = new BufferedReader(new InputStreamReader(fis));
	 
		String line = null;
		while ((line = br.readLine()) != null) {
//			System.out.println(line);
			notifyNewBrowseLine(line);
		}
	 
		br.close();
		} catch (Exception e) {
			System.out.println("ProcessBrowserHstory" + e);
		}
		
	}
	void waitForParticipantLogsToBeProcessed() {
		try {
//			difficultyPredictionThread.join();
			difficultyEventProcessor.getDifficultyPredictionThread().join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	/* (non-Javadoc)
	 * @see analyzer.Analyzer#processParticipant(java.lang.String)
	 */
	@Override
	public void processParticipant(String aParticipantId) {
		String aParticipantFolder = participants.get(aParticipantId);
//		notifyNewParticipant(aParticipantId);
		String aFullParticipantOutputFolderName =participantsFolder.getText() + OUTPUT_DATA + aParticipantFolder + "/";
		String aFullParticipantDataFolderName =participantsFolder.getText() + EXPERIMENTAL_DATA + aParticipantFolder + "/" + ECLIPSE_FOLDER;
		File anOutputFolder = new File(aFullParticipantOutputFolderName);
		if (!anOutputFolder.exists())
			anOutputFolder.mkdirs();
		String aFullRatiosFileName = aFullParticipantOutputFolderName + "ratios.csv";		
		File aRatiosFile = new File(aFullRatiosFileName);
		if (aRatiosFile.exists()) {
			DifficultyPredictionSettings.setRatioFileExists(true);

		} else {
//		if (!aRatiosFile.exists())
		try {
			DifficultyPredictionSettings.setRatioFileExists(false);
			aRatiosFile.createNewFile();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		}
		// erase file if it exists
		if (aRatiosFile.exists() && DifficultyPredictionSettings.isNewRatioFiles())	
		try {
			FileOutputStream writer = new FileOutputStream(aRatiosFile);
			writer.close();

		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		

		nestedCommandsList =  convertXMLLogToObjects(aFullParticipantDataFolderName);
		DifficultyPredictionSettings.setRatiosFileName(aFullRatiosFileName);
		difficultyEventProcessor = new ADifficultyPredictionPluginEventProcessor();
		ADifficultyPredictionPluginEventProcessor.setInstance(difficultyEventProcessor);
		difficultyEventProcessor.commandProcessingStarted();
		Mediator mediator = difficultyEventProcessor.getDifficultyPredictionRunnable().getMediator();

//		difficultyPredictionRunnable = new ADifficultyPredictionRunnable();
//		pendingPredictionCommands = difficultyPredictionRunnable.getPendingCommands();
//		difficultyPredictionThread = new Thread(difficultyPredictionRunnable);
//		difficultyPredictionThread.setName(DifficultyPredictionRunnable.DIFFICULTY_PREDICTION_THREAD_NAME);
//		difficultyPredictionThread.setPriority(Math.min(
//				Thread.currentThread().getPriority(),
//				DifficultyPredictionRunnable.DIFFICULTY_PREDICTION_THREAD_PRIORITY));
//		difficultyPredictionThread.start();
//		PluginThreadCreated.newCase(difficultyPredictionThread.getName(), this);
//		Mediator mediator = difficultyPredictionRunnable.getMediator();
		EventAggregator eventAggregator = mediator.getEventAggregator();
		eventAggregator.setEventAggregationStrategy(new DiscreteChunksAnalyzer("" + DifficultyPredictionSettings.getSegmentLength()));
		notifyNewParticipant(aParticipantId, aParticipantFolder);

		startTimeStamp = 0;
		for (int index = 0; index < nestedCommandsList.size(); index++) {
			List<ICommand> commands = nestedCommandsList.get(index);
			for (int i = 0; i < commands.size(); i++) {
				ICommand aCommand = commands.get(i);
//				if ((commands.get(i).getTimestamp() == 0)
//						&& (commands.get(i).getTimestamp2() > 0)) {
				if ((aCommand.getTimestamp() == 0)
							&& (aCommand.getTimestamp2() > 0)) {
					// this is the starttimestamp
					startTimeStamp = commands.get(i).getTimestamp2();
					difficultyEventProcessor.newCommand(aCommand);

					notifyStartTimeStamp(startTimeStamp);
					
				} else {
					eventAggregator.setStartTimeStamp(startTimeStamp); // not sure this is ever useful
					try {
//						pendingPredictionCommands.put(commands.get(i));
//						System.out.println("Put command:" + commands.get(i) );
//						difficultyEventProcessor.recordCommand(commands.get(i));
						difficultyEventProcessor.newCommand(aCommand);

//					} catch (InterruptedException e) {
					} catch (Exception e) {

						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
//					eventAggregator.getEventAggregationStrategy().performAggregation(commands.get(i), eventAggregator);

					
					
				}

			}
		}

		difficultyEventProcessor.commandProcessingStopped();
		waitForParticipantLogsToBeProcessed();


//		pendingPredictionCommands.add(new AnEndOfQueueCommand());
//		try {
////			difficultyPredictionThread.join();
//			difficultyEventProcessor.getDifficultyPredictionThread().join();
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		processBrowserHistoryOfFolder(participantsFolder.getText() + EXPERIMENTAL_DATA + aParticipantFolder + "/" + BROWSER_FOLDER);

		notifyFinishParticipant(aParticipantId, aParticipantFolder);
//		for (ICommand aCommand: commandsList) {
//			
//		}
		

	}
	/* (non-Javadoc)
	 * @see analyzer.Analyzer#convertXMLLogToObjects(java.lang.String)
	 */
	@Override
	public  List<List<ICommand>> convertXMLLogToObjects(
			String aFolderName) {
		
		List<List<ICommand>> listOfListOFcommands = new Vector<List<ICommand>>();
//		String fullName = participantsFolder.getText()
//				+ aFolderName + "/";
		String fullName =	aFolderName;
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
			if (!aFileName.endsWith(".xml"))
				continue;
				
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
	/* (non-Javadoc)
	 * @see analyzer.Analyzer#getParameters()
	 */
	@Override
	@Row(1)
	public AnAnalyzerParametersSelector getAnalyzerParameters() {
		return parameters;
	}
//	@Visible(false)
//	@Override
//	public boolean isNewRatioFiles() {
//		return DifficultyPredictionSettings.isNewRatioFiles();
//	}
//	@Override
//	public void setNewRatioFiles(boolean newRatioFiles) {
//		this.newRatioFiles = newRatioFiles;
//	}
	//	int segmentLength = 50;
//	@Row(1)
//	public int getSegmentLength() {
//		return segmentLength;
//	}
//	public void setSegmentLength(int newVal) {
//		this.segmentLength = newVal;
//	}
	
	// let us do this in the analyzerprocessor
	public static void maybeRecordFeatures(RatioFeatures details) {
//		if (!DifficultyPredictionSettings.isReplayMode())  {
//			LineGraphComposer.getLineGraph().newRatios(details);
//			return;
//		}
		if (!DifficultyPredictionSettings.isNewRatioFiles() && DifficultyPredictionSettings.isRatioFileExists())
			return;
		return;
		
//		String aFileName = DifficultyPredictionSettings.getRatiosFileName();
//
//
//		long absoluteTime = startTimeStamp + details.getSavedTimeStamp();
//		
//		java.util.Date time=new Date(absoluteTime);
//
//		Calendar mydate = Calendar.getInstance();
////		mydate.setTimeInMillis(details.getSavedTimeStamp());
//		mydate.setTimeInMillis(absoluteTime);
//		
//		//mydate.get(Calendar.HOUR)
//		//mydate.get(Calendar.MINUTE)
//		//mydate.get(Calendar.SECOND)
////		DateTime timestamp = new DateTime(details.getSavedTimeStamp());
//		DateTime timestamp = new DateTime(absoluteTime);
//
//		//timestamp.get(timestamp)
//		
////		System.out.println(timestamp.toString("MM-dd-yyyy H:mm:ss"));
//		try
//		{
////		    String filename= "/users/jasoncarter/filename.csv";
////		    String filename = dataFolder + "ratios.csv";
//		    FileWriter fw = new FileWriter(aFileName,true); //the true will append the new data
//		   
//		    fw.write(""+ details.getInsertionRatio());
//		    fw.write(",");
//		    fw.write("" + details.getDeletionRatio());
//		    fw.write(",");
//		    fw.write("" + details.getDebugRatio());
//		    fw.write(",");
//		    fw.write("" + details.getNavigationRatio());
//		    fw.write(",");
//		    fw.write("" + details.getFocusRatio());
//		    fw.write(",");
//			fw.write("" + details.getRemoveRatio());
//			fw.write(",");
//			String timeStampString = time.toString();
//			timeStampString = timestamp.toString("MM-dd-yyyy H:mm:ss");
////			fw.write("" + timestamp.toString("MM-dd-yyyy H:mm:ss"));
////			fw.write("" + timestamp.toString("MM-dd-yyyy H:mm:ss"));
//			fw.write("" + timeStampString);
//			fw.write("\n");
//		    fw.close();
//		}
//		catch(IOException ioe)
//		{
//		    System.err.println("IOException: " + ioe.getMessage());
//		}
		
		

	}
	/* (non-Javadoc)
	 * @see analyzer.Analyzer#getDifficultyEventProcessor()
	 */
	@Override
	@Visible(false)
	public DifficultyPredictionPluginEventProcessor getDifficultyEventProcessor() {
		return difficultyEventProcessor;
	}
	/* (non-Javadoc)
	 * @see analyzer.Analyzer#setDifficultyEventProcessor(difficultyPrediction.DifficultyPredictionPluginEventProcessor)
	 */
	@Override
	public void setDifficultyEventProcessor(
			DifficultyPredictionPluginEventProcessor difficultyEventProcessor) {
		this.difficultyEventProcessor = difficultyEventProcessor;
	}
	@Override
	public void addAnalyzerListener(AnalyzerListener aListener) {
		listeners.add(aListener);
	}
	@Override
	public void removeAnalyzerListener(AnalyzerListener aListener) {
		listeners.remove(aListener);
	}
	@Override
	public void notifyNewParticipant(String anId, String aFolder) {
		for (AnalyzerListener aListener:listeners) {
			aListener.newParticipant(anId, aFolder);
		}
	}
	@Override
	public void notifyFinishParticipant(String anId, String aFolder) {
		for (AnalyzerListener aListener:listeners) {
			aListener.finishParticipant(anId, aFolder);
		}
	}
	@Override
	public void notifyStartTimeStamp(long aStartTimeStamp) {
		for (AnalyzerListener aListener:listeners) {
			aListener.startTimeStamp(aStartTimeStamp);
		}
	}
	
	@Override
	public void notifyNewBrowseLine(String aLine) {
		for (AnalyzerListener aListener:listeners) {
			aListener.newBrowseLine(aLine);
			String[] parts = aLine.split("\t");
			String[] dateParts = parts[0].split(" ");
			String dateString = dateParts[0] + " " + dateParts[1];
			Date aDate = new Date(dateString);
			aListener.newBrowseEntries(aDate, parts[1], parts[2]);
		}
	}
	
	public void notifyFinishedBrowserLines() {
		for (AnalyzerListener aListener:listeners) {
			aListener.finishedBrowserLines();
			
		}
	}
	
	static Analyzer instance;
	@Visible(false)
	public static Analyzer getInstance() {
		if (instance == null) {
			instance = new AnAnalyzer();
		}
		return instance;
	}
	public static void main (String[] args) {
		
		
//		Analyzer analyzer = new AnAnalyzer();
		DifficultyPredictionSettings.setReplayMode(true);

		OEFrame frame = ObjectEditor.edit(AnAnalyzer.getInstance());
		frame.setSize(550, 450);
		
	}

}
