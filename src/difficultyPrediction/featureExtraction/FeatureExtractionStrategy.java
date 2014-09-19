package difficultyPrediction.featureExtraction;

import java.util.List;

import edu.cmu.scs.fluorite.commands.ICommand;

public interface FeatureExtractionStrategy {
	public void performFeatureExtraction(List<ICommand> actions, FeatureExtractor featureExtractor);
}
