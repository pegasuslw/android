//package com.mstar.tv.service.skin;
//
//import java.io.BufferedReader;
//import java.io.FileInputStream;
//import java.io.InputStreamReader;
//
//import com.tcl.common.mediaplayer.utils.Utils;
//import com.tvos.common.PictureManager;
//import com.tvos.common.exception.TvCommonException;
//
//import android.content.ContentResolver;
//import android.util.Log;
//
////import com.mstar.tvsettingservice.PictureDesk;
////import com.mstar.tvsettingservice.DataBaseDesk.EN_MS_VIDEOITEM;
////import com.tcl.tv.sys.SYSBackLightOperation;
//
//public class GraphicsManager {
//	private static final long SLEEP_TIME = 14;
//	public static String TAG = "ComMediaPlayerUtils";
//	public void resumeVideoBackLight(final int sourceBackLight,
//			final int targetBackLight, final PictureManager pictureSkin,Long time) {
//		gradientBackLight(sourceBackLight, targetBackLight, pictureSkin,time);
//	}
//
//
//
//	// private void gradientTVBackLight(final short sourceBackLight,
//	// final short targetBackLight, final PictureDesk pictureDesk)
//	// {
//	// Log.v("ZHUQ", "sourceBL = " + sourceBackLight + ", targetBL = " +
//	// targetBackLight);
//	//
//	// System.out.println("sourceBL" + sourceBackLight + "targetBL"
//	// + targetBackLight);
//	// new Thread()
//	// {
//	// public void run()
//	// {
//	// short step = sourceBackLight;
//	// if (step > 100)
//	// {
//	// step = 100;
//	// }
//	// if (step < 0)
//	// {
//	// step = 0;
//	// }
//	// while (step > targetBackLight)
//	// {
//	// try
//	// {
//	// sleep(SLEEP_TIME);
//	// }
//	// catch (InterruptedException e)
//	// {
//	// e.printStackTrace();
//	// }
//	// // if (step / 2 != 0 && step - 2 >= targetBackLight)
//	// // {
//	// // step -= 2;
//	// // }
//	// // else
//	// // {
//	// // step--;
//	// // }
//	// step--;
//	// System.out.println("--" + step);
//	// pictureDesk.SetBacklightDispaly(step);
//	// }
//	// while (step < targetBackLight)
//	// {
//	// try
//	// {
//	// sleep(SLEEP_TIME);
//	// }
//	// catch (InterruptedException e)
//	// {
//	// e.printStackTrace();
//	// }
//	// // if (step / 2 != 0 && step + 2 <= targetBackLight)
//	// // {
//	// // step += 2;
//	// // }
//	// // else
//	// // {
//	// // step++;
//	// // }
//	// step++;
//	// System.out.println("++" + step);
//	// pictureDesk.SetBacklightDispaly(step);
//	// }
//	// short natureLight = pictureDesk
//	// .GetVideoItem(EN_MS_VIDEOITEM.MS_VIDEOITEM_DBC);
//	// pictureDesk.SetNatureLightDisplay(natureLight);
//	// };
//	// }.start();
//	// }
//
//	public void gradientBackLight(final int sourceBackLight,
//			final int targetBackLight, final PictureManager pictureSkin,final Long time) {
//		Utils.printLog(TAG, "sourceBL" + sourceBackLight + "targetBL"
//				+ targetBackLight);
//	
//		new Thread() {
//			public void run() {
//				int step;
//				if(Utils.readStaticBacklight()!=sourceBackLight&&Utils.readStaticBacklight()!=-1){
//					step = Utils.readStaticBacklight();
//					Utils.printLog(TAG, "use StaticBacklight"+ step);
//				}else{
//				    step = sourceBackLight;
//				    Utils.printLog(TAG, "use sourceBackLight"+ step);
//				}
//				
//				Utils.printLog(TAG, "step -----"+step);
//				if (step > 100) {
//					step = 100;
//				}
//				if (step < 0) {
//					step = 0;
//				}
//				if(step == targetBackLight){
//					try {
//						pictureSkin.setBacklight(step);
//						Utils.writeStaticBacklight(step);
//						Utils.printLog(TAG, "step----------" +step);
//					} catch (TvCommonException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//				}
//				while (step > targetBackLight) {
//                    if(time !=Utils.readCurrentTime()){
//                    	break;
//                    }
//					try {
//						sleep(SLEEP_TIME);
//					} catch (InterruptedException e) {
//
//						e.printStackTrace();
//					}
//					// if (step / 2 != 0 && step - 2 >= targetBackLight)
//					// {
//					// step -= 2;
//					// }
//					// else
//					// {
//					// step--;
//					// }
//					// step = (short) (step-5);
//					// if(step < targetBackLight){
//					// step = targetBackLight;
//					// }
//					step--;
//
//					// pictureDesk.SetBacklightDispaly(step);
//					try {
//
//						pictureSkin.setBacklight(step);
//						Utils.writeStaticBacklight(step);
//						Utils.printLog(TAG, "step----------" +step);
//
//					} catch (TvCommonException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//				}
//				while (step < targetBackLight) {
//				    if(time !=Utils.readCurrentTime()){
//	                    	break;
//	                 }
//					try {
//						sleep(SLEEP_TIME);
//					} catch (InterruptedException e) {
//
//						e.printStackTrace();
//					}
//					// if (step / 2 != 0 && step + 2 <= targetBackLight)
//					// {
//					// step += 2;
//					// }
//					// else
//					// {
//					// step++;
//					// }
//
//					// step = (short) (step+5);
//					// if(step>targetBackLight){
//					// step = targetBackLight;
//					// }
//					step++;
//
//					// pictureDesk.SetBacklightDispaly(step);
//					try {
//						pictureSkin.setBacklight(step);
//						Utils.writeStaticBacklight(step);
//						Utils.printLog(TAG, "step +++++++++" + step);
//					} catch (TvCommonException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//				}
//
//			};
//		}.start();
//	}
//
//	public void gradientBackLightLauncher(final short sourceBackLight,
//			final short targetBackLight, final PictureManager pictureSkin) {
//		System.out.println("sourceBL" + sourceBackLight + "targetBL"
//				+ targetBackLight);
//		new Thread() {
//			public void run() {
//
//				short step = sourceBackLight;
//				if (step > 100) {
//					step = 100;
//				}
//				if (step < 0) {
//					step = 0;
//				}
//				while (step > targetBackLight) {
//
//					try {
//						sleep(SLEEP_TIME);
//					} catch (InterruptedException e) {
//
//						e.printStackTrace();
//					}
//					// if (step / 2 != 0 && step - 2 >= targetBackLight)
//					// {
//					// step -= 2;
//					// }
//					// else
//					// {
//					// step--;
//					// }
//
//					step--;
//
//					// pictureDesk.SetBacklightDispaly(step);
//					try {
//
//						pictureSkin.setBacklight(step);
//						Utils.printLog("gradientBackLightLauncher", "" + step);
//
//					} catch (TvCommonException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//				}
//				while (step < targetBackLight) {
//
//					try {
//						sleep(SLEEP_TIME);
//					} catch (InterruptedException e) {
//
//						e.printStackTrace();
//					}
//					// if (step / 2 != 0 && step + 2 <= targetBackLight)
//					// {
//					// step += 2;
//					// }
//					// else
//					// {
//					// step++;
//					// }
//
//					step++;
//
//					// pictureDesk.SetBacklightDispaly(step);
//					try {
//						pictureSkin.setBacklight(step);
//						Utils.printLog("gradientBackLightLauncher", "" + step);
//					} catch (TvCommonException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//				}
//
//			};
//		}.start();
//	}
//
//}
