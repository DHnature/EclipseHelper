package analyzer.query;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import weka.filters.unsupervised.attribute.AddNoise;

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
		
		InstIter insIterator=new AnInsIter(instructions);
		//translate into instruction usable by executor
		translate(insIterator,root);
		
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
	private void translate(InstIter instructions,ParseTreeNode root) {
		//start building the tree
		
		QueryKeyWords op=null;
		while(!instructions.atEnd()) {
			
			
			//cycle through the instruction until we reach the next keyword. Note that .next returns curr value and then moves to next index
			if((op=QueryKeyWords.getOperationByStringName(instructions.next())) == null) {
				continue;
				
			}
			
			
			switch(op) {
			case SELECT:
				parseSelect(instructions, root);
				break;
			case FROM:
				//i=parseFrom(instructions, i++);
				break;
			case WHERE:
				parseWhere(instructions, root);
				break;
			default:
			}
			
		}
		
	}
	
	//parse the select portions of the string query
	private void parseSelect(InstIter instructions, ParseTreeNode parent) {
		Set<SelectAttr> attributes=new HashSet<SelectAttr>();
		
		while( !instructions.atEnd() &&//Is not a keyword like FROM, SELECT,WHERE
				isNotOperationKeyWord(instructions.current())) {
			
			//split by comma, checks whether 
			List<String> split=Arrays.asList(instructions.current().split(","));
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
			
			//finally increment instruction
			instructions.next();
		}
		
		//If there are attributes add them to the node
		parent.addChildren(new AttributeNode(attributes.toArray(new SelectAttr[attributes.size()])));
		
	}
	
	//Not implemented
	private int parseFrom(List<String> instructions, int index) {
		return 0;
		
	}
	
	//Parse the where statement portion. Note index is 1 after the where
	private void parseWhere(InstIter instructions,ParseTreeNode parent) {
		
		//everything here is going to be tacked on to the where node
		KeyWordNode whereNode=new KeyWordNode(QueryKeyWords.WHERE);
		
		//keep going through the instructions until something ends the loop, such as reaching another query keyword
		while(!instructions.atEnd()) {
			
			//use iterator to seek out location of next where operation
			WhereOperations op=getNextWhereOperation(instructions);
			
			//if null, we must return
			if(op==null) return;
			
			//now add the new node to the tree
			addNewWhereNode(whereNode, op, instructions);
			
			//next
			instructions.next();
			
		}
		
		//finally add the where node to the parent
		parent.addChildren(whereNode);
	
		
	}
	
	private WhereOperations getNextWhereOperation(InstIter instructions) {
		
		WhereOperations op;
		while(!instructions.atEnd() &&
				//not a query keyword
				isNotOperationKeyWord(instructions.current())) {
			
			//somekind of WhereOperation keyword, immediately return the new instruction
			if((op=WhereOperations.getOperationFromString(instructions.current())) != null) {
				return op;
				
			
			//maybe in the form of operation(parameters). It may look like operation(paran...) or operation(
			} else {
				List<String> split=Arrays.asList(instructions.current().split("("));
				split.removeAll(Collections.singleton(""));
				
				//found next instruction
				if((op=WhereOperations.getOperationFromString(split.get(0))) != null) {
					return op;
					
				}
			}
			
			instructions.next();
		}
		
		//None found we are at the end or it is a keyword, roll back one so this instruction is not wasted when
		//next() is called
		instructions.prev();
		
		return null;
		
	}

	private void addNewWhereNode(ParseTreeNode parent, WhereOperations op, InstIter instructions) {
		
		
	}
	
	private boolean isNotOperationKeyWord(String inst) {
		return QueryKeyWords.getOperationByStringName(inst) != null;
		
	}
	
	
	

	public static void main(String[] args) {
		//new AQueryParser().parseQuery("SELECT attribute ,attribute2 two FROM SOMETHING WHERE a > b OR a == b DOMINANT attriubte");
		
		String test="max( attribute)";
		
		System.out.println(Arrays.asList((test.split(" ")[1]).split("[(]")));
		
		
	}



}
