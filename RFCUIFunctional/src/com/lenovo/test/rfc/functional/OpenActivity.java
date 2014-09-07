package com.lenovo.test.rfc.functional;

import java.io.IOException;

import com.android.uiautomator.core.UiDevice;

public class OpenActivity {
	public static void open(String componentName, String waittingPackage) {
		Process process;
		try {
			process = Runtime.getRuntime().exec("am start -n " + componentName);
			process.waitFor();
			UiDevice.getInstance().waitForWindowUpdate(waittingPackage, 1000);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void open(String componentName) {
		open(componentName, componentName.split("/")[0]);
	}
}
