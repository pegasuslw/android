package com.mstar.tv.service.aidl;

import android.os.Parcel;
import android.os.Parcelable;

public enum EN_PROGRAM_INFO_TYPE implements Parcelable
{
	 // / Query the cuttent program information
    E_INFO_CURRENT,
    // / Query the previous program information
    E_INFO_PREVIOUS,
    // / Query the next program information
    E_INFO_NEXT,
    // / Query the program information by database index
    E_INFO_DATABASE_INDEX,
    // / Query the program information by program number
    E_INFO_PROGRAM_NUMBER,
    // / Query the previous program information by your input program
    E_INFO_PREVIOUS_BY_NUMBER,
    // / Query the next program information by your input program
    E_INFO_NEXT_BY_NUMBER,
    // / Query the program information type count
    E_INFO_TYPE_MAX;
    
	public static final Parcelable.Creator<EN_PROGRAM_INFO_TYPE> CREATOR = new Parcelable.Creator<EN_PROGRAM_INFO_TYPE>()
	{
		public EN_PROGRAM_INFO_TYPE createFromParcel(Parcel in)
		{
			return EN_PROGRAM_INFO_TYPE.values()[in.readInt()];
		}

		public EN_PROGRAM_INFO_TYPE[] newArray(int size)
		{
			return new EN_PROGRAM_INFO_TYPE[size];
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
