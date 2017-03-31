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





import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.tcl.videoplayer.R;

public class NotePopupWindow {
    public static final int DISMISS_TIME = 2000;
    
    Context mContext;
    Handler mHandler;
    PopupWindow popupWindow;
    TextView textView;
    FrameLayout mLayout;
    private boolean is10810P = true;
    
    Runnable dismissRunnable = new Runnable() {

        @Override
        public void run() {
            if(popupWindow.isShowing()) {
                try {
                    popupWindow.dismiss();
                }catch(IllegalArgumentException e) {
                    //ignore it
                }
            }
        }
        
    };
    
    public NotePopupWindow(Context context, Handler handler) {
        mContext = context;
        mHandler = handler;
        mLayout = new FrameLayout(context);
        mLayout.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
        textView = new TextView(context);
        textView.setBackgroundResource(R.drawable.pop_background);
        textView.setSingleLine(true);
        textView.setTextSize(26);
        textView.setTextColor(Color.WHITE);
        textView.setGravity(Gravity.CENTER);
        textView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
        textView.setPadding(0, 0, 0, 15);
        mLayout.addView(textView);
        popupWindow = new PopupWindow(mLayout);
        popupWindow.setWidth(LayoutParams.WRAP_CONTENT);
        popupWindow.setHeight(LayoutParams.WRAP_CONTENT);
        popupWindow.setAnimationStyle(0);
        try{
        	is10810P = Utils.isWindow1080((Activity)(mContext));
        }catch (Exception e) {
			// TODO: handle exception
        	e.printStackTrace();
		}
    }
    
   
    public void showText(final View anchor, String text) {
        Log.v("text:", "" + text);
        mHandler.removeCallbacks(dismissRunnable);
        textView.setText(text);
//        textView.invalidate();
//        popupWindow.update();
        if(is10810P){
        	popupWindow.showAsDropDown(anchor, 0, -(anchor.getHeight() + 65));
        }else{
        	popupWindow.showAsDropDown(anchor, 0, -(anchor.getHeight() + 45));
        }
        
        mHandler.postDelayed(dismissRunnable, DISMISS_TIME);
    }
    
    public void showText(final View anchor, int resourceId) {
        showText(anchor, mContext.getString(resourceId));
    }
    
    public void dismiss() {
        if(popupWindow.isShowing()) {
            try {
                popupWindow.dismiss();
            }catch(IllegalArgumentException e) {
                //ignore it
            }
        }
        mHandler.removeCallbacks(dismissRunnable);
    }
    
    
}
