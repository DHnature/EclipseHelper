package difficultyPrediction.metrics;

import java.util.ArrayList;
import java.util.List;

import bus.uigen.ObjectEditor;

public class AFeatureDescriptors implements FeatureDescriptors {
//	String searchFeature = new AString(FeatureName.SEARCH.toString());
//	String debugFeature = new AString(FeatureName.EDIT_OR_INSERT.toString());
//
//	String editOrInsertFeature = new AString(FeatureName.EDIT_OR_INSERT.toString());
//	String focusFeature = new AString(FeatureName.FOCUS.toString());
//	String removeFeature = new AString(FeatureName.REMOVE.toString());
	
	String searchFeature = "";
	String debugFeature = "";
	String editOrInsertFeature = "";
	String focusFeature = "";
	String removeFeature = "";
	CommandToFeatureDescriptor[] commandsToFeatureDesciptor =
			new CommandToFeatureDescriptor[CommandName.values().length] ;
	
	@Override
	public String getSearchFeature() {
		return searchFeature;
	}
//	public void setSearchFeature(String searchFeature) {
//		this.searchFeature = searchFeature;
//	}
	@Override
	public String getDebugFeature() {
		return debugFeature;
	}
//	public void setDebugFeature(String debugFeature) {
//		this.debugFeature = debugFeature;
//	}
	@Override
	public String getEditOrInsertFeature() {
		return editOrInsertFeature;
	}
//	public void setEditOrInsertFeature(String editOrInsertFeature) {
//		this.editOrInsertFeature = editOrInsertFeature;
//	}
	@Override
	public String getFocusFeature() {
		return focusFeature;
	}
//	public void setFocusFeature(String focusFeature) {
//		this.focusFeature = focusFeature;
//	}
	@Override
	public String getRemoveFeature() {
		return removeFeature;
	}
//	public void setRemoveFeature(String removeFeature) {
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
				searchFeature += " " + aCommand.getCommand();
				break;
			case DEBUG:
				debugFeature += " " + aCommand.getCommand();
				break;
			case EDIT_OR_INSERT:
				editOrInsertFeature += " " + aCommand.getCommand();
				break;	
			case FOCUS:
				focusFeature += " " + aCommand.getCommand();
				break;
			case REMOVE:
				removeFeature += " " + aCommand.getCommand();
				break;	
			case OTHER:
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
		CommandToFeatureDescriptor aCommandToFeatureDescriptor = 
				new ACommandToFeatureDescriptor(aCommand, aFeatureName);
		commandsToFeatureDesciptor[aCommand.ordinal()] = aCommandToFeatureDescriptor;
	}
	public static void main (String[] args) {
		FeatureDescriptors commandsToFeatures = new AFeatureDescriptors();
		commandsToFeatures.map(CommandName.BreakPointCommand, FeatureName.DEBUG);
		ObjectEditor.edit(commandsToFeatures);
	}
	

}
