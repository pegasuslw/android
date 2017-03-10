package com.tcl.entity;

import android.util.Log;

import com.tcl.cyberui.entry.BlockActionBean;

public class HotPlayInfo {
	private String provinceMap;

	private String provinceName;

	private String videoPic;

	private String videoName;

	private String countPeople;

	private BlockActionBean actionUrl;

	public void setProvinceMap(String provinceMap) {
		this.provinceMap = provinceMap;
	}

	public String getProvinceMap() {
		return this.provinceMap;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getProvinceName() {
		return this.provinceName;
	}

	public void setVideoPic(String videoPic) {
        Log.i("liuwei","videopic = " + videoPic);
        Log.i("liuwei", new Exception().toString());
		this.videoPic = videoPic;
	}

	public String getVideoPic() {
		return this.videoPic;
	}

	public void setVideoName(String videoName) {
		this.videoName = videoName;
	}

	public String getVideoName() {
		return this.videoName;
	}

	public void setCountPeople(String countPeople) {
		this.countPeople = countPeople;
	}

	public String getCountPeople() {
		return this.countPeople;
	}

	public BlockActionBean getActionUrl() {
		return actionUrl;
	}

	public void setActionUrl(BlockActionBean actionUrl) {
		this.actionUrl = actionUrl;
	}



}
