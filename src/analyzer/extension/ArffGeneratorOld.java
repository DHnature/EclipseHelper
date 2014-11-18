package analyzer.extension;

import analyzer.AnalyzerListener;
import difficultyPrediction.DifficultyPredictionEventListener;

/**Arff file generator*/
public interface ArffGeneratorOld extends AnalyzerListener, DifficultyPredictionEventListener{

	/**
	 * Set the arff file path and name, defaults to data/userstudyx.arff where x is a number that does not cause a collision with an
	 * existing file.
	 *<p>
	 *<b> Include the .arff extension for paths</b>
	 */
	void setArffFilePath(String path);
	
	
	
}
