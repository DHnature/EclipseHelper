package difficultyPrediction;

import bus.uigen.models.FileSetterModel;
import difficultyPrediction.metrics.CommandClassificationScheme;
import difficultyPrediction.predictionManagement.ClassifierSpecification;
import difficultyPrediction.predictionManagement.OversampleSpecification;
import util.models.PropertyListenerRegisterer;
import analyzer.Resettable;

public interface PredictionParameters extends Resettable, PropertyListenerRegisterer {
	public int getSegmentLength() ;
	public void setSegmentLength(int newVal);
	public int getStartupLag() ;
	public void setStartupLag(int startupLag) ;
	public int getStatusAggregated() ;
	public void setStatusAggregated(int statusesAggregated);
	void setClassifierSpecification(ClassifierSpecification newVal);
	OversampleSpecification getOversampleSpecification();
	ClassifierSpecification getClassifierSpecification();
	CommandClassificationScheme getCommandClassificationScheme();
//	FileSetterModel getARFFFileName();
	String getARFFFileName();
	void setCommandClassificationScheme(CommandClassificationScheme ratioScheme);

}
