package analyzer;

public interface AnalyzerListener extends BrowseHistoryListener{
	void newParticipant(String anId, String aFolder);
//	void newFeatures(RatioFeatures aFeatures);
	public void startTimeStamp(long aStartTimeStamp);

}
