package com.mstar.tv.service.aidl;

import android.os.Parcel;
import android.os.Parcelable;

public enum EN_MS_MPEG_NR implements Parcelable{
	// / MPEG noise reduction off
			MS_MPEG_NR_OFF,
			// / MPEG noise reduction low
			MS_MPEG_NR_LOW,
			// / MPEG noise reduction middle
			MS_MPEG_NR_MIDDLE,
			// / MPEG noise reduction high
			MS_MPEG_NR_HIGH,
			// / total mpeg noise reduction type number
			MS_MPEG_NR_NUM;

	public static final Parcelable.Creator<EN_MS_MPEG_NR> CREATOR = new Parcelable.Creator<EN_MS_MPEG_NR>()
			{
				public EN_MS_MPEG_NR createFromParcel(Parcel in)
				{
					return EN_MS_MPEG_NR.values()[in.readInt()];
				}

				public EN_MS_MPEG_NR[] newArray(int size)
				{
					return new EN_MS_MPEG_NR[size];
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
