package com.tcl.common.mediaplayer.video.UI;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.iflytek.xiri.Feedback;
import com.iflytek.xiri.scene.ISceneListener;
import com.iflytek.xiri.scene.Scene;

public class XiriSceneActivity extends Activity implements ISceneListener{
	private static final String TAG = "XiriSceneActivity";
	protected Scene mScene;
	protected Feedback mFeedback;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mScene = new Scene(this);
		mFeedback = new Feedback(this);
		Log.i(TAG,"XiriSceneActivity onCreate()");
	}

	@Override
	protected void onStart() {
		super.onStart();
		mScene.init(this);
		Log.i(TAG,"mScene.init(this);");
	}

	@Override
	protected void onStop() {
		super.onStop();
		mScene.release();
		Log.i(TAG,"mScene.release();");
	}

	@Override
	public void onExecute(Intent arg0) {
		Log.i(TAG,"onExecute");
	}

	@Override
	public String onQuery() {
		Log.i(TAG,"onQuery()");
		return null;
	}

}
