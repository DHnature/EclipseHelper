package analyzer.ui;

import analyzer.AnAnalyzer;
import analyzer.AnalyzerFactory;
import bus.uigen.OEFrame;
import bus.uigen.ObjectEditor;
import difficultyPrediction.DifficultyPredictionSettings;

public class AVisuallyReplayingAnalyzer {
	public static void main (String[] args) {


		//		Analyzer analyzer = new AnAnalyzer();
		DifficultyPredictionSettings.setReplayMode(true);

		OEFrame frame = ObjectEditor.edit(AnalyzerFactory.getSingleton());
		AnAnalyzer.getInstance().getAnalyzerParameters().setReplayOutputFiles(true);
		AnAnalyzer.getInstance().getAnalyzerParameters().visualizePredictions();
		AnAnalyzer.getInstance().loadDirectory();
		frame.setSize(500, 250);

	}

}
