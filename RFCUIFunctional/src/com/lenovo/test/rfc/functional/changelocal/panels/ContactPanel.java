package com.lenovo.test.rfc.functional.changelocal.panels;

import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiSelector;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;
import com.lenovo.test.rfc.functional.OpenActivity;

public class ContactPanel extends UiAutomatorTestCase {
	public static void openContactPanel() {
		OpenActivity.open("com.lenovo.ideafriend/.alias.PeopleActivity");
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
		assertNull("no lenovo_note icon on the calling panel", uiObject);
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
