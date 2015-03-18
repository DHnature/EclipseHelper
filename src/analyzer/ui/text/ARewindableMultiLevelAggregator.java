package analyzer.ui.text;

import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

import trace.difficultyPrediction.AggregatePredictionChanged;
import trace.difficultyPrediction.PredictionChanged;
import difficultyPrediction.AMultiLevelAggregator;
import difficultyPrediction.MultiLevelAggregator;
import difficultyPrediction.featureExtraction.RatioFeatures;
import difficultyPrediction.metrics.RatioCalculator;
import edu.cmu.scs.fluorite.commands.ICommand;
import edu.cmu.scs.fluorite.model.StatusConsts;

public class ARewindableMultiLevelAggregator extends AMultiLevelAggregator {
	protected List<List<ICommand>> allCommands = new ArrayList();
	protected List<RatioFeatures> allFeatures = new ArrayList();
	protected List<String> allPredictions = new ArrayList();
	protected List<String> allAggregatedStatuses = new ArrayList();
	protected int lastFeatureIndex;
	protected boolean playBack;
	
	
	@Override
	public void newCommand(ICommand newCommand) {
		allCommands.get(lastFeatureIndex).add(newCommand);
		if (!playBack) super.newCommand(newCommand);		
	}
	@Override
	public void newStatus(String aStatus) {
		allPredictions.add(aStatus);
		if (lastFeatureIndex == 0)
			allAggregatedStatuses.add(StatusConsts.INDETERMINATE);
		else
			allAggregatedStatuses.add(allAggregatedStatuses.get(lastFeatureIndex - 1));
		if (!playBack) super.newStatus(aStatus); 
	}
	@Override
//	@Visible(false)
	public void newRatios(RatioFeatures newVal) {
		features.add(newVal);
		lastFeatureIndex++;
		if (!playBack) super.newRatios(newVal);
		

	}
	public void newAggregatedStatus(String aStatus) {
		allAggregatedStatuses.set(lastFeatureIndex, aStatus);
		if (!playBack) super.newAggregatedStatus(aStatus);
		
	}

}
