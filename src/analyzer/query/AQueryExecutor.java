package analyzer.query;

import java.util.List;
import java.util.Map;
import analyzer.ParticipantTimeLine;

public class AQueryExecutor implements QueryExecutor{
	private Map<String, ParticipantTimeLine> data;
	
	private List<QueryOperations> operations;
	private List<String[]> parameters;

	private int instructionptr;
	
	private List<Integer> latestSelectResult;
	@Override
	public boolean execute(List<QueryOperations> operations,
			List<String[]> parameters,Map<String, ParticipantTimeLine> data) {
		this.data=data;
		this.parameters=parameters;
		this.operations=operations;
		this.instructionptr=0;
		
		decodeAndExecute();
		
		return false;
	}

	private void decodeAndExecute() {
		for(;instructionptr<operations.size();instructionptr++) {
			QueryOperations op=operations.get(instructionptr);
			
			if(op instanceof WhereOperations) {
				whereDecodeAndExecute(++instructionptr);
				
			} else {
				
				
			}
			
		}
		
		
	}
	
	/**Select should only be applied in the last stage*/
	private void selectDecodeAndExecute() {
		
		
	}
	
	private void whereDecodeAndExecute(int instructionptr) {
		
		for(;instructionptr<this.operations.size() 
				&& isKeyWord(this.operations.get(instructionptr));instructionptr++) {
			
			
			
			
		}
		
		//Go through and execute each instruction
		switch(op) {
		
		case LESSTHAN:
		case LESSTHANEQUAL:
		case GREATERTHAN:
		case GREATERTHANEQUAL:
		case EQUAL:
		case NOTEQUAL:
		
		/**For dominant and significant*/
		case IGNORE:
		case DOMINANT:
		case SIGNIFICANT:
		
		default:
		}
		
		//Now collect the result together
		if() {
			
			
		}
	}
	
	private List<Integer> execute0OperandWhere() {
		switch(this.operations.get(instructionptr)) {
		case AND:
		
		break;
		case OR:
		
		default:
			System.out.println("Not zero operand instruction");
		}
		
		
	}
	
	private List<Integer> execute1OperandWhere() {
		
		
	}
	
	private List<Integer> execute2OperandWhere() {
		
		
	}

	private boolean isKeyWord(QueryOperations q) {
		return q instanceof QueryKeyWords;
		
	}
}
