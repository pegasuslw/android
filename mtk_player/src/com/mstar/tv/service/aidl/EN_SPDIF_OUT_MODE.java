package com.mstar.tv.service.aidl;

import android.os.Parcel;
import android.os.Parcelable;

public enum EN_SPDIF_OUT_MODE implements Parcelable
{
	

	        //DTS type
			PDIF_MODE_OFF,
			// /PCM type
			PDIF_MODE_PCM,
			// /RAW type
			PDIF_MODE_RAW;
			//  PDIF_MODE NUM
//			PDIF_MODE_NUM;

		public static final Parcelable.Creator<EN_SPDIF_OUT_MODE> CREATOR = new Parcelable.Creator<EN_SPDIF_OUT_MODE>()
				{
					public EN_SPDIF_OUT_MODE createFromParcel(Parcel in)
					{
						return EN_SPDIF_OUT_MODE.values()[in.readInt()];
					}

					public EN_SPDIF_OUT_MODE[] newArray(int size)
					{
						return new EN_SPDIF_OUT_MODE[size];
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
