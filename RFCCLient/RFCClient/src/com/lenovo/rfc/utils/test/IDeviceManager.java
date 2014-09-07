package com.lenovo.rfc.utils.test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class IDeviceManager {
	public interface ITaskTimeout {
		public void taskTimeout();
	}

	public static class Clocker implements Runnable {
		private long timeout;
		private ScheduledExecutorService looper;
		private ITaskTimeout taskTimeout;
		private static final long delay = 100;
		private long start;

		public Clocker(long timeout, ITaskTimeout iTaskTimeout) {
			this.timeout = timeout;
			this.taskTimeout = iTaskTimeout;
			looper = Executors.newSingleThreadScheduledExecutor();
			start = System.currentTimeMillis();
		}

		public static Clocker getDeviceConnectClocker(ITaskTimeout iTaskTimeout) {
			Clocker clocker = new Clocker(1000 * 60 * 5, iTaskTimeout);
			return clocker;
		}

		public void start() {
			looper.scheduleWithFixedDelay(this, 0, delay, TimeUnit.MILLISECONDS);
		}

		public void stop() {
			looper.shutdown();
		}

		@Override
		public void run() {
			if ((System.currentTimeMillis() - start) < (timeout + delay)) {
				stop();
				taskTimeout.taskTimeout();
			}
		}
	}

	public static class DeviceRetryHandler implements ITaskTimeout {

		private Clocker clocker;
		private int count;
		private static final int retryTimes = 3;
		private boolean isWorking = false;

		public DeviceRetryHandler() {

		}

		public void start() {
			count = retryTimes;
			isWorking = true;
			clocker = Clocker.getDeviceConnectClocker(this);
			clocker.start();
		}

		public void CountDownLatch() {
			new ADBCommand("kill-server").start();
			count--;
			if (count < 0) {
				stop();
				DeviceManager
				.instance()
				.notifyAdbErrors(
						"lose the device connection and cannot reconnect, please check!");

			}
		}

		public void stop() {
			count = retryTimes;
			clocker.stop();
			clocker = null;
			isWorking = false;
		}

		public boolean isWorking() {
			return isWorking;
		}

		@Override
		public void taskTimeout() {
			stop();
			DeviceManager
			.instance()
			.notifyAdbErrors(
					"lose the device connection and cannot reconnect, please check!");
		}

	}

	public static class DeviceConnectState {
		private boolean isDeviceConnected;

		public synchronized void deviceConnected() {
			this.isDeviceConnected = true;
		}

		public synchronized void deviceDisconnected() {
			this.isDeviceConnected = false;
		}

		public synchronized boolean waitForConnected(long timeout) {
			final ScheduledExecutorService looper = Executors
					.newSingleThreadScheduledExecutor();
			looper.scheduleWithFixedDelay(new Runnable() {

				@Override
				public void run() {
					if (isDeviceConnected) {
						looper.shutdown();
					}
				}
			}, 0, 100, TimeUnit.MILLISECONDS);
			try {
				return looper.awaitTermination(timeout, TimeUnit.MILLISECONDS);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return false;
		}
	}

	public static class DeviceRebootMonitor implements ITaskTimeout {
		private boolean isDeviceRebooting;
		private Clocker clocker;

		public DeviceRebootMonitor() {
			isDeviceRebooting = false;

		}

		private boolean isWorking = false;

		public boolean isWorking() {
			return isWorking;
		}

		public void setDeviceRebooting() {
			isDeviceRebooting = true;
		}

		public void start() {
			clocker = Clocker.getDeviceConnectClocker(this);
			isWorking = true;
			clocker.start();
		}

		public boolean isDeviceRebooting() {
			return isDeviceRebooting;
		}

		public void stop() {
			isDeviceRebooting = false;
			isWorking = false;
			if (clocker != null) {
				clocker.stop();
				clocker = null;
			}
		}

		@Override
		public void taskTimeout() {
			stop();
			DeviceManager.instance().notifyAdbErrors(
					"failed connect to device after rebooting, please check!");
		}
	}

	public interface IAdbWaitting {
		public void adbWaitting();
	}

	public List<IAdbWaitting> adbWaittings = new ArrayList<>();

	public void addAdbWaittingMonitors(IAdbWaitting adbWaitting) {
		this.adbWaittings.add(adbWaitting);
	}

	public void notifyAdbWaitting() {
		for (IAdbWaitting adbWaitting : adbWaittings) {
			adbWaitting.adbWaitting();
		}
	}

	public interface IAdbError {
		public void adbError(String message);
	}

	private List<IAdbError> adbErrorReceviers = new ArrayList<IDeviceManager.IAdbError>();

	public void addAdbErrorReceivers(IAdbError iaAdbError) {
		adbErrorReceviers.add(iaAdbError);
	}

	public void notifyAdbErrors(String message) {
		for (IAdbError iAdbError : adbErrorReceviers) {
			iAdbError.adbError(message);
		}
	}

	public interface IAdbConnected {
		public void adbConnected();
	}

	private List<IAdbConnected> adbConnectedReceivers = new ArrayList<>();

	public void addAdbConnectedReceiver(IAdbConnected iAdbConnected) {
		adbConnectedReceivers.add(iAdbConnected);
	}

	public void notifyAdbConnected() {
		for (IAdbConnected iAdbConnected : adbConnectedReceivers) {
			iAdbConnected.adbConnected();
		}
	}
}
