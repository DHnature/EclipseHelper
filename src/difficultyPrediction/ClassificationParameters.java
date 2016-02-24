package difficultyPrediction;

import bus.uigen.models.FileSetterModel;
import difficultyPrediction.metrics.CommandClassificationSchemeName;
import difficultyPrediction.predictionManagement.ClassifierSpecification;
import difficultyPrediction.predictionManagement.OversampleSpecification;
import util.models.PropertyListenerRegisterer;
import analyzer.Resettable;

public interface ClassificationParameters extends PropertyListenerRegisterer {
	void setClassifierSpecification(ClassifierSpecification newVal);
	OversampleSpecification getOversampleSpecification();
	ClassifierSpecification getClassifierSpecification();
//	CommandClassificationSchemeName getCommandClassificationScheme();
//	FileSetterModel getARFFFileName();
	String getARFFFileName();
	void setARFFFileName(String newVal);

}
