package com.mstar.tv.service.aidl;

import android.os.Parcel;
import android.os.Parcelable;

public enum EN_ON_OFF_TYPE implements Parcelable
{
	// /off
	 E_OFF,
     // /ON
     E_ON,
     //NUM
     E_NUM;

	public static final Parcelable.Creator<EN_ON_OFF_TYPE> CREATOR = new Parcelable.Creator<EN_ON_OFF_TYPE>()
              {
				public EN_ON_OFF_TYPE createFromParcel(Parcel in)
				{
					return EN_ON_OFF_TYPE.values()[in.readInt()];
				}

				public EN_ON_OFF_TYPE[] newArray(int size)
				{
					return new EN_ON_OFF_TYPE[size];
				}
			};
	
	
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(this.ordinal());		
	}
}	

	
