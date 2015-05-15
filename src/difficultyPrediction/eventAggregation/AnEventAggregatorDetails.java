package difficultyPrediction.eventAggregation;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;

import edu.cmu.scs.fluorite.commands.ICommand;
public class AnEventAggregatorDetails {
	
	public List<ICommand> actions;
	public long startTimeStamp;
	
	public AnEventAggregatorDetails () {
		actions = new ArrayList<ICommand>();
	}
	
	public AnEventAggregatorDetails(List<ICommand> actions) {
		this.actions = new ArrayList<ICommand>();
		for(ICommand event : actions) {
			this.actions.add(event);
		}
	}
	ObjectMapper mapper = new ObjectMapper();

	// can be pasted onto any class that needs to be JSON serialized
		public String toString() {
//			try {
//	            StringWriter writer = new StringWriter();
//	            mapper.writeValue(writer, this);
//	            return writer.toString();
//	        } catch (Exception e) {
//	            System.out.println("Unable to write .json file");
//	            
//	            return super.toString();
//	        }
			return "EventAggregation (" + startTimeStamp + ", " + actions.toString() + ")";
		}
}
