package com.tcl.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Log;

import com.tcl.hotplay.MyApplication;
import com.tcl.util.DataCallback;
import com.tcl.util.DataUtil;
import com.tcl.vod.PlatformUtilsImpl;

public class HotPlayDataUtil<RootType, BackType> extends DataUtil<RootType, BackType> {

	private static final String TAG = "HotPlayDataUtil";
	
	protected String protocol = "http"; 
	protected String host = "hotcast.api.my7v.com"; 
	protected int port = 80; 
	protected String file = ""; 
	
	private static Map<String, String> mDnumParamsMap=null; // 电视参数
	private static final String HTTP_TEST_FILE = "/data/data/com.tcl.hotplay/httptest.txt";
	private static final String HTTP_ERROR_FILE = "/data/data/com.tcl.hotplay/httperror.txt";
	
	/**
	 * 测试url
	 */
	protected HotPlayDataUtil(){
		long time = System.currentTimeMillis();
		File testFile=new File(HTTP_TEST_FILE); 
		if (testFile.exists())
		{ 
			try {
				BufferedReader reader =  new BufferedReader(new FileReader(testFile));
				String line = reader.readLine();
				if(line!=null&&!line.isEmpty()){  
					host = line;
				}
				reader.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		Log.i(TAG,"httptest.txt pastTime = "+(System.currentTimeMillis()-time));
	}
	
	/**测试返回错误的url
	 * 由字符串，解析出结果。
	 * 如果觉得效率不高，或者不够灵活。子类可以覆盖此方法。
	 * @param str
	 * @param findNames
	 * @return
	 */
	protected BackType parseResult(String str,String[] findNames) {
		long time = System.currentTimeMillis();
		File testFile=new File(HTTP_ERROR_FILE); 
		if (testFile.exists())
		{ 
			try {
				BufferedReader reader =  new BufferedReader(new FileReader(testFile));
				String line = reader.readLine();
				if(line!=null&&!line.isEmpty()){  
					str = line;
				}
				reader.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		Log.i(TAG,"httperror.txt pastTime = "+(System.currentTimeMillis()-time));
		return super.parseResult(str, findNames);
	}
	
	protected Object getData(String protocol,String host,int port,String file,Map<String,String> queryNamesAndValues,
			final DataCallback<BackType> dataCallback,final String[] findNames){
		Map<String, String> paramsMap = getDnumParams(MyApplication.getInstance());
		if(queryNamesAndValues==null){
			queryNamesAndValues = paramsMap;
		}else{
			queryNamesAndValues.putAll(paramsMap);
		}
		return super.getData(protocol, host, port, file, queryNamesAndValues, dataCallback, findNames);
	}
	
	/**获取电视参数，存储
	 * @param context
	 * @return
	 */
	private static  Map<String, String> getDnumParams(Context context) {
		if(mDnumParamsMap==null){
			mDnumParamsMap = new HashMap<String, String>();
			PlatformUtilsImpl platformUtils = new PlatformUtilsImpl();
			mDnumParamsMap.put("language", getString(context.getResources().getConfiguration().locale.getLanguage()));
			mDnumParamsMap.put("timezone", getString(Calendar.getInstance().getTimeZone().getID()));
			mDnumParamsMap.put("dnum", getString(platformUtils.getDum(context)));
			mDnumParamsMap.put("device_id", getString(platformUtils.getDeviceId()));
			mDnumParamsMap.put("sys_version", getString(platformUtils.getSystemVersion()));
			mDnumParamsMap.put("app_version", getString(String.valueOf(getPackageInfo(context).versionCode)));
			mDnumParamsMap.put("client_type", getString(platformUtils.getClientType()));
			mDnumParamsMap.put("mac", getString(platformUtils.getMacAddress()));
			return mDnumParamsMap;
		}
		return mDnumParamsMap;
	}
	
	private static String getString(String str){
		str = str.trim();
		if(str.length()==0){
			return "null";
		}
		return str;
	}
	
	private static PackageInfo getPackageInfo(Context ctx) {
		try {
			PackageInfo info = ctx.getPackageManager().getPackageInfo(ctx.getPackageName(), 0);
			return info;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
