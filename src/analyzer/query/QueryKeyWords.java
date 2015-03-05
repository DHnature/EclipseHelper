package analyzer.query;

import java.util.HashMap;
import java.util.Map;

//NOT CURRENTLY USED
public enum QueryKeyWords implements QueryOperation{
	
	SELECT("select"),
	FROM("from"),
	WHERE("where"),
	IGNORE("ignore"),
	LIMIT("limit"),
	OFFSET("offset")
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
