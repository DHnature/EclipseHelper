package difficultyPrediction;

import java.util.ArrayList;
import java.util.List;

import difficultyPrediction.featureExtraction.RatioFeatures;
import difficultyPrediction.featureExtraction.RatioFeaturesListener;
import difficultyPrediction.statusManager.StatusListener;
import edu.cmu.scs.fluorite.commands.ICommand;

public class AMultiLevelAggregator implements RatioFeaturesListener, StatusListener, DifficultyPredictionEventListener{
	List<ICommand> commands = new ArrayList();
	List<RatioFeatures> features = new ArrayList();
	List<String> predictions = new ArrayList();
	
	public AMultiLevelAggregator() {
//		DifficultyRobot.getInstance().addRatioFeaturesListener(this);
		DifficultyRobot.getInstance().addStatusListener(this);
		ADifficultyPredictionPluginEventProcessor.getInstance().addDifficultyPredictionEventListener(this);	}

	@Override
	public void newCommand(ICommand newCommand) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void commandProcessingStarted() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void commandProcessingStopped() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void newStatus(String aStatus) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void newAggregatedStatus(String aStatus) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void newStatus(int aStatus) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void newAggregatedStatus(int aStatus) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void newRatios(RatioFeatures newVal) {
		// TODO Auto-generated method stub
		
	}
	

}
