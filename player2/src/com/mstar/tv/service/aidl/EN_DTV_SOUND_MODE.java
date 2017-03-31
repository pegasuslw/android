package com.mstar.tv.service.aidl;

import android.os.Parcel;
import android.os.Parcelable;

public enum EN_DTV_SOUND_MODE implements Parcelable{
	// / DTV sound mode: stereo
    EN_SOUND_STEREO,
    // / DTV sound mode: left
    EN_SOUND_LEFT,
    // / DTV sound mode: right
    EN_SOUND_RIGHT,
    // / DTV sound mode: mixed
    EN_SOUND_MIXED,
    // / the number of DTV sound mode
    EN_SOUND_NUM;

    public static final Parcelable.Creator<EN_DTV_SOUND_MODE> CREATOR = new Parcelable.Creator<EN_DTV_SOUND_MODE>()
    		{
    			public EN_DTV_SOUND_MODE createFromParcel(Parcel in)
    			{
    				return  EN_DTV_SOUND_MODE.values()[in.readInt()];
    			}

    			public EN_DTV_SOUND_MODE[] newArray(int size)
    			{
    				return new EN_DTV_SOUND_MODE[size];
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
