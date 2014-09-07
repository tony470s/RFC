package com.lenovo.rfc.utils.test.data;

import java.util.Collections;
import java.util.LinkedList;

import com.lenovo.rfc.utils.test.TestManager;

public class TestSuite {
	private String local;
	private LinkedList<Test> testList = new LinkedList<Test>();

	public TestSuite(String local) {
		this.local = local;
		TestManager.instance().getTestSuite().addLast(this);
		TestManager.instance().notifyTestSuiteInit();
	}

	public void addTest(Test test) {
		if (test == null) {
			return;
		}
		testList.addLast(test);
		// System.out.println("update test list: " + testList.size());
		TestManager.instance().notifyTestSuiteUpdate();
	}

	public String getLocal() {
		return local;
	}

	public LinkedList<Test> getTestList() {
		return testList;
	}

	public void sort() {
		Collections.sort(testList);
	}
}
