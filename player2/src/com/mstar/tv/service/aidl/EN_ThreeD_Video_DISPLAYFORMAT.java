package com.mstar.tv.service.aidl;

import android.os.Parcel;
import android.os.Parcelable;

public enum EN_ThreeD_Video_DISPLAYFORMAT implements Parcelable{
	// / 3D Off mode
			DB_ThreeD_Video_DISPLAYFORMAT_NONE,
			// / 3D Side By Side mode
			DB_ThreeD_Video_DISPLAYFORMAT_SIDE_BY_SIDE,
			// / 3D Top Bottom mode
			DB_ThreeD_Video_DISPLAYFORMAT_TOP_BOTTOM,
			// / 3D Frame Packing mode
			DB_ThreeD_Video_DISPLAYFORMAT_FRAME_PACKING,
			// / 3D Line Alternative mode
			DB_ThreeD_Video_DISPLAYFORMAT_LINE_ALTERNATIVE,
			// / 3D 2Dto3D mode
			DB_ThreeD_Video_DISPLAYFORMAT_2DTO3D,
			// / 3D Auto mode
			DB_ThreeD_Video_DISPLAYFORMAT_AUTO,
			// / total format number
			DB_ThreeD_Video_DISPLAYFORMAT_COUNT;
	public static final Parcelable.Creator<EN_ThreeD_Video_DISPLAYFORMAT> CREATOR = new Parcelable.Creator<EN_ThreeD_Video_DISPLAYFORMAT>()
			{
				public EN_ThreeD_Video_DISPLAYFORMAT createFromParcel(Parcel in)
				{
					return EN_ThreeD_Video_DISPLAYFORMAT.values()[in.readInt()];
				}

				public EN_ThreeD_Video_DISPLAYFORMAT[] newArray(int size)
				{
					return new EN_ThreeD_Video_DISPLAYFORMAT[size];
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
