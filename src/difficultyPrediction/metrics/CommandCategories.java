package difficultyPrediction.metrics;

public interface CommandCategories {

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
	public abstract CommandClassification[] getCommandsToFeatureDesciptor();

	public abstract void setCommandsToFeatureDesciptor(
			CommandClassification[] commandsToFeatureDesciptor);

	void map(CommandName aCommand, CommandCategoryName aFeatureName);

	String getUnclassifiedCommands();

	void map(CommandName[] aCommandNames, CommandCategoryName aFeatureName);

	String getNavigationCommands();

}