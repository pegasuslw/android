package com.mstar.tv.service.aidl;

import android.os.Parcel;
import android.os.Parcelable;

public enum EnumInfoType implements Parcelable{
	// / RF signal information type first to show
    E_FIRST_TO_SHOW_RF,
    // / RF signal information type next
    E_NEXT_RF,
    // / RF signal information type prev
    E_PREV_RF,
    // / RF signal information type RF
    E_RF_INFO;

    public static final Parcelable.Creator<EnumInfoType> CREATOR = new Parcelable.Creator<EnumInfoType>()
    		{
    			public EnumInfoType createFromParcel(Parcel in)
    			{
    				return  EnumInfoType.values()[in.readInt()];
    			}

    			public EnumInfoType[] newArray(int size)
    			{
    				return new EnumInfoType[size];
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
