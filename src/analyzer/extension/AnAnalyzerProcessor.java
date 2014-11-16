package analyzer.extension;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import difficultyPrediction.DifficultyPredictionEventListener;
import difficultyPrediction.extension.APrintingDifficultyPredictionListener;
import difficultyPrediction.featureExtraction.RatioFeatures;
import edu.cmu.scs.fluorite.commands.ICommand;
import bus.uigen.OEFrame;
import bus.uigen.ObjectEditor;
import analyzer.AParticipantTimeLine;
import analyzer.AWebLink;
import analyzer.AnAnalyzer;
import analyzer.Analyzer;
import analyzer.AnalyzerListener;
import analyzer.ParticipantTimeLine;
import analyzer.WebLink;

public class AnAnalyzerProcessor extends APrintingDifficultyPredictionListener implements AnalyzerProcessor{
	static Analyzer analyzer;
	static AnalyzerProcessor analyzerProcessor;
	protected Map<String, ParticipantTimeLine> participantToTimeLine = new HashMap();
	
	String currentParticipant;
	long currentTime;
	WebLink lastWebLink;
	Integer lastPrediction = 0;
	Integer lastCorrection = 0;
	
	@Override
	public void newParticipant(String anId) {
		System.out.println("Extension**New Participant:" + anId);
		participantToTimeLine.put(anId, new AParticipantTimeLine());
		analyzer.getDifficultyEventProcessor().addDifficultyPredictionEventListener(this);		
		
	}
	
	@Override
	public void newBrowseLine(String aLine) {
		System.out.println("Browse line:" + aLine);
		
	}
	@Override
	public void newBrowseEntries(Date aDate, String aSearchString, String aURL) {
		System.out.println("Browse Date:" + aDate);
		System.out.println("Search string:" + aSearchString);
		System.out.println("Search string:" + aURL);
		lastWebLink = new AWebLink(aSearchString, aURL);
	}
	@Override
	public void recordCommand(ICommand newCommand) {
		System.out.println("Extension**New User/Prediction Command:" + newCommand);		
	}
	@Override
	public void start() {
		System.out.println("Extension**Difficulty Prediction Started");		
	}
	@Override
	public void stop() {
		System.out.println("Extension**Difficulty Prediction Stopped");			
	}
	@Override
	public void newRatios(RatioFeatures newVal) {
		System.out.println("Extension**New Ratios:" + newVal);		
	}
	public void startTimeStamp(long aStartTimeStamp) {
		System.out.println("Extension**Difficulty Prediction Started");		

	}
	public static void main (String[] args) {
		analyzer = new AnAnalyzer();
		analyzerProcessor = new AnAnalyzerProcessor();
		analyzer.addAnalyzerListener(analyzerProcessor);
		OEFrame frame = ObjectEditor.edit(analyzer);
		frame.setSize(550, 200);
		
	}

}
