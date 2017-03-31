package com.forcetech.android;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;

import org.xml.sax.InputSource;


import android.R.string;
import android.content.ContentResolver;
import android.content.Context;
import android.tcl.mediaplayer.forceTV.SqlCommon;
import android.util.Log;

public class ForceTV {
	private static final String TAG = "ForceTV";
	private static String ERROR_LICENSE_DATA = "000";
//	private static boolean p2pIsStart = false;
//
//	public static void initForceClient(Context ctx) {
//		Log.d(TAG, "Init Force P2P.........");
//		try {
//			Process process = Runtime.getRuntime().exec("netstat");
//			BufferedReader bufferedReader = new BufferedReader(
//					new InputStreamReader(process.getInputStream()), 1024);
//			String line = null;
//			while ((line = bufferedReader.readLine()) != null) {
//				if (line.contains("0.0.0.0:9906")) {
//					p2pIsStart = true;
//					Log.d(TAG, "ForceTV almost start.");
//				}
//			}
//			// Log.i(TAG,"initForceClient(), MyEnv:ANDROID_PROPERTY_WORKSPACE = "
//			// + System.getenv("ANDROID_PROPERTY_WORKSPACE"));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		if (!p2pIsStart) {
//			Log.d(TAG, "Start ForceTV");
//			Log.d(TAG, String.valueOf(start(9906, 20 * 1024 * 1024)));
//			SetLicenseData(ctx);
//		}
//	}
	
	public static void initForceClient(Context ctx) {
		stop();
		Log.d(TAG, "Start ForceTV");
		Log.d(TAG, String.valueOf(start(9906, 20 * 1024 * 1024)));
		SetLicenseData(ctx);
	}
	
	private static void SetLicenseData(Context ctx) {
		try{
			SqlCommon sqlcommon = new SqlCommon();
			ContentResolver resolver = ctx.getContentResolver();
			String deviceid = sqlcommon.getDeviceid(resolver);
			String licenseData = sqlcommon.getLicenseData(resolver);
			Log.d(TAG, "SetLicenseData(), deviceid = " + deviceid + ", licenseData = " + licenseData);
			if (IsLicenseDataAvailable(licenseData)) {
				String cmd_url = "http://127.0.0.1:9906/cmd.xml?cmd=set_device &device_id="
					+ deviceid
					+ "&lencese="
					+ licenseData
					+ "&device_ex1= &device_ex2= ";
				Execute(cmd_url);
			}
		}catch (Exception e) {
		e.printStackTrace();// TODO: handle exception
		}

	}

	private static boolean IsLicenseDataAvailable(String licenseData) {
		if (licenseData.equalsIgnoreCase(ERROR_LICENSE_DATA)) {
			Log.i(TAG, "Invalid licensedata");
			return false;
		}
		return true;
	}
	
	private static void Execute(String cmdUrl) {
		URL url;
		try {
			url = new URL(cmdUrl);
			InputSource is = new InputSource(new InputStreamReader(url
					.openStream(), "UTF-8"));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public native static int start(int port, int size);

	public native static int stop();

	static {
		System.loadLibrary("forcetv");
	}
}
