package com.tcl.common.mediaplayer.video.bookmark;

import com.tcl.common.mediaplayer.utils.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class BookMarkDB extends SQLiteOpenHelper {

	private static final String TAG = "BookMarkDB";

	private final static String DATABASE_NAME = "mediaplayer_bookmark_db";

	private final static int DATABASE_VERSION = 1;
	private final static String TABLE_NAME = "bookmark";

	public final static String MEDIA_NAME = "media_name";
	public final static String MEDIA_URI = "media_uri";
	public final static String BREAK_TIME = "break_time";
	public final static String TOTAL_TIME = "total_time";

	public BookMarkDB(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql = "CREATE TABLE " + TABLE_NAME + " (" + MEDIA_NAME
				+ " TEXT," + " " + MEDIA_URI + " TEXT," + " " + BREAK_TIME
				+ " INTEGER,"+" "+TOTAL_TIME +" INTEGER)";
		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		String sql = "DROP TABLE IF EXISTS " + TABLE_NAME;
		db.execSQL(sql);
		onCreate(db);
	}

	public long insert(String name, String url, int time,int total) {
		SQLiteDatabase db = getWritableDatabase();

		if(db.isReadOnly()){
			Utils.printLog(TAG, "###########insert database is readonly");
			return 0;
		}else{
			ContentValues cv = new ContentValues();
			cv.put(MEDIA_NAME, name);
			cv.put(MEDIA_URI,url);
			cv.put(BREAK_TIME, time);
			cv.put(TOTAL_TIME, total);

			long row = db.insert(TABLE_NAME, null, cv);
			return row;
		}

	}

	public void refreshListBeforInsert() {
		Utils.printLog(TAG, "refreshListBeforInsert");
		Cursor cur = query();
		Utils.printLog(TAG, "refreshListBeforInsert with count = "+cur.getCount());
		if (cur != null && cur.getCount() >= BookMarkConst.MAX_BOOKMARK_COUNT) {
			
			cur.moveToFirst();
			String url = cur.getString(1);
			Utils.printLog(TAG, "refreshListBeforInsert delete "+url);
			delete(url);
			
		}
		if (cur != null)
			cur.close();
	}

	public void delete(String url) {

		// SELECT * FROM bookmark WHERE
		// media_uri='http://127.0.0.1:9906/4ddb5174000985fd3428367b22d10b8b'
		SQLiteDatabase db = getWritableDatabase();
		String SQL = "delete from " + TABLE_NAME + " where " + MEDIA_URI + "="
				+ "'" + Utils.turnDBString(url) + "'";
		if(!db.isReadOnly()){
			db.execSQL(SQL);
		}else{
			Utils.printLog(TAG, "###########delete database is readonly");
		}
		
	}

	public Cursor query(String url) {
		SQLiteDatabase db = getWritableDatabase();
		String selection = MEDIA_URI + "=" + "'" + Utils.turnDBString(url) + "'";
		Log.v(TAG, "query " + selection);
		Cursor cursor = db.query(TABLE_NAME, null, selection, null, null, null,
				null);
		return cursor;
	}

	public Cursor queryForSelection(String selection) {
		SQLiteDatabase db = getWritableDatabase();
		Log.v(TAG, "query " + selection);
		Cursor cursor = db.query(TABLE_NAME, null, selection, null, null, null,
				null);
		return cursor;
	}

	public Cursor query() {
		Utils.printLog(TAG, "query");
		SQLiteDatabase db = getWritableDatabase();
		Cursor cur = db
				.query(TABLE_NAME, null, null, null, null, null, null);
//		if(cur!=null){
//		int count = cur.getCount();
//		System.out.println("==========================================================cout ="+count);
//		cur.moveToFirst();
//		System.out.println("=============***********=======================o ="+cur.getString(0)+" 1="+cur.getString(1)+"  2="+cur.getLong(2));	
//		while(cur.moveToNext()){
//			System.out.println("=============***********=======================o ="+cur.getString(0)+" 1="+cur.getString(1)+"  2="+cur.getLong(2));	
//		}
//		
//		
//	}else{
//		System.out.println("==========================================================cursor ="+null);
//	}

		return cur;
	}

	public void update(String url, int play_time) {
		SQLiteDatabase db = getWritableDatabase();
		String SQL = "update " + TABLE_NAME + " set " + BREAK_TIME + "="
				+ play_time + " where " + MEDIA_URI + "=" + "'" +Utils.turnDBString(url) + "'";
		db.execSQL(SQL);
	}

	public boolean isUrlInBookmark(String url) {
		boolean retVal = false;
		Cursor c = query(url);
		if (c != null && c.getCount() > 0) {
			retVal = true;
		}
		c.close();

		return retVal;
	}

	public int getPosFromDB(String url) {
		int n = 0;

		Cursor c = query(url);
		Utils.printLog(TAG, "query res"+c.getCount());
		if (c != null && c.getCount() > 0) {
			c.moveToFirst();
			n = c.getInt(2);

			Log.v(TAG, "#### getPosFromDB name " + c.getString(0) + " url "
					+ c.getString(1) + " break " + n);
		}
		c.close();

		return n;
	}
}