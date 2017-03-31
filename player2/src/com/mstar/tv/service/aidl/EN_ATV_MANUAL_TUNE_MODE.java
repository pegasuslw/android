package com.mstar.tv.service.aidl;

import android.os.Parcel;
import android.os.Parcelable;

public enum EN_ATV_MANUAL_TUNE_MODE implements Parcelable{
	
	 E_MANUAL_TUNE_MODE_SEARCH_ONE_TO_UP,
     // search one channel, direction = down
     E_MANUAL_TUNE_MODE_SEARCH_ONE_TO_DOWN,
     // fine tune to a given frequency
     E_MANUAL_TUNE_MODE_FINE_TUNE_ONE_FREQUENCY,
     // fine tune, direction = up
     E_MANUAL_TUNE_MODE_FINE_TUNE_UP,
     // fine tune, direction = down
     E_MANUAL_TUNE_MODE_FINE_TUNE_DOWN,
     // undefine
     E_MANUAL_TUNE_MODE_UNDEFINE;

	public static final Parcelable.Creator<EN_ATV_MANUAL_TUNE_MODE> CREATOR = new Parcelable.Creator<EN_ATV_MANUAL_TUNE_MODE>()
			{
				public EN_ATV_MANUAL_TUNE_MODE createFromParcel(Parcel in)
				{
					return EN_ATV_MANUAL_TUNE_MODE.values()[in.readInt()];
				}

				public EN_ATV_MANUAL_TUNE_MODE[] newArray(int size)
				{
					return new EN_ATV_MANUAL_TUNE_MODE[size];
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
