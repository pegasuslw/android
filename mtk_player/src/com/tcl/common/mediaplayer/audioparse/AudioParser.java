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

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CoderResult;
import java.util.HashMap;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.media.MediaMetadataRetriever;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;

import com.tcl.common.mediaplayer.R;
import com.tcl.common.mediaplayer.aidl.MediaBean;
import com.tcl.common.mediaplayer.utils.Utils;

public class AudioParser {

	private static final String TAG = "AudioPlayerActivity_AudioTag";

	private Context context;
	private AudioSongInfo audioInfo = null;
	// public String mPath;
	private MediaBean mBean;
	public Handler mHandler;
	public Uri GENRES_URI = MediaStore.Audio.Genres.EXTERNAL_CONTENT_URI;

	public AudioParser(Handler hanl, Context ct, MediaBean bean) {
		context = ct;
		mBean = bean;
		mHandler = hanl;
	}

	public void startParse() {
		Utils.printLog(TAG, "startParse");
		 String[] filePathsArray = new String[] { mBean.mPath };
		 MediaScannerConnection.scanFile(context, filePathsArray, null,
		 scanCb);

//		getMetaData(mBean.mPath);

		// getMusicInfo(mBean.mPath);
	}

	private void getAudioInfo() {
		Log.d(TAG, "parseAudioFileInfo  -------getAudioInfo");
		Utils.printLog(TAG, "getAudioInfo");
		String[] cols = new String[] { MediaStore.Audio.Media._ID,
				MediaStore.Audio.Media.ALBUM_ID, MediaStore.Audio.Media.TITLE,
				MediaStore.Audio.Media.ARTIST, MediaStore.Audio.Media.ALBUM,
				MediaStore.Audio.Media.YEAR, MediaStore.Audio.Media.DURATION,
				MediaStore.Audio.Media.SIZE, MediaStore.Audio.Media.DATA };
		StringBuilder where = new StringBuilder();
		where.append(MediaStore.Audio.Media.DATA);
		where.append(" LIKE '%");
		where.append(musicUtils.convertToQueryString(mBean.mPath));
		where.append("%'");
		where.append(" ESCAPE '\\'");

		Cursor cur = musicUtils.query(context,
				MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, cols,
				where.toString(), null, null);
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
				mBean.mName = audioInfo.mTitle;
				mBean.mSongsterName = audioInfo.mSonger;

				Log.d(TAG, "parseAudioFileInfo  -------getAudioInfo  end");
				Utils.printLog(TAG, "Album =" + audioInfo.mAlbum
						+ "  Artist = " + audioInfo.mSonger + "  Gener="
						+ audioInfo.mGenre);
				Message msg = new Message();
				msg.what = AudioParserConst.REFRESH_AUDIO_INFO;
				msg.obj = mBean;
				mHandler.sendMessage(msg);
			}

		} catch (RuntimeException ex) {
		} finally {
			if (cur != null) {
				cur.close();
			}
		}

		// if(Utils.isWindow1080((Activity)context)){
		// audioInfo.bitmap = musicUtils.getArtworkQuick(context,
		// audioInfo.mAlbumId, BitMapUtils.ALBUM_WIDTH_1080,
		// BitMapUtils.ALBUM_HEIGHT_1080);
		// }else{
		// audioInfo.bitmap = musicUtils.getArtworkQuick(context,
		// audioInfo.mAlbumId, BitMapUtils.ALBUM_WIDTH_720,
		// BitMapUtils.ALBUM_HEIGHT_720);
		// }

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
					Utils.printLog(TAG, "Gener id =" + c.getString(0)
							+ "   name =" + c.getString(1));
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
			where.append(musicUtils.convertToQueryString(mBean.mPath));
			where.append("%'");
			where.append(" ESCAPE '\\'");
			String mGenreInfo = null;
			try {
				for (String genreId : genreIdMap.keySet()) {
					Utils.printLog(TAG, "Select from GenereIdlist");
					c = musicUtils.query(context, makeGenreUri(genreId),
							new String[] { MediaStore.Audio.Media.DATA },
							where.toString(), null, null);
					if (c != null && c.getCount() != 0) {
						Utils.printLog(TAG,
								"Select from GenereIdlist which is not null");
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
			Log.d(TAG,
					"parseAudioFileInfo  -------ScanCompleted   mBean.mPath="
							+ mBean.mPath + "   path==" + path);
			Utils.printLog(TAG, "onScanCompleted");
			if (path.equalsIgnoreCase(mBean.mPath)) {
				getAudioInfo();
				// if(Utils.isWindow1080((Activity)context)){
				// audioInfo.bitmap = musicUtils.getAlbumArtwork(mPath,
				// BitMapUtils.ALBUM_WIDTH_1080, BitMapUtils.ALBUM_HEIGHT_1080);
				// Utils.printLog(TAG, "BitMapUtils.ALBUM_WIDTH_1080");
				// }else{
				// audioInfo.bitmap = musicUtils.getAlbumArtwork(mPath,
				// BitMapUtils.ALBUM_WIDTH_720, BitMapUtils.ALBUM_HEIGHT_720);
				// }
				// //audioInfo.bitmap = musicUtils.getArtwork(context,
				// audioInfo.mSongId, audioInfo.mAlbumId);
				// if (audioInfo.bitmap != null) {
				// Utils.printLog(TAG, "musicUtils.getArtwork!=null");
				// audioInfo.bitmap = musicUtils.getAlbumArtwork(mPath,
				// BitMapUtils.ALBUM_WIDTH_720, BitMapUtils.ALBUM_HEIGHT_720);
				// mHandler.sendEmptyMessage(AudioParserConst.REFRESH_ALBUM_IMG);
				// }

			}
		}
	};

	/**
	 * 获取解析歌曲信息
	 * 
	 * @return
	 */
	public AudioSongInfo getAudio() {
		return audioInfo;
	}

	/**
	 * 获取文件元数据
	 * 
	 * @param filePath
	 */
	public void getMetaData(String filePath) {
		Log.d(TAG, "getMetaData  filePath=" + filePath);
		String title = null;
		String album = null;
		String artist = null;
		String composer = null;
		String genre = null;
		String mime = null;
		String duration = null;

		MediaMetadataRetriever retriever = new MediaMetadataRetriever();
		retriever.setDataSource(filePath);

		String encode = "UTF-8";  //GBK 20151013
		try {
			encode = codeString(filePath);
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		title = retriever
				.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
		album = retriever
				.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM);
		artist = retriever
				.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
		composer = retriever
				.extractMetadata(MediaMetadataRetriever.METADATA_KEY_COMPOSER);
		genre = retriever
				.extractMetadata(MediaMetadataRetriever.METADATA_KEY_GENRE);
		mime = retriever
				.extractMetadata(MediaMetadataRetriever.METADATA_KEY_MIMETYPE);
		duration = retriever
				.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);

		Log.i(TAG, "getMetaData------encode==" + encode + " ,title=" + title
				+ ",album=" + album + ",artist=" + artist + ",composer="
				+ composer + ",genre=" + genre + ",mime=" + mime
				+ " ,duration=" + duration);
		// try {
		// title = switchEncode(title, encode);
		// album = switchEncode(album, encode);
		// artist = switchEncode(artist, encode);
		// composer = switchEncode(composer, encode);
		// genre = switchEncode(genre, encode);
		// mime = switchEncode(mime, encode);
		// duration = switchEncode(duration, encode);
		// }
		// catch (UnsupportedEncodingException e) {
		// e.printStackTrace();
		// }
		// Log.i(TAG, "getMetaData------title=" + title + ",album=" + album
		// + ",artist=" + artist + ",composer=" + composer + ",genre="
		// + genre + ",mime=" + mime + " ,duration=" + duration);

		audioInfo = new AudioSongInfo();
		audioInfo.mTitle = title;
		audioInfo.mSonger = artist;
		audioInfo.mAlbum = album;
		audioInfo.mDuration = Long.parseLong(duration);
		audioInfo.mGenre = genre;
		if (audioInfo.mGenre == null) {
			audioInfo.mGenre = context.getResources().getString(
					R.string.audio_info_unknown);
		}
		mBean.mName = audioInfo.mTitle;
		mBean.mSongsterName = audioInfo.mSonger;

		Log.d(TAG, "parseAudioFileInfo  -------getAudioInfo  end");
		Message msg = new Message();
		msg.what = AudioParserConst.REFRESH_AUDIO_INFO;
		msg.obj = mBean;
		mHandler.sendMessage(msg);
	}

	/**
	 * 获取歌曲信息
	 * 
	 * @param path
	 */
	private void getMusicInfo(String path) {
		Log.i(TAG, "getMusicInfo -----> " + path);
		String mytitle = path.substring(path.lastIndexOf("/") + 1);
		String title = null;
		String album = null;
		String artist = null;
		String composer = null;
		String genre = null;
		String mime = null;
		String duration = "0";
		String PathData = null;

		Cursor cursor = getCursorfromPath(path);
		Log.i(TAG, "getMusicInfo ---cursor=" + cursor);
		if (cursor == null || cursor.getCount() == 0) {

			title = mytitle;
			genre = context.getString(R.string.audio_info_unknown);
		} else if (cursor != null) {

			if (cursor.moveToFirst()) {
				do {
					title = cursor
							.getString(cursor
									.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE));
					album = cursor
							.getString(cursor
									.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM));
					artist = cursor
							.getString(cursor
									.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST));
					composer = cursor
							.getString(cursor
									.getColumnIndexOrThrow(MediaStore.Audio.Media.COMPOSER));
					// genre = cursor.getString(cursor
					// .getColumnIndexOrThrow(MediaStore.Audio.Media.GENRE));
					mime = cursor
							.getString(cursor
									.getColumnIndexOrThrow(MediaStore.Audio.Media.MIME_TYPE));
					PathData = cursor
							.getString(cursor
									.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));
					Log.i(TAG, "getMusicInfo-----title=" + title + ",album="
							+ album + ",artist=" + artist + ",composer="
							+ composer + ",genre=" + genre + ",mime=" + mime
							+ " ,duration=" + duration + "  ,PathData=="
							+ PathData);
					if (PathData.equals(path)) {
						Log.i(TAG, "getMusicInfo path.equals()");
						break;
					}

				} while (cursor.moveToNext());
			}
		}
		cursor.close();
		Log.i(TAG, "getMusicInfo--end---title=" + title + ",album=" + album
				+ ",artist=" + artist + ",composer=" + composer + ",genre="
				+ genre + ",mime=" + mime + " ,duration=" + duration);

		audioInfo = new AudioSongInfo();
		audioInfo.mTitle = title;
		audioInfo.mSonger = artist;
		audioInfo.mAlbum = album;
		audioInfo.mDuration = Long.parseLong(duration);
		audioInfo.mGenre = genre;
		mBean.mName = audioInfo.mTitle;
		mBean.mSongsterName = audioInfo.mSonger;

		Log.d(TAG, "getMusicInfo  -------getAudioInfo  end");
		Message msg = new Message();
		msg.what = AudioParserConst.REFRESH_AUDIO_INFO;
		msg.obj = mBean;
		mHandler.sendMessage(msg);

	}

	/**
	 * 根据文件地址获取查询文件信息的游标
	 * 
	 * @param filePath
	 * @return
	 */
	private Cursor getCursorfromPath(String filePath) {
		String path = null;
		Cursor c = context.getContentResolver().query(
				MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null,
				MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
		if (c == null) {

			return null;
		}
		if (c.moveToFirst()) {
			do {
				path = c.getString(c
						.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));
				if (path.equals(filePath)) {
					break;
				}
			} while (c.moveToNext());
		}
		return c;
	}

	/**
	 * 将文本转成指定编码格式
	 * 
	 * @param text
	 * @param encode
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	private String switchEncode(String text, String encode)
			throws UnsupportedEncodingException {
		Log.i(TAG, "switchEncode---defaultCharset=="
				+ Charset.defaultCharset().name());
		if (null != text) {
			text = new String(text.getBytes("ISO-8859-1"), encode);
			// text = new String(text.getBytes(Charset.defaultCharset().name()),
			// encode);
		}
		return text;
	}

	/**
	 * 判断文件的编码格式
	 * 
	 * @param fileName
	 *            :file
	 * @return 文件编码格式
	 * @throws Exception
	 */
	private String codeString(String fileName) throws Exception {
		BufferedInputStream bin = new BufferedInputStream(new FileInputStream(
				fileName));
		int p = (bin.read() << 8) + bin.read();
		String code = null;
		switch (p) {
		case 0xefbb:
			code = "UTF-8";
			break;
		case 0xfffe:
			code = "Unicode";
			break;
		case 0xfeff:
			code = "UTF-16BE";
			break;
		case 0x5c75:
			code = "ANSI|ASCII";
			break;
		default:
			code = "GBK";
		}
		bin.close();
		return code;
	}
}
