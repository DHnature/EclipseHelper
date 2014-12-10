package analyzer.query;

import java.util.HashMap;
import java.util.Map;

public enum SelectOperations implements QueryOperations{

	DOMINANT("dominant"),
	IGNOREPARTICIPANT("ignore participant"),


	;
	public static final Map<String, SelectOperations> opmap=new HashMap<>();

	static{
		for(SelectOperations op:SelectOperations.values()) {
			opmap.put(op.getOperationStringName(), op);

		}

	}

	private String opStrName;

	SelectOperations(String opStrName) {
		this.opStrName=opStrName;

	}

	public String getOperationStringName() {
		return this.opStrName;

	}

	@Override
	public boolean isSameOperation(String operation) {
		return this.opStrName.equalsIgnoreCase(operation);

	}

	public static SelectOperations getOperationFromString(String operation) {
		return opmap.get(operation.toLowerCase());

	}




}
