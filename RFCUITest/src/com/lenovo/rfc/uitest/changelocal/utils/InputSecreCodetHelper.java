package com.lenovo.rfc.uitest.changelocal.utils;

import com.android.uiautomator.core.UiObjectNotFoundException;
import com.lenovo.rfc.uitest.TestConfig;
import com.lenovo.rfc.uitest.changelocal.panel.ContactPanel;
import com.lenovo.rfc.uitest.changelocal.panel.DailerPanel;

public class InputSecreCodetHelper {

	public void inputSecretCode(String secretLocal)
			throws UiObjectNotFoundException {
		if (TestConfig.instance().isPhone()) {
			DailerPanel.openDailPanel();
			DailerPanel.inputNum(secretLocal);
		} else {
			ContactPanel.openContactPanel();
			if (!ContactPanel.isContactExist()) {
				ContactPanel.createContact();
				ContactPanel.inputCode(secretLocal);
			}
		}
	}
}
