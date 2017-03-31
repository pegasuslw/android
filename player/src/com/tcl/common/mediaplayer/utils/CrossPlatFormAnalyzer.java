package com.tcl.common.mediaplayer.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import android.content.Context;
import android.util.Log;

import com.tcl.appconfiger.AppConfiger;
import com.tcl.common.mediaplayer.aidl.CommonConst;
import com.tcl.deviceinfo.TDeviceInfo;
import com.tcl.tvmanager.TTv3DManager;
import com.tcl.tvmanager.TTvPictureManager;
import com.tcl.tvmanager.TvManager;
import com.tcl.tvmanager.pq.PQ;

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

	public boolean isNeed3DFunction(Context context) {

//		TDeviceInfo devinfo = TDeviceInfo.getInstance();
//		String clienttype = devinfo.getClientType(devinfo.getProjectID());
		String devmode = getDevModel();
		try {
//			if(clienttype.equalsIgnoreCase("TCL-EU-MT58K-S1")){
//				return false;
//			}
			if (devmode != null && mNo3Devmodes != null && mNo3Devmodes.contains(devmode)){
				return false;
			}else if(isShow3D(context) && !isDolby(context) ){
				return true;
			}

		} catch (Exception e) {
			return true;// TODO: handle exception
		}
		return false;
	}
	
	/**
	 * 配置项中3D数字表示的含义:0 - 不支持3D 1 - SG 快门式3D 2 - PR 偏光式3D return: 返回3d 或者 3d转2d
	 * 的字符串ID,这样就可以匹配返回值判断3D 和 3D转2D 选项是否显示.返回为0表示都不显示
	 */
	private boolean get3DType(Context context) {
		String type = Utils.getAppConfigerItem(context, CommonConst.CONFIG_3D);
		Log.d(TAG, "get3DType="+type);
		if (!"0".equals(type)) {
				return true;
		} else {
				return false;
		}
	}

	/**
	 * 增加对3D状态的判断，
	 * 1、确定机型是否支持3D(support3D)；
	 * 2、确定4K机型是否支持(is4K2KSupport该值如果返回true，表示是4K平台，且部分支持3D)；
	 * 3、确定是否是4K平台(is4K2K)，如果否，则支持。
	 */
	public boolean isShow3D(Context context) {
		boolean isConfig_3D = get3DType(context);
		boolean isConfig_3D_OLD = Utils.isShowAppConfigerItem(context, CommonConst.CONFIG_3D_OLD);
		Log.d(TAG, "isConfig_3D="+isConfig_3D+" isConfig_3D_OLD="+isConfig_3D_OLD);
		if(isConfig_3D){
			TTv3DManager tv3DManager = TTv3DManager.getInstance(context);
			boolean is4K2KSupport = tv3DManager.is4k2kModeFor3D();
			boolean is4K2K = TTvPictureManager.getInstance(context).is4k2kMode();
			boolean isShow3D = false;
			TDeviceInfo device = TDeviceInfo.getInstance();
			int  support3D = device.get3DStatus(device.getProjectID());
			if(support3D != 1){
				isShow3D = false;
			}else{
				if(is4K2KSupport){
					isShow3D = true;
				}else{
					if(!is4K2K){
						isShow3D = true;
					}else{
						isShow3D = false;
					}
				}
			}
			return isShow3D;
		}else{
			return false;
		}
	}
	//是否支持杜比
	public boolean isDolby(Context context) {
		boolean isDobby = Utils.getAppConfigerItem(context,CommonConst.CONFIG_DOLBY).contains(CommonConst.DOLBY);
		if (!isDobby) {
			return false;
		}
		PQ pq = TvManager.getInstance(context).getPQ();
		Method method = null;
		try {
			method = pq.getClass().getMethod("checkHdrMode");
		} catch (NoSuchMethodException e1) {
			// TODO Auto-generated catch block
			Log.i(TAG, "no checkHdrMode method");
			e1.printStackTrace();
		}
		int mode = 0;
		if (method != null) {
			try {
				mode = (Integer) method.invoke(pq);
				Log.i(TAG, "method.invoke:" + mode);
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				mode = 0;
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				mode = 0;
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				mode = 0;
				e.printStackTrace();
			}
		}
		Utils.printLog(TAG, "hdr checkHdrMode:" + mode);
		int DOLBY_MODE = 2;// dolby
		if (mode == DOLBY_MODE) {// 2 代表dolby
			return true;
		}
		return false;
	}

	private String getDevModel() {

		File datafile = new File("/data/devinfo.txt");
		if (datafile.exists()) {
			FileReader read = null;
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
			}finally{
				if(read!=null){
					try {
						read.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		} else {
			System.out
					.println("==========================devnifo.text doesn't exits!");
			return null;
		}

	}
}
