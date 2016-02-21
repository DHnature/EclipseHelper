package analyzer;

import util.models.LabelBeanModel;
import util.models.PropertyListenerRegisterer;

public interface WebLink extends PropertyListenerRegisterer{

	public abstract String getSearchString();

	public abstract void setSearchString(String newVal);

	public abstract String getUrlString();

	public abstract void setUrlString(String newVal);

	public abstract String toString();

	LabelBeanModel getClickableLink();

}