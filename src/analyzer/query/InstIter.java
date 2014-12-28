package analyzer.query;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**Iterator for the instructions list during the QueryParsing stage.<p>
 * 
 * 
 * @author wangk1
 *
 */
public interface InstIter extends Iterator<String>{
	
	public String current();
	
	public String prev();
	
	public boolean hasPrev();
	
	public int getCurrIndex();
	
	public void insertAfterIndex(int index, String... newInst);
	
	public void setIndex(int index);
	
	public boolean atEnd();
	
	public int size();
	
	public List<String> getStringRepOfInstruction();
}
