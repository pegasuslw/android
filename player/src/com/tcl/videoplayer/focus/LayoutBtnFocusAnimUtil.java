package com.tcl.videoplayer.focus;

import java.util.ArrayList;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.Animator.AnimatorListener;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class LayoutBtnFocusAnimUtil {
	private final String TAG = "com.tcl.videoplayer.focus.LayoutBtnFocusAnimUtil";
	private ImageView imgAnimation;//动画移动图片
	private LinearLayout layoutParent;//动画移动父布局
	private int aniIndex=0;//当前位置
	private int preIndex=0;//动画前位置
	private int count; 
	
	private final int AN_ITEM_TIME = 200;//单次按键动画时间
	private final int AN_PER_STEP_TIME = 150;//长按时单个item动画时间
	private final int AN_REPEAT_COUNT = 60;//长按时重复动画次数
	
	private Float layout_offset = 0f;//父布局偏移位置
	private Float itemWidth;
	
	ObjectAnimator van;//单次按键动画
	ObjectAnimator longVan;//长按按键动画
	
   private boolean longKey = false;//是否已经是在按键长按中
	
	private ArrayList<Integer> list = new ArrayList<Integer>();//去除掉隐藏项后的位置列表
	
	final int color_focus=Color.parseColor("#ffffff");
	final int color_unfocus=Color.parseColor("#c5c7d0");
	
	Handler handler = new Handler();
	private Context mContext;
	public LayoutBtnFocusAnimUtil(ImageView img,LinearLayout layout,Context mContext){
		imgAnimation = img;
		layoutParent = layout;
		count = layoutParent.getChildCount();
		this.mContext = mContext;
	}
	
	public void init(){
	
		handler.post(initRun);
	}
	
	Runnable initRun = new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			list.clear();
			count = layoutParent.getChildCount();
			Log.d(TAG, "initRun count = " +count +" , layoutParent = " +layoutParent.toString());
			for(int i=0;i< count;i++){
				//为了排除分隔线，用名字区分
				View view = layoutParent.getChildAt(i);
				if(view != null && mContext.getResources()!= null){
				//	Log.d(TAG," initRun = view.getId()"+view.getId());
					String name = mContext.getResources().getResourceName(view.getId());
					if((view.getVisibility() == View.VISIBLE&&name.contains("item"))
							//||(view.getVisibility() == View.INVISIBLE&&name.contains("item"))
							){
						Log.d(TAG, "initRun VISIBLE i = "+i+",  name = "+name+", view.isFocused() = "+view.isFocused());
						if(view.isFocused()){
							float X = view.getX() + layoutParent.getX();
							float Y=  layoutParent.getY();
						//	float Y= view.getY() + layoutParent.getY();
							imgAnimation.setX(X);
							imgAnimation.setY(Y);
							Log.d(TAG, " initRun  view.getX() ="+ view.getX()+", view.getY()  = "+view.getY()  );

							Log.d(TAG, " initRun X ="+X+", Y = "+Y +",  layoutParent.getX() = " + layoutParent.getX() +" ,   layoutParent.getY() = " +  layoutParent.getY());
						}
						list.add(i);			
					}
				}else{
					Log.v(TAG, " initRun mContext.getResources() = null");
				}
			
			}
			layout_offset = layoutParent.getX();
			itemWidth = layoutParent.getChildAt(list.get(1)).getX() - layoutParent.getChildAt(list.get(0)).getX();
			Log.d(TAG, "initRun itemWidth = "+itemWidth +" ,  layout_offset ="+layout_offset);
		}		
	};
	
	/**
	 * 向左移动一个位置
	 * @param curIndex当前位置
	 * @return 移动后的位置
	 */
	public int animationIndexLeft(int curIndex){
		int cur = list.indexOf(curIndex);
		if(cur < 0){
			Log.d(TAG, "animationIndexLeft cur=" + cur);
			return curIndex;
		}
		aniIndex = preIndex = curIndex;
		int next = cur - 1;
		if(next < 0){
			aniIndex = list.get(list.size()-1);
			Log.d(TAG, "animationIndexLeft next < 0 aniIndex = "+aniIndex);
			directTo();
			return aniIndex;
		}
		
		aniIndex = list.get(next);
	
		startAnimation(AN_ITEM_TIME);
		return aniIndex;
	}	
	
	/**
	 * 向右移动一个位置
	 * @param curIndex当前位置
	 * @return 移动后的位置
	 */
	public int animationIndexRight(int curIndex){
		
		int cur = list.indexOf(curIndex);
		if(cur < 0){
			Log.d(TAG, "cur:" + cur);
			return curIndex;
		}
		aniIndex = preIndex = curIndex;
		int next = cur+1;
		Log.d(TAG, "animationIndexRight next="+next+ ", list.size()="+list.size());
		if(next >= list.size()){
			
			aniIndex = list.get(0);
			Log.d(TAG, "animationIndexRight next >= list.size() aniIndex:"+aniIndex);
			directTo();
			return aniIndex;
		}
		
		aniIndex = list.get(next);
		Log.d(TAG, "animationIndexRight edit aniIndex:"+aniIndex);
		startAnimation(AN_ITEM_TIME);
		return aniIndex;
	}	
	
	/**
	 * 执行动画
	 * @param time
	 */
	private void startAnimation(int time) {
		Log.d(TAG, "startAnimation----------preIndex:"+preIndex+"-----aniIndex:" +aniIndex);
		AnimatorSet animationSet = new AnimatorSet();
	//	animationSet.setInterpolator(new ViewAccelerateDecelerateFrameInterpolator());
		animationSet.setDuration(time);
		
		float preX = layoutParent.getChildAt(preIndex).getX() + layout_offset;
		float desX = layoutParent.getChildAt(aniIndex).getX() + layout_offset;
		Log.d(TAG, "preX:"+preX+" desX"+desX);
		if(van != null){
			van.cancel();
		}
		van = ObjectAnimator.ofFloat(imgAnimation, "X", preX, desX);
		imgAnimation.setVisibility(View.VISIBLE);
		imgAnimation.requestFocus();
		van.setDuration(time);
		
		
	/*	TextView preView = (TextView) picture_layout.getChildAt(preIndex).findViewById(R.id.text);
		TextView desView = (TextView) picture_layout.getChildAt(aniIndex).findViewById(R.id.text);
		ValueAnimator preColorAnimation = ObjectAnimator.ofInt(preView, "textColor", color_focus,color_unfocus);
		preColorAnimation.setEvaluator(new ArgbEvaluator());
		ValueAnimator desColorAnimation = ObjectAnimator.ofInt(desView, "textColor", color_unfocus,color_focus);
		desColorAnimation.setEvaluator(new ArgbEvaluator());*/
		
		
		animationSet.addListener(new AnimatorListener() {
			
			@Override
			public void onAnimationStart(Animator arg0) {
				// TODO Auto-generated method stub		
				Log.d(TAG, "van onAnimationStart:"+this);
			
			}
			@Override
			public void onAnimationRepeat(Animator arg0) {
				// TODO Auto-generated method stub	
			}
			
			@Override
			public void onAnimationEnd(Animator arg0) {
				// TODO Auto-generated method stub
//				imgAnimation.setVisibility(View.INVISIBLE);
				layoutParent.getChildAt(aniIndex).requestFocus();
			}
			
			@Override
			public void onAnimationCancel(Animator arg0) {
				// TODO Auto-generated method stub
			}
		});
	//	animationSet.play(van).with(preColorAnimation).with(desColorAnimation);
		animationSet.play(van);
		animationSet.start();
		
	}
	
	/**
	 *直接跳转，不用动画
	 */
	private void directTo(){
		Log.d(TAG, "directTo aniIndex = "+aniIndex);
		if(van != null){
			van.cancel();
		}
		imgAnimation.requestFocus();
		imgAnimation.setX(layoutParent.getChildAt(aniIndex).getX()+layoutParent.getX());
		layoutParent.getChildAt(aniIndex).requestFocus();
		/*TextView preView = (TextView) layoutParent.getChildAt(preIndex).findViewById(R.id.text);
		TextView desView = (TextView) layoutParent.getChildAt(aniIndex).findViewById(R.id.text);
		preView.setTextColor(color_unfocus);
		desView.setTextColor(color_focus);*/
	}	
	
	/**
	 * 长按左键重复循环移动
	 */
	public void repeatLeftAn(){
		Log.d(TAG, "repeatLeftAn longKey = "+longKey);
		
		if(!longKey){
			if(van != null){
				van.cancel();
			}
			
			float desX = layoutParent.getChildAt(list.get(0)).getX()+layout_offset;
			float preX = layoutParent.getChildAt(aniIndex).getX()+layout_offset;
			Log.d(TAG, "repeatLeftAn imgAnimation.getX()="+preX);
			longVan = ObjectAnimator.ofFloat(imgAnimation, "X", preX, desX);//先动画移动到顶部
			longVan.setDuration(list.indexOf(aniIndex) * AN_PER_STEP_TIME);
			
//			imgAnimation.setVisibility(View.VISIBLE);
			imgAnimation.requestFocus();
			longVan.setInterpolator(new AccelerateDecelerateInterpolator());
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
					if(longKey){//再进行循环向上动画移动
						float preX = layoutParent.getChildAt(list.get(list.size()-1)).getX()+layout_offset;
						float desX = layoutParent.getChildAt(list.get(0)).getX()+layout_offset;
						longVan = ObjectAnimator.ofFloat(imgAnimation, "X", preX, desX);
						longVan.setDuration(AN_PER_STEP_TIME * list.size());
						longVan.setInterpolator(new AccelerateDecelerateInterpolator());
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
	
	/**
	 * 长按右键重复循环移动
	 */
	public void repeatRightAn(){
		Log.d(TAG, "repeatRightAn longKey="+longKey);
		
		if(!longKey){
			if(van != null){
				van.cancel();
			}
			float desX = layoutParent.getChildAt(list.get(list.size()-1)).getX() + layout_offset;
			float preX= layoutParent.getChildAt(aniIndex).getX()+layout_offset;
			Log.d(TAG, "imgAnimation.getX():"+preX);
			longVan = ObjectAnimator.ofFloat(imgAnimation, "X", preX, desX);//先动画移动到底部
			
			longVan.setDuration((list.size()-list.indexOf(aniIndex)-1) * AN_PER_STEP_TIME);
			
//			imgAnimation.setVisibility(View.VISIBLE);
			imgAnimation.requestFocus();
			longVan.setInterpolator(new AccelerateDecelerateInterpolator());
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
					Log.v(TAG, "longKey onAnimationEnd");
					if(longKey){//再进行循环向下动画移动
						float desX = layoutParent.getChildAt(list.get(list.size()-1)).getX()+layout_offset;
						float preX = layoutParent.getChildAt(list.get(0)).getX()+layout_offset;
						longVan = ObjectAnimator.ofFloat(imgAnimation, "X", preX, desX);
						longVan.setDuration(AN_PER_STEP_TIME * list.size());
						longVan.setInterpolator(new AccelerateDecelerateInterpolator());
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
	
	/**
	 * 长按键放开时调用来停止动画
	 * @return
	 */
	public int stopAn(){
		Log.d(TAG, "stopAn  longKey = "+longKey);
		if(longKey){
			longKey = false;
			longVan.cancel();//停止动画
			adjust();//调整动画图片偏移位置
			changeFocus();//改变焦点位置
		}
		return aniIndex;
	}
	
	
	private void adjust(){
		int position = (int) (imgAnimation.getX() / itemWidth);
		aniIndex = list.get(position);
		Log.d(TAG, "adjust position = "+position + ",  aniIndex = " +aniIndex);
		imgAnimation.setX(layoutParent.getChildAt(aniIndex).getX() + layout_offset);
	}
	
	
	private void changeFocus(){
		Log.d(TAG, "changeFocus aniIndex = "+aniIndex);
		layoutParent.getChildAt(aniIndex).requestFocus();
//		imgAnimation.setVisibility(View.INVISIBLE);
		
	}
	
}
