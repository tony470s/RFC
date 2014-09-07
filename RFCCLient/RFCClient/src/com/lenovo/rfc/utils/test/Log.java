package com.lenovo.rfc.utils.test;

import java.util.ArrayList;
import java.util.List;

public class Log {

	public interface onReceivingLogListener {
		public void onReceivingLog(String log);
	}

	private List<onReceivingLogListener> onReceivingLogListeners = new ArrayList<Log.onReceivingLogListener>();

	public void registerListener(onReceivingLogListener listener) {
		onReceivingLogListeners.add(listener);
	}

	public void startLog() {
		logcatCmd.startAsDameon();
		System.out.println("start");
	}

	LogcatCmd logcatCmd = new LogcatCmd();

	public void stopLog() {
		logcatCmd.stop();
	}

	private class LogcatCmd extends ADBCommand {

		public LogcatCmd() {
			super("logcat -s RFC");
			new ADBCommand("logcat -c").start();
		}

		@Override
		protected void onResponse(String content) {
			if (content.isEmpty() || content.contains("---------")) {
				return;
			}
			content = content.substring(content.indexOf(":") + 1).trim();
			System.err.println(content);
			for (onReceivingLogListener listener : onReceivingLogListeners) {
				listener.onReceivingLog(content);
			}
		}
	}

}
