package com.tcl.common.mediaplayer.http.data;

/*
 * 海报请求，数据返回类封装
 */
public class PosterResultData {
	private String singer;
	private String posterDownloadUrl;
	public String getSinger() {
		return singer;
	}
	public void setSinger(String singer) {
		this.singer = singer;
	}
	public String getPosterDownloadUrl() {
		return posterDownloadUrl;
	}
	public void setPosterDownloadUrl(String posterDownloadUrl) {
		this.posterDownloadUrl = posterDownloadUrl;
	}
	
}
