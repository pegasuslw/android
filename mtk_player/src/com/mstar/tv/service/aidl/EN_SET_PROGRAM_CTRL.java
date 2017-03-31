package com.mstar.tv.service.aidl;

import android.os.Parcel;
import android.os.Parcelable;

public enum EN_SET_PROGRAM_CTRL implements Parcelable
{
	// / Reset channel data
    E_RESET_CHANNEL_DATA,
    // / Init all channel data
    E_INIT_ALL_CHANNEL_DATA,
    // / Set current program number
    E_SET_CURRENT_PROGRAM_NUMBER,
    // / Inc current program number
    E_INC_CURRENT_PROGRAM_NUMBER,
    // / Dec current program number
    E_DEC_CURRENT_PROGRAM_NUMBER,
    // / Delete program
    E_DELETE_PROGRAM,
    // / Move program
    E_MOVE_PROGRAM,
    // / Swap program
    E_SWAP_PROGRAM,
    // / Copy program
    E_COPY_PROGRAM;

    public static final Parcelable.Creator<EN_SET_PROGRAM_CTRL> CREATOR = new Parcelable.Creator<EN_SET_PROGRAM_CTRL>()
    		{
    			public EN_SET_PROGRAM_CTRL createFromParcel(Parcel in)
    			{
    				return EN_SET_PROGRAM_CTRL.values()[in.readInt()];
    			}

    			public EN_SET_PROGRAM_CTRL[] newArray(int size)
    			{
    				return new EN_SET_PROGRAM_CTRL[size];
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
