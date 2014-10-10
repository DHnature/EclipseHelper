package difficultyPrediction.featureExtraction;

public class ARatioFeatures implements RatioFeatures {
	 double editRatio;
	 double debugRatio;
	 double navigationRatio;
	 double focusRatio;
	 double removeRatio;
	 double exceptionsPerRun;
	 double insertionRatio;
	 double deletionRatio;
	 double insertionTimeRatio;
	 double deletionTimeRatio;
	 double debugTimeRatio;
	 double navigationTimeRatio;
	 double focusTimeRatio;
	 double removeTimeRatio;
	 long savedTimeStamp;
	 
	 public ARatioFeatures() {
		 
	 }
	public double getEditRatio() {
		return editRatio;
	}
	public void setEditRatio(double editRatio) {
		this.editRatio = editRatio;
	}
	public double getDebugRatio() {
		return debugRatio;
	}
	public void setDebugRatio(double debugRatio) {
		this.debugRatio = debugRatio;
	}
	public double getNavigationRatio() {
		return navigationRatio;
	}
	public void setNavigationRatio(double navigationRatio) {
		this.navigationRatio = navigationRatio;
	}
	public double getFocusRatio() {
		return focusRatio;
	}
	public void setFocusRatio(double focusRatio) {
		this.focusRatio = focusRatio;
	}
	public double getRemoveRatio() {
		return removeRatio;
	}
	public void setRemoveRatio(double removeRatio) {
		this.removeRatio = removeRatio;
	}
	public double getExceptionsPerRun() {
		return exceptionsPerRun;
	}
	public void setExceptionsPerRun(double exceptionsPerRun) {
		this.exceptionsPerRun = exceptionsPerRun;
	}
	public double getInsertionRatio() {
		return insertionRatio;
	}
	public void setInsertionRatio(double insertionRatio) {
		this.insertionRatio = insertionRatio;
	}
	public double getDeletionRatio() {
		return deletionRatio;
	}
	public void setDeletionRatio(double deletionRatio) {
		this.deletionRatio = deletionRatio;
	}
	public double getInsertionTimeRatio() {
		return insertionTimeRatio;
	}
	public void setInsertionTimeRatio(double insertionTimeRatio) {
		this.insertionTimeRatio = insertionTimeRatio;
	}
	public double getDeletionTimeRatio() {
		return deletionTimeRatio;
	}
	public void setDeletionTimeRatio(double deletionTimeRatio) {
		this.deletionTimeRatio = deletionTimeRatio;
	}
	public double getDebugTimeRatio() {
		return debugTimeRatio;
	}
	public void setDebugTimeRatio(double debugTimeRatio) {
		this.debugTimeRatio = debugTimeRatio;
	}
	public double getNavigationTimeRatio() {
		return navigationTimeRatio;
	}
	public void setNavigationTimeRatio(double navigationTimeRatio) {
		this.navigationTimeRatio = navigationTimeRatio;
	}
	public double getFocusTimeRatio() {
		return focusTimeRatio;
	}
	public void setFocusTimeRatio(double focusTimeRatio) {
		this.focusTimeRatio = focusTimeRatio;
	}
	public double getRemoveTimeRatio() {
		return removeTimeRatio;
	}
	public void setRemoveTimeRatio(double removeTimeRatio) {
		this.removeTimeRatio = removeTimeRatio;
	}
	public long getSavedTimeStamp() {
		return savedTimeStamp;
	}
	public void setSavedTimeStamp(long savedTimeStamp) {
		this.savedTimeStamp = savedTimeStamp;
	}
	
}
