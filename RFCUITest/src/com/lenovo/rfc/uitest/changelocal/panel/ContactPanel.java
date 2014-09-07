package com.lenovo.rfc.uitest.changelocal.panel;

import java.io.IOException;

import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiSelector;

public class ContactPanel {
	public static void openContactPanel() {
		Process process;
		try {
			process = Runtime
					.getRuntime()
					.exec("am start -n com.lenovo.ideafriend/.alias.PeopleActivity -a android.intent.action.MAIN -c android.intent.category.LAUNCHER -f 0x10200000");
			process.waitFor();
			UiDevice.getInstance().waitForWindowUpdate("com.lenovo.ideafriend",
					1000);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void createContact() throws UiObjectNotFoundException {
		UiObject uiObject = new UiObject(
				new UiSelector()
						.resourceId("com.lenovo.ideafriend:id/action_bar_slot1"));
		uiObject.clickAndWaitForNewWindow();

		uiObject = new UiObject(new UiSelector().focused(true));
		uiObject.setText("11");
		UiDevice.getInstance().pressBack();
		uiObject = new UiObject(
				new UiSelector().resourceId("com.lenovo.ideafriend:id/done"));
		uiObject.clickAndWaitForNewWindow();
	}

	public static void inputCode(String secrecCode)
			throws UiObjectNotFoundException {
		UiObject uiObject = new UiObject(
				new UiSelector()
						.resourceId("com.lenovo.ideafriend:id/searchedit"));
		uiObject.setText(secrecCode);
		UiDevice.getInstance().waitForWindowUpdate(null, 1000);
	}

	public static void dailNum(String num) throws UiObjectNotFoundException {
		UiObject uiObject = new UiObject(
				new UiSelector()
						.resourceId("com.lenovo.ideafriend:id/searchedit"));
		uiObject.setText(num);
		uiObject = new UiObject(
				new UiSelector()
						.resourceId("com.lenovo.ideafriend:id/slot_index_2_iv"));
		uiObject.clickAndWaitForNewWindow();

		uiObject = new UiObject(
				new UiSelector()
						.resourceId("com.android.incallui:id/noteButton"));
	}

	public static boolean isContactExist() {
		UiObject uiObject = new UiObject(
				new UiSelector()
						.resourceId("com.lenovo.ideafriend:id/empty_new_contact"));
		if (uiObject.exists()) {
			return false;
		} else {
			return true;
		}
	}
}
