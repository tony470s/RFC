package com.lenovo.rfc.utils.test.data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lenovo.rfc.utils.test.TestManager;

public class Test implements Comparable<Test> {
	private String testName;
	private boolean result = true;
	private String local;
	private String spec;
	private int id;
	private Map<String, List<String>> infoSet = new HashMap<String, List<String>>();
	public static final String key_info = "info";
	public static final String key_exception = "exception";

	public void setInfos(List<String> infos) {
		this.infoSet.put(key_info, infos);
	}

	public List<String> getInfos() {
		return infoSet.get(key_info);
	}

	public Test(String testName, String local) {
		this.testName = testName;
		this.local = local;
		String prefix = "testID_";
		id = Integer.valueOf(testName.substring(prefix.length()));
		spec = TestManager.instance().getTestConfig().getTestSpec(id);
	}

	public int getId() {
		return id;
	}

	public String getSpec() {
		return spec;
	}

	public String getTestName() {
		return testName;
	}

	public String getLocal() {
		return local;
	}

	public void setResult(boolean result) {
		this.result = result;
	}

	public void setException(List<String> exception) {
		this.infoSet.put(key_exception, exception);
	}

	public int getResult() {
		if (!result) {
			return 1;
		} else {
			if ((getException() == null || getException().size() == 0)
					&& (getInfos() != null && getInfos().size() != 0)) {
				return 0;
			} else {
				return 2;
			}
		}
	}

	public Map<String, List<String>> getInfoSet() {
		return infoSet;
	}

	public List<String> getException() {
		return this.infoSet.get(key_exception);
	}

	@Override
	public int compareTo(Test o) {
		if (getResult() == o.getResult()) {
			return spec.compareTo(o.spec);
		}
		return getResult() - o.getResult();

	}
}