package com.tcl.data;

import java.util.List;

import com.tcl.entity.HotPlayInfo;
import com.tcl.entity.JsonRootBean;
import com.tcl.util.DataCallback;

public class HotPlayListUtil extends HotPlayDataUtil<JsonRootBean,List<HotPlayInfo>> {
	private static final String TAG = "VideoListUtil";
	
	protected String file = "/hotcast/v1/hotcast"; 
	
	//单例
	private static HotPlayListUtil singleInstance = new HotPlayListUtil();
	public static HotPlayListUtil getSingleInstance() {
		return singleInstance;
	}
	private HotPlayListUtil(){
		super();
	}

	
	
	/**获取视频列表。
	 * 因为父类此方法不是静态，所以此方法不是静态。请用单例模式调用
	 * @param page
	 * @param dataCallback
	 * @return
	 */
	public Object requestData(/*int page,int pagesize, */DataCallback<List<HotPlayInfo>> dataCallback){
/*		Log.i(TAG,"requestData(),page="+page);
		Map<String,String> map = new HashMap<String,String>();
		map.put("page",String.valueOf(page));
		map.put("pagesize",String.valueOf(pagesize));
		
		String[] findNames ={"data","infos"};
		return getData(protocol,host,port,file,map,dataCallback,findNames);*/
		String[] findNames ={"result","hotProvince"};
		return getData(protocol,host,port,file,null,dataCallback,findNames);
	}
	
//	/* 如果需要，子类可以复写此方法。
//	 * @see com.tcl.data.DataUtil#parseResult(java.lang.String, java.lang.String[])
//	 */
//	@Override
//	protected VideoListData parseResult(String str,String[] findNames) {
//		// TODO Auto-generated method stub
//		return super.parseResult(str,findNames);
//	}
	
	/**单元测试
	 * @param args
	 */
	public static void main(String[] args){
//		String s = "a";
//		String b = "get"+s.substring(0,1).toUpperCase()+s.substring(1);
//		System.out.println(b);
	}
}
