package analyzer.ui.graphics;

public class AWebLink implements WebLink {

	protected String searchString;
	protected String urlString;
	
	public AWebLink(String aSearchString, String aUrlString) {
		super();
		this.searchString = aSearchString;
		this.urlString = aUrlString;
	}
	/* (non-Javadoc)
	 * @see analyzer.WebLink#getSearchString()
	 */
	@Override
	public String getSearchString() {
		return searchString;
	}
	/* (non-Javadoc)
	 * @see analyzer.WebLink#setSearchString(java.lang.String)
	 */
	@Override
	public void setSearchString(String newVal) {
		this.searchString = newVal;
	}
	/* (non-Javadoc)
	 * @see analyzer.WebLink#getUrlString()
	 */
	@Override
	public String getUrlString() {
		return urlString;
	}
	/* (non-Javadoc)
	 * @see analyzer.WebLink#setUrlString(java.lang.String)
	 */
	@Override
	public void setUrlString(String newVal) {
		this.urlString = newVal;
	}
	
	/* (non-Javadoc)
	 * @see analyzer.WebLink#toString()
	 */
	@Override
	public String toString() {
		return searchString + "\t" + urlString;
	}
	
}
