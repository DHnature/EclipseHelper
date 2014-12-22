package difficultyPrediction;

import util.annotations.Column;
import util.annotations.Row;

public interface PredictionParameters {
	
	public int getSegmentLength() ;
	public void setSegmentLength(int newVal);
	public int getStartupLag() ;
	public void setStartupLag(int startupLag) ;
	public int getStatusAggregated() ;
	public void setStatusAggregated(int statusesAggregated) ;

}
