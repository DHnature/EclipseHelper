package util;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import edu.cmu.scs.fluorite.commands.ICommand;

public class AnAsyncExecutor implements AsyncExecutor {
	public static final int NUM_PENDING_RUNNABLES = 1024;
	BlockingQueue<Runnable> pendingRunnables = new LinkedBlockingQueue(
			NUM_PENDING_RUNNABLES);

	public void asyncExecute(Runnable aRunnable) {
		pendingRunnables.add(aRunnable);
	}

	public void run() {
		while (true) {
			try {

				Runnable aRunnable = pendingRunnables.take();

				aRunnable.run();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}
}
