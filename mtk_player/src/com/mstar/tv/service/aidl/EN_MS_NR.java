package com.mstar.tv.service.aidl;

import android.os.Parcel;
import android.os.Parcelable;

public enum EN_MS_NR implements Parcelable {
	// / noise reduction off
			MS_NR_OFF,
			// / noise reduction low
			MS_NR_LOW,
			// / noise reduction middle
			MS_NR_MIDDLE,
			// / noise reduction high
			MS_NR_HIGH,
			// / noise reduction auto
			MS_NR_AUTO,
			// / total noise reduction type number
			MS_NR_NUM;

			
			
	public static final Parcelable.Creator<EN_MS_NR> CREATOR = new Parcelable.Creator<EN_MS_NR>()
			{
				public EN_MS_NR createFromParcel(Parcel in)
				{
					return EN_MS_NR.values()[in.readInt()];
				}

				public EN_MS_NR[] newArray(int size)
				{
					return new EN_MS_NR[size];
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
