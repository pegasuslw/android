package com.tcl.common.mediaplayer.video.bookmark;

import com.tcl.common.mediaplayer.utils.Utils;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

public class BookMarkProvider extends ContentProvider {

	private BookMarkDB dbHelper = null;
	private String TAG="BookMarkProvider";
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getType(Uri uri) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean onCreate() {
		// TODO Auto-generated method stub
		dbHelper = new BookMarkDB(getContext());
		return false;
	}

	
	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		// TODO Auto-generated method stub
		Utils.printLog(TAG, "query");
		if(dbHelper!=null){
			
			return dbHelper.query();
		}else{
			return null;
		}
		
		
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	}

}
