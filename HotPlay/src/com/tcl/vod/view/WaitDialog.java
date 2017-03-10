package com.tcl.vod.view;

import java.lang.reflect.Method;

import android.content.Context;
import android.content.DialogInterface.OnCancelListener;
import android.tclwidget.TCLDLabel;
import android.util.Log;

/**
 * 等待对话框,实例化该类时，请注意判空
 * @author wangzhen
 *
 */
public class WaitDialog {
	private static final String TAG = "WaitDialog";
	private TCLDLabel mTCLDabel = null;// 进入Activity时的loading框
	private Context mContext = null;
	public  WaitDialog(Context cxt){
		mContext = cxt;
	}
	
	public void showWaiteDialog() {
		Log.i(TAG, "wz  showWaiteDialog 1");
		if (mContext == null) {
			return;
		}
		if (mTCLDabel == null) {
			mTCLDabel = TCLDLabel.makeTCLDLabel(mContext);
		}
		Log.i(TAG, "wz  showWaiteDialog 2");
		if (!mTCLDabel.isShowing()) {
			Method m = null;
			try {
				m = TCLDLabel.class.getMethod("showImmediately");
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
				mTCLDabel.show();
			}
			if (m != null) {
				mTCLDabel.showImmediately();
			}
		}
	}

	public void dissMissWaiteDialog() {
		Log.i(TAG, "wz  dissMissWaiteDialog 1");
		if (mTCLDabel != null && mTCLDabel.isShowing()) {
			Log.i(TAG, "wz  dissMissWaiteDialog 2");
			mTCLDabel.dismiss();
			//TCLDLabel.makeTCLDLabel(mContext.getApplicationContext());
			mTCLDabel = null;
		}
	}
	
	public boolean isShown(){
		if(mTCLDabel != null && mTCLDabel.isShowing()){
			return true;
		}else {
			return false;
		}
	}
	
	public void setOnCancelListener(OnCancelListener listener){
		mTCLDabel.setOnCancelListener(listener);
	}


}
