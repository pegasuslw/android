package com.tcl.common.mediaplayer.audio.ui;



import android.graphics.Camera;
import android.graphics.Matrix;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.Transformation;

public class animtion_right extends Animation{
private int mCenterX;
private int mCenterY;
private Camera camera=new Camera();
private boolean bAlbumAni,bReflect;


public animtion_right(boolean bAlbum){
	bAlbumAni = bAlbum;
	bReflect = false;
}

public animtion_right(boolean bAlbum,boolean bRef){
	bAlbumAni = bAlbum;
	bReflect = bRef;
}

@Override
//对控件动画进行初始化
	public void initialize(int width,int height,int parentWidth,int parentHeight)
{
	super.initialize(width, height, parentWidth, parentHeight);
	mCenterX=width/2;//取得控件的宽度的中点
	mCenterY=height/2;//取得控件的高度的中点
	setDuration(200);//设置动画时间延时
	setFillAfter(true);//保存结束动画时，控件的状态
	setInterpolator(new LinearInterpolator());//设置加速器的模式
	//setStartOffset(10000);
}
//设置动画的运动模式
@Override
protected void applyTransformation(float interpolatdTime,Transformation t){
	final Matrix matrix=t.getMatrix();
//        t.setAlpha(1.0F*interpolatdTime);
		camera.save();
		if(bAlbumAni){
			camera.translate(interpolatdTime*-7,-18.0f,62.0f);
		}
		else{
			camera.translate(interpolatdTime*0,-18.0f,0.0f);
		}
//		camera.rotateY(20);
//		camera.rotateX(20);
		if(bReflect){		
			camera.rotateY(10*interpolatdTime);
		}
		else{
			camera.rotateY(15*interpolatdTime);
		}
		camera.getMatrix(matrix);
		camera.restore();
		matrix.preTranslate(-mCenterX, -mCenterY);
		matrix.postTranslate(mCenterX, mCenterY);
	
}
}

