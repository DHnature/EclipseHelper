package difficultyPrediction;

import java.util.List;

import util.models.LabelBeanModel;
import util.models.PropertyListenerRegistrar;
import analyzer.Resettable;
import difficultyPrediction.featureExtraction.BarrierListener;
import difficultyPrediction.featureExtraction.RatioFeaturesListener;
import difficultyPrediction.featureExtraction.WebLinkListener;
import difficultyPrediction.statusManager.StatusListener;

public interface MultiLevelAggregator extends
	RatioFeaturesListener, WebLinkListener, StatusListener, 
	DifficultyPredictionEventListener, PropertyListenerRegistrar, Resettable, BarrierListener{

	String getManualStatus();

//	void setManualStatus(String newVal);

	String getManualBarrier();

//	void setBarrier(String newVal);

	String getAggregatedStatus();

	String getPredictions();

	String getRatios();

	List<LabelBeanModel> getWebLinks();

}
