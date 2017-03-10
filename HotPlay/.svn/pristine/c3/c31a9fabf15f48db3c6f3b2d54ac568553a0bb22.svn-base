package com.tcl.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonUtil {
	
	private static final String TAG = "JsonUtil";
	private static Gson mGson = new GsonBuilder()
//	.setLenient()// json宽松  
//    .enableComplexMapKeySerialization()//支持Map的key为复杂对象的形式  
//    .serializeNulls() //智能null  
//    .setPrettyPrinting()// 调教格式  
//    .disableHtmlEscaping() //默认是GSON把HTML 转义的   
	.create(); 
	
	/**不依赖于泛型的
	 * @param json
	 * @param rootType
	 * @return
	 */
	public static Object json2Object(String json,Type rootType){
		Log.i(TAG,"parseData(String json,Type rootType)");
		if(json==null||json.isEmpty()){
			return null;
		}
		long startTime= System.currentTimeMillis();
		Object root = null;
		try{
			 root = mGson.fromJson(json, rootType);
		}catch(Exception e){
			Log.e(TAG,e.toString());
		}
		Log.i(TAG,"parseData pastTime="+(System.currentTimeMillis()-startTime));
		return root;
	}
	
	public static Object findHierarchy(Object nood,String[] findNames/*,Type rootType*/){
		Log.i(TAG,"parseResult(String json,Type rootType)");
		if(nood==null){
			return null;
		}

		long time = System.currentTimeMillis();
		for(String s:findNames){
			nood = findField(nood,s);
			if(nood==null){
				break;
			}
		}
		
		Log.i(TAG,"findHierarchy pastTime="+(System.currentTimeMillis()-time));
		return nood;
	}
	
	public static Object findField(Object o,String fieldName){
		Log.i(TAG,"findField(),o="+o);
		if (o==null || fieldName.isEmpty()) {
			return null;
		}
		
		Object back = null;
		//步骤一：get方法名称。单元测试通过，长度为1的字符串也通过。
		String getMethodName = "get"+fieldName.substring(0, 1).toUpperCase()+fieldName.substring(1);
		Log.i(TAG,"getMethodName="+getMethodName);
		//寻找get方法。
		
		long time = System.currentTimeMillis();
		try {
			Method getMethod = o.getClass().getDeclaredMethod(getMethodName);
			Log.i(TAG,"getMethod="+getMethod);
			Log.i(TAG,"getMethod.getReturnType()="+getMethod.getReturnType());
			if(getMethod!=null /*&& getMethod.getReturnType().equals(fieldClass)*/){
				//无需类型检查，上面已查
				back = getMethod.invoke(o);
				Log.i(TAG,"back="+back);
			}
			if(back!=null){
				Log.i(TAG,"back!=null,return back="+back);
				return back;
			}
		} catch (Exception e) {
			Log.i(TAG,e.toString());
			//打印栈要花10-30ms
			//打印string花0-10ms，不稳定，0多些
			//不打印话0-1ms
		}
		Log.i(TAG,"pastTime1="+(System.currentTimeMillis()-time));
		//步骤二：fieldName
		try {
			 Field  field = o.getClass().getDeclaredField(fieldName);
			 Log.i(TAG,"field="+field);
			 if(field!=null /*&& field.getClass().equals(fieldClass)*/){
				 field.setAccessible(true);//设置允许访问  
					//无需类型检查，上面已查
					back = field.get(o);
					Log.i(TAG,"back="+back);
			 }			
			 if(back!=null){
				Log.i(TAG,"back!=null,return back="+back);
				return back;
			 }
		} catch (Exception e) {
			Log.i(TAG,e.toString());
		}
		Log.i(TAG,"pastTime2="+(System.currentTimeMillis()-time));
		return back;
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
