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
package com.tcl.pvr.pvrplayer.utils;

import com.tcl.pvr.pvrplayer.R;

import android.content.Context;
import android.tclwidget.TCLToast;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MyToast {
	private TCLToast toast;
	public MyToast(Context context , String descString){
		toast = TCLToast.makePrompt(context, descString, TCLToast.INFO_IMAGE, TCLToast.LENGTH_SHORT);
//		View view = View.inflate(context, R.layout.my_toast, null);
//		TextView textView = (TextView)view.findViewById(R.id.text_desc);
//		textView.setText(descString);
//		setView(view);
//		setDuration(Toast.LENGTH_LONG);
//		setGravity(Gravity.CENTER, 0, 0);
	}

	public void show(){
		if(toast !=null){
			toast.show();
		}
	}
}
