package com.lenovo.test.rfc.testcase;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import android.media.RingtoneManager;
import android.test.InstrumentationTestCase;

public class TestID_9 extends InstrumentationTestCase {

	public void testID_9() {
		List<String> resArr = new ArrayList<String>();

		RingtoneManager manager = new RingtoneManager(getInstrumentation()
				.getContext());

		manager.setType(RingtoneManager.TYPE_RINGTONE);

		Cursor cursor = manager.getCursor();

		if (cursor.moveToFirst()) {

			do {

				resArr.add(cursor.getString(RingtoneManager.TITLE_COLUMN_INDEX));

			} while (cursor.moveToNext());

		}

	}

}
