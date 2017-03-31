package com.tcl.common.mediaplayer.lyric;
/**
 * @author  :Cathy
 * @version  ：2015年6月23日 下午1:41:28
 * 类说明  http://cloud.tcl.com/api/lyric/:song/:
 *		电视端向服务器请求歌词信息时返回的歌词信息
 */
public class LyricInfo {
	private String artist;
	private String song;
	private String lrc;
	private String cover;
	
	public String getArtist() {
		return artist;
	}
	public void setArtist(String artist) {
		this.artist = artist;
	}
	public String getSong() {
		return song;
	}
	public void setSong(String song) {
		this.song = song;
	}
	public String getLrc() {
		return lrc;
	}
	public void setLrc(String lrc) {
		this.lrc = lrc;
	}
	public String getCover() {
		return cover;
	}
	public void setCover(String cover) {
		this.cover = cover;
	}
	
}
