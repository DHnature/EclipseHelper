package analyzer.extension;

import java.io.FileNotFoundException;

public interface CSVParser {

	public void start(String filename) throws FileNotFoundException;

	public void stop();

	public String getNextLine();

	public int read();

	public String readTillNextDelimiter();

	public void setDelimiter(char newDelimiter);
	
}
