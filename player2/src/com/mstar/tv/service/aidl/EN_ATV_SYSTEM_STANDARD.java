package com.mstar.tv.service.aidl;

import android.os.Parcel;
import android.os.Parcelable;

public enum EN_ATV_SYSTEM_STANDARD implements Parcelable{
	
	// /ATV_SYSTEM_STANDARDS_BG
    E_BG,
    // /ATV_SYSTEM_STANDARDS_DK
    E_DK,
    // /ATV_SYSTEM_STANDARDS_I
    E_I,
    // /ATV_SYSTEM_STANDARDS_L
    E_L,
    // /ATV_SYSTEM_STANDARDS_M
    E_M,
    // /ATVSYSTEM_STANDRADS_NUM
    E_NUM;

	public static final Parcelable.Creator<EN_ATV_SYSTEM_STANDARD> CREATOR = new Parcelable.Creator<EN_ATV_SYSTEM_STANDARD>()
			{
				public EN_ATV_SYSTEM_STANDARD createFromParcel(Parcel in)
				{
					return EN_ATV_SYSTEM_STANDARD.values()[in.readInt()];
				}

				public EN_ATV_SYSTEM_STANDARD[] newArray(int size)
				{
					return new EN_ATV_SYSTEM_STANDARD[size];
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

