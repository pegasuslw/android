package com.tcl.common.mediaplayer.video.UI;

import java.util.ArrayList;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import com.tcl.common.mediaplayer.aidl.MediaBean;
import com.tcl.common.mediaplayer.utils.FileItemData;
import com.tcl.common.mediaplayer.utils.FileListAdapter;
import com.tcl.common.mediaplayer.utils.MediaPlayerApplication;
import com.tcl.common.mediaplayer.utils.Utils;
import com.tcl.common.mediaplayer.video.contrl.IVideoPlayControlHandler;
import com.tcl.videoplayer.R;
import com.tcl.videoplayer.focus.ListViewFocusAnimUtil;

public class VideoFileListDialog extends Dialog{
	
	
	private ArrayList<MediaBean> filelistdata;
	private int  currentPosition = 0; 
	private ArrayList<FileItemData> fileItems;
	private ListView showFileList;
	private String TAG = "VideoPlayer_VideoFileListDialog";
	private Context mContext;
	private MediaPlayerApplication app;
	private IVideoPlayControlHandler mVideoContrl = null;
	private FileListAdapter nowShowAdapter;
	private ListViewFocusAnimUtil mListFocusMove;
	private ImageView mListMoveImg;
	private int mCheckedIndex = 0;
	private int listSize = 0;
	// list item animation	
	private int visibleFirstPosition = 0;
	private final int visibleListSize = 9;
	private boolean isActiveKey = false;   //add here for quick key null 20161121
	private final int AUTODISSMISS = 3;
	private final int delayToSetAnimationFirst = 2;
	
	
	public VideoFileListDialog(Context context,ArrayList<MediaBean> mlist,MediaPlayerApplication mApp) {
		super(context,R.style.StatusbarToast);
		mContext = context;
		filelistdata = mlist;
		app = mApp;
		// TODO Auto-generated constructor stub
	}
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Log.d(TAG, "onCreate VideoFileListDialog");
		setContentView(R.layout.filelistxml);
		
		mVideoContrl = app.getVideoCtrl();	
		Window dialogWindow = this.getWindow();        
		WindowManager.LayoutParams lp = dialogWindow.getAttributes();        
		dialogWindow.setGravity(Gravity.LEFT | Gravity.TOP);
		lp.x = 1310; // 新位置X坐标        
		lp.y = 0; // 新位置Y坐标       
		lp.windowAnimations = R.style.mydialoganimation;
		dialogWindow.setAttributes(lp);
		
		showFileList = (ListView)findViewById(R.id.filelistly);
		mListMoveImg = (ImageView)findViewById(R.id.itemfocus);

		
		mListFocusMove = new ListViewFocusAnimUtil(mListMoveImg, showFileList);
		if(mListFocusMove != null){
			mListFocusMove.init(0);
		}
		
		
		fileItems = new ArrayList<FileItemData>();
		Log.d(TAG,"now filelistdata size is "+ filelistdata.size());
		for(int i=0; i< filelistdata.size();i++){
			
			String name = filelistdata.get(i).mName;
//			Log.d(TAG,"now item name is "+ name);
			FileItemData data = new FileItemData(name, "00");
			fileItems.add(data);
		}
		
	
		Log.d(TAG,"now current position is "+currentPosition);
		
	/*	listItemAnimation=AnimationUtils.loadAnimation(mContext, R.anim.menu_list_item_anim_from_right);
		//listItemAnimation.setInterpolator(new BounceInterpolator());
		mLayoutAnimationController=new LayoutAnimationController(listItemAnimation);
		mLayoutAnimationController.setOrder(LayoutAnimationController.ORDER_NORMAL);
		mLayoutAnimationController.setDelay(aniTime);*/
	}
	
	
	Handler mFilelistHandler = new Handler(){
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				
				showFileList.setAdapter(new FileListAdapter(mContext, fileItems, currentPosition));
				showFileList.setSelection(currentPosition);
				
				break;
            case delayToSetAnimationFirst:
            	listSize = filelistdata.size();
				visibleFirstPosition = listSize - 9;				
				showFileList.requestFocus();
                Log.d(TAG,"now  listsize is "+listSize);
				if(listSize > 9){
					if(currentPosition < visibleFirstPosition){
						 mListFocusMove.animationIndexDown(0 ,currentPosition,true);  
					  }else  {
						 mListFocusMove.animationIndexDown(mCheckedIndex - visibleFirstPosition - 1, currentPosition,false);  
					  }
				}else{
					if(currentPosition != 0 && showFileList != null){
						
						mCheckedIndex = mListFocusMove.animationIndexDown(mCheckedIndex - 1);
					}
				}
				isActiveKey = true;
				break;
            case AUTODISSMISS:
            	dismiss();

			default:
				break;
			}
			
		}
	};
	
	public void refreshFocus(int index){
		
		Log.d(TAG,"wj=== now to refreshFocus index "+index);
		if(nowShowAdapter != null ){
			nowShowAdapter.setnowpostion(index);
			nowShowAdapter.notifyDataSetChanged();			
		}	
		
		updateFocus();
		
		if(showFileList!=null){
			showFileList.setSelection(index);
		}
		
	}
	
	
	public interface FileListCallBack{
		
		void onFileListShowed();
		void userStateRecord();
		void doBookMarkAction();
	}

	private FileListCallBack mCallback = null;
	
	public void setCallback(FileListCallBack cb) {
		mCallback = cb;
	}
	
	public boolean dispatchKeyEvent(KeyEvent event) {
		
		    mFilelistHandler.removeMessages(AUTODISSMISS);
		    mFilelistHandler.sendEmptyMessageDelayed(AUTODISSMISS, 15000);

	        if(event.getAction() == KeyEvent.ACTION_DOWN && isActiveKey){ 
        		 Log.d(TAG,"now mCheckedIndex is:"+mCheckedIndex+"visibleFirstPosition is:" +visibleFirstPosition);

		           if( event.getKeyCode()==KeyEvent.KEYCODE_DPAD_UP){                        //上
				       Log.v(TAG, "dispatchKeyEvent  ACTION_DOWN ");	
				       if(listSize <= 1){
					    	return true;
					    }
				       if(mListFocusMove != null  ){	                                                                 //运行动画
						  if(showFileList.hasFocus() ||mListMoveImg.hasFocus()){                                                //一级列表的上移动画                             
//										if(event.getRepeatCount() > 0){
//											Log.v(TAG, "dispatchKeyEvent(mMenuListView) KEYCODE_DPAD_UP  longKeyUp");
//											mListFocusMove.longKeyUp();
//											return true;
//										 }	
										if(listSize > 9){
											 int now =  showFileList.getSelectedItemPosition();
										 	if(now <= visibleFirstPosition){
												 if(now ==  0){
													 mListFocusMove.animationIndexUp(0, visibleListSize - 1);
												 }else{
													 mListFocusMove.animationIndexUp(0 ,now -1,true);
												 }
												
											 }else {
													mListFocusMove.animationIndexUp(now -visibleFirstPosition ,now -1,false);
											 }
											 return true;
										}else{
											mCheckedIndex = mListFocusMove.animationIndexUp(mCheckedIndex);
											return true;
										}
																
						 }
				       }
	         	 }else if( event.getKeyCode()==KeyEvent.KEYCODE_DPAD_DOWN){                                                         //下
	         		 if(listSize <= 1){
	      		    	return true;
	      		      }
	         		 if(mListFocusMove != null ){	                                                                   //运行动画
					    if(showFileList.hasFocus() || mListMoveImg.hasFocus()){                                                   //一级列表的下移动画  
//						  if(event.getRepeatCount() > 0){
//							mListFocusMove.longKeyDown();
//							return true;
//						  }
						  if(listSize > 9){
							  int now =  showFileList.getSelectedItemPosition();
							  Log.d(TAG,"now is"+now);
							  if(now < visibleFirstPosition){
								   mListFocusMove.animationIndexDown(0,now + 1,true);  
							  }else  {
								  mCheckedIndex = mListFocusMove.animationIndexDown(now - visibleFirstPosition, now + 1,false);  
							  }
						  }else{
							  mCheckedIndex = mListFocusMove.animationIndexDown(mCheckedIndex);  
						  }
							  return true;				
						
					   }
			        }
		         }else if(event.getKeyCode()==KeyEvent.KEYCODE_BACK){
			         mCallback.onFileListShowed();
			       
			         return	super.dispatchKeyEvent(event);
		         }else{
		        	 
		        	 return	super.dispatchKeyEvent(event);
	         	}
	     }else if(event.getAction() == KeyEvent.ACTION_UP && isActiveKey){
	    	 int nowFocusPostion = showFileList.getSelectedItemPosition();
	    	 Log.d(TAG,"now focus position is "+ nowFocusPostion);
	    	 if( event.getKeyCode()==KeyEvent.KEYCODE_DPAD_DOWN  || event.getKeyCode() == KeyEvent.KEYCODE_DPAD_UP){   
	    	    if(mListFocusMove != null ){	
				  if(showFileList.hasFocus() || mListMoveImg.hasFocus()){                                                       //一级列表
						
					  mCheckedIndex = mListFocusMove.stopAn();
			    	}
				return true;
	    	   }
			 }
	     else{
				return super.dispatchKeyEvent(event);
			}
			
		}
		
		
		return super.dispatchKeyEvent(event);
	};
	
	
	OnItemClickListener FileListItemClick = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
				Utils.printLog(TAG, "mVideoContrl.playUrl(filelistdata, index);="+arg2);
			    if(mVideoContrl.getPlayingVideoIndex() == arg2){
			        Log.d(TAG,"playing now ,do not need playing again");
			    }else{
			    	Log.d(TAG, " mCallback.userStateRecord() mCallback.doBookMarkAction()");
			    	mCallback.userStateRecord();//在播放列表更换播放，记住断点
				    mCallback.doBookMarkAction();
			        if(mVideoContrl != null){
			        	mVideoContrl.playUrl(filelistdata, arg2);
			        }
			        
				    dismiss();
				    isActiveKey = false;	
			    }    
		}
	};
	
	public void show() {		
		super.show();		
		if(mVideoContrl != null){
			currentPosition = mVideoContrl.getPlayingVideoIndex();			
			if(mVideoContrl.getPlayList() != null){
				fileItems.clear();
				for(int i=0; i< mVideoContrl.getPlayList().size();i++){
					
					String name = mVideoContrl.getPlayList().get(i).mName;
					FileItemData data = new FileItemData(name, "00");
					fileItems.add(data);
				}	
			}
		}
		
		Log.d(TAG,"now currentposition is "+ currentPosition);
		nowShowAdapter = new FileListAdapter(mContext, fileItems, currentPosition);
		showFileList.setAdapter(nowShowAdapter);
		
		showFileList.setOnItemClickListener(FileListItemClick);
		showFileList.setSelection(currentPosition);
	
		
		mCheckedIndex = currentPosition;
		mFilelistHandler.sendEmptyMessageDelayed(delayToSetAnimationFirst, 500);   //add this beacuse setting this here will ocurr forcecolse 20161017
		mFilelistHandler.sendEmptyMessageDelayed(AUTODISSMISS, 15000);
		
	};
	
	private void updateFocus(){   //modify for update img focus
		 if(mListFocusMove != null ){	                            
			    if(showFileList.hasFocus() || mListMoveImg.hasFocus()){      
				  if(listSize > 9){
					  int now =  showFileList.getSelectedItemPosition();
					  Log.d(TAG,"now is"+now);
					  if(now < visibleFirstPosition){
						   mListFocusMove.animationIndexDown(0,now + 1,true);  
					  }else  {
						  mCheckedIndex = mListFocusMove.animationIndexDown(now + 1 - visibleFirstPosition, now + 1,false);  
					  }
				  }else{
					  mCheckedIndex = mListFocusMove.animationIndexDown(mCheckedIndex);  
				  }		
				
			   }
	        }
	}
}
