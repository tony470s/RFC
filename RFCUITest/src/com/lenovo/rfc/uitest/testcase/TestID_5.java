package com.lenovo.rfc.uitest.testcase;

import com.android.uiautomator.core.UiObjectNotFoundException;
import com.lenovo.rfc.uitest.BaseUITestCase;
import com.lenovo.rfc.uitest.changelocal.panel.DailerPanel;

public class TestID_5 extends BaseUITestCase {
	public void testID_5() {
		try {
			DailerPanel.openDailPanel();
			DailerPanel.inputNum("10086");
			DailerPanel.dail();
			boolean result = DailerPanel.checkNoteButton();
			assertFalse("no lenovo_note button on calling panel", result);
			DailerPanel.stopCalling();
		} catch (UiObjectNotFoundException e) {
			e.printStackTrace();
		}
	}
}
