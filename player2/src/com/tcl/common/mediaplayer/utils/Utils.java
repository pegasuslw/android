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
package com.tcl.common.mediaplayer.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.TimeZone;

import com.tcl.common.mediaplayer.aidl.CommonConst;
import com.tcl.common.mediaplayer.aidl.MediaBean;
import com.tcl.common.mediaplayer.lyric.Sentence;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Handler;
import android.test.UiThreadTest;
import android.util.DisplayMetrics;
import android.util.Log;

public class Utils {

	public static final String AudioOprationType = "opt";
	public static final String StartPlayPosition = "position";
	public static final boolean DEBUG = true;
	public static String TAG = "ComMediaPlayerUtils";
   public static int videoBacklight = -1;
   public static long currentTime = -1;
	public static int SeekBarLength = 1000;
	public static int SeekBarThumbLength = 46;
	public static int MENU_TIMEOUT=10000;
	// public static final String AUDIO_CONTROL_STOP =
	// "com.tcl.mediaplayer.stop";
	// public static final String AUDIO_CONTROL_SHOW_LYLIC =
	// "com.tcl.mediaplayer.show_lylic";
	// public static final String AUDIO_CONTROL_START_OR_PAUSE =
	// "com.tcl.mediaplayer.start_or_pause";
	// public static final String AUDIO_CONTROL_NEXT =
	// "com.tcl.mediaplayer.next";
	// public static final String AUDIO_CONTROL_PREVIOUS =
	// "com.tcl.mediaplayer.previous";
	//
	// public static final String VIDIO_CONTROL_NEXT =
	// "com.tcl.mediaplayer.next";//下一个
	// public static final String VIDIO_CONTROL_PREVIOUS =
	// "com.tcl.mediaplayer.previous";//上一个
	// public static final String VIDIO_CONTROL_COMPLETION =
	// "com.tcl.mediaplayer.completion";//完成
	public static final String URL_FOR_VOD_TYPE = "VOD";
	private static final int MAX_ALBUM_IMAGE_SIZE = 256;

	public static String getTimeShort(int milliseconds) {
		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
		formatter.setTimeZone(TimeZone.getTimeZone("GMT+0:00"));
		int temp = milliseconds%1000;
		if(temp>900){
			milliseconds=milliseconds+1000;	
		}
		Date currentTime = new Date(milliseconds);
		String dateString = formatter.format(currentTime);
		if (milliseconds < 3600 * 1000) {
			return dateString.substring(3);
		}
		return dateString;
	}

	public static String getTimeAll(long milliseconds) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date currentTime = new Date(milliseconds);
		String dateString = formatter.format(currentTime);
		Utils.printLog(TAG, "userStateRecord getTimeAll = "+dateString);
		return dateString;
	}
	public static boolean isNetWorkUsable(Context context){

		  ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
		  if (cm == null)
		     return false;

		  NetworkInfo[] allinfo = cm.getAllNetworkInfo();
		  if (allinfo != null) {
		     for (NetworkInfo info : allinfo){
		       //Log.d("NetworkInfo","info.getTypeName:" + info.getTypeName());
		       //Log.d("NetworkInfo","info.isAvailable:" + info.isAvailable());
		       //Log.d("NetworkInfo","info.isConnected:" + info.isConnected());
		                            
		       if (info.isAvailable() && info.isConnected()) {
		    	   Utils.printLog(TAG, "isNetWorkUsable =true");
		            return true;
		       }
		     }
		  } 
		   Utils.printLog(TAG, "isNetWorkUsable =false");
		  return false;

	}
	public static int getRandomFromSize(int size, int old) {
		if(size <=1)
			return 0;
		int newInt = 0;
		do {
			newInt = new Random().nextInt(size);
		} while (newInt == old);

		return newInt;
	}
	public static String getRealName(String path){
		Utils.printLog("Utils", "getRealName path ="+path);
		String realName = null;
		try{
			realName = path.substring(path.lastIndexOf("/") + 1,
					path.lastIndexOf("."));
		}catch(Exception ex){
			realName = path.substring(path.lastIndexOf("/") + 1,
					path.length());
			ex.printStackTrace();
		}
		return realName;
	}
	
	
	public static void printPlayList(List<MediaBean> playlist){
		if(playlist !=null && playlist.size()>0){
			for(int i = 0;i<playlist.size();i++){
				printLog("printPlayList", "index = "+i+";  path ="+playlist.get(i).mPath);
			}
		}
		
	}
	public static String getUriPath(Uri uri) {
		Utils.printLog(TAG, "getUriPath  ="+uri.toString());
		Utils.printLog(TAG, "uri.getPath()  ="+uri.getPath());
		//return uri.toString();
		if (uri.getScheme() == null) {
			return uri.toString();
		} else {
			if (uri.getScheme().startsWith("file")) {
				try{
					return uri.toString().substring(7);
				}catch(Exception e){
					e.printStackTrace();
					return uri.toString();
				}
				
			} else {
				return uri.toString();
			}
		}

	}
	public static boolean isWindow1080(Activity act){
		 DisplayMetrics metrics = new DisplayMetrics(); 
		 act.getWindowManager().getDefaultDisplay().getMetrics(metrics);
		 if (metrics.heightPixels == 1080) {
			 return true;
		 }
		 return false;
	}
	public static String caculateVideoSize(String bString) {
		String mString = "未知";
		if (bString != null && bString.trim().equals("") == false) {
			float size = Float.valueOf(bString) / (1024 * 1024);
			if (size > 1024) {
				size = size / 1024;
				size = (float) (Math.round(size * 10)) / 10;
				mString = String.valueOf(size) + "GB";
			} else {
				size = (float) (Math.round(size * 10)) / 10;
				mString = String.valueOf(size) + "MB";
			}

		}
		return mString;
	}

	public static Bitmap loadPosterImage(String path) {
		Options opts = new Options();
		opts.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(path, opts);
		int outWidth = opts.outWidth;
		int outHeight = opts.outHeight;
		int sampleSize = outHeight / MAX_ALBUM_IMAGE_SIZE > outWidth
				/ MAX_ALBUM_IMAGE_SIZE ? outHeight / MAX_ALBUM_IMAGE_SIZE
				: outHeight / MAX_ALBUM_IMAGE_SIZE;
		if (sampleSize < 1) {
			sampleSize = 1;
		}
		Options opts2 = new Options();
		opts2.inSampleSize = sampleSize;
		return BitmapFactory.decodeFile(path, opts2);
	}

	public static int getNowSentenceIndex(List<Sentence> list, long time) {
		if (list == null) {
			return -1;
		}
		for (int i = 0; i < list.size(); i++) {
			Sentence sentence = list.get(i);
			// Utils.printLog("LYCParse", "current Time ="+time);
			// Utils.printLog("LYCParse", "sentence.getToTime ="+
			// sentence.getToTime());
			// Utils.printLog("LYCParse",
			// "sentence.getFromTime ="+sentence.getFromTime());
			if (time < sentence.getToTime() && (time > sentence.getFromTime()||time == sentence.getFromTime())) {
				return i;
			}
		}
		return -1;
	}

	public static boolean checkIsFileExists(String path) {
		final File file = new File(path);
		return file.exists();
	}

	public static void printLog(String tag, String log) {
		if (Utils.DEBUG) {
			Log.i(tag,
					"$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$"
							+ log);
		}

	}
	
	public static void removeHandlerMsg(Handler handler){
		
		if(handler != null){
			Utils.printLog("Utils", "removeHandlerMsg start	");
			handler.removeMessages(CommonConst.media_source_not_found);
			handler.removeMessages(CommonConst.unknown_media_format);
			handler.removeMessages(CommonConst.media_interrupt);
			handler.removeMessages(CommonConst.unknown_audio_format);
			handler.removeMessages(CommonConst.unknown_video_format);
			handler.removeMessages(CommonConst.media_player_unknown_exception);
			handler.removeMessages(CommonConst.media_player_network_slow);
			handler.removeMessages(CommonConst.media_player_exception);
			handler.removeMessages(CommonConst.media_player_init_error);			
			handler.removeMessages(CommonConst.media_player_not_seekable);
			handler.removeMessages(CommonConst.PLAY_LIST_NULL);
			handler.removeMessages(CommonConst.PLAY_DEVICE_UNMOUTED);
			Utils.printLog("Utils", "removeHandlerMsg end	");
		}
	}

	public static void killMyProcess(Context context){
		
		Utils.printLog(TAG, "killMyProcess start ok");
		ActivityManager manager = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
		manager.killBackgroundProcesses(context.getPackageName());
//		android.os.Process.killProcess(android.os.Process.myUid());
//		android.os.Process.killProcess(android.os.Process.myPid());
		Utils.printLog(TAG, "killMyProcess end");
	}
	public static String turnDBString(String key) {

		if (key == null)
			return null;

		key = key.replace("'", "''");
		//key = key.replace("[", "[[]");
		/*
		 * key = key.replace("\\", "\\\\"); key = key.replace("%", "\\%"); key =
		 * key.replace("_", "\\_");
		 */

		// Log.d(TAG, "---------->" + key);
		return key;
	}
	
	public static int readStaticBacklight(){
		
		
        return videoBacklight;
	}
	
	public static void writeStaticBacklight(int backlight){
		videoBacklight = backlight;
	}
	
	public static long readCurrentTime(){
		
		
        return currentTime;
	}
	
	public static void writeCurrentTime(long currenttime){
		printLog(TAG, "currenttime-----"+currenttime);
		currentTime = currenttime;
	}
	

}
