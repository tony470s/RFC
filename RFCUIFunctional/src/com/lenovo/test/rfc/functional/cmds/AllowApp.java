package com.lenovo.test.rfc.functional.cmds;

import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiSelector;
import com.lenovo.test.rfc.functional.BaseUITestCase;
import com.lenovo.test.rfc.functional.OpenActivity;

public class AllowApp extends BaseUITestCase {
	public void testAllowApp() {

		try {
			OpenActivity
					.open("com.google.android.gms/.security.settings.SecuritySettingsActivity");
			UiObject uiObject = new UiObject(
					new UiSelector()
							.resourceId("com.google.android.gms:id/checkbox"));
			if (uiObject.exists() && uiObject.isChecked()) {
				uiObject.clickAndWaitForNewWindow();
			}
		} catch (UiObjectNotFoundException e) {
			e.printStackTrace();
		}
	}
}
