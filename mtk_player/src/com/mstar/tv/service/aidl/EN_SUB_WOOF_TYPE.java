package com.mstar.tv.service.aidl;
import android.os.Parcel;
import android.os.Parcelable;

public enum EN_SUB_WOOF_TYPE implements Parcelable
{
	
	E_SUB_WOOF_IN,

	E_SUB_WOOF_OUT,

	E_SUB_WOOF_OFF;


	public static final Parcelable.Creator<EN_SUB_WOOF_TYPE> CREATOR = new Parcelable.Creator<EN_SUB_WOOF_TYPE>()
	{
		public EN_SUB_WOOF_TYPE createFromParcel(Parcel in)
		{
			return EN_SUB_WOOF_TYPE.values()[in.readInt()];
		}

		public EN_SUB_WOOF_TYPE[] newArray(int size)
		{
			return new EN_SUB_WOOF_TYPE[size];
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
	

