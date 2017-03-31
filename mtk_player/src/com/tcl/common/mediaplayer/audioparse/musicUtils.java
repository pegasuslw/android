package com.tcl.common.mediaplayer.audioparse;

import android.app.Activity;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaMetadataRetriever;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.TextUtils;
import android.text.format.Time;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.Window;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Formatter;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import com.tcl.common.mediaplayer.R;
import com.tcl.common.mediaplayer.utils.Utils;


public class musicUtils {

    private static final String TAG = "musicUtils";
    /*0523for browse files; */
    public static enum DeviceType{NODEVICE,SDCARD,USBSTOR,SMB,BROWSEFILE,UPNP};
    public static DeviceType device = DeviceType.NODEVICE;
    public static String deviceInforRec = null;
    public static String usbPathPre = "/data/usbdisk";
    public static String sdPathPre = "/mnt/sdcard";
    public static String nowDevicePath = null,nowDeviceName = null;
    public static int selectedItem = 0,selDeiveIndex = 0;
    /*bSelDeviceIn:（true）表示用户选择的设备还存在；
     * bTrying：（true）播放音乐的时候播放队列数据为空，正在尝试稍后再次查询数据库；
     * preparingPlay:(true)正在进行播放数据start之前的动作，如setDataSource、prepare等
     * */
    public static boolean bSelDeviceIn = true,bTrying = false,preparingPlay = false;

 
    public interface Defs {
        public final static int OPEN_URL = 0;
        public final static int ADD_TO_PLAYLIST = 1;
        public final static int USE_AS_RINGTONE = 2;
        public final static int PLAYLIST_SELECTED = 3;
        public final static int NEW_PLAYLIST = 4;
        public final static int PLAY_SELECTION = 5;
        public final static int GOTO_START = 6;
        public final static int GOTO_PLAYBACK = 7;
        public final static int PARTY_SHUFFLE = 8;
        public final static int SHUFFLE_ALL = 9;
        public final static int DELETE_ITEM = 10;
        public final static int SCAN_DONE = 11;
        public final static int QUEUE = 12;
        public final static int CHILD_MENU_BASE = 13; // this should be the last item
    }





    private final static long [] sEmptyList = new long[0];

    public static long [] getSongListForCursor(Cursor cursor) {
        if (cursor == null) {
            return sEmptyList;
        }
        int len = cursor.getCount();
        long [] list = new long[len];
        cursor.moveToFirst();
        int colidx = -1;
        try {
            colidx = cursor.getColumnIndexOrThrow(MediaStore.Audio.Playlists.Members.AUDIO_ID);
        } catch (IllegalArgumentException ex) {
            colidx = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID);
        }
        for (int i = 0; i < len; i++) {
            list[i] = cursor.getLong(colidx);
            cursor.moveToNext();
        }
        return list;
    }

    public static long [] getSongListForArtist(Context context, long id) {
        final String[] ccols = new String[] { MediaStore.Audio.Media._ID };
        String where = MediaStore.Audio.Media.ARTIST_ID + "=" + id + " AND " + 
        MediaStore.Audio.Media.IS_MUSIC + "=1";
        Cursor cursor = query(context, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                ccols, where, null,
                MediaStore.Audio.Media.ALBUM_KEY + ","  + MediaStore.Audio.Media.TRACK);
        
        if (cursor != null) {
            long [] list = getSongListForCursor(cursor);
            cursor.close();
            return list;
        }
        return sEmptyList;
    }
    
    public static Cursor getSongCurForArtist(Context context, String artist) {
        final String[] ccols = new String[] { MediaStore.Audio.Media._ID,
        		                              MediaStore.Audio.Media.ALBUM_ID,
        		                              MediaStore.Audio.Media.TITLE,
        		                              MediaStore.Audio.Media.DATE_ADDED};
        String where = MediaStore.Audio.Media.ARTIST + "=" + artist + " AND " + 
        MediaStore.Audio.Media.IS_MUSIC + "=1";
        Cursor cursor = query(context, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                ccols, where, null,
                MediaStore.Audio.Media.ALBUM_KEY + ","  + MediaStore.Audio.Media.TRACK);
        
        return cursor;
    }

    public static long [] getSongListForAlbum(Context context, long id) {
        final String[] ccols = new String[] { MediaStore.Audio.Media._ID };
        String where = MediaStore.Audio.Media.ALBUM_ID + "=" + id + " AND " + 
                MediaStore.Audio.Media.IS_MUSIC + "=1";
        Cursor cursor = query(context, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                ccols, where, null, MediaStore.Audio.Media.TRACK);

        if (cursor != null) {
            long [] list = getSongListForCursor(cursor);
            cursor.close();
            return list;
        }
        return sEmptyList;
    }

    public static long [] getSongListForPlaylist(Context context, long plid) {
        final String[] ccols = new String[] { MediaStore.Audio.Playlists.Members.AUDIO_ID };
        Cursor cursor = query(context, MediaStore.Audio.Playlists.Members.getContentUri("external", plid),
                ccols, null, null, MediaStore.Audio.Playlists.Members.DEFAULT_SORT_ORDER);
        
        if (cursor != null) {
            long [] list = getSongListForCursor(cursor);
            cursor.close();
            return list;
        }
        return sEmptyList;
    }



    /**
     * Fills out the given submenu with items for "new playlist" and
     * any existing playlists. When the user selects an item, the
     * application will receive PLAYLIST_SELECTED with the Uri of
     * the selected playlist, NEW_PLAYLIST if a new playlist
     * should be created, and QUEUE if the "current playlist" was
     * selected.
     * @param context The context to use for creating the menu items
     * @param sub The submenu to add the items to.
     */
    public static void makePlaylistMenu(Context context, SubMenu sub) {
        String[] cols = new String[] {
                MediaStore.Audio.Playlists._ID,
                MediaStore.Audio.Playlists.NAME
        };
        ContentResolver resolver = context.getContentResolver();
        if (resolver == null) {
            System.out.println("resolver = null");
        } else {
            String whereclause = MediaStore.Audio.Playlists.NAME + " != ''";
            Cursor cur = resolver.query(MediaStore.Audio.Playlists.EXTERNAL_CONTENT_URI,
                cols, whereclause, null,
                MediaStore.Audio.Playlists.NAME);
            sub.clear();
//            sub.add(1, Defs.QUEUE, 0, R.string.queue);
//            sub.add(1, Defs.NEW_PLAYLIST, 0, R.string.new_playlist);
            if (cur != null && cur.getCount() > 0) {
                //sub.addSeparator(1, 0);
                cur.moveToFirst();
                while (! cur.isAfterLast()) {
                    Intent intent = new Intent();
                    intent.putExtra("playlist", cur.getLong(0));
//                    if (cur.getInt(0) == mLastPlaylistSelected) {
//                        sub.add(0, MusicBaseActivity.PLAYLIST_SELECTED, cur.getString(1)).setIntent(intent);
//                    } else {
                        sub.add(1, Defs.PLAYLIST_SELECTED, 0, cur.getString(1)).setIntent(intent);
//                    }
                    cur.moveToNext();
                }
            }
            if (cur != null) {
                cur.close();
            }
        }
    }

    public static void clearPlaylist(Context context, int plid) {
        
        Uri uri = MediaStore.Audio.Playlists.Members.getContentUri("external", plid);
        context.getContentResolver().delete(uri, null, null);
        return;
    }
    

 

    
    
    public static Cursor query(Context context, Uri uri, String[] projection,
            String selection, String[] selectionArgs, String sortOrder, int limit) {
        try {
            ContentResolver resolver = context.getContentResolver();
            if (resolver == null) {
                return null;
            }
            if (limit > 0) {
                uri = uri.buildUpon().appendQueryParameter("limit", "" + limit).build();
            }
//            Log.i("musicUtils","to query content selection is  "+selection
//            		+" uri is "+uri);
            return resolver.query(uri, projection, selection, selectionArgs, sortOrder);
         } catch (UnsupportedOperationException ex) {
            return null;
        }
        
    }
    public static Cursor query(Context context, Uri uri, String[] projection,
            String selection, String[] selectionArgs, String sortOrder) {
        /*如果是USB需要改变URI*/
    	/*最新的版本SD卡，USB和SMB都扫入外部数据库, 但是SMB由于实现问题暂时不去去流派信息*/
    	if(/*(device == DeviceType.USBSTOR)||*/(device == DeviceType.SMB)){
    		if(uri == MediaStore.Audio.Genres.EXTERNAL_CONTENT_URI){
    			/*usb扫描到的数据库中没有流派这个表*/
    			return null;
    		}
//    		else{
//    		  uri = MediaStore.Audio.Media.INTERNAL_CONTENT_URI;
//    		}
    	}
    	//Log.i(TAG,"query uri is "+uri);//MediaStore.Audio.Media.EXTERNAL_CONTENT_URI content://media/external/audio/media
        return query(context, uri, projection, selection, selectionArgs, sortOrder, 0);
    }
    
    public static boolean isMediaScannerScanning(Context context) {
        boolean result = false;
        Cursor cursor = query(context, MediaStore.getMediaScannerUri(), 
                new String [] { MediaStore.MEDIA_SCANNER_VOLUME }, null, null, null);
        if (cursor != null) {
            if (cursor.getCount() == 1) {
                cursor.moveToFirst();
                result = "external".equals(cursor.getString(0));
            }
            cursor.close(); 
        } 
        Log.e(TAG,"localMusicBox isMediaScannerScanning return "+result);
        return result;
    }
    
    public static void setSpinnerState(Activity a) {
        if (isMediaScannerScanning(a)) {
            // start the progress spinner
            a.getWindow().setFeatureInt(
                    Window.FEATURE_INDETERMINATE_PROGRESS,
                    Window.PROGRESS_INDETERMINATE_ON);

            a.getWindow().setFeatureInt(
                    Window.FEATURE_INDETERMINATE_PROGRESS,
                    Window.PROGRESS_VISIBILITY_ON);
        } else {
            // stop the progress spinner
            a.getWindow().setFeatureInt(
                    Window.FEATURE_INDETERMINATE_PROGRESS,
                    Window.PROGRESS_VISIBILITY_OFF);
        }
    }
    
    private static String mLastSdStatus;

    public static void displayDatabaseError(Activity a) {
        if (a.isFinishing()) {
            // When switching tabs really fast, we can end up with a null
            // cursor (not sure why), which will bring us here.
            // Don't bother showing an error message in that case.
            return;
        }


    }
    
    public static void hideDatabaseError(Activity a) {

    }

    static protected Uri getContentURIForPath(String path) {
        return Uri.fromFile(new File(path));
    }

    
    /*  Try to use String.format() as little as possible, because it creates a
     *  new Formatter every time you call it, which is very inefficient.
     *  Reusing an existing Formatter more than tripled the speed of
     *  makeTimeString().
     *  This Formatter/StringBuilder are also used by makeAlbumSongsLabel()
     */
    private static StringBuilder sFormatBuilder = new StringBuilder();
    private static Formatter sFormatter = new Formatter(sFormatBuilder, Locale.getDefault());
    private static final Object[] sTimeArgs = new Object[5];

    public static String makeSizeString(Context context, long bytes) {
    	   Log.i(TAG," size is "+bytes);
           float sizeM = (float)bytes/1024;
           sizeM = sizeM/1024;
           String org = String.valueOf(sizeM);
           int point = org.lastIndexOf(".");
           if((point == -1)||(point>=(org.length()-3))){
        	   return org+"M";
           }

          return org.substring(0,point+3)+"M";
    }


    
    // A really simple BitmapDrawable-like class, that doesn't do
    // scaling, dithering or filtering.
    private static class FastBitmapDrawable extends Drawable {
        private Bitmap mBitmap;
        public FastBitmapDrawable(Bitmap b) {
            mBitmap = b;
        }
        @Override
        public void draw(Canvas canvas) {
            canvas.drawBitmap(mBitmap, 0, 0, null);
        }
        @Override
        public int getOpacity() {
            return PixelFormat.OPAQUE;
        }
        @Override
        public void setAlpha(int alpha) {
        }
        @Override
        public void setColorFilter(ColorFilter cf) {
        }
    }
    
    private static int sArtId = -2;
    private static Bitmap mCachedBit = null;
    private static final BitmapFactory.Options sBitmapOptionsCache = new BitmapFactory.Options();
    private static final BitmapFactory.Options sBitmapOptions = new BitmapFactory.Options();
    private static final Uri sArtworkUri = Uri.parse("content://media/external/audio/albumart");
    private static final HashMap<Long, Drawable> sArtCache = new HashMap<Long, Drawable>();
    private static int sArtCacheId = -1;
    
    static {
        // for the cache, 
        // 565 is faster to decode and display
        // and we don't want to dither here because the image will be scaled down later
        sBitmapOptionsCache.inPreferredConfig = Bitmap.Config.RGB_565;
        sBitmapOptionsCache.inDither = false;

        sBitmapOptions.inPreferredConfig = Bitmap.Config.RGB_565;
        sBitmapOptions.inDither = false;
    }


    public static void clearAlbumArtCache() {
        synchronized(sArtCache) {
            sArtCache.clear();
        }
    }
    
    public static Drawable getCachedArtwork(Context context, long artIndex, BitmapDrawable defaultArtwork) {
        Drawable d = null;
        synchronized(sArtCache) {
            d = sArtCache.get(artIndex);
        }
        if (d == null) {
            d = defaultArtwork;
            final Bitmap icon = defaultArtwork.getBitmap();
            int w = icon.getWidth();
            int h = icon.getHeight();
            Bitmap b = musicUtils.getArtworkQuick(context, artIndex, w, h);
            if (b != null) {
                d = new FastBitmapDrawable(b);
                synchronized(sArtCache) {
                    // the cache may have changed since we checked
                    Drawable value = sArtCache.get(artIndex);
                    if (value == null) {
                        sArtCache.put(artIndex, d);
                    } else {
                        d = value;
                    }
                }
            }
        }
        return d;
    }

    // Get album art for specified album. This method will not try to
    // fall back to getting artwork directly from the file, nor will
    // it attempt to repair the database.
    public static Bitmap getArtworkQuick(Context context, long album_id, int w, int h) {
        // NOTE: There is in fact a 1 pixel border on the right side in the ImageView
        // used to display this drawable. Take it into account now, so we don't have to
        // scale later.
    	if(album_id>=0){
    		w -= 1;
    		ContentResolver res = context.getContentResolver();
    		Uri uri = ContentUris.withAppendedId(sArtworkUri, album_id);
    		Log.i(TAG,"uri is "+uri);
    		if (uri != null) {
    			ParcelFileDescriptor fd = null;
    			try {
    				fd = res.openFileDescriptor(uri, "r");
    				int sampleSize = 1;
                
    				// Compute the closest power-of-two scale factor 
    				// and pass that to sBitmapOptionsCache.inSampleSize, which will
    				// result in faster decoding and better quality
    				sBitmapOptionsCache.inJustDecodeBounds = true;
    				BitmapFactory.decodeFileDescriptor(
                        fd.getFileDescriptor(), null, sBitmapOptionsCache);
    				int nextWidth = sBitmapOptionsCache.outWidth >> 1;
                	int nextHeight = sBitmapOptionsCache.outHeight >> 1;
                	while (nextWidth>w && nextHeight>h) {
                		sampleSize <<= 1;
                		nextWidth >>= 1;
                		nextHeight >>= 1;
                	}

                	sBitmapOptionsCache.inSampleSize = sampleSize;
                	sBitmapOptionsCache.inJustDecodeBounds = false;
                	Bitmap b = BitmapFactory.decodeFileDescriptor(
                        fd.getFileDescriptor(), null, sBitmapOptionsCache);

                	if (b != null) {
                		// finally rescale to exactly the size we need
                		if (sBitmapOptionsCache.outWidth != w || sBitmapOptionsCache.outHeight != h) {
                			Bitmap tmp = Bitmap.createScaledBitmap(b, w, h, true);
                			// Bitmap.createScaledBitmap() can return the same bitmap
                			if (tmp != b){
                				b.recycle();                				
                			}
                			b = tmp;
                		}
                	}
                
                	return b;
    			} catch (FileNotFoundException e) {
    			} finally {
    				try {
    					if (fd != null)
    						fd.close();
    				} catch (IOException e) {
    				}
    			}
    		}
    	}
        return getDefaultArtwork(context,w,h);
    }

    /** Get album art for specified album. You should not pass in the album id
     * for the "unknown" album here (use -1 instead)
     * This method always returns the default album art icon when no album art is found.
     */
    public static Bitmap getArtwork(Context context, long song_id, long album_id) {
        return getArtwork(context, song_id, album_id, true);
    }

    /** Get album art for specified album. You should not pass in the album id
     * for the "unknown" album here (use -1 instead)
     */
    public static Bitmap getArtwork(Context context, long song_id, long album_id,
            boolean allowdefault) {

        if (album_id < 0) {
            // This is something that is not in the database, so get the album art directly
            // from the file.
            if (song_id >= 0) {
            	Log.i(TAG,"get album image from file");
                Bitmap bm = getArtworkFromFile(context, song_id, -1);
                if (bm != null) {
                    return bm;
                }
            }
            if (allowdefault) {
            	Log.i(TAG,"getArtwork get default album image");
                return getDefaultArtwork(context);
            }
            return null;
        }

        ContentResolver res = context.getContentResolver();
        Uri uri = ContentUris.withAppendedId(sArtworkUri, album_id);
        if (uri != null) {
            InputStream in = null;
            try {
                in = res.openInputStream(uri);
                if(in == null){
                	Log.i(TAG,"getArtwork inputStream in is null");
                }
                else{
                	Log.i(TAG,"getArtwork get album image by decodeStream");
                }
                Bitmap bm = BitmapFactory.decodeStream(in, null, sBitmapOptions);
                if(bm == null){
                	Log.e(TAG,"getArtwork get album image by decodeStream return null to get default bm");
                	bm = getDefaultArtwork(context);
                }
                return bm;
            } catch (FileNotFoundException ex) {
                // The album art thumbnail does not actually exist. Maybe the user deleted it, or
                // maybe it never existed to begin with.
            	ex.printStackTrace();
            	Log.i(TAG,ex.getMessage());
//                Bitmap bm = getArtworkFromFile(context, song_id, album_id);
            	 Bitmap bm = null;
                if (bm != null) {
                    if (bm.getConfig() == null) {
                        bm = bm.copy(Bitmap.Config.RGB_565, false);
                        if (bm == null && allowdefault) {
                            return getDefaultArtwork(context);
                        }
                    }
                } else if (allowdefault) {
                	Log.i(TAG,"getArtwork get default album image");
                    bm = getDefaultArtwork(context);
                }
                return bm;
            } finally {
                try {
                    if (in != null) {
                        in.close();
                    }
                } catch (IOException ex) {
                }
            }
        }
        Log.e(TAG,"getArtwork return null");
        return null;
    }
    
    
    // get album art for specified file
    private static final String sExternalMediaUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI.toString();
    private static Bitmap getArtworkFromFile(Context context, long songid, long albumid) {
        Bitmap bm = null;
        byte [] art = null;
        String path = null;

        if (albumid < 0 && songid < 0) {
            throw new IllegalArgumentException("Must specify an album or a song id");
        }

        try {
        	Log.i(TAG,"getArtworkFromFile "+String.valueOf(albumid));
            if (albumid < 0) {
                Uri uri = Uri.parse("content://media/external/audio/media/" + songid + "/albumart");
                ParcelFileDescriptor pfd = context.getContentResolver().openFileDescriptor(uri, "r");
                if (pfd != null) {
                    FileDescriptor fd = pfd.getFileDescriptor();
                    bm = BitmapFactory.decodeFileDescriptor(fd);
                }
            } else {
            	
                Uri uri = ContentUris.withAppendedId(sArtworkUri, albumid);
                ParcelFileDescriptor pfd = context.getContentResolver().openFileDescriptor(uri, "r");
                if (pfd != null) {
                    FileDescriptor fd = pfd.getFileDescriptor();
                    bm = BitmapFactory.decodeFileDescriptor(fd);
                }
            }
        } catch (FileNotFoundException ex) {
        	Log.e(TAG,"getArtworkFromFile "+ex.getMessage());
        	bm = getDefaultArtwork(context);
        }
        if (bm != null) {
            mCachedBit = bm;
            Log.i(TAG,"getArtworkFromFile bm ok");
        }
        return bm;
    }
    
    public static Bitmap getDefaultArtwork(Context context,int w,int h) {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inPreferredConfig = Bitmap.Config.ARGB_8888;
        Bitmap bm = BitmapFactory.decodeStream(
                context.getResources().openRawResource(R.drawable.albumart_mp_unknown), null, opts);
       
       if (bm != null) {
           // finally rescale to exactly the size we need
           if (bm.getWidth() != w || bm.getHeight() != h) {
               Bitmap tmp = Bitmap.createScaledBitmap(bm , w, h, true);
               // Bitmap.createScaledBitmap() can return the same bitmap
               if (tmp != bm) bm.recycle();
               bm = tmp;
           }
       }
       
       return bm;
    }
    
    public static Bitmap getAlbumArtwork(String filePath,int w,int h) {
        Bitmap bitmap = null;
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            retriever.setDataSource(filePath);
            byte[] cover = retriever.getEmbeddedPicture();
            if(cover != null)
                bitmap = BitmapFactory.decodeByteArray(cover, 0, cover.length);
        } catch (IllegalArgumentException ex) {
            bitmap = null;
            ex.printStackTrace();
        } catch (RuntimeException ex) {
            bitmap = null;
            ex.printStackTrace();
        } finally {
            try {
                retriever.release();
            } catch (RuntimeException ex) {
                ex.printStackTrace();
            }
        }
       if (bitmap != null) {
    	   Utils.printLog(TAG, "getAlbumArtwork bitmap != null");
           // finally rescale to exactly the size we need
           if (bitmap.getWidth() != w || bitmap.getHeight() != h) {
               Bitmap tmp = Bitmap.createScaledBitmap(bitmap , w, h, true);
               // Bitmap.createScaledBitmap() can return the same bitmap
               if (tmp != bitmap) bitmap.recycle();
               bitmap = tmp;
           }
       }
       return bitmap;
    }
    
    
    public static Bitmap getNullArtwork(Context context,int w,int h) {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inPreferredConfig = Bitmap.Config.ARGB_8888;
        Bitmap bm = BitmapFactory.decodeResource(context.getResources(), R.drawable.album_null, opts);
       
       if (bm != null) {
           // finally rescale to exactly the size we need
           if (bm.getWidth() != w || bm.getHeight() != h) {
               Bitmap tmp = Bitmap.createScaledBitmap(bm , w, h, true);
               // Bitmap.createScaledBitmap() can return the same bitmap
               if (tmp != bm) bm.recycle();
               bm = tmp;
           }
       }
       
       return bm;
    }
    public static Bitmap getDefaultArtwork(Context context) {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inPreferredConfig = Bitmap.Config.ARGB_8888;
        return BitmapFactory.decodeStream(
                context.getResources().openRawResource(R.drawable.albumart_mp_unknown), null, opts);
    }
    
    static int getIntPref(Context context, String name, int def) {
        SharedPreferences prefs =
            context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        return prefs.getInt(name, def);
    }
    
    static void setIntPref(Context context, String name, int value) {
        SharedPreferences prefs =
            context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        Editor ed = prefs.edit();
        ed.putInt(name, value);
        ed.commit();
    }

    public static String getRightString(Context con,String str){
    	if(str == null){
    		return null;
    	}
    	String encording = getEncoding(str);    	
//		Log.i(TAG,"&&&&&&&&&&&songName encoding    "+encording+" String is " +str);
		String rightCode = null;
		
		try {
			rightCode = new String(str.getBytes(encording), encording);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(rightCode.equals("<unknown>")){
			rightCode = con.getString(R.string.audio_info_unknown);
		}
//		Log.i(TAG,"&&&&&&&&&&&rightCode    "+rightCode);
		return rightCode;
    }
    
    public static String getEncoding(String str) {
    	  
        String encode = "GB2312";


        try {
            byte[] stringBytes;
        	stringBytes = str.getBytes(encode);
//        	for(int i = 0;i<4;i++){
//        		if(i<stringBytes.length){
//        			Log.i(TAG,"GB2312 "+ i +" is "+Integer.toHexString((int)stringBytes[i]));
//        		}
//        	}
        	String gb2312Str = new String(stringBytes, encode);
//        	Log.i("getEncoding^^^^^^^^^^^","str is "+str +" gb2312Str "+gb2312Str);
            if (str.equals(gb2312Str)) {
                String s = encode;
                return s;
            }
        } catch (Exception exception) {
        }
        encode = "ISO-8859-1";
        try {
            if (str.equals(new String(str.getBytes(encode), encode))) {
                String s1 = encode;
                return s1;
            }
        } catch (Exception exception1) {
        }
        encode = "GBK";
        try {
            if (str.equals(new String(str.getBytes(encode), encode))) {
                String s3 = encode;
                return s3;
            }
        } catch (Exception exception3) {
        }
        
        encode = "GB18030";
        try {
            if (str.equals(new String(str.getBytes(encode), encode))) {
                String s3 = encode;
                return s3;
            }
        } catch (Exception exception3) {
        }
        encode = "UTF-8";
        try {
            if (str.equals(new String(str.getBytes(encode), encode))) {
                String s2 = encode;
                return s2;
            }
        } catch (Exception exception2) {
        }
        encode = "UNICODE";
        try {
            if (str.equals(new String(str.getBytes(encode), encode))) {
                String s3 = encode;
                return s3;
            }
        } catch (Exception exception3) {
        } 
//        Log.i(TAG,"return default as UTF-8");
        return "UTF-8";
 }
    
    static void setRingtone(Context context, long id) {
        ContentResolver resolver = context.getContentResolver();
        // Set the flag in the database to mark this as a ringtone
        Uri ringUri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, id);
        try {
            ContentValues values = new ContentValues(2);
            values.put(MediaStore.Audio.Media.IS_RINGTONE, "1");
            values.put(MediaStore.Audio.Media.IS_ALARM, "1");
            resolver.update(ringUri, values, null, null);
        } catch (UnsupportedOperationException ex) {
            // most likely the card just got unmounted
            Log.e(TAG, "couldn't set ringtone flag for id " + id);
            return;
        }

        String[] cols = new String[] {
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.TITLE
        };

        String where = MediaStore.Audio.Media._ID + "=" + id;
        Cursor cursor = query(context, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                cols, where , null, null);
        try {
            if (cursor != null && cursor.getCount() == 1) {
                // Set the system setting to make this the current ringtone
                cursor.moveToFirst();
                Settings.System.putString(resolver, Settings.System.RINGTONE, ringUri.toString());
//                String message = context.getString(R.string.ringtone_set, cursor.getString(2));
//                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }
    
    static int sActiveTabIndex = -1;
    

    static void processTabClick(Activity a, View v, int current) {
        int id = v.getId();
        if (id == current) {
            return;
        }

//        final TabWidget ll = (TabWidget) a.findViewById(R.id.buttonbar);

        activateTab(a, id);
//        if (id != R.id.nowplayingtab) {
//            ll.setCurrentTab((Integer) v.getTag());
//            setIntPref(a, "activetab", id);
//        }
    }
    
    static void activateTab(Activity a, int id) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        switch (id) {

        }
        intent.putExtra("withtabs", true);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        a.startActivity(intent);
        a.finish();
        a.overridePendingTransition(0, 0);
    }
    
    static void updateNowPlaying(Activity a) {

    }

    static void setBackground(View v, Bitmap bm) {

        if (bm == null) {
            v.setBackgroundResource(0);
            return;
        }

        int vwidth = v.getWidth();
        int vheight = v.getHeight();
        int bwidth = bm.getWidth();
        int bheight = bm.getHeight();
        float scalex = (float) vwidth / bwidth;
        float scaley = (float) vheight / bheight;
        float scale = Math.max(scalex, scaley) * 1.3f;

        Bitmap.Config config = Bitmap.Config.ARGB_8888;
        Bitmap bg = Bitmap.createBitmap(vwidth, vheight, config);
        Canvas c = new Canvas(bg);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        ColorMatrix greymatrix = new ColorMatrix();
        greymatrix.setSaturation(0);
        ColorMatrix darkmatrix = new ColorMatrix();
        darkmatrix.setScale(.3f, .3f, .3f, 1.0f);
        greymatrix.postConcat(darkmatrix);
        ColorFilter filter = new ColorMatrixColorFilter(greymatrix);
        paint.setColorFilter(filter);
        Matrix matrix = new Matrix();
        matrix.setTranslate(-bwidth/2, -bheight/2); // move bitmap center to origin
        matrix.postRotate(10);
        matrix.postScale(scale, scale);
        matrix.postTranslate(vwidth/2, vheight/2);  // Move bitmap center to view center
        c.drawBitmap(bm, matrix, paint);
        v.setBackgroundDrawable(new BitmapDrawable(bg));
    }

    static int getCardId(Context context) {
        ContentResolver res = context.getContentResolver();
        Cursor c = res.query(Uri.parse("content://media/external/fs_id"), null, null, null, null);
        int id = -1;
        if (c != null) {
            c.moveToFirst();
            id = c.getInt(0);
            c.close();
        }
        return id;
    }


    
     
     public static boolean bCheckCanResume(){
    	 boolean bRet = bSelDeviceIn;    	 
         Log.i(TAG,"bCheckCanResume return "+bRet);
         return bRet;
     }
     
     
  
     
     
     public static String convertToQueryString(String query){
    	 String key = query;

//    	 Log.i("convertToQueryString$$$$$$$$$$$$$$$$$$$$$",query);
    	 if(key != null){
    		 key = key.replace("[", "\\[");
    		 key = key.replace("'", "\\''");
    		 key = key.replace("%", "\\%");
    		 key = key.replace("_", "\\_");
    //		 key = key.replace("-", "\\-");
    	 }
//      	 Log.i("convertToQueryString$$$$$$$$$$$$$$$$$$$$$",key);
    	 return key;
     } 
     
     /*检查参数中的文件是不是音乐文件*/
     public static boolean isMusicFile(File fCheck){
     	
     	boolean bRet = false;
     	
     	String sName = fCheck.getName();
     	int pointInd = sName.lastIndexOf('.');
     	if(pointInd>=0){
     		String type = sName.substring(sName.lastIndexOf('.')+1, 
     			                             sName.length()).toLowerCase();
     		if(type.equals("mp3")||type.equals("wma")||type.equals("ogg")||type.equals("aac")
     				||type.equals("mid")||type.equals("wav")||type.equals("mp2")
     				||type.equals("flac")||type.equals("rm")||type.equals("ape")||type.equals("dts")
     				||type.equals("amr")||type.equals("ac3")||type.equals("m4a")||type.equals("midi")){    			
     			bRet = true;
     		}
     	}
     	return bRet;
     }



}
