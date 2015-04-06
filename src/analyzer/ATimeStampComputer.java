package analyzer;

import edu.cmu.scs.fluorite.commands.ICommand;

public class ATimeStampComputer implements TimeStampComputer {
	long startTimeStamp = 0;
	public ATimeStampComputer() {
		
	}
	@Override
	public void reset() {
		startTimeStamp = 0;
	}
	public long computeTimestamp(ICommand aCommand) {
		long aTimeStamp2 = aCommand.getTimestamp2();
		if (aTimeStamp2 > startTimeStamp) {
//				System.out.println ("TS 2 " + aTimeStamp2);			
		
			startTimeStamp = aTimeStamp2;
		}
		return aCommand.getTimestamp() + startTimeStamp;
			
	}

}
