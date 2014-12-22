package difficultyPrediction;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextArea;

import bus.uigen.OEFrame;
import bus.uigen.ObjectEditor;
import trace.difficultyPrediction.AggregatePredictionChanged;
import trace.difficultyPrediction.PredictionChanged;
import util.annotations.ComponentHeight;
import util.annotations.PreferredWidgetClass;
import util.annotations.Row;
import util.annotations.Visible;
import difficultyPrediction.featureExtraction.RatioFeatures;
import difficultyPrediction.featureExtraction.RatioFeaturesListener;
import difficultyPrediction.metrics.APercentageCalculator;
import difficultyPrediction.metrics.RatioCalculator;
import difficultyPrediction.statusManager.StatusListener;
import edu.cmu.scs.fluorite.commands.ICommand;

public class AMultiLevelAggregator implements MultiLevelAggregator{
	static OEFrame oeFrame;

	List<ICommand> commands = new ArrayList();
	List<RatioFeatures> features = new ArrayList();
	List<String> predictions = new ArrayList();
	String aggregatedStatus = "";
	static RatioCalculator ratioCalculator;
	static MultiLevelAggregator instance;
	StringBuffer commandsBuffer = new StringBuffer();
	StringBuffer ratiosBuffer = new StringBuffer();
	StringBuffer predictionsBuffer = new StringBuffer();
	PropertyChangeSupport propertyChangeSupport;
	
	
	public AMultiLevelAggregator() {
//		DifficultyRobot.getInstance().addRatioFeaturesListener(this);
		DifficultyRobot.getInstance().addStatusListener(this);
		DifficultyRobot.getInstance().addPluginEventEventListener(this);
		DifficultyRobot.getInstance().addRatioFeaturesListener(this);
		ratioCalculator = APercentageCalculator.getInstance();
		propertyChangeSupport = new PropertyChangeSupport(this);

	}

	@Override
	public void newCommand(ICommand newCommand) {
		commands.add(newCommand);
		commandsBuffer.append(toClassifiedString(newCommand) + "\n");
		propertyChangeSupport.firePropertyChange("Segment", "", commandsBuffer.toString());

		
	}

	@Override
	public void commandProcessingStarted() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void commandProcessingStopped() {
		// TODO Auto-generated method stub
		
	}
	String oldStatus = "";
	@Override
	public void newStatus(String aStatus) {
		if (!aStatus.equals(oldStatus)) {
			PredictionChanged.newCase(aStatus, getRatios(), this);
			

			oldStatus = aStatus;
		}
		predictions.add(aStatus);
		predictionsBuffer.append(aStatus + "\n");
		propertyChangeSupport.firePropertyChange("Predictions", "", predictionsBuffer.toString());
		
	}
	String oldAggregateStatus = "";
	@Override
	public void newAggregatedStatus(String aStatus) {
		if (!aStatus.equals(oldAggregateStatus)) {
			AggregatePredictionChanged.newCase(aStatus, getRatios(), this);
			aggregatedStatus = aStatus;
			propertyChangeSupport.firePropertyChange("AggregatedStatus", oldAggregateStatus, aStatus);
			oldAggregateStatus = aStatus;		

		}
		predictions.clear();
		predictionsBuffer.setLength(0);
		ratiosBuffer.setLength(0);
		commandsBuffer.setLength(0);
		
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
//	@Visible(false)
	public void newRatios(RatioFeatures newVal) {
		features.add(newVal);
		commands.clear();	
//		commandsBuffer.setLength(0);
		commandsBuffer.append("\n");
		ratiosBuffer.append(newVal + "\n");
		propertyChangeSupport.firePropertyChange("Ratios", "", ratiosBuffer.toString());

	}
	@Row(0)
	public String getAggregatedStatus() {
		return aggregatedStatus;
	}
	
	public  String toClassifiedString (ICommand aCommand) {
		String featureName = ratioCalculator.getFeatureName(aCommand);
		return featureName + " (" + aCommand + " )";
	}
	@Row(1)
	@PreferredWidgetClass(JTextArea.class)
	@ComponentHeight(100)
	public String getPredictions() {
		return predictionsBuffer.toString();
	}
	@Row(2)
	@PreferredWidgetClass(JTextArea.class)
	@ComponentHeight(100)

	public String getRatios() {
		return commandsBuffer.toString();
	}
	@Row(3)
	@PreferredWidgetClass(JTextArea.class)
	@ComponentHeight(200)
	public String getSegment() {
		return commandsBuffer.toString();
	}
	
	
	
	@Visible(false)
	public static MultiLevelAggregator getInstance() {
		
		if (instance == null) {
			instance = new AMultiLevelAggregator();
			
		}
		return instance;
	}
	
	public static void createUI() {
		if (oeFrame != null) {
			getInstance().reset();
			return;
		}
		 oeFrame = ObjectEditor.edit(getInstance());
		oeFrame.setSize(700, 500);
	}

	public static void main (String[] args) {
//		ObjectEditor.edit(AMultiLevelAggregator.getInstance());
		createUI();
	}

	@Override
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		propertyChangeSupport.addPropertyChangeListener(listener);
		
	}
	@Override
	public void reset() {
		// TODO Auto-generated method stub
		System.err.println("Reset not implemented");
		
	}
	

}
