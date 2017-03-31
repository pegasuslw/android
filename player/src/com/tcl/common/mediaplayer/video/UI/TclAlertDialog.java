/*package com.tcl.common.mediaplayer.video.UI;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.tclwidget.TCLSeekBar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager.BadTokenException;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mstar.tv.service.skin.PictureSkin;
import com.tcl.common.mediaplayer.R;
import com.tcl.common.mediaplayer.utils.Utils;
import com.tvos.common.PictureManager;
import com.tvos.common.TvManager;
import com.tvos.common.exception.TvCommonException;

public class TclAlertDialog extends Dialog {
	private final String TAG = "TclAlertDialog";
	private Context mContext;
	private View view;
	private LinearLayout sub_Lay, track_Lay;
	private ProgressBar progressBar;
	private ProgressBar progressBarWhite;
	private TextView naturalLight;
	private TextView progressText;
	public static final int PROGRESSBAR_MAX = 100;
	public int CurrentIndex = 0;
	private PictureManager mPic;
	private String[] mStateText;
	private boolean mNaturalValue;
	private static final int DISMISS_ALERT_DIALOG = 1;
	private Drawable drawableWhite;
	private Drawable drawableEnable;
	private TextView backLightText;

	public TclAlertDialog(Context context, PictureManager mPictureSkin) {
		super(context, R.style.CustomDialog);
		Log.d("TclAlertDialog", "TclAlertDialog(Context context)");
		mContext = context;
		mPic = mPictureSkin;
		mNaturalValue = VideoPlayerActivity.getBooleanISNatrueLightON(mContext);
		mStateText = new String[] {
				mContext.getString(R.string.natural_light_open),
				mContext.getString(R.string.natural_light_close) };
		LayoutInflater inflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		view = inflater.inflate(R.layout.background_image, null);
		sub_Lay = (LinearLayout) view.findViewById(R.id.subtitle);
		track_Lay = (LinearLayout) view.findViewById(R.id.audioTrack);
		progressBar = (ProgressBar) view.findViewById(R.id.seekbar);
		progressBarWhite = (ProgressBar) view.findViewById(R.id.whiteseekbar);
		progressText = (TextView) view.findViewById(R.id.progresstext);
		naturalLight = (TextView) view.findViewById(R.id.naturallighttext);
		backLightText = (TextView) view.findViewById(R.id.backlightname);
		drawableWhite = progressBarWhite.getProgressDrawable();
		drawableEnable = progressBar.getProgressDrawable();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(view);
		mImageSettingHander
				.sendEmptyMessageDelayed(DISMISS_ALERT_DIALOG, 10000);
		if (mNaturalValue) {
			naturalLight.setText(mStateText[0]);
			sub_Lay.setEnabled(false);
			sub_Lay.setFocusable(false);
			backLightText.setTextColor(mContext.getResources().getColor(
					R.color.text_color_disabled));
			progressText.setTextColor(mContext.getResources().getColor(
					R.color.text_color_disabled));
			progressBar.setProgressDrawable(drawableWhite);
			naturalLight.requestFocus();
		} else {
			naturalLight.setText(mStateText[1]);
			sub_Lay.requestFocus();
		}

		progressBar.setMax(PROGRESSBAR_MAX);
		// try {
		// CurrentIndex = mPic.getBacklight();
		// } catch (TvCommonException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		CurrentIndex = VideoPlayerActivity.getSavedBackLight(mContext);

		progressBar.setProgress(CurrentIndex);

		// progressBar.setProgressDrawable(d)
		progressText.setText("" + CurrentIndex);
		Log.d(TAG, "TclAlertDialog===>onCreate()");

		super.onCreate(savedInstanceState);
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		// if(naturalLight.getText().equals(mStateText[0])){
		// VideoPlayerActivity.setBooleanNatrueLight(mContext,true);
		// }else{
		// VideoPlayerActivity.setBooleanNatrueLight(mContext,false);
		// }
		super.onStop();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		mImageSettingHander.removeMessages(DISMISS_ALERT_DIALOG);
		mImageSettingHander
				.sendEmptyMessageDelayed(DISMISS_ALERT_DIALOG, 10000);

		if (event.getKeyCode() == KeyEvent.KEYCODE_DPAD_RIGHT) {

			if (sub_Lay.hasFocus() && sub_Lay.isEnabled()) {// 此时选中的是字幕菜单；
				if (CurrentIndex < PROGRESSBAR_MAX) {
					CurrentIndex++;
					progressBar.setProgress(CurrentIndex);
					progressText.setText("" + CurrentIndex);
					try {
						mPic.setBacklight(CurrentIndex);
						Utils.writeStaticBacklight(CurrentIndex);
						VideoPlayerActivity.saveBackLight(mContext,
								CurrentIndex);
					} catch (TvCommonException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}

			} else if (track_Lay.hasFocus()) {

				if (naturalLight.getText().equals(mStateText[0])) {

					try {
						boolean isSetting = TvManager
								.setTvosInterfaceCommand("DBC_OFF");

						Log.i("Naturelight---- ", "DBC_OFF");
						if (isSetting) {
							VideoPlayerActivity.setBooleanNatrueLight(mContext,
									false);
							mPic.setBacklight(CurrentIndex);
							Utils.writeStaticBacklight(CurrentIndex);
							naturalLight.setText(mStateText[1]);
							backLightText.setTextColor(mContext.getResources()
									.getColor(R.color.text_color_dark));
							progressText.setTextColor(mContext.getResources()
									.getColor(R.color.text_color_dark));
							progressBar.setEnabled(true);
							sub_Lay.setEnabled(true);
							sub_Lay.setFocusable(true);
						}
					} catch (TvCommonException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				} else {

					try {
						boolean isSetting = TvManager
								.setTvosInterfaceCommand("DBC_ON");

						if (isSetting) {
							VideoPlayerActivity.setBooleanNatrueLight(mContext,
									true);
							naturalLight.setText(mStateText[0]);
							progressBar.setEnabled(false);
							sub_Lay.setEnabled(false);
							sub_Lay.setFocusable(false);
							backLightText.setTextColor(mContext.getResources()
									.getColor(R.color.text_color_disabled));
							progressText.setTextColor(mContext.getResources()
									.getColor(R.color.text_color_disabled));
						}
						Log.i("Naturelight---- ", "DBC_ON");
					} catch (TvCommonException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			}

		} else if (event.getKeyCode() == KeyEvent.KEYCODE_DPAD_LEFT) {
			if (sub_Lay.hasFocus() && sub_Lay.isEnabled()) {

				if (CurrentIndex > 0) {
					CurrentIndex--;
					progressBar.setProgress(CurrentIndex);
					progressText.setText("" + CurrentIndex);
					try {
						mPic.setBacklight(CurrentIndex);
						Utils.writeStaticBacklight(CurrentIndex);
						VideoPlayerActivity.saveBackLight(mContext,
								CurrentIndex);
					} catch (TvCommonException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			} else if (track_Lay.hasFocus()) {

				if (naturalLight.getText().equals(mStateText[0])) {
					try {

						boolean isSetting = TvManager
								.setTvosInterfaceCommand("DBC_OFF");
						if (isSetting) {
							VideoPlayerActivity.setBooleanNatrueLight(mContext,
									false);
							mPic.setBacklight(CurrentIndex);
							Utils.writeStaticBacklight(CurrentIndex);
							naturalLight.setText(mStateText[1]);
							progressBar.setEnabled(true);
							sub_Lay.setEnabled(true);
							sub_Lay.setFocusable(true);
							backLightText.setTextColor(mContext.getResources()
									.getColor(R.color.text_color_dark));
							progressText.setTextColor(mContext.getResources()
									.getColor(R.color.text_color_dark));
						}
						Log.i("Naturelight---- ", "DBC_OFF");
					} catch (TvCommonException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				} else {
					try {
						boolean isSetting = TvManager
								.setTvosInterfaceCommand("DBC_ON");
						if (isSetting) {
							VideoPlayerActivity.setBooleanNatrueLight(mContext,
									true);
							naturalLight.setText(mStateText[0]);
							progressBar.setEnabled(false);
							sub_Lay.setEnabled(false);
							sub_Lay.setFocusable(false);
							backLightText.setTextColor(mContext.getResources()
									.getColor(R.color.text_color_disabled));
							progressText.setTextColor(mContext.getResources()
									.getColor(R.color.text_color_disabled));
						}
						Log.i("Naturelight---- ", "DBC_ON");
					} catch (TvCommonException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			}

		} else if (event.getKeyCode() == KeyEvent.KEYCODE_DPAD_UP) {
			if (track_Lay.hasFocus() && sub_Lay.isEnabled()) {
				progressBar.setProgressDrawable(drawableEnable);
			}

		} else if (event.getKeyCode() == KeyEvent.KEYCODE_DPAD_DOWN) {
			if (sub_Lay.hasFocus()) {
				progressBar.setProgressDrawable(drawableWhite);
			}
		}
		return super.onKeyDown(keyCode, event);
	}

	private Handler mImageSettingHander = new Handler() {
		public void handleMessage(Message msg) {
			if (msg.what == DISMISS_ALERT_DIALOG) {
				try {
					dismiss();
				} catch (Exception e) {
					Log.i(TAG, "showWait BadTokenException");
				}

			}
		}
	};

}
*/