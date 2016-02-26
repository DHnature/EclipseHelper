package difficultyPrediction.metrics;

public enum CommandClassificationSchemeName {
	A0("leaveoneouta0/", new AnA0CommandCategories()),
	A1("leaveoneouta1/", new AnA1CommandCategories()),
	A2("leaveoneouta2/", new AnA2CommandCategories()),
	A3("leaveoneouta3/", new AnA3CommandCategories() ),
	A4("leaveoneouta4/", new AnA4CommandCategories());
	
	private String dir;
	protected CommandCategoryMapping commandCategories;
	
	private CommandClassificationSchemeName(String dir, CommandCategoryMapping aCommandCategories) {
		this.dir=dir;
		commandCategories = aCommandCategories;
		
	}

	public String getSubDir() {
		return this.dir;
		
	}
	public CommandCategoryMapping getCommandCategoryMapping() {
		return commandCategories;
		
	}
}
