package analyzer.extension;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;

import util.misc.Common;
import difficultyPrediction.DifficultyPredictionEventListener;
import difficultyPrediction.DifficultyPredictionSettings;
import difficultyPrediction.DifficultyRobot;
import difficultyPrediction.extension.APrintingDifficultyPredictionListener;
import difficultyPrediction.featureExtraction.ARatioFeatures;
import difficultyPrediction.featureExtraction.RatioFeatures;
import edu.cmu.scs.fluorite.commands.DifficulyStatusCommand;
import edu.cmu.scs.fluorite.commands.ICommand;
import edu.cmu.scs.fluorite.commands.PredictionCommand;
import edu.cmu.scs.fluorite.model.EventRecorder;
import bus.uigen.OEFrame;
import bus.uigen.ObjectEditor;
import analyzer.AParticipantTimeLine;
import analyzer.AWebLink;
import analyzer.AnAnalyzer;
import analyzer.Analyzer;
import analyzer.AnalyzerListener;
import analyzer.ParticipantTimeLine;
import analyzer.WebLink;
import analyzer.ui.query.AQueryUI;

public class AnAnalyzerProcessor extends APrintingDifficultyPredictionListener implements AnalyzerProcessor{
	static Analyzer analyzer;
	static AnalyzerProcessor analyzerProcessor;
	protected Map<String, ParticipantTimeLine> participantToTimeLine = new HashMap();
	
	String currentParticipant;
	long currentTime;
	long startTime;
	WebLink lastWebLink;
	Integer lastPrediction = 0;
	Integer lastCorrection = 0;
	ParticipantTimeLine participantTimeLine;
	
	@Override
	public void newParticipant(String anId, String aFolder) {
		System.out.println("Extension**New Participant:" + anId);
		participantTimeLine = new AParticipantTimeLine();
		participantToTimeLine.put(anId, participantTimeLine );
		
		if(aFolder!= null) {
			DifficultyRobot.getInstance().addPluginEventEventListener(this);
			DifficultyRobot.getInstance().addRatioFeaturesListener(this);
		}
		
	}
	
	@Override
	public void newBrowseLine(String aLine) {
		System.out.println("Browse line:" + aLine);
		
	}
	@Override
	public void finishedBrowserLines() {
		if (!DifficultyPredictionSettings.isNewRatioFiles() && DifficultyPredictionSettings.isRatioFileExists())
			return;
		String aFileName = DifficultyPredictionSettings.getRatiosFileName();
		StringBuffer timeLineText = participantTimeLine.toText();
		try {
			Common.writeText(aFileName, timeLineText.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	@Override
	public void newBrowseEntries(Date aDate, String aSearchString, String aURL) {
//		System.out.println("Browse Date:" + aDate);
//		System.out.println("Search string:" + aSearchString);
//		System.out.println("Search string:" + aURL);
		lastWebLink = new AWebLink(aSearchString, aURL);
		long aTimeStamp = aDate.getTime();
		int anIndex = participantTimeLine.getIndexBefore(aTimeStamp);
		if (anIndex != -1) {
			List<WebLink> aLinks = participantTimeLine.getWebLinks().get(anIndex);
			if (aLinks == null) {
				aLinks = new ArrayList();
				participantTimeLine.getWebLinks().set(anIndex, aLinks);

			}
			aLinks.add(lastWebLink);
		}
//		participantTimeLine.getWebLinks().add(lastWebLink);
		lastWebLink = null;
	}
	int toInt(PredictionCommand aCommand) {
		if (aCommand.getPredictionType() == null) {
			return -1;
		}
		switch (aCommand.getPredictionType()) {
		case Indeterminate: return -1;
		case MakingProgress: return 0;
		case HavingDifficulty: return 1;
		}
		return -1;
	}
	int toInt(DifficulyStatusCommand aCommand) {
		if (aCommand.getStatus() == null)
			return -1;
		switch (aCommand.getStatus()) {
		case Insurmountable:return 1;
		case Surmountable: return 1;
		case Making_Progress: return 0;
		
		}
		return -1;
	}
	void maybeInitializeTimeStamp(ICommand newCommand) {
		if (newCommand.getTimestamp() == 0 && newCommand.getTimestamp2() != 0) {
			newStartTimeStamp(newCommand.getTimestamp2() );
		}
	}
	void maybeProcessPrediction(ICommand newCommand) {
		if (newCommand instanceof PredictionCommand) {
			lastPrediction = toInt((PredictionCommand) newCommand);
		}
	}
	void maybeProcessCorrection(ICommand newCommand) {
		if (newCommand instanceof DifficulyStatusCommand) {
			lastCorrection = toInt((DifficulyStatusCommand) newCommand);
		}
	}
	@Override
	public void newCommand(ICommand newCommand) {
		maybeInitializeTimeStamp(newCommand);
		maybeProcessPrediction(newCommand);
		maybeProcessCorrection(newCommand);
//		if (newCommand.getTimestamp() == 0 && newCommand.getTimestamp2() != 0) {
//			newStartTimeStamp(newCommand.getTimestamp2() );
//		}
		System.out.println("Extension**New User/Prediction Command:" + newCommand);	
	
	}
	@Override
	public void commandProcessingStarted() {
		System.out.println("Extension**Difficulty Prediction Started");		
	}
	@Override
	public void commandProcessingStopped() {
		System.out.println("Extension**Difficulty Prediction Stopped");		
		insertEntriesForPreviousTimeStamp();
	}
	boolean isBehindTimeStamp(List aList) {
		return (aList.size() < participantTimeLine.getTimeStampList().size()) ;
		
	}
//	void maybeAddNullWebLink() {
//		if (isBehindTimeStamp(participantTimeLine.getWebLinks())) {
//			participantTimeLine.getWebLinks().add(null);
//		}
//	}
	void insertEntriesForPreviousTimeStamp() {
		if (participantTimeLine.getTimeStampList().size() == 0) // no previous time stamp
			return;
		participantTimeLine.getPredictions().add(lastPrediction); // do not reset it as it is a status
		participantTimeLine.getPredictionCorrections().add(lastCorrection);
		lastCorrection = -1; // reset it as this is an event rather than a status
	}
	@Override
	public void newRatios(RatioFeatures newVal) {
		insertEntriesForPreviousTimeStamp();
		currentTime = startTime + newVal.getSavedTimeStamp();
		participantTimeLine.getTimeStampList().add(currentTime);
		participantTimeLine.getDebugList().add(newVal.getDebugRatio());
		participantTimeLine.getDeletionList().add(newVal.getDeletionRatio());
		participantTimeLine.getFocusList().add(newVal.getFocusRatio());
		participantTimeLine.getInsertionList().add(newVal.getInsertionRatio());
		participantTimeLine.getNavigationList().add(newVal.getNavigationRatio());
		participantTimeLine.getRemoveList().add(newVal.getRemoveRatio());
		participantTimeLine.getWebLinks().add(null);
		System.err.println("Extension**New Ratios:" + newVal + " at time:" + (new Date(currentTime)).toString());		
	}
	public void startTimeStamp(long aStartTimeStamp) {
		System.out.println("start time stamp:" + aStartTimeStamp);	
		 EventRecorder.getInstance().setStartTimeStamp(aStartTimeStamp);

//		currentTime = aStartTimeStamp;
//		RatioFeatures aRatioFetaures = new ARatioFeatures();
//		newRatios(aRatioFetaures);

	}
	// this seems to be called in addition to the previous pne
	 void newStartTimeStamp(long aStartTimeStamp) {
		 EventRecorder.getInstance().setStartTimeStamp(aStartTimeStamp);
//		System.out.println("Extension**Difficulty Prediction Started");	
		startTime = aStartTimeStamp;
		//System.out.println("New time stamp: " + startTime );
		//System.out.println ("New date:" + new Date(startTime));

		RatioFeatures aRatioFetaures = new ARatioFeatures();
		newRatios(aRatioFetaures);

	}
	public static void main (String[] args) {
		DifficultyPredictionSettings.setReplayMode(true);

		analyzer = new AnAnalyzer();
		analyzerProcessor = new AnAnalyzerProcessor();
		analyzer.addAnalyzerListener(analyzerProcessor);
		OEFrame frame = ObjectEditor.edit(analyzer);
		frame.setSize(550, 275);
		
		JFrame qframe=new JFrame("Query V1.0");
		qframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		qframe.setResizable(false);
		qframe.setLocationRelativeTo(null);

		qframe.add(new AQueryUI(((AnAnalyzerProcessor) analyzerProcessor).participantToTimeLine));
		qframe.pack();
		qframe.setVisible(true);
		
	}

	@Override
	public void finishParticipant(String anId, String aFolder) {
		// TODO Auto-generated method stub
		
		
	}

}
