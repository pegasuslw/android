package com.mstar.tv.service.aidl;

import android.os.Parcel;
import android.os.Parcelable;

public enum EN_GET_PROGRAM_CTRL implements Parcelable{
	
	// /Get current program number
    E_GET_CURRENT_PROGRAM_NUMBER,
    // /Get first program number
    E_GET_FIRST_PROGRAM_NUMBER,
    // /Get next program number
    E_GET_NEXT_PROGRAM_NUMBER,
    // /Get prev program number
    E_GET_PREV_PROGRAM_NUMBER,
    // /Get past program number
    E_GET_PAST_PROGRAM_NUMBER,
    // /Is program number active
    E_IS_PROGRAM_NUMBER_ACTIVE,
    // /Is program empty
    E_IS_PROGRAM_EMPTY,
    // /Get active program count
    E_GET_ACTIVE_PROGRAM_COUNT,
    // /Get non skip program count
    E_GET_NON_SKIP_PROGRAM_COUNT,
    // /Get channel max
    E_GET_CHANNEL_MAX,
    // /Get channel min
    E_GET_CHANNEL_MIN;

  public static final Parcelable.Creator<EN_GET_PROGRAM_CTRL> CREATOR = new Parcelable.Creator<EN_GET_PROGRAM_CTRL>()
			{
				public EN_GET_PROGRAM_CTRL createFromParcel(Parcel in)
				{
					return EN_GET_PROGRAM_CTRL.values()[in.readInt()];
				}

				public EN_GET_PROGRAM_CTRL[] newArray(int size)
				{
					return new EN_GET_PROGRAM_CTRL[size];
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

