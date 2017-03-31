package com.mstar.tv.service.aidl;

import android.os.Parcel;
import android.os.Parcelable;

public enum EN_SET_PROGRAM_INFO implements Parcelable
{
	// /Set program pll data
    E_SET_PROGRAM_PLL_DATA,
    // /Set audio standard
    E_SET_AUDIO_STANDARD,
    // /Set video standard of program
    E_SET_VIDEO_STANDARD_OF_PROGRAM,
    // /Skip program
    E_SKIP_PROGRAM,
    // /Hide program
    E_HIDE_PROGRAM,
    // /Lock program
    E_LOCK_PROGRAM,
    // /Need aft
    E_NEED_AFT,
    // /Set direct tuned
    E_SET_DIRECT_TUNED,
    // /Set AFT offset
    E_SET_AFT_OFFSET,
    // /Enable realtime audio detection
    E_ENABLE_REALTIME_AUDIO_DETECTION,
    // /Set station name
    E_SET_STATION_NAME,
    // /Set sorting priority
    E_SET_SORTING_PRIORITY,
    // /Set channel index
    E_SET_CHANNEL_INDEX,
    // /Get ATV auto color
    // SET_ATV_AUTOCOLOR, -> for isdb, remark
    // /Set misc
    E_SET_MISC;

    public static final Parcelable.Creator<EN_SET_PROGRAM_INFO> CREATOR = new Parcelable.Creator<EN_SET_PROGRAM_INFO>()
    		{
    			public EN_SET_PROGRAM_INFO createFromParcel(Parcel in)
    			{
    				return EN_SET_PROGRAM_INFO.values()[in.readInt()];
    			}

    			public EN_SET_PROGRAM_INFO[] newArray(int size)
    			{
    				return new EN_SET_PROGRAM_INFO[size];
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
