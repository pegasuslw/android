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
package com.tcl.common.mediaplayer.video.UI;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.util.LangUtils;
import org.json.JSONException;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.media.AudioManager;
import android.media.AudioManager.OnAudioFocusChangeListener;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.os.RemoteException;
import android.os.SystemClock;
import android.tclwidget.TCLSeekBar;
import android.util.DisplayMetrics;
//import android.telephony.gsm.SmsMessage.MessageClass;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.iflytek.xiri.Feedback;
import com.paster.util.JsonUtil;
import com.tcl.common.mediaplayer.aidl.CommonConst;
import com.tcl.common.mediaplayer.aidl.MediaBean;
import com.tcl.common.mediaplayer.utils.CrossPlatFormAnalyzer;
import com.tcl.common.mediaplayer.utils.CustomProgressDialog;
import com.tcl.common.mediaplayer.utils.ErrorConsts;
import com.tcl.common.mediaplayer.utils.MediaPlayerApplication;
import com.tcl.common.mediaplayer.utils.MyToast;
import com.tcl.common.mediaplayer.utils.NotePopupWindow;
import com.tcl.common.mediaplayer.utils.SeekBarPopWindow;
import com.tcl.common.mediaplayer.utils.TypefaceTextView;
import com.tcl.common.mediaplayer.utils.TypefaceTextViewScrolling;
import com.tcl.common.mediaplayer.utils.Utils;
import com.tcl.common.mediaplayer.utils.VolumeController;
import com.tcl.common.mediaplayer.utils.WaitDialogCallBackInterface;
import com.tcl.common.mediaplayer.video.UI.VideoFileListDialog.FileListCallBack;
import com.tcl.common.mediaplayer.video.UI.VideoMenuSettingDialog.SettingMenuCallBack;
import com.tcl.common.mediaplayer.video.bookmark.BookMarkConst;
import com.tcl.common.mediaplayer.video.bookmark.BookMarkDB;
import com.tcl.common.mediaplayer.video.bookmark.StartPlayModeDialog;
import com.tcl.common.mediaplayer.video.contrl.IVideoPlayControlHandler;
import com.tcl.common.mediaplayer.video.contrl.VideoPlayControlCallback;
import com.tcl.common.mediaplayer.video.contrl.VideoPlayerContrlConsts;
import com.tcl.deviceinfo.TDeviceInfo;
import com.tcl.multiscreeninteractiontv.IDLNAService;
import com.tcl.multiscreeninteractiontv.IPlayerCallback;
import com.tcl.tvmanager.TTvCommonManager;
import com.tcl.tvmanager.TTvManager;
import com.tcl.tvmanager.TTvPictureManager;
import com.tcl.tvmanager.TTvSoundManager;
import com.tcl.tvmanager.TTvUtils;
import com.tcl.tvmanager.TvManager;
import com.tcl.tvmanager.vo.EnTCLAspectRatio;
import com.tcl.tvmanager.vo.EnTCLCallBackSetSourceMsg;
import com.tcl.tvmanager.vo.EnTCLInputSource;
import com.tcl.tvmanager.vo.EnTCLPicSetting;
import com.tcl.tvmanager.vo.EnTCLPictureMode;
import com.tcl.tvmanager.vo.EnTCLWindow;
import com.tcl.tvmanager.vo.VideoWindowRect;
import com.tcl.videoplayer.R;
/*import com.tvos.common.PictureManager;
 import com.tvos.common.TvManager;
 import com.tvos.common.exception.TvCommonException;*/
import com.usagestats.util.behavior.MediaPlayerBehavior;

@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
public class VideoPlayerActivity extends XiriSceneActivity implements FileListCallBack, SettingMenuCallBack {
	private static final String TAG = Utils.PKG_NAME + "VideoPlayerActivity";
	private static final int DISMISS_PLAYER_CONTROL_BUTTONS = 0;
	private static final int DISPLAY_PLAYER_CONTROL_BUTTONS = 1;
	private static final int DISPLAY_PLAYER_PROGRESS_LAYOUT = 2;
	private static final int DISMISS_PLAYER_PROGRESS_LAYOUT = 3;
	private static final int DISMISS_PLAYER_CONTROL_BUTTONS_FIRST = 7895;
	private static final long VOLUM_BACK_TIME = 5000;
	private static final int SHOW_WAIT_DIALOG = 2342;
	private static final int DISSMISS_WAIT_DIALOG = 23442;
	private static final int REFRESH_PAUSEBUTON = 876983;
	private static final int REFRESH_DOLBY_IMAGE = 876988;
	private static final int REFRESH_DTS_IMAGE = 876989;
	private static final int REFRESH_DTSHD_MASTER_AUDIO_IMAGE = 876990;
	private static final int REFRESH_DTS_EXPRESS_IMAGE = 876991;
	private static final int REFRESH_DOLBY_PLUS_IMAGE = 876992;
	private static final int REFRESH_DTS_IMAGE_DISMISS = 876999;
	private static final int REFRESH_DTS_VISION_IMAGE_DISMISS = 876995;
	private static final int REFRESH_SEEKBAR_OP = 1010101;
	private static final int DISPLAY_PLAYER_AUDIO_NUM = 876512;
	private static final int START_PLAY = 4; // 开始进行视频的播放，触发播放器控制接口�?
	private static final int PLAYER_UI_INIT = 5;// 更新UI界面，以与播放保持一致；
	private static final int DISMISS_DIALOG_FOR_TIMEOUT = 7;// 播放准备超时�?
	private static final int PLAYER_PAUSE = 9;// 播放暂停�?
	private static final int PLAYER_START = 10;// 播放开始；
	private static final int PLAYER_URL = 19;// 多屏互动推送特定url开始；
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
	private static final int REFRESH_SEEKBAR_UNNABLE = 565591;// 视频开始或者暂停；
	private static final int VIDEO_NOTSUPPORT_SHARE = 4543;// 视频开始或者暂停；
	private static final int VOICE_FORWORD = 4546;// display the pause
	private static final int VOICE_BACKWORD = 4547;// 视频开始或者暂停；
	private static final int VOICE_SEEK_POSITION = 4548;// 视频开始或者暂停；
	private static final int CHANGELANUAGE = 4549; //变化语言刷新界面
	private boolean isOnNewIntent = false;
	private int m3DBtStatus = VideoPlayerUIConst.NO_3D;
	private int mClickedBtStatus = VideoPlayerUIConst.CLICKED_NO;
	private TTvPictureManager mPicmanager;
	// 播放模式的图片序列；
	public static final int mPlayerTypeIcon[] = { R.drawable.player_seqence_selector, R.drawable.player_single_selector, R.drawable.player_single_repeat_selector,
			R.drawable.player_rendom_selector, R.drawable.player_cycle_selector };
	// 播放模式的文字解释序列；
	public static final int mPlayerTypeText[] = { R.string.Video_Info_RepeatMode_Normal, R.string.Video_Info_RepeatMode_Single, R.string.Video_Info_RepeatMode_Re_one,
			R.string.Video_Info_RepeatMode_Random, R.string.Video_Info_RepeatMode_Re_all };

	private SurfaceView mSurfaceView = null;
	private SurfaceHolder holder = null;
	private volatile int mCurrIndex = 0;
	private int sEndTime = 0;
	private Timer mTime = new Timer();
	private Timer playEventTimer = new Timer();
	private ArrayList<MediaBean> mList;
	// private View mPlayerControlLayout;
	private View mPlayerProgressLayout;
	// private TextView mName;
	private TextView mPlayEndTime;
	private TextView mPlayBeginTime;
	private LinearLayout mPauseIcon;
	private TextView mPauseTimeTextView;
	private TextView mSubtitleTextView;
	private SurfaceView mSubtitleSurfaceView;
	private LinearLayout mDtsLayout;
	private TextView mDtsTextView;
	private ImageView mDtsImageView;
	private LinearLayout mDtsLayout_vision;
	private TextView mDtsTextView_vision;
	private ImageView mDtsImageView_vision;

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
	private String mTheLastPath;
	private String mTheLastName;
	private int mTheLastPosition = VideoPlayerContrlConsts.GET_CURRENTPOSITION_ERROR;

	private CustomProgressDialog mDialog;
	private MyToast mToast;

	private boolean mPauseByNetWorkDisconnected = false;// 网络连接断开，播放暂停；

	private int isDismissControl = 0;

	private IVideoPlayControlHandler mVideoContrl = null;// 视频控制接口�?
	private int mBreakPos = 0;
	private boolean isFirstNetChange = true;

	private volatile boolean isNextPreValid = true;
	private boolean isNeedSave = false;
//	private int m3DModeSet = 0;
	private String mTheLast3DPath = null;
	private boolean is3DEnabled = true;
	private boolean isShowContrlBtnFirst = true;
	private long userStateTimeStart = -1;
	private ContentResolver resolver;
	private TCLSeekBar mPlayerSeekar;
	private SeekBarPopWindow seekBarPopWindow;

	private FrameLayout mPlayerInfo;
	private String clienttype = "";
	private boolean isFromWeb = false;
	private IDLNAService nSreenTVService;
	private TTvSoundManager soundManager;
	private TvManager mTvmanager;
	private TTvManager mTTvmanager;
	private String playStatus = "TRANSITIONING";
	private boolean isDMR = false;
	private boolean isDLNA = false;
	private boolean isChangeNotStart = true;
	private boolean isMediaFile3D = true;
	private String[] splitArrayStrings;

	// WJ Add for Gener UI 201512
	private TypefaceTextViewScrolling mediaName;
	private VideoFileListDialog listDialog = null;
	public static int clickFileListPosition;
	private static final int showFileListMessage = 2106; // 显示文件列表界面
	private static final int showMenuDialogMessage = 618; // 显示更多选项
	private FrameLayout up_guide_ly;
	Animation menuInAnim;
	Animation menuOutAnim;
	Animation controllerLyAnim;
	Animation controllerLyOutAnim;
	Animation pauseIconInAnim;
	Animation pauseIconOutAnim;
	private VideoMenuSettingDialog menuDialog = null;
	private MediaPlayerApplication application;
	private String SHOW_WAIT = "SHOW_WAIT";
	private String DISSMISS_WAIT = "DISSMISS_WAIT";
	private String PLAYING = "PLAYING";
	private String PAUSED_PLAYBACK = "PAUSED_PLAYBACK";
	private String STOPPED = "STOPPED";
//	private String SEEK_FORWORD = "SEEK_FORWORD";
//	private String SEEK_BACKWORD = "SEEK_BACKWORD";
//	private String SEEK_POSITION = "SEEK_POSITION";
	private String PLAYBACK = "PLAYBACK";
	private String  COMPLETE = "COMPLETE";
	private WakeLock wakeLock;
	private boolean isBookMark = false;
	private AudioManager mAmanager;
	private boolean isDMRstop;
	private TypefaceTextViewScrolling mKeydown;
	private TypefaceTextViewScrolling mKeymenu;

	boolean isBind;
	private BookMarkDB mBookMark = null;
	
	private TextView volumMinNum;
	private ProgressBar volumBar;
	private LinearLayout volumLay;
	private int curVolum = 0;
	private VolumeController volumeContrl;
	
	private boolean isSourceChanged = false;
	private boolean ischangeSourcePlayed = false;
	private final int TYPE_HDR = 1279;	
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Utils.printLog(TAG, "onCreate start 20160621");
		resolver = getContentResolver();
		setContentView(R.layout.video_player);
		
		mAmanager = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);
		soundManager = TTvSoundManager.getInstance(this);
		mPicmanager = TTvPictureManager.getInstance(this);
		mTvmanager = TvManager.getInstance(this);
		mTTvmanager = TTvManager.getInstance(this);
		application = (MediaPlayerApplication) getApplication();
		
		
		mTTvmanager.addHandler(setSourceHandler, TTvUtils.TV_HANDLER_INDEX_TV_SET_SOURCE);
		
		// 切信源
		EnTCLInputSource curSource = TTvCommonManager.getInstance(this).getCurrentInputSource();

		Utils.printLog(TAG, "onCreate  curSource==" + curSource);
		if (curSource != EnTCLInputSource.EN_TCL_STORAGE) {
//			TTvCommonManager.getInstance(this).setInputSource(EnTCLInputSource.EN_TCL_STORAGE);
			TTvCommonManager.getInstance(this).setInputSource(EnTCLInputSource.EN_TCL_STORAGE,true);
			Utils.printLog(TAG, "setInputSource EN_TCL_STORAGE 1");
		}else{
			isSourceChanged = true;
			Utils.printLog(TAG, "InputSource EN_TCL_STORAGE 2");
		}
		
		

		TDeviceInfo devinfo = TDeviceInfo.getInstance();
		clienttype = devinfo.getClientType(devinfo.getProjectID());
		Utils.printLog(TAG, "clienttype is " + clienttype);
		if (clienttype != null) {
			splitArrayStrings = clienttype.split("-");
		}

		if (!getPlayList(getIntent())) {
			exitPlayforNoPlayList();
			return;
		}
		/***先获取参数，确认是否启动极简播放器后，再show loading**/
//		showWatingDialog(isSimple);
		if (mCurrIndex < 0) {
			mCurrIndex = 0;
		}
		Utils.printPlayList(mList);
		seekBarPopWindow = new SeekBarPopWindow(this, mMediaHanler);
		popWindow = new NotePopupWindow(this, mMediaHanler);
		findView();
		mVideoContrl = new IVideoPlayControlHandler(this);
		mVideoContrl.registerCallback(mCallback);
		application.setVideoContrl(mVideoContrl);
		mBookMark = new BookMarkDB(this);
		mSurfaceView = (SurfaceView) this.findViewById(R.id.surfaceView);
		holder = mSurfaceView.getHolder();
		holder.addCallback(mVideoContrl);
		holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		mTime = new Timer();
		mTime.schedule(mTimerTask, 0, 1000);
		//startPlayEventTimer();
		IntentFilter intentFilter2 = new IntentFilter();
		intentFilter2.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
		this.registerReceiver(mConnectionChangeReceiver, intentFilter2);

		IntentFilter intentFilter3 = new IntentFilter();
//		intentFilter3.addAction(VideoPlayerUIConst.COLLECTION_OPERATE_DONE);
		intentFilter3.addAction(VideoPlayerUIConst.ATV_OSD_OPEN);
		intentFilter3.addAction(CommonConst.CLOSE_VIDEO_PLAY);
		intentFilter3.addAction(CommonConst.HOME_PRESSED);
		intentFilter3.addAction(CommonConst.STR_PORWER_CHANGE);
		intentFilter3.addAction(CommonConst.TV_PRESSED);
		intentFilter3.addAction(CommonConst.CHANGE_SOURCE_PRESSED);
		intentFilter3.addAction(Intent.ACTION_SHUTDOWN); 
		intentFilter3.addAction(CommonConst.EXIT_3D);
		intentFilter3.addAction(CommonConst.HISTORYRECORD);
		intentFilter3.addAction(CommonConst.SOURCE_CHANGEandVOICEASS);
		intentFilter3.addAction(CommonConst.LanuageChange);
		intentFilter3.addAction(CommonConst.VIDEO_HDR_HLG_BROADCAST);
		this.registerReceiver(myCollectionBroadcastReceiver, intentFilter3);

		IntentFilter intentFilter4 = new IntentFilter();
		intentFilter4.addAction(CommonConst.VOICE_CONTROL);
		this.registerReceiver(mVoiceReceiver, intentFilter4);
		sendStopWidgetMusicBroadcast();

		if (isDMR) {
			mVideoContrl.setPlayType(VideoPlayerContrlConsts.MEDIA_SEQUENCE_PLAY);
		} else {
			mVideoContrl.setPlayType(getSavedPlayerType());
		}

		listDialog = new VideoFileListDialog(this, mList, application);
		listDialog.setCallback(this);

		menuDialog = new VideoMenuSettingDialog(this, application);
		menuDialog.setCallback(this);
		//设置屏幕保持唤醒
		PowerManager powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
		wakeLock = powerManager.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "VideoPlayer");
		//退出屏保 
		int sysVersion = VERSION.SDK_INT;  
		Log.v(TAG, "onCreate sysVersion= "+sysVersion);
		if (sysVersion>=17) {
			//退出屏保  minSdkVersion="17"
			powerManager.wakeUp(SystemClock.uptimeMillis());
			Utils.printLog(TAG, "powerManager.wakeUp");
		}
		
		int versionCode = Utils.getVersionCodeFromAPP(this);
		Utils.printLog(TAG, "onCreate end ,versionCode="+versionCode);
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
		}, 3000);
	}

	private Handler mMediaHanler = new Handler() {
		public void handleMessage(Message msg) {

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

				if (isNextPreValid) {

					isNextPreValid = false;
					contrlIsNextPreValid();
					if (mVideoContrl != null
							&& mVideoContrl.isMediaPlayerPrepared()) {
						new Thread(new Runnable() {

							@Override
							public void run() {
								// TODO Auto-generated method stub
								mVideoContrl.playNext();

							}
						}).start();
					}
				} else {
					Utils.printLog(TAG, " next invalid!");
				}

			} else if (msg.what == PRE_VIDEO) {
				Utils.printLog(TAG, "Handler msg :PRE_VIDEO");

				if (isNextPreValid) {

					isNextPreValid = false;
					contrlIsNextPreValid();
					if (mVideoContrl != null
							&& mVideoContrl.isMediaPlayerPrepared()) {
						new Thread(new Runnable() {

							@Override
							public void run() {
								// TODO Auto-generated method stub
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

//				new Thread(new Runnable() {
//					
//					
//					
//					@Override
//					public void run() {
//						// TODO Auto-generated method stub
						mVideoContrl.play(mList, mCurrIndex, holder);  //cancel thread because setting AudioBT need handler
////						                                               //20161023
//					}
//				}).start();
//                mylocalThread = new myThread();
//                mylocalThread.start();
			

			} else if (msg.what == BookMarkConst.START_PLAY_FROM_BEGIN) {

				Utils.printLog(TAG, "START_PLAY_FROM_BEGIN");

				new Thread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						mVideoContrl.start();
						isBreakDialogDissmiss = true;
						sendFirstShowContrlBtn();
						isChangeNotStart = true;
						mVideoPlayerHander.sendEmptyMessage(REFRESH_PAUSEBUTON);
						// start_3Dmode();
					}
				}).start();
				// mVideoPlayerHander.sendEmptyMessage(DISSMISS_WAIT_DIALOG);

			} else if (msg.what == BookMarkConst.START_PLAY_FROM_BREAK) {
				Utils.printLog(TAG, "START_PLAY_FROM_BREAK with pos = " + mBreakPos);

				new Thread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						mVideoContrl.seekTo(mBreakPos);
						mVideoContrl.start();
						isBreakDialogDissmiss = true;
						isChangeNotStart = true;

						// start_3Dmode();

					}
				}).start();
				// mVideoPlayerHander.sendEmptyMessage(DISSMISS_WAIT_DIALOG);

			} else if (msg.what == CommonConst.PLAY_DEVICE_UNMOUTED) {
				new MyToast(VideoPlayerActivity.this, VideoPlayerActivity.this
						.getResources().getString(
								R.string.unmountupan)).show();
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
				new MyToast(VideoPlayerActivity.this, VideoPlayerActivity.this
						.getResources().getString(
								R.string.media_player_listNull)).show();
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
						.getString(R.string.media_player_exception)).show();
			} else if (msg.what == CommonConst.media_interrupt) {
				new MyToast(VideoPlayerActivity.this, VideoPlayerActivity.this
						.getResources().getString(R.string.media_interrupt))
						.show();

			} else if (msg.what == CommonConst.unknown_video_format) {
				isMediaFile3D = false;
				new MyToast(VideoPlayerActivity.this, VideoPlayerActivity.this
						.getResources()
						.getString(R.string.unknown_video_format)).show();

			} else if (msg.what == CommonConst.unknown_audio_format) {
				VideoPlayerActivity.this.sendBroadcast(new Intent(
						"com.android.videosetting.refreshaudiotrack"));
				new MyToast(VideoPlayerActivity.this, VideoPlayerActivity.this
						.getResources()
						.getString(R.string.unknown_audio_format)).show();

			} else if (msg.what == CommonConst.media_player_network_slow) {
				new MyToast(VideoPlayerActivity.this, VideoPlayerActivity.this
						.getResources().getString(
								R.string.media_player_network_slow)).show();

			} else if (msg.what == CommonConst.media_player_not_seekable) {
				Log.d(TAG,"now to show toast seekable");
				new MyToast(VideoPlayerActivity.this, VideoPlayerActivity.this
						.getResources().getString(
								R.string.media_player_not_seekable)).show();

			} else if (msg.what == CommonConst.media_player_no_audio) {
				new MyToast(VideoPlayerActivity.this, VideoPlayerActivity.this
						.getResources().getString(R.string.audiotrack_iszero))
						.show();

			} else if (msg.what == CommonConst.media_player_no_video) {
				new MyToast(VideoPlayerActivity.this, VideoPlayerActivity.this
						.getResources().getString(R.string.videotrack_iszero))
						.show();

			} else if (msg.what == CommonConst.media_player_already_firstfiles) {
				new MyToast(VideoPlayerActivity.this, VideoPlayerActivity.this
						.getResources().getString(R.string.already_first_files))
						.show();

			} else if (msg.what == CommonConst.media_player_already_lastfiles) {
				new MyToast(VideoPlayerActivity.this, VideoPlayerActivity.this
						.getResources().getString(R.string.already_last_files))
						.show();

			} else if (msg.what == CommonConst.mediafile_notsupport3d) {
				new MyToast(VideoPlayerActivity.this, VideoPlayerActivity.this
						.getResources().getString(R.string.videonothreeD))
						.show();

			} else if (msg.what == VIDEO_NOTSUPPORT_SHARE) {
				new MyToast(VideoPlayerActivity.this, VideoPlayerActivity.this
						.getResources().getString(
								R.string.four_k_video_notshare)).show();

			} else if (msg.what == CommonConst.media_player_unknown_exception_38) {
				new MyToast(VideoPlayerActivity.this, VideoPlayerActivity.this
						.getResources().getString(
								R.string.media_player_exception)).show();
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
		SharedPreferences settings = getSharedPreferences(VideoPlayerUIConst.MY_SPREFS_FILE, 0);
		playtype = settings.getInt(VideoPlayerUIConst.VEDIO_PLAY_TYPE, 0);
		Utils.printLog(TAG, "getSaved sCurrentPlayerType =" + playtype);
		return playtype;
	}

	// 由于播放列表为空而退出视频播放；
	private void exitPlayforNoPlayList() {
		Utils.printLog(TAG, "exitPlayforNoPlayList");
		new MyToast(VideoPlayerActivity.this, getResources().getString(R.string.media_player_listNull)).show();
		VideoPlayerActivity.this.finish();
	}

	@Override
	protected void onNewIntent(Intent intent) {
		// TODO Auto-generated method stub
		Utils.printLog(TAG, "onNewIntent");
		if (!getPlayList(intent)) {
			exitPlayforNoPlayList();
			return;
		}

		if (mVideoContrl != null && mVideoContrl.isFinishInit()) {
			mVideoContrl.setFinishInit(false);
			isOnNewIntent = true;
			mVideoContrl.releaseMediaPlayer();
		}
		super.onNewIntent(intent);
		Utils.printLog(TAG, "onNewIntent  end");
	}

	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		
		if(!mVideoContrl.isMediaPlayerPrepared()){
			Log.d(TAG,"now refused to do everything");
			if(event.getKeyCode() == KeyEvent.KEYCODE_DPAD_LEFT  
					|| event.getKeyCode() == KeyEvent.KEYCODE_DPAD_RIGHT 
					|| event.getKeyCode() == KeyEvent.KEYCODE_DPAD_DOWN){
				return true;
			}
		}
		
		if (mPlayerProgressLayout.getVisibility() == View.VISIBLE || mPlayerProgressLayout.getVisibility() == View.VISIBLE) {
			mVideoPlayerHander.removeMessages(DISMISS_PLAYER_CONTROL_BUTTONS);
			mVideoPlayerHander.sendEmptyMessageDelayed(DISMISS_PLAYER_CONTROL_BUTTONS, Utils.MENU_TIMEOUT);// ３秒后消失；
		}
		

		return super.dispatchKeyEvent(event);
	}

	/*
	 * 从应用传递的Intent中解析出统一的播放列表；
	 * 
	 * @如果列表为空，返回false，否则返回true;
	 */

	private boolean getPlayList(Intent intent) {
		Log.v(TAG, "getPlayList intent = " + intent);
		if (intent.getAction().equals(Intent.ACTION_VIEW)) {
			isNeedSave = intent.getBooleanExtra("isNeedSave", false);
//			m3DModeSet = intent.getIntExtra("3dmode", 0);
			is3DEnabled = new CrossPlatFormAnalyzer().isNeed3DFunction(this);
			Utils.printLog(TAG,
					" new CrossPlatFormAnalyzer().isNeed3DFunction(); is3DEnabled ="
							+ is3DEnabled);
			// 判断平台是否支持3D
//			TDeviceInfo devinfo = TDeviceInfo.getInstance();
//			int is3dState = devinfo.get3DStatus(devinfo.getProjectID());
//			if (is3dState == 1) {
//				is3DEnabled = true;
			if (application != null) {
				application.setIs3DEnabled(is3DEnabled);
			}
//			} else if (is3dState == 0) {
//				is3DEnabled = false;
//				if (application != null) {
//					application.setIs3DEnabled(false);
//				}
//			}
			Utils.printLog(TAG, "devinfo.get3DStatus; is3DEnabled =" + is3DEnabled);

			if (intent.getIntExtra("playApp", 0) == 1) {
				isDMR = true;
				if (application != null) {
					application.setDMR(true);
				}
			} else if (intent.getIntExtra("playApp", 0) == 2) {
				isDLNA = true;
				if (application != null) {
					application.setDLNA(true);
				}
			} else {
				isDMR = false;
				isDLNA = false;
				if (application != null) {
					application.setDLNA(false);
					application.setDMR(false);
				}
			}
			if (intent.getBooleanExtra("IsMediaBean", false)) {
	
				mList = intent.getParcelableArrayListExtra("playlist");
				if (mList == null || mList.size()==0)
					return false;
				
				mCurrIndex = intent.getIntExtra("index", 0);
				Utils.printLog(TAG, "getPlayList IsMediaBean = true mCurrIndex="+mCurrIndex +"mlist size is "+ mList.size());
			    if(mCurrIndex >= mList.size()){
			    	Utils.printLog(TAG,"now index is invalid");
			    	return false;
			    }else{

				    MediaBean mediaBean = mList.get(mCurrIndex);
					if(mediaBean.mPath==null){
						Log.d(TAG, "fatal exception mediaBean.mPath="+mediaBean.mPath);
					}
					if(mediaBean.mName == null || mediaBean.mName.equalsIgnoreCase("") || mediaBean.mName.equals("unkown")){
						mList.get(mCurrIndex).mName = Utils.getRealName(mediaBean.mPath);
					}
			    }
				
			} else {
				mList = new ArrayList<MediaBean>();
				if (intent.getType().equals(
						"application/vnd.tcl.playlist-video")) {
					Utils.printLog(TAG,
							"getPlayList application/vnd.tcl.playlist-video");
					List<Uri> urilist = intent
							.getParcelableArrayListExtra("playlist");
					if (urilist == null) {
						// report.sendReport(0x03040002, "play list is null",
						// ErrorReport.LOGCAT,null);
						return false;
					}
					for (Uri u : urilist) {
						String path = Utils.getUriPath(u);
						MediaBean bean = new MediaBean();
						bean.mPath = path;
						if (path.startsWith("http")) {
							try {
								path = URLDecoder.decode(path, "utf-8");
							} catch (UnsupportedEncodingException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							Utils.printLog(TAG, "URLDecoder path =" + path);
							String name = intent.getStringExtra("name");
							bean.mName = name;
							if (bean.mName == null
									|| bean.mName.equalsIgnoreCase("")) {
								Log.v(TAG,
										"getPlayList  application/vnd.tcl.playlist-video  bean.mName = null");
								bean.mName = Utils.getRealName(path);
							}
							// bean.mName = Utils.getRealName(path);

						} else {
							bean.mName = Utils.getRealName(path);
						}

						mList.add(bean);
					}
					mCurrIndex = intent.getIntExtra("index", 0);
					Utils.printLog(TAG, "getPlayList application  mCurrIndex = " +mCurrIndex);
				} else if (intent.getType().contains("video/")) {
					Uri data = intent.getData();
					if (data == null)
						return false;
					String path = Utils.getUriPath(data);
					MediaBean bean = new MediaBean();
					bean.mPath = path;
					if (path.startsWith("http")) {
						try {
							path = URLDecoder.decode(path, "utf-8");
						} catch (UnsupportedEncodingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						Utils.printLog(TAG, "URLDecoder path =" + path);
						bean.mName = Utils.getRealName(path);
					} else {
						bean.mName = Utils.getRealName(path);
					}

					mList.add(bean);
					mCurrIndex = 0;
				}
			}
			Log.v(TAG, "getPlayList mList.size() = " + mList.size());
			if (mList.size() > 0) {

				MediaBean bean = mList.get(0);
				if (bean != null) {
					if (bean.mPath.startsWith("http")) {
						isFromWeb = true;
						if (application != null) {
							application.setFromWeb(true);
						}
					} else {
						if (application != null) {
							application.setFromWeb(false);
						}
					}
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

    OnAudioFocusChangeListener mAudiomanagerlistener = new OnAudioFocusChangeListener() {
		
		@Override
		public void onAudioFocusChange(int arg0) {
			
			
		}
	};
	@Override
	protected void onResume() {
		super.onResume();
		Utils.printLog(TAG, "1 onResume start");

		Utils.printLog(TAG, "onResume mIsInternetDisconnect =" + mIsInternetDisconnect);
		isDMRstop = false;
		if (mIsInternetDisconnect) {
			// 如果是网络是网络断开，又连接而触发的onResume(),不做任何处理
			mIsInternetDisconnect = false;
			return;
		}
		
		try {
			int m = mAmanager.requestAudioFocus(mAudiomanagerlistener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
		    Log.d(TAG,"now request audio focus result is "+m);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Utils.printLog(TAG, "onResume mIsSaveClicked =" + mIsSaveClicked);
		if (up_guide_ly.getVisibility() == View.INVISIBLE && mClickedBtStatus == VideoPlayerUIConst.CLICKED_ADVANCE) {

			up_guide_ly.setVisibility(View.VISIBLE);
			mPlayerProgressLayout.setVisibility(View.VISIBLE);
			up_guide_ly.startAnimation(menuInAnim);
			mPlayerProgressLayout.startAnimation(controllerLyAnim);

			mVideoPlayerHander.removeMessages(DISMISS_PLAYER_CONTROL_BUTTONS);
			mVideoPlayerHander.sendEmptyMessageDelayed(DISMISS_PLAYER_CONTROL_BUTTONS, Utils.MENU_TIMEOUT);// ３秒后消失；
		}
		if (isOnNewIntent) {
			Log.d(TAG,"now isSourceChanged is "+isSourceChanged +"==now ischangeSourcePlayed is "+ischangeSourcePlayed);
			if(isSourceChanged){
				mMediaHanler.sendEmptyMessage(START_PLAY);
				Utils.printLog(TAG, "isOnNewIntent PLAY_ONCE");	
			}
			isOnNewIntent = false;
		}

		if(null != wakeLock){
			wakeLock.acquire();
			Log.v(TAG, "onCreate wakeLock.acquire() wakeLock = "+wakeLock);
		}
		// 在线播放，需重新初始化播放信�?
		Utils.printLog(TAG, "2 onResume end");
	}

	@Override
	protected void onPause() {
		super.onPause();
		Utils.printLog(TAG, "1 onPause start");
		if (mIsInternetDisconnect || mIsSaveClicked || m3DBtStatus == VideoPlayerUIConst.SHOW_MENU_3D || m3DBtStatus == VideoPlayerUIConst.SHOW_VOLUME_3D
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
		Utils.printLog(TAG, "4 onPause end ");
	}

	/*
	 * 保存视频播放的类型到本地�?
	 */
	private void saveVideoPlayType() {
		if (mVideoContrl == null) {
			return;
		}
		SharedPreferences settings = getSharedPreferences(VideoPlayerUIConst.MY_SPREFS_FILE, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putInt(VideoPlayerUIConst.VEDIO_PLAY_TYPE, mVideoContrl.getPlayType());
		editor.commit();
	}

	protected void onStop() {
		Utils.printLog(TAG, "1 onStop start");		
		
		if (isFinishing() == false) {
			exitVideoPlay();
			VideoPlayerActivity.this.finish();
		}
		if(mTTvmanager != null){
			mTTvmanager.removeHandler(setSourceHandler,TTvUtils.TV_HANDLER_INDEX_TV_SET_SOURCE);
		}
		try {
			if (mConnectionChangeReceiver != null) {
				unregisterReceiver(mConnectionChangeReceiver);
			}
			if (myCollectionBroadcastReceiver != null) {
				unregisterReceiver(myCollectionBroadcastReceiver);
			}
			if (mVoiceReceiver != null) {
				unregisterReceiver(mVoiceReceiver);
			}
			if (mVideoContrl != null) {
				mVideoContrl.unRegisterReceiver();
			}

		} catch (Exception il) {

			il.printStackTrace();
		}
		if (nSreenConnection != null && isDMR && isBind) {
			Utils.printLog(TAG, " unbindService  nSreenConnection");
			if (nSreenTVService != null) {
				try {
					nSreenTVService.unregisterPlayerCallback();
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				nSreenTVService = null;
			}
			try {
				unbindService(nSreenConnection);
				nSreenConnection = null;
			} catch (Exception e) {
				e.printStackTrace();
				Utils.printLog(TAG, " fatal error unbindService  nSreenConnection Exception");
			}
			isBind = false;
		}
		mVideoPlayerHander.sendEmptyMessage(DISSMISS_WAIT_DIALOG);
		super.onStop();
		Log.d(TAG, "2 onStop end");
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		Log.d(TAG, "1 onStart start");
		super.onStart();
		if(isDMR){
			bindTVnSreenService();
		}
		// mMediaHanler.sendEmptyMessage(START_PLAY);
		Log.d(TAG, "2 onStart end");

	}

	private void start_3Dmode() {
		Utils.printLog(TAG, "start3Dmode is3DEnabled="+is3DEnabled);
		if (!is3DEnabled) {
			Utils.printLog(TAG, "start_3Dmode is3DEnabled =false");
			return;
		}
		Intent intent = new Intent("com.android.tcl.videostart");
		Utils.printLog(TAG, "is3DEnabled =" + is3DEnabled);
		intent.putExtra("3dmode", is3DEnabled);
		if (mList != null) {
			if (mList.size() > 0 && mCurrIndex < mList.size() && mCurrIndex >= 0) {

				try {
					String mCurrPath = mList.get(mCurrIndex).mPath;
					Utils.printLog(TAG, "mTheLast3DPath =" + mTheLast3DPath + "; mCurrPath =" + mCurrPath);
					if (mTheLast3DPath != null && mTheLast3DPath.equalsIgnoreCase(mCurrPath)) {
						intent.putExtra("SameFile", true);
						Utils.printLog(TAG, "SameFile true ");
					}
					mTheLast3DPath = mCurrPath;
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}

			}
		}
		VideoPlayerActivity.this.sendBroadcast(intent);

		Utils.printLog(TAG, "3dmode =" + is3DEnabled+" sendBroadcast  com.android.tcl.videostart ");
	}

	private void exit_3Dmode() {
		Utils.printLog(TAG, "exit3Dmode");
		if (!is3DEnabled) {
			Utils.printLog(TAG, "exit3Dmode is3DEnabled =false");
			return;
		}
		VideoPlayerActivity.this.sendBroadcast(new Intent("com.android.tcl.videoexit"));
		Utils.printLog(TAG, "sendBroadcast  com.android.tcl.videoexit ");
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		Utils.printLog(TAG, "1 onDestroy start");

		mTimerTask.cancel();
		mTime.cancel();
//		stopPlayEventTimer();
		if (mList != null) {
			mList.clear();
			mList = null;
		}

		if (holder != null) {
			holder = null;
		}
//		mSurfaceView = null;

		// mediaLooperThread.exitLooperThrad();
		dismissWaittingDialog();

		if (mBookMark != null) {
			mBookMark.close();
		}

		if (mMediaHanler != null) {
			Utils.removeHandlerMsg(mMediaHanler);
		}
//		System.exit(0);
		Utils.printLog(TAG, "4 onDestroy end");
		// Utils.killMyProcess(this);
	}

	@Override
	public void doBookMarkAction() {
	if(!isBookMark){
		if (mVideoContrl != null && mVideoContrl.isMediaPlayerPrepared()) {
			mTheLastPosition = mVideoContrl.getCurrentPosition();
			sEndTime = mVideoContrl.getDuration();
			Utils.printLog(TAG, "mTheLastPosition " + mTheLastPosition + "  sEndTime =" + sEndTime);
			Utils.printLog("lyf", "mTheLastPosition " + mTheLastPosition + "  sEndTime =" + sEndTime);
		} else {
			mTheLastPosition = VideoPlayerContrlConsts.GET_CURRENTPOSITION_ERROR;
		}
		Utils.printLog(TAG, "doBookMarkAction  ============ pos =" + mTheLastPosition);
		if (mTheLastPath == null || (mTheLastPosition == VideoPlayerContrlConsts.GET_CURRENTPOSITION_ERROR)
				|| mTheLastPosition == sEndTime){
			if(mTheLastPath != null){         //add here for finish player do book mark position error -565 WJ 20160705
				if (mBookMark.isUrlInBookmark(mTheLastPath)) {
					mBookMark.delete(mTheLastPath);
					Log.v(TAG, "bookmark delete " + mTheLastPath);
				}
			}
			return;
		}
		if (mTheLastPosition < 0 || mTheLastPosition > 14400000) {
			Log.d(TAG,"now bookmark slow here 1" + mTheLastPosition);
			if (mBookMark.isUrlInBookmark(mTheLastPath)) {
				mBookMark.delete(mTheLastPath);
				// CustomToast.showTCLCustomToast(showMovieDetail.this, name +
				// " 从书签删�?, CustomToast.LENGTH_SHORT,
				// CustomToast.WARNNING_ICON);
				Log.v(TAG, "bookmark delete " + mTheLastPath);
			}
		} else {
			Log.d(TAG,"now bookmark slow here 2" + mTheLastPosition);
			if (mBookMark.isUrlInBookmark(mTheLastPath)) {

				mBookMark.delete(mTheLastPath);
				// CustomToast.showTCLCustomToast(showMovieDetail.this, name +
				// " 更新书签成功", CustomToast.LENGTH_SHORT,
				// CustomToast.WARNNING_ICON);
				Log.v(TAG, "bookmark delete " + mTheLastPath + " at " + mTheLastPosition);
			}
			mBookMark.refreshListBeforInsert();
			mBookMark.insert(mTheLastName, mTheLastPath, mTheLastPosition, sEndTime);
			Utils.printLog(TAG, "mTheLastPosition =" + mTheLastPosition + "sEndTime" + sEndTime);
			// CustomToast.showTCLCustomToast(showMovieDetail.this, name +
			// " 成功加入书签�?, CustomToast.LENGTH_SHORT,
			// CustomToast.WARNNING_ICON);
			Log.v(TAG, "bookmark insert " + mTheLastPath + " at " + mTheLastPosition);

		 }
		
		}
	isBookMark = true;
	}

	@Override
	public void onBackPressed() {
		Utils.printLog(TAG, "onBackPressed() Back Pressed!");
		if (volumLay.getVisibility() == View.VISIBLE) {
			mVideoPlayerHander.sendEmptyMessage(DISSMISS_VOLUME);
			if (mClickedBtStatus == VideoPlayerUIConst.CLICKED_VOLUME) {
				// mIsVolumeClick = false;
				mVideoPlayerHander.sendEmptyMessage(DISPLAY_PLAYER_CONTROL_BUTTONS);
			}
		} else if (up_guide_ly.getVisibility() == View.VISIBLE || mPlayerProgressLayout.getVisibility() == View.VISIBLE) {
			mVideoPlayerHander.sendEmptyMessage(DISMISS_PLAYER_CONTROL_BUTTONS);
		} else {
			Utils.printLog(TAG, "exitVideoPlay");
			exitVideoPlay();
			// mPlayerProgressLayout.setVisibility(View.INVISIBLE);
		}
		// if (mPlayerControlLayout.getVisibility() == View.INVISIBLE) {
		// Utils.printLog(TAG, " onBackPressed");
		// finish();
		// } else {

		// mPlayerControlLayout.setVisibility(View.INVISIBLE);
		// mPlayerProgressLayout.setVisibility(View.INVISIBLE);
		// }
	}

	private void exitVideoPlay() {
		userStateRecord();
		playStatus = STOPPED;
		sendPlayEventBroadCast(mCurrIndex,playStatus);
		dmrSetPlayStatus(playStatus);
//		stopPlayEventTimer();
		// Tv3DManager tv3DManager = Tv3DManager.getInstance(this);
		// tv3DManager.reset3DMode();
		/*
		 * ThreeDVideoDisplayFormat setToMod =
		 * ThreeDVideoDisplayFormat.valueOf(ThreeDVideoDisplayFormat
		 * .E_ThreeD_Video_DISPLAYFORMAT_NONE.toString());
		 * 
		 * Tv3DManager tv3DManager = Tv3DManager.getInstance(this);
		 * tv3DManager.setDisplayFormat(setToMod);
		 */
//		if (mVideoContrl != null && mVideoContrl.isMediaPlayerPrepared()) {
//			mTheLastPosition = mVideoContrl.getCurrentPosition();
//			sEndTime = mVideoContrl.getDuration();
//			Utils.printLog(TAG, "mTheLastPosition " + mTheLastPosition + "  sEndTime =" + sEndTime);
//		} else {
//			mTheLastPosition = VideoPlayerContrlConsts.GET_CURRENTPOSITION_ERROR;
//		}
		Utils.printLog(TAG, "exitVideoPlay()");
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub

				if (mVideoContrl != null) {
					// mVideoContrl.releaseMediaPlayer();
					mVideoContrl.closeMediaControl();
				}

			}
		}).start();

		if (mTheLastPath != null) {
			doBookMarkAction();
		}
		exit_3Dmode();
		notifyPlayerActivityCallBack();
		saveVideoPlayType();
		sendActivityResu();
		dismissPopWindow();
		if(listDialog.isShowing()){
			listDialog.dismiss();
			listDialog = null;
		}
		if(menuDialog.isShowing()){
			menuDialog.dismiss();
			menuDialog = null;
		}
		// try {
		// if (mPictureManager != null ) {
		// Long time = System.currentTimeMillis();
		// Utils.writeCurrentTime(time);
		// myGraphics.resumeVideoBackLight( getSavedBackLight(this),
		// getBackLight(), mPictureManager,time);
		// }
		// if (getBooleanISNatrueLightON(this)) {
		// TvManager.setTvosInterfaceCommand("DBC_OFF");
		// }
		//
		// } catch (TvCommonException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		if(menuDialog!=null && menuDialog.isShowing()){
			Log.d(TAG, "exitVideoPlay menuDialog.dismiss()");
			menuDialog.dismiss();
		}
		VideoPlayerActivity.this.finish();

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		Utils.printLog(TAG, "onTouchEvent");

		if (event.getAction() == MotionEvent.ACTION_DOWN && mVideoContrl.isMediaPlayerPrepared()) {
			float clickPosition = event.getY();
			Utils.printLog(TAG, "onTouchEvent Y =" + clickPosition);
			int mClickBtnPos = 0;
			if (Utils.isWindow1080(VideoPlayerActivity.this)) {
				mClickBtnPos = 860;
			} else {
				mClickBtnPos = 560;
			}
			if (clickPosition >= mClickBtnPos
			/* && mPlayerControlLayout.getVisibility() == View.INVISIBLE */) {
				mVideoPlayerHander.sendEmptyMessage(DISPLAY_PLAYER_CONTROL_BUTTONS);
			} else {
				mVideoPlayerHander.sendEmptyMessage(VIDEO_PLAY_START_OR_PAUSE);
			}

		}

		return super.onTouchEvent(event);
	}

	private void findView() {

		mSubtitleSurfaceView = (SurfaceView) findViewById(R.id.video_subtitle_surface);
		mSubtitleSurfaceView.getHolder().setFormat(PixelFormat.RGBA_8888);
		mSubtitleSurfaceView.setBackgroundColor(Color.TRANSPARENT);
		mSubtitleSurfaceView.setVisibility(View.VISIBLE);
		mSubtitleSurfaceView.getHolder().addCallback(mSubtiteHolder);

		mPauseIcon = (LinearLayout) findViewById(R.id.player_pause_icon_video);
		mPauseTimeTextView = (TextView) findViewById(R.id.player_pause_time);
		mPlayerProgressLayout = findViewById(R.id.player_progress_layout);
		mPlayerProgressLayout.setVisibility(View.INVISIBLE);
		mPlayEndTime = (TextView) findViewById(R.id.player_end_time);
		mPlayBeginTime = (TextView) findViewById(R.id.player_begin_time);
		mSubtitleTextView = (TextView) findViewById(R.id.video_subtitle_text);
		mDtsLayout = (LinearLayout) findViewById(R.id.dts_view);
		mDtsImageView = (ImageView) findViewById(R.id.dts_icon);
		mDtsTextView=(TextView) findViewById(R.id.dts_text);
		mDtsLayout_vision = (LinearLayout) findViewById(R.id.dts_view_vision);
		mDtsImageView_vision = (ImageView) findViewById(R.id.dts_icon_vision);
		mDtsTextView_vision=(TextView) findViewById(R.id.dts_text_vision);
		initVolumeInfo();
		mPlayerSeekar = (TCLSeekBar) findViewById(R.id.player_seekbar);
		mPlayerSeekar.setOnSeekBarChangeListener(mSeekBarListener);
		mediaName = (TypefaceTextViewScrolling) findViewById(R.id.medianame);
		mKeydown = (TypefaceTextViewScrolling)findViewById(R.id.keydowntext);
		mKeymenu = (TypefaceTextViewScrolling)findViewById(R.id.keymenutext);

		menuInAnim = AnimationUtils.loadAnimation(this, R.anim.title_tip_in);
		menuOutAnim = AnimationUtils.loadAnimation(this, R.anim.title_tip_out);

		up_guide_ly = (FrameLayout) findViewById(R.id.tip_upper_ly);
		controllerLyAnim = AnimationUtils.loadAnimation(this, R.anim.slide_in_vertical_controllerly);
		controllerLyOutAnim = AnimationUtils.loadAnimation(this, R.anim.slide_out_vertical_controllerly);
		pauseIconInAnim = AnimationUtils.loadAnimation(this, R.anim.pause_icon_in);
		pauseIconOutAnim = AnimationUtils.loadAnimation(this, R.anim.pause_icon_out);

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
		public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
			// TODO Auto-generated method stub
			Utils.printLog(TAG, "SeekBarListener onProgressChanged progress =" + progress + "   fromUser =" + fromUser);
			if (fromUser) {

				if (sEndTime > 0) {
					final long time = progress * (long) sEndTime / Utils.SeekBarLength;
					Utils.printLog(TAG, "SeekBarListener mseekbariwidth=" + mPlayerSeekar.getWidth() + " onProgressChanged sEndTime =" + sEndTime + "   time =" + time);
					new Thread(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub
							mVideoContrl.seekTo((int) time);

						}
					}).start();
					long xoff = (mPlayerSeekar.getWidth() - Utils.SeekBarThumbLength) * (long) progress / Utils.SeekBarLength;
					seekBarPopWindow.showText(mPlayerSeekar, Utils.getTimeShort((int) time), (int) xoff, -22);
				} else {
					mPlayerSeekar.setProgress(0);
				}

			}
		}
	};



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
		if(mSurfaceView == null){
			findView();
		}
		Utils.printLog(TAG, "playerUIInit start");
		if (mList == null || mList.size() <= 0 || mCurrIndex >= mList.size()) {
			return;
		}
		// mVideoPlayerHander.sendEmptyMessage(SHOW_WAIT_DIALOG);
		mSurfaceView.setBackgroundColor(getResources().getColor(R.color.bg));
		
		//showWatingDialog(isSimple);
		mVideoPlayerHander.sendEmptyMessageDelayed(SHOW_WAIT_DIALOG,2000);//如果视频很快播放，则不弹出加载提示框
		mPlayerSeekar.setEnabled(true);
//		mVideoPlayerHander.removeMessages(DISMISS_DIALOG_FOR_TIMEOUT);//
		// 保证对应之前视频的超时消息被移除�?
		// mVideoPlayerHander.sendEmptyMessageDelayed(DISMISS_DIALOG_FOR_TIMEOUT,
		// VideoPlayerUIConst.INTENET_TIMEOUT);// 重新发送超时消息；
		// Message mss = mVideoPlayerHander.obtainMessage();
		// mss.getData().putInt("currentPos", 0);
		// mss.what = REFRESH_CURRENT_TIME;
		// mVideoPlayerHander.sendMessage(mss);
		if (seekBarPopWindow != null) {
			seekBarPopWindow.dismiss();
		}
		mDtsLayout.setVisibility(View.GONE);
		mDtsLayout_vision.setVisibility(View.GONE);
		try {
			MediaBean mediaBean = mList.get(mCurrIndex);
			// mPlayerStartButton
			// .setBackgroundResource(R.drawable.player_pause_selector);
			mPauseIcon.setVisibility(View.INVISIBLE);
			mSubtitleTextView.setText("");
			updateControlButtonStatus();
			Log.d(TAG, "isNeedSave="+isNeedSave);
			if (!isNeedSave) {
//				 mPlayerSaveButton.setEnabled(false);
//				 mPlayerSaveButton.setFocusable(false);
//				 mPlayerSaveButton.setVisibility(View.GONE);
			} else {
				// 如果在线播放，但是网络不可用，则会退出播放；

				// mPlayerSaveButton.setVisibility(View.VISIBLE);
				if (!Utils.isNetWorkUsable(VideoPlayerActivity.this)) {
					mToast = new MyToast(VideoPlayerActivity.this, getResources().getString(R.string.media_player_netUnausable));
					mToast.show();
					mVideoPlayerHander.sendEmptyMessage(EXIT_VIDEOPLAY);
					return;
				}

				// 如果已经收藏显示取消收藏icon
				// if (mediaBean.mIsSaved == 1) {
				// mPlayerSaveButton
				// .setBackgroundResource(R.drawable.player_cancel_save_selector);
				// } else {
				// mPlayerSaveButton
				// .setBackgroundResource(R.drawable.player_save_selector);
				// }
			}

			final String name = mediaBean.mName;
			// mName.setText(name);
			mediaName.setText(name);
			mPlayBeginTime.setText(Utils.getTimeShort(0));
			sEndTime = 0;
			mPlayEndTime.setText(Utils.getTimeShort(0));

			mPlayerSeekar.setProgress(0);
			mPlayerSeekar.setEnabled(false);
			if (mVideoContrl.isMediaPlayerPrepared()) {
				sEndTime = mVideoContrl.getDuration();
				Utils.printLog(TAG, "-sEndTime=" + sEndTime + ";timesort =" + Utils.getTimeShort(sEndTime));
				if (sEndTime > 0) {
					Utils.printLog(TAG, "get mPlayEndTime =" + Utils.getTimeShort(sEndTime));
					mPlayEndTime.setText(Utils.getTimeShort(sEndTime));
				}
			}

			mTheLastName = name;
			mTheLastPath = mediaBean.mPath;
		} catch (Exception e) {
			e.printStackTrace();
		}
		// mVideoPlayerHander.sendEmptyMessage(DISPLAY_PLAYER_PROGRESS_LAYOUT);

		// exit3Dmode();
        isBookMark = false;
		Utils.printLog(TAG, "playerUIInit end");
	}

	private void dismissWaittingDialog() {
		Utils.printLog(TAG, "2 dismissWaittingDialog");
		if(mVideoPlayerHander!=null){
			mVideoPlayerHander.removeMessages(SHOW_WAIT_DIALOG);
		}
		if (mDialog != null) {
			try {
				Utils.printLog(TAG, "3 mDialog.dismiss()  true");//lyf
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
			mDialog = new CustomProgressDialog(VideoPlayerActivity.this);
			mDialog.setMessage(this.getResources().getString(R.string.dialog_load));
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
			Utils.printLog(TAG, "myRunnable refresh currentPostion ="+currentPostion+" ;getPlayingVideoIndex="+mVideoContrl.getPlayingVideoIndex());
			currentPostion = mVideoContrl.getCurrentPosition();
			if (sEndTime <= 0) {
				duration = mVideoContrl.getDuration();
				sEndTime = duration;
				mVideoPlayerHander.sendEmptyMessage(REFRESH_TOTAL_TIME);
			}
			duration = mVideoContrl.getDuration();
			Utils.printLog(TAG, "myRunnable  sEndTime=" + sEndTime+"duration=" + duration);
			//Utils.printLog(TAG, "myRunnable reference duration=" + duration);
			if (currentPostion != VideoPlayerContrlConsts.GET_CURRENTPOSITION_ERROR) {
				Message mss = mVideoPlayerHander.obtainMessage();
				mss.getData().putInt("currentPos", currentPostion);
				mss.what = REFRESH_CURRENT_TIME;
				mVideoPlayerHander.sendMessage(mss);

			}
			//add here for seek >5m/30s  >2m/10s  else 5s/t  WJ 20160707
			if (sEndTime > 0) {
				if(sEndTime > 300000){
					mPlayerSeekar.setKeyProgressIncrement(Utils.SeekBarLength * 30000 / sEndTime);	
				}else if(sEndTime > 120000){
					mPlayerSeekar.setKeyProgressIncrement(Utils.SeekBarLength * 10000 / sEndTime);
				}else {
					mPlayerSeekar.setKeyProgressIncrement(Utils.SeekBarLength * 5000 / sEndTime);
				}
				
			} else {
				mPlayerSeekar.setKeyProgressIncrement(0);
			}

		}
	};

	private TimerTask mTimerTask = new TimerTask() {
		public void run() {
			if (mPlayerProgressLayout.getVisibility() == View.VISIBLE && mVideoContrl.isMediaPlayerPrepared() && !mIsSeeking && isBreakDialogDissmiss) {

				runOnUiThread(myRunnable);
				// new Thread(myRunnable).start();
			}
		}
	};

	// private class ButtonOnfocusListener implements View.OnFocusChangeListener
	// {
	//
	// @Override
	// public void onFocusChange(View v, boolean hasFocus) {
	// // TODO Auto-generated method stub
	// if (mPlayerControlLayout == null
	// || mPlayerControlLayout.getVisibility() == View.INVISIBLE) {
	// return;
	// }
	// if (hasFocus) {
	// int stringId = 0;
	// switch (v.getId()) {
	// case R.id.player_turn3d:
	// stringId = R.string.turnThree;
	// break;
	// case R.id.player_volume:
	// stringId = R.string.volumChange;
	// break;
	// case R.id.player_stop:
	// stringId = R.string.Video_Info_return;
	// break;
	//
	// case R.id.player_save:
	// if (mList != null && mList.size() > 0) {
	// if (mList.get(mCurrIndex).mIsSaved == 1) {
	// stringId = R.string.Video_Info_Cancel_favorite;
	// } else {
	// stringId = R.string.Video_Info_Favorite;
	// }
	// } else
	// stringId = R.string.Video_Info_Favorite;
	//
	// break;
	//
	// case R.id.player_start:
	// if (mVideoContrl != null
	// && mVideoContrl.isMediaPlayerPrepared()
	// & mVideoContrl.isPlaying()) {
	// stringId = R.string.Video_Info_Pause;
	// } else {
	// stringId = R.string.Video_Info_Play;
	// }
	//
	// break;
	// case R.id.player_next:
	//
	// stringId = R.string.Video_Info_Next_Film;
	//
	// break;
	// case R.id.player_previous:
	//
	// stringId = R.string.Video_Info_Last_Film;
	//
	// break;
	//
	// case R.id.player_left_fast:
	// stringId = R.string.Video_Info_Rewind_30S;
	//
	// break;
	//
	// case R.id.player_right_fast:
	// stringId = R.string.Video_Info_FastForward_30S;
	// break;
	//
	// case R.id.player_sequence:
	// stringId = mPlayerTypeText[mVideoContrl.getPlayType()];
	// break;
	// case R.id.player_advance:
	// stringId = R.string.AdvancedSetTip;
	// break;
	// case R.id.player_setimage:
	// stringId = R.string.image;
	// break;
	// }
	//
	// if (stringId != 0 && popWindow != null)
	// showPopWindow(v, stringId);
	// } else {
	// dismissPopWindow();
	// }
	// }
	//
	// }

	private void showPopWindow(View view, int string) {
		// popWindow.dismiss();
		// if (mPlayerControlLayout.getVisibility() == View.VISIBLE)
		// try {
		// popWindow.showText(view, string);
		// } catch (Exception ex) {
		// ex.printStackTrace();
		// }
	}

	private void dismissShowText() {
		// mPlayerStartText.setVisibility(View.INVISIBLE);
		// mPlayerPreviousText.setVisibility(View.INVISIBLE);
		// mPlayerNextText.setVisibility(View.INVISIBLE);
		// mPlayerSetImageText.setVisibility(View.INVISIBLE);
		// mPlayerAdvanceText.setVisibility(View.INVISIBLE);
		// mPlayerTurn3DText.setVisibility(View.INVISIBLE);
		// mPlayerInfoText.setVisibility(View.INVISIBLE);
		// mPlayShareText.setVisibility(View.INVISIBLE);
		// mPlayerSquenceText.setVisibility(View.INVISIBLE);
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
		
		if(clienttype.contains("CN")||clienttype.contains("HK")){
			
		//if (splitArrayStrings[1] == null || splitArrayStrings[1].equals("CN") || splitArrayStrings[1].equals("HK")) { // now
																														// is
																														// CN/
																														// add
																														// HK机型for
																														// 828hk
			try { // 20141016 modify for 3D

				Log.d("===========20141016", "now to start 3D setting use new method");
				Intent intent = new Intent();
				intent.setAction("com.tcl.ShortcutKey");
				intent.putExtra("Type", "3D");
				sendBroadcast(intent);
			} catch (ActivityNotFoundException acti) {
				acti.printStackTrace();
				MyToast.showNoFuntionToast(this);
			}
		} else { // now is AU TCL-AU-NT667K-S1
			Intent intent = new Intent();
			intent.setAction("android.intent.action.tcl.show3dmenu");
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			intent.putExtra("start3DType", 1);

			// ArrayList
			Bundle mB = new Bundle();
			mB.putParcelableArrayList("playlist", mList);
			intent.putExtras(mB);

			try {
				VideoPlayerActivity.this.startActivity(intent);
			} catch (ActivityNotFoundException acti) {
				acti.printStackTrace();
				new MyToast(VideoPlayerActivity.this, VideoPlayerActivity.this.getResources().getString(R.string.NoFuntion)).show();
			}
		}

	}


	private String getFileByte(String path) {
		long fileS = 0;
		String fileSizeString = "";
		File f = new File(path);
		try {
			// FileInputStream fis = new FileInputStream(f);
			fileS = f.length();
			Utils.printLog(TAG, "file length is " + fileS);
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

//	private boolean doSave() {
//
//		Intent intent = new Intent(VideoPlayerUIConst.COLLECTION_OPERATE);
//		boolean res = true;
//		if (mList != null && mList.size() > 0) {
//			if (mList.get(mCurrIndex).mIsSaved == 0) {
//				mIsSaveClicked = true;
//				intent.putExtra(VideoPlayerUIConst.ADD_OR_DEL, VideoPlayerUIConst.ADD_COLLECTION);
//				res = true;
//			} else {
//				intent.putExtra(VideoPlayerUIConst.ADD_OR_DEL, VideoPlayerUIConst.DEL_COLLECTION);
//				res = false;
//			}
//
//			intent.putExtra(VideoPlayerUIConst.COLLECTION_ADDRESS, mList.get(mCurrIndex).mPath);
//			intent.putExtra(VideoPlayerUIConst.COLLECTION_APP_MODE, mList.get(mCurrIndex).mVodType);
//			Utils.printLog(TAG, "Collection Url =" + intent.getStringExtra(VideoPlayerUIConst.COLLECTION_ADDRESS) + "  appmode =" + mList.get(mCurrIndex).mVodType);
//			// mPlayerSaveButton.setEnabled(false);
//		}
//		Log.d(TAG, "player_save  mIsSaveClicked =" + mIsSaveClicked);
//		sendBroadcast(intent);
//		return res;
//	}

	private void playPause() {
		Utils.printLog(TAG, "playPause");
		if (mVideoContrl != null && mVideoContrl.isMediaPlayerPrepared() && mVideoContrl.isPlaying()) {
			Utils.printLog(TAG, "playPause control into");
			mVideoContrl.pause();
			if (!mVideoContrl.isPlaying()) {
				mPauseIcon.setVisibility(View.VISIBLE);
				mPauseIcon.startAnimation(pauseIconInAnim);
				mPauseIcon.requestLayout();
				mPauseIcon.invalidate();
				Utils.printLog(TAG, "mPauseIcon.setVisibility(View.VISIBLE);");

			} else {
				mPauseIcon.setVisibility(View.INVISIBLE);
				mPauseIcon.startAnimation(pauseIconOutAnim);

			}

		}
		playStatus = PAUSED_PLAYBACK;
		dmrSetPlayStatus(playStatus);
		sendPlayEventBroadCast(mCurrIndex,playStatus);
	}

	private void playStart() {
		Utils.printLog(TAG, "playStart");
		if (mVideoContrl != null && mVideoContrl.isMediaPlayerPrepared() && !mVideoContrl.isPlaying()) {
			Utils.printLog(TAG, "playStart control into");
			mVideoContrl.start();
			if (mVideoContrl.isPlaying()) {
				mPauseIcon.setVisibility(View.INVISIBLE);
			} else {
				mPauseIcon.setVisibility(View.VISIBLE);
				mPauseIcon.requestLayout();
				mPauseIcon.invalidate();
				Utils.printLog(TAG, "mPauseIcon.setVisibility(View.VISIBLE);");

			}
		}
		playStatus = PLAYING;
		dmrSetPlayStatus(playStatus);
		sendPlayEventBroadCast(mCurrIndex,playStatus);
	}

//	private void maltiSpeedPlayNext() {
//		if (mVideoContrl != null && mVideoContrl.isMediaPlayerPrepared()) {
//			int currentPostion = mVideoContrl.getCurrentPosition();
//			if (currentPostion != VideoPlayerContrlConsts.GET_CURRENTPOSITION_ERROR) {
//				if ((currentPostion + BACK_OR_PREVIOUS_STEP_SIZE) <= sEndTime) {
//					currentPostion = (currentPostion + BACK_OR_PREVIOUS_STEP_SIZE) > sEndTime ? (sEndTime - 1000) : (currentPostion + BACK_OR_PREVIOUS_STEP_SIZE);
//					mVideoContrl.seekTo(currentPostion);
//					if (Utils.DEBUG)
//						System.out.println("=================================seek to " + currentPostion + "time =" + Utils.getTimeShort(currentPostion) + ";result="
//								+ mVideoContrl.getCurrentPosition());
//				} else {
//					System.out.println("===============================no right to maltiSpeedPlayNext");
//				}
//			}
//
//		}
//	}
//
//	private void maltiSpeedPlayPrevious() {
//		if (mVideoContrl.isMediaPlayerPrepared()) {
//			int currentPostion = mVideoContrl.getCurrentPosition();
//			if (currentPostion != VideoPlayerContrlConsts.GET_CURRENTPOSITION_ERROR) {
//				if (currentPostion >= BACK_OR_PREVIOUS_STEP_SIZE) {
//					currentPostion = (currentPostion - BACK_OR_PREVIOUS_STEP_SIZE) < 0 ? 0 : (currentPostion - BACK_OR_PREVIOUS_STEP_SIZE);
//
//					mVideoContrl.seekTo(currentPostion);
//					if (Utils.DEBUG)
//						System.out.println("=================================seek to " + currentPostion + "time =" + Utils.getTimeShort(currentPostion) + ";result="
//								+ mVideoContrl.getCurrentPosition());
//				} else {
//					System.out.println("===============================no right to maltiSpeedPlayPrevious");
//				}
//			}
//
//		}
//	}

	@Override
	public void userStateRecord() {
		if (userStateTimeStart == -1) {
			userStateTimeStart = System.currentTimeMillis();
		} else {
			long userStateTimeEnd = System.currentTimeMillis();
			long peroid = userStateTimeEnd - userStateTimeStart;
			Utils.printLog(TAG, "userStateRecord for period " + peroid);
			try {
				if (peroid >= 60000 && mTheLastName != null) {// 播放大于一分钟钟?
					Utils.printLog(TAG, "userStateRecord start for " + mTheLastName);
					MediaPlayerBehavior playerBehavior = new MediaPlayerBehavior(VideoPlayerActivity.this.getContentResolver());
					playerBehavior.setStartplayertime(Utils.getTimeAll(userStateTimeStart));
					playerBehavior.setEndplayertime(Utils.getTimeAll(userStateTimeEnd));
					playerBehavior.setPlaycontent(mTheLastName);
					playerBehavior.record();
					Utils.printLog(TAG, "userStateRecord end for " + mTheLastName);
					playerBehavior = null;

				}

				userStateTimeStart = userStateTimeEnd;
				
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}

		}
	}

	public static boolean isRight(KeyEvent event) {
		return ((event.getKeyCode() == 98) || (event.getKeyCode() == 99) || (event.getKeyCode() == 100) || (event.getKeyCode() == 101));
	}

	public static boolean isLeft(KeyEvent event) {
		return ((event.getKeyCode() == 94) || (event.getKeyCode() == 95) || (event.getKeyCode() == 96) || (event.getKeyCode() == 97));
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		Log.d(TAG, "============================keyCode=" + keyCode);
		isVolumeChangedKeyClicked = false;
		if (keyCode == KeyEvent.KEYCODE_MENU) {
			if (up_guide_ly.getVisibility() == View.VISIBLE) {
				up_guide_ly.startAnimation(menuOutAnim);
				mPlayerProgressLayout.startAnimation(controllerLyOutAnim);
				up_guide_ly.setVisibility(View.INVISIBLE);
				mPlayerProgressLayout.setVisibility(View.INVISIBLE);
			}
			if(null != menuDialog && menuDialog.isShowing()|| (mVideoPlayerHander.hasMessages(showFileListMessage))){
				return true;
			}else{
				mVideoPlayerHander.removeMessages(showMenuDialogMessage);
				mVideoPlayerHander.sendEmptyMessageDelayed(showMenuDialogMessage, 1000);
				mClickedBtStatus = VideoPlayerUIConst.CLICKED_VIDEOINFO;	
			}
			
			return true;

		} else if ((event.getKeyCode() == KeyEvent.KEYCODE_ENTER || event.getKeyCode() == KeyEvent.KEYCODE_DPAD_CENTER)) {
			mVideoPlayerHander.sendEmptyMessage(VIDEO_PLAY_START_OR_PAUSE);
			mVideoPlayerHander.sendEmptyMessage(DISPLAY_PLAYER_CONTROL_BUTTONS);
			return true;

		} else if (event.getKeyCode() == KeyEvent.KEYCODE_DPAD_RIGHT) {
			if (mPlayerProgressLayout.getVisibility() == View.VISIBLE) {
				mPlayerSeekar.onKeyDown(KeyEvent.KEYCODE_DPAD_RIGHT, null);
			} else if (mPlayerProgressLayout.getVisibility() != View.VISIBLE) {
				
				if (mVideoContrl != null && mVideoContrl.isMediaPlayerPrepared()) {
					int currentPostion = mVideoContrl.getCurrentPosition();
					if (currentPostion != VideoPlayerContrlConsts.GET_CURRENTPOSITION_ERROR) {
						Message mss = mVideoPlayerHander.obtainMessage();
						mss.getData().putInt("currentPos", currentPostion);
						mss.what = REFRESH_CURRENT_TIME;
						mVideoPlayerHander.sendMessage(mss);

					}
				}
				mPlayerProgressLayout.startAnimation(controllerLyAnim);
				mPlayerProgressLayout.setVisibility(View.VISIBLE);
				
//				mVideoPlayerHander.sendEmptyMessage(REFRESH_CURRENT_TIME);   //20161214 WJ
			}

			return true;
		} else if (event.getKeyCode() == KeyEvent.KEYCODE_DPAD_LEFT) {
			if (mPlayerProgressLayout.getVisibility() == View.VISIBLE) {
				mPlayerSeekar.onKeyDown(KeyEvent.KEYCODE_DPAD_LEFT, null);
			} else if (mPlayerProgressLayout.getVisibility() != View.VISIBLE) {
				mPlayerProgressLayout.startAnimation(controllerLyAnim);
				mPlayerProgressLayout.setVisibility(View.VISIBLE);
			}
			return true;
		} else if (keyCode == 115 && mIsSaveClicked == false && mVideoContrl != null && mVideoContrl.isVOD()) {
			// 遥控器上的收�?
			//doSave();
			return true;
		} else if ((event.getKeyCode() == KeyEvent.KEYCODE_DPAD_RIGHT || (event.getKeyCode() == 804) || (event.getKeyCode() == 805) || (event.getKeyCode() == 806) || (event
				.getKeyCode() == 807))) {

			if (mPlayerSeekar.hasFocus()) {

				mPlayerSeekar.onKeyDown(KeyEvent.KEYCODE_DPAD_RIGHT, null);
			} else {
				//dealLeftRightButon(true);
			}
			return true;
		} else if ((event.getKeyCode() == KeyEvent.KEYCODE_DPAD_LEFT || (event.getKeyCode() == 808) || (event.getKeyCode() == 809) || (event.getKeyCode() == 810) || (event
				.getKeyCode() == 811))) {
			if (mPlayerSeekar.hasFocus()) {
				mPlayerSeekar.onKeyDown(KeyEvent.KEYCODE_DPAD_LEFT, null);
			} else {
				//dealLeftRightButon(false);
			}
			return true;
		} else if (event.getKeyCode() == KeyEvent.KEYCODE_DPAD_UP) {
//			if (mVideoContrl != null && mVideoContrl.isMediaPlayerPrepared()) {
//				mMediaHanler.sendEmptyMessage(PRE_VIDEO);
//			}
//			return true;
		} else if (event.getKeyCode() == KeyEvent.KEYCODE_DPAD_DOWN) {
			if (up_guide_ly.getVisibility() == View.VISIBLE) {
				up_guide_ly.startAnimation(menuOutAnim);
				mPlayerProgressLayout.startAnimation(controllerLyOutAnim);
				up_guide_ly.setVisibility(View.INVISIBLE);
				mPlayerProgressLayout.setVisibility(View.INVISIBLE);
			}
			if(null != listDialog && listDialog.isShowing() || mVideoPlayerHander.hasMessages(showMenuDialogMessage)){
				return true;
			}else{
				mVideoPlayerHander.removeMessages(showFileListMessage);
				mVideoPlayerHander.sendEmptyMessageDelayed(showFileListMessage, 1000);
				mClickedBtStatus = VideoPlayerUIConst.CLICKED_VIDEOINFO;
			}
			return true;
		}

		return super.onKeyDown(keyCode, event);
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		Log.d(TAG, "onKeyUp keyCode ="+keyCode);
		// TODO Auto-generated method stub
		if ((event.getKeyCode() == 206) || (event.getKeyCode() == 803)) {// 3d�?
			if (!is3DEnabled) {
				return true;
			}
			if (!isMediaFile3D) {
				Utils.printLog(TAG, "isMediaFile3D is false");
				return true;
			}
			turn3DMenu();

		} else if (event.getKeyCode() == 4070 || event.getKeyCode() == 88) {
			if (mVideoContrl != null && mVideoContrl.isMediaPlayerPrepared()) {
				mMediaHanler.sendEmptyMessage(PRE_VIDEO);
			}
		} else if (event.getKeyCode() == 4072 || event.getKeyCode() == 87) {
			if (mVideoContrl != null && mVideoContrl.isMediaPlayerPrepared()) {
				mMediaHanler.sendEmptyMessage(NEXT_VIDEO);
			}
		} else if (event.getKeyCode() == 4071 || event.getKeyCode() == 126) {
			if (mVideoContrl != null && mVideoContrl.isMediaPlayerPrepared() && !mVideoContrl.isPlaying()) {
				mVideoPlayerHander.sendEmptyMessage(PLAYER_START);
			}
		} else if (event.getKeyCode() == 127) {
			if (mVideoContrl != null && mVideoContrl.isMediaPlayerPrepared() && mVideoContrl.isPlaying()) {
				mVideoPlayerHander.sendEmptyMessage(PLAYER_PAUSE);
			}
		} else if (event.getKeyCode() == 4075 || event.getKeyCode() == 89) {
			if (mPlayerProgressLayout.getVisibility() == View.INVISIBLE) {
				if (mVideoContrl != null && mVideoContrl.isMediaPlayerPrepared()) {
					int currentPostion = mVideoContrl.getCurrentPosition();
					if (currentPostion != VideoPlayerContrlConsts.GET_CURRENTPOSITION_ERROR) {
						Message mss = mVideoPlayerHander.obtainMessage();
						mss.getData().putInt("currentPos", currentPostion);
						mss.what = REFRESH_CURRENT_TIME;
						mVideoPlayerHander.sendMessage(mss);

					}
				}
				mVideoPlayerHander.sendEmptyMessage(DISPLAY_PLAYER_PROGRESS_LAYOUT);
			} else {
				mPlayerSeekar.onKeyDown(KeyEvent.KEYCODE_DPAD_LEFT, null);

			}

			return true;

		} else if (event.getKeyCode() == 4073 || event.getKeyCode() == 90) {
			if (mPlayerProgressLayout.getVisibility() == View.INVISIBLE) {
				if (mVideoContrl != null && mVideoContrl.isMediaPlayerPrepared()) {
					int currentPostion = mVideoContrl.getCurrentPosition();
					if (currentPostion != VideoPlayerContrlConsts.GET_CURRENTPOSITION_ERROR) {
						Message mss = mVideoPlayerHander.obtainMessage();
						mss.getData().putInt("currentPos", currentPostion);
						mss.what = REFRESH_CURRENT_TIME;
						mVideoPlayerHander.sendMessage(mss);

					}
				}
				mVideoPlayerHander.sendEmptyMessage(DISPLAY_PLAYER_PROGRESS_LAYOUT);
			} else {
				mPlayerSeekar.onKeyDown(KeyEvent.KEYCODE_DPAD_RIGHT, null);
			}

			return true;

		} else if (event.getKeyCode() == 4074 || event.getKeyCode() == 86 || event.getKeyCode() == 4095/*EXIT*/) {
			mVideoPlayerHander.sendEmptyMessage(EXIT_VIDEOPLAY);
		} else if(event.getKeyCode() == 165){  //need show info activity
		  try {  
			  Intent intent_videoinfo = new Intent("android.tcl.videoinfo.notpvr.show");
			  String mTheLastName = mVideoContrl.getPlayingVideoName();
			  mTheLastPath = mVideoContrl.getPlayingVideoPath();
			  String mVideosize = "";
			  mVideosize = getFileByte(mTheLastPath);
			  intent_videoinfo.putExtra("video_name", mTheLastName);
			  intent_videoinfo.putExtra("video_size", mVideosize);
			  
			  this.startActivity(intent_videoinfo);  
			} catch (Exception acti) {
					acti.printStackTrace();						
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

		VideoPlayerActivity.this.sendBroadcast(new Intent("com.tcl.mediaplayer.exit.subtitle"));

	}

	private VideoPlayControlCallback mCallback = new VideoPlayControlCallback() {

		@Override
		public void onVideoPlayBufferingUpdate(int percent) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onVideoPlayChanged(int index, int position) {
			// TODO Auto-generated method stub
			Utils.printLog(TAG, "onVideoPlayChanged with index =" + index + " and position= " + position);
			isShowContrlBtnFirst = true;
			isMediaFile3D = true;
			isChangeNotStart = false;
			playStatus = "TRANSITIONING";
			dmrSetPlayStatus(playStatus);
			mVideoPlayerHander.sendEmptyMessage(-99999);

			if (position != VideoPlayerContrlConsts.GET_CURRENTPOSITION_ERROR) {
				mTheLastPosition = position;
				mVideoPlayerHander.sendEmptyMessage(DO_BOOKMARK_ACTION);
			}

			if (index == VideoPlayerContrlConsts.MEDIA_PALY_FINISHED) {

				mVideoPlayerHander.sendEmptyMessage(EXIT_VIDEOPLAY);
			} else {
				userStateRecord();
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
			mVideoPlayerHander.removeMessages(DISMISS_DIALOG_FOR_TIMEOUT);
			Log.d(TAG,"on error ,then remove time out message");
			mMediaHanler.sendEmptyMessage(errCode);
			// report.sendReport(0x03040003, "onVideoPlayError",
			// ErrorReport.LOGCAT, null);
			if (errCode == CommonConst.media_player_exception || errCode == CommonConst.media_player_unknown_exception || errCode == CommonConst.PLAY_DEVICE_UNMOUTED
					|| errCode == CommonConst.PLAY_LIST_NULL || errCode == CommonConst.media_player_init_error || errCode == CommonConst.DEVICE_SHUTDOWN
					|| errCode == CommonConst.media_player_unknown_exception_38 || errCode == CommonConst.tv_sourcemanager_change
		            ||errCode == CommonConst.FINISH_NO_TIPS
					//   || errCode == CommonConst.unknown_media_format 
		         //   || errCode == CommonConst.media_interrupt 
		         //   || errCode == CommonConst.unknown_video_format
				//	|| errCode == CommonConst.media_player_network_slow
				//	|| errCode ==  CommonConst.media_source_not_found
					) {

				mVideoPlayerHander.sendEmptyMessage(EXIT_VIDEOPLAY);
			}
			
		}

		@Override
		public void onVideoPlayInfoNotify(int infoCode) {
			// TODO Auto-generated method stub
			Utils.printLog(TAG, "onVideoPlayInfoNotify infoCode="+infoCode);
			if(infoCode == CommonConst.GET_DATA_TIMEOUT){  //WJ
				mVideoPlayerHander.removeMessages(DISMISS_DIALOG_FOR_TIMEOUT);
				mVideoPlayerHander.sendEmptyMessageDelayed(DISMISS_DIALOG_FOR_TIMEOUT, 30000);
			    Log.d(TAG,"send time out message 30s later");
			}
			if (infoCode == CommonConst.media_player_buffering) {
				mVideoPlayerHander.sendEmptyMessage(SHOW_WAIT_DIALOG);
				mVideoPlayerHander.removeMessages(DISMISS_DIALOG_FOR_TIMEOUT);
				mVideoPlayerHander.sendEmptyMessageDelayed(DISMISS_DIALOG_FOR_TIMEOUT, 30000);
			    Log.d(TAG,"send time out message 30s later");
			} else if (infoCode == CommonConst.media_player_buffered) {
				mVideoPlayerHander.removeMessages(DISMISS_DIALOG_FOR_TIMEOUT);
				Log.d(TAG,"remove time out message 30s later");
				mVideoPlayerHander.sendEmptyMessage(DISSMISS_WAIT_DIALOG);

			} else if (infoCode == CommonConst.media_player_subtitle_update) {
				String subtitle = mVideoContrl.getCurrentSubtitleText();
				Utils.printLog(TAG, "setsubtiteltext = " + subtitle);
				mSubtitleTextView.setText(subtitle);
			} else if (infoCode == CommonConst.media_player_subtitle_null) {
				mSubtitleTextView.setText("");

			} else if(infoCode == ErrorConsts.media_player_getsubtitleinfo_mtk){
//				int subnum = mVideoContrl.getSubtitleNms();
				Log.d(TAG,"now get mtk subtitle info get");
				
			}

			else if (infoCode == CommonConst.media_player_startplayer_firstframe) {
				mSurfaceView.setBackgroundColor(getResources().getColor(R.color.transparent_background));
				if(clienttype.contains("CN")||clienttype.contains("HK")){
			//	if (splitArrayStrings.length<2 || splitArrayStrings[1] == null || splitArrayStrings[1].equals("CN") || splitArrayStrings[1].equals("HK"))  // now
																																// is
																																// CN
					Log.d(TAG, "now is CN method======");

					setTVToFullScreen(); // add here for 小窗口播放问�?20151027
					// setCNScreenMode();
					// refreshPicMode();
				} else {
					Log.d(TAG, "now is EM method======");
					setTVToFullScreen(); // add here for 小窗口播放问�?20151027
					// setEMScreenMode();
					// refreshPicMode();
				}

				if (mVideoContrl != null && mVideoContrl.isMediaPlayerPrepared() && mTvmanager != null) {
					int dolbyvision = mVideoContrl.isDolbyVision(mTvmanager);
					int isdts = mVideoContrl.isDTS();
					Log.d(TAG, "-------------dolby="+dolbyvision);  //for video
					Log.d(TAG, "-------------isdts="+isdts);        //for audio
					if(dolbyvision == CommonConst.VIDEO_DOBBY_VISION){//2  dobbyvision
						Utils.printLog(TAG, "Current audio is dobby vision");
						mVideoPlayerHander.sendEmptyMessage(REFRESH_DOLBY_PLUS_IMAGE);
					}
					if (isdts == CommonConst.STREAM_DOLBY) {//1 --audio AC3    //open here for 938 96947
						Utils.printLog(TAG, "Current audio is DOLBY");
						mVideoPlayerHander.sendEmptyMessage(REFRESH_DOLBY_IMAGE);
					}else  if (isdts == CommonConst.STREAM_DOLBY_PLUS) {//2  audio_EAC3
						Utils.printLog(TAG, "Current audio is DOLBY_plus");
						mVideoPlayerHander.sendEmptyMessage(REFRESH_DOLBY_IMAGE);
					}else if (isdts == CommonConst.STREAM_DTS) {//3
						Utils.printLog(TAG, "Current audio is DTS");
						mVideoPlayerHander.sendEmptyMessage(REFRESH_DTS_IMAGE);
					}else if (isdts == CommonConst.STREAM_DTSHD_MASTER_AUDIO) {//4
							Utils.printLog(TAG, "Current audio is DTS_MASTER_AUDIO)");
							mVideoPlayerHander.sendEmptyMessage(REFRESH_DTSHD_MASTER_AUDIO_IMAGE);
					}else if (isdts == CommonConst.STREAM_DTS_EXPRESS) {//5
							Utils.printLog(TAG, "Current audio is DTS_EXPRESS");
							mVideoPlayerHander.sendEmptyMessage(REFRESH_DTS_EXPRESS_IMAGE);
					}
					

				}
				playStatus = PLAYING;
				dmrSetPlayStatus(playStatus);
				sendPlayEventBroadCast(mCurrIndex,playStatus);

			}else if(infoCode == CommonConst.unknown_video_format){	
				mMediaHanler.sendEmptyMessage(infoCode);	
			}else if(infoCode == CommonConst.unknown_audio_format){
				mMediaHanler.sendEmptyMessage(infoCode);
			}else {
				if (infoCode == CommonConst.media_player_not_seekable) {
					mIsSeeking = false;
					mVideoPlayerHander.sendEmptyMessage(DISSMISS_WAIT_DIALOG);
				}
				mMediaHanler.removeMessages(infoCode);
				mMediaHanler.sendEmptyMessageDelayed(infoCode, 500);
			} 
		}

		@Override
		public void onVideoPlayPrepared() {
			// TODO Auto-generated method stub
			// 播放准备好，则开始查询是否断点播放；
			// 移出网络超时消息
			 mVideoPlayerHander.removeMessages(DISMISS_DIALOG_FOR_TIMEOUT);
			 Log.d(TAG,"on prepared ,then remove init time out message");
			
            if(listDialog.isShowing()){
            	try {
    				int nowindex = mVideoContrl.getPlayingVideoIndex();
    				listDialog.refreshFocus(nowindex);
    			} catch (Exception e) {
    				e.printStackTrace();
    			}
            }
			

			Utils.printLog(TAG, "onVideoPlayPrepared getPlayingVideoIndex="+mVideoContrl.getPlayingVideoIndex()+", getPlayingVideoName="+mVideoContrl.getPlayingVideoName());
			// setVideoBackLight();
			mVideoPlayerHander.sendEmptyMessage(-99999);
			start_3Dmode();

			if (mSubtitleSurfaceView != null && mSubtitleSurfaceView.getHolder() != null) {
				mVideoContrl.setSubtiteSurface(mSubtitleSurfaceView.getHolder());
			}

			// if(isDMR){
			// mMediaHanler.sendEmptyMessage(BookMarkConst.START_PLAY_FROM_BEGIN);
			// }else{
			mVideoPlayerHander.sendEmptyMessage(VIDEO_PLAY__CHECK_BOOKMARK);
			// }

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
			if (currentPosition == 0 && mVideoContrl != null && !mVideoContrl.isPlaying()) {
				mVideoContrl.start();

			}
			mIsSeeking = false;
//			mVideoPlayerHander.sendEmptyMessage(DISSMISS_WAIT_DIALOG);
			mVideoPlayerHander.sendEmptyMessageDelayed(REFRESH_PAUSEBUTON, 1000); // WJ
																					// add
																					// delay
																					// 1000
																					// ms

			sendFirstShowContrlBtn();

		}

		@Override
		public void onVideoSizeChanged(int width, int height) {
			// TODO Auto-generated method stub
			Utils.printLog(TAG, "onVideoSizeChanged width =" + width + "  height =" + height+" mVideoContrl.isbVideoDisplayByHardware()="+mVideoContrl.isbVideoDisplayByHardware());
//			mSurfaceView.setBackgroundColor(getResources().getColor(R.color.transparent_background));

			if(clienttype.contains("938")|| clienttype.contains("838")){
				if(mVideoContrl.isbVideoDisplayByHardware()){//0067739: 多屏互动：手机端推送用手机拍的视频（竖着拍）到电视，在电视端播放时图像里的景物和人都压得很扁，没做适配处理
					setVideoDisplayFullScreen();
					mVideoContrl.setbVideoDisplayByHardware(false);
				}else{
					setVideoDisplayRotate90();
				}
				if(mVideoContrl.getPlayMediaBean()!=null){
					Log.d(TAG	,"onVideoSizeChanged mVideoContrl.getPlayMediaBean()="+mVideoContrl.getPlayMediaBean().mPath);
				}
			}
//			if(mVideoContrl.isbVideoDisplayByHardware()){//如果返回硬解标志,App不处理
//				mVideoContrl.setbVideoDisplayByHardware(false);
//				return;
//			}
//			if(mVideoContrl.getPlayMediaBean()!=null){
//				Log.d(TAG	,"onVideoSizeChanged mVideoContrl.getPlayMediaBean()="+mVideoContrl.getPlayMediaBean().mPath);
//			}
//			if(mVideoContrl.getPlayMediaBean()!=null){
//				MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
//				mediaMetadataRetriever.setDataSource(mVideoContrl.getPlayMediaBean().mPath);
//				String rotation = mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_ROTATION);
//				Utils.printLog(TAG, "onVideoSizeChanged mediaMetadataRetriever rotation = "+rotation);
//				if(rotation.equals("90")||rotation.equals("270")){
//					setVideoDisplayRotate90();
//				}
//			}
		}

		@Override
		public void onVideoSeekStart(int pos) {
			// TODO Auto-generated method stub
			Utils.printLog(TAG, "onVideoSeekStart pos="+pos);
			mIsSeeking = true;

			// mVideoPlayerHander.sendEmptyMessage(SHOW_WAIT_DIALOG);
		}

		@Override
		public void onSurfaceCreated() {
			// TODO Auto-generated method stub
			if(isSourceChanged && !ischangeSourcePlayed){
				mMediaHanler.sendEmptyMessage(START_PLAY);	
				ischangeSourcePlayed  = true;
				Log.d(TAG, "onSurfaceCreated onStart end  start play");
			}
			Log.d(TAG, "onSurfaceCreated onStart end");//lyf
		}

		@Override
		public void onSurfaceDertory() {
			// TODO Auto-generated method stub
			// mVideoPlayerHander.sendEmptyMessage(EXIT_VIDEOPLAY);
			if (mTheLastPath != null) {    //add here 20160805 WJ
				Log.d(TAG,"come to bookmark ===");
				doBookMarkAction();
				
			}
			Log.d(TAG, "onSurfaceDertory end");
		}

		@Override
		public void onVideoPlayComplete() {
			// TODO Auto-generated method stub
			Log.d(TAG, "onVideoPlayComplete===========");
			Log.d(TAG, "mPlayBeginTime is " + mPlayBeginTime.getText().toString() + "===" + "mPlayEndTime is :" + mPlayEndTime.getText().toString());

			mVideoPlayerHander.sendEmptyMessage(REFRESH_SEEKBAR_OP);
			sendPlayEventBroadCast(mCurrIndex, COMPLETE);
		}

	};

	private void sendActivityResu() {
		if (mTheLastPath != null) {
			Utils.printLog(TAG, "start send reslut!");
			Intent backIntent = new Intent();
			backIntent.putExtra(CommonConst.ResultURL, mTheLastPath);
			setResult(CommonConst.ResultCode_FINISH, backIntent);// 返回Activity结果�?
		}

	}

	private Handler mVideoPlayerHander = new Handler(Looper.getMainLooper()) {
		public void handleMessage(Message msg) {
			Log.d(TAG, "mVideoPlayerHander msg.what="+msg.what);
			if (msg.what == DISPLAY_PLAYER_CONTROL_BUTTONS) {
				if (up_guide_ly.getVisibility() == View.INVISIBLE) {
					// AudioManager audiomanager =
					// (AudioManager)getSystemService(Context.AUDIO_SERVICE);
					// audiomanager.hideVolumePanel();
					// mPlayerControlLayout.setVisibility(View.VISIBLE);
					up_guide_ly.setVisibility(View.VISIBLE);
					up_guide_ly.startAnimation(menuInAnim);
					// mPlayerControlLayout.setAnimation(controllerLyAnim);

					updateControlButtonStatus();
					mVideoPlayerHander.sendEmptyMessageDelayed(DISMISS_PLAYER_CONTROL_BUTTONS, Utils.MENU_TIMEOUT);// ３秒后消失；
					if (mPauseIcon.getVisibility() == View.VISIBLE) {
						mPauseTimeTextView.setVisibility(View.INVISIBLE);
					}
					if (mClickedBtStatus == VideoPlayerUIConst.CLICKED_VOLUME) {
						// mPlayerVolumeButton.requestFocus();

					} else if (mClickedBtStatus == VideoPlayerUIConst.CLICKED_3D) {
						// mPlayerTurn3DButton.requestFocus();

					} else if (mClickedBtStatus == VideoPlayerUIConst.CLICKED_SAVE) {
						// if (mPlayerSaveButton.isEnabled()) {
						// mPlayerSaveButton.requestFocus();
						// }
					} else if (mClickedBtStatus == VideoPlayerUIConst.CLICKED_ADVANCE) {
						// mPlayerAdvanceButton.requestFocus();

					} else if (mClickedBtStatus == VideoPlayerUIConst.CLICKED_IMAGE) {
						// mPlayerSetImageButton.requestFocus();

					} else if (mClickedBtStatus == VideoPlayerUIConst.CLICKED_VIDEOINFO) {
						// mPlayerInfo.requestFocus();

					} else {
						// if(mPlayShareButton.getVisibility() == View.VISIBLE){
						// mPlayShareButton.requestFocus();
						//
						// }else{
						// mPlayerStartButton.requestFocus();
						// }
					}

					mClickedBtStatus = VideoPlayerUIConst.CLICKED_NO;

				}
				if (mPlayerProgressLayout.getVisibility() == View.INVISIBLE) {
					mPlayerProgressLayout.setVisibility(View.VISIBLE);
					mPlayerProgressLayout.setAnimation(controllerLyAnim);
				}

				if (mPlayerProgressLayout.getVisibility() == View.VISIBLE && mVideoContrl != null && mVideoContrl.isMediaPlayerPrepared() && !mIsSeeking) {
					// new Thread(myRunnable).start();
					runOnUiThread(myRunnable);
				}
			} else if (msg.what == DISMISS_PLAYER_CONTROL_BUTTONS_FIRST) {
				if (isDismissControl == 1) {
					isDismissControl = 2;
					return;
				}

				// if (mPlayerControlLayout.getVisibility() == View.VISIBLE) {
				//
				// mPlayerControlLayout.setVisibility(View.INVISIBLE);
				//
				// }
				if (mPlayerProgressLayout.getVisibility() == View.VISIBLE) {
					// mPlayerProgressLayout.setVisibility(View.INVISIBLE);
				}
			} else if (msg.what == DISMISS_PLAYER_CONTROL_BUTTONS) {
				dismissPopWindow();
				// if (mPlayerControlLayout.getVisibility() == View.VISIBLE) {
				// mPlayerControlLayout.setVisibility(View.INVISIBLE);
				// }
				if (mPlayerProgressLayout.getVisibility() == View.VISIBLE) {
					// mPlayerProgressLayout.setVisibility(View.INVISIBLE);
				}
				if (mPauseIcon.getVisibility() == View.VISIBLE) {
					mPauseTimeTextView.setVisibility(View.VISIBLE);
				}
				dismissControlAndTips();
			} else if (msg.what == DISMISS_PLAYER_PROGRESS_LAYOUT) {

				if (/*
					 * mPlayerControlLayout.getVisibility() == View.INVISIBLE &&
					 */
				mPlayerProgressLayout.getVisibility() == View.VISIBLE) {
					// mPlayerProgressLayout.setVisibility(View.INVISIBLE);
				}
			} else if (msg.what == DISSMISS_VOLUME) {

				if (volumLay.getVisibility() == View.VISIBLE) {
					if (isVolumeChangedKeyClicked) {
						isVolumeChangedKeyClicked = false;
						mVideoPlayerHander.sendEmptyMessageDelayed(DISSMISS_VOLUME, VOLUM_BACK_TIME);
					} else {
						volumLay.setVisibility(View.INVISIBLE);
						mVideoPlayerHander.removeMessages(DISSMISS_VOLUME);
					}
				}
			} else if (msg.what == DISPLAY_VOLUME) {
				refreshCurrentVolume();
				if (volumLay.getVisibility() == View.INVISIBLE) {
					volumLay.setVisibility(View.VISIBLE);
					volumLay.requestFocus();
					mVideoPlayerHander.removeMessages(DISSMISS_VOLUME);
					mVideoPlayerHander.sendEmptyMessageDelayed(DISSMISS_VOLUME, VOLUM_BACK_TIME);
					// updateControlButtonStatus(false);
				}
			} else if (msg.what == DISPLAY_PLAYER_PROGRESS_LAYOUT) {
				// if (mPlayerProgressLayout.getVisibility() == View.INVISIBLE)
				// {
				mPlayerProgressLayout.setVisibility(View.VISIBLE);
				// }
			} else if (msg.what == PLAYER_UI_INIT) {// UI初始化；
				playerUIInit();
			} else if (msg.what == VIDEO_PLAY__CHECK_BOOKMARK) {// 书签检查播放断点；
				dismissWaittingDialog();
				bookmark_check();
			} else if (msg.what == DO_BOOKMARK_ACTION) {
				new Thread(new Runnable() {

					@Override
					public void run() {
						doBookMarkAction();	
					}
				}).start();
							
			} else if (msg.what == DISMISS_DIALOG_FOR_TIMEOUT) {
				doTimeOutAction();
			} else if (msg.what == EXIT_VIDEOPLAY) {
				Utils.printLog(TAG, "EXIT_VIDEOPLAY");
				exitVideoPlay();
			} else if (msg.what == PLAYER_PAUSE) {
				playPause();
			} else if (msg.what == PLAYER_START) {
				playStart();
			} else if (msg.what == VIDEO_PLAY_START_OR_PAUSE) {
				if (mVideoContrl != null && mVideoContrl.isMediaPlayerPrepared() && mVideoContrl.isPlaying()) {
					mVideoPlayerHander.sendEmptyMessage(PLAYER_PAUSE);
				} else {
					mVideoPlayerHander.sendEmptyMessage(PLAYER_START);
				}
			} else if (msg.what == SHOW_WAIT_DIALOG) {
				showWatingDialog();
//				sendPlayEventBroadCast(mCurrIndex, SHOW_WAIT);
			} else if (msg.what == DISSMISS_WAIT_DIALOG) {
				dismissWaittingDialog();
//				sendPlayEventBroadCast(mCurrIndex, DISSMISS_WAIT);
			} else if (msg.what == -99999) {
				if (mSurfaceView != null) {
					mSurfaceView.requestLayout();
					mSurfaceView.invalidate();
					mSubtitleSurfaceView.requestLayout();
					mSubtitleSurfaceView.invalidate();
				}
			} else if (msg.what == REFRESH_PAUSEBUTON) {

				if (mVideoContrl != null && mVideoContrl.isMediaPlayerPrepared() && isChangeNotStart) {

					if (!mVideoContrl.isPlaying()) {
						mPauseIcon.setVisibility(View.VISIBLE);
						mPauseIcon.requestLayout();
						mPauseIcon.invalidate();
						Utils.printLog(TAG, "mPauseIcon.setVisibility(View.VISIBLE);");
						// mPlayerStartButton
						// .setBackgroundResource(R.drawable.player_start_selector);
						int currentPos = mVideoContrl.getCurrentPosition();
						int duration = 0;
						if (mVideoContrl.isMediaPlayerPrepared()) {
							duration = mVideoContrl.getDuration();
						}
						if (currentPos != VideoPlayerContrlConsts.GET_CURRENTPOSITION_ERROR) {
							Utils.printLog(TAG, "pause and time =" + currentPos);
							mPauseTimeTextView.setText(Utils.getTimeShort(currentPos) + "/" + Utils.getTimeShort(duration));
						} else {
							mPauseTimeTextView.setText("Error");
						}

					} else {
						mPauseIcon.setVisibility(View.INVISIBLE);
						// mPlayerStartButton
						// .setBackgroundResource(R.drawable.player_pause_selector);
					}
				}
			} else if (msg.what == REFRESH_TOTAL_TIME) {
				mPlayEndTime.setText(Utils.getTimeShort(sEndTime));
				if (sEndTime > 0 && !mPlayerSeekar.isEnabled()) {
					mPlayerSeekar.setEnabled(true);
				}
			} else if (msg.what == REFRESH_CURRENT_TIME) {

				int currentPostion = msg.getData().getInt("currentPos");
				mPlayBeginTime.setText(Utils.getTimeShort(currentPostion));
				if (sEndTime > 0) {

					int process = (int) Math.ceil((double) currentPostion * (double) Utils.SeekBarLength / (double) sEndTime);
					Utils.printLog(TAG, "SeekBarListener updateprocess currentPostion=" + currentPostion + " ;sEndtime =" + sEndTime + "; process=" + process);
					mPlayerSeekar.setProgress(process);
				}
			} else if (msg.what == REFRESH_DOLBY_IMAGE) {
				mDtsImageView.setBackgroundResource(R.drawable.dobby_icon);
				mDtsTextView.setVisibility(View.GONE);
				mDtsLayout.setVisibility(View.VISIBLE);
				mVideoPlayerHander.sendEmptyMessageDelayed(REFRESH_DTS_IMAGE_DISMISS, 5000);
				Log.d(TAG, "ddpicon msg.what="+msg.what);
			} else if (msg.what == REFRESH_DOLBY_PLUS_IMAGE) {
				mDtsImageView_vision.setBackgroundResource(R.drawable.dobby_vision_icon);
				mDtsTextView_vision.setVisibility(View.GONE);
				mDtsLayout_vision.setVisibility(View.VISIBLE);
				mVideoPlayerHander.sendEmptyMessageDelayed(REFRESH_DTS_VISION_IMAGE_DISMISS, 5000);
				Log.d(TAG, "ddpicon msg.what="+msg.what);
			} else if (msg.what == REFRESH_DTS_IMAGE) {
				mDtsImageView.setBackgroundResource(R.drawable.dts_icon); 
				mDtsTextView.setVisibility(View.GONE);
				mDtsLayout.setVisibility(View.VISIBLE);
				mVideoPlayerHander.sendEmptyMessageDelayed(REFRESH_DTS_IMAGE_DISMISS, 5000);
				Log.d(TAG, "ddpicon msg.what="+msg.what);
			} else if (msg.what == REFRESH_DTSHD_MASTER_AUDIO_IMAGE) {
				mDtsImageView.setBackgroundResource(R.drawable.dts_hp_icon);  
//				mDtsTextView.setText(R.string.dts_master_audio);
				mDtsLayout.setVisibility(View.VISIBLE);
				mVideoPlayerHander.sendEmptyMessageDelayed(REFRESH_DTS_IMAGE_DISMISS, 5000);
				Log.d(TAG, "ddpicon msg.what="+msg.what);
			} else if (msg.what == REFRESH_DTS_EXPRESS_IMAGE) {
				mDtsImageView.setBackgroundResource(R.drawable.dts_express);    
//				mDtsTextView.setText(R.string.dts_express);
				mDtsLayout.setVisibility(View.VISIBLE);
				mVideoPlayerHander.sendEmptyMessageDelayed(REFRESH_DTS_IMAGE_DISMISS, 5000);
				Log.d(TAG, "ddpicon msg.what="+msg.what);
			} else if (msg.what == REFRESH_DTS_IMAGE_DISMISS) {
				mDtsLayout.setVisibility(View.GONE);
			}else if (msg.what == REFRESH_DTS_VISION_IMAGE_DISMISS) {
				mDtsLayout_vision.setVisibility(View.GONE);
			} else if (msg.what == DISPLAY_PLAYER_AUDIO_NUM) {
				Utils.printLog(TAG, "audioTrackNum==0");
				new MyToast(VideoPlayerActivity.this, getResources().getString(R.string.audiotrack_iszero)).show();
			} else if (msg.what == REFRESH_SEEKBAR_UNNABLE) {
				mPlayerSeekar.setEnabled(false);
			} else if (msg.what == REFRESH_SEEKBAR_OP) {

				mVideoPlayerHander.post(new Runnable() {
					public void run() {

						if (mPlayerSeekar != null) {
							mPlayerSeekar.setProgress(1000);
						}
						if (mPlayBeginTime != null && mPlayEndTime != null) {
							mPlayBeginTime.setText(mPlayEndTime.getText().toString());
						}
					}
				});

			} else if (msg.what == PLAYER_URL) {
				Utils.printLog(TAG, "PLAYER_URL start");
				mVideoContrl.playUrl(mList, mCurrIndex);
			} else if (msg.what == VOICE_FORWORD) {

				int mSeekPos = msg.getData().getInt("mPos");
				int mCurrPos = mVideoContrl.getCurrentPosition();
				mCurrPos = mCurrPos + mSeekPos;
				SeekPositon(mCurrPos);
			} else if (msg.what == VOICE_BACKWORD) {

				int mSeekPos = msg.getData().getInt("mPos");
				int mCurrPos = mVideoContrl.getCurrentPosition();
				mCurrPos = mCurrPos - mSeekPos;
				if (mCurrPos < 0) {
					mCurrPos = 0;
				}
				SeekPositon(mCurrPos);
			} else if (msg.what == VOICE_SEEK_POSITION) {

				int mSeekPos = msg.getData().getInt("mPos");
				if (mSeekPos < 0) {
					mSeekPos = 0;
				}
				SeekPositon(mSeekPos);
			} else if (msg.what == showFileListMessage) {
				if(listDialog != null && !listDialog.isShowing()){
				   listDialog.show();
				}	
			} else if (msg.what == showMenuDialogMessage) {
				if(menuDialog != null && !menuDialog.isShowing()){
					menuDialog.show();
				}
			}else if(msg.what == DISMISS_DIALOG_FOR_TIMEOUT){  //WJ
				doTimeOutAction();
			}else if(msg.what == CHANGELANUAGE){
				mKeydown.setText(R.string.presskeydowntext);
				mKeymenu.setText(R.string.presskeymenutext);
			}

		}
	};

	private void doTimeOutAction() {
		mVideoPlayerHander.sendEmptyMessage(DISSMISS_WAIT_DIALOG);
		Utils.printLog(TAG, "doTimeOutAction ");
		if (mVideoContrl.isVOD()) {
			new MyToast(VideoPlayerActivity.this, getResources().getString(R.string.media_player_timeout)).show();
		} else {

			new MyToast(VideoPlayerActivity.this, getResources().getString(R.string.media_player_timeout_local)).show();
		}
		mVideoPlayerHander.sendEmptyMessage(EXIT_VIDEOPLAY);
	}



	private void bookmark_check() {
		if (mList == null)
			return;
		if (mVideoContrl != null && mVideoContrl.isMediaPlayerPrepared()) {
			int duration = mVideoContrl.getDuration();
			if ((duration > 0) && (!isDMR)) {
				String path = mList.get(mCurrIndex).mPath;
				if (mBookMark != null && path != null) {
					mBreakPos = mBookMark.getPosFromDB(path);

					Utils.printLog(TAG, "bookmark_check() mBreakPos = " + mBreakPos + "  time = " + Utils.getTimeShort(mBreakPos));
					if (mBreakPos <= 999) {
						mMediaHanler.sendEmptyMessage(BookMarkConst.START_PLAY_FROM_BEGIN);
					} else {
						try {
							new StartPlayModeDialog(VideoPlayerActivity.this, mMediaHanler, mBreakPos).show();
							mSurfaceView.setBackgroundColor(getResources().getColor(R.color.bg));
						} catch (Exception e) {
							e.printStackTrace();
						}

					}
				}
			} else {
				mMediaHanler.sendEmptyMessage(BookMarkConst.START_PLAY_FROM_BEGIN);
			}
		}

	}

	private BroadcastReceiver mConnectionChangeReceiver = new BroadcastReceiver() {

		public void onReceive(Context context, Intent intent) {
			Utils.printLog(TAG, "netWorkstatus Changed!");

			if (isFirstNetChange) {
				isFirstNetChange = false;

				Utils.printLog(TAG, "netWorkstatus Changed and isFirstNetChange!");
				return;
			}

			if (mVideoContrl == null || mVideoContrl.isMediaPlayerPrepared() == false || mList == null || mList.size() <= mCurrIndex
					|| (!mList.get(mCurrIndex).mPath.startsWith("/mnt/smb") && !mList.get(mCurrIndex).mPath.startsWith("http"))) {
				Utils.printLog(TAG, "netWorkstatus Changed and no need deal with!");
				return;
			}
			if (Utils.isNetWorkUsable(VideoPlayerActivity.this) == false) {
				if (Utils.DEBUG) {
					System.out.println("===================================netwrok Unusable");
				}

				new MyToast(VideoPlayerActivity.this, VideoPlayerActivity.this.getResources().getString(R.string.media_player_netUnausable)).show();
				mIsInternetDisconnect = true;
				if (mPauseIcon.getVisibility() == View.INVISIBLE) {
					mVideoPlayerHander.sendEmptyMessage(PLAYER_PAUSE);
					mPauseByNetWorkDisconnected = true;
				}
				mVideoPlayerHander.sendEmptyMessageDelayed(EXIT_VIDEOPLAY, 1000);
				// updateControlButtonStatus(false);
			} else {
				if (Utils.DEBUG) {
					System.out.println("=#################################===================================netwrok OK");
				}
				new MyToast(VideoPlayerActivity.this, VideoPlayerActivity.this.getResources().getString(R.string.media_player_netUsable)).show();
				mIsInternetDisconnect = false;
				if (mPauseByNetWorkDisconnected) {
					mVideoPlayerHander.sendEmptyMessage(PLAYER_START);
					mPauseByNetWorkDisconnected = false;
				}
				// updateControlButtonStatus(true);
			}
		}
	};

	private BroadcastReceiver mVoiceReceiver = new BroadcastReceiver() {

		public void onReceive(Context context, Intent intent) {
			Utils.printLog(TAG, "mVoiceReceiver receive!");

			Bundle bundle = intent.getExtras();
			String sVoice = bundle.getString("type");
			int mPosition = bundle.getInt("position", 0);
			mPosition = mPosition * 1000;
			if (sVoice == null) {
				return;
			}
			Utils.printLog(TAG, "mVoiceReceiver sVoice!" + sVoice);
			Utils.printLog(TAG, "mVoiceReceiver mPosition!" + mPosition);
			if (sVoice.equalsIgnoreCase("play")) {
				if (mVideoContrl != null && mVideoContrl.isMediaPlayerPrepared()) {
					mVideoPlayerHander.sendEmptyMessage(PLAYER_START);
				}
			} else if (sVoice.equalsIgnoreCase("pause")) {
				if (mVideoContrl != null && mVideoContrl.isMediaPlayerPrepared() && mVideoContrl.isPlaying()) {
					mVideoPlayerHander.sendEmptyMessage(PLAYER_PAUSE);
				}
			} else if (sVoice.equalsIgnoreCase("previous")) {
				if (mVideoContrl != null && mVideoContrl.isMediaPlayerPrepared()) {
					mMediaHanler.sendEmptyMessage(PRE_VIDEO);
				}
			} else if (sVoice.equalsIgnoreCase("next")) {
				if (mVideoContrl != null && mVideoContrl.isMediaPlayerPrepared()) {
					mMediaHanler.sendEmptyMessage(NEXT_VIDEO);
				}
			} else if (sVoice.equalsIgnoreCase("forward")) {
				if (mVideoContrl != null && mVideoContrl.isMediaPlayerPrepared()) {
					Message mss = mVideoPlayerHander.obtainMessage();
					mss.getData().putInt("mPos", mPosition);
					mss.what = VOICE_FORWORD;
					mVideoPlayerHander.sendMessage(mss);
				}
			} else if (sVoice.equalsIgnoreCase("backward")) {
				if (mVideoContrl != null && mVideoContrl.isMediaPlayerPrepared()) {
					Message mss = mVideoPlayerHander.obtainMessage();
					mss.getData().putInt("mPos", mPosition);
					mss.what = VOICE_BACKWORD;
					mVideoPlayerHander.sendMessage(mss);
				}
			} else if (sVoice.equalsIgnoreCase("seek")) {
				if (mVideoContrl != null && mVideoContrl.isMediaPlayerPrepared()) {
					Message mss = mVideoPlayerHander.obtainMessage();
					mss.getData().putInt("mPos", mPosition);
					mss.what = VOICE_SEEK_POSITION;
					mVideoPlayerHander.sendMessage(mss);
				}
			}

		}
	};

	private void updateControlButtonStatus(boolean isNetworkConnected) {

		// mPlayerNext.setEnabled(isNetworkConnected);
		//
		// mPlayerSequenceButton.setEnabled(isNetworkConnected);
		// mPlayerSaveButton.setEnabled(isNetworkConnected);
		// mPlayerStartButton.setEnabled(isNetworkConnected);
		// mPlayerPrevious.setEnabled(isNetworkConnected);
		// mPlayerVolumeButton.setEnabled(isNetworkConnected);
		//
		// mPlayerTurn3DButton.setEnabled(isNetworkConnected);
		//
		// mPlayerNext.setFocusable(isNetworkConnected);
		//
		// mPlayerSequenceButton.setFocusable(isNetworkConnected);
		// mPlayerSaveButton.setFocusable(isNetworkConnected);
		// mPlayerStartButton.setFocusable(isNetworkConnected);
		// mPlayerPrevious.setFocusable(isNetworkConnected);
		// mPlayerVolumeButton.setFocusable(isNetworkConnected);
		//
		// mPlayerTurn3DButton.setFocusable(isNetworkConnected);
		if (isNetworkConnected) {
			// if (mPlayShareButton.getVisibility() == View.VISIBLE) {
			// mPlayShareButton.requestFocus();
			//
			// } else {
			// mPlayerStartButton.requestFocus();
			// }
			// if (mVideoContrl != null && !mVideoContrl.isVOD()) {
			// mPlayerSaveButton.setEnabled(false);
			// mPlayerSaveButton.setFocusable(false);
			// }
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
			
			String mainuiaction3 = intent.getAction();
			int bundlewhat = intent.getIntExtra("what",-1);
			int bundlerarg = intent.getIntExtra("arg1",-1);
            Log.d(TAG, "now main ui actions3 bundler "+bundlewhat+"++++++++"+bundlerarg);
//			if (intent.getAction().trim().equals(VideoPlayerUIConst.COLLECTION_OPERATE_DONE)) {
//
//				mAddType = intent.getIntExtra(VideoPlayerUIConst.OPERATE_RESULT, VideoPlayerUIConst.Fail_COLLECTION);
//				boolean isSucess = mAddType == VideoPlayerUIConst.Succ_COLLECTION ? true : false;
//				Utils.printLog(TAG, "COLLECTION_OPERATE_DONE with res =" + isSucess + " num=" + mAddType);
//				if (mList != null && mList.size() > 0 && mCurrIndex < mList.size()) {
//				}
//
//			} else if (intent.getAction().equals(VideoPlayerUIConst.ATV_OSD_OPEN)) {
//				// if (mPlayerControlLayout.getVisibility() == View.VISIBLE) {
//				// mPlayerControlLayout.setVisibility(View.INVISIBLE);
//				// }
//			} else
			if (CommonConst.CLOSE_VIDEO_PLAY.equalsIgnoreCase(mainuiaction3)) {
				Utils.printLog(TAG, "CLOSE_VIDEO_PLAY");

				exitVideoPlay();
			} else if (CommonConst.HOME_PRESSED.equalsIgnoreCase(mainuiaction3)) {
				Utils.printLog(TAG, "HomePRESSED");
				exitVideoPlay();
			} else if (CommonConst.STR_PORWER_CHANGE.equalsIgnoreCase(mainuiaction3)) {
				Utils.printLog(TAG, "STR SHUT DOWN"); // add here for str
														// 待机再开机黑屏问�?20150901 WJ
				exitVideoPlay();
			} else if (CommonConst.TV_PRESSED.equalsIgnoreCase(mainuiaction3)) {
				Utils.printLog(TAG, "TV_PRESSED");
				exitVideoPlay();
			} else if (CommonConst.CHANGE_SOURCE_PRESSED.equalsIgnoreCase(mainuiaction3)) {
				Utils.printLog(TAG, "CHANGE_SOURCE_PRESSED");
				exitVideoPlay();
			} else if (Intent.ACTION_SHUTDOWN.equalsIgnoreCase(intent.getAction())) {
				Utils.printLog(TAG, "DEVICE_SHUTDOWN broadCast");
//				if (mVideoContrl != null && mVideoContrl.isMediaPlayerPrepared()) {
//					mTheLastPosition = mVideoContrl.getCurrentPosition();
//					sEndTime = mVideoContrl.getDuration();
//					Utils.printLog(TAG, "mTheLastPosition=" + mTheLastPosition + ",  sEndTime =" + sEndTime);
//				} else {
//					mTheLastPosition = VideoPlayerContrlConsts.GET_CURRENTPOSITION_ERROR;
//				}
				if (mTheLastPath != null) {
					doBookMarkAction();
				}
				// exitVideoPlay();
			} else if (CommonConst.SOURCE_CHANGEandVOICEASS.equalsIgnoreCase(mainuiaction3)) {
				Utils.printLog(TAG, "Sourcechange  or  voice assistant PRESSED");
				exitVideoPlay();
			} else if (CommonConst.HISTORYRECORD.equalsIgnoreCase(mainuiaction3)) {
				Utils.printLog(TAG, "HISTORYRECORD  PRESSED");
				exitVideoPlay();
			} else if(CommonConst.LanuageChange.equalsIgnoreCase(mainuiaction3)){
				Utils.printLog(TAG, "Setting LanuageChange  PRESSED");
				mVideoPlayerHander.removeMessages(CHANGELANUAGE);
				mVideoPlayerHander.sendEmptyMessageDelayed(CHANGELANUAGE, 1000);				
			}else if(CommonConst.VIDEO_HDR_HLG_BROADCAST.equalsIgnoreCase(mainuiaction3)){
				Utils.printLog(TAG, "hdr or hlg broadcast ==");
				if(TYPE_HDR == bundlewhat){
					switch (bundlerarg) {
					case Utils.EN_TCL_SIGNAL_HDR10:
						new MyToast(VideoPlayerActivity.this, VideoPlayerActivity.this
								.getResources().getString(
										R.string.hdrdialog),0).show();
						break;
                    case Utils.EN_TCL_SIGNAL_HLG:	
                    	new MyToast(VideoPlayerActivity.this, VideoPlayerActivity.this
								.getResources().getString(
										R.string.hlgdialog),0).show();
						break;
					default:
						break;
					}
				}
			}
		}
	};


	private void updateControlButtonStatus() {
		if (/*
			 * mPlayerControlLayout.getVisibility() == View.INVISIBLE ||
			 */mList == null) {
			return;
		}
		Utils.printLog(TAG, "updateControlButtonStatus");

		int size = mList.size();
		if (mVideoContrl.getPlayType() == VideoPlayerContrlConsts.MEDIA_RANDOM_PLAY || mVideoContrl.getPlayType() == VideoPlayerContrlConsts.MEDIA_CYCLE_PLAY
		/*
		 * || sCurrentPlayerType + 1 ==
		 * MediaControlIntent.FLAG_MEDIA_SINGLE_REPEAT_PLAY
		 */) {

			if (mList.size() == 1 && mVideoContrl.getPlayType() == VideoPlayerContrlConsts.MEDIA_RANDOM_PLAY) {
				return;
			}
			return;
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
					seekPosition = (int) ((float) clickPosition / sScrollRange_1080 * sEndTime);
				} else {
					clickPosition = clickPosition + 8;
					seekPosition = (int) ((float) clickPosition / sScrollRange_720 * sEndTime);
				}

				if (seekPosition > sEndTime) {
					seekPosition = sEndTime - 1000;
				}
				try {
					if (mVideoContrl != null && mVideoContrl.isMediaPlayerPrepared())
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
		public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
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
		Cursor cursor = resolver.query(Uri.parse("content://com.tcl.tv.sys.SYSPicSettings/pic_settings"), new String[] { "backlight" }, null, null, null);
		if (cursor != null) {
			if (cursor.moveToFirst()) {
				int backLightIndex = cursor.getColumnIndex("backlight");
				backLight = cursor.getInt(backLightIndex);
				Utils.printLog(TAG, "getBackLight()-------" + backLight);
			}
			cursor.close();
		}
		return backLight;
	}

	public static void setBooleanNatrueLight(Context context, boolean stat) {
		SharedPreferences mSp;
		mSp = context.getSharedPreferences("NatureLight", Context.MODE_WORLD_WRITEABLE);
		mSp.edit().putBoolean(VideoPlayerUIConst.VEDIO_NATURAL_LIGHT, stat).commit();
	}

	public static boolean getBooleanISNatrueLightON(Context context) {
		SharedPreferences mSp;
		mSp = context.getSharedPreferences("NatureLight", Context.MODE_WORLD_READABLE);
		return mSp.getBoolean(VideoPlayerUIConst.VEDIO_NATURAL_LIGHT, false);
	}

	public static int getSavedBackLight(Context context) {
		int backlight = -1;
		SharedPreferences settings = context.getSharedPreferences(VideoPlayerUIConst.VEDIO_SAVED_BACKLIGHT, -1);
		backlight = settings.getInt(VideoPlayerUIConst.VEDIO_SAVED_BACKLIGHT, -1);
		Utils.printLog(TAG, "getSavedBackLight backlight =" + backlight);
		return backlight;
	}

	public static void saveBackLight(Context context, int backlight) {

		SharedPreferences settings = context.getSharedPreferences(VideoPlayerUIConst.VEDIO_SAVED_BACKLIGHT, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putInt(VideoPlayerUIConst.VEDIO_SAVED_BACKLIGHT, backlight);
		editor.commit();
	}

	private void bindTVnSreenService() {
		final Intent serviceIntent = new Intent();
		serviceIntent.setAction("com.tcl.multiscreeninteractiontv.MSI_TV_Service");
		serviceIntent.setPackage("com.tcl.MultiScreenInteraction_TV");
		Log.i(TAG, "bindTVnSreenService to bind service");
		try {
			isBind = this.bindService(serviceIntent, nSreenConnection, BIND_AUTO_CREATE);
			if (isBind == false) {
				Log.e(TAG, "bindTVnSreenService bindService fail");
			}
		} catch (Exception e) {
			Log.v(TAG, "fatal error bindTVnSreenServicenSreenConnection");
		}
	}

	private void dmrSetPlayStatus(String State) {
		if (isDMR && nSreenTVService != null) {
			try {
				Utils.printLog(TAG, "current state" + State+"  isDMRstop=="+isDMRstop);
				if (isDMRstop) {
					return;
				}
				nSreenTVService.setPlayStatus(State);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void SeekPositon(int mPositon) {
		Log.d(TAG, "SeekPositon mPositon="+mPositon);//fendy
		if (sEndTime > 0) {
			final int mpos = mPositon;
			new Thread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					mVideoContrl.seekTo(mpos);

				}
			}).start();
		} else {
			mPlayerSeekar.setProgress(0);
		}
	}

	private ServiceConnection nSreenConnection = new ServiceConnection() {
		public void onServiceConnected(ComponentName classname, IBinder obj) {
			Log.i(TAG, "nSreenConnection onServiceConnected");
			nSreenTVService = IDLNAService.Stub.asInterface(obj);
			try {
				nSreenTVService.registerPlayerCallback(mTVnSreenCallback);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		public void onServiceDisconnected(ComponentName classname) {
			Log.v(TAG, "nSreenConnection Service disconnected.");

			nSreenTVService = null;
		}
	};

	private IPlayerCallback.Stub mTVnSreenCallback = new IPlayerCallback.Stub() {

		@Override
		public void dmr_play(String uri, String name, String player, String album) throws RemoteException {
			// TODO Auto-generated method stub
			Utils.printLog(TAG, "dmr_play uri " + uri+" name="+name+" player="+player+" album="+album);
			// if(mVideoContrl != null && nSreenTVService != null
			// && playStatus.endsWith("PAUSED_PLAYBACK")){
			// mVideoPlayerHander.sendEmptyMessage(VIDEO_PLAY_START_OR_PAUSE);
			// }else{
			if (uri == null) {
				return;
			}

			mList = new ArrayList<MediaBean>();
			MediaBean bean = new MediaBean();
			bean.mPath = uri;

			// if (uri.startsWith("http")) {
		
			// Utils.printLog(TAG, "URLDecoder path =" + uri);
			// bean.mName = Utils.getRealName(uri);
			// } else {
			// bean.mName = Utils.getRealName(uri);
			// }
			bean.mName = name;
			if (bean.mName == null || bean.mName.equalsIgnoreCase("") ||  bean.mName.equals("unkown")) {
				bean.mName = Utils.getRealName(uri);
			}

			mList.add(bean);
			mCurrIndex = 0;
			if (mVideoContrl != null) {
				mVideoPlayerHander.sendEmptyMessage(PLAYER_URL);
			}
			// }
		}

		@Override
		public void dmr_pause() throws RemoteException {
			// TODO Auto-generated method stub
			Utils.printLog(TAG, "dmr_pause");
			if (mVideoContrl != null && mVideoContrl.isMediaPlayerPrepared() && mVideoContrl.isPlaying()) {
				mVideoPlayerHander.sendEmptyMessage(PLAYER_PAUSE);
			}
		}

		@Override
		public void dmr_stop() throws RemoteException {
			// TODO Auto-generated method stub
			Utils.printLog(TAG, "dmr_stop");
			isDMRstop = true;
			Log.d(TAG,"now isDMR is "+ isDMR);
			if(isDMR){    //add here 20161125 for dmr push first,message play next,then dmr stop,tv stop
			   exitVideoPlay();
			}
			
		}

		@Override
		public void dmr_seek(long time) throws RemoteException {
			// TODO Auto-generated method stub
			Utils.printLog(TAG, "dmr_seek time="+time);//nicy
			if (sEndTime > 0) {
				final long now = time;
				new Thread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						mVideoContrl.seekTo((int) now);
						Utils.printLog(TAG, "mVideoContrl.seekTo now ="+now);//nicy
					}
				}).start();
			} else {
				mPlayerSeekar.setProgress(0);
			}
		}

		@Override
		public void dmr_setMute(boolean mute) throws RemoteException {
			// TODO Auto-generated method stub
//			soundManager.setAudioMuteEnabled(mute);
			mAmanager.setStreamMute(AudioManager.STREAM_MUSIC, mute);
			Utils.printLog(TAG, "mAmanager.setStreamMute()" + mute);
		}

		@Override
		public boolean dmr_getMute() throws RemoteException {
			// TODO Auto-generated method stub
			boolean mute = soundManager.getAudioMuteEnabled();
			Utils.printLog(TAG, "soundManager.getAudioMuteEnabled()" + mute);
			return mute;
		}

		@Override
		public void dmr_setVolume(int volume) throws RemoteException {
			// TODO Auto-generated method stub
//			soundManager.setVolume(volume);
			int flag = 4097;
			mAmanager.setStreamVolume(AudioManager.STREAM_MUSIC, volume,
					flag);
			Utils.printLog(TAG, "soundManager.setVolume" + volume);
		}

		@Override
		public int dmr_getVolume() throws RemoteException {
			// TODO Auto-generated method stub
			int current = soundManager.getVolume();
			Utils.printLog(TAG, "current Volume" + current);
			return current;
		}

		@Override
		public long dmr_getMediaDuration() throws RemoteException {
			// TODO Auto-generated method stub
			Utils.printLog(TAG, "dmr_getMediaDuration()" + sEndTime);
			return sEndTime;
		}

		@Override
		public long dmr_getCurPlayPosition() throws RemoteException {
			// TODO Auto-generated method stub
			if (mVideoContrl != null && mVideoContrl.isMediaPlayerPrepared()) {
				int currentPostion = mVideoContrl.getCurrentPosition();
				Utils.printLog(TAG, "dmr_getCurPlayPosition()" + currentPostion);
				if (currentPostion != VideoPlayerContrlConsts.GET_CURRENTPOSITION_ERROR) {
					return currentPostion;
				}
			}
			return 0;
		}

		@Override
		public void dmr_setPlayingName(String str) throws RemoteException {
			// TODO Auto-generated method stub

		}

		@Override
		public void dmr_pauseToResume() throws RemoteException {
			// TODO Auto-generated method stub
			if (mVideoContrl != null && playStatus.endsWith("PAUSED_PLAYBACK")) {
				mVideoPlayerHander.sendEmptyMessage(VIDEO_PLAY_START_OR_PAUSE);
			}

		}

		@Override
		public void dmr_playList(List<String> list) throws RemoteException {
			// TODO Auto-generated method stub

			if (list == null) {
				Utils.printLog(TAG, "current dmr playlist is null");
				return;
			}

			mList = new ArrayList<MediaBean>();
			for (String u : list) {
				String path = u;
				Utils.printLog(TAG, "dmr playlist ---" + path);
				MediaBean bean = new MediaBean();
				bean.mPath = path;
				if (path.startsWith("http")) {
					try {
						path = URLDecoder.decode(path, "utf-8");
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					Utils.printLog(TAG, "URLDecoder path =" + path);
					bean.mName = Utils.getRealName(path);
				} else {
					bean.mName = Utils.getRealName(path);
				}

				mList.add(bean);
			}
			if (mList != null && mList.size() > 0) {
				mCurrIndex = 0;
				if (mVideoContrl != null) {
					mVideoPlayerHander.sendEmptyMessage(PLAYER_URL);
				}
			}

		}

	};

	private void setTVToFullScreen() {

		final VideoWindowRect videoWindowType = new VideoWindowRect();
		videoWindowType.x = 65535;
		videoWindowType.y = 65535;
		videoWindowType.width = 65535;
		videoWindowType.height = 65535;
		try {
			Log.d(TAG, "now to set TV to FullScreen");

			if (TTvPictureManager.getInstance(this) != null) {

				TTvPictureManager.getInstance(this).scaleVideoWindow(
				EnTCLWindow.EN_TCL_MAIN, videoWindowType);

			}

		} catch (Exception e) {

			e.printStackTrace();

		}

	}

	private void setEMScreenMode() {
		try {
			EnTCLAspectRatio mAspect = mPicmanager.getAspectRatio();
			if (mAspect != null) {
				mPicmanager.setAndUpdatePictureSetting(EnTCLPicSetting.EN_TCL_UPDATE_SCREEN_TCL_MODE, mAspect.ordinal(), true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void setCNScreenMode() {
		try {
			EnTCLAspectRatio mAspect = mPicmanager.getAspectRatio();
			if (mAspect != null) {
				Log.d(TAG, "====now mASpec is " + mAspect);
				mPicmanager.setAspectRatio(mAspect);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void refreshPicMode() {
		try {

			TTvPictureManager mPic = TTvPictureManager.getInstance(this);
			EnTCLPictureMode en_picmode = mPic.getPictureMode();
			Log.d(TAG, "start show video, set picmode = " + en_picmode);
			mPic.setPictureMode(en_picmode);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void onFileListShowed() {

		if (up_guide_ly.getVisibility() == View.INVISIBLE && mClickedBtStatus == VideoPlayerUIConst.CLICKED_VIDEOINFO) {

			up_guide_ly.setVisibility(View.VISIBLE);
			mPlayerProgressLayout.setVisibility(View.VISIBLE);
			up_guide_ly.startAnimation(menuInAnim);
			mPlayerProgressLayout.startAnimation(controllerLyAnim);
			mVideoPlayerHander.removeMessages(DISMISS_PLAYER_CONTROL_BUTTONS);
			mVideoPlayerHander.sendEmptyMessageDelayed(DISMISS_PLAYER_CONTROL_BUTTONS, Utils.MENU_TIMEOUT);// ３秒后消失；
		}

	}

	@Override
	public void onSettingMenuShowed() {

		if (up_guide_ly.getVisibility() == View.INVISIBLE && mClickedBtStatus == VideoPlayerUIConst.CLICKED_VIDEOINFO) {

			up_guide_ly.setVisibility(View.VISIBLE);
			mPlayerProgressLayout.setVisibility(View.VISIBLE);
			up_guide_ly.startAnimation(menuInAnim);
			mPlayerProgressLayout.startAnimation(controllerLyAnim);
			mVideoPlayerHander.removeMessages(DISMISS_PLAYER_CONTROL_BUTTONS);
			mVideoPlayerHander.sendEmptyMessageDelayed(DISMISS_PLAYER_CONTROL_BUTTONS, Utils.MENU_TIMEOUT);// ３秒后消失；
		}

	}

	// private void get(){
	// EnTCLPictureMode mm = mPicmanager.getPictureMode();
	// mm.
	// }

	private void showControlAndTips() {
		if (up_guide_ly.getVisibility() == View.INVISIBLE) {
			up_guide_ly.setVisibility(View.VISIBLE);
			mPlayerProgressLayout.setVisibility(View.VISIBLE);
			up_guide_ly.startAnimation(menuInAnim);
			mPlayerProgressLayout.startAnimation(controllerLyAnim);
		}
	}

	private void dismissControlAndTips() {
		if (up_guide_ly.getVisibility() == View.VISIBLE) {
			up_guide_ly.startAnimation(menuOutAnim);
			up_guide_ly.setVisibility(View.INVISIBLE);
		}
		if (mPlayerProgressLayout.getVisibility() == View.VISIBLE) {
			mPlayerProgressLayout.startAnimation(controllerLyOutAnim);
			mPlayerProgressLayout.setVisibility(View.INVISIBLE);
		}
	}
	/**
	 * 0067739: 多屏互动：手机端推送用手机拍的视频（竖着拍）到电视，在电视端播放时图像里的景物和人都压得很扁，没做适配处理
	 * 如果bVideoDisplayByHardware 为false,就走软解flow,在OnVideoSizeChanged再call分辨率处理的函数
	 */
	public void setVideoDisplayRotate90() {
		DisplayMetrics outMetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
		int mScreenResolutionWidth = outMetrics.widthPixels;
		int mScreenResolutionHeight = outMetrics.heightPixels;
        float ratio = (float)mScreenResolutionHeight/mScreenResolutionWidth;
        int videoHeight = mScreenResolutionHeight;
        int videoWidth = mScreenResolutionHeight * mScreenResolutionHeight / mScreenResolutionWidth;
        Log.i(TAG, "setVideoDisplayRotate90 mScreenResolutionWidth:" + mScreenResolutionWidth + " screenHeight:"
                + mScreenResolutionHeight + " ratio:" + ratio + " videoWidth:" + videoWidth+" videoHeight="+videoHeight);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(videoWidth , videoHeight, Gravity.CENTER);
        mSurfaceView.setLayoutParams(params);
    } 
	public void setVideoDisplayFullScreen() {
		DisplayMetrics outMetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
		int mScreenResolutionWidth = outMetrics.widthPixels;
		int mScreenResolutionHeight = outMetrics.heightPixels;
        Log.i(TAG, "setVideoDisplayScreen:" + mScreenResolutionWidth + " mScreenResolutionHeight:"
                + mScreenResolutionHeight );
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(mScreenResolutionWidth , mScreenResolutionHeight, Gravity.CENTER);
        mSurfaceView.setLayoutParams(params);
    } 

	public void sendPlayEventBroadCast(int playIndex,String playType){
		/*
		 * 极简播放器增加广播机制： A、每隔30秒广播一次，视频播放时立即广播一次。广播内容包括：视频名称、视频ID、时间。
		 * B、播放、暂停、快进、快退、退出、网络缓冲、网络缓冲完毕需要进行广播— 广播内容包括目前的事件及时间点、视频名称、视频ID等。
		 */
		
		try {
			Log.d(TAG, "start------------------------------------sendPlayEventBroadCast playIndex="+playIndex+",getPlayingVideoName="+mVideoContrl.getPlayingVideoName()+", playType="+playType);
			Log.d(TAG, "stat---sendPlayEventBroadCast playIndex="+playIndex+",getPlayingVideoName="+mVideoContrl.getPlayingVideoName()+", playType="+playType);
			Intent intent = new Intent();
			intent.setAction("com.tcl.videoplayer.PLAYER_EVENT");
			intent.putExtra("videoId",  mVideoContrl.getPlayingVideoIndex()+"");
//			intent.putExtra("videoName",mVideoContrl.getPlayMediaBean().mName);
//			intent.putExtra("videoImgUrl", mVideoContrl.getPlayMediaBean().mPath);
//			intent.putExtra("currentPosition",mVideoContrl.getCurrentPosition());
//			intent.putExtra("duration", mVideoContrl.getDuration());
			intent.putExtra("playType", playType);
			sendBroadcast(intent);
			Log.d(TAG, "end------------sendPlayEventBroadCast com.tcl.videoplayer.PLAYER_EVENT playType="+playType);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	

	@Override
	public void onExecute(Intent intent) {
		Log.i(TAG,"onExecute(Intent intent)");
		 mFeedback.begin(intent);
	        Log.i(TAG,"get result : \r\n\r\n" + Uri.decode(intent.toURI()));
	        if (intent.hasExtra("_scene")
	                && intent.getStringExtra("_scene").equals("com.tcl.common.mediaplayer.video.UI:VideoPlayerActivity"))
	        {
	            if (intent.hasExtra("_command"))
	            {
	                String command = intent.getStringExtra("_command");
	                if ("play".equals(command))
	                {
	                    // key1 -> 预定义语意$P(_PLAY)，播放操作
	                    String action = intent.getStringExtra("_action");
	                    if ("PLAY".equals(action))
	                    {
	                        mFeedback.feedback(getResources().getString(R.string.Video_Info_Play), Feedback.SILENCE);
	                        if (mVideoContrl != null && mVideoContrl.isMediaPlayerPrepared()) {
	                        	mVideoPlayerHander.sendEmptyMessage(PLAYER_START);
	                        }
	                    }
	                    else if ("PAUSE".equals(action))
	                    {
	                        mFeedback.feedback(getResources().getString(R.string.Video_Info_Pause), Feedback.SILENCE);
	                        if (mVideoContrl != null && mVideoContrl.isMediaPlayerPrepared() && mVideoContrl.isPlaying()) {
	                        	mVideoPlayerHander.sendEmptyMessage(PLAYER_PAUSE);
	                        }
	                    }
	                    else if ("RESUME".equals(action))
	                    {
	                    	mFeedback.feedback(getResources().getString(R.string.voice_playcotinue), Feedback.SILENCE);
	                    	 mVideoPlayerHander.sendEmptyMessage(PLAYER_START);
	                    }
	                    else if ("RESTART".equals(action))
	                    {
	                    	mFeedback.feedback(getResources().getString(R.string.voice_playagain), Feedback.SILENCE);
	                    	mMediaHanler.sendEmptyMessage(START_PLAY);
	                    	//测试下行不行，不行就用seek
	                    }
	                    else if ("SEEK".equals(action))
	                    {
	                    	int position = intent.getIntExtra("position",0);
	                    	mFeedback.feedback(getResources().getString(R.string.voice_jumpto)+parseDate(position), Feedback.SILENCE);
	        				if (mVideoContrl != null && mVideoContrl.isMediaPlayerPrepared()) {
	        					Message mss = mVideoPlayerHander.obtainMessage();
	        					mss.getData().putInt("mPos", position*1000);
	        					Log.i(TAG,"SEEK:"+"mPos="+position*1000);
	        					mss.what = VOICE_SEEK_POSITION;
	        					mVideoPlayerHander.sendMessage(mss);
	        				}
	                    }
	                    else if ("FORWARD".equals(action))
	                    {
	                    	int offset = intent.getIntExtra("offset",0);
	                    	mFeedback.feedback(getResources().getString(R.string.Video_Info_FastForward_30S)+parseDate(offset), Feedback.SILENCE);
	                    	Log.i(TAG,"FORWARD:"+offset+"s");
	        				if (mVideoContrl != null && mVideoContrl.isMediaPlayerPrepared()) {
	        					Message mss = mVideoPlayerHander.obtainMessage();
	        					mss.getData().putInt("mPos", offset*1000);
	        					mss.what = VOICE_FORWORD;
	        					mVideoPlayerHander.sendMessage(mss);
	        				}
	                    }
	                    else if ("BACKWARD".equals(action))
	                    {
	                    	int offset = intent.getIntExtra("offset",0);
	                    	mFeedback.feedback(getResources().getString(R.string.voice_backword)+parseDate(offset), Feedback.SILENCE);
	                    	Log.i(TAG,"BACKWARD:"+offset+"s"); 
	        				if (mVideoContrl != null && mVideoContrl.isMediaPlayerPrepared()) {
	        					Message mss = mVideoPlayerHander.obtainMessage();
	        					mss.getData().putInt("mPos", offset*1000);
	        					mss.what = VOICE_BACKWORD;
	        					mVideoPlayerHander.sendMessage(mss);
	        				}
	                    }
	                    else if ("EXIT".equals(action))
	                    {
	                    	mFeedback.feedback(getResources().getString(R.string.voice_exit), Feedback.SILENCE);
	                    	
	                    	mVideoPlayerHander.sendEmptyMessage(EXIT_VIDEOPLAY);
	                    }
	                }
	                else if ("episode".equals(command))
	                {
	                    // key2 -> 预定义语意$P(_EPISODE)，玄机控制操作
	                    String action = intent.getStringExtra("_action");
	                    if ("PREV".equals(action))
	                    {
	                        mFeedback.feedback(getResources().getString(R.string.voice_last), Feedback.SILENCE);
	                        if (mVideoContrl != null && mVideoContrl.isMediaPlayerPrepared()) {
	        					mMediaHanler.sendEmptyMessage(PRE_VIDEO);
	        				}
	                    }
	                    else if ("NEXT".equals(action))
	                    {
	                        mFeedback.feedback(getResources().getString(R.string.voice_next), Feedback.SILENCE);
	                        if (mVideoContrl != null && mVideoContrl.isMediaPlayerPrepared()) {
	                        	mMediaHanler.sendEmptyMessage(NEXT_VIDEO);
	                        }
	                    }
	                    else if ("INDEX".equals(action))
	                    {
	                        String index = intent.getStringExtra("index");
	                        mFeedback.feedback(getResources().getString(R.string.voice_di) + index + getResources().getString(R.string.voice_ji), Feedback.SILENCE);
	                        
	                        //不执行
	                    }
	                }
	                else if ("list".equals(command))
	                {
	                	 mFeedback.feedback(getResources().getString(R.string.voice_playlist), Feedback.SILENCE);
	                	 KeyEvent keyEvent = new KeyEvent(KeyEvent.ACTION_DOWN,KeyEvent.KEYCODE_DPAD_DOWN);
	                	 onKeyDown(keyEvent.getKeyCode(), keyEvent);
	                }
	                else if ("set".equals(command))
	                {
	                	 mFeedback.feedback( getResources().getString(R.string.voice_morelist), Feedback.SILENCE);
	                	 KeyEvent keyEvent = new KeyEvent(KeyEvent.ACTION_DOWN,KeyEvent.KEYCODE_MENU);
	                	 onKeyDown(keyEvent.getKeyCode(), keyEvent);
	                }
	            }
	        }
	}


	@Override
	public String onQuery() {	
			HashMap<String, String[]> hashMap = new HashMap<String, String[]>();
			hashMap.put("play", new String[] { "$P(_PLAY)" });
			hashMap.put("episode", new String[] { "$P(_EPISODE)" });
			hashMap.put("list", new String[] { getResources().getString(R.string.voice_playlist),getResources().getString(R.string.voice_list),getResources().getString(R.string.voice_xia)});
			hashMap.put("set", new String[] { getResources().getString(R.string.voice_morelist),getResources().getString(R.string.voice_mor),getResources().getString(R.string.voice_menu) });
			String sceneJson="";
			try {
				sceneJson = JsonUtil.makeScenceJson(
						"com.tcl.common.mediaplayer.video.UI:VideoPlayerActivity", hashMap,
						null, null).toString();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Log.i(TAG, "onQuery(): " + sceneJson);
			return sceneJson;
	}

	private String parseDate(int position) {
		String temp = "";
		try {
			int times = position;
			if (times / 3600 >= 1) {
				if (times > 3599) {
					temp += times / 3600 + getResources().getString(R.string.voice_hour);
				}
			}
			times = times % 3600;
			if (times / 60 >= 1) {
				if (times > 59) {
					temp += times / 60 +  getResources().getString(R.string.voice_min);
				}
			}
			if (times % 60 > 0) {
				temp += times % 60 +  getResources().getString(R.string.voice_sec);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return temp;
	}
	
	
	protected Handler setSourceHandler = new Handler() { 
		 public void handleMessage(Message msg) { 
		 super.handleMessage(msg); 

		 Log.i(TAG, "setSourceHandler: msg.what" + msg.what); 

		 if (msg.what == EnTCLCallBackSetSourceMsg.EN_TCL_SET_SOURCE_END_SUCCEED.ordinal()) { //切换信源结束的消息
		 
			 if(isSourceChanged){
				 
			 }else{
				 isSourceChanged = true;
				 mMediaHanler.sendEmptyMessage(START_PLAY);
				 Log.d(TAG,"change source success ,play start");	
				 ischangeSourcePlayed = true;
			 }
			 EnTCLInputSource inputSource = TTvCommonManager.getInstance(VideoPlayerActivity.this).getCurrentInputSource();
			 Log.d(TAG,"now change source successd"+ inputSource);
			
		 } 
		 } 

		 };
}
