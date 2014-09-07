package com.lenovo.rfc.test;

import android.util.Log;

public class TestLog {
	private static final String TAG = "RFC";

	public static void log(String message) {
		Log.i(TAG, message);
	}

	public static void error(String message) {
		Log.e(TAG, message);
	}

	public static void information(String... infos) {
		Log.i(TAG, "----- begin information -----");
		for (String message : infos) {
			Log.i(TAG, message);
		}
		Log.i(TAG, "----- end information -----");
	}

}
