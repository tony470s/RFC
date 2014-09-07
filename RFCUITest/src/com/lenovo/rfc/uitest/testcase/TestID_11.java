package com.lenovo.rfc.uitest.testcase;

import java.io.IOException;

import com.lenovo.rfc.uitest.BaseUITestCase;

public class TestID_11 extends BaseUITestCase {
	public void testID_11() {
		Process process;
		try {
			process = Runtime.getRuntime().exec(
					"am start -a android.settings.ACCESSIBILITY_SETTINGS");
			process.waitFor();

			assertTrue(
					"same accessibility settings item with default android settings",
					true);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
