package com.lenovo.rfc.utils.test;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public abstract class TaskMonitor implements Runnable {
	public abstract boolean isTaskFinished();

	private ScheduledExecutorService looper = Executors
			.newSingleThreadScheduledExecutor();
	private static final long delay = 10;

	public void start() {
		System.out.println("looper start");
		looper.scheduleWithFixedDelay(this, 0, delay, TimeUnit.MILLISECONDS);
	}

	public boolean waitFor() {
		return waitFor(1000 * 60);
	}

	@Override
	public void run() {
		if (isTaskFinished()) {
			System.err.println("task finished");
			looper.shutdownNow();
		}
	}

	public boolean waitFor(long timeout) {
		boolean result = false;
		try {
			result = looper.awaitTermination(timeout, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {

			e.printStackTrace();
			looper.shutdownNow();
		}
		return result;
	}
}
