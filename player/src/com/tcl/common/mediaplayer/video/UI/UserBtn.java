package com.tcl.common.mediaplayer.video.UI;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tcl.videoplayer.R;

public class UserBtn extends LinearLayout{
	private static final String TAG = "SelectItem";
	private ImageView img;
	private TextView text;
	private int id;
	private Context mContext;
	
	public UserBtn(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		mContext = context;
		LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		LinearLayout layout = (LinearLayout) layoutInflater.inflate(R.layout.btn, this);
		img = (ImageView) layout.findViewById(R.id.img);
		text = (TextView) layout.findViewById(R.id.text);
	}
	
	public void setText(int pmNormal){
		text.setText(pmNormal);
	}
	
	public void setSelected(){
		//text.setTextColor(mContext.getResources().getColor(R.color.text_focus));
		img.setVisibility(View.VISIBLE);
	}
	
	public void clearSelected(){
		img.setVisibility(View.INVISIBLE);
	}

	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		// TODO Auto-generated method stub
		
		int keyCode = event.getKeyCode();
		int keyAction = event.getAction();
		Log.d(TAG, "dispatchKeyEvent keyCode = " + keyCode + " , keyAction = "+ keyAction);
		if(keyAction == KeyEvent.ACTION_DOWN){
			switch(keyCode){
			case KeyEvent.KEYCODE_DPAD_CENTER:
			case KeyEvent.KEYCODE_ENTER:
				setSelected();			
				break;
			default:
				break;
			}
		}
		
		return super.dispatchKeyEvent(event);
	}
}
