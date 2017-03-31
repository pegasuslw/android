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
package com.tcl.common.mediaplayer.audio.ui;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.tcl.common.mediaplayer.audioparse.AudioParser;
import com.tcl.common.mediaplayer.audioparse.AudioParserConst;
import com.tcl.common.mediaplayer.audioparse.AudioSongInfo;
import com.tcl.common.mediaplayer.audioparse.musicUtils;
import com.tcl.common.mediaplayer.lyric.ILyricParseListener;
import com.tcl.common.mediaplayer.lyric.Lyric;
import com.tcl.common.mediaplayer.lyric.PlayListItem;
import com.tcl.common.mediaplayer.lyric.Sentence;
import com.tcl.common.mediaplayer.aidl.CommonConst;
import com.tcl.common.mediaplayer.aidl.MediaBean;
import com.tcl.common.mediaplayer.utils.MyToast;
import com.tcl.common.mediaplayer.utils.NotePopupWindow;
import com.tcl.common.mediaplayer.utils.ScrollingTextView;
import com.tcl.common.mediaplayer.utils.SeekBarPopWindow;
import com.tcl.common.mediaplayer.utils.Utils;
import com.tcl.common.mediaplayer.utils.VolumeController;
import com.tcl.common.mediaplayer.utils.WaitDialogCallBackInterface;
import com.tcl.common.mediaplayer.utils.WaitingDialog;
import com.tcl.common.mediaplayer.video.UI.VideoPlayerActivity;
import com.tcl.common.mediaplayer.video.contrl.VideoPlayerContrlConsts;
import com.tcl.common.mediaplayer.R;
import com.tcl.common.mediaplayer.audio.contrl.AudioPlayerServiceConst;
import com.tcl.common.mediaplayer.aidl.BackGroundAudioCtrl;
import com.tcl.common.mediaplayer.aidl.BackGroundAudioCtrlCallBack;
import com.tcl.multiscreeninteractiontv.IDLNAService;
import com.tcl.multiscreeninteractiontv.IPlayerCallback;
import com.tcl.os.system.WindowManager;
import com.tcl.tvmanager.TTvCommonManager;
import com.tcl.tvmanager.TTvFunctionManager;
import com.tcl.tvmanager.TTvPictureManager;
import com.tcl.tvmanager.TTvSoundManager;
import com.tcl.tvmanager.TvManager;
import com.tcl.tvmanager.vo.EnTCLInputSource;

import android.app.Activity;
import android.app.WallpaperManager;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.AudioManager.OnAudioFocusChangeListener;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RecoverySystem;
import android.os.RemoteException;
import android.tclwidget.TCLSeekBar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager.BadTokenException;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation.AnimationListener;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.ImageView.ScaleType;

public class AudioPlayerActivity extends Activity {
	public static final String TAG = "AudioPlayerActivity";

	private static final int DISPLAY_PLAYER_CONTROL_BUTTONS = 1;// display the
	// control menu;
	private static final int DISMISS_PLAYER_CONTROL_BUTTONS = 0;// dismiss the
	// control menu;
	private static final int DISPLAY_PLAYER_PROGRESS_LAYOUT = 2;// display the
	// progress
	// menu;
	private static final int DISMISS_PLAYER_PROGRESS_LAYOUT = 3;// dismiss the
	// progress
	// menu;
	private static final int DISMISS_PLAYER_CONTROL_BUTTONS_FISRT = 4554;// dismiss
	// the

	private static final long VOLUM_BACK_TIME = 5000;
	private static final int DISPLAY_PAUSEICON_LAYOUT = 4;// display the pause
	// icon;
	private static final int INIT_AUDIO_UI = 5;// display the pause
	// icon;
	private static final int INIT_AUDIO_TIME = 6;// display the pause

	private static final int SHOW_WAITDIALOG = 7;// display the pause
	// icon;
	private static final int DISSMISS_WAITDIALOG = 8;// display the pause
	// icon;
	private static final int DISSMISS_VOLUME = 122;// display the pause
	private static final int DISPLAY_VOLUME = 435;// display the pause
	private static final int SET_ALBUM_IMAGE = 50;
	private static final int PLAY_START_OR_PAUSE = 9;
	private static final int PLAY_START = 10;
	private static final int PLAY_PAUSE = 11;
	private static final int PLAY_CONTINUE = 12;
	private static final int NEXT_AUDIO = 13;
	private static final int PRE_AUDIO = 14;
	private static final int FAST_NEXT_AUDIO = 15;
	private static final int FAST_PRE_AUDIO = 16;
	private static final int REFRESH_START_BUTTON = 17;
	private static final int PLAY_ONCE = 18;
	private static final int SET_BACK_LIGHT = 171;
	private static final int LYIC_NOFOUND = 121211;
	private static final int LYIC_FOUND = 1211;

	private static final int sScrollRange_1080 = 1016;
	private static final int sScrollRange_720 = 675;
	private static final int sIndicatorRange = 10;
	private static final int REFRESH_DTSIMAGE = 80009;
	private static final int REFRESH_DDIMAGE_DISMISS = 800010;

	private static final int PLAYER_URL = 19;// 多屏互动推送特定url开始；

	private static final int mPlayerTypeIcon[] = {
			R.drawable.audio_player_seqence_selector,
			R.drawable.audio_player_single_selector,
			R.drawable.audio_player_single_repeat_selector,
			R.drawable.audio_player_rendom_selector,
			R.drawable.audio_player_cycle_selector };// the
	// play
	// type
	// icons;
	private static final int mPlayerTypeText[] = {
			R.string.Audio_Info_RepeatMode_Normal,
			R.string.Audio_Info_RepeatMode_Single,
			R.string.Audio_Info_RepeatMode_Re_one,
			R.string.Audio_Info_RepeatMode_Random,
			R.string.Audio_Info_RepeatMode_Re_all };// the play type text ;
	private View mPlayerControlLayout;
	private View mPlayerProgressLayout;
	private TextView mName;
	private TextView mPlayEndTime;
	private TextView mPlayBeginTime;
	private FrameLayout mPlayerStartButton;
	private FrameLayout mPlayerSequenceButton;
	private FrameLayout mPlayerNextButton;
	private FrameLayout mPlayerPreviousButton;
	private FrameLayout mPlayerShareButton;
	private ImageView besideShareImg;
	private FrameLayout mPlayerAudioOnlyButton;
	private ImageView besideAudioOnlyImg;
	private ImageButton mPlayerVolumeButton;
	private ImageView mPauseIcon;
	private SampleView mLylic;
	private ImageView mMusicPosterImageView;
	private LinearLayout mAudioInfoLayout;
	private TextView mAudioInfoDisc;
	private TextView mAudioInfoSongster;
	private TextView mAudioInfoGenor;
	private AudioParser mAduioParser;
	private LinearLayout mLilicLayout;
	protected View mBackgroundView;

	private boolean isOnNewIntent = false;
	private int sCurrentPlayerType = 0;
	private int mCurrIndex = 0;
	private int sEndTime;
	private Timer mTime = new Timer();;// fresh the play position and lylic ,and
										// so on;
	private BackGroundAudioCtrl mControl;// AIDL control audio play service;
	private List<MediaBean> mList;
	private boolean mIsUpdatePlayList;// control the method of getting playlist;
	private boolean mIsLyicFound;
	private boolean IsDeskTopLylicShowing = false;// get the desk lylic state
	// from preference stored.
	// private NotePopupWindow popWindow = null;// show the control menu's text;
	private WaitingDialog mwaitDialog = null;
	private boolean mDeskTopLycInt = true;
	private boolean mIsVolumeClick = false;
	private int isDismissControl = 0;
	private boolean isFirstNetChange = true;
	private boolean isVolumeChangedKeyClicked = false;

	private volatile boolean isNextPreValid = true;
	private boolean isShowContrlBtnFirst = true;
	private TCLSeekBar mPlayerSeekar;
	private SeekBarPopWindow seekBarPopWindow;
	// private boolean mIsSeeking = false;

	private ScrollingTextView mPlayerStartButtonText;
	private ScrollingTextView mPlayerSequenceButtonText;
	private TextView mPlayerNextButtonText;
	private TextView mPlayerPreviousButtonText;
	private TextView mPlayerShareButtonText;
	private ScrollingTextView mPlayerAudioOnlyText;
	private ImageView mDDIcon;
	private TTvPictureManager mPicManager;
	private TTvFunctionManager mFunManager;

	private IDLNAService nSreenTVService;
	private TTvSoundManager soundManager;
	private String playStatus = "TRANSITIONING";
	private boolean isDMR = false;
	private int currentPostion = 0;
	private static final int REFRESH_SEEKBAR_OP = 1010101;
	private static final int REFRESH_TOTAL_TIME = 136216;// 音乐总时长刷新
	
	private boolean isAudioOnlySet = false;
//	private ErrorReport report;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// final WallpaperManager mWPManager =
		// WallpaperManager.getInstance(this);
		// final Drawable bgDrawable = mWPManager.getDrawable();
		// getWindow().setBackgroundDrawable(bgDrawable);
		setContentView(R.layout.musicplay);
		// popWindow = new NotePopupWindow(this, audioPlayerHander);
		seekBarPopWindow = new SeekBarPopWindow(this, mMediaHanler);
		findView();
		TTvCommonManager.getInstance(this).setInputSource(
				EnTCLInputSource.EN_TCL_STORAGE);
		soundManager = TTvSoundManager.getInstance(this);
		showWatingDialog();
		mTime.schedule(mTimerTask, 0, 1000);

		IntentFilter intentFilter1 = new IntentFilter();
		intentFilter1.addAction(Intent.ACTION_WALLPAPER_CHANGED);// update

		intentFilter1.addAction(AudioPlayerConst.ATV_OSD_OPEN_BROADCAST);// close
		intentFilter1.addAction(CommonConst.CLOSE_AUDIO_PLAY);// close

		this.registerReceiver(mWallPaperAndATV_ChangedReceiver, intentFilter1);

		IntentFilter intentFilter2 = new IntentFilter();
		intentFilter2.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
		this.registerReceiver(mConnectionChangeReceiver, intentFilter2);
		
		IntentFilter intentFilter4 = new IntentFilter();
		intentFilter4.addAction(CommonConst.VOICE_CONTROL);
		this.registerReceiver(mVoiceReceiver, intentFilter4);
		mPicManager = TTvPictureManager.getInstance(this);
		mFunManager = TTvFunctionManager.getInstance(this);
		mIsUpdatePlayList = getIntent().getBooleanExtra(
				AudioPlayerConst.UPDATE_PLAYLIST, true);// play audio once more
		// ,default;
		if (mIsUpdatePlayList) {// get the play list from other application by
			// intent;

			if (!getPlayList(getIntent())) {// the play list is null;
				exitPlayforNoPlayList();
				return;
			}
		}
		getAudioInfoFromLocal();
		
		if (isDMR) {
			bindTVnSreenService();
			
		}
		Log.d("=============wj=============", "here isDMR is " + isDMR);
		if (isDMR) {
			mPlayerShareButton.setVisibility(View.GONE);
			sCurrentPlayerType = VideoPlayerContrlConsts.MEDIA_SINGLE_REPEAT_PLAY;
			//mPlayerSequenceButton.setFocusable(false);
			//mPlayerSequenceButton.setEnabled(false);
		} else {
			mPlayerShareButton.setVisibility(View.VISIBLE);
		}
		
		//屏蔽分享功能 20150910
				TvManager tvManager = TvManager.getInstance(this);
				String supportShell = tvManager.getProperty().get("ro.sita.model.MediaPlayer.MediaShare", "null");
				if(supportShell!=null&&!supportShell.equalsIgnoreCase("null")){
					if(supportShell.equalsIgnoreCase("FALSE")){
						mPlayerShareButton.setVisibility(View.GONE);
						besideShareImg.setVisibility(View.GONE);
						Utils.printLog(TAG, "MediaPlayer.MediaShare is false");
					}
				}
				
		//根据配置项判断单独听按钮是否显示 20151105
				String supportAudioOnly = tvManager.getProperty().get("ro.sita.model.MediaPlayer.MediaAudioOnly", "null");
				if(supportAudioOnly!=null&&!supportAudioOnly.equalsIgnoreCase("null")){
					if(supportAudioOnly.equalsIgnoreCase("FALSE")){
						mPlayerAudioOnlyButton.setVisibility(View.GONE);
						besideAudioOnlyImg.setVisibility(View.GONE);
						Utils.printLog(TAG, "MediaPlayer.MediaAudioOnly is false");
					}
				}

		mPlayerSequenceButton
				.setBackgroundResource(mPlayerTypeIcon[sCurrentPlayerType]);
		

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

			if (msg.what == NEXT_AUDIO) {
				Utils.printLog(TAG, "Handle play next Msg!");

				if (isNextPreValid) {

					isNextPreValid = false;
					contrlIsNextPreValid();

					boolean isReady = false;

					try {
						isReady = mControl.isMediaPlayerAready();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if (isReady) {
						new Thread(new Runnable() {

							@Override
							public void run() {
								playNext();// TODO Auto-generated method
											// stub

							}
						}).start();
					}

				} else {
					Utils.printLog(TAG, " next invalid!");
				}

			} else if (msg.what == PRE_AUDIO) {

				if (isNextPreValid) {

					isNextPreValid = false;
					contrlIsNextPreValid();

					boolean isReady = false;

					try {
						isReady = mControl.isMediaPlayerAready();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if (isReady) {
						new Thread(new Runnable() {

							@Override
							public void run() {
								playPrevious();

							}
						}).start();
					}

				} else {
					Utils.printLog(TAG, " pre invalid!");
				}

			} else if (msg.what == FAST_NEXT_AUDIO) {

				boolean isReady = false;

				try {
					isReady = mControl.isMediaPlayerAready();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (isReady) {
					new Thread(new Runnable() {

						@Override
						public void run() {
							malFastNext();

						}
					}).start();
				}

			} else if (msg.what == FAST_PRE_AUDIO) {

				boolean isReady = false;

				try {
					isReady = mControl.isMediaPlayerAready();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (isReady) {
					new Thread(new Runnable() {

						@Override
						public void run() {
							malFastPre();

						}
					}).start();
				}

			} else if (msg.what == PLAY_ONCE) {
				new Thread(new Runnable() {

					@Override
					public void run() {
						try {
							mControl.play(mList, mCurrIndex);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				}).start();

			}else if(msg.what == SET_BACK_LIGHT){
				Log.d("wj==","now to set back light,get msg");
				if(mFunManager != null && !isAudioOnlySet){
					
			    Log.d("wj==","now to set back light");	 
			    mFunManager.setPowerBacklight(false); //关闭背光进入单独听模式
				isAudioOnlySet = true;
				}
			}
		}
	};

	@Override
	protected void onNewIntent(Intent intent) {
		// TODO Auto-generated method stub
		Utils.printLog(TAG, "onNewIntent");
		if (!getPlayList(intent)) {
			exitPlayforNoPlayList();
			return;
		}
		isOnNewIntent = true;

		super.onNewIntent(intent);
		Utils.printLog(TAG, "onNewIntent  end");
	}

	private void showWatingDialog() {
		if (mwaitDialog == null) {
			mwaitDialog = new WaitingDialog(AudioPlayerActivity.this);
			mwaitDialog.registerCallback(new WaitDialogCallBackInterface() {

				@Override
				public void onDialogMenuPressed() {
					// TODO Auto-generated method stub

				}

				@Override
				public void onDialogBackPressed() {
					// TODO Auto-generated method stub
					AudioPlayerActivity.this.finish();
				}
			});
		}
		try {
			mwaitDialog.show();
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}

	private void dismissWaittingDialog() {
		if (mwaitDialog != null) {
			try {
				mwaitDialog.dismiss();
				mwaitDialog = null;
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

//	public void onLowMemory() {
//		Log.e(TAG, "**********onLowMemory&&&&&&&&&&&");
//		this.finish();
//	}

	private void exitPlayforNoPlayList() {
		new MyToast(AudioPlayerActivity.this, getResources().getString(
				R.string.media_player_listNull)).show();
		this.finish();
	}

	@Override
	protected void onResume() {
		Utils.printLog(TAG, "onResume start");

		// 如果不更新播放列表，进入该界面直接从音乐播放Service获得当前正在播放歌曲的信息。
		// if (mIsUpdatePlayList) {
		// startService(new Intent(AudioPlayerConst.AUDIO_SERVICE_ACTION));
		// }

		  //app and histroy keycode force cut
		boolean isactive = false;
		int num = 2;
		int[] keycode = new int[2];
		keycode[0] = 4507;  //HDMI1
		keycode[1] = 4506;  //AV1
		WindowManager win = new WindowManager(AudioPlayerActivity.this);
		ComponentName mcomname = new ComponentName("com.tcl.common.mediaplayer", ".audio.ui.AudioPlayerActivity");
		win.activeKeyByKeycodeCnPn(num,keycode,isactive,mcomname,null);
		Log.d(TAG,"now to cancel 4507,4506 audioplay");
		// 20160325
		if (isOnNewIntent) {
			mMediaHanler.sendEmptyMessage(PLAY_ONCE);
			Utils.printLog(TAG, "isOnNewIntent PLAY_ONCE");
			isOnNewIntent = false;
		} else {
			doBindAudioPlayerService();
		}
		super.onResume();
		Utils.printLog(TAG, "onResume end");

	}

	private BroadcastReceiver mConnectionChangeReceiver = new BroadcastReceiver() {

		private void updateControlButtonStatus(boolean update) {

			mPlayerStartButton.setEnabled(update);
			mPlayerSequenceButton.setEnabled(update);
			mPlayerNextButton.setEnabled(update);
			mPlayerPreviousButton.setEnabled(update);
			mPlayerVolumeButton.setEnabled(update);

		}

		public void onReceive(Context context, Intent intent) {
			Utils.printLog(TAG, "netWorkstatus Changed!");

			if (isFirstNetChange) {
				isFirstNetChange = false;

				Utils.printLog(TAG,
						"netWorkstatus Changed and isFirstNetChange!");
				return;
			}

			if (mList == null
					|| mList.size() <= mCurrIndex
					|| !(mList.get(mCurrIndex).mPath
							.startsWith(CommonConst.HTTPFILE) || mList
							.get(mCurrIndex).mPath
							.startsWith(CommonConst.SMBFILE))) {
				return;
			}

			if (Utils.isNetWorkUsable(AudioPlayerActivity.this) == false) {
				// updateControlButtonStatus(false);
				new MyToast(AudioPlayerActivity.this, AudioPlayerActivity.this
						.getResources().getString(
								R.string.media_player_netUnausable)).show();
				audioPlayerHander.sendEmptyMessage(PLAY_PAUSE);
			} else {
				new MyToast(AudioPlayerActivity.this, AudioPlayerActivity.this
						.getResources().getString(
								R.string.media_player_netUsable)).show();

				// updateControlButtonStatus(true);
				audioPlayerHander.sendEmptyMessage(PLAY_START);
			}
		}
	};

	
	private BroadcastReceiver mVoiceReceiver = new BroadcastReceiver() {

		public void onReceive(Context context, Intent intent) {
			Utils.printLog(TAG, "mVoiceReceiver receive!");

			  Bundle bundle = intent.getExtras();
              String sVoice = bundle.getString("type");
              if(sVoice == null){
            	  return ;
              }
              Utils.printLog(TAG, "mVoiceReceiver receive!"+sVoice);
		      if(sVoice.equalsIgnoreCase("play")){
		    	    boolean isMReady = false;
					boolean isMPlaying = false;
					try {
						isMReady = mControl.isMediaPlayerAready();
						if (isMReady) {
							isMPlaying = mControl.isPlaying();
						}

					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if (isMReady && !isMPlaying) {
						playORpause(true);
					}
		      }else if(sVoice.equalsIgnoreCase("pause")){
		    	    boolean isMReady = false;
					boolean isMPlaying = false;
					try {
						isMReady = mControl.isMediaPlayerAready();
						if (isMReady) {
							isMPlaying = mControl.isPlaying();
						}

					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if (isMReady && isMPlaying) {
						playORpause(false);
					} 
		      }else if(sVoice.equalsIgnoreCase("previous")){
		    		mMediaHanler.sendEmptyMessage(PRE_AUDIO);
		      }else if(sVoice.equalsIgnoreCase("next")){
		    		mMediaHanler.sendEmptyMessage(NEXT_AUDIO);
		      }
			
			}
	};
	/*
	 * get the play list from the intent passed by other application;
	 * 
	 * @return true if list is not null; other wise return false;
	 */

	private boolean getPlayList(Intent intent) {

		if (intent.getAction().equals(Intent.ACTION_VIEW)) {
			Utils.printLog(TAG, "getPlayList getType=" + intent.getType());
			Utils.printLog(TAG,
					"getPlayList getDataString=" + intent.getDataString());
			if (intent.getIntExtra("playApp", 0) == 1) {
				isDMR = true;
			}
			if (intent.getBooleanExtra("IsMediaBean", false)) {

				mList = intent.getParcelableArrayListExtra("playlist");
				if (mList == null)
					return false;
				mCurrIndex = intent.getIntExtra("index", 0);
			} else {
				mList = new ArrayList<MediaBean>();
				if (intent.getType().equals(
						"application/vnd.tcl.playlist-audio")) {
					List<Uri> urilist = intent
							.getParcelableArrayListExtra("playlist");
					if (urilist == null)
						return false;
					for (Uri u : urilist) {
						String path = Utils.getUriPath(u);

						MediaBean bean = new MediaBean();
						bean.mPath = path;
						if (path.startsWith("http")) {
//							try {
//								path = URLDecoder.decode(path,"utf-8");
//							} catch (UnsupportedEncodingException e) {
//								// TODO Auto-generated catch block
//								e.printStackTrace();
//							}
//							Utils.printLog(TAG, "URLDecoder path =" + path);
//							bean.mName = Utils.getRealName(path);
							String name = intent.getStringExtra("name");
							bean.mName = name;
						} else {
							bean.mName = Utils.getRealName(path);
						}
						// bean.mName = path.substring(path.lastIndexOf("/") +
						// 1,
						// path.lastIndexOf("."));
						// Utils.printLog(TAG, "playlist for ="+path);
						mList.add(bean);
					}
					mCurrIndex = intent.getIntExtra("index", 0);

				} else if (intent.getType().contains("audio/")) {
					Utils.printLog(TAG, "getPlayList audio/*");
					Uri data = intent.getData();
					Utils.printLog(TAG,
							"getPlayList audio/* data " + data.toString());
					if (data == null)
						return false;
					String path = Utils.getUriPath(data);
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
					mCurrIndex = 0;
				}
			}
			if (mList.size() > 0) {
				return true;
			} else {
				return false;
			}

		} else {
			return false;
		}
	}

	protected void onPause() {
		Utils.printLog(TAG, "onPause");
		

		  //app and histroy keycode force cut
		boolean isactive = true;
		int num = 2;
		int[] keycode = new int[2];
		keycode[0] = 4507;  //HDMI1
		keycode[1] = 4506;  //AV1
		WindowManager win = new WindowManager(AudioPlayerActivity.this);
		ComponentName mcomname = new ComponentName("com.tcl.common.mediaplayer", ".audio.ui.AudioPlayerActivity");
		win.activeKeyByKeycodeCnPn(num,keycode,isactive,mcomname,null);
		Log.d(TAG,"now to back 4507,4506 audioplay");
		// 20160325
		mMediaHanler.removeMessages(SET_BACK_LIGHT);
		if (isAudioOnlySet) {
			// set audio only flag ,then return
			  Log.d("wj==","now to set back light, dispatch key");	
			if (mFunManager != null) {
				 Log.d("wj==","now to back to set back light");	
//				mPicManager.setBacklightEnabled(false);
				 mFunManager.setPowerBacklight(true);; // 取消单独听背光设置
				isAudioOnlySet = false;
			}
		}
		super.onPause();
	}

	protected void onStop() {
		Utils.printLog(TAG, "onStop");

		super.onStop();
		saveAudioInfoToLocal();
		try {
		doUnBindService();
		
		playStatus = "STOPPED";
		dmrSetPlayStatus(playStatus);
		if (nSreenConnection != null&&isDMR) {
			Utils.printLog(TAG, " unbindService  nSreenConnection");
			if (nSreenTVService != null) {
				try {
					nSreenTVService.unregisterPlayerCallback();
					Utils.printLog(TAG, " unregisterPlayerCallback");
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				nSreenTVService = null;
			}
			unbindService(nSreenConnection);
		}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (isFinishing() == false) {
			Utils.printLog(TAG, "finish audio");
			if(isAudioOnlySet){
				  Log.d("wj==","now to recover backlight, home press");	
					if (mFunManager != null) {
						 Log.d("wj==","now to recover set back light");	
						 mFunManager.setPowerBacklight(true); // 取消单独听背光设置
						isAudioOnlySet = false;
					}
			}
			AudioPlayerActivity.this.finish();
		}
		Utils.printLog(TAG, "onStop finish");
	}

	/***
	 * get audio state from local;
	 */
	private void getAudioInfoFromLocal() {
		// get the preference stored;
		SharedPreferences settings = getSharedPreferences(
				AudioPlayerConst.MY_SPREFS_FILE, 0);
		sCurrentPlayerType = settings.getInt(AudioPlayerConst.AUDIO_PLAY_TYPE,
				0);
		// IsDeskTopLylicShowing = settings.getBoolean(
		// AudioPlayerConst.LYLIC_SHOW, false);

		Utils.printLog(TAG, "Audio status get ---AUDIO_PLAY_TYPE ="
				+ sCurrentPlayerType + "  and LYLIC_SHOW ="
				+ IsDeskTopLylicShowing);
	}

	/**
	 * save the current state of audio
	 */
	private void saveAudioInfoToLocal() {

		SharedPreferences settings = getSharedPreferences(
				AudioPlayerConst.MY_SPREFS_FILE, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putInt(AudioPlayerConst.AUDIO_PLAY_TYPE, sCurrentPlayerType);
		editor.commit();
		Utils.printLog(TAG, "Save play type =" + sCurrentPlayerType);
		// if (mControl != null) {
		// boolean lycStatus = false;
		// try {
		// lycStatus = mControl.getLylicStatu();
		// } catch (RemoteException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// editor.putBoolean(AudioPlayerConst.LYLIC_SHOW, lycStatus);
		// Utils.printLog(TAG, "Audio status save ---AUDIO_PLAY_TYPE ="
		// + sCurrentPlayerType + "  and LYLIC_SHOW =" + lycStatus);
		//
		// editor.commit();
		// }
	}

	@Override
	protected void onDestroy() {
		Utils.printLog(TAG, "onDestroy");
		clearImageSource();
		if(mTimerTask!=null){
			mTimerTask.cancel();
		}
		if(mTime!=null){
			mTime.cancel();
		}
        if(mWallPaperAndATV_ChangedReceiver!=null){
        	unregisterReceiver(mWallPaperAndATV_ChangedReceiver);
        }
        if(mConnectionChangeReceiver!=null){
        	unregisterReceiver(mConnectionChangeReceiver);
        }
		if(mVoiceReceiver!=null){
			unregisterReceiver(mVoiceReceiver);
		}
		super.onDestroy();

		dismissWaittingDialog();
		if (mList != null) {
			mList = null;
		}
		// Utils.killMyProcess(this);

	}

	private void clearImageSource() {
		albumImage.setImageBitmap(null);
		albumImageReflect.setImageBitmap(null);
		playCdTag2.clearAnimation();
		playCdTag1.clearAnimation();
		if ((bit != null) && (bit.isRecycled() == false)) {
			bit.recycle();
			bit = null;
		}
		if ((bitReflect != null) && (bitReflect.isRecycled() == false)) {
			bitReflect.recycle();
			System.gc();
			bitReflect = null;
		}
		albumImageBg.setImageBitmap(null);
		if ((bigBg != null) && (bigBg.isRecycled() == false)) {
			bigBg.recycle();
			bigBg = null;
		}
		if ((cdPartWithReflect != null)
				&& (cdPartWithReflect.isRecycled() == false)) {
			cdPartWithReflect.recycle();
			cdPartWithReflect = null;
		}
	}

	@Override
	public void onBackPressed() {
		Utils.printLog(TAG, "onBackPressed");
		if (volumLay.getVisibility() == View.VISIBLE) {
			audioPlayerHander.sendEmptyMessage(DISSMISS_VOLUME);
			if (mIsVolumeClick) {
				audioPlayerHander
						.sendEmptyMessage(DISPLAY_PLAYER_CONTROL_BUTTONS);
			}
		}
		// else if (mPlayerControlLayout.getVisibility() == View.VISIBLE) {
		// audioPlayerHander.sendEmptyMessage(DISMISS_PLAYER_CONTROL_BUTTONS);
		// }
		else {
			try {
				if(mControl!=null){
					mControl.release();
				}
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finish();
			// mPlayerProgressLayout.setVisibility(View.INVISIBLE);
		}
	}

	private void findView() {
		ButtonClickListener buttonClickListener = new ButtonClickListener();
		ButtonOnfocusListener buttonFocusListener = new ButtonOnfocusListener();

		mPlayerNextButton = (FrameLayout) findViewById(R.id.player_next);
		mPlayerNextButton.setOnClickListener(buttonClickListener);
		mPlayerNextButton.setOnFocusChangeListener(buttonFocusListener);

		mPlayerVolumeButton = (ImageButton) findViewById(R.id.player_volume);
		mPlayerVolumeButton.setOnClickListener(buttonClickListener);
		mPlayerVolumeButton.setOnFocusChangeListener(buttonFocusListener);

		mPlayerSequenceButton = (FrameLayout) findViewById(R.id.player_sequence);
		mPlayerSequenceButton.setOnClickListener(buttonClickListener);
		mPlayerSequenceButton.setOnFocusChangeListener(buttonFocusListener);

		mPlayerStartButton = (FrameLayout) findViewById(R.id.player_start);
		mPlayerStartButton.setOnClickListener(buttonClickListener);
		mPlayerStartButton.setFocusableInTouchMode(true);
		mPlayerStartButton.setOnFocusChangeListener(buttonFocusListener);
		

		mPlayerPreviousButton = (FrameLayout) findViewById(R.id.player_previous);
		mPlayerPreviousButton.setOnClickListener(buttonClickListener);
		mPlayerPreviousButton.setOnFocusChangeListener(buttonFocusListener);

		mPlayerShareButton = (FrameLayout) findViewById(R.id.player_share);
		mPlayerShareButton.setOnClickListener(buttonClickListener);
		mPlayerShareButton.setOnFocusChangeListener(buttonFocusListener);
		besideShareImg = (ImageView)findViewById(R.id.besideshareimg);
		
		mPlayerAudioOnlyButton = (FrameLayout)findViewById(R.id.player_audioonly);
		mPlayerAudioOnlyButton.setOnFocusChangeListener(buttonFocusListener);
		mPlayerAudioOnlyButton.setOnClickListener(buttonClickListener);
		besideAudioOnlyImg = (ImageView)findViewById(R.id.besideaudioonly);
		
		mDDIcon = (ImageView) findViewById(R.id.music_ddpicon_show);

		mPauseIcon = (ImageView) findViewById(R.id.player_pause_icon);
		mPlayerControlLayout = findViewById(R.id.player_control_layout);
		mPlayerProgressLayout = findViewById(R.id.player_progress_layout);
		mPlayerControlLayout.setVisibility(View.INVISIBLE);
		mName = (TextView) findViewById(R.id.player_video_name);
		mName.setSelected(true);
		mPlayEndTime = (TextView) findViewById(R.id.player_end_time);
		mPlayBeginTime = (TextView) findViewById(R.id.player_begin_time);

		mLylic = (SampleView) findViewById(R.id.lyric_view);
		mLylic.setIs1080P(Utils.isWindow1080(AudioPlayerActivity.this));
		mMusicPosterImageView = (ImageView) findViewById(R.id.music_poster_image);
		mAudioInfoDisc = (TextView) findViewById(R.id.audio_info_disc);
		mAudioInfoGenor = (TextView) findViewById(R.id.audio_info_genor);
		mAudioInfoSongster = (TextView) findViewById(R.id.audio_info_songster);
		mAudioInfoDisc.setSelected(true);
		mAudioInfoGenor.setSelected(true);
		mAudioInfoSongster.setSelected(true);
		mAudioInfoLayout = (LinearLayout) findViewById(R.id.audio_info_layout);
		mLilicLayout = (LinearLayout) findViewById(R.id.lyric_disc_layout);// 未找到歌词；
		mPlayerSeekar = (TCLSeekBar) findViewById(R.id.player_seekbar_audio);
		mPlayerSeekar.setOnSeekBarChangeListener(mSeekBarListener);
		mPlayerSeekar.setKeyProgressIncrement(Utils.SeekBarLength / 20);

		mPlayerStartButtonText = (ScrollingTextView) findViewById(R.id.audio_player_start_text);
		mPlayerSequenceButtonText = (ScrollingTextView) findViewById(R.id.audio_player_sequence_text);
		mPlayerNextButtonText = (TextView) findViewById(R.id.audio_player_next_text);
		mPlayerPreviousButtonText = (TextView) findViewById(R.id.audio_player_previous_text);
		mPlayerShareButtonText = (TextView) findViewById(R.id.audio_player_share_text);
		mPlayerAudioOnlyText = (ScrollingTextView)findViewById(R.id.audio_player_audioonly_text);
		
//		if(mPlayerShareButton.getVisibility() == View.VISIBLE){
//			mPlayerShareButton.requestFocus();
//				
//		}else{		
//			mPlayerStartButton.requestFocus();		
//		}
		initAlbumInfo();
		initVolumeInfo();
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
			Utils.printLog(TAG, "SeekBarListener onProgressChanged progress ="
					+ progress + "   fromUser =" + fromUser);
			if (fromUser) {

				if (sEndTime > 0) {
					final long time = progress * (long) sEndTime
							/ Utils.SeekBarLength;
					Utils.printLog(TAG,
							"SeekBarListener onProgressChanged sEndTime ="
									+ sEndTime + "   time =" + time);
					long xoff = (mPlayerSeekar.getWidth() - Utils.SeekBarThumbLength)
							* (long) progress / Utils.SeekBarLength;
					seekBarPopWindow.showText(mPlayerSeekar,
							Utils.getTimeShort((int) time), (int) xoff,
							-mPlayerSeekar.getHeight() - 30);
					new Thread(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub
							try {
								mControl.seekTo((int) time);
								// mIsSeeking = true;
							} catch (RemoteException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

						}
					}).start();

				} else {
					mPlayerSeekar.setProgress(0);
				}

			}
		}
	};

	private void updateControlButtonStatus(boolean enable) {

		mPlayerNextButton.setFocusable(enable);
		mPlayerVolumeButton.setFocusable(enable);

		mPlayerSequenceButton.setFocusable(enable);
		mPlayerStartButton.setFocusable(enable);
		if (enable) {
			if (mPlayerShareButton.getVisibility() == View.VISIBLE) {
				mPlayerShareButton.requestFocus();
			} else {
				mPlayerStartButton.requestFocus();
			}
		}
		mPlayerPreviousButton.setFocusable(enable);

	}

	private LinearLayout imageLl, cdBgLl;
	private ImageView albumImage, albumImageBg, albumImageBgRef,
			albumImageReflect, butsBase, playMusicTag, playCd, playCdTag1,
			playCdTag2;
	private Bitmap bit, bitReflect, bigBg, cdPartWithReflect, cdPartRef;
	private Animation albumImageAni;
	private boolean bAlbumHasAnimation;

	private void initAlbumInfo() {
		cdBgLl = (LinearLayout) findViewById(R.id.playImageBgLl);
		albumImageBg = (ImageView) findViewById(R.id.albumImageBg);
		albumImageBgRef = (ImageView) findViewById(R.id.albumImageBgRef);
		cdBgLl.setVisibility(View.INVISIBLE);
		cdPartWithReflect = BitmapFactory.decodeResource(getResources(),
				R.drawable.cdbox_rightpart);
		Bitmap bmNow = BitmapFactory.decodeResource(getResources(),
				R.drawable.cdbox_rightpart_forref);
		cdPartRef = BitMapUtils.makeInverseBitmapFromTop(bmNow, false);
		albumImageBg.setImageBitmap(cdPartWithReflect);
		albumImageBgRef.setImageBitmap(cdPartRef);

		bigBg = BitmapFactory.decodeResource(getResources(), R.raw.cdbox_front);

		albumImage = (ImageView) findViewById(R.id.music_poster_image);
		albumImageReflect = (ImageView) findViewById(R.id.playimageRef);
		imageLl = (LinearLayout) findViewById(R.id.playImageLl);

		playMusicTag = (ImageView) findViewById(R.id.playMusicTag);
		playMusicTag.setImageResource(R.drawable.play_bigtag);

		playCd = (ImageView) findViewById(R.id.playCd);
		playCd.setImageResource(R.drawable.playcd);
		playCdTag1 = (ImageView) findViewById(R.id.playTag1);
		playCdTag1.setImageResource(R.drawable.play_tag1);
		playCdTag2 = (ImageView) findViewById(R.id.playTag2);
		playCdTag2.setImageResource(R.drawable.play_tag2);
		/* test animation 1214 */
		playCdTag2.startAnimation(AnimationUtils.loadAnimation(this,
				R.anim.music_tag2_ani));
		playCdTag1.startAnimation(AnimationUtils.loadAnimation(this,
				R.anim.music_tag1_ani));
	}

	private TextView volumMinNum;
	private ProgressBar volumBar;
	private LinearLayout volumLay;
	private int curVolum = 0;
	private VolumeController volumeContrl;

	private void initVolumeInfo() {
		volumeContrl = new VolumeController(AudioPlayerActivity.this);

		volumBar = (ProgressBar) findViewById(R.id.volumProgress_audio);

		volumMinNum = (TextView) findViewById(R.id.volumMinNum_audio);
		volumLay = (LinearLayout) findViewById(R.id.volumInfor_audio);
		volumLay.setVisibility(View.INVISIBLE);

		refreshCurrentVolume();

		/*******************************************************************************/
		// Utils.printLog(TAG, "requestAudioFocus");
		// volumeContrl.requestAudioFocus(new OnAudioFocusChangeListener() {
		//
		// @Override
		// public void onAudioFocusChange(int focusChange) {
		// // TODO Auto-generated method stub
		// switch (focusChange) {
		//
		// case AudioManager.AUDIOFOCUS_GAIN:
		// Utils.printLog(TAG, "AUDIOFOCUS_GAIN");
		// break;
		// case AudioManager.AUDIOFOCUS_LOSS:
		// Utils.printLog(TAG, "AUDIOFOCUS_LOSS");
		// break;
		//
		// case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
		// Utils.printLog(TAG, "AUDIOFOCUS_LOSS_TRANSIENT");
		// break;
		//
		// case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
		// Utils.printLog(TAG, "AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK");
		// break;
		//
		//
		// }
		// }
		// });
		/*******************************************************************************/

	}

	private void refreshCurrentVolume() {
		int vol = volumeContrl.getCurVolum();
		int max = volumeContrl.getMaxVolume();
		curVolum = (vol * 100 / max);
		volumMinNum.setText(String.valueOf(curVolum));
		volumBar.setProgress(curVolum);
	}

	/* 设置歌曲的唱片集图片 */
	private void setAlbumImage() {
		Utils.printLog(TAG, "%&%&%&&&&&&&&&&&&&&&&&&&&&&&%##&setAlbumImage");
		if ((bit != null) && (bit.isRecycled() == false)) {

			if (Utils.isWindow1080(AudioPlayerActivity.this)) {
				if (bit.getWidth() != BitMapUtils.ALBUM_WIDTH_1080
						|| bit.getHeight() != BitMapUtils.ALBUM_HEIGHT_1080) {
					bit = BitMapUtils.createAlbumBitmapFromBit(bit,
							BitMapUtils.ALBUM_WIDTH_1080,
							BitMapUtils.ALBUM_HEIGHT_1080);
				}
			} else {
				if (bit.getWidth() != BitMapUtils.ALBUM_WIDTH_720
						|| bit.getHeight() != BitMapUtils.ALBUM_HEIGHT_720) {
					bit = BitMapUtils.createAlbumBitmapFromBit(bit,
							BitMapUtils.ALBUM_WIDTH_720,
							BitMapUtils.ALBUM_HEIGHT_720);
				}
			}

			bit = BitMapUtils.frame2Org(bit, true);
			if (bit != null) {
				bit = BitMapUtils.combinBitmap(bigBg, bit);
				// bit = browsealbum.createReflectedImage(bit);
				// 用原图创建倒影
				bitReflect = BitMapUtils.makeInverseBitmap(bit, false);
				bitReflect = BitMapUtils.makeReflect(bitReflect);
			}

			// 给原图加透明边框
			// bit = browsealbum.CreateTransFrameBitmap(bit);
			bit = BitMapUtils.frame2Org(bit, false);

			if (bit != null) {
				albumImage.invalidate();
				albumImage.setImageBitmap(bit);
				albumImage.setScaleType(ScaleType.FIT_XY);
				albumImageReflect.setImageBitmap(bitReflect);
				albumImageReflect.setScaleType(ScaleType.FIT_XY);
				albumImageBg.setImageBitmap(cdPartWithReflect);
				albumImageBg.setScaleType(ScaleType.FIT_XY);
			}

			// albumImageBgRef.setVisibility(View.VISIBLE);
			// albumImageReflect.setVisibility(View.VISIBLE);
		} else {
			albumImageReflect.setVisibility(View.INVISIBLE);
			albumImageBgRef.setVisibility(View.INVISIBLE);
			albumImageBg.setImageBitmap(null);
			albumImage.invalidate();
			albumImage.setImageResource(R.drawable.albumart_mp_unknown);
			albumImage.setScaleType(ScaleType.FIT_START);
			albumImageBg.setImageResource(R.drawable.cdbox_rightpartfornull);
			albumImageBg.setScaleType(ScaleType.FIT_START);
		}
		// cdBgLl.setVisibility(View.VISIBLE);
		if (bAlbumHasAnimation == false) {
			albumImageAni = new animtion_right(true);
			albumImageAni.setAnimationListener(animListener);
			imageLl.startAnimation(albumImageAni);
			bAlbumHasAnimation = true;
		} else {
			cdBgLl.setVisibility(View.VISIBLE);
		}
	}

	AnimationListener animListener = new AnimationListener() {
		@Override
		public void onAnimationEnd(Animation animation) {
			cdBgLl.setVisibility(View.VISIBLE);
		}

		@Override
		public void onAnimationRepeat(Animation animation) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onAnimationStart(Animation animation) {
			// TODO Auto-generated method stub

		}
	};

	private void initAudioUI() {

		if (mList == null || mList.size() == 0 || mCurrIndex > mList.size() - 1) {
			exitPlayforNoPlayList();
			return;
		}
		Utils.printLog(TAG, "playAudio mCurrentIndex=" + mCurrIndex
				+ ";mListSize=" + mList.size());
		if (mPauseIcon.getVisibility() == View.VISIBLE) {
			mPauseIcon.setVisibility(View.INVISIBLE);
		}

		if (seekBarPopWindow != null) {
			seekBarPopWindow.dismiss();
		}
		mPlayerStartButton
				.setBackgroundResource(R.drawable.audio_player_pause_selector);
		updateControlButtonStatus();

		final MediaBean bean = mList.get(mCurrIndex);

		parseAudioFileInfo(bean);
		initLyric(bean);

		final String name = bean.mName;
		mName.setText(name);
		mPlayBeginTime.setText("");
		mPlayEndTime.setText("");
		mPlayerSeekar.setProgress(0);
		mPlayerSeekar.setEnabled(false);

	}

	private void initLyric(MediaBean bean) {
		Utils.printLog(TAG, "WE start search lyic");
		mIsLyicFound = false;
		final String lycpath = bean.mLycPath;
		if (lycpath == null) {// 未给歌词地址，判断如果为本地音乐，则在当前目录下搜索；

			if (bean.mPath.startsWith("http")) {
				Utils.printLog(TAG, "mIsLyic http");

				mLylic.setVisibility(View.INVISIBLE);
				mAudioInfoLayout.setVisibility(View.VISIBLE);
				mLilicLayout.setVisibility(View.VISIBLE);
			} else {
				if (bean.mPath != null && bean.mPath.length() > 0) {
					final String lylicPath = bean.mPath.substring(0, bean.mPath

					.lastIndexOf(".")) + ".lrc";
					localLycSearch(lylicPath);
				}
			
			}

		} else {// 已经给出歌词地址，则判断地址是本地还是网络，再进行处理；

			if (lycpath.startsWith("http")) {
				// mIsLyicFound = true;
				Utils.printLog(TAG, "Start Seart http LYC");
				new Thread(new Runnable() {
					public void run() {
						PlayListItem pli = new PlayListItem("", "", 1000L, true);
						new Lyric(lycpath, pli, mLyricParseListener);
					}
				}).start();
				mAudioInfoLayout.setVisibility(View.VISIBLE);
				mLylic.setVisibility(View.VISIBLE);
				mLilicLayout.setVisibility(View.INVISIBLE);
			} else {
				localLycSearch(lycpath);
			}
		}

	}

	private void localLycSearch(String lylicPath) {
		final File file = new File(lylicPath);
		boolean mIsLyicFileExits = file.exists();
		if (mIsLyicFileExits) {
			Utils.printLog(TAG, "mIsLyicFound");
			new Thread(new Runnable() {
				public void run() {
					PlayListItem pli = new PlayListItem("", "", 1000L, true);
					new Lyric(file, pli, mLyricParseListener);
				}
			}).start();
			mAudioInfoLayout.setVisibility(View.VISIBLE);
			mLylic.setVisibility(View.INVISIBLE);
			mLilicLayout.setVisibility(View.INVISIBLE);
		} else {
			Utils.printLog(TAG, "mIsLyicNotFound");

			mLylic.setVisibility(View.INVISIBLE);
			mAudioInfoLayout.setVisibility(View.VISIBLE);
			mLilicLayout.setVisibility(View.VISIBLE);

		}
	}

	private void initAudioPlayTime() {
		if (mList == null || mList.size() == 0 || mCurrIndex > mList.size() - 1) {
			exitPlayforNoPlayList();
			return;
		}
		int duration = 0;
		if (mControl != null) {
			try {
				duration = mControl.getDuration();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		// }
		sEndTime = duration;
		mPlayBeginTime.setText(Utils.getTimeShort(0));
		mPlayEndTime.setText(Utils.getTimeShort(duration));
		if (sEndTime > 0) {
			mPlayerSeekar.setEnabled(true);
		}
	}

	/**
	 * parse audiFile info like name,songer,gener ,and so on;
	 */
	private void parseAudioFileInfo(final MediaBean bean) {

		mAudioInfoSongster.setText(getString(R.string.audio_info_unknown));
		mAudioInfoGenor.setText(getString(R.string.audio_info_unknown));
		mAudioInfoDisc.setText(getString(R.string.audio_info_unknown));
		if (!bean.mPath.startsWith("http")) {
			Utils.printLog(TAG, "ParseMP3File!");
			new Thread(new Runnable() {
				public void run() {
					// 从文件中解析歌曲信息
					mAduioParser = new AudioParser(mAudioInfoUpdateHandler,
							AudioPlayerActivity.this, bean.mPath);
					mAduioParser.startParse();
				}
			}).start();

		} else {// 网络资源，无法解析音频内容；
			mAudioInfoSongster.setText(getString(R.string.audio_info_unknown));
			mAudioInfoGenor.setText(getString(R.string.audio_info_unknown));
			mAudioInfoDisc.setText(getString(R.string.audio_info_unknown));
		}

		Bitmap bm = null;
		String path_mp3 = bean.mPath;
		if(path_mp3 != null && path_mp3.length()>0){
		String path_img = path_mp3.substring(0, path_mp3.lastIndexOf("."));
		String[] imgs = { ".jpg", ".jpeg", ".png", ".gif", ".bmp" };
		String final_imgpath = null;
		for (int i = 0; i < imgs.length; i++) {
			String temp_path_img = path_img + imgs[i];
			if (new File(temp_path_img).exists()) {
				final_imgpath = temp_path_img;
				break;
			}

		}
		Utils.printLog(TAG, "final_imgpath =" + final_imgpath);
		if (final_imgpath != null) {
			bm = Utils.loadPosterImage(final_imgpath);

		}
		}
		if (bm != null && !bm.isRecycled()) {
			bit = bm;
		} else {
			bit = BitmapFactory.decodeResource(
					AudioPlayerActivity.this.getResources(),
					R.drawable.albumart_mp_unknown);
		}
		Bitmap bitAlbum = null;
		if (bean.mPath != null && !bean.mPath.startsWith("http")) {
			if (Utils.isWindow1080((Activity) this)) {
				bitAlbum = musicUtils.getAlbumArtwork(bean.mPath,
						BitMapUtils.ALBUM_WIDTH_1080,
						BitMapUtils.ALBUM_HEIGHT_1080);
				Utils.printLog(TAG, "BitMapUtils.ALBUM_WIDTH_1080");
			} else {
				bitAlbum = musicUtils.getAlbumArtwork(bean.mPath,
						BitMapUtils.ALBUM_WIDTH_720,
						BitMapUtils.ALBUM_HEIGHT_720);
			}
		}

		if (bitAlbum != null) {
			bit = bitAlbum;
			Utils.printLog(TAG, "bitAlbum!= null!");
		}

		audioPlayerHander.sendEmptyMessage(SET_ALBUM_IMAGE);

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

				case R.id.player_volume:
					// boolean lycStatus = false;
					// if (mControl != null) {
					// try {
					// lycStatus = mControl.getLylicStatu();
					// } catch (RemoteException e) {
					// // TODO Auto-generated catch block
					// e.printStackTrace();
					// }
					// }
					//
					// if (mControl != null && lycStatus) {
					// stringId = R.string.Audio_Info_Cancel_Lyc;
					// } else {
					// stringId = R.string.Audio_Info_Lyc;
					// }
					break;

				case R.id.player_start:
					boolean isMReady = false;
					boolean isMPlaying = false;
					if (mControl != null) {
						try {
							isMReady = mControl.isMediaPlayerAready();
							if (isMReady) {
								isMPlaying = mControl.isPlaying();
							}

						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

					if (mControl != null && isMReady && isMPlaying) {
						mPlayerStartButtonText.setVisibility(View.VISIBLE);
						mPlayerStartButtonText
								.setText(R.string.Audio_Info_Pause);

					} else {
						mPlayerStartButtonText.setVisibility(View.VISIBLE);
						mPlayerStartButtonText
								.setText(R.string.Audio_Info_Play);

					}
					break;
				case R.id.player_next:
					mPlayerNextButtonText.setVisibility(View.VISIBLE);
					mPlayerNextButtonText.setText(R.string.Audio_Info_Next_Film);
					break;
				case R.id.player_previous:
					mPlayerPreviousButtonText.setVisibility(View.VISIBLE);
					mPlayerPreviousButtonText.setText(R.string.Audio_Info_Last_Film);
					break;

				case R.id.player_share:
					mPlayerShareButtonText.setVisibility(View.VISIBLE);
					mPlayerShareButtonText.setText(R.string.video_share);
					break;
				case R.id.player_audioonly:
					mPlayerAudioOnlyText.setVisibility(View.VISIBLE);
					mPlayerAudioOnlyText.setText(R.string.audio_only);
					break;

				case R.id.player_sequence:
					mPlayerSequenceButtonText.setVisibility(View.VISIBLE);
					mPlayerSequenceButtonText
							.setText(mPlayerTypeText[sCurrentPlayerType]);

					break;

				}

			} else {
				dismissShowText();
			}
		}

	}

	private void dismissShowText() {

		mPlayerStartButtonText.setVisibility(View.INVISIBLE);
		mPlayerSequenceButtonText.setVisibility(View.INVISIBLE);
		mPlayerNextButtonText.setVisibility(View.INVISIBLE);
		mPlayerPreviousButtonText.setVisibility(View.INVISIBLE);
		mPlayerShareButtonText.setVisibility(View.INVISIBLE);
		mPlayerAudioOnlyText.setVisibility(View.INVISIBLE);

	}

	// private void dismissPopWindow() {
	// if (popWindow != null) {
	// try {
	// popWindow.dismiss();
	// } catch (Exception e) {
	// e.printStackTrace();// TODO: handle exception
	// }
	//
	// }
	// }

	// private void showPopWindow(View view, int string) {
	// // popWindow.dismiss();
	// if (mPlayerControlLayout.getVisibility() == View.VISIBLE)
	// try {
	// popWindow.showText(view, string);
	// } catch (Exception ex) {
	// ex.printStackTrace();
	// }
	// }

	private class ButtonClickListener implements View.OnClickListener {

		public void onClick(View v) {
			if (mControl == null) {
				return;
			}
			switch (v.getId()) {

			case R.id.player_volume:
				audioPlayerHander
						.sendEmptyMessage(DISMISS_PLAYER_CONTROL_BUTTONS);
				audioPlayerHander.sendEmptyMessage(DISPLAY_VOLUME);
				mIsVolumeClick = true;
				break;
			case R.id.player_start:
				audioPlayerHander.sendEmptyMessage(PLAY_START_OR_PAUSE);
				break;
			case R.id.player_next:
				mMediaHanler.sendEmptyMessage(NEXT_AUDIO);
				break;
			case R.id.player_previous:
				mMediaHanler.sendEmptyMessage(PRE_AUDIO);
				break;
			case R.id.player_share:
				Intent sendIntent = new Intent();
				sendIntent.setAction(Intent.ACTION_SEND);
				String mTheLastPath = mList.get(mCurrIndex).mPath;
				Utils.printLog(TAG, "share url -------" + mTheLastPath);
				Uri path = Uri.parse(mTheLastPath);
				sendIntent.putExtra(Intent.EXTRA_STREAM, path);
				sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Share");
				sendIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
				sendIntent.setType("audio/*");

				try {
					startActivity(sendIntent);
				} catch (ActivityNotFoundException acti) {
					acti.printStackTrace();
					new MyToast(AudioPlayerActivity.this,
							AudioPlayerActivity.this.getResources().getString(
									R.string.NoFuntion)).show();
				}
				break;
			case R.id.player_audioonly:
				
					mMediaHanler.removeMessages(SET_BACK_LIGHT);
					if(!isAudioOnlySet){
				new MyToast(AudioPlayerActivity.this, AudioPlayerActivity.this
						.getResources().getString(
								R.string.audio_only_tips)).show();
				Log.d("wj==","now to set back light,send msg");
				mMediaHanler.sendEmptyMessageDelayed(SET_BACK_LIGHT, 3000);
					}	
				break;
			case R.id.player_sequence: {
				if(isDMR){
					return;
				}else{
				    sCurrentPlayerType = (sCurrentPlayerType + 1)
							% mPlayerTypeIcon.length;
					mPlayerSequenceButton
							.setBackgroundResource(mPlayerTypeIcon[sCurrentPlayerType]);
					mPlayerSequenceButtonText
							.setText(mPlayerTypeText[sCurrentPlayerType]);
					try {
						mControl.setPlayType(sCurrentPlayerType);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					updateControlButtonStatus();
				}
		
				// showPopWindow(v, mPlayerTypeText[sCurrentPlayerType]);
			}
				break;

			}

		}
	}

	private void malFastPre() {

		int currentPostion = 0;
		try {
			currentPostion = mControl.getCurrentPosition();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		currentPostion = (currentPostion - AudioPlayerConst.BACK_OR_PREVIOUS_STEP_SIZE) < 0 ? 0
				: (currentPostion - AudioPlayerConst.BACK_OR_PREVIOUS_STEP_SIZE);

		audioPlayerHander.sendEmptyMessage(SHOW_WAITDIALOG);

		try {
			mControl.seekTo(currentPostion);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void malFastNext() {
		int currentPostion = 0;
		int duration = 0;
		try {
			currentPostion = mControl.getCurrentPosition();
			duration = mControl.getDuration();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		currentPostion = (currentPostion + AudioPlayerConst.BACK_OR_PREVIOUS_STEP_SIZE) > duration ? duration
				: (currentPostion + AudioPlayerConst.BACK_OR_PREVIOUS_STEP_SIZE);

		audioPlayerHander.sendEmptyMessage(SHOW_WAITDIALOG);
		try {
			mControl.seekTo(currentPostion);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void playORpause(boolean play) {
		if (mControl != null) {
			boolean isMReady = false;
			boolean isMPlaying = false;
			try {
				isMReady = mControl.isMediaPlayerAready();
				if (isMReady) {
					isMPlaying = mControl.isPlaying();
				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (isMReady && isMPlaying && !play) {
				try {
					mControl.pause();
					playStatus = "PAUSED_PLAYBACK";
					dmrSetPlayStatus(playStatus);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				mPauseIcon.setVisibility(View.VISIBLE);
				mPlayerStartButton
						.setBackgroundResource(R.drawable.audio_player_start_selector);

				mPlayerStartButtonText.setText(R.string.Audio_Info_Play);

				// showPopWindow(mPlayerStartButton, R.string.Audio_Info_Play);
			} else if (isMReady && !isMPlaying && play) {
				try {
					mControl.start();
					playStatus = "PAUSED_PLAYBACK";
					dmrSetPlayStatus(playStatus);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				mPauseIcon.setVisibility(View.INVISIBLE);
				mPlayerStartButton
						.setBackgroundResource(R.drawable.audio_player_pause_selector);
				mPlayerStartButtonText.setText(R.string.Audio_Info_Pause);
				// showPopWindow(mPlayerStartButton, R.string.Audio_Info_Pause);
			}
		}

	}

	private void playNext() {
		boolean isReady = false;
		boolean isNext = false;
		try {

			mControl.playNext();
			// isReady = mControl.isMediaPlayerAready();
			// if (isReady) {
			// Utils.printLog(TAG, "Ready to play next!");
			// isNext = mControl.playNext();
			// }

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (isNext) {
			try {
				mCurrIndex = mControl.getPlayingAudioIndex();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// updatePauseButton();
			audioPlayerHander.sendEmptyMessage(REFRESH_START_BUTTON);
		}
	}

	private void playPrevious() {
		boolean isReady = false;
		boolean isPre = false;
		try {
			isReady = mControl.isMediaPlayerAready();
			if (isReady) {
				Utils.printLog(TAG, "Ready to play next!");
				isPre = mControl.playPrevious();
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (isReady && isPre) {
			try {
				mCurrIndex = mControl.getPlayingAudioIndex();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// updatePauseButton();
			audioPlayerHander.sendEmptyMessage(REFRESH_START_BUTTON);
		}
	}

	private void updatePauseButton() {
		mPlayerStartButton
				.setBackgroundResource(R.drawable.audio_player_pause_selector);
	}

	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		// TODO Auto-generated method stub
		int keycodenow = event.getKeyCode();
		Log.d(TAG,"dispatchKeyEvent now key code is "+ event.getKeyCode());
		if (isDismissControl == 0) {
			isDismissControl = 1;
			Utils.printLog(TAG, "dispatchKeyEvent isDismissControl = 1");
		}
		if(keycodenow == 32531 || keycodenow == 32532 || keycodenow == 32533 || keycodenow == 32534 || keycodenow == 32594){
			return true;
		}
		if (isAudioOnlySet) {
			// set audio only flag ,then return
			  Log.d("wj==","now to set back light, dispatch key");	
			if (mFunManager != null) {
				 Log.d("wj==","now to back to set back light");	
//				mPicManager.setBacklightEnabled(false);
				 mFunManager.setPowerBacklight(true); // 取消单独听背光设置
				isAudioOnlySet = false;
			}
			return true;
		}

		return super.dispatchKeyEvent(event);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		Log.d(TAG, "============================keyCode=" + keyCode);
		isVolumeChangedKeyClicked = false;
		if (event.getKeyCode() == KeyEvent.KEYCODE_MENU) {
//			if (volumLay.getVisibility() == View.VISIBLE) {
//				audioPlayerHander.sendEmptyMessage(DISSMISS_VOLUME);
//				audioPlayerHander.sendEmptyMessageDelayed(
//						DISPLAY_PLAYER_CONTROL_BUTTONS, 0);
//
//			} else if (mPlayerControlLayout.getVisibility() == View.VISIBLE) {
//				audioPlayerHander.sendEmptyMessageDelayed(
//						DISMISS_PLAYER_CONTROL_BUTTONS, 0);
//				audioPlayerHander
//						.sendEmptyMessage(DISMISS_PLAYER_PROGRESS_LAYOUT);
//
//			} else {
//				audioPlayerHander.sendEmptyMessageDelayed(
//						DISPLAY_PLAYER_CONTROL_BUTTONS, 0);
//				audioPlayerHander
//						.sendEmptyMessage(DISPLAY_PLAYER_PROGRESS_LAYOUT);
//			}
			return true;
		} else if ((event.getKeyCode() == KeyEvent.KEYCODE_ENTER || event
				.getKeyCode() == KeyEvent.KEYCODE_DPAD_CENTER)
				&& mControl != null) {
			Utils.printLog(TAG, "Audio Enter Press!");
			Utils.printLog(TAG, "mindex=" + mCurrIndex);
			if (volumLay.getVisibility() == View.VISIBLE) {
				audioPlayerHander.sendEmptyMessage(DISSMISS_VOLUME);
				if (mIsVolumeClick) {
					audioPlayerHander
							.sendEmptyMessage(DISPLAY_PLAYER_CONTROL_BUTTONS);
				}
			} else if (mPlayerControlLayout.getVisibility() == View.INVISIBLE
					&& volumLay.getVisibility() == View.INVISIBLE) {
				boolean isPlaying = false;
				try {
					isPlaying = mControl.isPlaying();
					if (isPlaying) {
						mControl.pause();
						mPauseIcon.setVisibility(View.VISIBLE);
						mPlayerStartButton
								.setBackgroundResource(R.drawable.audio_player_start_selector);
					} else {
						mControl.start();
						mPauseIcon.setVisibility(View.INVISIBLE);
						mPlayerStartButton
								.setBackgroundResource(R.drawable.audio_player_pause_selector);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				return true;
			}
		} else if ((event.getKeyCode() == KeyEvent.KEYCODE_DPAD_RIGHT)
				&& mPlayerControlLayout.getVisibility() == View.INVISIBLE) {
			// if (mAudioSkin != null && mAudioSkin.isConnectionOk()) {
			// mAudioSkin.volumeUp();
			// }
			if (mPlayerSeekar.hasFocus()) {

				mPlayerSeekar.onKeyDown(KeyEvent.KEYCODE_DPAD_RIGHT, null);
			}
			return true;
		} else if ((event.getKeyCode() == KeyEvent.KEYCODE_DPAD_LEFT)
				&& mPlayerControlLayout.getVisibility() == View.INVISIBLE) {
			if (mPlayerSeekar.hasFocus()) {

				mPlayerSeekar.onKeyDown(KeyEvent.KEYCODE_DPAD_LEFT, null);
			}
			// if (mAudioSkin != null && mAudioSkin.isConnectionOk()) {
			// mAudioSkin.volumeDown();
			// }

			return true;
		}
		// else if (event.getKeyCode()== KeyEvent.KEYCODE_VOLUME_UP&&
		// mPlayerControlLayout.getVisibility() == View.VISIBLE) {
		// Utils.printLog(TAG, "mindex=" + mCurrIndex);
		// audioPlayerHander.sendEmptyMessage(DISMISS_PLAYER_CONTROL_BUTTONS);
		// volumeChange(true);
		//
		// // playNext();
		// return true;
		// } else if (event.getKeyCode()== KeyEvent.KEYCODE_VOLUME_DOWN&&
		// mPlayerControlLayout.getVisibility() == View.VISIBLE) {
		// audioPlayerHander.sendEmptyMessage(DISMISS_PLAYER_CONTROL_BUTTONS);
		// volumeChange(false);
		//
		// // playPrevious();
		// return true;
		// }
		else if ((event.getKeyCode() == KeyEvent.KEYCODE_DPAD_RIGHT
				|| (event.getKeyCode() == 804) || (event.getKeyCode() == 805)
				|| (event.getKeyCode() == 806) || (event.getKeyCode() == 807))
				&& mPlayerControlLayout.getVisibility() == View.VISIBLE) {
			dealLeftRightButon(true);
			return true;
		} else if ((event.getKeyCode() == KeyEvent.KEYCODE_DPAD_LEFT
				|| (event.getKeyCode() == 808) || (event.getKeyCode() == 809)
				|| (event.getKeyCode() == 810) || (event.getKeyCode() == 811))
				&& mPlayerControlLayout.getVisibility() == View.VISIBLE) {
			dealLeftRightButon(false);
			return true;
		}
		// else if (event.getKeyCode() == KeyEvent.KEYCODE_DPAD_UP
		// && volumLay.getVisibility() == View.INVISIBLE
		// && mPlayerControlLayout.getVisibility() == View.INVISIBLE) {
		// if (mControl != null) {
		// mMediaHanler.sendEmptyMessage(PRE_AUDIO);
		// }
		// return true;
		// }
		else if (event.getKeyCode() == KeyEvent.KEYCODE_DPAD_UP) {
			if (!mPlayerSeekar.hasFocus()) {
				mPlayerSeekar.requestFocus();
			} else {
				if (mPlayerSeekar.hasFocus()) {
					if (mControl != null) {
						mMediaHanler.sendEmptyMessage(PRE_AUDIO);
					}
				}
			}
			return true;
		} else if (event.getKeyCode() == KeyEvent.KEYCODE_DPAD_DOWN) {

			if (mPlayerSeekar.hasFocus()) {
				if (mPlayerShareButton.getVisibility() == View.VISIBLE) {
					mPlayerShareButton.requestFocus();
				} else {
					mPlayerStartButton.requestFocus();
				}
			} else if (mControl != null) {
				mMediaHanler.sendEmptyMessage(NEXT_AUDIO);
			}

			return true;
		}
		// else if (event.getKeyCode() == KeyEvent.KEYCODE_DPAD_DOWN
		// && volumLay.getVisibility() == View.INVISIBLE ) {
		// if(mPlayerControlLayout.getVisibility() == View.VISIBLE){
		// if(mPlayerSeekar.hasFocus()){
		// mPlayerStartButton.requestFocus();
		// }
		// }else if (mControl != null) {
		// mMediaHanler.sendEmptyMessage(NEXT_AUDIO);
		// }
		// return true;
		// }
		else if (event.getKeyCode() == KeyEvent.KEYCODE_VOLUME_DOWN) {

			// if(mPlayerControlLayout.getVisibility() == View.VISIBLE){
			// audioPlayerHander.sendEmptyMessage(DISMISS_PLAYER_CONTROL_BUTTONS);
			// }

		} else if (event.getKeyCode() == KeyEvent.KEYCODE_VOLUME_UP) {

			// if(mPlayerControlLayout.getVisibility() == View.VISIBLE){
			// audioPlayerHander.sendEmptyMessage(DISMISS_PLAYER_CONTROL_BUTTONS);
			// }

		}

		return super.onKeyDown(keyCode, event);
	}
	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		    if (event.getKeyCode() == 4070) {
		    	mMediaHanler.sendEmptyMessage(PRE_AUDIO);
			} else if (event.getKeyCode() == 4072) {
				mMediaHanler.sendEmptyMessage(NEXT_AUDIO);
			} else if (event.getKeyCode() == 4071 || event.getKeyCode() == 126) {
				boolean isMReady = false;
				boolean isMPlaying = false;
				try {
					isMReady = mControl.isMediaPlayerAready();
					if (isMReady) {
						isMPlaying = mControl.isPlaying();
					}

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (isMReady && !isMPlaying) {
					playORpause(true);
				}
			} else if (event.getKeyCode() == 127) {
				boolean isMReady = false;
				boolean isMPlaying = false;
				try {
					isMReady = mControl.isMediaPlayerAready();
					if (isMReady) {
						isMPlaying = mControl.isPlaying();
					}

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (isMReady && isMPlaying) {
					playORpause(false);
				} 
			} else if (event.getKeyCode() == 4075) {
				if(mPlayerProgressLayout.getVisibility()==View.INVISIBLE){
					   mPlayerSeekar.requestFocus();
					   audioPlayerHander.sendEmptyMessage(DISPLAY_PLAYER_CONTROL_BUTTONS); 
				}else{	
					   mPlayerSeekar.requestFocus();
					   mPlayerSeekar.onKeyDown(KeyEvent.KEYCODE_DPAD_LEFT, null);
				}
					return true;
			} else if (event.getKeyCode() == 4073) {
				if(mPlayerProgressLayout.getVisibility()==View.INVISIBLE){
					   mPlayerSeekar.requestFocus();
					   audioPlayerHander.sendEmptyMessage(DISPLAY_PLAYER_CONTROL_BUTTONS); 
				}else{	
					   mPlayerSeekar.requestFocus();
					   mPlayerSeekar.onKeyDown(KeyEvent.KEYCODE_DPAD_RIGHT, null);
				}
			} else if (event.getKeyCode() == 4074) {
				finish();
			}
		
		return super.onKeyUp(keyCode, event);
	}
	private void sendFirstShowContrlBtn() {
		if (isShowContrlBtnFirst) {
			isShowContrlBtnFirst = false;
			audioPlayerHander.sendEmptyMessage(DISPLAY_PLAYER_CONTROL_BUTTONS);
			// audioPlayerHander.sendEmptyMessageDelayed(// dismiss control
			// menu;
			// DISMISS_PLAYER_CONTROL_BUTTONS_FISRT, 10000);
		}
	}

	private void dealLeftRightButon(boolean isRight) {
		View mCurrView = mPlayerControlLayout.findFocus();
		if (mCurrView != null && isRight) {
			View mNextView = mCurrView.focusSearch(View.FOCUS_RIGHT);
			if (mNextView != null) {
				mNextView.requestFocus();
			} else {
				if(mPlayerAudioOnlyButton.getVisibility() == View.VISIBLE){
					mPlayerAudioOnlyButton.requestFocus();
				}else if(mPlayerShareButton.getVisibility() == View.VISIBLE){
					mPlayerShareButton.requestFocus();
				}else{
					mPlayerStartButton.requestFocus();
				}
				
			}
		} else if (mCurrView != null && !isRight) {
			View mNextView = mCurrView.focusSearch(View.FOCUS_LEFT);
			if (mNextView != null) {
				mNextView.requestFocus();
			} else {
				mPlayerSequenceButton.requestFocus();
			}
		}
	}

	private void volumeChange(boolean up) {
		audioPlayerHander.sendEmptyMessage(DISPLAY_VOLUME);
		if (volumeContrl != null) {
			isVolumeChangedKeyClicked = true;
			refreshCurrentVolume();
			if (up && curVolum < 100) {
				// volumeContrl.close_Slience();
				curVolum++;

			} else if (!up && curVolum >= 1) {
				curVolum--;
			}
			Utils.printLog(TAG, "now the volume is " + curVolum);
			volumMinNum.setText(String.valueOf(curVolum));
			volumBar.setProgress(curVolum);
			int max = volumeContrl.getMaxVolume();
			volumeContrl.setVolum((curVolum * max) / 100);
		}

	}

	private Handler audioPlayerHander = new Handler() {
		public void handleMessage(Message msg) {
			if (msg.what == DISPLAY_PLAYER_CONTROL_BUTTONS) {
				if (mPlayerControlLayout.getVisibility() == View.INVISIBLE) {
					// AudioManager audiomanager =
					// (AudioManager)getSystemService(Context.AUDIO_SERVICE);
					// audiomanager.hideVolumePanel();
					mPlayerControlLayout.setVisibility(View.VISIBLE);
					if (mIsVolumeClick) {
						mPlayerVolumeButton.requestFocus();
						mIsVolumeClick = false;
					} else {
//						if (mPlayerShareButton.getVisibility() == View.VISIBLE) {
//							mPlayerShareButton.requestFocus();
//						} else {
							mPlayerStartButton.requestFocus();
//						}
					}

				}
				if (mPlayerProgressLayout.getVisibility() == View.INVISIBLE) {
					mPlayerProgressLayout.setVisibility(View.VISIBLE);
				}
			} else if (msg.what == DISMISS_PLAYER_CONTROL_BUTTONS_FISRT) {
				if (isDismissControl == 1) {
					isDismissControl = 2;
					return;
				}
				if (mPlayerControlLayout.getVisibility() == View.VISIBLE) {
					// dismissPopWindow();
					mPlayerControlLayout.setVisibility(View.INVISIBLE);
				}
			} else if (msg.what == DISMISS_PLAYER_CONTROL_BUTTONS) {

				if (mPlayerControlLayout.getVisibility() == View.VISIBLE) {
					// dismissPopWindow();
					mPlayerControlLayout.setVisibility(View.INVISIBLE);
				}
			} else if (msg.what == DISMISS_PLAYER_PROGRESS_LAYOUT) {
				if (mPlayerProgressLayout.getVisibility() == View.VISIBLE) {
					mPlayerProgressLayout.setVisibility(View.INVISIBLE);
				}
			} else if (msg.what == DISPLAY_PLAYER_PROGRESS_LAYOUT) {
				if (mPlayerProgressLayout.getVisibility() == View.INVISIBLE) {
					mPlayerProgressLayout.setVisibility(View.VISIBLE);
				}
			} else if (msg.what == DISPLAY_PAUSEICON_LAYOUT) {
				if (mPauseIcon.getVisibility() == View.INVISIBLE) {
					mPauseIcon.setVisibility(View.VISIBLE);
				}
			} else if (msg.what == INIT_AUDIO_UI) {
				initAudioUI();
				showWatingDialog();
			} else if (msg.what == DISSMISS_VOLUME) {
				if (volumLay.getVisibility() == View.VISIBLE) {
					if (isVolumeChangedKeyClicked) {
						isVolumeChangedKeyClicked = false;
						audioPlayerHander.sendEmptyMessageDelayed(
								DISSMISS_VOLUME, VOLUM_BACK_TIME);
					} else {
						volumLay.setVisibility(View.INVISIBLE);
						audioPlayerHander.removeMessages(DISSMISS_VOLUME);
					}

					// if (mIsVolumeClick) {
					// // mIsVolumeClick = false;
					// audioPlayerHander
					// .sendEmptyMessage(DISPLAY_PLAYER_CONTROL_BUTTONS);
					// }

					// updateControlButtonStatus(true);
				}

			} else if (msg.what == DISPLAY_VOLUME) {
				refreshCurrentVolume();
				if (volumLay.getVisibility() == View.INVISIBLE) {

					volumLay.setVisibility(View.VISIBLE);
					volumLay.requestFocus();
					audioPlayerHander.removeMessages(DISSMISS_VOLUME);
					audioPlayerHander.sendEmptyMessageDelayed(DISSMISS_VOLUME,
							VOLUM_BACK_TIME);
					// updateControlButtonStatus(false);
				}
			} else if (msg.what == SET_ALBUM_IMAGE) {

				AudioPlayerActivity.this.setAlbumImage();
			} else if (msg.what == INIT_AUDIO_TIME) {
				initAudioPlayTime();
				Message messg = audioPlayerHander.obtainMessage();
				messg.what = DISSMISS_WAITDIALOG;
				audioPlayerHander.sendMessageDelayed(messg, 500);// dismissWaittingDialog();
			} else if (msg.what == SHOW_WAITDIALOG) {
				showWatingDialog();
			} else if (msg.what == DISSMISS_WAITDIALOG) {

				dismissWaittingDialog();
			} else if (msg.what == PLAY_START_OR_PAUSE) {
				boolean isMReady = false;
				boolean isMPlaying = false;
				try {
					isMReady = mControl.isMediaPlayerAready();
					if (isMReady) {
						isMPlaying = mControl.isPlaying();
					}

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (isMReady && isMPlaying) {
					playORpause(false);
				} else if (isMReady && !isMPlaying) {
					playORpause(true);
				}

			} else if (msg.what == PLAY_START) {
				playORpause(true);
			} else if (msg.what == PLAY_PAUSE) {
				playORpause(false);
			} else if (msg.what == PLAY_CONTINUE) {
				initAudioUI();
				initAudioPlayTime();
			} else if (msg.what == REFRESH_START_BUTTON) {
				updatePauseButton();
			} else if (msg.what == LYIC_NOFOUND) {
				Utils.printLog(TAG, "mIsLyicNotFound");
				mIsLyicFound = false;
				mLylic.setVisibility(View.INVISIBLE);
				mAudioInfoLayout.setVisibility(View.VISIBLE);
				mLilicLayout.setVisibility(View.VISIBLE);
			} else if (msg.what == LYIC_FOUND) {
				mIsLyicFound = true;
				mLylic.setVisibility(View.VISIBLE);
				mAudioInfoLayout.setVisibility(View.INVISIBLE);
				mLilicLayout.setVisibility(View.INVISIBLE);
				mLylic.setCurrentTime(0);
			} else if (msg.what == REFRESH_DTSIMAGE) {
				audioPlayerHander.removeMessages(REFRESH_DDIMAGE_DISMISS);
				mDDIcon.setBackgroundResource(R.drawable.dtsicon);
				mDDIcon.setVisibility(View.VISIBLE);
				audioPlayerHander.sendEmptyMessageDelayed(
						REFRESH_DDIMAGE_DISMISS, 5000);
			} else if (msg.what == REFRESH_DDIMAGE_DISMISS) {
				mDDIcon.setVisibility(View.INVISIBLE);
			} else if (msg.what == REFRESH_SEEKBAR_OP) {
				if (mPlayerSeekar != null) {
					mPlayerSeekar.setProgress(1000);
				}
				if (mPlayBeginTime != null && mPlayEndTime != null) {
					mPlayBeginTime.setText(mPlayEndTime.getText().toString());
				}
			} else if (msg.what == PLAYER_URL) {
				Utils.printLog(TAG, "PLAYER_URL start");
				try {
					mControl.play(mList, mCurrIndex);
				} catch (RemoteException e) {
					e.printStackTrace();
				}
			} else if (msg.what == CommonConst.media_player_already_firstfiles) {
				new MyToast(AudioPlayerActivity.this, AudioPlayerActivity.this
						.getResources().getString(
								R.string.already_first_files)).show();

			}  else if (msg.what == CommonConst.media_player_already_lastfiles) {
				new MyToast(AudioPlayerActivity.this, AudioPlayerActivity.this
						.getResources().getString(
								R.string.already_last_files)).show();

			}else if (msg.what == REFRESH_SEEKBAR_OP) {
				if (mPlayerSeekar != null) {
					mPlayerSeekar.setProgress(1000);
				}
				if (mPlayBeginTime != null && mPlayEndTime != null) {
					mPlayBeginTime.setText(mPlayEndTime.getText().toString());
				}
			}else if (msg.what == REFRESH_TOTAL_TIME) {
				mPlayEndTime.setText(Utils.getTimeShort(sEndTime));
				if(sEndTime>0 && !mPlayerSeekar.isEnabled()){
					mPlayerSeekar.setEnabled(true);
				}
			}

		}
	};

	private Runnable myRunnable = new Runnable() {
		public void run() {
			boolean isMReady = false;
			if (mControl == null) {
				return;
			} else {
				try {
					isMReady = mControl.isMediaPlayerAready();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (!isMReady) {
					return;
				}
			}
			// if(!mIsSeeking){
			currentPostion = 0;
			try {
				currentPostion = mControl.getCurrentPosition();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (sEndTime <= 0) {
				try {
					sEndTime = mControl.getDuration();
					Utils.printLog(TAG, "currentsEndTime="+sEndTime);
					audioPlayerHander.sendEmptyMessage(REFRESH_TOTAL_TIME);
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			}
			
		
			Utils.printLog(TAG, "currentPostion=" + currentPostion + "Time ="
					+ Utils.getTimeShort(currentPostion));
			mPlayBeginTime.setText(Utils.getTimeShort(currentPostion));

			if (sEndTime > 0) {
				long process = (long) currentPostion * Utils.SeekBarLength
						/ sEndTime;
				Utils.printLog(TAG,
						"SeekBarListener updateprocess currentPostion="
								+ currentPostion + " ;sEndtime =" + sEndTime
								+ "process" + process);
				mPlayerSeekar.setProgress((int) process);
			}
			if (mIsLyicFound) {
				mLylic.setCurrentTime(currentPostion);
				mLylic.invalidate();// 更新歌词
				// Utils.printLog(TAG, "Refresh  Lyc!");

			}
			// }

		}
	};

	private TimerTask mTimerTask = new TimerTask() {
		public void run() {
			boolean isMReady = false;
			if (mControl != null) {
				try {
					isMReady = mControl.isMediaPlayerAready();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			if (isMReady) {
				runOnUiThread(myRunnable);
			}
		}
	};

	private void doBindAudioPlayerService() {
	
		Intent intent = new Intent(this, com.tcl.common.mediaplayer.audio.contrl.AudioPlayerService.class);
//		intent.setAction(AudioPlayerConst.AUDIO_SERVICE_ACTION);
		bindService(intent,
				mLocalAudioServiceConnection, Context.BIND_AUTO_CREATE);
//		bindService(new Intent(AudioPlayerConst.AUDIO_SERVICE_ACTION),
//				mLocalAudioServiceConnection, Context.BIND_AUTO_CREATE);
	}

	private void doUnBindService() {
		if (mControl != null && mLocalAudioServiceConnection != null) {
			try {
				mControl.unregisterCallback(mCallback);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			unbindService(mLocalAudioServiceConnection);
			Intent intent = new Intent(this, com.tcl.common.mediaplayer.audio.contrl.AudioPlayerService.class);
			stopService(intent);
			//			stopService(new Intent(AudioPlayerConst.AUDIO_SERVICE_ACTION));
		}
	}

	private ServiceConnection mLocalAudioServiceConnection = new ServiceConnection() {
		public void onServiceConnected(ComponentName className, IBinder service) {
			mControl = BackGroundAudioCtrl.Stub.asInterface(service);
			try {
				mControl.registerCallback(mCallback);

				mControl.setPlayType(sCurrentPlayerType);
				mControl.doLylicAction(IsDeskTopLylicShowing);

				if (mIsUpdatePlayList == true) {
					Utils.printLog(TAG,
							"mIsUpdatePlayList == true ,so mControl.play(mList, mCurrIndex)");

					// mControl.play(mList, mCurrIndex);
					mMediaHanler.sendEmptyMessage(PLAY_ONCE);
				} else {
					// try {
					mList = mControl.getPlayList();
					Utils.printLog(TAG,
							"mIsUpdatePlayList == false ,so get playlist from service !");
					mCurrIndex = mControl.getPlayingAudioIndex();
					boolean isReayToPlay = mControl.isMediaPlayerAready();
					if (mCurrIndex == -1 || mCurrIndex >= mList.size()
							|| !isReayToPlay) {// ?????????????
						mCurrIndex = 0;
						// mControl.play(mList, mCurrIndex);
						mMediaHanler.sendEmptyMessage(PLAY_ONCE);
						// audioPlayerHander.sendEmptyMessage(INIT_AUDIO_UI);
					} else {
						audioPlayerHander.sendEmptyMessage(PLAY_CONTINUE);
						if (mControl.isPlaying() == false) {// audio is paused
							// in background;
							mPlayerStartButton
									.setBackgroundResource(R.drawable.audio_player_start_selector);
							Utils.printLog(TAG, "Continue Play for Pause");
							audioPlayerHander
									.sendEmptyMessage(DISPLAY_PAUSEICON_LAYOUT);

						}
					}

				}

			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}

		public void onServiceDisconnected(ComponentName className) {
			mControl = null;
		}
	};

	private Handler mAudioInfoUpdateHandler = new Handler() {
		public void handleMessage(Message msg) {
			AudioSongInfo mAduioInfo = mAduioParser.getAudio();
			if (mAduioInfo == null)
				return;
			if (msg.what == AudioParserConst.REFRESH_AUDIO_INFO) {

				if (mAduioInfo.mSonger != null
						&& mAduioInfo.mSonger.length() > 0) {
					mAudioInfoSongster.setText(musicUtils.getRightString(
							AudioPlayerActivity.this, mAduioInfo.mSonger));
				} else {
					mAudioInfoSongster.setText(getResources().getString(
							R.string.audio_info_unknown));
				}
				if (mAduioInfo.mGenre != null && mAduioInfo.mGenre.length() > 0) {
					mAudioInfoGenor.setText(musicUtils.getRightString(
							AudioPlayerActivity.this, mAduioInfo.mGenre));
				} else {
					mAudioInfoGenor.setText(getResources().getString(
							R.string.audio_info_unknown));
				}
				if (mAduioInfo.mAlbum != null && mAduioInfo.mAlbum.length() > 0) {
					mAudioInfoDisc.setText(musicUtils.getRightString(
							AudioPlayerActivity.this, mAduioInfo.mAlbum));
				} else {
					mAudioInfoDisc.setText(getResources().getString(
							R.string.audio_info_unknown));
				}
			} else if (msg.what == AudioParserConst.REFRESH_ALBUM_IMG) {
				if (mAduioInfo.bitmap != null) {
					bit = mAduioInfo.bitmap;
					// mMusicPosterImageView.setImageBitmap(mAduioInfo.bitmap);
				} else {
					bit = BitmapFactory.decodeResource(
							AudioPlayerActivity.this.getResources(),
							R.drawable.albumart_mp_unknown);
					// mMusicPosterImageView
					// .setImageResource(R.drawable.big_icon_songeter);
				}
				audioPlayerHander.sendEmptyMessage(SET_ALBUM_IMAGE);
			}
		}
	};

	private ILyricParseListener mLyricParseListener = new ILyricParseListener() {
		public void onParseFinished(List<Sentence> sentenceList) {
			if (sentenceList != null) {
				mLylic.setSentenceList(sentenceList);
				audioPlayerHander.sendEmptyMessage(LYIC_FOUND);
			} else {
				audioPlayerHander.sendEmptyMessage(LYIC_NOFOUND);
			}

		}

	};

	private BackGroundAudioCtrlCallBack.Stub mCallback = new BackGroundAudioCtrlCallBack.Stub() {
		@Override
		public void onRemoveIndex(int index) {
			Utils.printLog(TAG, "onRemoveIndex");
			if (mControl != null) {
				try {
					Utils.printLog(TAG, "mControl != null");

					mList = mControl.getPlayList();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		@Override
		public void onLycChangeSuccess(boolean ifshow) {
			Utils.printLog(TAG, "onLycChanged");

			// if (ifshow) {
			//
			// mPlayerSaveButton
			// .setBackgroundResource(R.drawable.player_cancel_save_selector);
			// } else {
			// mPlayerSaveButton
			// .setBackgroundResource(R.drawable.player_save_selector);
			// }
			//
			// if (mDeskTopLycInt) {
			// mDeskTopLycInt = false;
			// } else {
			// if (ifshow) {
			// showPopWindow(mPlayerSaveButton,
			// R.string.Audio_Info_Cancel_Lyc);
			// } else {
			// showPopWindow(mPlayerSaveButton, R.string.Audio_Info_Lyc);
			// }
			// }

		}

		@Override
		public void onAudioPlayError(int errCode) throws RemoteException {
			Utils.printLog(TAG, "onAudioPlayError");
			if (errCode == CommonConst.media_player_exception
					|| errCode == CommonConst.media_player_unknown_exception
					|| errCode == CommonConst.PLAY_DEVICE_UNMOUTED
					|| errCode == CommonConst.PLAY_LIST_NULL
					|| errCode == CommonConst.media_player_unknown_exception_38
					|| errCode == CommonConst.media_player_init_error
					|| errCode == CommonConst.DEVICE_SHUTDOWN
					|| errCode == CommonConst.HOME_PRESSED_BroadCast) {
				Utils.printLog(TAG,
						"onAudioPlayError AudioPlayerActivity.this.finish();");
				if(isAudioOnlySet){
					  Log.d("wj==","now to recover backlight, home press");	
						if (mFunManager != null) {
							 Log.d("wj==","now to recover set back light");	
							 mFunManager.setPowerBacklight(true); // 取消单独听背光设置
							isAudioOnlySet = false;
						}
				}
				AudioPlayerActivity.this.finish();
			}

		}

		@Override
		public void onAudioPlayBufferingUpdate(int percent)
				throws RemoteException {
			// Utils.printLog(TAG, "onAudioPlayBufferingUpdate");
			// TODO Auto-generated method stub

		}

		@Override
		public void onAudioPlayChanged(int index) throws RemoteException {
			playStatus = "TRANSITIONING";
			dmrSetPlayStatus(playStatus);
			Utils.printLog(TAG, "onAudioChanged");
			if (index == AudioPlayerServiceConst.EXIT_AUDIO_PALY) {// play is
				// finished
				// !
				Utils.printLog(TAG, "onAudioChanged finished");
				finish();
				return;
			}
			mCurrIndex = index;
			runOnUiThread(new Runnable() {
				public void run() {
					audioPlayerHander.sendEmptyMessage(INIT_AUDIO_UI);
				}
			});

		}

		@Override
		public void onAudioPlayInfoNotify(int infoCode) throws RemoteException {
			// TODO Auto-generated method stub
			Utils.printLog(TAG, "onAudioPlayInfoNotify");
			if(infoCode == CommonConst.media_player_already_lastfiles || infoCode == CommonConst.media_player_already_firstfiles){
				audioPlayerHander.sendEmptyMessage(infoCode);
			}
			
		}

		@Override
		public void onAudioPlayPrepared() throws RemoteException {
			// TODO Auto-generated method stub

			Utils.printLog(TAG, "onAudioPlayerPrepaer");
			audioPlayerHander.sendEmptyMessage(INIT_AUDIO_TIME);
			sendFirstShowContrlBtn();
			playStatus = "PLAYING";
			dmrSetPlayStatus(playStatus);
			// runOnUiThread(new Runnable() {
			// public void run() {
			// playAudio();
			// }
			// });
			// if (mControl != null) {
			// // int dobby = mControl.isDobby(-1);
			// // if(dobby == 1){
			// // Utils.printLog(TAG, "Current audio is Dobby");
			// // //audioPlayerHander.sendEmptyMessage(REFRESH_DDIMAGE);
			// // }else{
			// int dts = mControl.isDTS(-1);
			// if(dts == 1){
			// Utils.printLog(TAG, "Current audio is DTS");
			// audioPlayerHander.sendEmptyMessage(REFRESH_DTSIMAGE);
			// }else{
			// //audioPlayerHander.sendEmptyMessage(REFRESH_DTSIMAGE);
			// Utils.printLog(TAG, "Current audio is not Dobby and DTS");
			// }
			//
			// // }
			//
			//
			//
			// }
			 if (mControl != null) {
			    int dobby = mControl.isDobby(-1);
			    if(dobby == 3){
			       Utils.printLog(TAG, "Current audio is Dts");
			       audioPlayerHander.sendEmptyMessage(REFRESH_DTSIMAGE);
			    }
			 }

		}

		@Override
		public void onAudioPlaySeekComplete(int currentPosition)
				throws RemoteException {
			// TODO Auto-generated method stub
			Utils.printLog(TAG, "onAudioPlaySeekComplete");
			// mIsSeeking = false;
			audioPlayerHander.sendEmptyMessage(DISSMISS_WAITDIALOG);

		}

		@Override
		public void onAudioPlayComplete() throws RemoteException {

			Log.d(TAG, "onAudioPlayComplete===========");
			Log.d(TAG, "mPlayBeginTime is "
					+ mPlayBeginTime.getText().toString() + "==="
					+ "mPlayEndTime is :" + mPlayEndTime.getText().toString());
			// TODO Auto-generated method stub
			playStatus = "COMPLETED";   //modify the status "stoped"-"compelted" 20151020 WJ
			dmrSetPlayStatus("COMPLETED");
			audioPlayerHander.sendEmptyMessage(REFRESH_SEEKBAR_OP);

		}
	};

	private BroadcastReceiver mWallPaperAndATV_ChangedReceiver = new BroadcastReceiver() {
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (action.equals(Intent.ACTION_WALLPAPER_CHANGED)) {
				runOnUiThread(new Runnable() {
					public void run() {
						updateBackground();
					}
				});
			} else if (intent.getAction().equals(
					AudioPlayerConst.ATV_OSD_OPEN_BROADCAST)) {
				if (mPlayerControlLayout.getVisibility() == View.VISIBLE) {
					mPlayerControlLayout.setVisibility(View.INVISIBLE);
				}
			} else if (intent.getAction().equals(CommonConst.CLOSE_AUDIO_PLAY)) {
				AudioPlayerActivity.this.finish();
			}
		}
	};

	private void updateBackground() {
		mBackgroundView.setBackgroundDrawable(getWallpaper());
	}

	private void updateControlButtonStatus() {
		if (mList == null || mList.size() <= 0)
			return;
		if (sCurrentPlayerType == CommonConst.AUDIO_PALY_RANDOM
				|| sCurrentPlayerType == CommonConst.AUDIO_PALY_RECYCLE
		/*
		 * || sCurrentPlayerType + 1 ==
		 * MediaControlIntent.FLAG_MEDIA_SINGLE_REPEAT_PLAY
		 */) {

			if (mList.size() == 1
					&& sCurrentPlayerType == CommonConst.AUDIO_PALY_RANDOM) {
				mPlayerNextButton.setEnabled(false);
				mPlayerNextButton.setFocusable(false);
				mPlayerPreviousButton.setEnabled(false);
				mPlayerPreviousButton.setFocusable(false);
				return;
			}
			mPlayerNextButton.setEnabled(true);
			mPlayerNextButton.setFocusable(true);
			mPlayerPreviousButton.setEnabled(true);
			mPlayerPreviousButton.setFocusable(true);
			return;
		}
		final int max = mList.size() - 1;
		final int min = 0;
		if (mCurrIndex == max
		/*
		 * || sCurrentPlayerType + 1 ==
		 * MediaControlIntent.FLAG_MEDIA_SINGLE_PLAY
		 */) {
			mPlayerNextButton.setEnabled(false);
			mPlayerNextButton.setFocusable(false);
		} else {
			mPlayerNextButton.setEnabled(true);
			mPlayerNextButton.setFocusable(true);
		}
		if (mCurrIndex == min
		/*
		 * || sCurrentPlayerType + 1 ==
		 * MediaControlIntent.FLAG_MEDIA_SINGLE_PLAY
		 */) {
			mPlayerPreviousButton.setEnabled(false);
			mPlayerPreviousButton.setFocusable(false);
		} else {
			mPlayerPreviousButton.setEnabled(true);
			mPlayerPreviousButton.setFocusable(true);
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		Utils.printLog(TAG, "onTouchEvent");

		if (event.getAction() == MotionEvent.ACTION_DOWN && mControl != null) {
			float clickPosition = event.getY();
			Utils.printLog(TAG, "onTouchEvent Y =" + clickPosition);
			int mClickBtnPos = 0;
			if (Utils.isWindow1080(AudioPlayerActivity.this)) {
				mClickBtnPos = 890;
			} else {
				mClickBtnPos = 590;
			}
			if (clickPosition >= mClickBtnPos
					&& mPlayerControlLayout.getVisibility() == View.INVISIBLE) {
				audioPlayerHander
						.sendEmptyMessage(DISPLAY_PLAYER_CONTROL_BUTTONS);
			} else {
				audioPlayerHander.sendEmptyMessage(PLAY_START_OR_PAUSE);
			}

		}

		return super.onTouchEvent(event);
	}

	private OnTouchListener mOnProgressTouchListener = new OnTouchListener() {
		public boolean onTouch(View v, MotionEvent event) {
			if (event.getAction() == MotionEvent.ACTION_DOWN
					&& mControl != null) {
				float clickPosition = event.getX();
				Log.e(TAG, "getRawX=" + event.getRawX());
				Log.e(TAG, "getX=" + clickPosition);
				int seekPosition = 0;
				if (Utils.isWindow1080(AudioPlayerActivity.this)) {
					// clickPosition = clickPosition + 28;
					seekPosition = (int) ((float) clickPosition
							/ sScrollRange_1080 * sEndTime);
				} else {
					clickPosition = clickPosition + 10;
					seekPosition = (int) ((float) clickPosition
							/ sScrollRange_720 * sEndTime);
				}

				if (seekPosition > sEndTime) {
					seekPosition = sEndTime;
				}
				try {
					mControl.seekTo(seekPosition);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return true;
		}

	};

	private void dmrSetPlayStatus(String State) {
		if (isDMR && nSreenTVService != null) {
			try {
				nSreenTVService.setPlayStatus(State);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void bindTVnSreenService() {
		final Intent serviceIntent = new Intent();
	    serviceIntent.setAction("com.tcl.multiscreeninteractiontv.MSI_TV_Service");
        serviceIntent.setPackage("com.tcl.MultiScreenInteraction_TV");
		Log.i(TAG, "bindTVnSreenService to bind service");
		boolean bRet = this.bindService(serviceIntent, nSreenConnection,
				BIND_AUTO_CREATE);
		if (bRet == false) {
			Log.e(TAG, "bindTVnSreenService bindService fail");
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
		public void dmr_play(String uri, String name, String player,
				String album) throws RemoteException {
			// TODO Auto-generated method stub
			Utils.printLog(TAG, "dmr_play uri " + uri);
			mList = new ArrayList<MediaBean>();
			MediaBean bean = new MediaBean();
			bean.mPath = uri;
			if (uri.startsWith("http")) {
				bean.mName = name;
				
			} else {
				bean.mName = Utils.getRealName(uri);
			}
			mList.add(bean);
			mCurrIndex = 0;
			if (mControl != null) {
				audioPlayerHander.sendEmptyMessage(PLAYER_URL);		
			}
		}

		@Override
		public void dmr_pause() throws RemoteException {
			// TODO Auto-generated method stub
			Utils.printLog(TAG, "dmr_pause");
			audioPlayerHander.sendEmptyMessage(PLAY_PAUSE);
		}

		@Override
		public void dmr_stop() throws RemoteException {
			// TODO Auto-generated method stub
			Utils.printLog(TAG, "dmr_stop");
//			playStatus = "STOPPED";
//			dmrSetPlayStatus(playStatus);
//			if (nSreenConnection != null) {
//				Utils.printLog(TAG, " unbindService  nSreenConnection");
//				if (nSreenTVService != null) {
//					try {
//						nSreenTVService.unregisterPlayerCallback();
//						Utils.printLog(TAG, " unregisterPlayerCallback");
//					} catch (RemoteException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//					nSreenTVService = null;
//				}
//				unbindService(nSreenConnection);
//			}
			finish();
		}

		@Override
		public void dmr_seek(long time) throws RemoteException {
			// TODO Auto-generated method stub
			if (sEndTime > 0) {
				final long now = time;
				new Thread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						try {
							mControl.seekTo((int) now);
						} catch (RemoteException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}).start();
			}
		}

		@Override
		public void dmr_setMute(boolean mute) throws RemoteException {
			// TODO Auto-generated method stub
			soundManager.setAudioMuteEnabled(mute);
			Utils.printLog(TAG, "soundManager.setAudioMuteEnabled()" + mute);
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
			soundManager.setVolume(volume);
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

			return sEndTime;
		}

		@Override
		public long dmr_getCurPlayPosition() throws RemoteException {
			// TODO Auto-generated method stub
			return currentPostion;
		}

		@Override
		public void dmr_setPlayingName(String str) throws RemoteException {
			// TODO Auto-generated method stub

		}

		@Override
		public void dmr_pauseToResume() throws RemoteException {
			// TODO Auto-generated method stub
			audioPlayerHander.sendEmptyMessage(PLAY_START_OR_PAUSE);
             //add here for nscreen pause -- play  20141014
		}

		@Override
		public void dmr_playList(List<String> list) throws RemoteException {
			// TODO Auto-generated method stub
			
		}

	};

}
