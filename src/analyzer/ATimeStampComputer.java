package analyzer;

import edu.cmu.scs.fluorite.commands.ICommand;

public class ATimeStampComputer implements TimeStampComputer {
	long startTimestamp = 0;
	public ATimeStampComputer() {
		
	}
	public long computeTimestamp(ICommand aCommand) {
		long aTimeStamp2 = aCommand.getTimestamp2();
		if (aTimeStamp2 > startTimestamp) {
//				System.out.println ("TS 2 " + aTimeStamp2);			
		
			startTimestamp = aTimeStamp2;
		}
		return aCommand.getTimestamp() + startTimestamp;
			
	}

}
