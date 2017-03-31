package com.tcl.common.mediaplayer.audio.ui;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import com.tcl.common.mediaplayer.R;

import android.util.Log;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.Bitmap.Config;

public class BitMapUtils {
	
	public static final int ANGLE_90 = 1000;
	public static final int ANGLE_180 = 1002;
	public static final int ANGLE_270 = 1001;
	public static final int ALBUM_WIDTH_1080 = 648;
	public static final int ALBUM_HEIGHT_1080 = 648;
	
	public static final int ALBUM_WIDTH_720 = 410;
	public static final int ALBUM_HEIGHT_720 = 430;	
	public static Bitmap changeAlphaOfBitmap(Bitmap bitmap, int alpha) {
    	if((bitmap == null)||bitmap.isRecycled()){
    		return null;
    	} 
		boolean bitmapHasAlpha = bitmap.hasAlpha();
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		int pix[] = new int[width * height];
		bitmap.getPixels(pix, 0, width, 0, 0, width, height);
		for (int y = height - 1; y >= 0; y--) {
			for (int x = 0; x < width; x++) {
				int index = y * width + x;
				int r = (pix[index] >> 16) & 0xff;
				int g = (pix[index] >> 8) & 0xff;
				int b = pix[index] & 0xff;
				int orAlpha = (pix[index] >> 24) & 0xff;
				if (bitmapHasAlpha && orAlpha == 0x00000000) {
					pix[index] = orAlpha | (r << 16) | (g << 8) | b;
				} else {
					pix[index] = alpha | (r << 16) | (g << 8) | b;
				}
			}
		}
		Bitmap bm = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		bm.setPixels(pix, 0, width, 0, 0, width, height);
		//bitmap.recycle();
		return bm;
	}

	
	public static Bitmap makeInverseBitmap(Bitmap bitmap,boolean rightTrans) {
		if((bitmap == null)||bitmap.isRecycled() ){
			return null;
		}
		boolean bitmapHasAlpha = bitmap.hasAlpha();
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		int halfHei = height/2;
		int pix[] = new int[width * halfHei];
		bitmap.getPixels(pix, 0, width, 0, halfHei, width, halfHei);
		int alpha = 0x88000000,alphaSelf;		
//		Log.i("Utils*******","makeInverseBitmap halfHei   "+halfHei +"     "+(alpha>>24)/halfHei);
		boolean isAlphaChanged = true;
		for (int y = halfHei - 1; y >= 0; y--) {
			for (int x = 0; x < width; x++) {
				int index = y * width + x;
				int r = (pix[index] >> 16) & 0xff;
				int g = (pix[index] >> 8) & 0xff;
				int b = pix[index] & 0xff;
				int orAlpha = (pix[index] >> 24) & 0xff;
				int firstPart = width/4,thirdPart = width*3/4;

                if((alpha>0x0000000)&&(alpha<=0x4000000)){
                	if(x<firstPart){
                	alphaSelf=alpha-0x10000000;
                	}
                	else if(x>thirdPart){
                		alphaSelf=alpha-0x14000000;
                	}
                	else{
                		alphaSelf=alpha-0x04000000;	
                	}
                	if(alphaSelf < 0x00000000){
                		alphaSelf = 0x00000000;
                	}
                }
                else{
                	alphaSelf = alpha;
                }
                if((x>=width-2)&&rightTrans){
                	alphaSelf = 0x00000000;
                }
//                Log.i("Utils*******","makeInverseBitmap alphaSelf   "+alphaSelf);
				/*在这里取消倒影锯齿，原来倒影锯齿明显是因为这里将透明的alpha值改变了*/
				if(y > halfHei - 5){
					    pix[index] =  0x00000000 | (r << 16) | (g << 8) | b;
				}
				else 
					if (bitmapHasAlpha && orAlpha == 0x00000000) {
					pix[index] = orAlpha | (r << 16) | (g << 8) | b;
				}else{
				    pix[index] = alphaSelf | (r << 16) | (g << 8) | b;
				}
			}

			if (isAlphaChanged) {
				if(alpha<=0x10000000){
					alpha = (alpha - 0x01000000);
				}
				else{
					alpha = (alpha - 0x02000000);
				}
				if (alpha == 0x00000000) {
					alpha = 0x00000000;
					isAlphaChanged = false;
				}
			}
		}
		Bitmap bm = null;
		try{
		       bm = Bitmap.createBitmap(width, halfHei, Bitmap.Config.ARGB_8888);
		}catch(OutOfMemoryError e){
			 Log.e("^^^^^^^^^Utils","makeInverseBitmap " + e.getMessage());
	  		 bm =null ;
		}
		if(bm != null){
			bm.setPixels(pix,0, width, 0, 0, width, halfHei);
		}
//		else{
//			bm = bitmap;
//		}		
		return bm;
	}
	
	
	public static Bitmap makeInverseBitmapFromTop(Bitmap bitmap,boolean rightTrans) {
		if((bitmap == null)||bitmap.isRecycled() ){
			return null;
		}
		boolean bitmapHasAlpha = bitmap.hasAlpha();
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		int halfHei = height/2;
		int pix[] = new int[width * halfHei];
		bitmap.getPixels(pix, 0, width, 0, 0, width, halfHei);
		int alpha = 0x88000000,alphaSelf;		
		Log.i("Utils*******","makeInverseBitmap halfHei   "+halfHei +"     "+(alpha>>24)/halfHei);
		boolean isAlphaChanged = true;
		for (int y = 0; y <= halfHei - 1; y++) {
			for (int x = 0; x < width; x++) {
				int index = y * width + x;
				int r = (pix[index] >> 16) & 0xff;
				int g = (pix[index] >> 8) & 0xff;
				int b = pix[index] & 0xff;
				int orAlpha = (pix[index] >> 24) & 0xff;
				int firstPart = width/4,thirdPart = width*3/4;

                if((alpha>0x0000000)&&(alpha<=0x4000000)){
                	if(x<firstPart){
                	alphaSelf=alpha-0x10000000;
                	}
                	else if(x>thirdPart){
                		alphaSelf=alpha-0x14000000;
                	}
                	else{
                		alphaSelf=alpha-0x04000000;	
                	}
                	if(alphaSelf < 0x00000000){
                		alphaSelf = 0x00000000;
                	}
                }
                else{
                	alphaSelf = alpha;
                }
                if((x>=width-2)&&rightTrans){
                	alphaSelf = 0x00000000;
                }
//                Log.i("Utils*******","makeInverseBitmap alphaSelf   "+alphaSelf);
				/*在这里取消倒影锯齿，原来倒影锯齿明显是因为这里将透明的alpha值改变了*/
				if(y > halfHei - 2){
					    pix[index] =  0x00000000 | (r << 16) | (g << 8) | b;
				}
				else 
					if (bitmapHasAlpha && orAlpha == 0x00000000) {
					pix[index] = orAlpha | (r << 16) | (g << 8) | b;
				}else{
				    pix[index] = alphaSelf | (r << 16) | (g << 8) | b;
				}
			}

			if (isAlphaChanged) {
				if(alpha<=0x10000000){
					alpha = (alpha - 0x01000000);
				}
				else{
					alpha = (alpha - 0x02000000);
				}
				if (alpha == 0x00000000) {
					alpha = 0x00000000;
					isAlphaChanged = false;
				}
			}
		}
		Bitmap bm = null;
		try{
		       bm = Bitmap.createBitmap(width, halfHei, Bitmap.Config.ARGB_8888);
		}catch(OutOfMemoryError e){
			 Log.e("^^^^^^^^^Utils","makeInverseBitmap " + e.getMessage());
	  		 bm =null ;
		}
		if(bm != null){
			bm.setPixels(pix,0, width, 0, 0, width, halfHei);
		}
//		else{
//			bm = bitmap;
//		}		
		return bm;
	}
	
	public static Bitmap makeReflect(Bitmap bb)
    {
		    if((bb == null)||(bb.isRecycled())){
		    	return null;
		    }
		    Bitmap retBit = null;
            Matrix ma=new Matrix();
            ma.setScale(1, -1);
            retBit=bb.createBitmap(bb, 0, 0, bb.getWidth(), bb.getHeight(), ma, false);
            bb.recycle();
            return retBit;            
    }
	
	public static Bitmap makeInverseBitmapOrg(Bitmap bitmap) {
		if((bitmap == null)||bitmap.isRecycled() ){
			return null;
		}
		boolean bitmapHasAlpha = bitmap.hasAlpha();
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		int pix[] = new int[width * height];
		bitmap.getPixels(pix, 0, width, 0, 0, width, height);
		int alpha = 0x88000000;
		boolean isAlphaChanged = true;
		for (int y = height - 1; y >= 0; y--) {
			for (int x = 0; x < width; x++) {
				int index = y * width + x;
				int r = (pix[index] >> 16) & 0xff;
				int g = (pix[index] >> 8) & 0xff;
				int b = pix[index] & 0xff;
				int orAlpha = (pix[index] >> 24) & 0xff;
				if (bitmapHasAlpha && orAlpha == 0x00000000) {
					pix[index] = orAlpha | (r << 16) | (g << 8) | b;
				}else{
				    pix[index] = alpha | (r << 16) | (g << 8) | b;
				}
			}

			if (isAlphaChanged) {
				alpha = (alpha - 0x01000000);
				if (alpha == 0x00000000) {
					alpha = 0x00000000;
					isAlphaChanged = false;
				}
			}
		}
		Bitmap bm = null;
		try{
		       bm = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		}catch(OutOfMemoryError e){
			 Log.e("^^^^^^^^^Utils","makeInverseBitmap " + e.getMessage());
	  		 bm =null ;
		}
		if(bm != null){
			bm.setPixels(pix, 0, width, 0, 0, width, height);
		}
		return bm;
	}
	
	
	public static Bitmap makeInverseBitmap2(Bitmap bitmap) {
    	if((bitmap == null)||bitmap.isRecycled()){
    		return null;
    	} 
		boolean bitmapHasAlpha = bitmap.hasAlpha();
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		int pix[] = new int[width * height];
		bitmap.getPixels(pix, 0, width, 0, 0, width, height);
		int alpha = 0x66000000;
		boolean isAlphaChanged = true;
		for (int y = height - 1; y >= 0; y--) {
			for (int x = 0; x < width; x++) {
				int index = y * width + x;
				int r = (pix[index] >> 16) & 0xff;
				int g = (pix[index] >> 8) & 0xff;
				int b = pix[index] & 0xff;
				int orAlpha = (pix[index] >> 24) & 0xff;
				if (bitmapHasAlpha && orAlpha == 0x00000000) {
					pix[index] = orAlpha | (r << 16) | (g << 8) | b;
				} else {
					pix[index] = alpha | (r << 16) | (g << 8) | b;
				}
			}

			if (isAlphaChanged) {
				alpha = (alpha - 0x03000000);
				if (alpha <= 0x00000000) {
					alpha = 0x00000000;
					isAlphaChanged = false;

				}
		}
		}
		Bitmap bm = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		bm.setPixels(pix, 0, width, 0, 0, width, height);
		return bm;
	}
    
	
	public static Bitmap rotateBitmap(Bitmap bitmap, int angle){
    	if((bitmap == null)||bitmap.isRecycled()){
    		return null;
    	} 
	    Bitmap temp = makeInverseBitmapOrg(bitmap);	
	    int width = temp.getWidth();  
	    int height = temp.getHeight();          
	    Matrix matrix = new Matrix(); 
	    switch (angle) {
		case 1000:
			matrix.postRotate(90); 
			break;
		case 1001:
			matrix.postRotate(270); 
			break;
		case 1002:
			matrix.postRotate(180);
			break;
		default:
			break;
		}
	    
	    Bitmap rotateBitmap = Bitmap.createBitmap(temp, 0, 0, width,  
	            height, matrix, true);
	    temp.recycle();
        System.gc();
        temp = null;
		return rotateBitmap;	
	}
      
//	public  static Bitmap drawMovieName(Bitmap source,String name){
//    	if((source == null)||source.isRecycled()){
//    		return null;
//    	} 
//    	Bitmap bmp = Bitmap.createBitmap(source.getWidth(),source.getHeight(), Bitmap.Config.ARGB_8888); 
//    	Canvas canvasTemp = new Canvas(bmp);    	     
//        canvasTemp.drawBitmap(source, 0, 0, null);
//        Bitmap temp = drawNameOnBitmap(name);
//        canvasTemp.drawBitmap(temp,0,200,null); 
//        canvasTemp.save();
//        canvasTemp.restore();  
//        source.recycle();
//        source = null;
//        temp.recycle();
//        temp = null;
//        return bmp;
//        
//    } 
	public static Bitmap drawDeviceName(Context context,Bitmap source, String name) {
    	if((source == null)||source.isRecycled()){
    		return null;
    	} 
		Bitmap bmp = Bitmap.createBitmap(source.getWidth(), source.getHeight(),
				Bitmap.Config.ARGB_8888);
		Canvas canvasTemp = new Canvas(bmp);
		canvasTemp.drawBitmap(source, 0, 0, null);
		Paint p = new Paint();
		p.setTextAlign(Paint.Align.CENTER);
		p.setAntiAlias(true);
		String familyName = context.getResources().getString(R.string.fontSong);
		Typeface font = Typeface.create(familyName, Typeface.NORMAL);
		p.setColor(Color.BLACK);
		p.setTypeface(font);
		int nameLen = name.length();
		int nameSize =25;
		if(nameLen >5){
			nameSize =25*5/nameLen;
		}
		if(nameSize<10){
			nameSize = 10;
			name = name.substring(0, 13)+"...";
		}
		p.setTextSize(nameSize);
		canvasTemp.drawText(name, 100, 250, p);
		canvasTemp.save();
		canvasTemp.restore();
		//source.recycle();
		return bmp;

	} 
    
    /*根据Size和路径产生预览的Bitmap*/ 
    public static Bitmap createAlbumBitmapFromBit(Bitmap bm,int width,int heigth){

          if (bm != null) {
              // finally rescale to exactly the size we need
              if (bm.getWidth() != width || bm.getHeight()!= heigth) {
                  Bitmap tmp = Bitmap.createScaledBitmap(bm, width, heigth, true);
                  // Bitmap.createScaledBitmap() can return the same bitmap
                  if (tmp != bm){
                	  bm.recycle();
                	  bm = null;
                  }
                  bm = tmp;
              }
          }
  	  return bm;
  	  
    }
    
    /*根据Size和路径产生预览的Bitmap*/ 
    public static Bitmap createAlbumBitmap(String tag,int width,int height,String path){
  	  
  	  Bitmap bm = null;
  	  int calBeBase = width/10;
       //  File fimage = new File(items.get(position));	
//       Log.i(tag,"createPreBitmap "+path);
  	  /*
  	     1 图片太大，内存不足heap space 之类的问题：
  		 BitmapFactory.Options options=new BitmapFactory.Options();
  		 options.inSampleSize = 2;//数字越大读出的图片占用的heap越小
  		 BitmapFactory.decodeFile(pathName,options); 
  	  */
  	  BitmapFactory.Options options=new BitmapFactory.Options(); 
  	  options.inJustDecodeBounds = true;
  	  //options.inSampleSize = (int)(fimage.length()/BASE_SIZE*2); 
  			
  	  try{
  				bm = BitmapFactory.decodeFile(path,options);
  		 }catch(OutOfMemoryError e){
  		     Log.e(tag,"getView first BitmapFactory.decodeFile " + e.getMessage());
  		     bm = null;
  			 //	Toast.makeText(mContext, "内存不足，结束该应用", Toast.LENGTH_LONG);		
  		 }

//  			Log.i(TAG,"width "+String.valueOf(options.outWidth)+"height "+String.valueOf(options.outHeight));
  			options.inJustDecodeBounds = false;
//  			int be = (int) Math.max(Math.floor(options.outWidth / nImageWidth),Math.floor(options.outHeight /nImageLength));
  			int be = options.outWidth / calBeBase;//应该直接除110(grid layout is 110*170)的，但这里出12是为了增加一位数的精度
  			if(be%10 !=0)
  				be+=10; //尽量取大点图片，否则会模糊
  			be=be/10;
  			if (be <= 0) //判断120是否超过原始图片宽度
  			   be = 1; //如果超过，则不进行缩放
  		    options.inSampleSize = be;
  		    options.inDither = true;
  		    options.inPurgeable = true;
  		    options.inPreferredConfig = Bitmap.Config.RGB_565;
  		 //   Log.i(TAG,"getView before decode file "+String.valueOf(position) + " "+items.get(position));
  		    try{
  		    	
  		    	bm = BitmapFactory.decodeFile(path,options);	
  		    }catch(OutOfMemoryError e){
  		    	
  		    	Log.i(tag,"getView second BitmapFactory.decodeFile "+e.getMessage());
  		    	bm = null;
  		   	 //   Toast.makeText(mContext, "内存不足，结束该应用", Toast.LENGTH_LONG).show();
  		       
  		    	/*options.inSampleSize =be*2;
  		    	bm = BitmapFactory.decodeFile(items.get(position),options);  */  	
  		    }

          if (bm != null) {
              // finally rescale to exactly the size we need
              if (bm.getWidth() != width || bm.getHeight()!= height) {
                  Bitmap tmp = Bitmap.createScaledBitmap(bm, width, height, true);
                  // Bitmap.createScaledBitmap() can return the same bitmap
                  if (tmp != bm){
                	  bm.recycle();
                	  bm = null;
                  }
                  bm = tmp;
              }
          }
  	  return bm;
  	  
    }
	
    public static Bitmap combinBitmap(Bitmap frameBitmap, Bitmap bmBitmap){
    	
    	if((frameBitmap == null)||frameBitmap.isRecycled()){
    		return null;
    	} 
 	   if((bmBitmap == null)||bmBitmap.isRecycled()){
		   return null;
	   }
    	Bitmap bmp = null;
    	try{
    	    bmp = Bitmap.createBitmap(frameBitmap.getWidth(),frameBitmap.getHeight(), Bitmap.Config.ARGB_8888);
    	}catch(OutOfMemoryError e){
    		 Log.e(" Utils ","apFactory.decodeFile 1111" + e.getMessage());
   			 bmp = null;
    	}
        if(bmp != null){
    	Canvas canvasTemp = new Canvas(bmp); 
    	canvasTemp.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG|Paint.FILTER_BITMAP_FLAG));
    	if(bitmapCanUse(frameBitmap)){
    		canvasTemp.drawBitmap(frameBitmap, 0, 0, null);
    	}
      	
//    	Log.i("Utils","***combinBitmap*** canvasTemp is "+bmBitmap.getWidth()
//    			  +" height "+bmBitmap.getHeight());
//        if((bmBitmap.getWidth()!=ALBUM_WIDTH)||(bmBitmap.getHeight()!=ALBUM_HEIGHT)){
//        	canvasTemp.drawBitmap(reSizeBitmap(bmBitmap),45,3,null); 
//        }
//        else{
   	   if(bitmapCanUse(bmBitmap)){
        	canvasTemp.drawBitmap(bmBitmap,70,12,null); 
   	   }
//        }
        canvasTemp.save();
        canvasTemp.restore(); 
        }
        else{
        	 Log.e(" ******************Utils ","*******************8album image is frameBitmap");
//        	bmp = frameBitmap;
        }
//        frameBitmap.recycle();
//        bmBitmap.recycle();
        return bmp;
    }
    
    private static boolean bitmapCanUse(Bitmap bit){
    	if((bit == null)||bit.isRecycled()){
    		return false;
    	}
    	return true;
    }
    
   public static Bitmap reSizeBitmap(Bitmap bitmap,int heigh){
	   if((bitmap == null)||(bitmap.isRecycled())){
		   return null;
	   }
	  int width = bitmap.getWidth();
	   Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, width, heigh, true);
	   bitmap.recycle();
	   System.gc();
	   bitmap = null;
	   return resizedBitmap;
   }
    
//    private static Bitmap drawNameOnBitmap(String name){
//    	Bitmap bmp = Bitmap.createBitmap(190,42, Bitmap.Config.ARGB_8888); 
//    	Canvas canvasTemp = new Canvas(bmp);    	
//        canvasTemp.drawColor(Color.BLACK);
//        Paint p = new Paint(); 
//        String familyName ="����"; 
//        Typeface font = Typeface.create(familyName,Typeface.BOLD); 
//        p.setColor(Color.WHITE); 
//        p.setTypeface(font);
//        p.setTextSize(22); 
//        canvasTemp.drawText(name,50,30,p); 
//        canvasTemp.save();
//        canvasTemp.restore();
//        return bmp;
//    }
    
  
    
    public static Bitmap drawTypeName(Context context,Bitmap source, String name) {
	    if((source == null)||(source.isRecycled())){
	    	return null;
	    }
		Bitmap bmp = Bitmap.createBitmap(source.getWidth(), source.getHeight(),
				Bitmap.Config.ARGB_8888);
		Canvas canvasTemp = new Canvas(bmp);
		canvasTemp.drawBitmap(source, 0, 0, null);
		Paint p = new Paint();
		String familyName = context.getResources().getString(R.string.fontSong);
		Typeface font = Typeface.create(familyName, Typeface.BOLD);  
		p.setColor(Color.BLACK);
		p.setTypeface(font);
		p.setTextSize(35);
		int position = 25;
		if(name.length() <= 3 ){ position = 45; }
		canvasTemp.drawText(name, position, 240, p);
		canvasTemp.save();
		canvasTemp.restore();
		source.recycle();
		source = null;
		return bmp;
	}
    
    
    static int[] alphas2 = {0x01,0x01, 0x03, 0x14, 0x3f, 0x7c, 0xb9, 0xe2, 0xff };
    static PaintFlagsDrawFilter mSetfil = new PaintFlagsDrawFilter(0, Paint.FILTER_BITMAP_FLAG); 
    static int[] alphas = { 0x01000000,0x01000000,0x03000000, 0x14000000, 0x3f000000, 0x7c000000, 0xb9000000, 0xe2000000, 0xff000000};

    /* 模糊上下边缘 */
    public static Bitmap frame2Org(Bitmap bitmap,boolean bIsAlbum) {
    	    if((bitmap == null)||(bitmap.isRecycled())){
    	    	return null;
    	    }
        	int width = bitmap.getWidth();
    		int height = bitmap.getHeight();
    		int pixAll[] = new int[width * height];
    		bitmap.getPixels(pixAll, 0, width, 0, 0, width, height);
                int pix;
                for (int i = 0; i < 7; i++) {
                        for (int j = 0; j < bitmap.getWidth(); j++) {
                                pix = pixAll[i*width+j];
                                if (((pix >> 24) & 0xff) <= alphas2[i+2])
                                        ;
                                else{
                                	if(bIsAlbum){
                                		pixAll[i*width+j] = (pix & 0x00ffffff) | alphas[i+2];
                                	}else{
                                		pixAll[i*width+j] = (pix & 0x00ffffff) | alphas[i];
                                	}
                                }
                                int heightNowCal = bitmap.getHeight() - 1 - i;
                                pix = pixAll[heightNowCal*width+j];
                                if (((pix >> 24) & 0xff) <= alphas2[i+2])
                                        ;
                                else{
                                	if(bIsAlbum){
                                		pixAll[heightNowCal*width+j] = (pix & 0x00ffffff) | alphas[i+2];
                                	}else{
                                		pixAll[heightNowCal*width+j] = (pix & 0x00ffffff) | alphas[i];
                                	}
                                }
                        }
                }
                Bitmap bm = null;
            	try {
            	    bm = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            		bm.setPixels(pixAll, 0, width, 0, 0, width, height);

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        		bitmap.recycle();
                return bm;
        }

    
    /* 模糊上下边缘 */
    public static Bitmap frame2(Bitmap bitmap) {
	    if((bitmap == null)||(bitmap.isRecycled())){
	    	return null;
	    }
    	int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		int pixAll[] = new int[width * height];
		bitmap.getPixels(pixAll, 0, width, 0, 0, width, height);
            int pix;
            for (int i = 0; i < 9; i++) {
                    for (int j = 0; j < bitmap.getWidth(); j++) {
                            pix = pixAll[i*width+j];
                            if (((pix >> 24) & 0xff) <= alphas2[i])
                                    ;
                            else{
                            	pixAll[i*width+j] = (pix & 0x00ffffff) | alphas[i];
//                                   try{
//                                	   bitmap.setPixel(j, i, (pix & 0x00ffffff) | alphas[i]);
//                                	   }catch(IllegalStateException e){
//                                		   Log.e("Utils frame2","setPixel IllegalStateException");
//                                	   }
                            }
                            int heightNowCal = bitmap.getHeight() - 1 - i;
                            pix = pixAll[heightNowCal*width+j];
//                            pix = bitmap.getPixel(j, bitmap.getHeight() - 1 - i);
                            if (((pix >> 24) & 0xff) <= alphas2[i])
                                    ;
                            else{
                            	pixAll[heightNowCal*width+j] = (pix & 0x00ffffff) | alphas[i];
//                            	 try{   
//                            		 bitmap.setPixel(j, bitmap.getHeight() - 1 - i, (pix & 0x00ffffff) | alphas[i]);
//                            	 }catch(IllegalStateException e){
//                          		   Log.e("Utils frame2","setPixel IllegalStateException 2222222");
//                          	     }
                            }
                    }
            }
        	Bitmap bm = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
    		bm.setPixels(pixAll, 0, width, 0, 0, width, height);
    		bitmap.recycle();
            return bm;
    }
    
    
    
    /** 生成透明边框效果。 */
    public static Bitmap frame(Bitmap bitmap,int type) {
            /*
             * int w = 450; int h=300;
             */
	    if((bitmap == null)||(bitmap.isRecycled())){
	    	return null;
	    }
            int w = bitmap.getWidth();
            int h = bitmap.getHeight();
            int banner = 10;
            int top = 10;
            if(type == 1){
//            	banner = 5;
            	top = 2;
            }
            Bitmap tmp = Bitmap.createBitmap(w, h, Config.ARGB_8888);
            // Bitmap tmp = Bitmap.createBitmap(w, h, Config.RGB_565);
            Canvas c = new Canvas(tmp);
            c.setDrawFilter(mSetfil);
            Color color = new Color();
            color.argb(150, 0, 0, 0);
            c.drawColor(color.argb(0, 255, 255, 255));
            Rect srcRect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
            Rect dstRect = new Rect(0, top, w+1, h - banner-top);
            Paint p = new Paint();
            p.setAntiAlias(true);
            p.setFilterBitmap(true);
            c.setDrawFilter(mSetfil);
            c.drawBitmap(bitmap, srcRect, dstRect, p);
            bitmap.recycle();
            return tmp;
    }
}
