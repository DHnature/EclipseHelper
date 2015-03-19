package analyzer.ui.text;

import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

import trace.difficultyPrediction.AggregatePredictionChanged;
import trace.difficultyPrediction.PredictionChanged;
import util.annotations.Visible;
import difficultyPrediction.MultiLevelAggregator;
import difficultyPrediction.featureExtraction.RatioFeatures;
import difficultyPrediction.metrics.RatioCalculator;
import edu.cmu.scs.fluorite.commands.ICommand;
import edu.cmu.scs.fluorite.model.StatusConsts;

public class ARewindableMultiLevelAggregator extends AMultiLevelAggregator {
	protected List<List<ICommand>> allCommands = new ArrayList();
//	protected List<StringBuffer> allCommandBuffers = new ArrayList();

	protected List<RatioFeatures> allFeatures = new ArrayList();
	protected List<String> allPredictions = new ArrayList();
	protected List<String> allAggregatedStatuses = new ArrayList();
	protected int lastFeatureIndex;
	protected int previousAggregatedIndex;
	protected int currentAggregatedStatus;
	protected int currentFeatureIndex;
	protected boolean playBack;
	
	public ARewindableMultiLevelAggregator() {
		addRatioBasedSlots();
	}
	
	@Override
	@Visible(false)
	public void newCommand(ICommand newCommand) {
		allCommands.get(lastFeatureIndex).add(newCommand);
		if (!playBack) super.newCommand(newCommand);		
	}
	@Override
	@Visible(false)
	public void newStatus(String aStatus) {
		allPredictions.add(aStatus);
		if (lastFeatureIndex == 0)
			allAggregatedStatuses.add(StatusConsts.INDETERMINATE);
		else
			allAggregatedStatuses.add(allAggregatedStatuses.get(lastFeatureIndex - 1));
		if (!playBack) super.newStatus(aStatus); 
	}
	void addRatioBasedSlots() {	
		allFeatures.add(null);
		allCommands.add(new ArrayList());
		allPredictions.add(null);
		allAggregatedStatuses.add(null);		
	}
	@Override
	@Visible(false)
	public void newRatios(RatioFeatures newVal) {
		allFeatures.set(lastFeatureIndex, newVal);
		addRatioBasedSlots();
		lastFeatureIndex++;
		if (!playBack) super.newRatios(newVal);
	}
	@Visible(false)
	public void newAggregatedStatus(String aStatus) {
		allAggregatedStatuses.set(lastFeatureIndex, aStatus);
		if (!playBack) super.newAggregatedStatus(aStatus);
		
	}
	
	public boolean preBack() {
		return currentFeatureIndex > 0;
	}
	
	public void forward() {
		if (!preForward()) return;
		playBack = true;
		currentFeatureIndex = currentFeatureIndex + 1;
	}
	
	int previousAggregateIndex() {	
		int retVal = currentFeatureIndex - 1;
		while (retVal >= 0) {
			if (allAggregatedStatuses.get(retVal) != null) return retVal;
		}
		return Math.max(retVal, 0);
	}
	
	public boolean preForward() {
		return currentFeatureIndex < lastFeatureIndex;
	}
	
	public void resetWindowData() {
		features.clear();
		predictions.clear();
		commandsBuffer.setLength(0);
	}	
	public void computeWindowBounds() {
		previousAggregatedIndex = previousAggregateIndex();
	}
	
	public void fireWindowEvents() {
		String anAggregatedStatus = StatusConsts.INDETERMINATE;
		for (int featureIndex = previousAggregateIndex() + 1; featureIndex <= currentFeatureIndex; featureIndex++) {
			for (ICommand aCommand:allCommands.get(featureIndex)) {
				super.newCommand(aCommand);				
			}
			super.newRatios(allFeatures.get(featureIndex));
			super.newStatus(allPredictions.get(featureIndex));
			if (allAggregatedStatuses.get(featureIndex) != null)
				anAggregatedStatus = allAggregatedStatuses.get(featureIndex);
		}
		newAggregatedStatus(anAggregatedStatus);
		
	}
	
	public void newWindow() {
		resetWindowData();
		computeWindowBounds();
		fireWindowEvents();			
	}
	
	public void back() {
		playBack = true;
		currentFeatureIndex = Math.max(0, currentFeatureIndex - 1);
	}
	public boolean preLive() {
		return playBack;
	}
	public void live() {
		playBack = false;
		currentFeatureIndex = lastFeatureIndex;
	}

}
