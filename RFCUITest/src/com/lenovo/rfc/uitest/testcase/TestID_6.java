package com.lenovo.rfc.uitest.testcase;

import java.util.ArrayList;
import java.util.List;

import com.lenovo.rfc.uitest.BaseUITestCase;
import com.lenovo.rfc.uitest.TestConfig;
import com.lenovo.rfc.uitest.changelocal.utils.SecretCodeVerifier;

public class TestID_6 extends BaseUITestCase {
	public void testID_6() {
		List<String> secretCodes = collectVerifiedSecretCodes();
		StringBuffer errors = new StringBuffer();
		for (String secretCode : secretCodes) {
			SecretCodeVerifier verifier = SecretCodeVerifier
					.getVerifiver(secretCode);
			if (verifier == null) {
				continue;
			}
			if (!verifier.start(TestConfig.instance().isPhone())) {
				errors.append(verifier.getSecretCode() + ", ");
			}
		}
		if (errors.length() != 0) {
			assertFalse("invalid secret codes: " + errors.toString(), true);
		}
	}

	private List<String> collectVerifiedSecretCodes() {
		List<String> codes = new ArrayList<String>();
		codes.add("*#*#4636#*#*");
		codes.add("*#*#225#*#*");
		codes.add("*#*#426#*#*");
		codes.add("*#*#8255#*#*");
		codes.add("*#*#759#*#*");
		return codes;
	}
}
