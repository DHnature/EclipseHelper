package analyzer.extension;

import java.util.Date;

public class AStuckPoint implements StuckPoint{
	private Date date;
	private String type;
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	
	
}
