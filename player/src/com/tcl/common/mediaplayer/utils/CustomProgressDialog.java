package com.tcl.common.mediaplayer.utils;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.TextView;

import com.tcl.videoplayer.R;


public class CustomProgressDialog extends Dialog {
	private String text;
	private TextView textView;
	private WaitDialogCallBackInterface callback = null;

	public CustomProgressDialog(Context context) {
		// super(context,R.style.dialog_theme);

		super(context, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
		this.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
				// TODO Auto-generated method stub
				if (callback != null && (keyCode == KeyEvent.KEYCODE_BACK) && (event.getAction() == KeyEvent.ACTION_UP)) {
					dialog.dismiss();
					callback.onDialogBackPressed();

				} else if (callback != null && keyCode == KeyEvent.KEYCODE_MENU && (event.getAction() == KeyEvent.ACTION_UP)) {
					callback.onDialogMenuPressed();
					// dismiss();
				}

				return false;
			}
		});
	}

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.netphoto_custom_progress);
		textView = (TextView) findViewById(R.id.text);
		if (text != null) {
			textView.setText(text);
		}
	}

	public CustomProgressDialog setMessage(String text) {
		this.text = text;
		return this;
	}

	public void registerCallback(WaitDialogCallBackInterface call) {
		callback = call;
	}
}