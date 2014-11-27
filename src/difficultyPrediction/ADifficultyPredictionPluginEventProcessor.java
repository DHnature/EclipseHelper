package difficultyPrediction;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.ToolTip;
import org.eclipse.swt.widgets.TrayItem;
import org.eclipse.ui.PlatformUI;

import trace.plugin.PluginThreadCreated;
import config.FactoriesSelector;
import difficultyPrediction.extension.ADifficultyPredictionRegistry;
import difficultyPrediction.featureExtraction.RatioFeatures;
import edu.cmu.scs.fluorite.commands.ICommand;
import edu.cmu.scs.fluorite.commands.PredictionCommand;
import edu.cmu.scs.fluorite.model.EventRecorder;
import edu.cmu.scs.fluorite.model.StatusConsts;
import edu.cmu.scs.fluorite.viewpart.HelpViewPart;

public class ADifficultyPredictionPluginEventProcessor implements DifficultyPredictionPluginEventProcessor {
	protected Thread difficultyPredictionThread;	
	protected DifficultyPredictionRunnable difficultyPredictionRunnable;
	protected BlockingQueue<ICommand> pendingPredictionCommands;
	private static ToolTip balloonTip;
//	private static TrayItem trayItem;
	private  Mediator statusPredictor = null; // was static in eventrecorder
	private static DifficultyPredictionPluginEventProcessor instance = null;
//	List<DifficultyPredictionEventListener> listeners = new ArrayList();
//	List<PluginEventListener> listeners = new ArrayList();



//	enum PredictorThreadOption {
//		USE_CURRENT_THREAD,
//		NO_PROCESSING,
//		THREAD_PER_ACTION,
//		SINGLE_THREAD
//	} ;
//	PredictorThreadOption predictorThreadOption = PredictorThreadOption.THREAD_PER_ACTION;
//	PredictorThreadOption predictorThreadOption = PredictorThreadOption.USE_CURRENT_THREAD;
//	PredictorThreadOption predictorThreadOption = PredictorThreadOption.NO_PROCESSING;
	PredictorThreadOption predictorThreadOption = PredictorThreadOption.SINGLE_THREAD;
	
	public ADifficultyPredictionPluginEventProcessor() {
//		statusPredictor = new DifficultyRobot(""); // should this  be in start?
		statusPredictor = DifficultyRobot.getInstance();
		if (!DifficultyPredictionSettings.isReplayMode()) 
			ADifficultyPredictionRegistry.getInstance().registerDifficultyPredictionListeners(this);

	}
	/* (non-Javadoc)
	 * @see difficultyPrediction.DifficultyPredictionPluginEventProcessor#getPredictorThreadOption()
	 */
	@Override
	public PredictorThreadOption getPredictorThreadOption() {
		return predictorThreadOption;
	}
	/* (non-Javadoc)
	 * @see difficultyPrediction.DifficultyPredictionPluginEventProcessor#setPredictorThreadOption(difficultyPrediction.PredictorThreadOption)
	 */
	@Override
	public void setPredictorThreadOption(PredictorThreadOption predictorThreadOption) {
		this.predictorThreadOption = predictorThreadOption;
	}
	/* (non-Javadoc)
	 * @see difficultyPrediction.DifficultyPredictionPluginEventProcessor#getDifficultyPredictionThread()
	 */
	@Override
	public Thread getDifficultyPredictionThread() {
		return difficultyPredictionThread;
	}
	/* (non-Javadoc)
	 * @see difficultyPrediction.DifficultyPredictionPluginEventProcessor#setDifficultyPredictionThread(java.lang.Thread)
	 */
	@Override
	public void setDifficultyPredictionThread(Thread difficultyPredictionThread) {
		this.difficultyPredictionThread = difficultyPredictionThread;
	}
	/* (non-Javadoc)
	 * @see difficultyPrediction.DifficultyPredictionPluginEventProcessor#getDifficultyPredictionRunnable()
	 */
	@Override
	public DifficultyPredictionRunnable getDifficultyPredictionRunnable() {
		return difficultyPredictionRunnable;
	}
	/* (non-Javadoc)
	 * @see difficultyPrediction.DifficultyPredictionPluginEventProcessor#setDifficultyPredictionRunnable(difficultyPrediction.DifficultyPredictionRunnable)
	 */
	@Override
	public void setDifficultyPredictionRunnable(
			DifficultyPredictionRunnable difficultyPredictionRunnable) {
		this.difficultyPredictionRunnable = difficultyPredictionRunnable;
	}
	/* (non-Javadoc)
	 * @see difficultyPrediction.DifficultyPredictionPluginEventProcessor#getPendingPredictionCommands()
	 */
	@Override
	public BlockingQueue<ICommand> getPendingPredictionCommands() {
		return pendingPredictionCommands;
	}
	/* (non-Javadoc)
	 * @see difficultyPrediction.DifficultyPredictionPluginEventProcessor#setPendingPredictionCommands(java.util.concurrent.BlockingQueue)
	 */
	@Override
	public void setPendingPredictionCommands(
			BlockingQueue<ICommand> pendingPredictionCommands) {
		this.pendingPredictionCommands = pendingPredictionCommands;
	}
	protected void maybeCreateDifficultyPredictionThread() {
		if (predictorThreadOption == PredictorThreadOption.SINGLE_THREAD && pendingPredictionCommands == null) {
			// create the difficulty prediction thread
			difficultyPredictionRunnable = new ADifficultyPredictionRunnable();
			pendingPredictionCommands = difficultyPredictionRunnable.getPendingCommands();
			difficultyPredictionThread = new Thread(difficultyPredictionRunnable);
			difficultyPredictionThread.setName(DifficultyPredictionRunnable.DIFFICULTY_PREDICTION_THREAD_NAME);
			difficultyPredictionThread.setPriority(Math.min(
					Thread.currentThread().getPriority(),
					DifficultyPredictionRunnable.DIFFICULTY_PREDICTION_THREAD_PRIORITY));
			difficultyPredictionThread.start();
			PluginThreadCreated.newCase(difficultyPredictionThread.getName(), this);
			}
	}
	
	/* (non-Javadoc)
	 * @see difficultyPrediction.DifficultyPredictionPluginEventProcessor#recordCommand(edu.cmu.scs.fluorite.commands.ICommand)
	 */
	@Override
	public void newCommand(final ICommand newCommand) {
		switch (predictorThreadOption) {
		case NO_PROCESSING:
			break;
		case THREAD_PER_ACTION:
			System.out.println(" THREAD PER ACTION!");
	Runnable myTask = new Runnable() {
		@Override
		public void run() {
			// do not predict status commands, this can cause a circular
			// reference
			PluginThreadCreated.newCase(Thread.currentThread().getName(), "", this);
			System.out.println("New thread:" + Thread.currentThread().getName());

			if (!newCommand.getCommandType().equals("PredictionCommand"))
				statusPredictor.processEvent(newCommand);
			else {
				// need to display prediction, but this should be done on
				// the UI thread
				PlatformUI.getWorkbench().getDisplay()
						.asyncExec(new Runnable() {
							@Override
							public void run() {
								PredictionCommand predictionCommand = (PredictionCommand) newCommand;
								changeStatusInHelpView(predictionCommand);
							}
						});
				PlatformUI.getWorkbench().getDisplay()
						.asyncExec(new Runnable() {
							@Override
							public void run() {
								PredictionCommand predictionCommand = (PredictionCommand) newCommand;
								changeStatusInHelpView(predictionCommand);
							}
						});

			}
		}
	};
	Thread myThread = new Thread(myTask);
	myThread.start();
	break;
		case USE_CURRENT_THREAD: 
			System.out.println("USING CURRENT THREAD!");
			//  copy and paste code in above arm
			if (!newCommand.getCommandType().equals("PredictionCommand"))
				statusPredictor.processEvent(newCommand);
			else {
				// need to display prediction, but this should be done on
				// the UI thread
				PlatformUI.getWorkbench().getDisplay()
						.asyncExec(new Runnable() {
							@Override
							public void run() {
								PredictionCommand predictionCommand = (PredictionCommand) newCommand;
								changeStatusInHelpView(predictionCommand);
							}
						});
				PlatformUI.getWorkbench().getDisplay()
						.asyncExec(new Runnable() {
							@Override
							public void run() {
								PredictionCommand predictionCommand = (PredictionCommand) newCommand;
								changeStatusInHelpView(predictionCommand);
							}
						});

			}
			break;
		case SINGLE_THREAD:
			maybeCreateDifficultyPredictionThread(); // got a null pointer once, just to be safe
			// to be implemented
//			System.out.println ("Single Thread option not implemented");
			pendingPredictionCommands.add(newCommand); // do not block
			break;
		}
//		notifyRecordCommand(newCommand);
	}
	/* (non-Javadoc)
	 * @see difficultyPrediction.DifficultyPredictionPluginEventProcessor#changeStatusInHelpView(edu.cmu.scs.fluorite.commands.PredictionCommand)
	 */
	@Override
	public void changeStatusInHelpView(PredictionCommand predictionCommand) {
		String status = "";
		switch (predictionCommand.getPredictionType()) {
		case MakingProgress:
			status = StatusConsts.MAKING_PROGRESS_STATUS;
			break;
		case HavingDifficulty:
			status = StatusConsts.SLOW_PROGRESS_STATUS;
			break;
		case Indeterminate:
			status = StatusConsts.INDETERMINATE;
			break;
		}

		showStatusInBallonTip(status);
		HelpViewPart.displayStatusInformation(status);
	}
	private void showStatusInBallonTip(String status) {
		if (balloonTip == null) {
			balloonTip = new ToolTip(PlatformUI.getWorkbench()
					.getActiveWorkbenchWindow().getShell(), SWT.BALLOON
					| SWT.ICON_INFORMATION);

		}

		if (!balloonTip.isDisposed()) {
			balloonTip.setMessage("Status: " + status);
			balloonTip.setText("Status Change Notification");
			EventRecorder.getInstance().getTrayItem().setToolTip(balloonTip);
			balloonTip.setVisible(true);
		}

	}

	/* (non-Javadoc)
	 * @see difficultyPrediction.DifficultyPredictionPluginEventProcessor#start()
	 */
	@Override
	public void commandProcessingStarted() {
		FactoriesSelector.configureFactories();
		maybeCreateDifficultyPredictionThread();
		pendingPredictionCommands.add(new AStartOfQueueCommand());



	}
	/* (non-Javadoc)
	 * @see difficultyPrediction.DifficultyPredictionPluginEventProcessor#stop()
	 */
	@Override
	public void commandProcessingStopped() {
		pendingPredictionCommands.add(new AnEndOfQueueCommand());

	}
	public static DifficultyPredictionPluginEventProcessor getInstance() {
		if (instance == null) {
			instance = new ADifficultyPredictionPluginEventProcessor();
		}
		return instance;
	}
	public static void setInstance(DifficultyPredictionPluginEventProcessor newVal) {
		instance = newVal;
	}
	/* (non-Javadoc)
	 * @see difficultyPrediction.DifficultyPredictionPluginEventProcessor#addDifficultyPredictionEventListener(difficultyPrediction.DifficultyPredictionEventListener)
	 */
//	@Override
//	public void addPluginEventEventListener(PluginEventListener aListener){
//		listeners.add(aListener);
//	}
//	/* (non-Javadoc)
//	 * @see difficultyPrediction.DifficultyPredictionPluginEventProcessor#addRemovePredictionEventListener(difficultyPrediction.DifficultyPredictionEventListener)
//	 */
//	@Override
//	public void removePluginEventListener(PluginEventListener aListener){
//		listeners.remove(aListener);
//	}
//	/* (non-Javadoc)
//	 * @see difficultyPrediction.DifficultyPredictionPluginEventProcessor#notifyStart()
//	 */
//	@Override
//	public void notifyStartCommand() {
//		for (PluginEventListener aListener:listeners) {
//			aListener.commandProcessingStarted();
//		}
//	}
//	/* (non-Javadoc)
//	 * @see difficultyPrediction.DifficultyPredictionPluginEventProcessor#notifyStop()
//	 */
//	@Override
//	public void notifyStopCommand() {
//		for (PluginEventListener aListener:listeners) {
//			aListener.commandProcessingStopped();
//		}
//	}
//	/* (non-Javadoc)
//	 * @see difficultyPrediction.DifficultyPredictionPluginEventProcessor#notifyRecordCommand(edu.cmu.scs.fluorite.commands.ICommand)
//	 */
//	@Override
//	public void notifyRecordCommand(ICommand aCommand) {
//		for (PluginEventListener aListener:listeners) {
//			aListener.newCommand(aCommand);
//		}
//	}
	/* (non-Javadoc)
	 * @see difficultyPrediction.DifficultyPredictionPluginEventProcessor#notifyNewRatios(difficultyPrediction.featureExtraction.RatioFeatures)
	 */
//	@Override
//	public void notifyNewRatios(RatioFeatures aFeatures) {
//		for (DifficultyPredictionEventListener aListener:listeners) {
//			aListener.newRatios(aFeatures);
//		}
//	}

}
