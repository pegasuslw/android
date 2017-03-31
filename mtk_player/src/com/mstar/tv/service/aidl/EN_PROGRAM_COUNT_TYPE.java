package com.mstar.tv.service.aidl;

import android.os.Parcel;
import android.os.Parcelable;

public enum EN_PROGRAM_COUNT_TYPE implements Parcelable
{
	 // / program count both ATV and DTV type
    E_COUNT_ATV_DTV,
    // / program count ATV type only
    E_COUNT_ATV,
    // / program count DTV(TV+Radid+Data) type
    E_COUNT_DTV,
    // / program count DTV TV type only
    E_COUNT_DTV_TV,
    // / program count DTV Radio type only
    E_COUNT_DTV_RADIO,
    // / program count DTV Data type only
    E_COUNT_DTV_DATA,
    // / program up/down loop type count
    E_COUNT_TYPE_MAX;
    
	public static final Parcelable.Creator<EN_PROGRAM_COUNT_TYPE> CREATOR = new Parcelable.Creator<EN_PROGRAM_COUNT_TYPE>()
	{
		public EN_PROGRAM_COUNT_TYPE createFromParcel(Parcel in)
		{
			return EN_PROGRAM_COUNT_TYPE.values()[in.readInt()];
		}

		public EN_PROGRAM_COUNT_TYPE[] newArray(int size)
		{
			return new EN_PROGRAM_COUNT_TYPE[size];
		}
	};

	@Override
	public int describeContents()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel arg0, int arg1)
	{
		arg0.writeInt(this.ordinal());
	}
}
