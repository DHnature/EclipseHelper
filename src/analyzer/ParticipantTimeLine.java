package analyzer;

import java.util.List;

public interface ParticipantTimeLine {
	
	public List<Double> getEditList();
	
	public void setEditList(List<Double> editList);

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

	public abstract List<Integer> getPredictions();

	public abstract void setPredictions(List<Integer> predictionList);

	public abstract List<Integer> getPredictionCorrections();

	public abstract void setPredictionCorrections(
			List<Integer> predictionCorrection);

	public abstract List<List<WebLink>> getWebLinks();

	public abstract void setWebLinks(List<List<WebLink>> webLinks);
	public int getIndexBefore(long aTimeStamp) ;

	StringBuffer toText();


}