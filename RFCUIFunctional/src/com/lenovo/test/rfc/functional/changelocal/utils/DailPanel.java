package com.lenovo.test.rfc.functional.changelocal.utils;

import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiSelector;
import com.lenovo.test.rfc.functional.OpenActivity;

public class DailPanel {
	public static void openDailPanel() {
		OpenActivity.open("com.lenovo.ideafriend/.alias.DialtactsActivity");
	}

	public static void inputNum(String num) throws UiObjectNotFoundException {
		UiObject uiObject = new UiObject(
				new UiSelector().resourceId("com.lenovo.ideafriend:id/digits"));
		uiObject.setText(num);
		UiDevice.getInstance()
				.waitForWindowUpdate("com.lenovo.easyimage", 1000);
	}
}
