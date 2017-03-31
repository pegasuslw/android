package com.mstar.tv.service.aidl;

import android.os.Parcel;
import android.os.Parcelable;

public enum EN_MS_VIDEOITEM implements Parcelable{
	
	// / brightness
	MS_VIDEOITEM_BRIGHTNESS,
	// / contrast
	MS_VIDEOITEM_CONTRAST,
	// / saturation
	MS_VIDEOITEM_SATURATION,
	// / sharpness
	MS_VIDEOITEM_SHARPNESS,
	// / hue
	MS_VIDEOITEM_HUE,
	// num of video item
	MS_VIDEOITEM_NUM;

	public static final Parcelable.Creator<EN_MS_VIDEOITEM> CREATOR = new Parcelable.Creator<EN_MS_VIDEOITEM>()
			{
				public EN_MS_VIDEOITEM createFromParcel(Parcel in)
				{
					return EN_MS_VIDEOITEM.values()[in.readInt()];
				}

				public EN_MS_VIDEOITEM[] newArray(int size)
				{
					return new EN_MS_VIDEOITEM[size];
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
