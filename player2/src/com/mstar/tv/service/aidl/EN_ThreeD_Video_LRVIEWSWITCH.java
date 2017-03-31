package com.mstar.tv.service.aidl;

import android.os.Parcel;
import android.os.Parcelable;

public enum EN_ThreeD_Video_LRVIEWSWITCH implements Parcelable {
	// / 3D Left Right exchanging
			DB_ThreeD_Video_LRVIEWSWITCH_EXCHANGE,
			// / 3D Left Right not exchanging
			DB_ThreeD_Video_LRVIEWSWITCH_NOTEXCHANGE,
			// / 3D Left Right exchanging total number
			DB_ThreeD_Video_LRVIEWSWITCH_COUNT;

	public static final Parcelable.Creator<EN_ThreeD_Video_LRVIEWSWITCH> CREATOR = new Parcelable.Creator<EN_ThreeD_Video_LRVIEWSWITCH>()
			{
				public EN_ThreeD_Video_LRVIEWSWITCH createFromParcel(Parcel in)
				{
					return EN_ThreeD_Video_LRVIEWSWITCH.values()[in.readInt()];
				}

				public EN_ThreeD_Video_LRVIEWSWITCH[] newArray(int size)
				{
					return new EN_ThreeD_Video_LRVIEWSWITCH[size];
				}
			};
	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(this.ordinal());
	}

}
