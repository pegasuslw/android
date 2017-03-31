package com.tcl.common.mediaplayer.utils;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.tcl.common.mediaplayer.video.contrl.IVideoPlayControlHandler;

import android.app.Application;
import android.content.Context;

public class MediaPlayerApplication extends Application {
	
	private IVideoPlayControlHandler mVideoContrl = null;// 视频控制接口；
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		
        initImageLoader(getApplicationContext());
	};
	
	
	public void setVideoContrl(IVideoPlayControlHandler ctrl){
		
		mVideoContrl = ctrl;
		
	}
	
	
	public IVideoPlayControlHandler getVideoCtrl(){
		return mVideoContrl;
	}

	/**
	 * 初始化图片下载参数
	 * you can create default configuration by ImageLoaderConfiguration.createDefault(this);
	 * @param context
	 */
	public static void initImageLoader(Context context) {
		ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
		config.threadPriority(Thread.NORM_PRIORITY - 2);
		config.denyCacheImageMultipleSizesInMemory();
		config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
		config.diskCacheSize(50 * 1024 * 1024); // 50 MiB
		config.tasksProcessingOrder(QueueProcessingType.LIFO);
		config.writeDebugLogs(); // Remove for release app
		// Initialize ImageLoader with configuration.
		ImageLoader.getInstance().init(config.build());
	}
}
