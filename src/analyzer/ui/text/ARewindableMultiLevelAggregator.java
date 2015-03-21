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
//	protected List<ICommand> allCommands = new ArrayList();

	protected List<RatioFeatures> allFeatures = new ArrayList();
	protected List<String> allPredictions = new ArrayList();
	protected List<String> allAggregatedStatuses = new ArrayList();
	protected int nextFeatureIndex; // does not change during replay
//	protected int previousAggregatedIndex;
//	protected int currentAggregatedStatus;
//	protected int previousFeatureIndex;
	protected String currentAggregateStatus = StatusConsts.INDETERMINATE;
	protected int currentFeatureIndex; // changes during replay
	protected boolean playBack;
	
	public ARewindableMultiLevelAggregator() {
		addRatioBasedSlots();
	}
	
	@Override
	@Visible(false)
	public void newCommand(ICommand newCommand) {
		if (!playBack) {
		allCommands.get(nextFeatureIndex).add(newCommand);
//			allCommands.add(newCommand);
//		} else {
		 super.newCommand(newCommand);
		}
		
	}
	@Override
	@Visible(false)
	public void newStatus(String aStatus) {
		if (!playBack) {
		   allPredictions.set(nextFeatureIndex - 1, aStatus); // next feature index was bumpted by new feature
//		   allAggregatedStatuses.add(null);
//		   if (lastFeatureIndex == 0)
//			  allAggregatedStatuses.add(StatusConsts.INDETERMINATE);
//		   else
//			allAggregatedStatuses.add(allAggregatedStatuses.get(lastFeatureIndex - 1));
//		} else {
//		if (!playBack) {
			super.newStatus(aStatus); 
			propagatePre(); // to reset back and forward
		}
				
	}
    void propagatePre() {
		propertyChangeSupport.firePropertyChange("this", null, this);
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
		if (!playBack) {
		allFeatures.set(nextFeatureIndex, newVal);
		currentFeatureIndex = nextFeatureIndex;
		addRatioBasedSlots();
		nextFeatureIndex++;

//		} else {
//		if (!playBack) { 
			super.newRatios(newVal);	
		}
			
//		}
	}
	@Visible(false)
	public void newAggregatedStatus(String aStatus) {
		if (!playBack) {
		  allAggregatedStatuses.set(nextFeatureIndex - 1, aStatus); // index was bumped
//		} else {
		super.newAggregatedStatus(aStatus);
		}		
	}
	
	public boolean preBack() {
		return currentFeatureIndex > 0;
	}
	public void back() {
		if (!preBack()) return;
		setNewWindow(  currentFeatureIndex - 1);
	}
	public boolean preForward() {
		return currentFeatureIndex < nextFeatureIndex;
	}
	public void forward() {
		if (!preForward()) return;
		setNewWindow (currentFeatureIndex + 1);
	}
	
	void setNewWindow(int newVal) {
		playBack = true;
		currentFeatureIndex = newVal;
		newWindow();
	}
	
	int previousAggregateIndex() {	
		int retVal = currentFeatureIndex - 1;
		while (retVal >= 0) {
			if (allAggregatedStatuses.get(retVal) != null) return retVal;
			retVal--;
		}
		return Math.max(retVal, 0);
	}
	
//	void setAggregateStatus () {
//		currentAggregateStatus = StatusConsts.INDETERMINATE;
//		for (int aFeatureIndex = currentFeatureIndex; aFeatureIndex >= 0; aFeatureIndex-- ) {
//			if (allAggregatedStatuses.get(aFeatureIndex) != null) {
//				currentAggregateStatus = allAggregatedStatuses.get(aFeatureIndex);
//				return;
//			}
//		}
//	}
	
	
	@Visible(false)
	public void resetWindowData() {
//		features.clear();
//		predictions.clear();
//		commandsBuffer.setLength(0);
		super.reset();
	}	
//	public void computeWindowBounds() {
//		previousAggregatedIndex = previousAggregateIndex();
//	}
	
    @Visible(false)
	public void fireWindowEvents() {
//		String anAggregatedStatus = StatusConsts.INDETERMINATE;
		for (int featureIndex = 0; featureIndex <= currentFeatureIndex; featureIndex++) {
			for (ICommand aCommand:allCommands.get(featureIndex)) {
				super.newCommand(aCommand);				
			}
			super.newRatios(allFeatures.get(featureIndex));
			super.newStatus(allPredictions.get(featureIndex));
			if (allAggregatedStatuses.get(featureIndex) != null)
				super.newAggregatedStatus (allAggregatedStatuses.get(featureIndex));
		}		
	}
	@Visible(false)
	public void newWindow() {
//		setAggregateStatus();
		resetWindowData();		
//		computeWindowBounds();
		fireWindowEvents();	
		propagatePre();
	}
	
	
	public boolean preLive() {
		return playBack;
	}
	
	public void live() {		
		end(); //play back all past events
		playBack = false;
	}
	public void start() {
		setNewWindow (0);
	}
	public void end() {
		setNewWindow (nextFeatureIndex - 1);
	}

}
