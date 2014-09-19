package difficultyPrediction;

import java.sql.Date;

public class StatusInformation {
	public double editRatio;
	public double debugRatio;
	public double removeRatio;
	public double navigationRatio;
	public double focusRatio;
	public String predictedClass;
	public String prediction;
	public Date timeStamp;
	public StatusKind statusKind;
	public String userName;
	public String userId;
	public double getEditRatio() {
		return editRatio;
	}
	public double getDebugRatio() {
		return debugRatio;
	}
	public double getRemoveRatio() {
		return removeRatio;
	}
	public double getNavigationRatio() {
		return navigationRatio;
	}
	public double getFocusRatio() {
		return focusRatio;
	}
	public String getPredictedClass() {
		return predictedClass;
	}
	public String getPrediction() {
		return prediction;
	}
	public Date getTimeStamp() {
		return timeStamp;
	}
	public StatusKind getStatusKind() {
		return statusKind;
	}
	public String getUserName() {
		return userName;
	}
	public String getUserId() {
		return userId;
	}
	public void setEditRatio(double editRatio) {
		this.editRatio = editRatio;
	}
	public void setDebugRatio(double debugRatio) {
		this.debugRatio = debugRatio;
	}
	public void setRemoveRatio(double removeRatio) {
		this.removeRatio = removeRatio;
	}
	public void setNavigationRatio(double navigationRatio) {
		this.navigationRatio = navigationRatio;
	}
	public void setFocusRatio(double focusRatio) {
		this.focusRatio = focusRatio;
	}
	public void setPredictedClass(String predictedClass) {
		this.predictedClass = predictedClass;
	}
	public void setPrediction(String prediction) {
		this.prediction = prediction;
	}
	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}
	public void setStatusKind(StatusKind statusKind) {
		this.statusKind = statusKind;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
}
