package util;

public class GlobalAsyncExecutor {
	public static final String ASYNC_EXECUTOR_NAME = "Async Executor";
	static AsyncExecutor singleton;
	static Thread globalThread;
	public static AsyncExecutor getSingleton() {
		if (singleton == null) {
			singleton = new AnAsyncExecutor();
			globalThread = new Thread(singleton);
			globalThread.setName(ASYNC_EXECUTOR_NAME);
			globalThread.start();
		}
		return singleton;		
	}

}
