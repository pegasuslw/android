package com.mstar.tv.service.aidl;

import android.os.Parcel;
import android.os.Parcelable;

public enum EN_PROGRAM_ATTRIBUTE implements Parcelable
{
	E_DELETE, 
	E_LOCK, 
	E_SKIP, 
	E_HIDE, 
	E_MAX;
    
  public static final Parcelable.Creator<EN_PROGRAM_ATTRIBUTE> CREATOR = new Parcelable.Creator<EN_PROGRAM_ATTRIBUTE>()
	{
		public EN_PROGRAM_ATTRIBUTE createFromParcel(Parcel in)
		{
			return EN_PROGRAM_ATTRIBUTE.values()[in.readInt()];
		}

		public EN_PROGRAM_ATTRIBUTE[] newArray(int size)
		{
			return new EN_PROGRAM_ATTRIBUTE[size];
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
