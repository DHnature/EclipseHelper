package edu.cmu.scs.fluorite.commands;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.ui.IEditorPart;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import edu.cmu.scs.fluorite.model.EventRecorder;

public class ExceptionCommand extends AbstractCommand  {

	public static final String XML_Exception_Tag = "exceptionString";
	
	public ExceptionCommand()
	{
		
	}
	
	public ExceptionCommand(String exceptionText)
	{
		mExceptionText = exceptionText;
	}
	
	private String mExceptionText;
	@Override
	public boolean execute(IEditorPart target) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void dump() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Map<String, String> getAttributesMap() {
		// TODO Auto-generated method stub
		Map<String, String> attrMap = new HashMap<String, String>();
		attrMap.put("type", "Exception");
		//attrMap.put("text", mExceptionText);
		return attrMap;
	}

	@Override
	public Map<String, String> getDataMap() {
		Map<String, String> dataMap = new HashMap<String, String>();
		if (mExceptionText != null)
			dataMap.put(XML_Exception_Tag, mExceptionText);

		return dataMap;
	}
	
	
	@Override
	public void createFrom(Element commandElement) {
		super.createFrom(commandElement);
		
		NodeList nodeList = null;

		if ((nodeList = commandElement.getElementsByTagName(XML_Exception_Tag)).getLength() > 0) {
			Node textNode = nodeList.item(0);
			mExceptionText =textNode.getTextContent();
		}
	}

	@Override
	public String getCommandType() {
		// TODO Auto-generated method stub
		return "ExceptionCommand";
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Exception";
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getCategory() {
		// TODO Auto-generated method stub
		return EventRecorder.MacroCommandCategory;
	}

	@Override
	public String getCategoryID() {
		// TODO Auto-generated method stub
		return EventRecorder.MacroCommandCategoryID;
	}

	@Override
	public boolean combine(ICommand anotherCommand) {
		// TODO Auto-generated method stub
		return false;
	}

}
