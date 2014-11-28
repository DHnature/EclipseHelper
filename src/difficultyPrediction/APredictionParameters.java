package difficultyPrediction;

import bus.uigen.OEFrame;
import bus.uigen.ObjectEditor;
import util.annotations.Column;
import util.annotations.Row;

public class APredictionParameters implements PredictionParameters{
	static PredictionParameters instance;
	int segmentLength = 25;
	int startupLag = 50;
	int statusesAggregated = 5;
	
	@Row(1)
	@Column(0)
	public int getSegmentLength() {
		return segmentLength;
	}
	public void setSegmentLength(int newVal) {
		this.segmentLength = newVal;
	}
	@Row(0)
	@Column(0)
	public int getStartupLag() {
		return startupLag;
	}
	public void setStartupLag(int startupLag) {
		this.startupLag = startupLag;
	}
	@Row(2)
	@Column(0)
	public int getStatusesAggregated() {
		return statusesAggregated;
	}
	public void setStatusesAggregated(int statusesAggregated) {
		this.statusesAggregated = statusesAggregated;
	}
	public static PredictionParameters getInstance() {
		if (instance == null) {
			instance = new APredictionParameters();
		}
		return instance;
	}
	public static void createUI() {
		OEFrame predictionFrame = ObjectEditor.edit(getInstance());
		predictionFrame.setSize(300, 200);
	}

}
