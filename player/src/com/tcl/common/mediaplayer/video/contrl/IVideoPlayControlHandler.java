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
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

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
import android.tcl.mediaplayer.forcetv.ForceTVOperate;
import android.util.Log;
import android.view.SurfaceHolder;

import com.forcetech.android.ForceTV;
import com.tcl.common.mediaplayer.aidl.CommonConst;
import com.tcl.common.mediaplayer.aidl.MediaBean;
import com.tcl.common.mediaplayer.utils.ErrorConsts;
import com.tcl.common.mediaplayer.utils.MediaPlayerApplication;
import com.tcl.common.mediaplayer.utils.Utils;
import com.tcl.common.mediaplayer.video.UI.VideoPlayerActivity;
import com.tcl.media.AudioTrackInfo;
import com.tcl.media.SubtitleTrackInfo;
import com.tcl.media.TMediaPlayer;
import com.tcl.tvmanager.TvManager;

public class IVideoPlayControlHandler implements VideoPlayControlInterface, OnBufferingUpdateListener, OnCompletionListener, OnPreparedListener, OnVideoSizeChangedListener,
		OnErrorListener, OnSeekCompleteListener, OnInfoListener, SurfaceHolder.Callback {

	private String TAG =Utils.PKG_NAME +  "IVideoPlayControlHandler";
	private List<MediaBean> playList;
	private int playIndex = 0;
	private VideoPlayControlCallback callback = null;
	private TMediaPlayer mMediaPlayer = null;
	private int mCurrentPlayerType;
	private boolean mIsVideoReadyToBePlayed = false;
	private boolean mIsVideoReleasing = true;
	private boolean mIsActionPlayNext = true;
	public ForceTVOperate hForceTVClient = new ForceTVOperate();
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
	private int mFirstEject = 0;
	private boolean bVideoDisplayByHardware = false;
	private Handler mVideoHandler;
	private final int NEXTMSG = 10;
	private int mChapters = 0;
	private int mCurChapter = 1;
	///20161201
	public int getChapters(){
		return mChapters;
	}
	
	public void setChapters(int mCter){
		this.mChapters = mCter;
	}
	
	public int getCurrentChapter(){
		return mCurChapter;
	}
	
	public void setCurrentChapter(int index){
		this.mCurChapter = index;
	}
	// add here for 5658 more chapters 201601201
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

		thisContext.registerReceiver(mDeviceUnmountBroadcastReceiver, intentFilter2);

		//thisContext.registerReceiver(mShutDownBroadCastReceiver, intentFilter1);

		thisContext.sendBroadcast(new Intent(CommonConst.CLOSE_AUDIO_PLAY));
		mVideoHandler = new Handler();
	}

	private BroadcastReceiver mDeviceUnmountBroadcastReceiver = new BroadcastReceiver() {

		public void onReceive(Context context, Intent intent) {
			Utils.printLog(TAG, "mDeviceUnmountBroadcastReceiver" + intent.getDataString());
			Log.d("DeviceUnmoutedReceiver", "Unmouted == " + intent.getDataString());
			if (playList == null 
					|| playList.size() < playIndex 
					|| playList.get(playIndex).mPath.startsWith(CommonConst.HTTPFILE)
					|| playList.get(playIndex).mPath.startsWith(CommonConst.SMBFILE)) {// 播放的为网络视频，则不做处理；
				Utils.printLog(TAG, "No deal and exit");
				return;
			}
			Utils.printLog(TAG, "start deal and exit" + "action =" + intent.getAction());
			if ((intent.getAction().equals(Intent.ACTION_MEDIA_UNMOUNTED) 
					|| intent.getAction().equals(Intent.ACTION_MEDIA_BAD_REMOVAL)
					|| intent.getAction().equals(Intent.ACTION_MEDIA_REMOVED) 
					|| intent.getAction().equals(Intent.ACTION_MEDIA_EJECT))
					&& playList != null && playIndex >= 0 && playList.size() > playIndex) {
				Utils.printLog(TAG, "start deal action ok");
				if (playList.get(playIndex).mPath.contains(intent.getDataString().substring(7)) 
						&& mFirstEject == 0) {
					mFirstEject = 1;
					Utils.printLog(TAG, "start deal  Device Unmouted +" + intent.getDataString().substring(7));
                    if(mIsVideoReleasing){
    					releaseMediaPlayer();
                    }
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
		if (playList == null) {
			Utils.printLog(TAG, "playInit playList == null");
			return;
		}
		Utils.printLog(TAG, "playInit with index =" + playIndex + "  playList.size()=" + playList.size());

		mCurrentSubTitle = VideoPlayerContrlConsts.DEFAULT_SUBTITLE;
		mCurrentAudioTrack = VideoPlayerContrlConsts.DEFAULT_AUDIO_TRACK;
		mSubTitleInternNum = 0;
		mSubtitleExtNum = 0;
		isAudioUsable = true;
		if (playList == null || (playIndex >= playList.size())) {
			Utils.printLog(TAG, "playInit playIndex >= playList.size()");
			if(mIsVideoReleasing){
				releaseMediaPlayer();	
			}
			if (callback != null) {
				callback.onVideoPlayError(CommonConst.PLAY_LIST_NULL);
			}
			return;
		}
		MediaBean mediaBean = playList.get(playIndex);
		setPlayMediaBean(mediaBean);
		Utils.printLog(TAG, "setPlayMediaBean(mediaBean) = "+mediaBean.mPath);
		String path = mediaBean.mPath;
		Utils.printLog(TAG, "mediaBean.mURLType=" + mediaBean.mURLType);
		Utils.printLog(TAG, "mediaBean.path=" + path);

		if (Utils.URL_FOR_VOD_TYPE.equalsIgnoreCase(mediaBean.mURLType)) {
			isOnline = true;
			String ChanID = path.substring(path.lastIndexOf("/") + 1, path.lastIndexOf("."));

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
			/*
			 * if(!new File(path).exists()){ Utils.printLog(TAG,
			 * "文件不存在1111 path = " +path); }
			 */
			if (!path.startsWith("http") && !new File(path).exists()) {
				Utils.printLog(TAG, "文件不存在；");
				if (callback != null) {
					callback.onVideoPlayError(CommonConst.media_source_not_found);
				}
				releaseMediaPlayer();    //add here 20160926wj
				videoBackH.removeMessages(NEXTMSG);
				videoBackH.sendEmptyMessageDelayed(NEXTMSG, 1000);

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

		String filePath = videoPath.substring(0, videoPath.lastIndexOf("/"));
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
	
	private void initMeidaplayerBackUp(){
		 try {
			   
			   Log.d(TAG, "initMediaPlayer()");
			   Class<?> mClass = Class.forName("com.tcl.media.TMediaPlayer");
			   Constructor<?> mMethod =  mClass.getDeclaredConstructor(Context.class); 
			   if (mMethod != null) {
			    mMediaPlayer = (TMediaPlayer) mMethod.newInstance(MediaPlayerApplication.getContext());
			    Log.d(TAG, " Context INITMEDIAPLAYER");
			   } else {
			    mMediaPlayer = new TMediaPlayer();
			    Log.d(TAG, "ONT Context INITMEDIAPLAYER");
			   }

			  } catch (ClassNotFoundException e) {
			   e.printStackTrace();
			  } catch (NoSuchMethodException e) {
			   mMediaPlayer = new TMediaPlayer();
			   Log.d(TAG, "NoSuchMethodException");
			   e.printStackTrace();
			  } catch (IllegalArgumentException e) {
			   e.printStackTrace();
			  } catch (InvocationTargetException e) {
			   e.printStackTrace();
			  } catch (Exception e) {
			   e.printStackTrace();
			  }
			  
			  if (mMediaPlayer==null) {
			   mMediaPlayer = new TMediaPlayer();
			   Log.d(TAG, "mMediaPlayer = new TMediaPlayer()");
			  }
	}

	private void initMediaPlayer(String path) {
		// 初始化MediaPlayer；
		isFinishInit = false;
		try {
			Utils.printLog(TAG, "init mediaplayer ");
//			releaseMediaPlayer();                 //delete this code 20160926wj
//			 if(mMediaPlayer == null){
//			    mMediaPlayer = new TMediaPlayer(MediaPlayerApplication.getContext());
//			 }
			initMeidaplayerBackUp();   //change this method for 5658 api change 20161008
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
			
			mMediaPlayer.prepareAsync();
			if(callback != null){                         //监听超时退出20160825
				callback.onVideoPlayInfoNotify(CommonConst.GET_DATA_TIMEOUT);
			}
			Utils.printLog(TAG, "init mediaplayer  --- prepareAsync");
			isFinishInit = true;

		} catch (Exception ex) {
			Utils.printLog(TAG, "init mediaplayer  ---Exception==" + ex);
			ex.printStackTrace();
			mIsVideoReadyToBePlayed = false;
            if(mIsVideoReleasing){
    		   releaseMediaPlayer();
            }
			if (ex instanceof IOException) {
				if (ex.getMessage() != null && ex.getMessage().contains("0x8000000A")) {
					Utils.printLog(TAG, "ex.getMessage().contains(0x8000000A)");
				} else {
					onError(mMediaPlayer, MediaPlayer.MEDIA_ERROR_UNKNOWN, ErrorConsts.ERROR_IO);
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
		Utils.printLog(TAG, "getCurrentPosition start");
		try {
			if (mMediaPlayer != null) {
				int currentPosition = mMediaPlayer.getCurrentPosition();
				Utils.printLog(TAG, "getCurrentPosition end currentPosition="+currentPosition);
				return currentPosition;
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

	public void setPlaylist(List<MediaBean> inPlaylist){
		playList = inPlaylist;
	}
	@Override
	public int getPlayingVideoIndex() {
		// TODO Auto-generated method stub
		return playIndex;
	}

	// add here for generUI 20151205 WJ
	public String getPlayingVideoName() {
		String name = "";
		if(null != playList){
			if (playIndex >= 0 && playIndex < playList.size()) {
				name =  playList.get(playIndex).mName;
			}	
		}
		return name ;
	}

	public String getPlayingVideoPath() {
		if (playIndex >= 0 && playIndex < playList.size()) {
			return playList.get(playIndex).mPath;
		}else{
			return "";
		}
	}

	// add here for generUI 20151205 WJ end
	@Override
	public boolean isMediaPlayerPrepared() {
		// TODO Auto-generated method stub
		Log.d(TAG,"now isMediaPlayerPrepared is "+ mIsVideoReadyToBePlayed);
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

		Utils.printLog(TAG, "playNext with play type = " + mCurrentPlayerType + " index=" + playIndex);
		switch (mCurrentPlayerType) {

		case VideoPlayerContrlConsts.MEDIA_RANDOM_PLAY:
			Utils.printLog(TAG, "random Play 随机播放");
			if (playList.size() == 1) {
				return false;
			}
			playIndex = Utils.getRandomFromSize(playList.size(), playIndex);
			Utils.printLog(TAG, "random =" + playIndex);
			break;
		case VideoPlayerContrlConsts.MEDIA_CYCLE_PLAY:
			Utils.printLog(TAG, "loop Play 循环播放");
			playIndex = ++playIndex % playList.size();
			break;
		default:
			Utils.printLog(TAG, "index =" + playIndex + "  listsize=" + playList.size());
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
			if(mIsVideoReleasing){
				releaseMediaPlayer();
			}
			
			mVideoHandler.post(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					playInit(playIndex);
				}
			});
			
		}

		return true;
	}

	@Override
	public boolean playPrevious() {
		Utils.printLog(TAG, "playPrevious");
		switch (mCurrentPlayerType) {

		case VideoPlayerContrlConsts.MEDIA_RANDOM_PLAY:
			if (playList.size() == 1) {
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
			if(mIsVideoReleasing){
			   releaseMediaPlayer();
			}
			
			mVideoHandler.post(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					playInit(playIndex);
				}
			});
		
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
				Utils.printLog(TAG, "mMediaPlayer.reset()");
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
				Utils.printLog(TAG, "mMediaPlayer.start() start");
				mMediaPlayer.start();
				Utils.printLog(TAG, "mMediaPlayer.start()  end");

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
		if (callback != null) {
			callback.onVideoPlayComplete();
		}
		mIsVideoReadyToBePlayed = false;
		if(mIsVideoReleasing){
			releaseMediaPlayer();	
		}
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
			mVideoHandler.post(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					playInit(playIndex);	
				}
			});

		}

	}

	// 当播放器准备完成，则通知UI，由其完成开始播放和快进等操作。
	@Override
	public void onPrepared(MediaPlayer mp) {
		Utils.printLog(TAG, "onPrepared");
		mIsVideoReadyToBePlayed = true;


		if (callback != null) {
			callback.onVideoPlayPrepared();
		}

	}

	@Override
	public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
		// TODO Auto-generated method stub
		Utils.printLog(TAG, "onVideoSizeChanged width="+width+", height="+height+"");
		callback.onVideoSizeChanged(width, height);
	}

	@Override
	public boolean onError(MediaPlayer mp, int what, int extra) {

		Log.d(TAG, "==================================onError:" + what + "," + extra);
		mIsVideoReadyToBePlayed = false;

		int errorType = -1;
		if (what == MediaPlayer.MEDIA_ERROR_SERVER_DIED) {
			errorType = CommonConst.media_player_exception;
		}
		// else if (what == TMediaPlayer.TMEDIA_ERROR_CODEC_NOT_SUPPORT) {
		// if(extra == TMediaPlayer.TAUDIO_VIDEO_NOT_SUPPORT){
		// errorType = CommonConst.unknown_media_format;
		// }
		// }
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
		} else if(what == ErrorConsts.unknown_media_format){
			
			errorType = CommonConst.unknown_media_format;   //for mt5658 onerror (-5003,0)
		} else if(what == ErrorConsts.unknown_video_format){
			
			errorType = CommonConst.unknown_media_format;   //for mt5658 onerror (-5002,0) 20160926
		} 
		else{
			errorType = CommonConst.media_player_unknown_exception;
		}

		if(mIsVideoReleasing){
			Log.d(TAG,"on error to release==");
			releaseMediaPlayer();
		}
		if (callback != null) {
			callback.onVideoPlayError(errorType);
		}
		if (errorType == CommonConst.unknown_media_format
				|| errorType == CommonConst.media_interrupt
				|| errorType == CommonConst.unknown_video_format
				|| errorType == CommonConst.media_player_network_slow) {
//			new Thread(new Runnable() {
//
//				@Override
//				public void run() {
//					// TODO Auto-generated method stub
//					if(mIsActionPlayNext){
//					   playNextVideoAfterError();
//					}				
//				}
//			}).start();
			videoBackH.removeMessages(NEXTMSG);
			videoBackH.sendEmptyMessageDelayed(NEXTMSG, 1000);

		}

		return false;
	}

	private void playNextVideoAfterError() {
		Log.v(TAG, "playNextVideoAfterError");
		mIsActionPlayNext = false;
		if(mCurrentPlayerType == VideoPlayerContrlConsts.MEDIA_SINGLE_REPEAT_PLAY
		   ||mCurrentPlayerType == VideoPlayerContrlConsts.MEDIA_SINGLE_PLAY
		  ){
			Log.v(TAG, "playNextVideoAfterError  SINGLE");
			if (callback != null) {
				callback.onVideoPlayError(CommonConst.FINISH_NO_TIPS);
				mIsActionPlayNext = true; 
				return ;
			}
			
		}
		if (playList != null && playList.size() >= 1
				&& playIndex < playList.size()) {
			Utils.printLog(TAG, "playNextVideoAfterError with index="
					+ playIndex + " listsize=" + playList.size());
			 ++ playIndex;
//			playList.remove(playIndex);
//			setPlaylist(playList);    //update playlist 20170222
			if (playIndex >= playList.size()) {// 播放到最后一集，则播放下一集。
				playIndex = playList.size() - 1;
				onCompletion(mMediaPlayer);
			} else {
				if (callback != null) {// 删除播放的错误视频，继续播放当前索引对应的视频；
					callback.onVideoPlayChanged(playIndex,
							VideoPlayerContrlConsts.GET_CURRENTPOSITION_ERROR);
//					releaseMediaPlayer();     //delete here 20160926wj
					mVideoHandler.post(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							playInit(playIndex);
						}
					});

					
				}
			}

		} else {
			if (callback != null) {
				callback.onVideoPlayError(CommonConst.PLAY_LIST_NULL);
			}
		}

		mIsActionPlayNext = true; 
	}

	@Override
	public boolean releaseMediaPlayer() {
		
		mIsVideoReleasing = false;
		Log.d(TAG,"come to release mediaplayer");
		try {
			if (mMediaPlayer != null) {
				Utils.printLog(TAG, "1releaseMediaPlayer start");
//				if (mMediaPlayer.isPlaying()) {//去掉判断，75307如果已经播放完成，没有调用stop可能导致subtitle没有close导致异常退出（subtitle的close在mMediaPlayer.stop()）
					Utils.printLog(TAG, " 2MediaPlayer stop");
					if(mIsVideoReadyToBePlayed){
						Utils.printLog(TAG, " 3MediaPlayer stop");
						mIsVideoReadyToBePlayed = false;
						mMediaPlayer.stop();	
					}
//				}
				Utils.printLog(TAG, " 3MediaPlayer release");
				mMediaPlayer.release();
				mMediaPlayer = null;
				Utils.printLog(TAG, "4releaseMediaPlayer end ");
//			    mMediaPlayer.reset();
                System.gc();
			}
			mIsVideoReleasing = true;
			mIsVideoReadyToBePlayed = false;
			return true;
		} catch (Exception e) {
			Log.e(TAG, "IllegalStateException");
			e.printStackTrace();
			Utils.printLog(TAG, "releaseMediaPlayer end false");
			mIsVideoReleasing = false;
			return false;
		}

	}

	public void unRegisterReceiver() {
		thisContext.unregisterReceiver(mDeviceUnmountBroadcastReceiver);
		//thisContext.unregisterReceiver(mShutDownBroadCastReceiver);

	}

	public boolean closeMediaControl() {
		Utils.printLog(TAG, "closeMediaControl and releaseMediaPlayer");
		if(mIsVideoReleasing){
		   releaseMediaPlayer();
		}

		System.out.println("==========================================closeMediaControl");
		if (isForTVOpen) {
			hForceTVClient.stopAllChannels();
			isForTVOpen = false;
			Utils.printLog(TAG, " hForceTVClient.stopAllChannels()");
		}
		hForceTVClient = null;

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

	}

	@Override
	public void seekTo(int aMsec) {
		// TODO Auto-generated method stub

		try {
			if (mMediaPlayer != null && isMediaPlayerPrepared()) {
				int duration = mMediaPlayer.getDuration();
				if (duration < aMsec || duration == aMsec) {
					onCompletion(mMediaPlayer);
				} else {
					Utils.printLog(TAG, "will seek to aMsec=" + aMsec);
					if (callback != null) {
						callback.onVideoSeekStart(aMsec);
					}
					mMediaPlayer.seekTo(aMsec);
					if(thisContext!=null){
						VideoPlayerActivity videoPlayerActivity = (VideoPlayerActivity) thisContext;
//						videoPlayerActivity.sendPlayEventBroadCast(duration, "SEEK");
					}
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
			if(mp != null && isMediaPlayerPrepared()){
				int position = getCurrentPosition();			
		    	callback.onVideoPlaySeekComplete(position);
			}
		}
	}

	@Override
	public boolean onInfo(MediaPlayer mp, int what, int extra) {
		Utils.printLog(TAG, "==========================================onInfo: what=" + what + ", extra=" + extra);
		if (1005 == what) {// TMediaPlayer.MEDIA_INFO_VIDEO_DISPLAY_BY_HARDWARE
			Utils.printLog(TAG, "bVideoDisplayByHardware = true");
			bVideoDisplayByHardware = true;
		} else {
			Utils.printLog(TAG, "bVideoDisplayByHardware = false");
			bVideoDisplayByHardware = false;
		}
		if(1013 == what){   //add here for more chapters20161126
			Utils.printLog(TAG, "this file is more than one chapter");
			if(extra > 0){
				mChapters = extra;
			}
			playMediaBean.mChapters = mChapters;
		}
		int infotype = -1;
		if (what == ErrorConsts.media_player_buffering) {//701
			infotype = CommonConst.media_player_buffering;
		} else if (what == ErrorConsts.media_player_buffered) {//702
			infotype = CommonConst.media_player_buffered;
		}
//		else if(what == ErrorConsts.media_player_video_notsupport_mtk){
//			infotype = CommonConst.unknown_video_format;
//		}
//		else if (what == TMediaPlayer.TMEDIA_ERROR_CODEC_NOT_SUPPORT) {
//		      if(extra == TMediaPlayer.TAUDIO_NOT_SUPPORT){
//		         infotype = CommonConst.unknown_audio_format;
//		      }else if(extra == TMediaPlayer.TVIDEO_NOT_SUPPORT){
//		         infotype = CommonConst.unknown_video_format;
//		      }else if(extra == TMediaPlayer.TAUDIO_VIDEO_NOT_SUPPORT){
//		         infotype = CommonConst.unknown_media_format;
//		      }
//		 } else if (what == TMediaPlayer.TMEDIA_ERROR_NO_AUDIO) {
//		         infotype = CommonConst.media_player_no_audio;
//		 }
		else if (what == 1002 || what == ErrorConsts.unknown_audio_format || what == ErrorConsts.mtk_video_only_format) {
			infotype = CommonConst.unknown_audio_format;
		} else if (what == 1003 || what == ErrorConsts.media_player_video_notsupport_mtk) {
			infotype = CommonConst.unknown_video_format;
			
			if (callback != null) {
				callback.onVideoPlayInfoNotify(infotype);
			}

			if(mIsVideoReleasing){
				releaseMediaPlayer();    //add here 20160926wj
			}
			videoBackH.removeMessages(NEXTMSG);
			videoBackH.sendEmptyMessageDelayed(NEXTMSG, 1000);
			Log.d(TAG, "Info = 1003  run or video unknow mtk ");
			return true;
		} else if (what == 1000 && extra == 1) {
			infotype = CommonConst.media_player_subtitle_update;
		} else if (what == 1000 && extra == 0) {
			infotype = CommonConst.media_player_subtitle_null;
		} else if (what == ErrorConsts.media_player_not_seekable) {
			infotype = CommonConst.media_player_not_seekable;
		} else if (what == MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START) {
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
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
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
		if (mMediaPlayer != null) {
			Utils.printLog(TAG, "surfaceDestroyed");
			callback.onSurfaceDertory();
		} else {
			Utils.printLog(TAG, "mMediaPlayer is already null");
		}

		// releaseMediaPlayer();
		if(mIsVideoReleasing){
		   releaseMediaPlayer();
		}
		// 为了解决MS828 C2的bug，提前释放资源，可能会有异常 --同步修改
		// (播放视频的电视，收到推送的音乐文件，电视打开了音乐播放器界面，播放了几秒后，视频声音才消失)
	}

	public void playChapter(int index){
		Log.d(TAG,"playing chapter======index is "+index);
		
		
			try {
				Method mth = mMediaPlayer.getClass().getMethod("setTS", int.class);		
				mth.invoke(mMediaPlayer, index);
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch(Exception e){
				e.printStackTrace();
			}

	}
	
	@Override
	public int getAudioTrackNms() {
		// TODO Auto-generated method stub

		int nms = 0;
		if (mMediaPlayer != null && isMediaPlayerPrepared() && isAudioUsable) {

			AudioTrackInfo audioTrackNum = mMediaPlayer.getAudioTrackInfo();
			if (audioTrackNum != null) {
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
			SubtitleTrackInfo subtitleTrackNum = mMediaPlayer.getAllSubtitleTrackInfo();
			if (subtitleTrackNum != null) {
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
				mMediaPlayer.setAudioTrack(nums + 1);
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

		// if (mMediaPlayer != null && isMediaPlayerPrepared() && isAudioUsable)
		// {
		//
		// try {
		// Metadata data = mMediaPlayer.getMetadata(true, true);
		// if (data != null && data.has(Metadata.CURRENT_AUDIO_TRACK_ID)) {
		// int nms = data.getInt(Metadata.CURRENT_AUDIO_TRACK_ID);
		// if (nms >= 1) {
		// mCurrentAudioTrack = nms;
		// }
		// Utils.printLog(TAG,
		// "data.getInt(Metadata.CURRENT_AUDIO_TRACK_ID) = "
		// + nms);
		// }
		// } catch (Exception e) {
		// e.printStackTrace();
		// // TODO: handle exception
		// }
		//
		// }
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

	/**
	 * 看世涛的注释，这个方法是用来更新字幕的，可能mstar平台原有的字幕更新是有放在应用上来做， 这个返回的infocode也只有mstar平台有用，
	 * 目前我还没有见到这个方法在使用，所以，我建议如果amlogic平台没有这个的话，你直接屏蔽这个方法 mtk平台的代码，把这部分屏蔽了 T968
	 * 出现编译错误--- 后期，MS828出现视频无字幕的问题，所以为了通用，防止编译错误，张工在amlogic也加了相应的实现 --
	 * 2016/1/27
	 */
	@Override
	public String getCurrentSubtitleText() {
		// TODO Auto-generated method stub
		if (mMediaPlayer != null && isMediaPlayerPrepared()) {
			return mMediaPlayer.getSubtitleData(); // --------
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
				mMediaPlayer.setSubtitleDisplay(holder); // ----------
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}

		}
	}

	@Override
	public boolean isFinishInit() {
		// TODO Auto-generated method stub
		Utils.printLog(TAG, "isFinishInit = " + isFinishInit);
		return isFinishInit;
	}

	@Override
	public void setFinishInit(boolean isfinish) {
		// TODO Auto-generated method stub
		Utils.printLog(TAG, "setFinishInit = " + isfinish);
		isFinishInit = isfinish;
	}

	@Override
	public int isDolby(int audioTrack) {
		// TODO Auto-generated method stub
		int dobby = 0;
		if (mMediaPlayer != null && isMediaPlayerPrepared()) {
			dobby = mMediaPlayer.checkCurrentStreamIsDolby();
		}
		return dobby;
	}

	public int isDolbyVision(TvManager tvManager) {

        int dobbymode = 0;
        dobbymode = tvManager.getPQ().checkHdrMode();
        Log.i(TAG, "dobbymode:"+ dobbymode);
       
        return dobbymode;
   }

	@Override
	public int isDTS(int audioTrack) {

		int dtsmode = 0;
		 if (mMediaPlayer != null && isMediaPlayerPrepared()) {
			 dtsmode = mMediaPlayer.checkCurrentStreamIsDolby();
    		 }
		
		return dtsmode;
	}

	public int isDTS(){
		int dtsmode = 0;

		 if (mMediaPlayer != null && isMediaPlayerPrepared()) {
			 dtsmode = mMediaPlayer.checkCurrentStreamIsDolby();
   		 }
		 Log.i(TAG, "dtsmode:"+ dtsmode);
		return dtsmode;
		
	}

	@Override
	public int[] getMediaInfo() {
		// TODO Auto-generated method stub
		int[] mMediaInfo = new int[9];
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
	private MediaBean playMediaBean;
	public void setPlayMediaBean(MediaBean mediaBean){
		this.playMediaBean = mediaBean;
	}
	public MediaBean getPlayMediaBean(){
		return  playMediaBean;
	}
	
	
	private Handler videoBackH = new Handler(){
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case NEXTMSG:
				
				new Thread(new Runnable() {

					@Override
					public void run() {

						if(mIsActionPlayNext){
						   playNextVideoAfterError();
						}

					}
				}).start();
				break;

			default:
				break;
			}
			
		};
	};
}
