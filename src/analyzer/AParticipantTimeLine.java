package analyzer;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

public class AParticipantTimeLine implements ParticipantTimeLine {
	private List<Double> insertionList = new ArrayList<Double>();
	private List<Double> deletionList = new ArrayList<Double>();
	private List<Double> debugList = new ArrayList<Double>();
	private List<Double> navigationList = new ArrayList<Double>();
	private List<Double> focusList = new ArrayList<Double>();
	private List<Double> removeList = new ArrayList<Double>();
	private List<Long> timeStampList = new ArrayList<Long>();
	protected List<Integer> predictionList = new ArrayList<Integer>();
	protected List<Integer> predictionCorrections = new ArrayList<Integer>();
	protected List<List<WebLink>> webLinks = new ArrayList();
	public AParticipantTimeLine(List<Double> insertionList,
			List<Double> deletionList, List<Double> debugList,
			List<Double> navigationList, List<Double> focusList,
			List<Double> removeList, List<Long> timeStampList,
			List<Integer> predictionList, List<Integer> predictionCorrection,
			List<List<WebLink>> webLinks) {
		super();
		this.insertionList = insertionList;
		this.deletionList = deletionList;
		this.debugList = debugList;
		this.navigationList = navigationList;
		this.focusList = focusList;
		this.removeList = removeList;
		this.timeStampList = timeStampList;
		this.predictionList = predictionList;
		this.predictionCorrections = predictionCorrection;
		this.webLinks = webLinks;
	}
	public AParticipantTimeLine() {
		this(new ArrayList(), new ArrayList(), new ArrayList(), new ArrayList(), new ArrayList(), new ArrayList(), new ArrayList(), new ArrayList(), new ArrayList(), new ArrayList());
		
	}
	/* (non-Javadoc)
	 * @see analyzer.ParticipantTimeLine#getInsertionList()
	 */
	@Override
	public List<Double> getInsertionList() {
		return insertionList;
	}
	/* (non-Javadoc)
	 * @see analyzer.ParticipantTimeLine#setInsertionList(java.util.List)
	 */
	@Override
	public void setInsertionList(List<Double> insertionList) {
		this.insertionList = insertionList;
	}
	/* (non-Javadoc)
	 * @see analyzer.ParticipantTimeLine#getDeletionList()
	 */
	@Override
	public List<Double> getDeletionList() {
		return deletionList;
	}
	/* (non-Javadoc)
	 * @see analyzer.ParticipantTimeLine#setDeletionList(java.util.List)
	 */
	@Override
	public void setDeletionList(List<Double> deletionList) {
		this.deletionList = deletionList;
	}
	/* (non-Javadoc)
	 * @see analyzer.ParticipantTimeLine#getDebugList()
	 */
	@Override
	public List<Double> getDebugList() {
		return debugList;
	}
	/* (non-Javadoc)
	 * @see analyzer.ParticipantTimeLine#setDebugList(java.util.List)
	 */
	@Override
	public void setDebugList(List<Double> debugList) {
		this.debugList = debugList;
	}
	/* (non-Javadoc)
	 * @see analyzer.ParticipantTimeLine#getNavigationList()
	 */
	@Override
	public List<Double> getNavigationList() {
		return navigationList;
	}
	/* (non-Javadoc)
	 * @see analyzer.ParticipantTimeLine#setNavigationList(java.util.List)
	 */
	@Override
	public void setNavigationList(List<Double> navigationList) {
		this.navigationList = navigationList;
	}
	/* (non-Javadoc)
	 * @see analyzer.ParticipantTimeLine#getFocusList()
	 */
	@Override
	public List<Double> getFocusList() {
		return focusList;
	}
	/* (non-Javadoc)
	 * @see analyzer.ParticipantTimeLine#setFocusList(java.util.List)
	 */
	@Override
	public void setFocusList(List<Double> focusList) {
		this.focusList = focusList;
	}
	/* (non-Javadoc)
	 * @see anatlyzer.ParticipantTimeLine#getRemoveList()
	 */
	@Override
	public List<Double> getRemoveList() {
		return removeList;
	}
	/* (non-Javadoc)
	 * @see analyzer.ParticipantTimeLine#setRemoveList(java.util.List)
	 */
	@Override
	public void setRemoveList(List<Double> removeList) {
		this.removeList = removeList;
	}
	/* (non-Javadoc)
	 * @see analyzer.ParticipantTimeLine#getTimeStampList()
	 */
	@Override
	public List<Long> getTimeStampList() {
		return timeStampList;
	}
	/* (non-Javadoc)
	 * @see analyzer.ParticipantTimeLine#setTimeStampList(java.util.List)
	 */
	@Override
	public void setTimeStampList(List<Long> timeStampList) {
		this.timeStampList = timeStampList;
	}
	/* (non-Javadoc)
	 * @see analyzer.ParticipantTimeLine#getPredictionList()
	 */
	@Override
	public List<Integer> getPredictions() {
		return predictionList;
	}
	/* (non-Javadoc)
	 * @see analyzer.ParticipantTimeLine#setPredictionList(java.util.List)
	 */
	@Override
	public void setPredictions(List<Integer> predictionList) {
		this.predictionList = predictionList;
	}
	/* (non-Javadoc)
	 * @see analyzer.ParticipantTimeLine#getPredictionCorrection()
	 */
	@Override
	public List<Integer> getPredictionCorrections() {
		return predictionCorrections;
	}
	/* (non-Javadoc)
	 * @see analyzer.ParticipantTimeLine#setPredictionCorrection(java.util.List)
	 */
	@Override
	public void setPredictionCorrections(List<Integer> predictionCorrection) {
		this.predictionCorrections = predictionCorrection;
	}
	/* (non-Javadoc)
	 * @see analyzer.ParticipantTimeLine#getWebLinks()
	 */
	@Override
	public List<List<WebLink>> getWebLinks() {
		return webLinks;
	}
	/* (non-Javadoc)
	 * @see analyzer.ParticipantTimeLine#setWebLinks(java.util.List)
	 */
	@Override
	public void setWebLinks(List<List<WebLink>> webLinks) {
		this.webLinks = webLinks;
	}
	
	@Override
	public int getIndexBefore(long aTimeStamp) {
		for (int anIndex = 0; anIndex < timeStampList.size(); anIndex++ ) {
			long aSavedTimeStamp = timeStampList.get(anIndex);
			if (aSavedTimeStamp > aTimeStamp) {
				return anIndex > 0? anIndex-1:0;
			}
		}
		return timeStampList.size() -1; 
	}
	@Override
	public StringBuffer toText() {
		StringBuffer aStringBuffer = new StringBuffer(4096);
		for (int index = 0; index < timeStampList.size(); index++) {
			long absoluteTime = timeStampList.get(index);
			DateTime dateTime = new DateTime(absoluteTime);
			String timeStampString = dateTime.toString("MM-dd-yyyy H:mm:ss");
			aStringBuffer.append(timeStampString  + ", ");
			aStringBuffer.append(insertionList.get(index) + ", ");
			aStringBuffer.append(deletionList.get(index) + ", ");
			aStringBuffer.append(debugList.get(index) + ", ");
			aStringBuffer.append(navigationList.get(index) + ", ");
			aStringBuffer.append(focusList.get(index) + ", ");
			aStringBuffer.append(removeList.get(index) + ", ");
			aStringBuffer.append(predictionList.get(index) + ", ");
			aStringBuffer.append(predictionCorrections.get(index) + ", ");
			aStringBuffer.append(webLinks.get(index) + "\n"); 			
		}
		return aStringBuffer;
		
	}
	
	

}
