package com.tcl.common.mediaplayer.audio.contrl;

public interface AudioPlayerServiceConst {

	//EPG弹出，关闭音乐播放后台Service。
	public static final String ACTION_AUDIO_CONTROL_EPG = "EPG_SEARCH";
	public static final String HOME_IS_RESUME = "stop.all.apps";
	public static final String ACTION_AUDIO_PALYER = "android.intent.action.VIEW";
	public static final int EXIT_AUDIO_PALY=-1;
	//通知ｗｉｄｇｅ随身听关闭，进行音视频播放的广播；
	public static final String ACTION_STOP_WIDGETMUSIC="MSG_ATV_ACTIVE";
	
	
	public static final int AUDIO_PALY_ERROR_NETWORK_UNUSABLE =2;
	
}
