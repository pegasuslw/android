package com.tcl.videoplayer.focus;


import com.tcl.common.mediaplayer.aidl.CommonConst;
import com.tcl.common.mediaplayer.utils.Utils;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;

public class ListViewAnimation {
	public static String TAG = "com.tcl.audioplayer.ListViewAnimation";
	// 方向常量
	private final int DIRECTION_UP = 1;
	private final int DIRECTION_DOWN = 2;
	//动画时间
	public static final int LIST_ITEM_MOVE_SHORT_ANIMATION_TIME = 400;
	public static final int LIST_ITEM_MOVE_LONG_ANIMATION_TIME = 100;
	public static final int LIST_ITEM_SCROLL_ANIMATION_TIME = 300;
	// 本类中handler使用的功能标识常量
	//public final static int HANDLER_MSG_DELEY_PUT_FOCUS = 1001;
	//public final int HANDLER_MSG_DELEY_LOSE_FOCUS = 1002;
	public ListView mListView;
	public Handler mHandler;
	public int ItemHeight;
	public int ItemDivider;
	public Context mContext;
	public int itemCountInOneScreen;

	public int focusImagePosition = 0;
	//记录当前焦点所在区间 
	public int START_FOCUSE_POSITION = 0;
	public int MIDDLE_FOCUSE_POSITION = 0;
	public int END_FOCUSE_POSITION = 0;
	

	public int getFocusImagePosition() {
		return focusImagePosition;
	}

	public ListViewAnimation(Context context,Handler h,ListView list){
		mContext = context;
		mHandler = h;
		mListView = list;
		ItemHeight = measureItemHeight(mListView);
		ItemDivider = mListView.getDividerHeight();
		if(ItemHeight == 0){
			itemCountInOneScreen = 0;
		}else{
			itemCountInOneScreen = (list.getHeight())/( ItemHeight);
		}
		
		START_FOCUSE_POSITION = 0;
		MIDDLE_FOCUSE_POSITION = itemCountInOneScreen / 2;
		if(itemCountInOneScreen >0){
			END_FOCUSE_POSITION = itemCountInOneScreen - 1;
		}
		Utils.printLog(TAG, "ListViewAnimation ItemHeight = " + ItemHeight+" , ItemDivider = " + ItemDivider+" , itemCountInOneScreen = " + itemCountInOneScreen);
		Utils.printLog(TAG, "START = " + START_FOCUSE_POSITION+" , MIDDLE = " + MIDDLE_FOCUSE_POSITION +" , END= " + END_FOCUSE_POSITION);
	}
	
	public void init(final FocusView animView,int index){
		focusImagePosition = index;
		mHandler.post(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Log.d(TAG, "initRun  listView = "+mListView +" , focusImagePosition = " +focusImagePosition);
				float layout_offset = mListView.getY()+mListView.getPaddingTop();
				Log.d(TAG, "initRun  layout_offset = "+layout_offset);
				if( mListView.getChildAt(1) != null && mListView.getChildAt(0)!= null){
					ItemHeight = (int)(mListView.getChildAt(1).getY() - mListView.getChildAt(0).getY());
					Log.d(TAG, "initRun   itemHeight1 =  " +ItemHeight);
				}else {
					ItemHeight =measureItemHeight(mListView);
					Log.d(TAG, "initRun   itemHeight2 =  " +ItemHeight);
				}
				Log.d(TAG, "initRun layout_offset:"+layout_offset+" , itemHeight:"+ItemHeight);
				if( mListView.getChildAt(focusImagePosition) != null){
					float y0 = mListView.getChildAt(focusImagePosition).getY();
					float y = y0 + layout_offset;
					Log.d(TAG, "initRun  listView.getChildAt("+focusImagePosition+").getY() = "+ y0);
					animView.setY(y);
					//	imgAnimation.setY(listView.getChildAt(aniIndex).getY());
				}
					else{
						Log.d(TAG, "initRun  listView.getChildAt("+focusImagePosition+") = null");		
					}
			}
		});
	}
	
	public int getItemCountInOneScreen() {
		if( itemCountInOneScreen > 0){
			return itemCountInOneScreen;
		}else{
			ItemHeight = measureItemHeight(mListView);
			if(ItemHeight == 0){
				itemCountInOneScreen = 0;
			}else{
				itemCountInOneScreen = (mListView.getHeight() ) / ( ItemHeight);
			}
		}
		Utils.printLog(TAG, "getItemCountInOneScreen ItemHeight = " + ItemHeight + " , getHeight = " + mListView.getHeight());

		Utils.printLog(TAG, "kk getHeight = " + mListView.getHeight()+" , getPaddingBottom = " + mListView.getPaddingBottom()+" , getPaddingTop = " + mListView.getPaddingTop() );
	//	Utils.printLog(TAG, "kk getListPaddingBottom = " + mListView.getListPaddingBottom()+" , getListPaddingTop = " + mListView.getListPaddingTop()+" , getPaddingTop = " + mListView.getX() );
		return itemCountInOneScreen;
	}	
	
	private void updateComPosition(){
		if(MIDDLE_FOCUSE_POSITION > 0){
			return;
		}
		itemCountInOneScreen = getItemCountInOneScreen();
		START_FOCUSE_POSITION = 0;
		MIDDLE_FOCUSE_POSITION = itemCountInOneScreen / 2;
		if(itemCountInOneScreen >0){
			END_FOCUSE_POSITION = itemCountInOneScreen - 1;
		}
		Utils.printLog(TAG, "updateComPosition , itemCountInOneScreen = " + itemCountInOneScreen);
		Utils.printLog(TAG, "updateComPosition START = " + START_FOCUSE_POSITION+" , MIDDLE = " + MIDDLE_FOCUSE_POSITION +" , END= " + END_FOCUSE_POSITION);

	}
	
	public boolean Up(FocusView animView,int offset,boolean isLongOnclick){
		int listViewCount = mListView.getAdapter().getCount();
		Utils.printLog(TAG, "Up listViewCount = " + listViewCount +" , itemCountInOneScreen = " + itemCountInOneScreen);
		itemCountInOneScreen = getItemCountInOneScreen();
		if (listViewCount <= itemCountInOneScreen) {
			Log.d(TAG, "UP 0");
			moveFocusImage(animView,DIRECTION_UP,isLongOnclick);
		} else {
			updateComPosition();
			// TODO ouyyj 容错
			if (mListView.getChildAt(0) == null) {
				return true;
			}
			// 如果列表已经滚动至最初，无法继续上滚,则移动焦点框
			if (mListView.getFirstVisiblePosition() == 0
//					&& mListView.getChildAt(0).getY() == 0//屏蔽掉，有时候获取值不准确，导致连续向上按键时，在上面几个会卡顿
					) {
				Log.d(TAG, "UP 1");
				moveFocusImage(animView,DIRECTION_UP,isLongOnclick);
			}
			// 如果焦点框在屏幕的一半以下的位置，则优先移动焦点框
			else if (focusImagePosition > MIDDLE_FOCUSE_POSITION) {
				Log.d(TAG, "UP 2");
				moveFocusImage(animView,DIRECTION_UP,isLongOnclick);
			}
			// 其他情况，直接滚动列表，不移动焦点框
			else {
				Log.d(TAG, "UP 3");
				listScroll(DIRECTION_DOWN, offset, LIST_ITEM_SCROLL_ANIMATION_TIME);
			}
		}
		return true;
	}
	
	public void Down(FocusView animView,int offset,boolean isLongOnclick){
		int listViewCount = mListView.getAdapter().getCount();
		itemCountInOneScreen = getItemCountInOneScreen();
		Utils.printLog(TAG, "Down listViewCount = " + listViewCount+" , itemCountInOneScreen = " + itemCountInOneScreen);
	
		if (listViewCount <= itemCountInOneScreen) {
			Utils.printLog(TAG,"Down   0");
			moveFocusImage(animView,DIRECTION_DOWN,isLongOnclick);
		} else {
			updateComPosition();
			// 如果列表已经滚动至最尾，无法继续下滚,则移动焦点框
			Utils.printLog(TAG,"Down  getFirstVisibleItemIndex = " +getFirstVisibleItemIndex());	
			if (getFirstVisibleItemIndex() == listViewCount - itemCountInOneScreen) {
				Utils.printLog(TAG, "Down   1");
				moveFocusImage(animView,DIRECTION_DOWN,isLongOnclick);
			}
			// 如果焦点框在屏幕的一半以上的位置，则优先移动焦点框
			else if (focusImagePosition < MIDDLE_FOCUSE_POSITION) {
				Utils.printLog(TAG, "Down   2");
				moveFocusImage(animView,DIRECTION_DOWN,isLongOnclick);
			}
			// 其他情况，直接滚动列表，不移动焦点框
			else {
				Utils.printLog(TAG, "Down   3");
				listScroll(DIRECTION_UP, offset, LIST_ITEM_SCROLL_ANIMATION_TIME);
			}
		}
	}
	
	/**
	 * 移动焦点框
	 * 
	 * @param direction
	 *            移动方向，为DIRECTION_DOWN或者DIRECTION_UP中的一个
	 */
	public void moveFocusImage(FocusView animView,int direction,boolean islongclick) {
		Log.v(TAG, "moveFocusImage  islongclick = " +islongclick);
		int offset = 0;
		int listViewCount = mListView.getAdapter().getCount();
		//向下移动焦点
		if (direction == DIRECTION_DOWN) {
			Log.v(TAG, "moveFocusImage DOWN focusImagePosition1 = " +focusImagePosition);
			if (focusImagePosition < listViewCount - 1&& focusImagePosition < itemCountInOneScreen - 1) {
				//长按移动动画
				if(islongclick){
					updateComPosition();
					//长按根据起始位置和终点位置算出总长度，然后实现动画移动
					if(focusImagePosition < MIDDLE_FOCUSE_POSITION ){    //比如 当前位置0，MIDDLE = 5，总长度 2
						if( listViewCount - 1 < MIDDLE_FOCUSE_POSITION){    
							offset = listViewCount - 1 - focusImagePosition;
						}else{
							offset = MIDDLE_FOCUSE_POSITION - focusImagePosition;
						}
					//	offset = MIDDLE_FOCUSE_POSITION - focusImagePosition;
					}else if(focusImagePosition >= MIDDLE_FOCUSE_POSITION && focusImagePosition < END_FOCUSE_POSITION){
						if(listViewCount - 1 <  END_FOCUSE_POSITION){   //比如 当前位置5，MIDDLE = 5, END 9 ，总长度 8
							offset = listViewCount - 1 - focusImagePosition;
						}else{
							offset = END_FOCUSE_POSITION - focusImagePosition;
						}
					//	offset = END_FOCUSE_POSITION - focusImagePosition;
					}
					focusImagePosition += offset;
					Log.v(TAG, "moveFocusImage DIRECTION_DOWN focusImagePosition2 = " +focusImagePosition +" , offset = " +offset);
					//焦点移动动画效果，移动焦点长度=偏移量*列表每项的高度，移动时间=item移动时间*偏移量
					float y = (ItemHeight + ItemDivider) * offset;
					Log.v(TAG, "moveFocusImage DIRECTION_DOWN y = " +y+" ,  ItemHeight = " +ItemHeight +" ,  ItemDivider = " +ItemDivider);
					animView.onReLayout(0, y, 0, 0,LIST_ITEM_MOVE_LONG_ANIMATION_TIME*offset);
					mHandler.removeMessages(CommonConst.HANDLER_MSG_DELEY_PUT_FOCUS);
					mHandler.sendEmptyMessageDelayed(CommonConst.HANDLER_MSG_DELEY_PUT_FOCUS, LIST_ITEM_MOVE_LONG_ANIMATION_TIME*offset);
				}
				//短按移动动画
				else{
					focusImagePosition++;
					//短按每次移动一个列表项高度
			//		float y = (ItemHeight + ItemDivider) + animView.getY() + animView.getPaddingTop() ;
					float y = (ItemHeight + ItemDivider)  ;
				//	Log.v(TAG, "moveFocusImage DOWN(short) animView.getY() = " +animView.getY() + " ,getPaddingTop() =" + animView.getPaddingTop());
					Log.v(TAG, "moveFocusImage DOWN(short) focusImagePosition2 = " +focusImagePosition+" , y =" +y);
					animView.onReLayout(0, y, 0, 0,LIST_ITEM_MOVE_SHORT_ANIMATION_TIME);
				//	Log.d(TAG, "moveFocusImage DOWN(short) animView.getY() = "+animView.getY());
					mHandler.removeMessages(CommonConst.HANDLER_MSG_DELEY_PUT_FOCUS);
					mHandler.sendEmptyMessageDelayed(CommonConst.HANDLER_MSG_DELEY_PUT_FOCUS, 100);
				}
			}
			
			
		}
		//向上移动焦点
		else if (direction == DIRECTION_UP) {
			Log.v(TAG, "moveFocusImage DIRECTION_UP focusImagePosition1 = " + focusImagePosition);
			if (focusImagePosition > 0) {
				if(islongclick){
					if(focusImagePosition <= MIDDLE_FOCUSE_POSITION){
						offset = focusImagePosition - START_FOCUSE_POSITION;
					}else if(focusImagePosition > MIDDLE_FOCUSE_POSITION && focusImagePosition <= END_FOCUSE_POSITION){
						offset = focusImagePosition - MIDDLE_FOCUSE_POSITION;
					}
					focusImagePosition -= offset;
					animView.onReLayout(0, -(ItemHeight + ItemDivider)*offset, 0, 0,LIST_ITEM_MOVE_LONG_ANIMATION_TIME*offset);
					mHandler.removeMessages(CommonConst.HANDLER_MSG_DELEY_PUT_FOCUS);
					mHandler.sendEmptyMessageDelayed(CommonConst.HANDLER_MSG_DELEY_PUT_FOCUS, LIST_ITEM_MOVE_LONG_ANIMATION_TIME*offset);
				}else{
					focusImagePosition --;
					animView.onReLayout(0, -(ItemHeight + ItemDivider), 0, 0,LIST_ITEM_MOVE_SHORT_ANIMATION_TIME);
					mHandler.removeMessages(CommonConst.HANDLER_MSG_DELEY_PUT_FOCUS);
					mHandler.sendEmptyMessageDelayed(CommonConst.HANDLER_MSG_DELEY_PUT_FOCUS, 100);
					Log.v(TAG, "moveFocusImage DOWN(short) focusImagePosition2 = " +focusImagePosition);
				}
			}
		}
	}
	
	/**
	 * 对系统的listView的滚动函数做的封装，放置移动的距离超出列表可滚动最大距离，造成异常。
	 * 
	 * @param direction
	 *            移动方向，为DIRECTION_DOWN或DIRECTION_UP中的一个
	 * @param offset
	 *            移动的item的个数，必须为正数或0
	 * @param duration
	 *            移动要花的时间，决定移动速度
	 */
	public void listScroll(int direction, int offset, int duration) {
		int listViewCount = mListView.getAdapter().getCount();
		itemCountInOneScreen = getItemCountInOneScreen();
		// TODO ouyyj 容错
		if (mListView.getChildAt(0) == null) {
			return;
		}
		Utils.printLog(TAG,
				"listScroll(before)  coordinate is ("+ mListView.getFirstVisiblePosition() + ","+ (int) mListView.getChildAt(0).getY() + ")");
		if (direction == DIRECTION_DOWN) {
			// 计算最大可移动距离
			int surplusScollDistance = (int) (mListView.getFirstVisiblePosition() * (ItemHeight + ItemDivider) 
					                                              + (-mListView.getChildAt(0).getY()));
			// 如果需要移动的距离在可移动范围内，则直接移动
			Utils.printLog(TAG,"listScroll offset * ItemHeight = " + offset * (ItemHeight + ItemDivider));
			Utils.printLog(TAG,"listScroll surplusScollDistance = " + surplusScollDistance);
			if (surplusScollDistance > offset * (ItemHeight + ItemDivider)) {
				Utils.printLog(TAG,"listScroll  0");
				mListView.smoothScrollBy(-(ItemHeight + ItemDivider) * offset, duration);
			}
			// 如果需要移动的距离超出最大移动距离，则直接移动最大移动距离
			else {
				Utils.printLog(TAG,"listScroll  1");
				mListView.smoothScrollBy(-surplusScollDistance, duration);
			}
		} else if (direction == DIRECTION_UP) {
			// 计算列表移动到最尾时第一个可见元素的值
			int firstVisiblePositionMaxValue = listViewCount- itemCountInOneScreen;
			if (firstVisiblePositionMaxValue < 0) {
				firstVisiblePositionMaxValue = 0;
			}
			
			// 计算最大可移动距离
			int surplusScollDistance = (int) ((ItemHeight + ItemDivider)* (firstVisiblePositionMaxValue- mListView.getFirstVisiblePosition() - 1) 
					                                                //    + ((ItemHeight + ItemDivider) + mListView.getChildAt(0).getY()));
					                                              + ((ItemHeight + ItemDivider) + mListView.getChildAt(0).getY()));
			Utils.printLog(TAG,"listScroll offset * itemHeight = " + offset * (ItemHeight + ItemDivider));
			Utils.printLog(TAG,"listScroll surplusScollDistance = " + surplusScollDistance);
			// 如果需要移动的距离在可移动范围内，则直接移动
			if (surplusScollDistance > offset * (ItemHeight + ItemDivider)) {
				Utils.printLog(TAG,"listScroll  3");
				mListView.smoothScrollBy((ItemHeight + ItemDivider) * offset, duration);
			}
			// 如果需要移动的距离超出最大移动距离，则直接移动最大移动距离
			else {
				Utils.printLog(TAG,"listScroll  4");
				mListView.smoothScrollBy(surplusScollDistance, duration);
			}
		}
		
		//mHandler.removeMessages(HANDLER_MSG_DELEY_PUT_FOCUS);
		//mHandler.sendEmptyMessageDelayed(HANDLER_MSG_DELEY_PUT_FOCUS, 100);
	}
	
	/**
	 * 获取listView当前可见的第一个item在Adapter中的index。
	 * 
	 * @return 当前可见的第一个item在Adapter中的index
	 */
	public int getFirstVisibleItemIndex() {
	//	Log.v(TAG, "getFirstVisibleItemIndex mListView.getFirstVisiblePosition() = " +mListView.getFirstVisiblePosition());
		// TODO ouyyj 容错
		if (mListView.getChildAt(0) == null) {
			Log.v(TAG, "mListView.getChildAt(0) = null");
			return mListView.getFirstVisiblePosition();
		}
		// 因为listView有bug，有时明明一个item已经滚动到屏幕外了，但是还是会显示第一个可视元素是这个元素，并且这个元素的坐标为-itemHeight，这时，就需要手动把第一个可是元素的值加1.
		if (mListView.getChildAt(0).getY() == 0.0) {
		//	Log.v(TAG, "getFirstVisibleItemIndex  0.0");
			return mListView.getFirstVisiblePosition() ;
		} 
			else if (mListView.getChildAt(0).getY() == -ItemHeight){
	//	else if (mListView.getChildAt(0).getY() >= -ItemHeight  && mListView.getChildAt(0).getY() < 0.0) {  // 此是针对 音轨特殊情况
			Log.v(TAG, "getFirstVisibleItemIndex -ItemHeight");
			return mListView.getFirstVisiblePosition()+1;
		}
			else {
			//return mListView.getFirstVisiblePosition()+1;
				return mListView.getFirstVisiblePosition();
		}
	}
	
	/**
	 * get the height of one item in list
	 * @param listview
	 * @return
	 */
	public int measureItemHeight(ListView listview)
	{
		ListAdapter adapter = listview.getAdapter();
		//容错
		if(adapter == null ||adapter.getCount() == 0){
			return 0;
		}
		View itemView = adapter.getView(0, null, listview);
		itemView.measure(0, 0);
		return itemView.getMeasuredHeight();
	}
	
	
	/**
	 * 根据给定的position放置焦点，如果position对应的项需要滚动列表，则滚动列表后再放置焦点
	 * 
	 * @param position
	 *            需要放置焦点的项在adapter的资源列表中的位置。
	 */
	public void setFocusItemByPosition(FocusView animView,int position) {
		int listViewCount = mListView.getAdapter().getCount();
		Utils.printLog(TAG, "setFocusItemByPosition  listViewCount = " + listViewCount + " , position = " +position);
		// 防止越界
		if (position > listViewCount - 1) {
			position = listViewCount - 1;
		}
		if (position < 0) {
			position = 0;
		}

		Utils.printLog(TAG, "setFocusItemByPosition  position = " + position + ", focusImagePosition = " + focusImagePosition + ", itemCountInOneScreen = " + itemCountInOneScreen);
		// 如果当前列表元素填不满列表，则直接移动焦点框
		itemCountInOneScreen = getItemCountInOneScreen();
		if (listViewCount <= itemCountInOneScreen) {
			float y = animView.getY()+ (position - focusImagePosition) * (ItemHeight + ItemDivider);
		//	Log.v(TAG, "setFocusItemByPosition listViewCount <= itemCountInOneScreen , ItemHeight = " +ItemHeight +", ItemDivider  = " + ItemDivider);
		//	Log.v(TAG, "setFocusItemByPosition listViewCount <= itemCountInOneScreen , y = " +y +", animView.getY()  = " + animView.getY());
			animView.setY(y);
		//	Log.v(TAG, "setFocusItemByPosition listViewCount <= itemCountInOneScreen , 2,animView.getY()  = " + animView.getY());
			focusImagePosition = position;
		//	mListView.setSelection(position);
		}
		// 如果当前列表元素超出列表一屏显示个数
		else {
			updateComPosition();
			// 需要放置焦点的position是列表的最前的几个元素，则列表直接滚动至最前，然后放置焦点框
			if (position >= 0&& position <= MIDDLE_FOCUSE_POSITION) {
				Log.v(TAG, "setFocusItemByPosition 11111");				
				animView.setY(animView.getY()+ (position - focusImagePosition) * (ItemHeight + ItemDivider));
				mListView.setSelection(0);
				focusImagePosition = position;
			}
			// 需要放置焦点的position是列表的最后的几个元素，则列表直接滚动至最尾，然后放置焦点框
			//else if (position <= listViewCount - 1&& position >= listViewCount - 1 - MIDDLE_FOCUSE_POSITION) {    //listViewCount 偶数
				else if (position <= listViewCount - 1&& position > listViewCount - 1 - MIDDLE_FOCUSE_POSITION) {
				Utils.printLog(TAG, "setFocusItemByPosition 22222 animView.getY()  = " +animView.getY() );
				animView.setY(animView.getY() + (position - (listViewCount - itemCountInOneScreen) - focusImagePosition)* (ItemHeight + ItemDivider));
				mListView.setSelection(listViewCount - 1);
				focusImagePosition = position - (listViewCount - itemCountInOneScreen);
			}
			// 需要放置焦点的position是在列表的中间位置，则焦点框放置在中间，列表滚动至position-满屏一半元素的位置
			else {
				Utils.printLog(TAG, "setFocusItemByPosition 33333");
				animView.setY(animView.getY() + (MIDDLE_FOCUSE_POSITION - focusImagePosition)* (ItemHeight + ItemDivider));
				mListView.setSelection(position - MIDDLE_FOCUSE_POSITION);
				focusImagePosition = MIDDLE_FOCUSE_POSITION;
			}
		}
		Utils.printLog(TAG, "setFocusItemByPosition : focusImagePosition = " + focusImagePosition);
	}
	
	/**
	 * 获取当前焦点所在的item
	 * 
	 * @return 当前焦点所在的item
	 */
	public View getCurrentFocusView() {
		// 容错
		if (mListView.getChildAt(0) == null) {
			return null;
		}
		// 通过channelListView.getChildAt获取焦点所在View，由于listView的bug，有时需要获取当前focusImagePosition
		// + 1的child。
		Utils.printLog(TAG, "getCurrentFocusView channelListView.getChildAt(0).getY() = " + mListView.getChildAt(0).getY()+", focusImagePosition = " +focusImagePosition);
	//	Utils.printLog(TAG, "getCurrentFocusView ItemHeight = " +ItemHeight );
		
		if(ItemHeight == 0){
			ItemHeight = measureItemHeight(mListView);
		}
		
		if (mListView.getChildAt(0).getY() == -ItemHeight) {
	//	if (mListView.getChildAt(0).getY() >= -ItemHeight  && mListView.getChildAt(0).getY() <0.0) {  //针对特殊情况-- 音轨
			return mListView.getChildAt(focusImagePosition + 1);
		} else {
			return mListView.getChildAt(focusImagePosition);
		}
	}
	
	/**
	 * 获取当前focus的元素在adapter中的位置。
	 * 
	 * @return 当前focus的元素在adapter中的位置。
	 */
	public int getFocusItemIndex() {
		int index =0;
		int firsVisItemIndex =  getFirstVisibleItemIndex();
		Log.d(TAG, "getFocusItemIndex() focusImagePosition = "+ focusImagePosition+" , firsVisItemIndex() = "+firsVisItemIndex);
		int focusItemIndex = firsVisItemIndex + focusImagePosition;
		// dengfq 修复当列表项超出屏幕显示的时候，返回的index和count相等，会导致越界
	//	Log.d(TAG, "getFocusItemIndex() focusItemIndex = "+ focusItemIndex);
		if (focusItemIndex == mListView.getAdapter().getCount()) {
	//		Log.d(TAG, "getFocusItemIndex() focusItemIndex -1 ");
			index = focusItemIndex - 1;
		//	return focusItemIndex - 1;
		}else{
			index = focusItemIndex ;
		}
//		Log.d(TAG, "getFocusItemIndex() index = " +index +" , focusItemIndex = "+ focusItemIndex);
		return index;
		//return focusItemIndex;
	}
	
	/**
	 * 矫正当前列表位置使焦点对准item。
	 */
	public void correctListPosition() {
		// TODO ouyyj 容错
		if (mListView.getChildAt(0) == null) {
			return;
		}
		
		Utils.printLog(TAG, "begin to correctListPosition! before position is ("+ mListView.getFirstVisiblePosition() + ","
				+ (int) mListView.getChildAt(0).getY() + ")");
		int i = (int) mListView.getChildAt(0).getY();
		Utils.printLog(TAG, "begin to correctListPosition   i = " + i );
		if (i >= -ItemHeight / 2) {
			Utils.printLog(TAG, "begin to correctListPosition  0");
			mListView.smoothScrollBy(i, 100);
		} else {
			Utils.printLog(TAG, "begin to correctListPosition! ItemHeight = " + ItemHeight);
			mListView.smoothScrollBy(ItemHeight + i, 100);
		}
		// 由于矫正位置也需要调用smoothScrollBy函数，滚动完毕时又会调用一次correctListPosition函数，为了防止放置两次焦点，只有在第二次调用矫正函数时（矫正值为0，不需要矫正）才通知焦点状态改变。
		if (i == 0 || i == -ItemHeight) {
			mHandler.sendEmptyMessage(CommonConst.HANDLER_MSG_DELEY_PUT_FOCUS);
		}
	}
	
	
	public void dataNotify(){
		focusImagePosition = 0;
		}
	
}
