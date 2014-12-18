package analyzer.query;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public interface ParseTreeNode {
	
	public void addChildren(ParseTreeNode node);
	
	public ParseTreeNode getChild(int index);
	
	public List<ParseTreeNode> getChildren();
	
	public QueryOperation getOperation();
}

abstract class AbstractTreeNode implements ParseTreeNode{
	private List<ParseTreeNode> children;
	private QueryOperation op;
	
	
	public AbstractTreeNode(QueryOperation op) {
		children=new ArrayList<>();
	}
	
	@Override
	public final void addChildren(ParseTreeNode node) {
		children.add(node);
		
	}

	@Override
	public final ParseTreeNode getChild(int index) {
		return children.get(index);
		
	}

	@Override
	public final List<ParseTreeNode> getChildren() {
		return new ArrayList<ParseTreeNode> (this.children);

	}
	
	public final QueryOperation getOperation() {
		return this.op;
		
	}
	
}

//The root of the tree, also doubles as the node for the SELECT keyword
class TreeRoot extends AbstractTreeNode{
	private Set<String> ignoredParticipants;
	
	public TreeRoot() {
		super(QueryKeyWords.SELECT);
		this.ignoredParticipants=new HashSet<String>();
		
		
	}
	
	public void addIgnoredParticipants(String...participants) {
		this.ignoredParticipants.addAll(Arrays.asList(participants));
		
	}
	
	public List<String> getIgnoredParticipants() {
		return new ArrayList<String>(ignoredParticipants);
		
	}

	
}

class KeyWordNode extends AbstractTreeNode{
	public KeyWordNode(QueryOperation op) {
		super(op);
		
	}
	
}

class ExtraKeyWordNode extends KeyWordNode{
	private int value;
	
	public ExtraKeyWordNode(QueryOperation op, int value) {
		super(op);
		this.value=value;
		
	}
	
	public int getExtraValue() {
		return value;
		
	}
	
}



class WhereNode extends AbstractTreeNode{
	
	public WhereNode(QueryOperation op) {
		super(op);
		
	}
	
	
}

class StatementNode extends WhereNode{
	public StatementNode(QueryOperation op) {
		super(op);
		
	}
	
	
	
}

class AttributeNode extends AbstractTreeNode{
	
	private Set<SelectAttr> attributes;
	
	public AttributeNode(SelectAttr...attr) {
		super(SelectOperations.ATTRIBUTE);
		attributes=new HashSet<SelectAttr>(Arrays.asList(attr));
		
	}
	
	public void addAttributes(SelectAttr... attr) {
		attributes.addAll(Arrays.asList(attr));
		
	}
	
	public List<SelectAttr> getAttributes() {
		return new ArrayList<SelectAttr>(attributes);
		
	}
	
	
}
