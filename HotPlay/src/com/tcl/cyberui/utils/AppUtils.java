package com.tcl.cyberui.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.tcl.hotplay.R;
//import org.apache.commons.lang3.StringUtils;

/**
 * @author fand
 * 
 */
public class AppUtils {
	private static final String TAG = AppUtils.class.getSimpleName();
	//private static SharedData shareFrequncy;// 频率次数
	public static final String firstPackageName_Settings = "com.tcl.mstar.settings";
	public static final String firstActivityName_Settings = "com.tcl.mstar.settings.TclSettings";
	public static final String secondPackageName_Settings = "com.tcl.settings";
	public static final String secondActivityName_Settings = "com.tcl.settings.MainActivity";

//	public static SharedData getShareFrequency(Context mContext) {
//		if (shareFrequncy == null) {
//			shareFrequncy = new SharedData(mContext, "sortFrequency_file");
//		}
//		return shareFrequncy;
//	}

//	public static boolean isSettingsPackage(String packagename) {
//		return firstPackageName_Settings.equals(packagename)
//				|| secondPackageName_Settings.equals(packagename);
//	}
//
//	public static ComponentName getSettingComponent(Context ctx) {
//		if (isInstall(ctx, firstPackageName_Settings)) {
//			return new ComponentName(firstPackageName_Settings,
//					firstActivityName_Settings);
//		} else if (isInstall(ctx, secondPackageName_Settings)) {
//			return new ComponentName(secondPackageName_Settings,
//					secondActivityName_Settings);
//		} else {
//			return null;
//		}
//	}
//
//	public static boolean isInstall(Context c, String pkgName) {
//		PackageInfo packInfo = null;
//		try {
//			packInfo = c.getPackageManager().getPackageInfo(pkgName,
//					PackageManager.GET_ACTIVITIES);
//		} catch (NameNotFoundException e) {
//			e.printStackTrace();
//		}
//		if (packInfo != null) {
//			return true;
//		} else {
//			return false;
//		}
//	}

//	public static String getVersion() {
//		String version = StringUtils.EMPTY;
//		try {
//			// version = SystemProperties.get("ro.software.version");
//		} catch (Exception e) {
//			e.printStackTrace();
//			version = StringUtils.EMPTY;
//		}
//		Log.d(TAG, "version is " + version);
//		return version;
//
//	}

//	public static ComponentName getComponentNameFromResolveInfo(ResolveInfo info) {
//		if (info.activityInfo != null) {
//			return new ComponentName(info.activityInfo.packageName,
//					info.activityInfo.name);
//		} else {
//			return new ComponentName(info.serviceInfo.packageName,
//					info.serviceInfo.name);
//		}
//	}

//	public static void startApp(Context mContext, String mPackageName_temp,
//			String mActivityName_temp, Intent mIntent) {
//
//		String mPackageName = mPackageName_temp;
//		String mActivityName = mActivityName_temp;
//
//		// 从intent中取出包名和activity名
//		if (!isStringNoNull(mPackageName) && mIntent != null) {
//			if (mIntent.getPackage() != null) {
//				mPackageName = mIntent.getPackage();
//			}
//			if (mIntent.getComponent() != null) {
//				mPackageName = mIntent.getComponent().getPackageName();
//			}
//		}
//
//		if (!isStringNoNull(mActivityName) && mIntent != null) {
//			if (mIntent.getClass().getName() != null) {
//				mActivityName = mIntent.getClass().getName();
//			}
//			if (mIntent.getComponent() != null) {
//				mActivityName = mIntent.getComponent().getClassName();
//			}
//		}
//
//		// 构造最终的intent
//		Intent intent_final = null;
//		if (mIntent == null) {
//			intent_final = getIntentFromPackage(mContext, mPackageName,
//					mActivityName);
//
//		} else {
//			intent_final = mIntent;
//		}
//
//		Log.i(TAG, "startApp pack =" + mPackageName + "; mactivity ="
//				+ mActivityName);
//
////		if (isNeedStorageSource(mPackageName, mActivityName)) { // 设置信源；
////			TVWinManager.getInstance(mContext).setStorageSource();
////
////		}
//
//		if (!isActivityExit(mContext, intent_final)) {// 应用不存在，直接去到商店页面
//			goToMartketDetailPage(mContext, mPackageName);
//			return;
//		}
////		if (isStringNoNull(mPackageName) && isStringNoNull(mActivityName)) {
////			if (ShopUtil.getInstance(mContext).clickApp(mPackageName,
////					mActivityName)) { // 判断是否需要升级提示？
////				return;
////			}
////		
////			/*
////			 * if (MessageUtil.startActivity(mContext, // 是否需要消息盒子启动？
////			 * mPackageName, mActivityName)) { Log.d(TAG, "消息盒子启动Activity");
////			 * return; }
////			 */
////		}
////
////		if (isStringNoNull(mPackageName)) {
////			updateStartFrequency(mContext, mPackageName);
////		}
//
//		try {
//			if(mContext instanceof Activity){
//				 mContext.startActivity(intent_final);
//				((Activity) mContext).overridePendingTransition(R.anim.activity_show,R.anim.activity_hide);
//			} else {
//				((LauncherApp)(mContext.getApplicationContext())).getActivity().startActivity(intent_final);
//				((LauncherApp)(mContext.getApplicationContext())).getActivity().overridePendingTransition(R.anim.activity_show,R.anim.activity_hide);
//			}
//			Log.i(TAG, intent_final.toString());
//			Log.i(TAG, intent_final.getComponent().getPackageName());
//			//姬锐锋
//			//AddRecentAppInfo.getInstance(mContext).addAppInfo(intent_final.getComponent().getPackageName());
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	public static boolean startAppWithoutShop(Context mContext,
			String mPackageName_temp, String mActivityName_temp, Intent mIntent) {

		String mPackageName = mPackageName_temp;
		String mActivityName = mActivityName_temp;

		// 从intent中取出包名和activity名
		if (!isStringNoNull(mPackageName) && mIntent != null) {
			if (mIntent.getPackage() != null) {
				mPackageName = mIntent.getPackage();
			}
			if (mIntent.getComponent() != null) {
				mPackageName = mIntent.getComponent().getPackageName();
			}
		}

		if (!isStringNoNull(mActivityName) && mIntent != null) {
			if (mIntent.getClass().getName() != null) {
				mActivityName = mIntent.getClass().getName();
			}
			if (mIntent.getComponent() != null) {
				mActivityName = mIntent.getComponent().getClassName();
			}
		}

		// 构造最终的intent
		Intent intent_final = null;
		if (mIntent == null) {
			intent_final = getIntentFromPackage(mContext, mPackageName,
					mActivityName);

		} else {
			intent_final = mIntent;
		}

		Log.i(TAG, "startApp pack =" + mPackageName + "; mactivity ="
				+ mActivityName);

//		if (isNeedStorageSource(mPackageName, mActivityName)) { // 设置信源；
//			TVWinManager.getInstance(mContext).setStorageSource();
//
//		}

		if (!isActivityExit(mContext, intent_final)) {// 应用不存在
			return false;
		}
//		if (isStringNoNull(mPackageName) && isStringNoNull(mActivityName)) {
//			if (ShopUtil.getInstance(mContext).clickApp(mPackageName,
//					mActivityName)) { // 判断是否需要升级提示？
//				return true;
//			}
//			/*
//			 * if (MessageUtil.startActivity(mContext, // 是否需要消息盒子启动？
//			 * mPackageName, mActivityName)) { Log.d(TAG, "消息盒子启动Activity");
//			 * return; }
//			 */
//		}
//
//		if (isStringNoNull(mPackageName)) {
//			updateStartFrequency(mContext, mPackageName);
//		}

		try {
			
			if(mContext instanceof Activity){
				 mContext.startActivity(intent_final);
				((Activity) mContext).overridePendingTransition(R.anim.activity_show,R.anim.activity_hide);
			}else {
				//姬锐锋
//				((LauncherApp)(mContext.getApplicationContext())).getActivity().startActivity(intent_final);
//				((LauncherApp)(mContext.getApplicationContext())).getActivity().overridePendingTransition(R.anim.activity_show,R.anim.activity_hide);
			}
			Log.i(TAG, intent_final.toString());
//			try {
//			AddRecentAppInfo.getInstance(mContext).addAppInfo(intent_final.getComponent().getPackageName());
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	private static Intent getIntentFromPackage(Context context,
			String packageName, String activityName) {

		Intent intent = new Intent();
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
				| Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
		intent.setAction(Intent.ACTION_MAIN);
		intent.setComponent(new ComponentName(packageName, activityName));

		return intent;
	}
	
	public static int writeToFile( String str){
		//String strFilePath = context.getPackageResourcePath();
		File fLog = null;
		BufferedWriter out = null;
		try{
			fLog = new File("/data/data/com.tcl.hotplay/", "log.txt");
			out = new BufferedWriter(new FileWriter(fLog));
			out.write(str);
			out.close();
		}catch(Exception e){
			e.printStackTrace();
			return -1;
		}
		 
		return 0;
		
	}

//	private static void updateStartFrequency(Context mContext,
//			String packagename) {
//		if (shareFrequncy == null) {
//			shareFrequncy = new SharedData(mContext, "sortFrequency_file");
//		}
//		int count = shareFrequncy.readData(packagename, 0);
//		count++;
//		Log.e(TAG, "updateStartFrequency name = " + packagename + " count  = "
//				+ count);
//		shareFrequncy.saveData(packagename, count);
//
//	}

	public static boolean isStringNoNull(String des) {
		if (des != null && !des.equalsIgnoreCase("")) {
			return true;
		}
		return false;
	}

	private static boolean isActivityExit(Context ctx, Intent mIntent) {

		// Intent intent = null;
		// if (mIntent == null && isStringNoNull(mPackage)
		// && isStringNoNull(mActivity)) {
		// intent = new Intent();
		// intent.setClassName(mPackage, mActivity);
		// } else {
		// intent = mIntent;
		// }

		if (mIntent != null
				&& ctx.getPackageManager().resolveActivity(mIntent, 0) != null) {
			// 说明系统中存在这个activity
			return true;
		} else {
			return false;
		}

	}

//	public static Drawable getAppIcon(Context ctx, String mPackage, String activityName) {
//		Drawable drawable  = null;		
//		try {
//			ComponentName comp = new ComponentName(mPackage, activityName);
//			   drawable = ctx.getPackageManager().getActivityInfo(comp, 0)
//			     .loadIcon(ctx.getPackageManager());
//		} catch (Exception e1) {
//			e1.printStackTrace();
//		}
//
//		return drawable;
//	}

//	public static boolean goToMartketDetailPage(Context ctx, String packageName) {
//
//		Intent intent = new Intent();
//		ComponentName comp = new ComponentName("com.tcl.appmarket2",
//				"com.tcl.appmarket2.ui.appdetail.AppDetailActivity");
//		intent.setComponent(comp);
//		// intent.putExtra("appid", "34c135d98d3b4494b32f5e24e4380daf");//
//		// 参数:appid，可为空
//		intent.putExtra("apkpkgname", packageName);// 参数:包名，可为空
//		intent.putExtra("aidlFlag", true);// 外部调用商店标识，外部调用时默认为true
//		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//		intent.setAction("android.intent.category.MIAN");
//		try {
//			
//			if(ctx instanceof Activity){
//				 ctx.startActivity(intent);
//				((Activity) ctx).overridePendingTransition(R.anim.activity_show,R.anim.activity_hide);
//			} else {
//				((LauncherApp)(ctx.getApplicationContext())).getActivity().startActivity(intent);
//				((LauncherApp)(ctx.getApplicationContext())).getActivity().overridePendingTransition(R.anim.activity_show,R.anim.activity_hide);
//			}
//			return true;
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		return false;
//
//	}

//	public static boolean isNeedStorageSource(String packageName,
//			String activityName) {
//		boolean flag = true;
//
//		if (mNoNeedStoragePackages.contains(packageName)
//				|| mNoNeedStorageActivitys.contains(activityName)) {
//			flag = false;
//		}
//		Log.i(TAG, "isNeedStorageSource = " + flag);
//		return flag;
//	}
//
//	private static ArrayList<String> mNoNeedStoragePackages = new ArrayList<String>() { // 进入此些包名的应用，不需要切storge信源；
//		{
//			add("com.tcl.settings");
//			add("com.tcl.common.shortcutmenu");
//			add("com.android.systemui");
//			add("com.android.systemui2");
//			add("com.tcl.ui6");
//			add("com.tcl.tv");
//			// add("com.android.tcl.messagebox");
//			add("com.audiocn.kalaok.tv");
//		}
//
//	};
//
//	private static ArrayList<String> mNoNeedStorageActivitys = new ArrayList<String>() {// 进入此些activity名的应用，不需要切storge信源；
//		{
//			add("com.tcl.android.TVwidget.TVwidgetActivity");
//			add("com.tcl.tv.TVActivity");
//			add("com.tcl.insrc.InSrcActivity");
//			add("com.tcl.doublescreen.DoubleScreenActivity");
//			add("ccom.tcl.doublescreenboth.activity.DoubleScreenBothActivity");
//			add("com.tcl.DoubleScreenPic2.Activity.DoubleScreenPic2Activity");
//			add("com.tcl.mstar.settings");
//			add("com.tcl.settings");
////			add("com.android.tcl.messagebox.MainActivity");
//
//		}
//
//	};
	
	
	/**
	 * 获取当前应用是否在前台运行
	 * @param mContext
	 * @param packageName
	 * @return
	 */
//	public static boolean isAppCurrentOnTop(Context mContext,String packageName) {
//		if (packageName.equals("") | packageName == null)
//			return false;
//		ActivityManager am =  (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
//
//		List<RunningAppProcessInfo> runningAppProcessesList = am.getRunningAppProcesses();
//		if(runningAppProcessesList == null || runningAppProcessesList.size() == 0) return false;
//		for (RunningAppProcessInfo runinfo : runningAppProcessesList) {
//			String pn = runinfo.processName;
//			if (pn.equals(packageName)
//					&& runinfo.importance == RunningAppProcessInfo.IMPORTANCE_FOREGROUND)
//				return true;
//		}
//		return false;
//	}
//	
//	
//	/**
//	 * delete files about update
//	 */
//	public static void deleteUpdateFiles(Context mContext) {
//		if(mContext == null) {
//			Log.d(TAG, "delete mContext == null");
//			return;
//		}
//		String filePath = mContext.getFilesDir() + "/";
//    	Log.d(TAG, "delete FilePath ==" + filePath);
//		try {
//			File file = new File(filePath);
//			if(!file.exists()) return;
//			File arr[] = file.listFiles();
//			if(arr == null) {
//				Log.d(TAG, "delete FilePath listFiles() == null");
//				return;
//			}
//			for(int i = 0;i<arr.length;i++){
//				String fileName = arr[i].getName();
//				if(TextUtils.isEmpty(fileName)){
//					Log.d(TAG, "delete fileName == null");
//					continue;
//				}
//				Log.d(TAG, "delete fileName ==" + fileName);
//				String tmp = "";
//				if(fileName.contains(".")) 
//					tmp = fileName.substring(0, fileName.lastIndexOf("."));
//				else
//					tmp = fileName;
//				if(tmp.equals("apks")){
//					Log.d(TAG, "delete apksFile ==" + fileName);
//					File apkFile  = arr[i].getAbsoluteFile();
//					if(apkFile.isFile()) {
//						Log.d(TAG, "delete apk File");
//						apkFile.delete();
//					}else{
//						if(apkFile.isDirectory()){
//							Log.d(TAG, "delete apk Directory");
//							FileUtils.deleteDiretoryAndFile(apkFile);
//						}
//					}
//				}
//			}
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
}
