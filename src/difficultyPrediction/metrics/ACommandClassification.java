package difficultyPrediction.metrics;

import java.util.HashMap;
import java.util.Map;

import util.annotations.Position;
import edu.cmu.scs.fluorite.commands.ICommand;


public class ACommandClassification implements CommandClassification {
	CommandName command = null;
	CommandCategoryName feature = CommandCategoryName.OTHER;
	public ACommandClassification(CommandName command, CommandCategoryName feature) {
		this.command = command;
		this.feature = feature;
	}
	public ACommandClassification(CommandName command) {
		this.command = command;
	}
	
	@Override
	@Position(0)
	public CommandName getCommand() {
		return command;
	}
	
	@Override
	@Position(1)
	public CommandCategoryName getFeature() {
		return feature;
	}
	@Override
	public void setFeature(CommandCategoryName feature) {
		this.feature = feature;
	}
	
	
	
}
