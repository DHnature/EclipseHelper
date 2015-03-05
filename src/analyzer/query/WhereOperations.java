package analyzer.query;

import java.util.HashMap;
import java.util.Map;

public enum WhereOperations implements QueryOperation{
	LESSTHAN("<",2),
	LESSTHANEQUAL("<=",2),
	GREATERTHAN(">",2),
	GREATERTHANEQUAL(">=",2),
	EQUAL("==",2),
	NOTEQUAL("!=",2),
	
	/**Can ignore many*/
	IGNORE_ATTRIBUTE("except",Integer.MAX_VALUE),
	
	AND("and",2),
	OR("or",2),
	
	MAX("max",1),
	MIN("min",1)
	
	
	;
	public static final Map<String, WhereOperations> opmap=new HashMap<>();
	
	static{
		for(WhereOperations op:WhereOperations.values()) {
			opmap.put(op.getOperationStringName(), op);
			
		}
		
	}
	
	private String opStrName;
	private int numOperand;
	
	WhereOperations(String opStrName, int numOperand) {
		this.opStrName=opStrName;
		this.numOperand=numOperand;
		
	}
	
	public String getOperationStringName() {
		return this.opStrName;
		
	}
	
	public int numOperand() {
		return this.numOperand;
		
	}
	
	@Override
	public boolean isSameOperation(String operation) {
		return this.opStrName.equalsIgnoreCase(operation);
		
	}
	
	public static WhereOperations getOperationFromString(String operation) {
		return opmap.get(operation.toLowerCase());
		
	}

	
}
