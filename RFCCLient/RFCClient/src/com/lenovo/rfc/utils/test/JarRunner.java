package com.lenovo.rfc.utils.test;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import com.lenovo.rfc.utils.test.data.Test;
import com.lenovo.rfc.utils.test.data.TestBuilder;
import com.lenovo.rfc.utils.test.data.TestSuite;

public class JarRunner {
	public static final String packageDir_device = "/data/local/tmp/rfc/packages";

	private static Map<String, String> functionJarSet = new HashMap<String, String>();
	private static List<File> testJarList = new ArrayList<>();

	public static final String functionJar = "rfc_functional.jar";
	public static final String JarDir_local = TestRunner.packageDir_local + File.separator + "jars";
	static {
		functionJarSet.put(functionJar, null);
		for (File jarFile : new File(JarDir_local).listFiles()) {
			if (!jarFile.getName().endsWith(".jar")) {
				continue;
			}
			if (functionJarSet.containsKey(jarFile.getName())) {
				functionJarSet.put(jarFile.getName(), jarFile.getAbsolutePath());
			} else {
				testJarList.add(jarFile);
			}
		}
	}

	public static void installPackages() {
		new ADBCommand("shell rm -r " + packageDir_device).start();
		new ADBCommand("shell mkdir -p " + packageDir_device + "/").start();
		new ADBCommand("push " + JarDir_local + " " + packageDir_device).start();
	}

	public static void runTestJars(final TestSuite testSuite) {
		for (File testJarFile : testJarList) {
			new JarRunnerCommand(testJarFile.getName()) {
				private JSONObject jsonObject;
				private JSONArray jsonArray;

				@Override
				protected void onResponse(String message) {

					if (message.isEmpty()) {
						return;
					}
					System.out.println(message);
					try {
						if (message.startsWith("INSTRUMENTATION_STATUS_CODE:")) {
							if (message.startsWith("INSTRUMENTATION_STATUS_CODE: 1")) {
								jsonObject = new JSONObject();
							} else {
								if (jsonObject != null) {
									Test test = new TestBuilder(jsonObject.toString(), testSuite.getLocal()).getTest();
									testSuite.addTest(test);
									System.err.println(jsonObject);
								}
								jsonObject = null;
							}
						} else {
							if (jsonObject == null) {
								return;
							} else {
								if (!message.startsWith("INSTRUMENTATION_STATUS:")) {
									if (message.startsWith("Failure in")) {
										jsonArray = new JSONArray();
									} else {
										if (jsonArray != null) {
											jsonArray.add(message);
										}
									}
								} else {
									if (jsonArray != null && jsonArray.size() != 0) {
										jsonObject.put("exception", jsonArray);
									}
									jsonArray = null;

									if (message.startsWith("INSTRUMENTATION_STATUS: test")) {
										jsonObject.put("test name",
												message.substring("INSTRUMENTATION_STATUS: test=".length()));
									}
								}
							}
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				};
			}.start();
			TestManager.sleep(5);
		}
	}

	public static final String cmd_changeLocal = "com.lenovo.test.rfc.functional.cmds.ChangeLocal";
	public static final String cmd_allowApp = "com.lenovo.test.rfc.functional.cmds.AllowApp";

	public static void runFunction(String cmd) {
		new ADBCommand("shell uiautomator runtest " + packageDir_device + "/" + functionJar + " -c " + cmd) {
		}.start();
	}

	public static class JarRunnerCommand extends ADBCommand {

		public JarRunnerCommand(String jarName) {
			super("shell uiautomator runtest " + packageDir_device + "/" + jarName);
		}
	}
}