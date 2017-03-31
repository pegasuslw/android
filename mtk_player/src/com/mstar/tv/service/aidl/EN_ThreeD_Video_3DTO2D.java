package com.mstar.tv.service.aidl;

import android.os.Parcel;
import android.os.Parcelable;

public enum EN_ThreeD_Video_3DTO2D implements Parcelable{
	// / 3Dto2D Off mode
			DB_ThreeD_Video_3DTO2D_NONE,
			// / 3Dto2D Side By Side mode
			DB_ThreeD_Video_3DTO2D_SIDE_BY_SIDE,
			// / 3Dto2D Top Bottom mode
			DB_ThreeD_Video_3DTO2D_TOP_BOTTOM,
			// / 3Dto2D Frame Packing mode
			DB_ThreeD_Video_3DTO2D_FRAME_PACKING,
			// / 3Dto2D Line Alternative mode
			DB_ThreeD_Video_3DTO2D_LINE_ALTERNATIVE,
			// / 3Dto2D Auto mode
			DB_ThreeD_Video_3DTO2D_AUTO,
			// / total mode number
			DB_ThreeD_Video_3DTO2D_COUNT;

	public static final Parcelable.Creator<EN_ThreeD_Video_3DTO2D> CREATOR = new Parcelable.Creator<EN_ThreeD_Video_3DTO2D>()
			{
				public EN_ThreeD_Video_3DTO2D createFromParcel(Parcel in)
				{
					return EN_ThreeD_Video_3DTO2D.values()[in.readInt()];
				}

				public EN_ThreeD_Video_3DTO2D[] newArray(int size)
				{
					return new EN_ThreeD_Video_3DTO2D[size];
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
