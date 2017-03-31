package com.tcl.common.mediaplayer.audioparse;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;

import com.tcl.common.mediaplayer.utils.Utils;

public class AudioFileExtensionFilter implements FileFilter{

	private ArrayList<String> fileExtensions = new ArrayList<String>();
	
	public AudioFileExtensionFilter(){
		fileExtensions.add(".mp3");
		fileExtensions.add(".wma");
		fileExtensions.add(".wav");
		fileExtensions.add(".ape");
		fileExtensions.add(".3gpp");
		fileExtensions.add(".snd");
		fileExtensions.add(".mid");
		fileExtensions.add(".midi");
		fileExtensions.add(".kar");
		fileExtensions.add(".mpga");
		fileExtensions.add(".mpega");
		fileExtensions.add(".mp2");
		fileExtensions.add(".m4a");
		fileExtensions.add(".m3u");
		fileExtensions.add(".sid");
		fileExtensions.add(".aif");
		fileExtensions.add(".aiff");
		fileExtensions.add(".aifc");
		fileExtensions.add(".gsm");
		fileExtensions.add(".m3u");
		fileExtensions.add(".wax");
		fileExtensions.add(".flac");
		fileExtensions.add(".ram");
		fileExtensions.add(".ra");
		fileExtensions.add(".pls");
		fileExtensions.add(".sd2");	
		fileExtensions.add(".aac");	
		fileExtensions.add(".ogg");	
		
	}
	
	public AudioFileExtensionFilter(ArrayList<String> extions){
		fileExtensions = extions;
	}
	
	@Override
	public boolean accept(File pathname) {

		if(pathname.isDirectory()){
			return false;
		}
		if(fileExtensions == null || fileExtensions.size()==0){
			return true;
		}
		String name = pathname.getName().toLowerCase();
		Utils.printLog("AudioFileExtensionFilter", name);
		for(int i=0; i<fileExtensions.size();i++){
			if(name.endsWith(fileExtensions.get(i))){
				return true;
			}
		}
		
		return false;

}
}
