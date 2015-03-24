package difficultyPrediction;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import util.annotations.Column;
import util.annotations.ComponentWidth;
import util.annotations.Row;
import bus.uigen.OEFrame;
import bus.uigen.ObjectEditor;

public class APredictionParameters implements PredictionParameters{
	static PredictionParameters instance;
	int segmentLength = 25;
	int startupLag = 50;
	int statusAggregated = 5;
	static OEFrame predictionFrame;
	PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
	
//	@Row(1)
	@Row(0)
	@Column(1)
	@ComponentWidth(30)
	public int getSegmentLength() {
		return segmentLength;
	}
	public void setSegmentLength(int newVal) {
		int oldValue = segmentLength;
		this.segmentLength = newVal;
		propertyChangeSupport.firePropertyChange("SegmentLength", oldValue, newVal);
		
	}
	@Row(0)
	@Column(0)
	@ComponentWidth(30)
	public int getStartupLag() {
		return startupLag;
	}
	public void setStartupLag(int newValue) {
		int oldValue = startupLag;
		this.startupLag = newValue;
		propertyChangeSupport.firePropertyChange("StartupLag", oldValue, newValue);

	}
	@Row(0)
	@Column(2)
	@ComponentWidth(30)
	public int getStatusAggregated() {
		return statusAggregated;
	}
	public void setStatusAggregated(int newValue) {
		int oldValue = statusAggregated;
		this.statusAggregated = newValue;
		propertyChangeSupport.firePropertyChange("StatusAggregated", oldValue, newValue);
	}
	public static PredictionParameters getInstance() {
		if (instance == null) {
			instance = new APredictionParameters();
		}
		return instance;
	}
	
	public static void createUI() {
//		OEFrame predictionFrame = ObjectEditor.edit(getInstance())
		if (predictionFrame != null) {
			instance.reset();
			return;
			
		}
		predictionFrame = ObjectEditor.edit(getInstance());

		predictionFrame.setSize(450, 120);
	}
	@Override
	public void reset() {
		// TODO Auto-generated method stub
		System.err.println("Reset not implemented");
		
	}
	@Override
	public void addPropertyChangeListener(PropertyChangeListener arg0) {
		propertyChangeSupport.addPropertyChangeListener(arg0);
	}

}
