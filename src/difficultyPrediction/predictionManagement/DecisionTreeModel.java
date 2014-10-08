package difficultyPrediction.predictionManagement;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import weka.classifiers.trees.*;

public class DecisionTreeModel implements PredictionManagerStrategy {
	public APredictionManager predictionManager;

	private J48 j48Model = new J48();

	private boolean isj48ModelBuilt = false;

	private String WEKA_DATA_FILE_LOCATION = "data/userStudy2010.arff";


	public DecisionTreeModel(APredictionManager predictionManager) {
		this.predictionManager = predictionManager;
	}

	private void buildJ48Model() {
		weka.core.Instances isTrainingSet;
		URL url;

		try {
			url = new URL(edu.cmu.scs.fluorite.plugin.Activator.getDefault()
					.getDescriptor().getInstallURL(), WEKA_DATA_FILE_LOCATION);

			InputStream inputStream = url.openConnection().getInputStream();

			isTrainingSet = new weka.core.Instances(new BufferedReader(
					new InputStreamReader(inputStream)));
			isTrainingSet.setClassIndex(isTrainingSet.numAttributes() - 1);
			j48Model.buildClassifier(isTrainingSet);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void predictSituation(double editRatio, double debugRatio,
			double navigationRatio, double focusRatio, double removeRatio) {
		String predictedValue = "NO";
		try {
			// Declare five numeric attributes
			weka.core.Attribute searchPercentageAttribute = new weka.core.Attribute(
					"searchPercentage");
			weka.core.Attribute debugPercentageAttribute = new weka.core.Attribute(
					"debugPercentage");
			weka.core.Attribute focusPercentageAttribute = new weka.core.Attribute(
					"focusPercentage");
			weka.core.Attribute editPercentageAttribute = new weka.core.Attribute(
					"editPercentage");
			weka.core.Attribute removePercentageAttribute = new weka.core.Attribute(
					"removePercentage");

			// Declare the class attribute along with its values
			weka.core.FastVector fvClassVal = new weka.core.FastVector(2);
			fvClassVal.addElement("YES");
			fvClassVal.addElement("NO");
			weka.core.Attribute ClassAttribute = new weka.core.Attribute(
					"STUCK", fvClassVal);

			// Declare the feature vector
			// should be 6
			weka.core.FastVector fvWekaAttributes = new weka.core.FastVector(4);
			fvWekaAttributes.addElement(searchPercentageAttribute);
			fvWekaAttributes.addElement(debugPercentageAttribute);
			fvWekaAttributes.addElement(focusPercentageAttribute);
			fvWekaAttributes.addElement(editPercentageAttribute);
			fvWekaAttributes.addElement(removePercentageAttribute);
			fvWekaAttributes.addElement(ClassAttribute);

			// Create an empty training set
			weka.core.Instances isTestingSet = new weka.core.Instances("Rel",
					fvWekaAttributes, 10);

			// Set class index
			isTestingSet.setClassIndex(5);

			// Create the instance
			weka.core.Instance iExample = new weka.core.Instance(5);

			iExample.setValue(
					(weka.core.Attribute) fvWekaAttributes.elementAt(0),
					navigationRatio);
			iExample.setValue(
					(weka.core.Attribute) fvWekaAttributes.elementAt(1),
					debugRatio);
			iExample.setValue(
					(weka.core.Attribute) fvWekaAttributes.elementAt(2),
					focusRatio);
			iExample.setValue(
					(weka.core.Attribute) fvWekaAttributes.elementAt(3),
					editRatio);
			iExample.setValue(
					(weka.core.Attribute) fvWekaAttributes.elementAt(4),
					removeRatio);

			// add the instance
			isTestingSet.add(iExample);

			if (!isj48ModelBuilt) {
				long startTime = System.currentTimeMillis();
				buildJ48Model();
				isj48ModelBuilt = true;
				System.out.println(" Built J48 model in m:" + (System.currentTimeMillis() - startTime));

				
			}

			double predictedClass = j48Model.classifyInstance(isTestingSet
					.instance(0));
			predictedValue = isTestingSet.classAttribute().value(
					(int) predictedClass);
//			System.out.println("Predicted Value: " + predictedValue);
		} catch (Exception e) {
			e.printStackTrace();
		}

		predictionManager.onPredictionHandOff(predictedValue);
	}
}
