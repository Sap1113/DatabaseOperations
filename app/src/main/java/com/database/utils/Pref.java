package com.database.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class Pref {
	public static String PREF_FILE = "database_PREF";
	private static SharedPreferences sharedPreferences = null;

	public static void openPref(Context context) {

		sharedPreferences = context.getSharedPreferences(PREF_FILE,
				Context.MODE_PRIVATE);

	}

	public static String getValue(Context context, String key,
			String defaultValue) {
		Pref.openPref(context);
		String result = Pref.sharedPreferences.getString(key, defaultValue);
		Pref.sharedPreferences = null;
		return result;
	}

	public static void setValue(Context context, String key, String value) {
		Pref.openPref(context);
		Editor prefsPrivateEditor = Pref.sharedPreferences.edit();
		prefsPrivateEditor.putString(key, value);
		prefsPrivateEditor.commit();
		prefsPrivateEditor = null;
		Pref.sharedPreferences = null;
	}

}
