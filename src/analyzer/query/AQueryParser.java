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
		//translate(insIterator,root);

		//cache the newly created tree

		insIterator.setIndex(7);

		System.out.println(getNextWhereOperation(insIterator));
		insIterator.next();
		System.out.println(getNextWhereOperation(insIterator));
		System.out.println(instructions);

	}

	/**Parse into blocks of instruction in the instructions iterator. Split the whole string by space and commas
	 * 
	 * 
	 * */
	private List<String> parse(String query) {

		String processedQuery=preprocess(query);

		//split the instruction by space
		List<String> instructions=new ArrayList<>(Arrays.asList(processedQuery.split(" ")));

		//Remove all unnecessary elements
		instructions.removeAll(Collections.singleton(""));

		return instructions;

	}

	private String preprocess(String query) {
		StringBuilder q=new StringBuilder(query);

		//Little bit of preprocessing to space out the syntax characters so string.split by space will separate them out
		for(int i=0;i<q.length();i++) {
			if(q.charAt(i)==')' || q.charAt(i)=='(' || q.charAt(i)==',') {
				q.insert(i, " ");
				q.insert(i+2, " ");

				i+=3;

			}

		}

		return q.toString();

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

			//Notice that the instructions point is one past the keyword. 
			//No need for correction as reading a keyword will cause all the cases to exit early
			
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

			//grab the current and advance the instruction by 1
			SelectOperations op=SelectOperations.getOperationFromString(instructions.next());

			switch(op) {
			case DOMINANT:
			case SIGNIFICANT:
				parent.addChildren(new KeyWordNode(op));

				break;
			default:
				//defaults to attribute. Add to attributes set if not exists
				SelectAttr a=SelectAttr.getAttributeFromString(instructions.current());

				if(a!=null) {
					attributes.add(a);

				}

			}

			
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

		List<QueryOperation> ins=new ArrayList<>();
		List<Integer> locations=new ArrayList<>();

		//Find all the where Operations in the instructions
		QueryOperation op=null;
		
		while((op=getNextWhereOperation(instructions))!=null) {

			//get all the where operations
			ins.add(op);

			//cache each's index in the instructions iterator
			locations.add(instructions.getCurrIndex());

		}
		
		//Now find the and's or or's and start constructing the WHERE parse tree. Attach to WhereNode
		buildWhereTree(whereNode,instructions,ins,locations,0,ins.size());

		//finally add the where node to the parent
		parent.addChildren(whereNode);


	}

	/**Seek out the location of the next WhereOperation. Returns if reached end of instructions or hit a {@link QueryKeyWords}.
	 * (AKA. "FROM", "SELECT", etc.). Refer to {@link WhereOperations} for all where operations.
	 * 
	 * @param instructions
	 * @return A WhereOperations enum
	 */
	private QueryOperation getNextWhereOperation(InstIter instructions) {

		QueryOperation op;
		while(!instructions.atEnd() &&
				//not a query keyword
				isNotOperationKeyWord(instructions.current())) {

			//somekind of WhereOperation keyword, immediately return the new instruction
			if((op=WhereOperations.getOperationFromString(instructions.current())) != null ||
					//or if it is a syntax word like ( ) or ,
					(op=QuerySyntax.getOperationByStringName(instructions.current())) != null) {
				return op; 

			} 

			instructions.next();
		}

		return null;

	}

	/**Build the ParseTree's WHERE nodes with the instructions
	 * 
	 * @param parent
	 * @param instructions
	 * @param whereOps
	 * @param locations
	 * @param start where in the whereOps list to start
	 * @param end where in the whereOps list to start
	 */
	private void buildWhereTree(ParseTreeNode parent,InstIter instructions
			,List<QueryOperation> whereOps,List<Integer> locations, int start, int end) {
		
		//TODO: Make the ( and ) and , syntax characters and in whereOps array
		
		//The First node should be deeper down in the tree if the first is a (
		int oldStart=Integer.MIN_VALUE;
		if(whereOps.get(start) == QuerySyntax.OPENPAREN) {
			oldStart=start;
			//new start is where the closing parenthesis is. This will be the first child of the parent.
			//The ParseTree starting with the oldstart will be attached to this one instead.
			start=getLocationOfClosingParenthesis(whereOps, locations, start);
			
		}
		
		ParseTreeNode prev=null;
		for(int i=start;i<whereOps.size();i++) {
			//we are only concerned about AND or OR
			if(whereOps.get(i)==WhereOperations.AND || whereOps.get(i)==WhereOperations.OR) {
				instructions.setIndex(locations.get(i));
				
				//TODO: LINK POSSIBLE AND and ORs
				
				if(parent.getChildren().size()>0) {
					instructions.setIndex(locations.get(i-1));
					
					//p.addChildren(createNewStatementNode((WhereOperations) whereOps.get(i-1), instructions));
				
				//first node, means that there is a statement to the left
				//} else if(parent.getChildren().get(index)){
					
					
				} else {
					ParseTreeNode p=new WhereNode(whereOps.get(i));
					parent.addChildren(p);
					
				}
				
				if(whereOps.get(i)==QuerySyntax.OPENPAREN) {
					
					
				}
			
			//may also be ignoring
			} else if(whereOps.get(i)==WhereOperations.IGNORE_ATTRIBUTE) {
				instructions.setIndex(locations.get(i));
				
				
			} 
			
		}

	}
	
	/**Get the location of the closing parenthesis in whereOps array
	 * 
	 * @param instructions
	 * @return
	 */
	private int getLocationOfClosingParenthesis(List<QueryOperation> whereOps,List<Integer> locations, int start) {
		
		for(int i=start;i<whereOps.size();i++) {
			if(whereOps.get(i)==QuerySyntax.CLOSEPAREN) {
				return i;
				
			}
			
		}

		return Integer.MIN_VALUE;
	}

	/**Parse the instruction for the WhereOperation's operands
	 * 
	 * @param parent
	 * @param op
	 * @param instructions
	 */

	private ParseTreeNode createNewStatementNode(WhereOperations op, InstIter instructions) {

		ParseTreeNode n=null;
		switch(op.numOperand()) {

		case 1:
			n=handle1OperandWhere(op,instructions);
			break;
		case 2:
			n=handle2OperandWhere(op,instructions);
			break;
		case Integer.MAX_VALUE:

			break;
		default:
		}

		return n;
	}

	/**Handle Where Operations that only takes in 1 operation. Such as MAX(Attribute).
	 * <p>
	 * Note that the instruction iterator is at the location of said operation.
	 * <p> Current WhereNodes with 1 Operand:<br>
	 * 1. Max <br>
	 * 2. Min <br>
	 * 
	 * @param parent
	 * @param op
	 * @param instructions
	 */
	private ParseTreeNode handle1OperandWhere(WhereOperations op, InstIter instructions) {

		StatementNode node=null;
		switch(op) {
		//max and
		case MAX:
		case MIN:
			//the one following should be the operand, however it can also be (. So must concat next two together
			instructions.next();
			String operand=instructions.next();

			if(operand.equals("(")) {
				operand=instructions.current();

			}

			SelectAttr a=SelectAttr.getAttributeFromString(operand);

			//maybe because the operand is split and it still has a ( or )
			if(a==null) {
				List<String> s=new ArrayList<>(Arrays.asList(operand.split("[(]|[)]")));

				s.removeAll(Collections.singleton(""));

				a=SelectAttr.getAttributeFromString(s.get(0));

				if(a==null) break;
			}

			//create the new node
			node=new StatementNode(op,a);

			break;
		default:
		}

		return node;
	}

	private ParseTreeNode handle2OperandWhere(WhereOperations op, InstIter instructions) {
		StatementNode node=null;

		switch(op) {

		case AND:
		case OR:

			break;
			//regular case, just find the 2 attributes to the left and right
		default:


		}

		return node;
	}

	private boolean isNotOperationKeyWord(String inst) {
		return QueryKeyWords.getOperationByStringName(inst) == null;

	}




	public static void main(String[] args) {
		//new AQueryParser().parseQuery("SELECT attribute ,attribute2 two FROM SOMETHING WHERE a > b OR a == b DOMINANT attriubte");

		AQueryParser parser=new AQueryParser();

		parser.parseQuery("SELECT attribute, attribute2 FROM SOMETHING WHERE <(advs) AND MAX");

	}



}
