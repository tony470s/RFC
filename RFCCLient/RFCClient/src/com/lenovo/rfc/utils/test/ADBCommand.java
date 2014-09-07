package com.lenovo.rfc.utils.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

public class ADBCommand implements Runnable {
	private String adbCmd;
	private Process process;
	private InputReader inputReader;
	private ErrorReader errorReader;
	private static final String adb = "repo" + File.separator + "libs" + File.separator + "adb.exe";

	private String getCmd(String cmd) {
		return adb + " " + cmd;
	}

	public ADBCommand(String adbCmd) {
		this.adbCmd = getCmd(adbCmd);
		inputReader = new InputReader();
		errorReader = new ErrorReader();
	}

	protected void onResponse(String content) {
		if (content.isEmpty()) {
			return;
		}
		System.out.println("\t" + content);
	}

	protected void onError(String content) {
		if (content.isEmpty()) {
			return;
		}
		System.err.println("\t" + content);

	}

	private boolean isFinished = false;

	@Override
	public void run() {
		System.out.println(adbCmd.substring(adb.length() + 1) + ": start");
		try {
			process = Runtime.getRuntime().exec(adbCmd);
			inputReader.start();
			errorReader.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean start(long timeout) {
		run();
		boolean result = false;
		try {
			result = process.waitFor(timeout, TimeUnit.SECONDS);
			if (!result) {
				onTimeout();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return result;
	}

	public void onTimeout() {

	}

	public void startAsDameon() {
		run();
	}

	public boolean start() {
		return start(30);
	}

	public void stop() {
		isFinished = true;
		try {
			process.waitFor(5, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private class ErrorReader extends Thread {
		@Override
		public void run() {
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
			String line = null;
			try {
				while ((line = reader.readLine()) != null) {
					onError(line);
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private class InputReader extends Thread {

		@Override
		public void run() {
			BufferedReader reader = new BufferedReader(new java.io.InputStreamReader(process.getInputStream()));
			String line = null;
			try {
				while (!isFinished && (line = reader.readLine()) != null) {
					onResponse(line);
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}