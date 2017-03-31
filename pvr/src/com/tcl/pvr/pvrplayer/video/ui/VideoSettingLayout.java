package com.tcl.pvr.pvrplayer.video.ui;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import com.tcl.deviceinfo.TDeviceInfo;
import com.tcl.pvr.pvrplayer.R;
import com.tcl.pvr.pvrplayer.utils.MediaPlayerApplication;
import com.tcl.pvr.pvrplayer.utils.MyToast;
import com.tcl.pvr.pvrplayer.utils.Utils;
import com.tcl.pvr.pvrplayer.video.contrl.IVideoPlayControlHandler;
import com.tcl.pvr.pvrplayer.video.contrl.VideoPlayerContrlConsts;
import com.tcl.tvmanager.TDtvPvrPlayerManager;
import com.tcl.tvmanager.TvManager;
import com.tcl.tvmanager.vo.DtvPvrMediaFileInfo;
import com.tcl.tvmanager.vo.EnTCLPictureMode;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class VideoSettingLayout extends Activity {

	private final String TAG = "VideoSettingLayout";
	private ListSettingAdapter2 mAdapter;
	private ListView pListView;
	private FrameLayout layoutDetail;
	private LinearLayout prameterLayout;
	private LinearLayout infodetaillayout;

	private ListView prameterList;
	private TextView textName;
	private TextView textDate;

	private TextView textDuration;
	private static final int CLOSEVIDEOSETTING = 19801;
	// private TextView textDescription;
	// private TextView textSourceFrom;

	public String[] playSubtitleNames;

	public String[] playAudioTrackNames;

	public String[] playModeNames;
	public String[] playPicMode;
	private int subNms, trackNms;
	private int mCurrSub, mCurrTrack;
	private int positionParaList;
	private ArrayList<Integer> mSubValues = new ArrayList<Integer>();
	private int mCurrentPlayMode;

	private Animation fadeInAnimation;
	private Animation fadeOutAnimation;

	private IVideoPlayControlHandler mVideoContrl = null;
	private String mVideoName, mVideoDate, mVideoTime;
	private int mVideoHeight, mVideoWidth;
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
	private TDtvPvrPlayerManager mDtvPvrPlayerManager;
	private boolean is3DEnabled = true;
	private String mPlayerSettingText[];
	private Context mContext;
	private BroadcastReceiver mVideoPlayerReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			Utils.printLog(TAG, "Received broadCast mVideoPlayerReceiver");
			VideoSettingLayout.this.finish();
		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.image_view_setting);

		mContext = this;
		mDtvPvrPlayerManager = TDtvPvrPlayerManager.getInstance(this);
		mVideoSettingPlayerHander.sendEmptyMessageDelayed(CLOSEVIDEOSETTING,
				Utils.MENU_TIMEOUT);
		pListView = (ListView) findViewById(R.id.settinglist);
		layoutDetail = (FrameLayout) findViewById(R.id.layoutdetail);
		mAdapter = new ListSettingAdapter2(this);
		pListView.setAdapter(mAdapter);
		prameterList = (ListView) findViewById(R.id.paramterlist);
		//prameterList.setSelector(R.drawable.list_item_selector);
		prameterLayout = (LinearLayout) findViewById(R.id.parameterlayout);
		infodetaillayout = (LinearLayout) findViewById(R.id.infodetaillayout);
		fadeInAnimation = AnimationUtils.loadAnimation(this,
				R.anim.slide_left_in);
		fadeOutAnimation = AnimationUtils.loadAnimation(this,
				R.anim.slide_right_out);
		textName = (TextView) findViewById(R.id.TextView01);
		textDate = (TextView) findViewById(R.id.TextView02);
		textDuration = (TextView) findViewById(R.id.TextView03);
		textName.setSelected(true);
		textDuration.setSelected(true);

		mVideoName = getIntent().getStringExtra("video_name");
		mVideoDate = getIntent().getStringExtra("video_date");
		mVideoTime = getIntent().getStringExtra("video_time");
		IntentFilter mFilter = new IntentFilter();
		mFilter.addAction("com.tcl.mediaplayer.exit.subtitle");
		registerReceiver(mVideoPlayerReceiver, mFilter);
		MediaPlayerApplication application = (MediaPlayerApplication) getApplication();
		mVideoContrl = application.getVideoCtrl();

		if (mVideoContrl != null) {

			mCurrentPlayMode = mVideoContrl.getPlayType();

			subNms = mVideoContrl.getSubtitleNms();
			mSubValues.clear();
			mSubValues.add(VideoPlayerContrlConsts.DEFAULT_SUBTITLE);
			for (int i = 1; i < subNms + 1; i++) {
				mSubValues.add(i);
			}
			mCurrSub = mVideoContrl.getCurrentSubtitleNms();

			trackNms = mVideoContrl.getAudioTrackNms();
			mCurrTrack = mVideoContrl.getCurrentAudioTrackNms();
			playSubtitleNames = new String[subNms + 1];
			playAudioTrackNames = new String[trackNms];
			playModeNames = new String[4];
			playModeNames[0] = getResources().getString(
					R.string.Video_Info_RepeatMode_Normal);
			playModeNames[1] = getResources().getString(
					R.string.Video_Info_RepeatMode_Re_one);
			playModeNames[2] = getResources().getString(
					R.string.Video_Info_RepeatMode_Random);
			playModeNames[3] = getResources().getString(
					R.string.Video_Info_RepeatMode_Re_all);

			playPicMode = new String[5];
			playPicMode[0] = getResources().getString(R.string.video_standard);
			playPicMode[1] = getResources().getString(R.string.video_dynamic);
			playPicMode[2] = getResources().getString(R.string.video_studio);
			playPicMode[3] = getResources().getString(R.string.video_movie);
			playPicMode[4] = getResources().getString(R.string.video_personal);
			for (int i = 0; i < subNms + 1; i++) {
				if (i == 0) {
					playSubtitleNames[i] = getResources().getString(
							R.string.default_sub);
				} else {
					playSubtitleNames[i] = getResources().getString(
							R.string.subtitle_begin)
							+ i;
				}

			}

			for (int i = 0; i < trackNms; i++) {

				playAudioTrackNames[i] = getResources().getString(
						R.string.audioTrack_begin)
						+ (i + 1);

			}

		}

		pListView.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View arg0, boolean arg1) {
				if(!pListView.hasFocus()){
					//pListView.setSelector(getResources().getDrawable(R.drawable.filelistitemfocustou));
					pListView.setSelector(R.drawable.list_item_selector);
				}else{
					//pListView.setSelector(getResources().getDrawable(R.drawable.filelistitemfocus));
					pListView.setSelector(R.drawable.list_item_selector);

				}
				
			}
		});
		pListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

				TextView mm = (TextView) arg1.findViewById(R.id.paraTag);
				String mListView = mm.getText().toString();
				if (mListView.equals(getResources().getString(
						R.string.turnThree))) {
					
				try {
					
					Intent intent = new Intent();
					intent.setAction("android.intent.action.tcl.show3dmenu");
					intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					intent.putExtra("start3DType", 1);
				
//					Intent mIntent = new Intent("com.tcl.settings.SHOW_WINDOW");
//					mIntent.setClassName("com.tcl.settings",
//							"com.tcl.settings.ShowWindowService");
//					mIntent.putExtra("Type", "3D");
//					mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//					mContext.startService(mIntent);
					VideoSettingLayout.this.startActivity(intent);
					finish();
					} catch (Exception e) {
						e.printStackTrace();
						new MyToast(VideoSettingLayout.this,
								VideoSettingLayout.this.getResources()
										.getString(R.string.NoFuntion)).show();
					}
				} else if (mListView.equals(getResources().getString(
						R.string.video_info))) {
					if (infodetaillayout.getVisibility() == View.GONE) {
						showContent(layoutDetail, infodetaillayout, null, true);
						refreshDetails();
					} else {
						showContent(layoutDetail, infodetaillayout, null, false);
					}
				} else {
					processKey(KeyEvent.KEYCODE_DPAD_RIGHT);
				}

			}

		});

		prameterList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				positionParaList = pListView.getSelectedItemPosition();

				TextView mm = (TextView) pListView.getChildAt(positionParaList)
						.findViewById(R.id.paraTag);
				String mListView = mm.getText().toString();
				if (mListView.equals(getResources().getString(R.string.image))) {
					if (arg2 == 0) {
						mDtvPvrPlayerManager
								.setPictureMode(EnTCLPictureMode.EN_TCL_STANDARD);
					} else if (arg2 == 1) {
						mDtvPvrPlayerManager
								.setPictureMode(EnTCLPictureMode.EN_TCL_VIVID);
					} else if (arg2 == 2) {
						mDtvPvrPlayerManager
								.setPictureMode(EnTCLPictureMode.EN_TCL_STUDIO);
					} else if (arg2 == 3) {
						mDtvPvrPlayerManager
								.setPictureMode(EnTCLPictureMode.EN_TCL_MILD);
					} else if (arg2 == 4) {
						mDtvPvrPlayerManager
								.setPictureMode(EnTCLPictureMode.EN_TCL_USER);
					}
				} else if (mListView.equals(getResources().getString(
						R.string.play_mode))) {
					if (mVideoContrl != null) {
						mVideoContrl.setPlayType(arg2);
						mCurrentPlayMode = arg2;
					}
				} else if (mListView.equals(getResources().getString(
						R.string.audioTrack))) {
					if (arg2 == mCurrTrack) {
						Utils.printLog(TAG, "mCurrTrack==" + mCurrTrack
								+ "arg2==" + arg2);
					} else {
						VideoSettingLayout.this.sendBroadcast(new Intent(
								"com.android.pvrvideo.refreshpause"));
					}
					if (mVideoContrl != null) {
						mCurrTrack = arg2;
						mVideoContrl.setAudioTrackNms((short) mCurrTrack);

					}
				}
				pListView.invalidateViews();
			}
		});

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		switch (keyCode) {
		case KeyEvent.KEYCODE_DPAD_LEFT:
		case KeyEvent.KEYCODE_DPAD_RIGHT:
			processKey(keyCode);
			break;
		case KeyEvent.KEYCODE_BACK:
			if (layoutDetail.getVisibility() == View.VISIBLE) {
				layoutDetail.setVisibility(View.GONE);
				if (layoutDetail.getChildAt(0) != null) {
					layoutDetail.getChildAt(0).setVisibility(View.GONE);
				}
				if (layoutDetail.getChildAt(1) != null) {
					layoutDetail.getChildAt(1).setVisibility(View.GONE);
				}
				pListView.setFocusable(true);
                pListView.requestFocus();
				return true;
			}
			// setData();
			break;
		case KeyEvent.KEYCODE_DPAD_DOWN:
			if (pListView.hasFocus()
					&& pListView.getSelectedItemPosition() == pListView
							.getCount() - 1) {
				pListView.setSelection(0);
			}
			break;
		case KeyEvent.KEYCODE_DPAD_UP:
			if (pListView != null && pListView.getSelectedItemPosition() == 0) {
				pListView.setSelection(pListView.getCount() - 1);
			}
			break;
		}
		return super.onKeyDown(keyCode, event);
	}

	public void processKey(int keyCode) {

		int listFocus = pListView.getSelectedItemPosition();
		boolean rightPressed = keyCode == KeyEvent.KEYCODE_DPAD_RIGHT;

		TextView mm = (TextView) pListView.getChildAt(listFocus).findViewById(
				R.id.paraTag);
		String mListView = mm.getText().toString();
		final String playMode = ((TextView) pListView.getChildAt(listFocus).findViewById(
				R.id.paraSelect)).getText().toString();
		if (mListView.equals(getResources().getString(R.string.image))) {
			if (rightPressed) {

				if (layoutDetail.getVisibility() == View.GONE) {
					showContent(layoutDetail, prameterLayout, playPicMode, true);
				} else {
					infodetaillayout.setVisibility(View.GONE);
					prameterLayout.setVisibility(View.VISIBLE);
					prameterList.setAdapter(new ListPrameterShow(this,
							playPicMode));
				}
			} else {
				if (layoutDetail.getVisibility() == View.VISIBLE) {
					showContent(layoutDetail, prameterLayout, null, false);
				}
			}
		} else if (mListView.equals(getResources()
				.getString(R.string.play_mode))) {
			if (rightPressed) {
				pListView.setFocusable(false);
				prameterList.setFocusable(true);
				//prameterList.requestFocus();
                if (null!=prameterList /*&&  -1==prameterList.getSelectedItemPosition() */){
                	prameterList.post(new Runnable() {
						@Override
						public void run() {
	                        int index = 0;
	                        for(int i=0;i<playModeNames.length;i++){
	                            if(playMode.equals(playModeNames[i])){
	                            	Log.d("liuwei03---","i=" + i);
	                                index = i;
	                                break;
	                            }
	                        }
							prameterList.requestFocus();
							prameterList.setSelection(index);
						}
					});
                }
                
				if (layoutDetail.getVisibility() == View.GONE) {
					showContent(layoutDetail, prameterLayout, playModeNames,
							true);
				} else {
					infodetaillayout.setVisibility(View.GONE);
					prameterLayout.setVisibility(View.VISIBLE);
					prameterList.setAdapter(new ListPrameterShow(this,
							playModeNames));
				}
			} else {
				pListView.setFocusable(true);
				pListView.requestFocus();
				prameterList.setFocusable(false);
				if (layoutDetail.getVisibility() == View.VISIBLE) {
					showContent(layoutDetail, prameterLayout, null, false);
				}
			}
		} else if (mListView.equals(getResources().getString(
				R.string.audioTrack))) {
			if (rightPressed) {
				if (layoutDetail.getVisibility() == View.GONE) {
					showContent(layoutDetail, prameterLayout,
							playAudioTrackNames, true);
				} else {
					infodetaillayout.setVisibility(View.GONE);
					prameterLayout.setVisibility(View.VISIBLE);
					prameterList.setAdapter(new ListPrameterShow(this,
							playAudioTrackNames));
				}

			} else {
				if (layoutDetail.getVisibility() == View.VISIBLE) {
					showContent(layoutDetail, prameterLayout, null, false);
				}
			}
		}
	}

	public class ListSettingAdapter2 extends BaseAdapter {
		public String[] playOrderSetNames;

		public String[] playAnimSetNames;

		private LayoutInflater mInflater;

		private boolean musicSelectorVisible = true;

		private final String TAG = "myAdapter";
		private ArrayList<String> mPlayerSettingText;

		public ListSettingAdapter2(Context context) {
			Log.i(TAG, "myAdapter entry");
			TvManager tvManager = TvManager.getInstance(context);
			String propertyAudioTrack = tvManager.getProperty().get("ro.sita.model.PvrPlayer.AudioTrack", "TRUE");
			mInflater = LayoutInflater.from(context);
			// 判断平台是否支持3D
			TDeviceInfo devinfo = TDeviceInfo.getInstance();
			int is3dState = devinfo.get3DStatus(devinfo.getProjectID());
			is3dState = 0;
			if (is3dState == 1) {
				mPlayerSettingText = new ArrayList<String>();
//				mPlayerSettingText.add(context.getResources().getString(
//						R.string.image));
				mPlayerSettingText.add(context.getResources().getString(
						R.string.play_mode));

//				if(propertyAudioTrack!=null&&propertyAudioTrack.equalsIgnoreCase("TRUE")){
//				  mPlayerSettingText.add(context.getResources().getString(R.string.audioTrack));
//				}
				
				mPlayerSettingText.add(context.getResources().getString(
						R.string.turnThree));
				mPlayerSettingText.add(context.getResources().getString(
						R.string.video_info));

			} else if (is3dState == 0) {
				mPlayerSettingText = new ArrayList<String>();
//				mPlayerSettingText.add(context.getResources().getString(
//						R.string.image));
				mPlayerSettingText.add(context.getResources().getString(
						R.string.play_mode));
//				if(propertyAudioTrack!=null&&propertyAudioTrack.equalsIgnoreCase("TRUE")){
//					  mPlayerSettingText.add(context.getResources().getString(R.string.audioTrack));
//					}
				mPlayerSettingText.add(context.getResources().getString(
						R.string.video_info));
			}
			Utils.printLog(TAG, "devinfo.get3DStatus; is3DEnabled ="
					+ is3dState);

			// playOrderSetNames =
			// getResources().getStringArray(R.array.play_mode_array);
			// playAnimSetNames =
			// getResources().getStringArray(R.array.anim_style_mode);
		}

		@Override
		public int getCount() {
			// return itemsTag.size();
			/* 因为菜单列表中去掉了音乐开关设置项，所以这里返回size－1 */
			return mPlayerSettingText.size();
		}

		@Override
		public Object getItem(int position) {
			return position;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		public void setMusicSelectorVisible(boolean visible) {
			musicSelectorVisible = visible;
		}

		@Override
		public View getView(int arg0, View arg1, ViewGroup arg2) {
			// TODO Auto-generated method stub
			ViewHolder holder;

			if (arg1 == null) {
				arg1 = mInflater.inflate(R.layout.setparameteritem, null);
				holder = new ViewHolder();
				holder.textTag = (TextView) arg1.findViewById(R.id.paraTag);
				holder.textSelect = (TextView) arg1.findViewById(R.id.paraSelect);

				arg1.setTag(holder);
			} else {
				holder = (ViewHolder) arg1.getTag();
			}
			/* set text and icon */
			/* 去掉了音乐开关设置，这里index为3时应该显示音乐目录即列表中index为4的内容 */

			holder.textTag.setText(mPlayerSettingText.get(arg0));
			holder.textSelect.setText(getItemSelectText(arg0));

			if (mPlayerSettingText.get(arg0).equals(
					getResources().getString(R.string.turnThree))
					|| mPlayerSettingText.get(arg0).equals(
							getResources().getString(R.string.video_info))) {
				refreshSelectorVisible(arg1, false);
			} else {
				refreshSelectorVisible(arg1, true);
			}

			// if(arg0 == 4 ){
			// holder.textTag.setTextColor(0xffffffff);
			// }
			// end modify by hepeng

			return arg1;
		}

		private String getItemSelectText(int position) {
			String text = null;
			if (mPlayerSettingText.get(position).equals(
					getResources().getString(R.string.image))) {
				text = "";
				EnTCLPictureMode mode = mDtvPvrPlayerManager.getPictureMode();
				Utils.printLog(TAG, "getPicturePreset current mode is " + mode);
				if (mode == EnTCLPictureMode.EN_TCL_STANDARD) {
					text = playPicMode[0];
				} else if (mode == EnTCLPictureMode.EN_TCL_VIVID) {
					text = playPicMode[1];
				} else if (mode == EnTCLPictureMode.EN_TCL_STUDIO) {
					text = playPicMode[2];
				} else if (mode == EnTCLPictureMode.EN_TCL_MILD) {
					text = playPicMode[3];
				} else if (mode == EnTCLPictureMode.EN_TCL_USER) {
					text = playPicMode[4];
				}
			} else if (mPlayerSettingText.get(position).equals(
					getResources().getString(R.string.play_mode))) {
				text = playModeNames[mCurrentPlayMode];
			} else if (mPlayerSettingText.get(position).equals(
					getResources().getString(R.string.turnThree))) {
				text = "";
			} else if (mPlayerSettingText.get(position).equals(
					getResources().getString(R.string.audioTrack))) {
				text = getResources().getString(R.string.audioTrack_begin)
						+ (mCurrTrack + 1);
			} else if (mPlayerSettingText.get(position).equals(
					getResources().getString(R.string.video_info))) {
				text = "";
			}
			return text;
		}

		private void refreshSelectorVisible(View view, boolean visible) {
			if (visible) {
				view.findViewById(R.id.right).setVisibility(View.VISIBLE);
			} else {
				view.findViewById(R.id.right).setVisibility(View.INVISIBLE);
			}
		}

		/* class ViewHolder */
		private class ViewHolder {
			TextView textTag;

			TextView textSelect;
		}
	}

	public class ListPrameterShow extends BaseAdapter {

		ViewHolder mholder;
		private LayoutInflater mInflater;
		private String[] contentS;

		public ListPrameterShow(Context context, String[] m) {
			mInflater = LayoutInflater.from(context);
			contentS = m;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			int size = contentS.length;
			return size;
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return contentS[arg0];
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return arg0;
		}

		private class ViewHolder {

			TextView showcontent;

		}

		@Override
		public View getView(int arg0, View arg1, ViewGroup arg2) {
			// TODO Auto-generated method stub

			mholder = new ViewHolder();
			String shownow = contentS[arg0];
			Log.d("=================", "shownow is" + contentS[arg0]);

			if (arg1 == null) {
				arg1 = mInflater.inflate(R.layout.au_setparameteritem, null);

				mholder.showcontent = (TextView) arg1
						.findViewById(R.id.showcontentitem);

				arg1.setTag(mholder);
			} else {
				mholder = (ViewHolder) arg1.getTag();
			}
			/* set text and icon */
			/* 去掉了音乐开关设置，这里index为3时应该显示音乐目录即列表中index为4的内容 */
			mholder.showcontent.setSelected(true);
			mholder.showcontent.setText(shownow);

			return arg1;
		}

	}

	private void showContent(FrameLayout Layer1, LinearLayout Layer2,
			String[] mCon, boolean VorG) {

		FrameLayout LayoutPrasent;
		LinearLayout LayoutSon;
		String[] nowSet;

		LayoutPrasent = Layer1;
		LayoutSon = Layer2;
		nowSet = mCon;
		if (nowSet == null) {
			if (VorG) {
				LayoutPrasent.startAnimation(fadeInAnimation);
				LayoutPrasent.setVisibility(View.VISIBLE);
				if (LayoutPrasent.getChildAt(0) != null) {
					LayoutPrasent.getChildAt(0).setVisibility(View.GONE);
				}
				if (LayoutPrasent.getChildAt(1) != null) {
					LayoutPrasent.getChildAt(1).setVisibility(View.GONE);
				}
				LayoutSon.setVisibility(View.VISIBLE);
			} else {
				LayoutPrasent.startAnimation(fadeOutAnimation);
				LayoutPrasent.setVisibility(View.GONE);
				LayoutSon.setVisibility(View.GONE);
			}
		} else {
			if (VorG) {
				LayoutPrasent.startAnimation(fadeInAnimation);
				LayoutPrasent.setVisibility(View.VISIBLE);

				if (LayoutPrasent.getChildAt(0) != null) {
					LayoutPrasent.getChildAt(0).setVisibility(View.GONE);
				}
				if (LayoutPrasent.getChildAt(1) != null) {
					LayoutPrasent.getChildAt(1).setVisibility(View.GONE);
				}
				LayoutSon.setVisibility(View.VISIBLE);
				prameterList.setAdapter(new ListPrameterShow(this, nowSet));
			} else {
				LayoutPrasent.startAnimation(fadeOutAnimation);
				LayoutPrasent.setVisibility(View.GONE);
				LayoutSon.setVisibility(View.GONE);
			}
		}
	}

	public void refreshDetails() {

		// if (mVideoContrl != null
		// && mVideoContrl.isMediaPlayerPrepared()) {
		// int[] mMediaInfo = new int [9];
		// mMediaInfo = mVideoContrl.getMediaInfo();
		// for(int i=0;i<=8;i++){
		// Utils.printLog(TAG, ""+mMediaInfo[i]);
		// }
		// if(mMediaInfo!=null){
		// mVideoWidth = mMediaInfo[5];
		// mVideoHeight = mMediaInfo[6];
		// int mVideoDuration = mVideoContrl.getDuration();
		// textDuration.setText(getString(R.string.video_duration)+Utils.getTimeShort(mVideoDuration));
		// if(mVideoWidth!=0&&mVideoHeight!=0){
		// textResolution.setText(getString(R.string.video_resolution)
		// +String.valueOf(mVideoWidth)+"*"+String.valueOf(mVideoHeight));
		//
		// }else{
		// textResolution.setText(getString(R.string.video_resolution)
		// +getResources().getString(R.string.audio_info_unknown));
		// }
		// int codec = mMediaInfo[2];
		// switch (codec) {
		// case VIDEOCODEC_MPEG1MPEG2:
		// mVideoCode = "MPEG1/MPEG2";
		// break;
		// case VIDEOCODEC_MPEG4DIVX:
		// mVideoCode = "MPEG4/DIVX";
		// break;
		// case VIDEOCODEC_MotionJPEG:
		// mVideoCode = "Motion JPEG";
		// break;
		// case VIDEOCODEC_DIVX3:
		// mVideoCode = "DIVX3";
		// break;
		// case VIDEOCODEC_RealVideo:
		// mVideoCode = "RealVideo";
		// break;
		// case VIDEOCODEC_H264:
		// mVideoCode = "H264";
		// break;
		// case VIDEOCODEC_H263:
		// mVideoCode = "H263";
		// break;
		// case VIDEOCODEC_VC1VC1:
		// mVideoCode = "VC1";
		// break;
		// case VIDEOCODEC_VP6:
		// mVideoCode = "VP6";
		// break;
		// case VIDEOCODEC_VP8:
		// mVideoCode = "VP8";
		// break;
		// case VIDEOCODEC_AVS:
		// mVideoCode = "AVS";
		// break;
		// case VIDEOCODEC_WMV7:
		// mVideoCode = "WMV7";
		// break;
		// case VIDEOCODEC_WMV8:
		// mVideoCode = "WMV8";
		// break;
		// case VIDEOCODEC_DV:
		// mVideoCode = "DV";
		// break;
		// default:
		// mVideoCode = getResources().getString(R.string.audio_info_unknown);
		// }
		// textVideoCode.setText(getString(R.string.video_resolution)+mVideoCode);
		//
		//
		// }
		textDuration.setText(getString(R.string.video_duration) + mVideoTime);
		textDate.setText(getString(R.string.video_date) + mVideoDate);
		textName.setText(getString(R.string.video_name) + mVideoName);
		// textDate.setText(getString(R.string.video_size) + mVideoSize);
		// textResolution.setText(getString(R.string.video_resolution) + ":  ");

		// }

		// textName.setText(getString(R.string.name_label) + ":  " +
		// NormalViewer.mImageInfo[1].photoName);
		// int size =
		// NormalViewer.allPhotosArray.get(NormalViewer.currentPosition).getSize();
		// float sizeK = size / 1024f;
		// sizeK = Math.round(sizeK * 100) / 100f;
		// textSize.setText(getString(R.string.size_label) + ":  " + sizeK +
		// "K");
		// textResolution.setText(getString(R.string.resolution_label) + ":  " +
		// NormalViewer.currImageView.getBitmapWidth() + "*"
		// + NormalViewer.currImageView.getBitmapHeight());
		// textCreatedTime.setText(getString(R.string.time_label) + ":  " +
		// NormalViewer.mImageInfo[1].photoCreatedAt);
		// String des = NormalViewer.mImageInfo[1].photoDescription;
		// if (des == null) {
		// des = "";
		// }
		// textDescription.setText(getString(R.string.path_label) + ":  " +
		// des);

	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		try {
			if (mVideoPlayerReceiver != null) {
				unregisterReceiver(mVideoPlayerReceiver);
			}
		} catch (Exception il) {

			il.printStackTrace();
		}
	}

	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		// TODO Auto-generated method stub
		mVideoSettingPlayerHander.removeMessages(CLOSEVIDEOSETTING);
		mVideoSettingPlayerHander.sendEmptyMessageDelayed(CLOSEVIDEOSETTING,
				Utils.MENU_TIMEOUT);
		return super.dispatchKeyEvent(event);
	}

	private Handler mVideoSettingPlayerHander = new Handler() {
		public void handleMessage(Message msg) {
			if (msg.what == CLOSEVIDEOSETTING) {
				finish();
			}
		}
	};
}
