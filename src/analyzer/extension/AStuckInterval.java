package analyzer.extension;

import java.util.Date;

public class AStuckInterval implements StuckInterval{
	private String participant;
	private Date date;
	private String barrierType;
	private String surmountability;
	public String getParticipant() {
		return participant;
	}
	public void setParticipant(String participant) {
		this.participant = participant;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getBarrierType() {
		return barrierType;
	}
	public void setBarrierType(String barrierType) {
		this.barrierType = barrierType;
	}
	public String getSurmountability() {
		return surmountability;
	}
	public void setSurmountability(String surmountability) {
		this.surmountability = surmountability;
	}
}
