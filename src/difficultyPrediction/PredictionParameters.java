package difficultyPrediction;

import analyzer.Resettable;
import util.annotations.Column;
import util.annotations.Row;
import util.models.PropertyListenerRegisterer;

public interface PredictionParameters extends Resettable, PropertyListenerRegisterer {
	
	public int getSegmentLength() ;
	public void setSegmentLength(int newVal);
	public int getStartupLag() ;
	public void setStartupLag(int startupLag) ;
	public int getStatusAggregated() ;
	public void setStatusAggregated(int statusesAggregated) ;

}
