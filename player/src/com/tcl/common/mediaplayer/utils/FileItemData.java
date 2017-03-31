package com.tcl.common.mediaplayer.utils;

public class FileItemData {
	
	
	private String itemName;
	private String itemTime;
	
	public FileItemData(String name,String value){
		
		itemName = name;
		itemTime = value;
		
		
	}
	
	public String getItemName(){
		return itemName;
	}
	
	public String getItemTime(){
		return itemTime;
	}

}
