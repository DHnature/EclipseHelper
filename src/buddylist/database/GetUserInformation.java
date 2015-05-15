package buddylist.database;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;
import java.util.Vector;

import org.eclipse.jface.viewers.TableViewer;

import buddylist.chat.Buddy;

public class GetUserInformation extends TimerTask {

	public GetUserInformation()
	{
		
	}
	
	public GetUserInformation(List<Buddy> buddies)
	{
		this.buddies = buddies;
	}

	private TableViewer tableViewer;
	private List<Buddy> buddies;
	private String username;
	
	public TableViewer getTableViewer() {
		return tableViewer;
	}



	public void setTableViewer(TableViewer tableViewer) {
		this.tableViewer = tableViewer;
	}



	public List<Buddy> getBuddies() {
		return buddies;
	}



	public void setBuddies(List<Buddy> buddies) {
		this.buddies = buddies;
	}



	public String getUsername() {
		return username;
	}



	public void setUsername(String username) {
		this.username = username;
	}
	
	private void readLog(List<Log> logs)
	{
		String status = "";
		Map<String, List<Log>> wordCount = new HashMap<String,  List<Log>>();
		for(Log log : logs)
		{
			if(log.getCommandType().equalsIgnoreCase(""))
			{
				
			}
			if(wordCount.containsKey(log.getCommandText()))
			{
				List<Log> l = wordCount.get(log.getCommandText());
				l.add(log);
			}
			else
			{
				List<Log> l = new Vector<Log>();
				l.add(log);
				wordCount.put(log.getCommandType(), l);
			}
			
//			LogReader.readStringXML(log.getCommandText());
		}
	}
	
	@Override
	public void run() {
		for(Buddy buddy : buddies)
		{
			List<Log> logs = DatabaseConnection.getLastFiftyActions(buddy.getUsername());
			readLog(logs);
		}
		
		
	}

}
