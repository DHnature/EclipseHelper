package analyzer.query;

import java.util.HashMap;
import java.util.Map;

/**Enum for use with the data miner
 * 
 * 
 * */
public enum SelectAttr {

	EDIT("edit",null,null),//INSERTION+DELETION
	DEBUG("debug","getDebugList","setDebugList"),
	NAVIGATION("navigation","getNavigationList","setNavigationList"),
	REMOVE("remove","getRemoveList","setRemoveList"),
	FOCUS("focus","getFocusList","setFocusList"),
	
	INSERTION("insertion","getInsertionList","setInsertionList"),
	DELETION("deletion","getDeletionList","setDeletionList"),
	
	PREDICTION("prediction","getPredictions","setPredictions"),
	CORRECTION("correction","getPredictionCorrections","setPredictionCorrections")
	
	;
	
	public static Map<String, SelectAttr> attrmap=new HashMap<>();
	
	static{
		for(SelectAttr a:SelectAttr.values())
			attrmap.put(a.toString(), a);
		
	}
	
	
	private String name,getterName,setterName;
	
	SelectAttr(String name,String getterName, String setterName) {
		this.name=name;
		this.getterName=getterName;
		this.setterName=setterName;
		
	}
	
	/**Convert this feature to its string representation*/
	@Override
	public String toString() {
		return name;
		
	}
	
	/**Check if the SelectAttribute in String s is the same as the feature*/
	public boolean isSelectAttr(String s) {
		return this.name.equals(s);
		
	}
	
	public String getGetterName() {
		return this.getterName;
		
	}
	
	public String getSetterName(){
		return this.setterName;
		
	}
	
	/**Static method to convert string s to a SelectAttribute enum, null if no SelectAttribute under than name*/
	public static SelectAttr getAttributeFromString(String s) {
		return attrmap.get(s.toLowerCase());
		
	}
	
}
