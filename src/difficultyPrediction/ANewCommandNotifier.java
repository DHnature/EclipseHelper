package difficultyPrediction;

import edu.cmu.scs.fluorite.commands.ICommand;

public class ANewCommandNotifier  implements Runnable{
	PluginEventListener listener;
	ICommand command;
	public ANewCommandNotifier(PluginEventListener aListener, ICommand aCommand) {		
			listener = aListener;
			command = aCommand;		
	}
	public void run() {
		listener.newCommand(command);
	}
	
	

}
