package com.tcl.common.mediaplayer.video.contrl;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;

public class VideoExtSubtitleFilter implements FileFilter{

	private ArrayList<String> fileExtensions = new ArrayList<String>();
	private String mFileBeginName = null;
	
	public VideoExtSubtitleFilter(String mFileBegin){
		fileExtensions.add(".srt");
		fileExtensions.add(".ssa");
		fileExtensions.add(".ass");
		fileExtensions.add(".smi");
		fileExtensions.add(".idx");
		fileExtensions.add(".txt");
		mFileBeginName = mFileBegin;
	}

	
	@Override
	public boolean accept(File pathname) {

		if(pathname.isDirectory() || mFileBeginName==null){
			return false;
		}
		if(fileExtensions == null || fileExtensions.size()==0){
			return true;
		}
		String name = pathname.getName();
		for(int i=0; i<fileExtensions.size();i++){
			if(name.startsWith(mFileBeginName)&&name.endsWith(fileExtensions.get(i))){
				if(fileExtensions.get(i).equalsIgnoreCase("idx")){
					if(new File(name.substring(0, name.lastIndexOf("."))+".sub").exists()){
						return true;
					}
					return false;
				}
				return true;
				
			}
		}
		
		return false;

}
}
