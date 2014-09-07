package com.lenovo.test.rfc.testcase;

import java.util.List;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.test.InstrumentationTestCase;

public class TestID_8 extends InstrumentationTestCase {

	public void testID_8() {
		String leserviceName = "com.lenovo.frameworks";
		PackageManager pm = getInstrumentation().getContext()
				.getPackageManager();
		List<ApplicationInfo> infos = pm
				.getInstalledApplications(PackageManager.GET_META_DATA);
		for (ApplicationInfo info : infos) {
			if (info.packageName.equals(leserviceName)) {
				return;
			}
		}
		assertFalse("lenovo_service should be installed in ROW productions",
				false);
	}

}
