package analyzer.query;

import java.util.List;
import java.util.Map;

import analyzer.ParticipantTimeLine;

public interface QueryExecutor {
	boolean execute(List<QueryOperation> operations, List<String[]> parameters,Map<String, ParticipantTimeLine> data);
	
}
