package difficultyPrediction.metrics;

import java.util.ArrayList;
import java.util.List;

import bus.uigen.ObjectEditor;

public class ACommandCategories implements CommandCategories {
//	String searchFeature = new AString(FeatureName.SEARCH.toString());
//	String debugFeature = new AString(FeatureName.EDIT_OR_INSERT.toString());
//
//	String editOrInsertFeature = new AString(FeatureName.EDIT_OR_INSERT.toString());
//	String focusFeature = new AString(FeatureName.FOCUS.toString());
//	String removeFeature = new AString(FeatureName.REMOVE.toString());
	
	String searchCommands = "";
	String debugCommands = "";
	String editOrInsertCommands = "";
	String focusCommands = "";
	String removeCommands = "";
	String navigationCommands = "";
	String unclassifiedCommands = "";
	CommandClassification[] commandsToFeatureDesciptor =
			new CommandClassification[CommandName.values().length] ;
	public ACommandCategories() {
		initializeCommands();
		computeCommands();
	}
	protected void initializeCommands() {
		CommandName[] aCommandNames = CommandName.values();
		for (CommandName aCommandName: aCommandNames) {
			commandsToFeatureDesciptor[aCommandName.ordinal()] =
					new ACommandClassification(aCommandName);
		}
	}
	
	@Override
	public String getSearchCommands() {
		return searchCommands;
	}
//	public void setSearchCommands(String searchCommands) {
//		this.searchCommands = searchCommands;
//	}
	@Override
	public String getDebugCommands() {
		return debugCommands;
	}
//	public void setDebugCommands(String debugCommands) {
//		this.debugCommands = debugCommands;
//	}
	@Override
	public String getEditOrInsertCommands() {
		return editOrInsertCommands;
	}
//	public void setEditOrInsertCommands(String editOrInsertCommands) {
//		this.editOrInsertCommands = editOrInsertCommands;
//	}
	@Override
	public String getFocusCommands() {
		return focusCommands;
	}
//	public void setFocusFeature(String focusFeature) {
//		this.focusCommands = focusCommands;
//	}
	@Override
	public String getRemoveCommands() {
		return removeCommands;
	}
	@Override
	public String getNavigationCommands() {
		return navigationCommands;
	}
	@Override
	public String getUnclassifiedCommands() {
		return unclassifiedCommands;
	}
//	public void setRemoveCommands(String removeFeature) {
//		this.removeFeatures = removeFeatures;
//	}
	@Override
	public CommandClassification[] getCommandsToFeatureDesciptor() {
		return commandsToFeatureDesciptor;
	}
	public void computeCommands () {
		searchCommands = "";
		debugCommands = "";
		editOrInsertCommands = "";
		focusCommands = "";
		removeCommands = "";
		navigationCommands = "";
		unclassifiedCommands = "";
		for (CommandClassification aCommand:commandsToFeatureDesciptor) {
			computeCommands(aCommand);
		}
		
	}
	public void computeCommands (CommandClassification aCommand) {
			switch (aCommand.getFeature()) {
			case SEARCH: 
				searchCommands += " " + aCommand.getCommand();
				break;
			case DEBUG:
				debugCommands += " " + aCommand.getCommand();
				break;
			case EDIT_OR_INSERT:
				editOrInsertCommands += " " + aCommand.getCommand();
				break;	
			case FOCUS:
				focusCommands += " " + aCommand.getCommand();
				break;
			case REMOVE:
				removeCommands += " " + aCommand.getCommand();
				break;	
			case NAVIGATION:
				navigationCommands += " " + aCommand.getCommand();
			case OTHER:
				unclassifiedCommands += " " + aCommand.getCommand();
				break;
			}
	
		
	}
	@Override
	public void setCommandsToFeatureDesciptor(
			CommandClassification[] commandsToFeatureDesciptor) {
		this.commandsToFeatureDesciptor = commandsToFeatureDesciptor;
		
	}
	protected void mapButDoNotCompute (CommandName aCommand, CommandCategoryName aFeatureName) {		
		commandsToFeatureDesciptor[aCommand.ordinal()].setFeature(aFeatureName);
	}
	@Override
	public void map (CommandName aCommand, CommandCategoryName aFeatureName) {		
		commandsToFeatureDesciptor[aCommand.ordinal()].setFeature(aFeatureName);
		computeCommands();
	}
	@Override
	public void map (CommandName[] aCommandNames, CommandCategoryName aFeatureName) {		
		for (CommandName aCommandName:aCommandNames) {
			mapButDoNotCompute (aCommandName, aFeatureName);
		}
		computeCommands();
	}
	public static void main (String[] args) {
		CommandCategories commandsToFeatures = new ACommandCategories();
		commandsToFeatures.map(CommandName.BreakPointCommand, CommandCategoryName.DEBUG);
		ObjectEditor.edit(commandsToFeatures);
	}
	

}
