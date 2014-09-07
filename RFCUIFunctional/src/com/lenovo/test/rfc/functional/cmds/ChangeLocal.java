package com.lenovo.test.rfc.functional.cmds;

import com.android.uiautomator.core.UiObjectNotFoundException;
import com.lenovo.test.rfc.functional.TestConfig;
import com.lenovo.test.rfc.functional.changelocal.panels.ChangeLocalPanel;
import com.lenovo.test.rfc.functional.changelocal.utils.InputSecreCodetHelper;

public class ChangeLocal extends InputSecreCodetHelper {
	public void testChangeLocal() {
		boolean isPhone = TestConfig.instance().isPhone();
		String local = TestConfig.instance().getLocal();
		try {
			if (isPhone) {
				inputSecretCode(isPhone, "####682#");
			} else {
				inputSecretCode(isPhone, "####6020#");
			}
			ChangeLocalPanel.selectCountry(local);
		} catch (UiObjectNotFoundException e) {
			e.printStackTrace();
		}
	}
}
