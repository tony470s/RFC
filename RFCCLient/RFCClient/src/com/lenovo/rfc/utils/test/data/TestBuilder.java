package com.lenovo.rfc.utils.test.data;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class TestBuilder {
	private String message;
	private String local;

	public TestBuilder(String message, String local) {
		this.message = message;
		this.local = local;
	}

	public Test getTest() {
		Test test = null;
		JSONObject jsonObject = null;
		try {
			jsonObject = JSONObject.fromObject(message);
		} catch (Exception e) {
			return null;
		}
		if (jsonObject == null) {
			return null;
		}
		if (jsonObject.containsKey("test name")) {
			String testName = jsonObject.getString("test name");
			test = new Test(testName, local);
		}

		if (jsonObject.containsKey("exception")) {
			List<String> exceptions = new ArrayList<>();
			JSONArray jsonArray = jsonObject.getJSONArray("exception");
			for (Object object : jsonArray.toArray()) {
				exceptions.add(object.toString());
			}
			test.setException(exceptions);
			test.setResult(false);
		}
		if (jsonObject.containsKey("info")) {
			List<String> infos = new ArrayList<>();
			JSONObject infoObject = jsonObject.getJSONObject("info");
			String title = infoObject.getString("title");
			infos.add(title);
			JSONArray array = infoObject.getJSONArray("content");
			for (Object object : array.toArray()) {
				infos.add(object.toString());
			}
			test.setInfos(infos);
		}
		return test;
	}
}