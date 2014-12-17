package analyzer.query;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class AQueryParser implements QueryParser{
	//Index i in translatedInstruction will have a corresponding location at i in parameters
	//the String[] in location i in parameters are the inputs for the instruction
	List<QueryOperation> translatedInstructions;
	List<String[]> parameters;
	
	public AQueryParser() {
		parameters=new ArrayList<String[]>();
		translatedInstructions=new ArrayList<QueryOperation>();
		
	}

	/**parse the string query
	 * 
	 * 
	 */
	@Override
	public void parseQuery(String query) {
		//parse the instructions
		List<String> instructions=parse(query);
		
		//translate into instruction usable by executor
		translate(instructions);
		
		
		
		System.out.println(this.translatedInstructions);
		for(String[] arr: this.parameters)
			System.out.println(Arrays.asList(arr));
		
	}
	
	/**Parse into blocks of instruction. Split the whole string by space and commas
	 * 
	 * 
	 * */
	private List<String> parse(String query) {
		//split the instruction by space
		List<String> instructions=new ArrayList<>(Arrays.asList(query.split(" ")));
		
		//Remove all unnecessary elements
		instructions.removeAll(Collections.singleton(""));
		
		return instructions;
		
	}
	
	/**Translate instruction string array to a list of instructions usable by the executor*/
	private void translate(List<String> instructions) {
		//start building the tree
		
		
		for(int i=0;i<instructions.size();i++) {
			QueryKeyWords op=null;
			if((op=QueryKeyWords.getOperationByStringName(instructions.get(i))) == null) {
				continue;
				
			}
			
			//The instruction is an operation, increment to next instruction
			switch(op) {
			case SELECT:
				i=parseSelect(instructions, ++i);
				break;
			case FROM:
				//i=parseFrom(instructions, i++);
				break;
			case WHERE:
				i=parseWhere(instructions, ++i);
				break;
			default:
			}
			
		}
		
	}
	
	//parse the select
	private int parseSelect(List<String> instructions, int index) {
		
		
		while(index<instructions.size() && 
				//Is not a keyword like FROM, SELECT,WHERE
				isOperationKeyWord(instructions.get(index))) {
			
			//split by comma, checks whether 
			List<String> split=Arrays.asList(instructions.get(index).split(","));
			
			//generate the tree
			for(String s:split) {
				
				
			}
			
			index++;
		}
		
		return --index;
	}
	
	//Not implemented
	private int parseFrom(List<String> instructions, int index) {
		return 0;
		
	}
	
	//Parse the where statement portion. Note index is 1 after the where
	private int parseWhere(List<String> instructions, int index) {
		this.translatedInstructions.add(QueryKeyWords.WHERE);
		this.parameters.add(new String[0]);
		
		WhereOperations op;
		for(;index<instructions.size() &&
				//not a query keyword
				(QueryKeyWords.getOperationByStringName(instructions.get(index)) == null);index++) {
			
			//somekind of WhereOperation keyword
			if((op=WhereOperations.getOperationFromString(instructions.get(index))) != null) {
				this.translatedInstructions.add(op);
				
				String[] operands=null;
				
				if(op.numOperand()==0) {
					operands=new String[0];
					this.parameters.add(operands);
					
				} else if(op.numOperand()==1){
					operands=new String[1];
					operands[0]=instructions.get(++index);
					
				//2 operands	
				} else if(op.numOperand()==2){
					operands=new String[2];
					operands[0]=instructions.get(index-1);
					operands[1]=instructions.get(++index);
					
				//Infinity	
				} else {
					index++;
					for(;isOperand(instructions.get(index));index++) {
						//TODO: FINISH IMPLEMENTATION
						
					}
					
				}
				
				this.parameters.add(operands);
				
			} 
			
		}
		
		return --index;
	}
	
	private boolean isOperationKeyWord(String inst) {
		return QueryKeyWords.getOperationByStringName(inst) != null;
		
	}
	
	
	

	public static void main(String[] args) {
		new AQueryParser().parseQuery("SELECT attribute ,attribute2 two FROM SOMETHING WHERE a > b OR a == b DOMINANT attriubte");
		
		
	}

	@Override
	public List<QueryOperation> fetchParsedInstructions() {
		return this.translatedInstructions;

	}

	@Override
	public List<String[]> fetchParametersList() {
		return this.parameters;
		
	}
}
