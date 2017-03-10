package com.tcl.util;

import java.io.InputStream;

import android.app.Fragment;
import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.MemoryCategory;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.load.model.GlideUrl;
import com.tcl.hotplay.R;

public class ImageViewUtil {
	
	private static boolean hasInit = false;
	private static void init(Context context){
		if(!hasInit){
			//是否不同的context要分别注册？ doc上说取得的是singleton
			Glide
			.get(context.getApplicationContext())
			//.setMemoryCategory(MemoryCategory.LOW)
			//.setMemoryCategory(MemoryCategory.LOW)
			.register(GlideUrl.class, InputStream.class,
			        new OkHttpUrlLoader.Factory(HttpUtil.getmOkHttpClient()));
			hasInit = true;
		}
	}

	/**
	 * @param context
	 * @param imageView
	 * @param url
	 */
	public static void loadImageView(Context context,ImageView imageView,String url){
		init(context);
		Glide
		.with(context)  
//		.setMemoryCategory(MemoryCategory.LOW)

	    .load(url)  
	    //.skipMemoryCache(true)
	   // .setMemoryCache(new LruResourceCache(20*1000*1000))
	    .diskCacheStrategy(DiskCacheStrategy.RESULT )//仅缓存结果。默认是缓存ALL:全尺寸和结果尺寸
	    .placeholder(R.drawable.default_background)
	    .into(imageView); 
	}
	public static void loadImageView(Fragment fragment,ImageView imageView,String url){
		init(fragment.getActivity());
		Glide.with(fragment)  
	    .load(url)  
	    .placeholder(R.drawable.default_background)
	    .into(imageView); 
	}
	
	
	/**
	 * @param context
	 * @param url
	 */
	public static void downloadOnly(Context context,String url){
		init(context);
		Glide.with(context)  
	    .load(url);
	}
	public static void downloadOnly(Fragment fragment,String url){
		init(fragment.getActivity());
		Glide.with(fragment)  
	    .load(url);
	}
	
	public static void downloadOnly(Context context,String url,int width, int height){
		init(context);
		Glide.with(context)  
	    .load(url)
	    .downloadOnly(width, height);
	}
	public static void downloadOnly(Fragment fragment,String url,int width, int height){
		init(fragment.getActivity());
		Glide.with(fragment)  
	    .load(url)
	    .downloadOnly(width, height);
	}
}
