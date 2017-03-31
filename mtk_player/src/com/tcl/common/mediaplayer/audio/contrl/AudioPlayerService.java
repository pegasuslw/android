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
package com.tcl.common.mediaplayer.audio.contrl;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;

import com.tcl.common.mediaplayer.lyric.ILyricParseListener;
import com.tcl.common.mediaplayer.lyric.Lyric;
import com.tcl.common.mediaplayer.lyric.PlayListItem;
import com.tcl.common.mediaplayer.lyric.Sentence;
import com.tcl.common.mediaplayer.aidl.CommonConst;
import com.tcl.common.mediaplayer.aidl.MediaBean;
import com.tcl.common.mediaplayer.utils.ErrorConsts;
import com.tcl.common.mediaplayer.utils.MyToast;
import com.tcl.common.mediaplayer.utils.Utils;
import com.tcl.common.mediaplayer.utils.VolumeController;
import com.tcl.common.mediaplayer.video.UI.VideoPlayerActivity;
import com.tcl.common.mediaplayer.video.contrl.VideoPlayerContrlConsts;
import com.tcl.common.mediaplayer.R;
import com.tcl.common.mediaplayer.aidl.BackGroundAudioCtrl;
import com.tcl.common.mediaplayer.aidl.BackGroundAudioCtrlCallBack;
import com.tcl.common.mediaplayer.audio.ui.AudioPlayerActivity;
import com.tcl.common.mediaplayer.audio.ui.AudioPlayerConst;
import com.tcl.common.mediaplayer.audioparse.AudioFileExtensionFilter;
import com.tcl.media.TMediaPlayer;

/***
 * 
 * @author bailing
 * 
 */
public class AudioPlayerService extends Service implements
		OnCompletionListener, OnPreparedListener,
		MediaPlayer.OnBufferingUpdateListener, MediaPlayer.OnInfoListener,
		OnErrorListener, MediaPlayer.OnSeekCompleteListener {
	private static final String TAG = "AudioPlayerService";
	private static final int mUpdateDeskTopLylic = 0;
	private static final int mOpenOrCloseDeskTopLylic = 1;
	private static final int mDismissDeskTopLylic = 2;
	private static final int mShowDeskTopLylic = 3;
	private static final int mResetDeskTopLylic = 4;
	private static final int mInitDeskTopLylic = 5;
	private static final int START_PLAY = 10;
	private static final int INITMEDIAPLAYER = 11;
	private TMediaPlayer mMediaPlayer;
	private static List<MediaBean> mList = new ArrayList<MediaBean>();
	private boolean mThreadDisable = true;
	private volatile boolean isMediaPlayerAready = false;
	private int mCurrIndex;
	private int mCurrentPlayerType;
	private DeskTopLycShowDialog lycDialog;
	private List<Sentence> mSentenceList;
	private int mCurrentSentenIndex = -1;// 当前歌词索引
	private boolean isDeskTopLylicOpen = false;
	private VolumeController volumeContrl = null;
	private MyToast mToast;
	private RemoteCallbackList<BackGroundAudioCtrlCallBack> callbacklist;
	private CallBackHandler mCallBackHandler;
	private boolean isRealseFinish = true;

	@Override
	public void onCreate() {
		super.onCreate();
		Utils.printLog(TAG, "onCreate");
		volumeContrl = new VolumeController(AudioPlayerService.this);
		initDeskTopLycDialog();
		// 注册音乐wight事件
		IntentFilter intentFilter = new IntentFilter();
		intentFilter
				.addAction(AudioPlayerServiceConst.ACTION_AUDIO_CONTROL_EPG);
		intentFilter.addAction(AudioPlayerServiceConst.HOME_IS_RESUME);
		intentFilter.addAction(CommonConst.CLOSE_AUDIO_PLAY);
		this.registerReceiver(mGeneralBroadCastReceiver, intentFilter);

		IntentFilter intentFilter2 = new IntentFilter();
		intentFilter2.addAction(Intent.ACTION_MEDIA_UNMOUNTED);
		intentFilter2.addAction(Intent.ACTION_MEDIA_BAD_REMOVAL);
		intentFilter2.addAction(Intent.ACTION_MEDIA_REMOVED);
		intentFilter2.addAction(Intent.ACTION_MEDIA_EJECT);
		intentFilter2.addDataScheme("file");
		this.registerReceiver(mDeviceUnmountBroadcastReceiver, intentFilter2);

		IntentFilter intentFilter1 = new IntentFilter();
		intentFilter1.addAction(Intent.ACTION_SHUTDOWN);
		intentFilter1.addAction(CommonConst.HOME_PRESSED);
		intentFilter1.addAction(CommonConst.TV_PRESSED);
		intentFilter1.addAction(CommonConst.CHANGE_SOURCE_PRESSED);
		intentFilter1.addAction(CommonConst.VOICE_ASSIST_PRESSED); //for 5507 voice control disable 20150311
		registerReceiver(mShutDownBroadCastReceiver, intentFilter1);
		callbacklist = new RemoteCallbackList<BackGroundAudioCtrlCallBack>();
		mCallBackHandler = new CallBackHandler();

		sendStopWidgetMusicBroadcast();

		// 新建一个线程用来专门更新歌词
		new RefreshLycThread().start();
	}

	private BroadcastReceiver mShutDownBroadCastReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			if (Intent.ACTION_SHUTDOWN.equalsIgnoreCase(intent.getAction())) {
				Utils.printLog(TAG, "DEVICE_SHUTDOWN broadCast");
				releaseMediaPlayer();
				mAudioPlayerHander
						.sendEmptyMessage(CommonConst.DEVICE_SHUTDOWN);
				callBack_onAudioPlayError(CommonConst.DEVICE_SHUTDOWN);
				
			}else if(CommonConst.HOME_PRESSED.equalsIgnoreCase(intent.getAction())){
				Utils.printLog(TAG, "HomePRESSED");
				releaseMediaPlayer();
				callBack_onAudioPlayError(CommonConst.HOME_PRESSED_BroadCast);
			}else if(CommonConst.TV_PRESSED.equalsIgnoreCase(intent.getAction())){
				Utils.printLog(TAG, "TV_PRESSED");
				releaseMediaPlayer();
				callBack_onAudioPlayError(CommonConst.HOME_PRESSED_BroadCast);
			}else if(CommonConst.CHANGE_SOURCE_PRESSED.equalsIgnoreCase(intent.getAction())){
				Utils.printLog(TAG, "CHANGE_SOURCE_PRESSED");
				releaseMediaPlayer();
				callBack_onAudioPlayError(CommonConst.HOME_PRESSED_BroadCast);
			}else if(CommonConst.VOICE_ASSIST_PRESSED.equalsIgnoreCase(intent.getAction())){
				callBack_onAudioPlayError(CommonConst.voice_assist_disable);
			}
		}

	};
	

	private void initDeskTopLycDialog() {
		lycDialog = new DeskTopLycShowDialog(this,
				android.R.style.Theme_Light_Panel);

	}

	private class RefreshLycThread extends Thread {
		public void run() {
			while (!mThreadDisable) {
				if (mSentenceList != null && mSentenceList.size() > 0
						&& mMediaPlayer != null && isMediaPlayerAready) {
					int position = 0;
					try {
						position = mMediaPlayer.getCurrentPosition();
					} catch (Exception e) {
						Log.e(TAG, "IllegalStateException");
					}
					mCurrentSentenIndex = Utils.getNowSentenceIndex(
							mSentenceList, position);
					mLylicHandler.sendEmptyMessage(mUpdateDeskTopLylic);
				}
				try {
					Thread.sleep(1000);
				} catch (Exception e) {
				}
			}
		}
	}

	private void sendStopWidgetMusicBroadcast() {

		Intent intent = new Intent();
		intent.setAction(AudioPlayerServiceConst.ACTION_STOP_WIDGETMUSIC);
		sendBroadcast(intent);
		Utils.printLog(TAG, "send Broadcast MSG_ATV_ACTIVE");
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		handleCommand(intent);
		// We want this service to continue running until it is explicitly
		// stopped, so return sticky.
		return START_STICKY;
	}

	public void onDestroy() {
		super.onDestroy();
		Utils.printLog(TAG, "onDestroy");
		mThreadDisable = true;
		releaseMediaPlayer();
		lycDialog.dismiss();
		if(mGeneralBroadCastReceiver!=null){
			unregisterReceiver(mGeneralBroadCastReceiver);
		}
		if(mDeviceUnmountBroadcastReceiver!=null){
			unregisterReceiver(mDeviceUnmountBroadcastReceiver);
		}
		if(mShutDownBroadCastReceiver!=null){
			unregisterReceiver(mShutDownBroadCastReceiver);
		}
		mAudioPlayerHander.removeMessages(START_PLAY);
		// stopForegroundCompat(R.string.music_service_started);
		stopForeground(true);
		callbacklist.kill();

		if (mAudioPlayerHander != null) {
			Utils.removeHandlerMsg(mAudioPlayerHander);
		}
		
//		Utils.killMyProcess(AudioPlayerService.this);
	}

	@Override
	public boolean onUnbind(Intent intent) {
		// TODO Auto-generated method stub
		Utils.printLog(TAG, "onUnbind");
		return super.onUnbind(intent);
	}

	private void releaseMediaPlayer() {
		isMediaPlayerAready = false;
		if (mMediaPlayer != null&&isRealseFinish ==true) {
			isRealseFinish = false;
			try {
				if(mMediaPlayer.isPlaying()){
					mMediaPlayer.stop();
					Utils.printLog(TAG, "mMediaPlayer.stop();");
				}
				mMediaPlayer.release();
				Utils.printLog(TAG, "releaseMediaPlayer");
				mMediaPlayer = null;
				isRealseFinish = true;
			} catch (Exception e) {
				mMediaPlayer = null;
				isRealseFinish = true;
				Log.e(TAG, " releaseMediaPlayer IllegalStateException");
			}
		}

	}

	// handler 更新歌词
	private Handler mLylicHandler = new Handler() {
		public void handleMessage(Message msg) {
			// Utils.printLog(TAG, "mLylicHandler" + msg.what);
			if (msg.what == mUpdateDeskTopLylic && mCurrentSentenIndex >= 0
					&& mSentenceList != null) {
				if (mSentenceList.get(mCurrentSentenIndex).getContent()
						.equalsIgnoreCase("")) {
					lycDialog.refreshLyc("......");
				} else {
					lycDialog.refreshLyc(mSentenceList.get(mCurrentSentenIndex)
							.getContent());
				}

			} else if (msg.what == mOpenOrCloseDeskTopLylic) {
				Utils.printLog(TAG, "mLylicHandler mOpenOrCloseDeskTopLylic");
				if (isDeskTopLylicOpen) {
					lycDialog.dismiss();
					isDeskTopLylicOpen = false;
					callBack_onLycChangeSuccess(false);
				} else {
					lycDialog.show();
					isDeskTopLylicOpen = true;
					callBack_onLycChangeSuccess(true);
				}

			} else if (msg.what == mDismissDeskTopLylic) {
				if (isDeskTopLylicOpen) {
					lycDialog.dismiss();
					isDeskTopLylicOpen = false;

				}
				callBack_onLycChangeSuccess(false);

			} else if (msg.what == mShowDeskTopLylic) {
				if (isDeskTopLylicOpen == false) {
					lycDialog.show();
					isDeskTopLylicOpen = true;

				}
				callBack_onLycChangeSuccess(true);
			} else if (msg.what == mResetDeskTopLylic) {
				if (lycDialog != null) {
					lycDialog
							.refreshLyc(getString(R.string.audio_info_no_lylic));
				}
			} else if (msg.what == mInitDeskTopLylic) {
				if (lycDialog != null) {
					lycDialog.refreshLyc("");
				}
			}
		}
	};

	private void setLylicInfo_self(List<Sentence> sentenceList) {
		mSentenceList = sentenceList;
		if (sentenceList == null) {
			mLylicHandler.sendEmptyMessage(mResetDeskTopLylic);

		}
	}

	@Override
	public IBinder onBind(Intent intent) {
		Utils.printLog(TAG, "onBind");
		return binder;
	}

	private BackGroundAudioCtrl.Stub binder = new BackGroundAudioCtrl.Stub() {

		@Override
		public void unregisterCallback(BackGroundAudioCtrlCallBack callback) throws RemoteException {
			callbacklist.unregister(callback);
			stop();
		}

		@Override
		public void stop() throws RemoteException {

			if (mMediaPlayer != null) {
				try {
					mMediaPlayer.stop();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		}

		@Override
		public void start() throws RemoteException {
			if (mMediaPlayer != null && isMediaPlayerAready) {
				try {
					mMediaPlayer.start();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		}

		@Override
		public void setPlayType(int type) throws RemoteException {
			// TODO Auto-generated method stub
			Utils.printLog(TAG, "set Play type =" + type);
			mCurrentPlayerType = type;
		}

		@Override
		public void seekTo(int msec) throws RemoteException {
			Utils.printLog(TAG, "seekTo = " + msec);
			if (mMediaPlayer != null && isMediaPlayerAready) {
				try {
					int duration = mMediaPlayer.getDuration();
			        if(duration < msec || duration== msec){
			        	onCompletion(mMediaPlayer);
			        }else{
			        	mMediaPlayer.seekTo(msec);
			        }
				
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		}

		@Override
		public void reset() throws RemoteException {

			if (mMediaPlayer != null) {
				try {
					mMediaPlayer.reset();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		}

		@Override
		public void registerCallback(BackGroundAudioCtrlCallBack callback)
				throws RemoteException {
			// TODO Auto-generated method stub

			callbacklist.register(callback);
		}

		@Override
		public boolean playPrevious() throws RemoteException {

			if (!isMediaPlayerAready) {
				Utils.printLog(TAG, "  play next no use");
				return false;
			}

			Utils.printLog(TAG, "playNext");
			// setIsNextPreOk(false);
			if (mCurrentPlayerType == CommonConst.AUDIO_PALY_RANDOM) {
                if(mList.size()==1){
                	return false;
                }
				mCurrIndex = Utils.getRandomFromSize(mList.size(), mCurrIndex);
				releaseMediaPlayer();
				mAudioPlayerHander.sendEmptyMessage(START_PLAY);
				// setIsNextPreOk(true);
				return true;
			} else if (mCurrentPlayerType == CommonConst.AUDIO_PALY_RECYCLE) {
				mCurrIndex = --mCurrIndex % mList.size();
				releaseMediaPlayer();
				if (mCurrIndex < 0) {
					mCurrIndex = mCurrIndex + mList.size();
				}
				mAudioPlayerHander.sendEmptyMessage(START_PLAY);
				// setIsNextPreOk(true);
				return true;
			} else {
				if (--mCurrIndex >= 0) {
					releaseMediaPlayer();
					mAudioPlayerHander.sendEmptyMessage(START_PLAY);
					// setIsNextPreOk(true);
					return true;
				} else {
					mCurrIndex++;
					// setIsNextPreOk(true);
					callBack_onAudioPlayInfoNotify(CommonConst.media_player_already_firstfiles);
					return false;
				}
			}

		}

		@Override
		public boolean playNext() throws RemoteException {

			if (!isMediaPlayerAready) {
				Utils.printLog(TAG, "  play next no use");
				return false;
			}

			Utils.printLog(TAG, "playNext");
			// setIsNextPreOk(false);
			if (mCurrentPlayerType == CommonConst.AUDIO_PALY_RANDOM) {
			    if(mList.size()==1){
	                return false;
	            }
				mCurrIndex = Utils.getRandomFromSize(mList.size(), mCurrIndex);
				Utils.printLog(TAG, "random index =" + mCurrIndex);
				releaseMediaPlayer();
				mAudioPlayerHander.sendEmptyMessage(START_PLAY);
				// setIsNextPreOk(true);

				return true;
			} else if (mCurrentPlayerType == CommonConst.AUDIO_PALY_RECYCLE) {
				mCurrIndex = ++mCurrIndex % mList.size();
				releaseMediaPlayer();
				mAudioPlayerHander.sendEmptyMessage(START_PLAY);
				// setIsNextPreOk(true);
				return true;
			} else {
				if (++mCurrIndex < mList.size()) {
					releaseMediaPlayer();
					mAudioPlayerHander.sendEmptyMessage(START_PLAY);
					// setIsNextPreOk(true);
					return true;
				} else {
					--mCurrIndex;
					// setIsNextPreOk(true);
					callBack_onAudioPlayInfoNotify(CommonConst.media_player_already_lastfiles);
					return false;
				}
			}

		}

		@Override
		public void play(List<MediaBean> list, int index)
				throws RemoteException {

			isMediaPlayerAready = false;
			// 是否更新播放器播放列表。null -> false;
			if (mList.equals(list)) {
				Utils.printLog(TAG, "Mlist equal List!");
			} else {
				mList.clear();
				if (list != null) {
					mList.addAll(list);
				}
			}

			Utils.printLog(TAG, "play(List<MediaBean> list, int index) index ="
					+ index);

			if (mList != null && mList.size() > 0) {
				if (index >= mList.size() || index < 0) {
					mCurrIndex = 0;
					Utils.printLog(TAG, "set mCurentIndex = 0");
				} else {
					mCurrIndex = index;
					Utils.printLog(TAG, "set mCurentIndex = index = " + index);
				}

				for (int i = 0; i < list.size(); i++) {
					Utils.printLog(TAG, "mmm path = " + list.get(i).mPath);
				}
				// playAudio();
				mAudioPlayerHander.sendEmptyMessage(START_PLAY);
			} else {
				// dismissToast();
				// mToast = new MyToast(AudioPlayerService.this, getResources()
				// .getString(R.string.media_player_listNull));
				// mToast.show();
				mAudioPlayerHander.sendEmptyMessage(CommonConst.PLAY_LIST_NULL);
				Utils.printLog(TAG, "List is NUll Toast show!");
				mCurrIndex = AudioPlayerServiceConst.EXIT_AUDIO_PALY;
				// 回掉音乐播放界面，更新UI.-1为异常退出播放界面。
				callBack_onAudioPlayChanged(AudioPlayerServiceConst.EXIT_AUDIO_PALY);
			}

		}

		@Override
		public void pause() throws RemoteException {

			if (mMediaPlayer != null && isMediaPlayerAready) {
				try {
					mMediaPlayer.pause();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		}

		@Override
		public void openOrCloseDeskTopLylic() throws RemoteException {
			// TODO Auto-generated method stub

			mLylicHandler.sendEmptyMessage(mOpenOrCloseDeskTopLylic);

		}

		@Override
		public boolean isPlaying() throws RemoteException {

			if (mMediaPlayer == null) {
				return false;
			}
			boolean isPlay = false;
			try {
				isPlay = mMediaPlayer.isPlaying();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return isPlay;

		}

		@Override
		public boolean isMediaPlayerAready() throws RemoteException {

			// TODO Auto-generated method stub
			return isMediaPlayerAready;

		}

		@Override
		public int getPlayingAudioIndex() throws RemoteException {

			// TODO Auto-generated method stub
			return mCurrIndex;

		}

		@Override
		public List<MediaBean> getPlayList() throws RemoteException {

			// TODO Auto-generated method stub
			Utils.printLog(TAG, "getPlayList mList"+mList.size());
			return mList;

		}

		@Override
		public boolean getLylicStatu() throws RemoteException {
			Utils.printLog(TAG, "getLylicStatu =" + isDeskTopLylicOpen);
			if (isDeskTopLylicOpen) {
				return true;
			}
			return false;

		}

		@Override
		public int getDuration() throws RemoteException {

			int duration = 0;
			if (mMediaPlayer != null && isMediaPlayerAready) {
				try {
					duration = mMediaPlayer.getDuration();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return duration;

		}

		@Override
		public int getCurrentPosition() throws RemoteException {

			int position = 0;
			if (mMediaPlayer != null && isMediaPlayerAready) {
				try {
					position = mMediaPlayer.getCurrentPosition();
					// Utils.printLog(TAG, "getCurrentPosition =" + position);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return position;

		}

		@Override
		public void doLylicAction(boolean open) throws RemoteException {
			// TODO Auto-generated method stub

			if (open) {
				mLylicHandler.sendEmptyMessage(mShowDeskTopLylic);
			} else {
				mLylicHandler.sendEmptyMessage(mDismissDeskTopLylic);
			}

		}

		@Override
		public List<MediaBean> getPlayListFromPath(String path)
				throws RemoteException {
			// TODO Auto-generated method stub
			Utils.printLog(TAG, "getPlayListFromPath =" + path);
			File dir = new File(path);

			if (dir.exists() && dir.isDirectory()) {
				Utils.printLog(TAG, "getPlayListFromPath  is dir");
				File[] audiofiles = dir
						.listFiles(new AudioFileExtensionFilter());
				if (audiofiles != null && audiofiles.length > 0) {
					Utils.printLog(TAG,"getPlayListFromPath audiofiles.length > 0");
					ArrayList<MediaBean> playlist = new ArrayList<MediaBean>();
					for (int i = 0; i < audiofiles.length; i++) {

						String audioPath = audiofiles[i].getAbsolutePath();
						Utils.printLog(TAG, "get audio file =" + audioPath);
						MediaBean bean = new MediaBean();
						bean.mPath = audioPath;
						bean.mName = audiofiles[i].getName();
						playlist.add(bean);

					}
					return playlist;

				} else {
					Utils.printLog(TAG, "getPlayListFromPath  return null");
					return null;
				}

			} else {
				Utils.printLog(TAG, "getPlayListFromPath  return null");
				return null;
			}

		}

		@Override
		public int getVolume() throws RemoteException {
			// TODO Auto-generated method stub
			if (volumeContrl != null) {
				int curVolum = 0;
				int vol = volumeContrl.getCurVolum();
				int max = volumeContrl.getMaxVolume();
				curVolum = (vol * 100 / max);
				return curVolum;
			} else
				return 0;

		}

		@Override
		public void setVolume(int vol) throws RemoteException {
			// TODO Auto-generated method stub
			if (volumeContrl != null) {
				int max = volumeContrl.getMaxVolume();
				volumeContrl.setVolum((vol * max) / 100);
			}

		}

		@Override
		public void release() throws RemoteException {
			// TODO Auto-generated method stub
			if(mMediaPlayer != null){
			    releaseMediaPlayer();
			}
		}

		@Override
		public int isDobby(int audioTrack) throws RemoteException {
			// TODO Auto-generated method stub
			int dobby = 0;
			if (mMediaPlayer != null && isMediaPlayerAready) {
				dobby = mMediaPlayer.checkCurrentStreamIsDolby();
			}
			return dobby;
		}

		@Override
		public int isDTS(int audioTrack) throws RemoteException {
			// TODO Auto-generated method stub
			int dobby = 0;
//			if (mMediaPlayer != null && isMediaPlayerAready) {
//				dobby = mMediaPlayer.checkCurrentStreamIsDTS();
//			}
			return dobby;
		}

	};

	private void searchLyc() {
		Utils.printLog(TAG, " searchLyc");
		MediaBean bean = mList.get(mCurrIndex);
		final String lycpath = bean.mLycPath;
		if (lycpath == null) {// 未给歌词地址，判断如果为本地音乐，则在当前目录下搜索；
			final String lylicPath = bean.mPath.substring(0,
					bean.mPath.lastIndexOf("."))
					+ ".lrc";
			localLycSearch(lylicPath);

		} else {// 已经给出歌词地址，则判断地址是本地还是网络，再进行处理；

			if (lycpath.startsWith("http")) {
				Utils.printLog(TAG, "Start Seart http LYC");
				new Thread(new Runnable() {
					public void run() {
						PlayListItem pli = new PlayListItem("", "", 1000L, true);
						new Lyric(lycpath, pli, mLyricParseListener);
					}
				}).start();
			} else {
				localLycSearch(lycpath);
			}
		}

	}

	private void localLycSearch(String lylicPath) {
		final File file = new File(lylicPath);
		boolean mIsLyicFound;
		mIsLyicFound = file.exists();
		if (mIsLyicFound) {
			Utils.printLog(TAG, "mIsLyicFound");
			new Thread(new Runnable() {
				public void run() {
					PlayListItem pli = new PlayListItem("", "", 1000L, true);
					new Lyric(file, pli, mLyricParseListener);
				}
			}).start();
		} else {
			this.setLylicInfo_self(null);
		}
	}

	private ILyricParseListener mLyricParseListener = new ILyricParseListener() {
		public void onParseFinished(List<Sentence> sentenceList) {
			AudioPlayerService.this.setLylicInfo_self(sentenceList);
		}

	};

	public void playAudio() {
		Utils.printLog(TAG, "playMusic");
		releaseMediaPlayer();
		if (mList == null || mList.size() <= 0 || mCurrIndex < 0
				|| mCurrIndex >= mList.size()) {
			Log.e(TAG, "error index or mList.size   mList =" + mList.toString()
					+ "   and mCurr =" + mCurrIndex);
			/************************************ bailing modified ********************************/

			// dismissToast();
			// mToast = new MyToast(AudioPlayerService.this, getResources()
			// .getString(R.string.media_player_listNull));
			// mToast.show();
			mAudioPlayerHander.sendEmptyMessage(CommonConst.PLAY_LIST_NULL);
			Utils.printLog(TAG, "List is NUll Toast show");
			mCurrIndex = AudioPlayerServiceConst.EXIT_AUDIO_PALY;
			callBack_onAudioPlayChanged(AudioPlayerServiceConst.EXIT_AUDIO_PALY);
			return;
		}
		Utils.printLog(TAG, " List.new size =" + mList.size());

		MediaBean mediaBean = mList.get(mCurrIndex);
		String path = mediaBean.mPath;
		if (path != null) {
			callBack_onAudioPlayChanged(mCurrIndex);

			if ((path.startsWith("http")) || new File(path).exists()) {
				
				Message msg = mAudioPlayerHander.obtainMessage();
				msg.what = INITMEDIAPLAYER;
				mAudioPlayerHander.sendMessageDelayed(msg, 10);
				
//				searchLyc();
			} else {
				Utils.printLog(TAG, "文件不存在 with path =" + path);
				// new MyToast(AudioPlayerService.this,
				// AudioPlayerService.this.getResources().getString(
				// R.string.media_source_not_found)).show();
				mAudioPlayerHander
						.sendEmptyMessage(CommonConst.media_source_not_found);
				callBack_onAudioPlayError(CommonConst.media_source_not_found);
				playNextAudioAfterError();
				return;

			}
		} else {
			Utils.printLog(TAG, "文件地址不正确 with path =" + path);
			mAudioPlayerHander
					.sendEmptyMessage(CommonConst.media_source_not_found);
			playNextAudioAfterError();
			callBack_onAudioPlayError(CommonConst.media_source_not_found);
		}

	}

	private void initMediaPlayer(String path) {
		Utils.printLog(TAG, " initMediaPlayer with path =" + path);
		try {
//			if(mMediaPlayer == null){
				mMediaPlayer = new TMediaPlayer();
//			}		
//			mMediaPlayer.reset();
			//	Map<String, String> headers = new HashMap<String, String>();
			//	headers.put("FLOWTYPE", "PLAYBACK_TYPE_AUDIO_ONLY");
			//	mMediaPlayer.setDataSource(AudioPlayerService.this, Uri.parse(path), headers);
			
			mMediaPlayer.setDataSource(path);
			mMediaPlayer.prepareAsync();
			mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			mMediaPlayer.setOnErrorListener(this);
			mMediaPlayer.setOnPreparedListener(this);
			mMediaPlayer.setOnCompletionListener(this);
			mMediaPlayer.setOnSeekCompleteListener(this);
			mMediaPlayer.setOnBufferingUpdateListener(this);
			mMediaPlayer.setOnInfoListener(this);
		} catch (Exception e) {
			e.printStackTrace();
			isMediaPlayerAready = false;
			releaseMediaPlayer();
			if(e instanceof IOException){
				Utils.printLog(TAG, "e instanceof IOException");
				onError(mMediaPlayer,MediaPlayer.MEDIA_ERROR_UNKNOWN, ErrorConsts.ERROR_IO);
//				mAudioPlayerHander
//						.sendEmptyMessage(CommonConst.media_interrupt);
//				callBack_onAudioPlayError(CommonConst.media_interrupt);
//				playNextAudioAfterError();
			}else{
				Utils.printLog(TAG, "e not instanceof IOException");
				mAudioPlayerHander
						.sendEmptyMessage(CommonConst.media_player_init_error);
				callBack_onAudioPlayError(CommonConst.media_player_init_error);
			}


		}
	}

	private void callBack_onAudioPlayChanged(int index) {
		Message msg = mCallBackHandler.obtainMessage(MSG_AUDIO_PLAY_CHANGED, index, 0);
		mCallBackHandler.sendMessage(msg);
		/*final int N = callbacklist.beginBroadcast();
		for (int i = 0; i < N; i++) {
			try {
				callbacklist.getBroadcastItem(i).onAudioPlayChanged(index);

			} catch (RemoteException e) {
				// The RemoteCallbackList will take care of removing
				// the dead object for us.
			}
		}
		callbacklist.finishBroadcast();*/

	}

	private void callBack_onAudioPlayError(int errCode) {
		Utils.printLog(TAG, "callBack_onAudioPlayError errorcode ="+errCode);
		Message msg = mCallBackHandler.obtainMessage(MSG_AUDIO_PLAY_ERROR, errCode, 0);
		mCallBackHandler.sendMessage(msg);
		/*final int N = callbacklist.beginBroadcast();
		for (int i = 0; i < N; i++) {
			try {
				callbacklist.getBroadcastItem(i).onAudioPlayError(errCode);

			} catch (RemoteException e) {
				// The RemoteCallbackList will take care of removing
				// the dead object for us.
			}
		}
		callbacklist.finishBroadcast();*/

	}

	private void callBack_onAudioPlayPrepared() {
		Message msg = mCallBackHandler.obtainMessage(MSG_AUDIO_PLAY_PREPARED);
		mCallBackHandler.sendMessage(msg);
		/*final int N = callbacklist.beginBroadcast();
		for (int i = 0; i < N; i++) {
			try {
				callbacklist.getBroadcastItem(i).onAudioPlayPrepared();

			} catch (RemoteException e) {
				// The RemoteCallbackList will take care of removing
				// the dead object for us.
			}
		}
		callbacklist.finishBroadcast();*/

	}

	private void callBack_onRemoveIndex(int index) {
		Message msg = mCallBackHandler.obtainMessage(MSG_AUDIO_REMOVEINDEX, index, 0);
		mCallBackHandler.sendMessage(msg);
		/*final int N = callbacklist.beginBroadcast();
		for (int i = 0; i < N; i++) {
			try {
				callbacklist.getBroadcastItem(i).onRemoveIndex(index);

			} catch (RemoteException e) {
				// The RemoteCallbackList will take care of removing
				// the dead object for us.
			}
		}
		callbacklist.finishBroadcast();*/
	}

	private void callBack_onAudioPlaySeekComplete(int position) {
		Message msg = mCallBackHandler.obtainMessage(MSG_AUDIO_PLAY_SEEK_COMPLETE, position, 0);
		mCallBackHandler.sendMessage(msg);
		/*final int N = callbacklist.beginBroadcast();
		for (int i = 0; i < N; i++) {
			try {
				callbacklist.getBroadcastItem(i).onAudioPlaySeekComplete(
						position);

			} catch (RemoteException e) {
				// The RemoteCallbackList will take care of removing
				// the dead object for us.
			}
		}
		callbacklist.finishBroadcast();*/
	}

	private void callBack_onAudioPlayBufferingUpdate(int percent) {
		Message msg = mCallBackHandler.obtainMessage(MSG_AUDIO_PLAYBUFFER_UPDATE, percent, 0);
		mCallBackHandler.sendMessage(msg);
		/*final int N = callbacklist.beginBroadcast();
		for (int i = 0; i < N; i++) {
			try {
				callbacklist.getBroadcastItem(i).onAudioPlayBufferingUpdate(
						percent);

			} catch (RemoteException e) {
				// The RemoteCallbackList will take care of removing
				// the dead object for us.
			}
		}
		callbacklist.finishBroadcast();*/
	}

	private void callBack_onAudioPlayInfoNotify(int errCode) {
		Message msg = mCallBackHandler.obtainMessage(MSG_AUDIO_PLAY_INFO_NOTIFY, errCode, 0);
		mCallBackHandler.sendMessage(msg);
		/*final int N = callbacklist.beginBroadcast();
		for (int i = 0; i < N; i++) {
			try {
				callbacklist.getBroadcastItem(i).onAudioPlayInfoNotify(errCode);

			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
		callbacklist.finishBroadcast();*/
	}

	private void callBack_onLycChangeSuccess(boolean res) {
		int arg = res ? 1 : 0;
		Message msg = mCallBackHandler.obtainMessage(MSG_AUDIO_LYC_CHANGE_SUCCESS, arg, 0);
		mCallBackHandler.sendMessage(msg);
		/*final int N = callbacklist.beginBroadcast();
		for (int i = 0; i < N; i++) {
			try {
				callbacklist.getBroadcastItem(i).onLycChangeSuccess(res);

			} catch (RemoteException e) {
				// The RemoteCallbackList will take care of removing
				// the dead object for us.
			}
		}
		callbacklist.finishBroadcast();*/

	}
	
	private void callBack_onAudioComplete() {
		Message msg = mCallBackHandler.obtainMessage(MSG_AUDIO_COMPLETE);
		mCallBackHandler.sendMessage(msg);
		/*final int N = callbacklist.beginBroadcast();
		for (int i = 0; i < N; i++) {
			try {
				callbacklist.getBroadcastItem(i).onAudioPlayComplete();

			} catch (RemoteException e) {
				// The RemoteCallbackList will take care of removing
				// the dead object for us.
			}
		}
		callbacklist.finishBroadcast();*/

	}

	private void doFinishedAction(int delayTime) {
		Utils.printLog(TAG, "doFinishedAction");
		if (mList == null || mList.size() == 0) {
			mCurrIndex = AudioPlayerServiceConst.EXIT_AUDIO_PALY;
			Utils.printLog(TAG, "mList == null || mList.size() == 0");
		} else {
			switch (mCurrentPlayerType) {
			case CommonConst.AUDIO_PALY_SEQUENCE:
				if (++mCurrIndex < mList.size()) {
					mAudioPlayerHander.sendEmptyMessageDelayed(START_PLAY,
							delayTime);
				} else {
					// 播放到最后一集退出
					mCurrIndex = AudioPlayerServiceConst.EXIT_AUDIO_PALY;
				}
				break;
			case CommonConst.AUDIO_PALY_SINGLE:
				mCurrIndex = AudioPlayerServiceConst.EXIT_AUDIO_PALY;
				break;
			case CommonConst.AUDIO_PALY_SINGLE_REPEAT:
				if (mCurrIndex >= 0) {
					mAudioPlayerHander.sendEmptyMessageDelayed(START_PLAY,
							delayTime);
				} else {
					mCurrIndex = AudioPlayerServiceConst.EXIT_AUDIO_PALY;
				}
				break;
			case CommonConst.AUDIO_PALY_RANDOM:
				mCurrIndex = Utils.getRandomFromSize(mList.size(), mCurrIndex);
				mAudioPlayerHander.sendEmptyMessageDelayed(START_PLAY,
						delayTime);
				break;
			case CommonConst.AUDIO_PALY_RECYCLE:
				Utils.printLog(TAG, "CommonConst.AUDIO_PALY_RECYCLE");
				mCurrIndex = ++mCurrIndex % mList.size();
				mAudioPlayerHander.sendEmptyMessageDelayed(START_PLAY,
						delayTime);
				break;
			}
		}
		if (mCurrIndex == AudioPlayerServiceConst.EXIT_AUDIO_PALY) {
			// 回掉音乐播放界面，更新UI.-1为退出播放界面。
			Utils.printLog(TAG, "callBack_onAudioPlayChanged(AudioPlayerServiceConst.EXIT_AUDIO_PALY);");
			callBack_onAudioPlayChanged(AudioPlayerServiceConst.EXIT_AUDIO_PALY);
		}
	}

	private BroadcastReceiver mGeneralBroadCastReceiver = new BroadcastReceiver() {
		public void onReceive(Context context, Intent intent) {
			Log.d(TAG, "onReceive");
			String action = intent.getAction();
			Utils.printLog(TAG, "mGeneralBroadCastReceiver action =" + action);
			if (AudioPlayerServiceConst.ACTION_AUDIO_CONTROL_EPG
					.equalsIgnoreCase(action)) {
				try {
					if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
						mMediaPlayer.pause();

					}
					mLylicHandler.sendEmptyMessage(mDismissDeskTopLylic);
				} catch (IllegalStateException e) {
					Log.e(TAG, "IllegalStateException");
				}
			} else if (CommonConst.CLOSE_AUDIO_PLAY.equalsIgnoreCase(action)) {
				Utils.printLog(TAG, "CLOSE_AUDIO_PLAY");
				releaseMediaPlayer();
				AudioPlayerService.this.stopSelf();
			}

			if (mMediaPlayer == null || mList == null || mList.size() <= 0) {
				return;
			}

		}
	};

	void handleCommand(Intent intent) {

		// In this sample, we'll use the same text for the ticker and the
		// expanded notification
		CharSequence text = getText(R.string.music_service_started);

		// Set the icon, scrolling text and timestamp
		Notification notification = new Notification(R.drawable.stat_sample,
				text, System.currentTimeMillis());

		// The PendingIntent to launch our activity if the user selects this
		// notification
		Intent audioplayintent = new Intent(
				AudioPlayerServiceConst.ACTION_AUDIO_PALYER);
		audioplayintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
				audioplayintent, 0);

		// Set the info for the views that show in the notification panel.
		notification.setLatestEventInfo(this,
				getText(R.string.local_service_label), text, contentIntent);

		startForeground(R.string.music_service_started, notification);

		// startForegroundCompat(R.string.music_service_started, notification);

	}

	private Handler mAudioPlayerHander = new Handler(Looper.getMainLooper()) {
		public void handleMessage(Message msg) {
			if (msg.what == START_PLAY) {
				playAudio();
			} else if (msg.what == CommonConst.PLAY_DEVICE_UNMOUTED) {
				new MyToast(AudioPlayerService.this, AudioPlayerService.this
						.getResources().getString(
								R.string.media_source_not_found)).show();
			} else if (msg.what == CommonConst.media_player_exception) {
				new MyToast(AudioPlayerService.this, AudioPlayerService.this
						.getResources().getString(
								R.string.media_player_exception)).show();
			} else if (msg.what == CommonConst.media_player_unknown_exception) {
				new MyToast(AudioPlayerService.this, AudioPlayerService.this
						.getResources().getString(
								R.string.media_player_unknown_exception))
						.show();
			} else if (msg.what == CommonConst.PLAY_LIST_NULL) {
				// new MyToast(VideoPlayerActivity.this,
				// VideoPlayerActivity.this.getResources().getString(
				// R.string.media_player_listNull)).show();
			} else if (msg.what == CommonConst.media_player_init_error) {
				new MyToast(AudioPlayerService.this.getApplicationContext(),
						AudioPlayerService.this.getApplicationContext()
								.getResources()
								.getString(R.string.media_player_exception))
						.show();
			} else if (msg.what == CommonConst.media_source_not_found) {

				new MyToast(AudioPlayerService.this, AudioPlayerService.this
						.getResources().getString(
								R.string.media_source_not_found)).show();

			} else if (msg.what == CommonConst.unknown_media_format) {
				new MyToast(AudioPlayerService.this, AudioPlayerService.this
						.getResources()
						.getString(R.string.unknown_media_format)).show();
			} else if (msg.what == CommonConst.media_interrupt) {
				new MyToast(AudioPlayerService.this, AudioPlayerService.this
						.getResources().getString(R.string.media_interrupt))
						.show();

			} else if (msg.what == CommonConst.unknown_video_format) {
				new MyToast(AudioPlayerService.this, AudioPlayerService.this
						.getResources()
						.getString(R.string.unknown_video_format)).show();

			} else if (msg.what == CommonConst.unknown_audio_format) {
				new MyToast(AudioPlayerService.this, AudioPlayerService.this
						.getResources()
						.getString(R.string.unknown_audio_format)).show();

			} else if (msg.what == CommonConst.media_player_network_slow) {
				new MyToast(AudioPlayerService.this, AudioPlayerService.this
						.getResources().getString(
								R.string.media_player_network_slow)).show();

			} else if (msg.what == CommonConst.media_player_not_seekable) {
				new MyToast(AudioPlayerService.this, AudioPlayerService.this
						.getResources().getString(
								R.string.media_player_not_seekable)).show();

			}else if(msg.what == INITMEDIAPLAYER){
				MediaBean mediaBean = mList.get(mCurrIndex);
				String path = mediaBean.mPath;
				initMediaPlayer(path);
//				playNextAudioAfterError();
			}
		}
	};

	private BroadcastReceiver mDeviceUnmountBroadcastReceiver = new BroadcastReceiver() {

		public void onReceive(Context context, Intent intent) {
			Log.d("mDeviceUnmountBroadcastReceiver", "onReceive");
			Log.d("DeviceUnmoutedReceiver","Unmouted == " + intent.getDataString());

			if ((intent.getAction().equals(Intent.ACTION_MEDIA_UNMOUNTED)
					|| intent.getAction().equals(
							Intent.ACTION_MEDIA_BAD_REMOVAL) || intent
					.getAction().equals(Intent.ACTION_MEDIA_REMOVED)||intent.getAction().equals(Intent.ACTION_MEDIA_EJECT))
					&& mList != null && mList.size() > mCurrIndex && mCurrIndex>=0 ) {

				if (mList.get(mCurrIndex).mPath
						.startsWith(CommonConst.HTTPFILE)
						|| mList.get(mCurrIndex).mPath
								.startsWith(CommonConst.SMBFILE)) {
					return;
				}

				if (mList.get(mCurrIndex).mPath.contains(intent.getDataString()
						.substring(7))) {
					Utils.printLog(TAG,
							"mDeviceUnmountBroadcastReceiver  Device Unmouted  path ="
									+ intent.getDataString());
					// dismissToast();
					// mToast = new MyToast(AudioPlayerService.this,
					// getResources().getString(
					// R.string.media_source_not_found));
					// mToast.show();
					//mAudioPlayerHander.sendEmptyMessage(CommonConst.PLAY_DEVICE_UNMOUTED);
					callBack_onAudioPlayError(CommonConst.PLAY_DEVICE_UNMOUTED);
					releaseMediaPlayer();
				}
			}
		}
	};

	@Override
	public void onSeekComplete(MediaPlayer mp) {

		Utils.printLog(
				TAG,
				"onSeekComplete   with postion = "
						+ mMediaPlayer.getCurrentPosition());
		int pos = 0;
		try {
			pos = mp.getCurrentPosition();
		} catch (Exception e) {
			e.printStackTrace();
		}

		callBack_onAudioPlaySeekComplete(pos);
	}

	@Override
	public boolean onError(MediaPlayer mp, int what, int extra) {
		Utils.printLog(TAG,
				" $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$   onError: "
						+ what + "," + extra);
		isMediaPlayerAready = false;
		int errorType = -1;
		releaseMediaPlayer();
		if (what == MediaPlayer.MEDIA_ERROR_SERVER_DIED) {
			errorType = CommonConst.media_player_exception;
		} else if (what == MediaPlayer.MEDIA_ERROR_UNKNOWN) {

			if (extra == ErrorConsts.unknown_media_format) {
				errorType = CommonConst.unknown_media_format;
			} else if (extra == ErrorConsts.media_interrupt || extra == ErrorConsts.ERROR_IO) {
				errorType = CommonConst.media_interrupt;
			} else if (extra == ErrorConsts.media_player_network_slow
					|| extra == ErrorConsts.ERROR_NOT_CONNECTED
					|| extra == ErrorConsts.ERROR_UNKNOWN_HOST
					|| extra == ErrorConsts.ERROR_CANNOT_CONNECT
					|| extra == ErrorConsts.ERROR_CONNECTION_LOST) {
				errorType = CommonConst.media_player_network_slow;
			} else if (extra == ErrorConsts.unknown_audio_format) {
				errorType = CommonConst.unknown_audio_format;
			} else {
				errorType = CommonConst.media_player_unknown_exception;
			}
		} else if (what == ErrorConsts.media_player_unknown_exception_38) {
			errorType = CommonConst.media_player_unknown_exception_38;
		}else {
			errorType = CommonConst.media_player_unknown_exception;
		}

		mAudioPlayerHander.sendEmptyMessage(errorType);
		callBack_onAudioPlayError(errorType);
		if (errorType == CommonConst.media_source_not_found
				|| errorType == CommonConst.unknown_media_format
				|| errorType == CommonConst.media_interrupt
				|| errorType == CommonConst.media_player_network_slow
				|| errorType == CommonConst.unknown_audio_format) {

			playNextAudioAfterError();
		}
		return false;

	}

	private void playNextAudioAfterError() {
		Utils.printLog(TAG, "playNextAudioAfterError with index=" + mCurrIndex
				+ " listsize=" + mList.size());
		if (mList != null && mList.size() > 1 && mCurrIndex < mList.size()) {

			mList.remove(mCurrIndex);
			callBack_onRemoveIndex(mCurrIndex);
			if (mCurrIndex >= mList.size()) {// 播放到最后一集，则播放下一集。
				mCurrIndex = mList.size() - 1;
				doFinishedAction(5000);
			} else {

				// callBack_onAudioPlayChanged(mCurrIndex);
				mAudioPlayerHander.sendEmptyMessageDelayed(START_PLAY, 5000);
			}

		} else {
			mAudioPlayerHander.sendEmptyMessage(CommonConst.PLAY_LIST_NULL);
			callBack_onAudioPlayError(CommonConst.PLAY_LIST_NULL);
		}

	}

	@Override
	public void onBufferingUpdate(MediaPlayer mp, int percent) {
		// TODO Auto-generated method stub

		callBack_onAudioPlayBufferingUpdate(percent);
	}

	@Override
	public boolean onInfo(MediaPlayer mp, int what, int extra) {
		// TODO Auto-generated method stub
		Utils.printLog(TAG,
				" %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%5 onInfo: " + what
						+ "," + extra);
		int infotype = -1;
		if (what == ErrorConsts.media_player_buffering) {
			infotype = CommonConst.media_player_buffering;
		} else if (what == ErrorConsts.media_player_buffered) {
			infotype = CommonConst.media_player_buffered;
		} else if (what == ErrorConsts.media_player_not_seekable) {
			infotype = CommonConst.media_player_not_seekable;
		} else if (what == TMediaPlayer.TMEDIA_ERROR_CODEC_NOT_SUPPORT) {
			if(extra ==TMediaPlayer.TAUDIO_NOT_SUPPORT ){
				infotype = CommonConst.unknown_audio_format;
			}
		} else {
			infotype = what;
		}
		if(infotype!=CommonConst.unknown_audio_format){
			mAudioPlayerHander.sendEmptyMessage(infotype);
		}
	

		 if (infotype == CommonConst.unknown_audio_format) {
		      onError(mp, MediaPlayer.MEDIA_ERROR_UNKNOWN, ErrorConsts.unknown_audio_format);
		 }

		callBack_onAudioPlayInfoNotify(infotype);
		return false;
	}

	public void onPrepared(MediaPlayer mp) {
		Utils.printLog(TAG, "onPrepared:");
		isMediaPlayerAready = true;
		if (mMediaPlayer != null) {

			new Thread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					try {
						mMediaPlayer.start();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}).start();
		}
		callBack_onAudioPlayPrepared();

	}

	public void onCompletion(MediaPlayer mp) {
		Utils.printLog(TAG, "onCompletion");
		callBack_onAudioComplete();
		releaseMediaPlayer();
		mLylicHandler.sendEmptyMessage(mInitDeskTopLylic);
		doFinishedAction(500);
	}
	
	
	/****
	 * add for callbacklist synchronization
	 * @author chengchuntao
	 *
	 */
	private static final int MSG_AUDIO_COMPLETE = 101;
	private static final int MSG_AUDIO_PLAYBUFFER_UPDATE = 102;
	private static final int MSG_AUDIO_PLAY_CHANGED = 103;
	private static final int MSG_AUDIO_PLAY_ERROR = 104;
	private static final int MSG_AUDIO_PLAY_INFO_NOTIFY = 105;
	private static final int MSG_AUDIO_PLAY_PREPARED = 106;
	private static final int MSG_AUDIO_PLAY_SEEK_COMPLETE = 107;
	private static final int MSG_AUDIO_LYC_CHANGE_SUCCESS = 108;
	private static final int MSG_AUDIO_REMOVEINDEX = 109;
	private class CallBackHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			final int N = callbacklist.beginBroadcast();
			for (int i = 0; i < N; i++) {
				try {
					switch (msg.what) {
					case MSG_AUDIO_COMPLETE:
						callbacklist.getBroadcastItem(i).onAudioPlayComplete();
						break;
					case MSG_AUDIO_PLAYBUFFER_UPDATE:
						callbacklist.getBroadcastItem(i).onAudioPlayBufferingUpdate(
								msg.arg1);
						break;
					case MSG_AUDIO_PLAY_CHANGED:
						callbacklist.getBroadcastItem(i).onAudioPlayChanged(msg.arg1);
						break;
					case MSG_AUDIO_PLAY_ERROR:
						callbacklist.getBroadcastItem(i).onAudioPlayError(msg.arg1);
						break;
					case MSG_AUDIO_PLAY_INFO_NOTIFY:
						callbacklist.getBroadcastItem(i).onAudioPlayInfoNotify(msg.arg1);
						break;
					case MSG_AUDIO_PLAY_PREPARED:
						callbacklist.getBroadcastItem(i).onAudioPlayPrepared();
						break;
					case MSG_AUDIO_PLAY_SEEK_COMPLETE:
						callbacklist.getBroadcastItem(i).onAudioPlaySeekComplete(
								msg.arg1);
						break;
					case MSG_AUDIO_LYC_CHANGE_SUCCESS:
						boolean res = msg.arg1 == 1 ? true : false;
						callbacklist.getBroadcastItem(i).onLycChangeSuccess(res);
						break;
					case MSG_AUDIO_REMOVEINDEX:
						callbacklist.getBroadcastItem(i).onRemoveIndex(msg.arg1);
						break;
					default:
						break;
					}					

				} catch (RemoteException e) {
					// The RemoteCallbackList will take care of removing
					// the dead object for us.
				}
			}
			callbacklist.finishBroadcast();
			super.handleMessage(msg);

		}
	}

}

