package com.tcl.pvr.pvrplayer.utils;

import com.tcl.pvr.pvrplayer.video.contrl.IVideoPlayControlHandler;

import android.app.Application;

public class MediaPlayerApplication extends Application {
	
	private IVideoPlayControlHandler mVideoContrl = null;// 视频控制接口；
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
	};
	
	
	public void setVideoContrl(IVideoPlayControlHandler ctrl){
		
		mVideoContrl = ctrl;
		
	}
	
	
	public IVideoPlayControlHandler getVideoCtrl(){
		return mVideoContrl;
	}

}
