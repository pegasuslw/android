package com.tcl.common.mediaplayer.multiScreen;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import com.tcl.common.mediaplayer.aidl.CommonConst;
import com.tcl.common.mediaplayer.aidl.MediaBean;
import com.tcl.common.mediaplayer.utils.Utils;

import android.app.AlarmManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

public class MultiScreenInterActReceiver extends BroadcastReceiver {
	private String TAG = "MultiScreenInterActReceiver - RGPReceiver";
	public static String mIgrsDeviceId;
	private Intent intent_common;
	private Context this_context;

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		this_context = context;
		Utils.printLog(TAG, " onReceive");
		// mPlayPath = URLDecoder.decode(mPlayPath);
		if (intent.getAction().equals(MultiScreenConst.ACTION_LOCAL_START)) {
			Utils.printLog(TAG, "ACTION_RESTART_VIDEOPLAYER");

			String mPlayPath = intent
					.getStringExtra(MultiScreenConst.PLAY_PATH);
			Log.d(TAG, "mPlayPath=" + mPlayPath);
			if (mPlayPath == null)
				return;
			Log.d(TAG, URLDecoder.decode(mPlayPath));

			intent_common = new Intent();
			intent_common.setAction(CommonConst.COMMON_ACTION);
			intent_common.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			intent_common.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			Uri uri1 = Uri.parse(mPlayPath);
			Utils.printLog(TAG, "Uri.getpath =" + uri1.getPath());

			if (mPlayPath.endsWith("mp3") || mPlayPath.endsWith("wma")
					|| mPlayPath.endsWith("m4a") || mPlayPath.endsWith("wav")) {
				// context.sendBroadcast(new
				// Intent(CommonConst.CLOSE_AUDIO_PLAY));
				context.sendBroadcast(new Intent(CommonConst.CLOSE_VIDEO_PLAY));
				intent_common.setDataAndType(uri1, "audio/*");

			} else {
				context.sendBroadcast(new Intent(CommonConst.CLOSE_AUDIO_PLAY));
				// context.sendBroadcast(new
				// Intent(CommonConst.CLOSE_VIDEO_PLAY));
				intent_common.setDataAndType(uri1, "video/*");
			}

			Utils.printLog(TAG, "BroadCastFinished!");
			try {
				this_context.startActivity(intent_common);

			} catch (android.content.ActivityNotFoundException e) {
				e.printStackTrace();
				Log.d(TAG, "################ ActivityNotFoundException");
			}

		} else if (intent.getAction()
				.equals(MultiScreenConst.ACTION_LOCAL_STOP)
				|| intent.getAction().equals(
						MultiScreenConst.ACTION_PORTAL_STOP)||intent.getAction().equals(
								MultiScreenConst.ACTION_LOCAL_DEVICE_OFFLINE)) {
			Utils.printLog(TAG, "ACTION_STOP_VIDEOPLAYER");
			//context.sendBroadcast(new Intent(CommonConst.CLOSE_AUDIO_PLAY));
			//context.sendBroadcast(new Intent(CommonConst.CLOSE_VIDEO_PLAY));

		} else if (intent.getAction().equals(
				MultiScreenConst.ACTION_PORTAL_START)) {
			Utils.printLog(TAG, "ACTION_PORTAL_START");

			String mPlayPath = intent.getStringExtra(MultiScreenConst.PLAY_PATH);
			Log.d(TAG, "################ mPlayPath=" + mPlayPath);
			if (mPlayPath == null)
				return;
			Log.d(TAG, URLDecoder.decode(mPlayPath));
			context.sendBroadcast(new Intent(CommonConst.CLOSE_AUDIO_PLAY));
			
			
			intent_common = new Intent();
			intent_common.setType("application/vnd.tcl.playlist-video");
			intent_common.setAction(CommonConst.COMMON_ACTION);
			intent_common.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			intent_common.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			Uri uri1 = Uri.parse(mPlayPath);
			Utils.printLog(TAG, "Uri.getpath =" + uri1.getPath());

			ArrayList<MediaBean> playlist1 = new ArrayList<MediaBean>();
			MediaBean mb = new MediaBean();
			mb.mName = intent.getStringExtra(MultiScreenConst.PLAY_NAME);
			mb.mPath = mPlayPath;
			mb.mURLType = "VOD";
			playlist1.add(mb);
			intent_common.putExtra("IsMediaBean", true);
			intent_common.putParcelableArrayListExtra("playlist", playlist1);

			try {
				this_context.startActivity(intent_common);

			} catch (android.content.ActivityNotFoundException e) {
				e.printStackTrace();
				Log.d(TAG, "ActivityNotFoundException");
			}
		}
	}

}
