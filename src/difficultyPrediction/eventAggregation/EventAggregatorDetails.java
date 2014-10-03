package difficultyPrediction.eventAggregation;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;

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
	ObjectMapper mapper = new ObjectMapper();

	// can be pasted onto any class that needs to be JSON serialized
		public String toString() {
			try {
	            StringWriter writer = new StringWriter();
	            mapper.writeValue(writer, this);
	            return writer.toString();
	        } catch (IOException e) {
	            System.out.println("Unable to write .json file");
	            return "{}";
	        }
		}
}
