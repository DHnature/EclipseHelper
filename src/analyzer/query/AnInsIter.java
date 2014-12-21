package analyzer.query;

import java.util.Arrays;
import java.util.List;

public class AnInsIter implements InstIter{
	private List<String> instructions;
	private int currIndex;
	
	public AnInsIter(List<String> instructions) {
		this.instructions=instructions;
		this.currIndex=0;
		
	}
	

	@Override
	public boolean hasNext() {
		return this.currIndex<instructions.size()-1;
		
	}

	/**Returns the value of current index and go to next index
	 * 
	 */
	@Override
	public String next() {
		String curr=this.current();
		
		this.currIndex++;
		
		return curr;
		
	}


	

	@Override
	public boolean hasPrev() {
		return this.currIndex>0;
		
	}

	@Override
	public String prev() {
		String curr=this.current();
		
		this.currIndex--;
		
		return curr;
	}


	@Override
	public String current() {
		return instructions.get(currIndex);
		
	}


	@Override
	public int getCurrIndex() {
		return this.currIndex;
		
	}

	@Override
	public void insertAfterIndex(int index, List<String> newInst) {
		this.instructions.addAll(index+1, newInst);
		
	}

	@Override
	public void setIndex(int index) {
		this.currIndex=index;
		
	}


	@Override
	public int size() {
		return this.instructions.size();
		
	}


	@Override
	public boolean atEnd() {
		return this.currIndex>=this.instructions.size();
		
	}

}
