package analyzer.query;

/**Parent Operation Interface for all Query parsing enums<p>
 * 
 * 
 * @author wangk1
 *
 */
public interface QueryOperations {
	
	public boolean isSameOperation(String operation);
	
	public String getOperationStringName();
}
