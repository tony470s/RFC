package com.lenovo.rfc.utils.test;

import java.util.LinkedList;

import com.lenovo.rfc.utils.ConsoleManager;
import com.lenovo.rfc.utils.test.data.TestSuite;

public class TestManager extends ITestManager implements Runnable {

	private static TestManager instance;

	private TestManager() {
		testConfig = new TestConfig();
	}

	public static void sleep(long timeout) {
		try {
			Thread.sleep(timeout * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static TestManager instance() {
		if (instance == null) {
			instance = new TestManager();
		}
		return instance;
	}

	private TestConfig testConfig;

	public TestConfig getTestConfig() {
		return testConfig;
	}

	/**
	 * <local,test>
	 */
	private LinkedList<TestSuite> testSuites = new LinkedList<>();

	public LinkedList<TestSuite> getTestSuite() {
		return testSuites;
	}

	Thread thread = new Thread(this);

	public void start() {
		testSuites.clear();
		thread.start();
	}

	@Override
	public void run() {
		DeviceManager.instance().waitForDevice();
		notifyTestStart();
		testConfig.init();
		for (int index = 0; index < 2; index++) {
			DeviceManager.instance().waitForDevice();
			ConsoleManager.instance().addInfo("RFC test start");
			new TestRunner(testConfig.getLocal(index)).start();
			for (TestSuite testSuite : testSuites) {
				testSuite.sort();
			}
			notifyTestSuiteListUpdate();
			ConsoleManager.instance().addInfo("RFC test end");
			TestManager.sleep(2 * 60);
		}
		notifyTestEnd();
	}
}
