package com.example.mymap;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySqLiteHelper extends SQLiteOpenHelper {

	public static final String TABLE_Bookmarks = "bookmarks";
	public static final String COLUMN_Id = "_id";
	public static final String COLUMN_Title = "title";
	public static final String COLUMN_Details = "details";
	public static final String COLUMN_Lan = "lan";
	public static final String COLUMN_Lat = "lat";
	
	

	private static final String DATABASE_NAME = "bookmarks.db";
	private static final int DATABASE_VERSION = 1;

	// Database creation sql statement
	private static final String DATABASE_CREATE = "create table "
			+ TABLE_Bookmarks + "(" + COLUMN_Id
			+ " integer primary key autoincrement, " + COLUMN_Title
			+ " text,"+ COLUMN_Details+  " text, "+COLUMN_Lat+ " real, " 
			+COLUMN_Lan+" real);";

	public MySqLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(DATABASE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(MySqLiteHelper.class.getName(),
				"Upgrading database from version " + oldVersion + " to "
						+ newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_Bookmarks);
		onCreate(db);
	}

} 
