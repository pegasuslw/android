package com.mstar.tv.service.aidl;

import android.os.Parcel;
import android.os.Parcelable;

public enum EN_MEMBER_LANGUAGE implements Parcelable
{
	E_LANGUAGE_ENGLISH,
	E_LANGUAGE_CHINESE_S;
//	E_LANGUAGE_CHINESE_T, 
//	E_LANGUAGE_MAX;
	public static final Parcelable.Creator<EN_MEMBER_LANGUAGE> CREATOR = new Parcelable.Creator<EN_MEMBER_LANGUAGE>()
			{
				public EN_MEMBER_LANGUAGE createFromParcel(Parcel in)
				{
					return EN_MEMBER_LANGUAGE.values()[in.readInt()];
				}

				public EN_MEMBER_LANGUAGE[] newArray(int size)
				{
					return new EN_MEMBER_LANGUAGE[size];
				}
			};
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeInt(this.ordinal());
	}

}
