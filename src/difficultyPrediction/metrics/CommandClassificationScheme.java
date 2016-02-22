package difficultyPrediction.metrics;

public enum CommandClassificationScheme {
	A0("leaveoneouta0/", new AnA0CommandCategories()),
	A1("leaveoneouta1/", new AnA1CommandCategories()),
	A2("leaveoneouta2/", new AnA2CommandCategories()),
	A3("leaveoneouta3/", new AnA2CommandCategories() );
	
	private String dir;
	protected CommandCategories commandCategories;
	
	private CommandClassificationScheme(String dir, CommandCategories aCommandCategories) {
		this.dir=dir;
		commandCategories = aCommandCategories;
		
	}

	public String getSubDir() {
		return this.dir;
		
	}
	public CommandCategories getCommandCategories() {
		return commandCategories;
		
	}
}
