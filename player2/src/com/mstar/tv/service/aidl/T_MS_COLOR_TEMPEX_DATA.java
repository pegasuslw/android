package com.mstar.tv.service.aidl;

import android.os.Parcel;
import android.os.Parcelable;

public class T_MS_COLOR_TEMPEX_DATA implements Parcelable{

	public int redgain;
	public int greengain;
	public int bluegain;
	public int redoffset;
	public int greenoffset;
	public int blueoffset;

	public static final Parcelable.Creator<T_MS_COLOR_TEMPEX_DATA> CREATOR = new Parcelable.Creator<T_MS_COLOR_TEMPEX_DATA>()
			{
				public T_MS_COLOR_TEMPEX_DATA createFromParcel(Parcel in)
				{
					return new T_MS_COLOR_TEMPEX_DATA(in.readInt(),in.readInt(),in.readInt(),in.readInt(),in.readInt(),in.readInt());
				}

				public T_MS_COLOR_TEMPEX_DATA[] newArray(int size)
				{
					return new T_MS_COLOR_TEMPEX_DATA[size];
				}
			};
	
	
	public T_MS_COLOR_TEMPEX_DATA(int v1, int v2, int v3, int v4, int v5,
	        int v6)
	{
		this.redgain = v1;
		this.greengain = v2;
		this.bluegain = v3;
		this.redoffset = v4;
		this.greenoffset = v5;
		this.blueoffset = v6;
	}
	
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(redgain);
		dest.writeInt(greengain);
		dest.writeInt(bluegain);
		dest.writeInt(redoffset);
		dest.writeInt(greenoffset);
		dest.writeInt(blueoffset);
	}
	

}
