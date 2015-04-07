package analyzer.extension;

import java.util.Date;

import difficultyPrediction.featureExtraction.RatioFeatures;
import edu.cmu.scs.fluorite.commands.ICommand;

public class AFileReplayAnalyzerProcessor extends ALiveAnalyzerProcessor{
	public void newCommand(ICommand newCommand) {
		maybeInitializeTimeStamp(newCommand);
		maybeProcessPrediction(newCommand);
		maybeProcessCorrection(newCommand);
		//		if (newCommand.getTimestamp() == 0 && newCommand.getTimestamp2() != 0) {
		//			newStartTimeStamp(newCommand.getTimestamp2() );
		//		}
		System.out.println("Extension**New User/Prediction Command:" + newCommand);	

	}
	public void newRatios(RatioFeatures newVal) {

		insertEntriesForPreviousTimeStamp();
		currentTime = startTime + newVal.getSavedTimeStamp();
		participantTimeLine.getEditList().add(newVal.getEditRatio());
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

}
