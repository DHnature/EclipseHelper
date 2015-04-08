package analyzer;

import util.models.PropertyListenerRegistrar;

public interface WebLink extends PropertyListenerRegistrar{

	public abstract String getSearchString();

	public abstract void setSearchString(String newVal);

	public abstract String getUrlString();

	public abstract void setUrlString(String newVal);

	public abstract String toString();

}