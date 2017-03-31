package com.tcl.common.mediaplayer.video.UI;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
//import android.content.DialogInterface.OnClickListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.AdapterView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tcl.common.mediaplayer.aidl.CommonConst;
import com.tcl.common.mediaplayer.utils.MediaPlayerApplication;
import com.tcl.common.mediaplayer.utils.MyToast;
import com.tcl.common.mediaplayer.utils.SettingItemData;
import com.tcl.common.mediaplayer.utils.SettingMenuAdapter;
import com.tcl.common.mediaplayer.utils.Utils;
import com.tcl.common.mediaplayer.video.contrl.IVideoPlayControlHandler;
import com.tcl.common.mediaplayer.video.contrl.VideoPlayerContrlConsts;
import com.tcl.deviceinfo.TDeviceInfo;
import com.tcl.tvmanager.TTvPictureManager;
import com.tcl.tvmanager.TTvSoundManager;
import com.tcl.tvmanager.TvManager;
import com.tcl.tvmanager.vo.EnTCLAspectRatio;
import com.tcl.tvmanager.vo.EnTCLPictureMode;
import com.tcl.tvmanager.vo.EnTCLSoundMode;
import com.tcl.videoplayer.R;
import com.tcl.videoplayer.focus.FocusView;
import com.tcl.videoplayer.focus.LayoutBtnFocusAnimUtil;
import com.tcl.videoplayer.focus.ListViewAnimation;
import com.tcl.videoplayer.focus.ListViewFocusAnimUtil;

public class VideoMenuSettingDialog extends Dialog {
	private String TAG ="VideoSettingMenuDialog"; 		
	private Context mContext;
	private ArrayList<SettingItemData> menulist;
	private ListView mMenuListView;
	private ListView mSettingItemList;
	private RelativeLayout wholeMenuLayout;
	private RelativeLayout settingMenuLayout; 	
	private LinearLayout settingItemBtnLayout;
	private int[] btnIds = new int[]{R.id.item1,R.id.item2,R.id.item3,R.id.item4,R.id.item5};
	private UserBtn[] btn = new UserBtn[5];
	
	private String itemsName[];
	private String itemsValues[];
	private ArrayList<String> itemNames;
	private IVideoPlayControlHandler mVideoContrl = null;	
	private int subNms, trackNms,totalChapter;
	
	private int curPicMode = VideoPlayerUIConst.PM_STANDARD;
	private int curSoundMode = VideoPlayerUIConst.SM_STANDARD;
	private int curPlayType;
	private int curPicScale = VideoPlayerUIConst.PS_16x9;
	private int mCurrSub, mCurrTrack,mCurChapter;
	
	private String curPicModeText = "";
	private String curSoundModeText = "";
	private String curPlayTypeText = "";
	private String curPicScaleText = "";
	private String curSubText ="";
	private String curAudioTrackText ="";
	private String curChapterText = "";
	private int[] curSettingItem = new int[11];
	
	private ArrayList<Integer> mSubValues = new ArrayList<Integer>();
	private String mTheLastPath;
	private MediaPlayerApplication mApplication;
	private static final int TYPE_PIC_EFFECT = 0;
	private static final int TYPE_VOICE_EFFECT = 1;
	private static final int TYPE_CYCLE_MODE = 2;
	private static final int TYPE_PICTURE_SCALE = 3;
	private static final int TYPE_SAND = 4;
	private static final int TYPE_SUBTITILE = 5;
	private static final int TYPE_AUDIO_TRACK =6;
	private static final int TYPE_VIDEO_CHAPTER = 7;
	private static final int TYPE_SHARE = 8;
	private static final int TYPE_INFO = 9;
	private static final int TYPE_SETTING = 10;
	
	private TTvSoundManager soundManager;
	private TTvPictureManager mPicmanager;	
	
	private int settingIetmSelectedStatus = 0;
	private SettingMenuAdapter changeAdapter;		
	private SettingItemAdapter mSettingItemAdapter;
	private LayoutInflater mInflater;
	
	private static boolean isRunFocusAnim = true;  //?20161201
	private boolean isDLNA = false;
	private  boolean isNeedShare = true;                   //DLNA不需要分享(4K视频不需要分享)
	private  boolean isNeedInfo = true;  
	private  boolean isNeedPlayEffect = true;
	private  boolean isNeedPicEffect = false;
	private  boolean isNeedVoiceEffect = false;
	private  boolean isNeedPicScale = false;
	private  boolean isNeed3D = true;
	private  boolean isNeedSeting = true;
	private  boolean isNeedChapter = true;
	
	private LinkedHashMap<Integer, String> mMenuNameList = new LinkedHashMap<Integer, String>() ;
	private ArrayList<Integer> mMenuTypeList = new ArrayList<Integer>() ;
	
	private ImageView menuFocusImage;//用于ListView平移动画
	private ImageView itemFocusImage;//用于ListView平移动画
	private ImageView btnFocusImage;//btn平移焦点动画
	private ImageView btnBgImage;//btn背景
	// list item animation
	private Animation listItemAnimation;
	private float aniTime = 0.3f;
	private LayoutAnimationController mLayoutAnimationController;
	private ListViewFocusAnimUtil mListViewFocusAnimUtil;                   //一级列表
	private ListViewFocusAnimUtil mListViewItemFocusAnimUtil;           //二级列表  -- 音效，循环模式
	private LayoutBtnFocusAnimUtil  mLayoutBtnFocusAnimUtil;           //二级按钮
	private ListViewAnimation mListViewAnimation;                                      //二级列表 --字幕，音轨
	private FocusView mFocusView;
	private float mFocusViewY = 10.0f;
	public int keyAction = -1;
	private String clienttype = "";
	private String[] splitArrayStrings;
	private boolean isHkOrCn = true;              //国内外，启动3d和系统设置的方法不一样
	public VideoMenuSettingDialog(Context context,MediaPlayerApplication app) {
		super(context,R.style.StatusbarToast);
		// TODO Auto-generated constructor stub
		mContext = context;
		mApplication = app;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);		
		Log.d(TAG,"on create ====");
		setContentView(R.layout.settingmenu);
		
		mVideoContrl = mApplication.getVideoCtrl();		
		
		mMenuListView = (ListView)findViewById(R.id.menulist);
		mSettingItemList = (ListView)findViewById(R.id.list_setting);
		
		wholeMenuLayout  = (RelativeLayout)findViewById(R.id.lyofsettingmenu);
		settingMenuLayout = (RelativeLayout)findViewById(R.id.setting_item_layout);	
		settingMenuLayout.setVisibility(View.INVISIBLE);
		settingItemBtnLayout = (LinearLayout)findViewById(R.id.settingitemlayout);
		settingItemBtnLayout.setVisibility(View.INVISIBLE);
		btnBgImage =(ImageView)findViewById(R.id.btn_bg);
		btnBgImage.setVisibility(View.INVISIBLE);
		
		mFocusView =(FocusView)findViewById(R.id.listview_focus_image);
		
		//??? 20161201
		for(int i = 0; i <btnIds.length ; i++){
			btn[i] = (UserBtn)findViewById(btnIds[i]);
			btn[i].setOnClickListener(buttonclick);
			if(isRunFocusAnim){
			//	btn[i].setBackgroundResource(R.drawable.setting_item_bg_normal);
			}else{
				btn[i].setBackgroundResource(R.drawable.settingitem_bg_selector);
			}
		}
		
		menulist = new ArrayList<SettingItemData>();
		soundManager = TTvSoundManager.getInstance(mContext);
		mPicmanager = TTvPictureManager.getInstance(mContext);

		Window dialogWindow = this.getWindow();        
		WindowManager.LayoutParams lp = dialogWindow.getAttributes();          
		lp.windowAnimations = R.style.settingmenuanimation;
		dialogWindow.setAttributes(lp);     	
		
		IntentFilter mFilter = new IntentFilter();
	    mFilter.addAction("com.tcl.mediaplayer.exit.subtitle");
	    mFilter.addAction(CommonConst.VIDEO_SETTING_AUDIOTRACK);
	    mContext.registerReceiver(mVideoPlayerReceiver, mFilter);
	    
		mInflater = LayoutInflater.from(mContext);
	    itemNames = new ArrayList<String>();
	    
		menuFocusImage = (ImageView)findViewById(R.id.list_focus_bg);
		itemFocusImage = (ImageView)findViewById(R.id.list_item_focus_bg);
		btnFocusImage =(ImageView)findViewById(R.id.btn_focus);

		/*listItemAnimation=AnimationUtils.loadAnimation(mContext, R.anim.menu_list_item_anim_from_left);		
		mLayoutAnimationController =new LayoutAnimationController(listItemAnimation);
		mLayoutAnimationController.setOrder(LayoutAnimationController.ORDER_NORMAL);
		mLayoutAnimationController.setDelay(aniTime);*/
		
	    if(isRunFocusAnim){
			mMenuListView.setSelector(new ColorDrawable(Color.TRANSPARENT)); 
			mSettingItemList.setSelector(new ColorDrawable(Color.TRANSPARENT)); 	
			
			mListViewFocusAnimUtil = new ListViewFocusAnimUtil(menuFocusImage, mMenuListView);
			mListViewItemFocusAnimUtil = new ListViewFocusAnimUtil(itemFocusImage, mSettingItemList);
			mLayoutBtnFocusAnimUtil = new LayoutBtnFocusAnimUtil(btnFocusImage, settingItemBtnLayout,mContext);
			
			mListViewAnimation = new ListViewAnimation(mContext, mHand, mSettingItemList);		
			mLayoutBtnFocusAnimUtil.init();		
			btnFocusImage.setVisibility(View.INVISIBLE);
			mFocusView.setVisibility(View.INVISIBLE);
			itemFocusImage.setVisibility(View.INVISIBLE);
	    }else{
	    	mMenuListView.setSelector(R.drawable.list_item_selector);
			mSettingItemList.setSelector(R.drawable.list_item_selector);
			menuFocusImage.setVisibility(View.INVISIBLE);
		    mFocusView.setVisibility(View.INVISIBLE);
			itemFocusImage.setVisibility(View.INVISIBLE);
			mFocusView.setVisibility(View.INVISIBLE);
			btnFocusImage.setVisibility(View.INVISIBLE);
	    }
           if(mVideoContrl != null){
			
        	   totalChapter = mVideoContrl.getChapters();
		  } 
		isNeedShare = Utils.isShowAppConfigerItem(mContext, CommonConst.CONFIG_MEDIA_SHARE);
		mSettingItemList.setOnScrollListener(new OnScrollListener() {
			
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// TODO Auto-generated method stub
				if (scrollState == SCROLL_STATE_IDLE   && isRunFocusAnim && mListViewAnimation!= null) {
					//长按，等按键起来后再去校准焦点框，而不是没个每次校准，防止滚动列表时候，一顿一顿的
					Log.v(TAG, "scrollState == SCROLL_STATE_IDLE ");
					if(isLongOnclick){
						Log.v(TAG, "isLongOnclick keyAction:"+keyAction);
						if(keyAction == KeyEvent.ACTION_UP){
							Log.i(TAG, "keyAction == KeyEvent.ACTION_UP");
							mListViewAnimation.correctListPosition();
						}
					}
					//短按就直接校准
					else{
						mListViewAnimation.correctListPosition();
					}
					
				}
			}
			
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				// TODO Auto-generated method stub
				
			}
		});
		
		TDeviceInfo devinfo = TDeviceInfo.getInstance();
		clienttype = devinfo.getClientType(devinfo.getProjectID());
		Log.v(TAG, "clienttype =" + clienttype);
		if (clienttype != null) {
			splitArrayStrings = clienttype.split("-");
			if (splitArrayStrings[1] == null || splitArrayStrings[1].equals("CN") || splitArrayStrings[1].equals("HK")) {
				isHkOrCn = true;
			}else{
				isHkOrCn = false;
			}
		}
	}	
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
		super.show();
		
		wholeMenuLayout.setVisibility(View.VISIBLE);
		Log.v(TAG," show");

		menulist.clear();
		setListContent();		
		getItemsContent();
		setdata();		
		changeAdapter = new SettingMenuAdapter(mContext, menulist);
		mMenuListView.setAdapter(changeAdapter);
		mMenuListView.setOnItemSelectedListener(itemselect);
		mMenuListView.setOnItemClickListener(itemClick);

		
		mSettingItemAdapter  = new SettingItemAdapter();
		mSettingItemList.setAdapter(mSettingItemAdapter);
		mSettingItemList.setOnItemClickListener(onSecondItemClick);
		
		mMenuListView.clearAnimation();
	/*	if( mLayoutAnimationController != null ){
			mMenuListView.setLayoutAnimation(mLayoutAnimationController);
		}*/
		
		if(isRunFocusAnim && mListViewFocusAnimUtil!= null){
			//menuFocusImage.setBackgroundResource(R.drawable.setting_item_focus);
			menuFocusImage.setBackgroundResource(R.drawable.listview_item_selected);
			mListViewFocusAnimUtil.init(0);
			if(mListViewItemFocusAnimUtil!= null){
				mListViewItemFocusAnimUtil.init(0);			
			}
		
			mCheckedIndex = 0;			
		}

		postDismissControl();
		
	
	}
private void setListContent(){
		Log.d(TAG, "setListContent ");
		if(mApplication != null ){
			if( mApplication.isDLNA() ){
				isNeedShare = false;
				isNeedInfo = false;			
			}else if(mApplication.isDMR()){
				isNeedShare = false;      //modify 20161201 codereview WJ
				isNeedInfo = false;
				isNeedPlayEffect = false;
				 isNeedPicEffect = false;
				 isNeedVoiceEffect = false;
				 isNeedPicScale = false;
			}	
			
			if( mApplication.isFromWeb()){
				isNeedShare = false;
				isNeedInfo = false;
			}
			
			if(mApplication.isIs3DEnabled()){
				isNeed3D = true;
			}else{
				isNeed3D = false;
			}
		}else{
			isNeedShare = true;
			isNeedInfo = true;
		}

		if(totalChapter > 1){
			isNeedChapter = true;
		}else{
			isNeedChapter = false;
		}
		Log.d(TAG, "setListContent isNeedShare =  " +isNeedShare +" ,  isNeedInfo = " +isNeedInfo+" , isNeed3D = " +isNeed3D );
		Log.d(TAG, "setListContent isNeedPlayEffect =  " +isNeedPlayEffect +" ,  isNeedPicEffect = " +isNeedPicEffect+" , isNeedVoiceEffect = " +isNeedVoiceEffect );
		/*if(!isDLNA){
				itemsName = mContext.getResources().getStringArray(R.array.settingitems_all);
			itemsValues = new String[]{"","","","","","","","",""};  //9
			isNeedShare = true;
		}else{
					itemsName = mContext.getResources().getStringArray(R.array.settingitems_dlna);
			itemsValues = new String[]{"","","","","","","",""};        //8
			isNeedShare = false;
		}*/
		mMenuNameList.clear();
		if(isNeedPicEffect){
			mMenuNameList.put(TYPE_PIC_EFFECT, mContext.getResources().getString(R.string.figure_effecttext_text));
		}
		 if(isNeedVoiceEffect){
			 mMenuNameList.put(TYPE_VOICE_EFFECT, mContext.getResources().getString(R.string.sound_effecttext_text));
		 }
		
		if(isNeedPlayEffect){
			mMenuNameList.put(TYPE_CYCLE_MODE, mContext.getResources().getString(R.string.cycle_modetext_text));
		}
	   
		if(isNeedPicScale){
			mMenuNameList.put(TYPE_PICTURE_SCALE, mContext.getResources().getString(R.string.picture_scaletext_text));
		}
		if(isNeed3D){
			mMenuNameList.put(TYPE_SAND, mContext.getResources().getString(R.string.sand_mode_text));
		}
		
		mMenuNameList.put(TYPE_SUBTITILE, mContext.getResources().getString(R.string.subtitle_text));
		mMenuNameList.put(TYPE_AUDIO_TRACK, mContext.getResources().getString(R.string.audio_track_text));	
	    if(isNeedChapter){
			mMenuNameList.put(TYPE_VIDEO_CHAPTER,mContext.getResources().getString(R.string.chaptermore));	
	    }
		if(isNeedShare){
		    mMenuNameList.put(TYPE_SHARE, mContext.getResources().getString(R.string.share_text));	
		}
		if(isNeedInfo){
			mMenuNameList.put(TYPE_INFO, mContext.getResources().getString(R.string.fileinfo_text));	
		}
		if(isNeedSeting){
			mMenuNameList.put(TYPE_SETTING, mContext.getResources().getString(R.string.setting));	
		}
				
		if( mMenuNameList != null ){
			mMenuTypeList.clear();
			itemsValues = new String[TYPE_SETTING+1];   			
			for (int key : mMenuNameList.keySet()) {
				mMenuTypeList.add(key);			  
			}			
			if(itemsValues != null){
				 for(int i = 0; i < itemsValues.length; i++){
					 itemsValues[i]="";
				  }	 			
			}			
			if(mMenuTypeList != null){
				 for(int i = 0; i < mMenuTypeList.size(); i++){
					   Log.d(TAG, "setListContent  mMenuTypeList[i = " +i+"], value=" +  mMenuTypeList.get(i));
				  }	 			
			}
			
		}	
	}
	private void getItemsContent(){
		//获取图效
		getPictureMode();
		//获取音效
		getSoundMode();
		//获取循环模式
		if(isNeedPlayEffect){		
		      getCycleMode();
		}
		//获取画面比例
		getPicScale ();				
		//获取3D模式
		//itemsValues[TYPE_SAND] = "";
		//字幕
		 getSubtitleNum();
		//音轨
		 getAudioTrackNum();
		 //章节
		 getVideoChapters();
		//信息
	   //itemsValues[TYPE_SHARE] = "";		   
		//分享
		//itemsValues[TYPE_INFO] = "";
	}
	
	private void setdata(){
		//获取图效
		itemsValues[TYPE_PIC_EFFECT] = curPicModeText;
		//获取音效
		itemsValues[TYPE_VOICE_EFFECT] = curSoundModeText;
		
		//获取循环模式
		if(isNeedPlayEffect){		
	    	itemsValues[TYPE_CYCLE_MODE] = curPlayTypeText;
		}
		//获取画面比例
		itemsValues[TYPE_PICTURE_SCALE] = curPicScaleText;		
		//字幕
		itemsValues[TYPE_SUBTITILE] = curSubText;
		//音轨
		itemsValues[TYPE_AUDIO_TRACK] = curAudioTrackText;
		//章节
		itemsValues[TYPE_VIDEO_CHAPTER] = curChapterText;
		
		menulist.clear();
	/*	for(int i = 0; i< itemsName.length;i++){
			Log.d(TAG,"setdata itemsName["+i+"]="+ itemsName[i] +",  itemsValues ="+ itemsValues[i]);		
			SettingItemData m = new SettingItemData(itemsName[i], itemsValues[i]);
			menulist.add(m);
		}*/
		if(mMenuTypeList != null  && mMenuNameList!= null){
			for(int i = 0; i< mMenuTypeList.size();i++){
				String  d = mMenuNameList.get(mMenuTypeList.get(i)) ;
				Log.d(TAG,"setdata mMenuNameList["+i+"]="+d +",  itemsValues ="+ itemsValues[mMenuTypeList.get(i)]);		
				SettingItemData m = new SettingItemData(d, itemsValues[mMenuTypeList.get(i)]);
				menulist.add(m);
			}
		}
		
	}
	
	private static int mCheckedIndex = 0;
	private static int mCheckedItemIndex = 0;
	private static int mCheckedBtnIndex = 0;
	private int curMenuListItem = 0;                //当前设置项
	private void showSecondView(int type){
		curMenuListItem = type;
		mCheckedBtnIndex = 0;
		Log.d(TAG, "showSecondView curMenuListItem = " +curMenuListItem );
	//	Utils.printLog(TAG, "mFocusViewY1 = " +mFocusViewY);
		mFocusViewY = mMenuListView.getY();
		Utils.printLog(TAG, "mFocusViewY2 = " +mFocusViewY);
		if(isRunFocusAnim){
			//menuFocusImage.setBackgroundResource(R.drawable.setting_item_select);
			menuFocusImage.setBackgroundResource(R.drawable.listview_item_selected);
		//	mListViewItemFocusAnimUtil.init(0);
			mCheckedItemIndex = 0;
		}
		switch (type) {
			case TYPE_PIC_EFFECT:
				settingIetmSelectedStatus = TYPE_PIC_EFFECT;				
				btn[3].setVisibility(View.VISIBLE);
				btn[4].setVisibility(View.VISIBLE);
	        	btn[0].setText(R.string.picmode_standand);
	        	btn[1].setText(R.string.picmode_vivid);
	        	btn[2].setText(R.string.picmode_mild);
	        	btn[3].setText(R.string.picmode_studio);
	        	btn[4].setText(R.string.picmode_user);	        	
	        	settingItemBtnLayout.requestFocus();
	        	wholeMenuLayout.setVisibility(View.INVISIBLE);
				settingMenuLayout.setVisibility(View.INVISIBLE);
				settingItemBtnLayout.setVisibility(View.VISIBLE);
				btnBgImage.setVisibility(View.VISIBLE);
				 if(isRunFocusAnim && mLayoutBtnFocusAnimUtil != null){
			        	mLayoutBtnFocusAnimUtil.init();
						btnFocusImage.setVisibility(View.VISIBLE);
				 }
				 for(int i = 0 ; i < 5 ; i++){					
					 if(curSettingItem[TYPE_PIC_EFFECT]  == i){		
							Utils.printLog(TAG, "i = " +i + " ,  curSettingItem[TYPE_PIC_EFFECT]  = " +curSettingItem[TYPE_PIC_EFFECT] );
						 btn[i].setSelected();
					 }else{
						 btn[i].clearSelected();
					 }
				 }
				break;
				
			  case TYPE_VOICE_EFFECT:            	
	            settingIetmSelectedStatus = TYPE_VOICE_EFFECT;
	        	itemNames.clear();
				itemNames.add(mContext.getResources().getString(R.string.soundmode_standand));
				itemNames.add(mContext.getResources().getString(R.string.soundmode_music));
				itemNames.add(mContext.getResources().getString(R.string.soundmode_movie));
				itemNames.add(mContext.getResources().getString(R.string.soundmode_news));
				mSettingItemAdapter.notifyDataSetChanged();
				mSettingItemList.setSelection(0);
				settingMenuLayout.requestFocus();
	            wholeMenuLayout.setVisibility(View.VISIBLE);
				settingMenuLayout.setVisibility(View.VISIBLE);
				settingItemBtnLayout.setVisibility(View.INVISIBLE);		
				btnBgImage.setVisibility(View.INVISIBLE);
				btnFocusImage.setVisibility(View.INVISIBLE);
				if(isRunFocusAnim){
					//mFocusView.setY(mFocusViewY);
					//mListViewAnimation.dataNotify();
					itemFocusImage.setVisibility(View.VISIBLE);
					mFocusView.setVisibility(View.INVISIBLE);
					mListViewItemFocusAnimUtil.init(0);
				}
				break;	
				
			  case TYPE_CYCLE_MODE:
				    settingIetmSelectedStatus = TYPE_CYCLE_MODE;
					itemNames.clear();
					for(int i = 0 ; i <Utils.mPlayerTypeText.length; i++){
						itemNames.add(mContext.getResources().getString(Utils.mPlayerTypeText[i]));
					}
					mSettingItemAdapter.notifyDataSetChanged();
					mSettingItemList.setSelection(0);
					settingMenuLayout.requestFocus();					
					wholeMenuLayout.setVisibility(View.VISIBLE);
					settingMenuLayout.setVisibility(View.VISIBLE);
					settingItemBtnLayout.setVisibility(View.INVISIBLE);
					btnBgImage.setVisibility(View.INVISIBLE);
					btnFocusImage.setVisibility(View.INVISIBLE);
					if(isRunFocusAnim){
						//mFocusView.setY(mFocusViewY);
						//mListViewAnimation.dataNotify();
						itemFocusImage.setVisibility(View.VISIBLE);
						mFocusView.setVisibility(View.INVISIBLE);
						mListViewItemFocusAnimUtil.init(0);
					}
			        break;
			        
				case TYPE_PICTURE_SCALE:	
					settingIetmSelectedStatus = TYPE_PICTURE_SCALE;
					btn[0].setText(R.string.picscale_16x9);
					btn[1].setText(R.string.picscale_4x3);
					btn[2].setText(R.string.picscale_full);
					btn[3].setVisibility(View.GONE);
					btn[4].setVisibility(View.GONE);
	            	settingItemBtnLayout.requestFocus();	            	
	            	wholeMenuLayout.setVisibility(View.INVISIBLE);
					settingMenuLayout.setVisibility(View.INVISIBLE);
					settingItemBtnLayout.setVisibility(View.VISIBLE);	   
					btnBgImage.setVisibility(View.VISIBLE);
					 if(isRunFocusAnim && mLayoutBtnFocusAnimUtil != null){
				        	mLayoutBtnFocusAnimUtil.init();
							btnFocusImage.setVisibility(View.VISIBLE);
					 }
					 for(int i = 0 ; i < 3; i++){
						 if(curSettingItem[TYPE_PICTURE_SCALE]  == i){
							 btn[i].setSelected();
						 }else{
							 btn[i].clearSelected();
						 }
					 }
					break;
				case TYPE_SAND:
					turn3DMenu();
					break;
				case TYPE_SUBTITILE:
					Log.d(TAG,"TYPE_SUBTITILE mCurrSub = " + mCurrSub +", subNms = "+subNms);
					itemNames.clear();
					itemNames.add((String)mContext.getResources().getText(R.string.default_sub));
					if(mSubValues!= null &&   mSubValues.size() >1){
						for(int i = 1; i < mSubValues.size() ; i++){
							itemNames.add(mContext.getResources().getString(R.string.subtitle_begin) +mSubValues.get(i));
						}					
					}
					mSettingItemAdapter.notifyDataSetChanged();

					settingMenuLayout.requestFocus();	 				
					mSettingItemList.setSelection(0);
	            	wholeMenuLayout.setVisibility(View.VISIBLE);
					settingMenuLayout.setVisibility(View.VISIBLE);
//					settingItemBtnLayout.setVisibility(View.INVISIBLE);	
//					btnBgImage.setVisibility(View.INVISIBLE);
//					btnFocusImage.setVisibility(View.INVISIBLE);
					Log.d(TAG,"TYPE_SUBTITILE now is isRunFocusAnim :"+isRunFocusAnim);
					if(isRunFocusAnim){
						mFocusView.setY(mFocusViewY);
						mFocusView.setVisibility(View.VISIBLE);
						itemFocusImage.setVisibility(View.INVISIBLE);
						mListViewAnimation.dataNotify();
					}
					
					break;
				case TYPE_AUDIO_TRACK:
					Log.d(TAG,"TYPE_AUDIO_TRACK mCurrTrack = " + mCurrTrack +", trackNms = "+trackNms);
					itemNames.clear();
					if(mVideoContrl != null && trackNms > 0){
						for(int i = 1 ; i <= trackNms ; i++){
							itemNames.add(mContext.getResources().getString(R.string.audioTrack_begin) +i );
						}
					}else{
						itemNames.add(mContext.getResources().getString(R.string.default_sub));
					}
					mSettingItemAdapter.notifyDataSetChanged();
					
					settingMenuLayout.requestFocus();	      					
					mSettingItemList.setSelection(0);
	            	wholeMenuLayout.setVisibility(View.VISIBLE);
					settingMenuLayout.setVisibility(View.VISIBLE);
					settingItemBtnLayout.setVisibility(View.INVISIBLE);	
					btnBgImage.setVisibility(View.INVISIBLE);
					btnFocusImage.setVisibility(View.INVISIBLE);

					Log.d(TAG,"TYPE_AUDIO_TRACK now is isRunFocusAnim :"+isRunFocusAnim);
					if(isRunFocusAnim){
						mFocusView.setY(mFocusViewY);
						mFocusView.setVisibility(View.VISIBLE);
						itemFocusImage.setVisibility(View.INVISIBLE);
						mListViewAnimation.dataNotify();
					}
					break;
				case TYPE_VIDEO_CHAPTER:
					itemNames.clear();
					if(mVideoContrl != null && totalChapter > 1){
						for(int i = 1 ; i <= totalChapter ; i++){
							itemNames.add(mContext.getResources().getString(R.string.chaptermore) +i );
						}
					}else{
						itemNames.add(mContext.getResources().getString(R.string.chaptermore) +1);
					}
					mSettingItemAdapter.notifyDataSetChanged();
					settingMenuLayout.requestFocus();	      					
					mSettingItemList.setSelection(0);
	            	wholeMenuLayout.setVisibility(View.VISIBLE);
					settingMenuLayout.setVisibility(View.VISIBLE);
					settingItemBtnLayout.setVisibility(View.INVISIBLE);	
					btnBgImage.setVisibility(View.INVISIBLE);
					btnFocusImage.setVisibility(View.INVISIBLE);
					if(isRunFocusAnim){
						mFocusView.setY(mFocusViewY);
						mFocusView.setVisibility(View.VISIBLE);
						itemFocusImage.setVisibility(View.INVISIBLE);
						mListViewAnimation.dataNotify();
					}
					
					break;
				case TYPE_SHARE:
					
					if (mVideoContrl != null
					&& mVideoContrl.isMediaPlayerPrepared()) {
				int[] mMediaInfo = new int[9];
				mMediaInfo = mVideoContrl.getMediaInfo();
				if (mMediaInfo != null) {
					int mVideoWidth = mMediaInfo[5];
					int mVideoHeight = mMediaInfo[6];
					Utils.printLog(TAG, "TYPE_SHARE mVideoWidth =" + mVideoWidth + " , mVideoHeight = " +mVideoHeight);
					if (mVideoWidth >= 3840) {
						Utils.printLog(TAG, "TYPE_SHARE 4k video not support share");
						new MyToast(mContext, mContext.getResources().getString(R.string.four_k_video_notshare)).show();
						break;
					}
				}
			}
					
					Intent sendIntent = new Intent();  
					sendIntent.setAction(Intent.ACTION_SEND);  
					if(mVideoContrl!=null){
						mTheLastPath = mVideoContrl.getPlayingVideoPath();
					}
					Utils.printLog(TAG, "share url ="+mTheLastPath);
					Uri path = Uri.parse(mTheLastPath);
					sendIntent.putExtra(Intent.EXTRA_STREAM,path);
					sendIntent.setType("video/*");
					sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Share"); 
					sendIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
					try {

						mContext.startActivity(sendIntent);  
						dismiss();
					} catch (ActivityNotFoundException acti) {
						acti.printStackTrace();						
					}
					break;
				case TYPE_INFO:
					Intent intent_videoinfo = new Intent("android.tcl.videoinfo.notpvr.show");
					  String mTheLastName = mVideoContrl.getPlayingVideoName();
					  mTheLastPath = mVideoContrl.getPlayingVideoPath();
					  String mVideosize = "";
					  mVideosize = getFileByte(mTheLastPath);   //just for local resource
					  intent_videoinfo.putExtra("video_name", mTheLastName);
					  intent_videoinfo.putExtra("video_size", mVideosize);
					  try {
							wholeMenuLayout.setVisibility(View.INVISIBLE);
							btnFocusImage.setVisibility(View.INVISIBLE);
							mContext.startActivity(intent_videoinfo);  
						} catch (ActivityNotFoundException acti) {
							acti.printStackTrace();						
						}
					break;		
				case TYPE_SETTING:
					openSetting();			         

					break;
				default:
					break;
				
		}
	}
	OnItemSelectedListener itemselect = new OnItemSelectedListener() {

		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub
			
		}
	};
	

	
	OnItemClickListener itemClick = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub		
			showItem(arg2);
		}
	};			
	
	/**
	 * 不同平台，设置项不一样。因此点击list的位置，需要显示相应的设置项，此处是一个映射
	 * 比如某些平台，没有分享功能
	 * @param position  list点击位置
	 */

	private void showItem(int position){
		int p = mMenuTypeList.get(position);
		Log.d(TAG, "showItem position = " +position + " , p  =" +p );	
	  switch(p){
	  case TYPE_PIC_EFFECT:
		  showSecondView(TYPE_PIC_EFFECT);
		  break;
	  case TYPE_VOICE_EFFECT:
		  showSecondView(TYPE_VOICE_EFFECT);
		  break;
	  case TYPE_CYCLE_MODE:
		  showSecondView(TYPE_CYCLE_MODE);
		  break;
	  case TYPE_PICTURE_SCALE:
		  showSecondView(TYPE_PICTURE_SCALE);
		  break;
	  case TYPE_SAND:
		  showSecondView(TYPE_SAND);
		  break;
	  case TYPE_SUBTITILE:
		  showSecondView(TYPE_SUBTITILE);
		  break;
	  case TYPE_AUDIO_TRACK:
		  showSecondView(TYPE_AUDIO_TRACK);
		  break;
	  case TYPE_VIDEO_CHAPTER:
		  showSecondView(TYPE_VIDEO_CHAPTER);
		  break;
	  case TYPE_SHARE:
	//	if(isNeedShare){
			  showSecondView(TYPE_SHARE);
		/*  }else{
			  showSecondView(TYPE_INFO); 
		  }		*/
		  break;
	  case TYPE_INFO:
		  showSecondView(TYPE_INFO);
		  break;
	  case TYPE_SETTING:
		  showSecondView(TYPE_SETTING);
		  break;
		  
		  default:
			  showSecondView(p);
			  break;
	  }	
	}
	private String  getPictureMode(){
		if(mPicmanager != null){
			EnTCLPictureMode PM = mPicmanager.getPictureMode();
			Log.d(TAG,"getPictureMode pm = "+ PM);
			if(PM == EnTCLPictureMode.EN_TCL_STANDARD){				
				curPicMode =  VideoPlayerUIConst.PM_STANDARD;
				curPicModeText = mContext.getResources().getString(R.string.picmode_standand);
				curSettingItem[TYPE_PIC_EFFECT]=0;
			}
			if(PM == EnTCLPictureMode.EN_TCL_USER){
				curPicMode = VideoPlayerUIConst.PM_USER;
				curPicModeText = mContext.getResources().getString(R.string.picmode_user);
				curSettingItem[TYPE_PIC_EFFECT]=4;
			}
			if(PM == EnTCLPictureMode.EN_TCL_MILD){
				curPicMode = VideoPlayerUIConst.PM_MILD;
				curPicModeText = mContext.getResources().getString(R.string.picmode_mild);
				curSettingItem[TYPE_PIC_EFFECT]=2;
			}
			if(PM == EnTCLPictureMode.EN_TCL_VIVID){
				curPicMode = VideoPlayerUIConst.PM_VIVID;
				curPicModeText = mContext.getResources().getString(R.string.picmode_vivid);
				curSettingItem[TYPE_PIC_EFFECT]=1;
			}
			if(PM == EnTCLPictureMode.EN_TCL_STUDIO){
				curPicMode = VideoPlayerUIConst.PM_STUDIO;
				curPicModeText = mContext.getResources().getString(R.string.picmode_studio);
				curSettingItem[TYPE_PIC_EFFECT]=3;
			}
		}
		Log.d(TAG,"now getPictureMode curPicModeText = "+ curPicModeText);	
		return curPicModeText;
	}
   
   private String getSoundMode(){
		if(soundManager != null)
		{
		   EnTCLSoundMode SM = soundManager.getSoundMode();
		   Log.d(TAG,"getSoundMode sm = "+ SM);
		
		if(SM == EnTCLSoundMode.EN_TCL_STANDARD){
			curSoundMode = VideoPlayerUIConst.SM_STANDARD;
			curSoundModeText = mContext.getString(R.string.soundmode_standand);
			curSettingItem[TYPE_VOICE_EFFECT]= 0;
		}
		if(SM == EnTCLSoundMode.EN_TCL_MOVIE){
			curSoundMode = VideoPlayerUIConst.SM_MOVIE;
			curSoundModeText = mContext.getString(R.string.soundmode_movie);
			curSettingItem[TYPE_VOICE_EFFECT]= 2;
		}
		if(SM == EnTCLSoundMode.EN_TCL_MUSIC){
			curSoundMode = VideoPlayerUIConst.SM_MUSIC;
			curSoundModeText = mContext.getString(R.string.soundmode_music);
			curSettingItem[TYPE_VOICE_EFFECT]= 1;
		}
		if(SM == EnTCLSoundMode.EN_TCL_NEWS){
			curSoundMode = VideoPlayerUIConst.SM_NEWS;
			curSoundModeText = mContext.getString(R.string.soundmode_news);
			curSettingItem[TYPE_VOICE_EFFECT]= 3;
		}
		}
		Log.d(TAG,"getSoundMode curSoundModeText ="+ curSoundModeText);
		return curSoundModeText;
	}
	
	private String getCycleMode(){
		 curPlayType = mVideoContrl.getPlayType();
		 curPlayTypeText = mContext.getResources().getString(VideoPlayerActivity.mPlayerTypeText[curPlayType]);
			Log.d(TAG,"getCycleMode curPlayType ="+ curPlayType + ", curPlayTypeText = " +curPlayTypeText);
		 curSettingItem[TYPE_CYCLE_MODE]=curPlayType;
		 return curPlayTypeText;
	}
 
	  private String getPicScale (){
		   if(mPicmanager != null){
			   EnTCLAspectRatio PS = mPicmanager.getAspectRatio();
			   Log.d(TAG,"getPicScale ps = "+ PS);
			   
			   if(PS == EnTCLAspectRatio.EN_TCL_16X9){
				   curPicScale = VideoPlayerUIConst.PS_16x9;
				   curPicScaleText = mContext.getResources().getString(R.string.picscale_16x9);		
				   curSettingItem[TYPE_PICTURE_SCALE]=0;
			   }
			   if(PS == EnTCLAspectRatio.EN_TCL_4X3){
				   curPicScale = VideoPlayerUIConst.PS_4X3;
				   curPicScaleText = mContext.getResources().getString(R.string.picscale_4x3);			
				   curSettingItem[TYPE_PICTURE_SCALE]=1;
			   }
			   if(PS == EnTCLAspectRatio.EN_TCL_AUTO){
				   curPicScale = VideoPlayerUIConst.PS_AUTO;
				   curPicScaleText = mContext.getResources().getString(R.string.picscale_full);	
				   curSettingItem[TYPE_PICTURE_SCALE]=2;
			   }
		   }
		   
		   Log.d(TAG,"getPicScale curPicScaleText = "+ curPicScaleText);
		   return curPicScaleText;
	   }

	  private String  getSubtitleNum(){
			
			if (mVideoContrl != null&& mVideoContrl.isMediaPlayerPrepared()) {		
				subNms = mVideoContrl.getSubtitleNms();		
				mSubValues.clear();
				mSubValues.add(VideoPlayerContrlConsts.DEFAULT_SUBTITLE);
				for (int i = 1; i < subNms+1; i++) {
					mSubValues.add(i);
				}
				mCurrSub = mVideoContrl.getCurrentSubtitleNms();			
			}		
			
			if(mVideoContrl != null && subNms > 0){
				if (mCurrSub == VideoPlayerContrlConsts.DEFAULT_SUBTITLE) {
					curSubText = (String)mContext.getResources().getText(R.string.default_sub);
					curSettingItem[TYPE_SUBTITILE] = 0;
				} else {
					curSubText = mContext.getResources().getString(R.string.subtitle_begin) + mCurrSub;
					curSettingItem[TYPE_SUBTITILE] = mCurrSub;
				}
			}else{
				curSubText = (String)mContext.getResources().getText(R.string.default_sub);
			}
			
			Log.d(TAG, "getSubtitleNum subNms = " +subNms + " ,  mCurrSub = " +mCurrSub + ", curSubText = " +curSubText);
			return curSubText;
		}
		
		private String  getAudioTrackNum(){
			
			if (mVideoContrl != null&& mVideoContrl.isMediaPlayerPrepared()) {
				
				trackNms = mVideoContrl.getAudioTrackNms();
				Log.d(TAG, "getAudioTrackNum trackNms = " +trackNms + " ,  mCurrTrack = " + mCurrTrack);

			}
			mCurrTrack = mVideoContrl.getCurrentAudioTrackNms();
			curSettingItem[TYPE_AUDIO_TRACK] = mCurrTrack;
			if(trackNms > 0 && mCurrTrack +1 <= trackNms){
				mCurrTrack = mCurrTrack +1;
			}
			Log.d(TAG, "getAudioTrackNum trackNms = " +trackNms + " ,  mCurrTrack = " + mCurrTrack);
			if(mVideoContrl != null && trackNms > 0){
				curAudioTrackText= mContext.getResources().getString(R.string.audioTrack_begin) + mCurrTrack;			
			}else{
				curAudioTrackText = (String)mContext.getResources().getText(R.string.default_sub);
			}
			return curAudioTrackText;
		}
		
View.OnClickListener buttonclick = new View.OnClickListener() {
	
	@Override
	public void onClick(View arg0) {
		Log.d(TAG,"buttonclick ");
		if(curMenuListItem == TYPE_PIC_EFFECT){
			switch (arg0.getId()) {
			case R.id.item1:
				if(mPicmanager != null){
					mPicmanager.setPictureMode(EnTCLPictureMode.EN_TCL_STANDARD);
					curPicModeText = mContext.getResources().getString(R.string.picmode_standand);
					curSettingItem[curMenuListItem] =0;
				}			
			
				break;
			case R.id.item2:
				if(mPicmanager != null){
					mPicmanager.setPictureMode(EnTCLPictureMode.EN_TCL_VIVID);
					curPicModeText = mContext.getResources().getString(R.string.picmode_vivid);
					curSettingItem[curMenuListItem] =1;
				}
				break;
			case R.id.item3:
				if(mPicmanager != null){
					mPicmanager.setPictureMode(EnTCLPictureMode.EN_TCL_MILD);
					curPicModeText = mContext.getResources().getString(R.string.picmode_mild);
					curSettingItem[curMenuListItem] =2;
				}
				break;
			case R.id.item4:
				if(mPicmanager != null){
					mPicmanager.setPictureMode(EnTCLPictureMode.EN_TCL_STUDIO);
					curPicModeText = mContext.getResources().getString(R.string.picmode_studio);
					curSettingItem[curMenuListItem] =3;
				}
				break;
			case R.id.item5:
				if(mPicmanager != null){
					mPicmanager.setPictureMode(EnTCLPictureMode.EN_TCL_USER);
					curPicModeText = mContext.getResources().getString(R.string.picmode_user);
					curSettingItem[curMenuListItem] =4;
				}
				break;
				

			default:
				break;
				
			}
			 for(int i = 0 ; i < 5 ; i++){
				 if(curSettingItem[TYPE_PIC_EFFECT]  != i){
					 btn[i].clearSelected();
				 }else{
					 btn[i].setSelected();
				 }
			 }
			Log.d(TAG,"buttonclick curPicModeText = "+curPicModeText);
		}else if(curMenuListItem == TYPE_PICTURE_SCALE){
			switch (arg0.getId()) {
			case R.id.item1:
				if(mPicmanager != null){
					mPicmanager.setAspectRatio(EnTCLAspectRatio.EN_TCL_16X9);
					curPicScaleText = mContext.getResources().getString(R.string.picscale_16x9);
					curSettingItem[curMenuListItem] =0;
				}
				break;
			case R.id.item2:
				if(mPicmanager != null){
					mPicmanager.setAspectRatio(EnTCLAspectRatio.EN_TCL_4X3);
					curPicScaleText = mContext.getResources().getString(R.string.picscale_4x3);
					curSettingItem[curMenuListItem] =1;
				}
				break;
			case R.id.item3:
				if(mPicmanager != null){
					mPicmanager.setAspectRatio(EnTCLAspectRatio.EN_TCL_AUTO);
					curPicScaleText = mContext.getResources().getString(R.string.picscale_full);
					curSettingItem[curMenuListItem] =2;
				}
				break;

			default:
				break;
			}
			 for(int i = 0 ; i < 3 ; i++){
				 if(curSettingItem[TYPE_PICTURE_SCALE]  != i){
					 btn[i].clearSelected();
				 }else{
					 btn[i].setSelected();
				 }
			 }
			Log.d(TAG,"buttonclick curPicScaleText is "+curPicScaleText);
		}
		
	}
	
};
	
	private void turn3DMenu() {
		Log.d(TAG, "turn3DMenu   isHkOrCn = " + isHkOrCn);
		if (isHkOrCn) { // now is CN add HK机型 for828hk
			/*
			 * try { // 20141016 modify for 3D Log.d(TAG,
			 * "now to start 3D setting CN"); Intent intent = new Intent();
			 * intent.setAction("com.tcl.ShortcutKey"); intent.putExtra("Type",
			 * "3D"); wholeMenuLayout.setVisibility(View.INVISIBLE);
			 * mContext.sendBroadcast(intent); } catch
			 * (ActivityNotFoundException acti) { acti.printStackTrace();
			 * MyToast.showNoFuntionToast(mContext); }
			 */

			try { // 20160419 采用startService启动3D
				Log.d(TAG, "turn3DMenu  isHkOrCn 3D");
				Intent mIntent = new Intent("com.tcl.settings.SHOW_WINDOW");
				mIntent.setClassName("com.tcl.settings",
						"com.tcl.settings.ShowWindowService");
				mIntent.putExtra("Type", "3D");
				mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				mContext.startService(mIntent);
				wholeMenuLayout.setVisibility(View.INVISIBLE);
				// dismiss();
				// mContext.sendBroadcast(intent);
			} catch (ActivityNotFoundException acti) {
				acti.printStackTrace();
				MyToast.showNoFuntionToast(mContext);
			}

		} else { // now is AU TCL-AU-NT667K-S1
			Log.d(TAG, "now to start 3D setting   AU");
			Intent intent = new Intent();
			intent.setAction("android.intent.action.tcl.show3dmenu");
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			intent.putExtra("start3DType", 1);
			wholeMenuLayout.setVisibility(View.INVISIBLE);
			try {
				mContext.startActivity(intent);
			} catch (ActivityNotFoundException acti) {
				acti.printStackTrace();
				dismiss();
				MyToast.showNoFuntionToast(mContext);
			}
		}

	}

private String getFileByte(String path) {
	long fileS = 0;
	String fileSizeString = "";
	File f = new File(path);
	try {
		// FileInputStream fis = new FileInputStream(f);
		fileS = f.length();
		Utils.printLog(TAG, "file length is " + fileS);
		DecimalFormat df = new DecimalFormat("#.00");
		if (fileS < 1024) {
			fileSizeString = df.format((double) fileS) + "B";
		} else if (fileS < 1048576) {
			fileSizeString = df.format((double) fileS / 1024) + "K";
		} else if (fileS < 1073741824) {
			fileSizeString = df.format((double) fileS / 1048576) + "M";
		} else {
			fileSizeString = df.format((double) fileS / 1073741824) + "G";
		}

	} catch (Exception e1) {
		e1.printStackTrace();
	}
	return fileSizeString;

}	
@Override
public void onWindowFocusChanged(boolean hasFocus) {
	// TODO Auto-generated method stub
	if(hasFocus){
		if(wholeMenuLayout.getVisibility() == View.INVISIBLE){
			wholeMenuLayout.setVisibility(View.VISIBLE);				
		}
		if(wholeMenuLayout.hasFocus()){
			if(isRunFocusAnim){
			//	menuFocusImage.setBackgroundResource(R.drawable.setting_item_focus);
				menuFocusImage.setBackgroundResource(R.drawable.listview_item_selected);
			}
		}
	}
	super.onWindowFocusChanged(hasFocus);
}

public interface SettingMenuCallBack{
	
	void onSettingMenuShowed();
}

private SettingMenuCallBack mCallback = null;

public void setCallback(SettingMenuCallBack cb) {
	mCallback = cb;
}

@Override
public boolean onKeyDown(int keyCode, KeyEvent event) {
	
	if(keyCode == KeyEvent.KEYCODE_BACK){
		/*if(wholeMenuLayout.getVisibility()== View.VISIBLE){
			mCallback.onSettingMenuShowed();			
		}else if(settingItemBtnLayout.getVisibility() == View.VISIBLE){
			menulist.clear();
			getItemsContent();
			setdata();
			Log.d(TAG,"now menulist size is "+menulist.size());
			changeAdapter.changelist(menulist);
			changeAdapter.notifyDataSetChanged();
			Log.d(TAG,"now mentlist item 2 is "+menulist.get(2).getItemName() +"===="+menulist.get(2).getItemValue());
			Log.d(TAG,"======lyOfWholeMenu visible");
			wholeMenuLayout.setVisibility(View.VISIBLE);
			
			settingItemBtnLayout.setVisibility(View.INVISIBLE);
			return true;
		}*/
		onBack();
	}
	
	return super.onKeyDown(keyCode, event);
}

private BroadcastReceiver mVideoPlayerReceiver = new BroadcastReceiver(){                         //????

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		Log.v(TAG, "Received broadCast mVideoPlayerReceiver");
		if(intent.getAction().equals("com.tcl.mediaplayer.exit.subtitle")){
			settingItemBtnLayout.setVisibility(View.GONE);
			btnBgImage.setVisibility(View.GONE);
			mFocusView.setVisibility(View.INVISIBLE);
			btnFocusImage.setVisibility(View.INVISIBLE);
			dismiss();
		} else if (intent.getAction().equals(CommonConst.VIDEO_SETTING_AUDIOTRACK)) {
//			mCurrTrack = mLastTrack;
//			mVideoContrl.setAudioTrackNms((short) mCurrTrack);
//			reFreshTrackText();
		} 
	}
	
};

class SettingItemAdapter extends BaseAdapter{
	private int itemCountInOneScreen;
	private int itemHeight = -1;
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return itemNames.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return itemNames.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(position>= getCount()){
			position = itemNames.size()-1;//0076932: 【多屏互动】推送手机上的视频到电视播放时调出菜单键切换亮度，电视提示：“很抱歉，统一化视频播放器已停止运行”
		}
		// TODO Auto-generated method stub
		ViewHolder holder = null;	
		convertView = null;
		if(convertView == null){
			convertView = mInflater.inflate(R.layout.setting_list_item, null);			
			holder = new ViewHolder();			
			holder.name = (TextView)convertView.findViewById(R.id.setting_item_name);
			holder.mImageView = (ImageView)convertView.findViewById(R.id.setting_item_img);
			convertView.setTag(holder);
		}/*else {
			holder = (ViewHolder)convertView.getTag();			
		}*/
		holder.name.setText( itemNames.get(position));
		if(curSettingItem[curMenuListItem] == position){
			holder.mImageView.setVisibility(View.VISIBLE);
		}else{
			holder.mImageView.setVisibility(View.INVISIBLE);
		}
		return convertView;
	}
	
	 private class ViewHolder{		   
		    TextView name;
		    ImageView mImageView;
		}

		public int getItemCountInOneScreen() {
			return itemCountInOneScreen;
		}

		public void setItemCountInOneScreen(int itemCountInOneScreen) {
			this.itemCountInOneScreen = itemCountInOneScreen;
		}

}

/**
 * 二级list点击效果 --音效， 循环模式，字幕，音轨
 */
OnItemClickListener onSecondItemClick = new OnItemClickListener() {

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		Log.d(TAG, "onSettingItemClick position = " +position + ", curMenuListItem = " +curMenuListItem);
		int pos = position;
		switch (curMenuListItem) {
		case TYPE_PIC_EFFECT:
			break;
		case TYPE_VOICE_EFFECT:		
			switch (pos)	{						
			case 0:
				if(soundManager != null){
				soundManager.setSoundMode(EnTCLSoundMode.EN_TCL_STANDARD);
				curSoundModeText = mContext.getResources().getString(R.string.soundmode_standand);
				}
				break;
			case 1:
				if(soundManager != null){
				soundManager.setSoundMode(EnTCLSoundMode.EN_TCL_MUSIC);
				curSoundModeText = mContext.getResources().getString(R.string.soundmode_music);
				}
				break;
			case 2:
				if(soundManager != null){
				soundManager.setSoundMode(EnTCLSoundMode.EN_TCL_MOVIE);
				curSoundModeText = mContext.getResources().getString(R.string.soundmode_movie);
				}
				break;
			case 3:
				if(soundManager != null){
				soundManager.setSoundMode(EnTCLSoundMode.EN_TCL_NEWS);
				curSoundModeText = mContext.getResources().getString(R.string.soundmode_news);
				}
				break;	
			}
		    curSettingItem[TYPE_VOICE_EFFECT] = pos;
		    break;
		case TYPE_CYCLE_MODE:	
			
			 curSettingItem[TYPE_CYCLE_MODE] = pos;
			 curPlayType = pos;
			if(curPlayType < Utils.mPlayerTypeText.length){
				mVideoContrl.setPlayType(curPlayType);   //0-顺序，1-单个，2-单个循环，3-随机，4-所有循环
				 curPlayTypeText =mContext.getResources().getString(Utils.mPlayerTypeText[curPlayType]);
			 }		
			 Log.d(TAG, "onSettingItemClick curPlayType = " + curPlayType );
			break;
		case TYPE_PICTURE_SCALE:					
			break;
		case TYPE_SAND:							
			break;
		case TYPE_SUBTITILE:		
			
			if(isRunFocusAnim){
				pos = mListViewAnimation.getFocusItemIndex();
			}
			if(mSubValues != null){
				mCurrSub = mSubValues.get(pos);
			}			
			if (mVideoContrl != null) {
				mVideoContrl.setSubtitleNms((short) mCurrSub);
			}
			if (mCurrSub == VideoPlayerContrlConsts.DEFAULT_SUBTITLE) {
				curSubText = (String)mContext.getResources().getText(R.string.default_sub);
			} else {
				curSubText = mContext.getResources().getString(R.string.subtitle_begin) + mCurrSub;
			}
			 curSettingItem[TYPE_SUBTITILE] = pos;
			 Log.d(TAG, "onSettingItemClick TYPE_SUBTITILE mCurrSub = " + mCurrSub );
			break;
		case TYPE_AUDIO_TRACK:		
			if(isRunFocusAnim){
				pos = mListViewAnimation.getFocusItemIndex();
			}
			if(trackNms > 0 && pos +1 <= trackNms){
				mCurrTrack = pos;    //modify current track num show 20160715
				if (mVideoContrl != null) {
					mVideoContrl.setAudioTrackNms((short) mCurrTrack);
					 Log.d(TAG, "onSettingItemClick TYPE_AUDIO_TRACK mCurrTrack = " + mCurrTrack ); 
				}
				curAudioTrackText= mContext.getResources().getString(R.string.audioTrack_begin) +( mCurrTrack + 1);		//modify current track num show 20160715
				curSettingItem[TYPE_AUDIO_TRACK] = pos;
			}else{
				 Log.v(TAG, "onSettingItemClick TYPE_AUDIO_TRACK mCurrTrack = 0" );
			}
			break;
		case TYPE_VIDEO_CHAPTER:
			if(isRunFocusAnim){
				pos = mListViewAnimation.getFocusItemIndex();
			}
			if( pos +1 <= totalChapter){
				mCurChapter = pos + 1 ;
				if(mVideoContrl != null){
					mVideoContrl.playChapter(mCurChapter - 1);
					Log.d(TAG,"now change chapter index is "+mCurChapter);
					mVideoContrl.setCurrentChapter(mCurChapter);
				}
				curChapterText = mContext.getResources().getString(R.string.chaptermore)+ mCurChapter;
				curSettingItem[TYPE_VIDEO_CHAPTER] = pos;
			}else{
				 Log.v(TAG, "onSettingItemClick TYPE_VIDEO_CHAPTER mCurrTrack = 0" );
			}
			break;
		case TYPE_SHARE:							
			break;
		case TYPE_INFO:
			break;
		default:
			break;
		}		
		if(curMenuListItem ==TYPE_PIC_EFFECT 
				|| curMenuListItem ==TYPE_PICTURE_SCALE 
				|| curMenuListItem ==TYPE_SAND 
				|| curMenuListItem ==TYPE_SHARE 
				|| curMenuListItem ==TYPE_INFO 			
				){
			;
		}else{
			mSettingItemAdapter.notifyDataSetChanged();
			mSettingItemList.setSelection(pos);
			setdata();
			changeAdapter.notifyDataSetChanged();
		}
		
		Log.v(TAG,"onSettingItemClick");		
	}
};

	@Override
	public void dismiss() {
		try {
			cancelDismissControl();
			super.dismiss();
			Log.v(TAG,"dismiss");	
			
			mFocusView.setVisibility(View.INVISIBLE);
			settingMenuLayout.setVisibility(View.INVISIBLE);
			
			btnFocusImage.setVisibility(View.INVISIBLE);
			btnBgImage.setVisibility(View.INVISIBLE);
			settingItemBtnLayout.setVisibility(View.INVISIBLE);		
			
			wholeMenuLayout.setVisibility(View.INVISIBLE);
			if(mVideoPlayerReceiver!=null){
				mContext.unregisterReceiver(mVideoPlayerReceiver);
				mVideoPlayerReceiver = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private void onBack(){
		if(wholeMenuLayout.getVisibility()==View.VISIBLE
				&& settingMenuLayout.getVisibility()==View.INVISIBLE
				&& settingItemBtnLayout.getVisibility()==View.INVISIBLE 
				){
			settingMenuLayout.setVisibility(View.INVISIBLE);
			settingItemBtnLayout.setVisibility(View.INVISIBLE);
			btnBgImage.setVisibility(View.INVISIBLE);
			wholeMenuLayout.setVisibility(View.INVISIBLE);
			Log.v(TAG, "onBackPressed 1");
			if(mCallback!= null){
				mCallback.onSettingMenuShowed();	
			}			
			dismiss();			
		}		
		else 	if(wholeMenuLayout.getVisibility()==View.VISIBLE 
				&& settingMenuLayout.getVisibility()==View.VISIBLE 
				&&settingItemBtnLayout.getVisibility()==View.INVISIBLE 
				  ){
			fromSecondToFirst();
			Log.v(TAG, "onBackPressed 2");
		}
		else if(wholeMenuLayout.getVisibility()==View.INVISIBLE
				&& settingMenuLayout.getVisibility()==View.INVISIBLE
				&& settingItemBtnLayout.getVisibility()==View.VISIBLE 
                   ){
			fromSecondToFirst();
			Log.v(TAG, "onBackPressed 3");
		}
	
	}
	
	 private void fromSecondToFirst(){
		    setdata();
		    changeAdapter.notifyDataSetChanged();
			wholeMenuLayout.setVisibility(View.VISIBLE);
			settingMenuLayout.setVisibility(View.INVISIBLE);
			settingItemBtnLayout.setVisibility(View.INVISIBLE);
			btnBgImage.setVisibility(View.INVISIBLE);
			btnFocusImage.setVisibility(View.INVISIBLE);
			wholeMenuLayout.requestFocus();
			if(isRunFocusAnim){
				//menuFocusImage.setBackgroundResource(R.drawable.setting_item_focus);
				menuFocusImage.setBackgroundResource(R.drawable.listview_item_selected);
			}		
	 }
	 
		@Override
		public boolean dispatchKeyEvent(KeyEvent event) {
			// TODO Auto-generated method stub
			cancelDismissControl();
			postDismissControl();
			keyAction = event.getAction();
			if(event.getAction() == KeyEvent.ACTION_DOWN){                                           //向下按键
				 if( event.getKeyCode() == KeyEvent.KEYCODE_BACK){                                   //返回键--
						Log.v(TAG, "dispatchKeyEvent ACTION_DOWN KEYCODE_BACK");
						onBack();
						return true;
				 }else if(event.getKeyCode() == KeyEvent.KEYCODE_DPAD_LEFT){               //左
					 if(wholeMenuLayout.getVisibility()==View.VISIBLE                                       // 一级列表，二级列表可见  ----则消失二级列表，显示一级界面
								&& settingMenuLayout.getVisibility()==View.VISIBLE 
								&&settingItemBtnLayout.getVisibility()==View.INVISIBLE ){
						 fromSecondToFirst();
							return true;
					 }
					 if(wholeMenuLayout.getVisibility()==View.INVISIBLE                                     // 一级列表，二级按钮可见  ----则二级按钮的左移处理
								&& settingMenuLayout.getVisibility()==View.INVISIBLE 
								&&settingItemBtnLayout.getVisibility()==View.VISIBLE){				
						 if(isRunFocusAnim && mLayoutBtnFocusAnimUtil != null){
							 mCheckedBtnIndex = mLayoutBtnFocusAnimUtil.animationIndexLeft(mCheckedBtnIndex);//向左平移
							 return true;
						 }
					 }
				 }else if(event.getKeyCode() == KeyEvent.KEYCODE_DPAD_RIGHT){               //右
					 if(wholeMenuLayout.getVisibility()==View.VISIBLE                                          // 一级列表可见，二级列表不可见  ----则显示二级列表，显示一级界面
								&& settingMenuLayout.getVisibility()==View.INVISIBLE 
								&&settingItemBtnLayout.getVisibility()==View.INVISIBLE ){
						 // add here for mouse click    20161214 WJ
						    int tempos = mMenuListView.getSelectedItemPosition() ;
						    if(tempos == -1){
						    	tempos = 0;
						    }
						  // add here for mouse click   
							showItem(tempos);
							return true;
					 }				 
					 if(wholeMenuLayout.getVisibility()==View.INVISIBLE                                     // 一级列表，二级按钮可见  ----则二级按钮的右移处理
								&& settingMenuLayout.getVisibility()==View.INVISIBLE 
								&&settingItemBtnLayout.getVisibility()==View.VISIBLE){
						 if(isRunFocusAnim&& mLayoutBtnFocusAnimUtil != null){					 
							 mCheckedBtnIndex = mLayoutBtnFocusAnimUtil.animationIndexRight(mCheckedBtnIndex);//向右平移
							 return true;
						 }
					 }
				 }
				 else	if( event.getKeyCode()==KeyEvent.KEYCODE_DPAD_UP){                        //上
						Log.v(TAG, "dispatchKeyEvent  ACTION_DOWN ");		
						if(isRunFocusAnim && mListViewFocusAnimUtil != null  ){	                                                                 //运行动画
								  if(mMenuListView.hasFocus() ||menuFocusImage.hasFocus()){                                                //一级列表的上移动画                             
												if(event.getRepeatCount() > 0){
													Log.v(TAG, "dispatchKeyEvent(mMenuListView) KEYCODE_DPAD_UP  longKeyUp");
													mListViewFocusAnimUtil.longKeyUp();
													return true;
												 }						
												mCheckedIndex = mListViewFocusAnimUtil.animationIndexUp(mCheckedIndex);
												return true;						
								 }
								  else if(mSettingItemList.hasFocus()                                                                                                              //二级列表有焦点
										  //|| itemFocusImage.hasFocus()
										  ){
									  if((curMenuListItem== TYPE_VOICE_EFFECT                                                                                         //图效，循环模式
											  ||curMenuListItem== TYPE_CYCLE_MODE)
											  &&mListViewItemFocusAnimUtil != null){
												if(event.getRepeatCount() > 0){
										        	Log.v(TAG, "dispatchKeyEvent(mSettingItemList)  KEYCODE_DPAD_UP longKeyUp");
										        	mListViewItemFocusAnimUtil.longKeyUp();
										        	return true;
										       }
										           mCheckedItemIndex = mListViewItemFocusAnimUtil.animationIndexUp(mCheckedItemIndex);
									  }else{                                                                                                                                                                            //音轨，字幕
										if(mListViewAnimation!= null ){
											 onUpDownKeyClick(event);    
										}										                                     
									  }																	
										return true;						
									}
						}else {
									 return super.dispatchKeyEvent(event);
						}			
				 }
			  else if( event.getKeyCode()==KeyEvent.KEYCODE_DPAD_DOWN){                                                         //下
				  if(isRunFocusAnim && mListViewFocusAnimUtil != null ){	                                                                   //运行动画
						if(mMenuListView.hasFocus() || menuFocusImage.hasFocus()){                                                   //一级列表的下移动画  
							if(event.getRepeatCount() > 0){
								mListViewFocusAnimUtil.longKeyDown();
								return true;
							}
							mCheckedIndex = mListViewFocusAnimUtil.animationIndexDown(mCheckedIndex);
							return true;
						}
						else if(mSettingItemList.hasFocus()                                                                                                         //二级列表有焦点
							//	|| itemFocusImage.hasFocus()
								){
							  if((curMenuListItem== TYPE_VOICE_EFFECT                                                                               //图效，循环模式
									  ||curMenuListItem== TYPE_CYCLE_MODE)
									  &&mListViewItemFocusAnimUtil != null){
								  if(event.getRepeatCount() > 0){
									Log.d(TAG, "dispatchKeyEvent(mSettingItemList)KEYCODE_DPAD_DOWN  longKeyUp");
									mListViewItemFocusAnimUtil.longKeyDown();
									return true;
								 }						
								mCheckedItemIndex = mListViewItemFocusAnimUtil.animationIndexDown(mCheckedItemIndex);
							  }else{
								  if(mListViewAnimation!= null  ){                                                                                                       //音轨，字幕
									  onUpDownKeyClick(event);
								  }
									
							  }							
							return true;						
						}	
				 }else{
					 return super.dispatchKeyEvent(event);
				 }
			  }
			}
			
			else if(event.getAction() == KeyEvent.ACTION_UP){                                                                                        //向上松掉按键
				if(isRunFocusAnim && mListViewFocusAnimUtil != null ){	
					if(mMenuListView.hasFocus() || menuFocusImage.hasFocus()){                                                       //一级列表
						if(event.getKeyCode()==KeyEvent.KEYCODE_DPAD_UP || event.getKeyCode()==KeyEvent.KEYCODE_DPAD_DOWN){
							mCheckedIndex = mListViewFocusAnimUtil.stopAn();
						}
					}else if(mSettingItemList.hasFocus()                                                                                                            //二级列表
							//|| itemFocusImage.hasFocus()
							){
						if(event.getKeyCode()==KeyEvent.KEYCODE_DPAD_UP || event.getKeyCode()==KeyEvent.KEYCODE_DPAD_DOWN){
							  if((curMenuListItem== TYPE_VOICE_EFFECT                                                                                    //图效，循环模式
									  ||curMenuListItem== TYPE_CYCLE_MODE)
									  &&mListViewItemFocusAnimUtil != null){
							          mCheckedItemIndex = mListViewItemFocusAnimUtil.stopAn();
							  }
						}
					 }
				}	
			}	
			return super.dispatchKeyEvent(event);
		
		}
		
		public static final int DIMISS_TIME = 10 * 1000;// 控制台自动消失的时间间隔
		private Handler mHand = new Handler(){
			public void handleMessage(android.os.Message msg) {
				switch (msg.what) {
				case CommonConst.HANDLER_MSG_DELEY_PUT_FOCUS:
					Log.d(TAG, "handleMessage HANDLER_MSG_DELEY_PUT_FOCUS");
					changeTextColor(mListViewAnimation.getCurrentFocusView(), CommonConst.FOCUS_STATE_GET_FOCUS);
					break;
				case CommonConst.HANDLER_MSG_DELEY_LOSE_FOCUS:
					Log.v(TAG, "handleMessage HANDLER_MSG_DELEY_LOSE_FOCUS");
					changeTextColor(mListViewAnimation.getCurrentFocusView(), CommonConst.FOCUS_STATE_LOSE_FOCUS);
					break;
				default:
					break;
				}
			};
		};
		private Runnable autoDismissMenuRunnable = new Runnable() {

			@Override
			public void run() {
				if(mCallback!= null){
					mCallback.onSettingMenuShowed();	
				}	
				dismiss();			
			}
		};
		
		public void cancelDismissControl() {
			//Log.d(TAG, "cancelDismissMenu  autoDismissMenuRunnable=" + autoDismissMenuRunnable);
			if (null != autoDismissMenuRunnable) {
				mHand.removeCallbacks(autoDismissMenuRunnable);
			}
		}
		
		// 10s后不进行任何操作或者按返回键消失。
		public void postDismissControl() {
		//	Log.v(TAG, "postDismissControl");
			mHand.postDelayed(autoDismissMenuRunnable, DIMISS_TIME);
		}
		
		private boolean isLongOnclick;
		public int keyRepeateCount = 0;
			protected boolean onUpDownKeyClick(KeyEvent event) {
				isLongOnclick = event.getRepeatCount() >0 ? true:false;
				Log.d(TAG, "onUpDownKeyClick  isLongOnclick = " + isLongOnclick);
				//短按处理
				if(!isLongOnclick){
					shortOnKeyClick(event);
				}
				//长按处理
				else {
					//列表滚动加速度，根据这个值加速，这个值会在列表头和列表尾重置，保证循环一次后，下一个循环加速度回到原来的值
					keyRepeateCount ++;
					Log.d(TAG, "onUpDownKeyClick keyRepeateCount = " + keyRepeateCount);
					longOnKeyClick(event);
				}
				return true;
			}

			private boolean longOnKeyClick(KeyEvent event) {
				// TODO Auto-generated method stub
				if (event.getAction() == KeyEvent.ACTION_DOWN) {
					switch (event.getKeyCode()) {
					case KeyEvent.KEYCODE_DPAD_UP:
						// 告知焦点发生变化				
						mHand.removeMessages(CommonConst.HANDLER_MSG_DELEY_PUT_FOCUS);
						changeTextColor(mListViewAnimation.getCurrentFocusView(), CommonConst.FOCUS_STATE_LOSE_FOCUS);
						// 如果列表焦点已经到了最头部，再按上键则焦点移动到列表最尾部。（列表循环）
						if (mListViewAnimation.getFocusItemIndex() == 0 || mListViewAnimation.focusImagePosition ==0) {
							keyRepeateCount = 0;
							mFocusView.stopAnimY();
							mListViewAnimation.setFocusItemByPosition(mFocusView,mSettingItemAdapter.getCount() - 1);
							//保证列表能刷到最后项
							mSettingItemList.setSelection(mSettingItemAdapter.getCount() - 1);
							mHand.sendEmptyMessageDelayed(CommonConst.HANDLER_MSG_DELEY_PUT_FOCUS, 100);
							return true;
						}
						mListViewAnimation.Up(mFocusView,keyRepeateCount*2,isLongOnclick);
						return true;
					case KeyEvent.KEYCODE_DPAD_DOWN:
						mHand.removeMessages(CommonConst.HANDLER_MSG_DELEY_PUT_FOCUS);
						changeTextColor(mListViewAnimation.getCurrentFocusView(), CommonConst.FOCUS_STATE_LOSE_FOCUS);
						if (mListViewAnimation.focusImagePosition == mSettingItemAdapter.getItemCountInOneScreen()-1 
								||mListViewAnimation.getFocusItemIndex() == mSettingItemAdapter.getCount() - 1) {
							Log.v(TAG,	"KEYCODE_DPAD_DOWN  getFocusItemIndex() == listAdapter.getCount() - 1");
							mFocusView.stopAnimY();
							mListViewAnimation.setFocusItemByPosition(mFocusView,0);
							//保证列表能刷到第一项
							mSettingItemList.setSelection(0);
							keyRepeateCount = 0;
							mHand.sendEmptyMessageDelayed(CommonConst.HANDLER_MSG_DELEY_PUT_FOCUS, 100);

							return true;
						}
						mListViewAnimation.Down(mFocusView,keyRepeateCount*2,isLongOnclick);
						return true;
					}
				}
				return true;
			}
			
			private boolean shortOnKeyClick(KeyEvent event) {
				// TODO Auto-generated method stub
				Log.v(TAG, "shortOnKeyClick ");
				if (event.getAction() == KeyEvent.ACTION_DOWN) {
					switch (event.getKeyCode()) {
					case KeyEvent.KEYCODE_DPAD_UP://19
						Log.v(TAG, "shortOnKeyClick(UP) ");
						// 告知焦点发生变化
						mHand.removeMessages(CommonConst.HANDLER_MSG_DELEY_PUT_FOCUS);
						changeTextColor(mListViewAnimation.getCurrentFocusView(), CommonConst.FOCUS_STATE_LOSE_FOCUS);
						// 如果列表焦点已经到了最头部，再按上键则焦点移动到列表最尾部。（列表循环）
						int focusItemIndex = mListViewAnimation.getFocusItemIndex();
						Log.v(TAG, "shortOnKeyClick(UP)  focusItemIndex = " +focusItemIndex);
						if ( focusItemIndex== 0) {
							mFocusView.stopAnimY();
							mListViewAnimation.setFocusItemByPosition(mFocusView,mSettingItemAdapter.getCount() - 1);
							mHand.sendEmptyMessageDelayed(CommonConst.HANDLER_MSG_DELEY_PUT_FOCUS, 100);
							return true;
						}
						Log.v(TAG, "shortOnKeyClick(UP)   mListViewAnimation.up ");
						mListViewAnimation.Up(mFocusView,1, isLongOnclick);
						return true;
					case KeyEvent.KEYCODE_DPAD_DOWN:   //20
						Log.v(TAG, "shortOnKeyClick(DOWN) ");
						// 告知焦点发生变化
						mHand.removeMessages(CommonConst.HANDLER_MSG_DELEY_PUT_FOCUS);
						changeTextColor(mListViewAnimation.getCurrentFocusView(), CommonConst.FOCUS_STATE_LOSE_FOCUS);
						int focusItemIndex1 = mListViewAnimation.getFocusItemIndex();
						Log.v(TAG, "shortOnKeyClick(DOWN)  focusItemIndex1 = " +focusItemIndex1);
						if (focusItemIndex1 == mSettingItemAdapter.getCount() - 1) {
							Log.v(TAG,"shortOnKeyClick(DOWN)  getFocusItemIndex() == listAdapter.getCount() - 1");
							mFocusView.stopAnimY();
							mListViewAnimation.setFocusItemByPosition(mFocusView,0);
							mHand.sendEmptyMessageDelayed(CommonConst.HANDLER_MSG_DELEY_PUT_FOCUS, 100);
							return true;
						}			
						Log.v(TAG, "shortOnKeyClick(DOWN)   mListViewAnimation.down ");
						mListViewAnimation.Down(mFocusView, 1,isLongOnclick);
						return true;
					}
				}
				return true;
			}
			
			private void changeTextColor(View view,int state){
				if(state == CommonConst.FOCUS_STATE_GET_FOCUS){
					int focusItemIndex = mListViewAnimation.getFocusItemIndex();
					Log.v(TAG, "changeTextColor FOCUS_STATE_GET_FOCUS focusItemIndex = " +focusItemIndex);
				    mListViewAnimation.setFocusItemByPosition(mFocusView,focusItemIndex);
						if(view != null){
						((TextView) view.findViewById(R.id.setting_item_name)).setTextColor(mContext.getResources().getColor(R.color.white));					
					}
				
				}else if(state == CommonConst.FOCUS_STATE_LOSE_FOCUS){
					if(view != null){
						((TextView) view.findViewById(R.id.setting_item_name)).setTextColor(mContext.getResources().getColor(R.color.setting_item_text_normal));					
					}
				}
			}

			private void openSetting(){
				Log.d(TAG, "openSetting  isHkOrCn = " +isHkOrCn);
				if(isHkOrCn){
					Log.v(TAG, "now is CN method======");
					Intent mIntent = new Intent("com.tcl.settings.SHOW_WINDOW");
			        mIntent.setClassName("com.tcl.settings",  "com.tcl.settings.ShowWindowService");
			        mIntent.putExtra("Type", "Settings");
                    mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			           try {
							wholeMenuLayout.setVisibility(View.INVISIBLE);
							btnFocusImage.setVisibility(View.INVISIBLE);
							mContext.startService(mIntent);
						} catch (ActivityNotFoundException acti) {
							acti.printStackTrace();		
							new MyToast(mContext,mContext.getResources().getString(R.string.NoFuntion)).show();
						}
				}else{
					Log.v(TAG, "now is AU method======");
					try {
//						Intent intent_advance = new Intent();
//						intent_advance.setAction("android.intent.action.PICTURE");
//						mContext.startActivity(intent_advance);
					// for 5658 change method 20160727	
						Intent mIntent = new Intent("com.tcl.settings.SHOW_WINDOW");
						mIntent.setClassName("com.tcl.settings",
								"com.tcl.settings.ShowWindowService");
						mIntent.putExtra("flag", 1);
						mIntent.putExtra("Type", "Settings");
						mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						mContext.startService(mIntent);
						
						wholeMenuLayout.setVisibility(View.INVISIBLE);
						btnFocusImage.setVisibility(View.INVISIBLE);
					} catch (Exception e) {
						e.printStackTrace();
						new MyToast(mContext,mContext.getResources().getString(R.string.NoFuntion)).show();
					}
				}
			}
			
			
			private String getVideoChapters(){
				
				if(mVideoContrl != null){
					totalChapter = mVideoContrl.getChapters();
					mCurChapter = mVideoContrl.getCurrentChapter();
				}
				curChapterText = mContext.getResources().getString(R.string.chaptermore)+ mCurChapter;
				curSettingItem[TYPE_VIDEO_CHAPTER] = mCurChapter - 1;
				 
				
				return curChapterText;
			}
}

