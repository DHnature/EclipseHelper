package analyzer.query;

import java.util.ArrayList;
import java.util.List;

public interface ParseTreeNode {
	
	public void addChildren(ParseTreeNode node);
	
	public ParseTreeNode getChild(int index);
	
	public ParseTreeNode[] getChildren();
	
	public QueryOperation getOperation();
}

abstract class AbstractTreeNode implements ParseTreeNode{
	List<ParseTreeNode> children;
	
	
	public AbstractTreeNode() {
		children=new ArrayList<>();
	}
	
	@Override
	public void addChildren(ParseTreeNode node) {
		children.add(node);
		
	}

	@Override
	public ParseTreeNode getChild(int index) {
		return children.get(index);
		
	}

	@Override
	public ParseTreeNode[] getChildren() {
		return children.toArray(new ParseTreeNode[children.size()]);

	}
	
	abstract public QueryOperation getOperation();
	
}

//Same as the node for select
class TreeRoot extends AbstractTreeNode{
	
	public TreeRoot(String[] ignoreParticipants) {
		super();
		
		
	}

	@Override
	public QueryOperation getOperation() {
		// TODO Auto-generated method stub
		return null;
	}

	
}

class FromNode extends AbstractTreeNode{

	@Override
	public void addChildren(ParseTreeNode node) {
		
		
	}

	@Override
	public QueryOperation getOperation() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}

class WhereNode extends AbstractTreeNode{

	@Override
	public void addChildren(ParseTreeNode node) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public QueryOperation getOperation() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}

class ExtraNode extends AbstractTreeNode{

	@Override
	public void addChildren(ParseTreeNode node) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public QueryOperation getOperation() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}

class SelectKeyWordNode extends AbstractTreeNode{

	@Override
	public void addChildren(ParseTreeNode node) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public QueryOperation getOperation() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}

class AttributeNode extends AbstractTreeNode{

	@Override
	public void addChildren(ParseTreeNode node) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public QueryOperation getOperation() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
