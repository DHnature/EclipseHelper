package analyzer.extension;

import difficultyPrediction.DifficultyPredictionEventListener;
import difficultyPrediction.featureExtraction.RatioFeatures;
import edu.cmu.scs.fluorite.commands.ICommand;
import bus.uigen.OEFrame;
import bus.uigen.ObjectEditor;
import analyzer.AnAnalyzer;
import analyzer.Analyzer;
import analyzer.AnalyzerListener;

public class AnAnalyzerProcessor implements AnalyzerProcessor{
	static Analyzer analyzer;
	static AnalyzerProcessor analyzerProcessor;
	
	@Override
	public void recordCommand(ICommand newCommand) {
		System.out.println("New Command:" + newCommand);

		
	}
	@Override
	public void start() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void stop() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void newRatios(RatioFeatures newVal) {
		System.out.println("New Ratios:" + newVal);

		
	}
	@Override
	public void newParticipant(String anId) {
		System.out.println("New Participant:" + anId);
		analyzer.getDifficultyEventProcessor().addDifficultyPredictionEventListener(this);		
		
	}
	public static void main (String[] args) {
		analyzer = new AnAnalyzer();
		analyzerProcessor = new AnAnalyzerProcessor();
		analyzer.addAnalyzerListener(analyzerProcessor);
		OEFrame frame = ObjectEditor.edit(analyzer);
		frame.setSize(550, 200);
		
	}

}
