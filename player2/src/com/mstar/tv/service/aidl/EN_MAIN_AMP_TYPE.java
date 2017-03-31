package com.mstar.tv.service.aidl;

import android.os.Parcelable;
import android.os.Parcel;

public enum EN_MAIN_AMP_TYPE implements Parcelable{
	
	E_MAIN_AMP_IN,

	E_MAIN_AMP_OUT;
	
	public static final Parcelable.Creator<EN_MAIN_AMP_TYPE> CREATOR = new Parcelable.Creator<EN_MAIN_AMP_TYPE>()
	{
		public EN_MAIN_AMP_TYPE createFromParcel(Parcel in)
		{
			return EN_MAIN_AMP_TYPE.values()[in.readInt()];
		}

		public EN_MAIN_AMP_TYPE[] newArray(int size)
		{
			return new EN_MAIN_AMP_TYPE[size];
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
