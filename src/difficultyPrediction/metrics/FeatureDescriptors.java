package difficultyPrediction.metrics;

public interface FeatureDescriptors {

	public abstract String getSearchFeature();

	//	public void setSearchFeature(String searchFeature) {
	//		this.searchFeature = searchFeature;
	//	}
	public abstract String getDebugFeature();

	//	public void setDebugFeature(String debugFeature) {
	//		this.debugFeature = debugFeature;
	//	}
	public abstract String getEditOrInsertFeature();

	//	public void setEditOrInsertFeature(String editOrInsertFeature) {
	//		this.editOrInsertFeature = editOrInsertFeature;
	//	}
	public abstract String getFocusFeature();

	//	public void setFocusFeature(String focusFeature) {
	//		this.focusFeature = focusFeature;
	//	}
	public abstract String getRemoveFeature();

	//	public void setRemoveFeature(String removeFeature) {
	//		this.removeFeature = removeFeature;
	//	}
	public abstract CommandToFeatureDescriptor[] getCommandsToFeatureDesciptor();

	public abstract void setCommandsToFeatureDesciptor(
			CommandToFeatureDescriptor[] commandsToFeatureDesciptor);

	void map(CommandName aCommand, FeatureName aFeatureName);

}