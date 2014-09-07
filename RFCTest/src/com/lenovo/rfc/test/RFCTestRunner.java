package com.lenovo.rfc.test;

import junit.framework.AssertionFailedError;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestListener;
import junit.runner.BaseTestRunner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.test.AndroidTestRunner;
import android.test.InstrumentationTestRunner;

public class RFCTestRunner extends InstrumentationTestRunner {

	public void addInfo(String title, String[] contents) {
		JSONArray jsonArray = new JSONArray();
		for (String content : contents) {
			jsonArray.put(content);
		}
		JSONObject infoObect = new JSONObject();
		try {
			infoObect.put("title", title);
			infoObect.put("content", jsonArray);

		} catch (JSONException e) {
			e.printStackTrace();
		}
		try {
			jsonObject.put("info", infoObect);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void start() {
		TestLog.log("test start");
		super.start();
	}

	@Override
	public void onDestroy() {
		TestLog.log("test end");
		super.onDestroy();
	}

	private class RFCTestListener implements TestListener {
		private void addException(String exception) {
			String[] splits = exception.split("\n");
			JSONArray array = new JSONArray();
			for (String split : splits) {
				array.put(split.trim());
			}
			try {
				jsonObject.put("exception", array);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void startTest(Test test) {
			TestCase testCase = (TestCase) test;
			if (testCase.getName().equals(invalidTest)) {
				return;
			}
			jsonObject = new JSONObject();

			try {
				jsonObject.put("test name", testCase.getName());
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void endTest(Test test) {
			if (jsonObject == null) {
				return;
			}
			TestLog.log(jsonObject.toString());
			jsonObject = null;
		}

		@Override
		public void addFailure(Test test, AssertionFailedError t) {
			if (jsonObject == null) {
				return;
			}
			addException(BaseTestRunner.getFilteredTrace(t));
		}

		@Override
		public void addError(Test test, Throwable t) {
			if (jsonObject == null) {
				return;
			}
			addException(BaseTestRunner.getFilteredTrace(t));
		}
	}

	public static JSONObject getJsonObject() {
		return jsonObject;
	}

	static JSONObject jsonObject = null;
	String invalidTest = "testAndroidTestCaseSetupProperly";

	@Override
	protected AndroidTestRunner getAndroidTestRunner() {
		AndroidTestRunner runner = super.getAndroidTestRunner();
		runner.addTestListener(new RFCTestListener());
		return runner;
	}
}
