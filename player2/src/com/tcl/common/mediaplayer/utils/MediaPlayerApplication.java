package com.tcl.common.mediaplayer.utils;

import java.lang.Thread.UncaughtExceptionHandler;

import com.tcl.common.mediaplayer.video.contrl.IVideoPlayControlHandler;

import android.app.Application;
import android.util.Log;

public class MediaPlayerApplication extends Application {
	
	private IVideoPlayControlHandler mVideoContrl = null;// 视频控制接口；
	private String errorinfo="";
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		
		Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler() {	 //用此方法捕获整个进程中的异常			
			@Override                                                                //在异常时调用终端管理提供的方法上报异常
			public void uncaughtException(Thread thread, Throwable ex) {         
				        errorinfo = ex.getLocalizedMessage();
						Log.d("=======","errorinfo=="+ errorinfo);
						android.os.Process.killProcess(android.os.Process.myPid());		//进程捕获到异常后将自己关闭
						
			}
		});
	};
	
	
	public void setVideoContrl(IVideoPlayControlHandler ctrl){
		
		mVideoContrl = ctrl;
		
	}
	
	
	public IVideoPlayControlHandler getVideoCtrl(){
		return mVideoContrl;
	}
	
	

}
