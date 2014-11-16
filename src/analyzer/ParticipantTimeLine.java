package analyzer;

import java.util.List;

public interface ParticipantTimeLine {

	public abstract List<Double> getInsertionList();

	public abstract void setInsertionList(List<Double> insertionList);

	public abstract List<Double> getDeletionList();

	public abstract void setDeletionList(List<Double> deletionList);

	public abstract List<Double> getDebugList();

	public abstract void setDebugList(List<Double> debugList);

	public abstract List<Double> getNavigationList();

	public abstract void setNavigationList(List<Double> navigationList);

	public abstract List<Double> getFocusList();

	public abstract void setFocusList(List<Double> focusList);

	public abstract List<Double> getRemoveList();

	public abstract void setRemoveList(List<Double> removeList);

	public abstract List<Long> getTimeStampList();

	public abstract void setTimeStampList(List<Long> timeStampList);

	public abstract List<Integer> getPredictionList();

	public abstract void setPredictionList(List<Integer> predictionList);

	public abstract List<Integer> getPredictionCorrection();

	public abstract void setPredictionCorrection(
			List<Integer> predictionCorrection);

	public abstract List<WebLink> getWebLinks();

	public abstract void setWebLinks(List<WebLink> webLinks);

}