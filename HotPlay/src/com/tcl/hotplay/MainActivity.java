package com.tcl.hotplay;

import java.util.List;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OnItemClickListener;
import android.support.v7.widget.OnItemSelectedListener;
import android.support.v7.widget.RecyclerView.AdapterDataObserver;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.support.v7.widget.TclRecyclerView;
import android.tclwidget.TCLToast;
import android.util.Log;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.tcl.cyberui.action.AppBlockResourceAction;
import com.tcl.cyberui.entry.BlockActionBean;
import com.tcl.entity.HotPlayInfo;
import com.tcl.hotplay.HotplayAdapter.FruitViewHolder;
import com.tcl.util.HttpUtil;
import com.tcl.vod.view.WaitDialog;

public class MainActivity extends Activity {

	 private static final String TAG = "MainActivity";
	 
	 public static Handler mHandler = null;  //在oncreate附近，handler比View的靠谱
	 public static final int WAIT_DATA_MSG = 1;
	 public static final int SUCCESS_DATA_MSG = 2;
	 public static final int FAIL_DATA_MSG = 3;

	 private TclRecyclerView mRecyclerView=null;  
	 private TextView mLastSelectedTextView = null;
	 private  WaitDialog mWaitDialog = null;

	 private static final int SPAN_COUNT = 2;
	 private static final int FLY_TIME = 400;
	 private static final int BUTTON_DISABLE_TIME = FLY_TIME*3;
	 private  long mLastClickButtonTime  = System.currentTimeMillis();  
//	 private boolean hasAnimat = false;
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i(TAG,"onCreate()");
		long time = System.currentTimeMillis();
		
		//设置显示
		setContentView(R.layout.activity_main);
		Log.i(TAG,"setContentView(R.layout.activity_main); pastTime = "+(System.currentTimeMillis() - time));
		
		initHandler();
		Log.i(TAG,"mHandler = new Handler(); pastTime = "+(System.currentTimeMillis() - time));		
		
    	initView();
    	
    	initWaitOrUpdata();
	}



	@Override
	protected void onResume() {
		super.onResume();
		//long time = System.currentTimeMillis();
		//不在oncreat里请求数据，延时一点请求，是为了第一次尽早显示画面。
		initHttpAndGet();
		
		Log.i(TAG,"onResume(),pastTime="+(System.currentTimeMillis()-MyApplication.time));
	}

	private void initHttpAndGet() {
		if(HotPlayGetUtil.getTotalList()==null||HotPlayGetUtil.getTotalList().size()==0){
			Log.i(TAG,"onResume();HotPlayGetUtil.getTotalList()==null||");
			//在第三方线处理okhttp初始化，是为了不占用主线程显示时间。
			//第一次进入时，数据为null。	
			new Thread(){
				public void run(){
					//初始化Http空间
					HttpUtil.init(MainActivity.this);  //耗时几十毫秒。
					//Log.i(TAG,"HttpUtil.init(this); pastTime = "+(System.currentTimeMillis() - time));
					//获取数据
					Log.d("liuwei03", "getvideolistdata 1");
					HotPlayGetUtil.getVideoListData();
					//Log.i(TAG,"HotPlayGetUtil.getVideoListData(); pastTime = "+(System.currentTimeMillis() - time));
				}
			}.start();
		}
	}
	
	private void initHandler() {
		mHandler = new Handler(){

			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
					switch (msg.what) {//根据收到的消息的what类型处理  
			        case WAIT_DATA_MSG:  
			    		if(mWaitDialog==null){
			    			mWaitDialog = new WaitDialog(MainActivity.this);
			    		}
			    		mWaitDialog.showWaiteDialog();
			    		//其实，只有第一次要这是监听器。
				    	mWaitDialog.setOnCancelListener(new OnCancelListener(){
				    			@Override
				    			public void onCancel(DialogInterface dialog) {
				    				Log.i(TAG,"onCancel(DialogInterface dialog) ");
				    				//如果有人用Back键关掉等待框，那么结束此Activity
				    				MainActivity.this.onBackPressed();
				    				//MainActivity.this.finish();
				    				//考虑是否要结束应用。			
				    			}
				    	});
			            break;  
			        case SUCCESS_DATA_MSG:  
			        	mWaitDialog.dissMissWaiteDialog();
						//mRecyclerView.getAdapter().notifyDataSetChanged();	
						updataPage();
			            break;  
			        case FAIL_DATA_MSG:  
						mWaitDialog.dissMissWaiteDialog();
						TCLToast.makeText(MainActivity.this,R.string.net_error_load_fail, TCLToast.LENGTH_LONG).show();
			            break;  
			        default:  
			            super.handleMessage(msg);//这里最好对不需要或者不关心的消息抛给父类，避免丢失消息  
			            break;  
				}
			}
			
		};
	}

	private void initView() {
		//下一帧去初始化View的控制。其中，RecyclerView延时加载。
    	mHandler.post(new Runnable(){
			@Override
			public void run() {
				Log.i(TAG,"mHandler.post,pastTime="+(System.currentTimeMillis()-MyApplication.time));
				initRecycerView();
				Log.i(TAG,"initRecycerView(); pastTime = "+(System.currentTimeMillis() - MyApplication.time));
				
				initAdapter();
				Log.i(TAG,"initAdapter();   pastTime = "+(System.currentTimeMillis() - MyApplication.time));
				
				initButton();  
				Log.i(TAG,"initButton();  pastTime = "+(System.currentTimeMillis() - MyApplication.time));
			}
    	});
	}
	
	private void initWaitOrUpdata() {
		//数据处理。
    	if(HotPlayGetUtil.getTotalList()==null||HotPlayGetUtil.getTotalList().size()==0){
			//第一次多调用 无数据时显示对话框。
			MainActivity.mHandler.sendEmptyMessage(MainActivity.WAIT_DATA_MSG);
    	}else{
    		 //有数据时，更新数据。
    		updataPage();	
    	}
	}
	
	private void initRecycerView() {
		if(mRecyclerView!=null){
			//已经初始化过了，不用再次初始化。
			return;
		}
		// ViewStub方式二  
        ViewStub viewStub = (ViewStub) findViewById(R.id.stub_import) ;  
        // 成员变量commLv2为空则代表未加载  

	    // 加载评论列表布局, 并且获取评论ListView,inflate函数直接返回ListView对象  
        mRecyclerView = (TclRecyclerView)viewStub.inflate();    
		//查找显示元素
		//mRecyclerView = (TclRecyclerView) findViewById(R.id.my_recycler_view);  

       // improve performance if you know that changes in content  
       // do not change the size of the RecyclerView  
       mRecyclerView.setHasFixedSize(true);  
 
       // use a grid layout manager  
       GridLayoutManager gridLayoutManager = new GridLayoutManager(this,SPAN_COUNT);
       gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
       mRecyclerView.setLayoutManager(gridLayoutManager);
       mRecyclerView.setItemViewCacheSize(0);
       
       
       mRecyclerView.setAdapter(new HotplayAdapter());  
//       //出场动画
//       MainActivity.mHandler.post(new Runnable(){
//			@Override
//			public void run() {
//				mRecyclerView.scheduleLayoutAnimation();
//			} 
//       });
       
       /****************************这里是TclRecyclerView的属性设置start*****************************************************/
       //以下所有属性，都是可选配置，不配置也可以。
       //设置选中框的View。
       ImageView selectedRectView = (ImageView)findViewById(R.id.selected_rect_view);
       int left = (int)selectedRectView.getTranslationX();
       int top = (int)selectedRectView.getTranslationY();
       int right = selectedRectView.getPaddingRight();
       int bottom = selectedRectView.getPaddingBottom();
       
//       int left = getResources().getDimensionPixelSize(R.dimen.selected_left);
//       int top = getResources().getDimensionPixelSize(R.dimen.selected_top);
//       int right = getResources().getDimensionPixelSize(R.dimen.selected_right);
//       int bottom = getResources().getDimensionPixelSize(R.dimen.selected_bottom);
       
       Rect selectedRect = new Rect(left, top, right, bottom);
       //设置选中框大小。因为选中框图片往往带有阴影，或者仅想套在图片上，不想套在文字上。需要精确控制选中框位置时，可以用这个接口。
       mRecyclerView.setSelectedRect(selectedRectView,selectedRect);
       
//        ImageView topShadeView = new ImageView(this);
//		ImageView bottomShadeView = new ImageView(this);
//		topShadeView.setBackgroundResource(R.drawable.shade_up);
//		bottomShadeView.setBackgroundResource(R.drawable.shade_down);
//		mRecyclerView.setShade(topShadeView, bottomShadeView, 1.0f);

       //设置选中框位置索引
       mRecyclerView.setSelectedAdapterPosition(HotPlayGetUtil.PAGE_SIZE-1);
//       //设置滚动动画时间
       mRecyclerView.setSelectedFlyTime(FLY_TIME);
//       //设置滚动动画差值器
//       mRecyclerView.setSelectedFlyInterpolator(new DecelerateInterpolator());
//       //设置滚动时带有弹性
//       mRecyclerView.setIsRollWithElastic(true);
       //设置分割线。不推荐设置分割线。因为这个数字不能自适应分辨率。
       //不推荐设置分割线，出问题不维护。item大小即可实现控制分隔的目的。
       //mRecyclerView.setTclSpacingItemDecoration(new TclSpacingItemDecoration(100,50));

       //设置选中item的监听器
		mRecyclerView.setOnItemSelectedListener(new OnItemSelectedListener() {
			//private int lastPosition = -1;// 记录最后一次选中位置

			@Override
			public void onItemSelected(TclRecyclerView parent,
					ViewHolder viewHolder, int position, long id, Rect rect) {
				Log.i(TAG, "onItemSelected,position=" + position + ",viewHolder=" + viewHolder + ",rect=" + rect);
				if(!mRecyclerView.isFocused()){
					//如果没有或得焦点，则不处理。
					return;
				}
				//第一次出场动画
//				if(!hasAnimat){
//					Log.i(TAG,"!hasAnimat");
//					hasAnimat=true;
//					 mRecyclerView.setSelectedAdapterPosition( mRecyclerView.getAdapter().getItemCount()-1);
//					 //mRecyclerView.postScheduleLayoutAnimation();
//					return;
//				}
				
				//跑马灯
				if (viewHolder != null && viewHolder.itemView != null) {
					// 请注意，长按按键翻页时，可能传回的view为空，但position有效。
					Log.i(TAG, "viewHolder != null && viewHolder.itemView != null,position=" + position);
					Log.i(TAG, "fruitName="+ ((FruitViewHolder) viewHolder).fruitName.getText()+ "");

					// 跑马灯
					if (mLastSelectedTextView != null) {
						mLastSelectedTextView.setSelected(false);
					}
					mLastSelectedTextView = ((FruitViewHolder) viewHolder).fruitName;
					mLastSelectedTextView.setSelected(true);
				}

				//Log.i(TAG, "lastPosition="+lastPosition);
			}

			@Override
			public void onNothingSelected(TclRecyclerView parent) {
				Log.i(TAG, "onNothingSelected");
				if (mLastSelectedTextView != null) {
					//mLastSelectedTextView.setEllipsize(TruncateAt.END);
					mLastSelectedTextView.setSelected(false);
				}
			}
		});
       //设置点击item的监听器
       mRecyclerView.setOnItemClickListener(new OnItemClickListener() {
			
			@Override
			public void onItemClick(TclRecyclerView parent, ViewHolder viewHolder, int position,long id) {
				//移植自Block点击事件，启动应用。
				BlockActionBean blockActionBean = (BlockActionBean)viewHolder.itemView.getTag();
				AppBlockResourceAction appBlockResourceAction = new AppBlockResourceAction(MainActivity.this,blockActionBean);
				appBlockResourceAction.onClick();	
			}
		});
       
       /****************************这里是TclRecyclerView的属性设置end*****************************************************/

	}
	
	private void initAdapter(){
		mRecyclerView.getAdapter().registerAdapterDataObserver(new AdapterDataObserver(){
			@Override
			public void onChanged() {
				super.onChanged();
				boolean success = MainActivity.this.mRecyclerView.postScheduleLayoutAnimation();
				if(mRecyclerView.getSelectedAdapterPosition()>mRecyclerView.getAdapter().getItemCount()-1){
					mRecyclerView.setSelectedAdapterPosition(mRecyclerView.getAdapter().getItemCount()-1);
				}
				Log.i(TAG,"onChanged(),success ="+success);
			}
       });
	}
	
	private void initButton() {
		
		final Button button =  (Button)findViewById(R.id.button1);
		
		if(button.getOnFocusChangeListener()!=null){
			//已经初始化过了。不用再次初始化。
			return;
		}
		//获取焦点
		button.requestFocus();

		//按钮点击事件
		button.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {
				//避免按太快
				long curTime = System.currentTimeMillis();
				
				//大于0，是为了防止系统时间变更。
				if(0<(curTime-mLastClickButtonTime)&&(curTime-mLastClickButtonTime)<BUTTON_DISABLE_TIME){
					Log.i(TAG,"0<(curTime-mLastClickTime)&&(curTime-mLastClickTime)<BUTTON_DISABLE_TIME");

					return;
				}else{
					mLastClickButtonTime = curTime;
					
					button.setBackgroundResource(R.drawable.button_normal);
					button.postDelayed(new Runnable(){
						@Override
						public void run() {
							button.setBackgroundResource(R.drawable.button);
						}	
					}, BUTTON_DISABLE_TIME);
					
				}
				updataPage();	
			}
		});
	}
	



	/**
	 * 必须在主线程调用。
	 */
	private void updataPage() {
		
		if(HotPlayGetUtil.getTotalList()==null ){
			//数据还未到来
			return;
		}
		//数据已到来
		if(HotPlayGetUtil.getTotalList().size()!=0){
			//有数据时
			//换下一批8条数据。
			int subStart = (HotPlayGetUtil.indexAdapter + HotPlayGetUtil.PAGE_SIZE)%HotPlayGetUtil.getTotalList().size();
			int subEnd = (subStart+HotPlayGetUtil.PAGE_SIZE)<(HotPlayGetUtil.getTotalList().size())?
					(subStart+HotPlayGetUtil.PAGE_SIZE):(HotPlayGetUtil.getTotalList().size());
			final List<HotPlayInfo> list = HotPlayGetUtil.getTotalList().subList(subStart, subEnd);
			//更新索引
			HotPlayGetUtil.indexAdapter = subStart;
			//也不知道为什么，数据更新在下一帧，会更好。（直接的话，有10%不做layout动画。）
			MainActivity.mHandler.post(new Runnable(){
				@Override
				public void run() {
					//设置新的adapter数据。
					((HotplayAdapter)mRecyclerView.getAdapter()).setHotplayList(list) ;
					//通知数据更新
					mRecyclerView.getAdapter().notifyDataSetChanged();	
				}
			});

		}else{
			//数据==0，获取失败时。
			Log.d("liuwei03", "getvideolistdata 2");
			HotPlayGetUtil.getVideoListData();
		}
	}
	
}

