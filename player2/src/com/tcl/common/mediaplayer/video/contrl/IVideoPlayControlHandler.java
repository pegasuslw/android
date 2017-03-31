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
package com.tcl.common.mediaplayer.video.contrl;

import android.media.MediaPlayer;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
import android.tcl.mediaplayer.forceTV.forceTVOperate;
import android.text.method.MultiTapKeyListener;
import android.util.Log;
import android.view.SurfaceHolder;

import com.forcetech.android.ForceTV;
import com.tcl.common.mediaplayer.audio.contrl.AudioPlayerService;
import com.tcl.common.mediaplayer.aidl.CommonConst;
import com.tcl.common.mediaplayer.aidl.MediaBean;
import com.tcl.common.mediaplayer.utils.ErrorConsts;
import com.tcl.common.mediaplayer.utils.MyToast;
import com.tcl.common.mediaplayer.utils.Utils;

import com.tcl.common.mediaplayer.R;
import com.tcl.media.AudioTrackInfo;
import com.tcl.media.SubtitleTrackInfo;
import com.tcl.media.TMediaPlayer;
import com.tcl.os.system.SystemProperties;

public class IVideoPlayControlHandler implements VideoPlayControlInterface,
		OnBufferingUpdateListener, OnCompletionListener, OnPreparedListener,
		OnVideoSizeChangedListener, OnErrorListener, OnSeekCompleteListener,
		OnInfoListener, SurfaceHolder.Callback {

	private String TAG = "IVideoPlayControl";
	private List<MediaBean> playList;
	private int playIndex = 0;
	private VideoPlayControlCallback callback = null;
	private TMediaPlayer mMediaPlayer = null;
	private int mCurrentPlayerType;
	private boolean mIsVideoReadyToBePlayed = false;
	public forceTVOperate hForceTVClient = new forceTVOperate();
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
    private int mFirstEject =0;
    private boolean bVideoDisplayByHardware = false;   //add for shuping video 20161124
    
    public boolean isbVideoDisplayByHardware() {
		return bVideoDisplayByHardware;
	}

	public void setbVideoDisplayByHardware(boolean bVideoDisplayByHardware) {
		this.bVideoDisplayByHardware = bVideoDisplayByHardware;
	}
    
	public IVideoPlayControlHandler(Context context) {
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
//			if (Intent.ACTION_SHUTDOWN.equalsIgnoreCase(intent.getAction())) {
//				Utils.printLog(TAG, "DEVICE_SHUTDOWN broadCast");
//				releaseMediaPlayer();
//				if (callback != null) {
//					callback.onVideoPlayError(CommonConst.DEVICE_SHUTDOWN);
//				}
//			}
		}

	};

	private BroadcastReceiver mDeviceUnmountBroadcastReceiver = new BroadcastReceiver() {

		public void onReceive(Context context, Intent intent) {
			Utils.printLog(TAG,
					"mDeviceUnmountBroadcastReceiver" + intent.getDataString());
			Log.d("DeviceUnmoutedReceiver",
					"Unmouted == " + intent.getDataString());
			if (playList == null
					|| playList.size() < playIndex
					|| playList.get(playIndex).mPath
							.startsWith(CommonConst.HTTPFILE)
					|| playList.get(playIndex).mPath
							.startsWith(CommonConst.SMBFILE)) {// 播放的为网络视频，则不做处理；
				Utils.printLog(TAG, "No deal and exit");
				return;
			}
			Utils.printLog(TAG,
					"start deal and exit" + "action =" + intent.getAction());
			if ((intent.getAction().equals(Intent.ACTION_MEDIA_UNMOUNTED)
					|| intent.getAction().equals(
							Intent.ACTION_MEDIA_BAD_REMOVAL)
					|| intent.getAction().equals(Intent.ACTION_MEDIA_REMOVED) || intent
					.getAction().equals(Intent.ACTION_MEDIA_EJECT))
					&& playList != null
					&& playIndex >= 0
					&& playList.size() > playIndex) {
				Utils.printLog(TAG, "start deal action ok");
				if (playList.get(playIndex).mPath.contains(intent
						.getDataString().substring(7))&& mFirstEject ==0) {
					mFirstEject = 1;
					Utils.printLog(TAG, "start deal  Device Unmouted +"
							+ intent.getDataString().substring(7));

					releaseMediaPlayer();
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

		mCurrentSubTitle = VideoPlayerContrlConsts.DEFAULT_SUBTITLE;
		mCurrentAudioTrack = VideoPlayerContrlConsts.DEFAULT_AUDIO_TRACK;
		mSubTitleInternNum = 0;
		mSubtitleExtNum = 0;
		isAudioUsable = true;
        if(playList == null || 
        		(playIndex > playList.size() || playIndex == playList.size())){
        	return ;
        }
		MediaBean mediaBean = playList.get(playIndex);

		String path = mediaBean.mPath;
		Utils.printLog(TAG, "mediaBean.mURLType=" + mediaBean.mURLType);
		Utils.printLog(TAG, "mediaBean.path=" + path);

		if (Utils.URL_FOR_VOD_TYPE.equalsIgnoreCase(mediaBean.mURLType)) {
			isOnline = true;
			String ChanID = path.substring(path.lastIndexOf("/") + 1,
					path.lastIndexOf("."));

			String Server = path.substring(7, path.lastIndexOf("/"));
			// hForceTVClient.stopAllChannels();
			// hForceTVClient.openForceTVTask();
			if (!isForTVOpen) {
				Utils.printLog(TAG, "initForceClient");
				ForceTV.initForceClient(thisContext);
				isForTVOpen = true;
			}

			try {
				path = hForceTVClient.readyToPlay(ChanID, Server);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				return;
			}
			Utils.printLog(TAG, "hForceTVClient.ChanID=" + ChanID);
			Utils.printLog(TAG, "hForceTVClient.Server=" + Server);

		} else {
			if (!path.startsWith("http") && !new File(path).exists()) {
				Utils.printLog(TAG, "文件不存在；");
				if (callback != null) {
					callback.onVideoPlayError(CommonConst.media_source_not_found);
				}
				playNextVideoAfterError();
				return;
			}
		}

		initMediaPlayer(path);
	}

	private File[] getExtSubPath(String videoPath) {
		if (videoPath == null)
			return null;
		String fileBegin = videoPath.substring(videoPath.lastIndexOf("/") + 1,
				videoPath.lastIndexOf("."));

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
			Utils.printLog(TAG, "init mediaplayer url=" + path);

			// if(mMediaPlayer == null){
			mMediaPlayer = new TMediaPlayer();
			// }

			mMediaPlayer.reset();
			mMediaPlayer.setDataSource(path);
			mMediaPlayer.setDisplay(videoholder);
			mMediaPlayer.setOnErrorListener(this);
			mMediaPlayer.setOnBufferingUpdateListener(this);
			mMediaPlayer.setOnCompletionListener(this);
			mMediaPlayer.setOnPreparedListener(this);
			mMediaPlayer.setOnVideoSizeChangedListener(this);
			mMediaPlayer.setOnInfoListener(this);
			mMediaPlayer.setOnSeekCompleteListener(this);
			mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
//			 if(!path.startsWith("http")&& mMediaPlayer!=null){
//			 String mExtSubPath = path.substring(0, path.lastIndexOf("."));
//			 if(mExtSubPath!=null){
//			 mMediaPlayer.setSubtitleDataSource(mExtSubPath);
//			 Utils.printLog(TAG, "setExtSubtitleURI = "+mExtSubPath);
//			 }
//			
//			 }
			mMediaPlayer.prepareAsync();
			Utils.printLog(TAG, "init mediaplayer  --- prepareAsync");
			isFinishInit = true;
			
		} catch (Exception ex) {
			ex.printStackTrace();
			mIsVideoReadyToBePlayed = false;

			releaseMediaPlayer();
			if (ex instanceof IOException) {
				if(ex.getMessage()!=null&&ex.getMessage().contains("0x8000000A")){
					Utils.printLog(TAG, "ex.getMessage().contains(0x8000000A)");
					//onError(mMediaPlayer, TMediaPlayer.TMEDIA_ERROR_CODEC_NOT_SUPPORT,
					//		TMediaPlayer.TAUDIO_VIDEO_NOT_SUPPORT);
				}else{
					onError(mMediaPlayer, MediaPlayer.MEDIA_ERROR_UNKNOWN,
							ErrorConsts.ERROR_IO);
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
			if (mMediaPlayer != null) {
				return mMediaPlayer.getCurrentPosition();
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
			if (mMediaPlayer != null) {
				return mMediaPlayer.getDuration();
			} else {
				return 0;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}

	}

	@Override
	public List<MediaBean> getPlayList() {
		// TODO Auto-generated method stub

		return playList;
	}

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
		try {
			if (mMediaPlayer != null) {
				return mMediaPlayer.isPlaying();
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

		try {
			if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
				Utils.printLog(TAG, "pause");
				mMediaPlayer.pause();
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
	public void play(List<MediaBean> mList, int index, SurfaceHolder holder_temp) {

		// TODO Auto-generated method stub
		releaseMediaPlayer();
		playList = mList;
		playIndex = index;
		videoholder = holder_temp;
		videoholder.addCallback(this);

		if (callback != null) {
			callback.onVideoPlayChanged(playIndex, 0);
		}
		playInit(playIndex);

	}

	@Override
	public boolean playNext() {

		Utils.printLog(TAG, "playNext with play type = " + mCurrentPlayerType
				+ " index=" + playIndex);
		switch (mCurrentPlayerType) {

		case VideoPlayerContrlConsts.MEDIA_RANDOM_PLAY:
			Utils.printLog(TAG, "随机播放");
			if(playList.size() == 1){
				return false;
			}
			playIndex = Utils.getRandomFromSize(playList.size(), playIndex);
			Utils.printLog(TAG, "random =" + playIndex);
			break;
		case VideoPlayerContrlConsts.MEDIA_CYCLE_PLAY:
			Utils.printLog(TAG, "循环播放");
			playIndex = ++playIndex % playList.size();
			break;
		default:
			Utils.printLog(TAG, "index =" + playIndex + "  listsize="
					+ playList.size());
			if (playIndex == playList.size() - 1) {
				// playIndex = VideoPlayerContrlConsts.MEDIA_PALY_FINISHED;
				if (callback != null) {
					callback.onVideoPlayInfoNotify(CommonConst.media_player_already_lastfiles);
				}
				return false;
			} else {
				playIndex++;
			}
			break;
		}

		int currentPosition = getCurrentPosition();
		if (callback != null) {
			callback.onVideoPlayChanged(playIndex, currentPosition);
		}
		if (playIndex != VideoPlayerContrlConsts.MEDIA_PALY_FINISHED) {
			releaseMediaPlayer();
			playInit(playIndex);
		}

		return true;
	}

	@Override
	public boolean playPrevious() {
		// TODO Auto-generated method stub
		switch (mCurrentPlayerType) {

		case VideoPlayerContrlConsts.MEDIA_RANDOM_PLAY:
			if(playList.size() == 1){
				return false;
			}
			playIndex = Utils.getRandomFromSize(playList.size(), playIndex);
			break;
		case VideoPlayerContrlConsts.MEDIA_CYCLE_PLAY:
			if (playIndex == 0)
				playIndex = +playList.size();
			playIndex = --playIndex % playList.size();
			break;
		default:
			if (playIndex == 0) {
				// playIndex = VideoPlayerContrlConsts.MEDIA_PALY_FINISHED;
				if (callback != null) {
					callback.onVideoPlayInfoNotify(CommonConst.media_player_already_firstfiles);
				}
				return false;
			} else {
				playIndex--;
			}

			break;
		}

		int currentPosition = getCurrentPosition();
		if (callback != null) {
			callback.onVideoPlayChanged(playIndex, currentPosition);
		}
		if (playIndex != VideoPlayerContrlConsts.MEDIA_PALY_FINISHED) {
			releaseMediaPlayer();
			playInit(playIndex);
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

		this.mCurrentPlayerType = type;
	}

	@Override
	public void start() {
		// TODO Auto-generated method stub
		try {
			if (mMediaPlayer != null) {
				Utils.printLog(TAG, "start");
				mMediaPlayer.start();
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
			if (mMediaPlayer != null && isPlaying()) {
				Utils.printLog(TAG, "stop ");
				mMediaPlayer.stop();
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

	@Override
	public void onBufferingUpdate(MediaPlayer mp, int percent) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onCompletion(MediaPlayer mp) {
		// TODO Auto-generated method stub
		Utils.printLog(TAG, "-----onCompletion");
		if(callback != null){
			callback.onVideoPlayComplete();
		}
		mIsVideoReadyToBePlayed = false;
		releaseMediaPlayer();
		if (playList != null && playList.size() > 0) {
			switch (mCurrentPlayerType) {
			case VideoPlayerContrlConsts.MEDIA_SEQUENCE_PLAY:

				if (playIndex == playList.size() - 1) {
					playIndex = VideoPlayerContrlConsts.MEDIA_PALY_FINISHED;
				} else {
					playIndex++;
				}
				break;
			case VideoPlayerContrlConsts.MEDIA_SINGLE_PLAY:
				playIndex = VideoPlayerContrlConsts.MEDIA_PALY_FINISHED;
				break;
			case VideoPlayerContrlConsts.MEDIA_SINGLE_REPEAT_PLAY:
				break;
			case VideoPlayerContrlConsts.MEDIA_RANDOM_PLAY:
				playIndex = Utils.getRandomFromSize(playList.size(), playIndex);
				break;
			case VideoPlayerContrlConsts.MEDIA_CYCLE_PLAY:
				playIndex = ++playIndex % playList.size();
				break;
			}
		} else {
			playIndex = VideoPlayerContrlConsts.MEDIA_PALY_FINISHED;
		}

		if (callback != null) {
			callback.onVideoPlayChanged(playIndex, 0);
		}
		if (playIndex != VideoPlayerContrlConsts.MEDIA_PALY_FINISHED) {
			playInit(playIndex);
		}

	}

	// 当播放器准备完成，则通知UI，由其完成开始播放和快进等操作。
	@Override
	public void onPrepared(MediaPlayer mp) {
		Utils.printLog(TAG, "onPrepared");
		mIsVideoReadyToBePlayed = true;

		//mMediaPlayer.offSubtitleTrack();

		if (callback != null) {
			callback.onVideoPlayPrepared();
		}
		// String path =null;
		// try{
		//
		// path= playList.get(playIndex).mPath;
		// }catch(Exception ex){
		// ex.printStackTrace();
		// }
		//
		// if (path!=null && !path.startsWith("http") &&mMediaPlayer != null) {
		// String mExtSubPath = getExtSubPath(path);
		// if (mExtSubPath != null) {
		// if(mExtSubPath.endsWith("idx")){
		//
		// }else{
		// mMediaPlayer.setSubtitleDataSource(mExtSubPath);
		// }
		// Utils.printLog(TAG, "setExtSubtitleURI = " + mExtSubPath);
		// }
		//
		// }

	}

	@Override
	public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
		// TODO Auto-generated method stub
		Utils.printLog(TAG, "onVideoSizeChanged width="+width+", height="+height+"");
		callback.onVideoSizeChanged(width, height);
	}

	@Override
	public boolean onError(MediaPlayer mp, int what, int extra) {

		Log.d(TAG, "Error: " + what + "," + extra);
		mIsVideoReadyToBePlayed = false;

		int errorType = -1;
		if (what == MediaPlayer.MEDIA_ERROR_SERVER_DIED) {
			errorType = CommonConst.media_player_exception;
		}  
		//else if (what == TMediaPlayer.TMEDIA_ERROR_CODEC_NOT_SUPPORT) {
		//	if(extra == TMediaPlayer.TAUDIO_VIDEO_NOT_SUPPORT){
		//		errorType = CommonConst.unknown_media_format;
		//	}
		//} 
	    else if (what == MediaPlayer.MEDIA_ERROR_UNKNOWN) {
			if (extra == ErrorConsts.unknown_media_format) {
				errorType = CommonConst.unknown_media_format;
			} else if (extra == ErrorConsts.media_interrupt
					|| extra == ErrorConsts.ERROR_IO) {
				errorType = CommonConst.media_interrupt;
			} else if (extra == ErrorConsts.media_player_network_slow
					|| extra == ErrorConsts.ERROR_NOT_CONNECTED
					|| extra == ErrorConsts.ERROR_UNKNOWN_HOST
					|| extra == ErrorConsts.ERROR_CANNOT_CONNECT
					|| extra == ErrorConsts.ERROR_CONNECTION_LOST) {
				errorType = CommonConst.media_player_network_slow;
			} else if (extra == ErrorConsts.unknown_video_format) {
				errorType = CommonConst.unknown_video_format;
			} else if (extra == ErrorConsts.unknown_audio_format) {
				errorType = CommonConst.unknown_audio_format;
			} else {
				errorType = CommonConst.media_player_unknown_exception;
			}
		} else if (what == ErrorConsts.media_player_unknown_exception_38) {

			errorType = CommonConst.media_player_unknown_exception_38;
		} else if (what == ErrorConsts.tv_sourcemanager_change) {

			errorType = CommonConst.tv_sourcemanager_change;
		}else {
			errorType = CommonConst.media_player_unknown_exception;
		}
			
		releaseMediaPlayer();
			if (callback != null) {
				callback.onVideoPlayError(errorType);
			}
			if (errorType == CommonConst.unknown_media_format
					|| errorType == CommonConst.media_interrupt
					|| errorType == CommonConst.unknown_video_format
					|| errorType == CommonConst.media_player_network_slow) {
				new Thread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						playNextVideoAfterError();
					}
				}).start();

			}


		return false;
	}

	private void playNextVideoAfterError() {

		if (playList != null && playList.size() > 1
				&& playIndex < playList.size()) {
			Utils.printLog(TAG, "playNextVideoAfterError with index="
					+ playIndex + " listsize=" + playList.size());
			playList.remove(playIndex);
			if (playIndex >= playList.size()) {// 播放到最后一集，则播放下一集。
				playIndex = playList.size() - 1;
				onCompletion(mMediaPlayer);
				// if(!playNext()){
				// int currentPosition = getCurrentPosition();
				// if (callback != null) {
				// callback.onVideoPlayChanged(VideoPlayerContrlConsts.MEDIA_PALY_FINISHED,
				// currentPosition);
				// }
				// }
			} else {
				if (callback != null) {// 删除播放的错误视频，继续播放当前索引对应的视频；
					callback.onVideoPlayChanged(playIndex,
							VideoPlayerContrlConsts.GET_CURRENTPOSITION_ERROR);
					releaseMediaPlayer();
					playInit(playIndex);
				}
			}

		} else {

			if (callback != null) {
				callback.onVideoPlayError(CommonConst.PLAY_LIST_NULL);
			}
		}

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
		thisContext.unregisterReceiver(mDeviceUnmountBroadcastReceiver);
		thisContext.unregisterReceiver(mShutDownBroadCastReceiver);

	}

	public boolean closeMediaControl() {
		Utils.printLog(TAG, "closeMediaControl");
		SystemProperties.set("ms.subtitle.language","0");
		releaseMediaPlayer();
		System.out
				.println("==========================================closeMediaControl");
		if (isForTVOpen) {

			hForceTVClient.stopAllChannels();
			isForTVOpen = false;
			Utils.printLog(TAG, " hForceTVClient.stopAllChannels()");
		}
		hForceTVClient = null;
		// playList.clear();
		// playList = null;

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
					onCompletion(mMediaPlayer);
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
	

		try {
			if (mMediaPlayer != null && isMediaPlayerPrepared()) {
				int duration = mMediaPlayer.getDuration();
				if(duration<aMsec ||duration ==aMsec){
					onCompletion(mMediaPlayer);
				}else{
					Utils.printLog(TAG, "will seek to aMsec" + aMsec);
					if (callback != null) {
						callback.onVideoSeekStart(aMsec);
					}
					mMediaPlayer.seekTo(aMsec);
				}
			
			}
		} catch (Exception e) {
			Log.e(TAG, "IllegalStateException");
			e.printStackTrace();
		}

		Utils.printLog(TAG, " seek to finish =" + getCurrentPosition());

	}

	@Override
	public void onSeekComplete(MediaPlayer mp) {
		// TODO Auto-generated method stub

		Utils.printLog(TAG, "onSeekComplete");
		if (callback != null) {
			callback.onVideoPlaySeekComplete(getCurrentPosition());
		}
	}

	@Override
	public boolean onInfo(MediaPlayer mp, int what, int extra) {

		Log.d(TAG, "Info: " + what + "," + extra);

		// TODO Auto-generated method stub
		Utils.printLog(TAG,
				" %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%5 onInfo: " + what
						+ "," + extra);
		if (1005 == what) {// TMediaPlayer.MEDIA_INFO_VIDEO_DISPLAY_BY_HARDWARE
			Utils.printLog(TAG, "bVideoDisplayByHardware = true");
			bVideoDisplayByHardware = true;
		} else {
			Utils.printLog(TAG, "bVideoDisplayByHardware = false");
			bVideoDisplayByHardware = false;
		}

		int infotype = -1;
		if (what == ErrorConsts.media_player_buffering) {
			infotype = CommonConst.media_player_buffering;
		} else if (what == ErrorConsts.media_player_buffered) {
			infotype = CommonConst.media_player_buffered;
		} 
		//else if (what == TMediaPlayer.TMEDIA_ERROR_CODEC_NOT_SUPPORT) {
		//	if(extra == TMediaPlayer.TAUDIO_NOT_SUPPORT){
		//		infotype = CommonConst.unknown_audio_format;
		//	}else if(extra == TMediaPlayer.TVIDEO_NOT_SUPPORT){
		//		infotype = CommonConst.unknown_video_format;
		//	}else if(extra == TMediaPlayer.TAUDIO_VIDEO_NOT_SUPPORT){
		//		infotype = CommonConst.unknown_media_format;
		//	}
		//} else if (what == TMediaPlayer.TMEDIA_ERROR_NO_AUDIO) {
		//	infotype = CommonConst.media_player_no_audio;
		//} 
		else if (what == 1002) {
			infotype = CommonConst.unknown_audio_format;
		} else if (what == 1003) {
			infotype = CommonConst.unknown_video_format;
			if (callback != null) {
				callback.onVideoPlayInfoNotify(infotype);
			}
			new Thread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					Log.d(TAG, "Info = 1003  run");
					playNextVideoAfterError();
				}
			}).start();
			return true;
		} else if (what == 1000 && extra == 1) {
			infotype = CommonConst.media_player_subtitle_update;
		} else if (what == 1000 && extra == 0) {
			infotype = CommonConst.media_player_subtitle_null;
		} else if (what == ErrorConsts.media_player_not_seekable) {
			infotype = CommonConst.media_player_not_seekable;
		} else if(what ==MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START){
			infotype = CommonConst.media_player_startplayer_firstframe;
		} else {
			infotype = what;
		}

		if (infotype == CommonConst.media_player_no_audio) {
			isAudioUsable = false;
		}
		if (callback != null) {
			callback.onVideoPlayInfoNotify(infotype);
		}
		return true;

	}

	@Override
	public boolean isVOD() {
		// TODO Auto-generated method stub
		return isOnline;
	}

	@Override
	public int getPlayType() {
		// TODO Auto-generated method stub
		return mCurrentPlayerType;
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		Utils.printLog(TAG, "surfaceChanged");
		// TODO Auto-generated method stub

	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		Utils.printLog(TAG, "surfaceCreated");
		videoholder = holder;
		if (mMediaPlayer != null) {
			try {
				mMediaPlayer.setDisplay(videoholder);
			} catch (Exception e) {
				e.printStackTrace();
				// TODO: handle exception
			}
		}

		if (callback != null) {
			callback.onSurfaceCreated();
		}
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		if(mMediaPlayer != null){
			Utils.printLog(TAG, "surfaceDestroyed");
			callback.onSurfaceDertory();
		}else{
			Utils.printLog(TAG, "mMediaPlayer is already null");
		}
		
		// releaseMediaPlayer();
	}

	@Override
	public int getAudioTrackNms() {
		// TODO Auto-generated method stub
		
		
		int nms = 0;
		if (mMediaPlayer != null && isMediaPlayerPrepared() && isAudioUsable) {

			AudioTrackInfo audioTrackNum = 	mMediaPlayer.getAudioTrackInfo();
			if(audioTrackNum!=null){
				nms = audioTrackNum.getAllAudioTrackCount();
			}
		}
		Utils.printLog(TAG, "getAudioTrackNms = " + nms);
		return nms;
	}





	@Override
	public int getSubtitleNms() {
		int nms = 0;

		if (mMediaPlayer != null && isMediaPlayerPrepared()) {		
			 SubtitleTrackInfo subtitleTrackNum =  mMediaPlayer.getAllSubtitleTrackInfo();
			 if(subtitleTrackNum!=null){
				nms = subtitleTrackNum.getAllSubtitleCount();
			 }
		

		}
		Utils.printLog(TAG, "getSubtitleNms = " + nms);
		return nms;
	}

	@Override
	public void setAudioTrackNms(short nums) {
		// TODO Auto-generated method stub
		if (mMediaPlayer != null && isMediaPlayerPrepared()) {

			try {
				mMediaPlayer.setAudioTrack(nums);
				mCurrentAudioTrack = nums;
				Utils.printLog(TAG, "setAudioTrackNms = " + nums);
			} catch (Exception e) {
				e.printStackTrace();
				// TODO: handle exception
			}

		}
	}

	@Override
	public void setSubtitleNms(short nums) {
		// TODO Auto-generated method stub
		if (mMediaPlayer != null && isMediaPlayerPrepared()) {
			// && isPlaying()
			Utils.printLog(TAG, "setSubtitleNms = " + nums);
			try {
				if (nums == VideoPlayerContrlConsts.DEFAULT_SUBTITLE) {
					mMediaPlayer.offSubtitleTrack();
				} else {
					mMediaPlayer.onSubtitleTrack();
					mMediaPlayer.setSubtitleTrack(nums);			
				}

				mCurrentSubTitle = nums;
			} catch (Exception e) {
				e.printStackTrace();
				// TODO: handle exception
			}

		}
	}

	@Override
	public int getCurrentAudioTrackNms() {
		// TODO Auto-generated method stub

//		if (mMediaPlayer != null && isMediaPlayerPrepared() && isAudioUsable) {
//
//			try {
//				Metadata data = mMediaPlayer.getMetadata(true, true);
//				if (data != null && data.has(Metadata.CURRENT_AUDIO_TRACK_ID)) {
//					int nms = data.getInt(Metadata.CURRENT_AUDIO_TRACK_ID);
//					if (nms >= 1) {
//						mCurrentAudioTrack = nms;
//					}
//					Utils.printLog(TAG,
//							"data.getInt(Metadata.CURRENT_AUDIO_TRACK_ID) = "
//									+ nms);
//				}
//			} catch (Exception e) {
//				e.printStackTrace();
//				// TODO: handle exception
//			}
//
//		}
		Utils.printLog(TAG, "getCurrentAudioTrackNms = " + mCurrentAudioTrack);
		return mCurrentAudioTrack;
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
		if (mMediaPlayer != null && isMediaPlayerPrepared()) {
			String mm =  mMediaPlayer.getSubtitleData();
			Log.d(TAG,"now mm is :"+mm);
			return mm;
		}
		return "";
	}

	@Override
	public void setSubtiteSurface(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		mSubTitleHolder = holder;
		if (mMediaPlayer != null && isMediaPlayerPrepared()) {
			Utils.printLog(TAG, "setSubtiteSurface");
			try {
				mMediaPlayer.setSubtitleDisplay(holder);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}

		}
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
//		if (mMediaPlayer != null && isMediaPlayerPrepared()) {
//			dobby = mMediaPlayer.checkCurrentStreamIsDTS();
//		}
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
	public boolean playUrl(List<MediaBean> mList, int index) {
		// TODO Auto-generated method stub
		releaseMediaPlayer();
		playList = mList;
		playIndex = index;
		if (callback != null) {
			callback.onVideoPlayChanged(playIndex, 0);
		}
		if (playIndex != VideoPlayerContrlConsts.MEDIA_PALY_FINISHED) {
			playInit(playIndex);
		}
		return false;
	}

}
