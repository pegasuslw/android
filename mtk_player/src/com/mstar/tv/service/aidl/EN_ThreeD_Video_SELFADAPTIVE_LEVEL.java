package com.mstar.tv.service.aidl;

import android.os.Parcel;
import android.os.Parcelable;

public enum EN_ThreeD_Video_SELFADAPTIVE_LEVEL implements Parcelable{
	// / 3D Self Adaptive level Low
			DB_ThreeD_Video_SELF_ADAPTIVE_LOW,
			// / 3D Self Adaptive level Middle
			DB_ThreeD_Video_SELF_ADAPTIVE_MIDDLE,
			// / 3D Self Adaptive level High
			DB_ThreeD_Video_SELF_ADAPTIVE_HIGH,
			// / total level number
			DB_ThreeD_Video_DISPLAYFORMAT_COUNT;

	public static final Parcelable.Creator<EN_ThreeD_Video_SELFADAPTIVE_LEVEL> CREATOR = new Parcelable.Creator<EN_ThreeD_Video_SELFADAPTIVE_LEVEL>()
			{
				public EN_ThreeD_Video_SELFADAPTIVE_LEVEL createFromParcel(Parcel in)
				{
					return EN_ThreeD_Video_SELFADAPTIVE_LEVEL.values()[in.readInt()];
				}

				public EN_ThreeD_Video_SELFADAPTIVE_LEVEL[] newArray(int size)
				{
					return new EN_ThreeD_Video_SELFADAPTIVE_LEVEL[size];
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
