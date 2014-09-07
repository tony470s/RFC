package com.lenovo.rfc.uitest.changelocal.utils;

import java.util.HashMap;
import java.util.Map;

import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiSelector;

public abstract class SecretCodeVerifier extends InputSecreCodetHelper {
	public abstract boolean verify();

	private String secretCode;
	private static Map<String, SecretCodeVerifier> secretCodes = new HashMap<String, SecretCodeVerifier>();
	static {
		addVerifier(new TestSecretCode_4636());
		addVerifier(new TestSecretCode_225());
		addVerifier(new TestSecretCode_426());
		addVerifier(new TestSecretCode_8255());
		addVerifier(new TestSecretCode_759());
	}

	public static void addVerifier(SecretCodeVerifier verifier) {
		secretCodes.put(verifier.getSecretCode(), verifier);
	}

	public boolean contains(String secretCode) {
		return secretCodes.containsKey(secretCode);
	}

	public static SecretCodeVerifier getVerifiver(String secretCode) {
		return secretCodes.get(secretCode);
	}

	public SecretCodeVerifier(String secretCode) {
		this.secretCode = secretCode;
	}

	public String getSecretCode() {
		return secretCode;
	}

	public boolean start(boolean isPhone) {
		try {
			inputSecretCode(secretCode);
			UiDevice.getInstance().waitForWindowUpdate(null, 1000);
			return verify();
		} catch (UiObjectNotFoundException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static class TestSecretCode_4636 extends SecretCodeVerifier {

		public TestSecretCode_4636() {
			super("*#*#4636#*#*");
		}

		@Override
		public boolean verify() {

			return new UiObject(
					new UiSelector().packageName("com.android.settings"))
					.exists();
		}
	}

	public static class TestSecretCode_225 extends SecretCodeVerifier {

		public TestSecretCode_225() {
			super("*#*#225#*#*");
		}

		@Override
		public boolean verify() {
			return new UiObject(
					new UiSelector()
							.packageName("com.android.providers.calendar"))
					.exists();
		}
	}

	public static class TestSecretCode_426 extends SecretCodeVerifier {

		public TestSecretCode_426() {
			super("*#*#426#*#*");
		}

		@Override
		public boolean verify() {
			return new UiObject(
					new UiSelector().packageName("com.google.android.gms"))
					.exists();
		}
	}

	public static class TestSecretCode_8255 extends SecretCodeVerifier {

		public TestSecretCode_8255() {
			super("*#*#8255#*#*");
		}

		@Override
		public boolean verify() {
			return new UiObject(
					new UiSelector().packageName("com.google.android.gsf"))
					.exists();
		}
	}

	public static class TestSecretCode_759 extends SecretCodeVerifier {

		public TestSecretCode_759() {
			super("*#*#759#*#*");
		}

		@Override
		public boolean verify() {
			return new UiObject(
					new UiSelector()
							.packageName("com.google.android.partnersetup"))
					.exists();
		}
	}
}
