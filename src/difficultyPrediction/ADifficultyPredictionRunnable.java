package difficultyPrediction;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.ToolTip;
import org.eclipse.swt.widgets.TrayItem;
import org.eclipse.ui.PlatformUI;

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
	public static final int NUM_PENDING_COMMANDS = NUM_PENDING_SEGMENTS*ADisjointDiscreteChunks.DEFAULT_IGNORE_NUM;
//	BlockingQueue<ICommand> pendingCommands = new LinkedBlockingQueue(NUM_PENDING_COMMANDS);
	BlockingQueue<ICommand> pendingCommands = new LinkedBlockingQueue();

	protected Mediator mediator = null;
	ICommand newCommand;
	protected ToolTip ballonTip;
//	protected TrayItem trayItem;

	
	public ADifficultyPredictionRunnable() {
//		mediator = new DifficultyRobot("");
		mediator = DifficultyRobot.getInstance();
	}
//	void startExternalComponents() {
// 		LineGraphComposer.composeUI();
//		ObjectEditor.edit(APredictionParameters.getInstance());
// 	}
 	

	public void run() {
		ExternalComponentsStarter.startExternalComponents(); // comment this out if do not want the OE UI
		while (true) {
			try {
				newCommand = pendingCommands.take();
				//System.out.println("Taken command:" + newCommand);
				if(newCommand instanceof DifficulyStatusCommand) {
					ServerConnection.getServerConnection().updateStatus(((DifficulyStatusCommand) newCommand).getStatus().toString());
				}
				if (newCommand instanceof AnEndOfQueueCommand) {// stop event 
					ADifficultyPredictionPluginEventProcessor.getInstance().notifyStopCommand();
					break;
				} else if (newCommand instanceof AStartOfQueueCommand) {
					ADifficultyPredictionPluginEventProcessor.getInstance().notifyStartCommand();
					continue;

				}
				ADifficultyPredictionPluginEventProcessor.getInstance().notifyRecordCommand(newCommand);
				if (!newCommand.getCommandType().equals("PredictionCommand"))
					mediator.processEvent(newCommand);
				else {
					if (DifficultyPredictionSettings.isReplayMode()) {
						System.out.println("Prediction: "
								+ ((PredictionCommand) newCommand)
										.getPredictionType());
					} else {
						// need to display prediction, but this should be done
						// on
						// the UI thread
						ServerConnection.getServerConnection().updateStatus(((PredictionCommand) newCommand).getName());
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
		if (ballonTip == null) {
			ballonTip = new ToolTip(PlatformUI.getWorkbench()
					.getActiveWorkbenchWindow().getShell(), SWT.BALLOON
					| SWT.ICON_INFORMATION);

		}

		if (!ballonTip.isDisposed()) {
			ballonTip.setMessage("Status: " + status);
			ballonTip.setText("Status Change Notification");
			EventRecorder.getTrayItem().setToolTip(ballonTip);
			ballonTip.setVisible(true);
		}

	}
	public BlockingQueue<ICommand> getPendingCommands() {
		return pendingCommands;
	}
	public Mediator getMediator() {
		return mediator;
	}
	public ToolTip getBallonTip() {
		return ballonTip;
	}


}
