package com.lenovo.test.rfc.functional;

import java.io.IOException;

import android.graphics.Point;
import android.os.RemoteException;

import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiSelector;
import com.android.uiautomator.core.UiWatcher;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;

public class BaseUITestCase extends UiAutomatorTestCase {
	UiDevice uiDevice;

	@Override
	protected void setUp() throws Exception {
		uiDevice = UiDevice.getInstance();

		uiDevice.registerWatcher("androidErrorUiWatcher", androidErrorUiWatcher);
		uiDevice.registerWatcher("screen_is_off", screeIsOff);
		uiDevice.runWatchers();
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
	UiWatcher screeIsOff = new UiWatcher() {

		@Override
		public boolean checkForCondition() {

			try {
				if (!uiDevice.isScreenOn()) {
					uiDevice.wakeUp();
					Point point = uiDevice.getDisplaySizeDp();
					uiDevice.swipe(0, point.y, 0, 0, 5);
					return true;
				}
			} catch (RemoteException e) {
				e.printStackTrace();
			}

			return false;
		}
	};

	@Override
	protected void tearDown() throws Exception {

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
}
