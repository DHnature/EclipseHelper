package difficultyPrediction.predictionManagement;

import java.util.HashMap;
import java.util.Map;

import weka.classifiers.Classifier;
import weka.classifiers.meta.AdaBoostM1;
import weka.classifiers.meta.Bagging;
import weka.classifiers.trees.J48;

public enum ClassifierSpecification {
	J48,
	ADABOOST,
	BAGGING;
	static Map<ClassifierSpecification, Classifier> specificationToClassifier = new HashMap();
	static {
		specificationToClassifier.put(J48, new J48());
		specificationToClassifier.put(ADABOOST, new AdaBoostM1());
		specificationToClassifier.put(BAGGING, new Bagging());		
	}
	public Classifier toClassifier() {
		return specificationToClassifier.get(this);
	}
	
}
