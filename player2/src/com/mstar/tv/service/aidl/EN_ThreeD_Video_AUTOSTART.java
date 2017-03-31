package com.mstar.tv.service.aidl;

import android.os.Parcel;
import android.os.Parcelable;

public enum EN_ThreeD_Video_AUTOSTART implements Parcelable {
	// /auto start off
			DB_ThreeD_Video_AUTOSTART_OFF,
			// /auto start 2D
			DB_ThreeD_Video_AUTOSTART_2D,
			// /auto start 3D
			DB_ThreeD_Video_AUTOSTART_3D,
			// /auto start total number
			DB_ThreeD_Video_AUTOSTART_COUNT;

	public static final Parcelable.Creator<EN_ThreeD_Video_AUTOSTART> CREATOR = new Parcelable.Creator<EN_ThreeD_Video_AUTOSTART>()
			{
				public EN_ThreeD_Video_AUTOSTART createFromParcel(Parcel in)
				{
					return EN_ThreeD_Video_AUTOSTART.values()[in.readInt()];
				}

				public EN_ThreeD_Video_AUTOSTART[] newArray(int size)
				{
					return new EN_ThreeD_Video_AUTOSTART[size];
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
