package com.tcl.util;

import java.lang.reflect.Type;
import java.util.Map;

import com.tcl.cyberui.utils.AppUtils;

import android.util.Log;


/**Json数据的获取：下载、解析、回调。
 * 注意：此类、子类，不知道下载库和解析库，方便以后替换。 
 * 泛型的使用，也造成困难：带有泛型参数的方法，不可以是static
 * @author Administrator
 *
 * @param <DataType>Root下包裹的数据类型
 * @param <BackType>返回给请求者的数据类型
 */
public abstract class DataUtil<RootType,BackType> {
	
	private static final String TAG = "DataUtil";
	
	/**Http获取get
	 * 此类仅供子类调用。
	 * 因为使用泛型，此类不能写成静态方法，子类代码有点麻烦。
	 * @param protocol
	 * @param host
	 * @param port
	 * @param file
	 * @param queryNamesAndValues
	 * @param dataCallback
	 * @param findNames
	 * @return
	 */
	protected Object getData(String protocol,String host,int port,String file,Map<String,String> queryNamesAndValues,
			final DataCallback<BackType> dataCallback,final String[] findNames){
		Log.i(TAG,"getData()");
		String url = HttpUtil.getUrl(protocol, host, port, file, queryNamesAndValues);
		return getOrPostData(true,url,queryNamesAndValues,dataCallback,findNames);
	}
	
	/**Http获取post
	 * @param protocol
	 * @param host
	 * @param port
	 * @param file
	 * @param queryNamesAndValues
	 * @param dataCallback
	 * @param findNames
	 * @return
	 */
	protected Object postData(String protocol,String host,int port,String file,Map<String,String> queryNamesAndValues,
			final DataCallback<BackType> dataCallback,final String[] findNames){
		Log.i(TAG,"postData()");
		String url = HttpUtil.postUrl(protocol, host, port, file, queryNamesAndValues);
		return getOrPostData(false,url,queryNamesAndValues,dataCallback,findNames);
	}
	
	/**子类如果已经有URL了，调用此类更方便。
	 * @param isGet
	 * @param url
	 * @param queryNamesAndValues
	 * @param dataCallback
	 * @param findNames
	 * @return
	 */
	protected Object getOrPostData(boolean isGet,final String url,Map<String,String> queryNamesAndValues,
			final DataCallback<BackType> dataCallback,final String[] findNames){
		Log.i(TAG,"getOrPostData(),url="+url);
		DataCallback<String> httpDataCallback = new DataCallback<String>(){
			@Override
			public void onSuccess(String str) {
				Log.i(TAG,"onSuccess(String str)str="+str);
				AppUtils.writeToFile( url + "\r\n" + str);
				BackType backNode = parseResult(str, findNames);
				if(backNode==null){
					onFailure("backNode==null");
					return;
				}
				dataCallback.onSuccess(backNode);
			}
			@Override
			public void onFailure(String str) {
				dataCallback.onFailure(str);		
			}		
		};
		Object object = null;
		if(isGet){
			object = HttpUtil.httpGet(url,httpDataCallback);
		}else{
			object = HttpUtil.httpPost(url,queryNamesAndValues,httpDataCallback);
		}
		return object;
	}
	
	/**由字符串，解析出结果。
	 * 如果觉得效率不高，或者不够灵活。子类可以覆盖此方法。
	 * @param str
	 * @param findNames
	 * @return
	 */
	protected BackType parseResult(String str,String[] findNames) {
		Type[] types = ReflectionUtil.getParameterizedTypes(this);
		Type rootType=types[0];
		RootType rootNode = (RootType)JsonUtil.json2Object(str,rootType);
		BackType backNode = (BackType)JsonUtil.findHierarchy(rootNode,findNames);	
		return backNode;
	}

	/**取消获取数据
	 * @param object
	 */
	public void cancelData(Object object){
		HttpUtil.httpCancel(object);
	}
	
	/**单元测试
	 * @param args
	 */
	public static void main(String[] args){
//		String s = "a";
//		String b = "get"+s.substring(0,1).toUpperCase()+s.substring(1);
//		System.out.println(b);
	}
}
