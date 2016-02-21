package difficultyPrediction;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import javax.swing.JFileChooser;

import config.HelperConfigurationManagerFactory;
import difficultyPrediction.metrics.RatioScheme;
import difficultyPrediction.predictionManagement.ClassifierSpecification;
import difficultyPrediction.predictionManagement.OversampleSpecification;
import util.annotations.Column;
import util.annotations.ComponentWidth;
import util.annotations.Label;
import util.annotations.Row;
import util.annotations.Visible;
import bus.uigen.OEFrame;
import bus.uigen.ObjectEditor;
import bus.uigen.models.AFileSetterModel;
import bus.uigen.models.FileSetterModel;

public class APredictionParameters implements PredictionParameters {
	static PredictionParameters instance;
	int segmentLength = 25;
	int startupLag = 50;
	int statusAggregated = 5;
	ClassifierSpecification classifierSpecification;
	OversampleSpecification oversampleSpecification;
	RatioScheme ratioScheme;

	String arffFileName;
	static OEFrame predictionFrame;
	PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(
			this);

	// FileSetterModel arffFileName;
	public APredictionParameters() {
		// arffFileName = new AFileSetterModel(JFileChooser.FILES_ONLY);
		// arffFileName.setText(HelperConfigurationManagerFactory.getSingleton().getARFFFileName());
		arffFileName = HelperConfigurationManagerFactory.getSingleton()
				.getARFFFileName();
	}

	// @Row(1)
	@Row(0)
	@Column(0)
	@ComponentWidth(30)
	public int getStartupLag() {
		return startupLag;
	}

	public void setStartupLag(int newValue) {
		int oldValue = startupLag;
		this.startupLag = newValue;
		propertyChangeSupport.firePropertyChange("StartupLag", oldValue,
				newValue);

	}

	@Row(0)
	@Column(1)
	@ComponentWidth(30)
	public int getSegmentLength() {
		return segmentLength;
	}

	public void setSegmentLength(int newVal) {
		int oldValue = segmentLength;
		this.segmentLength = newVal;
		propertyChangeSupport.firePropertyChange("SegmentLength", oldValue,
				newVal);

	}

	@Row(0)
	@Column(2)
	@ComponentWidth(30)
	public int getStatusAggregated() {
		return statusAggregated;
	}

	public void setStatusAggregated(int newValue) {
		int oldValue = statusAggregated;
		this.statusAggregated = newValue;
		propertyChangeSupport.firePropertyChange("StatusAggregated", oldValue,
				newValue);
	}

	@Visible(false)
	public static PredictionParameters getInstance() {
		if (instance == null) {
			instance = new APredictionParameters();
		}
		return instance;
	}

	public static void createUI() {
		// OEFrame predictionFrame = ObjectEditor.edit(getInstance())
		if (predictionFrame != null) {
			instance.reset();
			return;

		}
		predictionFrame = ObjectEditor.edit(getInstance());

		predictionFrame.setSize(450, 120);
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		System.err.println("Reset not implemented");

	}

	@Override
	public void addPropertyChangeListener(PropertyChangeListener arg0) {
		propertyChangeSupport.addPropertyChangeListener(arg0);
	}

	// @Visible(false)
	@Row(2)
	@Column(0)
	@ComponentWidth(250)
	@Label("Model:")
	// @Override
	// public FileSetterModel getARFFFileName() {
	// // if (arffFileName == null) {
	// // arffFileName =
	// HelperConfigurationManagerFactory.getSingleton().getARFFFileName();
	// // }
	// return arffFileName;
	// }
	@Override
	public String getARFFFileName() {
		// if (arffFileName == null) {
		// arffFileName =
		// HelperConfigurationManagerFactory.getSingleton().getARFFFileName();
		// }
		return arffFileName;
	}

	// @Visible(false)
	@Row(1)
	@Column(0)
	@ComponentWidth(100)
	@Label("Classifier:")
	@Override
	public ClassifierSpecification getClassifierSpecification() {
		if (classifierSpecification == null) {
			classifierSpecification = HelperConfigurationManagerFactory
					.getSingleton().getClassifierSpecification();
		}
		return classifierSpecification;
	}

	// @Visible(false)
	@Row(1)
	@Column(1)
	@ComponentWidth(100)
	@Label("Oversampling:")
	@Override
	public OversampleSpecification getOversampleSpecification() {
		if (oversampleSpecification == null) {
			oversampleSpecification = HelperConfigurationManagerFactory
					.getSingleton().getOversampleSpecification();
		}
		return oversampleSpecification;
	}

	@Override
	public void setClassifierSpecification(ClassifierSpecification newVal) {
		classifierSpecification = newVal;
	}

	public void setOversampleSpecification(OversampleSpecification newVal) {
		oversampleSpecification = newVal;
	}
	@Row(1)
	@Column(3)
	@ComponentWidth(40)
	@Label("Ratios:")
	public RatioScheme getRatioScheme() {
		if (ratioScheme == null) {
			ratioScheme = HelperConfigurationManagerFactory
					.getSingleton().getRatioScheme();
		}
		return ratioScheme;
//		return ratioScheme;
	}

	public void setRatioScheme(RatioScheme ratioScheme) {
		this.ratioScheme = ratioScheme;
	}

}
