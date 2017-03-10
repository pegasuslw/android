package com.tcl.util;

import java.io.File;

import android.content.Context;
import android.os.Environment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.engine.cache.DiskCache;
import com.bumptech.glide.load.engine.cache.ExternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.module.GlideModule;

public class MyGlideModule implements GlideModule {
	private static final int CACHE_SIZE_BYTES = 5 * 1024 * 1024; // 5 MiB
	@Override
	public void applyOptions(Context paramContext,
			GlideBuilder paramGlideBuilder) {
		//磁盘缓存
		DiskCache.Factory diskCacheFactory;
		
		if(getDiskCacheDir(paramContext)){
			diskCacheFactory = new ExternalCacheDiskCacheFactory(paramContext, "glide_cache", CACHE_SIZE_BYTES);
		}else{
			diskCacheFactory = new InternalCacheDiskCacheFactory(paramContext, "glide_cache", CACHE_SIZE_BYTES);
		}
		
		paramGlideBuilder.setDiskCache(diskCacheFactory);

	}

	@Override
	public void registerComponents(Context paramContext, Glide paramGlide) {
		// TODO Auto-generated method stub

	}
	
	private static boolean getDiskCacheDir(Context context) {  
	    File cacheDir = null;  
	    if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())  
	            || !Environment.isExternalStorageRemovable()) {  
	    	cacheDir = context.getExternalCacheDir();
	    	//Log.i(TAG,"context.getExternalCacheDir();");
	    } 
	    if(cacheDir==null){  
	    	return false;
	    }else{
	    	return true;
	    }  
	}

}
