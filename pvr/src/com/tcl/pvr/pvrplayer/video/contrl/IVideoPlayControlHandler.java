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
package com.tcl.pvr.pvrplayer.video.contrl;

import android.media.MediaPlayer;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnInfoListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.MediaPlayer.OnSeekCompleteListener;
import android.media.MediaPlayer.OnVideoSizeChangedListener;
import android.os.Handler;
import android.os.Message;
import android.text.method.MultiTapKeyListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import com.tcl.pvr.pvrplayer.R;
import com.tcl.media.AudioTrackInfo;
import com.tcl.media.SubtitleTrackInfo;
import com.tcl.media.TMediaPlayer;
import com.tcl.pvr.pvrplayer.aidl.CommonConst;
import com.tcl.pvr.pvrplayer.utils.ErrorConsts;
import com.tcl.pvr.pvrplayer.utils.MyToast;
import com.tcl.pvr.pvrplayer.utils.Utils;
import com.tcl.pvr.pvrplayer.video.ui.VideoSettingLayout;
import com.tcl.tvmanager.TDtvPvrManager;
import com.tcl.tvmanager.TDtvPvrManager.PvrNotify;
import com.tcl.tvmanager.TDtvPvrPlayerManager;
import com.tcl.tvmanager.TTvManager;
import com.tcl.tvmanager.TTvUtils;
import com.tcl.tvmanager.vo.DtvPvrMediaFileInfo;
import com.tcl.tvmanager.vo.EnTCLDtvPvrMediaPlayMode;
import com.tcl.tvmanager.vo.EnTCLDtvTShiftMotion;

public class IVideoPlayControlHandler implements VideoPlayControlInterface
		 {

	private String TAG = "IVideoPlayControl";
	private List<String> playList;
	private int playIndex = 0;
	private VideoPlayControlCallback callback = null;
	private TMediaPlayer mMediaPlayer = null;
	private int mCurrentPlayerType = 0;
	private boolean mIsVideoReadyToBePlayed = false;
	private boolean isOnline = false;
	private boolean isForTVOpen = false;
	private SurfaceHolder videoholder = null;
	private Context thisContext = null;

	private int mCurrentSubTitle = VideoPlayerContrlConsts.DEFAULT_SUBTITLE;
	private int mCurrentAudioTrack = VideoPlayerContrlConsts.DEFAULT_AUDIO_TRACK;
	private boolean isAudioUsable = true;
	private File[] mExSubtiesPath;
	private SurfaceHolder mSubTitleHolder;
	private int mSubTitleInternNum = 0;
	private int mSubtitleExtNum = 0;
	private boolean isFinishInit = false;
	private TDtvPvrPlayerManager mTDtvPvrManager;
	private int mFirstEject =0;
	private TDtvPvrManager mTDtvManager;

	public IVideoPlayControlHandler(Context context) {
		mTDtvPvrManager = TDtvPvrPlayerManager.getInstance(thisContext);
		mTDtvManager = TDtvPvrManager.getInstance(thisContext);
		thisContext = context;
		IntentFilter intentFilter2 = new IntentFilter();
		intentFilter2.addAction(Intent.ACTION_MEDIA_UNMOUNTED);
		intentFilter2.addAction(Intent.ACTION_MEDIA_BAD_REMOVAL);
		intentFilter2.addAction(Intent.ACTION_MEDIA_REMOVED);
		intentFilter2.addAction(Intent.ACTION_MEDIA_EJECT);
		intentFilter2.addDataScheme("file");

		IntentFilter intentFilter1 = new IntentFilter();
		intentFilter1.addAction(Intent.ACTION_SHUTDOWN);

		thisContext.registerReceiver(mDeviceUnmountBroadcastReceiver,
				intentFilter2);

		thisContext.registerReceiver(mShutDownBroadCastReceiver, intentFilter1);

		thisContext.sendBroadcast(new Intent(CommonConst.CLOSE_AUDIO_PLAY));
	}

	private BroadcastReceiver mShutDownBroadCastReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			if (Intent.ACTION_SHUTDOWN.equalsIgnoreCase(intent.getAction())) {
				Utils.printLog(TAG, "DEVICE_SHUTDOWN broadCast");
				releaseMediaPlayer();
				if (callback != null) {
					callback.onVideoPlayError(CommonConst.DEVICE_SHUTDOWN);
				}
			}
		}

	};

	private BroadcastReceiver mDeviceUnmountBroadcastReceiver = new BroadcastReceiver() {

		public void onReceive(Context context, Intent intent) {
			Utils.printLog(TAG,
					"mDeviceUnmountBroadcastReceiver" + intent.getDataString());
			Utils.printLog(TAG,"start deal and exit" + "action =" + intent.getAction());
			if ((intent.getAction().equals(Intent.ACTION_MEDIA_UNMOUNTED)
					|| intent.getAction().equals(
							Intent.ACTION_MEDIA_BAD_REMOVAL)
					|| intent.getAction().equals(Intent.ACTION_MEDIA_REMOVED))) {
				Utils.printLog(TAG, "start deal action ok");
				if (playList.get(playIndex).contains(intent
						.getDataString().substring(7))&&mFirstEject ==0) {
					mFirstEject = 1;
					Utils.printLog(TAG, "start deal  Device Unmouted +"
							+ intent.getDataString().substring(7));
					if (callback != null) {
						callback.onVideoPlayError(CommonConst.PLAY_DEVICE_UNMOUTED);
					}
				}
			}
		}
	};

	/***
	 * init the path from mediabean and init the mediaPlayer;
	 * 
	 * @param index
	 *            the position that will be played;
	 */
	private void playInit(int index) {
		Utils.printLog(TAG, "playInit with index =" + playIndex);

//		mCurrentSubTitle = VideoPlayerContrlConsts.DEFAULT_SUBTITLE;
//		mCurrentAudioTrack = VideoPlayerContrlConsts.DEFAULT_AUDIO_TRACK;
//		mSubTitleInternNum = 0;
//		mSubtitleExtNum = 0;
//		isAudioUsable = true;
//
//		MediaBean mediaBean = playList.get(playIndex);
//
//		String path = mediaBean.mPath;
//		Utils.printLog(TAG, "mediaBean.mURLType=" + mediaBean.mURLType);
//		Utils.printLog(TAG, "mediaBean.path=" + path);
//			if (!path.startsWith("http") && !new File(path).exists()) {
//				Utils.printLog(TAG, "文件不存在；");
//				if (callback != null) {
//					callback.onVideoPlayError(CommonConst.media_source_not_found);
//				}
//				playNextVideoAfterError();
//				return;
//			}
//		
//
//		initMediaPlayer(path);
	}

	private File[] getExtSubPath(String videoPath) {
		if (videoPath == null)
			return null;
		String fileBegin = videoPath.substring(videoPath.lastIndexOf("/") + 1,
				videoPath.lastIndexOf("."));
		;
		String filePath = videoPath.substring(0, videoPath.lastIndexOf("/")); // new
																				// File(videoPath).getAbsolutePath();

		File mFilePath = new File(filePath);

		if (mFilePath.exists() && mFilePath.isDirectory()) {
			File[] mSubFiles = mFilePath.listFiles(new VideoExtSubtitleFilter(
					fileBegin));
			for (int i = 0; i < mSubFiles.length; i++) {
				Utils.printLog(TAG,
						"getExtSubPath = " + mSubFiles[i].getAbsolutePath());
			}
			if (mSubFiles.length > 0) {
				return mSubFiles;
			}

		}
		return null;

	}

	private void initMediaPlayer(String path) {
		// 初始化MediaPlayer；
		isFinishInit = false;
		try {
			String path1 = "/mnt/usb/F4F8-C2E0/PVR/20_BC HDTV_20090714_122732.pvr";
			Utils.printLog(TAG, "init pvrplayer url=" + path1);
		
			mTDtvPvrManager.prepare();
			mTDtvPvrManager.start(path);
			Utils.printLog(TAG, "init pvrplayer  --- prepareAsync");
			isFinishInit = true;
			
		} catch (Exception ex) {
			ex.printStackTrace();
			mIsVideoReadyToBePlayed = false;

			releaseMediaPlayer();
			if (ex instanceof IOException) {
				if(ex.getMessage()!=null&&ex.getMessage().contains("0x8000000A")){
					Utils.printLog(TAG, "ex.getMessage().contains(0x8000000A)");
					//onError(mMediaPlayer, TMediaPlayer.TMEDIA_ERROR_CODEC_NOT_SUPPORT,
							//TMediaPlayer.TAUDIO_VIDEO_NOT_SUPPORT);
				}else{
					//onError(mMediaPlayer, MediaPlayer.MEDIA_ERROR_UNKNOWN,
							//ErrorConsts.ERROR_IO);
				}
			} else {
				if (callback != null) {
					callback.onVideoPlayError(CommonConst.media_player_init_error);
				}
			}
		}
		
		isFinishInit = true;
		

	}

	@Override
	public int getCurrentPosition() {
		// TODO Auto-generated method stub
		Utils.printLog(TAG, "getCurrentPosition");
		try {
			if (mTDtvPvrManager != null) {
				return (int)mTDtvPvrManager.getCurrentPlayTime();
			} else {
				return VideoPlayerContrlConsts.GET_CURRENTPOSITION_ERROR;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return VideoPlayerContrlConsts.GET_CURRENTPOSITION_ERROR;
		}

	}

	@Override
	public int getDuration() {
		try {
			if (mTDtvPvrManager != null) {
				String file = playList.get(playIndex);
				Utils.printLog(TAG, file);
				return (int)mTDtvPvrManager.getPlayFileDuration(file);
			} else {
				return 0;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}

	}

//	@Override
//	public List<MediaBean> getPlayList() {
//		// TODO Auto-generated method stub
//
//		return playList;
//	}

	@Override
	public int getPlayingVideoIndex() {
		// TODO Auto-generated method stub
		return playIndex;
	}

	@Override
	public boolean isMediaPlayerPrepared() {
		// TODO Auto-generated method stub
		return mIsVideoReadyToBePlayed;

	}

	@Override
	public boolean isPlaying() {
		// TODO Auto-generated method stub
		EnTCLDtvTShiftMotion mRet = EnTCLDtvTShiftMotion.EN_TCL_TSHIFT_NORMAL;
		try {
			if(mTDtvManager!=null){
				mRet = mTDtvManager.getTShiftPlaybackMotion();
			    if(EnTCLDtvTShiftMotion.EN_TCL_TSHIFT_NORMAL == mRet){
				    Utils.printLog(TAG, "EN_TCL_TSHIFT_NORMAL play");
			    	return true;
			    }
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return false;

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

		try {
			if (mTDtvPvrManager!=null) {
				Utils.printLog(TAG, "pause");
				mTDtvPvrManager.pause();
				Utils.printLog(TAG, "pause finish");
				Utils.printLog(TAG, "isPlaying = " + isPlaying());

				// int audioTrack = mMediaPlayer._getAudioTrackNs();
				// Utils.printLog(TAG, "_getAudioTrackNs = "+audioTrack);

				// int videoSubtitle = mMediaPlayer._getSubtitleTrackNs();
				// Utils.printLog(TAG, "_getSubtitleTrackNs = "+videoSubtitle);
				//
				// if(videoSubtitle >0){
				// mMediaPlayer._setSubtitleTrack((short)0);
				// Utils.printLog(TAG, "_setSubtitleTrack = "+0);
				// }

				// if(audioTrack > 0){
				// mMediaPlayer._setAudioTrack((short)0);
				// Utils.printLog(TAG, "_setAudioTrack = "+0);
				// }

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public boolean playNext() {

//		Utils.printLog(TAG, "playNext with play type = " + mCurrentPlayerType
//				+ " index=" + playIndex);
//		switch (mCurrentPlayerType) {
//
//		case VideoPlayerContrlConsts.MEDIA_RANDOM_PLAY:
//			Utils.printLog(TAG, "随机播放");
//			if(playList.size() == 1){
//				return false;
//			}
//			playIndex = Utils.getRandomFromSize(playList.size(), playIndex);
//			Utils.printLog(TAG, "random =" + playIndex);
//			break;
//		case VideoPlayerContrlConsts.MEDIA_CYCLE_PLAY:
//			Utils.printLog(TAG, "循环播放");
//			playIndex = ++playIndex % playList.size();
//			break;
//		default:
//			Utils.printLog(TAG, "index =" + playIndex + "  listsize="
//					+ playList.size());
//			if (playIndex == playList.size() - 1) {
//				// playIndex = VideoPlayerContrlConsts.MEDIA_PALY_FINISHED;
//				return false;
//			} else {
//				playIndex++;
//			}
//			break;
//		}
		mTDtvPvrManager.next();
		playIndex = mTDtvPvrManager.getPlayingFilePosInList();
		Utils.printLog(TAG, "mTDtvPvrManager.getPlayingFilePosInList()"+playIndex);
		int currentPosition = getCurrentPosition();
		if (callback != null) {
			callback.onVideoPlayChanged(playIndex, currentPosition);
		}
//		if (playIndex != VideoPlayerContrlConsts.MEDIA_PALY_FINISHED) {
//			releaseMediaPlayer();
//			playInit(playIndex);
//		}

		return true;
	}

	@Override
	public boolean playPrevious() {
		// TODO Auto-generated method stub
//		switch (mCurrentPlayerType) {
//
//		case VideoPlayerContrlConsts.MEDIA_RANDOM_PLAY:
//			if(playList.size() == 1){
//				return false;
//			}
//			playIndex = Utils.getRandomFromSize(playList.size(), playIndex);
//			break;
//		case VideoPlayerContrlConsts.MEDIA_CYCLE_PLAY:
//			if (playIndex == 0)
//				playIndex = +playList.size();
//			playIndex = --playIndex % playList.size();
//			break;
//		default:
//			if (playIndex == 0) {
//				// playIndex = VideoPlayerContrlConsts.MEDIA_PALY_FINISHED;
//				return false;
//			} else {
//				playIndex--;
//			}
//
//			break;
//		}
//
//		int currentPosition = getCurrentPosition();
//		if (callback != null) {
//			callback.onVideoPlayChanged(playIndex, currentPosition);
//		}
//		if (playIndex != VideoPlayerContrlConsts.MEDIA_PALY_FINISHED) {
//			releaseMediaPlayer();
//			playInit(playIndex);
//		}
		mTDtvPvrManager.previous();
		playIndex = mTDtvPvrManager.getPlayingFilePosInList();
		int currentPosition = getCurrentPosition();
		if (callback != null) {
			callback.onVideoPlayChanged(playIndex, currentPosition);
		}
		return false;
	}

	@Override
	public void registerCallback(VideoPlayControlCallback controlcallback) {
		// TODO Auto-generated method stub
		callback = controlcallback;
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		try {
			if (mMediaPlayer != null) {
				mMediaPlayer.reset();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void setPlayType(int type) {
		// TODO Auto-generated method stub
		Utils.printLog(TAG, "mCurrentPlayerType"+type);
		this.mCurrentPlayerType = type;
		switch (type) {
		case 0:
			mTDtvPvrManager.setPlayMode(EnTCLDtvPvrMediaPlayMode.EN_PLAY_MODE_NORMAL);
			break;
		case 1:
			mTDtvPvrManager.setPlayMode(EnTCLDtvPvrMediaPlayMode.EN_PLAY_MODE_REPEAT_ONE);
			break;
		case 2:
			mTDtvPvrManager.setPlayMode(EnTCLDtvPvrMediaPlayMode.EN_PLAY_MODE_RANDOM);
			break;
		case 3:
			mTDtvPvrManager.setPlayMode(EnTCLDtvPvrMediaPlayMode.EN_PLAY_MODE_REPEAT_ALL);
			break;
		default:
			mTDtvPvrManager.setPlayMode(EnTCLDtvPvrMediaPlayMode.EN_PLAY_MODE_NORMAL);
			break;
		}
		
	}

	@Override
	public void start() {
		// TODO Auto-generated method stub
		try {
			if (mTDtvPvrManager != null) {
				Utils.printLog(TAG, "start");
				mTDtvPvrManager.resume();
				Utils.printLog(TAG, "start finish");

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		try {
			if (mTDtvPvrManager != null) {
				Utils.printLog(TAG, "stop");
			//	mTDtvPvrManager.pvrPlaybackCmd(EnTCLDtvPvrPlaybackCmd.EN_TCL_PVR_PAUSE_PLAY);
				Utils.printLog(TAG, "stop finish");

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void unregisterCallback() {
		// TODO Auto-generated method stub
		callback = null;
	}



//	@Override
//	public void onCompletion(MediaPlayer mp) {
//		// TODO Auto-generated method stub
//		Utils.printLog(TAG, "-----onCompletion");
//		mIsVideoReadyToBePlayed = false;
//		releaseMediaPlayer();
//		if (playList != null && playList.size() > 0) {
//			switch (mCurrentPlayerType) {
//			case VideoPlayerContrlConsts.MEDIA_SEQUENCE_PLAY:
//
//				if (playIndex == playList.size() - 1) {
//					playIndex = VideoPlayerContrlConsts.MEDIA_PALY_FINISHED;
//				} else {
//					playIndex++;
//				}
//				break;
//			case VideoPlayerContrlConsts.MEDIA_SINGLE_PLAY:
//				playIndex = VideoPlayerContrlConsts.MEDIA_PALY_FINISHED;
//				break;
//			case VideoPlayerContrlConsts.MEDIA_SINGLE_REPEAT_PLAY:
//				break;
//			case VideoPlayerContrlConsts.MEDIA_RANDOM_PLAY:
//				playIndex = Utils.getRandomFromSize(playList.size(), playIndex);
//				break;
//			case VideoPlayerContrlConsts.MEDIA_CYCLE_PLAY:
//				playIndex = ++playIndex % playList.size();
//				break;
//			}
//		} else {
//			playIndex = VideoPlayerContrlConsts.MEDIA_PALY_FINISHED;
//		}
//
//		if (callback != null) {
//			callback.onVideoPlayChanged(playIndex, 0);
//		}
//		if (playIndex != VideoPlayerContrlConsts.MEDIA_PALY_FINISHED) {
//			playInit(playIndex);
//		}
//
//	}

//	// 当播放器准备完成，则通知UI，由其完成开始播放和快进等操作。
//	@Override
//	public void onPrepared(MediaPlayer mp) {
//		Utils.printLog(TAG, "onPrepared");
//		mIsVideoReadyToBePlayed = true;
//
//		//mMediaPlayer.offSubtitleTrack();
//
//		if (callback != null) {
//			callback.onVideoPlayPrepared();
//		}
//		// String path =null;
//		// try{
//		//
//		// path= playList.get(playIndex).mPath;
//		// }catch(Exception ex){
//		// ex.printStackTrace();
//		// }
//		//
//		// if (path!=null && !path.startsWith("http") &&mMediaPlayer != null) {
//		// String mExtSubPath = getExtSubPath(path);
//		// if (mExtSubPath != null) {
//		// if(mExtSubPath.endsWith("idx")){
//		//
//		// }else{
//		// mMediaPlayer.setSubtitleDataSource(mExtSubPath);
//		// }
//		// Utils.printLog(TAG, "setExtSubtitleURI = " + mExtSubPath);
//		// }
//		//
//		// }
//
//	}


//	@Override
//	public boolean onError(MediaPlayer mp, int what, int extra) {
//
//		Log.d(TAG, "Error: " + what + "," + extra);
//		mIsVideoReadyToBePlayed = false;
//
//		int errorType = -1;
//		if (what == MediaPlayer.MEDIA_ERROR_SERVER_DIED) {
//			errorType = CommonConst.media_player_exception;
//		}  else if (what == TMediaPlayer.TMEDIA_ERROR_CODEC_NOT_SUPPORT) {
//			if(extra == TMediaPlayer.TAUDIO_VIDEO_NOT_SUPPORT){
//				errorType = CommonConst.unknown_media_format;
//			}
//		} else if (what == MediaPlayer.MEDIA_ERROR_UNKNOWN) {
//			if (extra == ErrorConsts.unknown_media_format) {
//				errorType = CommonConst.unknown_media_format;
//			} else if (extra == ErrorConsts.media_interrupt
//					|| extra == ErrorConsts.ERROR_IO) {
//				errorType = CommonConst.media_interrupt;
//			} else if (extra == ErrorConsts.media_player_network_slow
//					|| extra == ErrorConsts.ERROR_NOT_CONNECTED
//					|| extra == ErrorConsts.ERROR_UNKNOWN_HOST
//					|| extra == ErrorConsts.ERROR_CANNOT_CONNECT
//					|| extra == ErrorConsts.ERROR_CONNECTION_LOST) {
//				errorType = CommonConst.media_player_network_slow;
//			} else if (extra == ErrorConsts.unknown_video_format) {
//				errorType = CommonConst.unknown_video_format;
//			} else if (extra == ErrorConsts.unknown_audio_format) {
//				errorType = CommonConst.unknown_audio_format;
//			} else {
//				errorType = CommonConst.media_player_unknown_exception;
//			}
//		} else if (what == ErrorConsts.media_player_unknown_exception_38) {
//
//			errorType = CommonConst.media_player_unknown_exception_38;
//		} else {
//			errorType = CommonConst.media_player_unknown_exception;
//		}
//			
//		releaseMediaPlayer();
//			if (callback != null) {
//				callback.onVideoPlayError(errorType);
//			}
//			if (errorType == CommonConst.unknown_media_format
//					|| errorType == CommonConst.media_interrupt
//					|| errorType == CommonConst.unknown_video_format
//					|| errorType == CommonConst.media_player_network_slow) {
//				new Thread(new Runnable() {
//
//					@Override
//					public void run() {
//						// TODO Auto-generated method stub
//						playNextVideoAfterError();
//					}
//				}).start();
//
//			}
//
//
//		return false;
//	}

	private void playNextVideoAfterError() {

//		if (playList != null && playList.size() > 1
//				&& playIndex < playList.size()) {
//			Utils.printLog(TAG, "playNextVideoAfterError with index="
//					+ playIndex + " listsize=" + playList.size());
//			playList.remove(playIndex);
//			if (playIndex >= playList.size()) {// 播放到最后一集，则播放下一集。
//				playIndex = playList.size() - 1;
//				//onCompletion(mMediaPlayer);
//				// if(!playNext()){
//				// int currentPosition = getCurrentPosition();
//				// if (callback != null) {
//				// callback.onVideoPlayChanged(VideoPlayerContrlConsts.MEDIA_PALY_FINISHED,
//				// currentPosition);
//				// }
//				// }
//			} else {
//				if (callback != null) {// 删除播放的错误视频，继续播放当前索引对应的视频；
//					callback.onVideoPlayChanged(playIndex,
//							VideoPlayerContrlConsts.GET_CURRENTPOSITION_ERROR);
//					releaseMediaPlayer();
//					playInit(playIndex);
//				}
//			}
//
//		} else {
//
//			if (callback != null) {
//				callback.onVideoPlayError(CommonConst.PLAY_LIST_NULL);
//			}
//		}

	}

	@Override
	public boolean releaseMediaPlayer() {

		mIsVideoReadyToBePlayed = false;
		try {
			if (mMediaPlayer != null) {
				Utils.printLog(TAG, " releaseMediaPlayer start");
				if(mMediaPlayer.isPlaying()){
					Utils.printLog(TAG, " MediaPlayer stop ");
					mMediaPlayer.stop();
				}
				Utils.printLog(TAG, " MediaPlayer release");
				mMediaPlayer.release();

				mMediaPlayer = null;
				Utils.printLog(TAG, "releaseMediaPlayer end ");
				// mMediaPlayer.reset();

			}

			return true;
		} catch (Exception e) {
			Log.e(TAG, "IllegalStateException");
			e.printStackTrace();
			Utils.printLog(TAG, "releaseMediaPlayer end false");
			return false;
		}

	}

	public void unRegisterReceiver() {
		if(mDeviceUnmountBroadcastReceiver!=null){
			thisContext.unregisterReceiver(mDeviceUnmountBroadcastReceiver);
		}
		if(mShutDownBroadCastReceiver!=null){
			thisContext.unregisterReceiver(mShutDownBroadCastReceiver);
		}

	}

	public boolean closeMediaControl() {
		Utils.printLog(TAG, "closeMediaControl");
		mTDtvPvrManager.stop();
	
		return true;
	}

	@Override
	public void maltiSpeedPlayNext() {
		// TODO Auto-generated method stub
		try {
			if (mMediaPlayer != null && (isMediaPlayerPrepared())) {
				// && mMediaPlayer.isPlaying()
				int currentPostion = mMediaPlayer.getCurrentPosition();
				int duration = mMediaPlayer.getDuration();
				currentPostion = (currentPostion + VideoPlayerContrlConsts.BACK_OR_PREVIOUS_STEP_SIZE) > duration ? duration
						: (currentPostion + VideoPlayerContrlConsts.BACK_OR_PREVIOUS_STEP_SIZE);
				if (currentPostion == duration) {
					//onCompletion(mMediaPlayer);
				} else {
					seekTo(currentPostion);
				}

			}
		} catch (Exception e) {
			Log.e(TAG, "IllegalStateException");
			e.printStackTrace();
		}

	}

	@Override
	public void maltiSpeedPlayPrevious() {
		try {
			if (mMediaPlayer != null && (isMediaPlayerPrepared())) {
				// && mMediaPlayer.isPlaying()
				int currentPostion = mMediaPlayer.getCurrentPosition();
				currentPostion = (currentPostion - VideoPlayerContrlConsts.BACK_OR_PREVIOUS_STEP_SIZE) < 0 ? 0
						: (currentPostion - VideoPlayerContrlConsts.BACK_OR_PREVIOUS_STEP_SIZE);
				seekTo(currentPostion);
			}
		} catch (Exception e) {
			Log.e(TAG, "IllegalStateException");
			e.printStackTrace();
		}

		// TODO Auto-generated method stub

	}

	@Override
	public void seekTo(int aMsec) {
		// TODO Auto-generated method stub
	

		Utils.printLog(TAG, " seek to aMsec" + aMsec);
			
				int duration = getDuration();
				Utils.printLog(TAG, "duration---" + duration);
				if(duration<aMsec ||duration ==aMsec){
					if (callback != null) {
						callback.onVideoPlayComplete();
					}
				}else{
					Utils.printLog(TAG, "will seek to aMsec" + aMsec);
					mTDtvPvrManager.setPlaySeek(aMsec);
   		     	}
			
			
		

		//Utils.printLog(TAG, " seek to finish =" + getCurrentPosition());

	}

//	@Override
//	public void onSeekComplete(MediaPlayer mp) {
//		// TODO Auto-generated method stub
//
//		Utils.printLog(TAG, "onSeekComplete");
//		if (callback != null) {
//			callback.onVideoPlaySeekComplete(getCurrentPosition());
//		}
//	}
//
//	@Override
//	public boolean onInfo(MediaPlayer mp, int what, int extra) {
//
//		Log.d(TAG, "Info: " + what + "," + extra);
//
//		// TODO Auto-generated method stub
//		Utils.printLog(TAG,
//				" %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%5 onInfo: " + what
//						+ "," + extra);
//
//		int infotype = -1;
//		if (what == ErrorConsts.media_player_buffering) {
//			infotype = CommonConst.media_player_buffering;
//		} else if (what == ErrorConsts.media_player_buffered) {
//			infotype = CommonConst.media_player_buffered;
//		} else if (what == TMediaPlayer.TMEDIA_ERROR_CODEC_NOT_SUPPORT) {
//			if(extra == TMediaPlayer.TAUDIO_NOT_SUPPORT){
//				infotype = CommonConst.unknown_audio_format;
//			}else if(extra == TMediaPlayer.TVIDEO_NOT_SUPPORT){
//				infotype = CommonConst.unknown_video_format;
//			}else if(extra == TMediaPlayer.TAUDIO_VIDEO_NOT_SUPPORT){
//				infotype = CommonConst.unknown_media_format;
//			}
//		} else if (what == TMediaPlayer.TMEDIA_ERROR_NO_AUDIO) {
//			infotype = CommonConst.media_player_no_audio;
//		} else if (what == ErrorConsts.media_player_not_seekable) {
//			infotype = CommonConst.media_player_not_seekable;
//		} else if(what ==MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START){
//			infotype = CommonConst.media_player_startplayer_firstframe;
//		} else {
//			infotype = what;
//		}
//
//		if (infotype == CommonConst.unknown_audio_format||infotype == CommonConst.media_player_no_audio) {
//			isAudioUsable = false;
//		}
//		if (callback != null) {
//			callback.onVideoPlayInfoNotify(infotype);
//		}
//		return true;
//
//	}

	@Override
	public boolean isVOD() {
		// TODO Auto-generated method stub
		return isOnline;
	}

	@Override
	public int getPlayType() {
		// TODO Auto-generated method stub
		EnTCLDtvPvrMediaPlayMode  mPlayType = mTDtvPvrManager.getPlayMode();
		if(mPlayType == EnTCLDtvPvrMediaPlayMode.EN_PLAY_MODE_NORMAL){
			mCurrentPlayerType = 0;
		}else if(mPlayType == EnTCLDtvPvrMediaPlayMode.EN_PLAY_MODE_REPEAT_ONE){
			mCurrentPlayerType = 1;
		}else if(mPlayType == EnTCLDtvPvrMediaPlayMode.EN_PLAY_MODE_RANDOM){
			mCurrentPlayerType = 2;
		}else if(mPlayType == EnTCLDtvPvrMediaPlayMode.EN_PLAY_MODE_REPEAT_ALL){
			mCurrentPlayerType = 3;
		}
		return mCurrentPlayerType;
	}



	@Override
	public int getAudioTrackNms() {
		// TODO Auto-generated method stub
		
		
		int nms = 0;
		nms = mTDtvPvrManager.getAudioTrackNumber();
		Utils.printLog(TAG, "getAudioTrackNms = " + nms);
		return nms;
	}





	@Override
	public int getSubtitleNms() {
		int nms = 0;

		nms = mTDtvPvrManager.getSubtitleNumber();
		Utils.printLog(TAG, "getSubtitleNms = " + nms);
		return nms;
	}

	@Override
	public void setAudioTrackNms(short nums) {
		// TODO Auto-generated method stub
		mTDtvPvrManager.setAudioTrack((int)nums);
		Utils.printLog(TAG, "setAudioTrack = " + nums);
	}

	@Override
	public void setSubtitleNms(short nums) {
		// TODO Auto-generated method stub
//		if (mMediaPlayer != null && isMediaPlayerPrepared()) {
//			// && isPlaying()
//			Utils.printLog(TAG, "setSubtitleNms = " + nums);
//			try {
//				if (nums == VideoPlayerContrlConsts.DEFAULT_SUBTITLE) {
//					mMediaPlayer.offSubtitleTrack();
//				} else {
//					mMediaPlayer.onSubtitleTrack();
//					mMediaPlayer.setSubtitleTrack(nums);			
//				}
//
//				mCurrentSubTitle = nums;
//			} catch (Exception e) {
//				e.printStackTrace();
//				// TODO: handle exception
//			}
//
//		}
	}

	@Override
	public int getCurrentAudioTrackNms() {
		// TODO Auto-generated method stub

		int nms = 0;
		nms = mTDtvPvrManager.getAudioTrackIndex();
		Utils.printLog(TAG, "getCurrentAudioTrackNms = " + nms);
		return nms;
	}

	@Override
	public int getCurrentSubtitleNms() {
		// TODO Auto-generated method stub

		Utils.printLog(TAG, "getCurrentSubtitleNms = " + mCurrentSubTitle);

		return mCurrentSubTitle;
	}

	@Override
	public String getCurrentAudioTrackName() {
		// TODO Auto-generated method stub
		
		String strTitle = "";
	
		return strTitle;
	}

	@Override
	public String getCurrentSubtitleText() {
		// TODO Auto-generated method stub
//		if (mMediaPlayer != null && isMediaPlayerPrepared()) {
//			return mMediaPlayer.getSubtitleData();
//		}
		return null;
	}

	@Override
	public void setSubtiteSurface(SurfaceHolder holder) {
		// TODO Auto-generated method stub
//		mSubTitleHolder = holder;
//		if (mMediaPlayer != null && isMediaPlayerPrepared()) {
//			Utils.printLog(TAG, "setSubtiteSurface");
//			try {
//				mMediaPlayer.setSubtitleDisplay(holder);
//			} catch (Exception e) {
//				// TODO: handle exception
//				e.printStackTrace();
//			}
//
//		}
	}

	@Override
	public boolean isFinishInit() {
		// TODO Auto-generated method stub
		Utils.printLog(TAG, "isFinishInit = "+isFinishInit);
		return isFinishInit;
	}

	@Override
	public void setFinishInit(boolean isfinish) {
		// TODO Auto-generated method stub
		Utils.printLog(TAG, "setFinishInit = "+isfinish);
		isFinishInit = isfinish;
	}

	@Override
	public int isDobby(int audioTrack) {
		// TODO Auto-generated method stub
		int dobby = 0;
		if (mMediaPlayer != null && isMediaPlayerPrepared()) {
			dobby = mMediaPlayer.checkCurrentStreamIsDolby();
		}
		return dobby;
	}

	@Override
	public int isDTS(int audioTrack) {
		// TODO Auto-generated method stub
		int dobby = 0;
	//	if (mMediaPlayer != null && isMediaPlayerPrepared()) {
	//		dobby = mMediaPlayer.checkCurrentStreamIsDTS();
	//	}
		return dobby;
	}

	@Override
	public int[] getMediaInfo() {
		// TODO Auto-generated method stub
		int [] mMediaInfo =new int[9] ;
		if (mMediaPlayer != null && isMediaPlayerPrepared()) {
			mMediaInfo = mMediaPlayer.tclGetVideoInfo();
		}
		return mMediaInfo;
	}

	@Override
	public void play(List<String> mList, int index) {
		// TODO Auto-generated method stub
		playList = mList;
		playIndex = index;
	}

	
	

}
