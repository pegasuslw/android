package com.tcl.videoplayer.focus;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.animation.Animator.AnimatorListener;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;


/**
 * 此焦点移动动画，是针对 listview的内容不需翻页显示而设计
 */
public class ListViewFocusAnimUtil {
	private final String TAG = "com.tcl.videoplayer.ListViewFocusAnimUtil";
	private ImageView imgAnimation;
	private ListView listView;
	private Float layout_offset = 0f;
	private int aniIndex=0;
	private int preIndex=0;
	private Float itemHeight;
	private ObjectAnimator van;//单次按键动画
	private ObjectAnimator longVan;//长按按键动画
	private final int AN_ITEM_TIME = 300;
	private final int AN_PER_STEP_TIME = 150;
	private final int AN_REPEAT_COUNT = 60;
	private boolean longKey = false;
	boolean upLongAction=true;//判断是向上还是向下长按键
	
	Handler handler = new Handler();
	
	public ListViewFocusAnimUtil(ImageView img,ListView layout){
		imgAnimation = img;
		listView = layout;
		if(listView == null){
			Log.v(TAG, "ListViewFocusAnimUtil listView = null");
		}
		layout_offset = listView.getY()+listView.getPaddingTop();
		Log.d(TAG, "ListViewFocusAnimUtil layout_offset = "+layout_offset);
	}
	
	private float getLayoutOffset(){
		if(layout_offset <=0){
			layout_offset = listView.getY()+listView.getPaddingTop();
		}
		Log.d(TAG, "ListViewFocusAnimUtil getLayoutOffset() layout_offset = "+layout_offset);
		return layout_offset;
	}
	public void init(int index){
		aniIndex = index;
		handler.post(initRun);
	}
	
Runnable initRun = new Runnable() {
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			Log.d(TAG, "initRun  listView = "+listView +" , aniIndex = " +aniIndex);
			layout_offset = getLayoutOffset();
	//		Log.d(TAG, "initRun  layout_offset = "+layout_offset);
			if( listView.getChildAt(1) != null && listView.getChildAt(0)!= null){
				itemHeight = listView.getChildAt(1).getY() - listView.getChildAt(0).getY();
		//		Log.d(TAG, "initRun   itemHeight1 =  " +itemHeight);
			}else {
				itemHeight =measureItemHeight(listView);
		//		Log.d(TAG, "initRun   itemHeight2 =  " +itemHeight);
			}
			Log.d(TAG, "initRun layout_offset:"+layout_offset+" , itemHeight:"+itemHeight);
			if( listView.getChildAt(aniIndex) != null){
				float y0 = listView.getChildAt(aniIndex).getY();
				float y = y0 + layout_offset;
				Log.d(TAG, "initRun  listView.getChildAt("+aniIndex+").getY() = "+ y0);
				imgAnimation.setY(y);
				//	imgAnimation.setY(listView.getChildAt(aniIndex).getY());
			}
				else{
					Log.d(TAG, "initRun  listView.getChildAt("+aniIndex+") = null");		
				}
		}
	};

      private void startAnimation(int time) {
	    if(listView != null){
		layout_offset =  getLayoutOffset();
		float preY = listView.getChildAt(preIndex).getY()+layout_offset -10;
		float desY = preY;
		if(listView.getChildAt(aniIndex) != null){
			desY = listView.getChildAt(aniIndex).getY()+layout_offset - 10;
		}
	 
		Log.d(TAG, "startAnimation preY:"+preY+" desY"+desY);
		if(van != null){
			van.cancel();
		}
		van = ObjectAnimator.ofFloat(imgAnimation, "Y", preY, desY);
	//	van.setInterpolator(new MyInterpolator());

		imgAnimation.requestFocus();
		van.setDuration(time);
		
		AnimatorSet animationSet = new AnimatorSet();
		animationSet.setInterpolator(new ViewAccelerateDecelerateFrameInterpolator());
		animationSet.setDuration(time);

		/*TextView preView = (TextView) listView.getChildAt(preIndex).findViewById(R.id.tv_icon_name);
		TextView desView = (TextView) listView.getChildAt(aniIndex).findViewById(R.id.tv_icon_name);
		
		ImageView preImgView = (ImageView) listView.getChildAt(preIndex).findViewById(R.id.iv_icon);
		ImageView desImgView = (ImageView) listView.getChildAt(aniIndex).findViewById(R.id.iv_icon);
		ValueAnimator preImgAnimation = ObjectAnimator.ofFloat(preImgView, "alpha", 1f,picAlpha);
		ValueAnimator desImgAnimation = ObjectAnimator.ofFloat(desImgView, "alpha", picAlpha,1f);
		ValueAnimator preColorAnimation = ObjectAnimator.ofInt(preView, "textColor", color_focus,color_unfocus);
		preColorAnimation.setEvaluator(new ArgbEvaluator());
		
		ValueAnimator desColorAnimation = ObjectAnimator.ofInt(desView, "textColor", color_unfocus,color_focus);
		desColorAnimation.setEvaluator(new ArgbEvaluator());*/
		
		van.addListener(new AnimatorListener() {
			
			@Override
			public void onAnimationStart(Animator arg0) {
				// TODO Auto-generated method stub			
			//	Log.d(TAG, "van onAnimationEnd:"+this);
				listView.setSelection(aniIndex);
				listView.requestFocus();
			}
			@Override
			public void onAnimationRepeat(Animator arg0) {
				// TODO Auto-generated method stub	
			}
			
			@Override
			public void onAnimationEnd(Animator arg0) {
				// TODO Auto-generated method stub
			}
			
			@Override
			public void onAnimationCancel(Animator arg0) {
				// TODO Auto-generated method stub
			}
		});
		//animationSet.play(van).with(preColorAnimation).with(desColorAnimation).with(preImgAnimation).with(desImgAnimation);
		animationSet.play(van);
		animationSet.start();	
	    }
	}

private void startAnimation(int time ,boolean check,boolean isdown) {
	Log.d(TAG,"come to here "+ preIndex);
	if(listView != null){
	layout_offset =  getLayoutOffset();
	float preY = listView.getChildAt(preIndex).getY()+layout_offset -15;
	float desY = preY;
	if(isdown){
		if(check ){
			desY = preY + 1; 	
		}else{
			if(listView.getChildAt(preIndex + 1) != null){
				desY = listView.getChildAt(preIndex +1).getY()+layout_offset -15;
			}
		}
	}else{
		if(check ){
			desY = preY -1; 	
		}else{
			if(listView.getChildAt(preIndex - 1) != null){
				desY = listView.getChildAt(preIndex -1).getY()+layout_offset - 15;
			}
		}
	}
	
	
	Log.d(TAG, "startAnimation preY:"+preY+" desY"+desY);
	if(van != null){
		van.cancel();
	}
	van = ObjectAnimator.ofFloat(imgAnimation, "Y", preY, desY);
//	van.setInterpolator(new MyInterpolator());

	imgAnimation.requestFocus();
	van.setDuration(time);
	
	AnimatorSet animationSet = new AnimatorSet();
	animationSet.setInterpolator(new ViewAccelerateDecelerateFrameInterpolator());
	animationSet.setDuration(time);

	/*TextView preView = (TextView) listView.getChildAt(preIndex).findViewById(R.id.tv_icon_name);
	TextView desView = (TextView) listView.getChildAt(aniIndex).findViewById(R.id.tv_icon_name);
	
	ImageView preImgView = (ImageView) listView.getChildAt(preIndex).findViewById(R.id.iv_icon);
	ImageView desImgView = (ImageView) listView.getChildAt(aniIndex).findViewById(R.id.iv_icon);
	ValueAnimator preImgAnimation = ObjectAnimator.ofFloat(preImgView, "alpha", 1f,picAlpha);
	ValueAnimator desImgAnimation = ObjectAnimator.ofFloat(desImgView, "alpha", picAlpha,1f);
	ValueAnimator preColorAnimation = ObjectAnimator.ofInt(preView, "textColor", color_focus,color_unfocus);
	preColorAnimation.setEvaluator(new ArgbEvaluator());
	
	ValueAnimator desColorAnimation = ObjectAnimator.ofInt(desView, "textColor", color_unfocus,color_focus);
	desColorAnimation.setEvaluator(new ArgbEvaluator());*/
	
	van.addListener(new AnimatorListener() {
		
		@Override
		public void onAnimationStart(Animator arg0) {
			// TODO Auto-generated method stub			
		//	Log.d(TAG, "van onAnimationEnd:"+this);
			listView.setSelection(aniIndex);
			listView.requestFocus();
		}
		@Override
		public void onAnimationRepeat(Animator arg0) {
			// TODO Auto-generated method stub	
		}
		
		@Override
		public void onAnimationEnd(Animator arg0) {
			// TODO Auto-generated method stub
		}
		
		@Override
		public void onAnimationCancel(Animator arg0) {
			// TODO Auto-generated method stub
		}
	});
	//animationSet.play(van).with(preColorAnimation).with(desColorAnimation).with(preImgAnimation).with(desImgAnimation);
	animationSet.play(van);
	animationSet.start();	
	}
}

public int animationIndexUp(int curIndex){
	Log.d(TAG, "animationIndexUp curIndex:" + curIndex);
	if(curIndex >= listView.getCount() || curIndex < 0 ){
	//	Log.d(TAG, "animationIndexUp curIndex:" + curIndex);
		return curIndex;
	}
	
	preIndex = curIndex;
	aniIndex = curIndex -1;
	if(aniIndex < 0){
		aniIndex = listView.getCount()-1;
		Log.d(TAG, "animationIndexUp  next < 0 aniIndex ="+aniIndex);
		directTo();
		return aniIndex;
	}
	
	Log.d(TAG, "animationIndexUp need animation aniIndex:"+aniIndex);
	startAnimation(AN_ITEM_TIME);
	return aniIndex;
}	
public int animationIndexUp(int curIndex,int focusIndex ,boolean needcheck){
	Log.d(TAG, "animationIndexUp curIndex:" + curIndex);
	if(curIndex >= listView.getCount() || curIndex < 0 ){
	//	Log.d(TAG, "animationIndexUp curIndex:" + curIndex);
		return curIndex;
	}
	
	preIndex = curIndex;
	aniIndex = focusIndex;
	if(aniIndex < 0){
		aniIndex = listView.getCount()-1;
		Log.d(TAG, "animationIndexUp  next < 0 aniIndex ="+aniIndex);
		directTo();
		return aniIndex;
	}
	
	Log.d(TAG, "animationIndexUp need animation aniIndex:"+aniIndex);
	if(needcheck){
		startAnimation(AN_ITEM_TIME,true,false);
	}else{
		startAnimation(AN_ITEM_TIME,false,false);
	}
	
	return aniIndex;
}	

public int animationIndexUp(int curIndex ,int maxposition){
	Log.d(TAG, "animationIndexUp curIndex:" + curIndex);
	if(curIndex >= listView.getCount() || curIndex < 0 ){
	//	Log.d(TAG, "animationIndexUp curIndex:" + curIndex);
		return curIndex;
	}
	
	preIndex = curIndex;
	aniIndex = curIndex -1;
	if(aniIndex < 0){
		aniIndex = listView.getCount()-1;
		Log.d(TAG, "animationIndexUp  next < 0 aniIndex ="+aniIndex);
		if(aniIndex > maxposition){
			directTo(maxposition);
		}else{
			directTo();
		}
		
		return aniIndex;
	}
	
	Log.d(TAG, "animationIndexUp need animation aniIndex:"+aniIndex);
	startAnimation(AN_ITEM_TIME);
	return aniIndex;
}	

public int animationIndexDown(int curIndex){
	Log.d(TAG, "animationIndexDown curIndex = " +curIndex );
	if(curIndex >= listView.getCount() || curIndex < 0 ){
		Log.d(TAG, "curIndex:" + curIndex);
		return curIndex;
	}
	preIndex = curIndex;
	aniIndex = curIndex + 1;
	if(aniIndex >= listView.getCount()){
		aniIndex = 0;
		Log.d(TAG, "next >= listView.getCount() aniIndex:"+aniIndex);
		directTo();
		return aniIndex;
	}
	
	Log.d(TAG, "animationIndexDown aniIndex:"+aniIndex);
	startAnimation(AN_ITEM_TIME);
	return aniIndex;
}	

public int animationIndexDown(int curIndex,int focusIndex ,boolean needcheck){
	if(curIndex >= listView.getCount() || curIndex < 0 ){
		Log.d(TAG, "curIndex:" + curIndex);
		return curIndex;
	}
	Log.d(TAG,"now curindex is "+ curIndex +"focusindex is "+ focusIndex + "needcheck is "+needcheck);
	preIndex = curIndex;
	aniIndex = focusIndex;
	if(aniIndex >= listView.getCount()){
		aniIndex = 0;
		Log.d(TAG, "next >= listView.getCount() aniIndex:"+aniIndex);
		directTo();
		return aniIndex;
	}
	
	Log.d(TAG, "animationIndexDown aniIndex:"+aniIndex);
	if(needcheck){
		startAnimation(AN_ITEM_TIME,true,true);
	}else{
		startAnimation(AN_ITEM_TIME,false,true);
	}
	
	return aniIndex;
}

private void directTo(){
	Log.d(TAG, "directTo aniIndex = "+aniIndex);
	if(van != null){
		van.cancel();
	}
		layout_offset =  getLayoutOffset();
	float yOffset = listView.getChildAt(aniIndex).getY()+layout_offset;
	Log.d(TAG, "directTo yOffset = "+yOffset);
	imgAnimation.setY(yOffset);
	listView.setSelection(aniIndex);
	setTextAndIconEffect(preIndex,aniIndex);
}

private void directTo(int maxPostion){
	Log.d(TAG, "directTo aniIndex = "+aniIndex);
	if(van != null){
		van.cancel();
	}
	layout_offset =  getLayoutOffset();
	float yOffset = listView.getChildAt(maxPostion).getY()+layout_offset - 15;
	Log.d(TAG, "directTo yOffset = "+yOffset);
	imgAnimation.setY(yOffset);
	listView.setSelection(aniIndex);
	setTextAndIconEffect(preIndex,maxPostion);
}

/**
 * 设置图片和字体颜色渐变效果
 * @param preIndex 上一个焦点
 * @param aniIndex 当前焦点
 */
	private void setTextAndIconEffect(int preIndex, int aniIndex) {
		AnimatorSet animationSet = new AnimatorSet();
		animationSet.setInterpolator(new ViewAccelerateDecelerateFrameInterpolator());
		animationSet.setDuration(50);

		/*TextView preView = (TextView) listView.getChildAt(preIndex).findViewById(R.id.tv_icon_name);
		TextView desView = (TextView) listView.getChildAt(aniIndex).findViewById(R.id.tv_icon_name);
		ImageView preImgView = (ImageView) listView.getChildAt(preIndex).findViewById(R.id.iv_icon);
		ImageView desImgView = (ImageView) listView.getChildAt(aniIndex).findViewById(R.id.iv_icon);
		ValueAnimator preImgAnimation = ObjectAnimator.ofFloat(preImgView, "alpha", 1f,picAlpha);
		ValueAnimator desImgAnimation = ObjectAnimator.ofFloat(desImgView, "alpha", picAlpha,1f);
		ValueAnimator preColorAnimation = ObjectAnimator.ofInt(preView, "textColor", color_focus,color_unfocus);
		preColorAnimation.setEvaluator(new ArgbEvaluator());
		
		ValueAnimator desColorAnimation = ObjectAnimator.ofInt(desView, "textColor", color_unfocus,color_focus);
		desColorAnimation.setEvaluator(new ArgbEvaluator());
		animationSet.play(preColorAnimation).with(desColorAnimation).with(preImgAnimation).with(desImgAnimation);
		animationSet.start();*/
	}	
	
	public void longKeyUp(){
		Log.d(TAG, "repeatUPAn longKey:"+longKey);
		upLongAction=true;
		layout_offset = getLayoutOffset();
		if(!longKey){
			float desY = listView.getChildAt(0).getY()+layout_offset;
			float preY = listView.getChildAt(aniIndex).getY()+layout_offset;
			Log.d(TAG, "longKeyUp desY="+desY+" , preY="+preY);
			longVan = ObjectAnimator.ofFloat(imgAnimation, "Y", preY, desY);
			longVan.setDuration(aniIndex * AN_PER_STEP_TIME);			
			longVan.setInterpolator(new DecelerateInterpolator());
			longVan.addListener(new AnimatorListener() {
				
				@Override
				public void onAnimationStart(Animator arg0) {
					// TODO Auto-generated method stub				
				}
				@Override
				public void onAnimationRepeat(Animator arg0) {
					// TODO Auto-generated method stub	
				}
				
				@Override
				public void onAnimationEnd(Animator arg0) {
					// TODO Auto-generated method stub
					Log.d(TAG, "longKey onAnimationEnd");
					if(longKey){
						float preY = listView.getChildAt(listView.getCount()-1).getY()+layout_offset;
						float desY = listView.getChildAt(0).getY()+layout_offset;
						Log.d(TAG, "repeat desY="+desY+",  preY = "+preY);
						longVan = ObjectAnimator.ofFloat(imgAnimation, "Y", preY, desY);
						longVan.setDuration(AN_PER_STEP_TIME * listView.getCount());
						longVan.setInterpolator(new DecelerateInterpolator());
						longVan.setRepeatCount(AN_REPEAT_COUNT);
						longVan.start();
					}
				}
				
				@Override
				public void onAnimationCancel(Animator arg0) {
					// TODO Auto-generated method stub
				}
			});			
			longVan.start();
			longKey = true;
		}
	}
	
	public void longKeyDown(){
		Log.d(TAG, "longKeyDown longKey = "+longKey);
		upLongAction=false;
		layout_offset = getLayoutOffset();
		if(!longKey){
			float desY = listView.getChildAt(listView.getCount()-1).getY()+layout_offset;
			float preY = listView.getChildAt(aniIndex).getY()+layout_offset;
			Log.d(TAG, "longKeyUp desY="+desY+" , preY = "+preY);
			longVan = ObjectAnimator.ofFloat(imgAnimation, "Y", preY, desY);
			longVan.setDuration((listView.getCount()-aniIndex-1) * AN_PER_STEP_TIME);
			
			longVan.setInterpolator(new DecelerateInterpolator());
			longVan.addListener(new AnimatorListener() {
				
				@Override
				public void onAnimationStart(Animator arg0) {
					// TODO Auto-generated method stub				
				}
				@Override
				public void onAnimationRepeat(Animator arg0) {
					// TODO Auto-generated method stub	
				}
				
				@Override
				public void onAnimationEnd(Animator arg0) {
					// TODO Auto-generated method stub
					Log.d(TAG, "longKey onAnimationEnd");
					if(longKey){
						float desY = listView.getChildAt(listView.getCount()-1).getY()+layout_offset;
						float preY = listView.getChildAt(0).getY()+layout_offset;
						Log.d(TAG, "repeat desY ="+desY+" preY ="+preY);
						longVan = ObjectAnimator.ofFloat(imgAnimation, "Y", preY, desY);
						longVan.setDuration(AN_PER_STEP_TIME * listView.getCount());
						longVan.setInterpolator(new DecelerateInterpolator());
						longVan.setRepeatCount(AN_REPEAT_COUNT);
						longVan.start();
					}
				}
				
				@Override
				public void onAnimationCancel(Animator arg0) {
					// TODO Auto-generated method stub
				}
			});
			
			longVan.start();
			longKey = true;
		}
	}
	
	public int stopAn(){
		Log.d(TAG, "stopAn longKey:"+longKey);
		if(longKey){
			longKey = false;
			longVan.cancel();
			adjust();
			changeFocus();
		}
		return aniIndex;
	}
	
	private void adjust(){
       if(itemHeight == 0 ){
    	   itemHeight = measureItemHeight(listView);
       }
		
		int position = (int) (imgAnimation.getY() / itemHeight);
		aniIndex = position;
		Log.d(TAG, "adjust mCheckedIndex ="+aniIndex);
		layout_offset = getLayoutOffset();
		imgAnimation.setY(listView.getChildAt(aniIndex).getY() + layout_offset);
	}
	
	public void changeFocus(){
		int lastIndex;
		Log.d(TAG, "changeFocus aniIndex:"+aniIndex +" , preIndex:"+preIndex);
		listView.setSelection(aniIndex);
		if(upLongAction){
			lastIndex=preIndex-1;
			if(lastIndex < 0){
				lastIndex = listView.getCount()-1;
			}
		}else{
			lastIndex=preIndex+1;
			if(lastIndex >= listView.getCount()){
				lastIndex = 0;
			}
		}
		Log.d(TAG, "changeFocus lastIndex:"+lastIndex);
		setTextAndIconEffect(lastIndex, aniIndex);
		
	}
	
	/**
	 * get the height of one item in list
	 * @param listview
	 * @return
	 */
	public float measureItemHeight(ListView listview)
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
}
