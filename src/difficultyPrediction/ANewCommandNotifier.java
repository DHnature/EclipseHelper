package difficultyPrediction;

import fluorite.commands.EHICommand;

public class ANewCommandNotifier  implements Runnable{
	PluginEventListener listener;
	EHICommand command;
	public ANewCommandNotifier(PluginEventListener aListener, EHICommand aCommand) {		
			listener = aListener;
			command = aCommand;		
	}
	public void run() {
		listener.newCommand(command);
	}
	
	

}
