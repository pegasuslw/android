package com.tcl.common.mediaplayer.utils;

public interface ErrorConsts {

	// 播放器的错误类型；

	public static int media_source_not_found = 1;// 无法获取媒体资源文件;

	public static int unknown_media_format = -5003;// 不支持该媒体文件格式

	public static int media_interrupt = -1004;// 媒体文件已经损坏；
	//AUDIO_ENCODE_FROMAT_UNSUPPORT(-5001) MT5658是在onInfo中返回的，下次会告诉他们应该在onError中
//	public static int VIDEO_ENCODE_FROMAT_UNSUPPORT = -5000;
//	public static int AUDIO_ENCODE_FROMAT_UNSUPPORT = -5001;
	public static int unknown_audio_format = -5001;// 不支持媒体文件音频格式

	public static int unknown_video_format = -5002;// 不支持媒体文件视频格式
	
	public static int tv_sourcemanager_change = -5006;// 不支持媒体文件视频格式
	
	public static int mtk_video_only_format = -5009; //mtk 只有视频可播放文件

	public static int media_player_unknown_exception = 5;// 播放器播放过程发生未知错误，请重试
	public static int media_player_unknown_exception_38 = -38;// 播放器播放过程发生未知错误，请重试
	public static int media_player_buffering = 701;// 正在缓冲；
	public static int media_player_buffered = 702;// 缓冲结束；

	public static int media_player_network_slow = -5000;// 网络加载失败
	public static int media_player_video_notsupport_mtk = -5000;   //mtk 5658 oninfo 返回 video unknow
	public static int media_player_not_seekable = 801;// 不能ｓｅｅｋ
	public static int media_player_getsubtitleinfo_mtk = 802;//获取802info后get subtitle相关信息
	public static int media_player_exception = 6;// 播放过程中出现异常，请检查您的媒体文件后重新尝试
	public static int media_player_init_error = 7;// 播放器播放过程发生未知错误，请重试

	public static int media_player_cache_percent = 901;
	public static int PLAY_LIST_NULL = 23232223;
	public static int PLAY_DEVICE_UNMOUTED = 343434;

	public static int MEDIA_ERROR_BASE = -1000;
	public static int ERROR_ALREADY_CONNECTED = MEDIA_ERROR_BASE;
	public static int ERROR_NOT_CONNECTED = MEDIA_ERROR_BASE - 1;
	public static int ERROR_UNKNOWN_HOST = MEDIA_ERROR_BASE - 2;
	public static int ERROR_CANNOT_CONNECT = MEDIA_ERROR_BASE - 3;
	public static int ERROR_IO = MEDIA_ERROR_BASE - 4;
	public static int ERROR_CONNECTION_LOST = MEDIA_ERROR_BASE - 5;
	public static int ERROR_MALFORMED = MEDIA_ERROR_BASE - 7;
	public static int ERROR_OUT_OF_RANGE = MEDIA_ERROR_BASE - 8;
	public static int ERROR_BUFFER_TOO_SMALL = MEDIA_ERROR_BASE - 9;
	public static int ERROR_UNSUPPORTED = MEDIA_ERROR_BASE - 10;
	public static int ERROR_END_OF_STREAM = MEDIA_ERROR_BASE - 11;

}
