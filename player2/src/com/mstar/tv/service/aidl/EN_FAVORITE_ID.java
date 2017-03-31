package com.mstar.tv.service.aidl;

import android.os.Parcel;
import android.os.Parcelable;

public enum EN_FAVORITE_ID implements Parcelable{
	
	 E_FAVORITE_ID_1,
     E_FAVORITE_ID_2,
     E_FAVORITE_ID_3,
     E_FAVORITE_ID_4,
     E_FAVORITE_ID_5,
     E_FAVORITE_ID_6,
     E_FAVORITE_ID_7,
     E_FAVORITE_ID_8;

	public static final Parcelable.Creator<EN_FAVORITE_ID> CREATOR = new Parcelable.Creator<EN_FAVORITE_ID>()
			{
				public EN_FAVORITE_ID createFromParcel(Parcel in)
				{
					return EN_FAVORITE_ID.values()[in.readInt()];
				}

				public EN_FAVORITE_ID[] newArray(int size)
				{
					return new EN_FAVORITE_ID[size];
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
