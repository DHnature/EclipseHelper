package difficultyPrediction.metrics;

public enum RatioScheme {
	A0("leaveoneouta0/"),
	A1("leaveoneouta1/"),
	A2("leaveoneouta2/"),
	A3("leaveoneouta3/");
	
	private String dir;
	
	private RatioScheme(String dir) {
		this.dir=dir;
		
	}

	public String getSubDir() {
		return this.dir;
		
	}
}
