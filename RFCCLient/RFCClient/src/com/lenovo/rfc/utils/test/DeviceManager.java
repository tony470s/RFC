package com.lenovo.rfc.utils.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class DeviceManager extends IDeviceManager implements Runnable {
	private DeviceManager() {
		// deviceRebootMonitor = new DeviceRebootMonitor();
		// deviceRetryHandler = new DeviceRetryHandler();
		// deviceConnectState = new DeviceConnectState();
	}

	public void waitForDevice() {
		waitForDevice(30);
	}

	public void waitForDevice(long timeOut) {
		notifyAdbWaitting();
		boolean result = new ADBCommand("wait-for-device").start(timeOut);
		if (!result) {
			notifyAdbErrors("cannot connect to device, please check");
		} else {
			notifyAdbConnected();
		}
	}

	private static DeviceManager instance;

	public static DeviceManager instance() {
		if (instance == null) {
			instance = new DeviceManager();
		}
		return instance;
	}

	public boolean waitForDeviceConnected(long timeout) {
		return deviceConnectState.waitForConnected(timeout);
	}

	public boolean waitForDeviceConnected() {
		return waitForDeviceConnected(1000 * 60);
	}

	ScheduledExecutorService looper;

	public void startService() {
		looper = Executors.newSingleThreadScheduledExecutor();
		looper.scheduleWithFixedDelay(this, 0, 5, TimeUnit.MILLISECONDS);
	}

	public void stopService() {
		looper.shutdown();
		try {
			looper.awaitTermination(1, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		looper = null;
	}

	private DeviceRebootMonitor deviceRebootMonitor;
	private DeviceRetryHandler deviceRetryHandler;
	private DeviceConnectState deviceConnectState;

	public void setDeviceRebooting() {
		deviceRebootMonitor.setDeviceRebooting();
	}

	@Override
	public void run() {
		Process process = null;
		BufferedReader reader = null;
		try {
			process = Runtime.getRuntime().exec("adb logcat");
			String line = null;
			reader = new BufferedReader(new InputStreamReader(
					process.getInputStream()));
			while ((line = reader.readLine()) != null) {
				deviceConnectState.deviceConnected();
				if (deviceRebootMonitor.isWorking()) {
					deviceRebootMonitor.stop();
				}
				if (deviceRetryHandler.isWorking()) {
					deviceRetryHandler.stop();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			if (process != null) {
				try {
					process.waitFor(1, TimeUnit.SECONDS);
				} catch (InterruptedException e) {
					process.destroy();
					e.printStackTrace();
				}
			}

			if (deviceRebootMonitor.isDeviceRebooting()) {
				deviceRebootMonitor.start();
			} else {
				deviceRetryHandler.start();
			}
			deviceConnectState.deviceDisconnected();
		}
	}
}
