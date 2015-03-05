package edu.cmu.scs.fluorite.util;

import java.io.File;
import java.util.List;

import edu.cmu.scs.fluorite.commands.ICommand;

public class LogReaderTest2 {
	public static void main (String[] args) {
//		String logPath = "C:\Users\Jason\Desktop\Logs";
		String logPath = "data/Log2012-12-04-03-51-21-084.xml";
		File file = new File(logPath);
		System.out.println("File exists:" + file.exists());

		LogReader reader = new LogReader();

		List<ICommand> commands = reader.readAll(logPath);
	}

}
