package saros;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.picocontainer.annotations.Inject;

import de.fu_berlin.inf.dpp.editor.internal.EditorAPI;
import de.fu_berlin.inf.dpp.negotiation.FileList;
import de.fu_berlin.inf.dpp.negotiation.FileListDiff;
import de.fu_berlin.inf.dpp.negotiation.IncomingProjectNegotiation;
import de.fu_berlin.inf.dpp.net.IConnectionManager;
import de.fu_berlin.inf.dpp.net.xmpp.JID;
import de.fu_berlin.inf.dpp.preferences.PreferenceUtils;
import de.fu_berlin.inf.dpp.project.SarosSessionManager;
import de.fu_berlin.inf.dpp.session.ISarosSession;
import de.fu_berlin.inf.dpp.session.ISarosSessionManager;
import de.fu_berlin.inf.dpp.ui.util.SWTUtils;
import de.fu_berlin.inf.dpp.ui.widgets.wizard.ProjectOptionComposite;
import de.fu_berlin.inf.dpp.ui.wizards.AddProjectToSessionWizard;
import de.fu_berlin.inf.dpp.ui.wizards.pages.EnterProjectNamePage;

public class EclipseHelperAddProjectToSessionWizard extends AddProjectToSessionWizard {
//	 @Inject
//	    private static SarosSessionManager sessionManager;
	  private static Logger LOG = Logger
		        .getLogger(AddProjectToSessionWizard.class);
	public static final String SEPARATOR = "_";
	IncomingProjectNegotiation process;
	JID peer;
	List<FileList> fileLists;	
	Field sessionManagerField;
	Field  connectionManagerField;
	Field namePageField;
	Field preferenceUtilsField;
	EclipseHelperEnterProjectNamePage myNamePage;
	String localProjectName;
	Method getOpenEditorsForSharedProjectsMethod, displaySaveDirtyEditorsDialogMethod, 
	createProjectsAndGetModifiedResourcesMethod, confirmOverwritingResourcesMethod, triggerProjectNegotiationMethod  ;
	public EclipseHelperAddProjectToSessionWizard(
			IncomingProjectNegotiation aProcess, JID aPeer,
			List<FileList> aFileLists) {
		super(aProcess, aPeer, aFileLists);
		process = aProcess;
		peer = aPeer;
		fileLists = aFileLists;
		try {
			sessionManagerField = AddProjectToSessionWizard.class.getDeclaredField("sessionManager");
			sessionManagerField.setAccessible(true);
			connectionManagerField = AddProjectToSessionWizard.class.getDeclaredField("connectionManager");
			connectionManagerField.setAccessible(true);
			namePageField = AddProjectToSessionWizard.class.getDeclaredField("namePage");
			namePageField.setAccessible(true);
			preferenceUtilsField = AddProjectToSessionWizard.class.getDeclaredField("preferenceUtils");
			preferenceUtilsField.setAccessible(true);
			Method[] methods = AddProjectToSessionWizard.class.getDeclaredMethods();
			for (Method method:methods) {
				String methodName = method.getName();
				if (methodName.equals("getOpenEditorsForSharedProjects")) {
					getOpenEditorsForSharedProjectsMethod = method;
					getOpenEditorsForSharedProjectsMethod.setAccessible(true);
				} else if (methodName.equals("displaySaveDirtyEditorsDialog")) {
					displaySaveDirtyEditorsDialogMethod = method;
					displaySaveDirtyEditorsDialogMethod.setAccessible(true);
				} else if (methodName.equals("createProjectsAndGetModifiedResources")) {
					createProjectsAndGetModifiedResourcesMethod = method;
					createProjectsAndGetModifiedResourcesMethod.setAccessible(true);
				} else if (methodName.equals("confirmOverwritingResources")) {
					confirmOverwritingResourcesMethod = method;
					confirmOverwritingResourcesMethod.setAccessible(true);
				} else if (methodName.equals("triggerProjectNegotiation")) {
					triggerProjectNegotiationMethod = method;
					triggerProjectNegotiationMethod.setAccessible(true);
				} 
			}

		} catch (NoSuchFieldException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setLocalProjectName();
		// TODO Auto-generated constructor stub
	}
	
	 @Override
	    public void addPages() {
//		 super.addPages();
		 
		 try {
			ISarosSessionManager sessionManager = (ISarosSessionManager) sessionManagerField.get(this);
			 IConnectionManager connectionManager = (IConnectionManager) connectionManagerField.get(this);
			
			myNamePage = (EclipseHelperEnterProjectNamePage) namePageField.get(this);

		 
	        ISarosSession session = sessionManager.getSarosSession();
	        PreferenceUtils preferenceUtils = (PreferenceUtils) preferenceUtilsField.get(this);
	        if (session == null)
	            return;

	        myNamePage = new EclipseHelperEnterProjectNamePage(session, connectionManager,
	            preferenceUtils, fileLists, peer, process.getProjectNames());
	        namePageField.set(this, myNamePage);

	        addPage(myNamePage);
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
	 }
	 
	 public void setLocalProjectName () {
		 String aPeerName = peer.getName();
		  
         Map<String, String> remoteProjectNames = process.getProjectNames();

		 
		 for (final FileList fileList : fileLists) {

	            final String projectID = fileList.getProjectID();
	            String remoteProjectName = remoteProjectNames.get(projectID);
	            localProjectName = aPeerName + SEPARATOR + remoteProjectName;

	            
	        }
	 }
	 /**
	     * Returns the project ids and their target project as selected in the
	     * {@link EnterProjectNamePage}.
	     * <p>
	     * This method must only be called after the page in completed state !
	     */
	     Map<String, IProject> getFixedTargetProjectMapping() {

	        final Map<String, IProject> result = new HashMap<String, IProject>();

	        for (final FileList list : fileLists) {
	            final String projectID = list.getProjectID();

//	            result.put(projectID, ResourcesPlugin.getWorkspace().getRoot()
//	                .getProject(namePage.getTargetProjectName(projectID)));
	            result.put(projectID, ResourcesPlugin.getWorkspace().getRoot()
		                .getProject(localProjectName));
	        }

	        return result;
	    }
	    
	     Collection<IEditorPart> getOpenEditorsForSharedProjectsReflecting(
	            Collection<IProject> projects) {

	           try {
	        	   Object[] myArgs = {projects};
	        	   return (Collection<IEditorPart>) getOpenEditorsForSharedProjectsMethod.invoke(this, myArgs);
	        	   
	           } catch (Exception e) {
	        	   e.printStackTrace();
	        	   return new ArrayList();
	           }
	        }
	      void displaySaveDirtyEditorsDialogMethodReflecting(final List<IEditorPart> dirtyEditors) {
	    	  
	    	  try {
	    		  Object[] myArgs = {dirtyEditors};
	    		  displaySaveDirtyEditorsDialogMethod.invoke(this,myArgs);
	        	   
	           } catch (Exception e) {
	        	   e.printStackTrace();
	           }
	     }
	       Map<String, FileListDiff> createProjectsAndGetModifiedResourcesReflecting(
	    	        final Map<String, IProject> projectMapping,
	    	        final boolean useVersionControl) throws CoreException {
	    	   try {
		    		  Object[] myArgs = {projectMapping, useVersionControl};
		    		 return  (Map<String, FileListDiff>) createProjectsAndGetModifiedResourcesMethod.invoke(this, myArgs);
		        	   
		           } catch (Exception e) {
		        	   e.printStackTrace();
		        	   return null;
		           }
	       }
	        boolean confirmOverwritingResourcesReflecting(
	    	        final Map<String, FileListDiff> modifiedResources) {
	        	try {
		    		  Object[] myArgs = {modifiedResources};
		    		 return  (boolean) confirmOverwritingResourcesMethod.invoke(this, myArgs);
		        	   
		           } catch (Exception e) {
		        	   e.printStackTrace();
		        	   return true;
		           }
	        	
	        }
	        void triggerProjectNegotiationReflecting(
	                final Map<String, IProject> targetProjectMapping,
	                final boolean useVersionControl,
	                final Collection<IEditorPart> editorsToClose) {
	        	try {
		    		  Object[] myArgs = {targetProjectMapping, useVersionControl, editorsToClose};
		    		  triggerProjectNegotiationMethod.invoke(this, myArgs);
		        	   
		           } catch (Exception e) {
		        	   e.printStackTrace();
		        	  
		           }
	        	
	        }
	        // closing window calls this automatically, so override super class if we have done perform finish
	        @Override
	        public boolean performCancel() {
	        	if (performedFinish) {
	        		performedFinish = false;
	        		return true;
	        	} else {
	        		return super.performCancel();
	        	}
	        	
	        }
	        
	        boolean performedFinish = false;
	    
	 @Override
	    public boolean performFinish() {
		 performedFinish = true;
// we do not need a name page
//	        if (namePage == null)
//	            return true;

	        final Map<String, IProject> targetProjectMapping = getFixedTargetProjectMapping();
// we can simply make it some default value
	        final boolean useVersionControl = true;

//	        final boolean useVersionControl = namePage.useVersionControl();
	        

	        final Collection<IEditorPart> openEditors = getOpenEditorsForSharedProjectsReflecting(targetProjectMapping
	            .values());

	        final List<IEditorPart> dirtyEditors = new ArrayList<IEditorPart>();

	        boolean containsDirtyEditors = false;

	        for (IEditorPart editor : openEditors) {
	            if (editor.isDirty()) {
	                containsDirtyEditors = true;
	                dirtyEditors.add(editor);
	            }
	        }

	        if (containsDirtyEditors) {
	            SWTUtils.runSafeSWTAsync(LOG, new Runnable() {
	                @Override
	                public void run() {
	                    if (EclipseHelperAddProjectToSessionWizard.this.getShell().isDisposed())
	                        return;

	                    displaySaveDirtyEditorsDialogMethodReflecting(dirtyEditors);

	                }
	            });

	            return false;
	        }

	        final Map<String, FileListDiff> modifiedResources;

	        try {
	            modifiedResources = createProjectsAndGetModifiedResourcesReflecting(
	                targetProjectMapping, useVersionControl);
	        } catch (CoreException e) {
	            LOG.error("could not compute local file list", e);
	            MessageDialog.openError(getShell(), "Error computing file list",
	                "Could not compute local file list: " + e.getMessage());
	            return false;
	        } catch (RuntimeException e) {
	            LOG.error("internal error while computing file list", e);
	            MessageDialog.openError(getShell(), "Error computing file list",
	                "Internal error: " + e.getMessage());

	            return false;
	        }

	        if (!confirmOverwritingResourcesReflecting(modifiedResources))
	            return false;

	        triggerProjectNegotiationReflecting(targetProjectMapping, useVersionControl,
	            openEditors);

	        return true;
	    }
	 

}
