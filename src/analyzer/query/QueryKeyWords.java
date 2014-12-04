package analyzer.query;

import java.util.HashMap;
import java.util.Map;

//NOT CURRENTLY USED
public enum QueryKeyWords implements QueryOperations{
	
	SELECT("select"),
	FROM("from"),
	WHERE("where"),
	//pipe is only used during execution denote we are piping selection from query 1 as the data to be used for the outer query
	PIPE(""),
	;
	
	public static final Map<String, QueryKeyWords> opmap=new HashMap<>();
	
	static{
		for(QueryKeyWords q:QueryKeyWords.values()) {
			opmap.put(q.getOperationStringName(),q);
			
		}
		
	}
	
	private String operationStringName;
	
	QueryKeyWords(String operationStringName) {
		this.operationStringName=operationStringName;
		
	}
	
	public boolean isSameOperation(String operation) {
		return this.operationStringName.equalsIgnoreCase(operation);
		
	}
	
	public static QueryKeyWords getOperationByStringName(String operation) {
		return opmap.get(operation.toLowerCase());
	}

	@Override
	public String getOperationStringName() {
		return this.operationStringName;
		
	}
	
	
}
