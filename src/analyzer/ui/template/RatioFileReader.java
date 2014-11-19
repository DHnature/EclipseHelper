package analyzer.ui.template;

import util.models.PropertyListenerRegistrar;

public interface RatioFileReader extends PropertyListenerRegistrar {
	public void readFile(String fileName);
	public String getPath();

}
