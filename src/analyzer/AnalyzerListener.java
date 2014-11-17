package analyzer;

public interface AnalyzerListener extends BrowseHistoryListener{
	void newParticipant(String anId);
//	void newFeatures(RatioFeatures aFeatures);
	public void startTimeStamp(long aStartTimeStamp);

}
