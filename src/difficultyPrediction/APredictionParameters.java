package difficultyPrediction;

import util.annotations.Column;
import util.annotations.Row;

public class APredictionParameters implements PredictionParameters{
	static PredictionParameters instance;
	int segmentLength = 50;
	@Row(0)
	@Column(0)
	public int getSegmentLength() {
		return segmentLength;
	}
	public void setSegmentLength(int newVal) {
		this.segmentLength = newVal;
	}
	public static PredictionParameters getInstance() {
		if (instance == null) {
			instance = new APredictionParameters();
		}
		return instance;
	}

}
