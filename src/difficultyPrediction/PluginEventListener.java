package difficultyPrediction;

import edu.cmu.scs.fluorite.commands.ICommand;

public interface PluginEventListener {
	void recordCommand(final ICommand newCommand);
	void start();
	void stop();

}
