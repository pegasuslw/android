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
package com.tcl.common.mediaplayer.audio.contrl;

import com.tcl.common.mediaplayer.R;
import com.tcl.common.mediaplayer.utils.Utils;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.TextView;

/**
 * The Class DeskTopLycShowDialog.
 */
public class DeskTopLycShowDialog extends Dialog {
  
  /** The TAG. */
  private String TAG ="DeskTopLycShowDialog";
	
	/** The lycshow. */
	private TextView lycshow;
	
	/**
	 * Instantiates a new desk top lyc show dialog.
	 *
	 * @param context the context
	 * @param theme the theme
	 */
	public DeskTopLycShowDialog(Context context, int theme) {
		super(context, theme);
		Utils.printLog(TAG, "Constructor");
		setContentView(R.layout.desk_top_lylic_layout);
		lycshow =(TextView)findViewById(R.id.lylic_view);
		if(lycshow==null){
			System.out.println("===============================lycshow null");
		}else{
			System.out.println("===============================lycshow not null");
		}
	}

	/* (non-Javadoc)
	 * @see android.app.Dialog#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Utils.printLog(TAG, "onCreate");
		super.onCreate(savedInstanceState);

		WindowManager.LayoutParams lp = getWindow().getAttributes();
		lp.type = WindowManager.LayoutParams.TYPE_TOAST;
		lp.flags = WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
				| WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
				| WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
				| WindowManager.LayoutParams.FLAG_DITHER
				| WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
		lp.gravity = Gravity.BOTTOM;
		getWindow().setAttributes(lp);
		// setContentView(R.layout.notification_icon_dialog);

	}
	
	/**
	 * Refresh lyc.
	 *
	 * @param lyc the lyc
	 */
	public void refreshLyc(String lyc){
        if(lycshow!=null){
    		lycshow.setText(lyc);
    		lycshow.invalidate();
        }

	}

}
