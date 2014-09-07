package com.lenovo.test.rfc.testcase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.test.InstrumentationTestCase;

import com.lenovo.rfc.test.RFCTestRunner;

public class TestID_14 extends InstrumentationTestCase {

	public void testID_14() {
		RFCTestRunner runner = (RFCTestRunner) getInstrumentation();
		runner.addInfo("the 3-rd app list",
				getThe3AppList().toArray(new String[getThe3AppList().size()]));
	}

	private List<String> getThe3AppList() {
		PackageManager pm = getInstrumentation().getContext()
				.getPackageManager();
		List<PackageInfo> infoList = pm
				.getInstalledPackages(PackageManager.GET_ACTIVITIES);
		Intent intent_packageInfo = new Intent(Intent.ACTION_MAIN);
		intent_packageInfo.addCategory(Intent.CATEGORY_LAUNCHER);
		List<ResolveInfo> ra = pm.queryIntentActivities(intent_packageInfo, 0);
		Collections.sort(infoList, new PackInfoComparator());

		boolean matchFlag = false;

		String str = null;
		List<String> appList = new ArrayList<String>();
		for (PackageInfo info : infoList) {

			if (info.packageName
					.equalsIgnoreCase("com.lenovo.test.localization")
					|| info.packageName.startsWith("com.android")
					|| info.packageName.startsWith("com.google")
					|| info.packageName.startsWith("com.lenovo")
					|| info.packageName.endsWith("service")
					|| info.packageName.startsWith("android")
					|| info.packageName.startsWith("com.IdeaFriend")
					|| info.packageName.startsWith("com.validation"))
				continue;
			String label;
			ActivityInfo ai;
			str = pm.getApplicationLabel(info.applicationInfo).toString();
			matchFlag = false;
			for (int i = 0; i < ra.size(); i++) {
				ai = ra.get(i).activityInfo;
				label = ai.loadLabel(pm).toString();
				if (label.equals(str)) {
					matchFlag = true;
					continue;
				}
			}
			if (!matchFlag) {
				continue;
			}
			str = pm.getApplicationLabel(info.applicationInfo) + ": "
					+ info.versionName;
			appList.add(str + " | " + info.packageName);
		}
		return appList;
	}

	private static class PackInfoComparator implements Comparator<PackageInfo> {

		@Override
		public int compare(PackageInfo info0, PackageInfo info1) {

			return info0.packageName.compareTo(info1.packageName);
		}

	}
}
