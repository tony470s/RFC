package com.lenovo.test.rfc.testcase;

import java.util.List;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.test.InstrumentationTestCase;

public class TestID_13 extends InstrumentationTestCase {

	public void testID_13() {
		String defaultBrowser = "com.android.chrome";
		PackageManager pm = getInstrumentation().getContext()
				.getPackageManager();
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.addCategory(Intent.CATEGORY_DEFAULT);
		intent.addCategory(Intent.CATEGORY_BROWSABLE);
		intent.setDataAndType(Uri.parse("http://"), null);
		List<ResolveInfo> infos = pm.queryIntentActivities(intent,
				PackageManager.GET_INTENT_FILTERS);
		for (ResolveInfo info : infos) {
			String name = info.activityInfo.applicationInfo.packageName;
			if (info.isDefault) {
				assertEquals("the default browser should be Chrome",
						defaultBrowser, name);
			}
		}
		String lenovoBrowser = "com.lenovo.browser";
		List<ApplicationInfo> applicationInfos = pm
				.getInstalledApplications(PackageManager.GET_META_DATA);
		for (ApplicationInfo inf : applicationInfos) {
			if (inf.packageName.equals(lenovoBrowser)) {
				assertFalse("no LenovoBrowser installed in ROW productions",
						false);
			}
		}
	}
}
