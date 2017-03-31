package com.mstar.tv.service.aidl;

import android.os.Parcel;
import android.os.Parcelable;

public enum EN_SURROUND_MODE implements Parcelable{
	// /Surround mode type is OFF
	E_SURROUND_MODE_OFF,
	// /Surround mode type is ON
	E_SURROUND_MODE_ON;

	public static final Parcelable.Creator<EN_SURROUND_MODE> CREATOR = new Parcelable.Creator<EN_SURROUND_MODE>()
			{
				public EN_SURROUND_MODE createFromParcel(Parcel in)
				{
					return EN_SURROUND_MODE.values()[in.readInt()];
				}

				public EN_SURROUND_MODE[] newArray(int size)
				{
					return new EN_SURROUND_MODE[size];
				}
			};
	
	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(this.ordinal());
		
	}

}
