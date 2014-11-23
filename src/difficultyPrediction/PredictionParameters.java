package difficultyPrediction;

import util.annotations.Column;
import util.annotations.Row;

public interface PredictionParameters {
	
	public int getSegmentLength() ;
	public void setSegmentLength(int newVal);

}
