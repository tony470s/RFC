package com.lenovo.rfc.uitest.changelocal.panel;

import android.widget.ListView;

import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiScrollable;
import com.android.uiautomator.core.UiSelector;

public class ChangeLocalPanel {
	public static void selectCountry(String country)
			throws UiObjectNotFoundException {
		UiScrollable uiScrollable = new UiScrollable(
				new UiSelector().className(ListView.class));
		uiScrollable.setAsVerticalList();

		uiScrollable.scrollIntoView(new UiSelector().textContains(country));
		UiObject uiObject = new UiObject(new UiSelector().textContains(country));
		uiObject.click();

		uiObject = new UiObject(
				new UiSelector().resourceId("android:id/button1"));
		uiObject.clickAndWaitForNewWindow();

		uiObject = new UiObject(
				new UiSelector().resourceId("android:id/button1"));
		uiObject.click();
	}
}
