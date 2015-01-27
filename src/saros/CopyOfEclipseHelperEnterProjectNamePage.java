package saros;

import java.lang.reflect.Field;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;

import de.fu_berlin.inf.dpp.negotiation.FileList;
import de.fu_berlin.inf.dpp.net.IConnectionManager;
import de.fu_berlin.inf.dpp.net.xmpp.JID;
import de.fu_berlin.inf.dpp.preferences.PreferenceUtils;
import de.fu_berlin.inf.dpp.session.ISarosSession;
import de.fu_berlin.inf.dpp.ui.Messages;
import de.fu_berlin.inf.dpp.ui.preferencePages.GeneralPreferencePage;
import de.fu_berlin.inf.dpp.ui.widgets.wizard.ProjectOptionComposite;
import de.fu_berlin.inf.dpp.ui.widgets.wizard.events.ProjectNameChangedEvent;
import de.fu_berlin.inf.dpp.ui.widgets.wizard.events.ProjectOptionListener;
import de.fu_berlin.inf.dpp.ui.wizards.AddProjectToSessionWizard;
import de.fu_berlin.inf.dpp.ui.wizards.pages.EnterProjectNamePage;

public class CopyOfEclipseHelperEnterProjectNamePage extends EnterProjectNamePage {
	List<FileList> fileLists;
	Map<String, String> remoteProjectNames;
      Map<String, ProjectOptionComposite> superProjectOptionComposites; 
     Field projectOptionCompositesField;
	public CopyOfEclipseHelperEnterProjectNamePage(ISarosSession session,
			IConnectionManager connectionManager,
			PreferenceUtils aPreferenceUtils, List<FileList> aFileLists,
			JID peer, Map<String, String> aRemoteProjectNames) {
		super(session, connectionManager, aPreferenceUtils, aFileLists, peer,
				aRemoteProjectNames);
		fileLists = aFileLists;
		remoteProjectNames = aRemoteProjectNames;
		// TODO Auto-generated constructor stub
		try {
			projectOptionCompositesField = EnterProjectNamePage.class.getDeclaredField("projectComposites");
			projectOptionCompositesField.setAccessible(true);
			superProjectOptionComposites = (Map<String, ProjectOptionComposite>) projectOptionCompositesField.get(projectOptionCompositesField);
		} catch (NoSuchFieldException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
    public void createControl(Composite parent) {
        GridData gridData;

        Composite composite = new Composite(parent, SWT.NONE);
        setControl(composite);

        composite.setLayout(new GridLayout());

        gridData = new GridData(SWT.BEGINNING, SWT.FILL, false, false);
        composite.setLayoutData(gridData);

        TabFolder tabFolder = new TabFolder(composite, SWT.TOP);

        /*
         * grabExcessHorizontalSpace must be true or the tab folder will not
         * display a scroll bar if the wizard is resized
         */
        gridData = new GridData(SWT.BEGINNING, SWT.BEGINNING, true, false);

        /*
         * FIXME this does not work and the wizard may "explode" if too many
         * remote projects are presented
         */
        gridData.widthHint = 400;
        tabFolder.setLayoutData(gridData);

        for (final FileList fileList : fileLists) {

            final String projectID = fileList.getProjectID();

            TabItem tabItem = new TabItem(tabFolder, SWT.NONE);
            tabItem.setText(remoteProjectNames.get(projectID));

            ProjectOptionComposite tabComposite = new ProjectOptionComposite(
                tabFolder, projectID);

            tabItem.setControl(tabComposite);

            superProjectOptionComposites.put(projectID, tabComposite);
        }

        Composite vcsComposite = new Composite(composite, SWT.NONE);
        vcsComposite.setLayout(new GridLayout());

        disableVCSCheckbox = new Button(vcsComposite, SWT.CHECK);
        disableVCSCheckbox
            .setText(GeneralPreferencePage.DISABLE_VERSION_CONTROL_TEXT);
        disableVCSCheckbox.setSelection(!preferenceUtils.useVersionControl());

        Button explainButton = new Button(vcsComposite, SWT.PUSH);
        explainButton.setText("Explain");

        final Label explanation = new Label(vcsComposite, SWT.NONE);
        explanation.setEnabled(false);
        explanation.setText(Messages.Explain_version_control);
        explanation.setVisible(false);
        explainButton.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                explanation.setVisible(true);
            }
        });
        explainButton.pack();
        explanation.pack();
        updateConnectionStatus();

        attachListeners();

        // invokes updatePageComplete for every project id
        preselectProjectNames();
    }
	private void attachListeners() {
        for (ProjectOptionComposite composite : superProjectOptionComposites
            .values()) {

            composite.addProjectOptionListener(new ProjectOptionListener() {
                @Override
                public void projectNameChanged(ProjectNameChangedEvent event) {
                    updatePageComplete(event.projectID);
                }
            });
        }
    }
	private void updatePageComplete(String currentProjectID) {

        // first update all others because errors may be no longer valid

        for (String projectID : superProjectOptionComposites.keySet()) {
            if (projectID.equals(currentProjectID))
                continue;

            updateProjectSelectionStatus(projectID);
        }

        /*
         * this assumes the focus on the project option composite with the
         * current project id !
         */

        currentErrors.remove(currentProjectID);

        /*
         * do not generate errors for empty project names as long as the user is
         * on the current tab as it would be confusing
         */
        if (projectOptionComposites.get(currentProjectID).getProjectName()
            .isEmpty()) {
            showLatestErrorMessage();
            setPageComplete(false);
            return;
        }

        updateProjectSelectionStatus(currentProjectID);

        if (!currentErrors.isEmpty()) {
            showLatestErrorMessage();
            setPageComplete(false);
            return;
        }

        setErrorMessage(null);

        String warningMessage = findAndReportProjectArtifacts();

        if (!unsupportedCharsets.isEmpty()) {
            if (warningMessage == null)
                warningMessage = "";
            else
                warningMessage += "\n";

            warningMessage += "At least one remote project contains files "
                + "with a character encoding that is not available on this "
                + "Java platform. "
                + "Working on these projects may result in data loss or "
                + "corruption.\n"
                + "The following character encodings are not available: "
                + StringUtils.join(unsupportedCharsets, ", ");
        }

        setMessage(warningMessage, WARNING);

        setPageComplete(true);
    }
	private void updateProjectSelectionStatus(String projectID) {
        String errorMessage = isProjectSelectionValid(projectID);

        if (errorMessage != null)
            currentErrors.put(projectID, errorMessage);
        else
            currentErrors.remove(projectID);
    }
	/**
     * Checks if the project options for the given project id are valid.
     * 
     * @return an error message if the options are not valid, otherwise the
     *         error message is <code>null</code>
     */
    private String isProjectSelectionValid(String projectID) {

        ProjectOptionComposite projectOptionComposite = projectOptionComposites
            .get(projectID);

        String projectName = projectOptionComposite.getProjectName();

        if (projectName.isEmpty())
            return Messages.EnterProjectNamePage_set_project_name
                + " for remote project " + remoteProjectNames.get(projectID);

        IStatus status = ResourcesPlugin.getWorkspace().validateName(
            projectName, IResource.PROJECT);

        if (!status.isOK())
            // FIXME display remote project name
            return status.getMessage();

        List<String> currentProjectNames = getCurrentProjectNames(projectID);

        if (currentProjectNames.contains(projectName))
            // FIXME display the project ... do not let the user guess
            return MessageFormat.format(
                Messages.EnterProjectNamePage_error_projectname_in_use,
                projectName);

        IProject project = ResourcesPlugin.getWorkspace().getRoot()
            .getProject(projectName);

        if (projectOptionComposite.useExistingProject() && !project.exists())
            // FIXME crap error message
            return Messages.EnterProjectNamePage_error_wrong_name + " "
                + projectOptionComposite.getProjectName();

        if (!projectOptionComposite.useExistingProject() && project.exists())
            // FIXME we are working with tabs ! always display the remote
            // project name
            return MessageFormat.format(
                Messages.EnterProjectNamePage_error_projectname_exists,
                projectName);

        return null;
    }

}
