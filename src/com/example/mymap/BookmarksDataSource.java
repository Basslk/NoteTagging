package com.example.mymap;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Comment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class BookmarksDataSource {
	// Database fields
		private SQLiteDatabase database;
		private MySqLiteHelper dbHelper;
		private String[] allColumns = { MySqLiteHelper.COLUMN_Id,
				MySqLiteHelper.COLUMN_Title,MySqLiteHelper.COLUMN_Details,MySqLiteHelper.COLUMN_Lan,MySqLiteHelper.COLUMN_Lat };

		public BookmarksDataSource(Context context) {
			dbHelper = new MySqLiteHelper(context);
		}

		public void open() throws SQLException {
			database = dbHelper.getWritableDatabase();
		}

		public void close() {
			dbHelper.close();
		}

		public Bookmark createComment(Bookmark bookmark) {
			ContentValues values = new ContentValues();
			
			
			values.put(MySqLiteHelper.COLUMN_Title, bookmark.Name);
			values.put(MySqLiteHelper.COLUMN_Details, bookmark.Description);
			values.put(MySqLiteHelper.COLUMN_Lat, bookmark.lat);
			values.put(MySqLiteHelper.COLUMN_Lan, bookmark.lon);
			
			long insertId = database.insert(MySqLiteHelper.TABLE_Bookmarks, null,
					values);
			
			Cursor cursor = database.query(MySqLiteHelper.TABLE_Bookmarks,
					allColumns, MySqLiteHelper.COLUMN_Id + " = " + insertId, null,
					null, null, null);
			
			cursor.moveToFirst();
			Bookmark newBookmark = cursorToComment(cursor);
			cursor.close();
			return newBookmark;
		}

		public Bookmark getBookmarkById(int id)
		{
			Bookmark obj=new Bookmark();
			Cursor cursor = database.query(MySqLiteHelper.TABLE_Bookmarks,
					allColumns,MySqLiteHelper.COLUMN_Id,new String[]{ id+""}, null, null, null);
			
			cursor.moveToFirst();
			obj= cursorToComment(cursor);
			cursor.close();
			
			return obj;
		}
		
		public void deleteComment(int id) {
					System.out.println("Comment deleted with id: " + id);
			database.delete(MySqLiteHelper.TABLE_Bookmarks, MySqLiteHelper.COLUMN_Id
					+ " = " + id, null);
		}
		
		public List<Bookmark> getAllComments() {
			List<Bookmark> comments = new ArrayList<Bookmark>();

			Cursor cursor = database.query(MySqLiteHelper.TABLE_Bookmarks,
					allColumns, null, null, null, null, null);

			cursor.moveToFirst();
			while (!cursor.isAfterLast()) {
				Bookmark comment = cursorToComment(cursor);
				comments.add(comment);
				cursor.moveToNext();
			}
			// Make sure to close the cursor
			cursor.close();
			return comments;
		}

		private Bookmark cursorToComment(Cursor cursor) {
			Bookmark bookmark = new Bookmark();
			bookmark.id=cursor.getInt(0);
			bookmark.Name=cursor.getString(1);
			bookmark.Description=(cursor.getString(2));
			bookmark.lat=(cursor.getFloat(3));
			bookmark.lon=(cursor.getFloat(4));
			return bookmark;
		}
}
