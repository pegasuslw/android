package com.mstar.tv.service.aidl;

import android.os.Parcel;
import android.os.Parcelable;

public enum EN_FIRST_SERVICE_INPUT_TYPE implements Parcelable{
	
	// / First service type is ATV + DTV
    E_FIRST_SERVICE_ALL,
    // / First service type is DTV
    E_FIRST_SERVICE_DTV,
    // / First service type is ATV
    E_FIRST_SERVICE_ATV;

	public static final Parcelable.Creator<EN_FIRST_SERVICE_INPUT_TYPE> CREATOR = new Parcelable.Creator<EN_FIRST_SERVICE_INPUT_TYPE>()
			{
				public EN_FIRST_SERVICE_INPUT_TYPE createFromParcel(Parcel in)
				{
					return EN_FIRST_SERVICE_INPUT_TYPE.values()[in.readInt()];
				}

				public EN_FIRST_SERVICE_INPUT_TYPE[] newArray(int size)
				{
					return new EN_FIRST_SERVICE_INPUT_TYPE[size];
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
