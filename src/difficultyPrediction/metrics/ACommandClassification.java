package difficultyPrediction.metrics;

import java.util.HashMap;
import java.util.Map;

import util.annotations.Position;
import edu.cmu.scs.fluorite.commands.ICommand;


public class ACommandClassification implements CommandToFeatureDescriptor {
	CommandName command = null;
	FeatureName feature = FeatureName.OTHER;
	public ACommandClassification(CommandName command, FeatureName feature) {
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
	public FeatureName getFeature() {
		return feature;
	}
	@Override
	public void setFeature(FeatureName feature) {
		this.feature = feature;
	}
	
	
	
}
