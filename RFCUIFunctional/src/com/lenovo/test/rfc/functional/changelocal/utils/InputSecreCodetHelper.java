package com.lenovo.test.rfc.functional.changelocal.utils;

import com.android.uiautomator.core.UiObjectNotFoundException;
import com.lenovo.test.rfc.functional.BaseUITestCase;
import com.lenovo.test.rfc.functional.changelocal.panels.ContactPanel;

public class InputSecreCodetHelper extends BaseUITestCase {

	public void inputSecretCode(boolean isPhone, String secretLocal)
			throws UiObjectNotFoundException {
		if (isPhone) {
			DailPanel.openDailPanel();
			DailPanel.inputNum(secretLocal);
		} else {
			ContactPanel.openContactPanel();
			if (!ContactPanel.isContactExist()) {
				ContactPanel.createContact();
				ContactPanel.inputCode(secretLocal);
			}
		}
	}
}
