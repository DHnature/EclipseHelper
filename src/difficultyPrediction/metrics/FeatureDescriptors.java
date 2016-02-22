package difficultyPrediction.metrics;

public interface FeatureDescriptors {

	public abstract String getSearchCommands();

	//	public void setSearchCommands(String searchCommands) {
	//		this.searchCommands = searchCommands;
	//	}
	public abstract String getDebugCommands();

	//	public void setDebugCommands(String debugCommands) {
	//		this.debugCommands = debugCommands;
	//	}
	public abstract String getEditOrInsertCommands();

	//	public void setEditOrInsertCommands(String editOrInsertCommands) {
	//		this.editOrInsertCommands = editOrInsertCommands;
	//	}
	public abstract String getFocusCommands();

	//	public void setFocusCommands(String focusCommands) {
	//		this.focusCommands = focusCommands;
	//	}
	public abstract String getRemoveCommands();

	//	public void setRemoveCommands(String removeCommands) {
	//		this.removeCommands = removeCommands;
	//	}
	public abstract CommandToFeatureDescriptor[] getCommandsToFeatureDesciptor();

	public abstract void setCommandsToFeatureDesciptor(
			CommandToFeatureDescriptor[] commandsToFeatureDesciptor);

	void map(CommandName aCommand, FeatureName aFeatureName);

	String getUnclassifiedCommands();

}