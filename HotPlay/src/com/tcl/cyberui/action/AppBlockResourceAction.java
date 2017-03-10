package com.tcl.cyberui.action;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.tcl.cyberui.entry.BlockActionBean;
import com.tcl.cyberui.utils.AppUtils;


public class AppBlockResourceAction implements BlockResourceAction {

	private static final String TAG = "AppBlockResourceAction";

	private static final String VALUE_TYPE_STRING = "string";
	private static final String VALUE_TYPE_INT = "int";
	private static final String VALUE_TYPE_FLOAT = "float";
	private static final String VALUE_TYPE_BOOL = "boolean";
	private static final String VALUE_TYPE_LONG = "long";
	private static final String VALUE_TYPE_JSON = "json";

	private static final String KEY_MIMETYPE = "cyberui_mimetype";

	private static final String MAP_TYPE = "type";
	private static final String MAP_KEY = "key";
	private static final String MAP_VALUE = "value";

	private int PARSER_TYPE_EXTRA = 1;
	private int PARSER_TYPE_BUNDLE = 2;
	private final int nZeroId = 0;
	private Intent mIntent = null;
	private BlockActionBean mActionBean = null;
	private String mPackageName, mActivityName = null;
	private Bundle mBundle = null;
	private String mtest = "";
	private String mMimeType = null;

	protected Context mContext;

	public Intent getIntent() {
		return mIntent;
	}

	public AppBlockResourceAction(Context ctx) {
		mContext = ctx;
	}

	public String getPackageName() {
		return mPackageName;
	}
//姬锐锋注释，在cyberui以外项目不用
//	public AppBlockResourceAction(Context ctx, String jsonStr) {
//		this(ctx);
//		// ObjectMapper mapper = JsonUtils.createObjectMapper();
//		BlockActionBean bean = null;
//		try {
//			// bean = mapper.readValue(jsonStr, BlockActionBean.class);
//			bean = CyberDataManager.getResultFromJsonString(jsonStr,
//					BlockActionBean.class);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		setBlockActionBean(bean);
//	}

	public AppBlockResourceAction(Context ctx, BlockActionBean bean) {
		this(ctx);
		setBlockActionBean(bean);
	}

	public void setBlockActionBean(BlockActionBean bean) {

		if (bean != null) {
			mActionBean = bean;
			mIntent = new Intent();
			if (BlockActionBean.BEHAVIOR_TYPE_ACTIVITY.equalsIgnoreCase(bean
					.getBehavior())) {
				// mIntent = new Intent();
//				if (!(mContext instanceof Activity)) {
					mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//				}
				String m_action = bean.getAction();

				mPackageName = bean.getPackagename();
				if (isStringNoNull(mPackageName)) {
					mActivityName = bean.getActivityname();
					if (!isStringNoNull(mActivityName)) {
						mActivityName = getActivityNameFromPackage(mContext,
								mPackageName);
					}
					if (isStringNoNull(mActivityName)) {
						ComponentName comp = new ComponentName(mPackageName,
								mActivityName);
						mIntent.setComponent(comp);
					}

				}
				if (isStringNoNull(m_action))// action已经制定，则不再需要包名之类；
				{
					mIntent.setAction(m_action.trim());
				}

			} else if (BlockActionBean.BEHAVIOR_TYPE_BROADCAST
					.equalsIgnoreCase(bean.getBehavior())
					|| BlockActionBean.BEHAVIOR_TYPE_SERVICE
							.equalsIgnoreCase(bean.getBehavior())) {

				String m_action = bean.getAction();

				if (isStringNoNull(m_action)) {
					mIntent.setAction(m_action.trim());
				}
				mPackageName = bean.getPackagename();
				mActivityName = bean.getActivityname();
				if (isStringNoNull(mActivityName)
						&& isStringNoNull(mPackageName)) {
					ComponentName comp = new ComponentName(mPackageName,
							mActivityName);
					mIntent.setComponent(comp);
				}

			}
			if (mIntent != null) {
				parserIntentExTraData(bean.getExtramap(), PARSER_TYPE_EXTRA);
				parserIntentExTraData(bean.getBundlemap(), PARSER_TYPE_BUNDLE);
				if (mBundle != null) {
					mIntent.putExtras(mBundle);
				}
				mIntent.setData(null);
				String url = bean.getUri();
				if (isStringNoNull(url) || isStringNoNull(mMimeType)) {
					Uri data = null;
					if (url != null) {
						data = Uri.parse(url);
					}
					mIntent.setDataAndType(data, mMimeType);
				}

			}
		}

	}

	public boolean onClick() {

		if (mActionBean != null)
			mActionBean.toString();
		if (mActionBean != null && mIntent != null) {
			if (BlockActionBean.BEHAVIOR_TYPE_BROADCAST
					.equalsIgnoreCase(mActionBean.getBehavior())) {// 广播方式启动应用，只能设置storage信源；
				//姬锐锋注释，在cyberui以外项目不用
				//TVWinManager.getInstance(mContext).setStorageSource();
				mIntent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
				mContext.sendBroadcast(mIntent);
				return true;
			} else if (BlockActionBean.BEHAVIOR_TYPE_ACTIVITY
					.equalsIgnoreCase(mActionBean.getBehavior())) {// Activity方式启动应用，判断流程比较多

				ResolveInfo info = getActivityResovleInfoExist(mContext,
						mIntent);
				if (info != null && info.activityInfo != null) {
					Log.i(TAG, "resolvePackageName="
							+ info.activityInfo.packageName);
					if (!mContext.getPackageName().equalsIgnoreCase(// 非网格UI包名，增加clear参数；
							info.activityInfo.packageName)) {
						mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
					}

				}

				return AppUtils.startAppWithoutShop(mContext,
						mActionBean.getPackagename(),
						mActionBean.getActivityname(), mIntent);

			} else if (BlockActionBean.BEHAVIOR_TYPE_SERVICE
					.equalsIgnoreCase(mActionBean.getBehavior())) {// service方式启动应用，只能设置storage信源；
				//姬锐锋注释，在cyberui以外项目不用
				//TVWinManager.getInstance(mContext).setStorageSource();
				mContext.startService(mIntent);
				return true;
			}

		}
		return false;
	}

	private ResolveInfo getActivityResovleInfoExist(Context ctx, Intent mIntent) {

		if (mIntent != null && ctx != null) {

			return ctx.getPackageManager().resolveActivity(mIntent, 0);
		} else {
			return null;
		}

	}
//姬锐锋
//	public void goToShopPage() {
//		if (mPackageName != null && !isHisFavDeal()) {
//			AppUtils.goToMartketDetailPage(mContext, mPackageName);
//		}
//	}

//	private boolean isHisFavDeal() {
//		// 2.0取下来1.0的模板，处理历史收藏；
//		if ("com.tcl.cyberui".equals(mPackageName)) {
//			Intent mHisFavIntent = null;
//			if ("com.tcl.cyberui.historyfavourites.HistoryActivity"
//					.equals(mActivityName)) {
//				mHisFavIntent = new Intent("com.tcl.cyberui.history");
//			} else if ("com.tcl.cyberui.historyfavourites.FavouritesActivity"
//					.equals(mActivityName)) {
//				mHisFavIntent = new Intent("com.tcl.cyberui.favourite");
//			} else if ("com.tcl.cyberui.historyfavourites.HisFavourFragmentActivity"
//					.equals(mActivityName)) {
//				mHisFavIntent = new Intent("com.tcl.cyberui.RECENTS");
//			}
//			if (mHisFavIntent != null) {
//				return AppUtils.startAppWithoutShop(mContext, null, null,
//						mHisFavIntent);
//			}
//
//		}
//		return false;
//	}

	private boolean isStringNoNull(String des) {
		if (des != null && !des.equalsIgnoreCase("")) {
			return true;
		}
		return false;
	}

	private void parserIntentExTraData(List<Map<String, String>> dataList,
			int parserType) {
		if (mIntent == null || dataList == null) {
			return;
		}

		if (parserType == PARSER_TYPE_BUNDLE && mBundle == null) {
			mBundle = new Bundle();
		}

		for (Map<String, String> dataMap : dataList) {
			if (dataMap != null) {
				String type = (String) dataMap.get(MAP_TYPE);
				String key = (String) dataMap.get(MAP_KEY);
				String value = null;
				if (!VALUE_TYPE_JSON.equalsIgnoreCase(type)) {
					value = (String) dataMap.get(MAP_VALUE);
				} else {
					value = "json";
				}
				if (isStringNoNull(type) && isStringNoNull(key)
						&& isStringNoNull(value)) {
					if (VALUE_TYPE_STRING.equalsIgnoreCase(type)) {
						if (KEY_MIMETYPE.equalsIgnoreCase(key)) {
							mMimeType = value;
							Log.i(TAG, "mMimeType =" + value);
						} else {
							if (parserType == PARSER_TYPE_BUNDLE) {
								mBundle.putString(key, value);
							} else if (parserType == PARSER_TYPE_EXTRA) {

								mIntent.putExtra(key, value);
							}
						}

					} else if (VALUE_TYPE_FLOAT.equalsIgnoreCase(type)) {
						try {
							float value_des = Float.parseFloat(value);
							if (parserType == PARSER_TYPE_BUNDLE) {
								mBundle.putFloat(key, value_des);
							} else if (parserType == PARSER_TYPE_EXTRA) {
								mIntent.putExtra(key, value_des);
							}
						} catch (Exception ex) {
							ex.printStackTrace();
						}

					} else if (VALUE_TYPE_INT.equalsIgnoreCase(type)) {
						try {
							int value_des = Integer.parseInt(value);
							if (parserType == PARSER_TYPE_BUNDLE) {
								mBundle.putInt(key, value_des);
							} else if (parserType == PARSER_TYPE_EXTRA) {
								mIntent.putExtra(key, value_des);
							}
						} catch (Exception ex) {
							ex.printStackTrace();
						}

					} else if (VALUE_TYPE_BOOL.equalsIgnoreCase(type)) {
						try {
							boolean value_des = Boolean.parseBoolean(value);
							if (parserType == PARSER_TYPE_BUNDLE) {
								mBundle.putBoolean(key, value_des);
							} else if (parserType == PARSER_TYPE_EXTRA) {
								mIntent.putExtra(key, value_des);
							}
						} catch (Exception ex) {
							ex.printStackTrace();
						}

					} else if (VALUE_TYPE_LONG.equalsIgnoreCase(type)) {
						try {
							long value_des = Long.parseLong(value);
							if (parserType == PARSER_TYPE_BUNDLE) {
								mBundle.putLong(key, value_des);
							} else if (parserType == PARSER_TYPE_EXTRA) {
								mIntent.putExtra(key, value_des);
							}
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					} else if (VALUE_TYPE_JSON.equalsIgnoreCase(type)) {
						try {
							Gson g = new Gson();
							Map<String, String> values = g.fromJson(
									dataMap.get(MAP_VALUE), HashMap.class);
							if (values != null && !values.isEmpty()) {
								Iterator<String> iterator = values.keySet()
										.iterator();
								String mValue_json = "{";
								while (iterator.hasNext()) {
									String keys = iterator.next();
									mValue_json += "\"" + keys + "\":\""
											+ values.get(keys) + "\"";
									if (iterator.hasNext()) {
										mValue_json += ",";
									}
								}
								mValue_json += "}";
								mtest = mValue_json;
								if (parserType == PARSER_TYPE_BUNDLE) {
									mBundle.putString(key, mValue_json);
								} else if (parserType == PARSER_TYPE_EXTRA) {
									mIntent.putExtra(key, mValue_json);
								}
							}
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}
				}

			}
		}
	}

	private String getActivityNameFromPackage(Context context,
			String packageName) {
		try {
			PackageManager pm = context.getPackageManager();
			PackageInfo pi = pm.getPackageInfo(packageName, 0);
			Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
			resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER).setPackage(
					pi.packageName);
			List<ResolveInfo> apps = pm.queryIntentActivities(resolveIntent, 0);
			ResolveInfo info = apps.iterator().next();
			if (info != null) {
				return info.activityInfo.name;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return null;
	}

}
