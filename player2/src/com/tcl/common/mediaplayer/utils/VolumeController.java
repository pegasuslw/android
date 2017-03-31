package com.tcl.common.mediaplayer.utils;

import android.content.Context;
import android.media.AudioManager;
import android.media.AudioManager.OnAudioFocusChangeListener;
import android.util.Log;

public class VolumeController {

	private Context context;
	private AudioManager mAudioManager;
	private String TAG = "VolumeController";

	public VolumeController(Context ct) {
		context = ct;
		
		mAudioManager = (AudioManager) context
				.getSystemService(Context.AUDIO_SERVICE);
//		Utils.printLog(TAG, "isMusicActive ="+mAudioManager.isMusicActive() +"/n isSpeakerphoneOn="+mAudioManager.isSpeakerphoneOn());
//		mAudioManager.requestAudioFocus(new OnAudioFocusChangeListener() {
//			
//			@Override
//			public void onAudioFocusChange(int focusChange) {
//				// TODO Auto-generated method stub
//				Utils.printLog(TAG, "focusChange ="+focusChange);
//			}
//		}, mAudioManager.STREAM_MUSIC, mAudioManager.AUDIOFOCUS_GAIN);//abandonAudioFocus(null);
	}

	
	public void requestAudioFocus(OnAudioFocusChangeListener lister){
		mAudioManager.requestAudioFocus(lister, mAudioManager.STREAM_MUSIC, mAudioManager.AUDIOFOCUS_GAIN);//abandonAudioFocus(null);
	}
	public int volumDown() {
		// mediaPlay.
		// AudioManager audioMan = (AudioManager)
		// this.getSystemService(Context.AUDIO_SERVICE);
		if (mAudioManager == null) {
			return 5;
		}
		
		int maxVolum = mAudioManager
				.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		int curVolum = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
		Log.i(TAG, "musicVolumDown maxVolum is " + String.valueOf(maxVolum)
				+ " current volum is " + String.valueOf(curVolum));
		int afterSet = curVolum;
		if (curVolum > 0) {
			afterSet = curVolum - 1;
			mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, afterSet,
					0);
		}
		return afterSet;
	}

	public void setVolum(int volumTo) {
		if (mAudioManager == null) {
			return;
		}
		//Utils.printLog(TAG, "isMusicActive ="+mAudioManager.isMusicActive() +"/n isSpeakerphoneOn="+mAudioManager.isSpeakerphoneOn());
		//requestAudioFocus(null, mAudioManager.STREAM_MUSIC, mAudioManager.AUDIOFOCUS_GAIN);
//		int mode =mAudioManager.getRingerMode();
//		Utils.printLog(TAG, "volume mode ="+mode);
		int maxVolum = mAudioManager
				.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		Log.i(TAG, "musicVolumDown maxVolum is " + String.valueOf(maxVolum));
		Utils.printLog(TAG, "SetVolume:"+volumTo);
		if ((volumTo >= 0) && (volumTo <= maxVolum)) {
			mAudioManager
					.setStreamVolume(AudioManager.STREAM_MUSIC, volumTo, 0);
		}
		Utils.printLog(TAG, "GetVolume:"+getCurVolum());
	}

	public int volumUp() {
		if (mAudioManager == null) {
			return 5;
		}
		int maxVolum = mAudioManager
				.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		int curVolum = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
		Utils.printLog(TAG, "volumUp maxVolum is " + String.valueOf(maxVolum)
				+ " current volum is " + String.valueOf(curVolum));
		int afterSet = curVolum;
		if (curVolum < maxVolum) {
			afterSet = curVolum + 1;
			mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, afterSet,
					0);
		}
		return afterSet;
	}

	public int getMaxVolume(){
		if (mAudioManager == null) {
			return 5;
		}
		int maxVolum = mAudioManager
		.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		if(maxVolum>0){
			return maxVolum;
		}else{
			return 5;
		}
	}
	
	public void close_Slience(){
		Utils.printLog(TAG, "close_Slience come in");
		if(mAudioManager!=null){
//			int mode =mAudioManager.getRingerMode();
//			Utils.printLog(TAG, "close_Slience mode ="+mode);
//			if(mode == mAudioManager.RINGER_MODE_SILENT){
//				Utils.printLog(TAG, "close_Slience");
//				mAudioManager.setRingerMode(mAudioManager.RINGER_MODE_NORMAL);
//			}
			
			Utils.printLog(TAG, "isMicrophoneMute ="+mAudioManager.isMicrophoneMute()+"   isSpeakerphoneOn ="+mAudioManager.isSpeakerphoneOn());
			if(!mAudioManager.isSpeakerphoneOn()){
				mAudioManager.setSpeakerphoneOn(true);
			}
		}
	}
	
	public void start_Slience(){
		if(mAudioManager!=null){
			int mode =mAudioManager.getRingerMode();
			if(mode == mAudioManager.RINGER_MODE_NORMAL){
				Utils.printLog(TAG, "start_Slience");
				mAudioManager.setRingerMode(mAudioManager.RINGER_MODE_SILENT);
			}
		}
	}
	public int getCurVolum() {
		int cur = 0;
		if (mAudioManager != null) {
			cur = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
		}
		Log.i(TAG, "getCurVolum " + String.valueOf(cur));
		return cur;
	}
}
