package com.lenovo.test.rfc.testcase;

import java.util.List;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.test.InstrumentationTestCase;

public class TestID_12 extends InstrumentationTestCase {

	public void testID_12() {
		assertFalse("just false", true);
		String levoicPackageName = "com.lenovo.menu_assistant";
		PackageManager pm = getInstrumentation().getContext()
				.getPackageManager();
		List<ApplicationInfo> applications = pm
				.getInstalledApplications(PackageManager.GET_META_DATA);
		for (ApplicationInfo info : applications) {
			if (info.packageName.equals(levoicPackageName)) {
				assertFalse("no levoice app in ROW products!", false);
			}
		}
	}
}
