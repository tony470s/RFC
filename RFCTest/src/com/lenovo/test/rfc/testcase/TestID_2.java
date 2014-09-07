package com.lenovo.test.rfc.testcase;

import android.database.Cursor;
import android.provider.ContactsContract;
import android.test.InstrumentationTestCase;

import com.lenovo.rfc.test.TestConfig;

public class TestID_2 extends InstrumentationTestCase {
	public void testID_2() {
		String lenovoPhone = TestConfig.instance().getValue("lenovo_phone");
		Cursor cur = getInstrumentation()
				.getContext()
				.getContentResolver()
				.query(ContactsContract.Contacts.CONTENT_URI,
						null,
						null,
						null,
						ContactsContract.Contacts.DISPLAY_NAME
								+ " COLLATE LOCALIZED ASC");
		if (cur.moveToFirst()) {
			int idColumn = cur.getColumnIndex(ContactsContract.Contacts._ID);
			do {
				String contactId = cur.getString(idColumn);

				int phoneCount = cur
						.getInt(cur
								.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
				if (phoneCount > 0) {
					Cursor phones = getInstrumentation()
							.getContext()
							.getContentResolver()
							.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
									null,
									ContactsContract.CommonDataKinds.Phone.CONTACT_ID
											+ " = " + contactId, null, null);
					if (phones.moveToFirst()) {
						do {
							String phoneNumber = phones
									.getString(phones
											.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
							assertFalse(
									"no lenovo phone number in the default contacts",
									phoneNumber.equals(lenovoPhone));
						} while (phones.moveToNext());
					}
				}

			} while (cur.moveToNext());
		}
	}
}
