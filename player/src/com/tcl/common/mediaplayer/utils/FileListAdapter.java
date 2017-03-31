package com.tcl.common.mediaplayer.utils;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tcl.videoplayer.R;


public class FileListAdapter extends BaseAdapter{
	
	private Context adapterContext;
	private ArrayList<FileItemData> mList;
	private LayoutInflater mInflater;
	private int nowposition;
	private String TAG = "FileListAdapter";
	
	public FileListAdapter(Context mContext,ArrayList<FileItemData> list,int index){
		
		mInflater = LayoutInflater.from(mContext);
		adapterContext = mContext;
		mList = list;
		nowposition = index;
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
	
	public void setnowpostion(int index){
		
		nowposition = index;
	}

	 private class ViewHolder{
		   
		    TextView name;
//		    TextView time;
		    ImageView  tippic;
			
		}
	 
	
	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		
		ViewHolder holder = null;
		String name ;
//		String time;
		
		if(arg1 == null){
			arg1 = mInflater.inflate(R.layout.filelistitemxml, null);
			
			holder = new ViewHolder();
			
			holder.name = (TextView)arg1.findViewById(R.id.nameoffile);
//			holder.time = (TextView)arg1.findViewById(R.id.lasttimeoffile);
			holder.tippic = (ImageView)arg1.findViewById(R.id.tipofnowindex);
			
			arg1.setTag(holder);
			
		}else {
			holder = (ViewHolder)arg1.getTag();
			
		}
		
		name = mList.get(arg0).getItemName();
//		time = mList.get(arg0).getItemTime();
		
		
		Log.d(TAG,"now name is "+name+"===========");
		holder.name.setText(name);
//		holder.time.setText("");
		if(arg0 == nowposition){
			holder.tippic.setVisibility(View.VISIBLE);
		}else{
			holder.tippic.setVisibility(View.INVISIBLE);
		}
		
		return arg1;
	}

}
