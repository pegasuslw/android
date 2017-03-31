package com.mstar.tv.service.aidl;

import android.os.Parcel;
import android.os.Parcelable;

public enum EN_MEMBER_SERVICE_TYPE implements Parcelable
{
	E_SERVICETYPE_ATV, // /< Service type ATV
	E_SERVICETYPE_DTV, // /< Service type DTV
	E_SERVICETYPE_RADIO, // /< Service type Radio
	E_SERVICETYPE_DATA, // /< Service type Data
	E_SERVICETYPE_UNITED_TV, // /< Service type United TV
	E_SERVICETYPE_INVALID; // /< Service type INVALID
	public static final Parcelable.Creator<EN_MEMBER_SERVICE_TYPE> CREATOR = new Parcelable.Creator<EN_MEMBER_SERVICE_TYPE>()
			{
				public EN_MEMBER_SERVICE_TYPE createFromParcel(Parcel in)
				{
					return EN_MEMBER_SERVICE_TYPE.values()[in.readInt()];
				}

				public EN_MEMBER_SERVICE_TYPE[] newArray(int size)
				{
					return new EN_MEMBER_SERVICE_TYPE[size];
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

