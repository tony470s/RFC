package com.lenovo.rfc.utils.test;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import com.lenovo.rfc.utils.test.Log.onReceivingLogListener;
import com.lenovo.rfc.utils.test.data.Test;
import com.lenovo.rfc.utils.test.data.TestBuilder;
import com.lenovo.rfc.utils.test.data.TestSuite;

public class ApkRunner implements onReceivingLogListener {
	private Log log;
	private TestSuite testSuite;

	public ApkRunner(TestSuite testSuite) {
		this.testSuite = testSuite;
		log = new Log();
		log.registerListener(this);
	}

	private TaskMonitor taskMonitor;

	@Override
	public void onReceivingLog(String message) {
		if (message.startsWith("test start")) {
			taskMonitor.start();
		} else if (message.startsWith("test end")) {
			testCount--;
			if (testCount == 0) {
				log.stopLog();
			}
			System.out.println("log stoped");
		} else if (message.startsWith("{\"test name")) {
			Test test = new TestBuilder(message, testSuite.getLocal()).getTest();
			testSuite.addTest(test);
		}
	}

	private int testCount = 0;

	public void start() {
		log.startLog();
		Set<String> instruCmds = collectInstrCmds();
		uninstallAPk(instruCmds);
		instruCmds = collectInstrCmds();
		installApks();
		Set<String> fullInstruCmds = collectInstrCmds();
		fullInstruCmds.removeAll(instruCmds);
		testCount = fullInstruCmds.size();
		for (String instrCmd : fullInstruCmds) {
			runTest(instrCmd);
		}
		uninstallAPk(fullInstruCmds);
	}

	private void installApks() {
		for (File apkFile : new File(TestRunner.packageDir_local).listFiles()) {
			if (apkFile.getName().endsWith(".apk")) {
				new ADBCommand("install -r " + apkFile.getAbsolutePath()) {
					@Override
					public void onTimeout() {
						JarRunner.runFunction(JarRunner.cmd_allowApp);
						start();
					};
				}.start();
			}
		}
	}

	public static void main(String[] args) {
		JarRunner.runFunction(JarRunner.cmd_allowApp);
		new ADBCommand("install -r repo" + File.separator + "packages" + File.separator + "RFCTest.apk") {
			@Override
			public void onTimeout() {
				start();
			};
		}.start();
	}

	private void uninstallAPk(Set<String> instrCmds) {
		for (String instrCmd : instrCmds) {
			String pkgName = instrCmd.split("/")[0];
			new ADBCommand("uninstall " + pkgName).start();
		}
	}

	private Set<String> collectInstrCmds() {
		final Set<String> instrCmds = new HashSet<>();
		final String prefix_instru = "instrumentation:";
		new ADBCommand("shell pm list instrumentation") {
			@Override
			protected void onResponse(String content) {
				if (!content.startsWith(prefix_instru)) {
					return;
				}
				String text = content.substring(prefix_instru.length(), content.indexOf("(")).trim();
				instrCmds.add(text);
			};
		}.start();
		return instrCmds;
	}

	private void runTest(String instrCmd) {
		new ADBCommand("shell am instrument -w " + instrCmd).start();
	}

}