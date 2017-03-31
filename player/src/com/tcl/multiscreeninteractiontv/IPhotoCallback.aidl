package com.tcl.multiscreeninteractiontv;

interface IPhotoCallback{
	void dmr_play(String uri);//开始播放图片
	void dmr_stop();//停止图片播放
	void dmr_setPlayingName(String str);//设置播放的图片名称
	void dmr_picZoomIn();//放大当前播放图片
	void dmr_picZoomOut();//缩小当前播放图片
	void dmr_picRotateLeft();//当前播放图片左旋转
	void dmr_picRotateRight();//当前播放图片右旋转
}