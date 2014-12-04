package analyzer.query;

import java.util.HashMap;
import java.util.Map;

public enum QueryOperation implements QueryOperations{

	SELECT("select"),
	ALL("*"),
	ATTRIBUTE("attribute"),
	FROM("from"),
	WHERE("where"),
	OPERATION("operation"),
	CONSTANT("constant"),
	DOMINANT("dominant"),
	SIGNIFICANT("significant"),
	EXCEPT("except"),
	AND("and")
	;

	public static final Map<String, QueryOperation> opmap=new HashMap<>();

	static{
		for(QueryOperation q:QueryOperation.values()) {
			opmap.put(q.getOperationStringName(),q);

		}

	}


	private String operationStringName;

	QueryOperation(String operationStringName) {
		this.operationStringName=operationStringName;

	}

	public boolean isSameOperation(String operation) {
		return this.operationStringName.equalsIgnoreCase(operation);

	}

	public static QueryOperation getOperationFromStringName(String operation) {
		return opmap.get(operation);
		
	}

	@Override
	public String getOperationStringName() {
		return this.operationStringName;

	}
}
