
package com.mstar.tv.service.aidl;

import android.os.Parcel;
import android.os.Parcelable;

public enum EN_MS_PICTURE implements Parcelable
{
		// / picture mode dynamic
		PICTURE_DYNAMIC,
		// / picture mode normal
		PICTURE_NORMAL,
		// / picture mode mild
		PICTURE_SOFT,
		// / picture mode user
		PICTURE_USER,
		// / picture mode vivid
		PICTURE_VIVID,
		// / picture mode natural
		PICTURE_NATURAL,
		// / picture mode sports
		PICTURE_SPORTS,
		// / picture mode number
		PICTURE_NUMS;

	public static final Parcelable.Creator<EN_MS_PICTURE> CREATOR = new Parcelable.Creator<EN_MS_PICTURE>()
	{
		public EN_MS_PICTURE createFromParcel(Parcel in)
		{
			return EN_MS_PICTURE.values()[in.readInt()];
		}

		public EN_MS_PICTURE[] newArray(int size)
		{
			return new EN_MS_PICTURE[size];
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