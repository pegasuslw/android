package com.tcl.common.mediaplayer.http.data;

/*
 * 歌词请求，数据返回类封装
 */
public class MusicResultData {
	private String singer;
	private String MusicName;
	private String LyricDownloadUrl;
	private String AlbumDownloadUrl;
	private String savePath;
	public String getSinger() {
		return singer;
	}
	public void setSinger(String singer) {
		this.singer = singer;
	}
	public String getMusicName() {
		return MusicName;
	}
	public void setMusicName(String musicName) {
		MusicName = musicName;
	}
	public String getLyricDownloadUrl() {
		return LyricDownloadUrl;
	}
	public void setLyricDownloadUrl(String lyricDownloadUrl) {
		LyricDownloadUrl = lyricDownloadUrl;
	}
	public String getAlbumDownloadUrl() {
		return AlbumDownloadUrl;
	}
	public void setAlbumDownloadUrl(String albumDownloadUrl) {
		AlbumDownloadUrl = albumDownloadUrl;
	}
	public String getSavePath() {
		return savePath;
	}
	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}
	
	
	
}
