package com.database.utils;

import android.content.Context;

import java.util.regex.Pattern;

public class Utils {

    public static Pattern EMAIL_ADDRESS_PATTERN = Pattern
            .compile("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                    + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                    + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                    + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                    + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                    + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$");

    public static Pattern NAME = Pattern.compile("^[a-z_A-Z0-9 ]*$");

    private static int level = 0;
    public static void systemUpgrade(Context context) {
        DBHelper dbHelper = new DBHelper(context);
        //level = Integer.parseInt(Pref.getValue(context, "LEVEL", "0"));

        if (level == 0) {
            dbHelper.upgrade(level);

            // Create not confirmed order
            level++;

        }
        Pref.setValue(context, "LEVEL", level + "");
    }
}
