package com.tcl.pvr.pvrplayer.video.bookmark;

import com.tcl.pvr.pvrplayer.R;
import com.tcl.pvr.pvrplayer.utils.Utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.tclwidget.TCLAlertDialog;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class StartPlayModeDialog {
	private Button mSure, mCancel;
	// private LinearLayout mLayoutBreak, mLayoutBegin;
	protected Context mContex;
	protected int mStartPlayMode = BookMarkConst.START_PLAY_FROM_BREAK;
	private static final String TAG = "StartPlayModeDialog";
	private Handler mVideoHandler = null;
	private int mBreakPos = 0;
	private TextView textinfo;
	private TCLAlertDialog tcldiaolog = null;

	public StartPlayModeDialog(Context c, Handler handler,int pos) {
		//super(c, R.style.messagebox);
		mContex = c;
		mVideoHandler = handler;
		mBreakPos = pos;
		tcldiaolog = TCLAlertDialog.makeDefaultFullDialog(mContex, 0, TCLAlertDialog.TWO_BUTTON, TCLAlertDialog.QUESTION_IMAGE);
//		tcldiaolog= TCLAlertDialog.makeFullDialog(mContex, 0);
//		textinfo = new TextView(mContex);
//	//	textinfo.setTextColor(Color.BLACK);
//		
//		
//		RelativeLayout.LayoutParams title = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
//		RelativeLayout.LayoutParams content = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
//		RelativeLayout.LayoutParams btn = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
//		if(Utils.isWindow1080((Activity)(mContex))){
////			title.width = 810;
////			title.height = 112;
//			content.width = 810;
//			content.height = 135;//RelativeLayout.LayoutParams.WRAP_CONTENT;
//			content.setMargins(30, 30, 30, 30);
//			btn.width = 600; //RelativeLayout.LayoutParams.WRAP_CONTENT;
//			btn.height =  RelativeLayout.LayoutParams.WRAP_CONTENT;
//			//btn.leftMargin = 75;
//		//	btn.rightMargin = 50;
//			//btn.setMargins(20, 20, 20, 20);
//			textinfo.setTextSize(25);
//			textinfo.setTextColor(Color.WHITE);
//			
//		}else{
////			title.width = 540;
////			title.height = 75;
//			content.width = 540;
//			content.height = 90;//RelativeLayout.LayoutParams.WRAP_CONTENT;
//			content.setMargins(20, 20, 20, 20);
//			btn.width = 400; //RelativeLayout.LayoutParams.WRAP_CONTENT;
//			btn.height =  RelativeLayout.LayoutParams.WRAP_CONTENT;
//			btn.leftMargin = 50;
//		//	btn.rightMargin = 50;
//			//btn.setMargins(20, 20, 20, 20);
//			textinfo.setTextSize(25);
//			textinfo.setTextColor(Color.WHITE);
//		}
//		
//		tcldiaolog.setTitle(mContex, tcldiaolog, TCLAlertDialog.INFO_IMAGE, mContex.getResources().getString(R.string.Tips), title);
//		tcldiaolog.setCustomContentView(textinfo, content);
//		tcldiaolog.setButton(mContex, TCLAlertDialog.TWO_BUTTON, btn);
		//tcldiaolog.setti
		initView();
		
		tcldiaolog.setOnKeyListener(new OnKeyListener() {
			
			@Override
			public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
				// TODO Auto-generated method stub
				
				 if ((keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.KEYCODE_MENU) &&
				 (event.getAction() == KeyEvent.ACTION_UP)) {
				 Utils.printLog("StartPlayModelDialog", "BackDown");
				 dialog.dismiss();
				 mVideoHandler.sendEmptyMessage(BookMarkConst.START_PLAY_FROM_BREAK);
				
				 }
				return false;
			}
		});
	}

	public void show() {
		try {
			tcldiaolog.setCanceledOnTouchOutside(false);
			tcldiaolog.show();
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
	}

	public void dismiss() {
		try {
			tcldiaolog.dismiss();
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
	}

	private void initView() {
		mSure = tcldiaolog.getLeftButton();
		mCancel = tcldiaolog.getRightButton();
		mSure.setText(mContex.getResources().getString(R.string.play_break));
		mCancel.setText(mContex.getResources().getString(R.string.play_begin));
		String breakpos = Utils.getTimeShort(mBreakPos);
		Utils.printLog(TAG, "Utils.getTimeShort(mBreakPos)=" + breakpos);
		tcldiaolog.setContentText(mContex,mContex.getString(R.string.breakfrom) + breakpos+ mContex.getString(R.string.breakto));
//		textinfo.setText(mContex.getString(R.string.breakfrom) + breakpos
//				+ mContex.getString(R.string.breakto));
//		textinfo.invalidate();
		mSure.setOnClickListener(mSureListener);
		mCancel.setOnClickListener(mCancelListener);

		mSure.setNextFocusDownId(mSure.getId());
		mSure.setNextFocusLeftId(mSure.getId());
		mSure.setNextFocusRightId(mCancel.getId());

		mCancel.setNextFocusDownId(mCancel.getId());
		mCancel.setNextFocusLeftId(mSure.getId());
		mCancel.setNextFocusRightId(mCancel.getId());

	}

	Button.OnClickListener mCancelListener = new Button.OnClickListener() {

		public void onClick(View v) {
			// startPlayMovie(mPos);
			dismiss();
			mVideoHandler.sendEmptyMessage(BookMarkConst.START_PLAY_FROM_BEGIN);
		}

	};

	Button.OnClickListener mSureListener = new Button.OnClickListener() {
		public void onClick(View v) {
			dismiss();
			mVideoHandler.sendEmptyMessage(BookMarkConst.START_PLAY_FROM_BREAK);

		}
	};
	
	
	

	// public void onBackPressed() {
	// // Utils.printLog("StartPlayModelDialog", "onBackPressed");
	// // dismiss();
	// //mVideoHandler.sendEmptyMessage(BookMarkConst.START_PLAY_FROM_BREAK);
	// };
	// @Override
	// public boolean onKeyDown(int keyCode, KeyEvent event) {
	// Utils.printLog("StartPlayModelDialog",
	// "onKeyDown with keycode = "+keyCode);
	// // if (keyCode == KeyEvent.KEYCODE_BACK){
	// // Utils.printLog("StartPlayModelDialog", "BackDown");
	// // mVideoHandler.sendEmptyMessage(BookMarkConst.START_PLAY_FROM_BREAK);
	// // }
	// if (keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.KEYCODE_MENU
	// || ((keyCode == KeyEvent.KEYCODE_ENTER) &&
	// (event.getAction() == KeyEvent.ACTION_UP)) ) {
	// Utils.printLog("StartPlayModelDialog", "BackDown");
	// dismiss();
	// mVideoHandler.sendEmptyMessage(BookMarkConst.START_PLAY_FROM_BREAK);
	//
	// }
	// return super.onKeyDown(keyCode, event);
	// }
}
