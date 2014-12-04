package analyzer.query;

import java.util.List;

public interface QueryParser {

	public void parseQuery(String query);
	
	public List<QueryOperations> fetchParsedInstructions();
	
	public List<String[]> fetchParametersList();
}
