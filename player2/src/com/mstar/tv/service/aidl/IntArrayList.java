
package com.mstar.tv.service.aidl;

import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
/*
 * author long.chen
 */
public class IntArrayList extends ArrayList<Integer> implements Parcelable
{
	
	public IntArrayList()
	{  
		super();
	}
	
	public IntArrayList(Parcel in)
	{  
	    super();
	    int size = in.dataAvail();
	    //Log.i("IntArrayList",".......size is "+size+"........");
	    int i = 0;
	    while(i<size)
	    {
	    	in.setDataCapacity(i);
	    	int cur_int = in.readInt();
	    	i+=4;
	    	//Log.i("IntArrayList",".......cur_int is "+cur_int+"........");
	    	//Log.i("IntArrayList",".......i is "+i+"........");
	    	this.add(cur_int);
	    }
	}
	
	public static final Parcelable.Creator<IntArrayList> CREATOR = new Parcelable.Creator<IntArrayList>() 
	{     
		public IntArrayList createFromParcel(Parcel in) 
		{         
			return new IntArrayList(in);     
		}

		public IntArrayList[] newArray(int size) {
			// TODO Auto-generated method stub
			return new IntArrayList[size];
		}      
	};
	
	 @Override 
	 public int describeContents() 
	 {
		 // TODO Auto-generated method stub
		 return 0; 
     }  
	 @Override
	 public void writeToParcel(Parcel arg0, int arg1)
	 {
		 int size = this.size();
		 //Log.i("IntArrayList",".......size is "+size+"..........write....");
		 for (int i = 0; i < size; i++)
		 {
			 arg0.writeInt(this.get(i));
			 //Log.i("IntArrayList",".......this.get("+i+") is "+this.get(i)+"..........write....");
	     } 
	 }
}