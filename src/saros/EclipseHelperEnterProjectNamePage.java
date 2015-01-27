package saros;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URI;
import java.nio.charset.Charset;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.wizard.WizardPage;
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
import de.fu_berlin.inf.dpp.ui.ImageManager;
import de.fu_berlin.inf.dpp.ui.Messages;
import de.fu_berlin.inf.dpp.ui.preferencePages.GeneralPreferencePage;
import de.fu_berlin.inf.dpp.ui.util.SWTUtils;
import de.fu_berlin.inf.dpp.ui.views.SarosView;
import de.fu_berlin.inf.dpp.ui.widgets.wizard.ProjectOptionComposite;
import de.fu_berlin.inf.dpp.ui.widgets.wizard.events.ProjectNameChangedEvent;
import de.fu_berlin.inf.dpp.ui.widgets.wizard.events.ProjectOptionListener;
import de.fu_berlin.inf.dpp.ui.wizards.AddProjectToSessionWizard;
import de.fu_berlin.inf.dpp.ui.wizards.pages.EnterProjectNamePage;

public class EclipseHelperEnterProjectNamePage extends EnterProjectNamePage {
	private static final Logger log = Logger
	        .getLogger(EnterProjectNamePage.class.getName());

	    private final List<FileList> fileLists;
	    private final JID peer;

	    private final ISarosSession session;

	    private final Map<String, String> remoteProjectNames;
	    private final Map<String, ProjectOptionComposite> projectOptionComposites = new HashMap<String, ProjectOptionComposite>();

	    /** Map containing the current error messages for every project id. */
	    private Map<String, String> currentErrors = new HashMap<String, String>();

	    private Button disableVCSCheckbox;

	    private IConnectionManager connectionManager;

	    private PreferenceUtils preferenceUtils;

	    private boolean flashState;

	    private final Set<String> unsupportedCharsets = new HashSet<String>();
	    String remoteProjectName;

	    /**
	     * @param remoteProjectNames
	     *            since the <code>projectID</code> is no longer the name of the
	     *            project this mapping is necessary to display the names on
	     *            host/inviter side instead of ugly random numbers projectID =>
	     *            projectName
	     */
	    public EclipseHelperEnterProjectNamePage(ISarosSession session,
	        IConnectionManager connectionManager, PreferenceUtils preferenceUtils,
	        List<FileList> fileLists, JID peer,
	        Map<String, String> remoteProjectNames) {
	    	super(session, connectionManager, preferenceUtils, fileLists, peer, remoteProjectNames);

	        this.session = session;
	        this.connectionManager = connectionManager;
	        this.preferenceUtils = preferenceUtils;
	        this.peer = peer;
	        this.remoteProjectNames = remoteProjectNames;

	        // TODO show per project
	        for (FileList fileList : fileLists)
	            unsupportedCharsets.addAll(getUnsupportedCharsets(fileList
	                .getEncodings()));

	        this.fileLists = fileLists;

	        setPageComplete(false);
	        setTitle(Messages.EnterProjectNamePage_title2);
	    }

	    /**
	     * Returns the name of the project to use during the shared session.
	     */
	    public String getTargetProjectName(String projectID) {
	        return projectOptionComposites.get(projectID).getProjectName();
	    }

	    public boolean useVersionControl() {
	        return !disableVCSCheckbox.getSelection();
	    }

	    /**
	     * @return <code>true</code> if the synchronization option chosen by the
	     *         user could lead to overwriting project resources,
	     *         <code>false</code> otherwise.
	     */
	    public boolean overwriteResources(String projectID) {
	        return projectOptionComposites.get(projectID).useExistingProject();
	    }

	    @Override
	    public void performHelp() {
	        try {
	            Desktop.getDesktop().browse(
	                URI.create(Messages.EnterProjectNamePage_saros_url));
	        } catch (IOException e) {
	            SarosView.showNotification(Messages.EnterProjectNamePage_faq,
	                Messages.EnterProjectNamePage_error_browser_open);
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
	            // pd
	             remoteProjectName = remoteProjectNames.get(projectID);
	            tabItem.setText(remoteProjectNames.get(projectID));

	            ProjectOptionComposite tabComposite = new ProjectOptionComposite(
	                tabFolder, projectID);

	            tabItem.setControl(tabComposite);

	            projectOptionComposites.put(projectID, tabComposite);
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
	        for (ProjectOptionComposite composite : projectOptionComposites
	            .values()) {

	            composite.addProjectOptionListener(new ProjectOptionListener() {
	                @Override
	                public void projectNameChanged(ProjectNameChangedEvent event) {
	                    updatePageComplete(event.projectID);
	                }
	            });
	        }
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
	    
	    // not sure we really need this
	    
	     String isProjectNameValid(String projectName) {

	       
	        IStatus status = ResourcesPlugin.getWorkspace().validateName(
	            projectName, IResource.PROJECT);

	        if (!status.isOK())
	            // FIXME display remote project name
	            return status.getMessage();
	        // this is always emptyt

//	        List<String> currentProjectNames = getCurrentProjectNames(projectID);

//	        if (currentProjectNames.contains(projectName))
//	            // FIXME display the project ... do not let the user guess
//	            return MessageFormat.format(
//	                Messages.EnterProjectNamePage_error_projectname_in_use,
//	                projectName);

	        IProject project = ResourcesPlugin.getWorkspace().getRoot()
	            .getProject(projectName);

//	        if (projectOptionComposite.useExistingProject() && !project.exists())
//	            // FIXME crap error message
//	            return Messages.EnterProjectNamePage_error_wrong_name + " "
//	                + projectOptionComposite.getProjectName();
//
//	        if (!projectOptionComposite.useExistingProject() && project.exists())
//	            // FIXME we are working with tabs ! always display the remote
//	            // project name
//	            return MessageFormat.format(
//	                Messages.EnterProjectNamePage_error_projectname_exists,
//	                projectName);

	        return null;
	    }

	    private void updatePageComplete(String currentProjectID) {

	        // first update all others because errors may be no longer valid

	        for (String projectID : projectOptionComposites.keySet()) {
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

	    /**
	     * Shows the 'latest' error message (random) if there is currently anyone in
	     * the wizard page. If there is no error message present the error message
	     * status of the wizard page is cleared.
	     */
	    private void showLatestErrorMessage() {
	        if (!currentErrors.isEmpty())
	            setErrorMessage(currentErrors.entrySet().iterator().next()
	                .getValue());
	        else
	            setErrorMessage(null);
	    }

	    private void updateProjectSelectionStatus(String projectID) {
	        String errorMessage = isProjectSelectionValid(projectID);

	        if (errorMessage != null)
	            currentErrors.put(projectID, errorMessage);
	        else
	            currentErrors.remove(projectID);
	    }

	    /**
	     * Scans the current Eclipse Workspace for project artifacts.
	     * 
	     * @return string containing a warning message if artifacts are found,
	     *         <code>null</code> otherwise
	     */
	    private String findAndReportProjectArtifacts() {
	        IPath workspacePath = ResourcesPlugin.getWorkspace().getRoot()
	            .getLocation();

	        if (workspacePath == null)
	            return null;

	        File workspaceDirectory = workspacePath.toFile();

	        List<String> dirtyProjectNames = new ArrayList<String>();

	        for (ProjectOptionComposite composite : projectOptionComposites
	            .values()) {

	            if (composite.useExistingProject())
	                continue;

	            String projectName = composite.getProjectName();

	            if (projectName.isEmpty())
	                continue;

	            if (new File(workspaceDirectory, projectName).exists())
	                dirtyProjectNames.add(projectName);
	        }

	        String warningMessage = null;

	        if (!dirtyProjectNames.isEmpty()) {
	            warningMessage = MessageFormat.format(
	                Messages.EnterProjectNamePage_warning_project_artifacts_found,
	                StringUtils.join(dirtyProjectNames, ", "));
	        }

	        return warningMessage;
	    }

	    /**
	     * Preselect project names for the remote projects. This method will either
	     * use an existing shared project and disable the option to change the
	     * preselected values or just generate a unique project name for new
	     * projects.
	     * <p>
	     * This method does <b>not</b> preselect values for existing projects unless
	     * they are already shared ! This can do more harm than indented when the
	     * user is so eager and just ignores all warnings that will be presented
	     * before he can finish the wizard.
	     */

	    private void preselectProjectNames() {

	        final Set<String> reservedProjectNames = new HashSet<String>();

	        for (Entry<String, ProjectOptionComposite> entry : projectOptionComposites
	            .entrySet()) {

	            String projectID = entry.getKey();
	            ProjectOptionComposite projectOptionComposite = entry.getValue();

	            de.fu_berlin.inf.dpp.filesystem.IProject project = session
	                .getProject(projectID);

	            if (project == null)
	                continue;

	            projectOptionComposite.setProjectName(true, project.getName());
	            projectOptionComposite.setEnabled(false);
	            reservedProjectNames.add(project.getName());
	        }

	        for (Entry<String, ProjectOptionComposite> entry : projectOptionComposites
	            .entrySet()) {

	            String projectID = entry.getKey();
	            ProjectOptionComposite projectOptionComposite = entry.getValue();

	            de.fu_berlin.inf.dpp.filesystem.IProject project = session
	                .getProject(projectID);

	            if (project != null)
	                continue;

	            String projectNameProposal = findProjectNameProposal(
	                remoteProjectNames.get(projectID),
	                reservedProjectNames.toArray(new String[0]));

	            projectOptionComposite.setProjectName(false, projectNameProposal);
	            reservedProjectNames.add(projectNameProposal);
	        }
	    }

	    private List<String> getCurrentProjectNames(String... projectIDsToExclude) {
	        final List<String> currentProjectNames = new ArrayList<String>();

	        final Set<String> excludedProjectIDs = new HashSet<String>(
	            Arrays.asList(projectIDsToExclude));

	        for (Entry<String, ProjectOptionComposite> entry : projectOptionComposites
	            .entrySet()) {

	            String projectID = entry.getKey();
	            ProjectOptionComposite projectOptionComposite = entry.getValue();

	            if (excludedProjectIDs.contains(projectID))
	                continue;

	            currentProjectNames.add(projectOptionComposite.getProjectName());
	        }

	        return currentProjectNames;
	    }

	    /**
	     * get transfer mode and set header information of the wizard.
	     */
	    private void updateConnectionStatus() {

	        switch (connectionManager.getTransferMode(this.peer)) {
	        case SOCKS5_MEDIATED:
	            if (preferenceUtils.isLocalSOCKS5ProxyEnabled())
	                setDescription(Messages.EnterProjectNamePage_description_socks5proxy);
	            else
	                setDescription(Messages.EnterProjectNamePage_description_file_transfer);
	            setImageDescriptor(ImageManager
	                .getImageDescriptor("icons/wizban/socks5m.png"));
	            break;

	        case SOCKS5_DIRECT:
	            setDescription(Messages.EnterProjectNamePage_description_direct_filetranfser);
	            setImageDescriptor(ImageManager
	                .getImageDescriptor("icons/wizban/socks5.png"));
	            break;

	        case NONE:
	            // lost data connection
	            break;

	        case IBB:
	            String speedInfo = "";

	            if (preferenceUtils.forceFileTransferByChat()) {
	                setDescription(MessageFormat
	                    .format(
	                        Messages.EnterProjectNamePage_direct_filetransfer_deactivated,
	                        speedInfo));
	            } else {
	                setDescription(MessageFormat.format(
	                    Messages.EnterProjectNamePage_direct_filetransfer_nan,
	                    speedInfo));
	            }
	            startIBBLogoFlash();
	            break;
	        default:
	            setDescription(Messages.EnterProjectNamePage_unknown_transport_method);
	            break;
	        }
	    }

	    /**
	     * Starts and maintains a timer that will flash two IBB logos to make the
	     * user aware of the warning.
	     */
	    private void startIBBLogoFlash() {

	        final Timer timer = new Timer();

	        timer.schedule(new TimerTask() {

	            @Override
	            public void run() {
	                SWTUtils.runSafeSWTSync(log, new Runnable() {

	                    @Override
	                    public void run() {

	                        if (EclipseHelperEnterProjectNamePage.this.getControl().isDisposed()) {
	                            timer.cancel();
	                            return;
	                        }

	                        flashState = !flashState;
	                        if (flashState)
	                            setImageDescriptor(ImageManager
	                                .getImageDescriptor("icons/wizban/ibb.png"));
	                        else
	                            setImageDescriptor(ImageManager
	                                .getImageDescriptor("icons/wizban/ibbFaded.png"));
	                    }
	                });
	            }
	        }, 0, 1000);
	    }

	    /**
	     * Tests if the given project name does not already exist in the current
	     * workspace.
	     * 
	     * @param projectName
	     *            the name of the project
	     * @param reservedProjectNames
	     *            further project names that are already reserved even if the
	     *            projects do not exist physically
	     * @return <code>true</code> if the project name does not exist in the
	     *         current workspace and does not exist in the reserved project
	     *         names, <code>false</code> if the project name is an empty string
	     *         or the project already exists in the current workspace
	     */
	    boolean projectNameIsUnique(String projectName,
	        String... reservedProjectNames) {

	        if (projectName == null)
	            throw new NullPointerException("projectName is null");

	        if (projectName.length() == 0)
	            return false;

	        Set<IProject> projects = new HashSet<IProject>(
	            Arrays.asList(ResourcesPlugin.getWorkspace().getRoot()
	                .getProjects()));

	        for (String reservedProjectName : reservedProjectNames) {
	            projects.add(ResourcesPlugin.getWorkspace().getRoot()
	                .getProject(reservedProjectName));
	        }

	        return !projects.contains(ResourcesPlugin.getWorkspace().getRoot()
	            .getProject(projectName));
	    }

	    /**
	     * Proposes a project name based on the existing project names in the
	     * current workspace. The proposed project name is unique.
	     * 
	     * @param projectName
	     *            project name which shall be checked
	     * @param reservedProjectNames
	     *            additional project names that should not be returned when
	     *            finding a name proposal for a project
	     * @return a unique project name based on the original project name
	     */
	    String findProjectNameProposal(String projectName,
	        String... reservedProjectNames) {

	        int idx;

	        for (idx = projectName.length() - 1; idx >= 0
	            && Character.isDigit(projectName.charAt(idx)); idx--) {
	            // NOP
	        }

	        String newProjectName;

	        if (idx < 0)
	            newProjectName = "";
	        else
	            newProjectName = projectName.substring(0, idx + 1);

	        if (idx == projectName.length() - 1)
	            idx = 2;
	        else {
	            try {
	                idx = Integer.valueOf(projectName.substring(idx + 1));
	            } catch (NumberFormatException e) {
	                idx = 2;
	            }
	        }

	        projectName = newProjectName;

	        while (!projectNameIsUnique(projectName, reservedProjectNames)) {
	            projectName = newProjectName + idx;
	            idx++;
	        }

	        return projectName;
	    }

	    /**
	     * Returns all charsets from a given set that are not available on the
	     * current JVM.
	     */
	    private Set<String> getUnsupportedCharsets(Set<String> charsets) {
	        Set<String> missingCharsets = new HashSet<String>();

	        for (String charset : charsets) {
	            try {
	                Charset.forName(charset);
	            } catch (Exception e) {
	                missingCharsets.add(charset);
	            }
	        }

	        return missingCharsets;
	    }
//	    public String setLocalProjectName () {
//			 String aPeerName = peer.getName();
//			 String aProjectName = aPeerName + "_" + remoteProjectName;
//			 return isProjectSelectionValid(String projectID);
//			 
//		 }

}
