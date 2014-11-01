package analyzer.extension;

import difficultyPrediction.DifficultyPredictionEventListener;
import difficultyPrediction.extension.APrintingDifficultyPredictionListener;
import difficultyPrediction.featureExtraction.RatioFeatures;
import edu.cmu.scs.fluorite.commands.ICommand;
import bus.uigen.OEFrame;
import bus.uigen.ObjectEditor;
import analyzer.AnAnalyzer;
import analyzer.Analyzer;
import analyzer.AnalyzerListener;

public class AnAnalyzerProcessor extends APrintingDifficultyPredictionListener implements AnalyzerProcessor{
	static Analyzer analyzer;
	static AnalyzerProcessor analyzerProcessor;
	
	
	@Override
	public void newParticipant(String anId) {
		System.out.println("Extension**New Participant:" + anId);
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
