package difficultyPrediction;

import util.models.PropertyListenerRegisterer;
import analyzer.Resettable;

public interface PredictionParameters extends Resettable, PropertyListenerRegisterer {
	
	public int getSegmentLength() ;
	public void setSegmentLength(int newVal);
	public int getStartupLag() ;
	public void setStartupLag(int startupLag) ;
	public int getStatusAggregated() ;
	public void setStatusAggregated(int statusesAggregated) ;

}
