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
package com.tcl.pvr.pvrplayer.video.ui;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.net.URLDecoder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import com.tcl.os.system.WindowManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.WallpaperManager;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;

import android.media.AudioManager;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.PowerManager;
import android.os.Process;
import android.os.PowerManager.WakeLock;
import android.tclwidget.TCLAlertDialog;
import android.tclwidget.TCLSeekBar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
//import android.view.WindowManager;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.tcl.deviceinfo.TDeviceInfo;
import com.tcl.os.storage.TStorageManager;
import com.tcl.os.storage.TStorageVolume;
import com.tcl.pvr.pvrplayer.R;
import com.tcl.pvr.pvrplayer.aidl.CommonConst;
import com.tcl.pvr.pvrplayer.utils.CrossPlatFormAnalyzer;
import com.tcl.pvr.pvrplayer.utils.MediaPlayerApplication;
import com.tcl.pvr.pvrplayer.utils.MyToast;
import com.tcl.pvr.pvrplayer.utils.NotePopupWindow;
import com.tcl.pvr.pvrplayer.utils.SeekBarPopWindow;
import com.tcl.pvr.pvrplayer.utils.Utils;
import com.tcl.pvr.pvrplayer.utils.VolumeController;
import com.tcl.pvr.pvrplayer.utils.WaitDialogCallBackInterface;
import com.tcl.pvr.pvrplayer.utils.WaitingDialog;
import com.tcl.pvr.pvrplayer.video.bookmark.BookMarkConst;
import com.tcl.pvr.pvrplayer.video.bookmark.BookMarkDB;
import com.tcl.pvr.pvrplayer.video.bookmark.StartPlayModeDialog;
import com.tcl.pvr.pvrplayer.video.contrl.IVideoPlayControlHandler;
import com.tcl.pvr.pvrplayer.video.contrl.VideoPlayControlCallback;
import com.tcl.pvr.pvrplayer.video.contrl.VideoPlayerContrlConsts;
import com.tcl.tvmanager.TDtvPvrManager;
import com.tcl.tvmanager.TDtvPvrPlayerManager;
import com.tcl.tvmanager.TTvCommonManager;
import com.tcl.tvmanager.TTvManager;
import com.tcl.tvmanager.TTvPictureManager;
import com.tcl.tvmanager.TTvUtils;
import com.tcl.tvmanager.TDtvPvrManager.PvrNotify;
import com.tcl.tvmanager.vo.DtvPvrDiskInfo;
import com.tcl.tvmanager.vo.DtvPvrMediaFileInfo;
import com.tcl.tvmanager.vo.DtvPvrMediaVideoDetail;
import com.tcl.tvmanager.vo.EnTCLCallBackSetSourceMsg;
import com.tcl.tvmanager.vo.EnTCLInputSource;
/*import com.tvos.common.PictureManager;
import com.tvos.common.TvManager;
import com.tvos.common.exception.TvCommonException;*/
//import com.usagestats.util.behavior.MediaPlayerBehavior;
import com.tcl.tvmanager.vo.EnTCLWindow;
import com.tcl.tvmanager.vo.VideoWindowRect;

public class VideoPlayerActivity extends Activity {
	private static final String TAG = "RVP_VideoPlayerActivity";
	private static final int DISMISS_PLAYER_CONTROL_BUTTONS = 0;
	private static final int DISPLAY_PLAYER_CONTROL_BUTTONS = 1;
	private static final int DISPLAY_PLAYER_PROGRESS_LAYOUT = 2;
	private static final int DISMISS_PLAYER_PROGRESS_LAYOUT = 3;
	private static final int DISMISS_PLAYER_CONTROL_BUTTONS_FIRST = 7895;
	private static final long VOLUM_BACK_TIME = 5000;
	private static final int SHOW_WAIT_DIALOG = 2342;
	private static final int DISSMISS_WAIT_DIALOG = 23442;
	private static final int REFRESH_PAUSEBUTON = 876983;
	private static final int REFRESH_DDIMAGE = 876988;
	private static final int REFRESH_DTSIMAGE = 876989;
	private static final int REFRESH_DDIMAGE_DISMISS = 876999;
	private static final int DISMISS_PLAYER_PAUSE_ICON = 856339;
	private static final int START_PLAY = 4; // 开始进行视频的播放，触发播放器控制接口；
	private static final int PLAYER_UI_INIT = 5;// 更新UI界面，以与播放保持一致；
	private static final int DISMISS_DIALOG_FOR_TIMEOUT = 7;// 播放准备超时；
	private static final int PLAYER_PAUSE = 9;// 播放暂停；
	private static final int PLAYER_START = 10;// 播放开始；
	private static final int EXIT_VIDEOPLAY = 4546354;// 退出视频播放；
	private static final int BACK_OR_PREVIOUS_STEP_SIZE = 30000;// 快进或者快退的跨度；
	private static final int VIDEO_PLAY__CHECK_BOOKMARK = 23235324;// 检查即将播放的视频的断点信息；
	private static final int DO_BOOKMARK_ACTION = 4585;// 进行断点信息的收录；
	private static final int VIDEO_PLAY_START_OR_PAUSE = 0x00000008;// 视频开始或者暂停；
	private static final int DISSMISS_VOLUME = 122;// display the pause
	private static final int DISPLAY_VOLUME = 435;// display the pause

	private static final int NEXT_VIDEO = 34534;// display the pause
	private static final int PRE_VIDEO = 345;// display the pause
	private static final int FAST_NEXT = 5467;// display the pause
	private static final int FAST_BACK = 4545;// display the pause
	private static final int CLOSE_MEDIA = 4544;// display the pause
	private static final int REFRESH_TOTAL_TIME = 135656;// 视频开始或者暂停；
	private static final int REFRESH_CURRENT_TIME = 565565;// 视频开始或者暂停；
	private boolean isOnNewIntent = false;
	private int m3DBtStatus = VideoPlayerUIConst.NO_3D;
	private int mClickedBtStatus = VideoPlayerUIConst.CLICKED_NO;
	// 播放模式的图片序列；
	private static final int mPlayerTypeIcon[] = {
			R.drawable.player_seqence_selector,
			R.drawable.player_single_selector,
			R.drawable.player_single_repeat_selector,
			R.drawable.player_rendom_selector, R.drawable.player_cycle_selector };
	// 播放模式的文字解释序列；
	private static final int mPlayerTypeText[] = {
			R.string.Video_Info_RepeatMode_Normal,
			R.string.Video_Info_RepeatMode_Single,
			R.string.Video_Info_RepeatMode_Re_one,
			R.string.Video_Info_RepeatMode_Random,
			R.string.Video_Info_RepeatMode_Re_all };

	private SurfaceHolder holder = null;
	private volatile int mCurrIndex = 0;
	private int sEndTime = 0;
	private Timer mTime = new Timer();
	private View mPlayerControlLayout;
	private View mPlayerProgressLayout;
	private TextView mName;
	private TextView mPlayEndTime;
	private TextView mPlayBeginTime;
	private FrameLayout mPlayerStartButton;
	private FrameLayout mPlayerSequenceButton;
	private FrameLayout mPlayerNext, mPlayerPrevious;
	private ImageButton mPlayerSaveButton;
	private FrameLayout mPlayerSetImageButton;
	private FrameLayout mPlayerAdvanceButton;
	private ImageButton mPlayerVolumeButton;
	private FrameLayout mPlayerTurn3DButton;
	private LinearLayout mPauseIcon;
	private TextView mPauseTimeTextView;
	private TextView mSubtitleTextView;
	private ImageView mDDIcon;

	private static final int sScrollRange_720 = 800;
	private static final int sScrollRange_1080 = 1194;
	private static final int sIndicatorRange = 0;
	// private AdvanceSetDialog advanceDialog = null;

	private NotePopupWindow popWindow = null;

	private boolean mIsInternetDisconnect;
	private boolean mIsSaveClicked;
	private boolean mIsSeeking = false;
	private boolean isVolumeChangedKeyClicked = false;
	private boolean isBreakDialogDissmiss = true;

	// 需要保存到历史播放
	private String mTheLastPath="";
	private String mTheLastName="";
	private int mTheLastPosition = VideoPlayerContrlConsts.GET_CURRENTPOSITION_ERROR;


	private WaitingDialog mDialog;
	private MyToast mToast;

	private boolean mPauseByNetWorkDisconnected = false;// 网络连接断开，播放暂停；

	private int isDismissControl = 0;

	private IVideoPlayControlHandler mVideoContrl = null;// 视频控制接口；
	private int mBreakPos = 0;
	private boolean isFirstNetChange = true;

	private volatile boolean isNextPreValid = true;
	private boolean isNeedSave = false;
	private int m3DModeSet = 0;
	private String mTheLast3DPath = null;
	private boolean is3DEnabled = true;
	private boolean isShowContrlBtnFirst = true;
	private long userStateTimeStart = -1;
	private ContentResolver resolver;
	private TCLSeekBar mPlayerSeekar;
	private SeekBarPopWindow seekBarPopWindow ;
	
	private FrameLayout mPlayerInfo;
	
	
	private TextView mPlayerStartText,mPlayerPreviousText,mPlayerNextText,mPlayerSetImageText,mPlayerAdvanceText,mPlayerTurn3DText,mPlayerInfoText,mPlayerSquenceText;
    
    	private boolean isFromWeb = false;
	// private boolean mIsVolumeClick = false;

	// private int mIndex;
    private LinearLayout mPlayerNameLiner;
    
	private TDtvPvrPlayerManager mDtvPvrPlayerManager;
	private String file;
	private DtvPvrMediaVideoDetail mMeidaDetail;
	private List<String> playList;
	//private boolean isPlaying = true;
	private String mVideoDate ="";
	private WakeLock wakeLock;
	private boolean ispreparePlayed = false; //已经开始播放
	private TTvManager mTvManager = null;
	
	private boolean isNeedPause = true;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Utils.printLog(TAG, "onCreate");
		resolver = getContentResolver();
		// final WallpaperManager mWPManager =
		// WallpaperManager.getInstance(this);
		// final Drawable bgDrawable = mWPManager.getDrawable();
		// getWindow().setBackgroundDrawable(bgDrawable);
		setContentView(R.layout.video_player);
		//showWatingDialog();
		mDtvPvrPlayerManager = TDtvPvrPlayerManager.getInstance(this);
		Utils.printLog(TAG, "mTvManager.setHandler start");
		mTvManager = TTvManager.getInstance(null);   
		mTvManager.addHandler(setSourceHandler,TTvUtils.TV_HANDLER_INDEX_TV_SET_SOURCE);
		
		EnTCLInputSource inputSource = TTvCommonManager.getInstance(this).getCurrentInputSource();
		Log.d(TAG,"now input source is "+ inputSource);
		if(inputSource != EnTCLInputSource.EN_TCL_DTV){
			TTvCommonManager.getInstance(this).setInputSource(EnTCLInputSource.EN_TCL_DTV,true);
			Utils.printLog(TAG, "setInputSource EN_TCL_DTV");
//			setSourceHandler.sendEmptyMessage(what)
//			mVideoPlayerHander.sendEmptyMessage(SHOW_WAIT_DIALOG);
		}else{
			Utils.printLog(TAG, "now do not need to change setInputSource ");
			isSourceChanged = true;
			 if (!getPlayList(getIntent())) {
					exitPlayforNoPlayList();
					return;
				}
				if (mCurrIndex < 0) {
					mCurrIndex = 0;
				}
		}
		

        mTvManager.setHandler(mMediaHanler, TTvUtils.TV_HANDLER_INDEX_PVR_EVENT);
    	Utils.printLog(TAG, "mTvManager.setHandler end");
        //		boolean prepare = mDtvPvrPlayerManager.prepare();
//		Toast.makeText(VideoPlayerActivity.this, "prepare:"
//				+ prepare , Toast.LENGTH_LONG).show();
//		
//		List<DtvPvrDiskInfo> diskList;
//		diskList = new ArrayList<DtvPvrDiskInfo>();
//		
//		TStorageManager storagemanager = TStorageManager.getInstance(this);
//		TStorageVolume[] storagevolume = storagemanager.getVolumeListTcl();
//		if (storagevolume != null) {
//			for (int index = 0; index < storagevolume.length; index++) {
//				String path = storagevolume[index].getPath();
//				String state = storagemanager.getVolumeState(path);
//				if (state.equals(Environment.MEDIA_MOUNTED) || state.equals(Environment.MEDIA_MOUNTED_READ_ONLY)) {
//					DtvPvrDiskInfo diskInfo = new DtvPvrDiskInfo();
//					diskInfo.devPath = storagemanager.getVolumeDevpath(path);
//					if(diskInfo.devPath != null) {
//						int idx = diskInfo.devPath.indexOf("block/");
//						int length = diskInfo.devPath.length();
//						diskInfo.devPath = diskInfo.devPath.substring(idx, length);
//						diskInfo.devPath = "/dev/" + diskInfo.devPath;
//						diskInfo.devPath = diskInfo.devPath.replace("sda/sda", "sda");
//						diskInfo.mountPath = path;
//						diskList.add(diskInfo);
//					}
//				}
//			}
//		}
//		
//		DtvPvrDiskInfo diskInfo = diskList.get(0);
//		if(diskInfo !=null) {
//			mDtvPvrPlayerManager.setPvrScanDirectory(diskInfo.mountPath);
//			Toast.makeText(VideoPlayerActivity.this, "setPvrScanDirectory, path="
//					+ diskInfo.mountPath, Toast.LENGTH_LONG).show();
//		}
//		
//	
//	
//		playList = new ArrayList<DtvPvrMediaFileInfo>();
//		playList.clear();
//		int count = mDtvPvrPlayerManager.getPlayListCount();
//		Toast.makeText(VideoPlayerActivity.this, "PlayListCount=" + count, Toast.LENGTH_LONG).show();
//		if(count > 0) {
//			playList = mDtvPvrPlayerManager.getPlayList(0, count);
//			if(playList != null && playList.size()>0) {
//				for (Iterator<DtvPvrMediaFileInfo> iterator = playList.iterator(); iterator.hasNext();) {
//					DtvPvrMediaFileInfo info = (DtvPvrMediaFileInfo) iterator.next();
//				
//				}
//			}
//		}
//		for(int i=0;i<playList.size();i++){
//			file = playList.get(i);
//			Utils.printLog(TAG, "playid"+i+"playname"+ file.name);
//			Utils.printLog(TAG, ""+ file.path);
//			Utils.printLog(TAG, ""+ file.sizeInByte);
//		}
//	    file = playList.get(0);
//	    
//		if(file !=null) {
//			
//				int idx = file.path.indexOf("/PVR");
//				String mountPath = file.path.substring(0, idx);
//				TDtvPvrManager mPvrManager = TDtvPvrManager.getInstance(VideoPlayerActivity.this);
//				DtvPvrDiskInfo mDiskInfo= new DtvPvrDiskInfo();
//				mDiskInfo.mountPath = mountPath;
//				mDiskInfo.devPath = "";
//				
//				Log.i(TAG, "Mount Path:" + mDiskInfo.mountPath);
//				mPvrManager.setDiskInfo(mDiskInfo);
//			Utils.printLog(TAG, file.path);
//			mDtvPvrPlayerManager.start(file.path);
//		}
//		
//		mVideoPlayerHander.sendEmptyMessage(PLAYER_UI_INIT);
    	setTVToFullScreen();   //20160126 set tv to fullscreen
		
	
        
	      
        //app and histroy keycode force cut
		boolean isactive = false;
		int num = 5;
		int[] keycode = new int[5];
		keycode[0] = 4055; 
		keycode[1] = 4051;
		keycode[2] = 4507;  //HDMI1
		keycode[3] = 4506;  //AV1
		keycode[4] = 4057;   //USB
		String pkname = "com.tcl.pvr.pvrplayer"; 
		WindowManager win = new WindowManager(VideoPlayerActivity.this);
		win.activeKeyByKeycodeCnPn(num,keycode,isactive,null,pkname);
		// 20160325
		
		seekBarPopWindow = new SeekBarPopWindow(this, mMediaHanler);
		popWindow = new NotePopupWindow(this, mMediaHanler);
		findView();
		mVideoContrl = new IVideoPlayControlHandler(this);
		mVideoContrl.registerCallback(mCallback);
		MediaPlayerApplication application = (MediaPlayerApplication) getApplication();
		application.setVideoContrl(mVideoContrl);
	
		mBookMark = new BookMarkDB(this);


		mTime = new Timer();
		mTime.schedule(mTimerTask, 0, 1000);

		IntentFilter intentFilter3 = new IntentFilter();
		intentFilter3.addAction(VideoPlayerUIConst.COLLECTION_OPERATE_DONE);
		intentFilter3.addAction(VideoPlayerUIConst.ATV_OSD_OPEN);
		intentFilter3.addAction(CommonConst.CLOSE_VIDEO_PLAY);
		intentFilter3.addAction(CommonConst.HOME_PRESSED);
		intentFilter3.addAction(CommonConst.STR_PORWER_CHANGE);  //for STR shut down @WJ
		intentFilter3.addAction(CommonConst.TV_PRESSED);
		intentFilter3.addAction(CommonConst.SOUCE_CHANGE_PRESSED);
		intentFilter3.addAction(CommonConst.REFRESH_PAUSE_ICON);
		this.registerReceiver(myCollectionBroadcastReceiver, intentFilter3);
		
		//设置屏幕保持唤醒
		PowerManager powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
		wakeLock = powerManager.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "VideoPlayer");

//       if(isFromWeb){
//				mVideoContrl.setPlayType(VideoPlayerContrlConsts.MEDIA_SEQUENCE_PLAY);
//				mPlayerSequenceButton.setVisibility(View.GONE);
//			}else{
//				mVideoContrl.setPlayType(getSavedPlayerType());
//				mPlayerSequenceButton
//						.setBackgroundResource(mPlayerTypeIcon[mVideoContrl
//								.getPlayType()]);
//				mPlayerSquenceText.setText(mPlayerTypeText[mVideoContrl.getPlayType()]);
//			}
	
	}

	private void contrlIsNextPreValid() {
		new Timer().schedule(new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				Utils.printLog(TAG, "timer  set isNextPreValid = true; ");
				isNextPreValid = true;
				cancel();
			}
		}, 2000);
	}

	public void playNext(){
		int curPlayMode = mVideoContrl.getPlayType();
		String[] playModeNames;
		playModeNames = new String[4];
		playModeNames[0] = getResources().getString(
				R.string.Video_Info_RepeatMode_Normal);
		playModeNames[1] = getResources().getString(
				R.string.Video_Info_RepeatMode_Re_one);
		playModeNames[2] = getResources().getString(
				R.string.Video_Info_RepeatMode_Random);
		playModeNames[3] = getResources().getString(
				R.string.Video_Info_RepeatMode_Re_all);
		
		//Log.d("liuwei03", "curPlayMode:"+curPlayMode);
		if(playModeNames[curPlayMode].equals( getResources().getString(
				R.string.Video_Info_RepeatMode_Normal))){
			mCurrIndex++;
			if(mCurrIndex >= playList.size()){
				return;
			}
			
		}else if(playModeNames[curPlayMode].equals(getResources().getString(
				R.string.Video_Info_RepeatMode_Re_one))){
		}else if(playModeNames[curPlayMode].equals(getResources().getString(
				R.string.Video_Info_RepeatMode_Random))){
			Random random = new Random();
			int ran = random.nextInt(playList.size());
			int count = 0;
			while(mCurrIndex == ran || count<10 ){
				ran = random.nextInt(playList.size());
				count++;
			}
			mCurrIndex = ran;
		}else if(playModeNames[curPlayMode].equals( getResources().getString(
				R.string.Video_Info_RepeatMode_Re_all))){
			mCurrIndex++;
			if(mCurrIndex >= playList.size()){
				mCurrIndex = 0;
			}
			
		}
		
		file = playList.get(mCurrIndex);
		if (file != null) {
			Log.d("liuwei03", "file:"+file);
			mDtvPvrPlayerManager.start(file);
			mMediaHanler.sendEmptyMessage(START_PLAY);
			mVideoPlayerHander.sendEmptyMessage(PLAYER_UI_INIT);
		}
	}
	private Handler mMediaHanler = new Handler() {
	
		public void handleMessage(Message msg) {
			Utils.printLog(TAG, "PvrNotify.NOTIFY_PVR_PLAYBACK");
			Log.d(TAG,"now msg is "+ msg.what);
			if (msg.what == CLOSE_MEDIA) {
				new Thread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						mVideoContrl.closeMediaControl();

					}
				}).start();

			} else if (msg.what == NEXT_VIDEO) {
				Utils.printLog(TAG, "Handler msg :NEXT_VIDEO");
				
				if(mVideoContrl != null&&mVideoContrl.getPlayType()==0){
					if (playList != null && playList.size() > 0 && mCurrIndex == (playList.size()-1)){
						Utils.printLog(TAG, "THE last pvr");
						return;
					}	 
				}

				if (isNextPreValid) {
					isNextPreValid = false;
					contrlIsNextPreValid();
					if (mVideoContrl != null) {
						new Thread(new Runnable() {

							@Override
							public void run() {
								// TODO Auto-generated method stub
								ispreparePlayed = false; 
								//mVideoContrl.playNext();
								playNext();
							}
						}).start();
					}
				} else {
					Utils.printLog(TAG, " next invalid!");
				}

			} else if (msg.what == PRE_VIDEO) {
				Utils.printLog(TAG, "Handler msg :PRE_VIDEO");
				if(mVideoContrl != null&&mVideoContrl.getPlayType()==0){
					if (playList != null && playList.size() > 0 && mCurrIndex == 0){
						Utils.printLog(TAG, "THE first pvr");
						return;
					}	 
				}
				if (isNextPreValid) {

					isNextPreValid = false;
					contrlIsNextPreValid();
					if (mVideoContrl != null) {
						new Thread(new Runnable() {

							@Override
							public void run() {
								// TODO Auto-generated method stub
								ispreparePlayed = false; 
								mVideoContrl.playPrevious();

							}
						}).start();
					}
				} else {
					Utils.printLog(TAG, " next invalid!");
				}

			} else if (msg.what == FAST_BACK) {
				Utils.printLog(TAG, "Handler msg :FAST_BACK");
				new Thread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						mVideoContrl.maltiSpeedPlayPrevious();

					}
				}).start();

			} else if (msg.what == FAST_NEXT) {
				Utils.printLog(TAG, "Handler msg :FAST_NEXT");
				new Thread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						mVideoContrl.maltiSpeedPlayNext();

					}
				}).start();

			} else if (msg.what == START_PLAY) {// 首次开始播放；
				 mVideoContrl.play(playList, mCurrIndex);

			} else if (msg.what == BookMarkConst.START_PLAY_FROM_BEGIN) {

				Utils.printLog(TAG, "START_PLAY_FROM_BEGIN");

				new Thread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						mVideoContrl.start();
						isBreakDialogDissmiss = true;
					//	sendFirstShowContrlBtn();
						// start_3Dmode();
					}
				}).start();
				// mVideoPlayerHander.sendEmptyMessage(DISSMISS_WAIT_DIALOG);

			} else if (msg.what == BookMarkConst.START_PLAY_FROM_BREAK) {
				Utils.printLog(TAG, "START_PLAY_FROM_BREAK with pos = "
						+ mBreakPos);

				new Thread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						mVideoContrl.seekTo(mBreakPos);
						mVideoContrl.start();
						isBreakDialogDissmiss = true;

						// start_3Dmode();

					}
				}).start();
				// mVideoPlayerHander.sendEmptyMessage(DISSMISS_WAIT_DIALOG);

			} else if (msg.what == CommonConst.PLAY_DEVICE_UNMOUTED) {
				new MyToast(VideoPlayerActivity.this, VideoPlayerActivity.this
						.getResources().getString(
								R.string.media_source_not_found)).show();
			} else if (msg.what == CommonConst.media_player_exception) {
				new MyToast(VideoPlayerActivity.this, VideoPlayerActivity.this
						.getResources().getString(
								R.string.media_player_exception)).show();
			} else if (msg.what == CommonConst.media_player_unknown_exception) {
				new MyToast(VideoPlayerActivity.this, VideoPlayerActivity.this
						.getResources().getString(
								R.string.media_player_unknown_exception))
						.show();
			} else if (msg.what == CommonConst.PLAY_LIST_NULL) {
				// new MyToast(VideoPlayerActivity.this,
				// VideoPlayerActivity.this.getResources().getString(
				// R.string.media_player_listNull)).show();
			} else if (msg.what == CommonConst.media_player_init_error) {
				new MyToast(VideoPlayerActivity.this.getApplicationContext(),
						VideoPlayerActivity.this.getApplicationContext()
								.getResources()
								.getString(R.string.media_player_init_error))
						.show();
			} else if (msg.what == CommonConst.media_source_not_found) {

				new MyToast(VideoPlayerActivity.this, VideoPlayerActivity.this
						.getResources().getString(
								R.string.media_source_not_found)).show();

			} else if (msg.what == CommonConst.unknown_media_format) {
				Utils.printLog("MeidaHanlder", "unknown_media_format");
				new MyToast(VideoPlayerActivity.this, VideoPlayerActivity.this
						.getResources()
						.getString(R.string.unknown_media_format)).show();
			} else if (msg.what == CommonConst.media_interrupt) {
				new MyToast(VideoPlayerActivity.this, VideoPlayerActivity.this
						.getResources().getString(R.string.media_interrupt))
						.show();

			} else if (msg.what == CommonConst.unknown_video_format) {
				new MyToast(VideoPlayerActivity.this, VideoPlayerActivity.this
						.getResources()
						.getString(R.string.unknown_video_format)).show();

			} else if (msg.what == CommonConst.unknown_audio_format) {
				new MyToast(VideoPlayerActivity.this, VideoPlayerActivity.this
						.getResources()
						.getString(R.string.unknown_audio_format)).show();

			} else if (msg.what == CommonConst.media_player_network_slow) {
				new MyToast(VideoPlayerActivity.this, VideoPlayerActivity.this
						.getResources().getString(
								R.string.media_player_network_slow)).show();

			} else if (msg.what == CommonConst.media_player_not_seekable) {
				new MyToast(VideoPlayerActivity.this, VideoPlayerActivity.this
						.getResources().getString(
								R.string.media_player_not_seekable)).show();

			} else if (msg.what == CommonConst.media_player_no_audio) {
				new MyToast(VideoPlayerActivity.this, VideoPlayerActivity.this
						.getResources().getString(
								R.string.audiotrack_iszero)).show();

			}  else if (msg.what == PvrNotify.NOTIFY_PVR_PLAYBACK_END) {
				Utils.printLog(TAG, "PvrNotify.NOTIFY_PVR_PLAYBACK_END ");
	                if(playList == null||playList.size()==0 ||mVideoContrl == null){
	                	exitVideoPlay();
	                }
					if(mVideoContrl.getPlayType()==0&&mCurrIndex == (playList.size()-1)){
						Utils.printLog(TAG, "THE last pvr exit");
						exitVideoPlay();
					}else{
						isNextPreValid = true;
						mMediaHanler.sendEmptyMessage(NEXT_VIDEO);
				    }
				
			}  else if (msg.what == PvrNotify.NOTIFY_PVR_PLAYBACK_ERR) {
				Utils.printLog(TAG, "PvrNotify.NOTIFY_PVR_PLAYBACK_ERR ");
				new MyToast(VideoPlayerActivity.this, VideoPlayerActivity.this
						.getResources().getString(
								R.string.media_player_unknown_exception)).show();
				exitVideoPlay();
			}  else if (msg.what == PvrNotify.NOTIFY_PVR_PLAYBACK_LOOP_EXIT) {
				Utils.printLog(TAG, "PvrNotify.NOTIFY_PVR_PLAYBACK_LOOP_EXIT ");
				exitVideoPlay();   //20160913 wj
			}  else if (msg.what == PvrNotify.NOTIFY_PVR_PLAYBACK_START) {
				Utils.printLog(TAG, "PvrNotify.NOTIFY_PVR_PLAYBACK_START ");
				ispreparePlayed = true;
			} 
			


		}
	};

	private void sendFirstShowContrlBtn() {
		if (isShowContrlBtnFirst) {
			isShowContrlBtnFirst = false;
			mVideoPlayerHander.sendEmptyMessage(DISPLAY_PLAYER_CONTROL_BUTTONS);
		}
	}

	private int getSavedPlayerType() {
		// 载入保存的设定项
		int playtype = 0;
		SharedPreferences settings = getSharedPreferences(
				VideoPlayerUIConst.MY_SPREFS_FILE, 0);
		playtype = settings.getInt(VideoPlayerUIConst.VEDIO_PLAY_TYPE, 0);
		Utils.printLog(TAG, "getSaved sCurrentPlayerType =" + playtype);
		return playtype;
	}

	// 由于播放列表为空而退出视频播放；
	private void exitPlayforNoPlayList() {
		Utils.printLog(TAG, "exitPlayforNoPlayList");
		new MyToast(VideoPlayerActivity.this, getResources().getString(
				R.string.media_player_listNull)).show();
		VideoPlayerActivity.this.finish();
	}

	@Override
	protected void onNewIntent(Intent intent) {
		// TODO Auto-generated method stub
		Utils.printLog(TAG, "onNewIntent");
//		if (!getPlayList(intent)) {
//			exitPlayforNoPlayList();
//			return;
//		}
//	
//		if (mVideoContrl != null&& mVideoContrl.isFinishInit()) {
//			mVideoContrl.setFinishInit(false);
//			isOnNewIntent = true;
//			mVideoContrl.releaseMediaPlayer();
//		}
		super.onNewIntent(intent);
		Utils.printLog(TAG, "onNewIntent  end");
	}

	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		// TODO Auto-generated method stub
//		if (isDismissControl == 0) {
//			isDismissControl = 1;
//			Utils.printLog(TAG, "dispatchKeyEvent isDismissControl = 1");
//		}
		if(mPlayerControlLayout.getVisibility() == View.VISIBLE || mPlayerProgressLayout.getVisibility() == View.VISIBLE ){
			mVideoPlayerHander.removeMessages(DISMISS_PLAYER_PROGRESS_LAYOUT);
			mVideoPlayerHander.sendEmptyMessageDelayed(DISMISS_PLAYER_PROGRESS_LAYOUT, Utils.MENU_TIMEOUT);// ３秒后消失；
		}

		return super.dispatchKeyEvent(event);
	}

	/*
	 * 从应用传递的Intent中解析出统一的播放列表；
	 * 
	 * @如果列表为空，返回false，否则返回true;
	 */

	private boolean getPlayList(Intent intent) {

		if (intent.getAction().equals("application.vnd.tcl.playlist.pvr")) {
			Log.d(TAG,"equals application.vnd.tcl.playlist.pvr");
			isNeedSave = intent.getBooleanExtra("isNeedSave", false);
			m3DModeSet = intent.getIntExtra("3dmode", 0);
			//is3DEnabled = new CrossPlatFormAnalyzer().isNeed3DFunction();
			
			// 判断平台是否支持3D
			TDeviceInfo devinfo = TDeviceInfo.getInstance();
			int is3dState = devinfo.get3DStatus(devinfo.getProjectID());
			if (is3dState == 0) {
				is3DEnabled = false;
			}
			Utils.printLog(TAG,"isNeed3DFunction is3DEnabled ="
							+ is3DEnabled);
			playList = new ArrayList<String>();
			playList.clear();
			if (intent.getType().equals("application/vnd.tcl.playlist-pvr")) {
//				playList = intent.getParcelableArrayListExtra("playlist");
				playList = intent.getStringArrayListExtra("playlist");
				mCurrIndex = intent.getIntExtra("index", 0);
				Utils.printLog(TAG, "mCurrIndex " + mCurrIndex);
				for (int i = 0; i < playList.size(); i++) {
					file = playList.get(i);
//					Utils.printLog(TAG, "" + file.name);
					Utils.printLog(TAG, "" + file);
//					Utils.printLog(TAG, "" + file.sizeInByte);
				}
				
//				String path = "/storage/2897-FDDB/PVR/05.01 Globo HD_20080804_103730/05.01 Globo HD_20080804_103730.pvr";
//                playList.add(path);
//                Log.d(TAG,"play list add 1");
//                mCurrIndex = 0;
			}
			Log.d("======list size is ","="+playList.size());
			if (playList.size() > 0) {

				file = playList.get(mCurrIndex);
				if (file != null) {

					// int idx = file.path.indexOf("/PVR");
					// String mountPath = file.path.substring(0, idx);
					// TDtvPvrManager mPvrManager =
					// TDtvPvrManager.getInstance(VideoPlayerActivity.this);
					// DtvPvrDiskInfo mDiskInfo= new DtvPvrDiskInfo();
					// mDiskInfo.mountPath = mountPath;
					// mDiskInfo.devPath = "";
					//
					// Log.i(TAG, "Mount Path:" + mDiskInfo.mountPath);
					// mPvrManager.setDiskInfo(mDiskInfo);
					Utils.printLog(TAG, file);
					mDtvPvrPlayerManager.start(file);
					mMediaHanler.sendEmptyMessage(START_PLAY);
					mVideoPlayerHander.sendEmptyMessage(PLAYER_UI_INIT);
				}else{
					return false;
				}

			

				return true;
			} else {
				return false;
			}

		} else {
			return false;
		}
	}

	/*
	 * 为随便听听发送关闭播放广播；
	 */
	private void sendStopWidgetMusicBroadcast() {

		Intent intent = new Intent();
		intent.setAction(CommonConst.ACTION_STOP_WIDGETMUSIC);
		sendBroadcast(intent);
		Utils.printLog(TAG, "send Broadcast MSG_ATV_ACTIVE");
	}

	@Override
	protected void onResume() {
		super.onResume();
		Utils.printLog(TAG, "onResume");

		Utils.printLog(TAG, "onResume mIsInternetDisconnect ="
				+ mIsInternetDisconnect);
		if (mIsInternetDisconnect) {
			// 如果是网络是网络断开，又连接而触发的onResume(),不做任何处理
			mIsInternetDisconnect = false;
			return;
		}
		Utils.printLog(TAG, "onResume mIsSaveClicked =" + mIsSaveClicked);

		if (mIsSaveClicked) {
			// 如果保存按钮被单击,弹出保存菜单，然后退出保存菜单，进而触发onResume()，不做任何处理
			mIsSaveClicked = false;
			// mVideoPlayerHander.sendEmptyMessage(DISPLAY_PLAYER_CONTROL_BUTTONS);
			return;
		}
		
		isNeedPause = true;  //add here for quick key press 20161115 WJ
		
      	if(	mClickedBtStatus == VideoPlayerUIConst.CLICKED_3D||mClickedBtStatus == VideoPlayerUIConst.CLICKED_ADVANCE||mClickedBtStatus == VideoPlayerUIConst.CLICKED_IMAGE||mClickedBtStatus == VideoPlayerUIConst.CLICKED_VIDEOINFO){
			mVideoPlayerHander.sendEmptyMessage(DISPLAY_PLAYER_CONTROL_BUTTONS);
			return ;
		}
		if (isOnNewIntent) {
			mMediaHanler.sendEmptyMessage(START_PLAY);
			Utils.printLog(TAG, "isOnNewIntent PLAY_ONCE");
			isOnNewIntent = false;
		}

		if(null != wakeLock){
			wakeLock.acquire();
			Log.v(TAG, "onCreate wakeLock.acquire() wakeLock = "+wakeLock);
		}
		// 在线播放，需重新初始化播放信息
		Utils.printLog(TAG, "onResume end");
	}

	@Override
	protected void onPause() {
		super.onPause();
		Utils.printLog(TAG, "onPause start");
		
		
		if (isFinishing() == false && isNeedPause) {
			Log.d(TAG,"on pause do finish action");
			exitVideoPlay();
			VideoPlayerActivity.this.finish();
	    }
		if (mIsInternetDisconnect || mIsSaveClicked
				|| m3DBtStatus == VideoPlayerUIConst.SHOW_MENU_3D
				|| m3DBtStatus == VideoPlayerUIConst.SHOW_VOLUME_3D
				|| m3DBtStatus == VideoPlayerUIConst.NO_MENU_3D) {
			// 如果是网络是网络断开触发的onPause(),不做任何处理
			return;
		}
		
		// 电影在不可见后，不能播放，需关闭Activity.
		dismissWaittingDialog();
		mVideoPlayerHander.removeMessages(DISMISS_DIALOG_FOR_TIMEOUT);
		try {
			if(wakeLock!=null){
				wakeLock.release();
				Log.v(TAG, "wakeLock.release()");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		Utils.printLog(TAG, "onPause end ");
	}

	/*
	 * 保存视频播放的类型到本地；
	 */
	private void saveVideoPlayType() {
		if (mVideoContrl == null) {
			return;
		}
		SharedPreferences settings = getSharedPreferences(
				VideoPlayerUIConst.MY_SPREFS_FILE, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putInt(VideoPlayerUIConst.VEDIO_PLAY_TYPE,
				mVideoContrl.getPlayType());
		editor.commit();
	}

	protected void onStop() {
		Utils.printLog(TAG, "onStop start");
		// mVideoContrl.pause();
		// if (mVideoContrl.isMediaPlayerPrepared()) {
		// mTheLastPosition = mVideoContrl.getCurrentPosition();
		// sEndTime = mVideoContrl.getDuration();
		// Utils.printLog(TAG, "mTheLastPosition " +
		// mTheLastPosition+"  sEndTime ="+sEndTime);
		// }else{
		// mTheLastPosition = VideoPlayerContrlConsts.GET_CURRENTPOSITION_ERROR;
		// }
		// 发送广播通知客户端保存历史播放

		// mMediaHanler.sendEmptyMessage(CLOSE_MEDIA);// 关闭后台播放器控制；

		// if (advanceDialog != null) {
		// advanceDialog.dismiss();
		// }
		
//		if (isFinishing() == false) {
//			exitVideoPlay();
//			VideoPlayerActivity.this.finish();
//	    }
		if(mTvManager != null){
			mTvManager.removeHandler(setSourceHandler,TTvUtils.TV_HANDLER_INDEX_TV_SET_SOURCE);
		}
		try {
			if(myCollectionBroadcastReceiver!=null){
				unregisterReceiver(myCollectionBroadcastReceiver);
			}
			if(mVideoContrl!=null){
				mVideoContrl.unRegisterReceiver();
			}
		} catch (Exception il) {

			il.printStackTrace();
		}

	//	mVideoPlayerHander.sendEmptyMessage(DISSMISS_WAIT_DIALOG);
		super.onStop();
		Log.d(TAG, "onStop end");
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		Log.d(TAG, "onStart start");
		super.onStart();

		// mMediaHanler.sendEmptyMessage(START_PLAY);
		Log.d(TAG, "onStart end");

	}

	private void start_3Dmode() {
		Utils.printLog(TAG, "start3Dmode");
		if (!is3DEnabled) {
			Utils.printLog(TAG, "start_3Dmode is3DEnabled =false");
			return;
		}
		Intent intent = new Intent("com.android.tcl.videostart");
		Utils.printLog(TAG, "3dmode =" + m3DModeSet);
		intent.putExtra("3dmode", m3DModeSet);

		if (playList.size() > 0 && mCurrIndex < playList.size() && mCurrIndex >= 0) {

			try {
				String mCurrPath = playList.get(mCurrIndex);
				Utils.printLog(TAG, "mTheLast3DPath =" + mTheLast3DPath
						+ "; mCurrPath =" + mCurrPath);
				if (mTheLast3DPath != null
						&& mTheLast3DPath.equalsIgnoreCase(mCurrPath)) {
					intent.putExtra("SameFile", true);
					Utils.printLog(TAG, "SameFile true ");
				}
				mTheLast3DPath = mCurrPath;
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}

		}
		VideoPlayerActivity.this.sendBroadcast(intent);

		Utils.printLog(TAG, "sendBroadcast  com.android.tcl.videostart ");
	}

	private void exit_3Dmode() {
		Utils.printLog(TAG, "exit3Dmode");
		if (!is3DEnabled) {
			Utils.printLog(TAG, "exit3Dmode is3DEnabled =false");
			return;
		}
		Intent intent = new Intent("com.android.tcl.videoexit");
		intent.putExtra("isPVR", true);
		VideoPlayerActivity.this.sendBroadcast(intent);
		Utils.printLog(TAG, "sendBroadcast  com.android.tcl.videoexit ");
		// Intent intent = new Intent(VideoPlayerUIConst.NOTIFY_VIDEO_3D);
		// VideoPlayerActivity.this.sendBroadcast(intent);

		// boolean bRet = false;
		// int setTo = 1;
		// TVContent tvContent = TVContent.getInstance(VideoPlayerActivity.this,
		// true);
		// TVConfigurer mCfg = tvContent.getConfigurer();
		// TVOption<Integer> mTVOption3DMode = (TVOption<Integer>) mCfg
		// .getOption(BaseConfigType.CFG_3D_MODE);
		// ;
		// if (mTVOption3DMode == null) {
		// Log.e(TAG, "set3DMode mTVOption3DMode is null!!!!");
		// } else {
		// int cur3dMode = mTVOption3DMode.get();
		// if (cur3dMode != setTo) {
		// bRet = mTVOption3DMode.set(setTo);
		// } else {
		// Log.e(TAG, "set3DMode mTVOption3DMode alreay is " + cur3dMode);
		// }
		// }
		// if (bRet == false) {
		// Log.e(TAG, "set3DMode " + setTo + " fail!!!!!!!!!!!");
		// }else{
		// Log.e(TAG, "set3DMode " + setTo + " sucesss!!!!!!!!!!!");
		// }
		//

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		Utils.printLog(TAG, "onDestroy start");

		mTimerTask.cancel();
		mTime.cancel();

		if (playList != null) {
			playList.clear();
			playList = null;
		}

		if (holder != null) {
			holder = null;
		}

		// mediaLooperThread.exitLooperThrad();
		dismissWaittingDialog();

		if (mBookMark != null) {
			mBookMark.close();
		}

		if (mMediaHanler != null) {
			Utils.removeHandlerMsg(mMediaHanler);
		}
		Utils.printLog(TAG, "onDestroy end");
		// Utils.killMyProcess(this);
	}

	private void doBookMarkAction() {
		Utils.printLog(TAG, "doBookMarkAction  ============ pos ="
				+ mTheLastPosition);
		if (mTheLastPath == null
				|| (mTheLastPosition == VideoPlayerContrlConsts.GET_CURRENTPOSITION_ERROR))
			return;
		if (mTheLastPosition < 0) {
			if (mBookMark.isUrlInBookmark(mTheLastPath)) {
				mBookMark.delete(mTheLastPath);
				// CustomToast.showTCLCustomToast(showMovieDetail.this, name +
				// " 从书签删除", CustomToast.LENGTH_SHORT,
				// CustomToast.WARNNING_ICON);
				Log.v(TAG, "bookmark delete " + mTheLastPath);
			}
		} else {
			if (mBookMark.isUrlInBookmark(mTheLastPath)) {

				mBookMark.delete(mTheLastPath);
				// CustomToast.showTCLCustomToast(showMovieDetail.this, name +
				// " 更新书签成功", CustomToast.LENGTH_SHORT,
				// CustomToast.WARNNING_ICON);
				Log.v(TAG, "bookmark delete " + mTheLastPath + " at "
						+ mTheLastPosition);
			}
			mBookMark.refreshListBeforInsert();
			mBookMark.insert(mTheLastName, mTheLastPath, mTheLastPosition,
					sEndTime);
			Utils.printLog(TAG, "mTheLastPosition =" + mTheLastPosition
					+ "sEndTime" + sEndTime);
			// CustomToast.showTCLCustomToast(showMovieDetail.this, name +
			// " 成功加入书签！", CustomToast.LENGTH_SHORT,
			// CustomToast.WARNNING_ICON);
			Log.v(TAG, "bookmark insert " + mTheLastPath + " at "
					+ mTheLastPosition);

		}
	}

	@Override
	public void onBackPressed() {
		Utils.printLog(TAG, "Back Pressed!");
//		if (volumLay.getVisibility() == View.VISIBLE) {
//			mVideoPlayerHander.sendEmptyMessage(DISSMISS_VOLUME);
//			if (mClickedBtStatus == VideoPlayerUIConst.CLICKED_VOLUME) {
//				// mIsVolumeClick = false;
//				mVideoPlayerHander
//						.sendEmptyMessage(DISPLAY_PLAYER_CONTROL_BUTTONS);
//			}
//		} else if (mPlayerControlLayout.getVisibility() == View.VISIBLE || mPlayerProgressLayout.getVisibility() == View.VISIBLE ) {
//			mVideoPlayerHander.sendEmptyMessage(DISMISS_PLAYER_CONTROL_BUTTONS);
//		} else {

			exitVideoPlay();
			// mPlayerProgressLayout.setVisibility(View.INVISIBLE);
//		}
		// if (mPlayerControlLayout.getVisibility() == View.INVISIBLE) {
		// Utils.printLog(TAG, " onBackPressed");
		// finish();
		// } else {

		// mPlayerControlLayout.setVisibility(View.INVISIBLE);
		// mPlayerProgressLayout.setVisibility(View.INVISIBLE);
		// }
	}

	private void exitVideoPlay() {
	    

//		if (mVideoContrl!=null) {
//			mTheLastPosition = mVideoContrl.getCurrentPosition();
//			Utils.printLog(TAG, "mTheLastPosition " + mTheLastPosition
//					+ "  sEndTime =" + sEndTime);
//		} else {
//			mTheLastPosition = VideoPlayerContrlConsts.GET_CURRENTPOSITION_ERROR;
//		}
		Utils.printLog(TAG, "exitVideoPlay");
	

		if (mVideoContrl != null) {
			mVideoContrl.closeMediaControl();
		}



//		if (mTheLastPath != null) {
//			doBookMarkAction();
//		}
		exit_3Dmode();
		notifyPlayerActivityCallBack();
	//	saveVideoPlayType();
	//	sendActivityResu();
		dismissPopWindow();
		
		VideoPlayerActivity.this.finish();

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		Utils.printLog(TAG, "onTouchEvent");

		if (event.getAction() == MotionEvent.ACTION_DOWN
				&& mVideoContrl.isMediaPlayerPrepared()) {
			float clickPosition = event.getY();
			Utils.printLog(TAG, "onTouchEvent Y =" + clickPosition);
			int mClickBtnPos = 0;
			if (Utils.isWindow1080(VideoPlayerActivity.this)) {
				mClickBtnPos = 860;
			} else {
				mClickBtnPos = 560;
			}
			if (clickPosition >= mClickBtnPos
					&& mPlayerControlLayout.getVisibility() == View.INVISIBLE) {
				mVideoPlayerHander
						.sendEmptyMessage(DISPLAY_PLAYER_CONTROL_BUTTONS);
			} else {
				mVideoPlayerHander.sendEmptyMessage(VIDEO_PLAY_START_OR_PAUSE);
			}

		}

		return super.onTouchEvent(event);
	}

	private void findView() {

		ButtonClickListener buttonClickListener = new ButtonClickListener();
		ButtonOnfocusListener buttonOnfocusListener = new ButtonOnfocusListener();
		mPlayerNext = (FrameLayout) findViewById(R.id.player_next);
		mPlayerNext.setOnClickListener(buttonClickListener);
		mPlayerNext.setOnFocusChangeListener(buttonOnfocusListener);
	
		
		mPlayerSetImageButton = (FrameLayout) findViewById(R.id.player_setimage);
		mPlayerSetImageButton.setOnClickListener(buttonClickListener);
		mPlayerSetImageButton.setOnFocusChangeListener(buttonOnfocusListener);
		
		mPlayerAdvanceButton = (FrameLayout) findViewById(R.id.player_advance);
		mPlayerAdvanceButton.setOnClickListener(buttonClickListener);
		mPlayerAdvanceButton.setOnFocusChangeListener(buttonOnfocusListener);

	
		mPlayerSequenceButton = (FrameLayout) findViewById(R.id.player_sequence);
		mPlayerSequenceButton.setOnClickListener(buttonClickListener);
		mPlayerSequenceButton.setOnFocusChangeListener(buttonOnfocusListener);
		mPlayerSaveButton = (ImageButton) findViewById(R.id.player_save);
		mPlayerSaveButton.setOnClickListener(buttonClickListener);
		mPlayerSaveButton.setOnFocusChangeListener(buttonOnfocusListener);
		mPlayerStartButton = (FrameLayout) findViewById(R.id.player_start);
		mPlayerStartButton.setOnClickListener(buttonClickListener);
		mPlayerStartButton.setFocusableInTouchMode(true);
		mPlayerStartButton.requestFocus();
		mPlayerStartButton.setOnFocusChangeListener(buttonOnfocusListener);
		mPlayerPrevious = (FrameLayout) findViewById(R.id.player_previous);
		mPlayerPrevious.setOnClickListener(buttonClickListener);
		mPlayerPrevious.setOnFocusChangeListener(buttonOnfocusListener);
		mPlayerVolumeButton = (ImageButton) findViewById(R.id.player_volume);
		mPlayerVolumeButton.setOnClickListener(buttonClickListener);
		mPlayerVolumeButton.setOnFocusChangeListener(buttonOnfocusListener);
		mPlayerTurn3DButton = (FrameLayout) findViewById(R.id.player_turn3d);
		mPlayerTurn3DButton.setOnClickListener(buttonClickListener);
		
		mPlayerInfo = (FrameLayout)findViewById(R.id.player_info);
		mPlayerInfo.setOnClickListener(buttonClickListener);
		mPlayerInfo.setOnFocusChangeListener(buttonOnfocusListener);
		mPlayerTurn3DButton.setOnFocusChangeListener(buttonOnfocusListener);
		if (is3DEnabled) {
			mPlayerTurn3DButton.setVisibility(View.VISIBLE);
		}
		mPauseIcon = (LinearLayout) findViewById(R.id.player_pause_icon_video);
		mPauseTimeTextView = (TextView) findViewById(R.id.player_pause_time);
		mPlayerControlLayout = findViewById(R.id.player_control_layout);
		mPlayerProgressLayout = findViewById(R.id.player_progress_layout);
	//	mPlayerControlLayout.setVisibility(View.GONE);
		mPlayerProgressLayout.setVisibility(View.INVISIBLE);
		mPlayerNameLiner = (LinearLayout) findViewById(R.id.player_video_name_liner);
		mPlayerNameLiner.setVisibility(View.INVISIBLE);
		mName = (TextView) findViewById(R.id.player_video_name);
		mName.setSelected(true);
		mPlayEndTime = (TextView) findViewById(R.id.player_end_time);
		mPlayBeginTime = (TextView) findViewById(R.id.player_begin_time);
		mSubtitleTextView = (TextView) findViewById(R.id.video_subtitle_text);
		mDDIcon = (ImageView)findViewById(R.id.ddpicon_show);
		initVolumeInfo();
		mPlayerSeekar = (TCLSeekBar)findViewById(R.id.player_seekbar);
		mPlayerSeekar.setOnSeekBarChangeListener(mSeekBarListener);
		//mPlayerSeekar.setKeyProgressIncrement(Utils.SeekBarLength/20);
		
		mPlayerStartText = (TextView)findViewById(R.id.player_start_text);
		mPlayerPreviousText = (TextView)findViewById(R.id.player_previous_text);
		mPlayerNextText= (TextView)findViewById(R.id.player_next_text);
		mPlayerSetImageText= (TextView)findViewById(R.id.player_setimage_text);
		mPlayerAdvanceText= (TextView)findViewById(R.id.player_advance_text);
		mPlayerTurn3DText= (TextView)findViewById(R.id.player_turn3d_text);
		mPlayerInfoText= (TextView)findViewById(R.id.player_info_text);
		mPlayerSquenceText =(TextView)findViewById(R.id.player_sequence_text);
		
		
		if(isFromWeb){
			mPlayerPrevious.setVisibility(View.GONE);
		    mPlayerNext.setVisibility(View.GONE);
			mPlayerInfo.setVisibility(View.GONE);
			mPlayerAdvanceButton.setVisibility(View.GONE);
			mPlayerSequenceButton.setVisibility(View.GONE);
		}
		
	}
	
	private OnSeekBarChangeListener mSeekBarListener = new OnSeekBarChangeListener() {
		
		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
			// TODO Auto-generated method stub
			Utils.printLog(TAG, "SeekBarListener onStopTrackingTouch ");
		}
		
		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
			// TODO Auto-generated method stub
			Utils.printLog(TAG, "SeekBarListener onStartTrackingTouch ");
			
		}
		
		@Override
		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser) {
			// TODO Auto-generated method stub
			Utils.printLog(TAG, "SeekBarListener onProgressChanged progress ="+progress+"   fromUser ="+fromUser);
			if(fromUser ){
				
				if(sEndTime>0&&ispreparePlayed){
					final long time = progress *(long)sEndTime /Utils.SeekBarLength;
					Utils.printLog(TAG, "SeekBarListener mseekbariwidth="+mPlayerSeekar.getWidth()+" onProgressChanged sEndTime ="+sEndTime+"   time ="+time);
					new Thread(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub
							mVideoContrl.seekTo((int) time);
							mVideoPlayerHander.sendEmptyMessage(DISMISS_PLAYER_PAUSE_ICON);

						}
					}).start();
					long xoff = (mPlayerSeekar.getWidth()-Utils.SeekBarThumbLength)*(long)progress/Utils.SeekBarLength;
					seekBarPopWindow.showText(mPlayerSeekar, Utils.getTimeShort((int) time),(int)xoff,-12);
				}else{
					mPlayerSeekar.setProgress(0);
				}

				
			}
		}
	};

	private void dealLeftRightButon(boolean isRight) {
		View mCurrView = mPlayerControlLayout.findFocus();
		if (mCurrView != null && isRight) {
			View mNextView = mCurrView.focusSearch(View.FOCUS_RIGHT);
			if (mNextView != null) {
				mNextView.requestFocus();
			} else {
				mPlayerStartButton.requestFocus();
			}
		} else if (mCurrView != null && !isRight) {
			View mNextView = mCurrView.focusSearch(View.FOCUS_LEFT);
			if (mNextView != null) {
				mNextView.requestFocus();
			} else {
				mPlayerSetImageButton.requestFocus();
			}
		}

	}

	private TextView volumMinNum;
	private ProgressBar volumBar;
	private LinearLayout volumLay;
	private int curVolum = 0;
	private VolumeController volumeContrl;

	private void initVolumeInfo() {
		volumeContrl = new VolumeController(VideoPlayerActivity.this);

		volumBar = (ProgressBar) findViewById(R.id.volumProgress_video);

		volumMinNum = (TextView) findViewById(R.id.volumMinNum_video);
		volumLay = (LinearLayout) findViewById(R.id.volumInfor_video);
		volumLay.setVisibility(View.INVISIBLE);

		refreshCurrentVolume();

	}

	private void refreshCurrentVolume() {
		int vol = volumeContrl.getCurVolum();
		Utils.printLog(TAG, "volumeContrl.getCurVolum()=" + vol);
		int max = volumeContrl.getMaxVolume();
		curVolum = (vol * 100 / max);
		volumMinNum.setText(String.valueOf(curVolum));
		volumBar.setProgress(curVolum);
	}

	private void playerUIInit() {
		Utils.printLog(TAG, "playerUIInit start");
		mPauseIcon.setVisibility(View.INVISIBLE);
//		if (mList == null || mList.size() <= 0 || mCurrIndex >= mList.size()) {
//			return;
//		}
		// mVideoPlayerHander.sendEmptyMessage(SHOW_WAIT_DIALOG);
	//	showWatingDialog();
		// mVideoPlayerHander.removeMessages(DISMISS_DIALOG_FOR_TIMEOUT);//
		// 保证对应之前视频的超时消息被移除；
		// mVideoPlayerHander.sendEmptyMessageDelayed(DISMISS_DIALOG_FOR_TIMEOUT,
		// VideoPlayerUIConst.INTENET_TIMEOUT);// 重新发送超时消息；
		// Message mss = mVideoPlayerHander.obtainMessage();
		// mss.getData().putInt("currentPos", 0);
		// mss.what = REFRESH_CURRENT_TIME;
		// mVideoPlayerHander.sendMessage(mss);
		if(seekBarPopWindow!=null){
			seekBarPopWindow.dismiss();
		}

		if (playList != null && playList.size() > 0 && mCurrIndex < playList.size()) {
			file = playList.get(mCurrIndex);
			Utils.printLog(TAG, file);
		    mMeidaDetail = mDtvPvrPlayerManager.getFileDetailInfo(file);
			long duration = mDtvPvrPlayerManager.getPlayFileDuration(file);
			sEndTime = (int)duration;
			mName.setText(mMeidaDetail.name);
			mPlayBeginTime.setText(Utils.getTimeShort(0));
			mPlayEndTime.setText(Utils.getTimeShort(sEndTime));
			
			 mPlayerSeekar.setProgress(0);
		   //  mPlayerSeekar.setEnabled(false);
//			if (mVideoContrl.isMediaPlayerPrepared()) {
//				sEndTime = mVideoContrl.getDuration();
//				Utils.printLog(
//						TAG,
//						"-sEndTime=" + sEndTime + ";timesort ="
//								+ Utils.getTimeShort(sEndTime));
//				if (sEndTime > 0) {
//					Utils.printLog(TAG,
//							"get mPlayEndTime =" + Utils.getTimeShort(sEndTime));
//					mPlayEndTime.setText(Utils.getTimeShort(sEndTime));
//				}
//			}
            int day = mMeidaDetail.day;
            int month = mMeidaDetail.month;
            int year = mMeidaDetail.year;
            mVideoDate = day+"-"+month+"-"+year;
			mTheLastName = mMeidaDetail.name;
			mTheLastPath = mMeidaDetail.path;
	    }
	

		// mVideoPlayerHander.sendEmptyMessage(DISPLAY_PLAYER_PROGRESS_LAYOUT);

		// exit3Dmode();
		Utils.printLog(TAG, "playerUIInit end");
	}

	private void dismissWaittingDialog() {
		Utils.printLog(TAG, "dismissWaittingDialog");
		if (mDialog != null) {
			try {
				Utils.printLog(TAG, "dismissWaittingDialog  true");
				mDialog.dismiss();
				mDialog = null;
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	private void showWatingDialog() {
		Utils.printLog(TAG, "showWaitDialog");
		if (mDialog == null) {
			mDialog = new WaitingDialog(VideoPlayerActivity.this);
			mDialog.registerCallback(new WaitDialogCallBackInterface() {

				@Override
				public void onDialogMenuPressed() {
					// TODO Auto-generated method stub

				}

				@Override
				public void onDialogBackPressed() {
					// TODO Auto-generated method stub
					exitVideoPlay();
					// mVideoPlayerHander.sendEmptyMessage(EXIT_VIDEOPLAY);
				}
			});
		}
		try {
			Utils.printLog(TAG, "showWaitDialog true");
			mDialog.show();
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}

	/*
	 * 刷新播放进度以及播放总长度；
	 */
	private Runnable myRunnable = new Runnable() {
		public void run() {
			int currentPostion = 0;
			int duration = 0;
			if(mVideoContrl==null){
				return;
			}
			Utils.printLog(TAG, "refresh currentPostion ");
			currentPostion = mVideoContrl.getCurrentPosition();
			mTheLastPosition = currentPostion;
			Utils.printLog(TAG, "currentPostion =" + currentPostion);
			if (sEndTime <= 0) {
				duration = mVideoContrl.getDuration();
				sEndTime = duration;
				mVideoPlayerHander.sendEmptyMessage(REFRESH_TOTAL_TIME);
			}
		//	duration = mVideoContrl.getDuration();
			Utils.printLog(TAG, "sEndTime=" + sEndTime);
			Utils.printLog(TAG, "duration=" + duration);
			if (currentPostion != VideoPlayerContrlConsts.GET_CURRENTPOSITION_ERROR) {
				Message mss = mVideoPlayerHander.obtainMessage();
				mss.getData().putInt("currentPos", currentPostion);
				mss.what = REFRESH_CURRENT_TIME;
				mVideoPlayerHander.sendMessage(mss);
				
			}
			if(sEndTime > 0){
				mPlayerSeekar.setKeyProgressIncrement(Utils.SeekBarLength*30/sEndTime);
			}else{
				mPlayerSeekar.setKeyProgressIncrement(0);
			}

		}
	};

	private TimerTask mTimerTask = new TimerTask() {
		public void run() {
			if (mPlayerProgressLayout.getVisibility() == View.VISIBLE) {

				runOnUiThread(myRunnable);
				// new Thread(myRunnable).start();
			}
		}
	};

//	private class ButtonOnfocusListener implements View.OnFocusChangeListener {
//
//		@Override
//		public void onFocusChange(View v, boolean hasFocus) {
//			// TODO Auto-generated method stub
//			if (mPlayerControlLayout == null
//					|| mPlayerControlLayout.getVisibility() == View.INVISIBLE) {
//				return;
//			}
//			if (hasFocus) {
//				int stringId = 0;
//				switch (v.getId()) {
//				case R.id.player_turn3d:
//					stringId = R.string.turnThree;
//					break;
//				case R.id.player_volume:
//					stringId = R.string.volumChange;
//					break;
//				case R.id.player_stop:
//					stringId = R.string.Video_Info_return;
//					break;
//
//				case R.id.player_save:
//					if (mList != null && mList.size() > 0) {
//						if (mList.get(mCurrIndex).mIsSaved == 1) {
//							stringId = R.string.Video_Info_Cancel_favorite;
//						} else {
//							stringId = R.string.Video_Info_Favorite;
//						}
//					} else
//						stringId = R.string.Video_Info_Favorite;
//
//					break;
//
//				case R.id.player_start:
//					if (mVideoContrl != null
//							&& mVideoContrl.isMediaPlayerPrepared()
//							& mVideoContrl.isPlaying()) {
//						stringId = R.string.Video_Info_Pause;
//					} else {
//						stringId = R.string.Video_Info_Play;
//					}
//
//					break;
//				case R.id.player_next:
//
//					stringId = R.string.Video_Info_Next_Film;
//
//					break;
//				case R.id.player_previous:
//
//					stringId = R.string.Video_Info_Last_Film;
//
//					break;
//
//				case R.id.player_left_fast:
//					stringId = R.string.Video_Info_Rewind_30S;
//
//					break;
//
//				case R.id.player_right_fast:
//					stringId = R.string.Video_Info_FastForward_30S;
//					break;
//
//				case R.id.player_sequence:
//					stringId = mPlayerTypeText[mVideoContrl.getPlayType()];
//					break;
//				case R.id.player_advance:
//					stringId = R.string.AdvancedSetTip;
//					break;
//				case R.id.player_setimage:
//					stringId = R.string.image;
//					break;
//				}
//
//				if (stringId != 0 && popWindow != null)
//					showPopWindow(v, stringId);
//			} else {
//				dismissPopWindow();
//			}
//		}
//
//	}

	private void showPopWindow(View view, int string) {
		// popWindow.dismiss();
		if (mPlayerControlLayout.getVisibility() == View.VISIBLE)
			try {
				popWindow.showText(view, string);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
	}
	
	
	private void dismissShowText(){
		  mPlayerStartText.setVisibility(View.INVISIBLE);
		  mPlayerPreviousText.setVisibility(View.INVISIBLE);
		  mPlayerNextText.setVisibility(View.INVISIBLE);
		  mPlayerSetImageText.setVisibility(View.INVISIBLE);
		  mPlayerAdvanceText.setVisibility(View.INVISIBLE);
		  mPlayerTurn3DText.setVisibility(View.INVISIBLE);
		  mPlayerInfoText.setVisibility(View.INVISIBLE);
		  mPlayerSquenceText.setVisibility(View.INVISIBLE);
	}

	private void dismissPopWindow() {
		if (popWindow != null) {
			try {
				popWindow.dismiss();
			} catch (Exception e) {
				e.printStackTrace();// TODO: handle exception
			}

		}
	}

	private void turn3DMenu() {

//	boolean is4kMode = false;
//	TTvPictureManager  tvPicManager = TTvPictureManager.getInstance(this);
//	is4kMode = tvPicManager.is4k2kMode();
//	if(!is4kMode){
		Intent intent = new Intent();
		intent.setAction("android.intent.action.tcl.show3dmenu");
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.putExtra("start3DType", 1);
		try {
			VideoPlayerActivity.this.startActivity(intent);
			isNeedPause = false;
		} catch (ActivityNotFoundException acti) {
			acti.printStackTrace();
			new MyToast(VideoPlayerActivity.this, VideoPlayerActivity.this
					.getResources().getString(R.string.NoFuntion)).show();
		}
//	}else{
//		 new MyToast(VideoPlayerActivity.this, VideoPlayerActivity.this
//					.getResources().getString(R.string.videonothreeD)).show();
//	}
	
		
		
	

	}
	
	private class ButtonOnfocusListener implements View.OnFocusChangeListener {

		@Override
		public void onFocusChange(View v, boolean hasFocus) {
			// TODO Auto-generated method stub
			if (mPlayerControlLayout == null
					|| mPlayerControlLayout.getVisibility() == View.INVISIBLE) {
				return;
			}
			if (hasFocus) {
				dismissShowText();
				switch (v.getId()) {
				case R.id.player_turn3d:
					  mPlayerTurn3DText.setVisibility(View.VISIBLE);
					
					break;
				case R.id.player_volume:
					
					break;
	
				case R.id.player_start:
					if (mVideoContrl != null
							&& mVideoContrl.isMediaPlayerPrepared()
							& mVideoContrl.isPlaying()) {
						 mPlayerStartText.setVisibility(View.VISIBLE);
						 mPlayerStartText.setText(R.string.Video_Info_Pause);
					} else {
						 mPlayerStartText.setVisibility(View.VISIBLE);
						 mPlayerStartText.setText(R.string.Video_Info_Play);
					}

					break;
				case R.id.player_next:

					  mPlayerNextText.setVisibility(View.VISIBLE);

					break;
				case R.id.player_sequence:

					  mPlayerSquenceText.setVisibility(View.VISIBLE);

					break;
				case R.id.player_previous:
					  mPlayerPreviousText.setVisibility(View.VISIBLE);
				
					break;
				case R.id.player_advance:
					  mPlayerAdvanceText.setVisibility(View.VISIBLE);
					break;
				case R.id.player_setimage:
					 mPlayerSetImageText.setVisibility(View.VISIBLE);
					break;
				case R.id.player_info:
					 mPlayerInfoText.setVisibility(View.VISIBLE);
					 break;	
				}

		
			} else {
				dismissShowText();
			}
		}

	}
	

	private class ButtonClickListener implements View.OnClickListener {

		public void onClick(View v) {
			if (mVideoContrl == null) {
				return;
			}
			switch (v.getId()) {

			case R.id.player_turn3d:
				m3DBtStatus = VideoPlayerUIConst.SHOW_MENU_3D;
				mClickedBtStatus = VideoPlayerUIConst.CLICKED_3D;
				mVideoPlayerHander
						.sendEmptyMessage(DISMISS_PLAYER_CONTROL_BUTTONS);
				turn3DMenu();

				break;
			case R.id.player_volume:
				mVideoPlayerHander
						.sendEmptyMessage(DISMISS_PLAYER_CONTROL_BUTTONS);
				mVideoPlayerHander.sendEmptyMessage(DISPLAY_VOLUME);
				mClickedBtStatus = VideoPlayerUIConst.CLICKED_VOLUME;
				break;
//			case R.id.player_stop:
//				Utils.printLog(TAG, "Stop Pressed!");
//				mVideoPlayerHander.sendEmptyMessage(EXIT_VIDEOPLAY);
//				break;

			case R.id.player_save:

				boolean res = doSave();
				if (res) {
					showPopWindow(v, R.string.Video_Info_Favorite);
				} else {
					showPopWindow(v, R.string.Video_Info_Cancel_favorite);
				}
				mClickedBtStatus = VideoPlayerUIConst.CLICKED_SAVE;
				break;

			case R.id.player_start:
				if (mVideoContrl.isMediaPlayerPrepared() == false) {
					return;
				}
				mVideoPlayerHander.sendEmptyMessage(VIDEO_PLAY_START_OR_PAUSE);
				Bitmap test =  getframeBitmap(mTheLastPath,200,200);
				mDDIcon.setImageBitmap(test);
				mDDIcon.setVisibility(View.VISIBLE);
				break;
				
			  
				
			case R.id.player_next:
				if (mVideoContrl != null
						&& mVideoContrl.isMediaPlayerPrepared()) {
					mMediaHanler.sendEmptyMessage(NEXT_VIDEO);
				}

				break;
			case R.id.player_previous:

				if (mVideoContrl != null
						&& mVideoContrl.isMediaPlayerPrepared()) {
					mMediaHanler.sendEmptyMessage(PRE_VIDEO);
				}
				// mVideoContrl.playPrevious();

				break;

//			case R.id.player_left_fast: {
//				if (mVideoContrl != null
//						&& mVideoContrl.isMediaPlayerPrepared()) {
//					mPlayerSeekar.onKeyDown(KeyEvent.KEYCODE_DPAD_LEFT, null);
//					//mMediaHanler.sendEmptyMessage(FAST_BACK);
//				}
//
//				break;
//			}

//			case R.id.player_right_fast: {
//				if (mVideoContrl != null
//						&& mVideoContrl.isMediaPlayerPrepared()) {
//					mPlayerSeekar.onKeyDown(KeyEvent.KEYCODE_DPAD_RIGHT, null);
//					//mMediaHanler.sendEmptyMessage(FAST_NEXT);
//				}
//
//				// maltiSpeedPlayNext();
//				break;
//			}

			case R.id.player_sequence: {
				int sCurrentPlayerType = (mVideoContrl.getPlayType() + 1)
						% mPlayerTypeIcon.length;
				mPlayerSequenceButton
						.setBackgroundResource(mPlayerTypeIcon[sCurrentPlayerType]);
				mPlayerSquenceText.setText(mPlayerTypeText[sCurrentPlayerType]);
				mVideoContrl.setPlayType(sCurrentPlayerType);
				updateControlButtonStatus();

				break;
			}
			case R.id.player_setimage: {
				mClickedBtStatus = VideoPlayerUIConst.CLICKED_IMAGE;
				mVideoPlayerHander.sendEmptyMessage(DISMISS_PLAYER_CONTROL_BUTTONS);
				//Intent intent_advance = new Intent();
			    //intent_advance.setAction("android.intent.action.PICTURE");   
			    //startActivity(intent_advance);
				isNeedPause = false;
			    String mVideosize = "";
		        mVideosize = getFileByte(mTheLastPath);
			    Intent intent_advance = new Intent();
			    intent_advance.setAction("android.tcl.videosetting.show");  
			    intent_advance.putExtra("video_name", mTheLastName);
			    intent_advance.putExtra("video_size", mVideosize);
			    startActivity(intent_advance);
	

				break;
			}
	         case R.id.player_info: {	
	     
	        String mVideosize = "";
	        mVideosize = getFileByte(mTheLastPath);
	 	    mClickedBtStatus = VideoPlayerUIConst.CLICKED_VIDEOINFO;
	 		mVideoPlayerHander.sendEmptyMessage(DISMISS_PLAYER_CONTROL_BUTTONS);
	 		
	 		Intent intent_videoinfo = new Intent("android.tcl.videoinfo.show");
	 		  intent_videoinfo.putExtra("video_name", mTheLastName);
	 		  intent_videoinfo.putExtra("video_size", mVideosize);
	 		startActivity(intent_videoinfo);     
	 		isNeedPause = false;
	 		break;
	 			}
			case R.id.player_advance:
				mClickedBtStatus = VideoPlayerUIConst.CLICKED_ADVANCE;
				mVideoPlayerHander.sendEmptyMessage(DISMISS_PLAYER_CONTROL_BUTTONS);
				// advanceDialog = new
				// AdvanceSetDialog(VideoPlayerActivity.this);
				// advanceDialog.show();
				Intent intent_advance = new Intent("android.tcl.sub");
				// new MyToast(VideoPlayerActivity.this,
				// VideoPlayerActivity.this
				// .getResources().getString(R.string.NoFuntion)).show();
				startActivity(intent_advance);
				isNeedPause = false;
				break;

			}

		}
	}
	
	private String getFileByte(String path) {
		long fileS = 0;
		String fileSizeString = "";
		File f = new File(path);
		try {
			FileInputStream fis = new FileInputStream(f);
			fileS = fis.available();
			Utils.printLog(TAG, ""+fileS);
			DecimalFormat df = new DecimalFormat("#.00");
			if (fileS < 1024) {
				fileSizeString = df.format((double) fileS) + "B";
			} else if (fileS < 1048576) {
				fileSizeString = df.format((double) fileS / 1024) + "K";
			} else if (fileS < 1073741824) {
				fileSizeString = df.format((double) fileS / 1048576) + "M";
			} else {
				fileSizeString = df.format((double) fileS / 1073741824) + "G";
			}

		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return fileSizeString;

	}

	private boolean doSave() {

//		Intent intent = new Intent(VideoPlayerUIConst.COLLECTION_OPERATE);
		boolean res = true;
//		if (mList != null && mList.size() > 0) {
//			if (mList.get(mCurrIndex).mIsSaved == 0) {
//				mIsSaveClicked = true;
//				intent.putExtra(VideoPlayerUIConst.ADD_OR_DEL,
//						VideoPlayerUIConst.ADD_COLLECTION);
//				res = true;
//			} else {
//				intent.putExtra(VideoPlayerUIConst.ADD_OR_DEL,
//						VideoPlayerUIConst.DEL_COLLECTION);
//				res = false;
//			}
//
//			intent.putExtra(VideoPlayerUIConst.COLLECTION_ADDRESS,
//					mList.get(mCurrIndex).mPath);
//			intent.putExtra(VideoPlayerUIConst.COLLECTION_APP_MODE,
//					mList.get(mCurrIndex).mVodType);
//			Utils.printLog(
//					TAG,
//					"Collection Url ="
//							+ intent.getStringExtra(VideoPlayerUIConst.COLLECTION_ADDRESS)
//							+ "  appmode =" + mList.get(mCurrIndex).mVodType);
//			mPlayerSaveButton.setEnabled(false);
//		}
//		Log.d(TAG, "player_save  mIsSaveClicked =" + mIsSaveClicked);
//		sendBroadcast(intent);
		return res;
	}

	private void playPause() {
		Utils.printLog(TAG, "playPause");
		if (mVideoContrl != null ) {
			Utils.printLog(TAG, "playPause control into");
			mVideoContrl.pause();
			if (true) {
				mPauseIcon.setVisibility(View.VISIBLE);
				mPauseTimeTextView.setVisibility(View.VISIBLE);
				mPauseIcon.requestLayout();
				mPauseIcon.invalidate();
				Utils.printLog(TAG, "mPauseIcon.setVisibility(View.VISIBLE);");
				mPlayerStartButton
						.setBackgroundResource(R.drawable.player_start_selector);
				if (mPlayerControlLayout.getVisibility() == View.VISIBLE){
					 mPlayerStartText.setVisibility(View.VISIBLE);
					 mPlayerStartText.setText(R.string.Video_Info_Play);
					mPauseTimeTextView.setVisibility(View.INVISIBLE);
				}
			} else {
				mPauseIcon.setVisibility(View.INVISIBLE);
				mPlayerStartButton
						.setBackgroundResource(R.drawable.player_pause_selector);
				if (mPlayerControlLayout.getVisibility() == View.VISIBLE){
					 mPlayerStartText.setVisibility(View.VISIBLE);
					 mPlayerStartText.setText(R.string.Video_Info_Pause);
				}
			}

		}

	}

	private void playStart() {
		Utils.printLog(TAG, "playStart");
		if (mVideoContrl != null ) {
			Utils.printLog(TAG, "playStart control into");
			mVideoContrl.start();
			if (true) {
				mPauseIcon.setVisibility(View.INVISIBLE);
				mPlayerStartButton
						.setBackgroundResource(R.drawable.player_pause_selector);
				if (mPlayerControlLayout.getVisibility() == View.VISIBLE){
					 mPlayerStartText.setVisibility(View.VISIBLE);
					 mPlayerStartText.setText(R.string.Video_Info_Pause);
				}
				
			} else {
				mPauseIcon.setVisibility(View.VISIBLE);
				mPauseIcon.requestLayout();
				mPauseIcon.invalidate();
				Utils.printLog(TAG, "mPauseIcon.setVisibility(View.VISIBLE);");
				mPlayerStartButton
						.setBackgroundResource(R.drawable.player_start_selector);
				if (mPlayerControlLayout.getVisibility() == View.VISIBLE){
					 mPlayerStartText.setVisibility(View.VISIBLE);
					 mPlayerStartText.setText(R.string.Video_Info_Play);
				}
					
				int currentPos = mVideoContrl.getCurrentPosition();
				int duration = 0;
				if (mVideoContrl.isMediaPlayerPrepared()) {
					duration = mVideoContrl.getDuration();
				}
				if (currentPos != VideoPlayerContrlConsts.GET_CURRENTPOSITION_ERROR) {
					Utils.printLog(TAG, "pause and time =" + currentPos);
					mPauseTimeTextView.setText(Utils.getTimeShort(currentPos)
							+ "/" + Utils.getTimeShort(duration));
				} else {
					mPauseTimeTextView.setText("Error");
				}
			}
		}

	}

	private void maltiSpeedPlayNext() {
		if (mVideoContrl != null && mVideoContrl.isMediaPlayerPrepared()) {
			int currentPostion = mVideoContrl.getCurrentPosition();
			if (currentPostion != VideoPlayerContrlConsts.GET_CURRENTPOSITION_ERROR) {
				if ((currentPostion + BACK_OR_PREVIOUS_STEP_SIZE) <= sEndTime) {
					currentPostion = (currentPostion + BACK_OR_PREVIOUS_STEP_SIZE) > sEndTime ? (sEndTime - 1000)
							: (currentPostion + BACK_OR_PREVIOUS_STEP_SIZE);
					mVideoContrl.seekTo(currentPostion);
					if (Utils.DEBUG)
						System.out
								.println("=================================seek to "
										+ currentPostion
										+ "time ="
										+ Utils.getTimeShort(currentPostion)
										+ ";result="
										+ mVideoContrl.getCurrentPosition());
				} else {
					System.out
							.println("===============================no right to maltiSpeedPlayNext");
				}
			}

		}
	}

	private void maltiSpeedPlayPrevious() {
		if (mVideoContrl.isMediaPlayerPrepared()) {
			int currentPostion = mVideoContrl.getCurrentPosition();
			if (currentPostion != VideoPlayerContrlConsts.GET_CURRENTPOSITION_ERROR) {
				if (currentPostion >= BACK_OR_PREVIOUS_STEP_SIZE) {
					currentPostion = (currentPostion - BACK_OR_PREVIOUS_STEP_SIZE) < 0 ? 0
							: (currentPostion - BACK_OR_PREVIOUS_STEP_SIZE);

					mVideoContrl.seekTo(currentPostion);
					if (Utils.DEBUG)
						System.out
								.println("=================================seek to "
										+ currentPostion
										+ "time ="
										+ Utils.getTimeShort(currentPostion)
										+ ";result="
										+ mVideoContrl.getCurrentPosition());
				} else {
					System.out
							.println("===============================no right to maltiSpeedPlayPrevious");
				}
			}

		}
	}


	public static boolean isRight(KeyEvent event) {
		return ((event.getKeyCode() == 98) || (event.getKeyCode() == 99)
				|| (event.getKeyCode() == 100) || (event.getKeyCode() == 101));
	}

	public static boolean isLeft(KeyEvent event) {
		return ((event.getKeyCode() == 94) || (event.getKeyCode() == 95)
				|| (event.getKeyCode() == 96) || (event.getKeyCode() == 97));
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		Log.d(TAG, "============================ onKeyDown keyCode=" + keyCode);
		Log.d("liuwei03","on keydown codd=" + keyCode);
		isVolumeChangedKeyClicked = false;
		if (keyCode == KeyEvent.KEYCODE_MENU) {
			    Intent intent_advance = new Intent();
			    intent_advance.setAction("android.tcl.pvr.videosetting.show");  
			    intent_advance.putExtra("video_name", mTheLastName);
			    intent_advance.putExtra("video_date", mVideoDate);
			    intent_advance.putExtra("video_time", Utils.getTimeShort(sEndTime));
			    startActivity(intent_advance);
			    isNeedPause = false;
			    return true;
		} else if ((event.getKeyCode() == KeyEvent.KEYCODE_ENTER || event
				.getKeyCode() == KeyEvent.KEYCODE_DPAD_CENTER)) {
			Utils.printLog(TAG, "Video Enter Press!");
			mVideoPlayerHander.sendEmptyMessage(VIDEO_PLAY_START_OR_PAUSE);
			return true;
	

		} else if ((event.getKeyCode() == KeyEvent.KEYCODE_DPAD_RIGHT)) {

//			if (mAudioSkin != null && mAudioSkin.isConnectionOk()) {
//				mAudioSkin.volumeUp();
//			}
			Log.d("liuwei03","begin ");
			if(mPlayerProgressLayout.getVisibility()==View.INVISIBLE){
				Log.d("liuwei03","mPlayerProgressLayout.getVisibility()==View.INVISIBLE");
				if(mVideoContrl!=null&&mVideoContrl.isMediaPlayerPrepared()){
					int currentPostion = mVideoContrl.getCurrentPosition();
					if (currentPostion != VideoPlayerContrlConsts.GET_CURRENTPOSITION_ERROR) {
						Message mss = mVideoPlayerHander.obtainMessage();
						mss.getData().putInt("currentPos", currentPostion);
						mss.what = REFRESH_CURRENT_TIME;
						mVideoPlayerHander.sendMessage(mss);
						
					}
				}
				mVideoPlayerHander.sendEmptyMessage(DISPLAY_PLAYER_PROGRESS_LAYOUT);
			}else{
		        if(mPlayerSeekar.hasFocus()){
		        	Log.d("liuwei03","else 1");
					mPlayerSeekar.onKeyDown(KeyEvent.KEYCODE_DPAD_RIGHT, null);
		        }else{
		        	Log.d("liuwei03","else 2");
		        	mPlayerSeekar.requestFocus();
		        }
			}

			Log.d("liuwei03","return ");
			return true;
		} else if ((event.getKeyCode() == KeyEvent.KEYCODE_DPAD_LEFT) ) {

//			if (mAudioSkin != null && mAudioSkin.isConnectionOk()) {
//				mAudioSkin.volumeDown();
//			}
			if(mPlayerProgressLayout.getVisibility()==View.INVISIBLE){
			   if(mVideoContrl!=null&&mVideoContrl.isMediaPlayerPrepared()){
				int currentPostion = mVideoContrl.getCurrentPosition();
				if (currentPostion != VideoPlayerContrlConsts.GET_CURRENTPOSITION_ERROR) {
					Message mss = mVideoPlayerHander.obtainMessage();
					mss.getData().putInt("currentPos", currentPostion);
					mss.what = REFRESH_CURRENT_TIME;
					mVideoPlayerHander.sendMessage(mss);
					
				}
			   }
			  mVideoPlayerHander.sendEmptyMessage(DISPLAY_PLAYER_PROGRESS_LAYOUT);
			}else{
		        if(mPlayerSeekar.hasFocus()){
					
					mPlayerSeekar.onKeyDown(KeyEvent.KEYCODE_DPAD_LEFT, null);
		        }else{
		        	mPlayerSeekar.requestFocus();
		        }
			}

			return true;
		}
		// else if (event.getKeyCode() == KeyEvent.KEYCODE_VOLUME_UP
		// && mPlayerControlLayout.getVisibility() == View.VISIBLE) {
		// Utils.printLog(TAG, "mindex=" + mCurrIndex);
		// mVideoPlayerHander.sendEmptyMessage(DISMISS_PLAYER_CONTROL_BUTTONS);
		// volumeChange(true);
		//
		// // playNext();
		// return true;
		// } else if (event.getKeyCode() == KeyEvent.KEYCODE_VOLUME_DOWN
		// && mPlayerControlLayout.getVisibility() == View.VISIBLE) {
		// mVideoPlayerHander.sendEmptyMessage(DISMISS_PLAYER_CONTROL_BUTTONS);
		// volumeChange(false);
		//
		// // playPrevious();
		// return true;
		// }
        else if (event.getKeyCode() == KeyEvent.KEYCODE_DPAD_UP
				&& volumLay.getVisibility() == View.INVISIBLE&&mPlayerControlLayout.getVisibility() != View.VISIBLE) {
			
//		    if (mVideoContrl != null) {
//				mMediaHanler.sendEmptyMessage(PRE_VIDEO);
//			}
			return true;
		} else if (event.getKeyCode() == KeyEvent.KEYCODE_DPAD_DOWN
				&& volumLay.getVisibility() == View.INVISIBLE) {
			if(mPlayerControlLayout.getVisibility() == View.VISIBLE){
				 if(mPlayerSeekar.hasFocus()){
					 mPlayerStartButton.requestFocus();
				 }
			}
//			else if (mVideoContrl != null ) {
//				  mMediaHanler.sendEmptyMessage(NEXT_VIDEO);
//			}
			return true;
		}else if (event.getKeyCode() == KeyEvent.KEYCODE_VOLUME_DOWN) {
			
			if(mPlayerControlLayout.getVisibility() == View.VISIBLE){
				mVideoPlayerHander.sendEmptyMessage(DISMISS_PLAYER_CONTROL_BUTTONS);
			}
		
		}else if (event.getKeyCode() == KeyEvent.KEYCODE_VOLUME_UP) {
			
			if(mPlayerControlLayout.getVisibility() == View.VISIBLE){
				mVideoPlayerHander.sendEmptyMessage(DISMISS_PLAYER_CONTROL_BUTTONS);
			}
		
		}

		return super.onKeyDown(keyCode, event);
		// return true;
	}
	
	
	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		  if ((event.getKeyCode() == 206) || (event.getKeyCode() == 803)) {// 3d键
				if (!is3DEnabled) {
					return true;
				}
				if (mPlayerControlLayout.getVisibility() == View.VISIBLE) {
					m3DBtStatus = VideoPlayerUIConst.SHOW_MENU_3D;
					mVideoPlayerHander
							.sendEmptyMessage(DISMISS_PLAYER_CONTROL_BUTTONS);
				} else {
					m3DBtStatus = VideoPlayerUIConst.NO_MENU_3D;
				}
				turn3DMenu();

			}else if (event.getKeyCode() == 4071  || event.getKeyCode() == 126) {
				if (mVideoContrl != null&&!mVideoContrl.isPlaying()) {
					mVideoPlayerHander.sendEmptyMessage(PLAYER_START);
				} else if (mVideoContrl != null&&mVideoContrl.isPlaying()) {
					mVideoPlayerHander.sendEmptyMessage(PLAYER_PAUSE);
				}
			
			}else if (event.getKeyCode() == 127) {
				if (mVideoContrl != null&&mVideoContrl.isPlaying()) {
					mVideoPlayerHander.sendEmptyMessage(PLAYER_PAUSE);
				} 
			}
			else if (event.getKeyCode() == /*KeyEvent.KEYCODE_TCL_NETFLIX_FB*/ 4075 || event.getKeyCode() == 89) {
				if(mPlayerProgressLayout.getVisibility()==View.INVISIBLE){
					mVideoPlayerHander.sendEmptyMessage(DISPLAY_PLAYER_PROGRESS_LAYOUT);
				}
				Log.d("liuwei03","onkeyup left");
				mPlayerSeekar.onKeyDown(KeyEvent.KEYCODE_DPAD_LEFT, null);
			} else if (event.getKeyCode() == /*KeyEvent.KEYCODE_TCL_NETFLIX_FF*/ 4073 || event.getKeyCode() == 90) {
				if(mPlayerProgressLayout.getVisibility()==View.INVISIBLE){
					mVideoPlayerHander.sendEmptyMessage(DISPLAY_PLAYER_PROGRESS_LAYOUT);
				}
				Log.d("liuwei03","onkeyup rigth");
				mPlayerSeekar.onKeyDown(KeyEvent.KEYCODE_DPAD_RIGHT, null);
			} else if (event.getKeyCode() == /*KeyEvent.KEYCODE_TCL_NETFLIX_STOP*/ 4074 ||  event.getKeyCode() == 86) {
				Log.d("liuwei03", "============================ exitVideoPlay() ");
				exitVideoPlay();
			} else if (event.getKeyCode() == /*KeyEvent.KEYCODE_TCL_NETFLIX_FORWARD*/ 4070 || event.getKeyCode() == 88) {
				if (mVideoContrl != null){
					mMediaHanler.sendEmptyMessage(PRE_VIDEO);
				}
			} else if (event.getKeyCode() == /*KeyEvent.KEYCODE_TCL_NETFLIX_NEXT*/ 4072 || event.getKeyCode() == 87) {
				if (mVideoContrl != null){
					mMediaHanler.sendEmptyMessage(NEXT_VIDEO);
				}
			} 
		return super.onKeyUp(keyCode, event);
	}

	private void volumeChange(boolean up) {
		mVideoPlayerHander.sendEmptyMessage(DISPLAY_VOLUME);
		if (volumeContrl != null) {
			isVolumeChangedKeyClicked = true;
			refreshCurrentVolume();
			if (up && curVolum < 100) {
				curVolum++;
				// volumeContrl.close_Slience();

			} else if (!up && curVolum >= 1) {
				curVolum--;
			}
			volumMinNum.setText(String.valueOf(curVolum));
			volumBar.setProgress(curVolum);
			int max = volumeContrl.getMaxVolume();
			volumeContrl.setVolum((curVolum * max) / 100);
		}

	}

	private void notifyPlayerActivityCallBack() {

		VideoPlayerActivity.this.sendBroadcast(new Intent(
				"com.tcl.mediaplayer.exit.subtitle"));

	}

	private VideoPlayControlCallback mCallback = new VideoPlayControlCallback() {

		@Override
		public void onVideoPlayBufferingUpdate(int percent) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onVideoPlayChanged(int index, int position) {
			// TODO Auto-generated method stub
			Utils.printLog(TAG, "onVideoPlayChanged with index =" + index
					+ " and pos= " + position);
			isShowContrlBtnFirst = true;
			mVideoPlayerHander.sendEmptyMessage(-99999);
			

//			if (!(position == VideoPlayerContrlConsts.GET_CURRENTPOSITION_ERROR)) {
//				mTheLastPosition = position;
//				mVideoPlayerHander.sendEmptyMessage(DO_BOOKMARK_ACTION);
//			}

			if (index == VideoPlayerContrlConsts.MEDIA_PALY_FINISHED) {

				mVideoPlayerHander.sendEmptyMessage(EXIT_VIDEOPLAY);
			} else {
				//userStateRecord();
				mCurrIndex = index;
				mIsSeeking = false;
				mVideoPlayerHander.sendEmptyMessage(PLAYER_UI_INIT);
				isBreakDialogDissmiss = false;
				notifyPlayerActivityCallBack();
			}

		}

		@Override
		public void onVideoPlayError(int errCode) {
			// TODO Auto-generated method stub

			Utils.printLog(TAG, "onVideoPlayError with ErrorCode =" + errCode);
			// mVideoPlayerHander.removeMessages(DISMISS_DIALOG_FOR_TIMEOUT);
			mMediaHanler.sendEmptyMessage(errCode);
			if (errCode == CommonConst.media_player_exception
					|| errCode == CommonConst.media_player_unknown_exception
					|| errCode == CommonConst.PLAY_DEVICE_UNMOUTED
					|| errCode == CommonConst.PLAY_LIST_NULL
					|| errCode == CommonConst.media_player_init_error
					|| errCode == CommonConst.DEVICE_SHUTDOWN
					|| errCode == CommonConst.media_player_unknown_exception_38) {

				mVideoPlayerHander.sendEmptyMessage(EXIT_VIDEOPLAY);

			}
		}

		@Override
		public void onVideoPlayInfoNotify(int infoCode) {
			// TODO Auto-generated method stub
			Utils.printLog(TAG, "onVideoPlayInfoNotify");
			if (infoCode == CommonConst.media_player_buffering) {
//				mVideoPlayerHander.sendEmptyMessage(SHOW_WAIT_DIALOG);
			} else if (infoCode == CommonConst.media_player_buffered) {
				mVideoPlayerHander.sendEmptyMessage(DISSMISS_WAIT_DIALOG);

			} else if (infoCode == CommonConst.media_player_subtitle_update) {
				String subtitle = mVideoContrl.getCurrentSubtitleText();
				Utils.printLog(TAG, "setsubtiteltext = " + subtitle);
				mSubtitleTextView.setText(subtitle);
			} else if (infoCode == CommonConst.media_player_subtitle_null) {
				mSubtitleTextView.setText("");

			} 
		
//			else if (infoCode == CommonConst.media_player_startplayer_firstframe) {
//				if (mVideoContrl != null
//						&& mVideoContrl.isMediaPlayerPrepared()) {
//					int dobby = mVideoContrl.isDobby(-1);
//					if(dobby == 1){
//						Utils.printLog(TAG, "Current audio is Dobby");
//						mVideoPlayerHander.sendEmptyMessage(REFRESH_DDIMAGE);
//					}else{
//						int dts = mVideoContrl.isDTS(-1);
//						if(dts == 1){
//							Utils.printLog(TAG, "Current audio is DTS");
//							mVideoPlayerHander.sendEmptyMessage(REFRESH_DTSIMAGE);
//						}else{
//							Utils.printLog(TAG, "Current audio is not Dobby and DTS");
//						}
//						
//					}
//					
//					
//					
//				}
				
			


		//	}
		else {
				if (infoCode == CommonConst.media_player_not_seekable) {
					mIsSeeking = false;
					mVideoPlayerHander.sendEmptyMessage(DISSMISS_WAIT_DIALOG);
				}
				mMediaHanler.sendEmptyMessage(infoCode);
			}

		}

		@Override
		public void onVideoPlayPrepared() {
			// TODO Auto-generated method stub
			// 播放准备好，则开始查询是否断点播放；
			// 移出网络超时消息
			// mVideoPlayerHander.removeMessages(DISMISS_DIALOG_FOR_TIMEOUT);

			Utils.printLog(TAG, "onVideoPlayPrepared");
			//setVideoBackLight();
			mVideoPlayerHander.sendEmptyMessage(-99999);
			start_3Dmode();

//			if (mSubtitleSurfaceView != null
//					&& mSubtitleSurfaceView.getHolder() != null) {
//				mVideoContrl
//						.setSubtiteSurface(mSubtitleSurfaceView.getHolder());
//			}
			mVideoPlayerHander.sendEmptyMessage(VIDEO_PLAY__CHECK_BOOKMARK);

			// exit3Dmode();

			// mVideoPlayerHander.sendEmptyMessage(DISMISS_PLAYER_PROGRESS_LAYOUT);

		}

		@Override
		public void onVideoPlayRemoveIndex(int index) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onVideoPlaySeekComplete(int currentPosition) {
			// TODO Auto-generated method stub
			Utils.printLog(TAG, "onVideoPlaySeekComplete");
			if(currentPosition == 0&&mVideoContrl!=null&&!mVideoContrl.isPlaying()){
				mVideoContrl.start();
			}
			mIsSeeking = false;
			mVideoPlayerHander.sendEmptyMessage(DISSMISS_WAIT_DIALOG);
			mVideoPlayerHander.sendEmptyMessage(REFRESH_PAUSEBUTON);
			//sendFirstShowContrlBtn();

		}

		@Override
		public void onVideoSizeChanged(int width, int height) {
			// TODO Auto-generated method stub
			Utils.printLog(TAG, "onVideoSizeChanged width =" + width
					+ "  height =" + height);
		}

		@Override
		public void onVideoSeekStart(int pos) {
			// TODO Auto-generated method stub
			Utils.printLog(TAG, "onVideoSeekStart");
			mIsSeeking = true;

			//mVideoPlayerHander.sendEmptyMessage(SHOW_WAIT_DIALOG);
		}

		@Override
		public void onSurfaceCreated() {
			// TODO Auto-generated method stub
			mMediaHanler.sendEmptyMessage(START_PLAY);
			Log.d(TAG, "onStart end");
		}

		@Override
		public void onSurfaceDertory() {
			// TODO Auto-generated method stub
			mVideoPlayerHander.sendEmptyMessage(EXIT_VIDEOPLAY);
			Log.d(TAG, "onSurfaceDertory end");
		}

		@Override
		public void onVideoPlayComplete() {
			// TODO Auto-generated method stub
			Utils.printLog(TAG, "onVideoPlayComplete");
			mMediaHanler.sendEmptyMessage(PvrNotify.NOTIFY_PVR_PLAYBACK_END);
		}

	};

	private void sendActivityResu() {
		if (mTheLastPath != null) {
			Utils.printLog(TAG, "start send reslut!");
			Intent backIntent = new Intent();
			backIntent.putExtra(CommonConst.ResultURL, mTheLastPath);
			setResult(CommonConst.ResultCode_FINISH, backIntent);// 返回Activity结果码
		}

	}

	private Handler mVideoPlayerHander = new Handler(Looper.getMainLooper()) {
		public void handleMessage(Message msg) {
			if (msg.what == DISPLAY_PLAYER_CONTROL_BUTTONS) {
				if (mPlayerControlLayout.getVisibility() == View.INVISIBLE) {
//					AudioManager audiomanager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
//					audiomanager.hideVolumePanel();
					mPlayerControlLayout.setVisibility(View.VISIBLE);
					
                updateControlButtonStatus();
                mVideoPlayerHander.sendEmptyMessageDelayed(DISMISS_PLAYER_CONTROL_BUTTONS, Utils.MENU_TIMEOUT);// ３秒后消失；
					if(mPauseIcon.getVisibility()==View.VISIBLE){					
						mPauseTimeTextView.setVisibility(View.INVISIBLE);
					}
					if (mClickedBtStatus == VideoPlayerUIConst.CLICKED_VOLUME) {
						mPlayerVolumeButton.requestFocus();

					} else if (mClickedBtStatus == VideoPlayerUIConst.CLICKED_3D) {
						mPlayerTurn3DButton.requestFocus();

					} else if (mClickedBtStatus == VideoPlayerUIConst.CLICKED_SAVE) {
						if (mPlayerSaveButton.isEnabled()) {
							mPlayerSaveButton.requestFocus();
						}
					} else if (mClickedBtStatus == VideoPlayerUIConst.CLICKED_ADVANCE) {
						mPlayerAdvanceButton.requestFocus();

					}else if (mClickedBtStatus == VideoPlayerUIConst.CLICKED_IMAGE) {
						mPlayerSetImageButton.requestFocus();

					}else if (mClickedBtStatus == VideoPlayerUIConst.CLICKED_VIDEOINFO) {
						mPlayerInfo.requestFocus();

					}
					else {
						mPlayerStartButton.requestFocus();
					}

					mClickedBtStatus = VideoPlayerUIConst.CLICKED_NO;

				}
				if (mPlayerProgressLayout.getVisibility() == View.INVISIBLE) {
					mPlayerProgressLayout.setVisibility(View.VISIBLE);
				}

				if (mPlayerProgressLayout.getVisibility() == View.VISIBLE
						&& mVideoContrl != null
						&& mVideoContrl.isMediaPlayerPrepared() && !mIsSeeking) {
					// new Thread(myRunnable).start();
					runOnUiThread(myRunnable);
				}
			} else if (msg.what == DISMISS_PLAYER_CONTROL_BUTTONS_FIRST) {
				if (isDismissControl == 1) {
					isDismissControl = 2;
					return;
				}

				if (mPlayerControlLayout.getVisibility() == View.VISIBLE) {

					mPlayerControlLayout.setVisibility(View.INVISIBLE);

				}
				if (mPlayerProgressLayout.getVisibility() == View.VISIBLE) {
					mPlayerProgressLayout.setVisibility(View.INVISIBLE);
				}
			} else if (msg.what == DISMISS_PLAYER_CONTROL_BUTTONS) {
				dismissPopWindow();
				if (mPlayerControlLayout.getVisibility() == View.VISIBLE) {
					mPlayerControlLayout.setVisibility(View.INVISIBLE);
				}
				if (mPlayerProgressLayout.getVisibility() == View.VISIBLE) {
					mPlayerProgressLayout.setVisibility(View.INVISIBLE);
				}
				if(mPauseIcon.getVisibility() == View.VISIBLE){
					mPauseTimeTextView.setVisibility(View.VISIBLE);
				}

			} else if (msg.what == DISMISS_PLAYER_PROGRESS_LAYOUT) {

				if (mPlayerProgressLayout.getVisibility() == View.VISIBLE) {
					mPlayerProgressLayout.setVisibility(View.INVISIBLE);
				}
				if(mPlayerNameLiner.getVisibility() == View.VISIBLE){
					mPlayerNameLiner.setVisibility(View.INVISIBLE);
				}
			} else if (msg.what == DISSMISS_VOLUME) {

				if (volumLay.getVisibility() == View.VISIBLE) {
					if (isVolumeChangedKeyClicked) {
						isVolumeChangedKeyClicked = false;
						mVideoPlayerHander.sendEmptyMessageDelayed(
								DISSMISS_VOLUME, VOLUM_BACK_TIME);
					} else {
						volumLay.setVisibility(View.INVISIBLE);
						mVideoPlayerHander.removeMessages(DISSMISS_VOLUME);
					}

					// if (mClickedBtStatus == VideoPlayerUIConst.CLICKED_VOLUME
					// ) {
					// // mIsVolumeClick = false;
					// mVideoPlayerHander
					// .sendEmptyMessage(DISPLAY_PLAYER_CONTROL_BUTTONS);
					// }// updateControlButtonStatus(true);
				}
			} else if (msg.what == DISPLAY_VOLUME) {
				refreshCurrentVolume();
				if (volumLay.getVisibility() == View.INVISIBLE) {
					volumLay.setVisibility(View.VISIBLE);
					volumLay.requestFocus();
					mVideoPlayerHander.removeMessages(DISSMISS_VOLUME);
					mVideoPlayerHander.sendEmptyMessageDelayed(DISSMISS_VOLUME,
							VOLUM_BACK_TIME);
					// updateControlButtonStatus(false);
				}
			} else if (msg.what == DISPLAY_PLAYER_PROGRESS_LAYOUT) {
				if (mPlayerProgressLayout.getVisibility() == View.INVISIBLE) {
					mPlayerProgressLayout.setVisibility(View.VISIBLE);
				}
				if(mPlayerNameLiner.getVisibility() == View.INVISIBLE){
					mPlayerNameLiner.setVisibility(View.VISIBLE);
				}
			} else if (msg.what == PLAYER_UI_INIT) {// UI初始化；
				playerUIInit();
			} else if (msg.what == VIDEO_PLAY__CHECK_BOOKMARK) {// 书签检查播放断点；
				dismissWaittingDialog();
				bookmark_check();
			} else if (msg.what == DO_BOOKMARK_ACTION) {
				doBookMarkAction();
			} else if (msg.what == DISMISS_DIALOG_FOR_TIMEOUT) {
				doTimeOutAction();
			} else if (msg.what == EXIT_VIDEOPLAY) {
				Utils.printLog(TAG, "EXIT_VIDEOPLAY");

				exitVideoPlay();

			} else if (msg.what == PLAYER_PAUSE) {
				playPause();
				//isPlaying = false;
				mVideoPlayerHander.sendEmptyMessage(DISPLAY_PLAYER_PROGRESS_LAYOUT);
			} else if (msg.what == PLAYER_START) {
				playStart();
				//isPlaying = true;
				mVideoPlayerHander.sendEmptyMessage(DISMISS_PLAYER_PROGRESS_LAYOUT);
			} else if (msg.what == VIDEO_PLAY_START_OR_PAUSE) {
				if (mVideoContrl != null&&mVideoContrl.isPlaying()) {
					mVideoPlayerHander.sendEmptyMessage(PLAYER_PAUSE);
				} else {
					mVideoPlayerHander.sendEmptyMessage(PLAYER_START);
				}
			} else if (msg.what == SHOW_WAIT_DIALOG) {
				showWatingDialog();
			} else if (msg.what == DISSMISS_WAIT_DIALOG) {
				dismissWaittingDialog();
			} else if (msg.what == -99999) {
			
			} else if (msg.what == REFRESH_PAUSEBUTON) {

				if (mVideoContrl != null) {

					if (!mVideoContrl.isPlaying()) {
						mPauseIcon.setVisibility(View.VISIBLE);
						mPauseIcon.requestLayout();
						mPauseIcon.invalidate();
						//isPlaying = false;
						Utils.printLog(TAG,
								"mPauseIcon.setVisibility(View.VISIBLE);");
					} else {
						//isPlaying = true;
						Utils.printLog(TAG,
								"mPauseIcon.setVisibility(View.INVISIBLE);");
						mPauseIcon.setVisibility(View.INVISIBLE);
					}
				}
			}else if (msg.what == REFRESH_TOTAL_TIME) {
				mPlayEndTime.setText(Utils.getTimeShort(sEndTime));
				if(sEndTime>0 && !mPlayerSeekar.isEnabled()){
					mPlayerSeekar.setEnabled(true);
				}
			}else if (msg.what == REFRESH_CURRENT_TIME) {

				int currentPostion = msg.getData().getInt("currentPos");
				mPlayBeginTime.setText(Utils.getTimeShort(currentPostion));
				if (sEndTime > 0) {

					
					int process =(int)Math.ceil((double)currentPostion*(double)Utils.SeekBarLength/(double)sEndTime);
					Utils.printLog(TAG, "SeekBarListener updateprocess currentPostion="+currentPostion+" ;sEndtime ="+sEndTime +"process"+process);
					mPlayerSeekar.setProgress(process);
				}
			}else if (msg.what == REFRESH_DDIMAGE) {
				//mDDIcon.setBackgroundResource(R.drawable.ddpicon);
				//mDDIcon.setVisibility(View.VISIBLE);
				//mVideoPlayerHander.sendEmptyMessageDelayed(REFRESH_DDIMAGE_DISMISS, 5000);
			}else if (msg.what == REFRESH_DTSIMAGE) {
				//mDDIcon.setBackgroundResource(R.drawable.dtsicon);
				//mDDIcon.setVisibility(View.VISIBLE);
				//mVideoPlayerHander.sendEmptyMessageDelayed(REFRESH_DDIMAGE_DISMISS, 5000);
			}else if (msg.what == REFRESH_DDIMAGE_DISMISS) {
				//mDDIcon.setVisibility(View.INVISIBLE);
			}else if (msg.what == DISMISS_PLAYER_PAUSE_ICON) {
				if(mPauseIcon.getVisibility() == View.VISIBLE){
					mPauseIcon.setVisibility(View.INVISIBLE);
					//isPlaying = true;
				} 
			}
         
		}
	};

	private void doTimeOutAction() {
		mVideoPlayerHander.sendEmptyMessage(DISSMISS_WAIT_DIALOG);
		Utils.printLog(TAG, "doTimeOutAction ");
		if (mVideoContrl.isVOD()) {
			new MyToast(VideoPlayerActivity.this, getResources().getString(
					R.string.media_player_timeout)).show();
		} else {

			new MyToast(VideoPlayerActivity.this, getResources().getString(
					R.string.media_player_timeout_local)).show();
		}
		mVideoPlayerHander.sendEmptyMessage(EXIT_VIDEOPLAY);
	}

	private BookMarkDB mBookMark = null;

	private void bookmark_check() {
		if (playList == null)
			return;
        if(mVideoContrl!=null&&mVideoContrl.isMediaPlayerPrepared()){
        	int duration = mVideoContrl.getDuration();
        	if(duration > 0){
        		String path = playList.get(mCurrIndex);
        		if (mBookMark != null && path != null) {
        			mBreakPos = mBookMark.getPosFromDB(path);
        			
        			Utils.printLog(TAG, "mBreakPos = " + mBreakPos + "  time = "
        					+ Utils.getTimeShort(mBreakPos));
        			if (mBreakPos <= 999) {
        				mMediaHanler
        						.sendEmptyMessage(BookMarkConst.START_PLAY_FROM_BEGIN);
        			} else {

        				try {
        					new StartPlayModeDialog(VideoPlayerActivity.this,
        							mMediaHanler, mBreakPos).show();
        				} catch (Exception e) {
        					e.printStackTrace();
        				}

        			}
        		}
        	}else{
        		mMediaHanler.sendEmptyMessage(BookMarkConst.START_PLAY_FROM_BEGIN);
        	}
        }
	
	}



	private void updateControlButtonStatus(boolean isNetworkConnected) {

		mPlayerNext.setEnabled(isNetworkConnected);
	
		mPlayerSequenceButton.setEnabled(isNetworkConnected);
		mPlayerSaveButton.setEnabled(isNetworkConnected);
		mPlayerStartButton.setEnabled(isNetworkConnected);
		mPlayerPrevious.setEnabled(isNetworkConnected);
		mPlayerVolumeButton.setEnabled(isNetworkConnected);
	
		mPlayerTurn3DButton.setEnabled(isNetworkConnected);

		mPlayerNext.setFocusable(isNetworkConnected);

		mPlayerSequenceButton.setFocusable(isNetworkConnected);
		mPlayerSaveButton.setFocusable(isNetworkConnected);
		mPlayerStartButton.setFocusable(isNetworkConnected);
		mPlayerPrevious.setFocusable(isNetworkConnected);
		mPlayerVolumeButton.setFocusable(isNetworkConnected);
	
		mPlayerTurn3DButton.setFocusable(isNetworkConnected);
		if (isNetworkConnected) {
			mPlayerStartButton.requestFocus();
			if (mVideoContrl != null && !mVideoContrl.isVOD()) {
				mPlayerSaveButton.setEnabled(false);
				mPlayerSaveButton.setFocusable(false);
			}
		}

		// mPlayerPrevious.setEnabled(isNetworkConnected);
		// mPlayerPrevious.setFocusable(isNetworkConnected);
		// mPlayerNext.setEnabled(isNetworkConnected);
		// mPlayerNext.setFocusable(isNetworkConnected);
		// mMutiPlayerPrevious.setEnabled(isNetworkConnected);
		// mMutiPlayerPrevious.setFocusable(isNetworkConnected);
		// mMutiPlayerNext.setEnabled(isNetworkConnected);
		// mMutiPlayerNext.setFocusable(isNetworkConnected);
		// mPlayerSequenceButton.setEnabled(isNetworkConnected);
		// mPlayerSequenceButton.setFocusable(isNetworkConnected);
		// mPlayerVolumeButton.setEnabled(isNetworkConnected);
		// mPlayerVolumeButton.setFocusable(isNetworkConnected);
	}

	private BroadcastReceiver myCollectionBroadcastReceiver = new BroadcastReceiver() {
		// private final String TAG = "myCollectionBroadcastReceiver";
		private int mAddType = -1;

		@Override
		public void onReceive(Context context, Intent intent) {

			Log.d(TAG,"now intent action is "+intent.getAction());
	         if (intent.getAction().equals(
					VideoPlayerUIConst.ATV_OSD_OPEN)) {
				if (mPlayerControlLayout.getVisibility() == View.VISIBLE) {
					mPlayerControlLayout.setVisibility(View.INVISIBLE);
				}
			} else if (CommonConst.CLOSE_VIDEO_PLAY.equalsIgnoreCase(intent
					.getAction())) {
				Utils.printLog(TAG, "CLOSE_VIDEO_PLAY");

				exitVideoPlay();
			} else if (CommonConst.HOME_PRESSED.equalsIgnoreCase(intent
					.getAction())) {
				Utils.printLog(TAG, "HomePRESSED");
				exitVideoPlay();
			} else if (CommonConst.STR_PORWER_CHANGE.equalsIgnoreCase(intent
					.getAction())) {
				Utils.printLog(TAG, "STR SHUT DOWN"); // add here for str
				exitVideoPlay();
			}else if (CommonConst.TV_PRESSED.equalsIgnoreCase(intent
					.getAction())) {
				Utils.printLog(TAG, "TV_PRESSED");
				exitVideoPlay();
			} else if (CommonConst.SOUCE_CHANGE_PRESSED.equalsIgnoreCase(intent
					.getAction())) {
				Utils.printLog(TAG, "SOUCE_CHANGE_PRESSED");
				exitVideoPlay();
			} else if (CommonConst.REFRESH_PAUSE_ICON.equalsIgnoreCase(intent
					.getAction())) {
				Utils.printLog(TAG, "REFRESH_PAUSE_ICON");
				Utils.printLog(TAG,
						"mPauseIcon.setVisibility(View.INVISIBLE);");
				mPauseIcon.setVisibility(View.INVISIBLE);
				//mVideoPlayerHander.sendEmptyMessage(REFRESH_PAUSEBUTON);
			}
		}
	};
//	private void setVideoBackLight(){
//		int videoBackLight = getSavedBackLight(this);
//		if(mPictureManager!=null&&videoBackLight>=0){
//			Utils.printLog(TAG, "---"+videoBackLight);
//			try {
//				mPictureManager.setBacklight(videoBackLight);
//			} catch (TvCommonException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//	}

	private void updateControlButtonStatus() {
		if(mPlayerControlLayout.getVisibility()== View.INVISIBLE || playList==null){
			return;
		}
		Utils.printLog(TAG, "updateControlButtonStatus");
		
		int size = playList.size();
		if (mVideoContrl.getPlayType() == VideoPlayerContrlConsts.MEDIA_RANDOM_PLAY
				|| mVideoContrl.getPlayType() == VideoPlayerContrlConsts.MEDIA_CYCLE_PLAY
		/*
		 * || sCurrentPlayerType + 1 ==
		 * MediaControlIntent.FLAG_MEDIA_SINGLE_REPEAT_PLAY
		 */) {

			if (playList.size() == 1
					&& mVideoContrl.getPlayType() == VideoPlayerContrlConsts.MEDIA_RANDOM_PLAY) {
				mPlayerPrevious.setEnabled(false);
				mPlayerPrevious.setFocusable(false);
				mPlayerNext.setEnabled(false);
				mPlayerNext.setFocusable(false);
				return;
			}
			mPlayerPrevious.setEnabled(true);
			mPlayerPrevious.setFocusable(true);
			mPlayerNext.setEnabled(true);
			mPlayerNext.setFocusable(true);
			return;
		}
		final int max = size - 1;
		final int min = 0;
		if (mCurrIndex == max
		/*
		 * || sCurrentPlayerType + 1 ==
		 * MediaControlIntent.FLAG_MEDIA_SINGLE_PLAY
		 */) {
			mPlayerNext.setEnabled(false);
			mPlayerNext.setFocusable(false);
		} else {
			mPlayerNext.setEnabled(true);
			mPlayerNext.setFocusable(true);
		}
		if (mCurrIndex == min
		/*
		 * || sCurrentPlayerType + 1 ==
		 * MediaControlIntent.FLAG_MEDIA_SINGLE_PLAY
		 */) {
			mPlayerPrevious.setEnabled(false);
			mPlayerPrevious.setFocusable(false);
		} else {
			mPlayerPrevious.setEnabled(true);
			mPlayerPrevious.setFocusable(true);
		}
	}

	private OnTouchListener mOnProgressTouchListener = new OnTouchListener() {
		public boolean onTouch(View v, MotionEvent event) {
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				Utils.printLog(TAG, "onTouchEvent mOnProgressTouchListener");
				float clickPosition = event.getX();
				Log.e(TAG, "getRawX=" + event.getRawX());
				Log.e(TAG, "getX=" + clickPosition);
				int seekPosition = 0;
				if (Utils.isWindow1080(VideoPlayerActivity.this)) {
					clickPosition = clickPosition;
					seekPosition = (int) ((float) clickPosition
							/ sScrollRange_1080 * sEndTime);
				} else {
					clickPosition = clickPosition + 8;
					seekPosition = (int) ((float) clickPosition
							/ sScrollRange_720 * sEndTime);
				}

				if (seekPosition > sEndTime) {
					seekPosition = sEndTime - 1000;
				}
				try {
					if (mVideoContrl != null
							&& mVideoContrl.isMediaPlayerPrepared())
						mVideoContrl.seekTo(seekPosition);
				} catch (IllegalStateException e) {
					Log.e(TAG, "IllegalStateException");
				}
			}
			return true;
		}

	};

	private SurfaceHolder.Callback mSubtiteHolder = new SurfaceHolder.Callback() {

		@Override
		public void surfaceChanged(SurfaceHolder holder, int format, int width,
				int height) {
			// TODO Auto-generated method stub
			Utils.printLog("mSubtiteHolder callback", "surfaceChanged");
		}

		@Override
		public void surfaceCreated(SurfaceHolder holder) {
			// TODO Auto-generated method stub
			Utils.printLog("mSubtiteHolder callback", "surfaceCreated");
		}

		@Override
		public void surfaceDestroyed(SurfaceHolder holder) {
			// TODO Auto-generated method stub
			Utils.printLog("mSubtiteHolder callback", "surfaceDestroyed");
		}

	};
	
	 public int getBackLight() {
		  int backLight = 0;
		  Cursor cursor = resolver.query(Uri.parse("content://com.tcl.tv.sys.SYSPicSettings/pic_settings"), new String[] {"backlight"}, null, null, null);
		  if (cursor != null) {
		   if (cursor.moveToFirst()) {
		    int backLightIndex = cursor.getColumnIndex("backlight");
		    backLight = cursor.getInt(backLightIndex);
		    Utils.printLog(TAG, "getBackLight()-------"+backLight);
		   }
		   cursor.close();
		  }
		  return backLight;
		 }
	 
	 public  static void setBooleanNatrueLight(Context context , boolean stat) {
         SharedPreferences mSp;
         mSp = context.getSharedPreferences("NatureLight", Context.MODE_WORLD_WRITEABLE);
         mSp.edit().putBoolean(VideoPlayerUIConst.VEDIO_NATURAL_LIGHT, stat).commit();
     }

     public  static  boolean  getBooleanISNatrueLightON(Context context) {
         SharedPreferences mSp;
         mSp = context.getSharedPreferences("NatureLight", Context.MODE_WORLD_READABLE);
         return mSp.getBoolean(VideoPlayerUIConst.VEDIO_NATURAL_LIGHT, false);
     }
     
//     public   int getSavedBackLight() {
//		int backlight = -1;
//		SharedPreferences settings = getSharedPreferences(
//				VideoPlayerUIConst.VEDIO_SAVED_BACKLIGHT, -1);
//		backlight = settings.getInt(VideoPlayerUIConst.VEDIO_SAVED_BACKLIGHT,-1);
//		Utils.printLog(TAG, "getSavedBackLight backlight =" + backlight);
//		return backlight;
//	}
// 	
//     public  void saveBackLight(int backlight) {
//		
//		SharedPreferences settings = getSharedPreferences(
//				VideoPlayerUIConst.VEDIO_SAVED_BACKLIGHT, 0);
//		SharedPreferences.Editor editor = settings.edit();
//		editor.putInt(VideoPlayerUIConst.VEDIO_SAVED_BACKLIGHT,backlight);
//		editor.commit();
//	}
     
     
     public   static int getSavedBackLight(Context context) {
		int backlight = -1;
		SharedPreferences settings = context.getSharedPreferences(
				VideoPlayerUIConst.VEDIO_SAVED_BACKLIGHT, -1);
		backlight = settings.getInt(VideoPlayerUIConst.VEDIO_SAVED_BACKLIGHT,-1);
		Utils.printLog(TAG, "getSavedBackLight backlight =" + backlight);
		return backlight;
	}
 	
     public  static void saveBackLight(Context context,int backlight) {
		
		SharedPreferences settings = context.getSharedPreferences(
				VideoPlayerUIConst.VEDIO_SAVED_BACKLIGHT, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putInt(VideoPlayerUIConst.VEDIO_SAVED_BACKLIGHT,backlight);
		editor.commit();
	}
     
     public Bitmap getframeBitmap(String filePath,int w,int h) {
         Bitmap bitmap = null;
         MediaMetadataRetriever retriever = new MediaMetadataRetriever();
         try {
             retriever.setDataSource(filePath);
             bitmap = retriever.getFrameAtTime(188000);
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
     
     
//     private Handler myHandler = new Handler() {
//
// 		public void handleMessage(Message msg) {
// 			int arg1 = msg.arg1;
// 			int arg2 = msg.arg2;
// 			Utils.printLog(TAG, "PvrNotify.NOTIFY"+msg.what);
// 			switch (msg.what) {
// 			case PvrNotify.NOTIFY_PVR_DISK_FULL: 
// 				// do something
// 			
// 				break;
//
// 			case PvrNotify.NOTIFY_PVR_NO_SIGNA: 
// 				// do something
// 			
// 				break;
//
// 			case  PvrNotify.NOTIFY_PVR_PLAYBACK_END:
// 				// do something
// 				Utils.printLog(TAG, "PvrNotify.NOTIFY_PVR_PLAYBACK_END ");
// 				mMediaHanler.sendEmptyMessage(NEXT_VIDEO);
// 				break;
// 			}
// 		}
// 	};
     
 private void setTVToFullScreen() {

    	 

         final VideoWindowRect videoWindowType = new VideoWindowRect();

         videoWindowType.x = 65535;

         videoWindowType.y = 65535;

         videoWindowType.width = 65535;

         videoWindowType.height = 65535;

   

         try {
        	 Log.d(TAG,"now to set TV to FullScreen");

             if (TTvPictureManager.getInstance(this) != null) {

                TTvPictureManager.getInstance(this).scaleVideoWindow(

                       EnTCLWindow.EN_TCL_MAIN, videoWindowType);

             }

         } catch (Exception e) {

             e.printStackTrace();

         }

      }
 
 
 private int changeSourceWaiting = 1234;
 private boolean isSourceChanged = false;
 protected Handler setSourceHandler = new Handler() { 
	 public void handleMessage(Message msg) { 
	 super.handleMessage(msg); 

	 Log.i(TAG, "setSourceHandler: msg.what" + msg.what); 

	 if(msg.what == changeSourceWaiting){
		 mVideoPlayerHander.sendEmptyMessage(SHOW_WAIT_DIALOG);
	 }
	 if (msg.what == EnTCLCallBackSetSourceMsg.EN_TCL_SET_SOURCE_END_SUCCEED.ordinal()) { //切换信源结束的消息
	 
		 if(isSourceChanged){
			 
		 }else{
			 isSourceChanged = true;
			 if (!getPlayList(getIntent())) {
					exitPlayforNoPlayList();
					return;
				}
				if (mCurrIndex < 0) {
					mCurrIndex = 0;
				} 
				
		 }
		 EnTCLInputSource inputSource = TTvCommonManager.getInstance(VideoPlayerActivity.this).getCurrentInputSource();
		 Log.d(TAG,"now change source successd"+ inputSource);
		
	 } 
	 } 

	 };


}