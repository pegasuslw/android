package com.tcl.common.mediaplayer.video.UI;
import java.io.File;
import java.io.FileInputStream;
import java.text.DecimalFormat;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.Metadata;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

import com.tcl.common.mediaplayer.R;
import com.tcl.common.mediaplayer.utils.MediaPlayerApplication;
import com.tcl.common.mediaplayer.utils.Utils;
import com.tcl.common.mediaplayer.video.contrl.IVideoPlayControlHandler;

public class VideoInfoDialogActivity extends Activity{

	private final String TAG = "VideoInfoDialogActivity";
    private TextView videoName,videoCode,videoRate,VideoSize,videoResolution;
	private IVideoPlayControlHandler mVideoContrl = null;
	private String mVideoName,mVideoRate,mVideoCode,mVideoSize;
	private int mVideoHeight,mVideoWidth;
	private static final int DISMISS_VIDEOINFO = 8290;
	private static final int VIDEOCODEC_MPEG1MPEG2 = 1;
	private static final int VIDEOCODEC_MPEG4DIVX = 2;
	private static final int VIDEOCODEC_MotionJPEG = 3;
	private static final int VIDEOCODEC_DIVX3 = 4;
	private static final int VIDEOCODEC_RealVideo = 5;
	private static final int VIDEOCODEC_H264 = 6;
	private static final int VIDEOCODEC_H263 = 7;
	private static final int VIDEOCODEC_VC1VC1 = 8;
	private static final int VIDEOCODEC_VP6 = 9;
	private static final int VIDEOCODEC_VP8 = 10;
	private static final int VIDEOCODEC_AVS = 11;
	private static final int VIDEOCODEC_WMV7 = 12;
	private static final int VIDEOCODEC_WMV8 = 13;
	private static final int VIDEOCODEC_DV = 14;
	private static final int VIDEOCODEC_FLV = 15;
	private static final int VIDEOCODEC_MVC = 16;
	private static final int VIDEOCODEC_HEVC = 17;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.videoinfo);
		videoName =(TextView)findViewById(R.id.info_videoname);	
		videoName.setSelected(true);
		videoCode =(TextView)findViewById(R.id.info_videocode);	
		videoRate =(TextView)findViewById(R.id.info_videorate);	
		VideoSize =(TextView)findViewById(R.id.info_videosize);	
		VideoSize.setSelected(true);
		videoResolution =(TextView)findViewById(R.id.info_videoresolution);	
		mVideoName = getIntent().getStringExtra("video_name");
		mVideoSize = getIntent().getStringExtra("video_size");
		
		
		   IntentFilter mFilter = new IntentFilter();
	       mFilter.addAction("com.tcl.mediaplayer.exit.backlight");
	       registerReceiver(mVideoPlayerReceiver, mFilter);
	       MediaPlayerApplication application = (MediaPlayerApplication)getApplication();
			mVideoContrl = application.getVideoCtrl();	
		
		
	
		super.onCreate(savedInstanceState);
	}
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		if (mVideoContrl != null
				&& mVideoContrl.isMediaPlayerPrepared()) {
			int[] mMediaInfo  =  new int [9];
			mMediaInfo	= mVideoContrl.getMediaInfo();	
			for(int i=0;i<=8;i++){
				Utils.printLog(TAG, ""+mMediaInfo[i]);
			}
			if(mVideoName!=null){
				videoName.setText(mVideoName);
			}
			if(mVideoSize!=null&&mVideoSize!=""){
				VideoSize.setText(mVideoSize);
			}
			
			if(mMediaInfo!=null){
				 mVideoWidth = mMediaInfo[5];
				 mVideoHeight = mMediaInfo[6];
				 if(mVideoWidth!=0&&mVideoHeight!=0){
					 videoResolution.setText(String.valueOf(mVideoWidth)+"*"+String.valueOf(mVideoHeight));
				 }else{
					 videoResolution.setText(getResources().getString(R.string.audio_info_unknown));
				 }
			    int codec = mMediaInfo[2];
			 	switch (codec) {
				    case VIDEOCODEC_MPEG1MPEG2:
					     mVideoCode = "MPEG1/MPEG2";
					     break;
				    case VIDEOCODEC_MPEG4DIVX:
					     mVideoCode = "MPEG4/DIVX";
					     break;
				    case VIDEOCODEC_MotionJPEG:
					     mVideoCode = "Motion JPEG";
					     break;
				    case VIDEOCODEC_DIVX3:
					     mVideoCode = "DIVX3";
					     break;
				    case VIDEOCODEC_RealVideo:
					     mVideoCode = "RealVideo";
					     break;
				    case VIDEOCODEC_H264:
					     mVideoCode = "H264";
					     break;
				    case VIDEOCODEC_H263:
					     mVideoCode = "H263";
					     break;
				    case VIDEOCODEC_VC1VC1:
					     mVideoCode = "VC1";
					     break;
				    case VIDEOCODEC_VP6:
					     mVideoCode = "VP6";
					     break;
				    case VIDEOCODEC_VP8:
					     mVideoCode = "VP8";
					     break;
				    case VIDEOCODEC_AVS:
					     mVideoCode = "AVS";
					     break;
				    case VIDEOCODEC_WMV7:
					     mVideoCode = "WMV7";
					     break;
				    case VIDEOCODEC_WMV8:
					     mVideoCode = "WMV8";
					     break;
				    case VIDEOCODEC_DV:
					     mVideoCode = "DV";
					     break;
				    case VIDEOCODEC_FLV:
					     mVideoCode = "FLV";
					     break;    
					case VIDEOCODEC_MVC:
						 mVideoCode = "MVC";
						 break;
				    case VIDEOCODEC_HEVC:
					     mVideoCode = "HEVC";
					     break;
			        default:
			        	mVideoCode = getResources().getString(R.string.audio_info_unknown);
			 	}
			 	videoCode.setText(mVideoCode);
			 	
//			 	int bitrate = mMediaInfo[8];
//			 	String mbitRate = getVideoByte(bitrate);
//			 	if(mbitRate!=null&&mbitRate!=""){
//			 		videoRate.setText(mbitRate);
//			 	}else{
//			 		videoRate.setText(getResources().getString(R.string.audio_info_unknown));
//			 	}
			}
			
//			if(mVideoCode!=null){
//				videoCode.setText(mVideoCode);
//			}
//			if(mVideoRate!="-1"&&mVideoRate!=null&&mVideoRate!=""){
//				int mbite = Integer.parseInt(mVideoRate)/1000;
//				if(mbite == 0){
//					videoRate.setText(getResources().getString(R.string.audio_info_unknown));
//				}else{
//					videoRate.setText(String.valueOf(mbite)+"Kbps");
//				}
//			
//			}else{
//				videoRate.setText(getResources().getString(R.string.audio_info_unknown));
//			}
//			if(mVideoDuration>0){
//				
//			    videoDuration.setText(Utils.getTimeShort(mVideoDuration));
//			}
//

			
		     mVideoInfoHander.sendEmptyMessageDelayed(DISMISS_VIDEOINFO, 5000);
			
		}else{
			finish();
		}
		super.onStart();
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();

		finish();
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
	private BroadcastReceiver mVideoPlayerReceiver = new BroadcastReceiver(){

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			Utils.printLog(TAG, "Received broadCast mVideoPlayerReceiver");
			finish();
		}
		
	};
	
	private Handler mVideoInfoHander = new Handler(){
		public void handleMessage(Message msg) {
			if (msg.what == DISMISS_VIDEOINFO) {
				finish();
			}
		}
		
	};
	
	
	private String getVideoByte(int bits) {
		long fileS = bits;
		String fileBits = "";
		try {
			
			DecimalFormat df = new DecimalFormat("#.00");
			if (fileS < 1024&&fileS > 0) {
				fileBits = df.format((double)fileS) + "bps";
			} else if (fileS < 1048576) {
				fileBits = df.format((double) fileS / 1024) + "Kbps";
			} else if (fileS < 1073741824) {
				fileBits = df.format((double) fileS / 1048576) + "Mbps";
			} else {
				fileBits = df.format((double) fileS / 1073741824) + "Gbps";
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return fileBits;

	}
	
	
}
