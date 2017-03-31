package com.tcl.common.mediaplayer.utils;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tcl.videoplayer.R;


public class SettingMenuAdapter extends BaseAdapter{
	
	private Context adapterContext;
	private ArrayList<SettingItemData> mList = new ArrayList<SettingItemData>();
	private LayoutInflater mInflater;
	
	public SettingMenuAdapter(Context mContext,ArrayList<SettingItemData> list){
		
		mInflater = LayoutInflater.from(mContext);
		adapterContext = mContext;
		mList = list;
	}
	
	
	

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mList.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return mList.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}
	
	public void changelist(ArrayList<SettingItemData> list){
		 mList = list;
		 Log.d("videoplayer===","now mList item 2 is "+mList.get(2).getItemValue());
	}
	
	 private class ViewHolder{
		   
		    TextView name;
		    TextView value;
			
		}
	
	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		
		ViewHolder holder = null;
		String name ;
		String value;
		
		name = mList.get(arg0).getItemName();
		value = mList.get(arg0).getItemValue();
		Log.d("videoplayer_setting adpter","now arg0 is "+arg0+"now name is "+name+"now value is "+value);
		
		if(arg1 == null){
			arg1 = mInflater.inflate(R.layout.settingmenuitemxml, null);
			
			holder = new ViewHolder();
			
			holder.name = (TextView)arg1.findViewById(R.id.xmlmenuname);
			holder.value = (TextView)arg1.findViewById(R.id.xmlmenuvalue);
			arg1.setTag(holder);
		}else {
			holder = (ViewHolder)arg1.getTag();
			
		}
		holder.name.setText(name);
		holder.value.setText(value);
		
		return arg1;
	}

}
