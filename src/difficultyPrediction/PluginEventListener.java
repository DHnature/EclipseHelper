package difficultyPrediction;

import edu.cmu.scs.fluorite.commands.ICommand;

public interface PluginEventListener {
	void newCommand(final ICommand newCommand);
	void commandProcessingStarted();
	void commandProcessingStopped();

}
