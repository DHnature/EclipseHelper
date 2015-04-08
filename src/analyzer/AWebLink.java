package analyzer;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import bus.uigen.attributes.AttributeNames;
import util.annotations.ComponentWidth;
import util.annotations.LayoutName;
@LayoutName(AttributeNames.GRID_BAG_LAYOUT)
public class AWebLink implements WebLink {
	protected PropertyChangeSupport propertyChangeSupport;
	protected String searchString;
	protected String urlString;
	
	public AWebLink(String aSearchString, String aUrlString) {
//		super();
		this.searchString = aSearchString;
		this.urlString = aUrlString;
		propertyChangeSupport = new PropertyChangeSupport(this);
	}
	/* (non-Javadoc)
	 * @see analyzer.WebLink#getSearchString()
	 */
	@Override
	@ComponentWidth(200)
	public String getSearchString() {
		return searchString;
	}
	/* (non-Javadoc)
	 * @see analyzer.WebLink#setSearchString(java.lang.String)
	 */
	@Override
	public void setSearchString(String newVal) {
		String oldVal = searchString;
		this.searchString = newVal;
		propertyChangeSupport.firePropertyChange("SearchString", oldVal, newVal);

	}
	/* (non-Javadoc)
	 * @see analyzer.WebLink#getUrlString()
	 */
	@Override
	@ComponentWidth(500)
	public String getUrlString() {
		return urlString;
	}
	/* (non-Javadoc)
	 * @see analyzer.WebLink#setUrlString(java.lang.String)
	 */
	@Override
	public void setUrlString(String newVal) {
		String oldVal = urlString;
		this.urlString = newVal;
		propertyChangeSupport.firePropertyChange("UrlString", oldVal, newVal);
	}
	
	/* (non-Javadoc)
	 * @see analyzer.WebLink#toString()
	 */
	@Override
	public String toString() {
		return searchString + "\t" + urlString;
	}
	@Override
	public void addPropertyChangeListener(PropertyChangeListener arg0) {
		propertyChangeSupport.addPropertyChangeListener(arg0);
		
	}
	

}
