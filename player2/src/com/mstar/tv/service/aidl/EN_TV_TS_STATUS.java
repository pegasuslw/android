package com.mstar.tv.service.aidl;

import android.os.Parcel;
import android.os.Parcelable;

public enum EN_TV_TS_STATUS implements Parcelable{
	
	E_TS_NONE,
	// channel tuning is in atv manual tuning
	E_TS_ATV_MANU_TUNING_LEFT, E_TS_ATV_MANU_TUNING_RIGHT,
	// channel tuning is in atv auto tuning
	E_TS_ATV_AUTO_TUNING,
	// channel tuning is in atv scan pausing
	E_TS_ATV_SCAN_PAUSING,
	// channel tuning is in dtv scan manual pausing
	E_TS_DTV_MANU_TUNING,
	// channel tuning is in dtv scan auto tuning
	E_TS_DTV_AUTO_TUNING,
	// channel tuning is in dtv scan full tuning
	E_TS_DTV_FULL_TUNING,
	// channel tuning is in dtv scan pausing
	E_TS_DTV_SCAN_PAUSING;
   public static final Parcelable.Creator<EN_TV_TS_STATUS> CREATOR = new Parcelable.Creator<EN_TV_TS_STATUS>()
			{
				public EN_TV_TS_STATUS createFromParcel(Parcel in)
				{
					return EN_TV_TS_STATUS.values()[in.readInt()];
				}

				public EN_TV_TS_STATUS[] newArray(int size)
				{
					return new EN_TV_TS_STATUS[size];
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


