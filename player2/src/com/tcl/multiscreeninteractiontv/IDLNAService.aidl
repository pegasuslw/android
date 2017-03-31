package com.tcl.multiscreeninteractiontv;

import com.tcl.multiscreeninteractiontv.IPlayerCallback;
import com.tcl.multiscreeninteractiontv.IPhotoCallback;

interface IDLNAService {
	void registerPlayerCallback(IPlayerCallback cb);//注册音视频播放器回调接口
	void unregisterPlayerCallback();//取消音视频播放器回调注册
	void registerPhotoCallback(IPhotoCallback cb);//注册图片播放回调接口
	void unregisterPhotoCallback();//取消图片播放回调注册
	void setPlayStatus(String status);//设置当前播放状态:播放-PLAYING, 停止-STOPPED, 暂停-PAUSED_PLAYBACK, 正在传输中-TRANSITIONING
	String getRecordAddr();//为语音助手留的接口，可以访问保存的文件路径
	boolean isHaveNewPCM();//为语音助手留的接口，用来区分是手机录音还是遥控器录音
 }