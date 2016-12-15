package com.database.utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataBaseHelper extends SQLiteOpenHelper {

	// The Android's default system path of your application database.
	public static final String KEY_ROWID = "_id";
	public static final String KEY_Cspell = "Cspell";
	public static final String KEY_Wspell = "Wspell";

	private static final int DATABASE_VERSION = 1;
	public static String DB_NAME = "databaseApp.db";
	private static String DB_PATH = "/data/data/com.database/databases/";

	private SQLiteDatabase myDataBase;

	private final Context myContext;
	private static SQLiteDatabase db;

	/**
	 * Constructor Takes and keeps a reference of the passed context in order to
	 * access to the application assets and resources.
	 * 
	 * @param context
	 */
	public DataBaseHelper(Context context) {

		super(context, DB_NAME, null, 33);
		this.myContext = context;
	}

	// ---opens the database---
	public DataBaseHelper open() throws SQLException {
		db = getWritableDatabase();

		return this;
	}

	public List<String> selectAllSpell(String DATABASE_TABLE) {
		List<String> list = new ArrayList<String>();
		// ArrayList<Integer> RowID=new ArrayList<Integer>();
		String tag = null;

		Cursor cursor = db.query(DATABASE_TABLE, new String[] { KEY_ROWID,
				KEY_Cspell, KEY_Wspell }, null, null, null, null, KEY_ROWID);

		if (cursor.moveToFirst()) {
			do {
				// RowID.add(cursor.getInt(0));
				list.add(cursor.getString(1));

			} while (cursor.moveToNext());
		}
		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}

		return list;
	}

	public Cursor getSpell(Integer mRowId, String DATABASE_TABLE)
			throws SQLException {
		String tag = null;
		Log.e(tag, "mRowId getTitle :-" + mRowId);
		Cursor mCursor = db.query(true, DATABASE_TABLE, new String[] {
				KEY_ROWID, KEY_Cspell, KEY_Wspell }, KEY_ROWID + "=" + mRowId,
				null, null, null, KEY_ROWID, null);

		return mCursor;
	}

	// ---retrieves a particular title---

	public Integer getid(Cursor c) {
		return (c.getInt(0));
	}

	public String getCspell(Cursor c) {
		return (c.getString(1));
	}

	public String getWspell(Cursor c) {
		return (c.getString(2));
	}

	/**
	 * Creates a empty database on the system and rewrites it with your own
	 * database.
	 * */
	public void createDataBase() throws IOException {

		boolean dbExist = checkDataBase();

		if (dbExist) {
			// do nothing - database already exist
		} else {

			// By calling this method and empty database will be created into
			// the default system path
			// of your application so we are gonna be able to overwrite that
			// database with our database.
			this.getReadableDatabase();

			try {

				copyDataBase();

			} catch (IOException e) {

				throw new Error("Error copying database");

			}
		}

	}

	/**
	 * Check if the database already exist to avoid re-copying the file each
	 * time you open the application.
	 * 
	 * @return true if it exists, false if it doesn't
	 */
	private boolean checkDataBase() {

		SQLiteDatabase checkDB = null;

		try {
			String myPath = DB_PATH + DB_NAME;
			checkDB = SQLiteDatabase.openDatabase(myPath, null,
					SQLiteDatabase.OPEN_READONLY);

		} catch (SQLiteException e) {

			// database does't exist yet.

		}

		if (checkDB != null) {

			checkDB.close();

		}

		return checkDB != null ? true : false;
	}

	/**
	 * Copies your database from your local assets-folder to the just created
	 * empty database in the system folder, from where it can be accessed and
	 * handled. This is done by transfering bytestream.
	 * */
	private void copyDataBase() throws IOException {

		// Open your local db as the input stream
		InputStream myInput = myContext.getAssets().open(DB_NAME);

		// Path to the just created empty db
		String outFileName = DB_PATH + DB_NAME;

		// Open the empty db as the output stream
		OutputStream myOutput = new FileOutputStream(outFileName);

		// transfer bytes from the inputfile to the outputfile
		byte[] buffer = new byte[1024];
		int length;
		while ((length = myInput.read(buffer)) > 0) {
			myOutput.write(buffer, 0, length);
		}

		// Close the streams
		myOutput.flush();
		myOutput.close();
		myInput.close();

	}

	public void openDataBase() throws SQLException {

		// Open the database
		String myPath = DB_PATH + DB_NAME;
		myDataBase = SQLiteDatabase.openDatabase(myPath, null,
				SQLiteDatabase.OPEN_READONLY);

	}

	public synchronized void close() {

		if (myDataBase != null)
			myDataBase.close();

		super.close();

	}

	public void onCreate(SQLiteDatabase db) {

	}

	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

	// Add your public helper methods to access and get content from the
	// database.
	// You could return cursors by doing "return myDataBase.query(....)" so it'd
	// be easy
	// to you to create adapters for your views.

}