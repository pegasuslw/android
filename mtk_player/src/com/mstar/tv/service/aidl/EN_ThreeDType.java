package com.mstar.tv.service.aidl;

import android.os.Parcel;
import android.os.Parcelable;

public enum EN_ThreeDType implements Parcelable {
	// / None
	EN_3D_NONE,
	// / side by side half
	EN_3D_SIDE_BY_SIDE_HALF,
	// / top bottom
	EN_3D_TOP_BOTTOM,
	// / 1080p frame packing
	EN_3D_FRAME_PACKING_1080P,
	// / 720p frame packing
	EN_3D_FRAME_PACKING_720P,
	// / line alternative
	EN_3D_LINE_ALTERNATIVE,
	// / frame alernative
	EN_3D_FRAME_ALTERNATIVE,
	// / field alernative
	EN_3D_FIELD_ALTERNATIVE,
	// / check board
	EN_3D_CHECK_BORAD,
	// / 2DTo3D
	EN_3D_2DTO3D,
	// / format number
	EN_3D_TYPE_NUM;

	public static final Parcelable.Creator<EN_ThreeDType> CREATOR = new Parcelable.Creator<EN_ThreeDType>() {
		public EN_ThreeDType createFromParcel(Parcel in) {
			return EN_ThreeDType.values()[in.readInt()];
		}

		public EN_ThreeDType[] newArray(int size) {
			return new EN_ThreeDType[size];
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

