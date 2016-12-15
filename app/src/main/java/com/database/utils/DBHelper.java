package com.database.utils;

import java.io.IOException;
import java.sql.SQLException;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static Context context;
    public static String DB_NAME = "databaseApp.db";

    public DBHelper() {
        super(context, DB_NAME, null, 33);
    }

    public DBHelper(Context context) {
        super(context, DB_NAME, null, 33);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void execute(String Statment) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            db.execSQL(Statment);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
            db = null;
        }
    }

    public Cursor query(String Statment) {
        Cursor cur = null;
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            cur = db.rawQuery(Statment, null);
            cur.moveToPosition(-1);
        } catch (Exception e) {
            // Log.error("DataBaseHelper - ExecuteCursor", e);
        } finally {

            db.close();
            db = null;
        }

        return cur;
    }

    public static String getDBStr(String str) {

        str = str != null ? str.replaceAll("'", "''") : null;
        str = str != null ? str.replaceAll("&#039;", "''") : null;
        str = str != null ? str.replaceAll("&amp;", "&") : null;

        return str;

    }

    public void upgrade(int level) {
        switch (level) {
            case 0:
                doUpdate1();
            case 1:
                // doUpdate2();
            case 2:
                // doUpdate3();
            case 3:
                // doUpdate4();
        }
    }

    private void doUpdate1() {

        // Database Copied
        /*DataBaseHelper myDbHelper = new DataBaseHelper(this.context);
        try {
            myDbHelper.createDataBase();
            myDbHelper.getWritableDatabase();
        } catch (IOException ioe) {

            throw new Error("Unable to create database");
        }

        myDbHelper.openDataBase();*/
        

        this.execute("CREATE TABLE Category ( "
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "categoryName TEXT)");

        // this.execute("CREATE TABLE Hands ( "
        // + "hand_id INTEGER PRIMARY KEY AUTOINCREMENT,"
        // + "amount INTEGER ," + "win_loss TEXT," + "hole_cards TEXT,"
        // + "flop_cards TEXT," + "turn TEXT," + "river TEXT,"
        // + "opponent_cards TEXT," + "notes TEXT,"
        // + "pre_flop_position TEXT," + "post_flop_position TEXT,"
        // + "date TEXT)");
    }

    public SQLiteDatabase getConnection() {
        SQLiteDatabase dbCon = this.getWritableDatabase();

        return dbCon;
    }
}
