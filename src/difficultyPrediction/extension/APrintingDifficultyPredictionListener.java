package difficultyPrediction.extension;

import difficultyPrediction.DifficultyPredictionEventListener;
import difficultyPrediction.featureExtraction.RatioFeatures;
import edu.cmu.scs.fluorite.commands.ICommand;

public class APrintingDifficultyPredictionListener implements DifficultyPredictionEventListener{

	@Override
	public void recordCommand(ICommand newCommand) {
		System.out.println("Extension**New User/Prediction Command:" + newCommand);		
	}
	@Override
	public void start() {
		System.out.println("Extension**Difficulty Prediction Started");		
	}
	public void startTimeStamp(long aStartTimeStamp) {
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

}
