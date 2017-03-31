package com.tcl.common.mediaplayer.http;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;

import android.util.Log;

public class HttpDownload extends ThreadPool{
	private static final String TAG = "HttpDownload";
	private static HttpDownload instance;
	
	public static HttpDownload getInstance(){
		if(instance == null){
			instance = new HttpDownload();
		}
		return instance;
	}

	/**
	 * 网络下载
	 * @param url 下载地址
	 * @param savePath 文件保存地址
	 */
	public void download(final String url,final String savePath){
		Log.d(TAG, "url is " + url);
		Log.d(TAG, "savePath is " + savePath);
		executor.submit(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				HttpURLConnection conn = null;
				InputStream is = null;
				FileOutputStream fos = null;
				File file = new File(HttpUtils.decodeString(savePath));
				if(file.exists()){
					Log.d(TAG, savePath + " is existed return !!");
					return;
				}
				try {
					URL requestUrl = new URL(url);
					conn = (HttpURLConnection)requestUrl.openConnection();
					conn.setRequestMethod("GET");
					conn.connect();
					
					if(conn.getResponseCode() == 200){
						is = conn.getInputStream();//获取输入流，此时才真正建立链接
						fos = new FileOutputStream(file);
						byte[] buffer = new byte[1024];
						while(is.read(buffer) != -1){
							fos.write(buffer);
						}
						fos.flush();
						Log.d(TAG, "file download finished");
					}
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SocketTimeoutException e) {
					Log.d(TAG, "socket connect timeout !!");
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally{
					try {
						if(is != null){
							is.close();
						}
						if(fos != null){
							fos.close();
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if(conn != null){
						conn.disconnect();
					}
				}
			}
		});
	}

}
