package com.tcl.videoplayer.focus;

import com.tcl.videoplayer.R;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

@SuppressLint("DrawAllocation")
public class FocusView extends View {
    private static String TAG ="com.tcl.videoplayer.FocusView";
	public float nFocusX;
    public float nFocusY;
    public int nFocusW;
    public int nFocusH;
    public int drawbleLeft = 0,drawbleTop = 0,drawbleRight = 0,drawbleBottom = 0;
    private ObjectAnimator mAnimY=null,mAnimX=null;
    private AnimatorSet mAnimatorSet = null;
    
    public FocusView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	     mDrawableShadow_v = getResources().getDrawable(R.drawable.filelistitemfocus);
	}
	 public FocusView(Context context, AttributeSet attr) {
	        this(context, attr, 0);
	        mDrawableShadow_v = getResources().getDrawable(R.drawable.filelistitemfocus); 
	    }

	    public FocusView(Context context, AttributeSet attr, int style) {
	        super(context, attr, style);
	        mDrawableShadow_v = getResources().getDrawable(R.drawable.filelistitemfocus);
	    }
	    
	    private Drawable mDrawableShadow_v;
	    @Override
	    protected void onDraw(Canvas canvas) {
	    	  if (currentView == null) {
	    		   Log.d(TAG, "onDraw --- currentView  is null");
	              return;
	          }
	          Log.d(TAG, "onDraw---current" + currentPoint);
	          if (currentPoint != null) {
	              Rect padding = new Rect();
	              mDrawableShadow_v.getPadding(padding);
	              int l = (int) (currentPoint.left - padding.left);
	              int t = (int) (currentPoint.top - padding.top);
	              int r = (int) (currentPoint.right + padding.right);
	              int b = (int) (currentPoint.bottom + padding.bottom);
	              mDrawableShadow_v.setBounds(l, t, r, b);
	              mDrawableShadow_v.draw(canvas);
	          }
	    }
	    
	    /**
	     * set the background of focus view
	     * @param drawableId
	     */
	    public void setBgId(int drawableId){
	    	mDrawableShadow_v = getResources().getDrawable(drawableId);
	    }
	    
	public float getnFocusX() {
		return nFocusX;
	}
	public void setnFocusX(float nFocusX) {
		this.nFocusX = nFocusX;
	}
	public float getnFocusY() {
		return nFocusY;
	}
	public void setnFocusY(float nFocusY) {
		this.nFocusY = nFocusY;
	}
	public int getnFocusW() {
		return nFocusW;
	}
	public void setnFocusW(int nFocusW) {
		this.nFocusW = nFocusW;
	}
	public int getnFocusH() {
		return nFocusH;
	}
	public void setnFocusH(int nFocusH) {
		this.nFocusH = nFocusH;
	}
	
	   private View dstView;
	  public class PointEvaluator2 implements TypeEvaluator {
	        @Override
	        public Object evaluate(float fraction, Object startValue, Object endValue) {
	            RectF start = (RectF) startValue;//getLocation(currentView);
	            RectF end = getLocation(dstView);//
	    //        Log.d(TAG, "PointEvaluator2 --- startValue =" + startValue);
	    //        Log.d(TAG, "PointEvaluator2 --- endValue =" + (RectF)endValue);
	     //       Log.d(TAG, "PointEvaluator2 --- end =" + end);
	            
	            float right = start.right + fraction * (end.right - start.right);
	            float left = start.left + fraction * (end.left - start.left);
	            float bottom = start.bottom + fraction * (end.bottom - start.bottom);
	            float top = start.top + fraction * (end.top - start.top);

	            RectF r = new RectF(left, top, right, bottom);
	            Log.d(TAG, "PointEvaluator2 --- fraction =" + fraction + "  rect = " + r);
	            return r;
	        }
	    }
	  
	    RectF getLocation(View view) {
	        int location[] = new int[2];
	        getLocationInWindow(location);                            //0:0
	        int end[] = new int[2];
	        view.getLocationInWindow(end);
	    //    Log.d(TAG, "getLocation --- location =" + location[0]+" : "+ location[1]+", end =" + end[0]+" : " +end[1]);
	        return new RectF(end[0] - location[0], 
									        		end[1] - location[1], 
									        		end[0] + view.getWidth() - location[0], 
									        		end[1] + view.getHeight() - location[1]);
	    }
	    
	    private View currentView;
	    private boolean isEffect = true;
	    public void setFocusView(final View dst, int duration, int delayTime) {
	        isEffect = true;
	        dstView = dst;
	        Log.d(TAG, " setFocusView --");
	        if (dst == null) {
	            Log.d(TAG, " setFocusView --- dst is null");
	            return;
	        }
	        if (currentView == null) {
	            Log.d(TAG, " setFocusView --- currentView is null"+ " , dst.x  = " + dst.getX()+", Y="+dst.getY()+ ",W= "+dst.getWidth()+", H="+dst.getHeight());
	            currentView = dst;
	            jump(currentView, dst,duration,delayTime);
	        } else if (currentView != null && currentView != dst) {
	        	   Log.d(TAG, " setFocusView --src  =" + currentView.toString() + "  dst =" + dst.toString());
	            jump(currentView, dst,duration,delayTime);
	        }
	    }
	    
	    RectF startPoint;
	    RectF endPoint;
	    private RectF currentPoint;
	    private void jump(View src, View dst, int duration, int delayTime) {
	        int location[] = new int[2];
	        getLocationInWindow(location);
	    //    Log.d(TAG, "jump---  location =" + location[0]+", : " + location[1]);  //0:0
	        int start[] = new int[2];
	        int end[] = new int[2];
	        src.getLocationInWindow(start);
	        dst.getLocationInWindow(end);
	        Log.d(TAG, "jump---  start =" + start[0]+",  :" +start[1] +", end =" + end[0]+", :" +end[1] );
	        startPoint = new RectF(start[0] - location[0], start[1] - location[1], start[0] + src.getWidth() - location[0], start[1] + src.getHeight() - location[1]);
	        endPoint = new RectF(end[0] - location[0], end[1] - location[1], end[0] + dst.getWidth() - location[0], end[1] + dst.getHeight() - location[1]);
	        currentPoint = startPoint;
	        Log.d(TAG, "jump---  startPoint =" + startPoint+", endPoint ="  + endPoint);
	        startAnimation(duration, delayTime);
	    }
	    
	    private void startAnimation(int duration, int delayTime) {
	        ValueAnimator anim = ValueAnimator.ofObject(new PointEvaluator2(), startPoint, endPoint);
	        anim.addListener(new AnimatorListenerAdapter() {
	            @Override
	            public void onAnimationEnd(Animator animation) {
	                super.onAnimationEnd(animation);
	                currentView = dstView;
	            }
	        });
	        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
	            int i = 0;
	            @Override
	            public void onAnimationUpdate(ValueAnimator valueAnimator) {
	                currentPoint = (RectF) valueAnimator.getAnimatedValue();
	                Log.d(TAG, "onAnimationUpdate -------" + i++);
	                invalidate();
	                if (i % 3 != 0) {
	                    Log.d(TAG, "onAnimationUpdate(i % 3 != 0)--++++" + i);
	                }
	            }
	        });
	        anim.setDuration(duration); //300
	    //    anim.setFrameDelay(delayTime);
	        anim.start();
	    }
	    

	    public void startScaleAndMoveAnimation(int offsetx,int offsety,Rect startPoint,Rect endPoint,int duration){
	    	ObjectAnimator animTranlation=null;
	    	ValueAnimator animScale=null;
	    	Log.d(TAG, "startScaleAndMoveAnimation : offsetx = " + offsetx + ",offsety = " + offsety + ",startPoint = " + startPoint + ",endPoint = " + endPoint);
	    	stopScaleAndMoveAnimation();
	    	if(offsety != 0){
	    		animTranlation = ObjectAnimator.ofFloat(this, "translationY", nFocusY, nFocusY + offsety);
	    		animScale = ObjectAnimator.ofObject(new PointEvaluator(), startPoint,endPoint);
	    		animScale.addUpdateListener(updateListener);

	    		mAnimatorSet = new AnimatorSet();
	    		mAnimatorSet.play(animScale).with(animTranlation);
	    		mAnimatorSet.setDuration(duration);
	    		mAnimatorSet.setInterpolator(new ViewAccelerateDecelerateFrameInterpolator(20.0F,2.0F));
	    		//mAnimatorSet.setInterpolator(new ViewAccelerateDecelerateFrameInterpolator());
	    		mAnimatorSet.start();
	    	}
	    	if(offsetx != 0){
	    		animTranlation = ObjectAnimator.ofFloat(this, "translationX", nFocusX, nFocusX + offsetx);
	    		animScale = ObjectAnimator.ofObject(new PointEvaluator(), startPoint,endPoint);
	    		animScale.addUpdateListener(updateListener);	    		
	    		mAnimatorSet = new AnimatorSet();
	    		mAnimatorSet.play(animScale).with(animTranlation);
	    		mAnimatorSet.setDuration(duration);
	    		//mAnimatorSet.setInterpolator(new ViewAccelerateDecelerateFrameInterpolator());
	    		mAnimatorSet.setInterpolator(new ViewAccelerateDecelerateFrameInterpolator(20.0F,2.0F));
	    		mAnimatorSet.start();
	    	}
	    	nFocusX = nFocusX + offsetx;
	        nFocusY = nFocusY + offsety;
	    }
	
	    final AnimatorUpdateListener updateListener = new AnimatorUpdateListener() {			
			@Override
			public void onAnimationUpdate(ValueAnimator arg0) {
				// TODO Auto-generated method stub
				Rect curw = (Rect) arg0.getAnimatedValue();
				android.widget.RelativeLayout.LayoutParams param = (android.widget.RelativeLayout.LayoutParams) getLayoutParams();
				param.width = curw.right - curw.left ;//curw.right - curw.left + drawbleLeft;
				param.height = curw.bottom - curw.top;
				Log.d(TAG, "curw = " + curw);
				setLayoutParams(param);
			}
		};
		
		 public void stopScaleAndMoveAnimation(){
		    	Log.d(TAG, "stopScaleAndMoveAnimation");
		    	if(mAnimatorSet != null && mAnimatorSet.isRunning()){
					mAnimatorSet.cancel();
					mAnimatorSet = null;
				}
		    }
		   private class PointEvaluator implements TypeEvaluator<Object> {
		    	private Rect result = new Rect();
				@Override
				public Object evaluate(float fraction, Object startValue,
						Object endValue) {
					Rect startPoint = (Rect) startValue;
					Rect endPoint = (Rect) endValue;
					final float offsetleft = endPoint.left - startPoint.left;
					final float offsetRight = endPoint.right - startPoint.right;
					final float offsetTop = endPoint.top - startPoint.top;
					final float offsetButtom = endPoint.bottom - startPoint.bottom;

					result.set((int) (startPoint.left + fraction * offsetleft),
							(int) (startPoint.top + fraction * offsetTop),
							(int) (startPoint.right + fraction * offsetRight),
							(int) (startPoint.bottom + fraction * offsetButtom));

					return result;
				}

			}
		   
		   public void onReLayout(final float offsetx, final float offsety, final float scaleX, final float scaleY,int durating) {
		    	//列表上下移动动画
		    	//float startY = ;
		    	//float stopY = 
		    	if(offsety != 0){
		    		mAnimY = ObjectAnimator.ofFloat(this, "translationY", nFocusY, nFocusY + offsety);
		    		mAnimY.setDuration(durating);
		    		//vAnim.addUpdateListener(updateListener);
		    		mAnimY.setInterpolator(new AccelerateDecelerateFrameInterpolator());
		    		mAnimY.start();
		    	}
		    	//view左右移动动画
		    	if(offsetx != 0){
		    		mAnimX = ObjectAnimator.ofFloat(this, "translationX", nFocusX, nFocusX + offsetx);
		    		mAnimX.setDuration(durating);
		    		mAnimX.setInterpolator(new AccelerateDecelerateFrameInterpolator());
		    		mAnimX.start();
		    	}
		    	

		        nFocusX = nFocusX + offsetx;
		        nFocusY = nFocusY + offsety;
		    }
		   
		   public void stopAnimY(){
				if(mAnimY != null && mAnimY.isRunning()){
					mAnimY.cancel();
				}
			}
		   
		   @Override
			public float getX() {
				// TODO Auto-generated method stub
				return nFocusX;
			}
			@Override
			public float getY() {
				// TODO Auto-generated method stub
				return nFocusY;
			}
			@Override
			public void setX(float x) {
				// TODO Auto-generated method stub
				super.setX(x);
				nFocusX = x;
			}
			@Override
			public void setY(float y) {
				// TODO Auto-generated method stub
				super.setY(y);
				nFocusY = y;
			}
}
