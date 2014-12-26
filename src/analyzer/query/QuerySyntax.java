package analyzer.query;

import java.util.HashMap;
import java.util.Map;

public enum QuerySyntax implements QueryOperation{

	COMMA(","),
	CLOSEPAREN(")"),
	OPENPAREN("("),
	
	;
	
	private static Map<String, QuerySyntax> opmap=new HashMap<>();
	
	static{
		for(QuerySyntax s:QuerySyntax.values()) {
			opmap.put(s.getOperationStringName(), s);
			
		}
		
	}
	
	private String name;
	
	QuerySyntax(String name) {
		this.name=name;
		
	}
	
	@Override
	public boolean isSameOperation(String operation) {
		return this.getOperationStringName().equals(operation);
		
	}

	@Override
	public String getOperationStringName() {
		return this.name;
		
	}

	public static QuerySyntax getOperationByStringName(String operation) {
		return opmap.get(operation.toLowerCase());
	}
}
