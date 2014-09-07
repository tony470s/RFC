package com.lenovo.rfc.uitest.testcase;

import com.android.uiautomator.core.UiObjectNotFoundException;
import com.lenovo.rfc.uitest.BaseUITestCase;
import com.lenovo.rfc.uitest.changelocal.panel.DailerPanel;

public class TestID_4 extends BaseUITestCase {

	@Override
	protected void setUp() throws Exception {
		super.setUp();
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testID_4() {
		try {
			DailerPanel.openDailPanel();
			DailerPanel.inputNum("10086");
			DailerPanel.dail();
			boolean result = DailerPanel.checkSmartSoundButton();
			assertFalse("no smart_sound button on calling panel", result);
			DailerPanel.stopCalling();
		} catch (UiObjectNotFoundException e) {
			e.printStackTrace();
		}
	}
}
