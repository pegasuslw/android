package com.mstar.tv.service.aidl;

import android.os.Parcel;
import android.os.Parcelable;

public enum EN_TUNING_SCAN_TYPE implements Parcelable{
	
	E_SCAN_ATV, 
	E_SCAN_DTV, 
	E_SCAN_ALL;
   public static final Parcelable.Creator<EN_TUNING_SCAN_TYPE> CREATOR = new Parcelable.Creator<EN_TUNING_SCAN_TYPE>()
			{
				public EN_TUNING_SCAN_TYPE createFromParcel(Parcel in)
				{
					return EN_TUNING_SCAN_TYPE.values()[in.readInt()];
				}

				public EN_TUNING_SCAN_TYPE[] newArray(int size)
				{
					return new EN_TUNING_SCAN_TYPE[size];
				}
			};

   @Override
   public int describeContents() {
  // TODO Auto-generated method stub
   return 0;
   }

   @Override
   public void writeToParcel(Parcel dest, int flags) {
     // TODO Auto-generated method stub
    dest.writeInt(this.ordinal());
   }

}
