package com.mstar.tv.service.aidl;

import android.os.Parcel;
import android.os.Parcelable;

public enum EN_ANTENNA_TYPE implements Parcelable{
	
	E_ROUTE_DVBT,
	E_ROUTE_DVBC,
    E_ROUTE_MAX;

	public static final Parcelable.Creator<EN_ANTENNA_TYPE> CREATOR = new Parcelable.Creator<EN_ANTENNA_TYPE>()
			{
				public EN_ANTENNA_TYPE createFromParcel(Parcel in)
				{
					return EN_ANTENNA_TYPE.values()[in.readInt()];
				}

				public EN_ANTENNA_TYPE[] newArray(int size)
				{
					return new EN_ANTENNA_TYPE[size];
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
