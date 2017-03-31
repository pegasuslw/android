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

public interface VideoPlayControlCallback {

	 void onVideoPlayChanged(int index,int position);//视频播放完成，开始播放下一视频，通知UI进行界面更新；
	 void onVideoPlayPrepared();//需要播放的视频已经准本完成；
	 void onVideoPlaySeekComplete(int currentPosition);//视频播放seek完成通知；
	 void onVideoSeekStart(int pos);//当前视频开始seek；
	 void onVideoPlayBufferingUpdate(int percent);//底层播放缓冲通知；	
	 void onVideoPlayError(int errCode);//视频播放错误通知；
	 void onVideoPlayInfoNotify(int infoCode);//视频播放消息通知；如需要缓冲提示等；
	 void onVideoSizeChanged(int width, int height);//视频播放大小发生变化通知；
	 void onVideoPlayRemoveIndex(int index);//当前视频无法播放，从播放列表中删除通知；
	 void onSurfaceCreated();
	 void onSurfaceDertory();
	 void onVideoPlayComplete();
}
