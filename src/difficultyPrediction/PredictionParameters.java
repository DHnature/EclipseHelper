package difficultyPrediction;

import util.models.PropertyListenerRegistrar;
import analyzer.Resettable;

public interface PredictionParameters extends Resettable, PropertyListenerRegistrar {
	public int getSegmentLength() ;
	public void setSegmentLength(int newVal);
	public int getStartupLag() ;
	public void setStartupLag(int startupLag) ;
	public int getStatusAggregated() ;
	public void setStatusAggregated(int statusesAggregated);
}
