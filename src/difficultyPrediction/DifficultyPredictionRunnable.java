package difficultyPrediction;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.eclipse.swt.widgets.ToolTip;

import edu.cmu.scs.fluorite.commands.ICommand;

public interface DifficultyPredictionRunnable extends Runnable {
	final String DIFFICULTY_PREDICTION_THREAD_NAME = "Difficulty Prediction Thread";
	final int DIFFICULTY_PREDICTION_THREAD_PRIORITY = Thread.NORM_PRIORITY - 1;
	public BlockingQueue<ICommand> getPendingCommands() ;
	public Mediator getMediator() ;
	public ToolTip getBallonTip();
}
