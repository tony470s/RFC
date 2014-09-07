package com.lenovo.rfc.uitest;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import android.graphics.Point;
import android.os.RemoteException;

import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiSelector;
import com.android.uiautomator.core.UiWatcher;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;

public class BaseUITestCase extends UiAutomatorTestCase {
	private UnlockThread thread;

	@Override
	protected void setUp() throws Exception {
		setScreenOn();
		thread = new UnlockThread();
		thread.start();
		UiDevice.getInstance().registerWatcher("androidErrorUiWatcher",
				androidErrorUiWatcher);
		UiDevice.getInstance().runWatchers();
		super.setUp();

	}

	UiWatcher androidErrorUiWatcher = new UiWatcher() {
		@Override
		public boolean checkForCondition() {
			UiObject uiObject = new UiObject(new UiSelector().resourceId(
					"android:id/button1").packageName("android"));
			if (uiObject.exists()) {
				try {
					uiObject.clickAndWaitForNewWindow();
				} catch (UiObjectNotFoundException e) {
					e.printStackTrace();
				}
				return true;
			}
			return false;
		}
	};

	@Override
	protected void tearDown() throws Exception {
		thread.stop();
		super.tearDown();
	};

	protected void openPanel(String componentName) {
		try {
			Process process = Runtime.getRuntime().exec(
					"am start -n " + componentName);
			process.waitFor();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void setScreenOn() {
		try {
			UiDevice uiDevice = UiDevice.getInstance();
			if (!uiDevice.isScreenOn()) {
				uiDevice.wakeUp();
				Point point = uiDevice.getDisplaySizeDp();
				UiDevice.getInstance().swipe(0, point.y, 0, 0, 5);
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public class UnlockThread {

		ScheduledExecutorService looper = Executors
				.newSingleThreadScheduledExecutor();

		public void stop() {
			looper.shutdown();
		}

		public void start() {
			looper.scheduleWithFixedDelay(new Runnable() {

				@Override
				public void run() {
					setScreenOn();
				}
			}, 0, 5, TimeUnit.SECONDS);
		}
	}
}
