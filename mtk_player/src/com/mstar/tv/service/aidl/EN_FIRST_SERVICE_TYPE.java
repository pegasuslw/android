package com.mstar.tv.service.aidl;

import android.os.Parcel;
import android.os.Parcelable;

public enum EN_FIRST_SERVICE_TYPE implements Parcelable{
	
	// / Boot type is on time function of EPG
    E_ON_TIME_BOOT,
    // / Boot type is AC or DC
    E_AC_DC_BOOT,
    // / Auto scan type
    E_AUTO_SCAN,
    // / Menu scan type
    E_MENU_SCAN,
    // / DEFAULT type
    E_DEFAULT;

	public static final Parcelable.Creator<EN_FIRST_SERVICE_TYPE> CREATOR = new Parcelable.Creator<EN_FIRST_SERVICE_TYPE>()
			{
				public EN_FIRST_SERVICE_TYPE createFromParcel(Parcel in)
				{
					return EN_FIRST_SERVICE_TYPE.values()[in.readInt()];
				}

				public EN_FIRST_SERVICE_TYPE[] newArray(int size)
				{
					return new EN_FIRST_SERVICE_TYPE[size];
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

