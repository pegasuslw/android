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

import android.os.Handler;
import android.util.Log;

public class LyricRequest extends ThreadPool {
	private static final String TAG = "LyricRequest";
	private static final String URL_LRC = "http://lrc.vod.tcloudfamily.com/playservice-api/lyric/";
	private Handler mHandler = null;
	private String songName = null;
	private String singerName = null;
	private String savePath = null;//下载的歌词保存路径
	private List<MusicResultData> resultList =  new ArrayList<MusicResultData>();
	
	public LyricRequest(Handler handler, String songName, String singerName,String savePath) {
		this.mHandler = handler;
		this.songName = songName;
		this.singerName = singerName;
		this.savePath = savePath;
	}
	
	/**
	 * 发起网络请求
	 */
	public void connect() {
		final String url = URL_LRC + HttpUtils.encodeString(songName) + "/" + HttpUtils.encodeString(singerName);
		executor.submit(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				HttpURLConnection conn = null;
				InputStream is = null;
				StringBuilder builder = new StringBuilder();
				try {
					URL requestUrl = new URL(url);
					Log.d(TAG, "requestUrl is " + requestUrl+"    ,songName=="+songName);
					conn = (HttpURLConnection)requestUrl.openConnection();
					conn.setRequestMethod("GET");
//					conn.setRequestProperty("Accept-Charset", "UTF-8");
//					conn.setRequestProperty("Content-Type", "UTF-8");
					conn.setConnectTimeout(15 * 1000);
					conn.setReadTimeout(10 * 1000);
					conn.connect();
					
					int responseCode = conn.getResponseCode();
					Log.d(TAG, "responseCode is " + responseCode);
					if(responseCode == 200){
						is = conn.getInputStream();//获取输入流，此时才真正建立链接
						InputStreamReader isr = new InputStreamReader(is, "UTF-8"); //"UTF-8"
						BufferedReader br = new BufferedReader(isr);
						String inputLine = "";
						while((inputLine = br.readLine()) != null){
							builder.append(inputLine);
						}
						Log.d(TAG, "get data OK, parse data!!");
						resolveData(builder.toString(),savePath);
					}else{
						mHandler.sendEmptyMessage(AudioPlayerActivity.searchLrcInfo_failed);
					}
				} catch (MalformedURLException e) {
					e.printStackTrace();
					mHandler.sendEmptyMessage(AudioPlayerActivity.searchLrcInfo_failed);
				} catch (SocketTimeoutException e) {
					Log.d(TAG, "socket connect timeout !!");
					e.printStackTrace();
					mHandler.sendEmptyMessage(AudioPlayerActivity.searchLrcInfo_failed);
				} catch (IOException e) {
					e.printStackTrace();
					mHandler.sendEmptyMessage(AudioPlayerActivity.searchLrcInfo_failed);
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
	void resolveData(String str,String savePath){
		Log.d(TAG, "response str is " + str);
		try {
			JSONObject jsonObject = new JSONObject(str);
			int code = jsonObject.getInt("code");
			int count = jsonObject.getInt("count");
			if (count>0) {
				JSONArray resultArray = jsonObject.getJSONArray("result");
				for(int i=0;i<resultArray.length();i++){
					JSONObject tmpObject = (JSONObject) resultArray.opt(i);
					MusicResultData resultData = new MusicResultData();
					resultData.setSinger(tmpObject.getString("artist"));
					resultData.setMusicName(tmpObject.getString("song"));
					resultData.setLyricDownloadUrl(tmpObject.getString("lrc"));
					resultData.setAlbumDownloadUrl(tmpObject.getString("cover"));
					resultData.setSavePath(savePath);
					resultList.add(resultData);
				}
			}
			mHandler.sendEmptyMessage(AudioPlayerActivity.searchLrcInfo_succeed);
		} catch (JSONException e) {
			e.printStackTrace();
			mHandler.sendEmptyMessage(AudioPlayerActivity.searchLrcInfo_failed);
		}
	}
	
	/**
	 * 获取解析后的数据List
	 * @return 解析后的数据List
	 */
	public List<MusicResultData> getDataList(){
		return resultList;
	}
}
