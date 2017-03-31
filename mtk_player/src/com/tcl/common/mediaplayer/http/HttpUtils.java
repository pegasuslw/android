package com.tcl.common.mediaplayer.http;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class HttpUtils {

	/**
	 * 判断网络是否正常连接
	 */
	public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        return ni != null && ni.isConnected();
    }
	
	public static String encodeString(String str){
		String retStr = "";
		if (null == str) {
			return retStr;
		}
		try {
			retStr = URLEncoder.encode(str, "UTF-8");  //UTF-8
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return retStr;
	}
	
	public static String decodeString(String str){
		String retStr = "";
		try {
			retStr = URLDecoder.decode(str, "UTF-8"); //UTF-8
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return retStr;
	}
}
