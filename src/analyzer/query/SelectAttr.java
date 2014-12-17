package analyzer.query;

import java.util.HashMap;
import java.util.Map;

/**Enum for use with the data miner
 * 
 * 
 * */
public enum SelectAttr {

	EDIT("edit",null),//INSERTION+DELETION
	DEBUG("debug","DebugList"),
	NAVIGATION("navigation","NavigationList"),
	REMOVE("remove","RemoveList"),
	FOCUS("focus","FocusList"),
	
	INSERTION("insertion","InsertionList"),
	DELETION("deletion","DeletionList"),
	
	PREDICTION("prediction","Predictions"),
	CORRECTION("correction","PredictionCorrections"),
	TIMESTAMP("timestamp","TimeStampList")
	
	;
	
	public static Map<String, SelectAttr> attrmap=new HashMap<>();
	
	static{
		for(SelectAttr a:SelectAttr.values())
			attrmap.put(a.toString(), a);
		
	}
	
	
	private String name,methodName;
	
	SelectAttr(String name,String methodName) {
		this.name=name;
		this.methodName=methodName;
		
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
		return "get"+this.methodName;
		
	}
	
	public String getSetterName(){
		return "set"+this.methodName;
		
	}
	
	/**Static method to convert string s to a SelectAttribute enum, null if no SelectAttribute under than name*/
	public static SelectAttr getAttributeFromString(String s) {
		return attrmap.get(s.toLowerCase());
		
	}
	
}
