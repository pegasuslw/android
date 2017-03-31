package com.tcl.common.mediaplayer.video.UI;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import com.tcl.common.mediaplayer.R;
import com.tcl.common.mediaplayer.aidl.CommonConst;
import com.tcl.common.mediaplayer.utils.MediaPlayerApplication;
import com.tcl.common.mediaplayer.utils.Utils;
import com.tcl.common.mediaplayer.video.contrl.IVideoPlayControlHandler;
import com.tcl.common.mediaplayer.video.contrl.VideoPlayerContrlConsts;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.LinearLayout;
import android.widget.TextView;

public class VideoAdvanceDialogActivity extends Activity {



	private String TAG = "AdvanceSetDialog";
	private TextView sutitle, audiotrack;
	private LinearLayout sub_Lay, track_Lay;
	private int subNms, trackNms;
	private int mCurrSub, mCurrTrack;
	private ArrayList<Integer> mSubValues = new ArrayList<Integer>();
	private boolean isGetContent = false;
	private boolean isKeyPressed = false;
	final Timer timer = new Timer();
	private Timer subTimer = new Timer();
	private boolean isNextSubtitelValid = true;
	private TextView subtitlename, audiotrackname;
	private int mLastTrack =1;

//	protected AdvanceSetDialog(Context context) {
//		super(context);
//		init();
//		// TODO Auto-generated constructor stub
//	}

	private IVideoPlayControlHandler mVideoContrl = null;
	
	private void init() {

		if (mVideoContrl != null
				&& mVideoContrl.isMediaPlayerPrepared()) {
			
			subNms = mVideoContrl.getSubtitleNms();
			mSubValues.clear();
			mSubValues.add(VideoPlayerContrlConsts.DEFAULT_SUBTITLE);
			for (int i = 1; i < subNms+1; i++) {
				mSubValues.add(i);
			}
			mCurrSub = mVideoContrl.getCurrentSubtitleNms();

			trackNms = mVideoContrl.getAudioTrackNms();
			mCurrTrack = mVideoContrl.getCurrentAudioTrackNms();
			mLastTrack = mCurrTrack;
			isGetContent = true;

		}else{
			finish();
		}

	}

	private void setSubtiteValid(){
		subTimer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				isNextSubtitelValid = true;
				Utils.printLog(TAG, "isNextSubtitelValid = true;");
			}
		}, 1000);
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		Utils.printLog(TAG, "onKeyDown");

		isKeyPressed = true;
		if (event.getKeyCode() == KeyEvent.KEYCODE_DPAD_RIGHT) {

			if (sub_Lay.hasFocus()&& isNextSubtitelValid) {// 此时选中的是字幕菜单；

				if (subNms <= 0) {// 当前视频无字幕，则按键无效；
					return false;
				}
				isNextSubtitelValid = false;
				setSubtiteValid();
				int mCurrIndex = mSubValues.indexOf(mCurrSub);
				mCurrIndex = (mCurrIndex + 1) % mSubValues.size();
				mCurrSub = mSubValues.get(mCurrIndex);
				if (mVideoContrl != null) {
					reFreshSubtitleText();
					mVideoContrl.setSubtitleNms((short) mCurrSub);
				}
				
			} else if (track_Lay.hasFocus()&& isNextSubtitelValid) {
				if (trackNms <= 1) {
					return false;
				}
				isNextSubtitelValid = false;
				setSubtiteValid();
				mLastTrack = mCurrTrack;
				mCurrTrack = (mCurrTrack + 1) % trackNms;
				if(mCurrTrack ==0){
					mCurrTrack=trackNms;
				}
				if (mVideoContrl != null) {
					reFreshTrackText();
					mVideoContrl.setAudioTrackNms((short) mCurrTrack);
				}
				
			}

		} else if (event.getKeyCode() == KeyEvent.KEYCODE_DPAD_LEFT) {
			if (sub_Lay.hasFocus()&& isNextSubtitelValid) {// 此时选中的是字幕菜单；

				if (subNms <= 0) {// 当前视频无字幕，则按键无效；
					return false;
				}
				Utils.printLog(TAG, "left deal");
				isNextSubtitelValid = false;
				setSubtiteValid();
				int mCurrIndex = mSubValues.indexOf(mCurrSub);
				mCurrIndex = (mCurrIndex + mSubValues.size() - 1)
						% mSubValues.size();
				mCurrSub = mSubValues.get(mCurrIndex);
				if (mVideoContrl != null) {
					reFreshSubtitleText();
					mVideoContrl.setSubtitleNms((short) mCurrSub);
				}
				
			} else if (track_Lay.hasFocus()&& isNextSubtitelValid) {
				if (trackNms <= 1) {
					return false;
				}
				isNextSubtitelValid = false;
				setSubtiteValid();
				mLastTrack = mCurrTrack;
				mCurrTrack = (mCurrTrack + trackNms - 1) % trackNms;
				if(mCurrTrack ==0){
					mCurrTrack=trackNms;
				}
				
				if (mVideoContrl != null) {
					reFreshTrackText();
					mVideoContrl.setAudioTrackNms((short) mCurrTrack);
				}
				
			}

		}

		return super.onKeyDown(keyCode, event);
	}

	private void reFreshSubtitleText() {
		if (mVideoContrl != null && isGetContent && subNms > 0) {
			sub_Lay.setFocusable(true);
			if (mCurrSub == VideoPlayerContrlConsts.DEFAULT_SUBTITLE) {
				sutitle.setText(VideoAdvanceDialogActivity.this
						.getString(R.string.default_sub));
			} else {

				sutitle.setText(VideoAdvanceDialogActivity.this
						.getString(R.string.subtitle_begin)
						+ mCurrSub);
			}
			sutitle.setTextColor(getResources().getColor(R.color.ui_textcolor));
			//subtitlename.setTextColor(getResources().getColor(R.color.text_color_dark));

		} else {
			sutitle.setText(VideoAdvanceDialogActivity.this
					.getString(R.string.default_sub));
			sutitle.setTextColor(getResources().getColor(R.color.text_color_disabled));
			//subtitlename.setTextColor(getResources().getColor(R.color.text_color_disabled));
			sub_Lay.setFocusable(false);
			
		}
	}

	private void reFreshTrackText() {

		if (mVideoContrl != null && isGetContent && trackNms > 1) {
			track_Lay.setFocusable(true);
			 audiotrack.setText(VideoAdvanceDialogActivity.this
			 .getString(R.string.audioTrack_begin)
			 + mCurrTrack );
			 audiotrack.setTextColor(getResources().getColor(R.color.ui_textcolor));
			// audiotrackname.setTextColor(getResources().getColor(R.color.text_color_dark));
		//	audiotrack.setText(mVideoContrl.getCurrentAudioTrackName());
		} else {
			audiotrack.setText(VideoAdvanceDialogActivity.this
					.getString(R.string.default_track));
			audiotrack.setTextColor(getResources().getColor(R.color.text_color_disabled));
			//audiotrackname.setTextColor(getResources().getColor(R.color.text_color_disabled));
			track_Lay.setFocusable(false);
		}

	}
	
	private BroadcastReceiver mVideoPlayerReceiver = new BroadcastReceiver(){

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			Utils.printLog(TAG, "Received broadCast mVideoPlayerReceiver");
			if(intent.getAction().equals("com.tcl.mediaplayer.exit.subtitle")){
				VideoAdvanceDialogActivity.this.finish();
			} else if (intent.getAction().equals(CommonConst.VIDEO_SETTING_AUDIOTRACK)) {
				mCurrTrack = mLastTrack;
				mVideoContrl.setAudioTrackNms((short) mCurrTrack);
				reFreshTrackText();
			} 
		}
		
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		MediaPlayerApplication application = (MediaPlayerApplication)getApplication();
		mVideoContrl = application.getVideoCtrl();	
		init();
		setContentView(R.layout.advanced);		
		Utils.printLog(TAG, "setcontentview");
		sutitle = (TextView) findViewById(R.id.sub_text);
		audiotrack = (TextView) findViewById(R.id.track_text);
		subtitlename = (TextView) findViewById(R.id.sub_title);
		audiotrackname = (TextView) findViewById(R.id.track_title);
		sub_Lay = (LinearLayout) findViewById(R.id.subtitle);
		track_Lay = (LinearLayout) findViewById(R.id.audioTrack);
		reFreshSubtitleText();
		reFreshTrackText();
		
       IntentFilter mFilter = new IntentFilter();
       mFilter.addAction("com.tcl.mediaplayer.exit.subtitle");
       mFilter.addAction(CommonConst.VIDEO_SETTING_AUDIOTRACK);
       registerReceiver(mVideoPlayerReceiver, mFilter);
       
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				Utils.printLog(TAG, "Sub timer come in with isKeyPressed ="
						+ isKeyPressed);
				if (isKeyPressed) {
					isKeyPressed = false;
				} else {
					timer.cancel();
					VideoAdvanceDialogActivity.this.finish();
				//	advanceDialog = null;

				}
			}
		}, 10000, 10000);
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		timer.cancel();

		finish();
		//advanceDialog = null;
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		try {
			if(mVideoPlayerReceiver!=null){
				unregisterReceiver(mVideoPlayerReceiver);
			}
			} catch (Exception il) {

				il.printStackTrace();
			}

	
	}
}
