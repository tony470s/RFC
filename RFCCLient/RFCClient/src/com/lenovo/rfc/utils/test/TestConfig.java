package com.lenovo.rfc.utils.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class TestConfig {
	private static final String documentName = "repo" + File.separator
			+ "test_spec.xlsx";
	private Map<Integer, String> testId_spec_maps = new HashMap<Integer, String>();
	private LinkedList<String> localList = new LinkedList<>();

	public Map<Integer, String> getTestSets() {
		return testId_spec_maps;
	}

	public LinkedList<String> getLocalList() {
		return localList;
	}

	public String getLocal(int index) {
		return localList.get(index);
	}

	public String getTestSpec(int testId) {
		return testId_spec_maps.get(testId);
	}

	public TestConfig() {
	}

	private boolean isPhone = true;

	public boolean isPhone() {
		return isPhone;
	}

	public void setIsPhone(boolean isPhone) {
		this.isPhone = isPhone;
	}

	public void init() {
		loadDeviceInfo();
		loadConfig();
	}

	private String modelName;
	private String version;

	public void loadDeviceInfo() {
		new ADBCommand("shell getprop") {
			@Override
			protected void onResponse(String content) {
				if (content.contains("ro.product.model")) {
					String[] splits = content.split(":");
					modelName = splits[1].substring(splits[1].indexOf("["),
							splits[1].indexOf("]"));
				} else if (content.contains("ro.product.sw.internal.version")) {
					String[] splits = content.split(":");
					version = splits[1].substring(splits[1].indexOf("["),
							splits[1].indexOf("]"));
				}
			};
		}.start();
	}

	public String getModelName() {
		return modelName;
	}

	public String getVersion() {
		return version;
	}

	private void loadSpec(Workbook workbook) {
		int _column_id = 0;
		int _column_name = 3;
		Sheet sheet = workbook.getSheet("test list");
		System.out.println(sheet.getLastRowNum());
		for (int index = sheet.getFirstRowNum() + 1; index <= sheet
				.getLastRowNum(); index++) {
			Row row = sheet.getRow(index);
			System.out.println(row.getLastCellNum());
			int id = (int) row.getCell(_column_id).getNumericCellValue();
			String testName = row.getCell(_column_name).getStringCellValue();
			testId_spec_maps.put(id, testName);
		}
	}

	private void loadLocal(Workbook workbook) {
		int _column = 0;
		Sheet sheet = workbook.getSheet("country code");
		for (int index = sheet.getFirstRowNum() + 2; index < sheet
				.getLastRowNum(); index++) {
			Row row = sheet.getRow(index);
			String local = row.getCell(_column).getStringCellValue();
			localList.addLast(local);
		}
	}

	public void loadConfig() {

		try {
			InputStream is = new FileInputStream(documentName);
			XSSFWorkbook workbook = new XSSFWorkbook(is);
			loadSpec(workbook);
			loadLocal(workbook);
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
