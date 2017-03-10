package com.tcl.hotplay;

import java.util.ArrayList;
import java.util.List;

import android.app.Application;
import android.tclwidget.TCLToast;
import android.util.Log;

import com.tcl.data.HotPlayListUtil;
import com.tcl.entity.HotPlayInfo;
import com.tcl.util.DataCallback;
import com.tcl.util.HttpUtil;
import com.tcl.util.ExceptionUtil;

public class MyApplication extends Application {
	private static final String TAG = "MyApplication";
	private static final int APP_ERROR_CODE = 0x031F0001;//应用错误代码，唯一标示。姜工给每一个App分配的。
	
	public static long time = System.currentTimeMillis();
	private static Application mApplication = null;
	public static Application getInstance(){
		return mApplication;
	}
	@Override
	public void onCreate() {
		Log.i(TAG,"onCreate()");
		//long time = System.currentTimeMillis();
		mApplication = this;
		super.onCreate();
		
		//全局异常捕获和上报
		ExceptionUtil.init(MyApplication.this,APP_ERROR_CODE);
		Log.i(TAG,"ExceptionUtil.init(this); pastTime = "+(System.currentTimeMillis() - time));

		Log.i(TAG,"super.onCreate(); pastTime = "+(System.currentTimeMillis() - time));
	}

}
