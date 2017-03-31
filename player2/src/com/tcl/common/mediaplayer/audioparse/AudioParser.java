/**
 * ------------------------------------------------------------------
 *  Copyright (c) 2011 TCL, All Rights Reserved.
 *  -----------------------------------------------------------------
 *  
 * @author bailing /qiaobl@tcl.com/2011.09.21
 * @JDK version: 1.6
 * @brief: provide the play and control of video and audio;
 * @version:v1.0
 */
package com.tcl.common.mediaplayer.audioparse;

import java.util.HashMap;

import com.tcl.common.mediaplayer.R;
import com.tcl.common.mediaplayer.audio.ui.AudioPlayerActivity;
import com.tcl.common.mediaplayer.audio.ui.BitMapUtils;
import com.tcl.common.mediaplayer.utils.Utils;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;

public class AudioParser {

	private static final String TAG = "AudioTag";

	private Context context;
	private AudioSongInfo  audioInfo= null;
	public String mPath;
	public Handler mHandler;
	public Uri GENRES_URI = MediaStore.Audio.Genres.EXTERNAL_CONTENT_URI;

	public AudioParser(Handler hanl, Context ct, String path) {
		context = ct;
		mPath = path;
		mHandler = hanl;
	}
	public void startParse() {
		Utils.printLog(TAG, "startParse");
		String[] filePathsArray = new String[] { mPath };
		MediaScannerConnection.scanFile(context, filePathsArray, null, scanCb);

	}

	private void getAudioInfo() {
		Utils.printLog(TAG, "getAudioInfo");
		String[] cols = new String[] { MediaStore.Audio.Media._ID,
				MediaStore.Audio.Media.ALBUM_ID, MediaStore.Audio.Media.TITLE,
				MediaStore.Audio.Media.ARTIST, MediaStore.Audio.Media.ALBUM,
				MediaStore.Audio.Media.YEAR, MediaStore.Audio.Media.DURATION,
				MediaStore.Audio.Media.SIZE, MediaStore.Audio.Media.DATA };
		StringBuilder where = new StringBuilder();
		where.append(MediaStore.Audio.Media.DATA);
		where.append(" LIKE '%");
		where.append(musicUtils.convertToQueryString(mPath));
		where.append("%'");
		where.append(" ESCAPE '\\'");

		Cursor cur = musicUtils.query(context,
				MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, cols, where
						.toString(), null, null);
		try {
			if ((cur != null) && cur.moveToFirst()) {
				audioInfo = new AudioSongInfo();
				audioInfo.mSongId = cur.getLong(0);
				audioInfo.mAlbumId = cur.getLong(1);
				audioInfo.mTitle = cur.getString(2);
				audioInfo.mSonger = cur.getString(3);
				audioInfo.mAlbum = cur.getString(4);
				audioInfo.mReleaseYear = cur.getInt(5);
				audioInfo.mDuration = cur.getLong(6);
				audioInfo.mSize = cur.getLong(7);
				audioInfo.mDataPath = cur.getString(8);
				audioInfo.mGenre = getAudioGener();
				if (audioInfo.mGenre == null) {
					audioInfo.mGenre = context.getResources().getString(
							R.string.audio_info_unknown);
				}
				//add here for yilang show error
				java.util.Locale l = java.util.Locale.getDefault();
		        String language = l.getLanguage();
		        if (language.equals("fa")) {
		        	audioInfo.mGenre = context.getResources().getString(
							R.string.audio_info_unknown);	
		        }
				Utils.printLog(TAG, "Album ="+audioInfo.mAlbum+"  Artist = "+audioInfo.mSonger+"  Gener="+audioInfo.mGenre);
				mHandler.sendEmptyMessage(AudioParserConst.REFRESH_AUDIO_INFO);
			}

		} catch (RuntimeException ex) {
		} finally {
			if (cur != null) {
				cur.close();
			}
		}

//		if(Utils.isWindow1080((Activity)context)){
//			audioInfo.bitmap = musicUtils.getArtworkQuick(context, audioInfo.mAlbumId, BitMapUtils.ALBUM_WIDTH_1080, BitMapUtils.ALBUM_HEIGHT_1080);
//		}else{
//			audioInfo.bitmap = musicUtils.getArtworkQuick(context, audioInfo.mAlbumId, BitMapUtils.ALBUM_WIDTH_720, BitMapUtils.ALBUM_HEIGHT_720);
//		}
	

	}

	private String getAudioGener() {
		String GENRE_ID = MediaStore.Audio.Genres._ID;
		String GENRE_NAME = MediaStore.Audio.Genres.NAME;
		String AUDIO_ID = MediaStore.Audio.Media._ID;

		boolean isGetAllGeId = false;
		// Get a map from genre ids to names
		HashMap<String, String> genreIdMap = new HashMap<String, String>();
		Cursor c = musicUtils.query(context, GENRES_URI, new String[] {
				GENRE_ID, GENRE_NAME }, null, null, null);
		try {

			if (c != null && c.getCount() > 0) {
				for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
					Utils.printLog(TAG, "Gener id ="+c.getString(0)+"   name ="+c.getString(1));
					genreIdMap.put(c.getString(0), c.getString(1));
				}
				isGetAllGeId = true;
			}
		} catch (RuntimeException r) {

		} finally {
			if (c != null) {
				c.close();
			}
		}

		if (isGetAllGeId) {
			
			StringBuilder where = new StringBuilder();
			where.append(MediaStore.Audio.Media.DATA);
			where.append(" LIKE '%");
			where.append(musicUtils.convertToQueryString(mPath));
			where.append("%'");
			where.append(" ESCAPE '\\'");
			String mGenreInfo = null;
			try {
				for (String genreId : genreIdMap.keySet()) {
					Utils.printLog(TAG, "Select from GenereIdlist");
					c = musicUtils.query(context, makeGenreUri(genreId),
							new String[] { MediaStore.Audio.Media.DATA }, where
									.toString(), null, null);
					if (c != null && c.getCount() != 0) {
						Utils.printLog(TAG, "Select from GenereIdlist which is not null");
						mGenreInfo = genreIdMap.get(genreId);
						break;
					}

				}
			} catch (RuntimeException e) {
				// TODO: handle exception
				e.printStackTrace();
			} finally {
				if (c != null) {
					c.close();
				}
			}

			return mGenreInfo;
		} else {
			return null;
		}

	}

	private Uri makeGenreUri(String genreId) {
		String CONTENTDIR = MediaStore.Audio.Genres.Members.CONTENT_DIRECTORY;
		return Uri.parse(new StringBuilder().append(GENRES_URI.toString())
				.append("/").append(genreId).append("/").append(CONTENTDIR)
				.toString());
	}

	private MediaScannerConnection.OnScanCompletedListener scanCb = new MediaScannerConnection.OnScanCompletedListener() {

		@Override
		public void onScanCompleted(String path, Uri uri) {
			Utils.printLog(TAG, "onScanCompleted");
			if (path.equalsIgnoreCase(mPath)) {
				getAudioInfo();
//				if(Utils.isWindow1080((Activity)context)){
//					audioInfo.bitmap = musicUtils.getAlbumArtwork(mPath, BitMapUtils.ALBUM_WIDTH_1080, BitMapUtils.ALBUM_HEIGHT_1080);
//					Utils.printLog(TAG, "BitMapUtils.ALBUM_WIDTH_1080");
//				}else{
//					audioInfo.bitmap = musicUtils.getAlbumArtwork(mPath, BitMapUtils.ALBUM_WIDTH_720, BitMapUtils.ALBUM_HEIGHT_720);
//				}
//				//audioInfo.bitmap = musicUtils.getArtwork(context, audioInfo.mSongId, audioInfo.mAlbumId);
//				if (audioInfo.bitmap != null) {
//					Utils.printLog(TAG, "musicUtils.getArtwork!=null");
//					mHandler.sendEmptyMessage(AudioParserConst.REFRESH_ALBUM_IMG);
//				}
				
			}
		}
	};
	
	public AudioSongInfo getAudio(){
		return audioInfo;
	}
}
