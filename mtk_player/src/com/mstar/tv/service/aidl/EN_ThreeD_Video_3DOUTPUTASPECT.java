package com.mstar.tv.service.aidl;

import android.os.Parcel;
import android.os.Parcelable;

public enum EN_ThreeD_Video_3DOUTPUTASPECT implements Parcelable{
	// / 3D output aspect in fullscreen
			DB_ThreeD_Video_3DOUTPUTASPECT_FULLSCREEN,
			// / 3D output aspect in center
			DB_ThreeD_Video_3DOUTPUTASPECT_CENTER,
			// / 3D output aspect in auto adapted
			DB_ThreeD_Video_3DOUTPUTASPECT_AUTOADAPTED,
			// / 3D output aspect total number
			DB_ThreeD_Video_3DOUTPUTASPECT_COUNT;
	public static final Parcelable.Creator<EN_ThreeD_Video_3DOUTPUTASPECT> CREATOR = new Parcelable.Creator<EN_ThreeD_Video_3DOUTPUTASPECT>()
			{
				public EN_ThreeD_Video_3DOUTPUTASPECT createFromParcel(Parcel in)
				{
					return EN_ThreeD_Video_3DOUTPUTASPECT.values()[in.readInt()];
				}

				public EN_ThreeD_Video_3DOUTPUTASPECT[] newArray(int size)
				{
					return new EN_ThreeD_Video_3DOUTPUTASPECT[size];
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
