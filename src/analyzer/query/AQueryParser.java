package analyzer.query;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
		
		//create the root node
		TreeRoot root=new TreeRoot();
		
		//translate into instruction usable by executor
		translate(instructions,root);
		
		//cache the newly created tree
		
		
		
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
	private void translate(List<String> instructions,ParseTreeNode root) {
		//start building the tree
		
		
		for(int i=0;i<instructions.size();i++) {
			QueryKeyWords op=null;
			
			//cycle through the instruction until we reach the next keyword.
			if((op=QueryKeyWords.getOperationByStringName(instructions.get(i))) == null) {
				continue;
				
			}
			
			//The instruction is an operation, increment to next instruction
			switch(op) {
			case SELECT:
				i=parseSelect(instructions, ++i,root);
				break;
			case FROM:
				//i=parseFrom(instructions, i++);
				break;
			case WHERE:
				i=parseWhere(instructions, ++i,root);
				break;
			default:
			}
			
		}
		
	}
	
	//parse the select portions of the string query
	private int parseSelect(List<String> instructions, int index, ParseTreeNode parent) {
		Set<SelectAttr> attributes=new HashSet<SelectAttr>();
		
		while(index<instructions.size() && 
				//Is not a keyword like FROM, SELECT,WHERE
				isOperationKeyWord(instructions.get(index))) {
			
			//split by comma, checks whether 
			List<String> split=Arrays.asList(instructions.get(index).split(","));
			split.removeAll(Collections.singleton(""));
			
			
			for(String s:split) {
				SelectOperations op=SelectOperations.getOperationFromString(s);
				
				switch(op) {
				case DOMINANT:
				case SIGNIFICANT:
					parent.addChildren(new KeyWordNode(op));
					
					break;
				default:
					//defaults to attribute. Add to attributes set if not exists
					SelectAttr a=SelectAttr.getAttributeFromString(s);
					
					if(a!=null) {
						attributes.add(a);
						
					}
				
				}
				
			}
			
			index++;
		}
		
		//If there are attributes add them to the node
		parent.addChildren(new AttributeNode(attributes.toArray(new SelectAttr[attributes.size()])));
		
		return --index;
	}
	
	//Not implemented
	private int parseFrom(List<String> instructions, int index) {
		return 0;
		
	}
	
	//Parse the where statement portion. Note index is 1 after the where
	private int parseWhere(List<String> instructions, int index,ParseTreeNode parent) {
		
		//everything here is going to be tacked on to the where node
		KeyWordNode whereNode=new KeyWordNode(QueryKeyWords.WHERE);
		
		WhereOperations op;
		for(;index<instructions.size() &&
				//not a query keyword
				(QueryKeyWords.getOperationByStringName(instructions.get(index)) == null);index++) {
			
			//somekind of WhereOperation keyword
			if((op=WhereOperations.getOperationFromString(instructions.get(index))) != null) {
				
				String[] operands=null;
				
				//2 operands, only for AND and OR, do we record the left and right 2	
				if(op.numOperand()==2){
					
					
					operands=new String[2];
					operands[0]=instructions.get(index-1);
					operands[1]=instructions.get(++index);
					
				//Infinity, add special infinity node
				} else if(op.numOperand()==Integer.MAX_VALUE){
					index++;
//					for(;isOperand(instructions.get(index));index++) {
//						//TODO: FINISH IMPLEMENTATION
//						
//					}
					
				}
				
				this.parameters.add(operands);
			
			
			} else {
				
				
			}
			
		}
		
		//finally add the where node to the parent
		parent.addChildren(whereNode);
		
		return --index;
	}
	
//	private ParseTreeNode createNode(List<String>instructions, int index, WhereOperations op) {
//		
//		
//		
//		
//	}
	
	private boolean isOperationKeyWord(String inst) {
		return QueryKeyWords.getOperationByStringName(inst) != null;
		
	}
	
	
	

	public static void main(String[] args) {
		//new AQueryParser().parseQuery("SELECT attribute ,attribute2 two FROM SOMETHING WHERE a > b OR a == b DOMINANT attriubte");
		
		String test="max( attribute)";
		
		System.out.println(Arrays.asList((test.split(" ")[1]).split("[(]")));
		
		
	}



}
