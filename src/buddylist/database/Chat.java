package buddylist.database;

public class Chat {
	
	public Chat()
	{
		
	}
	
	public Chat(int id, String fromUser, String toUser, int isProcessed, String text)
	{
		this.id = id;
		this.fromUser = fromUser;
		this.toUser = toUser;
		this.isProcessed = isProcessed;
		this.text = text;
	}
	
	private int id;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFromUser() {
		return fromUser;
	}
	public void setFromUser(String fromUser) {
		this.fromUser = fromUser;
	}
	public String getToUser() {
		return toUser;
	}
	public void setToUser(String toUser) {
		this.toUser = toUser;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public int getIsProcessed() {
		return isProcessed;
	}
	public void setIsProcessed(int isProcessed) {
		this.isProcessed = isProcessed;
	}

	private String fromUser;
	private String toUser;
	private String text;
	private int isProcessed;

}
