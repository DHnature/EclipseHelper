package difficultyPrediction.metrics;

import java.util.ArrayList;
import java.util.List;

import bus.uigen.ObjectEditor;

public class ACommandCategories implements FeatureDescriptors {
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
	String unclassifiedCommands = "";
	CommandToFeatureDescriptor[] commandsToFeatureDesciptor =
			new CommandToFeatureDescriptor[CommandName.values().length] ;
	public ACommandCategories() {
		CommandName[] aCommandNames = CommandName.values();
		for (CommandName aCommandName: aCommandNames) {
			commandsToFeatureDesciptor[aCommandName.ordinal()] =
					new ACommandClassification(aCommandName);
		}
		computeCommands();
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
	public String getUnclassifiedCommands() {
		return unclassifiedCommands;
	}
//	public void setRemoveCommands(String removeFeature) {
//		this.removeFeatures = removeFeatures;
//	}
	@Override
	public CommandToFeatureDescriptor[] getCommandsToFeatureDesciptor() {
		return commandsToFeatureDesciptor;
	}
	public void computeCommands () {
		for (CommandToFeatureDescriptor aCommand:commandsToFeatureDesciptor) {
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
			case OTHER:
				unclassifiedCommands += " " + aCommand.getCommand();
				break;
			}
		}
		
	}
	@Override
	public void setCommandsToFeatureDesciptor(
			CommandToFeatureDescriptor[] commandsToFeatureDesciptor) {
		this.commandsToFeatureDesciptor = commandsToFeatureDesciptor;
		
	}
	@Override
	public void map (CommandName aCommand, FeatureName aFeatureName) {		
		commandsToFeatureDesciptor[aCommand.ordinal()].setFeature(aFeatureName);
	}
	public static void main (String[] args) {
		FeatureDescriptors commandsToFeatures = new ACommandCategories();
		commandsToFeatures.map(CommandName.BreakPointCommand, FeatureName.DEBUG);
		ObjectEditor.edit(commandsToFeatures);
	}
	

}
