package difficultyPrediction.eventAggregation;

import java.util.ArrayList;
import java.util.List;
import edu.cmu.scs.fluorite.commands.ICommand;
public class EventAggregatorDetails {
	
	public List<ICommand> actions;
	public long startTimeStamp;
	
	public EventAggregatorDetails () {
		actions = new ArrayList<ICommand>();
	}
	
	public EventAggregatorDetails(List<ICommand> actions) {
		this.actions = new ArrayList<ICommand>();
		for(ICommand event : actions) {
			this.actions.add(event);
		}
	}
}
