package com.lenovo.rfc.utils;

import java.util.ArrayList;
import java.util.List;

import com.lenovo.rfc.utils.test.DeviceManager;
import com.lenovo.rfc.utils.test.IDeviceManager.IAdbConnected;
import com.lenovo.rfc.utils.test.IDeviceManager.IAdbError;
import com.lenovo.rfc.utils.test.IDeviceManager.IAdbWaitting;

public class ConsoleManager extends IConsoleManager implements IAdbWaitting,
		IAdbConnected, IAdbError {
	private static ConsoleManager instance;

	private ConsoleManager() {
		DeviceManager.instance().addAdbConnectedReceiver(this);
		DeviceManager.instance().addAdbErrorReceivers(this);
		DeviceManager.instance().addAdbWaittingMonitors(this);
	}

	public static ConsoleManager instance() {
		if (instance == null) {
			instance = new ConsoleManager();
		}
		return instance;
	}

	private List<ConsoleInfo> consoleInfos = new ArrayList<ConsoleManager.ConsoleInfo>();

	public void addInfo(String content) {
		consoleInfos.add(new ConsoleInfo(content, ConsoleInfo.type_info));
		notifyDataUpdate();
	}

	public void addError(String content) {
		consoleInfos.add(new ConsoleInfo(content, ConsoleInfo.type_error));
		notifyDataUpdate();
	}

	public List<ConsoleInfo> getConsoleInfos() {
		return consoleInfos;
	}

	public void clearAll() {
		consoleInfos.clear();
		notifyDataUpdate();
	}

	public static class ConsoleInfo {
		private String content;
		private String type;
		public static final String type_info = "info";
		public static final String type_error = "error";

		public ConsoleInfo(String content, String type) {

			this.content = content;
			this.type = type;
		}

		public String getContent() {
			return content;
		}

		public void setContent(String content) {
			this.content = content;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

	}

	@Override
	public void adbWaitting() {
		addInfo("wait for connecting device");
	}

	@Override
	public void adbError(String message) {
		addError(message);
	}

	@Override
	public void adbConnected() {
		addInfo("device connected");
	}
}
