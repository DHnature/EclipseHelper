package util;

public interface AsyncExecutor extends Runnable {
	public void asyncExecute(Runnable aRunnable) ;

}
