package difficultyPrediction;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.ToolTip;
import org.eclipse.swt.widgets.TrayItem;
import org.eclipse.ui.PlatformUI;

import trace.difficultyPrediction.CommandIgnoredBecauseQueueFull;
import config.PredictorConfigurer;
import bus.uigen.ObjectEditor;
import analyzer.ui.graphics.LineGraphComposer;
import dayton.ServerConnection;
import difficultyPrediction.eventAggregation.ADisjointDiscreteChunks;
import difficultyPrediction.eventAggregation.EventAggregationStrategy;
import edu.cmu.scs.fluorite.commands.DifficulyStatusCommand;
import edu.cmu.scs.fluorite.commands.ICommand;
import edu.cmu.scs.fluorite.commands.PredictionCommand;
import edu.cmu.scs.fluorite.model.EventRecorder;
import edu.cmu.scs.fluorite.model.StatusConsts;
import edu.cmu.scs.fluorite.viewpart.HelpViewPart;

public class ADifficultyPredictionRunnable implements DifficultyPredictionRunnable{
	public static final int NUM_PENDING_SEGMENTS = 4;
	public static final int NUM_PENDING_COMMANDS = 40096;
//			NUM_PENDING_SEGMENTS*ADisjointDiscreteChunks.DEFAULT_IGNORE_NUM;
	BlockingQueue<ICommand> pendingCommands = new LinkedBlockingQueue(NUM_PENDING_COMMANDS);
//	BlockingQueue<ICommand> pendingCommands = new LinkedBlockingQueue();

	protected Mediator mediator = null;
	ICommand newCommand;
	protected ToolTip ballonTip;
//	protected TrayItem trayItem;
	static DifficultyPredictionRunnable instance;

	
	

	public ADifficultyPredictionRunnable() {
//		mediator = new DifficultyRobot("");
		mediator = DifficultyRobot.getInstance();
		instance = this;
	}
//	void startExternalComponents() {
// 		LineGraphComposer.composeUI();
//		ObjectEditor.edit(APredictionParameters.getInstance());
// 	}
 	
	public void run() {
		//PredictorConfigurer.configure(); // comment this out if do not want the OE UI
		while (true) {
			try {
				newCommand = pendingCommands.take();
				System.out.println ("Pending commands size" + pendingCommands.size());
//				if (pendingCommands.size() > NUM_PENDING_COMMANDS) {
//					System.err.printf("Number of commands > " + NUM_PENDING_COMMANDS  + "ending dfficulyty prediction");
//					System.out.println("LARGe NUMBER OF BUFFERED COMMANDS");
//					break;
//					
//				}
				//System.out.println("Taken command:" + newCommand);
				if(newCommand instanceof DifficulyStatusCommand && !DifficultyPredictionSettings.isReplayMode()) { // should handle this config
					ServerConnection.getServerConnection().updateStatus(((DifficulyStatusCommand) newCommand).getStatus().toString());
				}
				if (newCommand instanceof AnEndOfQueueCommand) {// stop event 
					DifficultyRobot.getInstance().notifyStopCommand();
					break;
				} else if (newCommand instanceof AStartOfQueueCommand) {
					DifficultyRobot.getInstance().notifyStartCommand();
					continue;

				}
				DifficultyRobot.getInstance().notifyNewCommand(newCommand);
				if (!newCommand.getCommandType().equals("PredictionCommand") && 
						!newCommand.getCommandType().equals("DifficultyStatusCommand")						
					&& !(newCommand instanceof PredictionCommand) && 
					!(newCommand instanceof DifficulyStatusCommand )) {
					mediator.processEvent(newCommand);
				} else if (!(newCommand instanceof PredictionCommand)){
					System.out.println("Ignoreing difficulty status Command " + newCommand);
				} else {
					String lastStatus = HelpViewPart.getStatusInformation();
					final String currentStatus = getStatus((PredictionCommand) newCommand);
					if (!currentStatus.equals(lastStatus)) {
					if (DifficultyPredictionSettings.isReplayMode()) {
						System.out.println("Prediction: "
								+ ((PredictionCommand) newCommand)
										.getPredictionType());
					} else  {
						// need to display prediction, but this should be done
						// on
						// the UI thread
						// need to so this in listener
						if (!DifficultyPredictionSettings.isReplayMode())
						ServerConnection.getServerConnection().updateStatus(((PredictionCommand) newCommand).getName());
						PlatformUI.getWorkbench().getDisplay()
								.asyncExec(new Runnable() {
									@Override
									public void run() {
										PredictionCommand predictionCommand = (PredictionCommand) newCommand;
//										changeStatusInHelpView(predictionCommand);
										changeStatusInHelpView(currentStatus);
									}
								});
//						PlatformUI.getWorkbench().getDisplay()
//								.asyncExec(new Runnable() {
//									@Override
//									public void run() {
//										PredictionCommand predictionCommand = (PredictionCommand) newCommand;
//										changeStatusInHelpView(predictionCommand);
//									}
//								});

					}
				}
				}

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
//	public  void processCommand(ICommand newCommand) {
//		if (!newCommand.getCommandType().equals("PredictionCommand"))
//			statusPredictor.processEvent(newCommand);
//		else {
//			// need to display prediction, but this should be done on
//			// the UI thread
//			PlatformUI.getWorkbench().getDisplay()
//					.asyncExec(new Runnable() {
//						@Override
//						public void run() {
//							PredictionCommand predictionCommand = (PredictionCommand) newCommand;
//							changeStatusInHelpView(predictionCommand);
//						}
//					});
//			PlatformUI.getWorkbench().getDisplay()
//					.asyncExec(new Runnable() {
//						@Override
//						public void run() {
//							PredictionCommand predictionCommand = (PredictionCommand) newCommand;
//							changeStatusInHelpView(predictionCommand);
//						}
//					});
//
//		}
//		
//	}
	String lastStatus = "";
	public static String getStatus(PredictionCommand predictionCommand) {
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
		return status;
	}

	public void changeStatusInHelpView(PredictionCommand predictionCommand) {
//		String status = "";
//		switch (predictionCommand.getPredictionType()) {
//		case MakingProgress:
//			status = StatusConsts.MAKING_PROGRESS_STATUS;
//			break;
//		case HavingDifficulty:
//			status = StatusConsts.SLOW_PROGRESS_STATUS;
//			break;
//		case Indeterminate:
//			status = StatusConsts.INDETERMINATE;
//			break;
//		}
//		if (status.equals(lastStatus)) return;
//		showStatusInBallonTip(status);
//		HelpViewPart.displayStatusInformation(status);
//		lastStatus = status;
		changeStatusInHelpView(getStatus(predictionCommand));

	}
	public void changeStatusInHelpView(String status) {
		if (status.equals(lastStatus)) return;
		showStatusInBallonTip(status);
		HelpViewPart.displayStatusInformation(status);
		lastStatus = status;

	}
	@Override
	public void showStatusInBallonTip(String status) {
		if (ballonTip == null) {
			ballonTip = new ToolTip(PlatformUI.getWorkbench()
					.getActiveWorkbenchWindow().getShell(), SWT.BALLOON
					| SWT.ICON_INFORMATION);

		}

		if (!ballonTip.isDisposed()) {
//			ballonTip.setMessage("Status: " + status);
			ballonTip.setMessage(status);
			ballonTip.setText("Status Change Notification");
			EventRecorder.getTrayItem().setToolTip(ballonTip);
			ballonTip.setVisible(true);
		}

	}
	@Override
	public void asyncShowStatusInBallonTip(String status) {
		final String currentStatus = status;
		PlatformUI.getWorkbench().getDisplay()
		.asyncExec(new Runnable() {
			@Override
			public void run() {
//				changeStatusInHelpView(predictionCommand);
				showStatusInBallonTip(currentStatus);
			}
		});

	}
//	public BlockingQueue<ICommand> getPendingCommands() {
//		return pendingCommands;
//	}
	public Mediator getMediator() {
		return mediator;
	}
	public ToolTip getBallonTip() {
		return ballonTip;
	}
	boolean full;
	@Override
	public void add(ICommand newCommand) {
		try {
			if (!(newCommand instanceof AnEndOfQueueCommand) && pendingCommands.size() > NUM_PENDING_COMMANDS -2) {
				System.err.printf("Number of commands > " + NUM_PENDING_COMMANDS  + "ending dfficulyty prediction");
				System.out.println("LARGe NUMBER OF BUFFERED COMMANDS");
				return;
//				break;
				
			}
			pendingCommands.add(newCommand);
			full = false;
		} catch (IllegalStateException e) {
			if (!full) {
			CommandIgnoredBecauseQueueFull.newCase(this);
			full = true;
			}
			
		}
		
	}
	public static DifficultyPredictionRunnable getInstance() {
		if (instance == null)
			new ADifficultyPredictionRunnable();
		return instance;
	}

	

}
