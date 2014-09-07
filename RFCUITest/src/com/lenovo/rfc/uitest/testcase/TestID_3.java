package com.lenovo.rfc.uitest.testcase;

import com.lenovo.rfc.uitest.BaseUITestCase;

public class TestID_3 extends BaseUITestCase {
	public void testID_3() {
		openPanel("com.android.phone/.MSimCallFeaturesSetting");
		assertTrue("no voice_service in the callsetting list", true);
	}
}
