package dayton;

import difficultyPrediction.DifficultyPredictionSettings;
import difficultyPrediction.DifficultyRobot;
import difficultyPrediction.PluginEventListener;
import fluorite.commands.DifficulyStatusCommand;
import fluorite.commands.EHICommand;
import fluorite.commands.PredictionCommand;

public class AServerStatusUpdater implements ServerStatusUpdater {
	public AServerStatusUpdater() {
		DifficultyRobot.getInstance().addPluginEventListener(this);

	}

	@Override
	public void newCommand(EHICommand newCommand) {
		if (DifficultyPredictionSettings.isReplayMode())
			return;
		if (newCommand instanceof DifficulyStatusCommand) {
			System.out.println("Difficulty status to server");
				
			ServerConnection.getServerConnection().updateStatus(
					((DifficulyStatusCommand) newCommand).getStatus()
							.toString());
		} else if (newCommand instanceof PredictionCommand) {
			System.out.println("Prediction command to server");

			ServerConnection.getServerConnection()
			.updateStatus(
					((PredictionCommand) newCommand)
							.getName());
		}
		
	}

	@Override
	public void commandProcessingStarted() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void commandProcessingStopped() {
		// TODO Auto-generated method stub
		
	}
	

}
