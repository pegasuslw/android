package com.mstar.tv.service.aidl;

import android.os.Parcel;
import android.os.Parcelable;

public enum EN_CAB_CONSTEL_TYPE implements Parcelable{
	
	// /< QAM 16
    E_CAB_QAM16,
    // /< QAM 32
    E_CAB_QAM32,
    // /< QAM 64
    E_CAB_QAM64,
    // /< QAM 128
    E_CAB_QAM128,
    // /< QAM 256
    E_CAB_QAM256,
    // /< Invalid value
    E_CAB_INVALID;

	public static final Parcelable.Creator<EN_CAB_CONSTEL_TYPE> CREATOR = new Parcelable.Creator<EN_CAB_CONSTEL_TYPE>()
			{
				public EN_CAB_CONSTEL_TYPE createFromParcel(Parcel in)
				{
					return EN_CAB_CONSTEL_TYPE.values()[in.readInt()];
				}

				public EN_CAB_CONSTEL_TYPE[] newArray(int size)
				{
					return new EN_CAB_CONSTEL_TYPE[size];
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