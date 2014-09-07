package com.lenovo.rfc.utils.test;

import java.util.ArrayList;
import java.util.List;

public class ITestManager {
	public interface OnTestSuiteListUpdateListener {
		public void onTestSuiteListUpdate();
	}

	private List<OnTestSuiteListUpdateListener> onTestSuiteListUpdateListeners = new ArrayList<>();

	public void addOnTestSuiteListUpdateListener(OnTestSuiteListUpdateListener listener) {
		this.onTestSuiteListUpdateListeners.add(listener);
	}

	public void notifyTestSuiteListUpdate() {
		for (OnTestSuiteListUpdateListener listener : onTestSuiteListUpdateListeners) {
			listener.onTestSuiteListUpdate();
		}
	}

	public interface OnTestSuiteUpdateListener {
		public void onTestSuiteUpdate();

		public void onTestSuiteInit();
	}

	private List<OnTestSuiteUpdateListener> onTestSuiteUpdateListeners = new ArrayList<>();

	public void addOnTestSuiteUpdateListener(OnTestSuiteUpdateListener listener) {
		this.onTestSuiteUpdateListeners.add(listener);
	}

	public void notifyTestSuiteUpdate() {
		for (OnTestSuiteUpdateListener listener : onTestSuiteUpdateListeners) {
			listener.onTestSuiteUpdate();
		}
	}

	public void notifyTestSuiteInit() {
		for (OnTestSuiteUpdateListener listener : onTestSuiteUpdateListeners) {
			listener.onTestSuiteInit();
		}
	}

	public interface ITestListener {
		public void onTestStart();

		public void onTestEnd();
	}

	private List<ITestListener> iTestListeners = new ArrayList<>();

	public void addITestListener(ITestListener listener) {
		iTestListeners.add(listener);
	}

	public void notifyTestStart() {
		for (ITestListener listener : iTestListeners) {
			listener.onTestStart();
		}
	}

	public void notifyTestEnd() {
		for (ITestListener listener : iTestListeners) {
			listener.onTestEnd();
		}
	}
}
