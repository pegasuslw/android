package com.tcl.entity;

import java.util.List;

public class Action_url {
	private String behavior;

	private String action;

	private List<Extra_map> extra_map;

	public void setBehavior(String behavior) {
		this.behavior = behavior;
	}

	public String getBehavior() {
		return this.behavior;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getAction() {
		return this.action;
	}

	public void setExtra_map(List<Extra_map> extra_map) {
		this.extra_map = extra_map;
	}

	public List<Extra_map> getExtra_map() {
		return this.extra_map;
	}

}