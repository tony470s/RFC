package com.lenovo.rfc.utils.test;

import java.io.File;

import com.lenovo.rfc.utils.ConsoleManager;
import com.lenovo.rfc.utils.test.data.TestSuite;

public class TestRunner {
	private TestSuite testSuite;
	static final String packageDir_local = "repo" + File.separator + "packages";

	public TestRunner(String local) {
		testSuite = new TestSuite(local);
	}

	public void start() {
		ConsoleManager.instance().addInfo("start test path: " + testSuite.getLocal());
		new ADBCommand("wait-for-device").start(5 * 60);
		JarRunner.installPackages();
		TestManager.sleep(5);
		JarRunner.runFunction(JarRunner.cmd_allowApp);
		TestManager.sleep(5);

		new ApkRunner(testSuite).start();
		TestManager.sleep(5);

		JarRunner.runTestJars(testSuite);
		TestManager.sleep(20);

		JarRunner.runFunction(JarRunner.cmd_changeLocal);
	}

	public static void main(String[] args) {
		new TestRunner("ae").start();
	}
}
