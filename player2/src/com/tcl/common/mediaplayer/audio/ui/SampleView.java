/**
 * ------------------------------------------------------------------
 *  Copyright (c) 2011 TCL, All Rights Reserved.
 *  -----------------------------------------------------------------
 *  
 * @author bailing /qiaobl@tcl.com/2011.09.21
 * @JDK version: 1.6
 * @brief: provide the play and control of video and audio;
 * @version:v1.0
 */
package com.tcl.common.mediaplayer.audio.ui;

import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import com.tcl.common.mediaplayer.lyric.Sentence;
import com.tcl.common.mediaplayer.utils.Utils;

public class SampleView extends TextView {
	//private static final String TAG = "SampleView";
	private static final int MarginOfEachSentence_1080 = 70;
	private static final int MarginOfEachSentence_720 = 45;
	private Paint mPaint;
	private List<Sentence> list;
	private int mCurrentTime;//当前音乐播放器的播放位置。
	private boolean is1080p = false;
	public static final String TAG = "AudioPlayerActivity";

	public SampleView(Context context) {
		super(context);
		 init();
	}

	public void setIs1080P(boolean is1080){
		is1080p = is1080;
	}
	public SampleView(Context context, AttributeSet attr) {
		super(context, attr);
		 init();
	}

	public SampleView(Context context, AttributeSet attr, int i) {
		super(context, attr, i);
		 init();
	}

	public void setCurrentTime(int time) {
		mCurrentTime = time;
	}
	
	public void setSentenceList(List<Sentence> list) {
		this.list = list;
	}

	private void init() {	
				mPaint = new Paint();
				mPaint.setAntiAlias(true);
				//mPaint.setTextSize(20);
				mPaint.setTypeface(Typeface.DEFAULT_BOLD);
				
				mPaint.setTextAlign(Paint.Align.LEFT);
		// Shader shader = new LinearGradient(0, 0, 500, 0, new int[]
		// {Color.RED,Color.BLUE }, null, TileMode.CLAMP);
		// mPaint.setShader(shader);

	}

//	@Override
//	protected void onDraw(Canvas canvas) {
//		super.onDraw(canvas);
//		//Log.d(TAG, "onDraw");
//		if (list != null) {
//			int index = Utils.getNowSentenceIndex(list,mCurrentTime);
//			//Utils.printLog("LYC", "getNowSentenceIndex ="+index);
//			//Log.d(TAG, "index="+index);
////            if(index != mOldSentenceIndex){
////            	mOldSentenceIndex = index;
//            	//canvas.drawColor(Color.WHITE);
//            	int mLyricLayoutTranslatePosition = 3*MarginOfEachSentence - MarginOfEachSentence*index-15;
//            	canvas.translate(0, mLyricLayoutTranslatePosition);
//            	for (int i = 0; i < list.size(); i++) {
//            		
//            		String text = list.get(i).getContent();
//            		
//            		//String text ="chaosjdfksjfka字体什么的三等奖我iesdskdjsido搜查三等奖发送斯蒂芬快速积分快速算法第三方快速的三等奖快速附近快速斯蒂芬三剑客jksjdkjfksjdfs";
//            		//Utils.printLog("LYC", "Will Draw LYC Text ="+text);
//            		Paint p = mPaint;
//            		if (i == index) {
//            			//text ="sdsf三等奖看到算法健康大家福克斯sdfsfdsfdfsdsfffsdf积分思考分阶段算了速度快附近算法看到算了";
//            			p.setColor(Color.parseColor("#F0A800"));
//            			p.setTextSize(45);
//            		         			
//            		} 
//            		else if(i == index-2 || i== index + 2){
////            			p.setColor(Color.parseColor("#E8E0DD"));
////            			p.setTextSize(22);
//            			p.setColor(Color.WHITE);
//            			p.setTextSize(38);
//            			
////                		canvas.drawText(text, 10/*300*/, 0, p);
////                		canvas.translate(0, MarginOfEachSentence);
//            		}
//            		else {
//            			p.setColor(Color.WHITE);
//            			p.setTextSize(38);
//            			
////                		canvas.drawText(text, 10/*300*/, 0, p);
////                		canvas.translate(0, MarginOfEachSentence);
//            		}
//           			ArrayList<String>lines  = getStringLines(text);
//        			if(lines!=null && lines.size()>0){
//        				for(int m=0 ;m< lines.size(); m++){
//        					String temp = lines.get(m);
//        					//Utils.printLog("SampleView", "line i ="+m+"  content ="+temp);
//                    		canvas.drawText(temp, 15/*300*/, 0, p);
//                    		canvas.translate(0, MarginOfEachSentence);
//        				}
//        			}
//
//            	}
//            	
//
//            	//canvas.restore();
//            	//canvas.save();
//            }
//		}
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		//Log.d(TAG, "onDraw");
		if (list != null) {
			int index = Utils.getNowSentenceIndex(list,mCurrentTime);
			if(is1080p){
				canvas.translate(0, MarginOfEachSentence_1080);
			}else{
				canvas.translate(0, MarginOfEachSentence_720);
			}
			
            	for (int i = index-2; i < index+4; i++) {
            		String text;
            		if(i<0 || i>=list.size() ){
            			text ="";
            		}else{
            			text = list.get(i).getContent();
            			//canvas. text ="chaosjdfksjfka字体什么的三等奖我iesdskdjsido搜查三等奖发送斯蒂芬快速积分快oipujpl";
            		}
            		 
            		
            		//String text ="chaosjdfksjfka字体什么的三等奖我iesdskdjsido搜查三等奖发送斯蒂芬快速积分快速算法第三方快速的三等奖快速附近快速斯蒂芬三剑客jksjdkjfksjdfs";
            		//Utils.printLog("LYC", "Will Draw LYC Text ="+text);
            		Paint p = mPaint;
            		if (i == index) {
            			//text ="sdsf三等奖看到算法健康大家福克斯sdfsfdsfdfsdsfffsdf积分思考分阶段算了速度快附近算法看到算了";
            			p.setColor(Color.parseColor("#F0A800"));
            			if(is1080p){
            				p.setTextSize(50);
            			}else{
            				p.setTextSize(40);
            			}
            		         			
            		}
            		else {
            			p.setColor(Color.WHITE);
            			if(is1080p){
            				p.setTextSize(40);
            			}else{
            				p.setTextSize(30);
            			}
            			
            			
//                		canvas.drawText(text, 10/*300*/, 0, p);
//                		canvas.translate(0, MarginOfEachSentence);
            		}
           			ArrayList<String>lines  = getStringLines(text);
        			if(lines!=null && lines.size()>0){
        				for(int m=0 ;m< lines.size(); m++){
        					String temp = lines.get(m);
        					//Utils.printLog("SampleView", "line i ="+m+"  content ="+temp);
        					if(is1080p){
                        		canvas.drawText(temp, 15/*300*/, 0, p);
                        		canvas.translate(0, MarginOfEachSentence_1080);
        					}else{
                        		canvas.drawText(temp, 10/*300*/, 0, p);
                        		canvas.translate(0, MarginOfEachSentence_720);
        					}

        				}
        			}
        			if(i== index -1){
        				canvas.translate(0, 10);
        			}

            	}
            	

            	//canvas.restore();
            	//canvas.save();
            }
		}
	
	private ArrayList<String> getStringLines(String mText){
		//判断中英文
		boolean isChinese = false;
		boolean isSub = false;
		try{
			if(mText!=null&&!mText.equals("")){
				byte[] buf = mText.getBytes();
				if (buf!= null&&(buf[0] & 0x80) > 0){
					isChinese = true;
					Utils.printLog(TAG, "Lyric is isChinese"+mText);
				}
			}else{
				//Utils.printLog(TAG, "Lyric is null");
			}
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ArrayList<String> stringLines = new ArrayList<String>();
		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append(mText);
		int strCount = 0;
		
		do

		{

			int count = 0;
			if(is1080p){
				count = mPaint.breakText(strBuilder.substring(0), true,1000, null);
			}else{
				count = mPaint.breakText(strBuilder.substring(0), true,650, null);
			}
//           if(count){
//        	   String temp = strBuilder.substring(count, count);
//        	   temp.e
//           }
		//	Utils.printLog(TAG, "lyric count first"+count);
		//	Utils.printLog(TAG, "lyric count first"+strBuilder.length());
			String str =null;
			if(count == strBuilder.length()){
				str = new String(strBuilder.substring(0, count));
				stringLines.add(str);
				Utils.printLog(TAG, "lyric shaoyu yihang"+str);
				return stringLines;
				
				
			}
			for(int j=count;j>=0;j--){
				 if(isChinese){
					 str = new String(strBuilder.substring(0, count));
					 break;
				 }
				 str = new String(strBuilder.substring(0, j));
			//	 Utils.printLog(TAG, "lyric count"+j);
				 if(str.endsWith(" ")){
				    Utils.printLog(TAG, "lyric sub ok"+str);
				    count =j;
					break;
				 }
			//	 Utils.printLog(TAG, "lyric sub error"+str);
			}
		
	

		stringLines.add(str);

		strBuilder.delete(0, count);

		strCount += count;

		} while (strCount < mText.length());
		return stringLines;
	}
	//}

}
