package com.mstar.tv.service.aidl;

import android.os.Parcel;
import android.os.Parcelable;

public enum EN_ThreeD_Video_SELFADAPTIVE_DETECT implements Parcelable{
	// / 3D Self Adaptive detect Off
			DB_ThreeD_Video_SELF_ADAPTIVE_DETECT_OFF,
			// / 3D Self Adaptive detect Right Now
			DB_ThreeD_Video_SELF_ADAPTIVE_DETECT_RIGHT_NOW,
			// / 3D Self Adaptive detect When Source Change
			DB_ThreeD_Video_SELF_ADAPTIVE_DETECT_WHEN_SOURCE_CHANGE,
			// / total detect number
			DB_ThreeD_Video_DISPLAYFORMAT_COUNT;

			
			public static final Parcelable.Creator<EN_ThreeD_Video_SELFADAPTIVE_DETECT> CREATOR = new Parcelable.Creator<EN_ThreeD_Video_SELFADAPTIVE_DETECT>()
					{
						public EN_ThreeD_Video_SELFADAPTIVE_DETECT createFromParcel(Parcel in)
						{
							return EN_ThreeD_Video_SELFADAPTIVE_DETECT.values()[in.readInt()];
						}

						public EN_ThreeD_Video_SELFADAPTIVE_DETECT[] newArray(int size)
						{
							return new EN_ThreeD_Video_SELFADAPTIVE_DETECT[size];
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
