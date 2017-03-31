package com.tcl.common.mediaplayer.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.tcl.common.mediaplayer.audio.ui.AudioPlayerActivity;
import com.tcl.common.mediaplayer.http.data.MusicResultData;
import com.tcl.common.mediaplayer.http.data.PosterResultData;

import android.os.Handler;
import android.util.Log;
/**
 * 
 * 请求海报下载地址
 *
 */
public class PostRequest extends ThreadPool {
	
	private static final String TAG = "PostRequest";
	private static final String URL_POSTER = "http://lrc.vod.tcloudfamily.com/playservice-api/photo/";
//	歌词查询接口地址：http://lrc.vod.tcloudfamily.com/playservice-api/lyric 
//		图片查询接口地址：http://lrc.vod.tcloudfamily.com/playservice-api/photo 

	private Handler mHandler = null;
	private String singerName = null;
	private List<PosterResultData> resultList =  new ArrayList<PosterResultData>();
	private int count_timeOut;
	
	public PostRequest(Handler handler, String singerName) {
		this.mHandler = handler;
		this.singerName = singerName;
		count_timeOut = 0;
	}
	
	/**
	 * 发起网络请求
	 */
	public void connect() {
		final String url = URL_POSTER + HttpUtils.encodeString(singerName);
		executor.submit(new Runnable() {
			
			@Override
			public void run() {
				HttpURLConnection conn = null;
				InputStream is = null;
				StringBuilder builder = new StringBuilder();
				try {
					URL requestUrl = new URL(url);
					Log.d(TAG, "requestUrl is " + requestUrl+"   ,singerName=="+singerName);
					
					conn = (HttpURLConnection)requestUrl.openConnection();
					conn.setRequestMethod("GET");
					conn.setConnectTimeout(15 * 1000);
					conn.setReadTimeout(10 * 1000);
					conn.connect();
					
					int responseCode = conn.getResponseCode();
					Log.d(TAG, "responseCode is " + responseCode);
					
					if(responseCode == 200){
						is = conn.getInputStream();//获取输入流，此时才真正建立链接
						InputStreamReader isr = new InputStreamReader(is, "UTF-8");  //UTF-8
						BufferedReader br = new BufferedReader(isr);
						String inputLine = "";
						while((inputLine = br.readLine()) != null){
							builder.append(inputLine);
						}
						Log.d(TAG, "get data OK, parse data!!");
						resolveData(builder.toString());
					}else{
						mHandler.sendEmptyMessage(AudioPlayerActivity.searchPostInfo_failed);
					}
				} catch (MalformedURLException e) {
					Log.d(TAG, "MalformedURLException =="+e);
					e.printStackTrace();
					mHandler.sendEmptyMessage(AudioPlayerActivity.searchPostInfo_failed);
				} catch (SocketTimeoutException e) {
					Log.d(TAG, "socket connect timeout !!SocketTimeoutException=="+e);
					e.printStackTrace();
					if (count_timeOut++<2) {
						connect();
					}else{
						mHandler.sendEmptyMessage(AudioPlayerActivity.searchPostInfo_failed);
					}
				} catch (IOException e) {
					Log.d(TAG, "IOException =="+e);
					e.printStackTrace();
					mHandler.sendEmptyMessage(AudioPlayerActivity.searchPostInfo_failed);
				}finally{
					if(is != null){
						try {
							is.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					if(conn != null){
						conn.disconnect();
					}
				}
			}
		});
	}
	
	/**
	 * 解析返回数据
	 */
	public void resolveData(String str){
		Log.d(TAG, "response str is " + str);
		try {
			JSONObject jsonObject = new JSONObject(str);
			int code = jsonObject.getInt("code");
			int count = jsonObject.getInt("count");
			if (count > 0) {
				JSONArray resultArray = jsonObject.getJSONArray("result");
				for(int i=0;i<resultArray.length();i++){
					JSONObject tmpObject = (JSONObject) resultArray.opt(i);
					PosterResultData resultData = new PosterResultData();
					resultData.setSinger(tmpObject.getString("artist"));
					resultData.setPosterDownloadUrl(tmpObject.getString("url"));
					resultList.add(resultData);
				}
			}
			mHandler.sendEmptyMessage(AudioPlayerActivity.searchPostInfo_succeed);
		} catch (JSONException e) {
			e.printStackTrace();
			mHandler.sendEmptyMessage(AudioPlayerActivity.searchPostInfo_failed);
		}
	}
	
	/**
	 * 获取解析后的数据List
	 * @return 解析后的数据List
	 */
	public List<PosterResultData> getDataList(){
		return resultList;
	}
}
