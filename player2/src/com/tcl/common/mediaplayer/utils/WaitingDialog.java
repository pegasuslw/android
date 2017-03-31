package com.tcl.common.mediaplayer.utils;

import com.tcl.common.mediaplayer.R;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.content.pm.LabeledIntent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.tclwidget.TCLAlertDialog;
import android.tclwidget.TCLDLabel;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class WaitingDialog  {


	private String TAG = this.getClass().getName();
	private WaitDialogCallBackInterface callback = null;
	private TCLDLabel lable;
//	public WaitingDialog(Context context) {
//		super(context, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
//	}
	public WaitingDialog(Context context) {
	//	super(context);
		// TODO Auto-generated constructor stub
		lable = TCLDLabel.makeTCLDLabel(context);
		lable.setOnKeyListener(new OnKeyListener() {
			
			@Override
			public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
				// TODO Auto-generated method stub
				if (callback!=null&&(keyCode == KeyEvent.KEYCODE_BACK)&&(event.getAction() == KeyEvent.ACTION_UP)) {
					dialog.dismiss();
					callback.onDialogBackPressed();
		 
				}else if(callback!=null&& keyCode == KeyEvent.KEYCODE_MENU&&(event.getAction() == KeyEvent.ACTION_UP)){
					callback.onDialogMenuPressed();
					//dismiss();
				}
				
				return false;
			}
		});
	}

public void show(){
	if(lable !=null && !lable.isShowing()){
		lable.show();
	}
}

public void dismiss(){
	if(lable !=null && lable.isShowing()){
		lable.dismiss();
	}
}
	public void registerCallback(WaitDialogCallBackInterface call){
		callback= call;
	}
	
	
	
}
