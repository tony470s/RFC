package com.lenovo.rfc.uitest.changelocal.panel;

import java.io.IOException;

import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiSelector;

public class DailerPanel {
	public static void openDailPanel() {
		Process process;
		try {
			process = Runtime
					.getRuntime()
					.exec("am start -n com.lenovo.ideafriend/.alias.DialtactsActivity -a android.intent.action.MAIN -c android.intent.category.LAUNCHER -f 0x10200000");
			process.waitFor();
			UiDevice.getInstance().waitForWindowUpdate("com.lenovo.ideafriend",
					1000);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void inputNum(String num) throws UiObjectNotFoundException {
		UiObject uiObject = new UiObject(
				new UiSelector().resourceId("com.lenovo.ideafriend:id/digits"));
		uiObject.setText(num);
	}

	public static void stopCalling() throws UiObjectNotFoundException {
		UiObject uiObject = new UiObject(
				new UiSelector()
						.resourceId("com.android.incallui:id/endButton"));
		uiObject.clickAndWaitForNewWindow();
	}

	public static void dail() throws UiObjectNotFoundException {
		UiObject uiObject = new UiObject(
				new UiSelector()
						.resourceId("com.lenovo.ideafriend:id/slot_index_2_iv"));
		uiObject.clickAndWaitForNewWindow();
	}

	public static boolean checkNoteButton() {
		UiObject uiObject = new UiObject(
				new UiSelector()
						.resourceId("com.android.incallui:id/noteButton"));
		return uiObject.exists();
	}

	public static boolean checkSmartSoundButton() {
		return false;
	}
}
