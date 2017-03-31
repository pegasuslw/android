package com.mstar.tv.service.aidl;

import android.os.Parcel;
import android.os.Parcelable;

public enum EN_AVD_VIDEO_STANDARD_TYPE implements Parcelable{
	
	PAL_BGHI,
    // / Video standard NTSC M
    NTSC_M,
    // / Video standard SECAM
    SECAM,
    // / Video standard NTSC 44
    NTSC_44,
    // / Video standard PAL M
    PAL_M,
    // / Video standard PAL N
    PAL_N,
    // / Video standard PAL 60
    PAL_60,
    // / NOT Video standard
    NOTSTANDARD,
    // / Video standard AUTO
    AUTO,
    // / Max Number
    MAX;

	public static final Parcelable.Creator<EN_AVD_VIDEO_STANDARD_TYPE> CREATOR = new Parcelable.Creator<EN_AVD_VIDEO_STANDARD_TYPE>()
			{
				public EN_AVD_VIDEO_STANDARD_TYPE createFromParcel(Parcel in)
				{
					return EN_AVD_VIDEO_STANDARD_TYPE.values()[in.readInt()];
				}

				public EN_AVD_VIDEO_STANDARD_TYPE[] newArray(int size)
				{
					return new EN_AVD_VIDEO_STANDARD_TYPE[size];
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


