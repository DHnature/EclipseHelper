package analyzer.tracker;

import weka.core.Instances;

public interface Filter {
	Instances filter(Instances i) throws Exception;
	
}
