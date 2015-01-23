package saros;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;

import de.fu_berlin.inf.dpp.accountManagement.*;
import de.fu_berlin.inf.dpp.net.xmpp.JID;
import de.fu_berlin.inf.dpp.ui.util.CollaborationUtils;
import de.fu_berlin.inf.dpp.ui.util.selection.retriever.SelectionRetrieverFactory;

public class ASarosAccessor {
	public static final String TEACHER_ID = "pd1@saros-con.imp.fu-berlin.de";

	JID teacherJID;
	List<JID> contacts = new ArrayList();

	public ASarosAccessor() {
		  teacherJID = new JID(TEACHER_ID);
		  contacts.add(teacherJID);
		// TODO Auto-generated constructor stub
	}
	
	public void shareFixedProjectWithFixedUser() {
		  final IProject[] workspaceProjects = ResourcesPlugin.getWorkspace()
		            .getRoot().getProjects();
		 if (workspaceProjects == null || workspaceProjects.length == 0) {
			 System.out.println("No projects to share");
			 return;
		 }
		 List<IResource> resources = Collections.<IResource> singletonList(workspaceProjects[0]);
//		 final List<JID> contacts = SelectionRetrieverFactory
//		            .getSelectionRetriever(JID.class).getSelection();
//		 if (contacts == null || contacts.size() == 0) {
//			 System.out.println("No contacts to share with");
//			 return;
//		 }
//		 JID teacherJID = new JID(TEACHER_ID);
		
		 
		 CollaborationUtils.startSession(
                resources, contacts);
	}
	static ASarosAccessor instance;
	public static ASarosAccessor getInstance() {
		if (instance == null){
			instance = new ASarosAccessor();			
		}
		return instance;
	}

}
