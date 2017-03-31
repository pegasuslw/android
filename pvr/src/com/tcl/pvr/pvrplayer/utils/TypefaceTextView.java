package com.tcl.pvr.pvrplayer.utils;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class TypefaceTextView extends TextView{
	
	public TypefaceTextView(Context context) {
		super(context);
		setTypeface("NotoSansCJKsc-Light");//基础字体
		setIncludeFontPadding(false);//取消字体边距 会导致设置Gravity，需要手动设置padding
	}
	public TypefaceTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		setTypeface("NotoSansCJKsc-Light");//基础字体
		setIncludeFontPadding(false);//取消字体边距 会导致设置Gravity，需要手动设置padding
	}
	public TypefaceTextView(Context context, AttributeSet attrs,int defStyle) {
		super(context, attrs,defStyle);
		setTypeface("NotoSansCJKsc-Light");//基础字体
		setIncludeFontPadding(false);//取消字体边距 会导致设置Gravity，需要手动设置padding
	}
	//给某些特别字体提供的方法
	public void setTypeface(String type){
		Typeface typeFace =Typeface.create(/*"NotoSansCJKsc-Light"*/type,0);
		setTypeface(typeFace);
	}
	
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {   
        if(focused)   
            super.onFocusChanged(focused, direction, previouslyFocusedRect);   
    }   
    
    @Override  
    public void onWindowFocusChanged(boolean focused) {   
        if(focused)   
            super.onWindowFocusChanged(focused);   
    }   
    
}
