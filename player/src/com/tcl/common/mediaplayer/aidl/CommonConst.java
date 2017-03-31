package com.tcl.common.mediaplayer.aidl;

public interface CommonConst {

	// 播放器的错误类型；

	public static int media_source_not_found = -1;// 无法获取媒体资源文件;

	public static int unknown_media_format =-2;// 不支持该媒体文件格式
	
	public static int media_interrupt = -3;// 媒体文件已经损坏；

	public static int unknown_audio_format = -4;// 不支持媒体文件音频格式

	public static int unknown_video_format = -5;// 不支持媒体文件视频格式

	public static int media_player_unknown_exception = -6;// 播放器播放过程发生未知错误，请重试
	

	public static int media_player_network_slow = -7;// 网络加载失败

	public static int media_player_exception = -9;// 播放过程中出现异常，请检查您的媒体文件后重新尝试
	public static int media_player_init_error = -10;// 播放器播放过程发生未知错误，请重试
	
	public static int PLAY_LIST_NULL = -11;
	public static int PLAY_DEVICE_UNMOUTED = -12;
	
	public static int GET_DATA_TIMEOUT = 2016;
	
	
	public static int media_player_buffering = -13;// 正在缓冲；
	public static int media_player_buffered = -14;// 缓冲结束；
	public static int media_player_subtitle_update = 1000;// 刷新字幕；
	//public static int media_player_subtitle_update = -15;// 刷新字幕；
	public static int media_player_subtitle_null = -16;// 刷新字幕为空；
	
	public static int media_player_not_seekable =-17;// 不能ｓｅｅｋ
	
	public static int HOME_PRESSED_BroadCast =-18;// 不能ｓｅｅｋ
	public static int  media_player_unknown_exception_38 = -19;
	
	public static int  media_player_startplayer_firstframe = -20;
	
	public static int  media_player_no_audio = -21;
	public static int  media_player_no_video = -22;
	public static int  media_player_already_firstfiles = -23;
	public static int  media_player_already_lastfiles = -24;
	public static int  mediafile_notsupport3d  = -25;
	public static int  tv_sourcemanager_change  = -26;
	public static int AUDIO_PALY_SEQUENCE = 0;
	public static int AUDIO_PALY_SINGLE = 1;
	public static int AUDIO_PALY_SINGLE_REPEAT = 2;
	public static int AUDIO_PALY_RANDOM = 3;
	public static int AUDIO_PALY_RECYCLE = 4;

	public static String CLOSE_VIDEO_PLAY = "CLOSE_VIDEO_PLAY";
	public static String CLOSE_AUDIO_PLAY = "CLOSE_AUDIO_PLAY";
	public static String COMMON_ACTION = "android.intent.action.VIEW";
	public static String HOME_PRESSED = "android.intent.action.ENTER_HOME";
	public static String VOICE_CONTROL = "com.tcl.voicemanager.appcmd";
	public static String TV_PRESSED = "com.tcl.action.tv";
	public static String CHANGE_SOURCE_PRESSED = "com.tcl.tv.action.changesource";
	public static String TV_BACKLIGHT_ENABLE = "android.intent.action.BLOffandKeyDown";
	public static String VIDEO_SETTING_AUDIOTRACK = "com.android.videosetting.refreshaudiotrack";
	public static String STR_PORWER_CHANGE = "android.intent.action.POWEROFF_NOTIFY";
	public static String VIDEO_HDR_HLG_BROADCAST = "com.tcl.Hdr";
	public static String EXIT_3D = "android.intent.action.tcl.exit3D";
	public static String SOURCE_CHANGEandVOICEASS = "android.intent.action.tcl.MMtoOtherSrc";
	public static String HISTORYRECORD = "com.android.systemui2.opentv";
	public static String LanuageChange = "android.intent.action.LOCALE_CHANGED";
   public static int DEVICE_SHUTDOWN = -8888;
	// 通知ｗｉｄｇｅ随身听关闭，进行音视频播放的广播；
	public static final String ACTION_STOP_WIDGETMUSIC = "com.tcl.mediaplayer.audio.start";
	
	public static final String HTTPFILE ="http";
	public static final String SMBFILE ="/mnt/smb";
	
	
	public static final int RequestCode = 99999;
	public static final int ResultCode_FINISH = 8888;
	public static final String ResultURL ="Last_play_url";
	
  	public static int HANDLER_MSG_DELEY_PUT_FOCUS = 0X0001;
  	public static int HANDLER_MSG_DELEY_LOSE_FOCUS = 0X0002;
  	public static final int FOCUS_STATE_GET_FOCUS = 1;
  	public static final int FOCUS_STATE_LOSE_FOCUS = 2;
  	public static final int FINISH_NO_TIPS = 0X1001;
  	
	public static String CONFIG_3D_OLD = "ro.sita.model.Settings.3D";// //3D项,在老配置中配置
	public static String CONFIG_3D ="ro.sita.model.TCL3DSet.TCL3DStatus";//新配置项，是否支持分享
	public static String CONFIG_MEDIA_SHARE = "ro.sita.model.MediaPlayer.MediaShare";//是否支持分享
	public static String CONFIG_DOLBY = "ro.sita.model.Apps.hardware_support";//新的产品配置项杜比
	public static String DOLBY= "DOLBY";
	public static final int STREAM_DOLBY = 1;
	public static final int STREAM_DOLBY_PLUS = 2;
	public static final int STREAM_DTS = 3;
	public static final int STREAM_DTSHD_MASTER_AUDIO = 4;
	public static final int STREAM_DTS_EXPRESS = 5;
	
	public static final int VIDEO_DOBBY_VISION = 2;
}
