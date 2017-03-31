package com.tcl.pvr.pvrplayer.video.contrl;

import com.tcl.pvr.pvrplayer.R;

public interface VideoPlayerContrlConsts {
	
	public static final int BACK_OR_PREVIOUS_STEP_SIZE = 30000;//快进或者后退的间隔；
	
	//播放器播放类型flag.
	public static final int MEDIA_SEQUENCE_PLAY = 0;//顺序播放
	public static final int MEDIA_SINGLE_PLAY = 1;//单曲播放
	public static final int MEDIA_SINGLE_REPEAT_PLAY = 2;//单曲重复播放
	public static final int MEDIA_RANDOM_PLAY = 3;//随机播放
	public static final int MEDIA_CYCLE_PLAY = 4;//循环播放
	
	public static final int MEDIA_PALYER_ERROR = 52;
	public static final int MEDIA_PALY_FINISHED = 2231;
	public static final String URL_FOR_VOD_TYPE = "VOD";
	
	public static int START_BUFFERING = 1234;
	public static int FINISH_BUFFERING = 12345;
	
	public static int GET_CURRENTPOSITION_ERROR=-565;
	
	public static int DEFAULT_SUBTITLE = 255;
	public static int DEFAULT_AUDIO_TRACK = 1;
	
	
}
