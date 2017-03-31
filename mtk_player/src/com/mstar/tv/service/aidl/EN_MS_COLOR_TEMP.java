package com.mstar.tv.service.aidl;

import android.os.Parcel;
import android.os.Parcelable;

public enum EN_MS_COLOR_TEMP implements Parcelable{
	// /color temperature cool
	MS_COLOR_TEMP_COOL,
	// /color temperature medium
	MS_COLOR_TEMP_NATURE,
	// /color temperature warm
	MS_COLOR_TEMP_WARM,
	// /color temperature user
	MS_COLOR_TEMP_USER,
	// /color temperature
	MS_COLOR_TEMP_NUM;

	public static final Parcelable.Creator<EN_MS_COLOR_TEMP> CREATOR = new Parcelable.Creator<EN_MS_COLOR_TEMP>()
			{
				public EN_MS_COLOR_TEMP createFromParcel(Parcel in)
				{
					return EN_MS_COLOR_TEMP.values()[in.readInt()];
				}

				public EN_MS_COLOR_TEMP[] newArray(int size)
				{
					return new EN_MS_COLOR_TEMP[size];
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
