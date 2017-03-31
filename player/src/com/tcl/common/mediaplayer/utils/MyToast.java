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
package com.tcl.common.mediaplayer.utils;



import com.tcl.videoplayer.R;

import android.content.Context;
import android.tclwidget.TCLToast;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MyToast {
	private static TCLToast toast;
	public MyToast(Context context , String descString){
		toast = TCLToast.makePrompt(context, descString, TCLToast.INFO_IMAGE, TCLToast.LENGTH_SHORT);
//		View view = View.inflate(context, R.layout.my_toast, null);
//		TextView textView = (TextView)view.findViewById(R.id.text_desc);
//		textView.setText(descString);
//		setView(view);
//		setDuration(Toast.LENGTH_LONG);
//		toast.setGravity(Gravity.CENTER, 0, -350);
	}
	public MyToast(Context context , String descString , int show){
		toast = TCLToast.makePrompt(context, descString, TCLToast.INFO_IMAGE, TCLToast.LENGTH_LONG);
//		View view = View.inflate(context, R.layout.my_toast, null);
//		TextView textView = (TextView)view.findViewById(R.id.text_desc);
//		textView.setText(descString);
//		setView(view);
//		setDuration(Toast.LENGTH_LONG);
		toast.setGravity(Gravity.CENTER, 0, -350);
	}

	public void show(){
		if(toast !=null){
			toast.show();
		}
	}
	public static TCLToast getInstance(Context mContext,String descString){
		if(toast==null){
			toast = TCLToast.makePrompt(mContext, descString, TCLToast.INFO_IMAGE, TCLToast.LENGTH_LONG);
		}else{
			return toast;
		}
		return toast;
	}
	public static void showNoFuntionToast(Context mContext){
		Log.d("Error", "showNoFuntionToast ActivityNotFoundException");
		MyToast.getInstance(mContext, mContext.getResources().getString(R.string.NoFuntion)).show();
	}
}
