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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.WallpaperManager;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.ServiceConnection;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.PowerManager;
import android.os.Process;
import android.os.RemoteException;
import android.tclwidget.TCLAlertDialog;
import android.tclwidget.TCLSeekBar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
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
import com.tcl.common.mediaplayer.audio.ui.AudioPlayerActivity;
import com.tcl.common.mediaplayer.aidl.BackGroundAudioCtrl;
import com.tcl.common.mediaplayer.aidl.BackGroundAudioCtrlCallBack;
import com.tcl.common.mediaplayer.aidl.CommonConst;
import com.tcl.common.mediaplayer.aidl.MediaBean;
import com.tcl.common.mediaplayer.utils.CrossPlatFormAnalyzer;
import com.tcl.common.mediaplayer.utils.MediaPlayerApplication;
import com.tcl.common.mediaplayer.utils.MyToast;
import com.tcl.common.mediaplayer.utils.NotePopupWindow;
import com.tcl.common.mediaplayer.utils.SeekBarPopWindow;
import com.tcl.common.mediaplayer.utils.Utils;
import com.tcl.common.mediaplayer.utils.VolumeController;
import com.tcl.common.mediaplayer.utils.WaitDialogCallBackInterface;
import com.tcl.common.mediaplayer.utils.WaitingDialog;
import com.tcl.common.mediaplayer.video.bookmark.BookMarkConst;
import com.tcl.common.mediaplayer.video.bookmark.BookMarkDB;
import com.tcl.common.mediaplayer.video.bookmark.StartPlayModeDialog;
import com.tcl.common.mediaplayer.video.contrl.IVideoPlayControlHandler;
import com.tcl.common.mediaplayer.video.contrl.VideoPlayControlCallback;
import com.tcl.common.mediaplayer.video.contrl.VideoPlayerContrlConsts;
import com.tcl.common.mediaplayer.R;
import com.tcl.deviceinfo.TDeviceInfo;
import com.tcl.multiscreeninteractiontv.IDLNAService;
import com.tcl.multiscreeninteractiontv.IPlayerCallback;
import com.tcl.tvmanager.TTvCommonManager;
import com.tcl.tvmanager.TTvPictureManager;
import com.tcl.tvmanager.TTvSoundManager;
import com.tcl.tvmanager.TvManager;
import com.tcl.tvmanager.vo.EnTCLAspectRatio;
import com.tcl.tvmanager.vo.EnTCLInputSource;
import com.tcl.tvmanager.vo.EnTCLPicSetting;
import com.tcl.tvmanager.vo.EnTCLWindow;
import com.tcl.tvmanager.vo.VideoWindowRect;
/*import com.tvos.common.PictureManager;
import com.tvos.common.TvManager;
import com.tvos.common.exception.TvCommonException;*/
import com.usagestats.util.behavior.MediaPlayerBehavior;

public class VideoPlayerActivity extends Activity {
	private static final String TAG = "VideoPlayerActivity";
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
	private static final int REFRESH_DD_PLUS_IMAGE = 876929;
	private static final int REFRESH_DTSIMAGE = 876989;
	private static final int REFRESH_DDIMAGE_DISMISS = 876999;
	private static final int REFRESH_SEEKBAR_OP = 1010101;
	private static final int START_PLAY = 4; // 开始进行视频的播放，触发播放器控制接口；
	private static final int PLAYER_UI_INIT = 5;// 更新UI界面，以与播放保持一致；
	private static final int DISMISS_DIALOG_FOR_TIMEOUT = 7;// 播放准备超时；
	private static final int PLAYER_PAUSE = 9;// 播放暂停；
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
	private static final int VIDEO_NOTSUPPORT_SHARE = 4543;// 视频开始或者暂停；
	private static final int VOICE_FORWORD = 4546;// display the pause
	private static final int VOICE_BACKWORD = 4547;// 视频开始或者暂停；
	private static final int VOICE_SEEK_POSITION = 4548;// 视频开始或者暂停；
   private boolean isOnNewIntent = false;
	private int m3DBtStatus = VideoPlayerUIConst.NO_3D;
	private int mClickedBtStatus = VideoPlayerUIConst.CLICKED_NO;
	private TTvPictureManager mPicmanager;
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

	private SurfaceView mSurfaceView = null;
	private SurfaceHolder holder = null;
	private volatile int mCurrIndex = 0;
	private int sEndTime = 0;
	private Timer mTime = new Timer();
	private List<MediaBean> mList;
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
	private FrameLayout mPlayShareButton;
	private LinearLayout mPauseIcon;
	private TextView mPauseTimeTextView;
	private TextView mSubtitleTextView;
	private SurfaceView mSubtitleSurfaceView;
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
	private String mTheLastPath;
	private String mTheLastName;
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
	
	private String clienttype = "";
	private TextView mPlayerStartText,mPlayerPreviousText,mPlayerNextText,mPlayerSetImageText,mPlayerAdvanceText,mPlayerTurn3DText,mPlayShareText,mPlayerInfoText,mPlayerSquenceText;

    	private boolean isFromWeb = false;
    	   private IDLNAService nSreenTVService;
    	   private TTvSoundManager soundManager;
    	   private String playStatus = "TRANSITIONING";
    	   private boolean isDMR = false;
    	   private boolean isDLNA = false;
    private boolean isChangeNotStart = true;
	private boolean isMediaFile3D = true;
	private String[] splitArrayStrings;
    	//   private AudioManager audiomanager;
	// private boolean mIsVolumeClick = false;

	// private int mIndex;

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

		TTvCommonManager.getInstance(this).setInputSource(EnTCLInputSource.EN_TCL_STORAGE);
	    soundManager = TTvSoundManager.getInstance(this);
	    mPicmanager = TTvPictureManager.getInstance(this);
//	    audiomanager=(AudioManager)getSystemService(Context.AUDIO_SERVICE);  
//		//设置背光or自然光
//		mPictureManager= TvManager.getPictureManager();
//		myGraphics = new GraphicsManager();
//		int mSaveBacklight = getSavedBackLight(this);
//		if (mSaveBacklight == -1) {
//			saveBackLight(this, getBackLight());
//			mSaveBacklight = getSavedBackLight(this);
//		}
//		if (mPictureManager != null) {
//
//			Long time = System.currentTimeMillis();
//			Utils.writeCurrentTime(time);	
//			myGraphics.resumeVideoBackLight( getBackLight(),
//					 mSaveBacklight, mPictureManager,time);
//			Utils.printLog(TAG, "LUNCHER backlight" + getBackLight());
//		}
//		if (getBooleanISNatrueLightON(this)) {
//			try {
//				TvManager.setTvosInterfaceCommand("DBC_ON");
//				Log.i("Naturelight---- ", "DBC_ON");
//
//			} catch (TvCommonException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
         
	
		
		showWatingDialog();
		TDeviceInfo devinfo = TDeviceInfo.getInstance();
		clienttype = devinfo.getClientType(devinfo.getProjectID());
		Utils.printLog(TAG, "clienttype is"+clienttype);
		if(clienttype!=null){
			splitArrayStrings = clienttype.split("-");
		}

		
		if (!getPlayList(getIntent())) {
			exitPlayforNoPlayList();
			return;
		}
		if (mCurrIndex < 0) {
			mCurrIndex = 0;
		}
		Utils.printPlayList(mList);
		// holder.setFixedSize(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		// WindowManager.LayoutParams.FLAG_FULLSCREEN);
		seekBarPopWindow = new SeekBarPopWindow(this, mMediaHanler);
		popWindow = new NotePopupWindow(this, mMediaHanler);
		findView();
		mVideoContrl = new IVideoPlayControlHandler(this);
		mVideoContrl.registerCallback(mCallback);
		MediaPlayerApplication application = (MediaPlayerApplication) getApplication();
		application.setVideoContrl(mVideoContrl);
	
		mBookMark = new BookMarkDB(this);

		mSurfaceView = (SurfaceView) this.findViewById(R.id.surfaceView);
		// mSurfaceView.invalidate();
		holder = mSurfaceView.getHolder();
		holder.addCallback(mVideoContrl);
		holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

		mTime = new Timer();
		mTime.schedule(mTimerTask, 0, 1000);

		IntentFilter intentFilter2 = new IntentFilter();
		intentFilter2.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
		this.registerReceiver(mConnectionChangeReceiver, intentFilter2);

		IntentFilter intentFilter3 = new IntentFilter();
		intentFilter3.addAction(VideoPlayerUIConst.COLLECTION_OPERATE_DONE);
		intentFilter3.addAction(VideoPlayerUIConst.ATV_OSD_OPEN);
		intentFilter3.addAction(CommonConst.CLOSE_VIDEO_PLAY);
		intentFilter3.addAction(CommonConst.HOME_PRESSED);
		intentFilter3.addAction(CommonConst.TV_PRESSED);
		intentFilter3.addAction(CommonConst.CHANGE_SOURCE_PRESSED);
		intentFilter3.addAction(Intent.ACTION_SHUTDOWN);
		intentFilter3.addAction(CommonConst.VOICE_ASSIST_PRESSED); //for 5507 voice control disable 20150311
		this.registerReceiver(myCollectionBroadcastReceiver, intentFilter3);

		IntentFilter intentFilter4 = new IntentFilter();
		intentFilter4.addAction(CommonConst.VOICE_CONTROL);
		this.registerReceiver(mVoiceReceiver, intentFilter4);
		sendStopWidgetMusicBroadcast();
     
       if(isDMR){
    	   bindTVnSreenService();
    	   mVideoContrl.setPlayType(VideoPlayerContrlConsts.MEDIA_SEQUENCE_PLAY);
       }else{
			mVideoContrl.setPlayType(getSavedPlayerType());
			mPlayerSequenceButton
					.setBackgroundResource(mPlayerTypeIcon[mVideoContrl
							.getPlayType()]);
			mPlayerSquenceText.setText(mPlayerTypeText[mVideoContrl.getPlayType()]);
		}
      
	
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

				// mVideoContrl.play(mList, mCurrIndex, holder);
				new Thread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						mVideoContrl.play(mList, mCurrIndex, holder);
					}
				}).start();

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
				Utils.printLog(TAG, "START_PLAY_FROM_BREAK with pos = "
						+ mBreakPos);

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
				new MyToast(VideoPlayerActivity.this, VideoPlayerActivity.this
						.getResources().getString(
								R.string.media_player_not_seekable)).show();

			} else if (msg.what == CommonConst.media_player_no_audio) {
				new MyToast(VideoPlayerActivity.this, VideoPlayerActivity.this
						.getResources().getString(
								R.string.audiotrack_iszero)).show();

			} else if (msg.what == CommonConst.media_player_no_video) {
				new MyToast(VideoPlayerActivity.this, VideoPlayerActivity.this
						.getResources().getString(
								R.string.videotrack_iszero)).show();

			}  else if (msg.what == CommonConst.media_player_already_firstfiles) {
				new MyToast(VideoPlayerActivity.this, VideoPlayerActivity.this
						.getResources().getString(
								R.string.already_first_files)).show();

			}  else if (msg.what == CommonConst.media_player_already_lastfiles) {
				new MyToast(VideoPlayerActivity.this, VideoPlayerActivity.this
						.getResources().getString(
								R.string.already_last_files)).show();

			}   else if (msg.what == CommonConst.mediafile_notsupport3d) {
				new MyToast(VideoPlayerActivity.this, VideoPlayerActivity.this
						.getResources().getString(
								R.string.videonothreeD)).show();

			}  else if (msg.what == VIDEO_NOTSUPPORT_SHARE) {
				new MyToast(VideoPlayerActivity.this, VideoPlayerActivity.this
						.getResources().getString(
								R.string.four_k_video_notshare)).show();

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
		if (!getPlayList(intent)) {
			exitPlayforNoPlayList();
			return;
		}
	
		if (mVideoContrl != null&& mVideoContrl.isFinishInit()) {
			mVideoContrl.setFinishInit(false);
			isOnNewIntent = true;
			mVideoContrl.releaseMediaPlayer();
		}
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

		if (intent.getAction().equals(Intent.ACTION_VIEW)) {
			isNeedSave = intent.getBooleanExtra("isNeedSave", false);
			m3DModeSet = intent.getIntExtra("3dmode", 0);
			is3DEnabled = new CrossPlatFormAnalyzer().isNeed3DFunction();
			Utils.printLog(TAG,
					" new CrossPlatFormAnalyzer().isNeed3DFunction(); is3DEnabled ="
							+ is3DEnabled);
			//判断平台是否支持3D
			TDeviceInfo devinfo = TDeviceInfo.getInstance();
			int is3dState = devinfo.get3DStatus(devinfo.getProjectID());
			if(is3dState == 1){
				is3DEnabled = true;
			}else if(is3dState == 0){
				is3DEnabled = false;	
			}
			Utils.printLog(TAG,"devinfo.get3DStatus; is3DEnabled ="+ is3DEnabled);
			
			int m = intent.getIntExtra("playApp", 0);
			Log.d(TAG,"now intent.getIntExtra playApp is"+m);
			if(intent.getIntExtra("playApp", 0)==1){
				isDMR = true;
			}else if(intent.getIntExtra("playApp", 0)==2){
				isDLNA = true;
			}
			
			if (intent.getBooleanExtra("IsMediaBean", false)) {
                Log.d(TAG,"is mediabean");
				mList = intent.getParcelableArrayListExtra("playlist");
				if (mList == null)
					return false;
				mCurrIndex = intent.getIntExtra("index", 0);
				if(mList != null){
					String name = mList.get(mCurrIndex).mName;
					if(name.equalsIgnoreCase("")){
						String path = mList.get(mCurrIndex).mPath;
						mList.get(mCurrIndex).mName = Utils.getRealName(path);
					}
					Log.d(TAG,"now do not need to decode name,index 0 name is"+mList.get(mCurrIndex).mName);
				}
				
			} else {
				mList = new ArrayList<MediaBean>();
				if (intent.getType().equals(
						"application/vnd.tcl.playlist-video")) {
					List<Uri> urilist = intent
							.getParcelableArrayListExtra("playlist");
					 Log.d(TAG,"is application/vnd.tcl.playlist-video");
					if (urilist == null)
						return false;
					for (Uri u : urilist) {
						String path = Utils.getUriPath(u);
						MediaBean bean = new MediaBean();
						bean.mPath = path;
						Utils.printLog(TAG, "un URLDecoder path =" + path);
						if (path.startsWith("http")) {
							try {
								path = URLDecoder.decode(path,"utf-8");
							} catch (UnsupportedEncodingException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							bean.mName = Utils.getRealName(path);
						} else {
							bean.mName = Utils.getRealName(path);
						}

						mList.add(bean);
					}
					mCurrIndex = intent.getIntExtra("index", 0);

				} else if (intent.getType().contains("video/")) {
					 Log.d(TAG,"is video/");
					Uri data = intent.getData();
					if (data == null)
						return false;
					String path = Utils.getUriPath(data);
					MediaBean bean = new MediaBean();
					bean.mPath = path;
					Utils.printLog(TAG, "un URLDecoder path =" + path);
					if (path.startsWith("http")) {
						try {
							path = URLDecoder.decode(path,"utf-8");
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
			if (mList.size() > 0) {
				
				MediaBean bean = mList.get(0);
				if(bean!=null){	
					if (bean.mPath.startsWith("http")) {
						isFromWeb = true;
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
      	if(	mClickedBtStatus == VideoPlayerUIConst.CLICKED_3D||mClickedBtStatus == VideoPlayerUIConst.CLICKED_ADVANCE||mClickedBtStatus == VideoPlayerUIConst.CLICKED_IMAGE||mClickedBtStatus == VideoPlayerUIConst.CLICKED_VIDEOINFO){
			mVideoPlayerHander.sendEmptyMessage(DISPLAY_PLAYER_CONTROL_BUTTONS);
			return ;
		}
		if (isOnNewIntent) {
			mMediaHanler.sendEmptyMessage(START_PLAY);
			Utils.printLog(TAG, "isOnNewIntent PLAY_ONCE");
			isOnNewIntent = false;
		}

		// 在线播放，需重新初始化播放信息
		Utils.printLog(TAG, "onResume end");
	}

	@Override
	protected void onPause() {
		super.onPause();
		Utils.printLog(TAG, "onPause start");
		if (mIsInternetDisconnect || mIsSaveClicked
				|| m3DBtStatus == VideoPlayerUIConst.SHOW_MENU_3D
				|| m3DBtStatus == VideoPlayerUIConst.SHOW_VOLUME_3D
				|| m3DBtStatus == VideoPlayerUIConst.NO_MENU_3D) {
			// 如果是网络是网络断开触发的onPause(),不做任何处理
			return;
		}
		// 电影在不可见后，不能播放，需关闭Activity.
		dismissWaittingDialog();

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
		if (isFinishing() == false) {
			exitVideoPlay();
			VideoPlayerActivity.this.finish();
		}
		try {
			if(mConnectionChangeReceiver!=null){
				unregisterReceiver(mConnectionChangeReceiver);
			}
			if(myCollectionBroadcastReceiver!=null){
				unregisterReceiver(myCollectionBroadcastReceiver);
			}
			if(mVoiceReceiver!=null){
				unregisterReceiver(mVoiceReceiver);
			}
			if(mVideoContrl!=null){
				mVideoContrl.unRegisterReceiver();
			}

		} catch (Exception il) {

			il.printStackTrace();
		}
		if(nSreenConnection!=null&&isDMR){
			Utils.printLog(TAG, " unbindService  nSreenConnection");
			try {
			if(nSreenTVService!=null){
			    try {
					nSreenTVService.unregisterPlayerCallback();
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			    nSreenTVService = null;
			}
		    unbindService(nSreenConnection);
			} catch (Exception e) {
			  
				e.printStackTrace();
			}
		}
		mVideoPlayerHander.sendEmptyMessage(DISSMISS_WAIT_DIALOG);
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
        if(mList != null){
		if (mList.size() > 0 && mCurrIndex < mList.size() && mCurrIndex >= 0) {

			try {
				String mCurrPath = mList.get(mCurrIndex).mPath;
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
		VideoPlayerActivity.this.sendBroadcast(new Intent(
				"com.android.tcl.videoexit"));
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

		if (mList != null) {
			mList.clear();
			mList = null;
		}

		if (holder != null) {
			holder = null;
		}
		mSurfaceView = null;

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
		if (mTheLastPosition < 0 || mTheLastPosition>14400000) {
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
		if (volumLay.getVisibility() == View.VISIBLE) {
			mVideoPlayerHander.sendEmptyMessage(DISSMISS_VOLUME);
			if (mClickedBtStatus == VideoPlayerUIConst.CLICKED_VOLUME) {
				// mIsVolumeClick = false;
				mVideoPlayerHander
						.sendEmptyMessage(DISPLAY_PLAYER_CONTROL_BUTTONS);
			}
		} else if (mPlayerControlLayout.getVisibility() == View.VISIBLE || mPlayerProgressLayout.getVisibility() == View.VISIBLE ) {
			mVideoPlayerHander.sendEmptyMessage(DISMISS_PLAYER_CONTROL_BUTTONS);
		} else {

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
		playStatus = "STOPPED";
		dmrSetPlayStatus(playStatus);
//		Tv3DManager  tv3DManager = Tv3DManager.getInstance(this);
//		tv3DManager.reset3DMode();
/*		ThreeDVideoDisplayFormat setToMod = ThreeDVideoDisplayFormat.valueOf(ThreeDVideoDisplayFormat.E_ThreeD_Video_DISPLAYFORMAT_NONE.toString());
		
		Tv3DManager  tv3DManager = Tv3DManager.getInstance(this);
	    tv3DManager.setDisplayFormat(setToMod);*/
		if (mVideoContrl!=null&&mVideoContrl.isMediaPlayerPrepared()) {
			mTheLastPosition = mVideoContrl.getCurrentPosition();
			sEndTime = mVideoContrl.getDuration();
			Utils.printLog(TAG, "mTheLastPosition " + mTheLastPosition
					+ "  sEndTime =" + sEndTime);
		} else {
			mTheLastPosition = VideoPlayerContrlConsts.GET_CURRENTPOSITION_ERROR;
		}
		Utils.printLog(TAG, "exitVideoPlay");
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

		
	
//		try {
//			if (mPictureManager != null ) {
//				Long time = System.currentTimeMillis();
//				Utils.writeCurrentTime(time);	
//				myGraphics.resumeVideoBackLight( getSavedBackLight(this),
//						 getBackLight(), mPictureManager,time); 
//			}
//			if (getBooleanISNatrueLightON(this)) {
//			    TvManager.setTvosInterfaceCommand("DBC_OFF");
//			}
//		
//		} catch (TvCommonException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
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

		mSubtitleSurfaceView = (SurfaceView) findViewById(R.id.video_subtitle_surface);
		mSubtitleSurfaceView.getHolder().setFormat(PixelFormat.RGBA_8888);
		mSubtitleSurfaceView.setBackgroundColor(Color.TRANSPARENT);
		mSubtitleSurfaceView.setVisibility(View.VISIBLE);
		mSubtitleSurfaceView.getHolder().addCallback(mSubtiteHolder);

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

	
		mPlayShareButton = (FrameLayout)findViewById(R.id.player_share);
		mPlayShareButton.setOnClickListener(buttonClickListener);
		mPlayShareButton.setOnFocusChangeListener(buttonOnfocusListener);
		TvManager tvManager = TvManager.getInstance(this);
		String supportShell = tvManager.getProperty().get("ro.sita.model.MediaPlayer.MediaShare", "null");
		if(supportShell!=null&&!supportShell.equalsIgnoreCase("null")){
			if(supportShell.equalsIgnoreCase("FALSE")){
				mPlayShareButton.setVisibility(View.GONE);
				Utils.printLog(TAG, "MediaPlayer.MediaShare is false");
			}
		}
		if(isDMR){
			mPlayShareButton.setVisibility(View.GONE);
		}
		
		mPlayerSequenceButton = (FrameLayout) findViewById(R.id.player_sequence);
		mPlayerSequenceButton.setOnClickListener(buttonClickListener);
		mPlayerSequenceButton.setOnFocusChangeListener(buttonOnfocusListener);
		mPlayerSaveButton = (ImageButton) findViewById(R.id.player_save);
		mPlayerSaveButton.setOnClickListener(buttonClickListener);
		mPlayerSaveButton.setOnFocusChangeListener(buttonOnfocusListener);
		mPlayerStartButton = (FrameLayout) findViewById(R.id.player_start);
		mPlayerStartButton.setOnClickListener(buttonClickListener);
		mPlayerStartButton.setFocusableInTouchMode(true);
		
//		mPlayerStartButton.requestFocus();
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
		mPlayerControlLayout.setVisibility(View.INVISIBLE);
		mPlayerProgressLayout.setVisibility(View.INVISIBLE);
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
		mPlayShareText = (TextView)findViewById(R.id.player_share_text);
		mPlayerSquenceText =(TextView)findViewById(R.id.player_sequence_text);
		
		
		if(isDMR){
			mPlayerPrevious.setVisibility(View.GONE);
		    mPlayerNext.setVisibility(View.GONE);
			mPlayerInfo.setVisibility(View.GONE);
			mPlayerAdvanceButton.setVisibility(View.GONE);
			mPlayerSequenceButton.setVisibility(View.GONE);
		}
		if(isDLNA ||isFromWeb){
			mPlayerInfo.setVisibility(View.GONE);
			mPlayShareButton.setVisibility(View.GONE);
		}
//		if(mPlayShareButton.getVisibility() == View.VISIBLE){
//			mPlayShareButton.requestFocus();
//			
//		}else{
//			mPlayerStartButton.requestFocus();	
//		}
		
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
				
				if(sEndTime>0){
					final long time = progress *(long)sEndTime /Utils.SeekBarLength;
					Utils.printLog(TAG, "SeekBarListener mseekbariwidth="+mPlayerSeekar.getWidth()+" onProgressChanged sEndTime ="+sEndTime+"   time ="+time);
					new Thread(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub
							mVideoContrl.seekTo((int) time);

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
				if(mPlayShareButton.getVisibility() == View.VISIBLE){
					mPlayShareButton.requestFocus();
					
				}else{
					mPlayerStartButton.requestFocus();	
				}
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
		if (mList == null || mList.size() <= 0 || mCurrIndex >= mList.size()) {
			return;
		}
		// mVideoPlayerHander.sendEmptyMessage(SHOW_WAIT_DIALOG);
		showWatingDialog();
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
		mDDIcon.setVisibility(View.INVISIBLE);
     try{
		MediaBean mediaBean = mList.get(mCurrIndex);
		mPlayerStartButton
				.setBackgroundResource(R.drawable.player_pause_selector);
		mPauseIcon.setVisibility(View.INVISIBLE);
		mSubtitleTextView.setText("");
		updateControlButtonStatus();
		if (!isNeedSave) {
			// mPlayerSaveButton.setEnabled(false);
			// mPlayerSaveButton.setFocusable(false);
			mPlayerSaveButton.setVisibility(View.GONE);
		} else {
			// 如果在线播放，但是网络不可用，则会退出播放；

			mPlayerSaveButton.setVisibility(View.VISIBLE);
			if (!Utils.isNetWorkUsable(VideoPlayerActivity.this)) {
				mToast = new MyToast(VideoPlayerActivity.this, getResources()
						.getString(R.string.media_player_netUnausable));
				mToast.show();
				mVideoPlayerHander.sendEmptyMessage(EXIT_VIDEOPLAY);
				return;
			}

			// 如果已经收藏显示取消收藏icon
			if (mediaBean.mIsSaved == 1) {
				mPlayerSaveButton
						.setBackgroundResource(R.drawable.player_cancel_save_selector);
			} else {
				mPlayerSaveButton
						.setBackgroundResource(R.drawable.player_save_selector);
			}
		}

		final String name = mediaBean.mName;
		mName.setText(name);
		mPlayBeginTime.setText(Utils.getTimeShort(0));
		sEndTime = 0;
		mPlayEndTime.setText(Utils.getTimeShort(0));
		
		 mPlayerSeekar.setProgress(0);
	     mPlayerSeekar.setEnabled(false);
		if (mVideoContrl.isMediaPlayerPrepared()) {
			sEndTime = mVideoContrl.getDuration();
			Utils.printLog(
					TAG,
					"-sEndTime=" + sEndTime + ";timesort ="
							+ Utils.getTimeShort(sEndTime));
			if (sEndTime > 0) {
				Utils.printLog(TAG,
						"get mPlayEndTime =" + Utils.getTimeShort(sEndTime));
				mPlayEndTime.setText(Utils.getTimeShort(sEndTime));
			}
		}

		mTheLastName = name;
		mTheLastPath = mediaBean.mPath;
     }catch (Exception e) {
			e.printStackTrace();
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
			Utils.printLog(TAG, "refresh currentPostion ");
			currentPostion = mVideoContrl.getCurrentPosition();

			Utils.printLog(TAG, "currentPostion =" + currentPostion);
			if (sEndTime <= 0) {
				duration = mVideoContrl.getDuration();
				sEndTime = duration;
				mVideoPlayerHander.sendEmptyMessage(REFRESH_TOTAL_TIME);
			}
			duration = mVideoContrl.getDuration();
			Utils.printLog(TAG, "sEndTime=" + sEndTime);
			Utils.printLog(TAG, "duration=" + duration);
			if (currentPostion != VideoPlayerContrlConsts.GET_CURRENTPOSITION_ERROR) {
				Message mss = mVideoPlayerHander.obtainMessage();
				mss.getData().putInt("currentPos", currentPostion);
				mss.what = REFRESH_CURRENT_TIME;
				mVideoPlayerHander.sendMessage(mss);
				
			}
			if(sEndTime > 0){
				mPlayerSeekar.setKeyProgressIncrement(Utils.SeekBarLength*40000/sEndTime);
			}else{
				mPlayerSeekar.setKeyProgressIncrement(0);
			}

		}
	};

	private TimerTask mTimerTask = new TimerTask() {
		public void run() {
			Utils.printLog(TAG, "mTimerTask is running");
			if (mPlayerProgressLayout.getVisibility() == View.VISIBLE
					&& mVideoContrl.isMediaPlayerPrepared() && !mIsSeeking
					&& isBreakDialogDissmiss) {
				Utils.printLog(TAG, "runOnUiThread");
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
		  mPlayShareText.setVisibility(View.INVISIBLE);
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

       
		if(splitArrayStrings[1]==null||splitArrayStrings[1].equals("CN")||splitArrayStrings[1].equals("HK")){   //now is CN
			try {   // 20141016 modify for 3D
				
				Log.d("===========20141016","now to start 3D setting use new method");
				Intent intent=new Intent();
	            intent.setAction("com.tcl.ShortcutKey");
	            intent.putExtra("Type", "3D");
	            sendBroadcast(intent);
			} catch (ActivityNotFoundException acti) {
				acti.printStackTrace();
				new MyToast(VideoPlayerActivity.this, VideoPlayerActivity.this
						.getResources().getString(R.string.NoFuntion)).show();
			}
		}else{    // now is AU TCL-AU-NT667K-S1
		Intent intent = new Intent();
		intent.setAction("android.intent.action.tcl.show3dmenu");
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.putExtra("start3DType", 1);
		try {
			VideoPlayerActivity.this.startActivity(intent);
		} catch (ActivityNotFoundException acti) {
			acti.printStackTrace();
			new MyToast(VideoPlayerActivity.this, VideoPlayerActivity.this
					.getResources().getString(R.string.NoFuntion)).show();
		}
		}

	
		
		
	

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
					  mPlayerTurn3DText.setText(R.string.turnThree);
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
					  mPlayerNextText.setText(R.string.Video_Info_Next_Film);
					break;
				case R.id.player_sequence:

					  mPlayerSquenceText.setVisibility(View.VISIBLE);
					  mPlayerSquenceText.setText(mPlayerTypeText[mVideoContrl.getPlayType()]);

					break;
				case R.id.player_previous:
					  mPlayerPreviousText.setVisibility(View.VISIBLE);
					  mPlayerPreviousText.setText(R.string.Video_Info_Last_Film);
				
					break;
				case R.id.player_advance:
					  mPlayerAdvanceText.setVisibility(View.VISIBLE);
					  mPlayerAdvanceText.setText(R.string.AdvancedSetTip);
					break;
				case R.id.player_share:
					mPlayShareText.setVisibility(View.VISIBLE);
					mPlayShareText.setText(R.string.video_share);
					break;
				case R.id.player_setimage:
					 mPlayerSetImageText.setVisibility(View.VISIBLE);
					 mPlayerSetImageText.setText(R.string.image);
					break;
				case R.id.player_info:
					 mPlayerInfoText.setVisibility(View.VISIBLE);
					 mPlayerInfoText.setText(R.string.Video_Info_detail);
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
				if(!isMediaFile3D){
					mMediaHanler.sendEmptyMessage(CommonConst.mediafile_notsupport3d);
					return;
				}
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
				if(splitArrayStrings[1]==null||splitArrayStrings[1].equals("CN")||splitArrayStrings[1].equals("HK")){   //now is CN
					Log.d(TAG,"now is CN method======");
					try {
						Intent intent=new Intent();
						intent.setAction("com.tcl.ShortcutKey");
						intent.putExtra("Type", "Setting");
						intent.putExtra("Catagory", "picture");
						sendBroadcast(intent);
					} catch (Exception e) {
						e.printStackTrace();
						new MyToast(VideoPlayerActivity.this, VideoPlayerActivity.this
								.getResources().getString(R.string.NoFuntion)).show();
					}
				}else{
					Log.d(TAG,"now is AU method======");
					try {
					  Intent intent_advance = new Intent();
				      intent_advance.setAction("android.intent.action.PICTURE");   
				      startActivity(intent_advance);
					} catch (Exception e) {
						e.printStackTrace();
						new MyToast(VideoPlayerActivity.this, VideoPlayerActivity.this
								.getResources().getString(R.string.NoFuntion)).show();
					}
				}
				
	
//                //test share
//			    Intent sendIntent = new Intent();  
//				sendIntent.setAction(Intent.ACTION_SEND);  
//				Utils.printLog(TAG, "share url -------"+mTheLastPath);
//				sendIntent.putExtra(Intent.EXTRA_STREAM,mTheLastPath);
//				sendIntent.setType("mkv/video");  
//			
//				try {
//					startActivity(sendIntent);  
//				} catch (ActivityNotFoundException acti) {
//					acti.printStackTrace();
//					new MyToast(VideoPlayerActivity.this, VideoPlayerActivity.this
//							.getResources().getString(R.string.NoFuntion)).show();
//				}
				break;
			}
			case R.id.player_share:
				
				if (mVideoContrl != null&& mVideoContrl.isMediaPlayerPrepared()) {
			       int[] mMediaInfo  =  new int [9];
			       mMediaInfo	= mVideoContrl.getMediaInfo();	
			       if(mMediaInfo!=null){
						 int mVideoWidth = mMediaInfo[5];
						 int mVideoHeight = mMediaInfo[6];
						 Utils.printLog(TAG, "mVideoWidth is"+mVideoWidth);
						 Utils.printLog(TAG, "mVideoWidth is"+mVideoHeight);
						 if(mVideoWidth>=3840){
							 Utils.printLog(TAG, "4k video not support share");
							 mMediaHanler.sendEmptyMessage(VIDEO_NOTSUPPORT_SHARE);
							 break;
						 }
			       }
				}
				Intent sendIntent = new Intent();  
				sendIntent.setAction(Intent.ACTION_SEND);  
				Utils.printLog(TAG, "share url -------"+mTheLastPath);
				Uri path = Uri.parse(mTheLastPath);
				sendIntent.putExtra(Intent.EXTRA_STREAM,path);
				sendIntent.setType("video/*");
				sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Share"); 
				sendIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
				try {
					startActivity(sendIntent);  
				} catch (ActivityNotFoundException acti) {
					acti.printStackTrace();
					new MyToast(VideoPlayerActivity.this, VideoPlayerActivity.this
							.getResources().getString(R.string.NoFuntion)).show();
				}
				break;
				
	         case R.id.player_info: {	
	     
	        String mVideosize = "";
	        mVideosize = getFileByte(mTheLastPath);
	 	    mClickedBtStatus = VideoPlayerUIConst.CLICKED_VIDEOINFO;
	 		mVideoPlayerHander.sendEmptyMessage(DISMISS_PLAYER_CONTROL_BUTTONS);
	 		
	 		Intent intent_videoinfo = new Intent("android.tcl.videoinfo.notpvr.show");
	 		  intent_videoinfo.putExtra("video_name", mTheLastName);
	 		  intent_videoinfo.putExtra("video_size", mVideosize);
	 		startActivity(intent_videoinfo);     
	 		break;
	 			}
			case R.id.player_advance:
				mClickedBtStatus = VideoPlayerUIConst.CLICKED_ADVANCE;
				mVideoPlayerHander.sendEmptyMessage(DISMISS_PLAYER_CONTROL_BUTTONS);
				// advanceDialog = new
				// AdvanceSetDialog(VideoPlayerActivity.this);
				// advanceDialog.show();
				Intent intent_advance = new Intent("android.tcl.sub.notpvrshow");
				// new MyToast(VideoPlayerActivity.this,
				// VideoPlayerActivity.this
				// .getResources().getString(R.string.NoFuntion)).show();
				startActivity(intent_advance);
				break;

			}

		}
	}
	
	private String getFileByte(String path) {
		long fileS = 0;
		String fileSizeString = "";
		File f = new File(path);
		try {
//			FileInputStream fis = new FileInputStream(f);
			fileS = f.length();
			Utils.printLog(TAG, "file length is "+fileS);
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

		Intent intent = new Intent(VideoPlayerUIConst.COLLECTION_OPERATE);
		boolean res = true;
		if (mList != null && mList.size() > 0) {
			if (mList.get(mCurrIndex).mIsSaved == 0) {
				mIsSaveClicked = true;
				intent.putExtra(VideoPlayerUIConst.ADD_OR_DEL,
						VideoPlayerUIConst.ADD_COLLECTION);
				res = true;
			} else {
				intent.putExtra(VideoPlayerUIConst.ADD_OR_DEL,
						VideoPlayerUIConst.DEL_COLLECTION);
				res = false;
			}

			intent.putExtra(VideoPlayerUIConst.COLLECTION_ADDRESS,
					mList.get(mCurrIndex).mPath);
			intent.putExtra(VideoPlayerUIConst.COLLECTION_APP_MODE,
					mList.get(mCurrIndex).mVodType);
			Utils.printLog(
					TAG,
					"Collection Url ="
							+ intent.getStringExtra(VideoPlayerUIConst.COLLECTION_ADDRESS)
							+ "  appmode =" + mList.get(mCurrIndex).mVodType);
			mPlayerSaveButton.setEnabled(false);
		}
		Log.d(TAG, "player_save  mIsSaveClicked =" + mIsSaveClicked);
		sendBroadcast(intent);
		return res;
	}

	private void playPause() {
		Utils.printLog(TAG, "playPause");
		if (mVideoContrl != null && mVideoContrl.isMediaPlayerPrepared()
				&& mVideoContrl.isPlaying()) {
			Utils.printLog(TAG, "playPause control into");
			mVideoContrl.pause();
			if (!mVideoContrl.isPlaying()) {
				mPauseIcon.setVisibility(View.VISIBLE);
				mPauseTimeTextView.setVisibility(View.VISIBLE);
				mPauseIcon.requestLayout();
				mPauseIcon.invalidate();
				Utils.printLog(TAG, "mPauseIcon.setVisibility(View.VISIBLE);");
				mPlayerStartButton
						.setBackgroundResource(R.drawable.player_start_selector);
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
		playStatus = "PAUSED_PLAYBACK";
		dmrSetPlayStatus(playStatus);
	
	
	}

	private void playStart() {
		Utils.printLog(TAG, "playStart");
		if (mVideoContrl != null && mVideoContrl.isMediaPlayerPrepared()
				&& !mVideoContrl.isPlaying()) {
			Utils.printLog(TAG, "playStart control into");
			mVideoContrl.start();
			if (mVideoContrl.isPlaying()) {
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
		playStatus = "PLAYING";
		dmrSetPlayStatus(playStatus);
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

	private void userStateRecord() {
		if (userStateTimeStart == -1) {
			userStateTimeStart = System.currentTimeMillis();
		} else {
			long userStateTimeEnd = System.currentTimeMillis();
			long peroid = userStateTimeEnd - userStateTimeStart;
			Utils.printLog(TAG, "userStateRecord for period " + peroid);
			try{
				if (peroid >= 60000 && mTheLastName != null) {// 播放大于一分钟；
					Utils.printLog(TAG, "userStateRecord start for " + mTheLastName);
					MediaPlayerBehavior playerBehavior = new MediaPlayerBehavior(
							VideoPlayerActivity.this.getContentResolver());
					playerBehavior.setStartplayertime(Utils
							.getTimeAll(userStateTimeStart));
					playerBehavior.setEndplayertime(Utils
							.getTimeAll(userStateTimeEnd));
					playerBehavior.setPlaycontent(mTheLastName);
					playerBehavior.record();
					Utils.printLog(TAG, "userStateRecord end for " + mTheLastName);

				}
				
				userStateTimeStart = userStateTimeEnd;
			}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
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
		Log.d(TAG, "============================keyCode=" + keyCode);
		isVolumeChangedKeyClicked = false;
		if (keyCode == KeyEvent.KEYCODE_MENU) {

			if (volumLay.getVisibility() == View.VISIBLE) {
				mVideoPlayerHander.sendEmptyMessage(DISSMISS_VOLUME);
				mVideoPlayerHander.sendEmptyMessageDelayed(
						DISPLAY_PLAYER_CONTROL_BUTTONS, 0);

			} else if (mPlayerControlLayout.getVisibility() == View.VISIBLE) {
				mVideoPlayerHander.sendEmptyMessageDelayed(
						DISMISS_PLAYER_CONTROL_BUTTONS, 0);

			} else {
				mVideoPlayerHander.sendEmptyMessageDelayed(
						DISPLAY_PLAYER_CONTROL_BUTTONS, 0);
			}
			return true;

		} else if ((event.getKeyCode() == KeyEvent.KEYCODE_ENTER || event
				.getKeyCode() == KeyEvent.KEYCODE_DPAD_CENTER)) {
			Utils.printLog(TAG, "Video Enter Press!");
			if (volumLay.getVisibility() == View.VISIBLE) {
				Utils.printLog(TAG, "Enter volume see!");
				mVideoPlayerHander.sendEmptyMessage(DISSMISS_VOLUME);
				if (mClickedBtStatus == VideoPlayerUIConst.CLICKED_VOLUME) {
					// mIsVolumeClick = false;
					mVideoPlayerHander
							.sendEmptyMessage(DISPLAY_PLAYER_CONTROL_BUTTONS);
				}
				// return true;
			} else if (mPlayerControlLayout.getVisibility() == View.INVISIBLE
					&& volumLay.getVisibility() == View.INVISIBLE) {
				mVideoPlayerHander.sendEmptyMessage(VIDEO_PLAY_START_OR_PAUSE);
				return true;
			}

		} else if ((event.getKeyCode() == KeyEvent.KEYCODE_DPAD_RIGHT)
				&& mPlayerControlLayout.getVisibility() == View.INVISIBLE) {

//			if (mAudioSkin != null && mAudioSkin.isConnectionOk()) {
//				mAudioSkin.volumeUp();
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
					
					mPlayerSeekar.onKeyDown(KeyEvent.KEYCODE_DPAD_RIGHT, null);
		        }
			}

			return true;
		} else if ((event.getKeyCode() == KeyEvent.KEYCODE_DPAD_LEFT)
				&& mPlayerControlLayout.getVisibility() == View.INVISIBLE) {

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
		else if (keyCode == 115 && mIsSaveClicked == false
				&& mVideoContrl != null && mVideoContrl.isVOD()) {
			// 遥控器上的收藏
			doSave();
			return true;
		} else if ((event.getKeyCode() == KeyEvent.KEYCODE_DPAD_RIGHT
				|| (event.getKeyCode() == 804) || (event.getKeyCode() == 805)
				|| (event.getKeyCode() == 806) || (event.getKeyCode() == 807))
				&& mPlayerControlLayout.getVisibility() == View.VISIBLE) {

	        if(mPlayerSeekar.hasFocus()){
				
				mPlayerSeekar.onKeyDown(KeyEvent.KEYCODE_DPAD_RIGHT, null);
			}else{
				dealLeftRightButon(true);
			}
			return true;
		} else if ((event.getKeyCode() == KeyEvent.KEYCODE_DPAD_LEFT
				|| (event.getKeyCode() == 808) || (event.getKeyCode() == 809)
				|| (event.getKeyCode() == 810) || (event.getKeyCode() == 811))
				&& mPlayerControlLayout.getVisibility() == View.VISIBLE) {
			if(mPlayerSeekar.hasFocus()){
				mPlayerSeekar.onKeyDown(KeyEvent.KEYCODE_DPAD_LEFT, null);
			}else{
				dealLeftRightButon(false);
			}
			return true;
		}
//		else if (event.getKeyCode() == KeyEvent.KEYCODE_DPAD_UP
//				&& volumLay.getVisibility() == View.INVISIBLE
//				&& mPlayerControlLayout.getVisibility() == View.INVISIBLE) {
//			if (mVideoContrl != null && mVideoContrl.isMediaPlayerPrepared()) {
//				mMediaHanler.sendEmptyMessage(PRE_VIDEO);
//			}
//			return true;
//		}
		else if(event.getKeyCode() == KeyEvent.KEYCODE_DPAD_UP){
			if(mPlayerControlLayout.getVisibility() == View.VISIBLE){
				if(!mPlayerSeekar.hasFocus()){
					mPlayerSeekar.requestFocus();
				}else{
					if (mVideoContrl != null && mVideoContrl.isMediaPlayerPrepared()) {
						mMediaHanler.sendEmptyMessage(PRE_VIDEO);
					}
				}
			}else{
				if (mVideoContrl != null && mVideoContrl.isMediaPlayerPrepared()) {
					mMediaHanler.sendEmptyMessage(PRE_VIDEO);
				}
			}
			return true;
		}else if(event.getKeyCode() == KeyEvent.KEYCODE_DPAD_DOWN){
			if(mPlayerControlLayout.getVisibility() == View.VISIBLE){
				Log.d("wj","====now mPlayerControlLayout.getVisibility() == View.VISIBLE");
				 if(mPlayerSeekar.hasFocus()){
						Log.d("wj","====now mPlayerSeekar.hasFocus()");
 
					 if(mPlayShareButton.getVisibility() == View.VISIBLE){
						 mPlayShareButton.requestFocus(); 
					 }else{
						 mPlayerStartButton.requestFocus();  
					 }
					
				 }else{
					 Log.d("wj","====now mPlayerSeekar.hasFocus() is false");
					 if (mVideoContrl != null && mVideoContrl.isMediaPlayerPrepared()) {
						  mMediaHanler.sendEmptyMessage(NEXT_VIDEO);
					} 
				 }
			}else {
				Log.d("wj","====now mPlayerControlLayout.getVisibility() == View.INVISIBLE");

				if (mVideoContrl != null && mVideoContrl.isMediaPlayerPrepared()) {
					  mMediaHanler.sendEmptyMessage(NEXT_VIDEO);
				}
			}
				
			
			return true;
		}
//		else if (event.getKeyCode() == KeyEvent.KEYCODE_DPAD_DOWN
//				&& volumLay.getVisibility() == View.INVISIBLE) {
//			if(mPlayerControlLayout.getVisibility() == View.VISIBLE){
//				 if(mPlayerSeekar.hasFocus()){
//					 mPlayerStartButton.requestFocus();
//				 }
//			}else if (mVideoContrl != null && mVideoContrl.isMediaPlayerPrepared()) {
//				  mMediaHanler.sendEmptyMessage(NEXT_VIDEO);
//			}
//			return true;
//		}
		else if (event.getKeyCode() == KeyEvent.KEYCODE_VOLUME_DOWN) {
			
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
				if(!isMediaFile3D){
					Utils.printLog(TAG, "isMediaFile3D is false");
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

		} else if (event.getKeyCode() == 4070) {
			if (mVideoContrl != null && mVideoContrl.isMediaPlayerPrepared()) {
				mMediaHanler.sendEmptyMessage(PRE_VIDEO);
			}
		} else if (event.getKeyCode() == 4072) {
			if (mVideoContrl != null && mVideoContrl.isMediaPlayerPrepared()) {
				mMediaHanler.sendEmptyMessage(NEXT_VIDEO);
			}
		} else if (event.getKeyCode() == 4071) {
			if (mVideoContrl != null
					&& mVideoContrl.isMediaPlayerPrepared()
					&& !mVideoContrl.isPlaying()) {
				mVideoPlayerHander.sendEmptyMessage(PLAYER_START);
			} 
		} else if (event.getKeyCode() == 127) {
			if (mVideoContrl != null
					&& mVideoContrl.isMediaPlayerPrepared()
					&& mVideoContrl.isPlaying()) {
				mVideoPlayerHander.sendEmptyMessage(PLAYER_PAUSE);
			} 
		} else if (event.getKeyCode() == 4075) {
//			if(mPlayerProgressLayout.getVisibility()==View.INVISIBLE){
//				 mVideoPlayerHander.sendEmptyMessage(DISPLAY_PLAYER_PROGRESS_LAYOUT);
//			}
//			mVideoPlayerHander.sendEmptyMessageDelayed(DISMISS_PLAYER_CONTROL_BUTTONS, Utils.MENU_TIMEOUT);// ３秒后消失；
//			mPlayerSeekar.onKeyDown(KeyEvent.KEYCODE_DPAD_LEFT, null);
			
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
						mPlayerSeekar.onKeyDown(KeyEvent.KEYCODE_DPAD_LEFT, null);
			      
				}

				return true;
			
		} else if (event.getKeyCode() == 4073) {
//			if(mPlayerProgressLayout.getVisibility()==View.INVISIBLE){
//				 mVideoPlayerHander.sendEmptyMessage(DISPLAY_PLAYER_PROGRESS_LAYOUT);
//			}
//			mVideoPlayerHander.sendEmptyMessageDelayed(DISMISS_PLAYER_CONTROL_BUTTONS, Utils.MENU_TIMEOUT);// ３秒后消失；
//			mPlayerSeekar.onKeyDown(KeyEvent.KEYCODE_DPAD_RIGHT, null);
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
					mPlayerSeekar.onKeyDown(KeyEvent.KEYCODE_DPAD_RIGHT, null);
			}

			return true;
			
			
		}  else if (event.getKeyCode() == 4074) {
			mVideoPlayerHander.sendEmptyMessage(EXIT_VIDEOPLAY);
		} 
//			else if (event.getKeyCode() == 220 || event.getKeyCode() == 4085) {
//			if(isFromWeb){
//				return true;
//			}
//			 String mVideosize = "";
//		        mVideosize = getFileByte(mTheLastPath);
//			    Intent intent_advance = new Intent();
//			    intent_advance.setAction("android.tcl.videosetting.show");  
//			    intent_advance.putExtra("video_name", mTheLastName);
//			    intent_advance.putExtra("video_size", mVideosize);
//			    startActivity(intent_advance);
//			    return true;
//		}
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
		   isMediaFile3D = true;
			isChangeNotStart = false;
			playStatus = "TRANSITIONING";
			dmrSetPlayStatus(playStatus);
			mVideoPlayerHander.sendEmptyMessage(-99999);
			

			if (!(position == VideoPlayerContrlConsts.GET_CURRENTPOSITION_ERROR)) {
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
				mVideoPlayerHander.sendEmptyMessage(SHOW_WAIT_DIALOG);
			} else if (infoCode == CommonConst.media_player_buffered) {
				mVideoPlayerHander.sendEmptyMessage(DISSMISS_WAIT_DIALOG);

			} else if (infoCode == CommonConst.media_player_subtitle_update) {
				String subtitle = mVideoContrl.getCurrentSubtitleText();
				Utils.printLog(TAG, "setsubtiteltext = " + subtitle);
				mSubtitleTextView.setText(subtitle);
			} else if (infoCode == CommonConst.media_player_subtitle_null) {
				mSubtitleTextView.setText("");

			} 
		
			else if (infoCode == CommonConst.media_player_startplayer_firstframe) {
				if(splitArrayStrings[1]==null||splitArrayStrings[1].equals("CN")){   //now is CN
				Log.d(TAG,"now is CN method======");
				
				   setTVToFullScreen();    //add here for from here to launcher ,small screen 20151022
				   setCNScreenMode();
				}else{
					Log.d(TAG,"now is EM method======");
					
					setTVToFullScreen();    //add here for from here to launcher ,small screen 20151022
					setEMScreenMode();
				}
				
				
				if (mVideoContrl != null
						&& mVideoContrl.isMediaPlayerPrepared()) {
					int dobby = mVideoContrl.isDobby(-1);
	             if(dobby == 1){
						Utils.printLog(TAG, "Current audio is Dobby");
						mVideoPlayerHander.sendEmptyMessage(REFRESH_DDIMAGE);
					}else if(dobby == 2){
						Utils.printLog(TAG, "Current audio is Dobby plus");
						mVideoPlayerHander.sendEmptyMessage(REFRESH_DD_PLUS_IMAGE);			
					} else if(dobby == 3){
						Utils.printLog(TAG, "Current audio is DTS");
						mVideoPlayerHander.sendEmptyMessage(REFRESH_DTSIMAGE);
					}
					
					
					
				}
				playStatus = "PLAYING";
				dmrSetPlayStatus(playStatus);
			 


			}
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
		
//			if(isDMR){
//				mMediaHanler.sendEmptyMessage(BookMarkConst.START_PLAY_FROM_BEGIN);
//			}else{
			    mVideoPlayerHander.sendEmptyMessage(VIDEO_PLAY__CHECK_BOOKMARK);
//			}
			

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
			mVideoPlayerHander.sendEmptyMessageDelayed(REFRESH_PAUSEBUTON, 1000); //WJ add  delay 1000 ms
		
			sendFirstShowContrlBtn();

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
			//mVideoPlayerHander.sendEmptyMessage(EXIT_VIDEOPLAY);
			Log.d(TAG, "onSurfaceDertory end");
		}

		@Override
		public void onVideoPlayComplete() {
			// TODO Auto-generated method stub
			Log.d(TAG,"onVideoPlayComplete===========");
			Log.d(TAG,"mPlayBeginTime is "+mPlayBeginTime.getText().toString()+"==="+
					"mPlayEndTime is :"+mPlayEndTime.getText().toString());
			
			
			mVideoPlayerHander.sendEmptyMessage(REFRESH_SEEKBAR_OP);
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
//						if(mPlayShareButton.getVisibility() == View.VISIBLE){
//							mPlayShareButton.requestFocus();
//							
//						}else{
							mPlayerStartButton.requestFocus();	
//						}
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

				if (mPlayerControlLayout.getVisibility() == View.INVISIBLE
						&& mPlayerProgressLayout.getVisibility() == View.VISIBLE) {
					mPlayerProgressLayout.setVisibility(View.INVISIBLE);
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
			} else if (msg.what == PLAYER_START) {
				playStart();
			} else if (msg.what == VIDEO_PLAY_START_OR_PAUSE) {
				if (mVideoContrl != null
						&& mVideoContrl.isMediaPlayerPrepared()
						&& mVideoContrl.isPlaying()) {
					mVideoPlayerHander.sendEmptyMessage(PLAYER_PAUSE);
				} else {
					mVideoPlayerHander.sendEmptyMessage(PLAYER_START);
				}
			} else if (msg.what == SHOW_WAIT_DIALOG) {
				showWatingDialog();
			} else if (msg.what == DISSMISS_WAIT_DIALOG) {
				dismissWaittingDialog();
			} else if (msg.what == -99999) {
				if(mSurfaceView != null){
				mSurfaceView.requestLayout();
				mSurfaceView.invalidate();
				mSubtitleSurfaceView.requestLayout();
				mSubtitleSurfaceView.invalidate();
				}
			} else if (msg.what == REFRESH_PAUSEBUTON) {

				if (mVideoContrl != null
						&& mVideoContrl.isMediaPlayerPrepared()&&isChangeNotStart) {

					if (!mVideoContrl.isPlaying()) {
						mPauseIcon.setVisibility(View.VISIBLE);
						mPauseIcon.requestLayout();
						mPauseIcon.invalidate();
						Utils.printLog(TAG,
								"mPauseIcon.setVisibility(View.VISIBLE);");
						mPlayerStartButton
								.setBackgroundResource(R.drawable.player_start_selector);
						int currentPos = mVideoContrl.getCurrentPosition();
						int duration = 0;
						if (mVideoContrl.isMediaPlayerPrepared()) {
							duration = mVideoContrl.getDuration();
						}
						if (currentPos != VideoPlayerContrlConsts.GET_CURRENTPOSITION_ERROR) {
							Utils.printLog(TAG, "pause and time =" + currentPos);
							mPauseTimeTextView.setText(Utils
									.getTimeShort(currentPos)
									+ "/"
									+ Utils.getTimeShort(duration));
						} else {
							mPauseTimeTextView.setText("Error");
						}

					} else {
						mPauseIcon.setVisibility(View.INVISIBLE);
						mPlayerStartButton
								.setBackgroundResource(R.drawable.player_pause_selector);
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
				mDDIcon.setBackgroundResource(R.drawable.ddpicon);
				mDDIcon.setVisibility(View.VISIBLE);
				mVideoPlayerHander.sendEmptyMessageDelayed(REFRESH_DDIMAGE_DISMISS, 5000);
			}else if (msg.what == REFRESH_DD_PLUS_IMAGE) {
				mDDIcon.setBackgroundResource(R.drawable.ddpicon);
				mDDIcon.setVisibility(View.VISIBLE);
				mVideoPlayerHander.sendEmptyMessageDelayed(REFRESH_DDIMAGE_DISMISS, 5000);
			}else if (msg.what == REFRESH_DTSIMAGE) {
				mDDIcon.setBackgroundResource(R.drawable.dtsicon);
				mDDIcon.setVisibility(View.VISIBLE);
				mVideoPlayerHander.sendEmptyMessageDelayed(REFRESH_DDIMAGE_DISMISS, 5000);
			}else if (msg.what == REFRESH_DDIMAGE_DISMISS) {
				mDDIcon.setVisibility(View.INVISIBLE);
			}else if (msg.what == REFRESH_SEEKBAR_OP) {

				mVideoPlayerHander.post(new Runnable() {
					public void run() {

						if(mPlayerSeekar!=null){
							mPlayerSeekar.setProgress(1000);
						}
						if(mPlayBeginTime!=null && mPlayEndTime!=null){
							mPlayBeginTime.setText(mPlayEndTime.getText().toString());
						}
					}
				});
				
			} else if (msg.what == PLAYER_URL) {
				Utils.printLog(TAG, "PLAYER_URL start");
				mVideoContrl.playUrl(mList, mCurrIndex);
			} else if (msg.what == VOICE_FORWORD) {

				int mSeekPos = msg.getData().getInt("mPos");
				int mCurrPos= mVideoContrl.getCurrentPosition();
				mCurrPos = mCurrPos + mSeekPos;
				SeekPositon(mCurrPos);
			}else if (msg.what == VOICE_BACKWORD) {

				int mSeekPos = msg.getData().getInt("mPos");
				int mCurrPos= mVideoContrl.getCurrentPosition();
				mCurrPos = mCurrPos - mSeekPos;
				if(mCurrPos < 0){
					mCurrPos = 0;
				}
				SeekPositon(mCurrPos);
			}else if (msg.what == VOICE_SEEK_POSITION) {

				int mSeekPos = msg.getData().getInt("mPos");
				if(mSeekPos < 0){
					mSeekPos = 0;
				}
				SeekPositon(mSeekPos);
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
		if (mList == null)
			return;
        if(mVideoContrl!=null&&mVideoContrl.isMediaPlayerPrepared()){
        	int duration = mVideoContrl.getDuration();
        	if((duration > 0) && (!isDMR)){
        		String path = mList.get(mCurrIndex).mPath;
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

	private BroadcastReceiver mConnectionChangeReceiver = new BroadcastReceiver() {

		public void onReceive(Context context, Intent intent) {
			Utils.printLog(TAG, "netWorkstatus Changed!");

			if (isFirstNetChange) {
				isFirstNetChange = false;

				Utils.printLog(TAG,
						"netWorkstatus Changed and isFirstNetChange!");
				return;
			}

			if (mVideoContrl == null
					|| mVideoContrl.isMediaPlayerPrepared() == false
					|| mList == null
					|| mList.size() <= mCurrIndex
					|| (!mList.get(mCurrIndex).mPath.startsWith("/mnt/smb") && !mList
							.get(mCurrIndex).mPath.startsWith("http"))) {
				Utils.printLog(TAG,
						"netWorkstatus Changed and no need deal with!");
				return;
			}
			if (Utils.isNetWorkUsable(VideoPlayerActivity.this) == false) {
				if (Utils.DEBUG) {
					System.out
							.println("=#################################===================================netwrok Unusable");
				}

				new MyToast(VideoPlayerActivity.this, VideoPlayerActivity.this
						.getResources().getString(
								R.string.media_player_netUnausable)).show();
				mIsInternetDisconnect = true;
				if (mPauseIcon.getVisibility() == View.INVISIBLE) {
					mVideoPlayerHander.sendEmptyMessage(PLAYER_PAUSE);
					mPauseByNetWorkDisconnected = true;
				}
				// updateControlButtonStatus(false);
			} else {
				if (Utils.DEBUG) {
					System.out
							.println("=#################################===================================netwrok OK");
				}
				new MyToast(VideoPlayerActivity.this, VideoPlayerActivity.this
						.getResources().getString(
								R.string.media_player_netUsable)).show();
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
              mPosition = mPosition*1000;
              if(sVoice == null){
            	  return ;
              }
              Utils.printLog(TAG, "mVoiceReceiver sVoice!"+sVoice);
              Utils.printLog(TAG, "mVoiceReceiver mPosition!"+mPosition);
		      if(sVoice.equalsIgnoreCase("play")){
		    		if (mVideoContrl != null
							&& mVideoContrl.isMediaPlayerPrepared()) {
		    			mVideoPlayerHander.sendEmptyMessage(PLAYER_START);
					} 
		      }else if(sVoice.equalsIgnoreCase("pause")){
		    		if (mVideoContrl != null
							&& mVideoContrl.isMediaPlayerPrepared()
							&& mVideoContrl.isPlaying()) {
						mVideoPlayerHander.sendEmptyMessage(PLAYER_PAUSE);
					} 
		      }else if(sVoice.equalsIgnoreCase("previous")){
		    		if (mVideoContrl != null
							&& mVideoContrl.isMediaPlayerPrepared()) {
						mMediaHanler.sendEmptyMessage(PRE_VIDEO);
					}
		      }else if(sVoice.equalsIgnoreCase("next")){
		    	    if (mVideoContrl != null
							&& mVideoContrl.isMediaPlayerPrepared()) {
						mMediaHanler.sendEmptyMessage(NEXT_VIDEO);
					}
		      }else if(sVoice.equalsIgnoreCase("forward")){
		    	    if (mVideoContrl != null
							&& mVideoContrl.isMediaPlayerPrepared()) {	
		    	    		Message mss = mVideoPlayerHander.obtainMessage();
		    				mss.getData().putInt("mPos", mPosition);
		    				mss.what = VOICE_FORWORD;
		    				mVideoPlayerHander.sendMessage(mss);
					}
		      }else if(sVoice.equalsIgnoreCase("backward")){
		    	    if (mVideoContrl != null
							&& mVideoContrl.isMediaPlayerPrepared()) {
		    	    	Message mss = mVideoPlayerHander.obtainMessage();
	    				mss.getData().putInt("mPos", mPosition);
	    				mss.what = VOICE_BACKWORD;
	    				mVideoPlayerHander.sendMessage(mss);
					}
		      }else if(sVoice.equalsIgnoreCase("seek")){
		    	    if (mVideoContrl != null
							&& mVideoContrl.isMediaPlayerPrepared()) {
		    	    	Message mss = mVideoPlayerHander.obtainMessage();
	    				mss.getData().putInt("mPos", mPosition);
	    				mss.what = VOICE_SEEK_POSITION;
	    				mVideoPlayerHander.sendMessage(mss);
					}
		      }
			
			}
	};

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
			if(mPlayShareButton.getVisibility() == View.VISIBLE){
				mPlayShareButton.requestFocus();
				
			}else{
				mPlayerStartButton.requestFocus();	
			}
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

			if (intent.getAction().trim()
					.equals(VideoPlayerUIConst.COLLECTION_OPERATE_DONE)) {

				mAddType = intent.getIntExtra(
						VideoPlayerUIConst.OPERATE_RESULT,
						VideoPlayerUIConst.Fail_COLLECTION);
				boolean isSucess = mAddType == VideoPlayerUIConst.Succ_COLLECTION ? true
						: false;
				Utils.printLog(TAG, "COLLECTION_OPERATE_DONE with res ="
						+ isSucess + " num=" + mAddType);
				if (mList != null && mList.size() > 0
						&& mCurrIndex < mList.size()) {
					if (mList.get(mCurrIndex).mIsSaved == 0) {// 表示为收藏结果；
						Utils.printLog(TAG, "表示为收藏结果");
						if (isSucess) {
							mPlayerSaveButton
									.setBackgroundResource(R.drawable.player_cancel_save_selector);
							showPopWindow(mPlayerSaveButton,
									R.string.Video_Info_Cancel_favorite);
							mList.get(mCurrIndex).mIsSaved = 1;
						} else {
							mPlayerSaveButton
									.setBackgroundResource(R.drawable.player_save_selector);
						}

					} else {// 表示为取消收藏结果；
						Utils.printLog(TAG, "表示为取消收藏结果");
						if (isSucess) {
							mPlayerSaveButton
									.setBackgroundResource(R.drawable.player_save_selector);
							showPopWindow(mPlayerSaveButton,
									R.string.Video_Info_Favorite);
							mList.get(mCurrIndex).mIsSaved = 0;
						} else {
							mPlayerSaveButton
									.setBackgroundResource(R.drawable.player_cancel_save_selector);
						}

					}

					mPlayerSaveButton.setEnabled(true);
				}

			} else if (intent.getAction().equals(
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
			} else if (CommonConst.TV_PRESSED.equalsIgnoreCase(intent
					.getAction())) {
				Utils.printLog(TAG, "TV_PRESSED");
				exitVideoPlay();
			} else if (CommonConst.	CHANGE_SOURCE_PRESSED.equalsIgnoreCase(intent
					.getAction())) {
				Utils.printLog(TAG, "CHANGE_SOURCE_PRESSED");
				exitVideoPlay();
			}  else if (Intent.ACTION_SHUTDOWN.equalsIgnoreCase(intent
					.getAction())) {
				Utils.printLog(TAG, "DEVICE_SHUTDOWN broadCast");
				if (mVideoContrl!=null&&mVideoContrl.isMediaPlayerPrepared()) {
					mTheLastPosition = mVideoContrl.getCurrentPosition();
					sEndTime = mVideoContrl.getDuration();
					Utils.printLog(TAG, "mTheLastPosition " + mTheLastPosition
							+ "  sEndTime =" + sEndTime);
				} else {
					mTheLastPosition = VideoPlayerContrlConsts.GET_CURRENTPOSITION_ERROR;
				}
				if (mTheLastPath != null) {
					doBookMarkAction();
				}
             //exitVideoPlay();
			} else if(CommonConst.VOICE_ASSIST_PRESSED.equalsIgnoreCase(intent.getAction())){
				
				//add here for 5507 disable voice control when top activity is player 20150311
				Utils.printLog(TAG, "VOICE_ASSIST_PRESSED broadCast");
				mMediaHanler.post(new Runnable() {
					
					@Override
					public void run() {
						mToast = new MyToast(VideoPlayerActivity.this, getResources()
								.getString(R.string.voiceassistkeypressdisable));
						mToast.show();
						
					}
				});
				
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
		if(mPlayerControlLayout.getVisibility()== View.INVISIBLE || mList==null){
			return;
		}
		Utils.printLog(TAG, "updateControlButtonStatus");
		
		int size = mList.size();
		if (mVideoContrl.getPlayType() == VideoPlayerContrlConsts.MEDIA_RANDOM_PLAY
				|| mVideoContrl.getPlayType() == VideoPlayerContrlConsts.MEDIA_CYCLE_PLAY
		/*
		 * || sCurrentPlayerType + 1 ==
		 * MediaControlIntent.FLAG_MEDIA_SINGLE_REPEAT_PLAY
		 */) {

			if (mList.size() == 1
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
     
     private void bindTVnSreenService(){                    
         final Intent serviceIntent = new Intent();
         serviceIntent.setAction("com.tcl.multiscreeninteractiontv.MSI_TV_Service");
         Log.i(TAG,"bindTVnSreenService to bind service");
         boolean bRet = this.bindService(serviceIntent, nSreenConnection, BIND_AUTO_CREATE);
         if(bRet==false){
             Log.e(TAG,"bindTVnSreenService bindService fail");
         }
     }
     
     private void dmrSetPlayStatus(String State){                    
    	 if(isDMR&&nSreenTVService!=null){
 			try {
 				Utils.printLog(TAG, "current state"+State);
 				nSreenTVService.setPlayStatus(State);
 			} catch (RemoteException e) {
 				// TODO Auto-generated catch block
 				e.printStackTrace();
 			}
 		}
     }
     
     private void SeekPositon(int mPositon){                    
			if(sEndTime>0){
				final int mpos = mPositon;
				new Thread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						mVideoContrl.seekTo(mpos);

					}
				}).start();
			}else{
				mPlayerSeekar.setProgress(0);
			}
     }
     
     
     private ServiceConnection nSreenConnection = new ServiceConnection() 
     {
         public void onServiceConnected(ComponentName classname, IBinder obj) 
         {
             Log.i(TAG,"nSreenConnection onServiceConnected");
             nSreenTVService = IDLNAService.Stub.asInterface(obj);
             try {
            	 nSreenTVService.registerPlayerCallback(mTVnSreenCallback);
             } catch (RemoteException e) {
                 // TODO Auto-generated catch block
                 e.printStackTrace();
             } 
           
         }
         public void onServiceDisconnected(ComponentName classname) 
         {
             Log.v(TAG, "nSreenConnection Service disconnected.");
         
             nSreenTVService = null;
         }
     };
     
     private IPlayerCallback.Stub mTVnSreenCallback = new IPlayerCallback.Stub() {

		@Override
		public void dmr_play(String uri, String name, String player,
				String album) throws RemoteException {
			// TODO Auto-generated method stub
			Utils.printLog(TAG, "dmr_play uri "+uri);
//			if(mVideoContrl != null && nSreenTVService != null 
//					&& playStatus.endsWith("PAUSED_PLAYBACK")){
//				mVideoPlayerHander.sendEmptyMessage(VIDEO_PLAY_START_OR_PAUSE);
//			}else{
			if(uri == null){
				return;
			}
			
			mList = new ArrayList<MediaBean>();
			MediaBean bean = new MediaBean();
			bean.mPath = uri;
			
			if (uri.startsWith("http")) {
				try {
					uri = URLDecoder.decode(uri,"utf-8");
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Utils.printLog(TAG, "URLDecoder path =" + uri);
				bean.mName = Utils.getRealName(uri);
			} else {
				bean.mName = Utils.getRealName(uri);
			}
			
			mList.add(bean);
			mCurrIndex = 0;
			if (mVideoContrl != null){
				mVideoPlayerHander.sendEmptyMessage(PLAYER_URL);
			}
//			}
		}

		@Override
		public void dmr_pause() throws RemoteException {
			// TODO Auto-generated method stub
			Utils.printLog(TAG, "dmr_pause");
			if (mVideoContrl != null
					&& mVideoContrl.isMediaPlayerPrepared()
					&& mVideoContrl.isPlaying()) {
				mVideoPlayerHander.sendEmptyMessage(PLAYER_PAUSE);
			} 
		}

		@Override
		public void dmr_stop() throws RemoteException {
			// TODO Auto-generated method stub
			Utils.printLog(TAG, "dmr_stop");
			exitVideoPlay();
		}

		@Override
		public void dmr_seek(long time) throws RemoteException {
			// TODO Auto-generated method stub
			if(sEndTime>0){
				final long now = time;
				new Thread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						mVideoContrl.seekTo((int) now);

					}
				}).start();
			}else{
				mPlayerSeekar.setProgress(0);
			}
		}

		@Override
		public void dmr_setMute(boolean mute) throws RemoteException {
			// TODO Auto-generated method stub
			soundManager.setAudioMuteEnabled(mute);
			Utils.printLog(TAG, "soundManager.setAudioMuteEnabled()"+mute);
		}

		@Override
		public boolean dmr_getMute() throws RemoteException {
			// TODO Auto-generated method stub
			boolean mute = soundManager.getAudioMuteEnabled();
			Utils.printLog(TAG, "soundManager.getAudioMuteEnabled()"+mute);
			return mute;
		}

		@Override
		public void dmr_setVolume(int volume) throws RemoteException {
			// TODO Auto-generated method stub
			soundManager.setVolume(volume);
//			audiomanager.setStreamVolume(AudioManager.STREAM_SYSTEM, volume, 0);
			Utils.printLog(TAG, "soundManager.setVolume"+volume);
		}

		@Override
		public int dmr_getVolume() throws RemoteException {
			// TODO Auto-generated method stub
			int current = soundManager.getVolume();
			Utils.printLog(TAG, "current Volume"+current);
			return current;
		}
		@Override
		public long dmr_getMediaDuration() throws RemoteException {
			// TODO Auto-generated method stub
			Utils.printLog(TAG, "dmr_getMediaDuration()"+sEndTime);
			return sEndTime;
		}

		@Override
		public long dmr_getCurPlayPosition() throws RemoteException {
			// TODO Auto-generated method stub
			if(mVideoContrl!=null&&mVideoContrl.isMediaPlayerPrepared()){
				int currentPostion = mVideoContrl.getCurrentPosition();
				Utils.printLog(TAG, "dmr_getCurPlayPosition()"+currentPostion);
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
			if(mVideoContrl != null && playStatus.endsWith("PAUSED_PLAYBACK")){
				mVideoPlayerHander.sendEmptyMessage(VIDEO_PLAY_START_OR_PAUSE);
			}
			
		}

		@Override
		public void dmr_playList(List<String> list) throws RemoteException {
			// TODO Auto-generated method stub
			
			if (list == null){
				Utils.printLog(TAG, "current dmr playlist is null");
				return ;
			}
			
			mList = new ArrayList<MediaBean>();
			for (String u : list) {
				String path = u;
				Utils.printLog(TAG, "dmr playlist ---"+path);
				MediaBean bean = new MediaBean();
				bean.mPath = path;
				if (path.startsWith("http")) {
					try {
						path = URLDecoder.decode(path,"utf-8");
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
			if(mList!=null&&mList.size()>0){
				mCurrIndex = 0;
				if (mVideoContrl != null){
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
        	 Log.d(TAG,"now to set TV to FullScreen");

             if (TTvPictureManager.getInstance(this) != null) {

                TTvPictureManager.getInstance(this).scaleVideoWindow(

                       EnTCLWindow.EN_TCL_MAIN, videoWindowType);

             }

         } catch (Exception e) {

             e.printStackTrace();

         }

      }
     
     private void setEMScreenMode(){
    	 try {
    		 EnTCLAspectRatio mAspect = mPicmanager.getAspectRatio();
 		    if(mAspect!=null){
 		    	//mPicmanager.setAndUpdatePictureSetting(EnTCLPicSetting.EN_TCL_UPDATE_SCREEN_TCL_MODE,mAspect.ordinal(), true);
 			}
		} catch (Exception e) {
			 e.printStackTrace();
		}
    	 
     }
     private void setCNScreenMode(){
    	 try {
    		 Log.d(TAG,"now to setCNScreenMode ");
    		 EnTCLAspectRatio mAspect = mPicmanager.getAspectRatio();
    		 if(mAspect == EnTCLAspectRatio.EN_TCL_4X3){
    			 Log.d(TAG,"screen mode now is 4:3");
    		 }
    		 if(mAspect == EnTCLAspectRatio.EN_TCL_16X9){
    			 Log.d(TAG,"screen mode now is 16:9");
    		 }
    		 if(mAspect == EnTCLAspectRatio.EN_TCL_AUTO){
    			 Log.d(TAG,"screen mode now is auto");
    		 }
    		 if(mAspect!=null && mAspect != EnTCLAspectRatio.EN_TCL_16X9){
     			  mPicmanager.setAspectRatio(mAspect);
     		   }
		} catch (Exception e) {
			 e.printStackTrace();
		}
    	 
     }

}
