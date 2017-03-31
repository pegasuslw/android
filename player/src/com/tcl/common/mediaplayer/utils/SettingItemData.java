package com.tcl.common.mediaplayer.utils;

public class SettingItemData {
	
	
	private String itemName;
	private String itemValues;
	
	public SettingItemData(String name,String value){
		
		itemName = name;
		itemValues = value;
		
		
	}
	
	public String getItemName(){
		return itemName;
	}
	
	public String getItemValue(){
		return itemValues;
	}

}
