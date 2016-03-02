package difficultyPrediction.metrics;

import java.util.ArrayList;
import java.util.List;

import difficultyPrediction.featureExtraction.RatioFeatures;
import edu.cmu.scs.fluorite.commands.ICommand;

public interface RatioCalculator {

	public abstract boolean isDebugEvent(ICommand event);

	public abstract boolean isInsertOrEditEvent(ICommand event);

	public abstract boolean isNavigationEvent(ICommand event);

	public abstract boolean isFocusEvent(ICommand event);

	public abstract boolean isAddRemoveEvent(ICommand event);

	public abstract ArrayList<Double> computeMetrics(List<ICommand> userActions);

	public abstract ArrayList<Integer> getPercentageData(
			List<ICommand> userActions);

	public abstract String getFeatureName(ICommand myEvent);

	RatioFeatures computeRatioFeatures(List<ICommand> userActions);

	RatioFeatures computeFeatures(List<ICommand> userActions);

}