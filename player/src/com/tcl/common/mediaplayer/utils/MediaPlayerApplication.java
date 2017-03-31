package com.tcl.common.mediaplayer.utils;

import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import com.tcl.common.mediaplayer.video.contrl.IVideoPlayControlHandler;
import com.tcl.deviceinfo.TDeviceInfo;

import android.app.Application;
import android.content.Context;
import android.util.Log;

public class MediaPlayerApplication extends Application {
	
	
	
	private String errorinfo="";
	private int appErrorNumber = 0x03020001;
	private Class<?> mClass;
	private Object mErrorReport;
//	private Object mSendReport;
	private int ErrorCodeReportMethod_logcat = 0;//logcat
	private int ErrorCodeReportMethod_file = 1;//file
	private IVideoPlayControlHandler mVideoContrl = null;// 视频控制接口；
	private boolean isDLNA = false;
	private boolean isDMR = false;
	private boolean isFromWeb = false;
	private boolean is3DEnabled = true;
	
    public static Context mContext;
	
	
	public static Context getContext(){
		return mContext;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		
		mContext = this;
		Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler() {	 //用此方法捕获整个进程中的异常			
			@Override                                                                //在异常时调用终端管理提供的方法上报异常
			public void uncaughtException(Thread thread, Throwable ex) {
			        
				        findErrorReportClass();            
				        errorinfo = ex.getLocalizedMessage();
						Log.d("=======","errorinfo=="+ errorinfo);
						initErrorReport(errorinfo);
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
	
	/**
	 * 通过反射方法获取系统是否存在ErrorReport类
	 * */
	public Class<?> findErrorReportClass() {
		try {
			mClass = Class.forName("com.tcl.terminalmanager.ErrorReport");
			Log.d("=======","find this class");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (Exception e){
			e.printStackTrace();
		}
		return mClass;
	}
	
	/**
	 * 通过反射方调用sendReport方法上报错误
	 * */
	public void initErrorReport(String errorinfo) {
		try {
			Method mReport = mClass.getDeclaredMethod("getInstance",
					Context.class); // 获取ErrorReport.getInstance(context)
			if (mReport != null) {
				mErrorReport = mReport.invoke(mClass, MediaPlayerApplication.this);
				Method mSendReport = mClass.getDeclaredMethod("sendReport",
						int.class, String.class, int.class, String.class); // 获取 m.sendReport(errorCode, errInfo, logStyle, FileName)方法
				if (mSendReport != null) {
					mSendReport.invoke(mErrorReport, appErrorNumber, errorinfo,
							ErrorCodeReportMethod_logcat, null);
				}
				
			}
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	public boolean isDLNA() {
		return isDLNA;
		//	return true;
	}

	public void setDLNA(boolean isDLNA) {
		this.isDLNA = isDLNA;
	}

	public boolean isDMR() {
			return isDMR;
			//	return true;
	}

	public void setDMR(boolean isDMR) {
		this.isDMR = isDMR;
		
	}
	
	public boolean isFromWeb() {
		return isFromWeb;
	}

	public void setFromWeb(boolean isFromWeb) {
		this.isFromWeb = isFromWeb;
	}
	
	public boolean isIs3DEnabled() {
		if(getClientType().contains("938") || getClientType().contains("838")){
			return false;
		}
		return is3DEnabled;
		//	return false;
	}

	public void setIs3DEnabled(boolean is3dEnabled) {
		this.is3DEnabled = is3dEnabled;
	}

	private String getClientType (){
		
		String nowClientType = "";
		try {
			int id = TDeviceInfo.getInstance().getProjectID();
			nowClientType = TDeviceInfo.getInstance().getClientType(id);
		} catch (Exception e) {
		    e.printStackTrace();
		}
		
		Log.d("videoplyaer===","nowClientType is:"+nowClientType);
		return nowClientType;
	}

}
