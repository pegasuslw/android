package com.mstar.tv.service.aidl;

import android.os.Parcel;
import android.os.Parcelable;

public enum EN_ThreeD_OSD_TYPE implements Parcelable{
		// Osd 3d off
		EN_ThreeD_OSD_OFF,
		// side by side 
		EN_ThreeD_OSD_SIDE_BY_SIDE,
		// top and bottom
		EN_ThreeD_OSD_TOP_BOTTOM,
		// top and bottom la
		EN_ThreeD_OSD_TOP_BOTTOM_LA,
		// line alternavtive
		EN_ThreeD_OSD_LINE_ALTERNATIVE,
		// top line alternavtive
		EN_ThreeD_OSD_TOP_LA,
		// bottom line alternavtive
		EN_ThreeD_OSD_BOTTOM_LA,
		// Left only
		EN_ThreeD_OSD_LEFT_ONLY,
		// Right only
		EN_ThreeD_OSD_RIGHT_ONLY,
		//Top only
		EN_ThreeD_OSD_TOP_ONLY,
		// Bottom only
		EN_ThreeD_OSD_BOTTOM_ONLY,
		// 3D output aspect in fullscreen
		EN_ThreeD_OSD_NUM;
	
	public static final Parcelable.Creator<EN_ThreeD_OSD_TYPE> CREATOR = new Parcelable.Creator<EN_ThreeD_OSD_TYPE>()
			{
				public EN_ThreeD_OSD_TYPE createFromParcel(Parcel in)
				{
					return EN_ThreeD_OSD_TYPE.values()[in.readInt()];
				}

				public EN_ThreeD_OSD_TYPE[] newArray(int size)
				{
					return new EN_ThreeD_OSD_TYPE[size];
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
