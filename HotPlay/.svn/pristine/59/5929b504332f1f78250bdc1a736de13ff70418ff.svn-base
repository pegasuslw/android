package com.tcl.cyberui.entry;

import java.util.List;
import java.util.Map;


import android.util.Log;

public class BlockActionBean {
	
	private static final String TAG = "cyber.BlockActionBean";
	public static final String BEHAVIOR_TYPE_ACTIVITY = "activity";
	public static final String BEHAVIOR_TYPE_BROADCAST = "broadcast";
	public static final String BEHAVIOR_TYPE_SERVICE= "service";

	public static final String BEHAVIOR_TYPE_AD = "ad";
	public static final String BEHAVIOR_TYPE_SUBJECT = "topic";
	
//	public BlockActionBean() {
//		//Log.i(TAG, "constructor"); // TODO Auto-generated
//	}
//
//	public BlockActionBean(String s) {
//		//Log.i(TAG, "constructor"); // TODO Auto-generated
//	}
//	
//	public BlockActionBean(String m_package, String m_activity) {
//		//Log.i(TAG, "constructor"); // TODO Auto-generated
//		behavior ="activity";
//		package_name = m_package;
//		activity_name = m_activity;
//		// constructor stub
//	}

	public String getBehavior() {
		return behavior;
	}

	public void setBehavior(String behavior) {
		this.behavior = behavior;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getPackagename() {
		return package_name;
	}

	public void setPackagename(String packagename) {
		this.package_name = packagename;
	}

	public String getActivityname() {
		return activity_name;
	}

	public void setActivityname(String activityname) {
		this.activity_name = activityname;
	}

	public List<Map<String, String>> getExtramap() {
		return extra_map;
	}

	public void setExtramap(List<Map<String, String>> extramap) {
		this.extra_map = extramap;
	}

	public List<Map<String, String>> getBundlemap() {
		return bundle_map;
	}

	public void setBundlemap(List<Map<String, String>> bundlemap) {
		this.bundle_map = bundlemap;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

//	@JsonProperty("behavior")
	private String behavior;

//	@JsonProperty("action")
	private String action;

	
//	@JsonProperty("package_name")
	private String package_name;


//	@JsonProperty("activity_name")
	private String activity_name;

	
//	@JsonProperty("extra_map")
	private List<Map<String, String>> extra_map;

//	@JsonProperty("bundle_map")
	private List<Map<String, String>> bundle_map;


//	@JsonProperty("uri")
	private String uri;
	
	/**
	 * 父节点的resource
	 */
	//private ResourceBean resource = null;
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		Log.i(TAG, "behavior= " + behavior + "\n action=" + action);
		if (extra_map != null) {
			for (Map<String, String> mExtramap : extra_map) {
				Log.i(TAG, extra_map.toString());
			}
		}
		if (bundle_map != null) {
			for (Map<String, String> mbundlemap : bundle_map) {
				Log.i(TAG, bundle_map.toString());
			}
		}

		return "behavior= " + behavior + "\n action=" + action;
	}

//	public ResourceBean getResource() {
//		return resource;
//	}
//
//	public void setResource(ResourceBean resource) {
//		this.resource = resource;
//	}
}
