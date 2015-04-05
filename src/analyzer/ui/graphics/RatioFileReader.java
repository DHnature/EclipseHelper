package analyzer.ui.graphics;

import java.util.List;

import util.models.PropertyListenerRegistrar;

public interface RatioFileReader extends PropertyListenerRegistrar {
	public static final String START_RATIOS = "startRatioFeatures";
	public static final String END_RATIOS = "endRatioFeatures";
	public static final String NEW_RATIO = "newRatioFeatures";

	
	public void readFile(String fileName);
	public String getPath();
//	List<RatioFileComponents> getRatioFeaturesList();

}
