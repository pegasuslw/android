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
package android.tcl.mediaplayer.forcetv;

import android.app.Activity;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory.Options;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.os.IBinder;
import android.os.RemoteException;
import android.preference.PreferenceManager;
import android.provider.MediaStore.Audio.Playlists;
import android.provider.MediaStore.Audio.Playlists.Members;
import android.util.Log;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;
import java.io.File;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Locale;

public class MediaUtils
{
  public static final int AUDIO_FILE_TYPE = 0;
  public static final int IMAGE_FILE_TYPE = 2;
  public static String LAST_PLAYLIST_PLAYED_KEY_VAL_PREF;
  public static String LAST_TRACK_PLAYED_PREF;
  public static String PLAYLIST_PREF;
  public static String PLAYLIST_PREF_EXTRA;
  public static String PLAYLIST_PREF_EXTRA1;
  public static int REPEAT_DEFAULT = 0;
  public static int SHUFFLE_DEFAULT = 0;
  private static final String TAG = "RP-MusicUtils";
  public static final int UNKNOWN_FILE_TYPE = 255;
  public static final int VIDEO_FILE_TYPE = 1;
  private static final String[] audioExtensions;
  public static int currentPlaylist;

 

  
  private static HashMap sFileTypeMap;
  private static StringBuilder sFormatBuilder;
  private static Formatter sFormatter;

  private static HashMap sMusicConnectionMap;


  private static HashMap sScannerConnectionMap;

  public static boolean slideShowMusicOn;
  private static final String[] videoExtensions;

  static
  {
    int i = 5;
    int j = 2;
    int k = 1;
    String[] arrayOfString1 = new String[17];
    arrayOfString1[0] = "rm";
    arrayOfString1[k] = "rv";
    arrayOfString1[j] = "rmvb";
    arrayOfString1[3] = "mp4";
    arrayOfString1[4] = "mpv";
    arrayOfString1[i] = "mkv";
    arrayOfString1[6] = "mov";
    arrayOfString1[7] = "3gp";
    arrayOfString1[8] = "3g2";
    arrayOfString1[9] = "wmv";
    arrayOfString1[10] = "wm";
    arrayOfString1[11] = "avi";
    arrayOfString1[12] = "mpg";
    arrayOfString1[13] = "ram";
    arrayOfString1[14] = "rms";
    arrayOfString1[15] = "flv";
    arrayOfString1[16] = "m4v";
    videoExtensions = arrayOfString1;
    String[] arrayOfString2 = new String[8];
    arrayOfString2[0] = "aac";
    arrayOfString2[k] = "ra";
    arrayOfString2[j] = "wma";
    arrayOfString2[3] = "mp3";
    arrayOfString2[4] = "wav";
    arrayOfString2[i] = "ogg";
    arrayOfString2[6] = "m4p";
    arrayOfString2[7] = "m4a";
    audioExtensions = arrayOfString2;
   
   
  
  }

  public static String formatTime(int param)
  {
	  String value ;
	  int hour=0,min=0, sec=0 ;
	  int temp ;
	  if(param<=0)
		  return "00:00:00";
	  
	  temp  = param/1000; //总共多少秒
	  sec = temp%60 ;
	  temp = temp/60; //总共多少分钟
	  min = temp%60 ;
	  hour = temp/60;
	  
	  if(hour<10)
		  value = "0"+Integer.toString(hour)+":";
	  else
		  value = Integer.toString(hour)+":";
	  if(min<10)
		  value += "0"+Integer.toString(min)+":";
	  else
		  value += Integer.toString(min)+":";
	  if(sec<10)
		  value += "0"+Integer.toString(sec);
	  else
		  value += Integer.toString(sec);
     return value;
  }

}