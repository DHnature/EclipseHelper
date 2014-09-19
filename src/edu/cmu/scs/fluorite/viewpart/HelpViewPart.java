package edu.cmu.scs.fluorite.viewpart;

import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;

import edu.cmu.scs.fluorite.commands.DifficulyStatusCommand;
import edu.cmu.scs.fluorite.commands.DifficulyStatusCommand.Status;
import edu.cmu.scs.fluorite.dialogs.InsurmountableDialog;
import edu.cmu.scs.fluorite.dialogs.SurmountableDialog;
import edu.cmu.scs.fluorite.model.EventRecorder;
import edu.cmu.scs.fluorite.model.StatusConsts;

import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.custom.CTabItem;

public class HelpViewPart extends ViewPart {
	public HelpViewPart() {
	}

	private static Label lblStatusValue;
	
	@Override
	public void createPartControl(Composite parent) {
		parent.setLayout(new FormLayout());

		final Label lblStatus = new Label(parent, SWT.NONE);
		FormData fd_lblStatus = new FormData();
		fd_lblStatus.top = new FormAttachment(0, 5);
		fd_lblStatus.left = new FormAttachment(0, 5);
		lblStatus.setLayoutData(fd_lblStatus);
		lblStatus.setText("Status:");

		lblStatusValue = new Label(parent, SWT.NONE);
		FormData fd_lblStatusValue = new FormData();
		fd_lblStatusValue.bottom = new FormAttachment(0, 19);
		fd_lblStatusValue.right = new FormAttachment(0, 455);
		fd_lblStatusValue.top = new FormAttachment(0, 5);
		fd_lblStatusValue.left = new FormAttachment(0, 159);
		lblStatusValue.setLayoutData(fd_lblStatusValue);
		lblStatusValue.setSize(500, lblStatusValue.getSize().y);
		lblStatusValue.setText("Session Started");

		final Shell shell = this.getSite().getWorkbenchWindow().getShell();
		Button btnMakingProgress = new Button(parent, SWT.NONE);
		FormData fd_btnMakingProgress = new FormData();
		fd_btnMakingProgress.top = new FormAttachment(0, 62);
		fd_btnMakingProgress.left = new FormAttachment(0, 5);
		btnMakingProgress.setLayoutData(fd_btnMakingProgress);
		btnMakingProgress.setText(StatusConsts.PROGRESS_TEXT);
		btnMakingProgress.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				DifficulyStatusCommand command = new DifficulyStatusCommand(Status.Making_Progress);
				EventRecorder.getInstance().recordCommand(command);
				lblStatusValue.setText(StatusConsts.PROGRESS_TEXT);
			}
		});

		Button btnSurmountable = new Button(parent, SWT.NONE);
		FormData fd_btnSurmountable = new FormData();
		fd_btnSurmountable.top = new FormAttachment(0, 62);
		fd_btnSurmountable.left = new FormAttachment(0, 159);
		btnSurmountable.setLayoutData(fd_btnSurmountable);
		btnSurmountable.setText(StatusConsts.SURMOUNTABLE_TEXT);
		btnSurmountable.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				SurmountableDialog dialog = new SurmountableDialog(shell);
				dialog.create();
				if (dialog.open() == Window.OK) {
					DifficulyStatusCommand command =
							new DifficulyStatusCommand(Status.Surmountable,dialog.getTryingToDo(), dialog.getCausedDifficulty(), 
									dialog.getOtherCausedDifficulty(), dialog.getOvercomeDifficultyDropDown(), 
									dialog.getOtherOverComeDifficultySaveText(), dialog.getOtherMinutes());
					EventRecorder.getInstance().recordCommand(command);
					lblStatusValue.setText(StatusConsts.SURMOUNTABLE_TEXT);
				}
			}
		});

		Button btnInsurmountable = new Button(parent, SWT.NONE);
		FormData fd_btnInsurmountable = new FormData();
		fd_btnInsurmountable.top = new FormAttachment(0, 62);
		fd_btnInsurmountable.left = new FormAttachment(0, 394);
		btnInsurmountable.setLayoutData(fd_btnInsurmountable);
		btnInsurmountable.setText(StatusConsts.INSURMOUNTABLE_TEXT);
		btnInsurmountable.addSelectionListener(new SelectionAdapter(){
			public void widgetSelected(SelectionEvent e) {
				InsurmountableDialog dialog = new InsurmountableDialog(shell);
				dialog.create();
				if (dialog.open() == Window.OK) {
					DifficulyStatusCommand command =
							new DifficulyStatusCommand(Status.Insurmountable,dialog.getTryingToDo(), dialog.getCausedDifficulty(), 
									dialog.getOtherCausedDifficulty(), dialog.getOvercomeDifficultyDropDown(), 
									dialog.getOtherOverComeDifficultySaveText(), dialog.getOtherMinutes(), 
									dialog.getPersonAskedForHelp());
					EventRecorder.getInstance().recordCommand(command);
					lblStatusValue.setText(StatusConsts.INSURMOUNTABLE_TEXT);
				}
			}
		});
	}
	
	public static void displayStatusInformation(String status)
	{
		if (!lblStatusValue.isDisposed())
		{
			if (lblStatusValue != null)
				lblStatusValue.setText(status);
		}
	}

	@Override
	public void setFocus() {
	}
}
