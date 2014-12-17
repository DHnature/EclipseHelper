package analyzer.query;

import java.util.List;

public interface QueryParser {

	public void parseQuery(String query);
	
	public List<QueryOperation> fetchParsedInstructions();
	
	public List<String[]> fetchParametersList();
}
