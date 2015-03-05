package buddylist.database;

public class Log {

	public Log()
	{
		
	}
	
	public Log(int id, String userId, String commandType, String commandText, int timestamp, int isProcessed)
	{
		this.id = id;
		this.userId = userId;
		this.commandType = commandType;
		this.commandText = commandText;
		this.timestamp = timestamp;
		this.isProcessed = isProcessed;
	}
	
	private int id;
	private String userId;
	private String commandType;
	private String commandText;
	private int timestamp;
	private int isProcessed;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getCommandType() {
		return commandType;
	}
	public void setCommandType(String commandType) {
		this.commandType = commandType;
	}
	public String getCommandText() {
		return commandText;
	}
	public void setCommandText(String commandText) {
		this.commandText = commandText;
	}
	public int getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(int timestamp) {
		this.timestamp = timestamp;
	}
	public int getIsProcessed() {
		return isProcessed;
	}
	public void setIsProcessed(int isProcessed) {
		this.isProcessed = isProcessed;
	}
}
