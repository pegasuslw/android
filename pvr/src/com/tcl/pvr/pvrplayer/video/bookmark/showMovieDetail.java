package com.tcl.pvr.pvrplayer.video.bookmark;
//package sis.OnlineMedia.com;
//
//import java.io.ByteArrayInputStream;
//import java.io.File;
//import java.io.IOException;
//import java.io.InputStream;
//import java.net.MalformedURLException;
//import java.net.URL;
//import java.net.URLConnection;
//import java.util.ArrayList;
//import java.util.Random;
//import javax.xml.parsers.ParserConfigurationException;
//import javax.xml.parsers.SAXParser;
//import javax.xml.parsers.SAXParserFactory;
//import javax.xml.transform.TransformerException;
//import org.xml.sax.SAXException;
//import com.tcl.mediaplayer.MediaControlIntent;
//import com.tcl.mediaplayer.service.ICountService;
//import com.tcl.mediaplayer.service.MediaBean;
//import com.tcl.xian.StartandroidService.SqlCommon;
//
//import sis.OnlineMedia.com.R;
//import android.R.integer;
//import android.app.Activity;
//import android.app.ActivityManager;
//import android.app.Dialog;
//import android.content.BroadcastReceiver;
//import android.content.ComponentName;
//import android.content.ContentResolver;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.content.IntentFilter;
//import android.content.ServiceConnection;
//import android.database.Cursor;
//import android.graphics.Bitmap;
//import android.graphics.drawable.Drawable;
//import android.os.AsyncTask;
//import android.os.Bundle;
//import android.os.IBinder;
//import android.os.RemoteException;
//import android.sis.adapter.StringGridView;
//import android.sis.custom.CustomToast;
//import android.sis.custom.StartPlayModeDialog;
//import android.sis.custom.TCLDialog;
//import android.sis.custom.TCLProgressDialog;
//import android.sis.custom.questionDialog;
//import android.sis.network.NetWorkConnectionStatus;
//import android.sis.utils.BitmapUtils;
//import android.sis.utils.ConstantUtils;
//import android.sis.utils.DeskTopMovieInfo;
//import android.sis.utils.GlobalVariant;
//import android.sis.utils.MovieDetailInfo;
//import android.sis.utils.MoviePlayUrlInfo;
//import android.tcl.MediaPlayer.forceTVOperate;
//import android.tcl.dbservice.BookMarkDB;
//import android.tcl.decodeTPSXML.DecodeFilmInforXML;
//import android.tcl.decodeTPSXML.GetSortFilmInfor;
//import android.tclwidget.TCLCheckBox;
//import android.tclwidget.TCLRadioButton;
//import android.tclwidget.TCLSliderBar;
//import android.util.Log;
//import android.view.KeyEvent;
//import android.view.MotionEvent;
//import android.view.View;
//import android.view.Window;
//import android.view.WindowManager;
//import android.view.WindowManager.BadTokenException;
//import android.widget.AdapterView;
//import android.widget.Button;
//import android.widget.CheckBox;
//import android.widget.GridView;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.RadioGroup;
//import android.widget.TextView;
//
//public class showMovieDetail extends Activity {
//	private final String TAG = "showMovieDetail";
//	private ArrayList<String> mVolume = new ArrayList<String>();// 多少集
//	private ArrayList<String> mPlayUrl = new ArrayList<String>(); // 播放列表
//	private ImageView mLogoPicture;
//	
//	//private ImageView mStar01, mStar02, mStar03, mStar04, mStar05, mStar06, mStar07, mStar08, mStar09, mStar10;
//	private ImageView[] mStar = new ImageView[10];
//	private TextView mFilmName;
//	private TextView mFilmType;
//	private TextView mFilmDirector;
//	private TextView mFilmDuration;
//	private TextView mFilmPubDate;
//	private TextView mFilmLanguage;
//	private TextView mFilmDistrict;
//	private TextView mFilmRate;
//	private TextView mFilmActor;
//	private TextView mFilmShort;
//	private LinearLayout mDetailPad;
//	private String mParentName;
//	private String mChildName;
//	private String mPage;
//	public String mMovieTitle;
//	private String mFilmId;
//	
//	private Button mPlay;
//	public static Button mCollection;
//	private DeskTopMovieInfo hDeskTopMovieInfo  = new DeskTopMovieInfo();
//	public forceTVOperate hForceTVClient = new forceTVOperate();
//	public MovieDetailInfo mMovieDetailInfo = new MovieDetailInfo();
//	private TCLProgressDialog mpDialog = new TCLProgressDialog(this);
//	private getMySearch mGetDeskTopShortCutContent = new getMySearch();
//	private int mFrameFlag = 1;
//	public boolean mHadCollectionItem = false;
//	private String mDetailMode = "";
//
//	private BookMarkDB mBookMarkDB;
//
//	
//	private ContentResolver resolver;
//	
//	@Override
//	public void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.movie_detail);
//
//		resolver = getContentResolver();
//		getLicense();
//
//		initUI();
//		initData();
//	}
//	
//	@Override
//	public void onNewIntent(Intent intent) {
//		Log.v(TAG, "onNewIntent~~~~~~~~~~~~~");
//		
//		Bundle extras = intent.getExtras();
//		String movieName = extras.getString(ConstantUtils.CURRENT_MOVIENAME);
//		if(mMovieTitle.equals(movieName))
//			return;
//		else {
//			if(mTCLDialog != null)
//				mTCLDialog.dismiss();
//			
//			mLogoPicture.setImageBitmap(null);
//			mFilmName.setText("");
//			mFilmType.setText("");
//			mFilmDirector.setText("");
//			mFilmDuration.setText("");
//			mFilmPubDate.setText("");
//			mFilmLanguage.setText("");
//			mFilmDistrict.setText("");
//			mFilmRate.setText("");
//			mFilmActor.setText("");
//			mFilmShort.setText("");
//		}
//		mParentName = extras.getString(ConstantUtils.MAINMENU_NAME);
//		mPage = extras.getString(ConstantUtils.CURRENT_PAGE);
//		mChildName = extras.getString(ConstantUtils.SUBMENU_NAME);
//		mFilmId = extras.getString(ConstantUtils.FILM_ID);
//		
//		Log.v(TAG, "###################################");
//		Log.v(TAG, mParentName);
//		Log.v(TAG, mChildName);
//		Log.v(TAG, mMovieTitle);
//		if(mFilmId != null && mFilmId.length() > 0)
//			Log.v(TAG, mFilmId);
//		else
//			Log.v(TAG, "-1");
//		Log.v(TAG, "###################################");
//		
//		mDetailMode = extras.getString(ConstantUtils.DETAIL_MODE);// 进入详情的模式
//		
//		if (mParentName == null || mMovieTitle.length() == 0 || mPage.length() == 0)
//			return;
//		
//		if (mDetailMode.equals(ConstantUtils.DETAIL_DESKTOP) || mDetailMode.equals(ConstantUtils.DETAIL_NETWORK)) {// DETAIL_NETWORK
//			if (mDetailMode.equals(ConstantUtils.DETAIL_NETWORK)) {
//				//mDetailPad.setVisibility(View.INVISIBLE);
//				getMovieInfoFromFavorite(mMovieTitle);
//				mCollection.setText(this.getString(R.string.sis_delfavorite));		
//			}
//			else
//			{
//				try {// 只从网络收藏查询
//					mHadCollectionItem = getMyFavorite.SIS_GetMyFavoriteByName(mMovieTitle);
//					if (mHadCollectionItem)// 已经收藏了
//					{
//						mCollection.setText(this.getString(R.string.sis_delfavorite));
//					} else {
//						mCollection.setText(this.getString(R.string.sis_favorite));
//					}
//				} catch (ParserConfigurationException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				mDetailPad.setVisibility(View.INVISIBLE);
//				runOnUiThread(new Runnable() {			
//					@Override
//					public void run() {
//						mpDialog.showProgressLoading();
//						
//					}
//				});
//				
//				updateMovieLogorTask mUpdate = new updateMovieLogorTask(true);
//				mUpdate.execute();
//			}
//			
//		} else if (mDetailMode.equals(ConstantUtils.DETAIL_NORMAL)) {
//
//			try {// 只从网络收藏查询
//				mHadCollectionItem = getMyFavorite.SIS_GetMyFavoriteByName(mMovieTitle);
//				if (mHadCollectionItem)// 已经收藏了
//				{
//					mCollection.setText(this.getString(R.string.sis_delfavorite));
//				} else {
//					mCollection.setText(this.getString(R.string.sis_favorite));
//				}
//			} catch (ParserConfigurationException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//
//			SIS_GetMovieDetailsInfo(mParentName, mChildName, mPage, mMovieTitle);
//		} else {
//			return;
//		}
//		isFavoriteChange = false;
//	}
//
//	private void initUI() {
//		mFrameFlag = 1;
//		mFilmName = (TextView) this.findViewById(R.id.detail_movie_name);
//		mFilmType = (TextView) this.findViewById(R.id.TextView_type);
//		mFilmDirector = (TextView) this.findViewById(R.id.detail_movie_director);
//		mFilmDuration = (TextView) this.findViewById(R.id.TextView_long);
//		mFilmPubDate = (TextView) this.findViewById(R.id.TextView_date);// 出品
//		mFilmLanguage = (TextView) this.findViewById(R.id.TextView_language);
//		mFilmDistrict = (TextView) this.findViewById(R.id.TextView_district);
//		mFilmRate = (TextView) this.findViewById(R.id.TextView_rate);
//		mFilmActor = (TextView) this.findViewById(R.id.detail_movie_actor);
//		mFilmShort = (TextView) this.findViewById(R.id.detail_movie_desc);
//		mStar[0] = (ImageView) this.findViewById(R.id.Star01);
//		mStar[1] = (ImageView) this.findViewById(R.id.Star02);
//		mStar[2] = (ImageView) this.findViewById(R.id.Star03);
//		mStar[3] = (ImageView) this.findViewById(R.id.Star04);
//		mStar[4] = (ImageView) this.findViewById(R.id.Star05);
//		mStar[5] = (ImageView) this.findViewById(R.id.Star06);
//		mStar[6] = (ImageView) this.findViewById(R.id.Star07);
//		mStar[7] = (ImageView) this.findViewById(R.id.Star08);
//		mStar[8] = (ImageView) this.findViewById(R.id.Star09);
//		mStar[9] = (ImageView) this.findViewById(R.id.Star10);
//		mLogoPicture = (ImageView) this.findViewById(R.id.detail_movie_pic);
//		mPlay = (Button) this.findViewById(R.id.movie_play);
//		mCollection = (Button) this.findViewById(R.id.movie_collection);
//		mPlay.setOnClickListener(mPlayOnClickListener);
//		mPlay.setOnTouchListener(mPlayOnTouchListener);
//		mCollection.setOnClickListener(mCollectionOnClickListener);
//		mCollection.setOnTouchListener(mCollectionOnTouchListener);
//		mDetailPad = (LinearLayout) this.findViewById(R.id.download_detail_main_linearlayout);
//	}   
// 
//	private void getLicense() {
//		
//		if(ConstantUtils.Client_Type == null ||
//				ConstantUtils.Client_ID == null || 
//				ConstantUtils.Client_keytoken == null ||
//				ConstantUtils.User_ID == null ||
//				ConstantUtils.User_Type == null ||
//				ConstantUtils.User_keytoken == null) {
//		
//			//ContentResolver resolver = getContentResolver(); 
//			SqlCommon sqlcommon = new SqlCommon(); 
//			String deviceId = sqlcommon.getDum(resolver);
//			String deviceType = sqlcommon.getDeviceModel(resolver);
//			String deviceToken = sqlcommon.getDidtoken(resolver);
//			
//			String userID = sqlcommon.getHuanid(resolver);
//			String userToKen = sqlcommon.getToken(resolver);
//			
//			if(deviceType == null)
//				deviceType = "";
//			if(deviceId == null)
//				deviceId = "";
//			if(deviceToken == null)
//				deviceToken = "";
//			if(userID == null)
//				userID = "";
//			if(userToKen == null)
//				userToKen = "";
//			
//			
//			ConstantUtils.Client_Type=" type=\"" + deviceType + "\"";
//			ConstantUtils.Client_ID =" id=\""+ deviceId +"\"" ;
//			ConstantUtils.Client_keytoken =" keytoken=\""+ deviceToken +"\"";
//			
//			ConstantUtils.User_Type=" type=\"Normal\"";
//			ConstantUtils.User_ID =" id=\"" + userID + "\"" ;
//			ConstantUtils.User_keytoken =" keytoken=\"" + userToKen +"\"";
//		}
//		
//		Log.v(TAG, "#################### License Info #####################");
//		Log.v(TAG, ConstantUtils.Client_ID);
//		Log.v(TAG, ConstantUtils.Client_Type);
//		Log.v(TAG, ConstantUtils.Client_keytoken);
//		Log.v(TAG, ConstantUtils.User_ID);
//		Log.v(TAG, "Normal");
//		Log.v(TAG, ConstantUtils.User_keytoken);
//		Log.v(TAG, "#################### License Info #####################");
//	}
//	
//	
//	Button.OnClickListener mPlayOnClickListener = new Button.OnClickListener() {
//
//		public void onClick(View v) {
//			// TODO Auto-generated method stub
//			prepareToPlayMedia();
//		}
//	};
//	Button.OnTouchListener  mPlayOnTouchListener = new Button.OnTouchListener(){
//		public boolean onTouch(View v, MotionEvent event) {
//			// TODO Auto-generated method stub
//			if (event.getAction() == MotionEvent.ACTION_UP) {
//				if (!mPlay.hasFocus()) {
//					prepareToPlayMedia();
//				}
//			}
//			return false;
//		}		
//	};
//
//	private void prepareToPlayMedia() {
//		if (mVolume.size() > 1) {
//			// mm.setVisibility(View.INVISIBLE);
//			MovieVolumDialogBox mb = new MovieVolumDialogBox(showMovieDetail.this, "");
//			mb.show();
//		} else if (mVolume.size() == 1) {
//			if (mPlayUrl.size() >= 1) {
//				if (mPlayUrl.get(0).trim().length() > 0) {
//					if (!hForceTVClient.checkForceTVIsStart()) // 客户端没有启动
//					{
//						if (ConstantUtils.DBG)
//							Log.i(TAG, "客户端没有启动");
//						hForceTVClient.openForceTVTask();
//					}
//					mPlayPosition = 0;
//					String playPathString = getMoviePlayUrl(mPlayPosition);
//					if(playPathString == null) {	
//						Log.v(TAG, "4");
//						CustomToast.showTCLCustomToast(showMovieDetail.this, showMovieDetail.this.getString(R.string.SIS_Play_error), CustomToast.LENGTH_SHORT, CustomToast.WARNNING_ICON);
//						return;
//					
//					int n = mBookMarkDB.getPosFromDB(playPathString);
//					if(n == 0)
//						startPlayMovie(0, ConstantUtils.START_PLAY_FROM_BEGIN);
//					else {
//						StartPlayModeDialogDetail mStartDialog = new StartPlayModeDialogDetail(showMovieDetail.this, 0);
//						mStartDialog.show();
//					}
//				} else {
//					Log.v(TAG, "5");
//					CustomToast.showTCLCustomToast(showMovieDetail.this, showMovieDetail.this.getString(R.string.SIS_Play_error), CustomToast.LENGTH_SHORT, CustomToast.WARNNING_ICON);
//				}
//			}
//		} else {
//			Log.v(TAG, "6");
//			CustomToast.showTCLCustomToast(showMovieDetail.this, showMovieDetail.this.getString(R.string.SIS_Play_error), CustomToast.LENGTH_SHORT, CustomToast.WARNNING_ICON);
//		}
//	}
//	
//	
//	private static boolean isFavoriteChange = false;
//	
//	public void finish() {
//		if(isFavoriteChange) {
//			Intent aintent = new Intent(this, collectionContent.class);
//			setResult(1, aintent);
//			Log.v(TAG, "setResult");
//		}
//		
//		super.finish();
//	}
//	
//	
////	@Override
////	protected void onPause() {
////		Log.v(TAG, "onPause");
////		super.onPause();
////		finish();
////		
////	}
//	
////	@Override
////	protected void onResume() {
////		super.onResume();
////		Log.v(TAG, "onResume");
////		resolver = getContentResolver();
////		getLicense();
////
////		initUI();
////		initData();
////	}
////	
//	@Override
//	protected void onDestroy() {
//		
//		unregisterReceiver(hServiceReceiver);
//		
//		if (countService != null) {
//			unbindService(serviceConnection);
//			countService = null;
//		}
//		if (mList != null) {
//			mList.clear();
//		}
//		
//		if(mBookMarkDB != null) {
//			mBookMarkDB.close();
//			mBookMarkDB = null;
//		}	
////		ActivityManager am = (ActivityManager)getSystemService(ACTIVITY_SERVICE); 
////        am.killBackgroundProcesses("sis.OnlineMedia.com"); 
//		super.onDestroy();
//	}
//	
//	public static final int KEYCODE_BookMark = 115;
//
//	
//	Button.OnClickListener mCollectionOnClickListener = new Button.OnClickListener() {
//
//		public void onClick(View v) {
//			// TODO Auto-generated method stub
//			prepareToCollection();
//		}
//	};
//	Button.OnTouchListener  mCollectionOnTouchListener = new Button.OnTouchListener(){
//		public boolean onTouch(View v, MotionEvent event) {
//			// TODO Auto-generated method stub
//			if (event.getAction() == MotionEvent.ACTION_UP) {
//				if (!mCollection.hasFocus()) {
//					prepareToCollection();
//				}
//			}
//			return false;
//		}
//	};
//
//	private void prepareToCollection() {
//		if (mVolume.size() == 0) {
//			CustomToast.showTCLCustomToast(showMovieDetail.this, showMovieDetail.this.getString(R.string.SIS_collection_error), CustomToast.LENGTH_SHORT, CustomToast.WARNNING_ICON);
//			return;
//		}
//		
//		isFavoriteChange = true;
//		if (mCollection.getText().toString().trim().equals(showMovieDetail.this.getString(R.string.sis_favorite).trim())) {
//			// mGetMyFavorite.SIS_AddCurrentFilmOnFavorite();
//			addCollectionBroadCast();
//		} else {
//			// getMyFavorite.SIS_DelCurrentFilmOnFavorite();
//			try {
//				getMyFavorite.SIS_DeleteMyFavorite(mMovieTitle);
//				mCollection.setText(showMovieDetail.this.getString(R.string.sis_favorite));
//				CustomToast.showTCLCustomToast(showMovieDetail.this, showMovieDetail.this.getString(R.string.SIS_NOTICE3), CustomToast.LENGTH_SHORT, CustomToast.WARNNING_ICON);
//			} catch (ParserConfigurationException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (SAXException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (TransformerException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//	}
//	
//	private void initData() {
//		Bundle extras = getIntent().getExtras();
//		mMovieTitle = extras.getString(ConstantUtils.CURRENT_MOVIENAME);
//		mParentName = extras.getString(ConstantUtils.MAINMENU_NAME);
//		mPage = extras.getString(ConstantUtils.CURRENT_PAGE);
//		mChildName = extras.getString(ConstantUtils.SUBMENU_NAME);
//		mFilmId = extras.getString(ConstantUtils.FILM_ID);
//		
//		Log.v(TAG, "###################################");
//		Log.v(TAG, mParentName);
//		Log.v(TAG, mChildName);
//		Log.v(TAG, mMovieTitle);
//		if(mFilmId != null && mFilmId.length() > 0)
//			Log.v(TAG, mFilmId);
//		else
//			Log.v(TAG, "-1");
//		Log.v(TAG, "###################################");
//		
//		mDetailMode = extras.getString(ConstantUtils.DETAIL_MODE);// 进入详情的模式
//		
//		if (mParentName == null || mMovieTitle.length() == 0 || mPage.length() == 0)
//			return;
//		
//		if (mDetailMode.equals(ConstantUtils.DETAIL_DESKTOP) || mDetailMode.equals(ConstantUtils.DETAIL_NETWORK)) {// DETAIL_NETWORK
//			if (mDetailMode.equals(ConstantUtils.DETAIL_NETWORK)) {
//				//mDetailPad.setVisibility(View.INVISIBLE);
//				getMovieInfoFromFavorite(mMovieTitle);
//				mCollection.setText(this.getString(R.string.sis_delfavorite));		
//			}
//			else
//			{
//				try {// 只从网络收藏查询
//					mHadCollectionItem = getMyFavorite.SIS_GetMyFavoriteByName(mMovieTitle);
//					if (mHadCollectionItem)// 已经收藏了
//					{
//						mCollection.setText(this.getString(R.string.sis_delfavorite));
//					} else {
//						mCollection.setText(this.getString(R.string.sis_favorite));
//					}
//				} catch (ParserConfigurationException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				mDetailPad.setVisibility(View.INVISIBLE);
//				runOnUiThread(new Runnable() {			
//					@Override
//					public void run() {
//						mpDialog.showProgressLoading();
//						
//					}
//				});
//				
//				updateMovieLogorTask mUpdate = new updateMovieLogorTask(true);
//				mUpdate.execute();
//			}
//			
//		} else if (mDetailMode.equals(ConstantUtils.DETAIL_NORMAL)) {
//
//			try {// 只从网络收藏查询
//				mHadCollectionItem = getMyFavorite.SIS_GetMyFavoriteByName(mMovieTitle);
//				if (mHadCollectionItem)// 已经收藏了
//				{
//					mCollection.setText(this.getString(R.string.sis_delfavorite));
//				} else {
//					mCollection.setText(this.getString(R.string.sis_favorite));
//				}
//			} catch (ParserConfigurationException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//
//			SIS_GetMovieDetailsInfo(mParentName, mChildName, mPage, mMovieTitle);
//		} else {
//			return;
//		}
//		
//		IntentFilter hfilter = new IntentFilter();
//		hfilter.addAction(MediaControlIntent.ACTION_VIDEO_CONTROL_NEXT);
//		hfilter.addAction(MediaControlIntent.ACTION_VIDEO_CONTROL_PREVIOUS);
//		hfilter.addAction(MediaControlIntent.ACTION_VIDEO_CONTROL_COMPLETION);
//		hfilter.addAction(MediaControlIntent.ACTION_VIDEO_CONTROL_SAVE);
//		hfilter.addAction(ACTION_BOOKMARK);
//		hServiceReceiver = new ServiceReceiver();
//		registerReceiver(hServiceReceiver, hfilter);
//		isFavoriteChange = false;
//	}
//	
//	@Override
//	public boolean dispatchKeyEvent(KeyEvent event) {
//		if (event.getKeyCode() == KEYCODE_BookMark && event.getAction() == KeyEvent.ACTION_UP) {
//			prepareToCollection();
//			return true;
//		}
//		
//		return super.dispatchKeyEvent(event);
//	}
//
//	public void getMovieInfoFromFavorite(String MovieName) {
//
//		mVolume.clear();
//		mPlayUrl.clear();
//		if(GlobalVariant.mMovieDetailInfo !=null)
//			GlobalVariant.mMovieDetailInfo.clearData() ;
//		if (MovieName.trim().length() > 0) {
//			/****************************************************************
//			 * 修改成读取文件
//			 */
//			String mPath = ConstantUtils.FAVORITE_FILE_PATH;
//
//			File mfile = new File(mPath);
//			if (mfile != null) {
//				try {
//					SAXParserFactory factory = SAXParserFactory.newInstance();
//					SAXParser parser = factory.newSAXParser();
//					DecodeFilmInforXML filminfor = new DecodeFilmInforXML();
//					parser.parse(mfile, filminfor);
//					// ////////解析完成后，将每个对象的信息打印出来，这里很好
//					
//					for (GetSortFilmInfor film : filminfor.getObjectListFromXML()) {
//						if (film.getFilmTitle().trim().equalsIgnoreCase(MovieName)) {
//							
//							mMovieDetailInfo.clearData() ;
//							
//							mLogoPicture.setImageResource(R.drawable.empty001);
//							
//							mSmallPoster = film.getSmallPicURL();
//							
//							if (film.getBigPicURL() == null || film.getBigPicURL().length() == 0)
//								mDetailURL = film.getSmallPicURL();
//							else
//								mDetailURL = film.getBigPicURL();
//
//							try {
//								updateMovieLogorTask mUpdate = new updateMovieLogorTask(false);
//								mUpdate.execute();
//							} catch (Exception e) {
//								e.printStackTrace();
//							}
//							mFilmName.setText(film.getFilmTitle());
//							if (film.getDesc().length() > 85)
//								mFilmShort.setText(film.getDesc().substring(0, 70) + " ...");
//							else
//								mFilmShort.setText(film.getDesc());
//							
//							Log.v(TAG, film.getPT() + " ~~~~~~~~~");
//							
//							if(!ConstantUtils.isNumeric(film.getPT()))
//								mFilmPubDate.setText(this.getString(R.string.film_date) + film.getPT() );
//							else
//								mFilmPubDate.setText(this.getString(R.string.film_date) + film.getPT() + getString(R.string.nian));
//							
//							
//							mFilmLanguage.setText(this.getString(R.string.film_language) + film.getLanguage());
//							mFilmDistrict.setText(this.getString(R.string.film_district) + film.getArea());
//							mFilmDirector.setText(film.getDirector());
//							mFilmActor.setText(film.getActors());
//							if(film.lFileInfor.size()==1)
//							{
//								mFilmDuration.setText(this.getString(R.string.film_duration)+film.lFileInfor.get(0).getFile_Length()+this.getString(R.string.SIS_minutes));
//								mMovieDetailInfo.duration = film.lFileInfor.get(0).getFile_Length();
//							}
//							else if(film.getFileCount() > 1)
//							{
//								mFilmDuration.setText(this.getString(R.string.film_duration)+this.getString(R.string.SIS_duration));
//								mMovieDetailInfo.duration = this.getString(R.string.SIS_duration);
//							}
//							else
//								mFilmDuration.setText(this.getString(R.string.film_duration));
//							if(film.lFileInfor.size() >=1)
//							{
//								mFilmRate.setText(this.getString(R.string.film_rate)+film.lFileInfor.get(0).getFile_Rate()+this.getString(R.string.SIS_KBS));
//								mMovieDetailInfo.rate=film.lFileInfor.get(0).getFile_Rate();
//								
//								if(film.lFileInfor.get(0).getFile_Stype().trim().equalsIgnoreCase("0"))//点播
//								{
//									mFilmType.setText(this.getString(R.string.film_type)+this.getString(R.string.SIS_movieType1));
//									mMovieDetailInfo.stype=this.getString(R.string.SIS_movieType1);
//								}
//								else
//								{
//									mFilmType.setText(this.getString(R.string.film_type)+this.getString(R.string.SIS_movieType2));//直播
//									mMovieDetailInfo.stype=this.getString(R.string.SIS_movieType2);
//								}
//							}
//							else
//							{
//								mFilmRate.setText(this.getString(R.string.film_rate));
//							    mFilmType.setText(this.getString(R.string.film_type));
//							}
//							mMovieDetailInfo.actors = film.getActors();
//							mMovieDetailInfo.area = film.getArea();
//							mMovieDetailInfo.cn = film.getCN();
//							mMovieDetailInfo.desc = film.getDesc();
//							mMovieDetailInfo.director = film.getDirector();
//							mMovieDetailInfo.language = film.getLanguage();
//							mMovieDetailInfo.moviename = film.getFilmTitle();
//							mMovieDetailInfo.poster_big_url = film.getBigPicURL();
//							mMovieDetailInfo.poster_small_url = film.getSmallPicURL();
//							mMovieDetailInfo.pt = film.getPT();
//							mMovieDetailInfo.score = film.getScore();
//							mMovieDetailInfo.id = film.getFilmId();
//
//							
//							showEvaluteStarStatus(film.getScore());//显示评分情况
//							for (int i = 0; i < film.getFileCount(); i++) {
//								mVolume.add(i, getString(R.string.di) + Integer.toString(i + 1) + getString(R.string.ji));
//								mPlayUrl.add(i, film.lFileInfor.get(i).getFile_URL());
//								MoviePlayUrlInfo urlInfo = new MoviePlayUrlInfo();
//								urlInfo.url = film.lFileInfor.get(i).getFile_URL();
//								urlInfo.ci = film.lFileInfor.get(i).gerFile_CI();
//								urlInfo.format = film.lFileInfor.get(i).getFile_Format();
//								urlInfo.id = film.lFileInfor.get(i).getFile_ID();
//								urlInfo.length = film.lFileInfor.get(i).getFile_Length();
//								urlInfo.rate = film.lFileInfor.get(i).getFile_Rate();
//								urlInfo.size= film.lFileInfor.get(i).getFile_Size();
//								urlInfo.stype = film.lFileInfor.get(i).getFile_Stype();
//								urlInfo.vf = film.lFileInfor.get(i).getFile_VF();
//								mMovieDetailInfo.url.add(urlInfo);
//								Log.v(TAG, "mMovieDetailInfo.url " + i + " url " + film.lFileInfor.get(i).getFile_URL());
//							}
//							GlobalVariant.mMovieDetailInfo = mMovieDetailInfo;
//							break; // 找到匹配的名字之后就退出
//						}
//					}
//				} catch (ParserConfigurationException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				} catch (SAXException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//			if (mfile != null)
//				mfile.exists();
//		}
//
//	
//	}
//
//	
//	
//	/***************************************************************************************************
//	 * "在线观看" mCurrentMainClassName mCurrentSubClassName 1 ,电影详情获取函数,from
//	 * databases
//	 * 
//	 * @param strMainClassName
//	 * @param strSubClassName
//	 * @param strCurPage
//	 * @param MovieName
//	 ******************************************************************************************************/
//	public void SIS_GetMovieDetailsInfo(String strMainClassName, String strSubClassName, String strCurPage, String MovieName) {
//		mVolume.clear();
//		mPlayUrl.clear();
//		if(GlobalVariant.mMovieDetailInfo !=null)
//			GlobalVariant.mMovieDetailInfo.clearData() ;
//		if (MovieName.trim().length() > 0) {
//			/****************************************************************
//			 * 修改成读取文件
//			 */
//			String mPath = ConstantUtils.DATADIRECTORY;
//			String file = mPath + strMainClassName + "_" + strSubClassName + "_" + strCurPage + ".xml";
//			File mfile = new File(file);
//			if (mfile != null) {
//				try {
//					SAXParserFactory factory = SAXParserFactory.newInstance();
//					SAXParser parser = factory.newSAXParser();
//					DecodeFilmInforXML filminfor = new DecodeFilmInforXML();
//					parser.parse(mfile, filminfor);
//					// ////////解析完成后，将每个对象的信息打印出来，这里很好
//					
//					for (GetSortFilmInfor film : filminfor.getObjectListFromXML()) {
//						if (film.getFilmTitle().trim().equalsIgnoreCase(MovieName)) {
//							
//							mMovieDetailInfo.clearData() ;
//							
//							mLogoPicture.setImageResource(R.drawable.empty001);
//							
//							mSmallPoster = film.getSmallPicURL();
//							
//							if (film.getBigPicURL() == null || film.getBigPicURL().length() == 0)
//								mDetailURL = film.getSmallPicURL();
//							else
//								mDetailURL = film.getBigPicURL();
//
//							try {
//								updateMovieLogorTask mUpdate = new updateMovieLogorTask(false);
//								mUpdate.execute();
//							} catch (Exception e) {
//								e.printStackTrace();
//							}
//							mFilmName.setText(film.getFilmTitle());
//							if (film.getDesc().length() > 85)
//								mFilmShort.setText(film.getDesc().substring(0, 70) + " ...");
//							else
//								mFilmShort.setText(film.getDesc());
//							
//							Log.v(TAG, film.getPT() + " ~~~~~~~~~");
//							
//							if(!ConstantUtils.isNumeric(film.getPT()))
//								mFilmPubDate.setText(this.getString(R.string.film_date) + film.getPT() );
//							else
//								mFilmPubDate.setText(this.getString(R.string.film_date) + film.getPT() + getString(R.string.nian));
//							
//							
//							mFilmLanguage.setText(this.getString(R.string.film_language) + film.getLanguage());
//							mFilmDistrict.setText(this.getString(R.string.film_district) + film.getArea());
//							mFilmDirector.setText(film.getDirector());
//							mFilmActor.setText(film.getActors());
//							if(film.lFileInfor.size()==1)
//							{
//								mFilmDuration.setText(this.getString(R.string.film_duration)+film.lFileInfor.get(0).getFile_Length()+this.getString(R.string.SIS_minutes));
//								mMovieDetailInfo.duration = film.lFileInfor.get(0).getFile_Length();
//							}
//							else if(film.getFileCount() > 1)
//							{
//								mFilmDuration.setText(this.getString(R.string.film_duration)+this.getString(R.string.SIS_duration));
//								mMovieDetailInfo.duration = this.getString(R.string.SIS_duration);
//							}
//							else
//								mFilmDuration.setText(this.getString(R.string.film_duration));
//							if(film.lFileInfor.size() >=1)
//							{
//								mFilmRate.setText(this.getString(R.string.film_rate)+film.lFileInfor.get(0).getFile_Rate()+this.getString(R.string.SIS_KBS));
//								mMovieDetailInfo.rate=film.lFileInfor.get(0).getFile_Rate();
//								
//								if(film.lFileInfor.get(0).getFile_Stype().trim().equalsIgnoreCase("0"))//点播
//								{
//									mFilmType.setText(this.getString(R.string.film_type)+this.getString(R.string.SIS_movieType1));
//									mMovieDetailInfo.stype=this.getString(R.string.SIS_movieType1);
//								}
//								else
//								{
//									mFilmType.setText(this.getString(R.string.film_type)+this.getString(R.string.SIS_movieType2));//直播
//									mMovieDetailInfo.stype=this.getString(R.string.SIS_movieType2);
//								}
//							}
//							else
//							{
//								mFilmRate.setText(this.getString(R.string.film_rate));
//							    mFilmType.setText(this.getString(R.string.film_type));
//							}
//							mMovieDetailInfo.actors = film.getActors();
//							mMovieDetailInfo.area = film.getArea();
//							mMovieDetailInfo.cn = film.getCN();
//							mMovieDetailInfo.desc = film.getDesc();
//							mMovieDetailInfo.director = film.getDirector();
//							mMovieDetailInfo.language = film.getLanguage();
//							mMovieDetailInfo.moviename = film.getFilmTitle();
//							mMovieDetailInfo.poster_big_url = film.getBigPicURL();
//							mMovieDetailInfo.poster_small_url = film.getSmallPicURL();
//							mMovieDetailInfo.pt = film.getPT();
//							mMovieDetailInfo.score = film.getScore();
//							mMovieDetailInfo.id = film.getFilmId();
//							
//							showEvaluteStarStatus(film.getScore());//显示评分情况
//							for (int i = 0; i < film.getFileCount(); i++) {
//								mVolume.add(i, getString(R.string.di) + Integer.toString(i + 1) + getString(R.string.ji));
//								mPlayUrl.add(i, film.lFileInfor.get(i).getFile_URL());
//								MoviePlayUrlInfo urlInfo = new MoviePlayUrlInfo();
//								urlInfo.url = film.lFileInfor.get(i).getFile_URL();
//								urlInfo.ci = film.lFileInfor.get(i).gerFile_CI();
//								urlInfo.format = film.lFileInfor.get(i).getFile_Format();
//								urlInfo.id = film.lFileInfor.get(i).getFile_ID();
//								urlInfo.length = film.lFileInfor.get(i).getFile_Length();
//								urlInfo.rate = film.lFileInfor.get(i).getFile_Rate();
//								urlInfo.size= film.lFileInfor.get(i).getFile_Size();
//								urlInfo.stype = film.lFileInfor.get(i).getFile_Stype();
//								urlInfo.vf = film.lFileInfor.get(i).getFile_VF();
//								
//								mMovieDetailInfo.url.add(urlInfo);
//								Log.v(TAG, "mMovieDetailInfo.url " + i + " url " + film.lFileInfor.get(i).getFile_URL());
//								Log.v(TAG, "mMovieDetailInfo.url " + i + " length " + film.lFileInfor.get(i).getFile_Length());
//								Log.v(TAG, "mMovieDetailInfo.url " + i + " rate " + film.lFileInfor.get(i).getFile_Rate());
//							}
//							
//							GlobalVariant.mMovieDetailInfo = mMovieDetailInfo;
//							break; // 找到匹配的名字之后就退出
//						}
//					}
//				} catch (ParserConfigurationException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				} catch (SAXException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//			if (mfile != null)
//				mfile.exists();
//		}
//
//	}
//
//	private void showEvaluteStarStatus(String  strScore) {
//		if (strScore.trim().length() > 0) {
//			int score = 0;
//			try{
//				score = (int)Double.parseDouble(strScore.trim());
//			}
//			catch(Exception e) {
//
//			}
//			
//			for(int i = 9; i >= score; i--) {
//				mStar[i].setImageResource(R.drawable.star_unfill);
//			}
//		}
//	}
//	
//	/***************************************************************************************************
//	 * "在线观看" mCurrentMainClassName mCurrentSubClassName 1 ,电影详情获取函数,from
//	 * databases
//	 * 
//	 * @param strMainClassName
//	 * @param strSubClassName
//	 * @param strCurPage
//	 * @param MovieName
//	 ******************************************************************************************************/
//	public int SIS_GetDeskTopMovieDetailsInfo(String mContentString, String MovieName) {
//		mVolume.clear();
//		mPlayUrl.clear();
//		if(GlobalVariant.mMovieDetailInfo !=null)
//			GlobalVariant.mMovieDetailInfo.clearData() ;
//		
//		if (mContentString.trim().length() > 0) {
//			try {
//				SAXParserFactory factory = SAXParserFactory.newInstance();
//				SAXParser parser = factory.newSAXParser();
//				DecodeFilmInforXML filminfor = new DecodeFilmInforXML();
//				parser.parse(new ByteArrayInputStream(mContentString.getBytes()), filminfor);
//				boolean right = filminfor.getXMLRight();
//				boolean find = filminfor.findFilm();
//				if (!right ) {
//					
//					//CustomToast.showTCLDialogToast(this, "网络连接超时！ ", CustomToast.LENGTH_SHORT, CustomToast.WARNNING_ICON);
//					showTCLDialog(showMovieDetail.this.getString(R.string.SIS_NetConnectionTimeout));
//					return -1;
//				}
//				if(!find)
//				{
//					// 提示节目下线
//					offlineMessageBoxShown(MovieName, ConstantUtils.DELETEDESKTOPSHORTCUT);
//					return -2;
//				}
//				// 否则正常显示该影片的详细信息，这里可以使用normal函数的代码，但为了代码的清晰，部分代码copy了一下
//				for (GetSortFilmInfor film : filminfor.getObjectListFromXML()) {
//					if (film.getFilmTitle().trim().equalsIgnoreCase(MovieName)) {
//						mMovieDetailInfo.clearData() ;
//						
//						
//						mLogoPicture.setImageResource(R.drawable.empty001);
//						
//						mSmallPoster = film.getSmallPicURL();
//						
//						if (film.getBigPicURL() == null || film.getBigPicURL().length() == 0)
//							mDetailURL = film.getSmallPicURL();
//						else
//							mDetailURL = film.getBigPicURL();
//						try {
//							updateMovieLogorTask mUpdate = new updateMovieLogorTask(false);
//							mUpdate.execute();
//						} catch (Exception e) {
//							e.printStackTrace();
//						}
//						mFilmName.setText(film.getFilmTitle());
//						if (film.getDesc().length() > 85)
//							mFilmShort.setText(film.getDesc().substring(0, 70) + " ...");
//						else
//							mFilmShort.setText(film.getDesc());
//						
//						
//						if(!ConstantUtils.isNumeric(film.getPT()))
//							mFilmPubDate.setText(this.getString(R.string.film_date) + film.getPT() );
//						else {
//							mFilmPubDate.setText(this.getString(R.string.film_date) + film.getPT() + getString(R.string.nian));
//						}	
//						
//						mFilmLanguage.setText(this.getString(R.string.film_language) + film.getLanguage());
//						mFilmDistrict.setText(this.getString(R.string.film_district) + film.getArea());
//						mFilmDirector.setText(film.getDirector());
//						mFilmActor.setText(film.getActors());
//						if(film.lFileInfor.size()==1)
//						{
//							mFilmDuration.setText(this.getString(R.string.film_duration)+film.lFileInfor.get(0).getFile_Length()+this.getString(R.string.SIS_minutes));
//							mMovieDetailInfo.duration = film.lFileInfor.get(0).getFile_Length();
//						}
//						else if(film.getFileCount() > 1)
//						{
//							mFilmDuration.setText(this.getString(R.string.film_duration)+this.getString(R.string.SIS_duration));
//							mMovieDetailInfo.duration = this.getString(R.string.SIS_duration);
//						}
//						else
//							mFilmDuration.setText(this.getString(R.string.film_duration));
//						if(film.lFileInfor.size() >=1)
//						{
//							mFilmRate.setText(this.getString(R.string.film_rate)+film.lFileInfor.get(0).getFile_Rate()+this.getString(R.string.SIS_KBS));
//							mMovieDetailInfo.rate=film.lFileInfor.get(0).getFile_Rate();
//							
//							if(film.lFileInfor.get(0).getFile_Stype().trim().equalsIgnoreCase("0"))//点播
//							{
//								mFilmType.setText(this.getString(R.string.film_type)+this.getString(R.string.SIS_movieType1));
//								mMovieDetailInfo.stype=this.getString(R.string.SIS_movieType1);
//							}
//							else
//							{
//								mFilmType.setText(this.getString(R.string.film_type)+this.getString(R.string.SIS_movieType2));//直播
//								mMovieDetailInfo.stype=this.getString(R.string.SIS_movieType2);
//							}
//						}
//						else
//						{
//							mFilmRate.setText(this.getString(R.string.film_rate));
//						    mFilmType.setText(this.getString(R.string.film_type));
//						}
//						mMovieDetailInfo.actors = film.getActors();
//						mMovieDetailInfo.area = film.getArea();
//						mMovieDetailInfo.cn = film.getCN();
//						mMovieDetailInfo.desc = film.getDesc();
//						mMovieDetailInfo.director = film.getDirector();
//						mMovieDetailInfo.language = film.getLanguage();
//						mMovieDetailInfo.moviename = film.getFilmTitle();
//						mMovieDetailInfo.poster_big_url = film.getBigPicURL();
//						mMovieDetailInfo.poster_small_url = film.getSmallPicURL();
//						mMovieDetailInfo.pt = film.getPT();
//						mMovieDetailInfo.score = film.getScore();
//						mMovieDetailInfo.id = film.getFilmId();
//						
//						showEvaluteStarStatus(film.getScore());//显示评分情况
//						for (int i = 0; i < film.getFileCount(); i++) {
//							mVolume.add(i, getString(R.string.di) + Integer.toString(i + 1) + getString(R.string.ji));
//							mPlayUrl.add(i, film.lFileInfor.get(i).getFile_URL());
//							MoviePlayUrlInfo urlInfo = new MoviePlayUrlInfo();
//							urlInfo.url = film.lFileInfor.get(i).getFile_URL();
//							urlInfo.ci = film.lFileInfor.get(i).gerFile_CI();
//							urlInfo.format = film.lFileInfor.get(i).getFile_Format();
//							urlInfo.id = film.lFileInfor.get(i).getFile_ID();
//							urlInfo.length = film.lFileInfor.get(i).getFile_Length();
//							urlInfo.rate = film.lFileInfor.get(i).getFile_Rate();
//							urlInfo.size= film.lFileInfor.get(i).getFile_Size();
//							urlInfo.stype = film.lFileInfor.get(i).getFile_Stype();
//							urlInfo.vf = film.lFileInfor.get(i).getFile_VF();
//							mMovieDetailInfo.url.add(urlInfo);
//							Log.v(TAG, "mMovieDetailInfo.url " + i + " url " + film.lFileInfor.get(i).getFile_URL());
//						}
//						
//						GlobalVariant.mMovieDetailInfo = mMovieDetailInfo;
//						return 0; // 找到匹配的名字之后就退出
//					}
//				}
//			} catch (ParserConfigurationException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (SAXException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		} else {
//			// 提示节目下线，询问是否要删除
//			//offlineMessageBoxShown(MovieName, ConstantUtils.DELETEDESKTOPSHORTCUT);
//			//CustomToast.showTCLDialogToast(this, "网络连接超时！ ", CustomToast.LENGTH_SHORT, CustomToast.WARNNING_ICON);
//			showTCLDialog(showMovieDetail.this.getString(R.string.SIS_NetConnectionTimeout));
//			return -2;
//		}
//		return -2;
//
//	}
//
//	private void offlineMessageBoxShown(String MovieName, int mValue) {
//		questionDialog msg = new questionDialog(showMovieDetail.this, showMovieDetail.this.getString(R.string.SIS_offline), MovieName, mValue);
//		msg.setOnCancelListener(new DialogInterface.OnCancelListener() {
//			public void onCancel(DialogInterface dialog) {
//				// TODO Auto-generated method stub
//			}
//		});
//		msg.show();
//	}
//
//	/************************************************************************************
//	 * 
//	 * @param PicURL
//	 * @return
//	 ************************************************************************************/
//	private String mDetailURL = "";
//	private String mSmallPoster = null;
//	private Bitmap mLogoTmp = null;
//
//	private void showPicLogo(String PicURL) {
//		
//		Log.v(TAG, "showPicLogo~~~~~");
//		try {		
//			hDeskTopMovieInfo.clearData();
//			hDeskTopMovieInfo.mParentName = mParentName;
//			hDeskTopMovieInfo.mChildName = mChildName;
//			hDeskTopMovieInfo.mMovieTitle = mMovieTitle;
//			hDeskTopMovieInfo.mPage       = mPage;
//			hDeskTopMovieInfo.mId = mFilmId;
//			hDeskTopMovieInfo.mIcon = null ;
//			
//			GlobalVariant.mDeskTopMovieInfo = hDeskTopMovieInfo;
//			
//			if (PicURL == null || PicURL.length() == 0)
//				return;
//			
//			mLogoTmp = BitmapUtils.downLoadBitmap(PicURL);
//			
//			Bitmap icon = null;
//			icon = mLogoTmp;
//			if(icon == null) {
//				icon = BitmapUtils.downLoadBitmap(mSmallPoster);
//			}
//			
//			if(icon != null)
//				hDeskTopMovieInfo.mIcon = BitmapUtils.resizeAndRoundedCorner(icon);
//		} catch (OutOfMemoryError oom) {
//			mLogoTmp = null;
//			hDeskTopMovieInfo.mIcon = null;
//			oom.printStackTrace();
//			System.gc();
//			System.runFinalization();
//		} catch (MalformedURLException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//	}
//	
//
//	/*********************************************************************************
//	 * 异步任务，正常模式时为了下载图片；而在桌面快捷方式时为了下载文字和图片
//	 * 
//	 * @author lyp
//	 * 
//	 *********************************************************************************/
//	public class updateMovieLogorTask extends AsyncTask<Void, Void, Void> {
//
//		private boolean mIsTextContent = false;
//		private String mDetailContent = "";
//
//		public updateMovieLogorTask(boolean isText) {
//			mIsTextContent = isText;
//
//		}
//
//		protected void onPostExecute(Void mVar) {// 电影名称下载完毕，开始下载图片,名称还是一下子更新上去比较好
//			if (!mIsTextContent) {
//				if (mLogoTmp != null)
//					mLogoPicture.setImageBitmap(mLogoTmp);
//				mDetailURL = "";
//			} else {
//				
//				if(mDetailContent == null) {
//					mpDialog.cancleProgressLoading();
//					showTCLDialog(showMovieDetail.this.getString(R.string.SIS_NetConnectionTimeout));
//				}
//				else {
//					SIS_GetDeskTopMovieDetailsInfo(mDetailContent, mMovieTitle);
//					mDetailPad.setVisibility(View.VISIBLE);
//					mpDialog.cancleProgressLoading();
//				}
//			}
//
//		}
//
//		@Override
//		protected Void doInBackground(Void... params) {
//			
//			if(mBookMarkDB == null) {
//				
//				mBookMarkDB = new BookMarkDB(showMovieDetail.this);
//				Log.v(TAG, "new BookMarkDB ~~~~~~~");
//			}
//			
//			if (!mIsTextContent) {
//				showPicLogo(mDetailURL);
//			} else {
//				mDetailContent = mGetDeskTopShortCutContent.SIS_GetOneSearchContent(mMovieTitle);
//				//mDetailContent = mGetDeskTopShortCutContent.getMovieInfoByID(mFilmId);
//				Log.v(TAG, "doInBackground media info：" + mDetailContent);
//			}
//
//			return null;
//		}
//
//	}
//	
//	
//	
//
//	/*********************************************************************************
//	 * 电视剧剧集列表对话框
//	 * 
//	 * @author lyp
//	 * 
//	 *********************************************************************************/
//	private GridView mVolumGrid1 =null;
//	public class MovieVolumDialogBox extends Dialog {
//	//	private GridView mVolumGrid1;
//		private String TAG = "MessageBoxVol";
//		private TCLSliderBar mScrollbar;
//		private TextView mTitleName;
//
//		public MovieVolumDialogBox(Context context, String msg) {
//
//			super(context, R.style.messagebox);
//		}
//
//		protected void onCreate(Bundle savedInstanceState) {
//			super.onCreate(savedInstanceState);
//			initView();
//		}
//
//		private void initView() {
//			Window window = getWindow();
//			WindowManager.LayoutParams lp = window.getAttributes();
//			lp.alpha = 1.0f;
//			window.setAttributes(lp);
//			setContentView(R.layout.movies);
//			mVolumGrid1 = (GridView) findViewById(R.id.film_CI);
//			mScrollbar = (TCLSliderBar) findViewById(R.id.vseekbar);
//			mTitleName = (TextView) findViewById(R.id.movie_title_lable);
//			mScrollbar.setVisibility(View.INVISIBLE);
//			mVolumGrid1.setOnItemClickListener(mVolumGridOnItemClickListener1);
//			mVolumGrid1.setOnItemSelectedListener(mVolumGridOnItemSelectedListener1);
//			mVolumGrid1.setNextFocusDownId(R.id.film_CI);
//			mVolumGrid1.setNextFocusUpId(R.id.film_CI);
//			mVolumGrid1.setNextFocusLeftId(R.id.film_CI);
//			mVolumGrid1.setNextFocusRightId(R.id.film_CI);
//			mTitleName.setText(mMovieTitle+" ");
//			StringGridView mStringGridViewAdapter = new StringGridView(showMovieDetail.this, mVolume, 1);
//			mVolumGrid1.setAdapter(mStringGridViewAdapter);
//			mScrollbar.setMax(((mVolume.size() % 4 == 0) ? (mVolume.size() / 4) : (mVolume.size() / 4 + 1))-1);
//			if (mVolume.size() > 12) {
//				mScrollbar.setVisibility(View.VISIBLE);
//			} else {
//				mScrollbar.setVisibility(View.INVISIBLE);
//			}
//		}
//
//		GridView.OnItemClickListener mVolumGridOnItemClickListener1 = new GridView.OnItemClickListener() {
//			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
//				// TODO Auto-generated method stub
//				if (mPlayUrl.size() > arg2) {
//					if (mPlayUrl.get(arg2).trim().length() > 0) {
//						Log.i(TAG, mPlayUrl.get(arg2));
//						if (!hForceTVClient.checkForceTVIsStart()) // 客户端没有启动
//						{
//							if(ConstantUtils.DBG)
//								Log.i(TAG, "客户端没有启动");
//							hForceTVClient.openForceTVTask();
//						} else {
//							if(ConstantUtils.DBG)
//								Log.i(TAG, "客户端已经启动");
//						}
//						mPlayPosition = arg2;
//						
//						String mPlayPath = getMoviePlayUrl(mPlayPosition);
////						String ChanID = mPlayUrl.get(mPlayPosition).substring(mPlayUrl.get(mPlayPosition).lastIndexOf("/") + 1, mPlayUrl.get(mPlayPosition).lastIndexOf("."));
////						String Server = mPlayUrl.get(mPlayPosition).substring(7, mPlayUrl.get(mPlayPosition).lastIndexOf("/"));
////						String mPlayPath = hForceTVClient.readyToPlay(ChanID, Server);// 返回实际的播放地址 SIS_Play_error
//						
//						if(mPlayPath == null) {
//							Log.v(TAG, "1");
//							CustomToast.showTCLCustomToast(showMovieDetail.this, showMovieDetail.this.getString(R.string.SIS_Play_error), CustomToast.LENGTH_SHORT, CustomToast.WARNNING_ICON);
//							return;
//						}
//						int n = mBookMarkDB.getPosFromDB(mPlayPath);
//						if(n == 0)
//							startPlayMovie(arg2, ConstantUtils.START_PLAY_FROM_BEGIN);
//						else {
//							StartPlayModeDialogDetail mStartDialog = new StartPlayModeDialogDetail(showMovieDetail.this, arg2);
//							mStartDialog.show();
//						}
//					} else {
//						Log.v(TAG, "2");
//						CustomToast.showTCLCustomToast(showMovieDetail.this, showMovieDetail.this.getString(R.string.SIS_Play_error), CustomToast.LENGTH_SHORT, CustomToast.WARNNING_ICON);
//					}
//				}
//				
//				//dismiss();
//			}
//		};
//		
//		GridView.OnItemSelectedListener mVolumGridOnItemSelectedListener1 = new GridView.OnItemSelectedListener() {
//
//			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
//				// TODO Auto-generated method stub
//				if(ConstantUtils.DBG)
//					Log.i(TAG, "tag" + arg2);
//				mScrollbar.setProgress((arg2 / 4 + 0));
//				mScrollbar.invalidate();
//			}
//
//			public void onNothingSelected(AdapterView<?> arg0) {
//				// TODO Auto-generated method stub
//			}
//
//		};
//		
//
//		@Override
//		public boolean onKeyDown(int keyCode, KeyEvent event) {
//			if(ConstantUtils.DBG)
//				Log.d(TAG, "onKeyDown");
//			if ((keyCode == KeyEvent.KEYCODE_MENU || keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.KEYCODE_ENTER) && (event.getAction() == KeyEvent.ACTION_UP)) {
//				if(ConstantUtils.DBG)
//					Log.d(TAG, "onKeyDown dismiss");
//				mVolumGrid1=null;
//				dismiss();
//				// return true;
//			}
//			// return true;
//			return super.onKeyDown(keyCode, event);
//		}
//	}
//	
//	//////////////////////////////////////////////////////////////////////////////////////////
//	/*********************************************************************************
//	 * 影视播放调用接口
//	 * 
//	 * @author lyp
//	 * 
//	 *********************************************************************************/
//	//////////////////////////////////////////////////////////////////////////////////////////
//	private static final String ACTION_BOOKMARK = "com.tcl.sis.onlinevideoplayer.bookmark";
//	private String mCurrentUrl;
//	
//	
//	public ICountService countService = null;// 远程Service对象
//	ArrayList<MediaBean> mList = new ArrayList<MediaBean>(); // 播放列表
//	public int mPlayPosition = 0; // 开始播放的位置
//	private int mCurPos = 0 ;
//	
//	
//	private String getMoviePlayUrl(int mPos) {
//		Log.v(TAG, "########## getMoviePlayUrl total:" + mPlayUrl.size() + " play:" + mPos);
//		String uString = mPlayUrl.get(mPos);
//		Log.v(TAG, "getMoviePlayUrl " + uString);
//		
//		int s = uString.lastIndexOf("/") + 1;
//		int e = uString.lastIndexOf(".");
//		
//		Log.v(TAG, "getMoviePlayUrl s " + s + " e " + e);
//		
//		String ChanID = null;
//		if(s < uString.length() && e < uString.length() && s < e)
//			ChanID = uString.substring(s, e);
//		
//		String Server = null;
//		s = 7;
//		e = uString.lastIndexOf("/");
//		
//		Log.v(TAG, "getMoviePlayUrl s " + s + " e " + e);
//		if(s < uString.length() && e < uString.length() && s < e)
//			Server = uString.substring(s, e);
//		Log.v(TAG, "ChanID " + ChanID);
//		Log.v(TAG, "Server " + Server);
//		
//		String mPlayPath = null;
//		if(ChanID != null && Server != null) {
//			Log.v(TAG,"============================================================");
//			mPlayPath = hForceTVClient.readyToPlay(ChanID, Server);
//			Log.v(TAG, "mPlayPath " + mPlayPath);
//			return mPlayPath;
//		}else {
//			return null;
//		}
//	}
//	
//	public void startPlayMovie(int mPos, int playMode) {
//		if (countService != null) {
//			unbindService(serviceConnection);
//			countService = null;
//		}
//		if (mList != null)
//			mList.clear();
//		
//		Log.v(TAG, "##########################    total:" + mPlayUrl.size() + " play:" + mPos);
//		Log.v(TAG, "####### play url: " + mPlayUrl.get(mPos));
//		
//		if (mPos >= mPlayUrl.size() || mPos < 0)
//			return;
//		if(mVolumGrid1 !=null)
//		{
//			mVolumGrid1.setSelection(mPos);
//		}
//		mCurPos =  mPos;
//		// ,地址格式
//		// http://124.40.120.110:9906/4c9f2aaa000c6caa61307a4d04ca463c.ts//我的雷人男友
//		String mFileEx = mPlayUrl.get(mPos).substring(mPlayUrl.get(mPos).lastIndexOf(".") + 1, mPlayUrl.get(mPos).length()).toLowerCase();
//		if (mFileEx != null) {
//			if (mFileEx.equalsIgnoreCase("ts") || mFileEx.equalsIgnoreCase("mp4") || mFileEx.equalsIgnoreCase("mp3") || mFileEx.equalsIgnoreCase("avi") || mFileEx.equalsIgnoreCase("mov")
//					|| mFileEx.equalsIgnoreCase("flv") || mFileEx.equalsIgnoreCase("rmvb")) {
//				// go on with the following ;
//			} else {
//				CustomToast.showTCLCustomToast(showMovieDetail.this, showMovieDetail.this.getString(R.string.zhanbuzhichi) + mFileEx + showMovieDetail.this.getString(R.string.geshi), CustomToast.LENGTH_SHORT, CustomToast.WARNNING_ICON);
//				return;
//			}
//		} else {
//			Log.v(TAG, "3");
//			CustomToast.showTCLCustomToast(showMovieDetail.this, showMovieDetail.this.getString(R.string.SIS_Play_error), CustomToast.LENGTH_SHORT, CustomToast.WARNNING_ICON);
//			return;
//		}
//
//		String ChanID = mPlayUrl.get(mPos).substring(mPlayUrl.get(mPos).lastIndexOf("/") + 1, mPlayUrl.get(mPos).lastIndexOf("."));
//		String Server = mPlayUrl.get(mPos).substring(7, mPlayUrl.get(mPos).lastIndexOf("/"));
//		String mPlayPath = hForceTVClient.readyToPlay(ChanID, Server);// 返回实际的播放地址
//		MediaBean mediaBean = new MediaBean();
//		mediaBean.mType = "video";// 视频（video）&音频		
//		mediaBean.mPath = mPlayPath;// 播放路径
//		mediaBean.mURLType = "VOD";
//		mediaBean.mTotalSize = mPlayUrl.size(); 
//		mediaBean.mToContinue = 1;
//		
//		if(playMode == ConstantUtils.START_PLAY_FROM_BREAK)
//			mediaBean.mLastPlayPosition = mBookMarkDB.getPosFromDB(mediaBean.mPath);
//		
//		if(playMode == ConstantUtils.START_PLAY_FROM_BEGIN)
//			mediaBean.mLastPlayPosition = 0;
//		
//		Log.v(TAG, "startPlayMovie " + mMovieTitle + " at " + mediaBean.mLastPlayPosition);
//		
//		if(mPlayUrl.size() >1)
//			mediaBean.mName = mMovieTitle + "." + mFileEx + "      "+ showMovieDetail.this.getString(R.string.di) + (mCurPos+1)+showMovieDetail.this.getString(R.string.ji);// 显示的名字
//		else
//			mediaBean.mName = mMovieTitle + "." + mFileEx ;
//		if (mCollection.getText().toString().equalsIgnoreCase(this.getString(R.string.sis_delfavorite)))// 已经收藏了
//		{
//			mediaBean.mIsSaved = 1 ;//已经收藏了
//		} else {
//			mediaBean.mIsSaved = 0 ;////还没有收藏
//		}		
//		mList.add(0, mediaBean);
//		if (countService == null) {
//			boolean mValue = this.bindService(new Intent("com.tcl.mediaservice.remotebinder"), this.serviceConnection, BIND_AUTO_CREATE);
//			if (!mValue) {
//				this.bindService(new Intent("com.tcl.mediaservice.remotebinder"), this.serviceConnection, BIND_AUTO_CREATE);
//				mCurrentUrl = mediaBean.mPath;
//			}
//		} 
//		else {
//			try {
//				countService.play(mList, mCurPos);
//			} catch (RemoteException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//	}
//	
//	private ServiceConnection serviceConnection = new ServiceConnection() {
//
//		public void onServiceConnected(ComponentName name, IBinder service) {
//			// TODO Auto-generated method stub
//			countService = (ICountService.Stub.asInterface(service));
//			try {
//				countService.play(mList, mCurPos);
//				if(ConstantUtils.DBG)
//					Log.i(TAG, "绑定服务成功!");
//
//			} catch (RemoteException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//
//		public void onServiceDisconnected(ComponentName name) {
//			// TODO Auto-generated method stub
//			countService = null;
//			mCurrentUrl = null;
//		}
//	};
//
//	
//
//	/*********************************************************************************
//	 * 
//	 * @author lyp
//	 * @des    播放器播放模式设置
//	 * 
//	 **********************************************************************************/
//	// 播放器播放类型flag.
//	private ServiceReceiver hServiceReceiver;
//	private Random mRandom = new Random();
//	public class ServiceReceiver extends BroadcastReceiver {
//		@Override
//		public void onReceive(Context context, Intent intent) {	
//			
//			if (intent.getAction().trim().equals(MediaControlIntent.ACTION_VIDEO_CONTROL_NEXT) || 
//                    intent.getAction().trim().equals(MediaControlIntent.ACTION_VIDEO_CONTROL_PREVIOUS) ||
//                    intent.getAction().trim().equals(MediaControlIntent.ACTION_VIDEO_CONTROL_COMPLETION)) {
//				
//				
//				Log.v(TAG, "#######onReceive: " + intent.getAction().trim());
//				 int playMode = intent.getFlags();
//
//				 switch (playMode) {
//                 case ConstantUtils.FLAG_MEDIA_CYCLE_PLAY: // cycle repeat mode
//                         if (intent.getAction().trim().equals(MediaControlIntent.ACTION_VIDEO_CONTROL_NEXT) ||
//                                         intent.getAction().trim().equals(MediaControlIntent.ACTION_VIDEO_CONTROL_COMPLETION)) {
//                                 mPlayPosition = mPlayPosition >= mPlayUrl.size()-1 ? 0 : ++mPlayPosition;
//                                 startPlayMovie(mPlayPosition, ConstantUtils.START_PLAY_FROM_BEGIN);
//                         } else if (intent.getAction().trim().equals(MediaControlIntent.ACTION_VIDEO_CONTROL_PREVIOUS)) {
//                                 mPlayPosition = mPlayPosition <= 0 ? mPlayUrl.size()-1 : --mPlayPosition;
//                                 startPlayMovie(mPlayPosition, ConstantUtils.START_PLAY_FROM_BEGIN);
//                         }
//                         break;
//                 
//                 case ConstantUtils.FLAG_MEDIA_SEQUENCE_PLAY: // sequence mode
//                         if (intent.getAction().trim().equals(MediaControlIntent.ACTION_VIDEO_CONTROL_NEXT) ||
//                                         intent.getAction().trim().equals(MediaControlIntent.ACTION_VIDEO_CONTROL_COMPLETION)) {
//                                 mPlayPosition++;
//                                 startPlayMovie(mPlayPosition, ConstantUtils.START_PLAY_FROM_BEGIN);
//                         } else if (intent.getAction().trim().equals(MediaControlIntent.ACTION_VIDEO_CONTROL_PREVIOUS)) {
//                                 mPlayPosition--;
//                                 startPlayMovie(mPlayPosition, ConstantUtils.START_PLAY_FROM_BEGIN);
//                         }
//                         break;
//
//                 case ConstantUtils.FLAG_MEDIA_SINGLE_PLAY:
//                         if (intent.getAction().trim().equals(MediaControlIntent.ACTION_VIDEO_CONTROL_NEXT)) {
//                                 mPlayPosition++;
//                                 startPlayMovie(mPlayPosition, ConstantUtils.START_PLAY_FROM_BEGIN);
//                         } else if (intent.getAction().trim().equals(MediaControlIntent.ACTION_VIDEO_CONTROL_PREVIOUS)) {
//                                 mPlayPosition--;
//                                 startPlayMovie(mPlayPosition, ConstantUtils.START_PLAY_FROM_BEGIN);
//                         } else if (intent.getAction().trim().equals(MediaControlIntent.ACTION_VIDEO_CONTROL_COMPLETION)) {
//                                 return;
//                         }
//                         break;
//         
//                 case ConstantUtils.FLAG_MEDIA_SINGLE_REPEAT_PLAY:
//                         if (intent.getAction().trim().equals(MediaControlIntent.ACTION_VIDEO_CONTROL_NEXT)) {
//                                 mPlayPosition++;
//                                 startPlayMovie(mPlayPosition, ConstantUtils.START_PLAY_FROM_BEGIN);
//                         } else if (intent.getAction().trim().equals(MediaControlIntent.ACTION_VIDEO_CONTROL_PREVIOUS)) {
//                                 mPlayPosition--;
//                                 startPlayMovie(mPlayPosition, ConstantUtils.START_PLAY_FROM_BEGIN);
//                         } else if (intent.getAction().trim().equals(MediaControlIntent.ACTION_VIDEO_CONTROL_COMPLETION)) {
//                                 startPlayMovie(mPlayPosition, ConstantUtils.START_PLAY_FROM_BEGIN);
//                         }
//                         break;
//                 
//                 case ConstantUtils.FLAG_MEDIA_RANDOM_PLAY:
//                         if (intent.getAction().trim().equals(MediaControlIntent.ACTION_VIDEO_CONTROL_NEXT) || 
//                                intent.getAction().trim().equals(MediaControlIntent.ACTION_VIDEO_CONTROL_PREVIOUS) ||
//                                intent.getAction().trim().equals(MediaControlIntent.ACTION_VIDEO_CONTROL_COMPLETION)) {
//                        	 
//                                mPlayPosition = mRandom.nextInt(mPlayUrl.size());
//                                startPlayMovie(mPlayPosition, ConstantUtils.START_PLAY_FROM_BEGIN);
//                         }
//                 
//                 default:
//                         break;
//                 }
//			}
//			
//			if(intent.getAction().trim().equals(MediaControlIntent.ACTION_VIDEO_CONTROL_SAVE))// 收藏
//			{
//				int nType = intent.getFlags();
//				if(nType== MediaControlIntent.FLAG_VIDEO_CONTROL_SAVE)//收藏
//				{
//					if(ConstantUtils.DBG)
//						Log.i(TAG, "添加收藏" );
//					// mGetMyFavorite.SIS_AddCurrentFilmOnFavorite();		         
//					addCollectionBroadCast() ;
//				}
//				else if(nType== MediaControlIntent.FLAG_VIDEO_CONTROL_CANCEL)//取消收藏
//				{
//					if(ConstantUtils.DBG)
//						Log.i(TAG, "取消收藏" );
//					try {
//						getMyFavorite.SIS_DeleteMyFavorite(mMovieTitle);
//						mCollection.setText(showMovieDetail.this.getString(R.string.sis_favorite));
//						isFavoriteChange = true;
//						CustomToast.showTCLCustomToast(context, context.getString(R.string.SIS_NOTICE3), CustomToast.LENGTH_SHORT, CustomToast.WARNNING_ICON);
//					} catch (ParserConfigurationException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					} catch (SAXException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					} catch (IOException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					} catch (TransformerException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//				}				
//			}
//			
//			if(intent.getAction().equals(ACTION_BOOKMARK)) {
//				String name = intent.getExtras().getString("name");  	// 影视名称
//				String path = intent.getExtras().getString("path"); 	// 影视播放路径
//				int postion = intent.getExtras().getInt("position");  	// 影视上次播放				
//				
//				Log.v(TAG, "recive ACTION_BOOKMARK");
//				
//				if(mBookMarkDB == null)
//					return;
//				
//				if(postion == 0) {
//					if(mBookMarkDB.isUrlInBookmark(path)) {
//						mBookMarkDB.delete(path);
//						//CustomToast.showTCLCustomToast(showMovieDetail.this, name + " 从书签删除", CustomToast.LENGTH_SHORT, CustomToast.WARNNING_ICON);
//						Log.v(TAG, "bookmark delete " + name);
//					}
//				}
//				else {
//					if(mBookMarkDB.isUrlInBookmark(path)) {
//						mBookMarkDB.update(path, postion);
//						//CustomToast.showTCLCustomToast(showMovieDetail.this, name + " 更新书签成功", CustomToast.LENGTH_SHORT, CustomToast.WARNNING_ICON);
//						Log.v(TAG, "bookmark update " + name + " at " + postion);
//					}
//					else {
//						mBookMarkDB.insert(name, path, postion);
//						//CustomToast.showTCLCustomToast(showMovieDetail.this, name + " 成功加入书签！", CustomToast.LENGTH_SHORT, CustomToast.WARNNING_ICON);
//						Log.v(TAG, "bookmark insert " + name + " at " + postion);
//					}
//				}
//			}
//		}
//	}
//	
//	private void addCollectionBroadCast(/*Context  context*/) {
//		try {
//			GlobalVariant.mFirstCoverName = getMyFavorite.SIS_GetFirstMyFavorite();
//		} catch (ParserConfigurationException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		Intent it = new Intent(ConstantUtils.ONLINE_MEDIA_PACKAGE);
//		Bundle bundle = new Bundle();
//		bundle.putString(ConstantUtils.FIRST_COLLECTED_MOVIE_NAME, GlobalVariant.mFirstCoverName);
//		bundle.putInt(ConstantUtils.COLLECTED_MOVIE_COUNT, getMyFavorite.SIS_GetMyFavoriteCount());
//		it.putExtras(bundle);
//		sendBroadcast(it);
//		//MyCollectionMonitoringReceiver.onReceive(context, ConstantUtils.mFirstCoverName, getMyFavorite.SIS_GetMyFavoriteCountVar());
//		//mCollection.setText(showMovieDetail.this.getString(R.string.sis_delfavorite));
//		Log.v(TAG, GlobalVariant.mFirstCoverName);
//		Log.v(TAG, "count:" + getMyFavorite.SIS_GetMyFavoriteCount());
//		Log.v(TAG, "addCollectionBroadCast over");
//	}
//	 /*
//	  * 超时退出的提示dialog
//	  */
//	private TCLDialog  mTCLDialog = null;
//	private void showTCLDialog(String tip) {
//		if (mTCLDialog == null) {
//			mTCLDialog = new TCLDialog(showMovieDetail.this, tip);
//			mTCLDialog.setCallback(new TCLDialog.Callback() {
//				public void onCannel() {
//					showMovieDetail.this.finish();
//				}
//			});
//		}
//		try {
//			mTCLDialog.show();
//		} catch (BadTokenException e) {
//			
//		} catch (IllegalStateException e) {
//		}
//	}
//	
//	private class StartPlayModeDialogDetail extends StartPlayModeDialog {
//
//		public StartPlayModeDialogDetail(Context c, int pos) {
//			super(c, pos);
//		}
//		
//		protected void startPlay() {
//			((showMovieDetail)mContex).startPlayMovie(mPos, mStartPlayMode);
//		}
//		
//	}
//	
//}