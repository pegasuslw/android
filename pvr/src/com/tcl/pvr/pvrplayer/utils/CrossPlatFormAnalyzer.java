package com.tcl.pvr.pvrplayer.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class CrossPlatFormAnalyzer {

	private ArrayList<String> mNo3Devmodes;
	private String TAG = "CrossPlatFormAnalyzer";

	public CrossPlatFormAnalyzer() {
		mNo3Devmodes = new ArrayList<String>();
		mNo3Devmodes.add("TCL-CN-MS801-E5300A");
		mNo3Devmodes.add("TCL-CN-MS600-F2590E");
		mNo3Devmodes.add("TCL-CN-MS600-F2570E");
		mNo3Devmodes.add("TCL-CN-MS600-F2510E");
		mNo3Devmodes.add("IKEA-CN-MS801-UPP10");	
		mNo3Devmodes.add("TCL-CN-MS600-E5000A");	
		mNo3Devmodes.add("TCL-CN-MS600-E5010A");	
		mNo3Devmodes.add("TCL-CN-MS600-E5070A");	
		mNo3Devmodes.add("TCL-CN-MS600-E5090A");	
		mNo3Devmodes.add("TCL-CN-MS600-E4690A-S");
	}

	public boolean isNeed3DFunction() {

		String devmode = getDevModel();
		try {
			if (devmode != null && mNo3Devmodes != null && mNo3Devmodes.contains(devmode)){
				return false;
			}

		} catch (Exception e) {
			return true;// TODO: handle exception
		}
		return true;
	}

	private String getDevModel() {

		File datafile = new File("/data/devinfo.txt");
		if (datafile.exists()) {
			FileReader read;
			try {
				read = new FileReader(datafile);
				BufferedReader br = new BufferedReader(read);
				if (br.ready()) {

					String devmodel_read = null;
					String tem;
					while ((tem = br.readLine()) != null) {
						tem.trim();
						if (tem.startsWith("devmodel")) {
							devmodel_read = tem;

							break;
						}

					}
					br.close();
					System.out
							.println("================================= Get Real devmodel_read ="
									+ devmodel_read);
					if (devmodel_read != null && devmodel_read.contains("=")) {
						devmodel_read = devmodel_read.substring(devmodel_read
								.indexOf("=") + 1);
						return devmodel_read;
					}

				}
				return null;

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		} else {
			System.out
					.println("==========================devnifo.text doesn't exits!");
			return null;
		}

	}
}
