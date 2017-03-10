package com.tcl.util;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.ConnectionPool;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import android.content.Context;
import android.os.Environment;
import android.util.Log;

//import android.util.Log;

/**下载组件，方便实现http请求：get，post
 * @author 姬锐锋
 *
 */
public abstract class HttpUtil {
	
	private static final String TAG = "HttpUtil";
	//private static final int CACHE_SIZE_BYTES = 30 * 1024 * 1024; // 30 MiB
	private static final int CACHE_SIZE_BYTES = 1; 
	private static final int MAX_IDLE_CONNECTIONS = 2;
	private static final int KEEP_ALIVE_DURATION  = 60;
	private static final int CONNECT_TIMEOUT  = 20;
	private static final int READ_TIMEOUT  = 20;
	//本来应该隐藏起来。但是这个共用下载组件，放在哪里合适？
	private static OkHttpClient mOkHttpClient = null; //new OkHttpClient();
	public static OkHttpClient getmOkHttpClient() {
		return mOkHttpClient;
	}
	
	/**使用前，需要初始化控件。
	 * @param context
	 */
	public static void  init(Context context){
		//File cacheDir = context.getExternalFilesDir("okhttpCache");
		File cacheDir = getDiskCacheDir(context);
		mOkHttpClient = new OkHttpClient.Builder()
		.cache(new Cache(cacheDir,CACHE_SIZE_BYTES))
		.connectionPool(new ConnectionPool(MAX_IDLE_CONNECTIONS,KEEP_ALIVE_DURATION,TimeUnit.SECONDS))
		.connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
		.readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
		.retryOnConnectionFailure(true)
		.build();
	}
	private static File getDiskCacheDir(Context context) {  
	    File cacheDir = null;  
	    if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())  
	            || !Environment.isExternalStorageRemovable()) {  
	    	cacheDir = context.getExternalCacheDir();
	    	Log.i(TAG,"context.getExternalCacheDir();");
	    } 
	    if(cacheDir==null){  
	    	cacheDir = context.getCacheDir();
	    	Log.i(TAG,"context.getCacheDir();");
	    }  
	    Log.i(TAG,"getDiskCacheDir(Context context),cacheDir="+cacheDir);
	    return cacheDir;  
	}

	/**HTTP请求Get
	 * @param url
	 * @param dataCallback
	 * @return
	 */
	public static Object httpGet(String url,final DataCallback<String> dataCallback){
		Log.i(TAG,"httpGet(),url="+url);
		//创建一个Request
		final Request request = new Request.Builder()
		                .url(url)
		                .build();
		Call call = httpRequest(request,dataCallback);            
		return call;
	}
	
	/**HTTP请求Post
	 * @param url
	 * @param dataCallback
	 * @param queryNamesAndValues
	 * @return
	 */
	public static Object httpPost(String url,Map<String,String> queryNamesAndValues,
			final DataCallback<String> dataCallback){
		Log.i(TAG,"httpPost(),url="+url);
		 FormBody.Builder builder = new FormBody.Builder();  
		 for(Map.Entry<String, String> entry:queryNamesAndValues.entrySet()){
		    builder.add(entry.getKey(),entry.getValue());  
		 }
		 FormBody body = builder.build();  
		//创建一个Request
		final Request request = new Request.Builder()
		                .url(url)
		                .post(body)
		                .build();
		Call call = httpRequest(request,dataCallback);            
		return call;
	}
	
	/**取消获取数据
	 * @param object
	 */
	public static void httpCancel(Object object){
		Call call = (Call)object;
		call.cancel();
	}
	
	private static Call httpRequest(Request request,final DataCallback<String> dataCallback){
		Log.i(TAG,"httpRequest()");

		//new call
		Call call = mOkHttpClient.newCall(request); 
		//请求加入调度
		call.enqueue(
				new Callback(){
					@Override
					public void onFailure(Call arg0, IOException e) {
						dataCallback.onFailure(e.getMessage());	
					}
					@Override
					public void onResponse(Call arg0, Response response)
							throws IOException {
						//Log.i(TAG,"onResponse()");
						String responseStr = response.body().string();
						dataCallback.onSuccess(responseStr);
					}
		        }
		);             
		return call;
	}
	
	public static String getUrl(String protocol,String host,int port,String file,Map<String,String> queryNamesAndValues){
		//拼凑URL字符串
		StringBuffer urlStringBuffer;// = new StringBuilder(url.toString());
		try {
			URL url = new URL(protocol,  host,  port,  file);
			urlStringBuffer = new StringBuffer(url.toString());
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
			return null;
		}
		//去除斜杠
//		if(urlStringBuilder.charAt(urlBuffer.length()-1)=='/'||urlStringBuilder.charAt(urlBuffer.length()-1)=='&'){
//			urlStringBuilder = urlStringBuilder.deleteCharAt(urlStringBuilder.length()-1);
//		}
		if(queryNamesAndValues!=null&&!queryNamesAndValues.isEmpty()){
			//添加问号
			if(!urlStringBuffer.toString().contains("?")){
				urlStringBuffer = urlStringBuffer.append("?");
			}
			
			//挂参数
			for(Map.Entry<String,String> e : queryNamesAndValues.entrySet()){
				if(urlStringBuffer.charAt(urlStringBuffer.length()-1)=='?'){
					urlStringBuffer .append(e.getKey()+"="+e.getValue());
				}else{
					urlStringBuffer .append("&"+e.getKey()+"="+e.getValue());
				}
			}
		}
		String url = urlStringBuffer.toString();
		Log.i(TAG,"getUrl(),url="+url);
		return url;
	}
	
	public static String postUrl(String protocol,String host,int port,String file,Map<String,String> queryNamesAndValues){
		try {
			URL url = new URL(protocol,  host,  port,  file);
			String urlStr = url.toString();
			Log.i(TAG,"postUrl(),url="+url);
			return urlStr;
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
			return null;
		}

	}
	
	/**单元测试
	 * @param args
	 */
	public static void main(String[] args){
		String s = "a";
		String b = "get"+s.substring(0,1).toUpperCase()+s.substring(1);
		System.out.println(b);
		System.out.println("main()");
//		Log.i(TAG,"main()");
	}
}
