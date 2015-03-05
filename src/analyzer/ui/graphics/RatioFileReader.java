package analyzer.ui.graphics;

import util.models.PropertyListenerRegistrar;

public interface RatioFileReader extends PropertyListenerRegistrar {
	public void readFile(String fileName);
	public String getPath();

}
