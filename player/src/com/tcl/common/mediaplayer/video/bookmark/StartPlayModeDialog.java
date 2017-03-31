package com.tcl.common.mediaplayer.video.bookmark;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.os.Handler;
import android.tclwidget.TCLAlertDialog;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tcl.common.mediaplayer.utils.Utils;
import com.tcl.videoplayer.R;

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
//		tcldiaolog = TCLAlertDialog.makeDefaultFullDialog(mContex, 0, TCLAlertDialog.TWO_BUTTON, TCLAlertDialog.QUESTION_IMAGE);
        tcldiaolog = TCLAlertDialog.makeNoTitle(mContex, 0);
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
				 if(keyCode ==KeyEvent.KEYCODE_DPAD_CENTER){
					 if(mSure.hasFocus()){
						 mVideoHandler.sendEmptyMessage(BookMarkConst.START_PLAY_FROM_BREAK);
						 dialog.dismiss();
					 }
					 if(mCancel.hasFocus()){
						 mVideoHandler.sendEmptyMessage(BookMarkConst.START_PLAY_FROM_BEGIN);
						 dialog.dismiss();
					 }
					 return true;
				 }
				 if ((keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.KEYCODE_MENU) &&
				 (event.getAction() == KeyEvent.ACTION_UP)) {
				 Utils.printLog("StartPlayModelDialog", "BackDown");
				 dialog.dismiss();
				 mVideoHandler.sendEmptyMessage(BookMarkConst.START_PLAY_FROM_BREAK);
				 if(dialog != null){
					 dialog = null;
				 }
				
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
			if(tcldiaolog != null){
				tcldiaolog = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
	}

	private void initView() {
		
		 RelativeLayout.LayoutParams lpbutton = new RelativeLayout.LayoutParams(
			     LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
			   lpbutton.width = LayoutParams.WRAP_CONTENT;
			   lpbutton.height = LayoutParams.WRAP_CONTENT;
		
		tcldiaolog.setButton(mContex, TCLAlertDialog.TWO_BUTTON, lpbutton);
		
		mSure = tcldiaolog.getLeftButton();
		mCancel = tcldiaolog.getRightButton();
		mSure.setText(mContex.getResources().getString(R.string.play_break));
		mCancel.setText(mContex.getResources().getString(R.string.play_begin));
		String breakpos = Utils.getTimeShort(mBreakPos);
		Utils.printLog(TAG, "Utils.getTimeShort(mBreakPos)=" + breakpos);
		RelativeLayout.LayoutParams lptitle = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
//		tcldiaolog.setTitle(mContex, tcldiaolog,TCLAlertDialog.QUESTION_IMAGE, mContex.getResources().getString(R.string.asking),lptitle);
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
