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

import com.tcl.common.mediaplayer.R;

public class SeekBarPopWindow {
	public static final int DISMISS_TIME = 600;

	Context mContext;
	Handler mHandler;
	PopupWindow popupWindow;
	TextView textView;
	FrameLayout mLayout;
	private boolean is10810P = true;

	Runnable dismissRunnable = new Runnable() {

		@Override
		public void run() {
			Utils.printLog("SeekBarPopWindow", "dismissRunnable");
			if (popupWindow.isShowing()) {
				try {
					Utils.printLog("SeekBarPopWindow", "dismiss");
					popupWindow.dismiss();
				} catch (IllegalArgumentException e) {
					// ignore it
				}
			}
		}

	};

	public SeekBarPopWindow(Context context, Handler handler) {
		mContext = context;
		mHandler = handler;
		mLayout = new FrameLayout(context);
		mLayout.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
		textView = new TextView(context);
		// textView.setBackgroundResource(R.drawable.seekbar_prompt_box);
		textView.setSingleLine(true);
		textView.setTextSize(20);
		textView.setTextColor(Color.WHITE);
		textView.setGravity(Gravity.CENTER);
		textView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
		// textView.setPadding(0, 0, 0, 15);
		mLayout.addView(textView);
		popupWindow = new PopupWindow(mLayout);
		popupWindow.setWidth(LayoutParams.WRAP_CONTENT);
		popupWindow.setHeight(LayoutParams.WRAP_CONTENT);
		popupWindow.setAnimationStyle(0);
		try {
			is10810P = Utils.isWindow1080((Activity) (mContext));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public void showText(final View anchor, String text, int xoff, int yoff) {
		mHandler.removeCallbacks(dismissRunnable);
		textView.setText(text);
		Utils.printLog("SeekBarPopWindow", "xoff =" + xoff +  "  textView.getWidth()="+textView.getWidth());
		popupWindow.dismiss();
		popupWindow.showAsDropDown(anchor, xoff-textView.getWidth()/2+Utils.SeekBarThumbLength/2, yoff);
		Utils.printLog("SeekBarPopWindow", "postDelayed");
		mHandler.postDelayed(dismissRunnable, DISMISS_TIME);
	}

	public void showText(final View anchor, int resourceId, int xoff, int yoff) {
		showText(anchor, mContext.getString(resourceId), xoff, yoff);
	}

	public void dismiss() {
		if (popupWindow.isShowing()) {
			try {
				popupWindow.dismiss();
			} catch (IllegalArgumentException e) {
				// ignore it
			}
		}
		mHandler.removeCallbacks(dismissRunnable);
	}

}
